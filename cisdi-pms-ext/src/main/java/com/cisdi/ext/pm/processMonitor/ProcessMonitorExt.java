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

/**
 * 流程监控扩展
 */
public class ProcessMonitorExt {

    MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

    /**
     * 查询截止当前所有流程总数(起始日期 2022-11-22)
     */
    public void getAllProcessInstance(){
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
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        ProcessMonitor input = JsonUtil.fromJson(json, ProcessMonitor.class);
        String start = input.getStartTime();
        String end = input.getEndTime();

        List<ProcessMonitor> startList = getStartTop(start,end);
        List<ProcessMonitor> endList = getEndTop(start,end);
        List<ProcessMonitor> checkList = getCheckTop(start,end);

        Map<String,List<ProcessMonitor>> resMap = new HashMap<>();
        resMap.put("start",startList);
        resMap.put("check",checkList);
        resMap.put("end",endList);

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resMap), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 获取时间内审批的TOP5流程
     * @param start 开始时间
     * @param end 结束时间
     * @return 查询结果
     */
    private List<ProcessMonitor> getCheckTop(String start, String end) {
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
     * @return 查询结果
     */
    private List<ProcessMonitor> getEndTop(String start, String end) {
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
     * @return 查询结果
     */
    private List<ProcessMonitor> getStartTop(String start, String end) {
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
