package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.ProjectPostModel;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectPostController
 * @package com.cisdi.pms.job.excel.export
 * @description 项目岗位导出
 * @date 2023/2/13
 */
@RestController
@RequestMapping("projectPost")
public class ProjectPostExportController extends BaseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void exportExcel(String projectName, HttpServletResponse response) {
        StringBuffer sb = new StringBuffer();
        sb.append("select pj.id as id, pj.`NAME` as project_name,GROUP_CONCAT(pi.`NAME`) as post from pm_prj pj  \n" +
                " left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID  \n" +
                " left join post_info pi on pp.POST_INFO_ID = pi.id \n" +
                " where pj.`STATUS`='ap' and pj.IZ_FORMAL_PRJ='1' and pj.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' ");
        if (!StringUtils.isEmpty(projectName)) {
            sb.append(" and pj.`NAME` like '%").append(projectName).append("%'");
        }
        sb.append("group by pj.id ");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<ProjectPostModel> modelList = list.stream().map(p -> {
            ProjectPostModel model = new ProjectPostModel();
            model.setProjectName(JdbcMapUtil.getString(p, "project_name"));
            model.setPostName(JdbcMapUtil.getString(p, "post"));
            return model;
        }).collect(Collectors.toList());
        super.setExcelRespProp(response, "项目岗位");
        EasyExcel.write(response.getOutputStream())
                .head(ProjectPostModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目岗位")
                .doWrite(modelList);
    }
}
