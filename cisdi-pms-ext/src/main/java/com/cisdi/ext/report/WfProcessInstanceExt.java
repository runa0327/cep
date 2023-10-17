package com.cisdi.ext.report;

import com.cisdi.ext.model.AdUser;
import com.cisdi.ext.model.HrDept;
import com.cisdi.ext.model.view.process.WfProcessInstanceView;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

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
        StringBuilder sb = new StringBuilder("select a.id as wfProcessInstanceId,a.name as wfProcessInstanceName, c.name as startUserName,a.START_DATETIME as startDate,a.END_DATETIME as endDate,a.IS_URGENT as urgent,a.CURRENT_NODE_ID as wfNodeId,a.CURRENT_NI_ID as wfNodeInstanceId,a.CURRENT_TODO_USER_IDS as currentTodoUserIds from wf_process_instance a left join wf_process b on a.WF_PROCESS_ID = b.id left join ad_user c on a.START_USER_ID = c.id where a.status = 'ap'");
        addWhere(sb,param,myJdbcTemplate);
        sb.append(" order by a.CRT_DT desc ").append(limit);
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
            List<AdUser> userList = AdUser.selectByWhere(new Where().eq(AdUser.Cols.NAME,currentUser).eq(AdUser.Cols.STATUS,"AP"));
            if (!CollectionUtils.isEmpty(userList)){
                String currentUserId = userList.get(0).getId();
                sb.append(" and find_in_set('").append(currentUserId).append("',a.CURRENT_TODO_USER_IDS ");
            }
        }
        String deptId = param.getDeptId(); // 部门
        if (StringUtils.hasText(deptId)){
            HrDept hrDept = HrDept.selectById(deptId);
            if (hrDept != null){
                String deptName = hrDept.getName();
                List<String> deptIds = HrDept.selectByWhere(new Where().eq(HrDept.Cols.STATUS,"AP").eq(HrDept.Cols.NAME,deptName)).stream().map(HrDept::getId).collect(Collectors.toList());
                String deptIdStr = String.join("','",deptIds);
                List<Map<String,Object>> list = myJdbcTemplate.queryForList("select ad_user_id from hr_dept_user where hr_dept_id in ('"+deptIdStr+"')");
                if (!CollectionUtils.isEmpty(list)){
                    
                }
            } else {
                throw new BaseException("查询不到该部门信息！");
            }
        }
    }
}
