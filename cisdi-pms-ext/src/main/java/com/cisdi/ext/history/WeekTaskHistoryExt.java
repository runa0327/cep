package com.cisdi.ext.history;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WeekTaskHistoryExt
 * @package com.cisdi.ext.history
 * @description 工作台历史错误数据处理
 * @date 2023/12/6
 */
public class WeekTaskHistoryExt {

    public void reventWeekTaskData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //清除脏数据,（节点状态是完成的，工作任务还没完成）
        myJdbcTemplate.update("delete from week_task where id in ( " +
                " select * from ( " +
                " select wt.id from week_task wt left join pm_pro_plan_node pppn on wt.RELATION_DATA_ID = pppn.ID " +
                " where pppn.PROGRESS_STATUS_ID='0099799190825106802' and wt.WEEK_TASK_STATUS_ID !='1634118629769482240' and wt.WEEK_TASK_TYPE_ID='1635080848313290752' " +
                " ) a " +
                ")");
        //处理刷新全景数据导致的工作任务跟全景节点不匹配的问题
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select id,TITLE,PM_PRJ_ID from week_task where  WEEK_TASK_TYPE_ID='1635080848313290752' and `STATUS`='ap' and RELATION_DATA_ID  not in (select id from pm_pro_plan_node);");
        list.forEach(item -> {
            String nodeName = JdbcMapUtil.getString(item, "TITLE").split("-")[1];
            String prjId = JdbcMapUtil.getString(item, "PM_PRJ_ID");
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select pppn.id as id,pppn.`NAME` from pm_pro_plan_node pppn left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID where PM_PRJ_ID= ? and pppn.`NAME`=?", prjId, nodeName);
            if (!CollectionUtils.isEmpty(list1)) {
                String nodeId = JdbcMapUtil.getString(list1.get(0), "id");
                myJdbcTemplate.update("update week_task set RELATION_DATA_ID=? where id= ?", nodeId, item.get("id"));
            } else {
                myJdbcTemplate.update("delete from week_task where id=?", item.get("ID"));
            }
        });

    }
}
