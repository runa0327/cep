package com.cisdi.ext.base;

import com.cisdi.ext.model.view.weekReport.PmProgressWeeklyView;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SystemUsage {

    /**
     * 系统使用情况查询
     */
    public void querySystemUsage(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmProgressWeeklyView param = JsonUtil.fromJson(json, PmProgressWeeklyView.class);
        String weekId = param.id;
        if (!StringUtils.hasText(weekId)){
            throw new BaseException("[id]周id不能为空");
        }
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList("select DATE_FORMAT(FROM_DATE,'%Y-%m-%d 00:00:00') as startDate,DATE_FORMAT(TO_DATE,'%Y-%m-%d 23:59:59') as endDate from pm_progress_weekly where id = ?",weekId);
        String startDate = JdbcMapUtil.getString(list1.get(0),"startDate").replace("T"," ");
        String endDate = JdbcMapUtil.getString(list1.get(0),"endDate").replace("T"," ");

        // allUserNum-查询系统所有人员 weekUserLoginNum-本周登录人数 loginNums-总体登录次数
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select a.num as allUserNum,b.num as weekUserLoginNum,c.num as loginNums from (select count(*) as num,'1' as id from ad_user where status = 'AP' ) a left join (select count(*) as num,'1' as id from (select ad_user_id from main.ad_login where crt_dt >= ? and crt_dt <= ? group by ad_user_id ) a) b on a.id = b.id left join (select count(*) as num,'1' as id from main.ad_login where crt_dt >= '2022-11-22 00:00:00') c on b.id = c.id",startDate,endDate);

        // allProcessInstanceNums-发起流程总数 needWriteNums-形象进度应填报 writeNums-形象进度实际填报 instanceNums-本周新增流程数量
        List<Map<String,Object>> list3 = myJdbcTemplate.queryForList("select a.num as allProcessInstanceNums,b.num as needWriteNums,c.num as writeNums,d.instanceNums from (select count(*) as num,'1' as id from wf_process_instance where status = 'ap' and crt_dt >= '2022-11-22 00:00:00' and START_USER_ID != '0099250247095871681' and 0 = locate('历史数据导入',name) ) a left join (select count(*) as num,'1' as id from pm_progress_weekly_prj where PM_PROGRESS_WEEKLY_ID = ? and status = 'AP') b on a.id = b.id left join (select count(*) as num,'1' as id from pm_progress_weekly_prj where PM_PROGRESS_WEEKLY_ID = ? and status = 'AP' and SUBMIT_TIME is not null ) c on a.id = c.id left join (select count(*) as instanceNums,'1' as id from wf_process_instance a where a.status = 'ap' and a.crt_dt >= '2022-11-22 00:00:00' and a.START_USER_ID != '0099250247095871681' and 0 = locate('历史数据导入',a.name) and a.crt_dt >= ? and a.crt_dt <= ? ) d on a.id = d.id",weekId,weekId,startDate,endDate);

        // 领导关注度
        List<Map<String,Object>> list4 = myJdbcTemplate.queryForList("select a.ad_user_id,ifnull(count(*),0) as num,(select name from main.ad_user where id = a.ad_user_id) as name from main.ad_login a where a.ad_user_id in (select AD_USER_ID from hr_dept where status = 'AP' and AD_USER_ID is not null GROUP BY AD_USER_ID ORDER BY AD_USER_ID asc) and crt_dt >= ? and crt_dt <= ? group by a.ad_user_id order by a.ad_user_id asc",startDate,endDate);

        //分管领导审批数
        List<Map<String,Object>> list5 = myJdbcTemplate.queryForList("select a.ad_user_id,(select name from ad_user where id = a.AD_USER_ID) as adUserName,ifnull(count(*),0) as instanceNums from wf_task a where a.status = 'ap' and a.WF_TASK_TYPE_ID = 'TODO' and a.ACT_DATETIME >= ? and a.ACT_DATETIME <= ? and a.ad_user_id in (select AD_USER_ID from hr_dept where status = 'AP' and AD_USER_ID is not null GROUP BY AD_USER_ID ORDER BY AD_USER_ID asc) GROUP BY a.ad_user_id order by a.ad_user_id asc",startDate,endDate);

        // 系统数据情况 prjNum-项目总数 projectAllTotalInvest-项目总投资 timeFrameNewPrjNums-时间范围内新增数 pmPrjReqNums-立项完成数 invest1Nums-可研完成数 invest2Nums-初概完成数 orderNums-合同总数 timeFrameOrderAmt-合同新增金额
        List<Map<String,Object>> list6 = myJdbcTemplate.queryForList("select a.prjNum,b.projectAllTotalInvest,c.zaiJianPrjNum,d.zaiJianJianAnAmt,e.timeFrameNewPrjNums,f.pmPrjReqNums,g.invest1Nums,i.invest2Nums,k.orderNums,j.timeFrameOrderAmt from (select count(*) as prjNum,'1' as id from pm_prj where status = 'AP' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' ) A LEFT JOIN (select sum(ESTIMATED_TOTAL_INVEST) as projectAllTotalInvest,'1' as id from pm_prj where status = 'AP' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374') B ON A.ID = B.ID left join (select count(*) as zaiJianPrjNum,'1' as id from pm_prj where status = 'AP' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and PROJECT_PHASE_ID in ('0099799190825080706','1673502467645648896') ) c on a.id = c.id left join (select sum(CONSTRUCT_PRJ_AMT) as zaiJianJianAnAmt,'1' as id from pm_prj where status = 'AP' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and PROJECT_PHASE_ID in ('0099799190825080706','1673502467645648896') ) d on a.id = d.id left join (select count(*) as timeFrameNewPrjNums,'1' as id from pm_prj where status = 'AP' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and CRT_DT >= ? and CRT_DT <= ?) e on a.id = e.id left join (select count(*) as pmPrjReqNums,'1' as id from pm_prj_req where status = 'AP') f on a.id = f.id left join (select count(*) as invest1Nums,'1' as id from PM_PRJ_INVEST1 where status = 'AP') g on a.id = g.id left join (select count(*) as invest2Nums,'1' as id from PM_PRJ_INVEST2 where status = 'AP') i on a.id = i.id left join (select sum(ifnull(AMT_TWO,0)) as timeFrameOrderAmt,'1' as id from po_order_req where status not in ('VD','VDING') and CRT_DT >= '2022-11-22 00:00:00' and (IS_IMPORT is null or IS_IMPORT = 0) and CRT_DT >=  ? AND CRT_DT <= ?) j on a.id = j.id left join (select count(*) as orderNums,'1' as id from po_order_req where status = 'AP' and CRT_DT >= '2022-11-22 00:00:00' ) k on a.id = k.id",startDate,endDate,startDate,endDate);

        Map<String,Object> map4 = queryNewInstanceDetail(startDate,endDate,myJdbcTemplate);
        Map<String,Object> resMap = new HashMap<>();
        resMap.put("weekProcessInstanceList",map4); // 查询时间范围内新增流程详情
        resMap.put("chargeUserLogin",list4); // 分管领导时间范围内登录次数
        resMap.put("chargeUserCheckNums",list5); // 分管领导时间范围内审批次数
        resMap.put("systemData",list6); // 系统数据情况
        resMap.put("weekUser",list2); // allUserNum-查询系统所有人员 weekUserLoginNum-本周登录人数 loginNums-总体登录次数
        resMap.put("weekProcess",list3); // allProcessInstanceNums-发起流程总数 needWriteNums-形象进度应填报 writeNums-形象进度实际填报 instanceNums-本周新增流程数量
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resMap), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 根据起止时间查询新增的流程详情
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @param myJdbcTemplate 数据源
     * @return 流程详情
     */
    private Map<String, Object> queryNewInstanceDetail(String startDate, String endDate, MyJdbcTemplate myJdbcTemplate) {
        Map<String,Object> map = new HashMap<>();
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select (select name from wf_process where id = a.WF_PROCESS_ID) as processName,a.WF_PROCESS_ID from wf_process_instance a where a.status = 'ap' and a.crt_dt >= '2022-11-22 00:00:00' and a.START_USER_ID != '0099250247095871681' and 0 = locate('历史数据导入',a.name)  and a.crt_dt >= ? and a.crt_dt <= ? GROUP BY a.WF_PROCESS_ID order by a.WF_PROCESS_ID asc",startDate,endDate);
        if (!CollectionUtils.isEmpty(list)){
            StringBuilder stringBuilder = new StringBuilder("select ");
            List<String> processIdList = list.stream().map(p->JdbcMapUtil.getString(p,"WF_PROCESS_ID")).collect(Collectors.toList());
            List<String> processNameList = list.stream().map(p->JdbcMapUtil.getString(p,"processName")).collect(Collectors.toList());
            for (String name : processNameList) {
                stringBuilder.append("group_concat(case when a.processName = '").append(name).append("' then a.instanceNums else '' end SEPARATOR '') as '").append(name).append("',");
            }
            stringBuilder.deleteCharAt(stringBuilder.length()-1);
            stringBuilder.append(" from ( select ( SELECT NAME FROM wf_process WHERE id = a.WF_PROCESS_ID ) AS processName,count(*) AS instanceNums from wf_process_instance a where a.STATUS = 'ap' AND a.crt_dt >= '2022-11-22 00:00:00' AND a.START_USER_ID != '0099250247095871681' AND 0 = locate( '历史数据导入', a.NAME ) AND a.crt_dt >= ? AND a.crt_dt <= ? group by a.WF_PROCESS_ID ORDER BY a.WF_PROCESS_ID ASC) a");
            List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(stringBuilder.toString(),startDate,endDate);
            map.put("nameList",processNameList);
            map.put("nums",list2);
        }
        return map;
    }
}
