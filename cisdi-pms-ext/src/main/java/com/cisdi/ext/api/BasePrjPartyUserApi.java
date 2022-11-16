package com.cisdi.ext.api;

import com.cisdi.ext.model.view.base.GrSetValueView;
import com.cisdi.ext.model.view.base.PmPartyView;
import com.cisdi.ext.model.view.order.PoOrderPaymentView;
import com.cisdi.ext.model.view.project.BasePrjPartyUserView;
import com.cisdi.ext.model.view.project.BaseProjectUserView;
import com.cisdi.ext.model.view.project.PmPrjView;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 项目成员配置
 */
public class BasePrjPartyUserApi {

    @Autowired
    private JdbcTemplate jdbcTemplate;
//    MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

    //单位下拉框
    public void getPartyRole(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        GrSetValueView param = JsonUtil.fromJson(json, GrSetValueView.class);
        StringBuilder sb = new StringBuilder();
        sb.append("select id,name from gr_set_value where GR_SET_ID = '99952822476391029'");
        if (!SharedUtil.isEmptyString(param.name)){
            sb.append(" and name like '%" + param.name + "%' ");
        }
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
        if (CollectionUtils.isEmpty(list)){
            ExtJarHelper.returnValue.set(null);
        } else {
            Map<String, Object> map1 = new HashMap<>();
            List<GrSetValueView> inputList = getValue(list);
            map1.put("result", inputList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /** 集合表下拉数据赋值 **/
    private List<GrSetValueView> getValue(List<Map<String, Object>> list) {
        List<GrSetValueView> li = list.stream().map(p->{
            GrSetValueView grSetValueView = new GrSetValueView();
            grSetValueView.id = JdbcMapUtil.getString(p,"id");
            grSetValueView.name = JdbcMapUtil.getString(p,"name");
            return grSetValueView;
        }).collect(Collectors.toList());
        return li;
    }

    //列表页
    public void getBasePrjPartyUserList(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        BasePrjPartyUserView param = JsonUtil.fromJson(json, BasePrjPartyUserView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder baseSql1 = new StringBuilder();
        StringBuilder baseSql2 = new StringBuilder();
        String sql1 = "SELECT a.id,a.PM_PRJ_ID,b.name as projectName,a.PM_PARTY_ID," +
                "c.name as partyName,a.PM_PARTY_ROLE_ID,d.name as partyRoleName," +
                "a.USER_IDS FROM base_prj_party_user a  " +
                "LEFT JOIN pm_prj b on a.PM_PRJ_ID = b.id " +
                "left join pm_party c on a.PM_PARTY_ID = c.id " +
                "left join gr_set_value d on a.PM_PARTY_ROLE_ID = d.id " +
                "WHERE a.status = 'ap'";
        String sql2 = "SELECT count(*) as num FROM base_prj_party_user a  " +
                "LEFT JOIN pm_prj b on a.PM_PRJ_ID = b.id " +
                "left join pm_party c on a.PM_PARTY_ID = c.id " +
                "left join gr_set_value d on a.PM_PARTY_ROLE_ID = d.id " +
                "WHERE a.status = 'ap'";
        baseSql1.append(sql1);
        baseSql2.append(sql2);
        //筛选条件
        if (!SharedUtil.isEmptyString(param.projectName)){
            baseSql1.append(" and b.name like ('%" + param.projectName + "%')");
            baseSql2.append(" and b.name like ('%" + param.projectName + "%')");
        }
        if (!SharedUtil.isEmptyString(param.partyName)){
            baseSql1.append(" and c.name like ('%" + param.partyName + "%')");
            baseSql2.append(" and c.name like ('%" + param.partyName + "%')");
        }
        if (!SharedUtil.isEmptyString(param.PM_PARTY_ROLE_ID)){
            baseSql1.append(" and d.id =" + param.PM_PARTY_ROLE_ID+"'");
            baseSql2.append(" and d.id =" + param.PM_PARTY_ROLE_ID+"'");
        }
        if (!SharedUtil.isEmptyString(param.userId)){
            baseSql1.append(" and find_in_set('"+param.userId+"',a.USER_IDS) ");
            baseSql2.append(" and find_in_set('"+param.userId+"',a.USER_IDS) ");
        }
        baseSql1.append("order BY a.CRT_DT DESC ").append(limit);
        baseSql2.append("order BY a.CRT_DT DESC");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(baseSql1.toString());
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(baseSql2.toString());
        Map<String, Object> map1 = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            List<BasePrjPartyUserView> inputList = list.stream().map(p->{
                BasePrjPartyUserView basePrjPartyUserView = new BasePrjPartyUserView();
                basePrjPartyUserView.PM_PRJ_ID = JdbcMapUtil.getString(p,"PM_PRJ_ID");
                basePrjPartyUserView.projectName = JdbcMapUtil.getString(p,"projectName");
                basePrjPartyUserView.PM_PARTY_ROLE_ID = JdbcMapUtil.getString(p,"PM_PARTY_ROLE_ID");
                basePrjPartyUserView.partyRoleName = JdbcMapUtil.getString(p,"partyRoleName");
                basePrjPartyUserView.PM_PARTY_ID = JdbcMapUtil.getString(p,"PM_PARTY_ID");
                basePrjPartyUserView.partyName = JdbcMapUtil.getString(p,"partyName");
                String userIds = JdbcMapUtil.getString(p,"USER_IDS").replace(",","','");
                String userName = "";
                List<Map<String,Object>> userList = myJdbcTemplate.queryForList("select GROUP_CONCAT(name) as name from ad_user where id in (?)",userIds);
                if (!CollectionUtils.isEmpty(userList)){
                    userName = userList.get(0).get("name").toString();
                }
                basePrjPartyUserView.USER_IDS = userIds;
                basePrjPartyUserView.userName = userName;
                return basePrjPartyUserView;
            }).collect(Collectors.toList());
            map1.put("result", inputList);
            map1.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    //项目列表页
    public void getProjectList(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmPrjView param = JsonUtil.fromJson(json, PmPrjView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder baseSql1 = new StringBuilder();
        StringBuilder baseSql2 = new StringBuilder();
        String sql1 = "SELECT id,name FROM pm_prj where status = 'ap'";
        String sql2 = "SELECT count(*) as num FROM pm_prj where status = 'ap'";
        baseSql1.append(sql1);
        baseSql2.append(sql2);
        //筛选条件
        if (!SharedUtil.isEmptyString(param.projectName)){
            baseSql1.append(" and name like ('%" + param.projectName + "%')");
            baseSql2.append(" and name like ('%" + param.projectName + "%')");
        }
        baseSql1.append("order BY a.CRT_DT DESC ").append(limit);
        baseSql2.append("order BY a.CRT_DT DESC");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(baseSql1.toString());
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(baseSql2.toString());
        Map<String, Object> map1 = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            List<PmPrjView> inputList = list.stream().map(p->{
                PmPrjView pmPrjView = new PmPrjView();
                pmPrjView.PM_PRJ_ID = JdbcMapUtil.getString(p,"id");
                pmPrjView.projectName = JdbcMapUtil.getString(p,"name");
                return pmPrjView;
            }).collect(Collectors.toList());
            map1.put("result", inputList);
            map1.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    //合作方列表页
    public void getPartyList(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmPartyView param = JsonUtil.fromJson(json, PmPartyView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder baseSql1 = new StringBuilder();
        StringBuilder baseSql2 = new StringBuilder();
        String sql1 = "SELECT id,name FROM pm_party where status = 'ap'";
        String sql2 = "SELECT count(*) as num FROM pm_party where status = 'ap'";
        baseSql1.append(sql1);
        baseSql2.append(sql2);
        //筛选条件
        if (!SharedUtil.isEmptyString(param.partyName)){
            baseSql1.append(" and name like ('%" + param.partyName + "%')");
            baseSql2.append(" and name like ('%" + param.partyName + "%')");
        }
        baseSql1.append("order BY a.CRT_DT DESC ").append(limit);
        baseSql2.append("order BY a.CRT_DT DESC");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(baseSql1.toString());
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(baseSql2.toString());
        Map<String, Object> map1 = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            List<PmPartyView> inputList = list.stream().map(p->{
                PmPartyView pmPrjView = new PmPartyView();
                pmPrjView.PM_PARTY_ID = JdbcMapUtil.getString(p,"id");
                pmPrjView.partyName = JdbcMapUtil.getString(p,"name");
                return pmPrjView;
            }).collect(Collectors.toList());
            map1.put("result", inputList);
            map1.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    //人员列表页
    public void getUserList(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        BaseProjectUserView param = JsonUtil.fromJson(json, BaseProjectUserView.class);
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }
        if (SharedUtil.isEmptyString(param.PM_PRJ_ID)){
            throw new BaseException("请先选择项目！");
        }
        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select group_concat(USER_IDS) as user from pm_dept where PM_PRJ_ID = ?";
        List<Map<String,Object>> userList = jdbcTemplate.queryForList(sql,param.PM_PRJ_ID);
        if (CollectionUtils.isEmpty(userList)){
            throw new BaseException("该项目暂未配置权限人员，请先进行人员项目权限配置！");
        }
        String userId = JdbcMapUtil.getString(userList.get(0),"user").replace(",","','");
        StringBuilder baseSql1 = new StringBuilder();
        StringBuilder baseSql2 = new StringBuilder();
        String sql1 = "SELECT id,name FROM ad_user where status = 'ap' and id in (?) ";
        String sql2 = "SELECT count(*) as num FROM ad_user where status = 'ap' and id in (?) ";
        baseSql1.append(sql1);
        baseSql2.append(sql2);
        //筛选条件
        if (!SharedUtil.isEmptyString(param.userName)){
            baseSql1.append(" and name like ('%" + param.userName + "%')");
            baseSql2.append(" and name like ('%" + param.userName + "%')");
        }
        baseSql1.append("order BY a.CRT_DT DESC ").append(limit);
        baseSql2.append("order BY a.CRT_DT DESC");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(baseSql1.toString(),userId);
        List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(baseSql2.toString(),userId);
        Map<String, Object> map1 = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            List<BaseProjectUserView> inputList = list.stream().map(p->{
                BaseProjectUserView baseProjectUserView = new BaseProjectUserView();
                baseProjectUserView.USER_IDS = JdbcMapUtil.getString(p,"id");
                baseProjectUserView.userName = JdbcMapUtil.getString(p,"name");
                return baseProjectUserView;
            }).collect(Collectors.toList());
            map1.put("result", inputList);
            map1.put("total", Integer.valueOf(list2.get(0).get("num").toString()));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     *  新增/修改项目人员配置
     */
    public void insert(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        BasePrjPartyUserView param = JsonUtil.fromJson(json, BasePrjPartyUserView.class);
        String id = param.id;
        if (SharedUtil.isEmptyString(id)){
            id = Crud.from("base_prj_party_user").insertData();
        }
        Crud.from("base_prj_party_user").where().eq("id",id).update()
                .set("STATUS","AP").set("PM_PRJ_ID",param.PM_PRJ_ID)
                .set("PM_PARTY_ID",param.PM_PARTY_ID).set("PM_PARTY_ROLE_ID",param.PM_PARTY_ROLE_ID)
                .set("USER_IDS",param.USER_IDS)
                .exec();
    }

    /**
     * 查询单条数据
     */
    public void getOneDetail(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        BasePrjPartyUserView param = JsonUtil.fromJson(json, BasePrjPartyUserView.class);
        if (SharedUtil.isEmptyString(param.id)){
            throw new BaseException("请先选择一条记录！");
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql1 = "SELECT a.id,a.PM_PRJ_ID,b.name as projectName,a.PM_PARTY_ID," +
                "c.name as partyName,a.PM_PARTY_ROLE_ID,d.name as partyRoleName," +
                "a.USER_IDS FROM base_prj_party_user a  " +
                "LEFT JOIN pm_prj b on a.PM_PRJ_ID = b.id " +
                "left join pm_party c on a.PM_PARTY_ID = c.id " +
                "left join gr_set_value d on a.PM_PARTY_ROLE_ID = d.id " +
                "WHERE a.status = 'ap' and a.id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql1,param.id);
        Map<String, Object> map1 = new HashMap<>();
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            List<BasePrjPartyUserView> inputList = list.stream().map(p->{
                BasePrjPartyUserView basePrjPartyUserView = new BasePrjPartyUserView();
                basePrjPartyUserView.PM_PRJ_ID = JdbcMapUtil.getString(p,"PM_PRJ_ID");
                basePrjPartyUserView.projectName = JdbcMapUtil.getString(p,"projectName");
                basePrjPartyUserView.PM_PARTY_ROLE_ID = JdbcMapUtil.getString(p,"PM_PARTY_ROLE_ID");
                basePrjPartyUserView.partyRoleName = JdbcMapUtil.getString(p,"partyRoleName");
                basePrjPartyUserView.PM_PARTY_ID = JdbcMapUtil.getString(p,"PM_PARTY_ID");
                basePrjPartyUserView.partyName = JdbcMapUtil.getString(p,"partyName");
                String userIds = JdbcMapUtil.getString(p,"USER_IDS").replace(",","','");
                String userName = "";
                List<Map<String,Object>> userList = myJdbcTemplate.queryForList("select GROUP_CONCAT(name) as name from ad_user where id in (?)",userIds);
                if (!CollectionUtils.isEmpty(userList)){
                    userName = userList.get(0).get("name").toString();
                }
                basePrjPartyUserView.USER_IDS = userIds;
                basePrjPartyUserView.userName = userName;
                return basePrjPartyUserView;
            }).collect(Collectors.toList());
            map1.put("result", inputList);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

}
