package com.cisdi.ext.proPlan;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import io.netty.util.internal.ObjectUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ProPlanExt {
    public void calcPlanTotalDays() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            calcPlanTotalDays(entityRecord);
        }
    }

    private void calcPlanTotalDays(EntityRecord entityRecord) {
        StringBuilder sbErr = new StringBuilder();
        if (entityRecord.extraEditableAttCodeList == null) {
            entityRecord.extraEditableAttCodeList = new ArrayList<>();
        }
        String csCommId = entityRecord.csCommId;
        //最小开始天数
        int minStartDay = 0;
        //最大计划天
        int maxPlanDay = 0;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        //查询项目进度计划模板
//        Map<String, Object> proMap = jdbcTemplate.queryForMap("select * from PM_PRO_PLAN where id=?", csCommId);

        //查询进度节点明细
        List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT," +
                "LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,ACTUAL_START_DATE,PROGRESS_RISK_REMARK,PM_PRO_PLAN_ID,PLAN_START_DATE," +
                "ifnull(PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,PLAN_CARRY_DAYS,ACTUAL_CARRY_DAYS,ACTUAL_TOTAL_DAYS,PLAN_CURRENT_PRO_PERCENT," +
                "ACTUAL_CURRENT_PRO_PERCENT,ifnull(PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,SHOW_IN_EARLY_PROC,SHOW_IN_PRJ_OVERVIEW," +
                "PROGRESS_STATUS_ID,PROGRESS_RISK_TYPE_ID,CHIEF_DEPT_ID,CHIEF_USER_ID,ifnull(START_DAY,0) as START_DAY from PM_PRO_PLAN_NODE " +
                "where PM_PRO_PLAN_ID=?", csCommId);


        if (nodeList != null && nodeList.size() > 0) {
            nodeList.forEach(item -> {
                List<Map<String, Object>> childrenList = getChildren(item, nodeList);
                if (childrenList != null && childrenList.size() > 0) {
                    // 至少1个最末级节点的START_DAY要为1（至少1个最末级节点，要从第1天开始做），否则报错、返回：
                    int minChildStartDay = childrenList.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("START_DAY")))).min().orElse(0);
                    if (minChildStartDay != 1) {
                        throw new BaseException("至少1个最末级节点，要从第1天开始做");
                    }
                    // 所有最末级节点，必须START_DAY>=1，PLAN_TOTAL_DAYS>=1，否则报错、返回：
                    int minPlanDay = childrenList.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("PLAN_TOTAL_DAYS")))).min().orElse(0);
                    if (minPlanDay < 1) {
                        throw new BaseException("预计天数必须大于1");
                    }
                    // 从倒数第2级节点往上推导START_DAY、PLAN_TOTAL_DAYS，直到计划上：
                    // 父节点的START_DAY=所有直接子节点的START_DAY的最小值
                    // 父节点的PLAN_TOTAL_DAYS=所有直接子节点的（START_DAY+PLAN_TOTAL_DAYS-1）的最大值-父节点的START_DAY+1
                    // 成功返回。
                    getParentData(item, childrenList);
                }

            });
            //处理进度计划
            List<Map<String, Object>> firstNodeList = nodeList.stream().filter(p -> Objects.equals("0", p.get("PM_PRO_PLAN_NODE_PID"))).collect(Collectors.toList());
            minStartDay = firstNodeList.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("START_DAY")))).min().orElse(0);
            maxPlanDay = firstNodeList.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("PLAN_TOTAL_DAYS")))).max().orElse(0);
        }
        //操作数据库，更新数据,更新项目进度
        jdbcTemplate.update("update PM_PRO_PLAN set START_DAY=?,PLAN_TOTAL_DAYS=? where id=?", minStartDay, maxPlanDay, csCommId);
        //操作数据库，更新数据,更新项目进度节点
        nodeList.forEach(item->{
            jdbcTemplate.update("update PM_PRO_PLAN_NODE set START_DAY=?,PLAN_TOTAL_DAYS=? where id=?",item.get("START_DAY"),item.get("PLAN_TOTAL_DAYS"),item.get("ID"));
        });

    }

    /**
     * 递归获取子节点
     *
     * @param root
     * @param allData
     * @return
     */
    private List<Map<String, Object>> getChildren(Map<String, Object> root, List<Map<String, Object>> allData) {
        return allData.stream().filter(p -> Objects.equals(root.get("ID"), p.get("PM_PRO_PLAN_NODE_PID")))
                .map(m -> {
                    List<Map<String, Object>> child = getChildren(m, allData);
                    m = getParentData(m, child);
                    return m;
                }).collect(Collectors.toList());
    }

    /**
     * 计算父节点的开始时间和总工时
     *
     * @param root
     * @param child
     * @return
     */
    private Map<String, Object> getParentData(Map<String, Object> root, List<Map<String, Object>> child) {
        if (child != null && child.size() > 0) {
            //获取开始天
            int minDay = child.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("START_DAY")))).min().orElse(0);
            //获取最大的用时
            int maxUseDay = child.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("START_DAY"))) + Integer.parseInt(String.valueOf(p.get("PLAN_TOTAL_DAYS"))) - 1).max().orElse(0);
            int useDay = maxUseDay - minDay + 1;
            root.put("START_DAY", minDay);
            root.put("PLAN_TOTAL_DAYS", useDay);
        }
        return root;
    }
}
