package com.cisdi.ext.home;

import com.cisdi.ext.invest.InvestEstActCompareExt;
import com.cisdi.ext.pm.PmStartExt;
import com.cisdi.ext.util.BigDecimalUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectHomeExt
 * @package com.cisdi.ext.home
 * @description 项目首页接口
 * @date 2023/2/28
 */
public class ProjectHomeExt {

    /**
     * 项目总投资
     */
    public void totalInvest() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        BigDecimal totalAmt = BigDecimal.ZERO;
        BigDecimal planAmt = BigDecimal.ZERO;
        BigDecimal completeAmt = BigDecimal.ZERO;
        List<Map<String, Object>> dataList = myJdbcTemplate.queryForList("select ifnull(pie.PRJ_TOTAL_INVEST,0) as PRJ_TOTAL_INVEST from pm_invest_est pie \n" +
                "left join gr_set_value gsv on gsv.id = pie.INVEST_EST_TYPE_ID \n" +
                "where pie.PM_PRJ_ID=? \n" +
                "order by gsv.code desc limit 0,1", projectId);
        if(!CollectionUtils.isEmpty(dataList)){
            totalAmt = new BigDecimal(String.valueOf(dataList.get(0).get("PRJ_TOTAL_INVEST")));
        }

        //今年计划投资-取投资计划汇总
        int currentYear = LocalDate.now().getYear();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(AMT,0) as AMT from PM_INVESTMENT_YEAR_PLAN where PM_PRJ_ID =? and `year`=?", projectId, currentYear);
        if (!CollectionUtils.isEmpty(list)) {
            planAmt = new BigDecimal(String.valueOf(list.get(0).get("AMT")));
        }

        //今年完成投资-取纳统汇总
        String sql = " select id,\n" +
                "        PM_PRJ_ID,\n" +
                "        year,\n" +
                "        month,\n" +
                "        ifnull(architectural_engineering_fee,0) as architectural_engineering_fee,\n" +
                "        ifnull(installation_engineering_fee,0) as installation_engineering_fee,\n" +
                "        ifnull(equipment_purchase_fee,0) as equipment_purchase_fee,\n" +
                "        ifnull(other_fee,0) as other_fee,\n" +
                "        ifnull(this_month_investment,0) as this_month_investment ,\n" +
                "        ifnull(residential,0) as residential,\n" +
                "        ifnull(purchase_old_equipment,0) as purchase_old_equipment,\n" +
                "        ifnull(purchase_old_building,0) as purchase_old_building,\n" +
                "        ifnull(construction_land_charge,0) as construction_land_charge,\n" +
                "        ifnull(this_year_investment,0) as this_year_investment\n" +
                "        from PM_STATISTICS_FEE\n" +
                "        where PM_PRJ_ID=? and year=? " +
                "        order by month";
        List<Map<String, Object>> feeList = myJdbcTemplate.queryForList(sql, projectId, currentYear);
        if (!CollectionUtils.isEmpty(feeList)) {
            completeAmt = BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("architectural_engineering_fee")))
                    .add(BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("installation_engineering_fee"))))
                    .add(BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("equipment_purchase_fee"))))
                    .add(BigDecimalUtil.stringToBigDecimal(String.valueOf(feeList.get(0).get("other_fee"))));
        }

        OutSide outSide = new OutSide();
        outSide.totalAmt = totalAmt;
        outSide.planAmt = planAmt;
        outSide.completeAmt = completeAmt;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    /**
     * 获取轮播图
     */
    public void getCarousel() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select PRJ_IMG from pm_prj where id=?", projectId);
        List<FileObj> fileObjList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            String fileIds = JdbcMapUtil.getString(list.get(0), "PRJ_IMG");
            if (Strings.isNotEmpty(fileIds)) {
                List<String> ids = Arrays.asList(fileIds.split(","));
                MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
                Map<String, Object> queryParams = new HashMap<>();// 创建入参map
                queryParams.put("ids", ids);
                List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
                AtomicInteger index = new AtomicInteger(0);
                fileObjList = fileList.stream().map(p -> {
                    FileObj obj = new FileObj();
                    obj.num = index.getAndIncrement() + 1;
                    obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
                    obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
                    obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
                    obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                    obj.id = JdbcMapUtil.getString(p, "ID");
                    obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                    obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                    return obj;
                }).collect(Collectors.toList());
            }
            if (CollectionUtils.isEmpty(fileObjList)) {
                ExtJarHelper.returnValue.set(Collections.emptyMap());
            } else {
                OutSide outSide = new OutSide();
                outSide.fileObjList = fileObjList;
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            }
        }
    }


    public static class OutSide {
        public BigDecimal totalAmt;

        public BigDecimal planAmt;

        public BigDecimal completeAmt;

        public List<FileObj> fileObjList;

    }

    public static class FileObj {
        //序号
        public Integer num;
        //文件名称
        public String fileName;
        //文件大小
        public String fileSize;
        //上传人
        public String uploadUser;
        //上传时间
        public String uploadDate;

        public String id;

        public String viewUrl;

        public String downloadUrl;

    }
}
