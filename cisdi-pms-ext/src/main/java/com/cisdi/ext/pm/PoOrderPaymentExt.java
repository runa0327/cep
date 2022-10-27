package com.cisdi.ext.pm;


import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 采购合同付款申请 流程扩展
 */
@Slf4j
public class PoOrderPaymentExt {

    /**
     * 采购合同付款 发起申请扩展
     */
    public void CreateProcess() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 流程id
        String csCommId = entityRecord.csCommId;
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 项目id
        String projectId = entityRecord.valueMap.get("AMOUT_PM_PRJ_ID").toString();
        //合同id
        String contractId = entityRecord.valueMap.get("CONTRACT_ID").toString();
        // 获取付款依据
        String payBasisId = entityRecord.valueMap.get("PAY_BASIS_ID").toString();
        if ("99902212142031993".equals(payBasisId)) {
            // 获取附件信息
            String fileId = JdbcMapUtil.getString(entityRecord.valueMap, "ATT_FILE_GROUP_ID");
            if (SharedUtil.isEmptyString(fileId) || "0".equals(fileId)) {
                throw new BaseException("付款依据选择“其他付款”时，请上传对应附件信息！");
            }
        }
        // 获取部门信息
        String sql1 = "SELECT b.NAME FROM hr_dept_user a LEFT JOIN hr_dept b on a.HR_DEPT_ID = b.id WHERE a.AD_USER_ID = ?";
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql1, userId);
        if (CollectionUtils.isEmpty(list1)) {
            throw new BaseException("当前用户没有部门信息，不允许进行操作！");
        }
        String deptName = list1.get(0).get("NAME").toString();

        // 查询 至上期累计付款金额
        String sql2 = "SELECT IFNULL(SUM(STAGE_PAY_AMT_TWO),0) as amount FROM PO_ORDER_PAYMENT_REQ WHERE AMOUT_PM_PRJ_ID = ? and status = 'AP'";
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sql2, projectId);
        BigDecimal lastAmount = new BigDecimal(0);
        if (!CollectionUtils.isEmpty(list2)) {
            lastAmount = new BigDecimal(list2.get(0).get("amount").toString());
        }

        //预付款
//        String yuFu = JdbcMapUtil.getString(entityRecord.valueMap,"BUDGET_AMT");
        //当前期数
//        String qiShu = JdbcMapUtil.getString(entityRecord.valueMap,"NOW_STAGE_ID");
//        if (!SharedUtil.isEmptyString(yuFu) && !SharedUtil.isEmptyString(qiShu)){
//            throw new BaseException("预付款和进度款只能选择一个");
//        }
        //预付款和进度款都为空，默认为查询进度款
