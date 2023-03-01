package com.cisdi.ext.projectAreaMap;

import com.cisdi.ext.demostration.ProjectInfoTemp;
import com.cisdi.ext.demostration.ProjectMapTemp;
import com.cisdi.ext.util.BigDecimalUtil;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
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
        String longitude1 = longitude.substring(0,longitude.length()-3);
        System.out.println("longitude1: "+longitude1);
        String latitude1 = latitude.substring(0,latitude.length()-3);
        System.out.println("latitude1: "+latitude1);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
//        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pj.id,pj.name,IFNULL(v.name,'未启动') statusName from pm_prj pj " +
//                " left join pm_pro_plan pp on pp.PM_PRJ_ID = pj.id  \n" +
//                " left join gr_set_value v on v.id = pp.PROGRESS_STATUS_ID left join gr_set s on s.id = v.gr_set_id and s.code = 'PROGRESS_STATUS'  " +
//                " where pj.id in (select PM_PRJ_ID from PM_MAP where LONGITUDE=? and LATITUDE=?)", longitude, latitude);

        String sql = "select pj.id,pj.name,IFNULL(v.name,'未启动') statusName,pp.ACTUAL_START_DATE as beginTime,pp.ACTUAL_COMPL_DATE as endTime,pmp.`NAME` as projectOwner,gsv.`NAME` as projectType,ggg.`NAME` as manageUnit,pppm.`NAME` as sgUnit " +
                "from pm_prj pj " +
                "left join pm_pro_plan pp on pp.PM_PRJ_ID = pj.id " +
                "left join gr_set_value v on v.id = pp.PROGRESS_STATUS_ID left join gr_set s on s.id = v.gr_set_id and s.code = 'PROGRESS_STATUS'  " +
                "left join PM_PARTY pmp on pmp.id = pj.CUSTOMER_UNIT " +
                "left join gr_set_value gsv on gsv.id = pj.PROJECT_TYPE_ID " +
                "left join gr_set_value ggg on ggg.id = pj.PRJ_MANAGE_MODE_ID " +
                "left join pm_party pppm on pppm.id = pj.CONSTRUCTOR_UNIT " +
                " where pj.id in (select PM_PRJ_ID from PM_MAP where LONGITUDE like ('"+longitude1+"%') and LATITUDE like ('"+latitude1+"%'))";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);

        //临时获取项目形象进度
        List<ProjectInfoTemp> prjTemps = ProjectMapTemp.getAllData();
        List<Project> projects = list.stream().map(p -> {
            String sql2 = "select p1.`NAME` as jsUnit,p2.`NAME` as kcUnit,p3.`NAME` as sjUnit,p4.`NAME` as jlUnit,p5.`NAME` as sgUnit,PRJ_SITUATION from pm_prj pj " +
                    "left join PM_PARTY p1 on pj.BUILDER_UNIT = p1.id " +
                    "left join PM_PARTY p2 on pj.SURVEYOR_UNIT = p2.id " +
                    "left join PM_PARTY p3 on pj.DESIGNER_UNIT = p3.id " +
                    "left join PM_PARTY p4 on pj.SUPERVISOR_UNIT = p4.id " +
                    "left join PM_PARTY p5 on pj.CONSTRUCTOR_UNIT = p5.id " +
                    "where pj.id=?";
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap(sql2, JdbcMapUtil.getString(p, "ID"));
            Project pro = new Project();
            pro.projectId = JdbcMapUtil.getString(p, "ID");
            pro.projectName = JdbcMapUtil.getString(p, "NAME");
            pro.status = JdbcMapUtil.getString(p, "statusName");
            pro.projectOwner = JdbcMapUtil.getString(p, "projectOwner");

            String type = JdbcMapUtil.getString(p, "projectType");
            if ("民用建筑".equals(type)) {
                pro.projectType = "房建";
            } else if ("市政道路".equals(type)) {
                pro.projectType = "道路";
            } else {
                pro.projectType = "其他";
            }
            pro.beginTime = JdbcMapUtil.getString(p, "beginTime");
            pro.endTime = JdbcMapUtil.getString(p, "endTime");
            pro.manageUnit = JdbcMapUtil.getString(p, "manageUnit");
            pro.sgUnit = JdbcMapUtil.getString(p, "sgUnit");
            pro.totalInvest = getProjectInvest(pro.projectId);
//            pro.progress = BigDecimal.ZERO;
            //临时形象进度
            Optional<BigDecimal> anyPrjTemp =
                    prjTemps.stream().filter(prjTemp -> prjTemp.getName().equals(pro.projectName)).map(prjTemp -> prjTemp.getRate()).findAny();
            if (anyPrjTemp.isPresent()){
                pro.progress = anyPrjTemp.get();
            }
            pro.jsUnit = JdbcMapUtil.getString(stringObjectMap, "jsUnit");
            pro.kcUnit = JdbcMapUtil.getString(stringObjectMap, "kcUnit");
            pro.sjUnit = JdbcMapUtil.getString(stringObjectMap, "sjUnit");
            pro.jlUnit = JdbcMapUtil.getString(stringObjectMap, "jlUnit");
            pro.sgUnit = JdbcMapUtil.getString(stringObjectMap, "sgUnit");
            pro.prjSituation = JdbcMapUtil.getString(stringObjectMap, "PRJ_SITUATION");
            return pro;
        }).collect(Collectors.toList());
        OutSide outSide = new OutSide();
        outSide.list = projects;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    /**
     * 地图详情通过项目id获取
     */
    public void getPrjDetail() {
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String prjId = String.valueOf(params.get("prjId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String sql = "select pj.id,pj.name,IFNULL(v.name,'未启动') statusName,pp.ACTUAL_START_DATE as beginTime,pp.ACTUAL_COMPL_DATE as endTime,pmp.`NAME` as projectOwner,gsv.`NAME` as projectType,ggg.`NAME` as manageUnit,pppm.`NAME` as sgUnit " +
                "from pm_prj pj " +
                "left join pm_pro_plan pp on pp.PM_PRJ_ID = pj.id " +
                "left join gr_set_value v on v.id = pp.PROGRESS_STATUS_ID left join gr_set s on s.id = v.gr_set_id and s.code = 'PROGRESS_STATUS'  " +
                "left join PM_PARTY pmp on pmp.id = pj.CUSTOMER_UNIT " +
                "left join gr_set_value gsv on gsv.id = pj.PROJECT_TYPE_ID " +
                "left join gr_set_value ggg on ggg.id = pj.PRJ_MANAGE_MODE_ID " +
                "left join pm_party pppm on pppm.id = pj.CONSTRUCTOR_UNIT " +
                "where pj.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql,prjId);

        //临时获取项目形象进度
        List<ProjectInfoTemp> prjTemps = ProjectMapTemp.getAllData();
        List<Project> projects = list.stream().map(p -> {
            String sql2 = "select p1.`NAME` as jsUnit,p2.`NAME` as kcUnit,p3.`NAME` as sjUnit,p4.`NAME` as jlUnit,p5.`NAME` as sgUnit,PRJ_SITUATION from pm_prj pj " +
                    "left join PM_PARTY p1 on pj.BUILDER_UNIT = p1.id " +
                    "left join PM_PARTY p2 on pj.SURVEYOR_UNIT = p2.id " +
                    "left join PM_PARTY p3 on pj.DESIGNER_UNIT = p3.id " +
                    "left join PM_PARTY p4 on pj.SUPERVISOR_UNIT = p4.id " +
                    "left join PM_PARTY p5 on pj.CONSTRUCTOR_UNIT = p5.id " +
                    "where pj.id=?";
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap(sql2, JdbcMapUtil.getString(p, "ID"));
            Project pro = new Project();
            pro.projectId = JdbcMapUtil.getString(p, "ID");
            pro.projectName = JdbcMapUtil.getString(p, "NAME");
            pro.status = JdbcMapUtil.getString(p, "statusName");
            pro.projectOwner = JdbcMapUtil.getString(p, "projectOwner");

            String type = JdbcMapUtil.getString(p, "projectType");
            if ("民用建筑".equals(type)) {
                pro.projectType = "房建";
            } else if ("市政道路".equals(type)) {
                pro.projectType = "道路";
            } else {
                pro.projectType = "其他";
            }
            pro.beginTime = JdbcMapUtil.getString(p, "beginTime");
            pro.endTime = JdbcMapUtil.getString(p, "endTime");
            pro.manageUnit = JdbcMapUtil.getString(p, "manageUnit");
            pro.sgUnit = JdbcMapUtil.getString(p, "sgUnit");
            pro.totalInvest = getProjectInvest(pro.projectId);
//            pro.progress = BigDecimal.ZERO;
            //临时形象进度
            Optional<BigDecimal> anyPrjTemp =
                    prjTemps.stream().filter(prjTemp -> prjTemp.getName().equals(pro.projectName)).map(prjTemp -> prjTemp.getRate()).findAny();
            if (anyPrjTemp.isPresent()){
                pro.progress = anyPrjTemp.get();
            }
            pro.jsUnit = JdbcMapUtil.getString(stringObjectMap, "jsUnit");
            pro.kcUnit = JdbcMapUtil.getString(stringObjectMap, "kcUnit");
            pro.sjUnit = JdbcMapUtil.getString(stringObjectMap, "sjUnit");
            pro.jlUnit = JdbcMapUtil.getString(stringObjectMap, "jlUnit");
            pro.sgUnit = JdbcMapUtil.getString(stringObjectMap, "sgUnit");
            pro.prjSituation = JdbcMapUtil.getString(stringObjectMap, "PRJ_SITUATION");
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
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> orgPrjList = myJdbcTemplate.queryForList("select pp.PM_PRJ_ID as PM_PRJ_ID,pp.LONGITUDE as LONGITUDE,pp.LATITUDE as LATITUDE,pj.`NAME` as `NAME` ,gsv.`NAME` as projectType " +
                "from PM_MAP pp left join pm_prj pj on pp.PM_PRJ_ID = pj.id left join gr_set_value gsv on gsv.id = pj.PROJECT_TYPE_ID where pj.id is not null");
        //新项目列表，暂时注释
//        List<Map<String, Object>> orgPrjList = myJdbcTemplate.queryForList("select pp.PM_PRJ_ID,pj.NAME,gsv.NAME as projectType,pj.PRJ_MANAGE_MODE_ID modeId,gv.name modeName\n" +
//                "from prj_parcel pp left join pm_prj pj on pp.PM_PRJ_ID = pj.id \n" +
//                "left join gr_set_value gsv on gsv.id = pj.PROJECT_TYPE_ID\n" +
//                "left join gr_set_value gv on gv.id = pj.PRJ_MANAGE_MODE_ID\n" +
//                "where pj.id is not null\n" +
//                "group by pp.PM_PRJ_ID");

//        List<Map<String, Object>> orgCoordinateList = myJdbcTemplate.queryForList("select p.CENTER_LONGITUDE centerLongitude,CENTER_LATITUDE centerLatitude,pp.PM_PRJ_ID prjId from parcel p left join prj_parcel pp on pp.PARCEL_ID = p.id where CENTER_LATITUDE is not null and CENTER_LATITUDE is not null");

        List<Project> projectList = orgPrjList.stream().map(p -> {
            Project project = new Project();
            project.projectId = JdbcMapUtil.getString(p, "PM_PRJ_ID");
            project.projectName = JdbcMapUtil.getString(p, "NAME");
            project.longitude = JdbcMapUtil.getString(p, "LONGITUDE");
            project.latitude = JdbcMapUtil.getString(p, "LATITUDE");
            project.modeId = JdbcMapUtil.getString(p,"modeId");
            project.modeName = JdbcMapUtil.getString(p,"modeName");
            String type = JdbcMapUtil.getString(p, "projectType");
            if ("民用建筑".equals(type)) {
                project.projectType = "房建";
            } else if ("市政道路".equals(type)) {
                project.projectType = "道路";
            } else {
                project.projectType = "其他";
            }
            //封装中心点（可能多个）
//            List<List<BigDecimal>> centerCoordinates = orgCoordinateList.stream()
//                    .filter(c -> String.valueOf(c.get("prjId")).equals(project.projectId))
//                    .map(c -> {
//                        List<BigDecimal> coordinate = new ArrayList<>();
//                        coordinate.add(new BigDecimal(c.get("centerLongitude").toString()));
//                        coordinate.add(new BigDecimal(c.get("centerLatitude").toString()));
//                        return coordinate;
//                    }).collect(Collectors.toList());
//            project.coordinates = centerCoordinates;
            return project;
        }).collect(Collectors.toList());
        OutSide outSide = new OutSide();
        outSide.list = projectList;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }


    private BigDecimal getProjectInvest(String projectId) {

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ifnull(PRJ_TOTAL_INVEST,0) as PRJ_TOTAL_INVEST from PM_INVEST_EST pie \n" +
                " left join gr_set_value gsv on gsv.id = pie.INVEST_EST_TYPE_ID \n" +
                " left join gr_set gs on gs.id = gsv.GR_SET_ID and gs.code ='invest_est_type' \n" +
                " where gsv.code='invest2' and PM_PRJ_ID=?", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            return BigDecimalUtil.stringToBigDecimal(String.valueOf(list.get(0).get("PRJ_TOTAL_INVEST")));
        }
        return BigDecimal.ZERO;
    }


    /**
     * 大屏项目统计
     */
    public void projectBigScreen() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pj.id,pj.name,ifnull(pj.FLOOR_AREA,0) as area,gsv.`NAME` as projectType from pm_prj pj \n" +
                "left join gr_set_value gsv on gsv.id = pj.PROJECT_TYPE_ID   \n" +
                "where pj.`STATUS`='ap'");
        List<ProjectBigScreen> resList = new ArrayList<>();
        List<Map<String, Object>> list1 = list.stream().filter(p -> "民用建筑".equals(JdbcMapUtil.getString(p, "projectType"))).collect(Collectors.toList());
        ProjectBigScreen bigScreen = new ProjectBigScreen();
        bigScreen.projectType = "房建项目";
        bigScreen.count = list1.size();
        bigScreen.area = list1.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "area"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        resList.add(bigScreen);
        List<Map<String, Object>> list2 = list.stream().filter(p -> "市政道路".equals(JdbcMapUtil.getString(p, "projectType"))).collect(Collectors.toList());

        ProjectBigScreen bigScreen2 = new ProjectBigScreen();
        bigScreen2.projectType = "市政道路";
        bigScreen2.count = list2.size();
        bigScreen2.area = list2.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "area"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        resList.add(bigScreen2);

        List<Map<String, Object>> list3 = list.stream().filter(p -> !"民用建筑".equals(JdbcMapUtil.getString(p, "projectType")) && !"市政道路".equals(JdbcMapUtil.getString(p, "projectType"))).collect(Collectors.toList());
        ProjectBigScreen bigScreen3 = new ProjectBigScreen();
        bigScreen3.projectType = "其他项目";
        bigScreen3.count = list3.size();
        bigScreen3.area = list3.stream().map(p -> new BigDecimal(JdbcMapUtil.getString(p, "area"))).reduce(BigDecimal.ZERO, BigDecimal::add);
        resList.add(bigScreen3);

        OutSide outSide = new OutSide();
        outSide.bigScreenList = resList;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    public void projectTypeList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> params = ExtJarHelper.extApiParamMap.get();
        String code = String.valueOf(params.get("code"));
        int pageSize = JdbcMapUtil.getInt(params, "pageSize");
        int pageIndex = JdbcMapUtil.getInt(params, "pageIndex");

        String sql = "select pj.id as id ,pj.name as name,pp.name as project_own,gsv.`NAME` as projectType from pm_prj pj " +
                "                left join gr_set_value gsv on gsv.id = pj.PROJECT_TYPE_ID  " +
                "                left join PM_PARTY pp on pp.id = pj.CUSTOMER_UNIT " +
                "                where pj.`STATUS`='ap'";
        StringBuffer sb = new StringBuffer();
        sb.append(sql);
        if ("其他项目".equals(code)) {
            sb.append(" and gsv.`NAME` not in ('民用建筑','市政道路') ");
        } else if ("房建项目".equals(code)) {
            sb.append(" and gsv.`NAME` = '民用建筑'");
        } else {
            sb.append(" and gsv.`NAME` = '市政道路'");
        }
        int start = pageSize * (pageIndex - 1);

        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<ProjectInfo> res = list.stream().map(p -> {
            ProjectInfo info = new ProjectInfo();
            info.id = JdbcMapUtil.getString(p, "id");
            info.name = JdbcMapUtil.getString(p, "name");
            info.type = JdbcMapUtil.getString(p, "projectType");
            info.own = JdbcMapUtil.getString(p, "project_own");
            return info;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(res)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            String param = "";
            if("房建项目".equals(code)){
                param ="民用建筑";
            }else if("市政道路".equals(code)){
                param ="市政道路";
            }else{
                param ="其他";
            }
            List<ProjectInfoTemp> tempList = ProjectMapTemp.getData(param);

            List<ProjectInfo> result = tempList.stream().map(p -> {
                ProjectInfo info = new ProjectInfo();
                info.name = p.getName();
                info.type = p.getType();
                info.own = p.getUnit();
                info.invest = p.getAmt();
                info.imageProgress = p.getRate();
                return info;
            }).collect(Collectors.toList());
            result.addAll(res);
            List<ProjectInfo> pageList = result.stream().skip(start).limit(pageSize).collect(Collectors.toList());
//            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.res = pageList;
//            outSide.total = totalList.size();
            outSide.total = result.size();
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class Project {
        public String projectId;
        public String projectName;
        public String modeId;
        public String modeName;
        public String longitude;
        public String latitude;
        public List<List<BigDecimal>> coordinates;

        public String status;

        /**
         * 概算总投资
         */
        public BigDecimal totalInvest;

        /**
         * 形象进度
         */
        public BigDecimal progress;

        /**
         * 项目业主
         */
        public String projectOwner;

        /**
         * 项目类型
         */
        public String projectType;

        /**
         * 开工时间
         */
        public String beginTime;

        /**
         * 竣工时间
         */
        public String endTime;

        /**
         * 管理单位
         */
        public String manageUnit;

        /**
         * 施工单位
         */
        public String sgUnit;

        //建设单位
        public String jsUnit;
        //勘察单位
        public String kcUnit;
        //设计单位
        public String sjUnit;
        //监理单位
        public String jlUnit;

        //建设规模及内容
        public String prjSituation;

    }

    public static class OutSide {
        public List<Project> list;
        public List<ProjectBigScreen> bigScreenList;

        public List<ProjectInfo> res;

        public Integer total;
    }


    public static class ProjectBigScreen {
        public String projectType;

        public int count;

        public BigDecimal area;
    }


    public static class ProjectInfo {
        public String id;
        public String name;
        public String type;
        public String own;
        public BigDecimal invest = BigDecimal.ZERO;
        public BigDecimal imageProgress = BigDecimal.ZERO;
    }
}
