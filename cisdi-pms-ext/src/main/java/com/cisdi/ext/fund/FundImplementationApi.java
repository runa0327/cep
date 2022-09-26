package com.cisdi.ext.fund;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 资金落实
 */
public class FundImplementationApi {
    //新增资金落实
    public void addFundImplementation(){
        Map<String, Object> ImpMap = ExtJarHelper.extApiParamMap.get();
        FundImplementation fundImplementation = JsonUtil.fromJson(JsonUtil.toJson(ImpMap), FundImplementation.class);
        //插入落实表
        String id = Crud.from("fund_implementation").insertData();
        Crud.from("fund_implementation").where().eq("ID",id).update().set("REMARK",fundImplementation.remark)
                .set("FUND_SOURCE_TEXT",fundImplementation.fundSourceText).set("FUND_CATEGORY_FIRST",fundImplementation.fundCategoryFirst)
                .set("FUND_CATEGORY_SECOND", fundImplementation.fundCategorySecond).set("DECLARED_AMOUNT", fundImplementation.declaredAmount)
                .set("APPROVAL_TIME", fundImplementation.approvalTime).set("ATT_FILE_GROUP_ID", fundImplementation.attFileGroupId).exec();
        //插入明细表
        for (FundImplementationDetail detail : fundImplementation.details) {
            String detailLid = Crud.from("fund_implementation_detail").insertData();
            Crud.from("fund_implementation_detail").where().eq("ID",detailLid).update().set("FUND_IMP_ID",id)
                    .set("PM_PRJ_ID", detail.pmPrjId).set("APPROVED_AMOUNT",detail.approvedAmount).exec();
        }
    }

    //资金落实列表


    public static class FundImplementation {
        //id
        public String id;
        //备注
        public String remark;
        //资金来源
        public String fundSourceText;
        //资金类别一级
        public String fundCategoryFirst;
        //资金类别二级
        public String fundCategorySecond;
        //申报金额
        public BigDecimal declaredAmount;
        //批复时间(日期)
        public String approvalTime;
        //附件ids
        public String attFileGroupId;
        //落实明细
        public List<FundImplementationDetail> details;
    }

    public static class FundImplementationDetail {
        //id
        public String id;
        //项目
        public String pmPrjId;
        //批复金额
        public BigDecimal approvedAmount;
    }
}
