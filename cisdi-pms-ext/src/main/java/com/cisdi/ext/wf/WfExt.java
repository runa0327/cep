package com.cisdi.ext.wf;

import com.cisdi.ext.enums.FileCodeEnum;
import com.cisdi.ext.model.PmFundReqPlan;
import com.cisdi.ext.util.ProFileUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;
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
        saveFile();
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
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {
                String csCommId = entityRecord.csCommId;
                Map<String, Object> valueMap = entityRecord.valueMap;
                int update = jdbcTemplate.update("update " + entityCode + " t set t.status = ? where t.id=?", newStatus, csCommId);
                log.info("已更新：{}", update);

                //审批流审批通过
                if ("AP".equals(newStatus)) {
                    Format formatCount = new DecimalFormat("0000");

                    //立项申请生成项目编号
                    if ("PM_PRJ_REQ".equals(entityCode)) {
                        Object prj_code = jdbcTemplate.queryForMap("select t.PRJ_CODE from PM_PRJ_REQ t where t.id=?", csCommId).get("PRJ_CODE");

                        // 若无项目编号：
                        if (SharedUtil.isEmptyObject(prj_code)) {
                            // 获取新的项目编号：
                            Object new_prj_code = jdbcTemplate.queryForMap("select concat('XM','-',lpad((select count(*) from pm_prj_req t),4,0)," +
                                    "'-',replace(current_date,'-','')) prj_code").get("prj_code");

                            // 设置项目编号、代码：
                            int update1 = jdbcTemplate.update("update PM_PRJ_REQ t set t.prj_code=?,t.code=? where t.id=?", new_prj_code,
                                    new_prj_code, csCommId);
                            log.info("已更新：{}", update1);
                        }
                    }

                    //合同签订批准后生成合同编号
                    if ("PO_ORDER_REQ".equals(entityCode) || "po_order_req".equals(entityCode)) {

                        //查询当前已审批通过的招标合同数量
                        List<Map<String, Object>> map = jdbcTemplate.queryForList("select count(*) as num from " +
                                "PO_ORDER_REQ where status = 'AP' ");
                        Date date = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        String year = sdf.format(date).substring(0, 7).replace("-", "");
                        //合同编码规则
                        int num = Integer.valueOf(map.get(0).get("num").toString()) + 1;

                        String formatNum = formatCount.format(num);
                        String code = "gc-" + year + "-" + formatNum;
                        String name = valueMap.get("CONTRACT_NAME").toString();
                        int update2 = jdbcTemplate.update("update PO_ORDER_REQ set CONTRACT_CODE = ? , NAME = ? where id = ?",
                                code, name, csCommId);
                    }
                    //补充合同批准后生成合同编号
                    if ("PO_ORDER_SUPPLEMENT_REQ".equals(entityCode)) {
                        //查询当前审批通过的补充合同数量和该合同的name
                        List<Map<String, Object>> map = jdbcTemplate.queryForList("select count(*) as num from " +
                                "PO_ORDER_REQ where status = 'AP' ");
                        int num = Integer.valueOf(map.get(0).get("num").toString()) + 1;
                        String formatNum = formatCount.format(num);

                        String relationContractId = valueMap.get("RELATION_CONTRACT_ID").toString();
                        List<Map<String, Object>> nameMap = jdbcTemplate.queryForList("SELECT CONTRACT_NAME FROM " +
                                "po_order_req where id = ?", relationContractId);
                        String name = nameMap.get(0).get("CONTRACT_NAME").toString();
                        String contractName = name + "补充协议" + formatNum;
                        //写入到补充合同表
                        int update1 = jdbcTemplate.update("update PO_ORDER_SUPPLEMENT_REQ set CONTRACT_NAME = ?  " +
                                "where id = ? ", contractName, csCommId);
                    }

                    // 通用方法。流程审批完后将流程名称写入该流程表名称字段
                    List<String> nameList = getTableList();
                    if (nameList.contains(entityCode)) {
                        if ("PM_PRJ_REQ".equals(entityCode)) {
                            int update1 = jdbcTemplate.update("update PM_PRJ_REQ t set t.name=t.PRJ_NAME where t.id=?", csCommId);
                        } else if("PO_ORDER_REQ".equals(entityCode)){
                            String name = entityRecord.valueMap.get("CONTRACT_NAME").toString();
                            int update1 = jdbcTemplate.update("update PM_PRJ_REQ t set t.name=? where t.id=?", name,csCommId);
                        }else {
                            String sql1 = "select a.NAME from wf_process_instance a left join " + entityCode + " b on a.id = b.LK_WF_INST_ID where " +
                                    "b.id = ?";
                            List<Map<String, Object>> list = jdbcTemplate.queryForList(sql1, csCommId);
                            if (CollectionUtils.isEmpty(list)) {
                                throw new BaseException("流程标题不能为空");
                            }
                            String name = list.get(0).get("NAME").toString();
                            String sql2 = "update " + entityCode + " set NAME = ? where id = ?";
                            int update2 = jdbcTemplate.update(sql2, name, csCommId);
                        }

                    }

                    //资金需求计划申请完成同步数据
                    if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entityCode)) {
                        PmFundReqPlan pmFundReqPlan = PmFundReqPlan.insertData();
                        String id = pmFundReqPlan.id;
                        String pmFundRequirePlanReqId = entityRecord.csCommId;
                        //项目id
                        String prjId = valueMap.get("AMOUT_PM_PRJ_ID").toString();
                        //发起部门id
                        String hrDeptId = valueMap.get("CRT_DEPT_ID").toString();
                        //计划需求总金额 待修改
                        //        String totalAmt = valueMap.get("").toString();
                        BigDecimal totalAmt = new BigDecimal(10);
                        //提出时间
                        String applyTime = valueMap.get("CRT_DT").toString();
                        //备注
                        String remark = valueMap.get("REMARK").toString();
                        //计划名称 待修改
                        String name = jdbcTemplate.queryForObject("select name from pm_prj where AMOUT_PM_PRJ_ID = ?", String.class, valueMap.get(
                                "AMOUT_PM_PRJ_ID")) + "资金需求计划";

                        String sql = "update pm_fund_req_plan set NAME = ?,PM_PRJ_ID = ?,HR_DEPT_ID = ?,TOTAL_AMT = ?,APPLY_TIME = ?," +
                                "PM_FUND_REQUIRE_PLAN_REQ_ID = ?,REMARK = ? where id = ?";
                        jdbcTemplate.update(sql, name, prjId, hrDeptId, totalAmt, applyTime, pmFundRequirePlanReqId, remark, id);
                    }
                }

                //审批流程创建
                if ("APING".equals(newStatus)) {

                    //一些额外校验
                    if ("PO_PUBLIC_BID_REQ".equals(entityCode)){ //招标校验
                        checkBidReq(entityRecord,csCommId);
                    }

                    List<String> tableList = getTableList();
                    if (!CollectionUtils.isEmpty(tableList)) {
                        if (tableList.contains(entityCode)) {
                            //流程名称按规定创建
                            int update1 = 0;
                            List<String> amtPrjList = getAmtPrjList();
                            if (amtPrjList.contains(entityCode)) {
                                //资金需求计划和付款申请项目\设计任务书名称使用的另外的字段
                                update1 = jdbcTemplate.update("update wf_process_instance pi join wf_process p on pi" +
                                        ".WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join " + entityCode + " t on pi" +
                                        ".ENTITY_RECORD_ID=t.id join pm_prj prj on t.AMOUT_PM_PRJ_ID=prj.id and t.id=? set pi.name=concat( p.name," +
                                        "'-', prj.name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
                            } else {
                                update1 = jdbcTemplate.update("update wf_process_instance pi join wf_process p on pi" +
                                        ".WF_PROCESS_ID=p.id join ad_user u on pi.START_USER_ID=u.id join " + entityCode + " t on pi" +
                                        ".ENTITY_RECORD_ID=t.id join pm_prj prj on t.PM_PRJ_ID=prj.id and t.id=? set pi.name=concat( p.name,'-', " +
                                        "prj.name ,'-',u.name,'-',pi.START_DATETIME)", csCommId);
                            }
                            log.info("已更新：{}", update1);

                            //发起人是否存在部门信息校验
                            try {
                                String hrDeptId = valueMap.get("CRT_DEPT_ID").toString();
                                if (SharedUtil.isEmptyString(hrDeptId) || "0".equals(hrDeptId)) {
                                    throw new BaseException("对不起，您尚未有部门信息，不允许进行流程提交");
                                }
                            } catch (Exception e) {
                                throw new BaseException("对不起，您尚未有部门信息，不允许进行流程提交");
                            }
                        }
                    }
                }
            }
        }
    }

    //公开招标流程校验
    private void checkBidReq(EntityRecord entityRecord,String csCommId) {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        //根据id查询是否有明细信息
        List<Map<String,Object>> list1 = jdbcTemplate.queryForList("select TOTAL_AMT from pm_cost_detail where BIDDING_NAME_ID = ?",csCommId);
        if (CollectionUtils.isEmpty(list1)){
            throw new BaseException("费用明细不能为空，请填写费用明细！");
        }
        //招标控制价
        BigDecimal price = new BigDecimal(entityRecord.valueMap.get("BID_CTL_PRICE_LAUNCH").toString());

        BigDecimal priceDetail = bigDecimalSum(list1);
        if (priceDetail.compareTo(price) != 0){
            throw new BaseException("费用明细总金额数和招标总控价不等，请核查");
        }

    }

    //list内求和
    private BigDecimal bigDecimalSum(List<Map<String,Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String date = tmp.get("TOTAL_AMT").toString();
            sum = sum.add(new BigDecimal(date));
        }
        return sum;
    }

    //流程审批通过后需要更新Name字段的
    //存文件
    private void saveFile() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        String procInstId = ExtJarHelper.procInstId.get();


        //水保
        if ("PM_WATER_PLAN".equals(entityCode)) {

            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            String conservationApplyFileIds = JdbcMapUtil.getString(valueMap, "CONSERVATION_APPLY_FILE");
            String conservationReplyFileIds = JdbcMapUtil.getString(valueMap, "CONSERVATION_REPLY_FILE");
            //水保申请材料
            ProFileUtils.insertProFile(prjId, conservationApplyFileIds,
                    FileCodeEnum.WATER_SOIL_CONSERVATION_APPLICATION_MATERIAL);
            //环评批复文件
            ProFileUtils.insertProFile(prjId, conservationReplyFileIds, FileCodeEnum.EIA_REPLY_DOCUMENT);
            //水保流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.WATER_PLAN_PROCESS_ATTACHMENT);

        }

        //施工规划许可
        if ("PM_PRJ_PLANNING_PERMIT_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //方案设计核查申请材料
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "EIA_REQ_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_VERIFICATION_APPLICATION_MATERIALS);
            //方案设计评审搞
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "REVIEW_REPORT_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_REVIEW_OFFICE);
            //方案设计专家意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "REVIEW_DRAFT_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_EXPERT_OPINIONS);
            //方案设计修编稿
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "REVISION_FILE"),
                    FileCodeEnum.SCHEME_DESIGN_REVISED_DRAFT);
            //方案检查ppt文件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PLAN_CHECK_PPT_FILE_GROUP_ID"),
                    FileCodeEnum.SCHEME_CHECK_PPT_FILE);
            //管理局预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ADMIN_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.ADMINISTRATION_REVIEW_MEETING_COMMITTEE_OPINIONS);
            //规委会预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PLAN_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.PLANNING_COMMITTEE_REVIEW_MEETING_OPINIONS);
            //分管副市长预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "DEPUTY_MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.DEPUTY_MAYOR_REVIEW_MEETING_OPINIONS);
            //分管副市长和市长预审会市委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "MAYOR_COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.MAYOR_REVIEW_MEETING_OPINIONS);
            //市规委会意见
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "COMMITTEE_OPINON_FILE_GROUP_ID"),
                    FileCodeEnum.MUNICIPAL_PLANNING_COMMISSION_OPINIONS);
            //工程规划许可申请材料
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PRJ_PLAN_REQ_FILE"),
                    FileCodeEnum.PROJECT_PLANNING_PERMIT_APPLICATION_MATERIALS);
            //工程规划许可证
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "PRJ_PLAN_REQ_PERMIT_FILE_GROUP_ID"),
                    FileCodeEnum.PROJECT_PLANNING_LICENCE);
            //工程规划许可流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId),
                    FileCodeEnum.PROJECT_PLANNING_PERMIT_PROCESS_ATTACHMENT);

        }

        //采购公开招标
        if ("PO_PUBLIC_BID_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //招标需求附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_DEMAND_FILE_GROUP_ID"),
                    FileCodeEnum.BID_DEMAND_FILE_GROUP);
            //领导审批附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "LEADER_APPROVE_FILE_GROUP_ID"),
                    FileCodeEnum.LEADER_APPROVE_FILE_GROUP);
            //招标文件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_FILE_GROUP_ID"),
                    FileCodeEnum.BID_FILE_GROUP);
            //发标招标文件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_ISSUE_FILE_GROUP_ID"),
                    FileCodeEnum.BID_ISSUE_FILE_GROUP);
            //标后附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_AFTER_FILE_GROUP_ID"),
                    FileCodeEnum.BID_AFTER_FILE_GROUP);
            //中标通知书
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "BID_WIN_NOTICE_FILE_GROUP_ID"),
                    FileCodeEnum.BID_WIN_NOTICE_FILE_GROUP);
            //采购公开招标流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.PURCHASE_TENDER_PROCESS_ATTACHMENT);
        }

        //开工申请
        if ("PM_PRJ_KICK_OFF_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");

            //开工申请附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID"),
                    FileCodeEnum.COMMENCEMENT_APPLICATION_ATTACHMENT);
            //开工申请流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId),
                    FileCodeEnum.COMMENCEMENT_APPLICATION_PROCESS_ATTACHMENT);
        }

        //采购合同补充协议申请
        if ("PO_ORDER_SUPPLEMENT_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //补充协议附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID"), FileCodeEnum.SUPPLEMENTARY_AGREEMENT_ATTACHMENT);
            //采购合同补充协议申请流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.PO_ORDER_SUPPLEMENT_REQ_PROCESS_ATTACHMENT);
        }

        //采购合同变更申请
        if ("PO_ORDER_CHANGE_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //变更附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID"), FileCodeEnum.CHANGE_ATTACHMENTS);
            //采购合同变更申请流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.PO_ORDER_CHANGE_REQ_PROCESS_ATTACHMENT);
        }

        //采购合同终止申请
        if ("PO_ORDER_TERMINATE_REQ".equals(entityCode)) {
            String prjId = JdbcMapUtil.getString(valueMap, "PM_PRJ_ID");
            //合同终止附件
            ProFileUtils.insertProFile(prjId, JdbcMapUtil.getString(valueMap, "ATT_FILE_GROUP_ID"), FileCodeEnum.CONTRACT_TERMINATION_APPENDIX);
            //合同终止申请流程附件
            ProFileUtils.insertProFile(prjId, getProcessFileByProcInstId(procInstId), FileCodeEnum.CONTRACT_TERMINATION_REQ_PROCESS_ATTACHMENT);
        }
    }

    private List<String> getTableList() {
        List<String> list = new ArrayList<>();
        list.add("PM_PRJ_REQ"); //立项申请
        list.add("PM_PRJ_INVEST1"); //可研估算
        list.add("PM_PRJ_INVEST2"); //初设概算
        list.add("PM_PRJ_INVEST3"); //预算财评
        list.add("PM_STABLE_EVAL"); //社会稳定性评价
        list.add("PM_ENERGY_EVAL"); //固定资产投资节能评价
        list.add("PM_WATER_PLAN"); //水保方案
        list.add("PM_ENVIRONMENT_EVAL"); //环评
        list.add("PO_ORDER_REQ"); //采购合同签订申请
        list.add("PO_PUBLIC_BID_REQ"); //采购公开招标申请
        list.add("PM_CONSTRUCT_PERMIT_REQ"); //施工许可
        list.add("PM_PRJ_PLANNING_PERMIT_REQ"); //工程规划许可
        list.add("PO_GUARANTEE_LETTER_REQUIRE_REQ"); //新增保函申请
        list.add("PO_GUARANTEE_LETTER_RETURN_REQ"); //保函退还申请
        list.add("PO_ORDER_SUPPLEMENT_REQ"); //采购合同补充协议申请
        list.add("PO_ORDER_TERMINATE_REQ"); //采购合同终止申请
        list.add("PO_ORDER_CHANGE_REQ"); //采购合同变更申请
        list.add("PM_PRJ_PARTY_REQ"); //五方责任主体维护申请
        list.add("PM_SUPERVISE_PLAN_REQ"); //监理规划及细则申请
        list.add("PM_PRJ_KICK_OFF_REQ"); //工程开工申请
        list.add("PM_FUND_REQUIRE_PLAN_REQ"); //资金需求计划申请
        list.add("PO_ORDER_PAYMENT_REQ"); //采购合同付款申请
        list.add("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD"); //技术交底与图纸会审记录
        list.add("PM_CONCEPTUAL_SCHEME_DESIGN"); //概念方案设计管理
        list.add("PM_CONSTRUCTION_DRAWING_DESIGN"); //施工图设计管理
        list.add("PM_DESIGN_ASSIGNMENT"); //方案设计管理
        list.add("PM_DESIGN_ASSIGNMENT_BOOK"); //设计任务书
        list.add("PM_FARMING_PROCEDURES"); //农转用手续办理
        list.add("PM_WOODLAND_PROCEDURES"); //林地调整办理手续
        return list;
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

    private void getDeptChief(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        String procInstId = ExtJarHelper.procInstId.get();

        Object START_USER_ID = Crud.from("wf_process_instance").where().eq("id", procInstId).select().specifyCols(
                "START_USER_ID").execForValue();

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select d.chief_user_id from hr_dept_user du join " +
                "hr_dept d on du.HR_DEPT_ID=d.id and du.AD_USER_ID=?", START_USER_ID);
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

    private String getProcessFileByProcInstId(String procInstId) {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        Map<String, Object> fileIdMap = jdbcTemplate.queryForMap("select GROUP_CONCAT(t.USER_ATTACHMENT) " +
                "fileIds from wf_process_instance i left join wf_task t on t.WF_PROCESS_INSTANCE_ID = i.id where i.id" +
                " = ? and t.USER_ATTACHMENT is not null", procInstId);
        if (CollectionUtils.isEmpty(fileIdMap)) {
            return null;
        }
        String fileIds = JdbcMapUtil.getString(fileIdMap, "fileIds");
        return fileIds;
    }


    private List<String> getAmtPrjList() {
        List<String> list = new ArrayList<>();
        list.add("PO_ORDER_PAYMENT_REQ"); //采购合同付款申请
        list.add("PM_FUND_REQUIRE_PLAN_REQ"); //资金需求计划申请
        list.add("PM_DESIGN_ASSIGNMENT_BOOK"); //设计任务书
        list.add("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD"); //技术交底与图纸会审记录
        return list;
    }
}
