package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cisdi.pms.job.utils.DateUtil;
import com.qygly.shared.util.DateTimeUtil;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
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
    public void exportExcel(String projectName, String projectType, String userId, String columns, HttpServletResponse response) throws ParseException {
        StringBuilder sb = new StringBuilder();
        sb.append("select po.pm_prj_id as id, pj.`NAME` as project_name,au.`NAME` as qquser from PLAN_OPERATION po  \n" +
                " left join pm_prj pj  on pj.id = po.PM_PRJ_ID\n" +
                " left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID and pp.POST_INFO_ID='1633731474912055296'\n" +
                " left join ad_user au on pp.AD_USER_ID = au.id \n" +
                " where pj.`STATUS`='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and (pj.PROJECT_STATUS <> '1661568714048413696' or pj.PROJECT_STATUS is null) ");
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
        List<String> headerList;
        if (Strings.isNotEmpty(columns)) {
            headerList = Arrays.asList(columns.split(","));
        } else {
            List<Map<String, Object>> strList = jdbcTemplate.queryForList("select `NAME`,ifnull(IZ_DISPLAY,0) as IZ_DISPLAY from STANDARD_NODE_NAME where `LEVEL`=3 order by SEQ_NO ");
            headerList = strList.stream().map(p -> JdbcMapUtil.getString(p, "NAME")).collect(Collectors.toList());
        }
        if (!headerList.contains("项目名称")) {
            headerList.add(0, "项目名称");
        }

        AtomicInteger index = new AtomicInteger(0);
        List<Title> titleList = headerList.stream().map(p -> {
            Title title = new Title();
            title.name = p;
            title.seq = String.valueOf(index.getAndIncrement());
            return title;
        }).collect(Collectors.toList());

        //数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());

        for (Map<String, Object> stringObjectMap : list) {
            Map<String, Object> newData = new HashMap<>();
            for (Title s : titleList) {
                if ("项目名称".equals(s.name)) {
                    JSONObject json = new JSONObject();
                    json.put("nameOrg", stringObjectMap.get("project_name"));
                    json.put("remarkCount", 0);
                    newData.put("项目名称-" + s.seq, json);
                } else {
                    List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select pn.*,pl.PM_PRJ_ID,gsv.`NAME` as status_name from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id \n" +
                            "left join STANDARD_NODE_NAME sn on sn.id = pn.SCHEDULE_NAME \n" +
                            "left join gr_set_value gsv on gsv.id = pn.PROGRESS_STATUS_ID \n" +
                            "where pl.PM_PRJ_ID=? and sn.`NAME`=?", stringObjectMap.get("id"), s.name);
                    if (!CollectionUtils.isEmpty(nodeList)) {
                        Map<String, Object> dataMap = nodeList.get(0);
                        JSONObject json = new JSONObject();
                        if (Objects.nonNull(dataMap.get("status_name"))) {
                            String status = JdbcMapUtil.getString(dataMap, "status_name");
                            String nameOrg = "";
                            String dateOrg = "";
                            String statusOrg = "";
                            String tips = null;
                            if ("已完成".equals(status)) {
                                nameOrg = "实际完成";
                                if (Objects.nonNull(dataMap.get("ACTUAL_COMPL_DATE"))) {
                                    dateOrg = JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE");
                                }
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE")) && Objects.nonNull(dataMap.get("ACTUAL_COMPL_DATE"))) {
                                    Date plan = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                    Date actual = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE"));
                                    int days = DateUtil.daysBetween(plan, actual);
                                    if (plan.before(actual)) {
                                        tips = "超期" + Math.abs(days) + "天";
                                    } else {
                                        tips = "提前" + days + "完成！";
                                    }
                                }
                                statusOrg = "已完成";
                            } else if ("未涉及".equals(status)) {
                                nameOrg = "未涉及";
                                tips = "项目未涉及" + JdbcMapUtil.getString(dataMap, "NAME");
                                statusOrg = "未涉及";
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                    Date planCompDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                    if (planCompDate.before(new Date())) {
                                        int days = DateUtil.daysBetween(planCompDate, new Date());
                                        tips = "超期" + Math.abs(days) + "天";
                                    }
                                    nameOrg = "计划完成";
                                    if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                        dateOrg = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
                                    }
                                }
                            } else {
                                if ("0".equals(JdbcMapUtil.getString(dataMap, "izOverdue"))) {
                                    if ("进行中".equals(status)) {
                                        statusOrg = "进行中";
                                    } else if ("未启动".equals(status)) {
                                        statusOrg = "未启动";
                                    }
                                } else {
                                    statusOrg = "已超期";
                                }
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                    Date planCompDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                    if (planCompDate.before(new Date())) {
                                        int days = DateUtil.daysBetween(planCompDate, new Date());
                                        tips = "超期" + Math.abs(days) + "天";
                                    }
                                    nameOrg = "计划完成";
                                    if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                        dateOrg = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
                                    }
                                }
                            }

                            json.put("nameOrg", nameOrg);
                            json.put("dateOrg", dateOrg);
                            json.put("statusOrg", statusOrg);
                            json.put("tips", tips);
                            int count = getRemarkCount(JdbcMapUtil.getString(stringObjectMap, "ID"), JdbcMapUtil.getString(dataMap, "SCHEDULE_NAME"));
                            json.put("remarkCount", count);
                            json.put("postInfo", JdbcMapUtil.getString(dataMap, "postName") + ":" + (JdbcMapUtil.getString(dataMap, "userName") == null ? "" : JdbcMapUtil.getString(dataMap, "userName")));
                            json.put("nodeId", JdbcMapUtil.getString(dataMap, "ID"));
                        }
                        newData.put(s.name + "-" + s.seq, json);
                    } else {
                        newData.put(s.name + "-" + s.seq, "");
                    }
                }
            }
            dataList.add(newData);
        }

        List<List<Object>> modelList = new ArrayList<>();
        for (Map<String, Object> objectMap : dataList) {
            List<Object> obj = new ArrayList<>();
            for (String key : objectMap.keySet()) {
                String msg = "";
                Object object = objectMap.get(key);
                if (Objects.nonNull(object) && !Objects.equals("", object)) {
                    JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(object));
                    if ("未涉及".equals(jsonObject.getString("statusOrg"))) {
                        msg = jsonObject.getString("statusOrg");
                    } else {
                        if (jsonObject.getString("nameOrg") != null) {
                            String dataOrg = jsonObject.getString("dateOrg") == null ? "" : jsonObject.getString("dateOrg");
                            if (Strings.isNotEmpty(dataOrg)) {
                                msg = jsonObject.getString("nameOrg") + "-" + dataOrg;
                            } else {
                                msg = jsonObject.getString("nameOrg");
                            }

                        }
                    }
                }
                obj.add(Integer.parseInt(key.split("-")[1].split("=")[0]) + "/" + msg);
            }
            modelList.add(obj);
        }

        List<List<Object>> result = modelList.stream().map(m -> m.stream().sorted(Comparator.comparing(p -> Integer.parseInt(String.valueOf(p).split("/")[0]))).collect(Collectors.toList())).collect(Collectors.toList());

        List<List<String>> res = result.stream().map(p -> p.stream().map(m -> {
            String[] split = String.valueOf(m).split("/");
            if (split.length < 2) {
                return "";
            } else {
                return split[1];
            }
        }).collect(Collectors.toList())).collect(Collectors.toList());

        super.setExcelRespProp(response, "项目推进计划");
        EasyExcel.write(response.getOutputStream())
                .head(this.getHeader(headerList))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目推进计划")
                .doWrite(res);
    }


    /**
     * 表头
     */
    private List<List<String>> getHeader(List<String> headerList) {
        List<List<String>> headTitles = new ArrayList<>();
        for (String header : headerList) {
            List<String> secondHeader = new ArrayList<>();
            secondHeader.add(header);
            secondHeader.add(header);
            headTitles.add(secondHeader);
        }
        return headTitles;
    }

    private int getRemarkCount(String projectId, String baseNodeId) {
        int count = 0;
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from remark_info where REMARK_TYPE='2' and PM_PRJ_ID=? and SCHEDULE_NAME=?", projectId, baseNodeId);
        if (!CollectionUtils.isEmpty(list)) {
            count = list.size();
        }
        return count;
    }

    public static class Title {
        public String name;
        public String seq;
    }
}
