package com.cisdi.ext.pm;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 二类费统计
 * @author dlt
 * @date 2023/5/29 周一
 */
public class SecondFeeStatisticExt {
    /**
     * 项目维度统计
     */
    public void statisticByPrj(){
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        HashMap<String, Object> sqlParams = new HashMap<>(input);
        StatisticReq req = JSONObject.parseObject(JSONObject.toJSONString(input), StatisticReq.class);
        String sql = "select tt.prjId,tt.prjName\n" +
                ",IFNULL(slt.lastMonthRequiredAmt,0) secondLastMonthRequiredAmt\n" +
                ",IFNULL(lt.lastMonthRequiredAmt,0) lastMonthRequiredAmt\n" +
                ",nt.thisMonthRequiredAmt,tt.requiredAmt,tt.contractRequiredTotalAmt,tt.sumPayAmt,tt.paidRatio from \n" +
                "-- 总需求资金,和其他基础数据\n" +
                "(select pp.id prjId,pp.name prjName\n" +
                ",SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) requiredAmt\n" +
                ",SUM(IFNULL(oo.AMT_SIX,0)) contractRequiredTotalAmt\n" +
                ",SUM(IFNULL(htemp.sumPayAmt,0)) sumPayAmt\n" +
                ",SUM(IFNULL(htemp.sumPayAmt,0))/SUM(IFNULL(oo.AMT_SIX,0)) paidRatio\n" +
                ",max(dd.SUBMIT_TIME) submitTime\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null\n" +
                "group by fd.PM_PRJ_ID) tt\n" +
                "left join \n" +
                "-- 上月需求资金\n" +
                "(select fd.PM_PRJ_ID prjId,SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) lastMonthRequiredAmt\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tand DATE_FORMAT(dd.SUBMIT_TIME,'%Y-%m') <= DATE_FORMAT(SUBDATE(NOW(),INTERVAL 1 month),'%Y-%m')\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null \n" +
                "group by fd.PM_PRJ_ID) lt on lt.prjId = tt.prjId\n" +
                "left join\n" +
                "-- 上上月\n" +
                "(select fd.PM_PRJ_ID prjId,SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) lastMonthRequiredAmt\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tand DATE_FORMAT(dd.SUBMIT_TIME,'%Y-%m') <= DATE_FORMAT(SUBDATE(NOW(),INTERVAL 2 month),'%Y-%m')\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null \n" +
                "group by fd.PM_PRJ_ID) slt on slt.prjId = tt.prjId\n" +
                "left join\n" +
                "-- 本月需求资金\n" +
                "(select fd.PM_PRJ_ID prjId,SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) thisMonthRequiredAmt\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tand DATE_FORMAT(dd.SUBMIT_TIME,'%Y-%m') <= DATE_FORMAT(NOW(),'%Y-%m')\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null \n" +
                "group by fd.PM_PRJ_ID) nt on nt.prjId = tt.prjId where 1=1";

        if (!CollectionUtils.isEmpty(req.prjIds)){
            sql += " and tt.prjId in (:prjIds)";
        }
        //全部数据
        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sql, sqlParams);
        sql += " order by tt.submitTime limit " + (req.pageIndex - 1) * req.pageSize + " , " + req.pageSize;
        //分页后的数据
        List<Map<String, Object>> pageList = myNamedParameterJdbcTemplate.queryForList(sql, sqlParams);
        for (Map<String, Object> dataMap : pageList) {
            BigDecimal secondLastMonthRequiredAmt = new BigDecimal(dataMap.get("secondLastMonthRequiredAmt").toString());//前两月金额
            BigDecimal lastMonthRequiredAmt = new BigDecimal(dataMap.get("lastMonthRequiredAmt").toString());//上月金额
            BigDecimal thisMonthRequiredAmt = new BigDecimal(dataMap.get("thisMonthRequiredAmt").toString());//本月金额
            //梯次相减
            dataMap.put("thisMonthRequiredAmt",thisMonthRequiredAmt.subtract(lastMonthRequiredAmt));
            dataMap.put("lastMonthRequiredAmt",lastMonthRequiredAmt.subtract(secondLastMonthRequiredAmt));
        }
        List<PrjData> prjDataList = JSONObject.parseArray(JSONObject.toJSONString(pageList), PrjData.class);
        PrjStatisticResp resp = new PrjStatisticResp();
        resp.prjDataList = prjDataList;
        //再次统计
        if (!CollectionUtils.isEmpty(totalList)){
            resp.prjNum = totalList.size();
            resp.total = totalList.size();
            BigDecimal totalContractAmt = new BigDecimal(0);
            for (Map<String, Object> staMap : totalList) {
                resp.totalRequiredAmt = resp.totalRequiredAmt.add(new BigDecimal(staMap.get("requiredAmt").toString()));
                resp.totalPaidAmt = resp.totalPaidAmt.add(new BigDecimal(staMap.get("sumPayAmt").toString()));
                totalContractAmt = totalContractAmt.add(new BigDecimal(staMap.get("contractRequiredTotalAmt").toString()));
            }
            resp.totalPaidRatio = resp.totalPaidAmt.divide(totalContractAmt,4,BigDecimal.ROUND_HALF_UP);
        }

