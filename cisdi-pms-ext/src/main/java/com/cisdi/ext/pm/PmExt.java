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

        int i = PmPrj.updateById(pmPrj.id, keyValueHashMap, null, null);
        int i2 = PmPrj.updateByIds(Lists.newArrayList(pmPrj.id, "id2"), keyValueHashMap, null, null);
        Where where = new Where();
        where.eq(PmPrj.Cols.ID, pmPrj.id).contain(PmPrj.Cols.NAME, "5").or().in(PmPrj.Cols.CODE, "a", "b", "c").begin().sql("exists select 1 from xxxx x where x.pm_Prj_id=t.id").or().eq(PmPrj.Cols.NAME, "3").end();
        PmPrj.updateByWhere(where, keyValueHashMap, null, null);

        pmPrj.deleteById();

        int i3 = PmPrj.deleteById(pmPrj.id);
        int i4 = PmPrj.deleteByIds(Lists.newArrayList(pmPrj.id, "id2"));
        Where where1 = new Where();
        PmPrj.deleteByWhere(where1);

        PmPrj pmPrj1 = PmPrj.selectById("id1", null, null);
        List<PmPrj> list = PmPrj.selectByIds(Lists.newArrayList(pmPrj.id, "id2"), null, null);
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
}
