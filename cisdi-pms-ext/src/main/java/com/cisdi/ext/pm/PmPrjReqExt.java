package com.cisdi.ext.pm;

import com.cisdi.ext.enums.FileCodeEnum;
import com.cisdi.ext.util.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PmPrjReqExt {

    public void validateBeforeSave() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            validateBeforeSave(entityRecord);
        }
    }

    private void validateBeforeSave(EntityRecord entityRecord) {
        StringBuilder sbErr = new StringBuilder();
        if (entityRecord.extraEditableAttCodeList == null) {
            entityRecord.extraEditableAttCodeList = new ArrayList<>();
        }
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_TYPE_ID");
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_UOM_ID");


        Map<String, Object> valueMap = entityRecord.valueMap;

        String prj_total_investStr = SharedUtil.isEmptyObject(JdbcMapUtil.getString(valueMap, "PRJ_TOTAL_INVEST")) ? "0":JdbcMapUtil.getString(valueMap, "PRJ_TOTAL_INVEST");
        Double prj_total_invest = Double.parseDouble(prj_total_investStr);
        AmtUtil.checkAmt(sbErr, prj_total_invest, "总投资");

        String CONSTRUCT_AMTStr = SharedUtil.isEmptyObject(JdbcMapUtil.getString(valueMap, "CONSTRUCT_AMT")) ? "0":JdbcMapUtil.getString(valueMap, "CONSTRUCT_AMT");
        Double construct_amt = Double.parseDouble(CONSTRUCT_AMTStr);

        String EQUIP_AMTStr = SharedUtil.isEmptyObject(JdbcMapUtil.getString(valueMap, "EQUIP_AMT")) ? "0":JdbcMapUtil.getString(valueMap, "EQUIP_AMT");
        Double equip_amt = Double.parseDouble(EQUIP_AMTStr);

        String PROJECT_OTHER_AMTStr = SharedUtil.isEmptyObject(JdbcMapUtil.getString(valueMap, "PROJECT_OTHER_AMT")) ? "0":JdbcMapUtil.getString(valueMap, "PROJECT_OTHER_AMT");
        Double project_other_amt = Double.parseDouble(PROJECT_OTHER_AMTStr);

        String LAND_AMTStr = SharedUtil.isEmptyObject(JdbcMapUtil.getString(valueMap, "LAND_AMT")) ? "0":JdbcMapUtil.getString(valueMap, "LAND_AMT");
        Double land_amt = Double.parseDouble(LAND_AMTStr);

        String PREPARE_AMTStr = SharedUtil.isEmptyObject(JdbcMapUtil.getString(valueMap, "PREPARE_AMT")) ? "0":JdbcMapUtil.getString(valueMap, "PREPARE_AMT");
        Double prepare_amt = Double.parseDouble(PREPARE_AMTStr);

        if (DoubleUtil.add(construct_amt,project_other_amt, prepare_amt,equip_amt,land_amt) > prj_total_invest) {
            sbErr.append("建安工程费+工程其他费用+预备费+设备采购费+土地征拆费用>总投资！");
        }

        if (sbErr.length() > 0) {
            throw new BaseException(sbErr.toString());
        }
    }


    public void createPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            createPrj(entityRecord);
        }
    }

    private void createPrj(EntityRecord entityRecord) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String csCommId = entityRecord.csCommId;

        // 获取立项申请：
        Map<String, Object> pm_prj_req = Crud.from("pm_prj_req")
                .where().eq("ID", csCommId)
                .select().specifyCols().execForMap();

        // 新建项目：
        String newPrjId = Crud.from("PM_PRJ").insertData();
        Integer exec = Crud.from("PM_PRJ").where().eq("ID", newPrjId).update().set("PM_PRJ_REQ_ID", pm_prj_req.get(
                        "id")).set("code", pm_prj_req.get("code")).set("name", pm_prj_req.get("PRJ_NAME")).set("CUSTOMER_UNIT",
                        pm_prj_req.get("CUSTOMER_UNIT")).set("PRJ_MANAGE_MODE_ID", pm_prj_req.get("PRJ_MANAGE_MODE_ID")).set(
                        "BASE_LOCATION_ID", pm_prj_req.get("BASE_LOCATION_ID")).set("FLOOR_AREA", pm_prj_req.get(
                        "FLOOR_AREA")).set("PROJECT_TYPE_ID", pm_prj_req.get("PROJECT_TYPE_ID")).set(
                        "CON_SCALE_TYPE_ID", pm_prj_req.get("CON_SCALE_TYPE_ID")).set("CON_SCALE_QTY"
                        , pm_prj_req.get("CON_SCALE_QTY")).set("CON_SCALE_QTY2", pm_prj_req.get("CON_SCALE_QTY2")).set(
                        "CON_SCALE_UOM_ID", pm_prj_req.get("CON_SCALE_UOM_ID")).set("PRJ_SITUATION", pm_prj_req.get(
                        "PRJ_SITUATION")).set("INVESTMENT_SOURCE_ID", pm_prj_req.get("INVESTMENT_SOURCE_ID"))
                .set("PRJ_EARLY_USER_ID", pm_prj_req.get("PRJ_EARLY_USER_ID")).set("PRJ_DESIGN_USER_ID", pm_prj_req.get("PRJ_DESIGN_USER_ID"))
                .set("PRJ_COST_USER_ID", pm_prj_req.get("PRJ_COST_USER_ID")).set("PRJ_CODE", pm_prj_req.get("PRJ_CODE"))
                .set("BUILDING_AREA", pm_prj_req.get("CON_SCALE_QTY")).set("QTY_ONE",pm_prj_req.get("QTY_ONE"))
                .set("QTY_TWO",pm_prj_req.get("QTY_TWO")).set("QTY_THREE",pm_prj_req.get("QTY_THREE"))
                .exec();
        log.info("已更新：{}", exec);

        // 将项目ID设置到立项申请上：
        Integer exec1 = Crud.from("pm_prj_req").where().eq("ID", csCommId).update().set("pm_prj_id", newPrjId).exec();
        log.info("已更新：{}", exec1);

        // 立项匡算的集合值：
        Map<String, Object> grSetValueInvest0 =
                Crud.from("GR_SET_VALUE").where().eq("CODE", "invest0").select().execForMap();

        // 新建项目投资测算：
        WfPmInvestUtil.calculateData(csCommId, "PM_PRJ_REQ", newPrjId);

        // 新增项目进度计划网络图
        createPlan(newPrjId);

        // 创建项目文件夹
        ProFileUtils.createFolder(newPrjId);

        // 立项申请文件归档
        // 项目申请材料
        String reqFile = JdbcMapUtil.getString(pm_prj_req, "PRJ_REQ_FILE");
        ProFileUtils.insertProFile(newPrjId, reqFile, FileCodeEnum.PRJ_REQ_FILE);

    }

    private void createInvestEstDtl(Map<String, Object> pm_prj_req, String newInvestEtsId, String colName) {
        // 获取金额：
        Object amt = pm_prj_req.get(colName);

        // 获取费用类型ID：
        Object expTypeId =
                Crud.from("PM_EXP_TYPE").where().eq("code", colName).select().specifyCols("ID").execForValue();
        if (SharedUtil.isEmptyObject(expTypeId)) {
            throw new BaseException("没有" + colName + "对应的费用类型！");
        }

        // 新建项目投资测算明细：
        String newInvestEstDtlId = Crud.from("PM_INVEST_EST_DTL").insertData();
        Integer exec3 = Crud.from("PM_INVEST_EST_DTL").where().eq("ID", newInvestEstDtlId).update().set(
                "PM_EXP_TYPE_ID", expTypeId).set("AMT", amt).set("PM_INVEST_EST_ID", newInvestEtsId).exec();
        log.info("已更新：{}", exec3);
    }

    /**
     * 建设年限写入项目信息表
     */
    public void updatePrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            updatePrjYearLimit(entityRecord);
        }
    }

    private void updatePrjYearLimit(EntityRecord entityRecord) {

        // 获取项目id
        String projectId = entityRecord.valueMap.get("PM_PRJ_ID").toString();

        // 获取建设年限
        String year = JdbcMapUtil.getString(entityRecord.valueMap,"BUILD_YEARS");

        // 修改项目建设年限信息：
        Integer exec = Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("BUILD_YEARS", year).exec();
        log.info("已更新：{}", exec);
    }

    /**
     * 立项批复信息写入项目表
     */
    public void updatePrjReply() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            updatePrjReply(entityRecord);
        }
    }

    private void updatePrjReply(EntityRecord entityRecord) {

        // 获取项目id
//        String projectId = entityRecord.valueMap.get("PM_PRJ_ID").toString();
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap, "PM_PRJ_ID");
        if (projectId == null || projectId.length() == 0) {
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
            // 流程id
            String csCommId = entityRecord.csCommId;
            List<Map<String, Object>> map1 = myJdbcTemplate.queryForList("SELECT id FROM pm_prj WHERE PM_PRJ_REQ_ID = ? ORDER BY CRT_DT desc limit 1", csCommId);
            if (CollectionUtils.isEmpty(map1)) {
                throw new BaseException("项目未创建完成无法进行批复信息同步");
            } else {
                projectId = map1.get(0).get("id").toString();
            }
        }

        // 批复日期
        String replyDate = entityRecord.valueMap.get("PRJ_REPLY_DATE").toString();
        // 批复文号
        String replyNo = entityRecord.valueMap.get("PRJ_REPLY_NO").toString();
        // 批复材料
        String replyFile = entityRecord.valueMap.get("REPLY_FILE").toString();
        // 资金来源
        String investmentSourceId = entityRecord.valueMap.get("INVESTMENT_SOURCE_ID").toString();
        //前期岗
        String userId = ExtJarHelper.loginInfo.get().userId;
        //工程项目编号
        String code = entityRecord.valueMap.get("CONSTRUCTION_PROJECT_CODE").toString();

        // 修改项目建设年限信息：
        Integer exec = Crud.from("PM_PRJ").where().eq("ID", projectId).update().set("PRJ_REPLY_DATE", replyDate)
                .set("PRJ_REPLY_NO", replyNo).set("PRJ_REPLY_FILE", replyFile).set("INVESTMENT_SOURCE_ID", investmentSourceId)
                .set("PRJ_EARLY_USER_ID",userId).set("PRJ_CODE",code).exec();
        log.info("已更新：{}", exec);

        //同步立项后续文件
        // 盖章立项申请书
        String applyBook = JdbcMapUtil.getString(entityRecord.valueMap, "STAMPED_PRJ_REQ_FILE");
        ProFileUtils.insertProFile(projectId, applyBook, FileCodeEnum.STAMPED_PRJ_REQ_FILE);

        // 批复文件
        String reply = JdbcMapUtil.getString(entityRecord.valueMap, "REPLY_FILE");
        ProFileUtils.insertProFile(projectId, reply, FileCodeEnum.REPLY_FILE);
    }

    /**
     * 采购公开招标-回显采购方式及招标控制价
     */
    public void updateBid() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String procInstId = ExtJarHelper.procInstId.get();

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 采购方式
        String buyType = entityRecord.valueMap.get("APPROVE_PURCHASE_TYPE").toString();
        // 招标控制价
        String price = entityRecord.valueMap.get("APPROVE_BID_CTL_PRICE").toString();


        myJdbcTemplate.update("update PO_PUBLIC_BID_REQ t set t.APPROVE_PURCHASE_TYPE_ECHO = ?,t" +
                ".BID_CTL_PRICE_LAUNCH_ECHO=? where t.id=?", buyType, price, csCommId);
    }

    /**
     * 采购公开招标-更新招标复检
     */
    public void updateBidFile() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 该条记录id
        String csCommId = entityRecord.csCommId;
        // 文件id
        String fileId = entityRecord.valueMap.get("BID_FILE_GROUP_ID").toString();
        myJdbcTemplate.update("update PO_PUBLIC_BID_REQ set BID_ISSUE_FILE_GROUP_ID = ? where id = ?", fileId, csCommId);

    }

    /**
     * 采购公开招标-更新需求发起人
     */
    public void updatePromoter() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 该条记录id
        String csCommId = entityRecord.csCommId;
        // 需求发起人
        String promoterName = myJdbcTemplate.queryForMap("SELECT u.name FROM ad_user u left join po_public_bid_req b on" +
                " u.id = " +
                "b.CRT_USER_ID where b.id = ?", csCommId).get("name").toString();
        // 更新需求发起人
        myJdbcTemplate.update("update PO_PUBLIC_BID_REQ set DEMAND_PROMOTER = ? where id = ?", promoterName, csCommId);
    }

    /**
     * 采购公开招标-更新中标信息
     */
    public void updateWinUnit() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 该条记录id
        String csCommId = entityRecord.csCommId;
        // 需要更新的信息
        String winBidUnitTxt = entityRecord.valueMap.get("WIN_BID_UNIT_TXT").toString();
        String tenderOffer = entityRecord.valueMap.get("TENDER_OFFER").toString();
        String contactName = entityRecord.valueMap.get("CONTACT_NAME").toString();
        String contactMobileWin = entityRecord.valueMap.get("CONTACT_MOBILE_WIN").toString();
        String idcardWin = entityRecord.valueMap.get("CONTACT_IDCARD_WIN").toString();
        // 更新
        myJdbcTemplate.update("update PO_PUBLIC_BID_REQ set WIN_BID_UNIT_RECORD = ?," +
                        "TENDER_OFFER_RECORD = ?," +
                        "CONTACT_NAME_RECORD =?," +
                        "CONTACT_MOBILE_RECORD =?," +
                        "CONTACT_IDCARD_RECORD =? where id = ?", winBidUnitTxt, tenderOffer, contactName, contactMobileWin,
                idcardWin, csCommId);
    }

    /**
     * 采购公开招标
     * 改动后的回显招标控制价不能大于原招标控制价
     */
    public void compareCtlPrice() {
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        if (entityRecord.extraEditableAttCodeList == null) {
            entityRecord.extraEditableAttCodeList = new ArrayList<>();
        }
        entityRecord.extraEditableAttCodeList.add("BID_CTL_PRICE_LAUNCH_ECHO");
        Map<String, Object> valueMap = entityRecord.valueMap;

        // 拿到两个控制价
        Double bidCtlPriceLaunchEcho = Double.parseDouble(JdbcMapUtil.getString(valueMap, "BID_CTL_PRICE_LAUNCH_ECHO"));
        Double bidCtlPriceLaunch = Double.parseDouble(JdbcMapUtil.getString(valueMap, "BID_CTL_PRICE_LAUNCH"));

        if (bidCtlPriceLaunch != null && bidCtlPriceLaunchEcho != null && Double.compare(bidCtlPriceLaunchEcho,
                bidCtlPriceLaunch) == 1) {
            throw new BaseException("招标控制价不应大于核定招标控制价");
        }
    }

    /**
     * 项目类型数量填写控制
     */
    public void validateProjectTypeBeforeSave() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            validateProjectTypeBeforeSave(entityRecord);
        }
    }

    private void validateProjectTypeBeforeSave(EntityRecord entityRecord) {
        StringBuilder sbErr = new StringBuilder();
        if (entityRecord.extraEditableAttCodeList == null) {
            entityRecord.extraEditableAttCodeList = new ArrayList<>();
        }
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_TYPE_ID");
        entityRecord.extraEditableAttCodeList.add("CON_SCALE_UOM_ID");


        Map<String, Object> valueMap = entityRecord.valueMap;
        String con_scale_type_id = JdbcMapUtil.getString(valueMap, "CON_SCALE_TYPE_ID");
        Double con_scale_qty2 = Double.parseDouble(JdbcMapUtil.getString(valueMap, "CON_SCALE_QTY2"));
        boolean need2 = "0099799190825087119".equals(con_scale_type_id);
        if (need2) {
            if (con_scale_qty2 == null || con_scale_qty2 <= 0d) {
                sbErr.append("建设规模数量2请填写正确宽度！");
            }
        } else {
            if (con_scale_qty2 != null && con_scale_qty2 != 0d) {
                sbErr.append("建设规模数量2请不要填写！");
            }
        }
        if (sbErr.length() > 0) {
            throw new BaseException(sbErr.toString());
        }
    }

    /**
     * 新增项目进度网络图
     *
     * @param projectId
     */
    private void createPlan(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            // 根据项目类型查询项目进度计划模板
            Map<String, Object> proMap = myJdbcTemplate.queryForMap("select ppp.*,PRJ_REPLY_DATE from PM_PRO_PLAN ppp \n" +
                    "left join pm_prj pp on ppp.TEMPLATE_FOR_PROJECT_TYPE_ID = pp.PROJECT_TYPE_ID\n" +
                    "where ppp.`STATUS`='AP' and ppp.IS_TEMPLATE='1' and pp.id=?", projectId);
            if (!Objects.isNull(proMap)) {
                // 先创建项目的进度计划
                String newPlanId = Crud.from("PM_PRO_PLAN").insertData();

                Crud.from("PM_PRO_PLAN").where().eq("ID", newPlanId).update().set("IS_TEMPLATE", 0).set("PM_PRJ_ID", projectId).set("PLAN_TOTAL_DAYS", proMap.get("PLAN_TOTAL_DAYS"))
                        .set("PROGRESS_STATUS_ID", proMap.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", proMap.get("PROGRESS_RISK_TYPE_ID")).set("START_DAY", proMap.get("START_DAY")).exec();


                // 查询项目进度计划节点模板
                List<Map<String, Object>> planNodeList = myJdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT," +
                        "LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,ACTUAL_START_DATE,PROGRESS_RISK_REMARK,PM_PRO_PLAN_ID,PLAN_START_DATE," +
                        "PLAN_TOTAL_DAYS,PLAN_CARRY_DAYS,ACTUAL_CARRY_DAYS,ACTUAL_TOTAL_DAYS,PLAN_CURRENT_PRO_PERCENT,ACTUAL_CURRENT_PRO_PERCENT," +
                        "ifnull(PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,SHOW_IN_EARLY_PROC,SHOW_IN_PRJ_OVERVIEW," +
                        "PROGRESS_STATUS_ID,PROGRESS_RISK_TYPE_ID,CHIEF_DEPT_ID,CHIEF_USER_ID,START_DAY,SEQ_NO,CPMS_UUID,CPMS_ID,`LEVEL`,LINKED_WF_PROCESS_ID,LINKED_WF_NODE_ID " +
                        "from PM_PRO_PLAN_NODE where PM_PRO_PLAN_ID=?", proMap.get("ID"));
                if (planNodeList.size() > 0) {
                    planNodeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
                        String id = Crud.from("PM_PRO_PLAN_NODE").insertData();

                        Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                                .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                                .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("CHIEF_USER_ID", m.get("CHIEF_USER_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                                .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_WF_NODE_ID", m.get("LINKED_WF_NODE_ID")).exec();

                        getChildrenNode(m, planNodeList, id, newPlanId);
                    }).collect(Collectors.toList());
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<Map<String, Object>> getChildrenNode(Map<String, Object> root, List<Map<String, Object>> allData, String pId, String newPlanId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")), String.valueOf(root.get("ID")))).peek(m -> {
            String id = Crud.from("PM_PRO_PLAN_NODE").insertData();
            Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                    .set("PM_PRO_PLAN_NODE_PID", pId)
                    .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                    .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("CHIEF_USER_ID", m.get("CHIEF_USER_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                    .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_WF_NODE_ID", m.get("LINKED_WF_NODE_ID")).exec();
            getChildrenNode(m, allData, id, newPlanId);
        }).collect(Collectors.toList());
    }


    public void createProjectFolder() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("pmPrjId"));
        ProFileUtils.createFolder(projectId);
    }

    /** 立项申请-发起时数据校验 **/
    public void checkCreateDate(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目名称
        String projectName = entityRecord.valueMap.get("PRJ_NAME").toString();
        //流程id
        String id = entityRecord.csCommId;
        //查询名称是否存在
        String sql1 = "select name from PM_PRJ_REQ where PRJ_NAME = ? and id != ?";
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,projectName,id);
        if (!CollectionUtils.isEmpty(list1)){
            throw new BaseException("项目名称不能重复，请重新输入项目名称！");
        }
    }

    /** 立项结束没有生产项目进展图的项目 生成项目进展图 **/
    public void createProcess(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目id
        String projectId = entityRecord.csCommId;
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan where PM_PRJ_ID = ?",projectId);
        if (CollectionUtils.isEmpty(list)){
            createPlan(projectId);
        }
    }

    public static String getPrjId(Map<String, Object> valueMap) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //项目来源类型  0099952822476441375=非立项
        String projectType = JdbcMapUtil.getString(valueMap,"PROJECT_SOURCE_TYPE_ID");
        //部门
        String deptId = JdbcMapUtil.getString(valueMap, "CRT_DEPT_ID");
        String prjId = "";
        if ("0099952822476441375".equals(projectType)){
            //创建人
            String userId = JdbcMapUtil.getString(valueMap,"CRT_USER_ID");
            //项目名称
            String projectName = JdbcMapUtil.getString(valueMap,"PROJECT_NAME_WR");
            projectName = StringUtil.chineseCodeToEnCode(projectName,",");
            List<String> prjNameList = new ArrayList<>();
            int index = projectName.indexOf(",");
            if (index == -1){
                prjNameList.add(projectName);
            } else {
                prjNameList = Arrays.asList(projectName.split(","));
            }
            for (String tmp : prjNameList) {
                String sql1 = "select * from pm_prj where name = ? and PROJECT_SOURCE_TYPE_ID = ?";
                List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,tmp,projectType);
                if (CollectionUtils.isEmpty(list1)){
                    prjId = Crud.from("pm_prj").insertData();
                    myJdbcTemplate.update("update pm_prj set CRT_USER_ID = ?,STATUS = ?,NAME = ?,PROJECT_SOURCE_TYPE_ID = ? where id = ?",userId,"AP",tmp,projectType,prjId);
                    String pmDeptId = Crud.from("pm_dept").insertData();
                    myJdbcTemplate.update("update pm_dept set PM_PRJ_ID = ?,HR_DEPT_ID = ?,USER_IDS = ?,ver = ? where id = ?",prjId,deptId,userId,1,pmDeptId);
                } else {
                    prjId = JdbcMapUtil.getString(list1.get(0),"id");
                    String sql2 = "select * from pm_dept where PM_PRJ_ID=? and HR_DEPT_ID=? and status='ap'";
                    List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2,prjId,deptId);
                    if (CollectionUtils.isEmpty(list2)){
                        String pmDeptId = Crud.from("pm_dept").insertData();
                        myJdbcTemplate.update("update pm_dept set PM_PRJ_ID = ?,HR_DEPT_ID = ?,USER_IDS = ?,ver = ? where id = ?",prjId,deptId,userId,1,pmDeptId);
                    } else {
                        String userIds = JdbcMapUtil.getString(list2.get(0),"USER_IDS");
                        if (!userIds.contains(userId)){
                            userIds = userIds+","+userId;
                            myJdbcTemplate.update("update pm_dept set USER_IDS = ? where id = ?",userIds,JdbcMapUtil.getString(list2.get(0),"id"));
                        }
                    }
                }
            }
        }
        return prjId;
    }
}

