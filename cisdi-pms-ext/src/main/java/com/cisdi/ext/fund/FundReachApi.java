package com.cisdi.ext.fund;

import com.cisdi.ext.fundManage.FileCommon;
import com.cisdi.ext.model.view.CommonDrop;
import com.cisdi.ext.model.view.file.File;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundReachApi
 * @package com.cisdi.ext.fund
 * @description 资金到位
 * @date 2022/9/26
 */
public class FundReachApi {

    /**
     * 获取请求参数
     *
     * @return
     */
    public RequestParam getRequestParam() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        return JsonUtil.fromJson(json, RequestParam.class);
    }

    /**
     * 资金到位列表查询
     */
    public void fundReachList() {
        RequestParam param = this.getRequestParam();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sourceName = param.sourceName;
        String projectId = param.projectId;
        String beginTime = param.beginTime;
        String endTime = param.endTime;
        int pageSize = param.pageSize;
        int pageIndex = param.pageIndex;
        String categoryNameId = param.categoryNameId;
        String secondCategoryNameId = param.secondCategoryNameId;

        StringBuilder sb = new StringBuilder();

        sb.append("select r.ID,p.name projectName,r.FUND_SOURCE_TEXT sourceName,sv.name reachCategory,IFNULL(REACH_AMOUNT,0) amt," +
                "REACH_DATE reachDate,t1.name firstTypeName,t2.name secondTypeName " +
                "from fund_reach r left join pm_prj p on p.id = r.PM_PRJ_ID " +
                "left join fund_implementation i on i.FUND_SOURCE_TEXT = r.FUND_SOURCE_TEXT " +
                "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                "left join fund_type t2 on t2.id = i.FUND_CATEGORY_SECOND " +
                "left join gr_set_value sv on sv.id = r.FUND_REACH_CATEGORY " +
                "left join gr_set se on se.id = sv.GR_SET_ID and se.code = 'fund_reach_category' " +
                "where 1=1 ");
        if (Strings.isNotEmpty(sourceName)) {
            sb.append(" and r.FUND_SOURCE_TEXT like '%").append(sourceName).append("%'");
        }
        if (Strings.isNotEmpty(projectId)) {
            sb.append(" and r.PM_PRJ_ID = '").append(projectId).append("'");
        }
        if (Strings.isNotEmpty(beginTime) && Strings.isNotEmpty(endTime)) {
            sb.append(" and r.REACH_DATE between '").append(beginTime).append("' and '").append(endTime).append("'");
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(categoryNameId)) {
            sb.append("and t1.id = '").append(categoryNameId).append("' ");
        }
        if (!com.google.common.base.Strings.isNullOrEmpty(secondCategoryNameId)) {
            sb.append("and t2.id = '").append(secondCategoryNameId).append("' ");
        }

        sb.append("order by r.CRT_DT desc ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<FundReach> dataList = list.stream().map(m -> this.convertFundReach(m, "1")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.list = dataList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 资金到位详情
     */
    public void fundReachView() {
        RequestParam param = this.getRequestParam();
        String id = param.id;
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            String sql = "select r.ID,p.name projectName,p.id prjId,r.FUND_SOURCE_TEXT sourceName,sv.name reachCategory,sv.id reachCategoryId,IFNULL(r.REACH_AMOUNT,0)" +
                    " amt,b3.id payeeId,b3.name payee,b1.name bank,b1.id bankId,b2.name account,b2.id accountId,r.REACH_DATE reachDate,r.remark,r.ATT_FILE_GROUP_ID fileIds,temp.sumAmt,t1" +
                    ".name firstTypeName,t2.name secondTypeName,temp.count,i.APPROVAL_TIME,r.REACH_TIMES " +
                    "from fund_reach r left join pm_prj p on p.id = r.PM_PRJ_ID " +
                    "left join receiving_bank b1 on b1.id = r.RECEIVING_BANK_ID " +
                    "left join receiving_bank b2 on b2.id = r.RECEIPT_ACCOUNT " +
                    "left join receiving_bank b3 on b3.id = r.PAYEE " +
                    "left join fund_implementation i on i.FUND_SOURCE_TEXT = r.FUND_SOURCE_TEXT " +
                    "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                    "left join fund_type t2 on t2.id = i.FUND_CATEGORY_SECOND " +
                    "left join (select FUND_SOURCE_TEXT,PM_PRJ_ID,FUND_REACH_CATEGORY,sum(IFNULL(REACH_AMOUNT,0)) sumAmt,count(*) count from " +
                    "fund_reach group by FUND_SOURCE_TEXT,PM_PRJ_ID,FUND_REACH_CATEGORY ) temp on temp.FUND_SOURCE_TEXT = r.FUND_SOURCE_TEXT and " +
                    "temp.PM_PRJ_ID = r.PM_PRJ_ID and temp.FUND_REACH_CATEGORY = r.FUND_REACH_CATEGORY " +
                    "left join gr_set_value sv on sv.id = r.FUND_REACH_CATEGORY " +
                    "left join gr_set se on se.id = sv.GR_SET_ID and se.code = 'fund_reach_category' " +
                    "where r.id = ?";
            Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap(sql, id);
            FundReach reach = this.convertFundReach(stringObjectMap, "2");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(reach), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            throw new BaseException("查询数据错误");
        }
    }

    /**
     * 查询项目，资金来源到位的条数
     * 前端新增时，根据选择的项目，资金来源，到位类型并且按照时间排序新数据放前面查询此类数据的条数列表给到前端
     */
    public void getReachCountList() {
        RequestParam param = this.getRequestParam();
        String projectId = param.projectId;
        String sourceName = param.sourceName;
        String type = param.type;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String recordSql = "select id,payee,REACH_TIMES reachTimes from fund_reach where FUND_SOURCE_TEXT = ? and PM_PRJ_ID = ? and FUND_REACH_CATEGORY = ? order by CRT_DT desc";
        String detailSql = "select t1.name firstTypeName,t2.name secondTypeName,i.APPROVAL_TIME approvalTime " +
                "from fund_implementation i " +
                "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                "left join fund_type t2 on t2.id = i.FUND_CATEGORY_SECOND " +
                "where i.FUND_SOURCE_TEXT = ?";
        String sumSql = "SELECT sum( REACH_AMOUNT ) sumAmt FROM fund_reach WHERE FUND_SOURCE_TEXT = ? AND PM_PRJ_ID = ? AND FUND_REACH_CATEGORY = ?";
        List<Map<String, Object>> recordList = myJdbcTemplate.queryForList(recordSql, sourceName, projectId, type);
        Map<String, Object> detailMap = myJdbcTemplate.queryForMap(detailSql, sourceName);
        Map<String, Object> sumMap = myJdbcTemplate.queryForMap(sumSql, sourceName, projectId, type);

        HashMap<String, Object> result = new HashMap<>();
        result.put("recordList",recordList);
        result.put("detailMap",detailMap);
        result.put("sumMap",sumMap);

        ExtJarHelper.returnValue.set(result);
    }

    /**
     * 资金到位新增，编辑
     */
    public void fundReachModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        FundReachInput input = JsonUtil.fromJson(json, FundReachInput.class);
        String id = input.id;
        if (!Strings.isNotEmpty(id)) {
            id = Crud.from("FUND_REACH").insertData();
        }
        Crud.from("FUND_REACH").where().eq("ID",id).update()
                .set("PM_PRJ_ID",input.projectId).set("FUND_SOURCE_TEXT",input.sourceName).set("FUND_REACH_CATEGORY",input.type)
                .set("REACH_AMOUNT",input.amt).set("PAYEE",input.unit).set("RECEIPT_ACCOUNT",input.account).set("REACH_DATE",input.reachDate)
                .set("ATT_FILE_GROUP_ID", input.fileIds).set("RECEIVING_BANK_ID",input.bank).set("REMARK",input.remark)
                .set("REACH_TIMES",input.reachTimes).exec();
    }

    /**
     * 资金到位删除
     */
    public void fundReachDel() {
        RequestParam param = this.getRequestParam();
        String id = param.id;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from FUND_REACH where id = ?", id);
    }

    /**
     * 项目下拉
     *
     */
    public void impPrjDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String prjName = Objects.isNull(inputMap.get("prjName")) ? null : inputMap.get("prjName").toString();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select d.PM_PRJ_ID id,p.name from fund_implementation_detail d left join pm_prj p on p.id = d.PM_PRJ_ID where 1=1 and d.PM_PRJ_ID is not null ");
        if (!com.google.common.base.Strings.isNullOrEmpty(prjName)) {
            baseSql.append("and p.name like '%" + prjName + "%' ");
        }
        baseSql.append("group by d.PM_PRJ_ID ");

        List<Map<String, Object>> projects = myJdbcTemplate.queryForList(baseSql.toString());
        List<CommonDrop> commonDrops = new ArrayList<>();
        for (Map<String, Object> project : projects) {
            CommonDrop projectDrop = new CommonDrop();
            projectDrop.id = JdbcMapUtil.getString(project, "id");
            projectDrop.name = JdbcMapUtil.getString(project, "name");
            commonDrops.add(projectDrop);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("projects", commonDrops);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 资金来源下拉
     *
     */
    public void sourceDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String prjId = Objects.isNull(inputMap.get("prjId")) ? null : inputMap.get("prjId").toString();
        String name = Objects.isNull(inputMap.get("name")) ? null : inputMap.get("name").toString();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select i.FUND_SOURCE_TEXT name from fund_implementation i left join fund_implementation_detail d on d.fund_implementation_id = i.id where 1=1 ");
        if (Strings.isNotEmpty(prjId)) {
            baseSql.append("and d.PM_PRJ_ID = '" + prjId + "' ");
        }
        if (Strings.isNotEmpty(name)) {
            baseSql.append("and i.FUND_SOURCE_TEXT like '%" + name + "%' ");
        }
        baseSql.append("group by i.FUND_SOURCE_TEXT ");

        List<Map<String, Object>> list = myJdbcTemplate.queryForList(baseSql.toString());
        List<CommonDrop> commonDrops = new ArrayList<>();
        for (Map<String, Object> map : list) {
            CommonDrop projectDrop = new CommonDrop();
            projectDrop.name = JdbcMapUtil.getString(map, "name");
            commonDrops.add(projectDrop);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("sources", commonDrops);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 资金到位导出
     */
    public void fundReachExport() {
        //TODO 导出逻辑
    }

    /**
     * 数据转换
     *
     * @param data Map数据集
     * @param mark 1-列表查询 2-详情
     * @return
     */
    private FundReach convertFundReach(Map<String, Object> data, String mark) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        FundReach fundReach = new FundReach();
        fundReach.id = JdbcMapUtil.getString(data, "ID");
        fundReach.firstTypeName = JdbcMapUtil.getString(data, "firstTypeName");
        fundReach.secondTypeName = JdbcMapUtil.getString(data, "secondTypeName");
        fundReach.projectName = JdbcMapUtil.getString(data, "projectName");
        fundReach.sourceName = JdbcMapUtil.getString(data, "sourceName");
        fundReach.reachCategory = JdbcMapUtil.getString(data, "reachCategory");
        fundReach.reachCategoryId = JdbcMapUtil.getString(data, "reachCategoryId");
        fundReach.amt = JdbcMapUtil.getString(data, "amt");
        fundReach.reachDate = JdbcMapUtil.getString(data, "reachDate");
        if (Objects.equals("2", mark)) {
            fundReach.prjId = JdbcMapUtil.getString(data,"prjId");
            fundReach.bankId = JdbcMapUtil.getString(data, "bankId");
            fundReach.accountId = JdbcMapUtil.getString(data, "accountId");
            fundReach.payeeId = JdbcMapUtil.getString(data,"payeeId");
            fundReach.payee = JdbcMapUtil.getString(data,"payee");
            fundReach.bank = JdbcMapUtil.getString(data,"bank");
            fundReach.account = JdbcMapUtil.getString(data,"account");
            fundReach.remark = JdbcMapUtil.getString(data, "remark");
            fundReach.sumAmt = JdbcMapUtil.getString(data, "sumAmt");
            fundReach.count = JdbcMapUtil.getInt(data,"count");
            fundReach.approvalTime = JdbcMapUtil.getString(data,"APPROVAL_TIME");
            fundReach.reachTimes = JdbcMapUtil.getInt(data,"REACH_TIMES");
            List<File> fileList = FileCommon.getFileResp(JdbcMapUtil.getString(data,"fileIds"), myJdbcTemplate);
            fundReach.fileList = fileList;
        }
        return fundReach;
    }



    /**
     * 请求参数
     */
    public static class RequestParam {
        public String id;
        //资金来源名称
        public String sourceName;
        public Integer pageSize;
        public Integer pageIndex;
        //项目
        public String projectId;

        public String beginTime;

        public String endTime;
        //资金类别一级id
        public String categoryNameId;
        //资金类别二级id
        public String secondCategoryNameId;

        /**
         * 到位类型
         */
        public String type;
    }

    /**
     * 新增，修改入参
     */
    public static class FundReachInput {
        //新增时不传值，修改时必传
        public String id;
        //项目
        public String projectId;
        //资金来源名称
        public String sourceName;
        //到位类别
        public String type;
        //到位金额
        public String amt;
        //收款单位
        public String unit;
        //收款银行
        public String bank;
        //收款账户
        public String account;
        //到位日期
        public String reachDate;
        //备注
        public String remark;
        //到位次数
        public Integer reachTimes;
        //文件
        public String fileIds;
    }

    /**
     * 资金到位
     */
    public static class FundReach {
        public String id;
        public String firstTypeName;
        public String secondTypeName;
        public String prjId;
        public String projectName;
        public String sourceName;
        public String reachCategory;
        public String reachCategoryId;
        public String amt;
        public String reachDate;
        //收款单位
        public String payee;
        public String payeeId;
        //收款银行
        public String bank;
        public String bankId;
        //收款账户
        public String account;
        public String accountId;
        //备注
        public String remark;
        //累计到位金额
        public String sumAmt;
        //到位次数（废弃）
        public Integer count;
        //批复时间
        public String approvalTime;
        //文件集合
        public List<File> fileList;
        //到位次数
        public Integer reachTimes;

    }

    /**
     * 返回值包装类
     */
    public static class OutSide {
        public Integer total;
        public List<FundReach> list;
    }


}
