package com.cisdi.ext.demostration;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/2/1 周三
 * 项目首页演示
 */
public class ProjectHome {

    /**
     * 累计建设投资
     */
    public void showCumulateInvest() {
        List<CumulateInvest> cumulateInvests = new ArrayList<>();
        CumulateInvest row1 = new CumulateInvest("前期", "67", "3366338.88", "1762169.45", "1409735.56");
        CumulateInvest row2 = new CumulateInvest("其他", "42", "2110242.28", "1975236.85", "1580189.48");
        CumulateInvest row3 = new CumulateInvest("完工", "103", "5175117.98", "5175117.98", "4140094.384");
        CumulateInvest row4 = new CumulateInvest("在建", "123", "6179995.26", "3774321.74", "3019457.392");
        cumulateInvests.add(row1);
        cumulateInvests.add(row2);
        cumulateInvests.add(row3);
        cumulateInvests.add(row4);
        Map<String, List> result = new HashMap<>();
        result.put("cumulateInvests", cumulateInvests);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 年度投资进展
     */
    public void yearInvestProgress() {
//        List<InvestProgress> actualList = new ArrayList<>();
//        InvestProgress actualProgress1 = new InvestProgress("1月", "75768");
//        InvestProgress actualProgress2 = new InvestProgress("2月", "45892");
//        InvestProgress actualProgress3 = new InvestProgress("3月", "94152");
//        InvestProgress actualProgress4 = new InvestProgress("4月", "107787");
//        InvestProgress actualProgress5 = new InvestProgress("5月", "104995");
//        InvestProgress actualProgress6 = new InvestProgress("6月", "91453");
//        InvestProgress actualProgress7 = new InvestProgress("7月", "75853");
//        InvestProgress actualProgress8 = new InvestProgress("8月", "69898");
//        InvestProgress actualProgress9 = new InvestProgress("9月", "62901");
//        InvestProgress actualProgress10 = new InvestProgress("10月", "84893");
//        InvestProgress actualProgress11 = new InvestProgress("11月", "126752");
//        InvestProgress actualProgress12 = new InvestProgress("12月", "99536");
//        actualList.add(actualProgress1);
//        actualList.add(actualProgress2);
//        actualList.add(actualProgress3);
//        actualList.add(actualProgress4);
//        actualList.add(actualProgress5);
//        actualList.add(actualProgress6);
//        actualList.add(actualProgress7);
//        actualList.add(actualProgress8);
//        actualList.add(actualProgress9);
//        actualList.add(actualProgress10);
//        actualList.add(actualProgress11);
//        actualList.add(actualProgress12);

        List<BigDecimal> actualList = new ArrayList<>();
        actualList.add(new BigDecimal("75768"));
        actualList.add(new BigDecimal("45892"));
        actualList.add(new BigDecimal("94152"));
        actualList.add(new BigDecimal("107787"));
        actualList.add(new BigDecimal("104995"));
        actualList.add(new BigDecimal("91453"));
        actualList.add(new BigDecimal("75853"));
        actualList.add(new BigDecimal("69898"));
        actualList.add(new BigDecimal("62901"));
        actualList.add(new BigDecimal("84893"));
        actualList.add(new BigDecimal("126752"));
        actualList.add(new BigDecimal("99536"));

        double actualSum = actualList.stream().mapToDouble(actual -> Double.valueOf(actual.toString())).sum();
        BigDecimal actualSumDecimal = new BigDecimal(actualSum);
        HashMap<String, Object> actualResult = new HashMap<>();
        actualResult.put("actualList",actualList);
        actualResult.put("actualSumDecimal",actualSumDecimal);

//        List<InvestProgress> planList = new ArrayList<>();
//        InvestProgress planProgress1 = new InvestProgress("1月", "85768");
//        InvestProgress planProgress2 = new InvestProgress("2月", "35892");
//        InvestProgress planProgress3 = new InvestProgress("3月", "44152");
//        InvestProgress planProgress4 = new InvestProgress("4月", "97787");
//        InvestProgress planProgress5 = new InvestProgress("5月", "114995");
//        InvestProgress planProgress6 = new InvestProgress("6月", "90453");
//        InvestProgress planProgress7 = new InvestProgress("7月", "85853");
//        InvestProgress planProgress8 = new InvestProgress("8月", "79898");
//        InvestProgress planProgress9 = new InvestProgress("9月", "32901");
//        InvestProgress planProgress10 = new InvestProgress("10月", "74893");
//        InvestProgress planProgress11 = new InvestProgress("11月", "116752");
//        InvestProgress planProgress12 = new InvestProgress("12月", "98536");
//        planList.add(planProgress1);
//        planList.add(planProgress2);
//        planList.add(planProgress3);
//        planList.add(planProgress4);
//        planList.add(planProgress5);
//        planList.add(planProgress6);
//        planList.add(planProgress7);
//        planList.add(planProgress8);
//        planList.add(planProgress9);
//        planList.add(planProgress10);
//        planList.add(planProgress11);
//        planList.add(planProgress12);

        List<BigDecimal> planList = new ArrayList<>();
        planList.add(new BigDecimal("85768"));
        planList.add(new BigDecimal("35892"));
        planList.add(new BigDecimal("44152"));
        planList.add(new BigDecimal("97787"));
        planList.add(new BigDecimal("114995"));
        planList.add(new BigDecimal("90453"));
        planList.add(new BigDecimal("85853"));
        planList.add(new BigDecimal("79898"));
        planList.add(new BigDecimal("32901"));
        planList.add(new BigDecimal("84893"));
        planList.add(new BigDecimal("126752"));
        planList.add(new BigDecimal("99536"));

        double planSum = planList.stream().mapToDouble(plan -> Double.valueOf(plan.toString())).sum();
        BigDecimal planSumDecimal = new BigDecimal(planSum);
        HashMap<String, Object> planResult = new HashMap<>();
        planResult.put("planList",planList);
        planResult.put("planSumDecimal",planSumDecimal);

        Map<String, Object> result = new HashMap<>();
        result.put("actualResult",actualResult);
        result.put("planResult",planResult);

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    @Data
    public static class InvestProgress{
        public String month;
        public String money;

        public InvestProgress(String month, String money) {
            this.month = month;
            this.money = money;
        }
    }

    public static class CumulateInvest {
        public String prjStatus;
        public String prjNum;
        public String totalInvest;
        public String complementInvest;
        public String complementPay;

        public CumulateInvest(String prjStatus, String prjNum, String totalInvest, String complementInvest, String complementPay) {
            this.prjStatus = prjStatus;
            this.prjNum = prjNum;
            this.totalInvest = totalInvest;
            this.complementInvest = complementInvest;
            this.complementPay = complementPay;
        }
    }


    /**
     * 最新动态，预警，公告查询
     */
    public void dynamicData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> List = myJdbcTemplate.queryForList("select * from DYNAMIC_TEMP where BANK_TYPE=?", map.get("type"));
        List<dynamicTemp> res = List.stream().map(p -> {
            dynamicTemp temp = new dynamicTemp();
            temp.demandPromoter = JdbcMapUtil.getString(p, "DEMAND_PROMOTER");
            temp.remarkShort = JdbcMapUtil.getString(p, "REMARK_SHORT");
            temp.dateOne = JdbcMapUtil.getString(p, "DATE_ONE");
            temp.nameOne = JdbcMapUtil.getString(p, "NAME_ONE");
            temp.bankType = JdbcMapUtil.getString(p, "BANK_TYPE");
            return temp;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(res)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.res = res;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class dynamicTemp {
        //发起人
        public String demandPromoter;
        //内容
        public String remarkShort;
        //日期
        public String dateOne;
        //标题
        public String nameOne;
        //类型 1-最新动态 2-预警 3-公告
        public String bankType;
    }

    public static class OutSide {
        public List<dynamicTemp> res;
    }
}
