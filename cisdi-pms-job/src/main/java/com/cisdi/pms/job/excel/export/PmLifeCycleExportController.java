package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.utils.DateUtil;
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
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmLifeCycleExportController
 * @package com.cisdi.pms.job.excel.export
 * @description 项目推进计划
 * @date 2023/5/10
 */
@RestController
@RequestMapping("pmLifeCycle")
public class PmLifeCycleExportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void exportExcel(String projectName, String projectType, String userId, HttpServletResponse response) {
        StringBuilder sb = new StringBuilder();
        sb.append("select pj.id as id, pj.`NAME` as project_name,au.`NAME` as qquser from pm_prj pj \n" +
                "left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID and pp.POST_INFO_ID='1633731474912055296'\n" +
                "left join ad_user au on pp.AD_USER_ID = au.id \n" +
                "where pj.`STATUS`='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' \n");
        sb.append(" and pj.PROJECT_TYPE_ID ='").append(projectType).append("'");
        if (!StringUtils.isEmpty(projectName)) {
            sb.append(" and pj.name like '%").append(projectName).append("%'");
        }
        if (!StringUtils.isEmpty(userId)) {
            sb.append(" and au.id = '").append(userId).append("'");
        }
        sb.append(" and pj.pm_code is not null ");
        sb.append("group by pj.id,au.`NAME` order by pj.pm_code desc");

        //header
        List<Map<String, Object>> strList = jdbcTemplate.queryForList("select `NAME` from pm_pro_plan_node where `NAME` is not null and `LEVEL`=3 GROUP BY `NAME`");

        List<String> headerList = strList.stream().map(p -> JdbcMapUtil.getString(p, "NAME")).collect(Collectors.toList());
        headerList.add(0, "项目名称");
        headerList.add(1, "前期手续经办人");

        //数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());

        List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select pn.*,pl.PM_PRJ_ID,gsv.`NAME` as status_name from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id left join gr_set_value gsv on gsv.id = pn.PROGRESS_STATUS_ID where pl.IS_TEMPLATE <>1");

        for (Map<String, Object> stringObjectMap : list) {
            Map<String, Object> newData = new HashMap<>();
            for (String s : headerList) {
                if ("项目名称".equals(s)) {
                    newData.put("项目名称", stringObjectMap.get("project_name"));
                } else if ("ID".equals(s)) {
                    newData.put("ID", stringObjectMap.get("id"));
                } else if ("前期手续经办人".equals(s)) {
                    newData.put("ID", stringObjectMap.get("qqusers"));
                } else {
                    Optional<Map<String, Object>> optional = nodeList.stream().filter(p -> Objects.equals(stringObjectMap.get("id"), p.get("PM_PRJ_ID")) && Objects.equals(s, p.get("NAME"))).findAny();
                    if (optional.isPresent()) {
                        Map<String, Object> dataMap = optional.get();
                        JSONObject json = new JSONObject();
                        if (Objects.nonNull(dataMap.get("status_name"))) {
                            String status = JdbcMapUtil.getString(dataMap, "status_name");
                            String nameOrg = "";
                            Date dateOrg = null;
                            String statusOrg = "";
                            String tips = null;
                            if ("已完成".equals(status)) {
                                nameOrg = "实际完成";
                                if (Objects.nonNull(dataMap.get("ACTUAL_COMPL_DATE"))) {
                                    dateOrg = DateUtil.stringToDate(JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE"));
                                }
                                statusOrg = "已完成";
                            } else if ("未涉及".equals(status)) {
                                nameOrg = "未涉及";
                                statusOrg = "未涉及";
                            } else if ("进行中".equals(status)) {
                                nameOrg = "计划完成";
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                    dateOrg = DateUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                }
                                statusOrg = "进行中";

                            } else if ("未开始".equals(status)) {
                                nameOrg = "计划完成";
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                    dateOrg = DateUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                }
                                statusOrg = "进行中";
                            }

                            int days = 0;
                            if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE")) && Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                Date plan = DateUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                Date actual = DateUtil.stringToDate(JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE"));
                                try {
                                    days = DateUtil.daysBetween(plan, actual);
                                } catch (Exception ignored) {
                                }
                            }

                            if ("未涉及".equals(status)) {
                                tips = "项目未涉及" + JdbcMapUtil.getString(dataMap, "NAME");
                            } else {
                                if (days > 0) {
                                    tips = "提前" + days + "完成！";
                                } else {
                                    tips = "超期" + Math.abs(days) + "天";
                                }
                            }

                            json.put("nameOrg", nameOrg);
                            json.put("dateOrg", dateOrg);
                            json.put("statusOrg", statusOrg);
                            json.put("tips", tips);
                        }
                        newData.put(s, json);
                    } else {
                        newData.put(s, "");
                    }
                }
            }
            dataList.add(newData);
        }

        super.setExcelRespProp(response, "项目推进计划");
        EasyExcel.write(response.getOutputStream())
                .head(this.getHeader(headerList))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目推进计划")
                .doWrite(dataList);
    }


    /**
     * 表头
     */
    private List<List<String>> getHeader(List<String> headerList) {
        List<List<String>> headTitles = new ArrayList<>();
        for (String header : headerList) {
            List<String> secondHeader = new ArrayList<>();
            secondHeader.add(header);
            headTitles.add(secondHeader);
        }
        return headTitles;
    }
}
