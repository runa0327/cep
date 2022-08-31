package com.cisdi.data.transfer;

import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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
                " v" +
                ".GR_SET_ID where t.CODE = 'psm_money_type'");

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
            if (prjIdOp.isPresent()){
                prjId = prjIdOp.get();
            }

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
}