        HashMap<String, Object> result = new HashMap<>();
        result.put("prjStatisticResp",resp);
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 单项目合同维度统计
     */
    public void contractStatistic(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        PrjStatisticReq req = JSONObject.parseObject(JSONObject.toJSONString(input), PrjStatisticReq.class);
        if (Strings.isNullOrEmpty(req.prjId)){
            throw new BaseException("项目id必填");
        }
        Map<String, Object> sqlParams = new HashMap<>(input);
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        StringBuffer sqlSb = new StringBuffer();
        StringBuffer groupSql = new StringBuffer();
        sqlSb.append("select pp.id prjId,pp.name prjName,oo.CONTRACT_NAME contractName,dd.id,dd.LAST_MODI_USER_ID operatorId,u.name operatorName," +
                "u.deptId,d.name deptName,dd.SUBMIT_TIME submitTime,IFNULL(dd.REQUIRED_AMOUNT,0) requiredAmt,IFNULL(oo.AMT_SIX,0) contractAmt," +
                "IFNULL(dd.PAID_AMOUNT,0) paidAmt,IFNULL(dd.PAYMENT_RATIO,0) paymentRatio " +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select u.id,u.name,max(d.id) deptId from ad_user u left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "\t\t\t\t\t\tleft join hr_dept d on d.id = du.HR_DEPT_ID group by u.id) u on u.id = dd.LAST_MODI_USER_ID\n" +
                "left join hr_dept d on d.id = u.deptId\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null");
        groupSql.append("select d.id deptId,d.name deptName,SUM(IFNULL(dd.REQUIRED_AMOUNT,0)) requiredAmt\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select u.id,u.name,max(d.id) deptId from ad_user u left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "\t\t\t\t\t\tleft join hr_dept d on d.id = du.HR_DEPT_ID group by u.id) u on u.id = dd.LAST_MODI_USER_ID\n" +
                "left join hr_dept d on d.id = u.deptId\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null\n");
        sqlSb.append(" and fd.PM_PRJ_ID = :prjId");
        groupSql.append(" and fd.PM_PRJ_ID = :prjId");
        if (!Strings.isNullOrEmpty(req.contractName)){
            sqlSb.append(" and oo.CONTRACT_NAME like :contractName");
            groupSql.append(" and oo.CONTRACT_NAME like :contractName");
            req.contractName = "%" + req.contractName + "%";
            sqlParams.put("contractName",req.contractName);
        }
        if (!CollectionUtils.isEmpty(req.deptIds)){
            sqlSb.append(" and d.id in (:deptIds)");
            groupSql.append(" and d.id in (:deptIds)");
        }
        if (!CollectionUtils.isEmpty(req.operatorIds)){
            sqlSb.append(" and dd.LAST_MODI_USER_ID in (:operatorIds)");
            groupSql.append(" and dd.LAST_MODI_USER_ID in (:operatorIds)");
        }
        if (!Strings.isNullOrEmpty(req.startDate)){
            sqlSb.append(" and dd.SUBMIT_TIME >= :startDate");
            groupSql.append(" and dd.SUBMIT_TIME >= :startDate");
        }
        if (!Strings.isNullOrEmpty(req.endDate)){
            sqlSb.append( " and dd.SUBMIT_TIME <= :endDate");
            groupSql.append( " and dd.SUBMIT_TIME <= :endDate");
        }
        groupSql.append(" group by d.id");
        ContractStatisticResp resp = new ContractStatisticResp();
        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        List<Map<String, Object>> pageList = totalList.stream().limit((req.pageIndex - 1) * req.pageSize).skip(req.pageSize).collect(Collectors.toList());
        List<ContractData> contractDataList = JSONObject.parseArray(JSONObject.toJSONString(pageList), ContractData.class);
        //统计
        BigDecimal totalContractAmt = new BigDecimal(0);
        for (Map<String, Object> data : totalList) {
            resp.totalRequiredAmt = resp.totalRequiredAmt.add(JdbcMapUtil.getBigDecimal(data,"requiredAmt"));
            resp.totalPaidAmt = resp.totalPaidAmt.add(JdbcMapUtil.getBigDecimal(data,"paidAmt"));
            totalContractAmt = totalContractAmt.add(JdbcMapUtil.getBigDecimal(data,"contractAmt"));
        }
        //分部门统计
        List<Map<String, Object>> deptStatisticList = myNamedParameterJdbcTemplate.queryForList(groupSql.toString(), sqlParams);
        resp.deptStatisticList = deptStatisticList;
        Map<String, Object> prjMap = myJdbcTemplate.queryForMap("select name from pm_prj where id = ?", req.prjId);
        resp.prjName = JdbcMapUtil.getString(prjMap,"name");
        resp.paymentRatio = totalContractAmt.compareTo(BigDecimal.ZERO) == 0 ? BigDecimal.ZERO : resp.totalPaidAmt.divide(totalContractAmt,4, RoundingMode.HALF_UP);
        resp.total = totalList.size();
        resp.contractDataList = contractDataList;

