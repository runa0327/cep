package com.cisdi.ext.proPlan;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.PrjPlanUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.IdCodeName;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
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
                "from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where SHOW_IN_PRJ_OVERVIEW='1' and ppp.PM_PRJ_ID=?", pmPrjId);

        // 结果转换
        List<PrjProPlanNodeInfo> infoList = allList.stream().map(p -> this.convertPlanInfoNode(p, myJdbcTemplate)).collect(Collectors.toList());
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
                        "ifnull(pppn.ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(pppn.ACTUAL_TOTAL_DAYS,0) as ACTUAL_TOTAL_DAYS,ifnull(pppn.PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,\n" +
                        "ifnull(pppn.ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW,pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID,pppn.START_DAY,pppn.SEQ_NO,pppn.`LEVEL` \n" +
                        "from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where ppp.PM_PRJ_ID=?", pmPrjId);
                // 结果转换
                List<PrjProPlanNodeInfo> infoList = allList.stream().map(p -> this.convertPlanInfoNode(p, myJdbcTemplate)).collect(Collectors.toList());
                // 构建树结构
                List<PrjProPlanNodeInfo> tree = infoList.stream().filter(p -> "0".equals(p.pid)).peek(m -> {
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
    private PrjProPlanNodeInfo convertPlanInfoNode(Map<String, Object> dataMap, MyJdbcTemplate myJdbcTemplate) {
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
            Map<String, Object> deptObj = myJdbcTemplate.queryForMap("select * from hr_dept where id =?", JdbcMapUtil.getString(dataMap, "CHIEF_DEPT_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(deptObj.get("ID"));
            idCodeName.code = String.valueOf(deptObj.get("CODE"));
            idCodeName.name = String.valueOf(deptObj.get("NAME"));
            nodeInfo.chiefDept = idCodeName;
        }

        if (!Objects.isNull(dataMap.get("CHIEF_USER_ID"))) {
            Map<String, Object> userObj = myJdbcTemplate.queryForMap("select * from ad_user where id =?", JdbcMapUtil.getString(dataMap, "CHIEF_USER_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(userObj.get("ID"));
            idCodeName.code = String.valueOf(userObj.get("CODE"));
            idCodeName.name = String.valueOf(userObj.get("NAME"));
            nodeInfo.chiefUser = idCodeName;
        }
        nodeInfo.showInPrjOverview = JdbcMapUtil.getString(dataMap, "SHOW_IN_PRJ_OVERVIEW");
        nodeInfo.seqNo = JdbcMapUtil.getString(dataMap, "SEQ_NO");
        nodeInfo.level = JdbcMapUtil.getString(dataMap, "LEVEL");
        nodeInfo.chiefDeptId = JdbcMapUtil.getString(dataMap, "CHIEF_DEPT_ID");
        nodeInfo.ver = JdbcMapUtil.getString(dataMap, "VER");
        return nodeInfo;
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
            // 查询所有的进度计划节点
            List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER," +
                    "pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID," +
                    "pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE," +
                    "pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,pppn.PLAN_TOTAL_DAYS," +
                    "pppn.PLAN_CARRY_DAYS,pppn.ACTUAL_CARRY_DAYS,pppn.ACTUAL_TOTAL_DAYS,pppn.PLAN_CURRENT_PRO_PERCENT," +
                    "pppn.ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID," +
                    "pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW," +
                    "pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID," +
                    "pppn.START_DAY,pppn.SEQ_NO,pppn.CPMS_UUID,pppn.CPMS_ID,pppn.LEVEL,pppn.LINKED_WF_PROCESS_ID," +
                    "pppn.LINKED_WF_NODE_ID,pppn.LINKED_WF_PROCESS_INSTANCE_ID,pppn.LINKED_WF_NODE_INSTANCE_ID," +
                    "gsv.`CODE` as PROGRESS_STATUS_CODE from pm_pro_plan_node pppn \n" +
                    "left join pm_pro_plan ppp on pppn.PM_PRO_PLAN_ID = ppp.ID\n" +
                    "left join gr_set_value gsv on gsv.id = pppn.PROGRESS_STATUS_ID\n" +
                    "left join gr_set gr on gr.id = gsv.GR_SET_ID\n" +
                    "where gr.`CODE`='PROGRESS_STATUS' and ppp.PM_PRJ_ID=?", csCommId);

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
    }

    /**
     * 递归修改父节点的状态
     *
     * @param root
     * @param allData
     * @param myJdbcTemplate
     * @return
     */
    private List<Map<String, Object>> getChildrenNodeForCollectProgressStatus(Map<String, Object> root, List<Map<String, Object>> allData, MyJdbcTemplate myJdbcTemplate) {
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
        Map<String, Object> proMap = myJdbcTemplate.queryForMap("select pr.CODE,pr.NAME,pr.REMARK,pr.ACTUAL_START_DATE,pr.PROGRESS_RISK_REMARK,pr.IS_TEMPLATE,pr.PM_PRJ_ID,pr.PLAN_START_DATE,\n" +
                "ifnull(PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,\n" +
                "ifnull(ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(ACTUAL_TOTAL_DAYS,0),ifnull(PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,\n" +
                "ifnull(ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,PLAN_COMPL_DATE,ACTUAL_COMPL_DATE,TEMPLATE_FOR_PROJECT_TYPE_ID,PROGRESS_STATUS_ID,\n" +
                "PROGRESS_RISK_TYPE_ID,ifnull(START_DAY,0) as START_DAY,pr.CPMS_UUID,pr.CPMS_ID,pj.name as PROJECT_NAME from PM_PRO_PLAN pr left join pm_prj pj on pr.PM_PRJ_ID = pj.id where PM_PRJ_ID=?", pmPrjId);
        ProContrast contrast;
        if (proMap != null) {
            contrast = this.convertProContrast(proMap, myJdbcTemplate);

            List<Map<String, Object>> allList = myJdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE,pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,ifnull(pppn.PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(pppn.PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,\n" +
                    "ifnull(pppn.ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(pppn.ACTUAL_TOTAL_DAYS,0) as ACTUAL_TOTAL_DAYS,ifnull(pppn.PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,\n" +
                    "ifnull(pppn.ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW,pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID,pppn.START_DAY,pppn.SEQ_NO,pppn.`LEVEL` \n" +
                    "from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where ppp.PM_PRJ_ID=?", pmPrjId);

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
                node.beginOffset = DateTimeUtil.daysBetween(DateTimeUtil.stringToDate(String.valueOf(data.get("PLAN_START_DATE"))), actual);
            } catch (Exception e) {
                node.beginOffset = null;
            }

        }


        node.planComplDate = JdbcMapUtil.getString(data, "PLAN_COMPL_DATE");
        node.actualComplDate = JdbcMapUtil.getString(data, "ACTUAL_COMPL_DATE");
        if (Objects.nonNull(data.get("PLAN_COMPL_DATE"))) {
            Date actual = new Date();
            if (Objects.nonNull(data.get("ACTUAL_START_DATE"))) {
                actual = DateTimeUtil.stringToDate(String.valueOf(data.get("ACTUAL_COMPL_DATE")));
            }
            try {
                node.endOffset = DateTimeUtil.daysBetween(DateTimeUtil.stringToDate(String.valueOf(data.get("PLAN_COMPL_DATE"))), actual);
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
}
