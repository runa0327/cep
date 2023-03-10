package com.cisdi.ext.proPlan;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmProPlanExt
 * @package com.cisdi.ext.proPlan
 * @description 项目进展
 * @date 2023/3/1
 */
public class PmProPlanExt {

    /**
     * 准予启动。
     */
    public void allowStartNode() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        // MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // String projectId = String.valueOf(map.get("projectId"));
        String nodeId = String.valueOf(map.get("nodeId"));

        allowStartNodeRecursively(new ArrayList<>(), nodeId);
    }

    private void allowStartNodeRecursively(List<String> processedIdList, String nodeId) {
        if (processedIdList.contains(nodeId)) {
            throw new BaseException("允许启动节点时，出现死循环！路径上某个节点的ID：" + nodeId);
        }

        processedIdList.add(nodeId);

        Map<String, Object> map = Crud.from("PM_PRO_PLAN_NODE")
                .where().eq("ID", nodeId)
                .select().execForMap();
        if (map != null && !Boolean.TRUE.equals(JdbcMapUtil.getBoolean(map, "CAN_START"))) {
            Crud.from("PM_PRO_PLAN_NODE")
                    .where().eq("ID", nodeId)
                    .update().set("CAN_START", true).exec();

            String pid = JdbcMapUtil.getString(map, "PM_PRO_PLAN_NODE_PID");
            if (!SharedUtil.isEmptyString(pid)) {
                allowStartNodeRecursively(processedIdList, pid);
            }
        }
    }

    public void proPlanView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String projectId = String.valueOf(map.get("projectId"));
        String nodeId = String.valueOf(map.get("nodeId"));

        viewObj viewObj = new viewObj();
        List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select hd.`NAME` as post,PLAN_START_DATE,PLAN_COMPL_DATE,PLAN_TOTAL_DAYS, " +
                "gsv.`NAME` as `status`,ACTUAL_START_DATE,ACTUAL_COMPL_DATE,PLAN_CARRY_DAYS,ifnull(pppn.CAN_START,0) as  CAN_START " +
                "from PM_PRO_PLAN_NODE  pppn " +
                "left join hr_dept hd on hd.id = pppn.CHIEF_DEPT_ID " +
                "left join gr_set_value gsv on gsv.id = pppn.PROGRESS_STATUS_ID " +
                "where pppn.id=?", nodeId);
        if (!CollectionUtils.isEmpty(nodeList)) {
            Map<String, Object> node = nodeList.get(0);
            viewObj.post = JdbcMapUtil.getString(node, "post");
            viewObj.planStartTime = JdbcMapUtil.getString(node, "PLAN_START_DATE");
            viewObj.planCompleteTime = JdbcMapUtil.getString(node, "PLAN_COMPL_DATE");
            viewObj.predictDays = JdbcMapUtil.getString(node, "PLAN_TOTAL_DAYS");
            viewObj.status = JdbcMapUtil.getString(node, "status");
            viewObj.actualStartTime = JdbcMapUtil.getString(node, "ACTUAL_START_DATE");
            viewObj.actualCompleteTime = JdbcMapUtil.getString(node, "ACTUAL_COMPL_DATE");
            viewObj.surplusDays = JdbcMapUtil.getString(node, "PLAN_CARRY_DAYS");
            viewObj.canStart = JdbcMapUtil.getInt(node, "CAN_START");

            // 查询存在问题 problemList
            List<Map<String, Object>> proList = myJdbcTemplate.queryForList("select np.*,au.`NAME` as userName from NODE_PROBLEM np left join ad_user au on np.AD_USER_ID = au.id  where PM_PRO_PLAN_NODE_ID=?", nodeId);
            List<Problem> problemList = proList.stream().map(p -> {
                Problem problem = new Problem();
                problem.id = JdbcMapUtil.getString(p, "ID");
                problem.des = JdbcMapUtil.getString(p, "PROBLEM_DESCRIBE");
                problem.way = JdbcMapUtil.getString(p, "SOLUTION");
                problem.izDo = JdbcMapUtil.getString(p, "IS_SOLVE");
                problem.userName = JdbcMapUtil.getString(p, "userName");
                problem.ctime = JdbcMapUtil.getString(p, "PROPOSAL_DATE");
                return problem;
            }).collect(Collectors.toList());
            viewObj.problemList = problemList;

            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppl.AD_ENT_ID_IMP,pppl.AD_ATT_ID_IMP,ae.`NAME` as ant_name,aa.`NAME` as att_name, ae.`CODE` as ant_code ,aa.`CODE` as att_code " +
                    "from pm_pro_plan_node pppn " +
                    "left join base_node bn on bn.`NAME` = pppn.`NAME` " +
                    "left join PM_PRO_PLAN_LEDGER pppl on pppn.`NAME` = pppl.base_node_id = bn.id " +
                    "left join ad_ent ae on ae.id = pppl.AD_ENT_ID_IMP  " +
                    "left join ad_att aa on aa.id = pppl.AD_ATT_ID_IMP " +
                    "where pppn.id=?", nodeId);
            if (!CollectionUtils.isEmpty(list)) {
                if (Objects.nonNull(list.get(0).get("ant_code")) && Objects.nonNull(list.get(0).get("att_code"))) {
                    String tableName = String.valueOf(list.get(0).get("ant_code"));
                    String column = String.valueOf(list.get(0).get("att_code"));
                    List<FileListObj> fileListObjList = new ArrayList<>();
                    for (Map<String, Object> stringObjectMap : list) {
                        FileListObj fileListObj = new FileListObj();
                        fileListObj.title = JdbcMapUtil.getString(stringObjectMap, "att_name");
                        StringBuilder sb = new StringBuilder();
                        sb.append("select * from ").append(tableName).append(" where PM_PRJ_ID ='").append(projectId).append("'");
                        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList(sb.toString());
                        if (!CollectionUtils.isEmpty(dataList)) {
                            String fileIds = JdbcMapUtil.getString(dataList.get(0), column);
                            if (!"null".equals(fileIds)) {
                                List<String> ids = Arrays.asList(fileIds.split(","));
                                MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
                                Map<String, Object> queryParams = new HashMap<>();// 创建入参map
                                queryParams.put("ids", ids);
                                List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
                                AtomicInteger index = new AtomicInteger(0);
                                List<FileObj> fileObjList = fileList.stream().map(p -> {
                                    FileObj obj = new FileObj();
                                    obj.num = index.getAndIncrement() + 1;
                                    obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
                                    obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
                                    obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
                                    obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                                    obj.id = JdbcMapUtil.getString(p, "ID");
                                    obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                                    obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                                    return obj;
                                }).collect(Collectors.toList());
                                fileListObj.fileObjList = fileObjList;
                                fileListObjList.add(fileListObj);
                            }
                        }
                    }
                }
            }

            // 获取流程、流程实例的相关信息：
            List<Map<String, Object>> procInfoList = myJdbcTemplate.queryForList("select p.id P_ID,p.name P_NAME,p.EXTRA_INFO P_EXTRA_INFO,pi.id PI_ID,pi.name PI_NAME,pi.ENT_CODE PI_ENT_CODE,pi.ENTITY_RECORD_ID PI_ENTITY_RECORD_ID,PI.CURRENT_VIEW_ID PI_CURRENT_VIEW_ID from PM_PRO_PLAN_NODE pppn join wf_process p on pppn.LINKED_WF_PROCESS_ID=p.id and pppn.id=? left join wf_process_instance pi on pppn.LINKED_WF_PROCESS_INSTANCE_ID=pi.id", nodeId);
            if (!CollectionUtils.isEmpty(procInfoList)) {
                Map<String, Object> procInfo = procInfoList.get(0);

                viewObj.procId = JdbcMapUtil.getString(procInfo, "P_ID");
                viewObj.procName = JdbcMapUtil.getString(procInfo, "P_NAME");
                viewObj.procIcon = JdbcMapUtil.getString(procInfo, "P_EXTRA_INFO");

                viewObj.procInstId = JdbcMapUtil.getString(procInfo, "PI_ID");
                viewObj.procInstName = JdbcMapUtil.getString(procInfo, "PI_NAME");
                viewObj.procInstEntityRecordId = JdbcMapUtil.getString(procInfo, "PI_ENTITY_RECORD_ID");

                if (!SharedUtil.isEmptyString(viewObj.procInstId)) {
                    viewObj.procViewId = JdbcMapUtil.getString(procInfo, "PI_CURRENT_VIEW_ID");
                } else {
                    List<Map<String, Object>> wfNodeList = myJdbcTemplate.queryForList("select n.AD_VIEW_ID from wf_node n where n.status='ap' and n.WF_PROCESS_ID='0100031468511691070' and n.NODE_TYPE='START_EVENT'");
                    if (!CollectionUtils.isEmpty(wfNodeList)) {
                        viewObj.procViewId = JdbcMapUtil.getString(wfNodeList.get(0), "AD_VIEW_ID");
                    }
                }

            }


            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(viewObj), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }

    }

    public static class FileObj {
        // 序号
        public Integer num;
        // 文件名称
        public String fileName;
        // 文件大小
        public String fileSize;
        // 上传人
        public String uploadUser;
        // 上传时间
        public String uploadDate;

        public String id;

        public String viewUrl;

        public String downloadUrl;

    }

    public static class FileListObj {
        public String title;
        public List<FileObj> fileObjList;
    }

    public static class viewObj {
        // 岗位
        public String post;
        // 计划开始实际
        public String planStartTime;
        // 计划完成时间
        public String planCompleteTime;
        // 预计用时
        public String predictDays;
        // 状态
        public String status;
        // 实际开始时间
        public String actualStartTime;
        // 实际结束时间
        public String actualCompleteTime;
        // 剩余用时
        public String surplusDays;

        public List<FileListObj> fileListObjList;

        public List<Problem> problemList;

        // 流程ID
        public String procId;
        // 流程名称
        public String procName;
        // 流程图标
        public String procIcon;

        // 流程实例ID
        public String procInstId;
        // 流程实例名称
        public String procInstName;
        // 流程实例的关联的实体记录ID
        public String procInstEntityRecordId;

        // 流程视图ID。若未发起流程，则为流程的启动节点。
        public String procViewId;

        // 能否启动
        public Integer canStart;

    }

    public static class Problem {
        // ID
        public String id;
        // 问题描述
        public String des;
        // 解决方式
        public String way;
        // 是否解决
        public String izDo;
        // 提出人
        public String userName;
        // 提出时间
        public String ctime;
    }

    /**
     * 立项更新项目节点状态
     *
     * @param pmPrjId        项目id
     * @param str            来源类型
     * @param myJdbcTemplate 数据源
     */
    public static String updatePrjProPlan(String pmPrjId, String str, MyJdbcTemplate myJdbcTemplate) {
        // 判断该项目的进度计划是否存在
        String pmProPlanId = getPmProPlanId(pmPrjId, myJdbcTemplate);
        if (SharedUtil.isEmptyString(pmProPlanId)) { // 新增
            pmProPlanId = createPlan(pmPrjId, myJdbcTemplate);
        }
        return pmProPlanId;
    }

    /**
     * 根据项目id创建项目进度计划
     *
     * @param projectId      项目id
     * @param myJdbcTemplate 数据源
     */
    public static String createPlan(String projectId, MyJdbcTemplate myJdbcTemplate) {
        // 根据项目类型查询项目进度计划模板
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ppp.*,PRJ_REPLY_DATE from PM_PRO_PLAN ppp " +
                "left join pm_prj pp on ppp.TEMPLATE_FOR_PROJECT_TYPE_ID = pp.PROJECT_TYPE_ID " +
                "where ppp.`STATUS`='AP' and ppp.IS_TEMPLATE='1' and pp.id=?", projectId);
        if (CollectionUtils.isEmpty(list)) {
            list = myJdbcTemplate.queryForList("SELECT ppp.*,null as PRJ_REPLY_DATE FROM PM_PRO_PLAN ppp " +
                    "WHERE ppp.`STATUS` = 'AP' AND ppp.IS_TEMPLATE = '1' " +
                    "and ppp.TEMPLATE_FOR_PROJECT_TYPE_ID = '0099799190825080750'");
        }
        Map<String, Object> proMap = list.get(0);
        // 先创建项目的进度计划
        String newPlanId = Crud.from("PM_PRO_PLAN").insertData();

        Crud.from("PM_PRO_PLAN").where().eq("ID", newPlanId).update().set("IS_TEMPLATE", 0).set("PM_PRJ_ID", projectId).set("PLAN_TOTAL_DAYS", proMap.get("PLAN_TOTAL_DAYS"))
                .set("PROGRESS_STATUS_ID", proMap.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", proMap.get("PROGRESS_RISK_TYPE_ID")).set("START_DAY", proMap.get("START_DAY")).exec();

        // 查询项目进度计划节点模板
        List<Map<String, Object>> planNodeList = myJdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT," +
                "LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,ACTUAL_START_DATE,PROGRESS_RISK_REMARK,PM_PRO_PLAN_ID,PLAN_START_DATE," +
                "PLAN_TOTAL_DAYS,PLAN_CARRY_DAYS,ACTUAL_CARRY_DAYS,ACTUAL_TOTAL_DAYS,PLAN_CURRENT_PRO_PERCENT,ACTUAL_CURRENT_PRO_PERCENT," +
                "ifnull(PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,SHOW_IN_EARLY_PROC,SHOW_IN_PRJ_OVERVIEW," +
                "PROGRESS_STATUS_ID,PROGRESS_RISK_TYPE_ID,CHIEF_DEPT_ID,CHIEF_USER_ID,START_DAY,SEQ_NO,CPMS_UUID,CPMS_ID,`LEVEL`,LINKED_WF_PROCESS_ID,LINKED_WF_NODE_ID,POST_INFO_ID " +
                "from PM_PRO_PLAN_NODE where PM_PRO_PLAN_ID=?", proMap.get("ID"));
        if (planNodeList.size() > 0) {
            planNodeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
                String id = Crud.from("PM_PRO_PLAN_NODE").insertData();

                Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                        .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                        .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("CHIEF_USER_ID", m.get("CHIEF_USER_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                        .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_WF_NODE_ID", m.get("LINKED_WF_NODE_ID"))
                        .set("SHOW_IN_EARLY_PROC", m.get("SHOW_IN_EARLY_PROC")).set("POST_INFO_ID", m.get("POST_INFO_ID"))
                        .set("SHOW_IN_PRJ_OVERVIEW", m.get("SHOW_IN_PRJ_OVERVIEW")).exec();

                getChildrenNode(m, planNodeList, id, newPlanId);
            }).collect(Collectors.toList());
        }
        return newPlanId;
    }

    public static List<Map<String, Object>> getChildrenNode(Map<String, Object> root, List<Map<String, Object>> allData, String pId, String newPlanId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")), String.valueOf(root.get("ID")))).peek(m -> {
            String id = Crud.from("PM_PRO_PLAN_NODE").insertData();
            Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                    .set("PM_PRO_PLAN_NODE_PID", pId)
                    .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                    .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("CHIEF_USER_ID", m.get("CHIEF_USER_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                    .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_WF_NODE_ID", m.get("LINKED_WF_NODE_ID")).set("SHOW_IN_EARLY_PROC", m.get("SHOW_IN_EARLY_PROC")).set("SHOW_IN_PRJ_OVERVIEW", m.get("SHOW_IN_PRJ_OVERVIEW")).exec();
            getChildrenNode(m, allData, id, newPlanId);
        }).collect(Collectors.toList());
    }


    /**
     * 查询项目进度计划主表id
     *
     * @param pmPrjId        项目id
     * @param myJdbcTemplate 数据源
     * @return
     */
    public static String getPmProPlanId(String pmPrjId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select id from PM_PRO_PLAN where PM_PRJ_ID = ? and status = 'ap'";
        String id = "";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, pmPrjId);
        if (!CollectionUtils.isEmpty(list)) {
            id = JdbcMapUtil.getString(list.get(0), "id");
        }
        return id;
    }

    /**
     * 立项导入-更新项目禁止-项目建议书编制、立项批复
     *
     * @param pmPrjId        项目id
     * @param pmProPlanId    项目进度节点id
     * @param proPlanList    数据集
     * @param myJdbcTemplate 数据源
     */
    public static void updatePrjPlanDetailPrjReq(String pmPrjId, String pmProPlanId, List<Map<String, Object>> proPlanList, MyJdbcTemplate myJdbcTemplate) {
        if (!proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "项目建议书编制").update()
                    .set("PROGRESS_STATUS_ID", proPlanList.get(0).get("planStatus")) // 进度状态
                    .set("ACTUAL_COMPL_DATE", proPlanList.get(0).get("ACTUAL_COMPL_DATE")) // 实际完成日期
                    .exec();
        }
        if (!proPlanList.get(1).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "立项批复").update()
                    .set("PROGRESS_STATUS_ID", proPlanList.get(1).get("planStatus")) // 进度状态
                    .set("ACTUAL_COMPL_DATE", proPlanList.get(1).get("REPLY_ACTUAL_COMPL_DATE")) // 实际完成日期
                    .exec();
        }
        if (!proPlanList.get(1).isEmpty() && !proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "立项").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106802") // 进度状态
                    .exec();
        } else if (!proPlanList.get(1).isEmpty() || !proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "立项").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106801") // 进度状态
                    .exec();
        } else if (proPlanList.get(1).isEmpty() && proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "立项").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106800") // 进度状态
                    .exec();
        }
    }

    /**
     * 可研导入-更新项目禁止-可研报告编制、可研批复
     *
     * @param pmPrjId        项目id
     * @param pmProPlanId    项目进度节点id
     * @param proPlanList    数据集
     * @param myJdbcTemplate 数据源
     */
    public static void updatePrjPlanDetailInvest1(String pmPrjId, String pmProPlanId, List<Map<String, Object>> proPlanList, MyJdbcTemplate myJdbcTemplate) {
        if (!proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "可研报告编制").update()
                    .set("PROGRESS_STATUS_ID", proPlanList.get(0).get("planStatus")) // 进度状态
                    .set("ACTUAL_COMPL_DATE", proPlanList.get(0).get("ACTUAL_COMPL_DATE")) // 实际完成日期
                    .exec();
        }
        if (!proPlanList.get(1).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "可研批复").update()
                    .set("PROGRESS_STATUS_ID", proPlanList.get(1).get("planStatus")) // 进度状态
                    .set("ACTUAL_COMPL_DATE", proPlanList.get(1).get("REPLY_ACTUAL_COMPL_DATE")) // 实际完成日期
                    .exec();
        }
        if (!proPlanList.get(1).isEmpty() && !proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "可行性研究").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106802") // 进度状态
                    .exec();
        } else if (!proPlanList.get(1).isEmpty() || !proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "可行性研究").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106801") // 进度状态
                    .exec();
        } else if (proPlanList.get(1).isEmpty() && proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "可行性研究").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106800") // 进度状态
                    .exec();
        }
    }

    /**
     * 概算导入-更新项目禁止-可研报告编制、可研批复
     *
     * @param pmPrjId        项目id
     * @param pmProPlanId    项目进度节点id
     * @param proPlanList    数据集
     * @param myJdbcTemplate 数据源
     */
    public static void updatePrjPlanDetailInvest2(String pmPrjId, String pmProPlanId, List<Map<String, Object>> proPlanList, MyJdbcTemplate myJdbcTemplate) {
        if (!proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "初步设计及概算编制").update()
                    .set("PROGRESS_STATUS_ID", proPlanList.get(0).get("planStatus")) // 进度状态
                    .set("ACTUAL_COMPL_DATE", proPlanList.get(0).get("ACTUAL_COMPL_DATE")) // 实际完成日期
                    .exec();
        }
        if (!proPlanList.get(1).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "初步设计概算批复").update()
                    .set("PROGRESS_STATUS_ID", proPlanList.get(1).get("planStatus")) // 进度状态
                    .set("ACTUAL_COMPL_DATE", proPlanList.get(1).get("REPLY_ACTUAL_COMPL_DATE")) // 实际完成日期
                    .exec();
        }
        if (!proPlanList.get(1).isEmpty() && !proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "初步设计及概算").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106802") // 进度状态
                    .exec();
        } else if (!proPlanList.get(1).isEmpty() || !proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "初步设计及概算").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106801") // 进度状态
                    .exec();
        } else if (proPlanList.get(1).isEmpty() && proPlanList.get(0).isEmpty()) {
            Crud.from("PM_PRO_PLAN_NODE").where().eq("PM_PRO_PLAN_ID", pmProPlanId).eq("NAME", "初步设计及概算").update()
                    .set("PROGRESS_STATUS_ID", "0099799190825106800") // 进度状态
                    .exec();
        }
    }

}
