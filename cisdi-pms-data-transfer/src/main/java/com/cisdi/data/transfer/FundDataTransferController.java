package com.cisdi.data.transfer;

import com.cisdi.data.util.ProFileUtils;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequestMapping("fund")
@RestController()
@Slf4j
public class FundDataTransferController {
    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @Autowired
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;

    @GetMapping("transferFundPlan")
    public String transferFundPlan() {
        //删除原导入数据
        testJdbcTemplate.update("delete from pm_fund_req_plan_detail where CPMS_ID IS NOT NULL");
        testJdbcTemplate.update("delete from pm_fund_req_plan where CPMS_ID IS NOT NULL");
        //用于对比转化的集合
        List<Map<String, Object>> prjMap = testJdbcTemplate.queryForList("select id,CPMS_UUID from pm_prj");
        List<Map<String, Object>> fundTypeList = testJdbcTemplate.queryForList("select v.id,v.code from gr_set_value v left join gr_set t on t.id =" +
                " v.GR_SET_ID where t.CODE = 'psm_money_type'");

        //CPMS库数据
        List<Map<String, Object>> fundPlanList = cpmsJdbcTemplate.queryForList("select * from project_fund_plan where del_flag = '0'");
        for (Map<String, Object> fundPlan : fundPlanList) {
            //添加基本数据得到需求计划表id
            String id = Util.insertData(testJdbcTemplate, "pm_fund_req_plan");
            //更换prjId
            String prjuuid = JdbcMapUtil.getString(fundPlan, "project_id");
            Optional<String> prjIdOp = prjMap.stream()
                    .filter(prj -> prj.get("CPMS_UUID").toString().equals(prjuuid))
                    .map(prj -> prj.get("id").toString())
                    .findAny();
            String prjId = null;
            if (prjIdOp.isPresent()) {
                prjId = prjIdOp.get();
            }
            //关联文件
            List<Map<String, Object>> oldFileIdList = cpmsJdbcTemplate.queryForList("select r.file_id from project_fund_plan a left join " +
                            "project_relevance r on r.relevance_id = a.plan_id where a.plan_id = ?"
                    , fundPlan.get("plan_id"));
            String fileGroupId = getNewFileGroup(oldFileIdList);
            relateFile(fileGroupId, prjId, "FUND_PLAN_FILE");
            //总金额
            BigDecimal totalAmt = cpmsJdbcTemplate.queryForObject("SELECT sum(demand_amount) totalAmt FROM " +
                    "project_fund_details where plan_id = ? group by plan_id", BigDecimal.class, fundPlan.get("plan_id"));

            //插入需求计划表
            String fundPlanSql = "update pm_fund_req_plan set NAME = ?,PM_PRJ_ID = ?,TOTAL_AMT = ?,CPMS_UUID = ?,CPMS_ID = ? " +
                    "where ID = ?";
            testJdbcTemplate.update(fundPlanSql, fundPlan.get("plan_name"), prjId, totalAmt, fundPlan.get("plan_id"),
                    fundPlan.get("id"), id);
            log.info("成功同步一条资金需求计划数据------" + id);

            //插入子表
            List<Map<String, Object>> planDetailList = cpmsJdbcTemplate.queryForList("select * from project_fund_details " +
                    "where plan_id = ? and del_flag = '0'", fundPlan.get("plan_id"));
            for (Map<String, Object> planDetail : planDetailList) {
                String detailId = Util.insertData(testJdbcTemplate, "PM_FUND_REQ_PLAN_DETAIL");
                Optional<String> idOp = fundTypeList.stream()
                        .filter(type -> Objects.equals(type.get("code").toString(), planDetail.get("demand_type").toString()))
                        .map(type -> type.get("id").toString())
                        .findAny();
                String typeId = null;
                if (idOp.isPresent()) {
                    typeId = idOp.get();
                }
                String planDetailSql = "update pm_fund_req_plan_detail set REMARK = ?,CPMS_UUID = ?,CPMS_ID = ?,REQ_AMT = ?,REQ_TIME = ?," +
                        "FUND_REQ_TYPE_ID = ?,PM_FUND_REQ_PLAN_ID = ? where id = ?";
                testJdbcTemplate.update(planDetailSql, planDetail.get("remark"), planDetail.get("details_id"), planDetail.get("id"),
                        planDetail.get("demand_amount"), planDetail.get("demand_time"), typeId, id, detailId);
                log.info("成功同步一条资金需求详情数据------" + detailId);
            }

        }

        return "success";
    }

