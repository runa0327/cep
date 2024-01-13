package com.bid.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 施工计划搜集的扩展。
 */
public class ConPlanImpExt {
    public void check() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        if (!SharedUtil.isEmptyList(entityRecordList)) {
            for (EntityRecord entityRecord : entityRecordList) {
                check(entityRecord);
            }
        }
    }

    private void check(EntityRecord entityRecord) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        Object pm_prj_id = entityRecord.valueMap.get("PM_PRJ_ID");
        Map<String, Object> pm_prj = myJdbcTemplate.queryForMap("select * from pm_prj t where t.id=?", pm_prj_id);
        Object project_type_id = pm_prj.get("PROJECT_TYPE_ID");

        ConPlanType conPlanType = getConPlanType(project_type_id);
        List<Map<String, Object>> nodeTypeList = getNodeTypeList(conPlanType);
        List<Map<String, Object>> milestoneNodeList = getMilestoneNodeList(conPlanType);
        List<Map<String, Object>> lvl1NodeList = getLvl1NodeList(conPlanType);

        Object con_plan_node_type_id = entityRecord.valueMap.get("CON_PLAN_NODE_TYPE_ID");
        Object con_plan_node_by_select = entityRecord.valueMap.get("CON_PLAN_NODE_BY_SELECT");
        Object con_plan_node_by_input = entityRecord.valueMap.get("CON_PLAN_NODE_BY_INPUT");

        List<Object> nodeTypeIdList = nodeTypeList.stream().map(item -> item.get("ID")).collect(Collectors.toList());

        Map<String, Object> nodeType = nodeTypeList.stream().filter(item -> item.get("ID").equals(con_plan_node_type_id)).collect(Collectors.toList()).get(0);

        String nodeTypeName = nodeType.get("NAME").toString();
        boolean isMilestone = nodeTypeName.contains("里程碑");
        boolean isLvl1 = nodeTypeName.contains("一级");
        boolean isSelect = nodeTypeName.contains("选择");
        if (isSelect) {
            // 选择：

            if (SharedUtil.isEmptyObject(con_plan_node_by_select)) {
                throw new BaseException("必须“选择”！");
            } else if (!SharedUtil.isEmptyObject(con_plan_node_by_input)) {
                throw new BaseException("不能“输入”！");
            }

            if (isMilestone) {
                if (!milestoneNodeList.stream().map(item -> item.get("ID")).collect(Collectors.toList()).contains(con_plan_node_by_select)) {
                    throw new BaseException("请重新“选择”正确的节点！");
                }
            }

            if (isLvl1) {
                if (!lvl1NodeList.stream().map(item -> item.get("ID")).collect(Collectors.toList()).contains(con_plan_node_by_select)) {
                    throw new BaseException("请重新“选择”正确的节点！");
                }
            }
        } else {
            // 输入：
            if (!SharedUtil.isEmptyObject(con_plan_node_by_select)) {
                throw new BaseException("不能“选择”！");
            } else if (SharedUtil.isEmptyObject(con_plan_node_by_input)) {
                throw new BaseException("必须“输入”！");
            }
        }
    }

    private List<Map<String, Object>> getNodeTypeList(ConPlanType conPlanType) {
        String setCode = null;
        if (conPlanType == ConPlanType.house) {
            setCode = "con_plan_node_type_house";
        } else if (conPlanType == ConPlanType.civic) {
            setCode = "con_plan_node_type_civic";
        } else if (conPlanType == ConPlanType.scape) {
            setCode = "con_plan_node_type_scape";
        } else if (conPlanType == ConPlanType.other) {
            setCode = "con_plan_node_type_other";
        }

        return getGrSetValueList(setCode);
    }

    private List<Map<String, Object>> getMilestoneNodeList(ConPlanType conPlanType) {
        String setCode = null;
        if (conPlanType == ConPlanType.house) {
            setCode = "con_plan_milestone_node_house";
        } else if (conPlanType == ConPlanType.civic) {
            setCode = "con_plan_milestone_node_civic";
        } else if (conPlanType == ConPlanType.scape) {
            setCode = "con_plan_milestone_node_scape";
        } else if (conPlanType == ConPlanType.other) {
            setCode = "con_plan_milestone_node_other";
        }

        return getGrSetValueList(setCode);
    }

    private List<Map<String, Object>> getLvl1NodeList(ConPlanType conPlanType) {
        String setCode = null;
        if (conPlanType == ConPlanType.house) {
            setCode = "con_plan_lvl_1_node_house";
        } else if (conPlanType == ConPlanType.civic) {
            setCode = "con_plan_lvl_1_node_civic";
        } else if (conPlanType == ConPlanType.scape) {
            setCode = "con_plan_lvl_1_node_scape";
        } else if (conPlanType == ConPlanType.other) {
            setCode = "con_plan_lvl_1_node_other";
        }

        return getGrSetValueList(setCode);
    }

    private static List<Map<String, Object>> getGrSetValueList(String setCode) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select v.* from gr_set s join gr_set_value v on s.id=v.GR_SET_ID and s.code=? order by v.SEQ_NO", setCode);
        return list;
    }

    /**
     * 根据项目类型ID获取施工计划类型
     *
     * @param project_type_id
     * @return
     */
    private ConPlanType getConPlanType(Object project_type_id) {
        if (Arrays.asList("0099799190825080689").contains(project_type_id)) {
            // 房建：
            return ConPlanType.house;
        } else if (Arrays.asList("0099799190825080690", "0099799190825080739").contains(project_type_id)) {
            // 市政道路：
            return ConPlanType.civic;
        } else if (Arrays.asList("0099799190825080692").contains(project_type_id)) {
            // 园林景观：
            return ConPlanType.scape;
        } else {
            // 其它：
            return ConPlanType.other;
        }
    }

    /**
     * 施工计划类型
     */
    enum ConPlanType {
        /**
         * 房建
         */
        house,
        /**
         * 市政道路
         */
        civic,
        /**
         * 园林景观
         */
        scape,
        /**
         * 其它
         */
        other
    }
}
