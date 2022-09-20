package com.cisdi.ext.api;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmProNodeSeqInit
 * @package com.cisdi.ext.api
 * @description 项目进度计划节点序列刷新
 * @date 2022/9/20
 */
public class PmProNodeSeqApi {

    public void initPlanSeq() {
        MyJdbcTemplate jdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_pro_plan");
        list.forEach(item -> {
            List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select ID,CODE,NAME,REMARK,ACTUAL_START_DATE," +
                    "PROGRESS_RISK_REMARK,PM_PRO_PLAN_ID,PLAN_START_DATE,PLAN_TOTAL_DAYS,PLAN_CARRY_DAYS,ACTUAL_CARRY_DAYS,ACTUAL_TOTAL_DAYS," +
                    "PLAN_CURRENT_PRO_PERCENT,ACTUAL_CURRENT_PRO_PERCENT,ifnull(PM_PRO_PLAN_NODE_PID,'0') as PM_PRO_PLAN_NODE_PID,PLAN_COMPL_DATE," +
                    "ACTUAL_COMPL_DATE,SHOW_IN_EARLY_PROC,SHOW_IN_PRJ_OVERVIEW,PROGRESS_STATUS_ID,PROGRESS_RISK_TYPE_ID,CHIEF_DEPT_ID,CHIEF_USER_ID," +
                    "START_DAY,ifnull(SEQ_NO,'0') as SEQ_NO,CPMS_UUID,CPMS_ID,LEVEL,LINKED_WF_PROCESS_ID,LINKED_WF_NODE_ID,LINKED_WF_PROCESS_INSTANCE_ID,LINKED_WF_NODE_INSTANCE_ID," +
                    "SCHEDULE_NAME,SEQ_NO_BAK from pm_pro_plan_node where PM_PRO_PLAN_ID=?", item.get("ID"));

            List<NodeInfo> nodeInfoList = nodeList.stream().map(m -> {
                NodeInfo info = new NodeInfo();
                info.id = JdbcMapUtil.getString(m, "ID");
                info.pid = JdbcMapUtil.getString(m, "PM_PRO_PLAN_NODE_PID");
                info.seqNo = JdbcMapUtil.getString(m, "SEQ_NO");
                info.seqNoBak = JdbcMapUtil.getString(m, "SEQ_NO_BAK");
                return info;
            }).collect(Collectors.toList());

            AtomicInteger index = new AtomicInteger(1);
            nodeInfoList.stream().filter(p -> Objects.equals("0", String.valueOf(p.pid))).sorted(Comparator.comparing(o -> o.seqNo)).forEach(m -> {
                jdbcTemplate.update("update pm_pro_plan_node set SEQ_NO_BAK =? where id=?", index.getAndIncrement(), m.id);
                getChildren(m, nodeInfoList, jdbcTemplate);
            });
        });
    }

    private List<NodeInfo> getChildren(NodeInfo parent, List<NodeInfo> allData, MyJdbcTemplate jdbcTemplate) {
        AtomicInteger index = new AtomicInteger(1);
        return allData.stream().filter(p -> Objects.equals(parent.id, p.pid)).sorted(Comparator.comparing(o -> o.seqNo)).peek(m -> {
            int seq = index.getAndIncrement();
            jdbcTemplate.update("update pm_pro_plan_node set SEQ_NO_BAK =? where id=?", seq, m.id);
            getChildren(m, allData, jdbcTemplate);
        }).collect(Collectors.toList());
    }

    public static class NodeInfo {
        public String id;
        public String pid;
        public String seqNo;
        public String seqNoBak;
    }
}
