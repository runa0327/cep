package com.cisdi.ext.demostration;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;

import java.util.*;

/**
 * @author dlt
 * @date 2023/2/1 周三
 * 项目首页演示
 */
public class ProjectHome {

    /**
     * 累计建设投资
     */
    public void showCumulateInvest(){
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
        result.put("cumulateInvests",cumulateInvests);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 年度投资进展
     */
    public void yearInvestProgress(){
        Map<String, String> investMap = new HashMap<>();
        investMap.put("1月","75768");
        investMap.put("2月","45892");
        investMap.put("3月","94152");
        investMap.put("4月","107787");
        investMap.put("5月","104995");
        investMap.put("6月","91453");
        investMap.put("7月","75853");
        investMap.put("8月","69898");
        investMap.put("9月","62901");
        investMap.put("10月","84893");
        investMap.put("11月","126752");
        investMap.put("12月","99536");

        ExtJarHelper.returnValue.set(investMap);
    }

    public static class CumulateInvest{
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
}
