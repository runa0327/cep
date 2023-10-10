package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.StarchingProjectExportModel;
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
 * @title StarchingProjectExportController
 * @package com.cisdi.pms.job.excel.export
 * @description
 * @date 2023/10/9
 */
@RestController
@RequestMapping("starchingProject")
public class StarchingProjectExportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void exportExcel(String name, String code, String owner, String type, String location, HttpServletResponse response) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("select pm.id as prj_id,pm.pm_code as prj_code,pm.`NAME` as prj_name,pp.`NAME` as owners,gs.`NAME` as type,gg.`NAME` as location  from pm_prj pm\n" +
                "left join pm_party pp on pm.CUSTOMER_UNIT = pp.id\n" +
                "left join gr_set_value gs on pm.PROJECT_TYPE_ID = gs.id\n" +
                "left join gr_set_value gg on pm.BASE_LOCATION_ID = gg.id\n" +
                "where pm.PROJECT_CLASSIFICATION_ID ='1704686841101975552'");
        if (StringUtils.hasText(name)) {
            sb.append(" and pm.`NAME` like '%").append(name).append("%'");
        }
        if (StringUtils.hasText(code)) {
            sb.append(" and pm.pm_code like '%").append(code).append("%'");
        }
        if (StringUtils.hasText(owner)) {
            sb.append(" and pp.name like '%").append(owner).append("%'");
        }
        if (StringUtils.hasText(type)) {
            sb.append(" and pm.PROJECT_TYPE_ID ='").append(type).append("'");
        }
        if (StringUtils.hasText(location)) {
            sb.append(" and pm.BASE_LOCATION_ID ='").append(location).append("'");
        }

        sb.append(" order by pm.PM_CODE desc ");

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<StarchingProjectExportModel> modelList = list.stream().map(p -> {
            StarchingProjectExportModel model = new StarchingProjectExportModel();
            model.setCode(JdbcMapUtil.getString(p, "prj_code"));
            model.setName(JdbcMapUtil.getString(p, "prj_name"));
            model.setOwner(JdbcMapUtil.getString(p, "owners"));
            model.setType(JdbcMapUtil.getString(p, "type"));
            model.setLocation(JdbcMapUtil.getString(p, "location"));
            return model;
        }).collect(Collectors.toList());
        super.setExcelRespProp(response, "零星项目库");
        EasyExcel.write(response.getOutputStream())
                .head(StarchingProjectExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("零星项目库")
                .doWrite(modelList);
    }
}
