package com.cisdi.ext.proPlan;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.proPlan.model.HeaderObj;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmLifeCycleExt
 * @package com.cisdi.ext.proPlan
 * @description 项目推进计划
 * @date 2023/5/10
 */
public class PmLifeCycleExt {


    /**
     * 前期岗人员
     */
    public void earlyStageUserList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from ad_user where id in (select AD_USER_ID from pm_roster where POST_INFO_ID='1633731474912055296' and AD_USER_ID is not null GROUP BY AD_USER_ID)");
        List<ObjInfo> objInfoList = list.stream().map(p -> {
            ObjInfo info = new ObjInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.name = JdbcMapUtil.getString(p, "NAME");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.objInfoList = objInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 查询列头
     */
    public void getColumnList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> strList = myJdbcTemplate.queryForList("select `NAME`,ifnull(IZ_DISPLAY,0) as IZ_DISPLAY from STANDARD_NODE_NAME where `LEVEL`=3 order by SEQ_NO");
        List<HeaderObj> columnList = strList.stream().map(p -> {
            HeaderObj obj = new HeaderObj();
            obj.name = JdbcMapUtil.getString(p, "NAME");
            obj.izDisplay = JdbcMapUtil.getString(p, "IZ_DISPLAY");
            return obj;
        }).collect(Collectors.toList());

        columnList.add(0, HeaderObj.builder().name("ID").izDisplay("0").build());
        columnList.add(1, HeaderObj.builder().name("项目名称").izDisplay("1").build());
        columnList.add(2, HeaderObj.builder().name("前期手续经办人").izDisplay("1").build());

        if (CollectionUtils.isEmpty(columnList)) {
            OutSide outSide = new OutSide();
            outSide.columnList = columnList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            OutSide outSide = new OutSide();
            outSide.columnList = columnList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 项目推进计划列表查询
     */
    public void pmLifeCycleList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String projectName = String.valueOf(map.get("projectName"));
        String projectType = String.valueOf(map.get("projectType"));
        String userId = String.valueOf(map.get("userId"));
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
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //header
        List<Map<String, Object>> strList = myJdbcTemplate.queryForList("select `NAME`,ifnull(IZ_DISPLAY,0) as IZ_DISPLAY from STANDARD_NODE_NAME where `LEVEL`=3 order by SEQ_NO ");

        List<String> headerList = strList.stream().map(p -> JdbcMapUtil.getString(p, "NAME")).collect(Collectors.toList());

        headerList.add(0, "ID");
        headerList.add(1, "项目名称");
        headerList.add(2, "前期手续经办人");

        //数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<Map<String, Object>> nodeList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            List<String> ids = list.stream().map(p -> JdbcMapUtil.getString(p, "id")).collect(Collectors.toList());
            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
            Map<String, Object> queryParams = new HashMap<>();// 创建入参map
            queryParams.put("ids", ids);
            nodeList = myNamedParameterJdbcTemplate.queryForList("select pn.*,pl.PM_PRJ_ID,gsv.`NAME` as status_name from pm_pro_plan_node pn " +
                    "left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id " +
                    "left join gr_set_value gsv on gsv.id = pn.PROGRESS_STATUS_ID " +
                    "where pl.IS_TEMPLATE <>1 and pl.PM_PRJ_ID in (:ids)  ", queryParams);
        }


        for (Map<String, Object> stringObjectMap : list) {
            Map<String, Object> newData = new HashMap<>();
            for (String s : headerList) {
                if ("项目名称".equals(s)) {
                    JSONObject json = new JSONObject();
                    json.put("nameOrg", stringObjectMap.get("project_name"));
                    newData.put("项目名称", json);
                } else if ("ID".equals(s)) {
                    JSONObject json = new JSONObject();
                    json.put("nameOrg", stringObjectMap.get("id"));
                    newData.put("ID", json);
                } else if ("前期手续经办人".equals(s)) {
                    JSONObject json = new JSONObject();
                    json.put("nameOrg", stringObjectMap.get("qqusers"));
                    newData.put("ID", json);
                } else {
                    Optional<Map<String, Object>> optional = nodeList.stream().filter(p -> Objects.equals(stringObjectMap.get("id"), p.get("PM_PRJ_ID")) && Objects.equals(s, p.get("NAME"))).findAny();
                    if (optional.isPresent()) {
                        Map<String, Object> dataMap = optional.get();
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
                                statusOrg = "已完成";
                            } else if ("未涉及".equals(status)) {
                                nameOrg = "未涉及";
                                statusOrg = "未涉及";
                            } else if ("进行中".equals(status)) {
                                nameOrg = "计划完成";
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                    dateOrg = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
                                }
                                statusOrg = "进行中";

                            } else if ("未启动".equals(status)) {
                                nameOrg = "计划完成";
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                    dateOrg = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
                                }
                                statusOrg = "未启动";
                            }

                            int days = 0;
                            if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE")) && Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                Date plan = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                Date actual = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE"));
                                try {
                                    days = DateTimeUtil.daysBetween(plan, actual);
                                } catch (Exception ignored) {
                                }
                            }

                            if ("未涉及".equals(status)) {
                                tips = "项目未涉及" + JdbcMapUtil.getString(dataMap, "NAME");
                            } else {
                                if (days > 0) {
                                    tips = "提前" + days + "完成！";
                                } else if (days < 0) {
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

        if (CollectionUtils.isEmpty(dataList)) {
            OutSide outSide = new OutSide();
            outSide.headerList = headerList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.dataList = dataList;
            outSide.headerList = headerList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }

    }


    public static class ObjInfo {
        public String id;
        public String name;

    }


    public static class OutSide {
        public List<ObjInfo> objInfoList;

        public List<HeaderObj> columnList;

        public List<String> headerList;

        public Integer total;

        public List<Map<String, Object>> dataList;
    }
}