        //返回
        Map output = JsonUtil.fromJson(JsonUtil.toJson(resp), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 部门维度月度统计
     */
    public void deptStatistic(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Date now = new Date();
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        int year = JdbcMapUtil.getInt(input, "year") == null ? Integer.valueOf(DateUtil.year(now)) : JdbcMapUtil.getInt(input, "year");
        SimpleDateFormat monthDf = new SimpleDateFormat("MM");
        //今年的月份取现在之前，往年的月月份取12
        int month = year == Integer.valueOf(DateUtil.year(now)) ? Integer.parseInt(monthDf.format(now)) : 12;

        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select fd.PM_PRJ_ID prjId,pp.name prjName,fd.PO_ORDER_REQ_ID contractId,oo.CONTRACT_NAME contractName,d.id deptId,d.name " +
                "deptName,dd.REQUIRED_AMOUNT requiredAmt,dd.SUBMIT_TIME submitTime,va.SEQ_NO seqNo\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select u.id,u.name,max(d.id) deptId from ad_user u left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "\t\t\t\t\t\tleft join hr_dept d on d.id = du.HR_DEPT_ID group by u.id) u on u.id = dd.LAST_MODI_USER_ID\n" +
                "left join hr_dept d on d.id = u.deptId");

        sqlSb.append(" where YEAR(dd.SUBMIT_TIME) <= ?");
        //原始数据
        List<Map<String, Object>> originList = myJdbcTemplate.queryForList(sqlSb.toString(),year);
        //所有相关的部门
        Map<String, String> deptMap = originList.stream().collect(Collectors.toMap(map -> map.get("deptId").toString(), map -> map.get("deptName").toString(),(ov,nv) -> nv));

