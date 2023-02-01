package com.cisdi.ext.demostration;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

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
        Map<String, String> investMap = new HashMap<>();
        investMap.put("1月", "75768");
        investMap.put("2月", "45892");
        investMap.put("3月", "94152");
        investMap.put("4月", "107787");
        investMap.put("5月", "104995");
        investMap.put("6月", "91453");
        investMap.put("7月", "75853");
        investMap.put("8月", "69898");
        investMap.put("9月", "62901");
        investMap.put("10月", "84893");
        investMap.put("11月", "126752");
        investMap.put("12月", "99536");

        ExtJarHelper.returnValue.set(investMap);
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
