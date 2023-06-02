package com.cisdi.ext.proPlan;

import com.cisdi.ext.pm.office.PmNodeAdjustReqExt;
import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.PrjPlanUtil;
import com.cisdi.ext.weektask.WeekTaskExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.IdCodeName;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class ProPlanExt {
    public void calcPlanTotalDays() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            calcPlanTotalDays(entityRecord);
        }
    }

    /**
     * 进度计划模板计算天数
     *
     * @param entityRecord
     */
    private void calcPlanTotalDays(EntityRecord entityRecord) {
        StringBuilder sbErr = new StringBuilder();
        if (entityRecord.extraEditableAttCodeList == null) {
            entityRecord.extraEditableAttCodeList = new ArrayList<>();
        }
        String csCommId = entityRecord.csCommId;
        // 最小开始天数
        int minStartDay = 0;
        // 最大计划天
        int maxPlanDay = 0;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        // 查询项目进度计划模板
//        Map<String, Object> proMap = myJdbcTemplate.queryForMap("select * from PM_PRO_PLAN where id=?", csCommId);

        // 查询进度节点明细
        List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT," +
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
            // 处理进度计划
            List<Map<String, Object>> firstNodeList = nodeList.stream().filter(p -> Objects.equals("0", p.get("PM_PRO_PLAN_NODE_PID"))).collect(Collectors.toList());
            minStartDay = firstNodeList.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("START_DAY")))).min().orElse(0);
            maxPlanDay = firstNodeList.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("PLAN_TOTAL_DAYS")))).max().orElse(0);
        }
        // 操作数据库，更新数据,更新项目进度
        myJdbcTemplate.update("update PM_PRO_PLAN set START_DAY=?,PLAN_TOTAL_DAYS=? where id=?", minStartDay, maxPlanDay, csCommId);
        // 操作数据库，更新数据,更新项目进度节点
        nodeList.forEach(item -> {
            myJdbcTemplate.update("update PM_PRO_PLAN_NODE set START_DAY=?,PLAN_TOTAL_DAYS=? where id=?", item.get("START_DAY"), item.get("PLAN_TOTAL_DAYS"), item.get("ID"));
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
            // 获取开始天
            int minDay = child.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("START_DAY")))).min().orElse(0);
            // 获取最大的用时
            int maxUseDay = child.stream().mapToInt(p -> Integer.parseInt(String.valueOf(p.get("START_DAY"))) + Integer.parseInt(String.valueOf(p.get("PLAN_TOTAL_DAYS"))) - 1).max().orElse(0);
            int useDay = maxUseDay - minDay + 1;
            root.put("START_DAY", minDay);
            root.put("PLAN_TOTAL_DAYS", useDay);
        }
        return root;
    }

    /**
     * 获取项目首页-项目进度数据
     */
    public void getPrjOverviewNodeList() {
        // 输入：PrjOverviewNodeListParam
        // 输出：List<PrjProPlanNodeInfo>
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        ProPlanExt.GetPrjOverviewNodeListParam param = JsonUtil.fromJson(json, ProPlanExt.GetPrjOverviewNodeListParam.class);
        String pmPrjId = param.pmPrjId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        List<Map<String, Object>> allList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE,pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,ifnull(pppn.PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(pppn.PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,\n" +
                "ifnull(pppn.ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(pppn.ACTUAL_TOTAL_DAYS,0) as ACTUAL_TOTAL_DAYS,ifnull(pppn.PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,\n" +
                "ifnull(pppn.ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW,pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID,pppn.START_DAY,pppn.SEQ_NO \n" +
                "from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where SHOW_IN_PRJ_OVERVIEW='1' and pppn.OPREATION_TYPE is null and ppp.PM_PRJ_ID=?", pmPrjId);

        // 结果转换
        List<PrjProPlanNodeInfo> infoList = allList.stream().map(p -> this.convertPlanInfoNode(pmPrjId, p, myJdbcTemplate)).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(infoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.nodeInfoList = infoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 树转换
     *
     * @param source
     * @param outList
     */
    private void convertChildrenToTileList(List<PrjProPlanNodeInfo> source, List<PrjProPlanNodeInfo> outList) {
        if (outList == null) {
            outList = new ArrayList<>();
        }
        for (PrjProPlanNodeInfo nodeInfo : source) {
            if (nodeInfo.children != null) {
                List<PrjProPlanNodeInfo> children = nodeInfo.children;
                convertChildrenToTileList(children, outList);
            }
            outList.add(nodeInfo);
        }

    }

    /**
     * 获取项目进展树结构数据
     */
    public void getPrjProPlanNetwork() {
        // 输入：GettPrjProPlanNetworkParam
        // 输出：PrjProPlanInfo，内含nodeInfoList属性，再含children属性（递归）
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        ProPlanExt.GetPrjProPlanNetworkParam param = JsonUtil.fromJson(json, ProPlanExt.GetPrjProPlanNetworkParam.class);
        String pmPrjId = param.pmPrjId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        PrjProPlanInfo planInfo = new PrjProPlanInfo();
        Map<String, Object> proMap = null;
        try {
            proMap = myJdbcTemplate.queryForMap("select pr.ID,pr.CODE,pr.NAME,pr.REMARK,pr.ACTUAL_START_DATE,pr.PROGRESS_RISK_REMARK,pr.IS_TEMPLATE,pr.PM_PRJ_ID,pr.PLAN_START_DATE,\n" +
                    "ifnull(PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,\n" +
                    "ifnull(ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(ACTUAL_TOTAL_DAYS,0),ifnull(PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,\n" +
                    "ifnull(ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,TEMPLATE_FOR_PROJECT_TYPE_ID,PROGRESS_STATUS_ID,\n" +
                    "PROGRESS_RISK_TYPE_ID,ifnull(START_DAY,0) as START_DAY,pr.CPMS_UUID,pr.CPMS_ID,pj.name as PROJECT_NAME,pr.ver as VER from PM_PRO_PLAN pr left join pm_prj pj on pr.PM_PRJ_ID = pj.id where PM_PRJ_ID=?", pmPrjId);
            if (proMap != null) {
                planInfo = this.covertPlanInfo(proMap, myJdbcTemplate);

                List<Map<String, Object>> allList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE,pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,ifnull(pppn.PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(pppn.PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,\n" +
                        " ifnull(pppn.ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(pppn.ACTUAL_TOTAL_DAYS,0) as ACTUAL_TOTAL_DAYS,ifnull(pppn.PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,LINKED_WF_PROCESS_ID,\n" +
                        " ifnull(pppn.ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW,pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID,pppn.START_DAY,pppn.SEQ_NO,pppn.`LEVEL`,pppn.POST_INFO_ID,ifnull(pppn.CAN_START,0) CAN_START, \n" +
                        " PRE_NODE_ID,AD_ENT_ID_IMP,AD_ATT_ID_IMP,IZ_MILESTONE,SCHEDULE_NAME  " +
                        "from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where pppn.OPREATION_TYPE is null and ppp.PM_PRJ_ID=?", pmPrjId);

                List<String> notStart = new ArrayList<>();
                // 结果转换
                List<PrjProPlanNodeInfo> infoList = allList.stream().map(p -> this.convertPlanInfoNode(pmPrjId, p, myJdbcTemplate)).collect(Collectors.toList());
                // 处理是否能启动，逻辑：关联同1个流程的多个节点，只有第1个可以启动
                Map<String, List<PrjProPlanNodeInfo>> mapData = infoList.stream().filter(m -> "1".equals(m.canStart) && m.linkedWfProcessId != null).collect(Collectors.groupingBy(p -> p.linkedWfProcessId));
                for (String key : mapData.keySet()) {
                    List<PrjProPlanNodeInfo> data = mapData.get(key);
                    if (!CollectionUtils.isEmpty(data)) {
                        List<PrjProPlanNodeInfo> infos = data.stream().sorted(Comparator.comparing(l -> l.seqNo)).collect(Collectors.toList());
                        infos.remove(0);
                        notStart.addAll(infos.stream().map(t -> t.id).collect(Collectors.toList()));
                    }
                }
                infoList.forEach(item -> {
                    if (notStart.contains(item.id)) {
                        item.canStart = "0";
                    }
                });

                // 构建树结构
                List<PrjProPlanNodeInfo> tree = infoList.stream().filter(p -> "0".equals(p.pid)).sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo))).peek(m -> {
                    m.children = getChildNode(m, infoList).stream().sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
                }).collect(Collectors.toList());

                planInfo.nodeInfoList = tree;
                // 最终，返回：
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(planInfo), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            } else {
                ExtJarHelper.returnValue.set(null);
            }
        } catch (Exception e) {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 递归子节点
     *
     * @param root        当前单个实体
     * @param allListTree 表中的所有实体集合
     * @return
     */
    private List<PrjProPlanNodeInfo> getChildNode(PrjProPlanNodeInfo root, List<PrjProPlanNodeInfo> allListTree) {
        return allListTree.stream().filter((treeEntity) -> treeEntity.pid.equals(root.id)).peek((treeEntity) -> {
            treeEntity.children = getChildNode(treeEntity, allListTree).stream().sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }

    /**
     * 项目预览中的节点列表的参数。
     */
    public static class GetPrjOverviewNodeListParam {
        public String pmPrjId;
    }

    public static class GetPrjProPlanNetworkParam {
        public String pmPrjId;
    }

    /**
     * Map转换成PrjProPlanInfo
     *
     * @param dataMap
     * @param myJdbcTemplate
     * @return
     */
    private PrjProPlanInfo covertPlanInfo(Map<String, Object> dataMap, MyJdbcTemplate myJdbcTemplate) {
        PrjProPlanInfo planInfo = new PrjProPlanInfo();
        planInfo.id = String.valueOf(dataMap.get("ID"));
        planInfo.code = JdbcMapUtil.getString(dataMap, "CODE");
        planInfo.name = JdbcMapUtil.getString(dataMap, "NAME");
        planInfo.remark = JdbcMapUtil.getString(dataMap, "REMARK");
        planInfo.startDay = JdbcMapUtil.getString(dataMap, "START_DAY");
        planInfo.planStartDate = JdbcMapUtil.getString(dataMap, "PLAN_START_DATE");
        planInfo.planComplDate = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
        planInfo.planTotalDays = JdbcMapUtil.getInt(dataMap, ("PLAN_TOTAL_DAYS"));
        planInfo.planCarryDays = JdbcMapUtil.getInt(dataMap, "PLAN_CARRY_DAYS");
        planInfo.planCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(dataMap, "PLAN_CURRENT_PRO_PERCENT"));
        planInfo.actualStartDate = JdbcMapUtil.getString(dataMap, "ACTUAL_START_DATE");
        planInfo.actualComplDate = JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE");
        planInfo.actualTotalDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_TOTAL_DAYS");
        planInfo.actualCarryDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_CARRY_DAYS");
        planInfo.actualCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(dataMap, "ACTUAL_CURRENT_PRO_PERCENT"));
        String sql = "select * from gr_set_value where id=?";
        if (!Objects.isNull(dataMap.get("PROGRESS_STATUS_ID"))) {
            Map<String, Object> statusObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_STATUS_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(statusObj.get("ID"));
            idCodeName.code = String.valueOf(statusObj.get("CODE"));
            idCodeName.name = String.valueOf(statusObj.get("NAME"));
            planInfo.progressStatus = idCodeName;
        }
        if (!Objects.isNull(dataMap.get("PROGRESS_RISK_TYPE_ID"))) {
            Map<String, Object> riskObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_TYPE_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(riskObj.get("ID"));
            idCodeName.code = String.valueOf(riskObj.get("CODE"));
            idCodeName.name = String.valueOf(riskObj.get("NAME"));
            planInfo.progressRiskType = idCodeName;
        }

        planInfo.progressRiskRemark = JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_REMARK");
        planInfo.projectId = JdbcMapUtil.getString(dataMap, "PM_PRJ_ID");
        planInfo.ver = JdbcMapUtil.getString(dataMap, "VER");
        planInfo.projectName = JdbcMapUtil.getString(dataMap, "PROJECT_NAME");
        return planInfo;
    }

    /**
     * Map转换成PrjProPlanNodeInfo
     *
     * @param dataMap
     * @param myJdbcTemplate
     * @return
     */
    private PrjProPlanNodeInfo convertPlanInfoNode(String projectId, Map<String, Object> dataMap, MyJdbcTemplate myJdbcTemplate) {
        PrjProPlanNodeInfo nodeInfo = new PrjProPlanNodeInfo();
        nodeInfo.id = JdbcMapUtil.getString(dataMap, "ID");
        nodeInfo.pid = JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_NODE_PID");
        nodeInfo.code = JdbcMapUtil.getString(dataMap, "CODE");
        nodeInfo.name = JdbcMapUtil.getString(dataMap, "NAME");
        nodeInfo.remark = JdbcMapUtil.getString(dataMap, "REMARK");
        nodeInfo.startDay = JdbcMapUtil.getString(dataMap, "START_DAY");
        nodeInfo.planStartDate = JdbcMapUtil.getString(dataMap, "PLAN_START_DATE");
        nodeInfo.planComplDate = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
        nodeInfo.planTotalDays = JdbcMapUtil.getInt(dataMap, "PLAN_TOTAL_DAYS");
        nodeInfo.planCarryDays = JdbcMapUtil.getInt(dataMap, "PLAN_CARRY_DAYS");
        nodeInfo.planCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(dataMap, "PLAN_CURRENT_PRO_PERCENT"));
        nodeInfo.actualStartDate = JdbcMapUtil.getString(dataMap, "ACTUAL_START_DATE");
        nodeInfo.actualComplDate = JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE");
        nodeInfo.actualTotalDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_TOTAL_DAYS");
        nodeInfo.actualCarryDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_CARRY_DAYS");
        nodeInfo.actualCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(dataMap, "ACTUAL_CURRENT_PRO_PERCENT"));
        String sql = "select * from gr_set_value where id=?";
        if (!Objects.isNull(dataMap.get("PROGRESS_STATUS_ID"))) {
            Map<String, Object> statusObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_STATUS_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(statusObj.get("ID"));
            idCodeName.code = String.valueOf(statusObj.get("CODE"));
            idCodeName.name = String.valueOf(statusObj.get("NAME"));
            nodeInfo.progressStatus = idCodeName;
        }
        if (!Objects.isNull(dataMap.get("PROGRESS_RISK_TYPE_ID"))) {
            Map<String, Object> riskObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_TYPE_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(riskObj.get("ID"));
            idCodeName.code = String.valueOf(riskObj.get("CODE"));
            idCodeName.name = String.valueOf(riskObj.get("NAME"));
            nodeInfo.progressRiskType = idCodeName;
        }
        nodeInfo.progressRiskRemark = JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_REMARK");
        if (!Objects.isNull(dataMap.get("CHIEF_DEPT_ID"))) {
            List<Map<String, Object>> deptList = myJdbcTemplate.queryForList("select * from hr_dept where id =?", JdbcMapUtil.getString(dataMap, "CHIEF_DEPT_ID"));
            if (!CollectionUtils.isEmpty(deptList)) {
                Map<String, Object> deptObj = deptList.get(0);
                IdCodeName idCodeName = new IdCodeName();
                idCodeName.id = String.valueOf(deptObj.get("ID"));
                idCodeName.code = String.valueOf(deptObj.get("CODE"));
                idCodeName.name = String.valueOf(deptObj.get("NAME"));
                nodeInfo.chiefDept = idCodeName;
            }

        }

        if (!Objects.isNull(dataMap.get("CHIEF_USER_ID"))) {
            List<Map<String, Object>> userList = myJdbcTemplate.queryForList("select * from ad_user where id =?", JdbcMapUtil.getString(dataMap, "CHIEF_USER_ID"));
            if (!CollectionUtils.isEmpty(userList)) {
                Map<String, Object> userObj = userList.get(0);
                IdCodeName idCodeName = new IdCodeName();
                idCodeName.id = String.valueOf(userObj.get("ID"));
                idCodeName.code = String.valueOf(userObj.get("CODE"));
                idCodeName.name = String.valueOf(userObj.get("NAME"));
                nodeInfo.chiefUser = idCodeName;
            }
        }

        if (!Objects.isNull(dataMap.get("POST_INFO_ID"))) {
            List<Map<String, Object>> postObj = myJdbcTemplate.queryForList("select * from POST_INFO where id=?", dataMap.get("POST_INFO_ID"));
            if (postObj != null && postObj.size() > 0) {
                Map<String, Object> objectMap = postObj.get(0);
                IdCodeName idCodeName = new IdCodeName();
                idCodeName.id = String.valueOf(objectMap.get("ID"));
                idCodeName.code = String.valueOf(objectMap.get("CODE"));
                idCodeName.name = String.valueOf(objectMap.get("NAME"));
                nodeInfo.post = idCodeName;
            }
//            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from ad_user where id in ( select ad_user_id from PM_POST_USER where POST_INFO_ID=? and PM_PRJ_ID =?)", dataMap.get("POST_INFO_ID"), projectId);
//            if (list != null && list.size() > 0) {
//                Map<String, Object> userMap = list.get(0);
//                IdCodeName codeName = new IdCodeName();
//                codeName.id = String.valueOf(userMap.get("ID"));
//                codeName.code = String.valueOf(userMap.get("CODE"));
//                codeName.name = String.valueOf(userMap.get("NAME"));
//                nodeInfo.chiefUser = codeName;
//            }
        }
        nodeInfo.showInPrjOverview = JdbcMapUtil.getString(dataMap, "SHOW_IN_PRJ_OVERVIEW");
        nodeInfo.seqNo = JdbcMapUtil.getString(dataMap, "SEQ_NO");
        nodeInfo.level = JdbcMapUtil.getString(dataMap, "LEVEL");
        nodeInfo.chiefDeptId = JdbcMapUtil.getString(dataMap, "CHIEF_DEPT_ID");
        nodeInfo.ver = JdbcMapUtil.getString(dataMap, "VER");
        nodeInfo.proCount = getProblemCount(nodeInfo.id);
        nodeInfo.canStart = JdbcMapUtil.getString(dataMap, "CAN_START");
        nodeInfo.linkedWfProcessId = JdbcMapUtil.getString(dataMap, "LINKED_WF_PROCESS_ID");
        return nodeInfo;
    }


    private Integer getProblemCount(String nodeId) {
        int res = 0;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from NODE_PROBLEM where PM_PRO_PLAN_NODE_ID = ?", nodeId);
        if (list != null) {
            res = list.size();
        }
        return res;
    }


    /**
     * 汇总进度计划状态
     */
    public void collectProgressStatus() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        if (ExtJarHelper.entityRecordList.get() == null) {
            return;
        }
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;
            PrjPlanUtil.updateNodeStatus(csCommId);
        }
    }


    /**
     * 刷新进度接话节点时间
     */
    public void refreshProPlanTime() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        String proPlanId = entityRecordList.get(0).valueMap.get("ID").toString();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan where id=?", proPlanId);
        if (!CollectionUtils.isEmpty(list)) {
            String projectId = String.valueOf(list.get(0).get("PM_PRJ_ID"));
            Date date = null;
            if (entityRecordList.get(0).valueMap.get("PLAN_START_DATE") != null) {
                date = DateTimeUtil.stringToDate(entityRecordList.get(0).valueMap.get("PLAN_START_DATE").toString());
            }
            PrjPlanUtil.refreshProPlanTime(projectId, date);
        }

    }


    /**
     * 项目进度计划信息。
     */
    public static class PrjProPlanInfo {
        /**
         * 主键ID
         */
        public String id;
        /**
         * code
         */
        public String code;
        /**
         * 名称
         */
        public String name;
        /**
         * 备注
         */
        public String remark;

        /**
         * 第几天开始
         */
        public String startDay;
        /**
         * 预计开始日期
         */
        public String planStartDate;
        /**
         * 预计完成日期
         */
        public String planComplDate;
        /**
         * 预计总天数
         */
        public Integer planTotalDays;
        /**
         * 预计已开展天数
         */
        public Integer planCarryDays;
        /**
         * 预计当前进度百分比
         */
        public Double planCurrentProPercent;
        /**
         * 实际开始日期
         */
        public String actualStartDate;
        /**
         * 实际完成日期
         */
        public String actualComplDate;
        /**
         * 实际总天数
         */
        public Integer actualTotalDays;
        /**
         * 实际已开展天数
         */
        public Integer actualCarryDays;
        /**
         * 实际当前进度百分比
         */
        public Double actualCurrentProPercent;
        /**
         * 进度状态
         */
        public IdCodeName progressStatus;
        /**
         * 进度风险类型
         */
        public IdCodeName progressRiskType;
        /**
         * 进度风险说明
         */
        public String progressRiskRemark;

        public String projectId;

        public String ver;

        /**
         * 项目名称
         */
        public String projectName;

        public List<PrjProPlanNodeInfo> nodeInfoList;
    }

    /**
     * 项目进度计划节点信息。
     */
    public static class PrjProPlanNodeInfo {
        /**
         * 主键ID
         */
        public String id;
        /**
         * 父ID
         */
        public String pid;
        /**
         * code
         */
        public String code;
        /**
         * 进度节点名称
         */
        public String name;
        /**
         * 备注
         */
        public String remark;
        /**
         * 第几天开始
         */
        public String startDay;
        /**
         * 预计开始日期
         */
        public String planStartDate;
        /**
         * 预计完成日期
         */
        public String planComplDate;
        /**
         * 预计总天数
         */
        public Integer planTotalDays;
        /**
         * 预计已开展天数
         */
        public Integer planCarryDays;
        /**
         * 预计当前进度百分比
         */
        public Double planCurrentProPercent;
        /**
         * 实际开始日期
         */
        public String actualStartDate;
        /**
         * 实际完成日期
         */
        public String actualComplDate;
        /**
         * 实际总天数
         */
        public Integer actualTotalDays;
        /**
         * 实际已开展天数
         */
        public Integer actualCarryDays;
        /**
         * 实际当前进度百分比
         */
        public Double actualCurrentProPercent;
        /**
         * 进度状态
         */
        public IdCodeName progressStatus;
        /**
         * 进度风险类型
         */
        public IdCodeName progressRiskType;
        /**
         * 进度风险说明
         */
        public String progressRiskRemark;
        /**
         * 责任部门
         */
        // ad_user
        public IdCodeName chiefDept;
        /**
         * 责任人
         */
        //
        public IdCodeName chiefUser;

        /**
         * 岗位
         */
        public IdCodeName post;
        /**
         * 是否显示首页
         */
        // SHOW_IN_PRJ_OVERVIEW
        public String showInPrjOverview;

        public String seqNo;
        public String level;
        public String chiefDeptId;

        public String ver;

        /**
         * 子节点
         */
        public List<PrjProPlanNodeInfo> children;

        public Integer proCount;

        public String canStart;

        public String linkedWfProcessId;
    }

    /**
     * 项目进度对比表
     */
    public void proContrast() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();
        String json = JsonUtil.toJson(map);
        ProPlanExt.GetPrjProPlanNetworkParam param = JsonUtil.fromJson(json, ProPlanExt.GetPrjProPlanNetworkParam.class);
        String pmPrjId = param.pmPrjId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> proMap = myJdbcTemplate.queryForMap("select pr.CODE,pr.NAME,pr.REMARK,pr.ACTUAL_START_DATE,pr.PROGRESS_RISK_REMARK,pr.IS_TEMPLATE,pr.PM_PRJ_ID,pr.PLAN_START_DATE,ifnull(PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,ifnull(ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(ACTUAL_TOTAL_DAYS,0),ifnull(PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,ifnull(ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,TEMPLATE_FOR_PROJECT_TYPE_ID,PROGRESS_STATUS_ID,PROGRESS_RISK_TYPE_ID,ifnull(START_DAY,0) as START_DAY,pr.CPMS_UUID,pr.CPMS_ID,pj.name as PROJECT_NAME from PM_PRO_PLAN pr left join pm_prj pj on pr.PM_PRJ_ID = pj.id where PM_PRJ_ID=?", pmPrjId);
        ProContrast contrast;
        if (proMap != null) {
            contrast = this.convertProContrast(proMap, myJdbcTemplate);

            List<Map<String, Object>> allList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE,pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,ifnull(pppn.PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(pppn.PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,ifnull(pppn.ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(pppn.ACTUAL_TOTAL_DAYS,0) as ACTUAL_TOTAL_DAYS,ifnull(pppn.PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,ifnull(pppn.ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW,pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID,pppn.START_DAY,pppn.SEQ_NO,pppn.`LEVEL` from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where ppp.PM_PRJ_ID=?", pmPrjId);

            List<ProContrastNode> infoList = allList.stream().map(p -> this.convertProContrastNode(p, myJdbcTemplate)).collect(Collectors.toList());
            // 构建树
            List<ProContrastNode> treeList = infoList.stream().filter(p -> Objects.equals("0", String.valueOf(p.pid))).peek(m -> {
                m.children = getContrastChildren(m, infoList);
            }).collect(Collectors.toList());
            contrast.info = treeList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(contrast), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    private List<ProContrastNode> getContrastChildren(ProContrastNode parent, List<ProContrastNode> allData) {
        return allData.stream().filter(p -> Objects.equals(parent.id, p.pid)).peek(m -> {
            m.children = getContrastChildren(m, allData);
        }).collect(Collectors.toList());
    }

    private ProContrastNode convertProContrastNode(Map<String, Object> data, MyJdbcTemplate myJdbcTemplate) {
        ProContrastNode node = new ProContrastNode();
        node.id = JdbcMapUtil.getString(data, "ID");
        node.pid = JdbcMapUtil.getString(data, "PM_PRO_PLAN_NODE_PID");
        node.name = JdbcMapUtil.getString(data, "NAME");
        if (!Objects.isNull(data.get("CHIEF_DEPT_ID"))) {
            Map<String, Object> deptObj = myJdbcTemplate.queryForMap("select * from hr_dept where id =?", JdbcMapUtil.getString(data, "CHIEF_DEPT_ID"));
            node.dept = String.valueOf(deptObj.get("NAME"));
        }
        if (!Objects.isNull(data.get("CHIEF_USER_ID"))) {
            Map<String, Object> userObj = myJdbcTemplate.queryForMap("select * from ad_user where id =?", JdbcMapUtil.getString(data, "CHIEF_USER_ID"));
            node.user = String.valueOf(userObj.get("NAME"));
        }
        String sql = "select * from gr_set_value where id=?";
        if (!Objects.isNull(data.get("PROGRESS_STATUS_ID"))) {
            Map<String, Object> statusObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(data, "PROGRESS_STATUS_ID"));
            node.proStatus = String.valueOf(statusObj.get("NAME"));
        }
        if (!Objects.isNull(data.get("PROGRESS_RISK_TYPE_ID"))) {
            Map<String, Object> riskObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(data, "PROGRESS_RISK_TYPE_ID"));
            node.riskStatus = String.valueOf(riskObj.get("NAME"));
        }
        node.proRiskRemark = JdbcMapUtil.getString(data, "PROGRESS_RISK_REMARK");

        node.planStartDate = JdbcMapUtil.getString(data, "PLAN_START_DATE");
        node.actualStartDate = JdbcMapUtil.getString(data, "ACTUAL_START_DATE");
        if (Objects.nonNull(data.get("PLAN_START_DATE"))) {
            Date actual = new Date();
            if (Objects.nonNull(data.get("ACTUAL_START_DATE"))) {
                actual = DateTimeUtil.stringToDate(String.valueOf(data.get("ACTUAL_START_DATE")));
            }
            try {
                node.beginOffset = DateTimeUtil.daysBetween(actual, DateTimeUtil.stringToDate(String.valueOf(data.get("PLAN_START_DATE"))));
            } catch (Exception e) {
                node.beginOffset = null;
            }

        }


        node.planComplDate = JdbcMapUtil.getString(data, "PLAN_COMPL_DATE");
        node.actualComplDate = JdbcMapUtil.getString(data, "ACTUAL_COMPL_DATE");
        if (Objects.nonNull(data.get("PLAN_COMPL_DATE"))) {
            Date actual = new Date();
            if (Objects.nonNull(data.get("ACTUAL_COMPL_DATE"))) {
                actual = DateTimeUtil.stringToDate(String.valueOf(data.get("ACTUAL_COMPL_DATE")));
            }
            try {
                node.endOffset = DateTimeUtil.daysBetween(actual, DateTimeUtil.stringToDate(String.valueOf(data.get("PLAN_COMPL_DATE"))));
            } catch (Exception e) {
                node.endOffset = null;
            }

        }


        node.planTotalDays = JdbcMapUtil.getInt(data, "PLAN_TOTAL_DAYS");
        node.actualTotalDays = JdbcMapUtil.getInt(data, "ACTUAL_TOTAL_DAYS");
        if (Objects.nonNull(data.get("PLAN_TOTAL_DAYS"))) {
            int actualTotalDays = 0;
            if (Objects.nonNull(data.get("ACTUAL_TOTAL_DAYS"))) {
                actualTotalDays = JdbcMapUtil.getInt(data, "ACTUAL_TOTAL_DAYS");
            }
            node.daysOffset = node.planTotalDays - actualTotalDays;
        }


        node.planCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(data, "PLAN_CURRENT_PRO_PERCENT"));
        node.actualCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(data, "ACTUAL_CURRENT_PRO_PERCENT"));
        if (Objects.nonNull(data.get("PLAN_CURRENT_PRO_PERCENT"))) {
            double actualCurrentProPercent = 0d;
            if (Objects.nonNull(data.get("ACTUAL_CURRENT_PRO_PERCENT"))) {
                actualCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(data, "ACTUAL_CURRENT_PRO_PERCENT"));
            }
            node.percentOffset = String.valueOf(actualCurrentProPercent - node.planCurrentProPercent);
        }

        return node;
    }

    private ProContrast convertProContrast(Map<String, Object> data, MyJdbcTemplate myJdbcTemplate) {
        ProContrast contrast = new ProContrast();
        contrast.id = JdbcMapUtil.getString(data, "ID");
        contrast.projectId = JdbcMapUtil.getString(data, "PM_PRJ_ID");
        contrast.projectName = JdbcMapUtil.getString(data, "PROJECT_NAME");
        if (!Objects.isNull(data.get("CHIEF_DEPT_ID"))) {
            Map<String, Object> deptObj = myJdbcTemplate.queryForMap("select * from hr_dept where id =?", JdbcMapUtil.getString(data, "CHIEF_DEPT_ID"));
            contrast.dept = String.valueOf(deptObj.get("NAME"));
        }
        if (!Objects.isNull(data.get("CHIEF_USER_ID"))) {
            Map<String, Object> userObj = myJdbcTemplate.queryForMap("select * from ad_user where id =?", JdbcMapUtil.getString(data, "CHIEF_USER_ID"));
            contrast.user = String.valueOf(userObj.get("NAME"));
        }
        String sql = "select * from gr_set_value where id=?";
        if (!Objects.isNull(data.get("PROGRESS_STATUS_ID"))) {
            Map<String, Object> statusObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(data, "PROGRESS_STATUS_ID"));
            contrast.proStatus = String.valueOf(statusObj.get("NAME"));
        }
        if (!Objects.isNull(data.get("PROGRESS_RISK_TYPE_ID"))) {
            Map<String, Object> riskObj = myJdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(data, "PROGRESS_RISK_TYPE_ID"));
            contrast.riskStatus = String.valueOf(riskObj.get("NAME"));
        }
        contrast.proRiskRemark = JdbcMapUtil.getString(data, "PROGRESS_RISK_REMARK");

        contrast.planStartDate = JdbcMapUtil.getString(data, "PLAN_START_DATE");
        contrast.actualStartDate = JdbcMapUtil.getString(data, "ACTUAL_START_DATE");
        if (Objects.nonNull(data.get("PLAN_START_DATE"))) {
            Date actual = new Date();
            if (Objects.nonNull(data.get("ACTUAL_START_DATE"))) {
                actual = DateTimeUtil.stringToDate(String.valueOf(data.get("ACTUAL_START_DATE")));
            }
            try {
                contrast.beginOffset = DateTimeUtil.daysBetween(DateTimeUtil.stringToDate(String.valueOf(data.get("PLAN_START_DATE"))), actual);
            } catch (Exception e) {
                contrast.beginOffset = null;
            }
        }

        contrast.planComplDate = JdbcMapUtil.getString(data, "PLAN_COMPL_DATE");
        contrast.actualComplDate = JdbcMapUtil.getString(data, "ACTUAL_COMPL_DATE");
        if (Objects.nonNull(data.get("PLAN_COMPL_DATE"))) {
            Date actual = new Date();
            if (Objects.nonNull(data.get("ACTUAL_START_DATE"))) {
                if (Objects.nonNull(data.get("ACTUAL_COMPL_DATE"))) {
                    actual = DateTimeUtil.stringToDate(String.valueOf(data.get("ACTUAL_COMPL_DATE")));
                }
            }
            try {
                contrast.endOffset = DateTimeUtil.daysBetween(DateTimeUtil.stringToDate(String.valueOf(data.get("PLAN_COMPL_DATE"))), actual);
            } catch (Exception e) {
                contrast.endOffset = null;
            }
        }

        contrast.planTotalDays = JdbcMapUtil.getInt(data, "PLAN_TOTAL_DAYS");
        contrast.actualTotalDays = JdbcMapUtil.getInt(data, "ACTUAL_TOTAL_DAYS");
        if (Objects.nonNull(data.get("PLAN_TOTAL_DAYS"))) {
            int actualTotalDays = 0;
            if (Objects.nonNull(data.get("ACTUAL_TOTAL_DAYS"))) {
                actualTotalDays = JdbcMapUtil.getInt(data, "ACTUAL_TOTAL_DAYS");
            }
            contrast.daysOffset = contrast.planTotalDays - actualTotalDays;
        }


        contrast.planCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(data, "PLAN_CURRENT_PRO_PERCENT"));
        contrast.actualCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(data, "ACTUAL_CURRENT_PRO_PERCENT"));
        if (Objects.nonNull(data.get("PLAN_CURRENT_PRO_PERCENT"))) {
            double actualCurrentProPercent = 0d;
            if (Objects.nonNull(data.get("ACTUAL_CURRENT_PRO_PERCENT"))) {
                actualCurrentProPercent = Double.parseDouble(JdbcMapUtil.getString(data, "ACTUAL_CURRENT_PRO_PERCENT"));
            }
            contrast.percentOffset = String.valueOf(actualCurrentProPercent - contrast.planCurrentProPercent);
        }
        return contrast;
    }

    public static class ProContrastNode {

        /**
         * ID
         */
        public String id;

        /**
         * 父ID
         */
        public String pid;

        /**
         * 节点名称
         */
        public String name;

        /**
         * 责任部门
         */
        public String dept;

        /**
         * 责任人
         */
        public String user;

        /**
         * 进度状态
         */
        public String proStatus;

        /**
         * 风险状态
         */
        public String riskStatus;

        /**
         * 进度风险说明
         */
        public String proRiskRemark;

        /**
         * 预计开始日期
         */
        public String planStartDate;
        /**
         * 实际开始日期
         */
        public String actualStartDate;
        /**
         * 开始日期偏差
         */
        public Integer beginOffset;
        /**
         * 预计完成日期
         */
        public String planComplDate;
        /**
         * 实际完成日期
         */
        public String actualComplDate;

        /**
         * 完成日期偏差
         */
        public Integer endOffset;

        /**
         * 预计总天数
         */
        public Integer planTotalDays;
        /**
         * 实际总天数
         */
        public Integer actualTotalDays;
        /**
         * 总天数偏差
         */
        public Integer daysOffset;

        /**
         * 预计当前进度百分比
         */
        public Double planCurrentProPercent;
        /**
         * 实际当前进度百分比
         */
        public Double actualCurrentProPercent;

        /**
         * 进度偏差
         */
        public String percentOffset;

        /**
         * 子集
         */
        public List<ProContrastNode> children;

    }

    public static class ProContrast {
        public String id;
        /**
         * 项目ID
         */
        public String projectId;
        /**
         * 项目名称
         */
        public String projectName;
        /**
         * 责任部门
         */
        public String dept;

        /**
         * 责任人
         */
        public String user;

        /**
         * 进度状态
         */
        public String proStatus;

        /**
         * 风险状态
         */
        public String riskStatus;

        /**
         * 进度风险说明
         */
        public String proRiskRemark;

        /**
         * 预计开始日期
         */
        public String planStartDate;
        /**
         * 实际开始日期
         */
        public String actualStartDate;
        /**
         * 开始日期偏差
         */
        public Integer beginOffset;
        /**
         * 预计完成日期
         */
        public String planComplDate;
        /**
         * 实际完成日期
         */
        public String actualComplDate;

        /**
         * 完成日期偏差
         */
        public Integer endOffset;

        /**
         * 预计总天数
         */
        public Integer planTotalDays;
        /**
         * 实际总天数
         */
        public Integer actualTotalDays;
        /**
         * 总天数偏差
         */
        public Integer daysOffset;

        /**
         * 预计当前进度百分比
         */
        public Double planCurrentProPercent;
        /**
         * 实际当前进度百分比
         */
        public Double actualCurrentProPercent;

        /**
         * 进度偏差
         */
        public String percentOffset;

        public List<ProContrastNode> info;
    }

    public static class OutSide {
        public List<PrjProPlanNodeInfo> nodeInfoList;
    }


    /**
     * 全景节点详情
     */
    public void nodeEditView() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppn.*,pi.name as post_name,pre.name as pre_name,wp.name as processName " +
                "from pm_pro_plan_node pppn left join post_info pi on pppn.POST_INFO_ID = pi.id " +
                "left join pm_pro_plan_node pre on pppn.PRE_NODE_ID = pre.id " +
                "left join WF_PROCESS wp on pppn.LINKED_WF_PROCESS_ID = wp.id " +
                "where pppn.ID=?", map.get("nodeId"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            Map<String, Object> dataMap = list.get(0);
            PmProPlanTempExt.PlanNode node = new PmProPlanTempExt.PlanNode();
            node.id = JdbcMapUtil.getString(dataMap, "ID");
            node.pid = JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_NODE_PID");
            node.name = JdbcMapUtil.getString(dataMap, "NAME");
            node.postId = JdbcMapUtil.getString(dataMap, "POST_INFO_ID");
            node.postName = JdbcMapUtil.getString(dataMap, "post_name");
            node.days = JdbcMapUtil.getInt(dataMap, "PLAN_TOTAL_DAYS");
            node.preNodeId = JdbcMapUtil.getString(dataMap, "PRE_NODE_ID");
            node.preNodeName = JdbcMapUtil.getString(dataMap, "pre_name");
            node.processId = JdbcMapUtil.getString(dataMap, "LINKED_WF_PROCESS_ID");
            node.processName = JdbcMapUtil.getString(dataMap, "processName");
            node.startNode = JdbcMapUtil.getString(dataMap, "LINKED_START_WF_NODE_ID");
            node.endNode = JdbcMapUtil.getString(dataMap, "LINKED_END_WF_NODE_ID");
            node.iz_milestone = JdbcMapUtil.getString(dataMap, "IZ_MILESTONE");
            node.seqNo = JdbcMapUtil.getString(dataMap, "SEQ_NO");
            node.planStartDay = JdbcMapUtil.getString(dataMap, "PLAN_START_DATE");
            node.proPlanId = JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_ID");
            node.level = JdbcMapUtil.getString(dataMap, "level");
            node.baseNodeId = JdbcMapUtil.getString(dataMap, "SCHEDULE_NAME");
            node.ver = JdbcMapUtil.getString(dataMap, "VER");
            String att = JdbcMapUtil.getString(dataMap, "AD_ATT_ID_IMP");
            if (Strings.isNotEmpty(att)) {
                node.atts = Arrays.asList(att.split(","));
            }
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(node), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 项目进展节点修改
     */
    public void nodeModify() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        NodeInput input = JsonUtil.fromJson(json, NodeInput.class);
        String operationType = null;
        if (Strings.isEmpty(input.id)) {
            input.id = Crud.from("pm_pro_plan_node").insertData();
            operationType = "add";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("update pm_pro_plan_node set LAST_MODI_DT =NOW() ");
        if (operationType != null) {
            sb.append(",OPREATION_TYPE='").append(operationType).append("'");
        }
        if (Strings.isNotEmpty(input.name)) {
            sb.append(",`NAME` ='").append(input.name).append("'");
        }
        if (Strings.isNotEmpty(input.postId)) {
            sb.append(",POST_INFO_ID ='").append(input.postId).append("'");
            //如果修改的是岗位，把花名册的人，同时也刷过去
            String userId = getRosterUser(input.projectId, input.postId);
            sb.append(",CHIEF_USER_ID ='").append(userId).append("'");
        }
        if (Strings.isNotEmpty(input.preNodeId)) {
            sb.append(",PRE_NODE_ID ='").append(input.preNodeId).append("'");
        }
        if (Strings.isNotEmpty(input.processId)) {
            sb.append(",LINKED_WF_PROCESS_ID ='").append(input.processId).append("'");
        }
        if (Strings.isNotEmpty(input.startNodeId)) {
            sb.append(",LINKED_START_WF_NODE_ID ='").append(input.startNodeId).append("'");
        }
        if (Strings.isNotEmpty(input.endNodeId)) {
            sb.append(",LINKED_END_WF_NODE_ID ='").append(input.endNodeId).append("'");
        }
        if (null != input.days && input.days > 0) {
            sb.append(",PLAN_TOTAL_DAYS =").append(input.days);
        }
        if (Strings.isNotEmpty(input.tableId)) {
            sb.append(",AD_ENT_ID_IMP ='").append(input.tableId).append("'");
        }
        if (Strings.isNotEmpty(input.attIds)) {
            sb.append(",AD_ATT_ID_IMP ='").append(input.attIds).append("'");
        }
        if (Strings.isNotEmpty(input.baseNodeId)) {
            sb.append(",SCHEDULE_NAME ='").append(input.baseNodeId).append("'");
        }
        if (Strings.isNotEmpty(input.level)) {
            sb.append(",LEVEL = '").append(input.level).append("'");
        }
        if (Strings.isNotEmpty(input.proPlanId)) {
            sb.append(",PM_PRO_PLAN_ID = '").append(input.proPlanId).append("'");
        }
        if (Strings.isNotEmpty(input.pid)) {
            sb.append(",PM_PRO_PLAN_NODE_PID = '").append(input.pid).append("'");
        }
        if (Strings.isNotEmpty(input.seqNo)) {
            sb.append(",SEQ_NO = '").append(input.seqNo).append("'");
        }
        if (Strings.isNotEmpty(input.planStartDay)) {
            sb.append(",PLAN_START_DATE = '").append(input.planStartDay).append("'");
        }
        if (Strings.isEmpty(input.izMilestone)) {
            input.izMilestone = "0";
        }
        if (Strings.isNotEmpty(input.attData)) {
            sb.append(",ATT_DATA = '").append(input.attData).append("'");
        }
        sb.append(",IZ_MILESTONE =").append(input.izMilestone);
        sb.append(" where id='").append(input.id).append("'");
        myJdbcTemplate.update(sb.toString());
        //刷新时间
        PrjPlanUtil.updatePreNodeTime(input.id, input.projectId);
    }

    /**
     * 获取花名册人员
     *
     * @param projectId
     * @param postId
     * @return
     */
    private String getRosterUser(String projectId, String postId) {
        String userId = null;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_ROSTER where PM_PRJ_ID=? and POST_INFO_ID=?", projectId, postId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            userId = JdbcMapUtil.getString(dataMap, "AD_USER_ID");
        }
        return userId;
    }


    /**
     * 删除时查询当前节点的后置节点
     */
    public void nodeDelCheck() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where PRE_NODE_ID=?", map.get("id"));
        if (!CollectionUtils.isEmpty(list)) {
            List<String> res = list.stream().map(p -> JdbcMapUtil.getString(p, "ID")).collect(Collectors.toList());
            PlanOutSide outSide = new PlanOutSide();
            outSide.ids = res;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    /**
     * 删除节点--先给个操作标识符。需要流程审批通过后才删除
     */
    public void delPrjNode() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String nodeId = JdbcMapUtil.getString(inputMap, "nodeId");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan_node where id=?", nodeId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> dataMap = list.get(0);
            if ("add".equals(JdbcMapUtil.getString(dataMap, "OPREATION_TYPE"))) {
                myJdbcTemplate.update("delete from pm_pro_plan_node where id=?", nodeId);
            } else {
                myJdbcTemplate.update("update pm_pro_plan_node set OPREATION_TYPE='del' where id=?", nodeId);
            }
        }
    }


    /**
     * 延期申请历史
     */
    public void delayApplyHistory() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pe.*,ad.`NAME` as user_name from PM_EXTENSION_REQUEST_REQ pe left join ad_user ad on pe.CRT_USER_ID = ad.id where PM_PRO_PLAN_NODE_ID = ?", map.get("nodeId"));
        AtomicInteger index = new AtomicInteger(1);
        List<WeekTaskExt.DelayApplyHistory> historyList = list.stream().map(p -> {
            WeekTaskExt.DelayApplyHistory history = new WeekTaskExt.DelayApplyHistory();
            history.serNo = String.valueOf(index.getAndIncrement());
            history.delayNum = JdbcMapUtil.getString(p, "DAYS_ONE");
            history.description = JdbcMapUtil.getString(p, "TEXT_REMARK_ONE");
            history.applyUser = JdbcMapUtil.getString(p, "user_name");
            history.applyTime = JdbcMapUtil.getString(p, "CRT_DT");
            return history;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(historyList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            WeekTaskExt.OutSide outSide = new WeekTaskExt.OutSide();
            outSide.historyList = historyList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class NodeInput {
        //节点ID
        public String id;
        //名称
        public String name;
        //岗位ID
        public String postId;
        //工期
        public Integer days = 0;
        //前置节点
        public String preNodeId;
        //流程
        public String processId;
        //开始节点
        public String startNodeId;
        //结束节点
        public String endNodeId;
        //是否是里程碑
        public String izMilestone = "0";
        //展示字段
        public String attIds;
        //表
        public String tableId;
        //标准节点ID
        public String baseNodeId;
        //层级
        public String level;
        //计划id
        public String proPlanId;
        //父id
        public String pid;
        //序号
        public String seqNo;
        //计划开始日期
        public String planStartDay;

        public String projectId;

        public String attData;
    }


    /**
     * 根据项目id查进度计划
     */
    public void getNodeByPrj() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> proPlanList = myJdbcTemplate.queryForList("select id proPlanId from pm_pro_plan where pm_prj_id = ?", map.get("prjId"));
        if (!CollectionUtils.isEmpty(proPlanList)) {
            Map<String, Object> proPlanIdMap = proPlanList.get(0);
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppn.*,pi.name as post_name,pre.name as pre_name,wp.name as processName,v.name progressStatusName  " +
                    "from pm_pro_plan_node pppn left join post_info pi on pppn.POST_INFO_ID = pi.id  " +
                    "left join pm_pro_plan_node pre on pppn.PRE_NODE_ID = pre.id  " +
                    "left join WF_PROCESS wp on pppn.LINKED_WF_PROCESS_ID = wp.id  " +
                    "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID " +
                    "left join gr_set_value v on v.id = pppn.PROGRESS_STATUS_ID " +
                    "where (pppn.OPREATION_TYPE <> 'add' or pppn.OPREATION_TYPE is null) and  ppp.pm_prj_id = ?", map.get("prjId"));
            List<PlanNode> nodeList = list.stream().map(p -> {
                PlanNode node = new PlanNode();
                node.id = JdbcMapUtil.getString(p, "ID");
                node.pid = JdbcMapUtil.getString(p, "PM_PRO_PLAN_NODE_PID") == null ? "0" : JdbcMapUtil.getString(p, "PM_PRO_PLAN_NODE_PID");
                node.name = JdbcMapUtil.getString(p, "NAME");
                node.postId = JdbcMapUtil.getString(p, "POST_INFO_ID");
                node.days = JdbcMapUtil.getInt(p, "PLAN_TOTAL_DAYS");
                node.actualDays = JdbcMapUtil.getInt(p, "ACTUAL_TOTAL_DAYS");
                node.planStartDay = JdbcMapUtil.getString(p, "PLAN_START_DATE");
                node.planComplDay = JdbcMapUtil.getString(p, "PLAN_COMPL_DATE");
                node.actualStartDay = JdbcMapUtil.getString(p, "ACTUAL_START_DATE");
                node.actualComplDay = JdbcMapUtil.getString(p, "ACTUAL_COMPL_DATE");
                node.progressStatusId = JdbcMapUtil.getString(p, "PROGRESS_STATUS_ID");
                node.progressStatusName = JdbcMapUtil.getString(p, "progressStatusName");
                node.preNodeId = JdbcMapUtil.getString(p, "PRE_NODE_ID");
                node.processId = JdbcMapUtil.getString(p, "LINKED_WF_PROCESS_ID");
                node.startNode = JdbcMapUtil.getString(p, "LINKED_START_WF_NODE_ID");
                node.endNode = JdbcMapUtil.getString(p, "LINKED_END_WF_NODE_ID");
                node.seqNo = JdbcMapUtil.getString(p, "SEQ_NO");
                node.postName = JdbcMapUtil.getString(p, "post_name");
                node.iz_milestone = JdbcMapUtil.getString(p, "IZ_MILESTONE");
                node.preNodeName = JdbcMapUtil.getString(p, "pre_name");
                node.processName = JdbcMapUtil.getString(p, "processName");
                node.level = JdbcMapUtil.getString(p, "level");
                node.baseNodeId = JdbcMapUtil.getString(p, "SCHEDULE_NAME");
                node.ver = JdbcMapUtil.getString(p, "VER");
                String att = JdbcMapUtil.getString(p, "AD_ATT_ID_IMP");
                if (Strings.isNotEmpty(att)) {
                    node.atts = Arrays.asList(att.split(","));
                }
                node.izDisplay = JdbcMapUtil.getString(p, "IZ_DISPLAY");
                node.attData = JdbcMapUtil.getString(p, "ATT_DATA");
                return node;
            }).collect(Collectors.toList());

            List<PlanNode> tree = nodeList.stream()
                    .filter(p -> "0".equals(p.pid))
                    .sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo)))
                    .peek(m -> m.children = getChildren(m, nodeList).stream()
                            .sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo)))
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(tree)) {
                ExtJarHelper.returnValue.set(Collections.emptyMap());
            } else {
                PlanOutSide outSide = new PlanOutSide();
                outSide.proPlanId = proPlanIdMap.get("proPlanId").toString();
                outSide.tree = tree;
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            }
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 全景计划调整页数据查询
     */
    public void getNodeByPrjAdjust() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> proPlanList = myJdbcTemplate.queryForList("select id proPlanId from pm_pro_plan where pm_prj_id = ?", map.get("prjId"));
        if (!CollectionUtils.isEmpty(proPlanList)) {
            Map<String, Object> proPlanIdMap = proPlanList.get(0);
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pppn.*,pi.name as post_name,pre.name as pre_name,wp.name as processName,v.name progressStatusName  " +
                    "from pm_pro_plan_node pppn left join post_info pi on pppn.POST_INFO_ID = pi.id  " +
                    "left join pm_pro_plan_node pre on pppn.PRE_NODE_ID = pre.id  " +
                    "left join WF_PROCESS wp on pppn.LINKED_WF_PROCESS_ID = wp.id  " +
                    "left join pm_pro_plan ppp on ppp.id = pppn.PM_PRO_PLAN_ID " +
                    "left join gr_set_value v on v.id = pppn.PROGRESS_STATUS_ID " +
                    "where ppp.pm_prj_id = ?", map.get("prjId"));
            List<PlanNode> nodeList = list.stream().map(p -> {
                PlanNode node = new PlanNode();
                node.id = JdbcMapUtil.getString(p, "ID");
                node.pid = JdbcMapUtil.getString(p, "PM_PRO_PLAN_NODE_PID") == null ? "0" : JdbcMapUtil.getString(p, "PM_PRO_PLAN_NODE_PID");
                node.name = JdbcMapUtil.getString(p, "NAME");
                node.postId = JdbcMapUtil.getString(p, "POST_INFO_ID");
                node.days = JdbcMapUtil.getInt(p, "PLAN_TOTAL_DAYS");
                node.actualDays = JdbcMapUtil.getInt(p, "ACTUAL_TOTAL_DAYS");
                node.planStartDay = JdbcMapUtil.getString(p, "PLAN_START_DATE");
                node.planComplDay = JdbcMapUtil.getString(p, "PLAN_COMPL_DATE");
                node.actualStartDay = JdbcMapUtil.getString(p, "ACTUAL_START_DATE");
                node.actualComplDay = JdbcMapUtil.getString(p, "ACTUAL_COMPL_DATE");
                node.progressStatusId = JdbcMapUtil.getString(p, "PROGRESS_STATUS_ID");
                node.progressStatusName = JdbcMapUtil.getString(p, "progressStatusName");
                node.preNodeId = JdbcMapUtil.getString(p, "PRE_NODE_ID");
                node.processId = JdbcMapUtil.getString(p, "LINKED_WF_PROCESS_ID");
                node.startNode = JdbcMapUtil.getString(p, "LINKED_START_WF_NODE_ID");
                node.endNode = JdbcMapUtil.getString(p, "LINKED_END_WF_NODE_ID");
                node.seqNo = JdbcMapUtil.getString(p, "SEQ_NO");
                node.postName = JdbcMapUtil.getString(p, "post_name");
                node.iz_milestone = JdbcMapUtil.getString(p, "IZ_MILESTONE");
                node.preNodeName = JdbcMapUtil.getString(p, "pre_name");
                node.processName = JdbcMapUtil.getString(p, "processName");
                node.level = JdbcMapUtil.getString(p, "level");
                node.baseNodeId = JdbcMapUtil.getString(p, "SCHEDULE_NAME");
                node.ver = JdbcMapUtil.getString(p, "VER");
                String att = JdbcMapUtil.getString(p, "AD_ATT_ID_IMP");
                if (Strings.isNotEmpty(att)) {
                    node.atts = Arrays.asList(att.split(","));
                }
                node.izDisplay = JdbcMapUtil.getString(p, "IZ_DISPLAY");
                node.oprType = JdbcMapUtil.getString(p, "OPREATION_TYPE");
                node.attData = JdbcMapUtil.getString(p, "ATT_DATA");
                return node;
            }).collect(Collectors.toList());

            List<PlanNode> tree = nodeList.stream()
                    .filter(p -> "0".equals(p.pid))
                    .sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo)))
                    .peek(m -> m.children = getChildren(m, nodeList).stream()
                            .sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo)))
                            .collect(Collectors.toList()))
                    .collect(Collectors.toList());
            if (CollectionUtils.isEmpty(tree)) {
                ExtJarHelper.returnValue.set(Collections.emptyMap());
            } else {
                PlanOutSide outSide = new PlanOutSide();
                outSide.proPlanId = proPlanIdMap.get("proPlanId").toString();
                outSide.tree = tree;
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            }
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    private List<PlanNode> getChildren(PlanNode parentNode, List<PlanNode> allData) {
        return allData.stream().filter(p -> parentNode.id.equals(p.pid)).peek(m -> {
            m.children = getChildren(m, allData).stream().sorted(Comparator.comparing(p -> p.seqNo, Comparator.nullsFirst(String::compareTo))).collect(Collectors.toList());
        }).collect(Collectors.toList());
    }


    /**
     * 刷新当前节点时间及其后置节点时间
     */
    public void refreshCurrentNodeTime() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        PrjPlanUtil.updatePreNodeTime(JdbcMapUtil.getString(map, "nodeId"), JdbcMapUtil.getString(map, "projectId"));
    }

    public static class PlanOutSide {
        public String proPlanId;
        public List<ProPlanTempRule> ruleList;
        public List<PlanNode> tree;
        public List<String> names;
        public List<ObjInfo> objInfoList;
        public List<AttInfo> attInfoList;
        public List<String> ids;
    }

    public static class AttInfo {
        public String id;
        public String name;
        public String tableId;
    }

    public static class ObjInfo {
        public String id;
        public String name;
    }

    public static class ProPlanTempRule {
        public String id;
        public String condtionId;
        public String condtionName;
        public String modeId;
        public String modeName;
        public String typeId;
        public String typeName;
        public String sourceId;
        public String sourceName;
        public String proPlanId;
        public String editStatusName;
        public String editStatusId;
    }

    public static class PlanNode {
        public String id;
        public String pid;
        public String name;
        public String postId;
        public String postName;
        public Integer days;
        public Integer actualDays;
        public String planStartDay;
        public String planComplDay;
        public String actualStartDay;
        public String actualComplDay;
        public String preNodeId;
        public String preNodeName;
        public String processId;
        public String processName;
        public String startNode;
        public String endNode;
        public List<String> atts;
        public String iz_milestone;
        public String seqNo;
        public String proPlanId;
        public String level;

        public List<PlanNode> children;
        public String baseNodeId;

        public String ver;

        public String progressStatusId;
        public String progressStatusName;

        public String izDisplay;

        public String oprType;

        public String attData;
    }


    /**
     * 隐藏显示二级节点
     */
    public void displaySecondLevel() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("update PM_PRO_PLAN_NODE set IZ_DISPLAY=? where id=?", map.get("izDisplay"), map.get("nodeId"));
    }

    /**
     * 判断是否有正在进行的全景时间调整审批流程
     */
    public void chargeDataApprove() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map
        Integer count = PmNodeAdjustReqExt.getNodeAdjustByPrj(JdbcMapUtil.getString(map, "projectId"));
        Map obj = new HashMap();
        obj.put("count", count);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(obj), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 刷新项目全景
     */
    public void initPrjProPlan() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map
        PrjPlanUtil.createPlan(JdbcMapUtil.getString(map, "projectId"), null, null, null, null);
    }
}