        List<Map<String, Object>> sheetList = new ArrayList<>();
        List<DeptAmt> deptSumList = new ArrayList<>();

        for (String deptId : deptMap.keySet()) {
            Map<String, Object> rowCells = new HashMap<>();//一行的单元格
            Map<Integer, BigDecimal> tempRowCells = new HashMap<>();//临时一行单元格，存储每月之前的数据总和，方便相减求当月
            rowCells.put("需求部门",deptMap.get(deptId));
            rowCells.put("deptId",deptId);
            DeptAmt deptAmt = new DeptAmt();//同时统计部门汇总数据
            deptAmt.deptId = deptId;
            deptAmt.deptName = deptMap.get(deptId);
            //该部门相关的数据
            List<Map<String, Object>> deptData = originList.stream().filter(map -> map.get("deptId").toString().equals(deptId)).collect(Collectors.toList());
            //去年12月的
            BigDecimal lastDecSumAmt = this.getSumAmt(year - 1, deptData, 12);
            tempRowCells.put(0, lastDecSumAmt);
            for (int m = 1; m <= month; m++) {
                //筛选出该月相关的
                BigDecimal sumAmt = this.getSumAmt(year, deptData, m);
                if (sumAmt.compareTo(BigDecimal.ZERO) == 0) {
                    rowCells.put(m + "月",BigDecimal.ZERO);
                    tempRowCells.put(m, BigDecimal.ZERO);
                    continue;//没有数据，内部已赋0，下一个
                }
                //有数据，减前一月汇总数据，记录该月汇总数据，汇总部门数据
                tempRowCells.put(m, sumAmt);
                BigDecimal thisMonthAmt = sumAmt.subtract(tempRowCells.get(m - 1));
                rowCells.put(m + "月",thisMonthAmt);
                deptAmt.sumAmt = deptAmt.sumAmt.add(thisMonthAmt);
            }
            sheetList.add(rowCells);
            deptSumList.add(deptAmt);
        }

        //合计
        Map<String, Object> sumCells = new HashMap<>();//一行的单元格，合计
        sumCells.put("需求部门","合计");
        for (int i = 1; i <= month; i++) {
            BigDecimal sumValue = BigDecimal.ZERO;
            for (Map<String, Object> deptCells : sheetList) {
                BigDecimal sheetValue = (BigDecimal) deptCells.get(i + "月");
                sumValue = sumValue.add(sheetValue);
            }
            sumCells.put(i + "月",sumValue);
        }
        sheetList.add(sumCells);

