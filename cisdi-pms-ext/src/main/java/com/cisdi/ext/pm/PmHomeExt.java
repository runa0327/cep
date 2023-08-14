package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmHomeExt
 * @package com.cisdi.ext.pm
 * @description
 * @date 2023/8/10
 */
public class PmHomeExt {


    /**
     * 项目详情
     */
    public void proViewInfo() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pm.id as ID,  PRJ_CODE as no, pm.`NAME`,g1.`NAME` as type,g2.`NAME` as source,PRJ_REPLY_NO,PRJ_REPLY_DATE,pp.`NAME` ownner ,g3.`NAME` as mode ,FLOOR_AREA,\n" +
                "g4.`NAME` as tender,g5.`NAME` as location ,pm.PRJ_SITUATION as des,PLAN_START_TIME,PLAN_END_TIME,ACTUAL_START_TIME,ACTUAL_END_TIME, \n" +
                "g6.`NAME` as phase,p1.`NAME` as js, p2.`NAME` as kc, p3.`NAME` as sj, p4.`NAME` as sg, p5.`NAME` as jl,p6.`NAME` as zjunit  from pm_prj pm\n" +
                "left join gr_set_value g1 on g1.id = pm.PROJECT_TYPE_ID\n" +
                "left join gr_set_value g2 on g2.id = pm.INVESTMENT_SOURCE_ID\n" +
                "left join pm_party pp on pp.id = pm.CUSTOMER_UNIT\n" +
                "left join gr_set_value g3 on g3.id = pm.PRJ_MANAGE_MODE_ID\n" +
                "left join gr_set_value g4 on g4.id = pm.TENDER_MODE_ID\n" +
                "left join gr_set_value g5 on g5.id = pm.BASE_LOCATION_ID\n" +
                "left join gr_set_value g6 on g6.id = pm.TRANSITION_PHASE_ID\n" +
                "left join pm_party p1 on p1.id = pm.BUILDER_UNIT\n" +
                "left join pm_party p2 on p2.id = pm.SURVEYOR_UNIT\n" +
                "left join pm_party p3 on p3.id = pm.DESIGNER_UNIT\n" +
                "left join pm_party p4 on p4.id = pm.CONSTRUCTOR_UNIT\n" +
                "left join pm_party p5 on p5.id = pm.SUPERVISOR_UNIT\n" +
                "left join pm_party p6 on p6.id = pm.CONSULTER_UNIT\n" +
                "where pm.id=?", map.get("projectId"));
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> mapData = list.get(0);
            Info info = new Info();
            info.id = JdbcMapUtil.getString(mapData, "ID");
            info.no = JdbcMapUtil.getString(mapData, "no");
            info.name = JdbcMapUtil.getString(mapData, "NAME");
            info.type = JdbcMapUtil.getString(mapData, "type");
            info.source = JdbcMapUtil.getString(mapData, "source");
            info.pfNo = JdbcMapUtil.getString(mapData, "PRJ_REPLY_NO");
            info.pfTime = JdbcMapUtil.getString(mapData, "PRJ_REPLY_DATE");
            info.owner = JdbcMapUtil.getString(mapData, "ownner");
            info.mode = JdbcMapUtil.getString(mapData, "mode");
            info.area = JdbcMapUtil.getString(mapData, "FLOOR_AREA");
            info.tender = JdbcMapUtil.getString(mapData, "tender");
            info.location = JdbcMapUtil.getString(mapData, "location");
            info.des = JdbcMapUtil.getString(mapData, "des");
            info.planBeginTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "PLAN_START_TIME"));
            info.planEndTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "PLAN_END_TIME"));
            info.actualBeginTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "ACTUAL_START_TIME"));
            info.actualEndTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "ACTUAL_END_TIME"));
            info.imageProcess = getImageProcess(JdbcMapUtil.getString(map,"projectId"));
            info.step = JdbcMapUtil.getString(mapData, "phase");
            info.jsUnit = JdbcMapUtil.getString(mapData, "js");
            info.kcUnit = JdbcMapUtil.getString(mapData, "kc");
            info.sjUnit = JdbcMapUtil.getString(mapData, "sj");
            info.sgUnit = JdbcMapUtil.getString(mapData, "sg");
            info.jlUnit = JdbcMapUtil.getString(mapData, "jl");
            info.zjUnit = JdbcMapUtil.getString(mapData, "zj");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(info), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    /**
     * 项目编辑页详情
     */
    public void projectView() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pm_prj where id=?", map.get("projectId"));
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> mapData = list.get(0);
            Info info = new Info();
            info.id = JdbcMapUtil.getString(mapData, "ID");
            info.no = JdbcMapUtil.getString(mapData, "PRJ_CODE");
            info.name = JdbcMapUtil.getString(mapData, "NAME");
            info.type = JdbcMapUtil.getString(mapData, "PROJECT_TYPE_ID");
            info.source = JdbcMapUtil.getString(mapData, "INVESTMENT_SOURCE_ID");
            info.pfNo = JdbcMapUtil.getString(mapData, "PRJ_REPLY_NO");
            info.pfTime = JdbcMapUtil.getString(mapData, "PRJ_REPLY_DATE");
            info.owner = JdbcMapUtil.getString(mapData, "CUSTOMER_UNIT");
            info.mode = JdbcMapUtil.getString(mapData, "PRJ_MANAGE_MODE_ID");
            info.area = JdbcMapUtil.getString(mapData, "FLOOR_AREA");
            info.tender = JdbcMapUtil.getString(mapData, "TENDER_MODE_ID");
            info.location = JdbcMapUtil.getString(mapData, "BASE_LOCATION_ID");
            info.des = JdbcMapUtil.getString(mapData, "PRJ_SITUATION");
            info.planBeginTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "PLAN_START_TIME"));
            info.planEndTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "PLAN_END_TIME"));
            info.actualBeginTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "ACTUAL_START_TIME"));
            info.actualEndTime = StringUtil.withOutT(JdbcMapUtil.getString(mapData, "ACTUAL_END_TIME"));
            info.imageProcess = getImageProcess(JdbcMapUtil.getString(map,"projectId"));
            info.step = JdbcMapUtil.getString(mapData, "TRANSITION_PHASE_ID");
            info.jsUnit = JdbcMapUtil.getString(mapData, "BUILDER_UNIT");
            info.kcUnit = JdbcMapUtil.getString(mapData, "SURVEYOR_UNIT");
            info.sjUnit = JdbcMapUtil.getString(mapData, "DESIGNER_UNIT");
            info.sgUnit = JdbcMapUtil.getString(mapData, "CONSTRUCTOR_UNIT");
            info.jlUnit = JdbcMapUtil.getString(mapData, "SUPERVISOR_UNIT");
            info.zjUnit = JdbcMapUtil.getString(mapData, "CONSULTER_UNIT");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(info), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }

    /**
     * 合作单位
     */
    public void unitList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String type = JdbcMapUtil.getString(map, "type");
        StringBuilder sb = new StringBuilder();
        sb.append("select * from PM_PARTY where status='ap' ");
        switch (type) {
            case "1":
                //业主单位
                sb.append(" and IS_CUSTOMER='1' ");
                break;
            case "2":
                //建设单位
                sb.append(" and IS_BUILDER='1' ");
                break;
            case "3":
                //勘察单位
                sb.append(" and IS_SURVEYOR='1' ");
                break;
            case "4":
                //设计单位
                sb.append(" and IS_DESIGNER='1' ");
                break;
            case "5":
                //施工单位
                sb.append(" and IS_CONSTRUCTOR='1' ");
                break;
            case "6":
                //监理单位
                sb.append(" and IS_SUPERVISOR='1' ");
                break;
            case "7":
                //检查单位
                sb.append(" and IS_CHECKER='1' ");
                break;
            case "8":
                //检查单位
                sb.append(" and IS_CONSULTER='1' ");
                break;
            case "9":
                //评审单位
                sb.append(" and IS_REVIEW='1' ");
                break;
            default:
        }
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        if (!CollectionUtils.isEmpty(list)) {
            List<Party> partyList = list.stream().map(p -> {
                Party party = new Party();
                party.id = JdbcMapUtil.getString(p, "ID");
                party.name = JdbcMapUtil.getString(p, "NAME");
                return party;
            }).collect(Collectors.toList());
            OutSide resData = new OutSide();
            resData.partyList = partyList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    /**
     * 项目信息修改
     */
    public void projectEdit() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        Info info = JsonUtil.fromJson(json, Info.class);
        StringBuilder sb = new StringBuilder();
        sb.append("update pm_prj set LAST_MODI_DT =NOW() ");
        if (Strings.isNotEmpty(info.no)) {
            sb.append(" ,PRJ_CODE='").append(info.no).append("'");
        }
        if (Strings.isNotEmpty(info.name)) {
            sb.append(" ,NAME='").append(info.name).append("'");
        }
        if (Strings.isNotEmpty(info.type)) {
            sb.append(" ,PROJECT_TYPE_ID='").append(info.type).append("'");
        }
        if (Strings.isNotEmpty(info.source)) {
            sb.append(" ,INVESTMENT_SOURCE_ID='").append(info.source).append("'");
        }
        if (Strings.isNotEmpty(info.pfNo)) {
            sb.append(" ,PRJ_REPLY_NO='").append(info.pfNo).append("'");
        }
        if (Strings.isNotEmpty(info.pfTime)) {
            sb.append(" ,PRJ_REPLY_DATE='").append(info.pfTime).append("'");
        }
        if (Strings.isNotEmpty(info.owner)) {
            sb.append(" ,CUSTOMER_UNIT='").append(info.owner).append("'");
        }
        if (Strings.isNotEmpty(info.mode)) {
            sb.append(" ,PRJ_MANAGE_MODE_ID='").append(info.mode).append("'");
        }
        if (Strings.isNotEmpty(info.area)) {
            sb.append(" ,FLOOR_AREA='").append(info.area).append("'");
        }
        if (Strings.isNotEmpty(info.tender)) {
            sb.append(" ,TENDER_MODE_ID='").append(info.tender).append("'");
        }
        if (Strings.isNotEmpty(info.location)) {
            sb.append(" ,BASE_LOCATION_ID='").append(info.location).append("'");
        }
        if (Strings.isNotEmpty(info.des)) {
            sb.append(" ,PRJ_SITUATION='").append(info.des).append("'");
        }
        if (Strings.isNotEmpty(info.planBeginTime)) {
            sb.append(" ,PLAN_START_TIME='").append(info.planBeginTime).append("'");
        }
        if (Strings.isNotEmpty(info.planEndTime)) {
            sb.append(" ,PLAN_END_TIME='").append(info.planEndTime).append("'");
        }
        if (Strings.isNotEmpty(info.actualBeginTime)) {
            sb.append(" ,ACTUAL_START_TIME='").append(info.actualBeginTime).append("'");
        }
        if (Strings.isNotEmpty(info.actualEndTime)) {
            sb.append(" ,ACTUAL_END_TIME='").append(info.actualEndTime).append("'");
        }
        if (Strings.isNotEmpty(info.step)) {
            sb.append(" ,TRANSITION_PHASE_ID='").append(info.step).append("'");
        }
        if (Strings.isNotEmpty(info.jsUnit)) {
            sb.append(" ,BUILDER_UNIT='").append(info.jsUnit).append("'");
        }
        if (Strings.isNotEmpty(info.kcUnit)) {
            sb.append(" ,SURVEYOR_UNIT='").append(info.kcUnit).append("'");
        }
        if (Strings.isNotEmpty(info.sjUnit)) {
            sb.append(" ,DESIGNER_UNIT='").append(info.sjUnit).append("'");
        }
        if (Strings.isNotEmpty(info.sgUnit)) {
            sb.append(" ,CONSTRUCTOR_UNIT='").append(info.sgUnit).append("'");
        }
        if (Strings.isNotEmpty(info.jlUnit)) {
            sb.append(" ,SUPERVISOR_UNIT='").append(info.jlUnit).append("'");
        }
        if (Strings.isNotEmpty(info.zjUnit)) {
            sb.append(" ,CONSULTER_UNIT='").append(info.zjUnit).append("'");
        }
        if (Strings.isNotEmpty(info.pics)) {
            sb.append(" ,PRJ_IMG='").append(info.pics).append("'");
        }
        sb.append(" where id='").append(info.id).append("'");
        myJdbcTemplate.update(sb.toString());
    }


    public static class Info {
        public String id;
        public String no;
        public String name;
        public String type;
        public String source;
        public String pfNo;
        public String pfTime;
        public String owner;
        public String mode;
        public String area;
        public String tender;
        public String location;
        public String des;
        public String planBeginTime;
        public String planEndTime;
        public String actualBeginTime;
        public String actualEndTime;
        public String imageProcess;
        public String step;
        public String jsUnit;
        public String kcUnit;
        public String sjUnit;
        public String sgUnit;
        public String jlUnit;
        public String zjUnit;

        public String pics;
    }

    public static class OutSide {
        public List<Party> partyList;
    }

    public static class Party {
        public String id;
        public String name;
    }

    /**
     * 查询形象进度
     * @param projectId
     * @return
     */
    private String getImageProcess(String projectId) {
        String imageProcess = "0";
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select round(ifnull(VISUAL_PROGRESS,0),2) as VISUAL_PROGRESS from PM_PROGRESS_WEEKLY_PRJ_DETAIL where PM_PRJ_ID=? and FILE_ID_ONE is not null order by LAST_MODI_DT desc", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> mapData = list.get(0);
            imageProcess = JdbcMapUtil.getString(mapData, "VISUAL_PROGRESS");
        }
        return imageProcess;
    }

}
