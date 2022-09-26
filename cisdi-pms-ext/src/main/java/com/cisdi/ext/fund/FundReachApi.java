package com.cisdi.ext.fund;

import com.cisdi.ext.fundManage.FileCommon;
import com.cisdi.ext.model.view.File;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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

        StringBuilder sb = new StringBuilder();
        //TODO 查询优化
        sb.append("select PM_PRJ_ID,FUND_SOURCE_TEXT,FUND_REACH_CATEGORY,IFNULL(REACH_AMOUNT,0) AS REACH_AMOUNT,PAYEE,RECEIPT_ACCOUNT,REACH_DATE,ATT_FILE_GROUP_ID from fund_reach where 1=1 ");
        if (Strings.isNotEmpty(sourceName)) {
            sb.append(" and FUND_SOURCE_TEXT like '%").append(sourceName).append("%'");
        }
        if (Strings.isNotEmpty(projectId)) {
            sb.append(" and PM_PRJ_ID =").append(projectId);
        }
        if (Strings.isNotEmpty(beginTime) && Strings.isNotEmpty(endTime)) {
            sb.append(" and REACH_DATE between ").append(beginTime).append(" and ").append(endTime);
        }

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
            //TODO SQL
            Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("", id);
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("", projectId, sourceName, type);
        List<FundReach> reachList = list.stream().map(m -> this.convertFundReach(m, "1")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(reachList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.list = reachList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 资金到位新增，编辑
     */
    public void fundReachModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        FundReachInput input = JsonUtil.fromJson(json, FundReachInput.class);
        String id = input.id;
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        if (!Strings.isNotEmpty(id)) {
            id = Crud.from("FUND_REACH").insertData();
        }
        //TODO 数据补全
        jdbcTemplate.update("", id);
    }

    /**
     * 资金到位删除
     */
    public void fundReachDel() {
        RequestParam param = this.getRequestParam();
        String id = param.id;
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        jdbcTemplate.update("", id);
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
        //TODO 数据完善
        FundReach fundReach = new FundReach();
        fundReach.id = JdbcMapUtil.getString(data, "ID");
        fundReach.firstTypeName = JdbcMapUtil.getString(data, "");
        fundReach.secondTypeName = JdbcMapUtil.getString(data, "");
        fundReach.projectName = JdbcMapUtil.getString(data, "");
        fundReach.sourceName = JdbcMapUtil.getString(data, "");
        fundReach.reachCategory = JdbcMapUtil.getString(data, "");
        fundReach.amt = JdbcMapUtil.getString(data, "");
        fundReach.reachDate = JdbcMapUtil.getString(data, "");
        if (Objects.equals("2", mark)) {
            List<File> fileList = FileCommon.getFileResp("", myJdbcTemplate);
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
        public String projectName;
        public String sourceName;
        public String reachCategory;
        public String amt;
        public String reachDate;
        //文件集合
        public List<File> fileList;
    }

    /**
     * 返回值包装类
     */
    public static class OutSide {
        public Integer total;
        public List<FundReach> list;
    }


}
