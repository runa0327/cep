package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.write.style.column.SimpleColumnWidthStyleStrategy;
import com.cisdi.pms.job.excel.model.PmPrjExportModel;
import com.cisdi.pms.job.excel.model.request.FixedAssetRequest;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2022/11/29 周二
 */
@RestController
@RequestMapping("fixedAsset")
public class FixedAssetsStatisticalController extends BaseController {

    @Autowired
    private JdbcTemplate myJdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate myNamedParameterJdbcTemplate;


    @SneakyThrows
    @PostMapping("/export")
    public void fixedAssetsStatistical(@RequestBody FixedAssetRequest request, HttpServletResponse response) {

        List<String> projectIds = request.getProjectIds();

        StringBuilder sb = new StringBuilder();
        sb.append("select * from pm_prj where status='ap' ");
        Map<String, Object> dataParam = new HashMap<>();
        if (!CollectionUtils.isEmpty(projectIds)) {
            dataParam.put("ids", projectIds);
            sb.append(" and id in (:ids)");
        }
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


        List<List> modelList = new ArrayList<>();
        for (FixedAssetsInfo fixedAssetsInfo : fixedAssetsInfoList) {
            modelList.add(this.getList(fixedAssetsInfo));
        }
        super.setExcelRespProp(response,"固定资产计划");
        EasyExcel.write(response.getOutputStream())
                .head(this.getHeader(resp.headerList))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("固定资产计划")
                .registerWriteHandler(new SimpleColumnWidthStyleStrategy(30))
                .doWrite(modelList);
    }

    /**
     * 表头
     */
    private List<List<String>> getHeader(List<String> headerList) {
        List<List<String>> headTitles = new ArrayList<>();
        headTitles.add(Arrays.asList("项目名称","项目名称"));
        headTitles.add(Arrays.asList("项目业主","项目业主"));
        headTitles.add(Arrays.asList("建设规模与内容","建设规模与内容"));
        headTitles.add(Arrays.asList("建设性质","建设性质"));
        headTitles.add(Arrays.asList("行业属性","行业属性"));
        headTitles.add(Arrays.asList("建设地点","建设地点"));
        headTitles.add(Arrays.asList("开工或计划开工","开工或计划开工"));
        headTitles.add(Arrays.asList("计划竣工年月","计划竣工年月"));
        headTitles.add(Arrays.asList("总投资","总投资"));

        //往年计划投资
        for (String yearHeader : headerList) {
            List<String> secondHeader = new ArrayList<>();
            secondHeader.add("往年计划投资");
            secondHeader.add(yearHeader);
            headTitles.add(secondHeader);
        }
        return headTitles;
    }


    /**
     * 导出内容单元格
     * @param info
     * @return
     */
    private List getList(FixedAssetsInfo info){
        List<Object> infoList = new ArrayList<>();
        infoList.add(info.projectName);
        infoList.add(info.projectOwner);
        infoList.add(info.description);
        infoList.add(info.projectStatus);
        infoList.add(info.industryAttributes);
        infoList.add(info.constructionSite);
        infoList.add(info.approvalTime);
        infoList.add(info.completionTime);
        infoList.add(info.total);
        Map<String, BigDecimal> yearData = info.yearData;
        for (String key : yearData.keySet()) {
            infoList.add(yearData.get(key));
        }
        return infoList;
    }
    /**
     * 获取概算总投资
     *
     * @param projectId
     * @return
     */
    public BigDecimal getTotalInvest(String projectId) {
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
