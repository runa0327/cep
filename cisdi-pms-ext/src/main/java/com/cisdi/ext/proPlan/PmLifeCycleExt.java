package com.cisdi.ext.proPlan;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.proPlan.model.HeaderObj;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.text.ParseException;
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
        columnList.add(3, HeaderObj.builder().name("备注说明").izDisplay("1").build());

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
    public void pmLifeCycleList() throws ParseException {
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
        headerList.add(3, "备注说明");

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
                    json.put("remarkCount", 0);
                    newData.put("项目名称", json);
                } else if ("ID".equals(s)) {
                    JSONObject json = new JSONObject();
                    json.put("nameOrg", stringObjectMap.get("id"));
                    json.put("remarkCount", 0);
                    newData.put("ID", json);
                } else if ("前期手续经办人".equals(s)) {
                    JSONObject json = new JSONObject();
                    json.put("nameOrg", stringObjectMap.get("qquser"));
                    json.put("remarkCount", 0);
                    newData.put("前期手续经办人", json);
                } else if ("备注说明".equals(s)) {
                    JSONObject json = new JSONObject();
                    List<String> contentList = new ArrayList<>();
                    List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from REMARK_INFO where REMARK_TYPE='1' and PM_PRJ_ID=?", stringObjectMap.get("id"));
                    if (!CollectionUtils.isEmpty(list1)) {
                        contentList = list1.stream().map(m -> JdbcMapUtil.getString(m, "CONTENT")).collect(Collectors.toList());
                    }
                    int reCount = 0;
                    List<Map<String, Object>> reList = myJdbcTemplate.queryForList("select * from remark_info where REMARK_TYPE='1' and PM_PRJ_ID=?", stringObjectMap.get("id"));
                    if (CollectionUtils.isEmpty(reList)) {
                        reCount = reList.size();
                    }
                    json.put("nameOrg", contentList);
                    json.put("remarkCount", reCount);
                    newData.put("备注说明", json);
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
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE")) && Objects.nonNull(dataMap.get("ACTUAL_COMPL_DATE"))) {
                                    Date plan = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                    Date actual = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE"));
                                    int days = DateTimeUtil.daysBetween(plan, actual);
                                    if (days > 0) {
                                        tips = "提前" + days + "完成！";
                                    } else if (days < 0) {
                                        tips = "超期" + Math.abs(days) + "天";
                                    }
                                }
                                statusOrg = "已完成";
                            } else if ("未涉及".equals(status)) {
                                nameOrg = "未涉及";
                                tips = "项目未涉及" + JdbcMapUtil.getString(dataMap, "NAME");
                                statusOrg = "未涉及";
                            } else {
                                if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                    Date planCompDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                    if (planCompDate.before(new Date())) {
                                        statusOrg = "已超期";
                                        if (Objects.nonNull(dataMap.get("PLAN_COMPL_DATE"))) {
                                            Date plan = DateTimeUtil.stringToDate(JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE"));
                                            int days = DateTimeUtil.daysBetween(plan, new Date());
                                            tips = "超期" + Math.abs(days) + "天";
                                        }
                                    } else {
                                        if ("进行中".equals(status)) {
                                            statusOrg = "进行中";
                                        } else if ("未启动".equals(status)) {
                                            statusOrg = "未启动";
                                        }
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


    private int getRemarkCount(String projectId, String baseNodeId) {
        int count = 0;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from remark_info where REMARK_TYPE='2' and PM_PRJ_ID=? and SCHEDULE_NAME=?", projectId, baseNodeId);
        if (!CollectionUtils.isEmpty(list)) {
            count = list.size();
        }
        return count;
    }

    /**
     * 查看项目备注说明/节点备注说明
     */
    public void checkPmRemark() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sql = new StringBuilder();
        if ("1".equals(JdbcMapUtil.getString(map, "type"))) {
            sql.append("select ri.*,au.`NAME` as user_name from REMARK_INFO ri left join ad_user au on ri.AD_USER_ID = au.id where REMARK_TYPE='1' and PM_PRJ_ID='").append(map.get("projectId")).append("'");
        } else if ("2".equals(JdbcMapUtil.getString(map, "type"))) {
            sql.append("select ri.*,au.`NAME` as user_name from REMARK_INFO ri left join ad_user au on ri.AD_USER_ID = au.id left join STANDARD_NODE_NAME sn on sn.id = ri.SCHEDULE_NAME");
            sql.append(" where REMARK_TYPE='2' and PM_PRJ_ID='").append(map.get("projectId")).append("' and sn.`NAME`='").append(map.get("nodeName")).append("'");
        }
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql.toString());
        List<RemarkInfo> remarkInfos = list.stream().map(p -> {
            RemarkInfo info = new RemarkInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.content = JdbcMapUtil.getString(p, "CONTENT");
            info.submitTime = StringUtil.withOutT(JdbcMapUtil.getString(p, "SUBMIT_TIME"));
            info.user = JdbcMapUtil.getString(p, "user_name");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(remarkInfos)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.remarkInfos = remarkInfos;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 备注说明填报
     */
    public void fillRemark() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputInfo input = JsonUtil.fromJson(json, InputInfo.class);
        String id = input.id;
        if (Strings.isNullOrEmpty(input.id)) {
            id = Crud.from("REMARK_INFO").insertData();
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String standardNodeNameId = null;
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from STANDARD_NODE_NAME where name=?", input.nodeName);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            standardNodeNameId = JdbcMapUtil.getString(dataMap, "ID");
        }
        Crud.from("REMARK_INFO").where().eq("ID", id).update()
                .set("PM_PRJ_ID", input.projectId).set("AD_USER_ID", ExtJarHelper.loginInfo.get().userId).set("SCHEDULE_NAME", standardNodeNameId)
                .set("CONTENT", input.content).set("SUBMIT_TIME", new Date()).set("REMARK_TYPE", input.type).exec();
    }

    /**
     * 备注说明删除
     */
    public void delRemark() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from REMARK_INFO where id=?", map.get("id"));
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

        public List<RemarkInfo> remarkInfos;
    }

    public static class RemarkInfo {
        public String id;
        public String content;
        public String submitTime;
        public String user;
    }

    public static class InputInfo {
        public String id;
        public String content;
        public String nodeName;
        public String projectId;
        public String type;
    }
}
