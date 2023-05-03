package com.cisdi.pms.job.history;

import com.cisdi.pms.job.utils.ListUtils;
import com.cisdi.pms.job.utils.Util;
import com.google.common.base.Strings;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProPlanController
 * @package com.cisdi.pms.job.history
 * @description
 * @date 2023/5/3
 */
@Slf4j
@RestController
@RequestMapping("proPlan")
public class ProPlanController {

    @Autowired
    private JdbcTemplate myJdbcTemplate;

    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;


    @GetMapping("/refreshData")
    @Async("taskExecutor")
    public String intiPrjProPlan() {

        myJdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0;delete from pm_pro_plan_node where pm_pro_plan_id in (select id from pm_pro_plan where IS_TEMPLATE <>'1');SET FOREIGN_KEY_CHECKS = 1;");
        myJdbcTemplate.update("delete from pm_pro_plan where IS_TEMPLATE <>'1'");

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where status ='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' order by pm_code desc");

        List<List<Map<String, Object>>> dataList = ListUtils.split(list, 50);
        AtomicInteger index = new AtomicInteger(0);
        dataList.forEach(item -> {
            log.info("定时刷新进度计划状态线程运行--------------------当前进程第" + index.getAndIncrement() + "个");
            taskExecutor.execute(() -> {
                for (Map<String, Object> objectMap : item) {
                    createPlan(JdbcMapUtil.getString(objectMap, "ID"));
                }
            });
        });
        return "正在处理";
    }


    private void createPlan(String projectId) {
        //根据规则查询模板
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRO_PLAN where id = '1648561483233353728'");

        if (!CollectionUtils.isEmpty(list)) {
            //先清出原来的
            clearPlan(projectId);

            Map<String, Object> proMap = list.get(0);
            // 先创建项目的进度计划

            String newPlanId = Util.insertData(myJdbcTemplate, "PM_PRO_PLAN");

            myJdbcTemplate.update("update PM_PRO_PLAN set IS_TEMPLATE=0 ,PM_PRJ_ID=?,PLAN_TOTAL_DAYS=?,PLAN_TOTAL_DAYS=?,PROGRESS_STATUS_ID=?,PROGRESS_RISK_TYPE_ID=?,START_DAY=? where id=?",
                    projectId, proMap.get("PLAN_TOTAL_DAYS"), proMap.get("PROGRESS_STATUS_ID"), proMap.get("PROGRESS_RISK_TYPE_ID"), proMap.get("START_DAY"),newPlanId);

            // 查询项目进度计划节点模板
            List<Map<String, Object>> planNodeList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT, \n" +
                    "pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,ACTUAL_START_DATE,PROGRESS_RISK_REMARK,PM_PRO_PLAN_ID,PLAN_START_DATE, \n" +
                    "PLAN_TOTAL_DAYS,PLAN_CARRY_DAYS,ACTUAL_CARRY_DAYS,ACTUAL_TOTAL_DAYS,PLAN_CURRENT_PRO_PERCENT,ACTUAL_CURRENT_PRO_PERCENT, \n" +
                    "ifnull(PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,SHOW_IN_EARLY_PROC,SHOW_IN_PRJ_OVERVIEW,CAN_START, \n" +
                    "PROGRESS_STATUS_ID,PROGRESS_RISK_TYPE_ID,CHIEF_DEPT_ID,CHIEF_USER_ID,START_DAY,SEQ_NO,CPMS_UUID,CPMS_ID,`LEVEL`,LINKED_WF_PROCESS_ID,LINKED_START_WF_NODE_ID,LINKED_END_WF_NODE_ID,POST_INFO_ID ,AD_USER_ID, \n" +
                    "PRE_NODE_ID,AD_ENT_ID_IMP,AD_ATT_ID_IMP,IZ_MILESTONE,SCHEDULE_NAME  " +
                    "from PM_PRO_PLAN_NODE pppn \n" +
                    "left join POST_INFO pi on pppn.POST_INFO_ID = pi.id \n" +
                    "where PM_PRO_PLAN_ID=?", proMap.get("ID"));
            if (planNodeList.size() > 0) {
                planNodeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
                    String id = Util.insertData(myJdbcTemplate, "PM_PRO_PLAN_NODE");

                    myJdbcTemplate.update("update PM_PRO_PLAN_NODE set NAME=?,PM_PRO_PLAN_ID=?, PLAN_TOTAL_DAYS=?,PROGRESS_STATUS_ID=?,PROGRESS_RISK_TYPE_ID=?,CHIEF_DEPT_ID=?,START_DAY=?,SEQ_NO=?," +
                                    "LEVEL=?,LINKED_WF_PROCESS_ID=?,LINKED_START_WF_NODE_ID=?,LINKED_END_WF_NODE_ID=?,SHOW_IN_EARLY_PROC=?,SHOW_IN_PRJ_OVERVIEW=?,POST_INFO_ID=?,CHIEF_USER_ID=?,CAN_START=?,PRE_NODE_ID=?," +
                                    "AD_ENT_ID_IMP=?,AD_ENT_ID_IMP=?,AD_ATT_ID_IMP=?,IZ_MILESTONE=?,SCHEDULE_NAME=? where id=?", m.get("NAME"), newPlanId, m.get("PLAN_TOTAL_DAYS"), m.get("PROGRESS_STATUS_ID"), m.get("PROGRESS_RISK_TYPE_ID"),
                            m.get("CHIEF_DEPT_ID"), m.get("START_DAY"), m.get("SEQ_NO"), m.get("LEVEL"), m.get("LINKED_WF_PROCESS_ID"), m.get("LINKED_START_WF_NODE_ID"), m.get("LINKED_END_WF_NODE_ID"), m.get("SHOW_IN_EARLY_PROC"),
                            m.get("SHOW_IN_PRJ_OVERVIEW"), m.get("POST_INFO_ID"), m.get("AD_USER_ID"), m.get("CAN_START"), m.get("PRE_NODE_ID"), m.get("AD_ENT_ID_IMP"), m.get("AD_ATT_ID_IMP"), m.get("IZ_MILESTONE"), m.get("SCHEDULE_NAME"), id);

                    getChildrenNode(m, planNodeList, id, newPlanId);
                }).collect(Collectors.toList());

