package com.cisdi.ext.fundManage;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.view.file.File;
import com.cisdi.ext.model.view.ImplementFund;
import com.cisdi.ext.util.EntityUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class FundPay {
    // 资金来源支付列表
    public void getSourcePayList() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String inputJson = JsonUtil.toJson(inputMap);
        Input input = JsonUtil.fromJson(inputJson, Input.class);

        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select s.id sourceId,s.name fundSourceName,s.IMPL_AMT imptAmt,sum(a.APPORTION_AMT) " +
                "apportionAmt,sum(y.PAY_AMT) payAmt,v.name sourceTypeName,s.IMPL_DATE date,s.remark from " +
                "pm_fund_source s left join pm_fund_pay y on s.id = y.PM_FUND_SOURCE_ID left join pm_fund_apportion a" +
                " on a.PM_FUND_SOURCE_ID = s.id left join gr_set_value v on v.id = s.FUND_SOURCE_TYPE_ID left join " +
                "gr_set t on t.id = v.GR_SET_ID and t.code = 'source_type' where 1=1 ");
        if (!Strings.isNullOrEmpty(input.fundSourceId)) {
            baseSql.append("and s.id = '" + input.fundSourceId + "' ");
        }
        if (!Strings.isNullOrEmpty(input.startDate) && !Strings.isNullOrEmpty(input.endDate)) {
            baseSql.append("and s.IMPL_DATE BETWEEN '" + input.startDate + "' and '" + input.endDate + "' ");
        }
        baseSql.append("group by s.id ");
        // 除开翻页查询获取总条数
        String totalSql = baseSql.toString();
        baseSql.append("order by s.IMPL_DATE desc ");

        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql.append("limit " + start + "," + input.pageSize);


        log.info(baseSql.toString());
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList(baseSql.toString());
        List<FundPayResp> fundPayRespList = new ArrayList<>();
        for (Map<String, Object> result : resultList) {
            FundPayResp fundPayResp = new FundPayResp();
            fundPayResp.sourceId = JdbcMapUtil.getString(result, "sourceId");
            fundPayResp.fundSourceName = JdbcMapUtil.getString(result, "fundSourceName");
            fundPayResp.imptAmt = Double.parseDouble(JdbcMapUtil.getString(result, "imptAmt"));
            fundPayResp.apportionAmt = Double.parseDouble(JdbcMapUtil.getString(result, "apportionAmt"));
            fundPayResp.payAmt = Double.parseDouble(JdbcMapUtil.getString(result, "payAmt"));
            fundPayResp.sourceTypeName = JdbcMapUtil.getString(result, "sourceTypeName");
            fundPayResp.date = JdbcMapUtil.getString(result, "date");
            fundPayResp.remark = JdbcMapUtil.getString(result, "remark");
            fundPayRespList.add(fundPayResp);
        }
        List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
        HashMap<String, Object> result = new HashMap<>();
        result.put("resultList", fundPayRespList);
        result.put("total", totalList.size());

        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    // 资金落实及支付详情
    public void getFundPayDetail() {
        Map<String, Object> idMap = ExtJarHelper.extApiParamMap.get();
        String id = idMap.get("id").toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> infoMap = myJdbcTemplate.queryForMap("select s.id sourceId,s.name fundSourceName,s.IMPL_AMT" +
                " imptAmt,sum(a.APPORTION_AMT) apportionAmt,sum(y.PAY_AMT) payAmt,v.name sourceTypeName,s.IMPL_DATE " +
                "date,s.remark,s.ATT_FILE_GROUP_ID fileIds from pm_fund_source s left join pm_fund_pay y on s.id = y.PM_FUND_SOURCE_ID left join " +
                "pm_fund_apportion a on a.PM_FUND_SOURCE_ID = s.id left join gr_set_value v on v.id = s" +
                ".FUND_SOURCE_TYPE_ID left join gr_set t on t.id = v.GR_SET_ID and t.code = 'source_type' where s" +
                ".id=?", id);
        String fileIds = JdbcMapUtil.getString(infoMap, "fileIds");
        List<File> fileList = FileCommon.getFileResp(fileIds, myJdbcTemplate);
        infoMap.put("fileList", fileList);

        List<Map<String, Object>> payList = myJdbcTemplate.queryForList("select s.id source_Id,p.id prj_Id,p.name " +
                "prj_Name,sum(a.APPORTION_AMT) apportion_Amt,y.PAY_AMT pay_Amt " +
                "from pm_fund_source s " +
                "left join pm_fund_apportion a on a.PM_FUND_SOURCE_ID = s.id " +
                "left join pm_prj p on p.id = a.PM_PRJ_ID " +
                "left join pm_fund_pay y on y.PM_FUND_SOURCE_ID = s.ID and p.id = y.PM_PRJ_ID " +
                "where s.id = ? and p.id is not null group by y.id,p.id", id);
        List<ImplementFund> implementFunds = new ArrayList<>();
        for (Map<String, Object> pay : payList) {
            ImplementFund implementFund = EntityUtil.mapToEntity(ImplementFund.class, pay);
            implementFunds.add(implementFund);
        }
        HashMap<Object, Object> result = new HashMap<>();
        result.put("payList", implementFunds);
        result.put("infoMap", infoMap);
        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    // 资金详情
    public void getFundDetail() {
        Map<String, Object> idMap = ExtJarHelper.extApiParamMap.get();
        String sourceId = idMap.get("sourceId").toString();
        String prjId = idMap.get("prjId").toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> infoMap = myJdbcTemplate.queryForMap("SELECT s.id,p.NAME prjName,sum( a.APPORTION_AMT ) " +
                "apportionAmt,max(temp.payAmt) payAmt " +
                "FROM pm_fund_source s " +
                "LEFT JOIN pm_fund_apportion a ON a.PM_FUND_SOURCE_ID = s.id " +
                "LEFT JOIN pm_prj p ON p.id = a.PM_PRJ_ID " +
                "left join (SELECT p.id,sum( y.PAY_AMT ) payAmt " +
                "FROM pm_fund_source s " +
                "LEFT JOIN pm_fund_pay y ON y.PM_FUND_SOURCE_ID = s.ID " +
                "left join pm_prj p on p.id = y.PM_PRJ_ID " +
                "GROUP BY p.id)temp on temp.id = p.id  " +
                "WHERE s.id = ? GROUP BY p.id having p.id = ?", sourceId, prjId);

        List<Map<String, Object>> apportionList = myJdbcTemplate.queryForList("select a.APPORTION_AMT apportionAmt,a" +
                ".APPORTION_DATE apportionDate,a.remark ,a.ATT_FILE_GROUP_ID fileIds from pm_fund_apportion a where a.PM_FUND_SOURCE_ID = ?" +
                " and a.PM_PRJ_ID = ?", sourceId, prjId);
        // 分配文件
        apportionList.forEach(apportion -> {
            String fileIdStr = JdbcMapUtil.getString(apportion, "fileIds");
            List<File> fileList = FileCommon.getFileResp(fileIdStr, myJdbcTemplate);
            apportion.put("fileList", fileList);
        });

        List<Map<String, Object>> payList = myJdbcTemplate.queryForList("select y.PAY_AMT payAmt,y.CRT_DT payDate,y" +
                ".FUND_TYPE fundType,y.PAY_REMARK payRemark,t.name payeeUnit,y.remark,y.ATT_FILE_GROUP_ID fileIds from pm_fund_pay y left " +
                "join pm_fund_source s on s.id = y.PM_FUND_SOURCE_ID left join pm_party t on t.id = y.PAYEE_UNIT where y.PM_FUND_SOURCE_ID = ? " +
                "and y.PM_PRJ_ID = ?", sourceId, prjId);

        // 支付文件
        payList.forEach(pay -> {
            // 时间
            pay.put("payDate", StringUtil.withOutT(JdbcMapUtil.getString(pay, "payDate")));
            // 文件
            String fileIdStr = JdbcMapUtil.getString(pay, "fileIds");
            List<File> fileList = FileCommon.getFileResp(fileIdStr, myJdbcTemplate);
            pay.put("fileList", fileList);
        });

        HashMap<Object, Object> result = new HashMap<>();
        result.put("infoMap", infoMap);
        result.put("apportionList", apportionList);
        result.put("payList", payList);
        Map outputMap = JsonUtil.fromJson(JSONObject.toJSONString(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    public static class Input extends BasePageEntity {
        /**
         * 资金来源id
         */
        public String fundSourceId;

        /**
         * 开始日期
         */
        public String startDate;

        /**
         * 结束日期
         */
        public String endDate;
    }

    public static class FundPayResp {
        public String sourceId;
        public String fundSourceName;
        public Double imptAmt;
        public Double apportionAmt;
        public Double payAmt;
        public String sourceTypeName;
        public String date;
        public String remark;
    }
}
