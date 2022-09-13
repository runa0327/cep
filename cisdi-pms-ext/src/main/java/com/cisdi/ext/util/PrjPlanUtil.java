package com.cisdi.ext.util;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class PrjPlanUtil {

    public static void refreshProPlanTime(String projectId, Date date) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        // 查询该项目立项批复时间。若为空，不执行后续操作
        List<Map<String, Object>> mapZero = myJdbcTemplate.queryForList("select PRJ_REPLY_DATE from PM_PRJ where id = ? ", projectId);
        if (CollectionUtils.isEmpty(mapZero)) {
            throw new BaseException("该项目没有批复日期，请稍后在进行节点更新！");
        }
        String replyDate = JdbcMapUtil.getString(mapZero.get(0), "PRJ_REPLY_DATE");
        if (SharedUtil.isEmptyString(replyDate)) {
            throw new BaseException("该项目没有批复日期，请稍后在进行节点更新！");
        }
        // 项目计划开始日期 当天加30天，因为当天也算，实际加29天
        Date planBegin = DateTimeUtil.addDays(DateTimeUtil.stringToDate(replyDate), 29);

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

}