                //刷新前置节点
                List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where PM_PRO_PLAN_ID =(select id from PM_PRO_PLAN where PM_PRJ_ID=?)", projectId);
                if (!CollectionUtils.isEmpty(nodeList)) {
                    nodeList.forEach(item -> {
                        //查找模板种相同的节点
                        Optional<Map<String, Object>> optional = planNodeList.stream().filter(p -> Objects.equals(item.get("NAME"), p.get("NAME"))).findAny();
                        if (optional.isPresent()) {
                            Map<String, Object> tempNode = optional.get();
                            String tempPreNodeId = JdbcMapUtil.getString(tempNode, "PRE_NODE_ID");
                            if (!Strings.isNullOrEmpty(tempPreNodeId)) {
                                //找到模板种前置节点对应的节点
                                Optional<Map<String, Object>> tempPreOptional = planNodeList.stream().filter(p -> Objects.equals(tempPreNodeId, p.get("ID"))).findAny();
                                if (tempPreOptional.isPresent()) {
                                    String tempPreNodeName = JdbcMapUtil.getString(tempPreOptional.get(), "NAME");
                                    Optional<Map<String, Object>> preNodeOptional = nodeList.stream().filter(f -> Objects.equals(tempPreNodeName, f.get("NAME"))).findAny();
                                    if (preNodeOptional.isPresent()) {
                                        String preNodeId = JdbcMapUtil.getString(preNodeOptional.get(), "ID");
                                        myJdbcTemplate.update("update PM_PRO_PLAN_NODE set PRE_NODE_ID=? where id=?", preNodeId, item.get("ID"));
                                    }
                                }
                            }
                        }
                    });
                }

            }
        }
    }

    private void clearPlan(String projectId) {
        myJdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0;delete from PM_PRO_PLAN_NODE where PM_PRO_PLAN_ID in (select id from PM_PRO_PLAN where PM_PRJ_ID=?);SET FOREIGN_KEY_CHECKS = 1;", projectId);
        myJdbcTemplate.update("delete from PM_PRO_PLAN where PM_PRJ_ID=?", projectId);
    }

    private List<Map<String, Object>> getChildrenNode(Map<String, Object> root, List<Map<String, Object>> allData, String pId, String newPlanId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")), String.valueOf(root.get("ID")))).peek(m -> {

            String id = Util.insertData(myJdbcTemplate, "PM_PRO_PLAN_NODE");

            myJdbcTemplate.update("update PM_PRO_PLAN_NODE set NAME=?,PM_PRO_PLAN_ID=?, PLAN_TOTAL_DAYS=?,PROGRESS_STATUS_ID=?,PROGRESS_RISK_TYPE_ID=?,CHIEF_DEPT_ID=?,START_DAY=?,SEQ_NO=?," +
                            "LEVEL=?,LINKED_WF_PROCESS_ID=?,LINKED_START_WF_NODE_ID=?,LINKED_END_WF_NODE_ID=?,SHOW_IN_EARLY_PROC=?,SHOW_IN_PRJ_OVERVIEW=?,POST_INFO_ID=?,CHIEF_USER_ID=?,CAN_START=?,PRE_NODE_ID=?," +
                            "AD_ENT_ID_IMP=?,AD_ENT_ID_IMP=?,AD_ATT_ID_IMP=?,IZ_MILESTONE=?,SCHEDULE_NAME=? where id=?", m.get("NAME"), newPlanId, m.get("PLAN_TOTAL_DAYS"), m.get("PROGRESS_STATUS_ID"), m.get("PROGRESS_RISK_TYPE_ID"),
                    m.get("CHIEF_DEPT_ID"), m.get("START_DAY"), m.get("SEQ_NO"), m.get("LEVEL"), m.get("LINKED_WF_PROCESS_ID"), m.get("LINKED_START_WF_NODE_ID"), m.get("LINKED_END_WF_NODE_ID"), m.get("SHOW_IN_EARLY_PROC"),
                    m.get("SHOW_IN_PRJ_OVERVIEW"), m.get("POST_INFO_ID"), m.get("AD_USER_ID"), m.get("CAN_START"), m.get("PRE_NODE_ID"), m.get("AD_ENT_ID_IMP"), m.get("AD_ATT_ID_IMP"), m.get("IZ_MILESTONE"), m.get("SCHEDULE_NAME"), id);

            getChildrenNode(m, allData, id, newPlanId);
        }).collect(Collectors.toList());
    }
}
