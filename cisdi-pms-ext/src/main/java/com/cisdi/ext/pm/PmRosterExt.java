package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmRosterExt
 * @package com.cisdi.ext.pm
 * @description 项目花名册
 * @date 2023/2/9
 */
public class PmRosterExt {

    /**
     * 花名册列表查询
     */
    public void pmRosterList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String projectName = String.valueOf(map.get("projectName"));
        StringBuffer sb = new StringBuffer();
        sb.append("select pj.id as id, pj.`NAME` as project_name from pm_prj pj left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID where pj.`STATUS`='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374'");
        if (!StringUtils.isEmpty(projectName)) {
            sb.append(" and pj.id like '%").append(projectName).append("%'");
        }
        sb.append("group by pj.id ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //header
        List<Map<String, Object>> strList = myJdbcTemplate.queryForList("select PROJECT_POST from PM_ROSTER GROUP BY PROJECT_POST");
        List<String> headerList = strList.stream().map(p -> JdbcMapUtil.getString(p, "PROJECT_POST")).collect(Collectors.toList());
        headerList.add(0, "项目名称");
        headerList.add("ID");
        //数据
        List<Map<String, Object>> dataList = new ArrayList<>();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        for (Map<String, Object> stringObjectMap : list) {
            Map<String, Object> newData = new HashMap<>();
            for (String s : headerList) {
                if ("项目名称".equals(s)) {
                    newData.put("项目名称", stringObjectMap.get("project_name"));
                } else if ("ID".equals(s)) {
                    newData.put("ID", stringObjectMap.get("id"));
                } else {
                    String prjId = String.valueOf(stringObjectMap.get("id"));
                    List<Map<String, Object>> rosterList = myJdbcTemplate.queryForList("select pp.*,au.`NAME` as user_name from PM_ROSTER pp left join ad_user au on pp.AD_USER_ID = au.id where PM_PRJ_ID=? and PROJECT_POST=?", prjId, s);
                    String users = "/";
                    if (!CollectionUtils.isEmpty(rosterList)) {
                        users = rosterList.stream().map(mm -> JdbcMapUtil.getString(mm, "user_name")).collect(Collectors.joining(","));
                        if ("null".equals(users)) {
                            users = "/";
                        }
                    }

                    newData.put(s, users);
                }
            }
            dataList.add(newData);
        }

        if (CollectionUtils.isEmpty(dataList)) {
            OutSide outSide = new OutSide();
            outSide.headerList = headerList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.dataList = dataList;
            outSide.headerList = headerList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 获取部门人员数结构
     */
    public void deptUserList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select id,name,ifnull(HR_DEPT_PID,0) as pid from hr_dept where status ='ap'");
        List<DeptUserTree> treeList = list.stream().map(p -> {
            DeptUserTree tree = new DeptUserTree();
            tree.id = JdbcMapUtil.getString(p, "id");
            tree.name = JdbcMapUtil.getString(p, "name");
            tree.pid = JdbcMapUtil.getString(p, "pid");
            return tree;
        }).collect(Collectors.toList());
        List<DeptUserTree> resData = treeList.stream().filter(p -> "0".equals(p.pid)).peek(m -> {
            List<DeptUserTree> children = this.getChildren(m, treeList);
            List<UserInfo> userList = this.getUserList(m.id);
            List<DeptUserTree> users = userList.stream().map(o -> {
                DeptUserTree deptUserTree = new DeptUserTree();
                deptUserTree.id = o.id;
                deptUserTree.name = o.name;
                deptUserTree.type = "1";
                return deptUserTree;
            }).collect(Collectors.toList());
            children.addAll(users);
            m.children = children;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resData)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.resData = resData;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 岗位列表
     */
    public void projectOwnerPostList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。

        StringBuffer sb = new StringBuffer();
        sb.append("select PROJECT_POST from PM_ROSTER where 1=1 ");
        if (StringUtils.isEmpty(map.get("name"))) {
            sb.append(" and PROJECT_POST like '%").append(map.get("name")).append("%'");
        }
        sb.append(" GROUP BY PROJECT_POST");
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> strList = myJdbcTemplate.queryForList(sb.toString());
        List<String> res = strList.stream().map(p -> JdbcMapUtil.getString(p, "PROJECT_POST")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(res)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.strList = res;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 项目列表
     */
    public void projectList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        StringBuffer sb = new StringBuffer();
        sb.append("select * from pm_prj where 1=1 ");
        if (!StringUtils.isEmpty(map.get("name"))) {
            sb.append(" and NAME like '%").append(map.get("name")).append("%'");
        }
        sb.append(" and status ='ap'");
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> strList = myJdbcTemplate.queryForList(sb.toString());
        List<ProjectInfo> projectInfoList = strList.stream().map(p -> {
            ProjectInfo info = new ProjectInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.name = JdbcMapUtil.getString(p, "NAME");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(projectInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.projectInfoList = projectInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 剔除没有当前岗位的项目
     */
    public void postProject() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuffer sb = new StringBuffer();
        sb.append("select * from pm_prj where id in (select PM_PRJ_ID from PM_ROSTER where PROJECT_POST= '").append(map.get("postName")).append("') ");
        if (!StringUtils.isEmpty(map.get("projectName"))) {
            sb.append("and `NAME` like '%").append(map.get("projectName")).append("%'");
        }
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<ProjectInfo> projectInfoList = list.stream().map(p -> {
            ProjectInfo info = new ProjectInfo();
            info.id = JdbcMapUtil.getString(p, "ID");
            info.name = JdbcMapUtil.getString(p, "NAME");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(projectInfoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.projectInfoList = projectInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 根据人元和岗位查询已经配置过的项目
     */
    public void postLinkProject() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_ROSTER where AD_USER_ID=? and PROJECT_POST=?", map.get("userId"), map.get("postName"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<String> projectIds = list.stream().map(p -> JdbcMapUtil.getString(p, "PM_PRJ_ID")).collect(Collectors.toList());
            OutSide outSide = new OutSide();
            outSide.strList = projectIds;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 批量处理项目岗位人员
     */
    public void batchProjectPostUser() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        ProjectPostUser projectPostUser = JsonUtil.fromJson(json, ProjectPostUser.class);
        List<String> dataList = projectPostUser.dataList;

        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        queryParams.put("postName", projectPostUser.postName);
        queryParams.put("userId", projectPostUser.userId);
        myNamedParameterJdbcTemplate.update("update PM_ROSTER set AD_USER_ID=null where PROJECT_POST= :postName and AD_USER_ID= :userId", queryParams);

        if (!CollectionUtils.isEmpty(dataList)) {
            queryParams.put("ids", dataList);
            queryParams.put("ancestral", projectPostUser.ancestral);
            myNamedParameterJdbcTemplate.update("update PM_ROSTER set AD_USER_ID= :userId,ANCESTRAL=:ancestral where PROJECT_POST= :postName and PM_PRJ_ID in (:ids) ", queryParams);
        }
    }


    /**
     * 查询项目岗位
     */
    public void getProjectPost() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_ROSTER where PM_PRJ_ID=?", map.get("projectId"));
        List<String> str = list.stream().map(p -> JdbcMapUtil.getString(p, "PROJECT_POST")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(str)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.strList = str;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 查询项目的某一个岗位绑定的用户
     */
    public void getProjectPostUser() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select au.*,ANCESTRAL from ad_user au  " +
                "left join PM_ROSTER pr on au.id = pr.AD_USER_ID " +
                "where  PM_PRJ_ID=? and PROJECT_POST=? ", map.get("projectId"), map.get("postName"));
        if (CollectionUtils.isEmpty(list)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            Map<String, Object> obj = list.get(0);
            UserInfo userInfo = new UserInfo();
            userInfo.id = JdbcMapUtil.getString(obj, "ID");
            userInfo.name = JdbcMapUtil.getString(obj, "NAME");
            userInfo.ancestral = JdbcMapUtil.getString(obj, "ANCESTRAL");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(userInfo), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 修改花名册
     */
    public void editProjectPostUser() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        EditObj editObj = JsonUtil.fromJson(json, EditObj.class);
        List<PostUser> dataList = editObj.dataList;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        for (PostUser postUser : dataList) {
            myJdbcTemplate.update("update PM_ROSTER set AD_USER_ID=null where PM_PRJ_ID=? and PROJECT_POST=?", editObj.projectId, postUser.postName);
            if (!Strings.isNullOrEmpty(postUser.userId)) {
                myJdbcTemplate.update("update PM_ROSTER set AD_USER_ID =?,ancestral=? where PROJECT_POST=? and PM_PRJ_ID=?", postUser.userId,postUser.ancestral, postUser.postName, editObj.projectId);
            }
        }
    }


    /**
     * 部门树结构
     */
    public void getDeptList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuffer sb = new StringBuffer();
        sb.append("select id,name,ifnull(HR_DEPT_PID,0) as pid from hr_dept where status ='ap' ");
        if (map.get("name") != null) {
            sb.append(" and `name` like '%").append(map.get("name")).append("%'");
            ;
        }
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<DeptUserTree> treeList = list.stream().map(p -> {
            DeptUserTree tree = new DeptUserTree();
            tree.id = JdbcMapUtil.getString(p, "id");
            tree.name = JdbcMapUtil.getString(p, "name");
            tree.pid = JdbcMapUtil.getString(p, "pid");
            return tree;
        }).collect(Collectors.toList());
        List<DeptUserTree> resData = treeList.stream().filter(p -> "0".equals(p.pid)).peek(m -> {
            m.children = this.getChildren(m, treeList);
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resData)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.resData = resData;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 当前岗位绑定的部门
     */
    public void postDept() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_ROSTER where PROJECT_POST=?", map.get("postName"));
        List<String> ids = list.stream().map(p -> JdbcMapUtil.getString(p, "HR_DEPT_ID")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(ids)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.strList = ids;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 岗位绑定部门
     */
    public void postBondDept() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("update PM_ROSTER set HR_DEPT_ID=? where PROJECT_POST=?", map.get("deptId"), map.get("postName"));
    }


    /**
     * 查询项目岗位人员
     */
    public void getPostUserByPrj() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_ROSTER where PM_PRJ_ID=?", map.get("projectId"));
        List<PostUser> postUserList = list.stream().map(p -> {
            PostUser user = new PostUser();
            user.postName = JdbcMapUtil.getString(p, "PROJECT_POST");
            user.userId = JdbcMapUtil.getString(p, "AD_USER_ID");
            user.ancestral = JdbcMapUtil.getString(p, "ANCESTRAL");
            return user;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(postUserList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.postUserList = postUserList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    private List<DeptUserTree> getChildren(DeptUserTree parent, List<DeptUserTree> allData) {
        return allData.stream().filter(p -> parent.id.equals(p.pid)).peek(m -> {
            List<DeptUserTree> children = this.getChildren(m, allData);
            List<UserInfo> userList = this.getUserList(m.id);
            List<DeptUserTree> users = userList.stream().map(o -> {
                DeptUserTree deptUserTree = new DeptUserTree();
                deptUserTree.id = o.id;
                deptUserTree.name = o.name;
                deptUserTree.type = "1";
                return deptUserTree;
            }).collect(Collectors.toList());
            children.addAll(users);
            m.children = children;
        }).collect(Collectors.toList());
    }

    private List<UserInfo> getUserList(String deptId) {
        List<UserInfo> res = new ArrayList<>();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from ad_user where id in (select AD_USER_ID from hr_dept_user where HR_DEPT_ID=?)", deptId);
        if (!CollectionUtils.isEmpty(list)) {
            res = list.stream().map(p -> {
                UserInfo userInfo = new UserInfo();
                userInfo.id = JdbcMapUtil.getString(p, "ID");
                userInfo.name = JdbcMapUtil.getString(p, "NAME");
                return userInfo;
            }).collect(Collectors.toList());
        }
        return res;
    }

    public String getAncestral(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> deptList = myJdbcTemplate.queryForList("select * from hr_dept where name = (select pp.`NAME` from pm_prj pm left join pm_party pp on pm.CUSTOMER_UNIT = pp.id where pm.id=?)", projectId);
        String res = "";
        if (!CollectionUtils.isEmpty(deptList)) {
            res = String.valueOf(deptList.get(0).get("ID"));
        }
        return res;
    }

    public static class OutSide {
        public List<Map<String, Object>> dataList;

        public List<String> headerList;

        public Integer total;

        public List<DeptUserTree> resData;

        public List<String> strList;

        public List<ProjectInfo> projectInfoList;

        public List<PostUser> postUserList;
    }


    public static class DeptUserTree {
        public String id;
        public String name;
        public String pid;
        public String type = "0"; //0-部门 1-用户
        public List<DeptUserTree> children;
    }

    public static class UserInfo {
        public String id;
        public String name;
        public String type = "1"; //0-部门 1-用户
        public String ancestral;
    }

    public static class ProjectInfo {
        public String id;
        public String name;
    }


    public static class ProjectPostUser {
        public String userId;

        public String postName;

        /**
         * 祖籍
         */
        public String ancestral;
        /**
         * 选中的集合
         */
        public List<String> dataList;

    }


    public static class EditObj {
        public String projectId;

        public List<PostUser> dataList;

    }

    public static class PostUser {
        public String postName;

        public String userId;

        public String ancestral;
    }

}