//        if (SharedUtil.isEmptyString(yuFu) && SharedUtil.isEmptyString(qiShu)){
//            throw new BaseException("预付款和进度款必须选择一个");
//        }


        // 本期支付金额
        String nowAmount = entityRecord.valueMap.get("STAGE_PAY_AMT_TWO").toString();
        Double amount = Double.valueOf(nowAmount);
        if (SharedUtil.isEmptyString(nowAmount) || amount <= 0) {
            throw new BaseException("本期支付金额不能为空且必须大于0");
        }
        BigDecimal maxAmount = lastAmount.add(new BigDecimal(amount));

        // 获取合同总金额
        BigDecimal contractAmt = new BigDecimal(entityRecord.valueMap.get("CONTRACT_PRICE").toString());
        // 累计合同付款占比
        BigDecimal contractPre = maxAmount.divide(contractAmt, 2, BigDecimal.ROUND_HALF_UP);
        // 因采购招标处当前缺乏明细细心，以下三个数据无法取数暂为0
        // 累计概算付款占比  累计财评付款占比 累计结算付款占比
        BigDecimal ESTIMATE_PERCENT = new BigDecimal(0);
        BigDecimal FINANCIAL_PERCENT = new BigDecimal(0);
        BigDecimal SETTLEMENT_PERCENT = new BigDecimal(0);

        //查询明细信息
        String sql3 = "select PAY_AMT_ONE from PM_PAY_COST_DETAIL where PARENT_ID = ?";
        List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql3,csCommId);
        if (CollectionUtils.isEmpty(list3)){
            throw new BaseException("费用明细信息不能为空");
        }
        BigDecimal account = getSumAmt(list3,"PAY_AMT_ONE");

        // 信息更新付款申请表
        String updateSql = "update PO_ORDER_PAYMENT_REQ set DEPT_NAME = ?,LAST_SUM_PAY=?,NOW_SUM_PAY=?,CONTRACT_PERCENT=?,ESTIMATE_PERCENT=?,FINANCIAL_PERCENT=?,SETTLEMENT_PERCENT=?,PAY_AMT=? where id = ?";
        int num = myJdbcTemplate.update(updateSql, deptName, lastAmount, maxAmount, contractPre, ESTIMATE_PERCENT, FINANCIAL_PERCENT, SETTLEMENT_PERCENT, account,csCommId);
    }

    // 汇总求和
    private BigDecimal getSumAmt(List<Map<String, Object>> list,String str) {
        BigDecimal sum = new BigDecimal(0);
        for (Map<String, Object> tmp : list) {
            String value = tmp.get(str).toString();
            sum = sum.add(new BigDecimal(value));
        }
        return sum;
    }

    /**
     * 按先后顺序审批 第一次审批
     */
    public void checkFirst() {
        String newStatus = "first";
        check(newStatus);
    }

    /**
     * 按先后顺序审批 第二次审批
     */
    public void checkSecond() {
        String newStatus = "second";
        check(newStatus);
    }

    /**
     * 按先后顺序审批 第三次审批
     */
    public void checkThird() {
        String newStatus = "third";
        check(newStatus);
    }

    /**
     * 按先后顺序审批 第四次审批
     */
    public void checkFourth() {
        String newStatus = "fourth";
        check(newStatus);
    }

    /**
     * 按先后顺序审批 第五次审批
     */
    public void checkFifth() {
        String newStatus = "fifth";
        check(newStatus);
    }

    /**
     * 按先后顺序审批 第六次审批
     */
    public void checkSixth() {
        String newStatus = "sixth";
        check(newStatus);
    }

    /**
     * 按先后顺序审批 第七次审批
     */
    public void checkSeventh() {
        String newStatus = "seventh";
        check(newStatus);
    }

    private void check(String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date date = new Date();
        String now = DateTimeUtil.dateToString(date);
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userName;

        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String csCommId = entityRecord.csCommId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 审批意见
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select u.id u_id,u.code u_code,u.name u_name,tk.user_comment from wf_node_instance ni join wf_task tk on ni.wf_process_instance_id=? and ni.is_current=1 and ni.id=tk.wf_node_instance_id join ad_user u on tk.ad_user_id=u.id", procInstId);
        StringBuffer comment = new StringBuffer();
        if (!CollectionUtils.isEmpty(list)) {
            for (Map<String, Object> tmp : list) {
                String txt = JdbcMapUtil.getString(tmp,"user_comment");
                if (!SharedUtil.isEmptyString(txt)){
                    comment = comment.append(JdbcMapUtil.getString(tmp,"u_name")).append("： ").append(txt).append("; ");
                }

            }
        }
        if ("first".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_PAYMENT_REQ").where().eq("ID", csCommId).update()
                    .set("COMMENT_PUBLISH_USER", userId).set("COMMENT_PUBLISH_DATE", now).set("COMMENT_PUBLISH_CONTENT", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("second".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_PAYMENT_REQ").where().eq("ID", csCommId).update()
                    .set("DEPT_COMMENT_PUBLISH_USER", userId).set("DEPT_COMMENT_PUBLISH_DATE", now).set("DEPT_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("third".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_PAYMENT_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_PUBLISH_USER", userId).set("FINANCE_PUBLISH_DATE", now).set("FINANCE_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fourth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_PAYMENT_REQ").where().eq("ID", csCommId).update()
                    .set("LEADER_COMMENT_PUBLISH_USER", userId).set("LEADER_COMMENT_PUBLISH_DATE", now).set("LEADER_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("fifth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_PAYMENT_REQ").where().eq("ID", csCommId).update()
                    .set("FINANCE_LEADER_PUBLISH_USER", userId).set("FINANCE_LEADER_PUBLISH_DATE", now).set("FINANCE_LEADER_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("sixth".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_PAYMENT_REQ").where().eq("ID", csCommId).update()
                    .set("PRESIDENT", userId).set("PRESIDENT_DATE", now).set("PRESIDENT_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        } else if ("seventh".equals(status)) {
            Integer exec = Crud.from("PO_ORDER_PAYMENT_REQ").where().eq("ID", csCommId).update()
                    .set("CHAIRMAN", userId).set("CHAIRMAN_DATE", now).set("CHAIRMAN_MESSAGE", comment).exec();
            log.info("已更新：{}", exec);
        }
    }

    /**
     * 采购合同付款申请-审批通过-同步汇总更新付款明细信息
     */
    public void syncPayDetail(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        // 流程id
        String csCommId = entityRecord.csCommId;
        // 当前登录人
        String userId = ExtJarHelper.loginInfo.get().userId;
        // 流程id
        String procInstId = ExtJarHelper.procInstId.get();
        // 项目id
        String projectId = entityRecord.valueMap.get("AMOUT_PM_PRJ_ID").toString();
        //合同id
        String contractId = entityRecord.valueMap.get("CONTRACT_ID").toString();
        //本期付款金额
        String payAmt = entityRecord.valueMap.get("PAY_AMT").toString();
        //合同金额
        String contractAmt = entityRecord.valueMap.get("CONTRACT_PRICE").toString();

        //本期以前已支付金额
        //查询明细信息
        String sql3 = "select PAY_AMT_ONE,PAY_AMT_TWO from PM_PAY_COST_DETAIL where PARENT_ID = ?";
        List<Map<String,Object>> list3 = myJdbcTemplate.queryForList(sql3,csCommId);
        if (CollectionUtils.isEmpty(list3)){
            throw new BaseException("费用明细信息不能为空");
        }
        BigDecimal account = getSumAmt(list3,"PAY_AMT_TWO");
        //截止本期已支付金额
        BigDecimal payAmtNow = account.add(new BigDecimal(payAmt));

        //剩余可支付
        BigDecimal amt = new BigDecimal(contractAmt).subtract(payAmtNow);

        //写入付款情况数据表
        String sql = "insert into PO_ORDER_PAYMENT (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,CONTRACT_ID,PAY_AMT,PAY_DATE,AMT,STAGE_PAY_AMT_TWO,PM_PRJ_ID)" +
                "values((select UUID_SHORT()),'1',now(),now(),?,now(),?,'AP',?,?,now(),?,?,?)";
        myJdbcTemplate.update(sql,userId,userId,contractId,payAmt,amt,payAmtNow,projectId);
    }
}
