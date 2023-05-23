package com.cisdi.ext.fund;

import com.cisdi.ext.fundManage.FileCommon;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.view.file.File;
import com.cisdi.ext.util.EntityUtil;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Update;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 资金落实
 */
public class FundImplementationApi {
    //新增资金落实
    public void addFundImplementation() {
        Map<String, Object> ImpMap = ExtJarHelper.extApiParamMap.get();
        FundImplementation fundImplementation = JsonUtil.fromJson(JsonUtil.toJson(ImpMap), FundImplementation.class);
        String id = fundImplementation.id;
        if (Strings.isNullOrEmpty(id)) {
            if (checkDuplicate(fundImplementation.fundSourceText)) {//新增时资金来源不能重复
                throw new BaseException("资金来源重复！");
            }
            //插入落实表
            id = Crud.from("fund_implementation").insertData();
        }
        //更新落实表
        Update updateValue = Crud.from("fund_implementation").where().eq("ID", id).update().set("REMARK", fundImplementation.remark)
                .set("FUND_SOURCE_TEXT", fundImplementation.fundSourceText).set("FUND_CATEGORY_FIRST", fundImplementation.fundCategoryFirst)
                .set("FUND_CATEGORY_SECOND", fundImplementation.fundCategorySecond).set("DECLARED_AMOUNT", fundImplementation.declaredAmount)
                .set("ATT_FILE_GROUP_ID", fundImplementation.attFileGroupId);
        if (!Strings.isNullOrEmpty(fundImplementation.approvalTime)){
            updateValue.set("APPROVAL_TIME", fundImplementation.approvalTime);
        }
        updateValue.exec();
        //删除明细
        Crud.from("fund_implementation_detail").where().eq("FUND_IMPLEMENTATION_ID", id).delete().exec();
        //插入明细表
        for (FundImplementationDetail detail : fundImplementation.details) {
            String detailLid = Crud.from("fund_implementation_detail").insertData();
            Crud.from("fund_implementation_detail").where().eq("ID", detailLid).update().set("FUND_IMPLEMENTATION_ID", id)
                    .set("PM_PRJ_ID", detail.pmPrjId).set("APPROVED_AMOUNT", detail.approvedAmount).exec();
        }
    }

    //资金落实列表
    public void fundImpList() {
        Map<String, Object> fundImpMap = ExtJarHelper.extApiParamMap.get();
        FundImpReq fundImpReq = JsonUtil.fromJson(JsonUtil.toJson(fundImpMap), FundImpReq.class);
        StringBuffer baseSql = new StringBuffer("select i.id,t1.name FUND_CATEGORY_FIRST,t2.name FUND_CATEGORY_SECOND,i.FUND_SOURCE_TEXT,i" +
                ".DECLARED_AMOUNT,sum(d.APPROVED_AMOUNT) APPROVED_AMOUNT,i.APPROVAL_TIME " +
                "from fund_implementation i " +
                "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                "left join fund_type t2 on t2.id = i.FUND_CATEGORY_SECOND " +
                "left join fund_implementation_detail d on d.FUND_IMPLEMENTATION_ID = i.id " +
                "where 1 = 1 ");
        if (!Strings.isNullOrEmpty(fundImpReq.fundSource)) {
            baseSql.append("and i.FUND_SOURCE_TEXT like '%" + fundImpReq.fundSource + "%' ");
        }
        if (!Strings.isNullOrEmpty(fundImpReq.pmPrjId)) {
            baseSql.append("and d.PM_PRJ_ID = '").append(fundImpReq.pmPrjId).append("' ");
        }
        if (!Strings.isNullOrEmpty(fundImpReq.beginTime) && !Strings.isNullOrEmpty(fundImpReq.endTime)) {
            baseSql.append("and i.APPROVAL_TIME BETWEEN '" + fundImpReq.beginTime + "' and '" + fundImpReq.endTime + "' ");
        }
        if (!Strings.isNullOrEmpty(fundImpReq.categoryNameId)) {
            baseSql.append("and t1.id = '").append(fundImpReq.categoryNameId).append("' ");
        }
        if (!Strings.isNullOrEmpty(fundImpReq.secondCategoryNameId)) {
            baseSql.append("and t2.id = '").append(fundImpReq.secondCategoryNameId).append("' ");
        }
        baseSql.append("GROUP BY i.id ");
        // 总条数sql
        String totalSql = baseSql.toString();

        baseSql.append("order by i.CRT_DT desc ");

        Integer start = fundImpReq.pageSize * (fundImpReq.pageIndex - 1);
        baseSql.append("limit " + start + "," + fundImpReq.pageSize);

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> impList = myJdbcTemplate.queryForList(baseSql.toString());
        List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
        ArrayList<FundImpResp> fundImpRespList = new ArrayList<>();
        for (Map<String, Object> impMap : impList) {
            FundImpResp fundImpResp = EntityUtil.mapToEntity(FundImpResp.class, impMap);
            fundImpRespList.add(fundImpResp);
        }
        ListResult listResult = new ListResult();
        listResult.fundImpRespList = fundImpRespList;
        listResult.total = totalList.size();

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(listResult), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    //删除
    public void delImp() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        FundImpReq fundImpReq = JsonUtil.fromJson(JsonUtil.toJson(inputMap), FundImpReq.class);
        Crud.from("fund_implementation_detail").where().eq("FUND_IMPLEMENTATION_ID", fundImpReq.id).delete().exec();
        Crud.from("fund_implementation").where().eq("id", fundImpReq.id).delete().exec();
    }

