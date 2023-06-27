package com.cisdi.ext.util;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title WeekTaskUtils
 * @package com.cisdi.ext.util
 * @description
 * @date 2023/6/26
 */
public class WeekTaskUtils {

    public static final String NOT_STARTED = "0099799190825106800";
    public static final String IN_PROCESSING = "0099799190825106801";
    public static final String COMPLETED = "0099799190825106802";


    /**
     * 当前节点完成时，给他的后续节点发周任务
     *
     * @param nodeId 个参数节点的后置节点发
     */
    public static void sendPreNodeWeekTask(String nodeId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pm.`NAME` as prjName,pppn.*,pi.AD_USER_ID as default_user,pm.id as projectId from pm_pro_plan_node pppn \n" +
                "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID \n" +
                "left join pm_prj pm on pm.id = ppp.PM_PRJ_ID  \n" +
                "left join POST_INFO pi on pi.id = pppn.POST_INFO_ID  \n" +
                "where PRE_NODE_ID =?", nodeId);

        String msg = "{0}【{1}】计划将在{2}完成，请及时处理！";
        for (Map<String, Object> objectMap : list) {
            // 当节点状态是未启动的时候才发周任务
            String status = JdbcMapUtil.getString(objectMap, "PROGRESS_STATUS_ID");
            if (NOT_STARTED.equals(status)) {
                String id = Crud.from("WEEK_TASK").insertData();
                String userId = JdbcMapUtil.getString(objectMap, "CHIEF_USER_ID");
                if (Objects.isNull(objectMap.get("CHIEF_USER_ID"))) {
                    userId = JdbcMapUtil.getString(objectMap, "default_user");
                }
                String processName = JdbcMapUtil.getString(objectMap, "NAME");
                if (Objects.nonNull(objectMap.get("LINKED_WF_PROCESS_ID"))) {
                    // 取流程名称
                    List<Map<String, Object>> processlist = myJdbcTemplate.queryForList("select * from WF_PROCESS where id=?", objectMap.get("LINKED_WF_PROCESS_ID"));
                    if (!CollectionUtils.isEmpty(processlist)) {
                        Map<String, Object> dataMap = processlist.get(0);
                        processName = JdbcMapUtil.getString(dataMap, "NAME");
                    }
                }
                String dateOrg = "";
                if (Objects.nonNull(objectMap.get("PLAN_COMPL_DATE"))) {
                    Date compDate = JdbcMapUtil.getDate(objectMap, "PLAN_COMPL_DATE");
                    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                    dateOrg = sp.format(compDate);
                }
                String title = objectMap.get("prjName") + "-" + processName;
                String content = MessageFormat.format(msg, objectMap.get("prjName"), processName, dateOrg);
                myJdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=? where id=?",
                        userId, title, content, new Date(), "1634118574056542208", "1635080848313290752", objectMap.get("ID"), objectMap.get("projectId"), id);
            }
        }
    }

    /**
     * 发消息代表
     * @param nodeId 给参数节点发工作任务
     */
    public static void sendWeekTask(String nodeId){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pm.`NAME` as prjName,pppn.*,pi.AD_USER_ID as default_user,pm.id as projectId from pm_pro_plan_node pppn \n" +
                "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID \n" +
                "left join pm_prj pm on pm.id = ppp.PM_PRJ_ID  \n" +
                "left join POST_INFO pi on pi.id = pppn.POST_INFO_ID  \n" +
                "where pppn.id =?", nodeId);

        String msg = "{0}【{1}】计划将在{2}完成，请及时处理！";
        for (Map<String, Object> objectMap : list) {
            // 当节点状态是未启动的时候才发周任务
            String status = JdbcMapUtil.getString(objectMap, "PROGRESS_STATUS_ID");
            if (NOT_STARTED.equals(status)) {
                String id = Crud.from("WEEK_TASK").insertData();
                String userId = JdbcMapUtil.getString(objectMap, "CHIEF_USER_ID");
                if (Objects.isNull(objectMap.get("CHIEF_USER_ID"))) {
                    userId = JdbcMapUtil.getString(objectMap, "default_user");
                }
                String processName = JdbcMapUtil.getString(objectMap, "NAME");
                if (Objects.nonNull(objectMap.get("LINKED_WF_PROCESS_ID"))) {
                    // 取流程名称
                    List<Map<String, Object>> processlist = myJdbcTemplate.queryForList("select * from WF_PROCESS where id=?", objectMap.get("LINKED_WF_PROCESS_ID"));
                    if (!CollectionUtils.isEmpty(processlist)) {
                        Map<String, Object> dataMap = processlist.get(0);
                        processName = JdbcMapUtil.getString(dataMap, "NAME");
                    }
                }
                String dateOrg = "";
                if (Objects.nonNull(objectMap.get("PLAN_COMPL_DATE"))) {
                    Date compDate = JdbcMapUtil.getDate(objectMap, "PLAN_COMPL_DATE");
                    SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                    dateOrg = sp.format(compDate);
                }
                String title = objectMap.get("prjName") + "-" + processName;
                String content = MessageFormat.format(msg, objectMap.get("prjName"), processName, dateOrg);
                myJdbcTemplate.update("update WEEK_TASK set AD_USER_ID=?,TITLE=?,CONTENT=?,PUBLISH_START=?,WEEK_TASK_STATUS_ID=?,WEEK_TASK_TYPE_ID=?,RELATION_DATA_ID=?,CAN_DISPATCH='0',PM_PRJ_ID=? where id=?",
                        userId, title, content, new Date(), "1634118574056542208", "1635080848313290752", objectMap.get("ID"), objectMap.get("projectId"), id);
            }
        }
    }

}
