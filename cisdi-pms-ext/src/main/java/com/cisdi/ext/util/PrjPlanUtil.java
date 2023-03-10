package com.cisdi.ext.util;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PrjPlanUtil {

    public static void refreshProPlanTime(String projectId, Date date) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        if (date == null) {
            // 查询该项目立项批复时间。若为空，不执行后续操作
            List<Map<String, Object>> mapZero = myJdbcTemplate.queryForList("select PRJ_REPLY_DATE from PM_PRJ where id = ? ", projectId);
            if (CollectionUtils.isEmpty(mapZero)) {
                throw new BaseException("该项目没有批复日期，请稍后在进行节点更新！");
            }
            String replyDate = JdbcMapUtil.getString(mapZero.get(0), "PRJ_REPLY_DATE");
            if (SharedUtil.isEmptyString(replyDate)) {
                throw new BaseException("该项目没有批复日期，请稍后在进行节点更新！");
            }
            date = DateTimeUtil.stringToDate(replyDate);
        }

        // 项目计划开始日期 当天加30天，因为当天也算，实际加29天
        Date planBegin = DateTimeUtil.addDays(date, 29);

        // 查询该项目所有计划节点
        String sql = "SELECT b.id,b.PLAN_START_DATE,b.PLAN_COMPL_DATE,b.PLAN_TOTAL_DAYS,b.NAME,b.START_DAY,ifnull(b.PM_PRO_PLAN_NODE_PID,'0') as PM_PRO_PLAN_NODE_PID " +
                "FROM PM_PRO_PLAN a " +
                "LEFT JOIN PM_PRO_PLAN_NODE b on a.id = b.PM_PRO_PLAN_ID " +
                "LEFT JOIN pm_prj c on a.PM_PRJ_ID = c.id " +
                "WHERE c.id = ?";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql, projectId);
        if (CollectionUtils.isEmpty(map)) {
            throw new BaseException("该项目没有项目节点");
        }

        // 遍历生成父级idList
        List<String> parentList = map.stream().map(p -> {
            return p.get("PM_PRO_PLAN_NODE_PID").toString();
        }).distinct().collect(Collectors.toList());

        // 遍历修改
        for (Map<String, Object> p : map) {
            String id = JdbcMapUtil.getString(p, "id");
            String parentId = JdbcMapUtil.getString(p, "PM_PRO_PLAN_NODE_PID");
            // 子级
            if (!parentList.contains(id)) {
                // 第几天开始
                int startDay = Integer.parseInt(JdbcMapUtil.getString(p, "START_DAY"));
                // 预计总天数
                int days = Integer.parseInt(JdbcMapUtil.getString(p, "PLAN_TOTAL_DAYS").toString());
                // 预计开始日期
                Date planStartDate = DateTimeUtil.addDays(planBegin, (startDay - 1));
                // 预计结束日期
                Date planEndDate = DateTimeUtil.addDays(planStartDate, (days - 1));

                String planStartTime = DateTimeUtil.dateToString(planStartDate);
                String planEndDateTime = DateTimeUtil.dateToString(planEndDate);

                // 预计开始日期
                p.put("PLAN_START_DATE", planStartTime);
                p.put("PLAN_COMPL_DATE", planEndDateTime);

                // 更新父级
                if (!"0".equals(parentId)) {
                    map = changeParent(parentId, map, planBegin, p);
                }
            } else { // 父级
                Map<String, Object> q = getNewMap(id, map, planBegin, p);
                p.put("PLAN_START_DATE", q.get("PLAN_START_DATE"));
                p.put("PLAN_COMPL_DATE", q.get("PLAN_COMPL_DATE"));
                p.put("PLAN_TOTAL_DAYS", q.get("PLAN_TOTAL_DAYS"));
                p.put("START_DAY", q.get("START_DAY"));
                // 更新父级
                if (!"0".equals(parentId)) {
                    map = changeParent(parentId, map, planBegin, p);
                }
            }
        }
        ;


        if (!CollectionUtils.isEmpty(map)) {
            StringBuilder sb = new StringBuilder("");
            for (Map<String, Object> tmp : map) {
                sb.append("update PM_PRO_PLAN_NODE set PLAN_COMPL_DATE = '" + JdbcMapUtil.getString(tmp, "PLAN_COMPL_DATE") + "',PLAN_TOTAL_DAYS = '" + JdbcMapUtil.getString(tmp, "PLAN_TOTAL_DAYS") + "" +
                        "',PLAN_START_DATE = '" + JdbcMapUtil.getString(tmp, "PLAN_START_DATE") + "',START_DAY = '" + JdbcMapUtil.getString(tmp, "START_DAY") + "' where id = '" + tmp.get("id").toString() + "';");
            }
            int num = myJdbcTemplate.update(sb.toString());
        }

        // 更新主表数据
        int mainDays = getMaxDate("0", map);
        Map<String, Object> father = getChildStart(map, "0", planBegin);
        String cha = father.get("cha").toString();
        String start = father.get("start").toString();
        String planCom = father.get("max").toString();
        String planStart = father.get("min").toString();
        myJdbcTemplate.update("update PM_PRO_PLAN set PLAN_COMPL_DATE = ?,PLAN_TOTAL_DAYS = ?,PLAN_START_DATE=?,START_DAY=? where PM_PRJ_ID = ?", planCom, mainDays, planStart, start, projectId);

    }

    // 由里而外递归处理父级数据
    private static List<Map<String, Object>> changeParent(String parentId, List<Map<String, Object>> map, Date planBegin, Map p) {
        for (Map<String, Object> tmp : map) {
            String id = tmp.get("id").toString();
            String parent = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
            if (id.equals(parentId)) {
                Map<String, Object> q = getNewMap(id, map, planBegin, p);
                tmp.put("PLAN_START_DATE", q.get("PLAN_START_DATE"));
                tmp.put("PLAN_COMPL_DATE", q.get("PLAN_COMPL_DATE"));
                tmp.put("PLAN_TOTAL_DAYS", q.get("PLAN_TOTAL_DAYS"));
                tmp.put("START_DAY", q.get("START_DAY"));
                changeParent(parent, map, planBegin, tmp);
            }
        }
        return map;
    }

    // 根据父级id查询所需数据
    private static Map<String, Object> getNewMap(String id, List<Map<String, Object>> map, Date planBegin, Map p) {
        String min = "2001-01-01", max = "2199-01-01";
        Date minDate = DateTimeUtil.stringToDate(max);
        Date maxDate = DateTimeUtil.stringToDate(min);
        Map<String, Object> res = new HashMap<>();
        for (Map<String, Object> tmp : map) {
            String parentId = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
            if (parentId.equals(id)) {
                String begin1 = JdbcMapUtil.getString(tmp, "PLAN_START_DATE");
                String end1 = JdbcMapUtil.getString(tmp, "PLAN_COMPL_DATE");
                Date begin = null, end = null;
                // 第几天开始
                String number = JdbcMapUtil.getString(tmp, "START_DAY");
                int startDay = Integer.parseInt(number);
                // 预计总天数
                int days = Integer.parseInt(JdbcMapUtil.getString(tmp, "PLAN_TOTAL_DAYS").toString());
                if (SharedUtil.isEmptyString(begin1)) {
                    minDate = DateTimeUtil.addDays(planBegin, (startDay - 1));
                    min = DateTimeUtil.dateToString(minDate);
                    tmp.put("PLAN_START_DATE", min);
                } else {
                    begin = DateTimeUtil.stringToDate(begin1);
                    if (begin.compareTo(minDate) <= 0) {
                        minDate = begin;
                        min = DateTimeUtil.dateToString(minDate);
                    }
                }
                if (SharedUtil.isEmptyString(end1)) {
                    maxDate = DateTimeUtil.addDays(minDate, (days - 1));
                    max = DateTimeUtil.dateToString(maxDate);
                    tmp.put("PLAN_COMPL_DATE", max);

                } else {
                    end = DateTimeUtil.stringToDate(end1);
                    if (end.compareTo(maxDate) > 0) {
                        maxDate = end;
                        max = DateTimeUtil.dateToString(maxDate);
                    }
                }
            }
        }
        res.put("PLAN_COMPL_DATE", max);
        res.put("PLAN_START_DATE", min);
        res.put("id", id);
        res.put("PLAN_TOTAL_DAYS", DateTimeUtil.getTwoTimeDays(maxDate, minDate) + 1);
        res.put("START_DAY", DateTimeUtil.getTwoTimeDays(minDate, planBegin) + 1);
        res.put("NAME", p.get("NAME"));
        res.put("PM_PRO_PLAN_NODE_PID", p.get("PM_PRO_PLAN_NODE_PID"));
        return res;
    }

    // 遍历获取子级最小开始日期、最大结束日期
    private static Map<String, Object> getChildStart(List<Map<String, Object>> map, String id, Date planBegin) {
        String min = "2001-01-01", max = "2399-01-01";
        Date minDate = DateTimeUtil.stringToDate(max);
        Date maxDate = DateTimeUtil.stringToDate(min);
        for (Map<String, Object> tmp : map) {
            String parentId = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
            if (parentId.equals(id)) {
                String begin1 = JdbcMapUtil.getString(tmp, "PLAN_START_DATE");
                String end1 = JdbcMapUtil.getString(tmp, "PLAN_COMPL_DATE");
                Date begin = DateTimeUtil.stringToDate(begin1);
                Date end = DateTimeUtil.stringToDate(end1);
                if (begin.compareTo(minDate) <= 0) {
                    minDate = begin;
                }
                if (end.compareTo(maxDate) > 0) {
                    maxDate = end;
                }
            }
        }
        Map<String, Object> res = new HashMap<>();
        res.put("max", DateTimeUtil.dateToString(maxDate));
        res.put("min", DateTimeUtil.dateToString(minDate));
        res.put("cha", DateTimeUtil.getTwoTimeDays(maxDate, minDate) + 1);
        res.put("start", DateTimeUtil.getTwoTimeDays(minDate, planBegin) + 1);
        return res;
    }

    // 遍历得到所有需要变更的数据
    private static List<Map<String, Object>> getUpdateSql(List<Map<String, Object>> map, String parentId, Date afterTime) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!SharedUtil.isEmptyString(parentId)) {
            tp:
            for (Map<String, Object> tmp : map) {
                String id = tmp.get("id").toString();
                String fatherId = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
                // 找到父级id数据
                if (parentId.equals(id)) {
                    Date start = DateTimeUtil.stringToDate(tmp.get("PLAN_START_DATE").toString());
                    Date end = DateTimeUtil.stringToDate(tmp.get("PLAN_COMPL_DATE").toString());
                    String parentId2 = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
                    int day = Integer.valueOf(tmp.get("PLAN_TOTAL_DAYS").toString());
                    int maxDate = getMaxDate(id, map);

                    if (maxDate != day) {
                        tmp.put("PLAN_TOTAL_DAYS", maxDate);
                        afterTime = DateTimeUtil.addDays(start, (maxDate - 1));
                        String afterDay = DateTimeUtil.dateToString(afterTime);
                        tmp.put("PLAN_COMPL_DATE", afterDay);
                        list.add(tmp);
                    }
                    if (!"0".equals(parentId2)) {
                        List<Map<String, Object>> map1 = getUpdateSql(map, parentId2, DateTimeUtil.stringToDate(tmp.get("PLAN_COMPL_DATE").toString()));
                        if (!CollectionUtils.isEmpty(map1)) {
                            list.addAll(map1);
                        }
                    }
                    break tp;
                }
            }
        }
        return list;
    }

    // 得到子级最大预计总天数
    private static int getMaxDate(String id, List<Map<String, Object>> map) {
        int maxDate = 0;
        List<Integer> dates = new ArrayList<>();
        for (Map<String, Object> tmp : map) {
            String fatherId = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
            if (id.equals(fatherId)) {
                dates.add(Integer.valueOf(tmp.get("PLAN_TOTAL_DAYS").toString()));
            }
        }
        if (!CollectionUtils.isEmpty(dates)) {
            maxDate = Collections.max(dates);
        }
        return maxDate;
    }

    /**
     * 新增项目进度网络图
     *
     * @param projectId
     */
    public static void createPlan(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 根据项目类型查询项目进度计划模板
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ppp.*,PRJ_REPLY_DATE from PM_PRO_PLAN ppp \n" +
                "left join pm_prj pp on ppp.TEMPLATE_FOR_PROJECT_TYPE_ID = pp.PROJECT_TYPE_ID\n" +
                "where ppp.`STATUS`='AP' and ppp.IS_TEMPLATE='1' and pp.id=?", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> proMap = list.get(0);
            // 先创建项目的进度计划
            String newPlanId = Crud.from("PM_PRO_PLAN").insertData();

            Crud.from("PM_PRO_PLAN").where().eq("ID", newPlanId).update().set("IS_TEMPLATE", 0).set("PM_PRJ_ID", projectId).set("PLAN_TOTAL_DAYS", proMap.get("PLAN_TOTAL_DAYS"))
                    .set("PROGRESS_STATUS_ID", proMap.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", proMap.get("PROGRESS_RISK_TYPE_ID")).set("START_DAY", proMap.get("START_DAY")).exec();


            // 查询项目进度计划节点模板
            List<Map<String, Object>> planNodeList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT, \n" +
                    "pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,ACTUAL_START_DATE,PROGRESS_RISK_REMARK,PM_PRO_PLAN_ID,PLAN_START_DATE, \n" +
                    "PLAN_TOTAL_DAYS,PLAN_CARRY_DAYS,ACTUAL_CARRY_DAYS,ACTUAL_TOTAL_DAYS,PLAN_CURRENT_PRO_PERCENT,ACTUAL_CURRENT_PRO_PERCENT, \n" +
                    "ifnull(PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,SHOW_IN_EARLY_PROC,SHOW_IN_PRJ_OVERVIEW,CAN_START \n" +
                    "PROGRESS_STATUS_ID,PROGRESS_RISK_TYPE_ID,CHIEF_DEPT_ID,CHIEF_USER_ID,START_DAY,SEQ_NO,CPMS_UUID,CPMS_ID,`LEVEL`,LINKED_WF_PROCESS_ID,LINKED_START_WF_NODE_ID,LINKED_END_WF_NODE_ID,POST_INFO_ID ,AD_USER_ID \n" +
                    "from PM_PRO_PLAN_NODE pppn \n" +
                    "left join POST_INFO pi on pppn.POST_INFO_ID = pi.id \n" +
                    "where PM_PRO_PLAN_ID=?", proMap.get("ID"));
            if (planNodeList.size() > 0) {
                // 查询项目岗位人员
                List<Map<String, Object>> postUserList = myJdbcTemplate.queryForList("select * from pm_post_user where pm_prj_id=?", projectId);
                planNodeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
                    String id = Crud.from("PM_PRO_PLAN_NODE").insertData();
                    Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                            .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                            .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("CHIEF_USER_ID", m.get("CHIEF_USER_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                            .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_START_WF_NODE_ID", m.get("LINKED_START_WF_NODE_ID")).set("LINKED_END_WF_NODE_ID", m.get("LINKED_END_WF_NODE_ID")).set("SHOW_IN_EARLY_PROC", m.get("SHOW_IN_EARLY_PROC"))
                            .set("SHOW_IN_PRJ_OVERVIEW", m.get("SHOW_IN_PRJ_OVERVIEW")).set("POST_INFO_ID", m.get("POST_INFO_ID")).set("CHIEF_USER_ID", m.get("AD_USER_ID")).set("CAN_START",  m.get("CAN_START")).exec();

                    getChildrenNode(m, planNodeList, id, newPlanId, postUserList);
                }).collect(Collectors.toList());
            }
        }
    }

    private static List<Map<String, Object>> getChildrenNode(Map<String, Object> root, List<Map<String, Object>> allData, String pId, String newPlanId, List<Map<String, Object>> postUserList) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")), String.valueOf(root.get("ID")))).peek(m -> {
            String id = Crud.from("PM_PRO_PLAN_NODE").insertData();
            Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                    .set("PM_PRO_PLAN_NODE_PID", pId)
                    .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                    .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("CHIEF_USER_ID", m.get("CHIEF_USER_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                    .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_START_WF_NODE_ID", m.get("LINKED_START_WF_NODE_ID")).set("LINKED_END_WF_NODE_ID", m.get("LINKED_END_WF_NODE_ID")).set("SHOW_IN_EARLY_PROC", m.get("SHOW_IN_EARLY_PROC"))
                    .set("SHOW_IN_PRJ_OVERVIEW", m.get("SHOW_IN_PRJ_OVERVIEW")).set("POST_INFO_ID", m.get("POST_INFO_ID")).set("CHIEF_USER_ID", m.get("AD_USER_ID")).set("CAN_START", m.get("CAN_START")).exec();
            getChildrenNode(m, allData, id, newPlanId, postUserList);
        }).collect(Collectors.toList());
    }

    private static String getUser(List<Map<String, Object>> postUserList, Object postId) {
        String userId = null;
        Optional<Map<String, Object>> any = postUserList.stream().filter(p -> Objects.equals(p.get("POST_INFO_ID"), postId)).findAny();
        if (any.isPresent()) {
            userId = String.valueOf(any.get().get("AD_USER_ID"));
        }
        return userId;
    }


    /**
     * 跟新进度节点状态
     *
     * @param projectId
     */
    public static void updateNodeStatus(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 查询所有的进度计划节点
        List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER," +
                "pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID," +
                "pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE," +
                "pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,pppn.PLAN_TOTAL_DAYS," +
                "pppn.PLAN_CARRY_DAYS,pppn.ACTUAL_CARRY_DAYS,pppn.ACTUAL_TOTAL_DAYS,pppn.PLAN_CURRENT_PRO_PERCENT," +
                "pppn.ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID," +
                "pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW," +
                "pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID," +
                "pppn.START_DAY,pppn.SEQ_NO,pppn.CPMS_UUID,pppn.CPMS_ID,pppn.LEVEL " +
                "gsv.`CODE` as PROGRESS_STATUS_CODE from pm_pro_plan_node pppn \n" +
                "left join pm_pro_plan ppp on pppn.PM_PRO_PLAN_ID = ppp.ID\n" +
                "left join gr_set_value gsv on gsv.id = pppn.PROGRESS_STATUS_ID\n" +
                "left join gr_set gr on gr.id = gsv.GR_SET_ID\n" +
                "where gr.`CODE`='PROGRESS_STATUS' and ppp.PM_PRJ_ID=?", projectId);

        if (nodeList.size() > 0) {
            nodeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
                List<Map<String, Object>> children = getChildrenNodeForCollectProgressStatus(m, nodeList, myJdbcTemplate);
                if (children.size() > 0) {
                    int count = children.size();
                    String proStatus = "0";
                    List<Map<String, Object>> finishList = children.stream().filter(p -> Objects.equals("2", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                    if (count == finishList.size()) {
                        // 父级改为已完成
                        proStatus = "2";
                    } else {
                        List<Map<String, Object>> UnBeginList = children.stream().filter(p -> Objects.equals("0", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                        if (count != UnBeginList.size()) {
                            // 父级改为进行中
                            proStatus = "1";
                        }
                    }
                    List<Map<String, Object>> grSetValueList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv " +
                            "left join gr_set gs on gs.id = gsv.gr_set_id where gs.`code`='PROGRESS_STATUS' and gsv.`code`=?", proStatus);
                    String proStatusID = "";
                    if (grSetValueList.size() > 0) {
                        proStatusID = String.valueOf(grSetValueList.get(0).get("ID"));
                    }
                    myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", proStatusID, m.get("ID"));
                }
            }).collect(Collectors.toList());

        }
    }

    /**
     * 递归修改父节点的状态
     *
     * @param root
     * @param allData
     * @param myJdbcTemplate
     * @return
     */
    private static List<Map<String, Object>> getChildrenNodeForCollectProgressStatus(Map<String, Object> root, List<Map<String, Object>> allData, MyJdbcTemplate myJdbcTemplate) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(root.get("ID")), String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
            List<Map<String, Object>> children = getChildrenNodeForCollectProgressStatus(m, allData, myJdbcTemplate);
            if (children.size() > 0) {
                int count = children.size();
                String proStatus = "0";
                List<Map<String, Object>> finishList = children.stream().filter(p -> Objects.equals("2", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                if (count == finishList.size()) {
                    // 父级改为已完成
                    proStatus = "2";
                } else {
                    List<Map<String, Object>> UnBeginList = children.stream().filter(p -> Objects.equals("0", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                    if (count != UnBeginList.size()) {
                        // 父级改为进行中
                        proStatus = "1";
                    }
                }
                List<Map<String, Object>> grSetValueList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv " +
                        "left join gr_set gs on gs.id = gsv.gr_set_id where gs.`code`='PROGRESS_STATUS' and gsv.`code`=?", proStatus);
                String proStatusID = "";
                if (grSetValueList.size() > 0) {
                    proStatusID = String.valueOf(grSetValueList.get(0).get("ID"));
                }
                myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", proStatusID, m.get("ID"));
            }
        }).collect(Collectors.toList());
    }

}
