package com.cisdi.ext.wf;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.link.linkPackage.AttLinkDifferentProcess;
import com.cisdi.ext.model.GrSetValue;
import com.cisdi.ext.model.PmFundReqPlan;
import com.cisdi.ext.pm.development.PmPrjReqExt;
import com.cisdi.ext.pm.PrjMaterialInventory;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;

@Slf4j
public class WfExt {

    public void changeStatusToAping() {
        String newStatus = "APING";
        changeStatus(newStatus);
    }

    public void changeStatusToDr() {
        String newStatus = "DR";
        changeStatus(newStatus);
    }

    public void changeStatusToAp() {
        String newStatus = "AP";
        changeStatus(newStatus);
        //写文件到项目清单明细
        this.addInventoryDtl();
    }

    public void changeStatusToDn() {
        String newStatus = "DN";
        changeStatus(newStatus);
    }

    public void changeStatusToVding() {
        String newStatus = "VDING";
        changeStatus(newStatus);
    }

    public void changeStatusToVd() {
        String newStatus = "VD";
        changeStatus(newStatus);
    }

    private void changeStatus(String newStatus) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
                Map<String, Object> valueMap = entityRecord.valueMap;
                int update = myJdbcTemplate.update("update " + entityCode + " t set t.status = ? where t.id=?", newStatus, csCommId);
                log.info("已更新：{}", update);

                String processName = "", nowDate = "",userName = "";
                String sql0 = "SELECT c.name as processName,b.IS_URGENT,b.START_DATETIME," +
                        "(select name from ad_user where id = b.START_USER_ID) as userName FROM "+entityCode+" a " +
                        "LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id LEFT JOIN wf_process c on b.WF_PROCESS_ID = c.id WHERE a.id = ?";
                List<Map<String,Object>> list0 = myJdbcTemplate.queryForList(sql0,csCommId);
                if (!CollectionUtils.isEmpty(list0)){
                    processName = JdbcMapUtil.getString(list0.get(0),"processName");
                    nowDate = JdbcMapUtil.getString(list0.get(0),"START_DATETIME").replace("T"," ");
                    userName = JdbcMapUtil.getString(list0.get(0),"userName");
                }

                //需要自定义标题的流程
                List<String> tableList = AttLinkDifferentProcess.getProcessTable();
                if (CollectionUtils.isEmpty(tableList)){
                    throw new BaseException("父实体为通用单据的实体不能为空，请联系管理员处理");
                }

