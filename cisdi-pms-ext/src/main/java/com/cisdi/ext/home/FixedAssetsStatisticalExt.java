package com.cisdi.ext.home;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FixedAssetsSta
 * @package com.cisdi.ext.home
 * @description 固定资产投资统计
 * @date 2022/11/25
 */
public class FixedAssetsStatisticalExt {

    public void fixedAssetsStatistical() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));

        Map<String, Object> dataParam = new HashMap<>();
        dataParam.put("ids", Arrays.asList(projectId.split(",")));

        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));

        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();

        StringBuilder sb = new StringBuilder();
        sb.append("select * from pm_prj where status='ap' ");
        if (Strings.isNotEmpty(projectId)) {
            sb.append(" and id in (:ids)");
        }
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), dataParam);

        FixedAssetsResp resp = new FixedAssetsResp();
        Set<String> headerList = new HashSet<>();

        List<FixedAssetsInfo> fixedAssetsInfoList = list.stream().map(this::convertData).collect(Collectors.toList());
        int currentYear = LocalDate.now().getYear();
        for (FixedAssetsInfo info : fixedAssetsInfoList) {
            //总投资
            BigDecimal total = this.getTotalInvest(info.projectId);
            info.total = total.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP);
            //每一年计划投资
            Map<String, BigDecimal> yeaData = new HashMap<>();
            for (int i = 2019; i <= currentYear; i++) {
                String header = i + "年计划投资";
                BigDecimal fee = this.getCompleteInvest(info.projectId, i);
                yeaData.put(header, fee);
                info.yearData = yeaData;
                headerList.add(header);
            }
        }
        resp.headerList = headerList.stream().sorted(Comparator.reverseOrder()).collect(Collectors.toList());
        resp.list = fixedAssetsInfoList;
        List<Map<String, Object>> list1 = myNamedParameterJdbcTemplate.queryForList(totalSql, dataParam);
        resp.total = list1.size();
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resp), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 获取概算总投资
     *
     * @param projectId
     * @return
     */
    public BigDecimal getTotalInvest(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(PRJ_TOTAL_INVEST,0) PRJ_TOTAL_INVEST from PM_INVEST_EST pie \n" +
                "left join gr_set_value gsv on pie.INVEST_EST_TYPE_ID = gsv.id \n" +
                "where  gsv.`CODE`='invest2' and pie.PM_PRJ_ID=?", projectId);
        if (CollectionUtils.isEmpty(list)) {
            return BigDecimal.ZERO;
        }
        return list.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "PRJ_TOTAL_INVEST"))).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    /**
     * 获取完成投资
     *
     * @param project
     * @param year
     * @return
     */
    public BigDecimal getCompleteInvest(String project, int year) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(ARCHITECTURAL_ENGINEERING_FEE,0) ARCHITECTURAL_ENGINEERING_FEE," +
                "ifnull(INSTALLATION_ENGINEERING_FEE,0) INSTALLATION_ENGINEERING_FEE, " +
                "ifnull(EQUIPMENT_PURCHASE_FEE,0) EQUIPMENT_PURCHASE_FEE, " +
                "ifnull(OTHER_FEE,0) OTHER_FEE " +
                "from pm_statistics_fee where year=? and PM_PRJ_ID=?", year, project);
        if (CollectionUtils.isEmpty(list)) {
            return BigDecimal.ZERO;
        }
        BigDecimal jz = list.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "ARCHITECTURAL_ENGINEERING_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal az = list.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "INSTALLATION_ENGINEERING_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal cg = list.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "EQUIPMENT_PURCHASE_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal qt = list.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "OTHER_FEE"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        return jz.add(az).add(cg).add(qt);
    }


    public FixedAssetsInfo convertData(Map<String, Object> data) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        FixedAssetsInfo info = new FixedAssetsInfo();
        info.projectId = JdbcMapUtil.getString(data, "ID");
        info.projectName = JdbcMapUtil.getString(data, "NAME");

        String customerUnit = JdbcMapUtil.getString(data, "CUSTOMER_UNIT");
        if (!StringUtils.isEmpty(customerUnit)) {
            List<Map<String, Object>> unitList = myJdbcTemplate.queryForList("select * from PM_PARTY where id=?", customerUnit);
            if (!CollectionUtils.isEmpty(unitList)) {
                info.projectOwner = JdbcMapUtil.getString(unitList.get(0), "NAME");
            }
        }
        info.description = JdbcMapUtil.getString(data, "PRJ_SITUATION");

        String projectStatus = JdbcMapUtil.getString(data, "PROJECT_PHASE_ID");
        if (!StringUtils.isEmpty(projectStatus)) {
            List<Map<String, Object>> statusList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id \n" +
                    "where gs.`CODE`='project_phase' and gsv.id=?", projectStatus);
            if (!CollectionUtils.isEmpty(statusList)) {
                info.projectStatus = JdbcMapUtil.getString(statusList.get(0), "NAME");
            }
        }
        String projectType = JdbcMapUtil.getString(data, "PROJECT_TYPE_ID");
        if (!StringUtils.isEmpty(projectType)) {
            List<Map<String, Object>> typeList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id \n" +
                    "where gs.`CODE`='project_type' and gsv.id=?", projectType);
            if (!CollectionUtils.isEmpty(typeList)) {
                info.industryAttributes = JdbcMapUtil.getString(typeList.get(0), "NAME");
            }
        }

        String baseLocation = JdbcMapUtil.getString(data, "BASE_LOCATION_ID");
        if (!StringUtils.isEmpty(baseLocation)) {
            List<Map<String, Object>> locationList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gsv.GR_SET_ID = gs.id \n" +
                    "where gs.`CODE`='base_location' and gsv.id=?", baseLocation);
            if (!CollectionUtils.isEmpty(locationList)) {
                info.constructionSite = JdbcMapUtil.getString(locationList.get(0), "NAME");
            }
        }
        info.approvalTime = JdbcMapUtil.getString(data, "PRJ_REPLY_DATE");
        info.completionTime = JdbcMapUtil.getString(data, "");
        return info;
    }

    public static class FixedAssetsResp {

        public List<String> headerList;

        public List<FixedAssetsInfo> list;

        public Integer total;
    }


    public static class FixedAssetsInfo {
        //项目ID
        public String projectId;

        //项目名称
        public String projectName;

        //项目业主
        public String projectOwner;

        //建设规模与内容
        public String description;

        //建设性质
        public String projectStatus;

        //行业属性
        public String industryAttributes;

        //建设地点
        public String constructionSite;


        //计划开工
        public String approvalTime;


        //竣工/计划竣工
        public String completionTime;

        //总投资
        public BigDecimal total;

        /**
         * 往年计划投资
         */
        public Map<String, BigDecimal> yearData;
    }
}
