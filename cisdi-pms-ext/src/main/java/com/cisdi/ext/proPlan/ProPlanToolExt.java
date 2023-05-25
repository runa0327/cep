package com.cisdi.ext.proPlan;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.PrjPlanUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProPlanToolExt
 * @package com.cisdi.ext.proPlan
 * @description 项目进度计划工具
 * @date 2023/5/25
 */
public class ProPlanToolExt {


    /**
     * 刷新项目的模板
     */
    public void refreshPmPlan() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputData inputData = JsonUtil.fromJson(json, InputData.class);
        StringBuilder sb = new StringBuilder();
        sb.append("select * from pm_prj where status = 'ap' ");
        List<Map<String, Object>> list = new ArrayList<>();
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        if ("1".equals(inputData.type)) {
            if (Strings.isNotEmpty(inputData.sources)) {
                sb.append(" and INVESTMENT_SOURCE_ID in (:sources)");
                queryParams.put("sources", Arrays.asList(inputData.sources.split(",")));
            }
            if (Strings.isNotEmpty(inputData.minAmount) && Strings.isNotEmpty(inputData.maxAmount)) {
                sb.append(" and ESTIMATED_TOTAL_INVEST between :minAmount and :maxAmount ");
                queryParams.put("minAmount", inputData.minAmount);
                queryParams.put("maxAmount", inputData.maxAmount);
            }
            if (Strings.isNotEmpty(inputData.projectTypes)) {
                sb.append(" and PROJECT_TYPE_ID in (:projectTypes)");
                queryParams.put("projectTypes", Arrays.asList(inputData.projectTypes.split(",")));
            }
            if (Strings.isNotEmpty(inputData.tendModes)) {
                sb.append(" and TENDER_MODE_ID in (:tendModes)");
                queryParams.put("tendModes", Arrays.asList(inputData.tendModes.split(",")));
            }
            list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), queryParams);
        } else if ("2".equals(inputData.type)) {
            if (Strings.isNotEmpty(inputData.projectIds)) {
                sb.append(" and id in (:projectIds)");
                queryParams.put("projectIds", Arrays.asList(inputData.projectIds.split(",")));
            }
            list = myNamedParameterJdbcTemplate.queryForList(sb.toString(), queryParams);
        }

        if (!CollectionUtils.isEmpty(list)) {
            list.forEach(item -> {
                //刷新项目全景计划
                PrjPlanUtil.createPlan(JdbcMapUtil.getString(item, "ID"), inputData.templateId);
                //刷新项目全景计划时间
                PrjPlanUtil.refreshProPlanTime(JdbcMapUtil.getString(item, "ID"), inputData.paramDate);
                //通过流程反刷新项目全景计划状态和完成时间
                PrjPlanUtil.updatePrjProPlanByProcInst(JdbcMapUtil.getString(item, "ID"));
            });
        }
    }


    public static class InputData {
        //资金来源(多选)
        public String sources;
        //项目类型(多选)
        public String projectTypes;
        //招标模式(多选)
        public String tendModes;
        //模板ID(单选)
        public String templateId;
        //时间
        public Date paramDate;


        //金额范围最小值
        public String minAmount;
        //金额范围最大值
        public String maxAmount;

        //项目ID 多选
        public String projectIds;

        //方式一： 1  方式二 ： 2
        public String type;


    }


    /**
     * 获取模板
     */
    public void templateList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_pro_plan where IS_TEMPLATE=1 and status='ap'");
        List<ObjInfo> objInfoList = list.stream().map(p -> {
            ObjInfo info = new ObjInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.name = JdbcMapUtil.getString(p, "NAME");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(objInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.objInfoList = objInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class OutSide {
        public List<ObjInfo> objInfoList;
    }

    public static class ObjInfo {
        public String id;
        public String name;
    }

}