    //详情
    public void ImpDetail() {
        Map<String, Object> idMap = ExtJarHelper.extApiParamMap.get();
        String id = JdbcMapUtil.getString(idMap, "id");
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "SELECT i.ID,i.REMARK,i.FUND_SOURCE_TEXT,i.FUND_CATEGORY_FIRST FUND_CATEGORY_FIRST_ID,t1.name FUND_CATEGORY_FIRST,i.FUND_CATEGORY_SECOND FUND_CATEGORY_SECOND_ID,t2.name FUND_CATEGORY_SECOND,i.DECLARED_AMOUNT,i" +
                ".APPROVAL_TIME,i.ATT_FILE_GROUP_ID,sum(d.APPROVED_AMOUNT) SUM_APPROVED_AMOUNT " +
                "FROM fund_implementation i " +
                "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                "left join fund_type t2 on t2.id = i.FUND_CATEGORY_SECOND " +
                "left join fund_implementation_detail d on d.FUND_IMPLEMENTATION_ID = i.id " +
                "WHERE i.id = ? " +
                "GROUP BY i.id";
        Map<String, Object> impMap = myJdbcTemplate.queryForMap(sql, id);
        //文件
        List<File> fileList = FileCommon.getFileResp(JdbcMapUtil.getString(impMap, "ATT_FILE_GROUP_ID"), myJdbcTemplate);
        //落实明细
        String detailSql = "select d.id,d.PM_PRJ_ID,p.name prj_name,d.APPROVED_AMOUNT " +
                "from fund_implementation_detail d left join pm_prj p on p.id = d.PM_PRJ_ID " +
                "where d.FUND_IMPLEMENTATION_ID = ?";
        List<Map<String, Object>> detailMapList = myJdbcTemplate.queryForList(detailSql, id);
        //封装
        ArrayList<FundImplementationDetail> detailList = new ArrayList<>();
        for (Map<String, Object> detailMap : detailMapList) {
            FundImplementationDetail detail = EntityUtil.mapToEntity(FundImplementationDetail.class, detailMap);
            detailList.add(detail);
        }
        ImpDetail impDetail = EntityUtil.mapToEntity(ImpDetail.class, impMap);
        impDetail.detailList = detailList;
        impDetail.fileList = fileList;

        //返回
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(impDetail), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    //查重
    public boolean checkDuplicate(String name) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "SELECT count(*) count FROM fund_implementation where FUND_SOURCE_TEXT = ?";
        Map<String, Object> countMap = myJdbcTemplate.queryForMap(sql, name);
        if (JdbcMapUtil.getInt(countMap, "count") == 0) {
            return false;
        }
        return true;
    }


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

    @Data
    public static class FundImplementationDetail {
        //id
        public String id;
        //项目
        public String pmPrjId;
        //批复金额
        public BigDecimal approvedAmount;
        //项目名
        public String prjName;
    }

    public static class FundImpReq extends BasePageEntity {
        //id
        public String id;
        //资金来源
        public String fundSource;
        //项目id
        public String pmPrjId;
        //开始批复时间
        public String beginTime;
        //结束批复时间
        public String endTime;
        //资金类别一级id
        public String categoryNameId;
        //资金类别二级id
        public String secondCategoryNameId;

    }

    @Data
    public static class FundImpResp {
        //id
        public String id;
        //资金类别一级
        public String fundCategoryFirst;
        //资金类别二级
        public String fundCategorySecond;
        //资金来源
        public String fundSourceText;
        //申报金额
        public BigDecimal declaredAmount;
        //批复金额
        public BigDecimal approvedAmount;
        //批复时间
        public String approvalTime;
    }

    public static class ListResult {
        public Integer total;
        public List<FundImpResp> fundImpRespList;
    }

    @Data
    public static class ImpDetail {
        //id
        public String id;
        //资金来源
        public String fundSourceText;
        //资金类别一级
        public String fundCategoryFirst;
        public String fundCategoryFirstId;
        //资金类别二级
        public String fundCategorySecond;
        public String fundCategorySecondId;
        //申报金额
        public BigDecimal declaredAmount;
        //批复金额
        public BigDecimal sumApprovedAmount;
        //批复时间
        public String approvalTime;
        //备注
        public String remark;
        //落实明细
        public List<FundImplementationDetail> detailList;
        //文件
        public List<File> fileList;
    }
}
