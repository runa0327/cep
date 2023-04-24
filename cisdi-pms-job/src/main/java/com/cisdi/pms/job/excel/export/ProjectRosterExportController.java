package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectRosterExportController
 * @package com.cisdi.pms.job.excel.export
 * @description
 * @date 2023/2/14
 */
@RestController
@RequestMapping("projectRoster")
public class ProjectRosterExportController extends BaseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void exportExcel(String projectName, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pj.id as id, pj.`NAME` as project_name from pm_prj pj left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID where pj.`STATUS`='ap' ");
        if (!StringUtils.isEmpty(projectName)) {
            sb.append(" and pj.id like '%").append(projectName).append("%'");
        }
        sb.append("group by pj.id ");
        //header
        List<Map<String, Object>> strList = jdbcTemplate.queryForList("select p.`NAME` as PROJECT_POST from PM_ROSTER t  left join post_info p on t.POST_INFO_ID = p.id where p.`NAME` is not null GROUP BY p.id");
        List<String> headerList = strList.stream().map(p -> JdbcMapUtil.getString(p, "PROJECT_POST")).collect(Collectors.toList());
        headerList.add(0, "项目名称");
        headerList.removeAll(Collections.singleton(null));

        //数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        for (Map<String, Object> stringObjectMap : list) {
            Map<String, Object> newData = new HashMap<>();
            for (String s : headerList) {
                if ("项目名称".equals(s)) {
                    newData.put("项目名称", stringObjectMap.get("project_name"));
                } else {
                    String prjId = String.valueOf(stringObjectMap.get("id"));
                    List<Map<String, Object>> rosterList = jdbcTemplate.queryForList("select pp.*,au.`NAME` as user_name from PM_ROSTER pp left join ad_user au on pp.AD_USER_ID = au.id left join post_info pi on pp.POST_INFO_ID = pi.id where PM_PRJ_ID=? and pi.`NAME`=?;", prjId, s);
                    String users = "/";
                    if (!CollectionUtils.isEmpty(rosterList)) {
                        users = rosterList.stream().map(mm -> JdbcMapUtil.getString(mm, "user_name")).collect(Collectors.joining(","));
                        if ("null".equals(users)) {
                            users = "/";
                        }
                    }

                    newData.put(s, users);
                }
            }
            dataList.add(newData);
        }
        super.setExcelRespProp(response, "项目花名册");
        EasyExcel.write(response.getOutputStream())
                .head(EasyExcelUtil.head(headerList))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目花名册")
                .doWrite(EasyExcelUtil.dataList(dataList, headerList));

    }

}
