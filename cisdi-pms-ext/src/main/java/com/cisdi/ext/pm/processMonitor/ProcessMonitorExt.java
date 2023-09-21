package com.cisdi.ext.pm.processMonitor;

import com.cisdi.ext.model.base.ProcessMonitor;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程监控扩展
 */
public class ProcessMonitorExt {

    /**
     * 查询截止当前所有流程总数(起始日期 2022-11-22)
     */
    public void getAllProcessInstance(){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select ifnull(count(*),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681'";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            Map<String,Object> map = new HashMap<>();
            map.put("total", JdbcMapUtil.getString(list.get(0),"num"));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 查询截止当前所有流程大类总数(起始日期 2022-11-22)
     */
    public void getAllProcessGroupByType(){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select c.name,ifnull(count(*),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id LEFT JOIN wf_cate c on b.wf_cate_id = c.id where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' group by c.id ORDER BY num desc";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            List<ProcessMonitor> returnList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                ProcessMonitor processMonitor = new ProcessMonitor();
                processMonitor.setCateTypeName(JdbcMapUtil.getString(map,"name"));
                processMonitor.setNum(JdbcMapUtil.getInt(map,"num"));
                returnList.add(processMonitor);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("list", returnList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 区间范围内发起、办理、办结 TOP5 数
     */
    public void getTop5ProcessCate(){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        ProcessMonitor input = JsonUtil.fromJson(json, ProcessMonitor.class);
        String start = input.getStartTime();
        String end = input.getEndTime();

        List<ProcessMonitor> startList = getStartTop(start,end,jdbcTemplate);
        List<ProcessMonitor> endList = getEndTop(start,end,jdbcTemplate);
        List<ProcessMonitor> checkList = getCheckTop(start,end,jdbcTemplate);

        Map<String,List<ProcessMonitor>> resMap = new HashMap<>();
//        resMap = dealData(startList,endList,checkList); // 2023-08-02 显示所有分类增加 暂不删除
        resMap.put("start",startList);
        resMap.put("check",checkList);
        resMap.put("end",endList);

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resMap), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 发起、审批、办结数据处理
     * @param startList 发起明细
     * @param endList 办结明细
     * @param checkList 办理明细
     * @return
     */
    private Map<String, List<ProcessMonitor>> dealData(List<ProcessMonitor> startList, List<ProcessMonitor> endList, List<ProcessMonitor> checkList) {
        List<String> startStr = startList.stream().map(ProcessMonitor::getCateTypeName).collect(Collectors.toList());
        List<String> endStr = endList.stream().map(ProcessMonitor::getCateTypeName).collect(Collectors.toList());
        List<String> checkStr = checkList.stream().map(ProcessMonitor::getCateTypeName).collect(Collectors.toList());
        List<String> catStr = distinctName(startStr,endStr,checkStr);
        Map<String, List<ProcessMonitor>> map = new HashMap<>();
        return getMap(map,catStr,startList,endList,checkList,startStr,endStr,checkStr);
    }

    /**
     * 发起、审批、办结流程去重汇总后按照固定顺序返回
     * @param map 返回的结果集
     * @param catStr 去重后流程分类
     * @param startList 所有发起明细
     * @param endList 所有办结明细
     * @param checkList 所有审批明细
     * @param startStr 发起流程分类信息
     * @param endStr 办结流程分类信息
     * @param checkStr 审批流程分类信息
     * @return 处理后数据
     */
    private Map<String, List<ProcessMonitor>> getMap(Map<String, List<ProcessMonitor>> map, List<String> catStr, List<ProcessMonitor> startList, List<ProcessMonitor> endList, List<ProcessMonitor> checkList, List<String> startStr, List<String> endStr, List<String> checkStr) {
        List<ProcessMonitor> beginList = new ArrayList<>();
        List<ProcessMonitor> finishList = new ArrayList<>();
        List<ProcessMonitor> examList = new ArrayList<>();
        for (String s : catStr) {

            // 处理发起
            dealProcessDate(s,beginList,startList,startStr);

            // 处理办结
            dealProcessDate(s,finishList,endList,endStr);

            // 处理审批
            dealProcessDate(s,examList,checkList,checkStr);

        }
        map.put("start",beginList);
        map.put("check",examList);
        map.put("end",finishList);
        return map;
    }

    /**
     * 数据处理
     * @param cateName 分类名称
     * @param resList 返回的数据
     * @param sourceList 来源数据
     * @param listStr 来源数据分类去重
     */
    private void dealProcessDate(String cateName, List<ProcessMonitor> resList, List<ProcessMonitor> sourceList, List<String> listStr) {
        if (!CollectionUtils.isEmpty(sourceList)){
            if (listStr.contains(cateName)){
                ProcessMonitor processMonitor = sourceList.stream().filter(p->cateName.equals(p.getCateTypeName())).collect(Collectors.toList()).get(0);
                resList.add(processMonitor);
                sourceList.removeIf(p -> cateName.equals(p.getCateTypeName()));
            } else {
                ProcessMonitor processMonitor = new ProcessMonitor();
                processMonitor.setCateTypeName(cateName);
                processMonitor.setNum(0);
                resList.add(processMonitor);
            }
        } else {
            ProcessMonitor processMonitor = new ProcessMonitor();
            processMonitor.setCateTypeName(cateName);
            processMonitor.setNum(0);
            resList.add(processMonitor);
        }
    }

    /**
     * 流程分类去重
     * @param startStr 所有发起流程分类
     * @param endStr 所有办结流程分类
     * @param checkStr 所有审批流程分类
     * @return 去重汇总后流程分类
     */
    private List<String> distinctName(List<String> startStr, List<String> endStr, List<String> checkStr) {
        List<String> resList = new ArrayList<>();
        resList.addAll(startStr);
        resList.addAll(endStr);
        resList.addAll(checkStr);
        resList = resList.stream().distinct().collect(Collectors.toList());
        return resList;
    }

    /**
     * 获取时间内审批的TOP5流程
     * @param start 开始时间
     * @param end 结束时间
     * @param jdbcTemplate 数据源
     * @return 查询结果
     */
    private List<ProcessMonitor> getCheckTop(String start, String end, MyJdbcTemplate jdbcTemplate) {
        List<ProcessMonitor> res = new ArrayList<>();
        String sql = "select c.name,ifnull(count(*),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id LEFT JOIN wf_cate c on b.wf_cate_id = c.id LEFT JOIN wf_node_instance d on a.id = d.wf_process_instance_id left join wf_node e on d.wf_node_id = e.id where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' and d.end_dateTime >= ? and d.end_dateTime <= ? and e.node_type = 'user_task' group by c.id ORDER BY num desc limit 5";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,start,end);
        if (!CollectionUtils.isEmpty(list)){
            packageDate(res,list);
        }
        return res;
    }

    /**
     * 获取时间内办结的TOP5流程
     * @param start 开始时间
     * @param end 结束时间
     * @param jdbcTemplate 数据源
     * @return 查询结果
     */
    private List<ProcessMonitor> getEndTop(String start, String end, MyJdbcTemplate jdbcTemplate) {
        List<ProcessMonitor> res = new ArrayList<>();
        String sql = "select c.name,ifnull(count(*),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id LEFT JOIN wf_cate c on b.wf_cate_id = c.id where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' and a.end_dateTime >= ? and a.end_dateTime <= ? group by c.id ORDER BY num desc limit 5";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,start,end);
        if (!CollectionUtils.isEmpty(list)){
            packageDate(res,list);
        }
        return res;
    }

    /**
     * 获取时间内发起的TOP5流程
     * @param start 开始时间
     * @param end 结束时间
     * @param jdbcTemplate 数据源
     * @return 查询结果
     */
    private List<ProcessMonitor> getStartTop(String start, String end, MyJdbcTemplate jdbcTemplate) {
        List<ProcessMonitor> res = new ArrayList<>();
        String sql = "select c.name,ifnull(count(*),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id LEFT JOIN wf_cate c on b.wf_cate_id = c.id where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' and a.crt_dt >= ? and a.crt_dt <= ? group by c.id ORDER BY num desc limit 5";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,start,end);
        if (!CollectionUtils.isEmpty(list)){
            packageDate(res,list);
        }
        return res;
    }

    /**
     * 通用返回数据封装
     * @param res 返回对象
     * @param list 数据来源对象
     */
    private void packageDate(List<ProcessMonitor> res, List<Map<String, Object>> list) {
        for (Map<String, Object> map : list) {
            ProcessMonitor processMonitor = new ProcessMonitor();
            processMonitor.setNum(JdbcMapUtil.getInt(map,"num"));
            processMonitor.setCateTypeName(JdbcMapUtil.getString(map,"name"));
            res.add(processMonitor);
        }
    }

    /**
     * 流程处理半年趋势图
     */
    public void processHalfYear(){
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select a.month,a.num as chechNum,b.num as startNum,c.num as endNum from (select DATE_FORMAT(d.end_dateTime,'%Y-%m') as month,ifnull(count(distinct a.id),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id  LEFT JOIN wf_node_instance d on a.id = d.wf_process_instance_id left join wf_node e on d.wf_node_id = e.id where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' and d.end_dateTime >= DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-01'),INTERVAL 6 MONTH) and e.node_type = 'user_task' group by month ORDER BY month desc) a LEFT JOIN (select DATE_FORMAT(a.crt_dt,'%Y-%m') as month,ifnull(count(*),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id  where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' and a.crt_dt >= DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-01'),INTERVAL 6 MONTH)  group by month ORDER BY month desc ) b on a.month = b.month LEFT JOIN (select DATE_FORMAT(a.crt_dt,'%Y-%m') as month,ifnull(count(*),0) as num from wf_process_instance a left join wf_process b on a.wf_process_id = b.id  where a.status = 'ap' and b.status in ('ap','aping') and a.crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' and a.end_dateTime >= DATE_SUB(DATE_FORMAT(NOW(),'%Y-%m-01'),INTERVAL 6 MONTH)  group by month ORDER BY month desc ) c on a.month = c.month order by a.month asc";
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            List<ProcessMonitor> resList = new ArrayList<>();
            for (Map<String, Object> map : list) {
                ProcessMonitor processMonitor = new ProcessMonitor();
                processMonitor.setMonth(JdbcMapUtil.getString(map,"month"));
                processMonitor.setStartNum(JdbcMapUtil.getInt(map,"startNum"));
                processMonitor.setEndNum(JdbcMapUtil.getInt(map,"endNum"));
                processMonitor.setCheckNum(JdbcMapUtil.getInt(map,"chechNum"));
                resList.add(processMonitor);
            }
            Map<String,Object> map = new HashMap<>();
            map.put("list", resList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

}
