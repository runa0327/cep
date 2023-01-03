package com.cisdi.ext.projectAreaMap;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmMap
 * @package com.cisdi.ext.projectAreaMap
 * @description 新版项目区位图
 * @date 2023/1/3
 */
public class PmMap {


    /**
     * 项目绑定
     */
    public void bandingPm() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String projectIds = String.valueOf(params.get("projectIds"));
        String longitude = String.valueOf(params.get("longitude"));
        String latitude = String.valueOf(params.get("latitude"));
        List<String> ids = Arrays.asList(projectIds.split(","));
        for (String s : ids) {
            String id = Crud.from("PM_MAP").insertData();
            Crud.from("PM_MAP").where().eq("ID", id).update().set("PM_PRJ_ID", s).set("LONGITUDE", longitude).set("LATITUDE", latitude).exec();
        }
    }

    /**
     * 地图详情
     */
    public void mapView() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String longitude = String.valueOf(params.get("longitude"));
        String latitude = String.valueOf(params.get("latitude"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pj.id,pj.name,IFNULL(v.name,'未启动') statusName from pm_prj pj " +
                " left join pm_pro_plan pp on pp.PM_PRJ_ID = pj.id  \n" +
                " left join gr_set_value v on v.id = pp.PROGRESS_STATUS_ID left join gr_set s on s.id = v.gr_set_id and s.code = 'PROGRESS_STATUS'  " +
                " where pj.id in (select PM_PRJ_ID from PM_MAP where LONGITUDE=? and LATITUDE=?)", longitude, latitude);
        List<Project> projects = list.stream().map(p -> {
            Project pro = new Project();
            pro.projectId = JdbcMapUtil.getString(p, "ID");
            pro.projectName = JdbcMapUtil.getString(p, "NAME");
            pro.status = JdbcMapUtil.getString(p, "statusName");
            return pro;
        }).collect(Collectors.toList());
        OutSide outSide = new OutSide();
        outSide.list = projects;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 获取3D地图绑定的项目
     */
    public void getMapProject() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_MAP");
        List<Project> projectList = list.stream().map(p -> {
            Project project = new Project();
            project.projectId = JdbcMapUtil.getString(p, "PM_PRJ_ID");
            project.longitude = JdbcMapUtil.getString(p, "LONGITUDE");
            project.latitude = JdbcMapUtil.getString(p, "LATITUDE");
            return project;
        }).collect(Collectors.toList());
        OutSide outSide = new OutSide();
        outSide.list = projectList;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    public static class Project {
        public String projectId;
        public String projectName;
        public String longitude;
        public String latitude;

        public String status;
    }

    public static class OutSide{
       public List<Project> list;
    }
}