    @GetMapping("/transferFundSource")
    public String transferFundSource() {
        //删除测试库原导入的数据
        testJdbcTemplate.update("delete from pm_fund_pay where CPMS_ID IS NOT NULL");
        log.info("删除原导入资金支付");
        testJdbcTemplate.update("delete from pm_fund_apportion where CPMS_ID IS NOT NULL");
        log.info("删除原导入资金分配");
        testJdbcTemplate.update("delete from pm_fund_source where CPMS_ID IS NOT NULL");
        log.info("删除原导入资金来源");
        //cpms库资金来源数据
        List<Map<String, Object>> fundSourceList = cpmsJdbcTemplate.queryForList("select * from project_fund_soure where del_flag = '0'");

        //资金来源类型
        List<Map<String, Object>> sourceTypeList = testJdbcTemplate.queryForList("select v.code,v.id from gr_set_value v left join gr_set s on s.id" +
                " = v.GR_SET_ID where s.CODE = 'source_type'");
        //资金需求类型
        List<Map<String, Object>> demandTypeList = testJdbcTemplate.queryForList("select v.code,v.id from gr_set_value v left join gr_set s on s.id" +
                " = v.GR_SET_ID where s.CODE = 'psm_money_type'");
        //项目列表
        List<Map<String, Object>> prjList = testJdbcTemplate.queryForList("select ID,CPMS_UUID from pm_prj");
        for (Map<String, Object> fundSource : fundSourceList) {
            String sourceId = Util.insertData(testJdbcTemplate, "pm_fund_source");
            Optional<String> typeIdOp = sourceTypeList.stream()
                    .filter(sourceType -> sourceType.get("code").toString().equals(fundSource.get("source_type")))
                    .map(sourceType -> sourceType.get("id").toString())
                    .findAny();
            String typeId = null;
            if (typeIdOp.isPresent()) {
                typeId = typeIdOp.get();
            }
            String sourceSql = "update pm_fund_source set NAME = ?,REMARK = ?,FUND_SOURCE_TYPE_ID = ?,IMPL_DATE = ?,IMPL_AMT = ?,CPMS_UUID = ?," +
                    "CPMS_ID = ? where ID = ?";
            //插入来源表
            testJdbcTemplate.update(sourceSql, fundSource.get("capital_source_name"), fundSource.get("remark"), typeId, fundSource.get(
                    "implement_time"), fundSource.get("implement_capital"), fundSource.get("capital_source_id"), fundSource.get("id"), sourceId);
            log.info("同步一条资金来源数据");
            //插入分配表(子表)
            List<Map<String, Object>> apportionList = cpmsJdbcTemplate.queryForList("select * from apportion_capital where capital_source_id = " +
                    "? and del_flag = '0'", fundSource.get("capital_source_id"));
            for (Map<String, Object> apportion : apportionList) {
                String apportionId = Util.insertData(testJdbcTemplate, "pm_fund_apportion");
                Optional<String> prjIdOp = prjList.stream()
                        .filter(prj -> prj.get("CPMS_UUID").toString().equals(apportion.get("project_id")))
                        .map(prj -> prj.get("ID").toString())
                        .findAny();
                String prjId = null;
                if (prjIdOp.isPresent()) {
                    prjId = prjIdOp.get();
                }
                //文件处理
                List<Map<String, Object>> oldFileIdList = cpmsJdbcTemplate.queryForList("select r.file_id from apportion_capital a left join " +
                                "project_relevance r on r.relevance_id = a.apportion_capital_id where a.apportion_capital_id = ?"
                        , apportion.get("apportion_capital_id"));
                String fileGroupId = getNewFileGroup(oldFileIdList);
                relateFile(fileGroupId, prjId, "FUND_APPORTION_FILE");

                //同步数据
                String apportionSql = "update pm_fund_apportion set REMARK = ?,PM_FUND_SOURCE_ID = ?,APPORTION_AMT = ?,APPORTION_DATE = ?,CPMS_UUID" +
                        " = ?,CPMS_ID = ?,PM_PRJ_ID = ?,FUND_SOURCE_AMT = ? where ID = ?";

                testJdbcTemplate.update(apportionSql, apportion.get("remark"), sourceId, apportion.get("apportion_money"), apportion.get(
                        "create_time"), apportion.get("apportion_capital_id"), apportion.get("id"), prjId, fundSource.get("implement_capital"),
                        apportionId);
                log.info("同步一条资金分配数据");
            }

            //插入支付表（子表）
            List<Map<String, Object>> payList = cpmsJdbcTemplate.queryForList("select * from pay_capital where del_flag = '0' and " +
                    "capital_source_id = ?", fundSource.get("capital_source_id"));
            for (Map<String, Object> pay : payList) {
                String payId = Util.insertData(testJdbcTemplate, "pm_fund_pay");
                Optional<String> demandTypeIdOp = demandTypeList.stream()
                        .filter(demandType -> demandType.get("code").toString().equals(pay.get("capital_type").toString()))
                        .map(demandType -> demandType.get("id").toString())
                        .findAny();
                String demandTypeId = null;
                if (demandTypeIdOp.isPresent()) {
                    demandTypeId = demandTypeIdOp.get();
                }
                List<Map<String, Object>> oldFileIdList = cpmsJdbcTemplate.queryForList("select r.file_id from pay_capital c left join " +
                        "project_relevance r on r.relevance_id = c" +
                        ".pay_capital_id where c.pay_capital_id = ?", pay.get("pay_capital_id"));
                String fileGroupId = getNewFileGroup(oldFileIdList);
                Optional<String> prjIdOp = prjList.stream()
                        .filter(prj -> prj.get("CPMS_UUID").toString().equals(pay.get("project_id")))
                        .map(prj -> prj.get("ID").toString())
                        .findAny();
                String prjId = null;
                if (prjIdOp.isPresent()) {
                    prjId = prjIdOp.get();
                }
                //关联文件和文件夹
                relateFile(fileGroupId, prjId, "FUND_PAY_FILE");

                String payeeUnit = "".equals(JdbcMapUtil.getString(pay,"receiver_unit")) ? null : JdbcMapUtil.getString(pay,"receiver_unit");
                String paySql = "update pm_fund_pay set REMARK = ? ,FUND_TYPE = ?,PAY_REMARK = ?, PM_FUND_SOURCE_ID = ?,ATT_FILE_GROUP_ID = ?," +
                        "CPMS_UUID = ?,PM_PRJ_ID = ?,CPMS_ID = ?, PAY_AMT = ?, PAYEE_UNIT = ? where ID = ?";
                testJdbcTemplate.update(paySql, pay.get("remark"), demandTypeId, pay.get("pay_detail"), sourceId, fileGroupId, pay.get(
                        "pay_capital_id"), prjId, pay.get("id"), pay.get("pay_money"), payeeUnit, payId);
                log.info("同步一条资金支付数据");
            }

        }

        return "success";
    }