        //总数统计
        Map<String, Object> sumMap = myJdbcTemplate.queryForMap("select \n" +
                "IFNULL(SUM(IFNULL(oo.AMT_SIX,0)),0) contractRequiredTotalAmt\n" +
                ",IFNULL(SUM(IFNULL(htemp.sumPayAmt,0)),0) sumPayAmt\n" +
                ",IFNULL(SUM(IFNULL(htemp.sumPayAmt,0))/SUM(IFNULL(oo.AMT_SIX,0)),0) paidRatio\n" +
                "from second_category_fee_demand fd\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "where YEAR(fd.CRT_DT) = ?", year);
        BigDecimal sumRequiredAmt = deptSumList.stream().map(deptAmt -> deptAmt.sumAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
        sumMap.put("sumRequiredAmt",sumRequiredAmt);


        //封装返回
        Map<String, Object> result = new HashMap<>();
        result.put("sheetList",sheetList);
        result.put("deptSumList",deptSumList);
        result.put("sumMap",sumMap);
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    private BigDecimal getSumAmt(int year, List<Map<String, Object>> deptData, int m) {
        final int i = m;
        List<Map<String, Object>> monthData = deptData.stream()
                .filter(map -> JdbcMapUtil.getString(map,"submitTime") != null
                        //日期小于等于该月份的
                        && JdbcMapUtil.getString(map,"submitTime").substring(0, 7).compareTo(year + "-" + String.format("%0" + 2 + "d",i)) < 1)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(monthData)){//没有该月数据
            return BigDecimal.ZERO;
        }
        //有该月数据，根据合同分组
        Map<String, List<Map<String, Object>>> contractMap = monthData.stream().collect(Collectors.groupingBy(item -> item.get("contractId").toString()));
        BigDecimal sumAmt = BigDecimal.ZERO;
        for (String k : contractMap.keySet()) {
            List<Map<String, Object>> singleContractData = contractMap.get(k);//单个合同 对应的多条填报记录
            //单个合同对应的多个填报记录，经过月份、部门筛选后仍剩多个记录，过滤后取节点优先级最大的
            Map<String, Object> resultDtl = singleContractData.stream().max(Comparator.comparingDouble(dtl -> Double.parseDouble(dtl.get("seqNo").toString()))).get();
            sumAmt = sumAmt.add(new BigDecimal(resultDtl.get("requiredAmt").toString()));
        }
        return sumAmt;
    }

    /**
     * 部门合同需求
     */
    public void getDeptContractDemand(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        Map<String, Object> sqlParams = new HashMap<>(input);
        DeptContractReq req = JSONObject.parseObject(JSONObject.toJSONString(input), DeptContractReq.class);
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        StringBuffer sqlSb = new StringBuffer();
        sqlSb.append("select pp.id prjId,pp.name prjName,oo.CONTRACT_NAME contractName,d.id deptId,d.name deptName,u.id operatorId,u.name operatorName,dd" +
                ".SUBMIT_TIME submitTime,IFNULL(dd.APPROVED_AMOUNT,0) approvedAmt,IFNULL(oo.AMT_SIX,0) contractAmt,IFNULL(htemp.sumPayAmt,0) " +
                "paidAmt,IFNULL(IFNULL(htemp.sumPayAmt,0)/IFNULL(oo.AMT_SIX,0),0) paidRatio\n" +
                "from second_category_fee_demand fd\n" +
                "left join fee_demand_dtl dd on dd.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id\n" +
                "left join pm_prj pp on pp.id = fd.PM_PRJ_ID\n" +
                "left join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "left join (select dd.SECOND_CATEGORY_FEE_DEMAND_ID,max(SEQ_NO) maxSeqNo from fee_demand_dtl dd \n" +
                "\t\t\t\t\t\tleft join gr_set_value va on va.id = dd.FEE_DEMAND_NODE\n" +
                "\t\t\t\t\t\twhere dd.SUBMIT_TIME is not null\n" +
                "\t\t\t\t\t\tgroup by dd.SECOND_CATEGORY_FEE_DEMAND_ID\n" +
                "\t\t\t\t\t) dtemp on dtemp.SECOND_CATEGORY_FEE_DEMAND_ID = fd.id and dtemp.maxSeqNo = va.SEQ_NO\n" +
                "left join po_order oo on oo.CONTRACT_APP_ID = fd.PO_ORDER_REQ_ID and oo.PM_PRJ_ID = fd.PM_PRJ_ID\n" +
                "left join (select PO_ORDER_ID,sum(PAY_AMT) sumPayAmt from contract_pay_history ph group by PO_ORDER_ID) htemp on htemp.PO_ORDER_ID" +
                " = oo.id\n" +
                "left join (select u.id,u.name,max(d.id) deptId from ad_user u left join hr_dept_user du on du.AD_USER_ID = u.id\n" +
                "\t\t\t\t\t\tleft join hr_dept d on d.id = du.HR_DEPT_ID group by u.id) u on u.id = dd.LAST_MODI_USER_ID\n" +
                "left join hr_dept d on d.id = u.deptId\n" +
                "where dtemp.SECOND_CATEGORY_FEE_DEMAND_ID is not null");
        if (!CollectionUtils.isEmpty(req.deptIds)){
            sqlSb.append(" and d.id in (:deptId)");
        }
        if (!Strings.isNullOrEmpty(req.prjName)){
            sqlParams.put("prjName","%" + sqlParams.get("prjName") + "%");
            sqlSb.append(" and pp.name like :prjName");
        }
        if (!Strings.isNullOrEmpty(req.contractName)){
            sqlParams.put("contractName","%" + sqlParams.get("contractName") + "%");
            sqlSb.append( " and oo.CONTRACT_NAME like :contractName");
        }
        if (!Strings.isNullOrEmpty(req.startDate)){
            sqlSb.append(" and dd.SUBMIT_TIME <= :startDate");
        }
        if (!Strings.isNullOrEmpty(req.endDate)){
            sqlSb.append(" and dd.SUBMIT_TIME <= :endDate");
        }
        if (!Strings.isNullOrEmpty(req.yearMonth)){
            sqlSb.append( " and DATE_FORMAT(dd.SUBMIT_TIME,'%Y-%m') = :yearMonth");
        }
        List<Map<String, Object>> totalList = myNamedParameterJdbcTemplate.queryForList(sqlSb.toString(), sqlParams);
        List<DeptContract> totalDeptContracts = JSONObject.parseArray(JSONObject.toJSONString(totalList), DeptContract.class);//转对象

        DeptContractResp resp = new DeptContractResp();//待返回的对象
        resp.date = req.yearMonth;//年月
        if (!CollectionUtils.isEmpty(totalDeptContracts)){//头部汇总
            for (DeptContract contract : totalDeptContracts) {
                resp.sumRequiredAmt = resp.sumRequiredAmt.add(contract.requiredAmt);//资金需求总金额
                resp.sumPaidAmt = resp.sumPaidAmt.add(contract.paidAmt);//已支付总金额
                resp.sumPaidRatio = resp.sumPaidRatio.add(contract.paidRatio);//支付比例
            }
        }

        //部门汇总
        Map<String, List<DeptContract>> deptIdMap = totalDeptContracts.stream().collect(Collectors.groupingBy(contract -> contract.deptId));
        List<DeptAmt> deptAmts = new ArrayList<>();
        for (String deptId : deptIdMap.keySet()) {
            DeptAmt deptAmt = new DeptAmt();
            List<DeptContract> deptContracts = deptIdMap.get(deptId);
            BigDecimal requiredAmt = deptContracts.stream().map(deptContract -> deptContract.requiredAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
            deptAmt.sumAmt = requiredAmt;
            deptAmt.deptName = deptContracts.get(0).deptName;
            deptAmt.deptId = deptId;
            deptAmts.add(deptAmt);
        }

        //返回
        Map output = JsonUtil.fromJson(JsonUtil.toJson(resp), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 部门需求总金额
     */
    @Data
    private static class DeptAmt{
        private String deptId;
        private String deptName;
        private BigDecimal sumAmt = BigDecimal.ZERO;
    }

    /**
     * 项目维度统计请求
     */
    @Data
    private static class StatisticReq{
        //项目id
        private List<String> prjIds;
        //分页
        private Integer pageIndex;
        private Integer pageSize;
    }

    /**
     * 项目维度统计响应
     */
    @Data
    private static class PrjStatisticResp{
        //项目个数
        private Integer prjNum;
        //资金需求总额
        private BigDecimal totalRequiredAmt;
        //已支付总金额
        private BigDecimal totalPaidAmt;
        //总合同支付占比
        private BigDecimal totalPaidRatio;
        //列表
        private List<PrjData> prjDataList;
        //总条数
        private Integer total;
        private PrjStatisticResp(){
            this.totalRequiredAmt = new BigDecimal(0);
            this.totalPaidAmt = new BigDecimal(0);
            this.totalPaidRatio = new BigDecimal(0);
        }
    }

    /**
     * 项目维度统计数据
     */
    @Data
    private static class PrjData{
        //项目
        private String prjId;
        private String prjName;
        //上月需求金额
        private BigDecimal lastMonthRequiredAmt;
        //本月需求金额
        private BigDecimal thisMonthRequiredAmt;
        //需求总金额
        private BigDecimal requiredAmt;
        //需求合同总金额
        private BigDecimal contractRequiredTotalAmt;
        //已支付总金额
        private BigDecimal sumPayAmt;
        //合同支付占比
        private BigDecimal paidRatio;
    }

    /**
     * 单项目合同维度统计请求
     */
    @Data
    private static class PrjStatisticReq{
        //项目id
        private String prjId;
        //合同名称
        private String contractName;
        //需求部门ids
        private List<String> deptIds;
        //经办人ids
        private List<String> operatorIds;
        //开始日期
        private String startDate;
        //结束日期
        private String endDate;
        //分页
        private Integer pageSize;
        private Integer pageIndex;
    }

    /**
     * 单项目合同维度统计响应
     */
    @Data
    private static class ContractStatisticResp{
        //项目名称
        private String prjName;
        //部门统计
        private List<Map<String, Object>> deptStatisticList;
        //资金需求总金额
        private BigDecimal totalRequiredAmt = new BigDecimal(0);
        //已支付总金额
        private BigDecimal totalPaidAmt = new BigDecimal(0);
        //合同支付占比
        private BigDecimal paymentRatio = new BigDecimal(0);
        //列表
        private List<ContractData> contractDataList;
        //总条数
        private Integer total;
    }

    /**
     * 合同数据
     */
    @Data
    private static class ContractData{
        //合同
        private String contractName;
        //需求部门
        private String deptName;
        //经办人
        private String operatorName;
        //需求提出时间
        private String submitTime;
        //资金需求金额
        private BigDecimal requiredAmt;
        //资金需求合同金额
        private BigDecimal contractAmt;
        //已支付总金额
        private BigDecimal paidAmt;
        //支付占比
        private BigDecimal paymentRatio;

        public String getSubmitTime() {
            this.submitTime = StringUtil.withOutT(this.submitTime);
            return submitTime;
        }
    }

    /**
     * 部门合同请求
     */
    @Data
    private static class DeptContractReq{
        //项目名称
        private String prjName;
        //合同名称
        private String contractName;
        //需求部门
        private List<String> deptIds;
        //经办人
        private List<String> operatorIds;
        //年月
        private String yearMonth;
        //开始日期
        private String startDate;
        //结束日期
        private String endDate;
        //分页
        private Integer pageIndex;
        private Integer pageSize;
    }

    @Data
    private static class DeptContractResp{
        //日期
        private String date;
        //资金需求总金额
        private BigDecimal sumRequiredAmt;
        //已支付总金额
        private BigDecimal sumPaidAmt;
        //合同支付占比
        private BigDecimal sumPaidRatio;
        //部门统计
        private List<DeptAmt> deptAmts;
    }

    @Data
    private static class DeptContract{
        //项目
        private String prjId;
        private String prjName;
        //合同
        private String contractName;
        //需求部门
        private String deptId;
        private String deptName;
        //经办人
        private String operatorId;
        private String operatorName;
        //需求提出时间
        private String submitTime;
        //资金需求金额
        private BigDecimal requiredAmt;
        //需求资金合同金额
        private BigDecimal contractAmt;
        //已支付总金额
        private BigDecimal paidAmt;
        //合同支付占比
        private BigDecimal paidRatio;

    }

    public static void main(String[] args) {
//        System.out.println("2".compareTo("1"));
//        System.out.println(String.format("%0" + 2 + "d",1));
        HashMap<Integer, String> testMap = new HashMap<>();
        testMap.put(0,"0");
        testMap.put(1,"1");
        System.out.println();

    }
}
