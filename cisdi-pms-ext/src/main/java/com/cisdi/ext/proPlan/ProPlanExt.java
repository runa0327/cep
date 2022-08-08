package com.cisdi.ext.proPlan;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.IdCodeName;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;

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

        List<Map<String, Object>> allList = jdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE,pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,ifnull(pppn.PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(pppn.PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,\n" +
                "ifnull(pppn.ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(pppn.ACTUAL_TOTAL_DAYS,0) as ACTUAL_TOTAL_DAYS,ifnull(pppn.PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,\n" +
                "ifnull(pppn.ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW,pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID,pppn.START_DAY,pppn.SEQ_NO \n" +
                "from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where ppp.PM_PRJ_ID=?", pmPrjId);

        //结果转换
        List<PrjProPlanNodeInfo> infoList = allList.stream().map(p -> this.convertPlanInfoNode(p, jdbcTemplate)).collect(Collectors.toList());
        //构建树结构
        List<PrjProPlanNodeInfo> tree = infoList.stream().filter(p -> "0".equals(p.pid)).peek(m -> {
            m.children = getChildNode(m, infoList);
        }).collect(Collectors.toList());
        List<PrjProPlanNodeInfo> list = new ArrayList<>();
        for (PrjProPlanNodeInfo nodeInfo : tree) {
            if (nodeInfo.children != null) {
                List<PrjProPlanNodeInfo> children = nodeInfo.children;
                convertChildrenToTileList(children, list);
            }
            list.add(nodeInfo);
        }

        List<PrjProPlanNodeInfo> nodeInfoList = list.stream().filter(p -> Objects.equals("1", p.showInPrjOverview)).collect(Collectors.toList());
        PrjProPlanInfo info = new PrjProPlanInfo();
        info.nodeInfoList = nodeInfoList;
        if (nodeInfoList.size() == 0) {
            ExtJarHelper.returnValue.set(null);
        } else {
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(info), Map.class);
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
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        PrjProPlanInfo planInfo = new PrjProPlanInfo();
        Map<String, Object> proMap = null;
        try {
            proMap = jdbcTemplate.queryForMap("select * from PM_PRO_PLAN where PM_PRJ_ID=?", pmPrjId);
            if (proMap != null) {
                planInfo = this.covertPlanInfo(proMap, jdbcTemplate);

                List<Map<String, Object>> allList = jdbcTemplate.queryForList("select pppn.ID,pppn.VER,pppn.TS,pppn.IS_PRESET,pppn.CRT_DT,pppn.CRT_USER_ID,pppn.LAST_MODI_DT,pppn.LAST_MODI_USER_ID,pppn.STATUS,pppn.LK_WF_INST_ID,pppn.CODE,pppn.NAME,pppn.REMARK,pppn.ACTUAL_START_DATE,pppn.PROGRESS_RISK_REMARK,pppn.PM_PRO_PLAN_ID,pppn.PLAN_START_DATE,ifnull(pppn.PLAN_TOTAL_DAYS,0) as PLAN_TOTAL_DAYS,ifnull(pppn.PLAN_CARRY_DAYS,0) as PLAN_CARRY_DAYS,\n" +
                        "ifnull(pppn.ACTUAL_CARRY_DAYS,0) as ACTUAL_CARRY_DAYS,ifnull(pppn.ACTUAL_TOTAL_DAYS,0) as ACTUAL_TOTAL_DAYS,ifnull(pppn.PLAN_CURRENT_PRO_PERCENT,0) as PLAN_CURRENT_PRO_PERCENT,\n" +
                        "ifnull(pppn.ACTUAL_CURRENT_PRO_PERCENT,0) as ACTUAL_CURRENT_PRO_PERCENT,ifnull(pppn.PM_PRO_PLAN_NODE_PID,0) as PM_PRO_PLAN_NODE_PID,pppn.PLAN_COMPL_DATE,pppn.ACTUAL_COMPL_DATE,pppn.SHOW_IN_EARLY_PROC,pppn.SHOW_IN_PRJ_OVERVIEW,pppn.PROGRESS_STATUS_ID,pppn.PROGRESS_RISK_TYPE_ID,pppn.CHIEF_DEPT_ID,pppn.CHIEF_USER_ID,pppn.START_DAY,pppn.SEQ_NO \n" +
                        "from PM_PRO_PLAN_NODE pppn left join PM_PRO_PLAN ppp on pppn.PM_PRO_PLAN_ID = ppp.ID where ppp.PM_PRJ_ID=?", pmPrjId);
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
        } catch (Exception e) {
            ExtJarHelper.returnValue.set(null);
        }
        ExtJarHelper.returnValue.set(null);

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
        planInfo.code = JdbcMapUtil.getString(dataMap, "CODE");
        planInfo.name = JdbcMapUtil.getString(dataMap, "NAME");
        planInfo.remark = JdbcMapUtil.getString(dataMap, "REMARK");
        planInfo.planStartDate = JdbcMapUtil.getString(dataMap, "PLAN_START_DATE");
        planInfo.planComplDate = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
        planInfo.planTotalDays = JdbcMapUtil.getInt(dataMap, ("PLAN_TOTAL_DAYS"));
        planInfo.planCarryDays = JdbcMapUtil.getInt(dataMap, "PLAN_CARRY_DAYS");
        planInfo.planCurrentProPercent = JdbcMapUtil.getDouble(dataMap, "PLAN_CURRENT_PRO_PERCENT");
        planInfo.actualStartDate = JdbcMapUtil.getString(dataMap, "ACTUAL_START_DATE");
        planInfo.actualComplDate = JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE");
        planInfo.actualTotalDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_TOTAL_DAYS");
        planInfo.actualCarryDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_CARRY_DAYS");
        planInfo.actualCurrentProPercent = JdbcMapUtil.getDouble(dataMap, "ACTUAL_CURRENT_PRO_PERCENT");
        String sql = "select * from gr_set_value where id=?";
        if (!Objects.isNull(dataMap.get("PROGRESS_STATUS_ID"))) {
            Map<String, Object> statusObj = jdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_STATUS_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(statusObj.get("ID"));
            idCodeName.code = String.valueOf(statusObj.get("CODE"));
            idCodeName.name = String.valueOf(statusObj.get("NAME"));
            planInfo.progressStatus = idCodeName;
        }
        if (!Objects.isNull(dataMap.get("PROGRESS_RISK_TYPE_ID"))) {
            Map<String, Object> riskObj = jdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_TYPE_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(riskObj.get("ID"));
            idCodeName.code = String.valueOf(riskObj.get("CODE"));
            idCodeName.name = String.valueOf(riskObj.get("NAME"));
            planInfo.progressRiskType = idCodeName;
        }

        planInfo.progressRiskRemark = JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_REMARK");
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
        nodeInfo.id = JdbcMapUtil.getString(dataMap, "ID");
        nodeInfo.pid = JdbcMapUtil.getString(dataMap, "PM_PRO_PLAN_NODE_PID");
        nodeInfo.code = JdbcMapUtil.getString(dataMap, "CODE");
        nodeInfo.name = JdbcMapUtil.getString(dataMap, "NAME");
        nodeInfo.remark = JdbcMapUtil.getString(dataMap, "REMARK");
        nodeInfo.planStartDate = JdbcMapUtil.getString(dataMap, "PLAN_START_DATE");
        nodeInfo.planComplDate = JdbcMapUtil.getString(dataMap, "PLAN_COMPL_DATE");
        nodeInfo.planTotalDays = JdbcMapUtil.getInt(dataMap, "PLAN_TOTAL_DAYS");
        nodeInfo.planCarryDays = JdbcMapUtil.getInt(dataMap, "PLAN_CARRY_DAYS");
        nodeInfo.planCurrentProPercent = JdbcMapUtil.getDouble(dataMap, "PLAN_CURRENT_PRO_PERCENT");
        nodeInfo.actualStartDate = JdbcMapUtil.getString(dataMap, "ACTUAL_START_DATE");
        nodeInfo.actualComplDate = JdbcMapUtil.getString(dataMap, "ACTUAL_COMPL_DATE");
        nodeInfo.actualTotalDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_TOTAL_DAYS");
        nodeInfo.actualCarryDays = JdbcMapUtil.getInt(dataMap, "ACTUAL_CARRY_DAYS");
        nodeInfo.actualCurrentProPercent = JdbcMapUtil.getDouble(dataMap, "ACTUAL_CURRENT_PRO_PERCENT");
        String sql = "select * from gr_set_value where id=?";
        if (!Objects.isNull(dataMap.get("PROGRESS_STATUS_ID"))) {
            Map<String, Object> statusObj = jdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_STATUS_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(statusObj.get("ID"));
            idCodeName.code = String.valueOf(statusObj.get("CODE"));
            idCodeName.name = String.valueOf(statusObj.get("NAME"));
            nodeInfo.progressStatus = idCodeName;
        }
        if (!Objects.isNull(dataMap.get("PROGRESS_RISK_TYPE_ID"))) {
            Map<String, Object> riskObj = jdbcTemplate.queryForMap(sql, JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_TYPE_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(riskObj.get("ID"));
            idCodeName.code = String.valueOf(riskObj.get("CODE"));
            idCodeName.name = String.valueOf(riskObj.get("NAME"));
            nodeInfo.progressRiskType = idCodeName;
        }
        nodeInfo.progressRiskRemark = JdbcMapUtil.getString(dataMap, "PROGRESS_RISK_REMARK");
        if (!Objects.isNull(dataMap.get("CHIEF_DEPT_ID"))) {
            Map<String, Object> deptObj = jdbcTemplate.queryForMap("select * from hr_dept where id =?", JdbcMapUtil.getString(dataMap, "CHIEF_DEPT_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(deptObj.get("ID"));
            idCodeName.code = String.valueOf(deptObj.get("CODE"));
            idCodeName.name = String.valueOf(deptObj.get("NAME"));
            nodeInfo.chiefDept = idCodeName;
        }

        if (!Objects.isNull(dataMap.get("CHIEF_USER_ID"))) {
            Map<String, Object> userObj = jdbcTemplate.queryForMap("select * from ad_user where id =?", JdbcMapUtil.getString(dataMap, "CHIEF_USER_ID"));
            IdCodeName idCodeName = new IdCodeName();
            idCodeName.id = String.valueOf(userObj.get("ID"));
            idCodeName.code = String.valueOf(userObj.get("CODE"));
            idCodeName.name = String.valueOf(userObj.get("NAME"));
            nodeInfo.chiefUser = idCodeName;
        }
        nodeInfo.showInPrjOverview = JdbcMapUtil.getString(dataMap, "SHOW_IN_PRJ_OVERVIEW");
        return nodeInfo;
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
        //ad_user
        public IdCodeName chiefDept;
        /**
         * 责任人
         */
        //
        public IdCodeName chiefUser;
        /**
         * 是否显示首页
         */
        //SHOW_IN_PRJ_OVERVIEW
        public String showInPrjOverview;
        /**
         * 子节点
         */
        public List<PrjProPlanNodeInfo> children;
    }
}
