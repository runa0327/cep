package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmDeptExt
 * @package com.cisdi.ext.pm
 * @description 项目部门人员
 * @date 2022/11/16
 */
public class PmDeptExt {


    /**
     * 项目部门人员新增单条记录
     * @param userId 创建人员id
     * @param userIds 项目人员id，多个用,分隔
     * @param projectId 项目id
     * @param deptId 部门id
     * @param myJdbcTemplate 数据源
     */
    public static void createPrjUserOne(String userId,String userIds, String projectId, String deptId, MyJdbcTemplate myJdbcTemplate) {
        String id = Crud.from("pm_dept").insertData();
        String sql = "update pm_dept set ver = 1, TS = now(),CRT_DT=now(),CRT_USER_ID=?,LAST_MODI_DT=now(),LAST_MODI_USER_ID=?,STATUS='AP'," +
                "PM_PRJ_ID=?,USER_IDS=?,HR_DEPT_ID=? where id = ?";
        myJdbcTemplate.update(sql,userId,userId,projectId,userIds,deptId,id);

    }

    /**
     * 项目部门人员新增/修改
     */
    public void pmDeptModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmDeptInput input = JsonUtil.fromJson(json, PmDeptInput.class);
        String id = input.id;
        if (!Strings.isNotEmpty(id)) {
            id = Crud.from("PM_DEPT").insertData();
        }
        Crud.from("PM_DEPT").where().eq("ID", id).update()
                .set("PM_PRJ_ID", input.projectId).set("HR_DEPT_ID", input.deptId).set("USER_IDS", input.userIds).exec();

    }


    /**
     * 项目部门人员列表查询
     */
    public void pmDeptList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pd.*,hd.`NAME` as dept_name from PM_DEPT pd left join hr_dept hd on pd.HR_DEPT_ID = hd.id where PM_PRJ_ID=?", projectId);
        List<PmDeptInput> pmDeptInputList = list.stream().map(p -> {
            PmDeptInput input = new PmDeptInput();
            input.id = JdbcMapUtil.getString(p, "ID");
            input.projectId = JdbcMapUtil.getString(p, "PM_PRJ_ID");
            input.deptId = JdbcMapUtil.getString(p, "HR_DEPT_ID");
            input.userIds = JdbcMapUtil.getString(p, "USER_IDS");
            input.deptName = JdbcMapUtil.getString(p, "dept_name");

            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
            Map<String, Object> queryParams = new HashMap<>();// 创建入参map
            queryParams.put("ids", Arrays.asList(input.userIds.split(",")));
            List<Map<String, Object>> uList = myNamedParameterJdbcTemplate.queryForList("select * from ad_user where id in (:ids)", queryParams);
            List<User> users = uList.stream().map(o -> {
                User user = new User();
                user.id = JdbcMapUtil.getString(o, "ID");
                user.name = JdbcMapUtil.getString(o, "NAME");
                return user;
            }).collect(Collectors.toList());
            input.userList = users;
            input.userNames = users.stream().map(u->u.name).collect(Collectors.joining(","));
            return input;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(pmDeptInputList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            outSide resData = new outSide();
            resData.pmDeptInputList = pmDeptInputList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 项目部门人员详情
     */
    public void pmDeptView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select pd.*,hd.`NAME` as dept_name from PM_DEPT pd left join hr_dept hd on pd.HR_DEPT_ID = hd.id where pd.id=?", id);
            PmDeptInput input = new PmDeptInput();
            input.id = JdbcMapUtil.getString(stringObjectMap, "ID");
            input.projectId = JdbcMapUtil.getString(stringObjectMap, "PM_PRJ_ID");
            input.deptId = JdbcMapUtil.getString(stringObjectMap, "HR_DEPT_ID");
            input.userIds = JdbcMapUtil.getString(stringObjectMap, "USER_IDS");
            input.deptName = JdbcMapUtil.getString(stringObjectMap, "dept_name");

            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
            Map<String, Object> queryParams = new HashMap<>();// 创建入参map
            queryParams.put("ids", Arrays.asList(input.userIds.split(",")));
            List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select * from ad_user where id in (:ids)", queryParams);
            List<User> users = list.stream().map(p -> {
                User user = new User();
                user.id = JdbcMapUtil.getString(p, "ID");
                user.name = JdbcMapUtil.getString(p, "NAME");
                return user;
            }).collect(Collectors.toList());
            input.userList = users;
            input.userNames = users.stream().map(u->u.name).collect(Collectors.joining(","));
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(input), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            throw new BaseException("查询数据错误");
        }
    }

    /**
     * 项目部门人员删除
     */
    public void pmDeptDel() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PM_DEPT where id = ?", id);
    }

    /**
     * 获取部门
     */
    public void getDept() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,`NAME`,IFNULL(HR_DEPT_PID,0) PID from hr_dept where `STATUS` =  'ap'");
        List<Dept> deptList = list.stream().map(p -> {
            Dept dept = new Dept();
            dept.id = JdbcMapUtil.getString(p, "ID");
            dept.pid = JdbcMapUtil.getString(p, "PID");
            dept.name = JdbcMapUtil.getString(p, "NAME");
            return dept;
        }).collect(Collectors.toList());
        List<Dept> tree = deptList.stream().filter(p -> Objects.equals("0", p.pid)).peek(m -> {
            m.children = getChildren(m, deptList);
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(tree)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            outSide resData = new outSide();
            resData.deptList = tree;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    public List<Dept> getChildren(Dept parent, List<Dept> allData) {
        return allData.stream().filter(p -> Objects.equals(parent.id, p.pid)).peek(m -> {
            m.children = getChildren(m, allData);
        }).collect(Collectors.toList());
    }

    /**
     * 根据部门查询用户
     */
    public void getUserByDept() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from ad_user where id in (select AD_USER_ID from hr_dept_user where HR_DEPT_ID =?) and status = 'AP'", id);
        List<User> users = list.stream().map(p -> {
            User user = new User();
            user.id = JdbcMapUtil.getString(p, "ID");
            user.name = JdbcMapUtil.getString(p, "NAME");
            return user;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            DeptUsers resData = new DeptUsers();
            resData.users = users;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 根据部门和名称查询用户
     */
    public void getUserByDeptIdAndName() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        String name = String.valueOf(map.get("name"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuffer sb = new StringBuffer();
        sb.append("select * from ad_user where id in (select AD_USER_ID from hr_dept_user where HR_DEPT_ID ='").append(id).append("') and NAME like ").append("'%").append(name).append("%'");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<User> users = list.stream().map(p -> {
            User user = new User();
            user.id = JdbcMapUtil.getString(p, "ID");
            user.name = JdbcMapUtil.getString(p, "NAME");
            return user;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            DeptUsers resData = new DeptUsers();
            resData.users = users;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    public static class PmDeptInput {
        public String id;
        //项目ID
        public String projectId;
        //部门ID
        public String deptId;
        //多选用户ID
        public String userIds;

        public List<User> userList;

        public String deptName;

        public String userNames;

    }

    public static class outSide {
        public List<PmDeptInput> pmDeptInputList;
        public List<Dept> deptList;
    }

    public static class User {
        public String id;
        public String name;
    }

    public static class DeptUsers {
        public List<User> users;
    }

    public static class Dept {
        public String id;
        public String pid;
        public String name;
        public List<Dept> children;
    }
}
