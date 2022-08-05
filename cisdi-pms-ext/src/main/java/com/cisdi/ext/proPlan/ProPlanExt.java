package com.cisdi.ext.proPlan;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.IdCodeName;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.*;
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
        nodeList.forEach(item -> {
            jdbcTemplate.update("update PM_PRO_PLAN_NODE set START_DAY=?,PLAN_TOTAL_DAYS=? where id=?", item.get("START_DAY"), item.get("PLAN_TOTAL_DAYS"), item.get("ID"));
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
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> nodeList = jdbcTemplate.queryForList("select pppn.* from PM_PRO_PLAN_NODE pppn \n" +
                "left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID\n" +
                "where SHOW_IN_PRJ_OVERVIEW = '1' and ppp.PM_PRJ_ID='' ", pmPrjId);
        List<Map<String, Object>> allList = jdbcTemplate.queryForList("select pppn.* from PM_PRO_PLAN_NODE pppn \n" +
                "left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID\n" +
                "where ppp.PM_PRJ_ID='' ", pmPrjId);

        //组装排序
        allList.forEach(item -> {
            List<Map<String, Object>> childrenList = getChildrenSeq(item, allList);
            childrenList.forEach(v->{
                v.put("SEQ_NO", item.get("SEQ_NO") + String.valueOf(v.get("SEQ_NO")));
            });
        });
        nodeList.forEach(item->{
            Optional<Map<String,Object>> optional = allList.stream().filter(p->Objects.equals(item.get("ID"),p.get("ID"))).findAny();
            optional.ifPresent(stringObjectMap -> item.put("SEQ_NO", stringObjectMap.get("SEQ_NO")));
        });
        //根据SEQ_NO排序
        List<Map<String, Object>> res = nodeList.stream().sorted(Comparator.comparing(p->String.valueOf(p.get("SEQ_NO")))).collect(Collectors.toList());
        //map转换成实体集合
        List<PrjProPlanNodeInfo> nodeInfoList = res.stream().map(p-> this.convertPlanInfoNode(p,jdbcTemplate)).collect(Collectors.toList());
        // 最终，返回：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(nodeInfoList), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 递归处理子节点的排序
     * @param root
     * @param allData
     * @return
     */
    private List<Map<String, Object>> getChildrenSeq(Map<String, Object> root, List<Map<String, Object>> allData) {
        return allData.stream().filter(p -> Objects.equals(root.get("ID"), p.get("PM_PRO_PLAN_NODE_PID")))
                .map(m -> {
                    List<Map<String, Object>> child = getChildren(m, allData);
                    child.forEach(v->{
                        v.put("SEQ_NO", m.get("SEQ_NO") + String.valueOf(v.get("SEQ_NO")));
                    });
                    return m;
                }).collect(Collectors.toList());
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
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        Map<String, Object> proMap = jdbcTemplate.queryForMap("select * from PM_PRO_PLAN where PM_PRJ_ID=?", pmPrjId);
        PrjProPlanInfo planInfo = this.covertPlanInfo(proMap, jdbcTemplate);

        List<Map<String, Object>> allList = jdbcTemplate.queryForList("select pppn.* from PM_PRO_PLAN_NODE pppn \n" +
                "left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID\n" +
                "where ppp.PM_PRJ_ID=? ", pmPrjId);
        //结果转换
        List<PrjProPlanNodeInfo> infoList = allList.stream().map(p -> this.convertPlanInfoNode(p, jdbcTemplate)).collect(Collectors.toList());
        //构建树结构
        List<PrjProPlanNodeInfo> tree = infoList.stream().filter(p -> "0".equals(p.pid)).peek(m -> {
            m.children = getChildNode(m, infoList);
        }).collect(Collectors.toList());

        planInfo.nodeInfoList = tree;
        // 最终，返回：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(planInfo), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
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
            treeEntity.children = getChildNode(treeEntity, allListTree);
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
     * @param jdbcTemplate
     * @return
     */
    private PrjProPlanInfo covertPlanInfo(Map<String, Object> dataMap, JdbcTemplate jdbcTemplate) {
        PrjProPlanInfo planInfo = new PrjProPlanInfo();
        planInfo.id = String.valueOf(dataMap.get("ID"));
        planInfo.code = String.valueOf(dataMap.get("CODE"));
        planInfo.name = String.valueOf(dataMap.get("NAME"));
        planInfo.remark = String.valueOf(dataMap.get("REMARK"));
        planInfo.planStartDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("PLAN_START_DATE")));
        planInfo.planComplDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("PLAN_COMPL_DATE")));
        planInfo.planTotalDays = Integer.parseInt(String.valueOf(dataMap.get("PLAN_TOTAL_DAYS")));
        planInfo.planCarryDays = Integer.parseInt(String.valueOf(dataMap.get("PLAN_CARRY_DAYS")));
        planInfo.planCurrentProPercent = Double.parseDouble(String.valueOf(dataMap.get("PLAN_CURRENT_PRO_PERCENT")));
        planInfo.actualStartDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("ACTUAL_START_DATE")));
        planInfo.actualComplDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("ACTUAL_COMPL_DATE")));
        planInfo.actualTotalDays = Integer.parseInt(String.valueOf(dataMap.get("ACTUAL_TOTAL_DAYS")));
        planInfo.actualCarryDays = Integer.parseInt(String.valueOf(dataMap.get("ACTUAL_CARRY_DAYS")));
        planInfo.actualCurrentProPercent = Double.parseDouble(String.valueOf(dataMap.get("ACTUAL_CURRENT_PRO_PERCENT")));
        String sql = "select * from gr_set_value where id=?";
        Map<String, Object> statusObj = jdbcTemplate.queryForMap(sql, String.valueOf(dataMap.get("PROGRESS_STATUS_ID")));
        planInfo.progressStatus.id = String.valueOf(statusObj.get("ID"));
        planInfo.progressStatus.code = String.valueOf(statusObj.get("CODE"));
        planInfo.progressStatus.name = String.valueOf(statusObj.get("NAME"));
        Map<String, Object> riskObj = jdbcTemplate.queryForMap(sql, String.valueOf(dataMap.get("PROGRESS_RISK_TYPE_ID")));
        planInfo.progressRiskType.id = String.valueOf(riskObj.get("ID"));
        planInfo.progressRiskType.code = String.valueOf(riskObj.get("CODE"));
        planInfo.progressRiskType.name = String.valueOf(riskObj.get("NAME"));
        planInfo.progressRiskRemark = String.valueOf(dataMap.get("PROGRESS_RISK_REMARK"));
        return planInfo;
    }

    /**
     * Map转换成PrjProPlanNodeInfo
     *
     * @param dataMap
     * @param jdbcTemplate
     * @return
     */
    private PrjProPlanNodeInfo convertPlanInfoNode(Map<String, Object> dataMap, JdbcTemplate jdbcTemplate) {
        PrjProPlanNodeInfo nodeInfo = new PrjProPlanNodeInfo();
        nodeInfo.id = String.valueOf(dataMap.get("ID"));
        nodeInfo.pid = String.valueOf(dataMap.get("PM_PRO_PLAN_NODE_PID") == null ? "0" : dataMap.get("PM_PRO_PLAN_NODE_PID"));
        nodeInfo.code = String.valueOf(dataMap.get("CODE"));
        nodeInfo.name = String.valueOf(dataMap.get("NAME"));
        nodeInfo.remark = String.valueOf(dataMap.get("REMARK"));
        nodeInfo.planStartDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("PLAN_START_DATE")));
        nodeInfo.planComplDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("PLAN_COMPL_DATE")));
        nodeInfo.planTotalDays = Integer.parseInt(String.valueOf(dataMap.get("PLAN_TOTAL_DAYS")));
        nodeInfo.planCarryDays = Integer.parseInt(String.valueOf(dataMap.get("PLAN_CARRY_DAYS")));
        nodeInfo.planCurrentProPercent = Double.parseDouble(String.valueOf(dataMap.get("PLAN_CURRENT_PRO_PERCENT")));
        nodeInfo.actualStartDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("ACTUAL_START_DATE")));
        nodeInfo.actualComplDate = DateTimeUtil.stringToDate(String.valueOf(dataMap.get("ACTUAL_COMPL_DATE")));
        nodeInfo.actualTotalDays = Integer.parseInt(String.valueOf(dataMap.get("ACTUAL_TOTAL_DAYS")));
        nodeInfo.actualCarryDays = Integer.parseInt(String.valueOf(dataMap.get("ACTUAL_CARRY_DAYS")));
        nodeInfo.actualCurrentProPercent = Double.parseDouble(String.valueOf(dataMap.get("ACTUAL_CURRENT_PRO_PERCENT")));
        String sql = "select * from gr_set_value where id=?";
        Map<String, Object> statusObj = jdbcTemplate.queryForMap(sql, String.valueOf(dataMap.get("PROGRESS_STATUS_ID")));
        nodeInfo.progressStatus.id = String.valueOf(statusObj.get("ID"));
        nodeInfo.progressStatus.code = String.valueOf(statusObj.get("CODE"));
        nodeInfo.progressStatus.name = String.valueOf(statusObj.get("NAME"));
        Map<String, Object> riskObj = jdbcTemplate.queryForMap(sql, String.valueOf(dataMap.get("PROGRESS_RISK_TYPE_ID")));
        nodeInfo.progressRiskType.id = String.valueOf(riskObj.get("ID"));
        nodeInfo.progressRiskType.code = String.valueOf(riskObj.get("CODE"));
        nodeInfo.progressRiskType.name = String.valueOf(riskObj.get("NAME"));
        nodeInfo.progressRiskRemark = String.valueOf(dataMap.get("PROGRESS_RISK_REMARK"));

        Map<String, Object> deptObj = jdbcTemplate.queryForMap("select * from hr_dept where id =?", String.valueOf(dataMap.get("CHIEF_DEPT_ID")));
        nodeInfo.chiefDept.id = String.valueOf(deptObj.get("ID"));
        nodeInfo.chiefDept.code = String.valueOf(deptObj.get("CODE"));
        nodeInfo.chiefDept.name = String.valueOf(deptObj.get("NAME"));
        Map<String, Object> userObj = jdbcTemplate.queryForMap("select * from ad_user where id =?", String.valueOf(dataMap.get("CHIEF_USER_ID")));
        nodeInfo.chiefUser.id = String.valueOf(userObj.get("ID"));
        nodeInfo.chiefUser.code = String.valueOf(userObj.get("CODE"));
        nodeInfo.chiefUser.name = String.valueOf(userObj.get("NAME"));
        return nodeInfo;
    }

    /**
     * 项目进度计划信息。
     */
    public static class PrjProPlanInfo {
        public String id;
        public String code;
        public String name;
        public String remark;
        public String planStartDate;
        public String planComplDate;
        public Integer planTotalDays;
        public Integer planCarryDays;
        public Double planCurrentProPercent;
        public String actualStartDate;
        public String actualComplDate;
        public Integer actualTotalDays;
        public Integer actualCarryDays;
        public Double actualCurrentProPercent;
        public IdCodeName progressStatus;
        public IdCodeName progressRiskType;
        public String progressRiskRemark;

        public List<PrjProPlanNodeInfo> nodeInfoList;
    }

    /**
     * 项目进度计划节点信息。
     */
    public static class PrjProPlanNodeInfo {
        public String id;
        public String pid;
        public String code;
        public String name;
        public String remark;
        public String planStartDate;
        public String planComplDate;
        public Integer planTotalDays;
        public Integer planCarryDays;
        public Double planCurrentProPercent;
        public String actualStartDate;
        public String actualComplDate;
        public Integer actualTotalDays;
        public Integer actualCarryDays;
        public Double actualCurrentProPercent;
        public IdCodeName progressStatus;
        public IdCodeName progressRiskType;
        public String progressRiskRemark;
        //ad_user
        public IdCodeName chiefDept;
        //hr_dept
        public IdCodeName chiefUser;

        public List<PrjProPlanNodeInfo> children;
    }
}
