package com.cisdi.ext.base;

import com.cisdi.ext.link.LinkSql;
import com.cisdi.ext.model.PmPlan;
import com.cisdi.ext.model.PmPrjSettleAccounts;
import com.cisdi.ext.model.base.PmPrj;
import com.cisdi.ext.model.PrjStart;
import com.cisdi.ext.model.view.project.PmPrjView;
import com.cisdi.ext.pm.PmPlanExt;
import com.cisdi.ext.pm.development.PmPrjReqExt;
import com.cisdi.ext.pm.PmRosterExt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.PmPrjCodeUtil;
import com.cisdi.ext.util.StringUtil;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目表相关扩展
 */
public class PmPrjExt {

    /**
     * 根据项目名称查询项目id
     * @param projectName 项目名称
     * @param sourceType 项目类型 0099952822476441374(系统) 0099952822476441375(非系统)
     * @param myJdbcTemplate 数据源
     * @return 项目id
     */
    public static String getProjectId(String projectName,String sourceType, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select id from pm_prj where PROJECT_SOURCE_TYPE_ID = ? and NAME = ? and status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,sourceType,projectName);
        if (!CollectionUtils.isEmpty(list)){
            return list.get(0).get("id").toString();
        } else {
            throw new BaseException("在项目表中未查询到该项目，请联系管理员处理！");
        }
    }

    /**
     * 根据项目id返回项目名称
     * @param projectIds 项目id
     * @param myJdbcTemplate 数据源
     * @return 项目名称
     */
    public static String queryProjectNameByIds(String projectIds, MyJdbcTemplate myJdbcTemplate) {
        projectIds = projectIds.replace(",","','");
        String sql = "select group_concat(name) as name from pm_prj where id in ('"+projectIds+"')";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            return JdbcMapUtil.getString(list.get(0),"name");
        } else {
            return null;
        }
    }

    /**
     * 通过流程信息获取项目id
     * @param valueMap 流程主体信息
     * @return 项目id
     */
    public static String getProjectIdByProcess(Map<String, Object> valueMap) {
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_ID");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = getRealProjectId(valueMap);
        }
        return projectId;
    }

    /**
     * 项目/非项目/项目多选中筛选项目id
     * @param valueMap 流程主体内容
     * @return 项目id
     */
    private static String getRealProjectId(Map<String, Object> valueMap) {
        String projectId = JdbcMapUtil.getString(valueMap,"PM_PRJ_IDS");
        if (SharedUtil.isEmptyString(projectId)){
            projectId = JdbcMapUtil.getString(valueMap,"AMOUT_PM_PRJ_ID");
            if (SharedUtil.isEmptyString(projectId)){
                projectId = PmPrjReqExt.getPrjIdNew(valueMap);
            }
        }
        return projectId;
    }

    /**
     * 立项-可研-初概流程更新项目信息(基础信息、资金信息)
     * @param entityRecord 实体信息
     * @param entityCode 信息来源名称
     * @param level 本次数据来源级别
     * @param code 数据来源表名
     * @param myJdbcTemplate 数据源
     */
    public static void updatePrjBaseData(EntityRecord entityRecord, String entityCode, int level, MyJdbcTemplate myJdbcTemplate,String code) {
        //项目id
        String projectId = getProjectIdByProcess(entityRecord.valueMap);
        if (projectId.contains(",")){
            throw new BaseException("数据更新不支持多项目同时修改，请重新进行数据处理或联系管理员处理！");
        }
        // 查询当前项目信息数据级别
        int oldLevel = getPrjDataLevel(projectId,myJdbcTemplate);
        if (level >= oldLevel){ //更新数据
            //当前更新级别id
            String levelId = GrSetValueExt.getValueId("invest_priority",String.valueOf(level),myJdbcTemplate);
            //更新项目基础信息
            updateBaseData(projectId,entityRecord.valueMap,levelId,code);
            //更新项目资金信息
            WfPmInvestUtil.updatePrjInvest(entityRecord,code);
        }
    }

    /**
     * 更新资金信息
     * @param entityRecord 表单数据
     * @param pm_prj_settle_accounts 表
     * @param level 级别
     * @param myJdbcTemplate 数据源
     * @param entCode 业务表名
     */
    public static void updatePrjAmt(EntityRecord entityRecord, String pm_prj_settle_accounts, int level, MyJdbcTemplate myJdbcTemplate, String entCode) {
        //项目id
        String projectId = getProjectIdByProcess(entityRecord.valueMap);
        if (projectId.contains(",")){
            throw new BaseException("数据更新不支持多项目同时修改，请重新进行数据处理或联系管理员处理！");
        }
        // 查询当前项目信息数据级别
        int oldLevel = getPrjDataLevel(projectId,myJdbcTemplate);
        if (level >= oldLevel){ //更新数据
            //更新项目资金信息
            List<PmPrjSettleAccounts> list = PmPrjSettleAccounts.selectByWhere(new Where().eq(PmPrjSettleAccounts.Cols.STATUS,"AP")
                    .eq(PmPrjSettleAccounts.Cols.PM_PRJ_ID,projectId));
            if (!CollectionUtils.isEmpty(list)){
                BigDecimal prjTotalInvest = list.stream().filter(p->p.getPrjTotalInvest() != null).map(PmPrjSettleAccounts::getPrjTotalInvest).reduce(BigDecimal.ZERO,BigDecimal::add); //总投资
                BigDecimal constructAmt = list.stream().filter(p->p.getConstructAmt() != null).map(PmPrjSettleAccounts::getConstructAmt).reduce(BigDecimal.ZERO,BigDecimal::add); //建安工程费
                BigDecimal equipAmt = list.stream().filter(p->p.getEquipAmt() != null).map(PmPrjSettleAccounts::getEquipAmt).reduce(BigDecimal.ZERO,BigDecimal::add); //设备采购费
                BigDecimal equipmentCost = list.stream().filter(p->p.getEquipmentCost() != null).map(PmPrjSettleAccounts::getEquipmentCost).reduce(BigDecimal.ZERO,BigDecimal::add); //科研设备费
                BigDecimal projectOtherAmt = list.stream().filter(p->p.getProjectOtherAmt() != null).map(PmPrjSettleAccounts::getProjectOtherAmt).reduce(BigDecimal.ZERO,BigDecimal::add); //工厂其他费用
                BigDecimal landAmt = list.stream().filter(p->p.getLandAmt() != null).map(PmPrjSettleAccounts::getLandAmt).reduce(BigDecimal.ZERO,BigDecimal::add); //工厂其他费用
                BigDecimal prepareAmt = list.stream().filter(p->p.getPrepareAmt() != null).map(PmPrjSettleAccounts::getPrepareAmt).reduce(BigDecimal.ZERO,BigDecimal::add); //预备费

                entityRecord.valueMap.put("PRJ_TOTAL_INVEST",prjTotalInvest); // 总投资
                entityRecord.valueMap.put("CONSTRUCT_AMT",constructAmt); // 建安工程费
                entityRecord.valueMap.put("EQUIP_AMT",equipAmt); // 设备采购费
                entityRecord.valueMap.put("EQUIPMENT_COST",equipmentCost); // 科研设备费
                entityRecord.valueMap.put("PROJECT_OTHER_AMT",projectOtherAmt); // 工程其他费用
                entityRecord.valueMap.put("LAND_AMT",landAmt); // 土地征拆费用
                entityRecord.valueMap.put("PREPARE_AMT",prepareAmt); // 预备费
            }
            WfPmInvestUtil.updatePrjInvest(entityRecord,entCode);
        }
    }


    /**
     * 更新项目基础信息
     * @param projectId 项目id
     * @param valueMap map值
     * @param level 当前级别
     * @param code 流程表名
     */
    private static void updateBaseData(String projectId, Map<String, Object> valueMap, String level, String code) {
        Crud.from("pm_prj").where().eq("id",projectId).update()
                .set("FLOOR_AREA",JdbcMapUtil.getString(valueMap,"FLOOR_AREA")) //占地面积
                .set("PROJECT_TYPE_ID",JdbcMapUtil.getString(valueMap,"PROJECT_TYPE_ID")) //项目类型
                .set("CON_SCALE_TYPE_ID",JdbcMapUtil.getString(valueMap,"CON_SCALE_TYPE_ID")) //建设规模类型
                .set("CON_SCALE_QTY",JdbcMapUtil.getString(valueMap,"CON_SCALE_QTY")) // 道路长度
                .set("CON_SCALE_QTY2",JdbcMapUtil.getString(valueMap,"CON_SCALE_QTY2")) // 道路宽度
                .set("QTY_ONE",JdbcMapUtil.getString(valueMap,"QTY_ONE")) // 建筑面积
                .set("QTY_THREE",JdbcMapUtil.getString(valueMap,"QTY_THREE")) // 建筑面积
                .set("OTHER",JdbcMapUtil.getString(valueMap,"QTY_THREE")) // 其他
                .set("CON_SCALE_UOM_ID",JdbcMapUtil.getString(valueMap,"CON_SCALE_UOM_ID")) // 建设规模单位
                .set("PRJ_SITUATION",JdbcMapUtil.getString(valueMap,"PRJ_SITUATION")) // 项目概况
                .set("INVEST_PRIORITY",level) // 来源级别
                .set("REPLY_NO",JdbcMapUtil.getString(valueMap,"REPLY_NO_WR")) // 来源级别
                .exec();
        if (!"PM_PRJ_SETTLE_ACCOUNTS".equals(code)){
            Crud.from("pm_prj").where().eq("id",projectId).update()
                    .set("REPLY_NO",JdbcMapUtil.getString(valueMap,"REPLY_NO_WR")) // 批复文号
                    .set("PRJ_CODE",JdbcMapUtil.getString(valueMap,"PRJ_CODE")) // 项目代码
                    .set("BUILD_YEARS",JdbcMapUtil.getString(valueMap,"BUILD_YEARS")) // 建设年限
                    .exec();
        }
    }

    /**
     * 获取项目表(pm_prj)中基础信息和资金信息来源级别
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return INVEST_PRIORITY 数据级别
     */
    private static int getPrjDataLevel(String projectId, MyJdbcTemplate myJdbcTemplate) {
        int level = 0 ;
        String sql = "select b.code from pm_prj a left join gr_set_value b on a.INVEST_PRIORITY = b.id where a.id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
        if (!CollectionUtils.isEmpty(list)){
            String code = JdbcMapUtil.getString(list.get(0),"code");
            if (!SharedUtil.isEmptyString(code)){
                level = Integer.valueOf(code);
            }
        }
        return level;
    }

    /**
     * 获取项目中成本、设计、前期岗人员信息
     * @param projectId 项目id
     * @param myJdbcTemplate 数据源
     * @return 人员对应的map组
     */
    public static Map<String, String> getProjectDeptUser(String projectId, MyJdbcTemplate myJdbcTemplate) {
        String sql = "select PRJ_EARLY_USER_ID,PRJ_DESIGN_USER_ID,PRJ_COST_USER_ID from pm_prj where id = ?";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,projectId);
        Map<String,String> map = new HashMap<>();
        if (!CollectionUtils.isEmpty(list)){
            String early = JdbcMapUtil.getString(list.get(0),"PRJ_EARLY_USER_ID"); //前期部
            String design = JdbcMapUtil.getString(list.get(0),"PRJ_DESIGN_USER_ID"); //设计部
            String cost = JdbcMapUtil.getString(list.get(0),"PRJ_COST_USER_ID"); //成本部
            map.put("PRJ_EARLY_USER_ID",early);
            map.put("PRJ_DESIGN_USER_ID",design);
            map.put("PRJ_COST_USER_ID",cost);
        }
        return map;
    }

    /**
     * 获取人员在项目中对应的岗位信息
     * @param userId 当前用户id
     * @param map 人员岗位map组
     * @return 人员岗位信息
     */
    public static String getUserDept(String userId, Map<String, String> map) {
        String dept = "";
        for (String key : map.keySet()){
            String value = map.get(key);
            if (value.equals(userId)){
                dept = key;
            }
        }
        return dept.toUpperCase(Locale.ROOT);
    }

    /**
     * 初概-可研-概算-导入信息更新项目基础信息
     * @param investMap 信息map
     * @param entityCode 数据来源名称
     * @param level 更新级别
     * @param myJdbcTemplate 数据源
     */
    public static void updatePrjBase(String pmPrjId,Map<String, Object> investMap, String entityCode, int level, MyJdbcTemplate myJdbcTemplate) {
        //当前更新级别id
        String levelId = GrSetValueExt.getValueId("invest_priority",String.valueOf(level),myJdbcTemplate);
        // 查询当前项目信息数据级别
        int oldLevel = getPrjDataLevel(pmPrjId,myJdbcTemplate);
        if (level >= oldLevel) { //更新数据
            //更新项目基础信息
            updatePrjInvestByMap(pmPrjId,investMap,levelId);
        }
    }

    /**
     * 更新项目资金信息
     * @param pmPrjId 项目id
     * @param investMap 基础信息map
     * @param levelId 优先级id
     */
    private static void updatePrjInvestByMap(String pmPrjId, Map<String, Object> investMap,String levelId) {
        Crud.from("pm_prj").where().eq("id",pmPrjId).update()
                .set("INVEST_PRIORITY",levelId)
                .set("ESTIMATED_TOTAL_INVEST",investMap.get("PRJ_TOTAL_INVEST")) //总投资
                .set("PROJECT_AMT",investMap.get("PROJECT_AMT-OTHER")) //工程费用
                .set("CONSTRUCT_PRJ_AMT",investMap.get("CONSTRUCT_AMT")) //建安费
                .set("EQUIP_BUY_AMT",investMap.get("EQUIP_AMT")) //设备费
                .set("EQUIPMENT_COST",investMap.get("SCIENTIFIC_EQUIPMENT_AMT")) //科研费
                .set("PROJECT_OTHER_AMT",investMap.get("PROJECT_AMT")) //工程其他费用
                .set("LAND_BUY_AMT",investMap.get("LAND_AMT")) //土地征拆费
                .set("PREPARE_AMT",investMap.get("PREPARE_AMT")) //预备费
                .exec();
    }

    /**
     * 立项申请流程完结-更新项目基础信息
     * @param entityRecord 表单数据源
     */
    public static void updatePrjBaseByPrjReq(EntityRecord entityRecord) {
        String id = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        Map map = entityRecord.valueMap;
        Crud.from("PM_PRJ").where().eq("ID",id).update()
                .set("PRJ_REPLY_DATE",JdbcMapUtil.getString(map,"PRJ_REPLY_DATE")) //立项批复日期
                .set("PRJ_REPLY_NO",JdbcMapUtil.getString(map,"PRJ_REPLY_NO")) //立项批复文号
                .set("REPLY_NO",JdbcMapUtil.getString(map,"PRJ_REPLY_NO")) //批复文号
                .set("PRJ_REPLY_FILE",JdbcMapUtil.getString(map,"REPLY_FILE")) //立项批复材料
                .set("PRJ_EARLY_USER_ID",JdbcMapUtil.getString(map,"PRJ_EARLY_USER_ID")) //前期岗
                .set("PRJ_DESIGN_USER_ID",JdbcMapUtil.getString(map,"PRJ_DESIGN_USER_ID")) //设计岗
                .set("PRJ_COST_USER_ID",JdbcMapUtil.getString(map,"PRJ_COST_USER_ID")) //成本岗
                .set("PRJ_CODE",JdbcMapUtil.getString(map,"CONSTRUCTION_PROJECT_CODE")) //项目编号
                .set("CUSTOMER_UNIT",JdbcMapUtil.getString(map,"CUSTOMER_UNIT")) //业主单位
                .set("PRJ_MANAGE_MODE_ID",JdbcMapUtil.getString(map,"PRJ_MANAGE_MODE_ID")) //项目管理模式
                .set("BASE_LOCATION_ID",JdbcMapUtil.getString(map,"BASE_LOCATION_ID")) //建设地点
                .set("FLOOR_AREA",JdbcMapUtil.getString(map,"FLOOR_AREA")) //占地面积
                .set("PROJECT_TYPE_ID",JdbcMapUtil.getString(map,"PROJECT_TYPE_ID")) //项目类型
                .set("CON_SCALE_TYPE_ID",JdbcMapUtil.getString(map,"CON_SCALE_TYPE_ID")) //建设规模类型
                .set("CON_SCALE_QTY",JdbcMapUtil.getString(map,"CON_SCALE_QTY")) //道路长度
                .set("CON_SCALE_QTY2",JdbcMapUtil.getString(map,"CON_SCALE_QTY2")) //道路宽度
                .set("QTY_ONE",JdbcMapUtil.getString(map,"QTY_ONE")) //建筑面积
                .set("QTY_THREE",JdbcMapUtil.getString(map,"QTY_THREE")) //海域面积
                .set("OTHER",JdbcMapUtil.getString(map,"OTHER")) //其他
                .set("CON_SCALE_UOM_ID",JdbcMapUtil.getString(map,"CON_SCALE_UOM_ID")) //建设规模单位
                .set("PRJ_SITUATION",JdbcMapUtil.getString(map,"PRJ_SITUATION")) //项目简介
                .exec();
    }

    /**
     * 判断项目是否是设备项目,并返回工程项目id
     * @param projectId 项目id
     * @return 工程项目id
     */
    public static String getFatherProject(String projectId) {
        PmPrj pmPrj = PmPrj.selectById(projectId);
        if (pmPrj != null){
            String classId = pmPrj.getProjectClassificationId();
            if ("1704686735267102720".equals(classId)){
                projectId = pmPrj.getSubordinateProject();
            }
        }
        return projectId;
    }

    /**
     * 重复非系统项目作废扩展
     */
    public void vdRepeatPrj(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String projectName = JdbcMapUtil.getString(entityRecord.valueMap,"NAME");
        String vdProjectId = JdbcMapUtil.getString(entityRecord.valueMap,"ID");
        List<PmPrj> list = PmPrj.selectByWhere(new Where().eq(PmPrj.Cols.NAME,projectName).eq(PmPrj.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list)){
            List<PmPrj> prjList = list.stream().filter(p->"0099952822476441374".equals(p.getProjectSourceTypeId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(prjList)){
                String prjId = prjList.get(0).getId();
                //作废非系统项目
                Crud.from("PM_PRJ").where().eq("ID",vdProjectId).update().set("STATUS","VD").exec();
                //涉及流程标题处理
                ProcessCommon.updateProcessTitleByProjectName(projectName,prjId);
            } else {
                throw new BaseException("该项目不存在其他项目来源位 ”系统“ 的数据，不允许作废！");
            }
        }
    }

    /**
     * 在花名册中根据岗位判断改人员所在部门
     * @param userId 人员id
     * @param projectId 项目id
     * @param companyId 业主单位id
     * @param csCommId 业务数据表id
     * @param entCode 业务表名
     * @param myJdbcTemplate 数据源
     * @return 人员部门信息
     */
    public static String getUserDeptByRoster(String userId, String projectId, String companyId, String csCommId, String entCode, MyJdbcTemplate myJdbcTemplate) {
        String dept = "";
        Map<String,Object> map = PmRosterExt.getUserDeptCodeByRoster(userId,projectId,companyId,myJdbcTemplate);
        if (!map.isEmpty()){
            dept = (String) map.get("deptCode");
        } else {
            List<Map<String,Object>> list = LinkSql.getPrjProcessUserDept(csCommId,entCode,myJdbcTemplate);
            if (!CollectionUtils.isEmpty(list)){
                map = list.get(0);
                if (!map.isEmpty()){
                    for (String tmp : map.keySet()){
                        Object value1 = map.get(tmp);
                        if (value1 != null){
                            String value = value1.toString();
                            if (!SharedUtil.isEmptyString(value) && userId.equals(value)){
                                dept = tmp;
                            }
                        }
                    }
                }
            }
        }
        return dept;
    }

    /**
     * 非系统多项目-根据项目名称创建项目
     * @param projectName 项目名称
     * @return 项目id
     */
    public static String createPrjByMoreName(String projectName) {
        StringBuilder sb = new StringBuilder();
        List<String> prjNameList = StringUtil.getStrToList(projectName,",");
        for (String tp : prjNameList) {
            List<PmPrj> prjList = PmPrj.selectByWhere(new Where().eq(PmPrj.Cols.NAME,tp).eq(PmPrj.Cols.STATUS,"AP"));
            String prjCode = "", id = "";
            if (CollectionUtils.isEmpty(prjList)){
                prjCode = PmPrjCodeUtil.getPrjCode();
                id = Crud.from("pm_prj").insertData();
            } else {
                prjCode = prjList.get(0).getPmCode();
                id = prjList.get(0).getId();
            }
            Crud.from("pm_prj").where().eq("id",id).update()
                    .set("name",projectName).set("PROJECT_SOURCE_TYPE_ID","0099952822476441375")
                    .set("PM_CODE",prjCode).set("IZ_END",0).set("IZ_START_REQUIRE",1).set("IZ_FORMAL_PRJ","1")
                    .exec();
            sb.append(id).append(",");
        }
        sb.deleteCharAt(sb.length()-1);
        return sb.toString();
    }

    /**
     * 项目库系统写入项目启动/项目谋划
     */
    public void prjInsertStart(){
        List<PmPrj> prjList = PmPrj.selectByWhere(new Where().eq(PmPrj.Cols.STATUS,"AP")
                .eq(PmPrj.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441374"));
        if (!CollectionUtils.isEmpty(prjList)){
            for (PmPrj tmp : prjList) {
                String prjCode = tmp.getPmCode();
                String prjName = tmp.getName();
                //通过名称和code查询项目启动中是否存在
                List<PrjStart> prjStartList = PrjStart.selectByWhere(new Where().eq(PrjStart.Cols.PM_CODE,prjCode)
                        .eq(PrjStart.Cols.STATUS,"AP").eq(PrjStart.Cols.NAME,prjName));
                if (CollectionUtils.isEmpty(prjStartList)){ // 新增项目启动
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String nowDate = sdf.format(new Date());
                    String prjStartId = Crud.from(PrjStart.ENT_CODE).insertData();
                    Crud.from(PrjStart.ENT_CODE).where().eq(PrjStart.Cols.ID,prjStartId).update()
                            .set(PrjStart.Cols.VER,99).set(PrjStart.Cols.TS,nowDate)
                            .set(PrjStart.Cols.CRT_DT,nowDate).set(PrjStart.Cols.CRT_USER_ID,"0099799190825078690")
                            .set(PrjStart.Cols.LAST_MODI_DT,nowDate).set(PrjStart.Cols.LAST_MODI_USER_ID,"0099799190825078690")
                            .set(PrjStart.Cols.STATUS,"AP").set(PrjStart.Cols.PRJ_SITUATION,tmp.getPrjSituation())
                            .set(PrjStart.Cols.NAME,prjName).set(PrjStart.Cols.PRJ_TOTAL_INVEST,tmp.getEstimatedTotalInvest())
                            .set(PrjStart.Cols.PROJECT_TYPE_ID,tmp.getProjectTypeId()).set(PrjStart.Cols.BUILDER_UNIT,tmp.getBuilderUnit())
                            .set(PrjStart.Cols.PM_CODE,prjCode).set(PrjStart.Cols.INVESTMENT_SOURCE_ID,tmp.getInvestmentSourceId())
                            .set(PrjStart.Cols.BASE_LOCATION_ID,tmp.getBaseLocationId()).set(PrjStart.Cols.TENDER_MODE_ID,tmp.getTenderModeId())
                            .set(PrjStart.Cols.PRJ_START_STATUS_ID,"1636549534274465792")
                            .exec();

                    //写入项目谋划库
                    List<PmPlan> pmPlanList = PmPlan.selectByWhere(new Where().eq(PmPlan.Cols.NAME,prjName).eq(PmPlan.Cols.STATUS,"AP"));
                    if (CollectionUtils.isEmpty(pmPlanList)){
                        String pmPlanId = Crud.from(PmPlan.ENT_CODE).insertData();
                        String pmPlanCode = PmPrjCodeUtil.getPmPlanCode();
                        Crud.from(PmPlan.ENT_CODE).where().eq(PmPlan.Cols.ID,pmPlanId).update()
                                .set(PmPlan.Cols.VER,99).set(PmPlan.Cols.CRT_USER_ID,"0099799190825078690")
                                .set(PmPlan.Cols.LAST_MODI_DT,nowDate).set(PmPlan.Cols.LAST_MODI_USER_ID,"0099799190825078690")
                                .set(PmPlan.Cols.STATUS,"AP").set(PmPlan.Cols.CODE,pmPlanCode)
                                .set(PmPlan.Cols.NAME,prjName).set(PmPlan.Cols.BASE_LOCATION_ID,tmp.getBaseLocationId())
                                .set(PmPlan.Cols.PROJECT_TYPE_ID,tmp.getProjectTypeId()).set(PmPlan.Cols.PLAN_STATUS_ID,"1635456054244651008")
                                .set(PmPlan.Cols.AMT,tmp.getEstimatedTotalInvest())
                                .exec();
                    }
                }
            }
        }
    }

    /**
     * 项目暂停
     */
    public void prjStatusChange(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmPrj param = JsonUtil.fromJson(json, PmPrj.class);
        String projectId = param.getId();
        if (SharedUtil.isEmptyString(projectId)){
            throw new BaseException("项目id不能为空");
        }
        param = PmPrj.selectById(projectId);
        startToStop(projectId,param);
    }

    /**
     * 项目暂停
     * @param projectId 项目id
     * @param project 项目信息
     */
    private void startToStop(String projectId, PmPrj project) {
        //更新项目库状态
        Crud.from(PmPrj.ENT_CODE).where().eq(PmPrj.Cols.ID,projectId).update().set(PmPrj.Cols.PROJECT_STATUS,"1661568714048413696").exec();
        //刷新谋划库
        PmPlanExt.refreshPrj(projectId,project);
    }

    /**
     * 更新项目状态
     * @param projectIds 项目id
     * @param statusId 状态id
     */
    public static void updatePrjStatus(String projectIds, String statusId) {
        String[] arr = projectIds.split(",");
        for (String projectId : arr) {
            Crud.from(PmPrj.ENT_CODE).where().eq(PmPrj.Cols.ID,projectId).update()
                    .set(PmPrj.Cols.PROJECT_PHASE_ID,statusId)
                    .exec();
        }
    }

    /**
     * 项目问题台账-项目下拉列表
     */
    public void getProjectProblemPrjList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select distinct a.pm_prj_id as prjId,b.name as prjName from PM_PROJECT_PROBLEM_REQ a left join pm_prj b on a.pm_prj_id = b.id where a.status != 'VD' order by a.pm_prj_id asc";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        Map<String, Object> result = new HashMap<>();
        result.put("prjList",list);
        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 更新项目效果图
     * @param rendering 效果图地址
     * @param projectId 项目id
     */
    public static void updatePrjImg(String rendering, String projectId) {
        String imgUrl = null;
        PmPrj pmPrj = PmPrj.selectById(projectId);
        if (pmPrj != null){
            String prjImg = pmPrj.getPrjImg();
            if (StringUtils.hasText(prjImg)){
                imgUrl = prjImg + "," + rendering;
            } else {
                imgUrl = rendering;
            }
        }
        if (StringUtils.hasText(imgUrl)){
            Crud.from("PM_PRJ").where().eq("ID",projectId).update().set(PmPrj.Cols.PRJ_IMG,imgUrl).exec();
        }
    }

    /**
     * 通用-动态根据字段更新值
     * @param prj 项目id
     * @param value 更新的值
     * @param colCode 更新的字段
     */
    public static void updateOneColValue(String prj, String value, String colCode) {
        Crud.from(PmPrj.ENT_CODE).where().eq(PmPrj.Cols.ID,prj).update()
                .set(colCode,value).exec();
    }

    /**
     * 更新项目表信息
     * @param pmPrj 项目实体
     */
    public static void updateData(PmPrj pmPrj) {
        pmPrj.updateById();
    }

    /**
     * 变更项目开工条件、完工状态
     */
    public void changePrjStatus(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmPrjView param = JsonUtil.fromJson(json,PmPrjView.class);
        PmPrj pmPrj = new PmPrj();
        pmPrj.setId(param.getProjectId());
        if (param.getWeatherCompleted() == 1){
            pmPrj.setIzEnd(true);
        } else {
            pmPrj.setIzEnd(false);
        }
        if (param.getWeatherStart() == 1){
            pmPrj.setIzStartRequire(true);
        } else {
            pmPrj.setIzStartRequire(false);
        }
        pmPrj.updateById();
    }
}