    //获取新文件组id
    private String getNewFileGroup(List<Map<String, Object>> oldFileIdList) {
        if (CollectionUtils.isEmpty(oldFileIdList)){
            return null;
        }
        List<String> oldFileIds = oldFileIdList.stream()
                .filter(item -> !Objects.isNull(item.get("file_id")))
                .map(item -> item.get("file_id").toString())
                .collect(Collectors.toList());
        String oldFileString = String.join(",", oldFileIds);
        return testJdbcTemplate.queryForObject("select GROUP_CONCAT(FL_FILE_ID) from pf_file where CPMS_UUID in (?)",
                String.class, oldFileString);
    }

    /**
     * 关联文件和文件夹
     *
     * @param fileGroupId 新文件组id fl_file的id
     * @param prjId       新项目id
     * @param code        对应文件夹代码
     */
    public void relateFile(String fileGroupId, String prjId, String code) {
        if (!Strings.isNullOrEmpty(fileGroupId)){
            ProFileUtils.createFolder(prjId,testJdbcTemplate);
            Map<String, Object> folderIdMap = testJdbcTemplate.queryForMap("select id from pf_folder where PM_PRJ_ID = ? and code = ?", prjId, code);
            String folderId = folderIdMap.get("id").toString();
            String[] fileIds = fileGroupId.split(",");
            for (String fileId : fileIds) {
                testJdbcTemplate.update("update pf_file set PF_FOLDER_ID = ? where FL_FILE_ID = ?", folderId, fileId);
                log.info("同步关联一条文件");
            }
        }
    }

}