                // 审批流审批通过
                if ("AP".equals(newStatus)) {

                    // 流程文件资料同步至资料库 采用定时任务，将任务写入中间表 已有定时任务 暂时取消 暂时勿删
//                    dataToFileTask(entityCode,csCommId,ExtJarHelper.procInstId.get());

                    //一些特殊流程发起后即结束。流程名称处理
                    List<String> endProcessList = getEndProcessList();
                    if (endProcessList.contains(entityCode)){
                        int update1 = myJdbcTemplate.update("update wf_process_instance pi join wf_process p on pi" +
                                ".WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join " + entityCode + " t on pi" +
                                ".ENTITY_RECORD_ID=t.id join pm_prj prj on t.PM_PRJ_ID=prj.id and t.id=? set pi.name=concat( p.name,'-', " +
                                "prj.name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
                    }

                    // 特殊流程，名称在流程发起时即生成，此处不生成
                    List<String> specialList = getSpecialList();
                    if (tableList.contains(entityCode)) {
                        if (specialList.contains(entityCode)){
                            continue;
                        } else if ("PO_ORDER_REQ".equals(entityCode)) {
                            String name = entityRecord.valueMap.get("CONTRACT_NAME").toString();
                            myJdbcTemplate.update("update PM_PRJ_REQ t set t.name=? where t.id=?", name, csCommId);
                        } else if ("PM_PRJ_STOP_ORDER_REQ".equals(entityCode)){
                            myJdbcTemplate.update("update PM_PRJ_STOP_ORDER_REQ t set t.name=t.REMARK_TWO where t.id=?", csCommId);
                        } else if ("PM_BID_KEEP_FILE_REQ".equals(entityCode)){
                            String sql = "update wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id LEFT JOIN pm_bid_keep_file_req c on a.id = c.LK_WF_INST_ID " +
                                    "LEFT JOIN pm_prj d on c.PM_PRJ_ID = d.id LEFT JOIN ad_user e on c.CRT_USER_ID = e.id " +
                                    "set a.name = concat(b.name,'-',d.name,'-',e.name,'-',now()) where c.id = ?";
                            myJdbcTemplate.update(sql, csCommId);
                            myJdbcTemplate.update("update pm_bid_keep_file_req a LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id set a.name = b.name where a.id = ?",csCommId);
                        } else {
                            String sql1 = "select a.NAME from wf_process_instance a left join " + entityCode + " b on a.id = b.LK_WF_INST_ID where b.id = ?";
                            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql1, csCommId);
                            if (CollectionUtils.isEmpty(list)) {
                                throw new BaseException("流程标题不能为空");
                            }
                            String name = JdbcMapUtil.getString(list.get(0),"NAME");
                            String sql2 = "update " + entityCode + " set NAME = ? where id = ?";
                            myJdbcTemplate.update(sql2, name, csCommId);
                        }

                    }

                    // 资金需求计划申请完成同步数据
                    if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entityCode)) {
                        PmFundReqPlan pmFundReqPlan = PmFundReqPlan.insertData();
                        String id = pmFundReqPlan.getId();
                        String pmFundRequirePlanReqId = entityRecord.csCommId;
                        // 项目id
                        String prjId = valueMap.get("AMOUT_PM_PRJ_ID").toString();
                        // 发起部门id
                        String hrDeptId = valueMap.get("CRT_DEPT_ID").toString();
                        // 计划需求总金额 待修改
                        //        String totalAmt = valueMap.get("").toString();
                        BigDecimal totalAmt = new BigDecimal(10);
                        // 提出时间
                        String applyTime = valueMap.get("CRT_DT").toString();
                        // 备注
                        String remark = valueMap.get("REMARK").toString();
                        // 计划名称 待修改
                        String name = myJdbcTemplate.queryForMap("select name from pm_prj where id = ?", valueMap.get(
                                "AMOUT_PM_PRJ_ID")).get("name").toString() + "-资金需求计划";

                        String sql = "update pm_fund_req_plan set NAME = ?,PM_PRJ_ID = ?,HR_DEPT_ID = ?,TOTAL_AMT = ?,APPLY_TIME = ?," +
                                "PM_FUND_REQUIRE_PLAN_REQ_ID = ?,REMARK = ? where id = ?";
                        myJdbcTemplate.update(sql, name, prjId, hrDeptId, totalAmt, applyTime, pmFundRequirePlanReqId, remark, id);
                    }
                }

                // 审批流程创建
                if ("APING".equals(newStatus)) {
                    checkStartDept(valueMap); // 发起人是否存在部门信息校验
                    createProcessTitle(entityCode,entityRecord,myJdbcTemplate); // 流程标题创建
                }
            }
        }
    }

    /**
     * 流程发起人部门校验
     * @param valueMap 流程表单数据详情
     */
    private void checkStartDept(Map<String, Object> valueMap) {
        String hrDeptId = JdbcMapUtil.getString(valueMap,"CRT_DEPT_ID");
        if (!StringUtils.hasText(hrDeptId)){
            throw new BaseException("对不起，您尚未有部门信息，不允许进行流程提交");
        }
    }

    /**
     * 暂存-生成流程实例标题
     */
    public void generateProInstanceName(){
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        checkStartDept(entityRecord.valueMap); // 发起人是否存在部门信息校验
        createProcessTitle(entityCode,entityRecord,myJdbcTemplate); // 流程标题创建
    }

    /**
     * 查询流程中某一个字段
     * @param tableName 流程表名
     * @param colName 需要查询的字段
     * @param csCommId 该条记录id
     * @param myJdbcTemplate 数据源
     * @return
     */
    private String getContractName(String tableName, String colName, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String value = "";
        String sql = "select " + colName + " from " + tableName + " where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
        if (!CollectionUtils.isEmpty(list)){
            value = JdbcMapUtil.getString(list.get(0),colName);
        }
        return value;
    }

    /**
     * 查询流程中某一个字段
     * @param tableName 流程表名
     * @param colName 需要查询的字段
     * @param csCommId 该条记录id
     * @param myJdbcTemplate 数据源
     * @return
     */
    public static String getContractNameStatic(String tableName, String colName, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String value = "";
        String sql = "select " + colName + " from " + tableName + " where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,csCommId);
        if (!CollectionUtils.isEmpty(list)){
            value = JdbcMapUtil.getString(list.get(0),colName);
        }
        return value;
    }

    // 获取发起流程时的项目名称
    private String getProjectName(MyJdbcTemplate myJdbcTemplate, EntityRecord entityRecord) {
        String projectName = "";
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
            }
        }
        if (SharedUtil.isEmptyString(projectId)){
            projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
        } else {
            projectId = projectId.replace(",","','");
            String sql = "select GROUP_CONCAT(name SEPARATOR '-') as name from pm_prj where id in ('"+projectId+"')";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
            if (!CollectionUtils.isEmpty(list)){
                projectName = JdbcMapUtil.getString(list.get(0),"name");
            } else {
                throw new BaseException("没有找到对应项目，请联系管理员处理");
            }
        }
        return projectName;
    }

    // 获取发起流程时的项目名称
    public static String getProjectNameStatic(MyJdbcTemplate myJdbcTemplate, EntityRecord entityRecord) {
        String projectName = "";
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(entityRecord.valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
            }
        }
        if (SharedUtil.isEmptyString(projectId)){
            projectName = JdbcMapUtil.getString(entityRecord.valueMap,"PROJECT_NAME_WR");
        } else {
            projectId = projectId.replace(",","','");
            String sql = "select GROUP_CONCAT(name SEPARATOR '-') as name from pm_prj where id in ('"+projectId+"')";
            List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
            if (!CollectionUtils.isEmpty(list)){
                projectName = JdbcMapUtil.getString(list.get(0),"name");
            } else {
                throw new BaseException("没有找到对应项目，请联系管理员处理");
            }
        }
        return projectName;
    }

    // 获取项目id
    private String getProjectId(Map<String, Object> valueMap) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_IDS");
                if (SharedUtil.isEmptyString(projectId)){
                    projectId = PmPrjReqExt.getPrjIdNew(valueMap);
                }
            }
        }
        return projectId;
    }

    // 根据长度自动拼接规则
    private String concatProcessName(String start,String...values) {
        StringJoiner sb = new StringJoiner(start);
        Arrays.stream(values).filter(p->!SharedUtil.isEmptyString(p)).forEach(p->sb.add(p));
        return sb.toString();
    }

    // 根据长度自动拼接规则
    public static String concatProcessNameStatic(String start,String...values) {
        StringJoiner sb = new StringJoiner(start);
        Arrays.stream(values).filter(p->!SharedUtil.isEmptyString(p)).forEach(p->sb.add(p));
        return sb.toString();
    }

    /**
     * 获取流程启动用户的部门的负责人。
     */
    public void getDeptChief() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            getDeptChief(entityRecord);
        }
    }

    //获取填写项目的id
    private String getWritePrjId(Map<String, Object> valueMap){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String name = JdbcMapUtil.getString(valueMap,"PROJECT_NAME_WR");
        String PROJECT_SOURCE_TYPE_ID = JdbcMapUtil.getString(valueMap,"PROJECT_SOURCE_TYPE_ID");
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select id from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID = ?",name,PROJECT_SOURCE_TYPE_ID);
        if (CollectionUtils.isEmpty(list)){
            return PmPrjReqExt.getPrjId(valueMap);
        } else {
            return JdbcMapUtil.getString(list.get(0),"id");
        }
    }

    private void getDeptChief(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String procInstId = ExtJarHelper.procInstId.get();

        Object START_USER_ID = Crud.from("wf_process_instance").where().eq("id", procInstId).select().specifyCols(
                "START_USER_ID").execForValue();

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select d.chief_user_id from hr_dept_user du join hr_dept d on du.HR_DEPT_ID=d.id and du.AD_USER_ID=? and du.status = 'ap' limit 1", START_USER_ID);
        if (CollectionUtils.isEmpty(list) || list.get(0).get("chief_user_id") == null) {
            throw new BaseException("启动用户没有对应的部门负责人！");
        } else if (list.size() > 1) {
            throw new BaseException("启动用户不能对应" + list.size() + "个部门负责人！");
        }

        String chief_user_id = list.get(0).get("chief_user_id").toString();

        ArrayList<Object> userIdList = new ArrayList<>(1);
        userIdList.add(chief_user_id);
        ExtJarHelper.returnValue.set(userIdList);

    }

    /**
     * 根据流程实例id获取文件id组
     * @param procInstId 流程实例id
     * @return 文件id
     */
    private String getProcessFileByProcInstId(String procInstId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> fileIdMap = myJdbcTemplate.queryForMap("select GROUP_CONCAT(t.USER_ATTACHMENT) " +
                "fileIds from wf_process_instance i left join wf_task t on t.WF_PROCESS_INSTANCE_ID = i.id where i.id" +
                " = ? and t.USER_ATTACHMENT is not null", procInstId);
        if (CollectionUtils.isEmpty(fileIdMap)) {
            return null;
        }
        return JdbcMapUtil.getString(fileIdMap, "fileIds");
    }

    /**
     * 根据流程实例id 返回根据节点分组的附件id字符串组 根据节点顺序排序后
     * @param procInstId 流程实例id
     * @return
     */
    private List<Map<String, Object>> getProcessFileGroupByNode(String procInstId){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> fileIdsList = myJdbcTemplate.queryForList("select n.name,n.id,GROUP_CONCAT(t.USER_ATTACHMENT) fileIds " +
                "from wf_node_instance ni " +
                "left join wf_task t on ni.id=t.wf_node_instance_id " +
                "left join wf_node n on n.id = ni.WF_NODE_ID " +
                "where ni.wf_process_instance_id=? " +
                "GROUP BY n.id " +
                "order by n.name ", procInstId);
        return fileIdsList;
    }


    //一些特殊流程，发起后即结束流程。
    public List<String> getEndProcessList() {
        List<String> list = new ArrayList<>();
        list.add("PM_FARMING_PROCEDURES"); //农转用手续办理
        list.add("PM_EARTHWORK_PRJ_REQ"); //土石方工程施工
        list.add("PM_BUILD_FOUNDATION_REQ"); //基坑支护工程施工
        list.add("PM_BUILD_PILE_REQ"); //桩基工程施工
        list.add("PM_BUILD_PLUS_MINUS_REQ"); //正负零施工
        list.add("PM_SUBJECT_CAPPING_REQ"); //主体结构封顶
        list.add("PM_SECOND_STRUCTURAL_REQ"); //二次结构工程
        list.add("PM_ROOFING_REQ"); //屋面工程
        list.add("PM_WAIL_REQ"); //幕墙工程
        list.add("PM_ELECTROMECHANICAL_REQ"); //机电
        list.add("PM_INTERIOR_DECORATION_REQ"); //室内精装工程
        list.add("PM_BUILD_INTELLIGENT_REQ"); //建筑智能化安装工程
        list.add("PM_ELEVATOR_INSTALL_REQ"); //电梯安装工程
        list.add("PM_FLOOD_LIGHTING_REQ"); //泛光照明工程
        list.add("PM_MUNICIPAL_MATCHED_REQ"); //配套市政工程
        list.add("PM_SIGN_PRJ_REQ"); //标识牌工程
        list.add("PM_LANDSCAPE_REQ"); //园林景观工程
        list.add("PM_ORGAN_SYS_DEBUG_REQ"); //组织系统联合调试
        list.add("PM_HOUSE_CADASTRE_REQ"); //房屋、地籍实测
        list.add("PM_SEWAGE_REQ"); //污水工程
        list.add("PM_RAIN_REQ"); //雨水工程
        list.add("PM_PIPE_GALLERY_REQ"); //管廊工程
        list.add("PM_ROAD_BASE_REQ"); //路面基层(碎石层与水稳层)
        list.add("PM_COOLING_REQ"); //供冷工程
        list.add("PM_ROAD_SURFACE_REQ"); //路面面层(沥青摊铺)
        list.add("PM_BRIDGE_FOUNDATION_REQ"); //桥梁基础工程
        list.add("PM_BRIDGE_SUBSTRUCTURE_REQ"); //桥梁下部结构
        list.add("PM_BRIDGE_SUPERSTRUCTURE_DECK_REQ"); //桥梁上部结构与桥面系工程
        list.add("PM_GREEN_REQ"); //绿化工程
        list.add("PM_POWER_REQ"); //电力工程
        list.add("PM_COMPLETED_CHECK_REQ"); //竣工初验
        list.add("PM_QUALITY_COMPLETED_CHECK_REQ"); //质监竣工验收
        list.add("PM_COMPLETED_RECORD_REQ"); //工程竣工备案
        list.add("PM_PLAN_CHECK_REQ"); //规划验收
        list.add("PM_FUNCTIONAL_OPENING_REQ"); //功能性通车
        list.add("PM_COMMUNICATION_ENGINEERING_REQ"); //通信工程
        list.add("PM_SIDEWALK_BUILD_REQ"); //人行道施工
        list.add("PM_TRAFFIC_REQ"); //交通工程
        list.add("PM_LIGHTING_REQ"); //照明工程
        list.add("PM_FEEDWATER_REQ"); //给水工程
        list.add("PM_RECLAIMED_WATER_REQ"); //中水工程
        list.add("PM_SUBGRADE_REQ"); //路基工程
        list.add("PM_CLEAR_FORM_REQ"); //清表完成
        list.add("PM_TRIPLET_FLAT_REQ"); //三通一平、临水、临电完成
        return list;
    }

    // 特殊流程，名称在流程发起时即生成，此处不生成
    public List<String> getSpecialList() {
        List<String> list = new ArrayList<>();
        list.add("PM_BUY_DEMAND_REQ"); //采购需求审批
        list.add("PM_BID_APPROVAL_REQ"); //招标文件审批
        list.add("PM_USE_CHAPTER_REQ"); //中选单位及标后用印申请
        list.add("PO_ORDER_REQ"); //合同签订
        list.add("PM_SUPERVISE_PLAN_REQ"); // 监理规划及细则申请
        list.add("QUALITY_RECORD"); // 质量交底记录
        list.add("PM_SUPERVISE_NOTICE_REQ"); // 监理通知单
        return list;
    }

    /**
     * 流程标题生成
     * @param entityCode 流程表名
     * @param entityRecord 表单数据
     * @param myJdbcTemplate 数据源
     */
    public static void createProcessTitle(String entityCode, EntityRecord entityRecord, MyJdbcTemplate myJdbcTemplate) {
        String processName = "", nowDate = "",userName = "",projectName;
        String csCommId = entityRecord.csCommId;
        Map<String,Object> valueMap = entityRecord.valueMap;

        String sql0 = "SELECT c.name as processName,b.IS_URGENT,b.START_DATETIME," +
                "(select name from ad_user where id = b.START_USER_ID) as userName FROM "+entityCode+" a " +
                "LEFT JOIN wf_process_instance b on a.LK_WF_INST_ID = b.id LEFT JOIN wf_process c on b.WF_PROCESS_ID = c.id WHERE a.id = ?";
        List<Map<String,Object>> list0 = myJdbcTemplate.queryForList(sql0,csCommId);
        if (!CollectionUtils.isEmpty(list0)){
            processName = JdbcMapUtil.getString(list0.get(0),"processName");
            nowDate = JdbcMapUtil.getString(list0.get(0),"START_DATETIME").replace("T"," ");
            userName = JdbcMapUtil.getString(list0.get(0),"userName");
        }

        //查询该实例紧急程度
        String urgent = getProcessUrgent(entityCode,csCommId,myJdbcTemplate);
        processName = urgent + processName;

        //无项目流程更新流程实例名称 判断是否该流程是否有项目信息
        List<String> noProjectList = AttLinkDifferentProcess.getNoProjectStaticList();
        if (noProjectList.contains(entityCode)){
            updateNoPrjProcessInstanceName(entityCode,processName,userName,nowDate,csCommId,valueMap,myJdbcTemplate);
        } else {
            projectName = getProjectNameStatic(myJdbcTemplate,entityRecord); // 获取项目名称

            // 流程名称按规定创建

            List<String> specialList = AttLinkDifferentProcess.getSpecialList(); // 特殊流程 更新流程内name字段
            updatePrjProcessInstanceName(specialList,entityCode,valueMap,processName,projectName,userName,nowDate,csCommId,myJdbcTemplate);
        }
    }

    /**
     * 更新流程实例名称
     * @param specialList 特殊流程特殊标题的流程集合
     * @param entityCode 业务表名
     * @param valueMap 表单数据详情
     * @param processName 流程名称
     * @param projectName 项目名称
     * @param userName 当前用户名称
     * @param nowDate 当前时间
     * @param csCommId 业务流程记录id
     * @param myJdbcTemplate 数据源
     */
    private static void updatePrjProcessInstanceName(List<String> specialList, String entityCode, Map<String, Object> valueMap, String processName, String projectName, String userName, String nowDate, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String otherName, name;
        if (specialList.contains(entityCode)) {
            if ("PM_BUY_DEMAND_REQ".equals(entityCode)){ //采购需求审批
                otherName = GrSetValueExt.getValueNameById(JdbcMapUtil.getString(valueMap,"BUY_MATTER_ID"));
                String subTitle = JdbcMapUtil.getString(valueMap,"REMARK_LONG_ONE"); // 副标题
                name = concatProcessNameStatic("-",processName,projectName,subTitle,otherName,userName,nowDate);
            } else if ("PO_ORDER_REQ".equals(entityCode)){ //合同签订
                otherName = getContractNameStatic(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
                name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            } else if ("PM_BID_APPROVAL_REQ".equals(entityCode)) { //招标文件审批
                otherName = getContractNameStatic(entityCode,"NAME_ONE",csCommId,myJdbcTemplate); // 招标名称
                String matterTypeId = JdbcMapUtil.getString(valueMap,"BUY_MATTER_ID");
                String matterTypeName = GrSetValueExt.getValueNameById(matterTypeId);
                name = concatProcessNameStatic("-",processName,otherName,matterTypeName,userName,nowDate);
            } else if ("PM_PROJECT_PROBLEM_REQ".equals(entityCode)){
                otherName = GrSetValueExt.getValueNameById(JdbcMapUtil.getString(valueMap,"PRJ_PUSH_PROBLEM_TYPE_ID"));
                name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            } else {
                if ("PM_SUPERVISE_PLAN_REQ".equals(entityCode)){
                    otherName = JdbcMapUtil.getString(valueMap,"REMARK_ONE");
                } else if ("QUALITY_RECORD".equals(entityCode)){
                    otherName = JdbcMapUtil.getString(valueMap,"REMARK_ONE");
                } else if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                    otherName = JdbcMapUtil.getString(valueMap,"CODE_ONE");
                } else {
                    otherName = getContractNameStatic(entityCode,"NAME_ONE",csCommId,myJdbcTemplate);
                }
                name = concatProcessNameStatic("-",processName,otherName,projectName,userName,nowDate);
            }
            if ("PM_SUPERVISE_NOTICE_REQ".equals(entityCode)){
                myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", otherName,csCommId);
            } else {
                myJdbcTemplate.update("update "+entityCode+" set name = ? where id = ?", name,csCommId);
            }
        }  else {
            List<String> orderNameTable = AttLinkDifferentProcess.getOrderProcessName(); //合同流程标题规则
            if (orderNameTable.contains(entityCode)){ //补充协议/合同需求审批/合同终止 流程标题规则
                otherName = getContractNameStatic(entityCode,"CONTRACT_NAME",csCommId,myJdbcTemplate);
                name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            } else if ("PM_PRJ_SETTLE_ACCOUNTS".equals(entityCode)){ //项目结算
                otherName = getTitleOtherNameByGrSetValueId(entityCode,"SETTLE_COST_TYPE_IDS",csCommId,myJdbcTemplate);
                name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            } else if ("PO_GUARANTEE_LETTER_RETURN_OA_REQ".equals(entityCode) || "PO_GUARANTEE_LETTER_REQUIRE_REQ".equals(entityCode)){ // 新增保函/保函退还
                otherName = JdbcMapUtil.getString(valueMap,"GUARANTEE_CODE");
                name = concatProcessNameStatic("-",processName,projectName,otherName,userName,nowDate);
            } else {
                name = concatProcessNameStatic("-",processName,projectName,userName,nowDate);
            }
        }
        myJdbcTemplate.update("update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?",name,csCommId);
    }

    /**
     * 无项目流程更新流程实例名称
     * @param entityCode 业务表名
     * @param processName 流程名称
     * @param userName 当前操作用户名称
     * @param nowDate 当前时间
     * @param csCommId 业务流程记录id
     * @param valueMap 表单数据
     * @param myJdbcTemplate 数据源
     */
    private static void updateNoPrjProcessInstanceName(String entityCode, String processName, String userName, String nowDate, String csCommId, Map<String,Object> valueMap, MyJdbcTemplate myJdbcTemplate) {
        String sql = "update wf_process_instance pi join " + entityCode + " t on pi.ENTITY_RECORD_ID = t.id set pi.name = ? where t.id = ?";
        String title = JdbcMapUtil.getString(valueMap,"APPROVAL_COMMENT_THREE"); // 意见咨询主题信息写入标题
        String name = concatProcessNameStatic("-",processName,title,userName,nowDate);
        myJdbcTemplate.update(sql, name,csCommId);
    }

    /**
     * 获取流程紧急情况
     * @param entityCode 业务表名
     * @param csCommId 业务流程记录id
     * @param myJdbcTemplate 数据源
     * @return 紧急情况
     */
    private static String getProcessUrgent(String entityCode, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        String urgent = "";
        String urgentSql = "SELECT a.IS_URGENT FROM WF_PROCESS_INSTANCE a left join "+entityCode+" b on a.id = b.LK_WF_INST_ID where b.id = ? ";
        List<Map<String,Object>> urgentList = myJdbcTemplate.queryForList(urgentSql,csCommId);
        if (!CollectionUtils.isEmpty(urgentList)){
            String urgentType = JdbcMapUtil.getString(urgentList.get(0),"IS_URGENT");
            if ("1".equals(urgentType)){
                urgent = "【紧急】";
            }
        }
        return urgent;
    }

    /**
     * 根据id查询集合名称
     * @param entityCode 流程表名
     * @param colName 查询的字段
     * @param csCommId 流程业务唯一id
     * @param myJdbcTemplate 数据源
     * @return 集合名称
     */
    public static String getTitleOtherNameByGrSetValueId(String entityCode, String colName, String csCommId, MyJdbcTemplate myJdbcTemplate) {
        StringBuilder sb = new StringBuilder();
        String id = getContractNameStatic(entityCode,colName,csCommId,myJdbcTemplate);
        List<String> idList = StringUtil.getStrToList(id,",");
        for (String tmp : idList) {
            String name = GrSetValue.selectByWhere(new Where().eq(GrSetValue.Cols.ID,tmp)).get(0).getName();
            sb.append(name).append("、");
        }
        if (sb.length() > 0){
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 流程结束将需要的文件写入项目清单明细
     */
    private void addInventoryDtl(){
        Map<String, Object> valueMap = ExtJarHelper.entityRecordList.get().get(0).valueMap;

        String prjIds;
        if (valueMap.get("PM_PRJ_IDS") != null){
            prjIds = JdbcMapUtil.getString(valueMap,"PM_PRJ_IDS");
        }else {
            prjIds = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
        }
        if (StringUtils.hasText(prjIds)){
            PrjMaterialInventory.addInventoryDtl(prjIds,ExtJarHelper.procId.get(),ExtJarHelper.procInstId.get());
        }
    }

}
