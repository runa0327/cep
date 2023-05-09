package com.cisdi.pms.job.history;

import com.cisdi.pms.job.utils.DateUtil;
import com.cisdi.pms.job.utils.ListUtils;
import com.cisdi.pms.job.utils.MapUtils;
import com.cisdi.pms.job.utils.Util;
import com.google.common.base.Strings;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
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


    /**
     * 初始化默认模板
     *
     * @return
     */
    @GetMapping("/refreshData")
    public String intiPrjProPlan() {

        myJdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0;delete from pm_pro_plan_node where pm_pro_plan_id in (select id from pm_pro_plan where IS_TEMPLATE <>'1');SET FOREIGN_KEY_CHECKS = 1;");
        myJdbcTemplate.update("delete from pm_pro_plan where IS_TEMPLATE <>'1'");

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where status ='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' order by pm_code desc");

        List<List<Map<String, Object>>> dataList = ListUtils.split(list, 50);
        AtomicInteger index = new AtomicInteger(0);
        dataList.forEach(item -> {
            log.info("刷新全景多线程运行--------------------当前进程第" + index.getAndIncrement() + "个");
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

            myJdbcTemplate.update("update PM_PRO_PLAN set IS_TEMPLATE=0 ,PM_PRJ_ID=?,PLAN_TOTAL_DAYS=?,PROGRESS_STATUS_ID=?,PROGRESS_RISK_TYPE_ID=?,START_DAY=? where id=?",
                    projectId, proMap.get("PLAN_TOTAL_DAYS"), proMap.get("PROGRESS_STATUS_ID"), proMap.get("PROGRESS_RISK_TYPE_ID"), proMap.get("START_DAY"), newPlanId);

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
                                    "AD_ENT_ID_IMP=?,AD_ATT_ID_IMP=?,IZ_MILESTONE=?,SCHEDULE_NAME=? where id=?", m.get("NAME"), newPlanId, m.get("PLAN_TOTAL_DAYS"), m.get("PROGRESS_STATUS_ID"), m.get("PROGRESS_RISK_TYPE_ID"),
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
                            "AD_ENT_ID_IMP=?,AD_ATT_ID_IMP=?,IZ_MILESTONE=?,SCHEDULE_NAME=?,PM_PRO_PLAN_NODE_PID=? where id=?", m.get("NAME"), newPlanId, m.get("PLAN_TOTAL_DAYS"), m.get("PROGRESS_STATUS_ID"), m.get("PROGRESS_RISK_TYPE_ID"),
                    m.get("CHIEF_DEPT_ID"), m.get("START_DAY"), m.get("SEQ_NO"), m.get("LEVEL"), m.get("LINKED_WF_PROCESS_ID"), m.get("LINKED_START_WF_NODE_ID"), m.get("LINKED_END_WF_NODE_ID"), m.get("SHOW_IN_EARLY_PROC"),
                    m.get("SHOW_IN_PRJ_OVERVIEW"), m.get("POST_INFO_ID"), m.get("AD_USER_ID"), m.get("CAN_START"), m.get("PRE_NODE_ID"), m.get("AD_ENT_ID_IMP"), m.get("AD_ATT_ID_IMP"), m.get("IZ_MILESTONE"), m.get("SCHEDULE_NAME"), pId, id);

            getChildrenNode(m, allData, id, newPlanId);
        }).collect(Collectors.toList());
    }


    /**
     * 初始化责任用户
     *
     * @return
     */
    @GetMapping("/refreshUser")
    public String initPrjProPlanUser() {
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_roster where status='ap'");
        List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select pn.*,pl.PM_PRJ_ID as PM_PRJ_ID from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id where pl.IS_TEMPLATE<>'1'");
        Map<String, List<Map<String, Object>>> dataMap = nodeList.stream().collect(Collectors.groupingBy(p -> JdbcMapUtil.getString(p, "PM_PRJ_ID")));

        List<Map<String, List<Map<String, Object>>>> subList = MapUtils.splitByChunkSize(dataMap, 50);
        AtomicInteger index = new AtomicInteger(0);
        subList.forEach(it -> {
            log.info("刷新全景责任用户多线程运行--------------------当前进程第" + index.getAndIncrement() + "个");
            taskExecutor.execute(() -> {
                for (String key : it.keySet()) {
                    List<Map<String, Object>> prjPostList = list.stream().filter(m -> Objects.equals(key, m.get("PM_PRJ_ID"))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(prjPostList)) {
                        List<Map<String, Object>> prjNodeList = dataMap.get(key);
                        prjNodeList.forEach(item -> {
                            Optional<Map<String, Object>> optional = prjPostList.stream().filter(o -> Objects.equals(item.get("POST_INFO_ID"), o.get("POST_INFO_ID"))).findAny();
                            if (optional.isPresent()) {
                                Map<String, Object> postInfo = optional.get();
                                myJdbcTemplate.update("update pm_pro_plan_node set CHIEF_USER_ID=? where id=?", postInfo.get("AD_USER_ID"), item.get("ID"));
                            }
                        });
                    }
                }
            });
        });
        return "";
    }


    /**
     * 初始化计划时间
     *
     * @return
     */
    @GetMapping("/refreshTime")
    public String initPrjProPlanTime() {
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where status ='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' order by pm_code desc");
        List<List<Map<String, Object>>> dataList = ListUtils.split(list, 50);
        AtomicInteger index = new AtomicInteger(0);
        dataList.forEach(item -> {
            log.info("刷新全景计划时间多线程运行--------------------当前进程第" + index.getAndIncrement() + "个");
            taskExecutor.execute(() -> {
                for (Map<String, Object> objectMap : item) {
                    Date paraData = DateUtil.stringToDate("2023-01-01");
                    List<Map<String, Object>> startList = myJdbcTemplate.queryForList("select * from PRJ_START where pm_code=?", objectMap.get("pm_code"));
                    if (!CollectionUtils.isEmpty(startList)) {
                        Map<String, Object> dataMap = startList.get(0);
                        if (Objects.nonNull(dataMap.get("START_TIME"))) {
                            paraData = DateUtil.stringToDate(JdbcMapUtil.getString(dataMap, "START_TIME"));
                        }
                    }
                    refreshProPlanTime(JdbcMapUtil.getString(objectMap, "ID"), paraData);
                }
            });
        });
        return "正在处理";
    }

    /**
     * 新版刷新项目进展时间--通过项目启动时间，立项节点的开始时间为项目启动时间
     *
     * @param projectId
     * @param paramDate
     */
    private void refreshProPlanTime(String projectId, Date paramDate) {
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan where PM_PRJ_ID=?", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> proMap = list.get(0);
            String proPlanId = JdbcMapUtil.getString(proMap, "ID");
            List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where PM_PRO_PLAN_ID=?", proPlanId);
            if (!CollectionUtils.isEmpty(nodeList)) {
                List<Map<String, Object>> firstNodeList = nodeList.stream().filter(p -> 1 == JdbcMapUtil.getInt(p, "LEVEL")).sorted(Comparator.comparing(c -> JdbcMapUtil.getBigDecimal(c, "SEQ_NO"))).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(firstNodeList)) {
                    Map<String, Object> firstNode = firstNodeList.get(0);
                    List<Map<String, Object>> secondNodeList = nodeList.stream().filter(p -> Objects.equals(firstNode.get("ID"), p.get("PM_PRO_PLAN_NODE_PID"))).sorted(Comparator.comparing(c -> JdbcMapUtil.getBigDecimal(c, "SEQ_NO"))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(secondNodeList)) {
                        Map<String, Object> secondNode = secondNodeList.get(0);
                        List<Map<String, Object>> threeNodeList = nodeList.stream().filter(p -> Objects.equals(secondNode.get("ID"), p.get("PM_PRO_PLAN_NODE_PID"))).sorted(Comparator.comparing(c -> JdbcMapUtil.getBigDecimal(c, "SEQ_NO"))).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(threeNodeList)) {
                            Map<String, Object> threeNode = threeNodeList.get(0);

                            SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                            int totalDays = JdbcMapUtil.getInt(threeNode, "PLAN_TOTAL_DAYS");
                            Date completeDate = DateUtil.addDays(paramDate, totalDays);
                            //待优化
                            for (int i = 0; i < 10; i++) {
                                nodeList.forEach(m -> {
                                    if (Objects.equals(m.get("ID"), threeNode.get("ID"))) {
                                        m.put("PLAN_START_DATE", sp.format(paramDate));
                                        m.put("PLAN_COMPL_DATE", sp.format(completeDate));
                                    }
                                    nodeList.stream().filter(p -> Objects.equals(m.get("ID"), p.get("PRE_NODE_ID"))).forEach(item -> {
                                        if (!Strings.isNullOrEmpty(JdbcMapUtil.getString(m, "PLAN_START_DATE"))) {
                                            Date dateOrg = DateUtil.stringToDate(JdbcMapUtil.getString(m, "PLAN_COMPL_DATE"));
                                            int days = JdbcMapUtil.getInt(item, "PLAN_TOTAL_DAYS");
                                            Date endDate = DateUtil.addDays(dateOrg, days);
                                            item.put("PLAN_START_DATE", sp.format(dateOrg));
                                            item.put("PLAN_COMPL_DATE", sp.format(endDate));
                                        }
                                    });
                                });
                            }

                            StringBuilder sb = new StringBuilder();
                            nodeList.forEach(item -> {
                                if (!Strings.isNullOrEmpty(JdbcMapUtil.getString(item, "PLAN_START_DATE"))) {
                                    sb.append("update pm_pro_plan_node set PLAN_START_DATE='").append(item.get("PLAN_START_DATE"))
                                            .append("', PLAN_COMPL_DATE ='").append(item.get("PLAN_COMPL_DATE")).append("' where id='").append(item.get("ID")).append("' ;");
                                }
                            });
                            myJdbcTemplate.update(sb.toString());
                            updateNodeTime(projectId);
                        }

                    }

                }

            }

        }
    }

    private void updateNodeTime(String projectId) {
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan where PM_PRJ_ID=?", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> proMap = list.get(0);
            String proPlanId = JdbcMapUtil.getString(proMap, "ID");
            List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select ID,name,ifnull(PM_PRO_PLAN_NODE_PID,0) as pid,PLAN_START_DATE,PLAN_COMPL_DATE from pm_pro_plan_node where PM_PRO_PLAN_ID=?", proPlanId);

            nodeList.stream().filter(p -> Objects.equals("0", p.get("pid"))).forEach(item -> getChildren(item, nodeList));

            List<Map<String, Object>> newNodeList = myJdbcTemplate.queryForList("select ID,name,ifnull(PM_PRO_PLAN_NODE_PID,0) as pid,PLAN_START_DATE,PLAN_COMPL_DATE from pm_pro_plan_node where PM_PRO_PLAN_ID=?", proPlanId);
            newNodeList.stream().filter(p -> Objects.equals("0", p.get("pid"))).forEach(item -> {
                List<Map<String, Object>> sonList = newNodeList.stream().filter(p -> Objects.equals(item.get("ID"), p.get("pid"))).collect(Collectors.toList());
                List<Map<String, Object>> noNullData = sonList.stream().filter(p -> JdbcMapUtil.getDate(p, "PLAN_START_DATE") != null).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(noNullData)) {
                    Object o = noNullData.stream().sorted(Comparator.comparing(m -> JdbcMapUtil.getDate(m, "PLAN_START_DATE"))).collect(Collectors.toList()).get(0).get("PLAN_START_DATE");
                    Object o1 = noNullData.stream().sorted(Comparator.comparing(f -> JdbcMapUtil.getDate((Map<String, Object>) f, "PLAN_COMPL_DATE")).reversed()).collect(Collectors.toList()).get(0).get("PLAN_COMPL_DATE");
                    myJdbcTemplate.update("update pm_pro_plan_node set PLAN_START_DATE=?,PLAN_COMPL_DATE=? where id=?", o, o1, item.get("ID"));
                }
            });

        }
    }

    private List<Map<String, Object>> getChildren(Map<String, Object> parent, List<Map<String, Object>> allData) {
        return allData.stream().filter(p -> Objects.equals(parent.get("id"), p.get("pid"))).peek(item -> {
            List<Map<String, Object>> sonList = getChildren(item, allData);
            List<Map<String, Object>> noNullData = sonList.stream().filter(p -> JdbcMapUtil.getDate(p, "PLAN_START_DATE") != null).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(noNullData)) {
                Object o = noNullData.stream().sorted(Comparator.comparing(m -> JdbcMapUtil.getDate(m, "PLAN_START_DATE"))).collect(Collectors.toList()).get(0).get("PLAN_START_DATE");
                Object o1 = noNullData.stream().sorted(Comparator.comparing(f -> JdbcMapUtil.getDate((Map<String, Object>) f, "PLAN_COMPL_DATE")).reversed()).collect(Collectors.toList()).get(0).get("PLAN_COMPL_DATE");
                myJdbcTemplate.update("update pm_pro_plan_node set PLAN_START_DATE=?,PLAN_COMPL_DATE=? where id=?", o, o1, item.get("ID"));
            }
        }).collect(Collectors.toList());
    }
}
