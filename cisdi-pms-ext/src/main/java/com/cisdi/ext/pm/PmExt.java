package com.cisdi.ext.pm;

import com.cisdi.ext.model.PmPrj;
import com.cisdi.ext.model.TestStu;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.collect.Lists;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class PmExt {
    /**
     * 克隆项目。
     */
    public void clonePmPrj() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            clonePmPrj(entityRecord);
        }
    }

    private void clonePmPrj(EntityRecord entityRecord) {
        String csCommId = entityRecord.csCommId;
        PmPrj oldPmPrj = PmPrj.selectById(csCommId, null, null);
        PmPrj newPmPrj = PmPrj.insertData();
        PmPrj.copyCols(oldPmPrj, newPmPrj, null, Lists.newArrayList(PmPrj.Cols.ID));
        newPmPrj.updateById(null, null, false);

        TestStu oldTestStu = TestStu.selectByWhere(null, null, null).get(0);
        TestStu newTestStu = TestStu.insertData();
        TestStu.copyCols(oldTestStu, newTestStu, null, Lists.newArrayList(TestStu.Cols.ID));
        oldTestStu.deleteById();
        newTestStu.setName(newTestStu.getName() + "-新");
        newTestStu.updateById(null, null, true);

        OrmHelper.copyCols(newTestStu, newPmPrj, Lists.newArrayList("CODE", "NAME"), null);

        PmPrj pmPrj = PmPrj.insertData();
        pmPrj.setCode("123").setName("456").setBuildYears(new BigDecimal(50)).updateById(Lists.newArrayList(PmPrj.Cols.CODE, PmPrj.Cols.NAME), Lists.newArrayList(PmPrj.Cols.CODE), true);

        HashMap<String, Object> keyValueHashMap = new HashMap<>();
        keyValueHashMap.put(PmPrj.Cols.CODE, "123");
        keyValueHashMap.put(PmPrj.Cols.NAME, "456");
        keyValueHashMap.put(PmPrj.Cols.BUILD_YEARS, 50d);

        int i = PmPrj.updateById(pmPrj.getId(), keyValueHashMap, null, null);
        int i2 = PmPrj.updateByIds(Lists.newArrayList(pmPrj.getId(), "id2"), keyValueHashMap, null, null);
        Where where = new Where();
        where.eq(PmPrj.Cols.ID, pmPrj.getId()).contain(PmPrj.Cols.NAME, "5").or().in(PmPrj.Cols.CODE, "a", "b", "c").begin().sql("exists select 1 from xxxx x where x.pm_Prj_id=t.id").or().eq(PmPrj.Cols.NAME, "3").end();
        PmPrj.updateByWhere(where, keyValueHashMap, null, null);

        pmPrj.deleteById();

        int i3 = PmPrj.deleteById(pmPrj.getId());
        int i4 = PmPrj.deleteByIds(Lists.newArrayList(pmPrj.getId(), "id2"));
        Where where1 = new Where();
        PmPrj.deleteByWhere(where1);

        PmPrj pmPrj1 = PmPrj.selectById("id1", null, null);
        List<PmPrj> list = PmPrj.selectByIds(Lists.newArrayList(pmPrj.getId(), "id2"), null, null);
        Where where2 = new Where();
        List<PmPrj> pmPrjs = PmPrj.selectByWhere(where2, null, Lists.newArrayList(PmPrj.Cols.CODE));

        PmPrj pmPrj2 = PmPrj.selectById("5", null, null);
        pmPrj2.setId("6").updateById(null, null, false);

        HashMap<String, Object> kvHashMap = new HashMap<>();
        kvHashMap.put(PmPrj.Cols.ID, "6");
        PmPrj.updateById("5", kvHashMap, null, null);

        Integer i5 = Crud.from(PmPrj.ENT_CODE).where().eq(PmPrj.Cols.ID, "5").update().set(PmPrj.Cols.ID, "6").exec();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("UPDATE PM_PRJ T SET T.ID=?,T.NAME=?,T.CODE=? WHERE T.ID=?", "6", "5");


    }

    /**
     * 项目入库
     */
    public void prjInStorage() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String name = param.name;
        int pageSize = param.pageSize;
        int pageIndex = param.pageIndex;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
        sb.append("select CODE,NAME,REMARK,PRJ_MANAGE_MODE_ID,BASE_LOCATION_ID,FLOOR_AREA,PROJECT_TYPE_ID,CON_SCALE_TYPE_ID,CON_SCALE_QTY,CON_SCALE_UOM_ID,PRJ_SITUATION,INVESTMENT_SOURCE_ID,PM_PRJ_REQ_ID,CON_SCALE_QTY2,CUSTOMER_UNIT,PRJ_REPLY_DATE,PRJ_REPLY_NO,PRJ_REPLY_FILE,PRJ_EARLY_USER_ID,PRJ_DESIGN_USER_ID,PRJ_COST_USER_ID,BASE_EPC_ID,IN_PROVINCE_REP,IN_COUNTRY_REP,PROJECT_PHASE_ID,TRANSITION_PHASE_ID,PRJ_IMG,BUILD_YEARS,BUILDER_UNIT,SURVEYOR_UNIT,DESIGNER_UNIT,CONSTRUCTOR_UNIT,SUPERVISOR_UNIT,CHECKER_UNIT,CONSULTER_UNIT,CPMS_UUID,CPMS_ID,CPMS_CONSTRUCTION_SITE,PRJ_CODE,BUILDING_AREA from pm_prj where 1=1 ");
        if (Strings.isNotEmpty(name)) {
            sb.append(" and `NAME` like '%").append(name).append("%'");
        }
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<Project> records = list.stream().map(this::convertProject).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(records)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.list = records;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    private Project convertProject(Map<String, Object> data) {
        Project project = new Project();
        project.id = JdbcMapUtil.getString(data, "ID");
        project.name = JdbcMapUtil.getString(data, "NAME");
        project.customerUnit = JdbcMapUtil.getString(data, "CUSTOMER_UNIT");
        project.type = JdbcMapUtil.getString(data, "PROJECT_TYPE");
        project.baseLocation = JdbcMapUtil.getString(data, "BASE_LOCATION");
//        project.total= JdbcMapUtil.getString(data,"");
        project.total = "0";
        project.inProvinceRep = JdbcMapUtil.getString(data, "IN_PROVINCE_REP");
        project.inCountryRep = JdbcMapUtil.getString(data, "IN_COUNTRY_REP");

        return project;
    }

    private static class Project {
        public String id;
        public String name;
        // 业主单位
        public String customerUnit;
        public String type;
        // 建设地点
        public String baseLocation;
        public String total;
        // 是否入省库
        public String inProvinceRep;
        // 是否入国库
        public String inCountryRep;
    }

    public static class OutSide {
        public Integer total;

        public List<Project> list;

        public List<ProjectInfo> projectInfoList;
    }

    private static class RequestParam {
        public Integer pageSize;
        public Integer pageIndex;
        public String name;

    }

    /**
     * 修改项目封面图
     */
    public void modifyPmImg() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        String imgId = String.valueOf(map.get("imgId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("update pm_prj set PRJ_IMG=? where id=?", imgId, projectId);
    }

    /**
     * 项目概况，建设单位，勘察单位，设计单位，监理单位，施工单位
     */
    public void prjCompany() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select p1.`NAME` as jsUnit,p2.`NAME` as kcUnit,p3.`NAME` as sjUnit,p4.`NAME` as jlUnit,p5.`NAME` as sgUnit from pm_prj pj \n" +
                "left join PM_PARTY p1 on pj.BUILDER_UNIT = p1.id \n" +
                "left join PM_PARTY p2 on pj.SURVEYOR_UNIT = p2.id \n" +
                "left join PM_PARTY p3 on pj.DESIGNER_UNIT = p3.id \n" +
                "left join PM_PARTY p4 on pj.SUPERVISOR_UNIT = p4.id \n" +
                "left join PM_PARTY p5 on pj.CONSTRUCTOR_UNIT = p5.id \n" +
                "where pj.id=?", projectId);
        ProjectCompany company = new ProjectCompany();
        company.jsUnit = JdbcMapUtil.getString(stringObjectMap, "jsUnit");
        company.kcUnit = JdbcMapUtil.getString(stringObjectMap, "kcUnit");
        company.sjUnit = JdbcMapUtil.getString(stringObjectMap, "sjUnit");
        company.jlUnit = JdbcMapUtil.getString(stringObjectMap, "jlUnit");
        company.sgUnit = JdbcMapUtil.getString(stringObjectMap, "sgUnit");
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(company), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    public static class ProjectCompany {
        //建设单位
        public String jsUnit;
        //勘察单位
        public String kcUnit;
        //设计单位
        public String sjUnit;
        //监理单位
        public String jlUnit;
        //施工单位
        public String sgUnit;
    }


    /**
     * 项目库查询
     */
    public void projectLibrary() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PrjRequestParam param = JsonUtil.fromJson(json, PrjRequestParam.class);
        int pageSize = param.pageSize;
        int pageIndex = param.pageIndex;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder sb = new StringBuilder();
        sb.append("select pm.PM_SEQ as num,pm.id as id,pm.`NAME` as name,pt.`NAME` as unit,gsv.`NAME` as type,pm.PM_CODE as code,ggg.`NAME` as local, \n" +
                "gsvv.`NAME` as pmode,'0' as invest,gss.`NAME` as status,\n" +
                "ppp.PLAN_START_DATE as PLAN_START_DATE, \n" +
                "ppp.ACTUAL_START_DATE as ACTUAL_START_DATE, \n" +
                "ppp.PLAN_COMPL_DATE AS PLAN_COMPL_DATE, \n" +
                "ppp.ACTUAL_COMPL_DATE AS ACTUAL_COMPL_DATE, \n" +
                "ppp.PLAN_CURRENT_PRO_PERCENT AS PLAN_CURRENT_PRO_PERCENT, \n" +
                "ppp.ACTUAL_CURRENT_PRO_PERCENT AS ACTUAL_CURRENT_PRO_PERCENT  \n" +
                "from pm_prj pm \n" +
                "left join pm_party pt on pm.CUSTOMER_UNIT = pt.id \n" +
                "left join gr_set_value gsv on gsv.id = pm.PROJECT_TYPE_ID \n" +
                "left join gr_set_value gsvv on gsvv.id = pm.PRJ_MANAGE_MODE_ID \n" +
                "left join gr_set_value gss on gss.id = pm.PROJECT_PHASE_ID \n" +
                "left join pm_pro_plan ppp on ppp.PM_PRJ_ID = pm.id \n" +
                "left join gr_set_value ggg on ggg.id = pm.BASE_LOCATION_ID "+
                "where pm.PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and pm.`STATUS`='ap' ");
        if (Strings.isNotEmpty(param.name)) {
            sb.append(" and pm.`name` like '%").append(param.name).append("%'");
        }
        if (Strings.isNotEmpty(param.code)) {
            sb.append(" and pm.PM_CODE like '%").append(param.code).append("%'");
        }
        if (Strings.isNotEmpty(param.unit)) {
            sb.append(" and pm.CUSTOMER_UNIT = '").append(param.unit).append("'");
        }
        if (Strings.isNotEmpty(param.type)) {
            sb.append(" and pm.PROJECT_TYPE_ID = '").append(param.type).append("'");
        }
        if (Strings.isNotEmpty(param.status)) {
            sb.append(" and pm.PROJECT_PHASE_ID = '").append(param.status).append("'");
        }
        if (Strings.isNotEmpty(param.phase)) {
            sb.append(" and pm.TRANSITION_PHASE_ID = '").append(param.phase).append("'");
        }
        if (Strings.isNotEmpty(param.startTime)) {
            sb.append(" and DATE_FORMAT(ppp.PLAN_START_DATE,'%Y-%m-%d') = DATE_FORMAT('").append(param.startTime).append("','%Y-%m-%d')");
        }
        String userId = ExtJarHelper.loginInfo.get().userId;
        sb.append(" and IF('").append(userId).append("' in (select ad_user_id from ad_role_user where ad_role_id = '0099250247095870406') ,1=1, ");
        sb.append(" pm.id in (select DISTINCT pm_prj_id from pm_dept WHERE STATUS = 'ap' and FIND_IN_SET('").append(userId).append("', USER_IDS )))");

        sb.append(" order by pm.PM_SEQ desc");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<ProjectInfo> projectInfoList = list.stream().map(this::convertProjectInfo).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(projectInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.projectInfoList = projectInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 初始化项目序号
     */
    public void initPrjNum(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRJ where status='ap' order by CRT_DT");
        AtomicInteger index = new AtomicInteger(0);
        for (Map<String, Object> stringObjectMap : list) {
            Crud.from("PM_PRJ").where().eq("ID", stringObjectMap.get("ID")).update().set("PM_SEQ", index.getAndIncrement()).exec();
        }
    }

    public ProjectInfo convertProjectInfo(Map<String, Object> data) {
        ProjectInfo projectInfo = new ProjectInfo();
        projectInfo.num = JdbcMapUtil.getString(data, "num");
        projectInfo.id = JdbcMapUtil.getString(data, "id");
        projectInfo.name = JdbcMapUtil.getString(data, "name");
        projectInfo.unit = JdbcMapUtil.getString(data, "unit");
        projectInfo.type = JdbcMapUtil.getString(data, "type");
        projectInfo.local = JdbcMapUtil.getString(data, "local");
        projectInfo.code = JdbcMapUtil.getString(data, "code");
        projectInfo.mode = JdbcMapUtil.getString(data, "pmode");
        projectInfo.invest = getPrjInvest(projectInfo.id);
        projectInfo.status = JdbcMapUtil.getString(data, "status");
        projectInfo.planStartDate = JdbcMapUtil.getString(data, "PLAN_START_DATE");
        projectInfo.actualStartDate = JdbcMapUtil.getString(data, "ACTUAL_START_DATE");
        projectInfo.planComplDate = JdbcMapUtil.getString(data, "PLAN_COMPL_DATE");
        projectInfo.actualComplDate = JdbcMapUtil.getString(data, "ACTUAL_COMPL_DATE");
        projectInfo.planCurrentProPercent = JdbcMapUtil.getString(data, "PLAN_CURRENT_PRO_PERCENT");
        projectInfo.actualCurrentProPercent = JdbcMapUtil.getString(data, "ACTUAL_CURRENT_PRO_PERCENT");
        return projectInfo;
    }


    public String getPrjInvest(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pie.id,round(ifnull(pie.PRJ_TOTAL_INVEST,0),2) as amt ,gsv.code from pm_invest_est pie  " +
                "left join  gr_set_value gsv on gsv.id = pie.INVEST_EST_TYPE_ID " +
                "where PM_PRJ_ID=? order by gsv.`CODE` desc limit 0,1  ", projectId);
        if (CollectionUtils.isEmpty(list)) {
            return "0";
        } else {
            return String.valueOf(list.get(0).get("amt"));
        }
    }


    public static class PrjRequestParam {
        public String name;
        public String code;
        public String unit;
        public String type;
        public String status;
        public String phase;
        public String startTime;
        public Integer pageSize;
        public Integer pageIndex;
    }

    public static class ProjectInfo {
        public String num;
        public String id;
        public String name;
        public String unit;
        public String type;
        public String local;
        public String code;
        public String mode;
        public String invest;
        public String status;
        public String planStartDate;
        public String actualStartDate;
        public String planComplDate;
        public String actualComplDate;
        public String planCurrentProPercent;
        public String actualCurrentProPercent;
    }

}
