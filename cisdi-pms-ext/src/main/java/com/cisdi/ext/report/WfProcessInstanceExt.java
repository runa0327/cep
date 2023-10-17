package com.cisdi.ext.report;

import com.cisdi.ext.api.HrDeptExt;
import com.cisdi.ext.base.AdUserExt;
import com.cisdi.ext.model.AdUser;
import com.cisdi.ext.model.HrDept;
import com.cisdi.ext.model.view.base.HrDeptView;
import com.cisdi.ext.model.view.process.WfProcessInstanceView;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 流程监控台账
 */
public class WfProcessInstanceExt {

    /**
     * 流程监控-流程实例-列表
     */
    public void getWfProcessInstanceList(){
        // 获取输入：
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        WfProcessInstanceView param = JsonUtil.fromJson(json, WfProcessInstanceView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;
        StringBuilder sb = new StringBuilder("select a.id as wfProcessInstanceId,a.name as wfProcessInstanceName, c.name as startUserName,a.START_DATETIME as startDate,a.END_DATETIME as endDate,ifnull(a.IS_URGENT,0) as urgent,a.CURRENT_NODE_ID as wfNodeId,a.CURRENT_NI_ID as wfNodeInstanceId,a.CURRENT_TODO_USER_IDS as currentTodoUserIds,b.name as processName,d.name as wfNodeInstanceName,e.name as wfNodeName,a.wf_process_id as wfProcessId,a.CURRENT_VIEW_ID as currentViewId,b.EXTRA_INFO as icon,a.ENTITY_RECORD_ID as entityRecordId from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id left join ad_user c on a.START_USER_ID = c.id LEFT JOIN WF_NODE_INSTANCE d on a.CURRENT_NI_ID = d.id left join wf_node e on d.wf_node_id = e.id where a.status = 'ap'");
        addWhere(sb,param,myJdbcTemplate);
        sb.append(" order by a.CRT_DT desc ");
        List<Map<String,Object>> lis2 = myJdbcTemplate.queryForList(String.valueOf(sb));
        sb.append(limit);
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sb.toString());
        if (!CollectionUtils.isEmpty(list)){
            List<WfProcessInstanceView> wfProcessInstanceViewList = new ArrayList<>();
            list.forEach(p->{
                WfProcessInstanceView wfProcessInstanceView = new WfProcessInstanceView();
                wfProcessInstanceValue(p,wfProcessInstanceView,myJdbcTemplate);
                wfProcessInstanceViewList.add(wfProcessInstanceView);
            });
            HashMap<String, Object> result = new HashMap<>();
            result.put("list",wfProcessInstanceViewList);
            result.put("total",lis2.size());
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 流程监控查询出的数据赋值给实体
     * @param map 单条查询记录
     * @param wfProcessInstanceView 实例信息
     * @param myJdbcTemplate 数据源
     */
    private void wfProcessInstanceValue(Map<String, Object> map, WfProcessInstanceView wfProcessInstanceView, MyJdbcTemplate myJdbcTemplate) {
        wfProcessInstanceView.setWfProcessInstanceId(JdbcMapUtil.getString(map,"wfProcessInstanceId")); // id
        wfProcessInstanceView.setWfProcessInstanceName(JdbcMapUtil.getString(map,"wfProcessInstanceName")); // 名称
        wfProcessInstanceView.setProcessName(JdbcMapUtil.getString(map,"processName")); // 流程名称
        wfProcessInstanceView.setStartUserName(JdbcMapUtil.getString(map,"startUserName")); // 启动用户
        wfProcessInstanceView.setStartDate(JdbcMapUtil.getString(map,"startDate").replace("T"," ")); // 开始时间
        String endDate = JdbcMapUtil.getString(map,"endDate"); // 结束时间
        if (StringUtils.hasText(endDate)){
            wfProcessInstanceView.setEndDate(endDate.replace("T"," "));
        }
        wfProcessInstanceView.setUrgent(JdbcMapUtil.getInt(map,"urgent")); // 是否紧急
        wfProcessInstanceView.setWfNodeInstanceName(JdbcMapUtil.getString(map,"wfNodeInstanceName")); // 当前节点实例名称
        wfProcessInstanceView.setWfNodeName(JdbcMapUtil.getString(map,"wfNodeName")); // 当前节点名称
        String currentTodoUserIds = JdbcMapUtil.getString(map,"currentTodoUserIds"); // 当前代办用户
        if (StringUtils.hasText(currentTodoUserIds)){
            String userName = AdUserExt.getUserNameById(currentTodoUserIds,myJdbcTemplate);
            if (StringUtils.hasText(userName)){
                wfProcessInstanceView.setCurrentToDoUserNames(userName);
            }
        }
        wfProcessInstanceView.setWfProcessId(JdbcMapUtil.getString(map,"wfProcessId")); // 流程id
        wfProcessInstanceView.setCurrentViewId(JdbcMapUtil.getString(map,"currentViewId")); // 当前代办视图
        wfProcessInstanceView.setIcon(JdbcMapUtil.getString(map,"icon")); // 流程图标
        wfProcessInstanceView.setEntityRecordId(JdbcMapUtil.getString(map,"entityRecordId")); // 当条业务记录id
    }

    /**
     * 流程监控-添加模糊查询条件
     * @param sb sql语句
     * @param param 实体判断条件
     * @param myJdbcTemplate 数据源
     */
    private void addWhere(StringBuilder sb, WfProcessInstanceView param, MyJdbcTemplate myJdbcTemplate) {
        String wfProcessInstanceName = param.getWfProcessInstanceName(); // 名称
        if (StringUtils.hasText(wfProcessInstanceName)){
            sb.append(" and a.name like ('%").append(wfProcessInstanceName).append("%') ");
        }
        String startUserId = param.getStartUserId(); // 启动用户
        if (StringUtils.hasText(startUserId)){
            sb.append(" and a.START_USER_ID = '").append(startUserId).append("' ");
        }
        String startDateMin = param.getStartDateMin(); // 最小发起时间
        String startDateMax = param.getStartDateMax(); // 最大发起时间
        if (StringUtils.hasText(startDateMin)){
            sb.append(" and a.START_DATETIME >= '").append(startDateMin).append("' ");
        }
        if (StringUtils.hasText(startDateMax)){
            sb.append(" and a.START_DATETIME <= '").append(startDateMax).append("' ");
        }
        String currentUser = param.getCurrentToDoUserNames(); // 当前代办用户
        if (StringUtils.hasText(currentUser)){
            List<Map<String,Object>> userList = myJdbcTemplate.queryForList("select id from ad_user where status = 'ap' and name like ('%"+currentUser+"%')");
            if (!CollectionUtils.isEmpty(userList)){
                String currentUserId = JdbcMapUtil.getString(userList.get(0),"id");
                sb.append(" and find_in_set('").append(currentUserId).append("',a.CURRENT_TODO_USER_IDS ");
            } else {
                sb.append(" and find_in_set('").append(currentUser).append("',a.CURRENT_TODO_USER_IDS ");
            }
        }
        String deptId = param.getDeptId(); // 部门
        if (StringUtils.hasText(deptId)){
            HrDept hrDept = HrDept.selectById(deptId);
            if (hrDept != null){
                List<HrDeptView> deptList = HrDeptExt.getDeptListById(deptId,myJdbcTemplate);
                if (!CollectionUtils.isEmpty(deptList)){
                    List<String> nameList = deptList.stream().map(HrDeptView::getName).collect(Collectors.toList());
                    nameList.add(hrDept.getName());
                    List<String> userList = HrDeptExt.getUserByNameLike(nameList,myJdbcTemplate);
                    if (!CollectionUtils.isEmpty(userList)){
                        if (userList.size() <= 150){
                            String userIds = String.join("','",userList);
                            sb.append(" and a.id in (select distinct wf_process_instance_id from wf_task where status = 'ap' and wf_task_type_id = 'TODO' AND ACT_DATETIME is not null and ad_user_id in ('").append(userIds).append("')) ");
                        }
                    }
                }
            } else {
                throw new BaseException("查询不到该部门信息！");
            }
        }
        String checkUserId = param.getCheckUserId(); // 审批人
        if (StringUtils.hasText(checkUserId)){
            sb.append(" and a.id in (select distinct wf_process_instance_id from wf_task where status = 'ap' and wf_task_type_id = 'TODO' and ACT_DATETIME is not null and ad_user_id = '").append(checkUserId).append("' ) ");
        }
        String checkDateMin = param.getCheckDateMin(); // 最小审批时间
        String checkDateMax = param.getCheckDateMax(); // 最大审批时间
        if (StringUtils.hasText(checkDateMin)){
            sb.append(" and a.id in (select distinct wf_process_instance_id from wf_task where status = 'ap' and wf_task_type_id = 'TODO' and ACT_DATETIME >= '").append(checkDateMin).append("' ");
        }
        if (StringUtils.hasText(checkDateMax)){
            sb.append(" and a.id in (select distinct wf_process_instance_id from wf_task where status = 'ap' and wf_task_type_id = 'TODO' and ACT_DATETIME <= '").append(checkDateMax).append("' ");
        }
    }
}
