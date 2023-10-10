package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.EquipProjectExportModel;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title EquipProjectExportController
 * @package com.cisdi.pms.job.excel.export
 * @description
 * @date 2023/10/9
 */
@RestController
@RequestMapping("equipProject")
public class EquipProjectExportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void exportExcel(String eqPrjName, String prjName, String researchRange, HttpServletResponse response) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("select pm.id as eq_id,pm.pm_code as eq_code,pm.`NAME` as eq_name,pp.`NAME` as owner_name,pm.EQUIPMENT_PURCHASE_BUDGET_AMOUNT as eq_amt,gs.`NAME` as eq_range,pm.PRJ_SITUATION as eq_situation\n" +
                "from pm_prj pm left join pm_prj pp on pm.SUBORDINATE_PROJECT = pp.ID\n" +
                "left join gr_set_value gs on gs.id = pm.RESEARCH_RANGE\n" +
                "where pm.PROJECT_CLASSIFICATION_ID ='1704686735267102720' ");
        if (StringUtils.hasText(eqPrjName)) {
            sb.append(" and pm.`NAME` like '%").append(eqPrjName).append("%'");
        }
        if (StringUtils.hasText(prjName)) {
            sb.append(" and pp.`NAME` like '%").append(prjName).append("%'");
        }
        if (StringUtils.hasText(researchRange)) {
            sb.append(" and pm.RESEARCH_RANGE ='").append(researchRange).append("'");
        }
        sb.append(" order by pm.PM_CODE desc ");

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<EquipProjectExportModel> modelList = list.stream().map(p -> {
            EquipProjectExportModel model = new EquipProjectExportModel();
            model.setCode(JdbcMapUtil.getString(p, "eq_code"));
            model.setName(JdbcMapUtil.getString(p, "eq_name"));
            model.setOwner(JdbcMapUtil.getString(p, "owner_name"));
            model.setAmt(JdbcMapUtil.getString(p, "eq_amt"));
            model.setRange(JdbcMapUtil.getString(p, "eq_range"));
            model.setDesc(JdbcMapUtil.getString(p, "eq_situation"));
            return model;
        }).collect(Collectors.toList());
        super.setExcelRespProp(response, "科研设备库");
        EasyExcel.write(response.getOutputStream())
                .head(EquipProjectExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("科研设备库")
                .doWrite(modelList);
    }
}
