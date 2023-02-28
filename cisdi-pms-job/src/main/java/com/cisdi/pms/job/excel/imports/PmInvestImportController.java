package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.PmStatisticsFeeModel;
import com.cisdi.pms.job.excel.model.PmYearInvestModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmInvestImport
 * @package com.cisdi.pms.job.excel.imports
 * @description 投资相关导入
 * @date 2023/2/27
 */
@RestController
@RequestMapping("/invest")
public class PmInvestImportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 项目投资模板下载
     *
     * @param response
     */
    @SneakyThrows(IOException.class)
    @GetMapping("investTemplate")
    public void investTemplate(HttpServletResponse response) {
        super.setExcelRespProp(response, "项目投资");
        EasyExcel.write(response.getOutputStream())
                .head(PmYearInvestModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目投资")
                .doWrite(new ArrayList<>());
    }


    /**
     * 项目投资导入
     *
     * @param file
     * @return
     */
    @SneakyThrows(IOException.class)
    @RequestMapping("/investImport")
    public Map<String, Object> investImport(MultipartFile file, HttpServletResponse response) {
        List<String> res = new ArrayList<>();
        List<PmYearInvestModel> investModelList = EasyExcelUtil.read(file.getInputStream(), PmYearInvestModel.class);
        //去除空行
        List<PmYearInvestModel> modelList = investModelList.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");

        Map<String, List<PmYearInvestModel>> projectMap = modelList.stream().collect(Collectors.groupingBy(PmYearInvestModel::getProjectName));
        for (String projectName : projectMap.keySet()) {
            //项目 先判断项目没有对应项目直接返回
            Optional<Map<String, Object>> optional = prjList.stream().filter(p -> String.valueOf(p.get("name")).equals(projectName)).findAny();
            if (!optional.isPresent()) {
                res.add("项目名称为:" + projectName + "不存在，未导入！");
                continue;
            }
            String projectId = String.valueOf(optional.get().get("ID"));
            Map<Integer, List<PmYearInvestModel>> yearMap = projectMap.get(projectName).stream().collect(Collectors.groupingBy(PmYearInvestModel::getYear));
            for (int year : yearMap.keySet()) {
                List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_INVESTMENT_YEAR_PLAN where `YEAR`=? and PM_PRJ_ID=?", year, projectId);
                String yearId = "";
                if (CollectionUtils.isEmpty(list)) {
                    yearId = Util.insertData(jdbcTemplate, "PM_INVESTMENT_YEAR_PLAN");
                } else {
                    yearId = String.valueOf(list.get(0).get("ID"));
                }
                BigDecimal total = modelList.stream().map(PmYearInvestModel::getAmt).reduce(BigDecimal.ZERO, BigDecimal::add);
                jdbcTemplate.update("update PM_INVESTMENT_YEAR_PLAN set PM_PRJ_ID=?,`YEAR`=?,AMT=? where id=?", projectId, year, total,yearId);
                //处理明细
                jdbcTemplate.update("delete from PM_INVESTMENT_MONTH_PLAN where PM_INVESTMENT_YEAR_PLAN_ID=?", yearId);
                for (PmYearInvestModel p : modelList) {
                    String monthId = Util.insertData(jdbcTemplate, "PM_INVESTMENT_MONTH_PLAN");
                    jdbcTemplate.update("update PM_INVESTMENT_MONTH_PLAN set PM_INVESTMENT_YEAR_PLAN_ID=?,`MONTH`=?,AMT=? where id=?", yearId, p.getMonth(), p.getAmt(), monthId);
                }
            }
        }
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "项目岗位导入日志");
            return null;
        }
    }


    /**
     * 纳统填报导入模板下载
     *
     * @param response
     */
    @SneakyThrows(IOException.class)
    @GetMapping("ntTemplate")
    public void ntTemplate(HttpServletResponse response) {
        super.setExcelRespProp(response, "纳统填报");
        EasyExcel.write(response.getOutputStream())
                .head(PmStatisticsFeeModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("纳统填报")
                .doWrite(new ArrayList<>());
    }


    /**
     * 纳统填报导入
     *
     * @param file
     * @param response
     * @return
     */
    @SneakyThrows(IOException.class)
    @RequestMapping("/ntImport")
    public Map<String, Object> ntImport(MultipartFile file, HttpServletResponse response) {
        List<String> res = new ArrayList<>();
        List<PmStatisticsFeeModel> pmStatisticsFeeModels = EasyExcelUtil.read(file.getInputStream(), PmStatisticsFeeModel.class);
        List<PmStatisticsFeeModel> modelList = pmStatisticsFeeModels.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        Map<String, List<PmStatisticsFeeModel>> projectMap = modelList.stream().collect(Collectors.groupingBy(PmStatisticsFeeModel::getProjectName));
        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");
        for (String s : projectMap.keySet()) {
            //项目 先判断项目没有对应项目直接返回
            Optional<Map<String, Object>> optional = prjList.stream().filter(p -> String.valueOf(p.get("name")).equals(s)).findAny();
            if (!optional.isPresent()) {
                res.add("项目名称为:" + s + "不存在，未导入！");
                continue;
            }
            String projectId = String.valueOf(optional.get().get("ID"));
            Map<Integer, List<PmStatisticsFeeModel>> yearMap = projectMap.get(s).stream().collect(Collectors.groupingBy(PmStatisticsFeeModel::getYear));
            for (int year : yearMap.keySet()) {
                jdbcTemplate.update("delete from PM_STATISTICS_FEE where `YEAR`=? and PM_PRJ_ID=? ", year,projectId);
                for (PmStatisticsFeeModel p : modelList) {
                    String monthId = Util.insertData(jdbcTemplate, "PM_STATISTICS_FEE");
                    jdbcTemplate.update("update PM_STATISTICS_FEE set PM_PRJ_ID=?," +
                                    "`YEAR`=?, " +
                                    "`MONTH`=?," +
                                    "THIS_YEAR_INVESTMENT=?," +
                                    "THIS_MONTH_INVESTMENT=?," +
                                    "RESIDENTIAL=?," +
                                    "ARCHITECTURAL_ENGINEERING_FEE=?," +
                                    "INSTALLATION_ENGINEERING_FEE=?," +
                                    "EQUIPMENT_PURCHASE_FEE=?," +
                                    "PURCHASE_OLD_EQUIPMENT=?," +
                                    "OTHER_FEE=?," +
                                    "PURCHASE_OLD_BUILDING=?," +
                                    "CONSTRUCTION_LAND_CHARGE=?" +
                                    "where id=?", projectId, year, p.getMonth(), p.getThisYearInvestment(), p.getThisMonthInvestment(), p.getResidential(), p.getArchitecturalEngineeringFee(),
                            p.getInstallationEngineeringFee(), p.getEquipmentPurchaseFee(), p.getPurchaseOldEquipment(), p.getOtherFee(), p.getPurchaseOldBuilding(), p.getConstructionLandCharge(), monthId);
                }
            }

        }
        Map<String, Object> result = new HashMap<>();
        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "项目纳统填报日志");
            return null;
        }
    }

}
