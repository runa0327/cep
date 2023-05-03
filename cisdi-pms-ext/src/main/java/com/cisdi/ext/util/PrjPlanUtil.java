package com.cisdi.ext.util;

import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class PrjPlanUtil {
    /**
     * 新版刷新项目进展时间--通过项目启动时间，立项节点的开始时间为项目启动时间
     *
     * @param projectId
     * @param paramDate
     */
    public static void refreshProPlanTime(String projectId, Date paramDate) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
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
                            Date completeDate = DateTimeUtil.addDays(paramDate, totalDays);

                            nodeList.forEach(m -> {
                                if (Objects.equals(m.get("ID"), threeNode.get("ID"))) {
                                    m.put("PLAN_START_DATE", sp.format(paramDate));
                                    m.put("PLAN_COMPL_DATE", sp.format(completeDate));
                                }
                                if (!Strings.isNullOrEmpty(JdbcMapUtil.getString(m, "PLAN_START_DATE"))) {
                                    List<Map<String, Object>> preList = getPreList(m, nodeList);
                                    if (!CollectionUtils.isEmpty(preList)) {
                                        preList.forEach(item -> {
                                            Date dateOrg = DateTimeUtil.stringToDate(JdbcMapUtil.getString(m, "PLAN_COMPL_DATE"));
                                            int days = JdbcMapUtil.getInt(item, "PLAN_TOTAL_DAYS");
                                            Date endDate = DateTimeUtil.addDays(dateOrg, days);
                                            item.put("PLAN_START_DATE", sp.format(dateOrg));
                                            item.put("PLAN_COMPL_DATE", sp.format(endDate));
                                        });
                                    }
                                }
                            });
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

    private static List<Map<String, Object>> getPreList(Map<String, Object> own, List<Map<String, Object>> allData) {
        SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
        return allData.stream().filter(p -> Objects.equals(own.get("ID"), p.get("PRE_NODE_ID"))).peek(m -> {
            if (!Strings.isNullOrEmpty(JdbcMapUtil.getString(m, "PLAN_COMPL_DATE"))) {
                Date endaaDate = DateTimeUtil.stringToDate(JdbcMapUtil.getString(m, "PLAN_COMPL_DATE"));
                if (endaaDate != null) {
                    int days = JdbcMapUtil.getInt(m, "PLAN_TOTAL_DAYS");
                    Date endmDate = DateTimeUtil.addDays(endaaDate, days);
                    m.put("PLAN_START_DATE", sp.format(endaaDate));
                    m.put("PLAN_COMPL_DATE", sp.format(endmDate));
                }
            }
            if (!Strings.isNullOrEmpty(JdbcMapUtil.getString(m, "PLAN_START_DATE"))) {
                List<Map<String, Object>> preList = getPreList(m, allData);
                preList.forEach(item -> {
                    Date dateOrg = DateTimeUtil.stringToDate(JdbcMapUtil.getString(m, "PLAN_COMPL_DATE"));
                    Date oweStart = JdbcMapUtil.getDate(item, "PLAN_START_DATE");
                    if (oweStart != null) {
                        if (oweStart.before(dateOrg)) {
                            int days = JdbcMapUtil.getInt(item, "PLAN_TOTAL_DAYS");
                            Date endDate = DateTimeUtil.addDays(dateOrg, days);
                            item.put("PLAN_START_DATE", sp.format(dateOrg));
                            item.put("PLAN_COMPL_DATE", sp.format(endDate));
                        }
                    } else {
                        int days = JdbcMapUtil.getInt(item, "PLAN_TOTAL_DAYS");
                        Date endDate = DateTimeUtil.addDays(dateOrg, days);
                        item.put("PLAN_START_DATE", sp.format(dateOrg));
                        item.put("PLAN_COMPL_DATE", sp.format(endDate));
                    }

                });
            }
        }).collect(Collectors.toList());
    }

    private static void updateNodeTime(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
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

    private static List<Map<String, Object>> getChildren(Map<String, Object> parent, List<Map<String, Object>> allData) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
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


    /**
     * 当前节点时间修改后，修改与之相关的节点的时间
     *
     * @param nodeId
     */
    public static void updatePreNodeTime(String nodeId, String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> currentNodeList = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=?", nodeId);
        if (!CollectionUtils.isEmpty(currentNodeList)) {
            Map<String, Object> currentNode = currentNodeList.get(0);
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where pm_pro_plan_id = ? and LEVEL =3 ", currentNode.get("pm_pro_plan_id"));
            if (!CollectionUtils.isEmpty(list)) {
                SimpleDateFormat sp = new SimpleDateFormat("yyyy-MM-dd");
                List<Map<String, Object>> preList = getPreList(currentNode, list);
                if (!CollectionUtils.isEmpty(preList)) {
                    preList.forEach(item -> {
                        Date dateOrg = DateTimeUtil.stringToDate(JdbcMapUtil.getString(currentNode, "PLAN_COMPL_DATE"));
                        Date oweStart = JdbcMapUtil.getDate(item, "PLAN_START_DATE");
                        if (oweStart != null) {
                            if (oweStart.before(dateOrg)) {
                                int days = JdbcMapUtil.getInt(item, "PLAN_TOTAL_DAYS");
                                Date endDate = DateTimeUtil.addDays(dateOrg, days);
                                item.put("PLAN_START_DATE", sp.format(dateOrg));
                                item.put("PLAN_COMPL_DATE", sp.format(endDate));
                            }
                        } else {
                            int days = JdbcMapUtil.getInt(item, "PLAN_TOTAL_DAYS");
                            Date endDate = DateTimeUtil.addDays(dateOrg, days);
                            item.put("PLAN_START_DATE", sp.format(dateOrg));
                            item.put("PLAN_COMPL_DATE", sp.format(endDate));
                        }
                    });

                    StringBuilder sb = new StringBuilder();
                    list.forEach(item -> {
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

    /**
     * 新增项目进度网络图
     *
     * @param projectId
     * @param type
     * @param sourceId
     * @param invest
     * @param invest
     */
    public static void createPlan(String projectId, String type, String sourceId, BigDecimal invest, String tenderWay) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //根据项目的类型，项目投资来源，项目的总额，招标方式查询模板
        String proPlanId = "0099902212142021791";  //默认的模板
        List<Map<String, Object>> ruleList = myJdbcTemplate.queryForList("select pptr.*,gsv.`code` as rule from PRO_PLAN_TEMPLATE_RULE pptr " +
                "left join gr_set_value gsv on pptr.PRO_PLAN_RULE_CONDITION_ID = gsv.id " +
                "where TEMPLATE_FOR_PROJECT_TYPE_ID=? and INVESTMENT_SOURCE_ID=? and TENDER_MODE_ID=?", type, sourceId, tenderWay);
        if (!CollectionUtils.isEmpty(ruleList)) {
            for (Map<String, Object> objectMap : ruleList) {
                String condition = JdbcMapUtil.getString(objectMap, "rule");
                String ex = condition.replaceAll("param", String.valueOf(invest));
                if (StringUtil.doExpression(ex)) {
                    proPlanId = JdbcMapUtil.getString(objectMap, "PM_PRO_PLAN_ID");
                    break;
                }
            }
        }
        //根据规则查询模板
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRO_PLAN where id = ?", proPlanId);

        if (!CollectionUtils.isEmpty(list)) {
            //先清出原来的
            clearPlan(projectId);

            Map<String, Object> proMap = list.get(0);
            // 先创建项目的进度计划
            String newPlanId = Crud.from("PM_PRO_PLAN").insertData();

            Crud.from("PM_PRO_PLAN").where().eq("ID", newPlanId).update().set("IS_TEMPLATE", 0).set("PM_PRJ_ID", projectId).set("PLAN_TOTAL_DAYS", proMap.get("PLAN_TOTAL_DAYS"))
                    .set("PROGRESS_STATUS_ID", proMap.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", proMap.get("PROGRESS_RISK_TYPE_ID")).set("START_DAY", proMap.get("START_DAY")).exec();


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
                    String id = Crud.from("PM_PRO_PLAN_NODE").insertData();
                    Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                            .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                            .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                            .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_START_WF_NODE_ID", m.get("LINKED_START_WF_NODE_ID")).set("LINKED_END_WF_NODE_ID", m.get("LINKED_END_WF_NODE_ID")).set("SHOW_IN_EARLY_PROC", m.get("SHOW_IN_EARLY_PROC"))
                            .set("SHOW_IN_PRJ_OVERVIEW", m.get("SHOW_IN_PRJ_OVERVIEW")).set("POST_INFO_ID", m.get("POST_INFO_ID")).set("CAN_START", m.get("CAN_START"))
                            .set("PRE_NODE_ID", m.get("PRE_NODE_ID")).set("AD_ENT_ID_IMP", m.get("AD_ENT_ID_IMP")).set("AD_ATT_ID_IMP", m.get("AD_ATT_ID_IMP")).set("IZ_MILESTONE", m.get("IZ_MILESTONE")).set("SCHEDULE_NAME", m.get("SCHEDULE_NAME")).exec();
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
                setFirstNodeUser(projectId);
            }
        }
    }

    /**
     * 把项目启动里的前期报建岗人员，初始化搞第一个节点上
     *
     * @param projectId
     */
    private static void setFirstNodeUser(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
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
                            List<Map<String, Object>> startList = myJdbcTemplate.queryForList("select ps.* from pm_prj pm left join prj_start ps on pm.pm_code = ps.pm_code where pm.id=?", projectId);
                            if (!CollectionUtils.isEmpty(startList)) {
                                Map<String, Object> startObj = startList.get(0);
                                myJdbcTemplate.update("update pm_pro_plan_node set CHIEF_USER_ID=? where id=?", startObj.get("AD_USER_ID"), threeNode.get("ID"));
                            }
                        }
                    }
                }
            }
        }
    }

    private static List<Map<String, Object>> getChildrenNode(Map<String, Object> root, List<Map<String, Object>> allData, String pId, String newPlanId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")), String.valueOf(root.get("ID")))).peek(m -> {
            String id = Crud.from("PM_PRO_PLAN_NODE").insertData();
            Crud.from("PM_PRO_PLAN_NODE").where().eq("ID", id).update().set("NAME", m.get("NAME")).set("PM_PRO_PLAN_ID", newPlanId)
                    .set("PM_PRO_PLAN_NODE_PID", pId)
                    .set("PLAN_TOTAL_DAYS", m.get("PLAN_TOTAL_DAYS")).set("PROGRESS_STATUS_ID", m.get("PROGRESS_STATUS_ID")).set("PROGRESS_RISK_TYPE_ID", m.get("PROGRESS_RISK_TYPE_ID"))
                    .set("CHIEF_DEPT_ID", m.get("CHIEF_DEPT_ID")).set("START_DAY", m.get("START_DAY")).set("SEQ_NO", m.get("SEQ_NO")).set("LEVEL", m.get("LEVEL"))
                    .set("LINKED_WF_PROCESS_ID", m.get("LINKED_WF_PROCESS_ID")).set("LINKED_START_WF_NODE_ID", m.get("LINKED_START_WF_NODE_ID")).set("LINKED_END_WF_NODE_ID", m.get("LINKED_END_WF_NODE_ID")).set("SHOW_IN_EARLY_PROC", m.get("SHOW_IN_EARLY_PROC"))
                    .set("SHOW_IN_PRJ_OVERVIEW", m.get("SHOW_IN_PRJ_OVERVIEW")).set("POST_INFO_ID", m.get("POST_INFO_ID")).set("CAN_START", m.get("CAN_START"))
                    .set("PRE_NODE_ID", m.get("PRE_NODE_ID")).set("AD_ENT_ID_IMP", m.get("AD_ENT_ID_IMP")).set("AD_ATT_ID_IMP", m.get("AD_ATT_ID_IMP")).set("IZ_MILESTONE", m.get("IZ_MILESTONE")).set("SCHEDULE_NAME", m.get("SCHEDULE_NAME")).exec();
            getChildrenNode(m, allData, id, newPlanId);
        }).collect(Collectors.toList());
    }

    private static void clearPlan(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("SET FOREIGN_KEY_CHECKS = 0;delete from PM_PRO_PLAN_NODE where PM_PRO_PLAN_ID in (select id from PM_PRO_PLAN where PM_PRJ_ID=?);SET FOREIGN_KEY_CHECKS = 1;", projectId);
        myJdbcTemplate.update("delete from PM_PRO_PLAN where PM_PRJ_ID=?", projectId);
    }

    /**
     * 跟新进度节点状态
     *
     * @param projectId
     */
    public static void updateNodeStatus(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 查询所有的进度计划节点
        List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER," +
                "pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID," +
                "pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE," +
                "pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,pppn.PLAN_TOTAL_DAYS," +
                "pppn.PLAN_CARRY_DAYS,pppn.ACTUAL_CARRY_DAYS,pppn.ACTUAL_TOTAL_DAYS,pppn.PLAN_CURRENT_PRO_PERCENT," +
                "pppn.ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID," +
                "pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW," +
                "pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID," +
                "pppn.START_DAY,pppn.SEQ_NO,pppn.CPMS_UUID,pppn.CPMS_ID,pppn.LEVEL,AD_ENT_ID_IMP,AD_ATT_ID_IMP, " +
                "gsv.`CODE` as PROGRESS_STATUS_CODE from pm_pro_plan_node pppn \n" +
                "left join pm_pro_plan ppp on pppn.PM_PRO_PLAN_ID = ppp.ID\n" +
                "left join gr_set_value gsv on gsv.id = pppn.PROGRESS_STATUS_ID\n" +
                "left join gr_set gr on gr.id = gsv.GR_SET_ID\n" +
                "where gr.`CODE`='PROGRESS_STATUS' and ppp.PM_PRJ_ID=?", projectId);

        if (nodeList.size() > 0) {
            nodeList.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
                List<Map<String, Object>> children = getChildrenNodeForCollectProgressStatus(m, nodeList, myJdbcTemplate);
                if (children.size() > 0) {
                    int count = children.size();
                    String proStatus = "0";
                    List<Map<String, Object>> finishList = children.stream().filter(p -> Objects.equals("2", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                    if (count == finishList.size()) {
                        // 父级改为已完成
                        proStatus = "2";
                    } else {
                        List<Map<String, Object>> UnBeginList = children.stream().filter(p -> Objects.equals("0", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                        if (count != UnBeginList.size()) {
                            // 父级改为进行中
                            proStatus = "1";
                        }
                    }
                    List<Map<String, Object>> grSetValueList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv " +
                            "left join gr_set gs on gs.id = gsv.gr_set_id where gs.`code`='PROGRESS_STATUS' and gsv.`code`=?", proStatus);
                    String proStatusID = "";
                    if (grSetValueList.size() > 0) {
                        proStatusID = String.valueOf(grSetValueList.get(0).get("ID"));
                    }
                    myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", proStatusID, m.get("ID"));
                }
            }).collect(Collectors.toList());

        }
    }

    /**
     * 递归修改父节点的状态
     *
     * @param root
     * @param allData
     * @param myJdbcTemplate
     * @return
     */
    private static List<Map<String, Object>> getChildrenNodeForCollectProgressStatus(Map<String, Object> root, List<Map<String, Object>> allData, MyJdbcTemplate myJdbcTemplate) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(root.get("ID")), String.valueOf(p.get("PM_PRO_PLAN_NODE_PID")))).peek(m -> {
            List<Map<String, Object>> children = getChildrenNodeForCollectProgressStatus(m, allData, myJdbcTemplate);
            if (children.size() > 0) {
                int count = children.size();
                String proStatus = "0";
                List<Map<String, Object>> finishList = children.stream().filter(p -> Objects.equals("2", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                if (count == finishList.size()) {
                    // 父级改为已完成
                    proStatus = "2";
                } else {
                    List<Map<String, Object>> UnBeginList = children.stream().filter(p -> Objects.equals("0", p.get("PROGRESS_STATUS_CODE"))).collect(Collectors.toList());
                    if (count != UnBeginList.size()) {
                        // 父级改为进行中
                        proStatus = "1";
                    }
                }
                List<Map<String, Object>> grSetValueList = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv " +
                        "left join gr_set gs on gs.id = gsv.gr_set_id where gs.`code`='PROGRESS_STATUS' and gsv.`code`=?", proStatus);
                String proStatusID = "";
                if (grSetValueList.size() > 0) {
                    proStatusID = String.valueOf(grSetValueList.get(0).get("ID"));
                }
                myJdbcTemplate.update("update pm_pro_plan_node set PROGRESS_STATUS_ID=? where id=?", proStatusID, m.get("ID"));
            }
        }).collect(Collectors.toList());
    }


    /**
     * 更加花名册刷新全景的负责人
     *
     * @param projectId
     */
    public static void refreshProPlanUser(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_roster where PM_PRJ_ID=?", projectId);
        //查询未启动的节点
        List<Map<String, Object>> proList = myJdbcTemplate.queryForList("select pn.* from pm_pro_plan_node pn left join pm_pro_plan pl on pn.PM_PRO_PLAN_ID = pl.id  where pn.PROGRESS_STATUS_ID ='0099799190825106800' and  PM_PRJ_ID=?",projectId);
        list.forEach(item -> {
            String postId = JdbcMapUtil.getString(item, "POST_INFO_ID");
            if (!Strings.isNullOrEmpty(postId)) {
                String userId = JdbcMapUtil.getString(item, "AD_USER_ID");
                if (!Strings.isNullOrEmpty(userId)) {
                    List<Map<String, Object>> dataList = proList.stream().filter(p -> Objects.equals(postId, JdbcMapUtil.getString(p, "POST_INFO_ID"))).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dataList)) {
                        dataList.forEach(m -> {
                            myJdbcTemplate.update("update pm_pro_plan_node set CHIEF_USER_ID=? where id=?", userId, m.get("ID"));
                        });
                    }
                }
            }
        });
    }


}
