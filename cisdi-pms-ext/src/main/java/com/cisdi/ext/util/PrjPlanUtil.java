package com.cisdi.ext.util;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;

public class PrjPlanUtil {

    public void prjPlanCreate(String projectId,Date date){
//        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
//        String projectId = entityRecordList.get(0).valueMap.get("PM_PRJ_ID").toString();
//        Date date = DateTimeUtil.stringToDate(entityRecordList.get(0).valueMap.get("PLAN_START_DATE").toString());
        //date为空，取项目预计开始日期
        if (date == null){
           Object date1 = Crud.from("pm_pro_plan").where().eq("PM_PRJ_ID",projectId).select().specifyCols("PLAN_START_DATE").execForValue();
           if (date1 == null ){
               return;
           }
           date = DateTimeUtil.stringToDate(date1.toString());
        }

        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        //查询该项目所有计划节点
        List<Map<String,Object>> map = jdbcTemplate.queryForList("SELECT " +
                "b.id, " +
                "b.PLAN_START_DATE, " +
                "b.PLAN_COMPL_DATE, " +
                "b.PLAN_TOTAL_DAYS, " +
                "b.NAME, " +
                "ifnull(b.PM_PRO_PLAN_NODE_PID,'0') as PM_PRO_PLAN_NODE_PID " +
                "FROM " +
                "PM_PRO_PLAN a " +
                "LEFT JOIN PM_PRO_PLAN_NODE b on a.id = b.PM_PRO_PLAN_ID " +
                "LEFT JOIN pm_prj c on a.PM_PRJ_ID = c.id " +
                "WHERE " +
                "c.id = ?",projectId);
        if (CollectionUtils.isEmpty(map)){
            throw new BaseException("该项目没有项目节点");
        }

        List<Map<String,Object>> updateSql = new ArrayList<>();
        for (Map<String, Object> tmp : map) {
            String start = JdbcMapUtil.getString(tmp,"PLAN_START_DATE");
            if (SharedUtil.isEmptyString(start)){
                throw new BaseException("预计开始日期不能有空值");
            }
            String end = tmp.get("PLAN_COMPL_DATE").toString();
            String parentId = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
            int day = Integer.valueOf(tmp.get("PLAN_TOTAL_DAYS").toString());
            Date endTime = DateTimeUtil.stringToDate(end);
            Date afterTime = DateTimeUtil.addDays(DateTimeUtil.stringToDate(start), (day-1));
            if (endTime.compareTo(afterTime) != 0){
                String afterDay = DateTimeUtil.dateToString(afterTime);
                tmp.put("PLAN_COMPL_DATE",afterDay);
                updateSql = getUpdateSql(map,parentId,afterTime);
                updateSql.add(tmp);
            }
        }
        if (!CollectionUtils.isEmpty(updateSql)){
            StringBuilder sb = new StringBuilder("");
            for (Map<String, Object> tmp : updateSql) {
                sb.append("update PM_PRO_PLAN_NODE set PLAN_COMPL_DATE = '"+ tmp.get("PLAN_COMPL_DATE").toString() + "',PLAN_TOTAL_DAYS = '" + tmp.get("PLAN_TOTAL_DAYS").toString()+"" +
                        "' where id = '" +tmp.get("id").toString()+"';");
            }
            int num = jdbcTemplate.update(sb.toString());
        }

        //更新主表数据
        int mainDays = getMaxDate("0",map);
        Date lastTime = DateTimeUtil.addDays(date,mainDays);
        String end = DateTimeUtil.dateToString(lastTime);
        jdbcTemplate.update("update PM_PRO_PLAN set PLAN_COMPL_DATE = ?,PLAN_TOTAL_DAYS = ? where PM_PRJ_ID = ?",end,mainDays,projectId);

    }

    //遍历得到所有需要变更的数据
    private List<Map<String, Object>> getUpdateSql(List<Map<String, Object>> map, String parentId, Date afterTime) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (!SharedUtil.isEmptyString(parentId)){
            tp: for (Map<String, Object> tmp : map) {
                String id = tmp.get("id").toString();
                String fatherId = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
                //找到父级id数据
                if (parentId.equals(id)){
                    Date start = DateTimeUtil.stringToDate(tmp.get("PLAN_START_DATE").toString());
                    Date end = DateTimeUtil.stringToDate(tmp.get("PLAN_COMPL_DATE").toString());
                    String parentId2 = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
                    int day = Integer.valueOf(tmp.get("PLAN_TOTAL_DAYS").toString());
                    int maxDate = getMaxDate(id,map);

                    if (maxDate != day){
                        tmp.put("PLAN_TOTAL_DAYS",maxDate);
                        afterTime = DateTimeUtil.addDays(start, (maxDate - 1));
                        String afterDay = DateTimeUtil.dateToString(afterTime);
                        tmp.put("PLAN_COMPL_DATE",afterDay);
                        list.add(tmp);
                    }
                    if (!"0".equals(parentId2)){
                        List<Map<String, Object>> map1 = getUpdateSql(map,parentId2,DateTimeUtil.stringToDate(tmp.get("PLAN_COMPL_DATE").toString()));
                        if (!CollectionUtils.isEmpty(map1)){
                            list.addAll(map1);
                        }
                    }
                    break tp;
                }
            }
        }
        return list;
    }

    //得到子级最大预计总天数
    private int getMaxDate(String id, List<Map<String, Object>> map) {
        int maxDate = 0;
        List<Integer> dates = new ArrayList<>();
        for (Map<String, Object> tmp : map) {
            String fatherId = tmp.get("PM_PRO_PLAN_NODE_PID").toString();
            if (id.equals(fatherId)){
                dates.add(Integer.valueOf(tmp.get("PLAN_TOTAL_DAYS").toString()));
            }
        }
        if (!CollectionUtils.isEmpty(dates)){
            maxDate = Collections.max(dates);
        }
        return maxDate;
    }

}
