package com.cisdi.ext.pm;

import com.cisdi.ext.link.LinkSql;
import com.cisdi.ext.model.HrDept;
import com.cisdi.ext.model.PmRoster;
import com.cisdi.ext.model.PostInfo;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.PrjPlanUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
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

    public static final Map<String, String> postDeptMap = new HashMap<>();

    static {
        postDeptMap.put("AD_USER_TWELVE_ID", "前期管理部");
        postDeptMap.put("AD_USER_THIRTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_FOURTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_FIFTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_SIXTEEN_ID", "前期管理部");
        postDeptMap.put("AD_USER_EIGHTEEN_ID", "成本合约部");
        postDeptMap.put("AD_USER_NINETEEN_ID", "成本合约部");
        postDeptMap.put("AD_USER_TWENTY_ID", "成本合约部");
        postDeptMap.put("AD_USER_TWENTY_ONE_ID", "采购管理部");
        postDeptMap.put("AD_USER_TWENTY_TWO_ID", "设计管理部");
        postDeptMap.put("AD_USER_TWENTY_THREE_ID", "工程管理部");
        postDeptMap.put("AD_USER_TWENTY_FOUR_ID", "工程管理部");
        postDeptMap.put("AD_USER_TWENTY_FIVE_ID", "财务金融部");
    }

    /**
     * 花名册列表查询
     */
    public void pmRosterList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String projectName = String.valueOf(map.get("projectName"));
        StringBuffer sb = new StringBuffer();
        sb.append("select pj.id as id, pj.`NAME` as project_name from pm_prj pj left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID where pj.`STATUS`='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' and pj.IZ_FORMAL_PRJ = 1 ");
        if (!StringUtils.isEmpty(projectName)) {
            sb.append(" and pj.name like '%").append(projectName).append("%'");
        }
        sb.append(" and pj.pm_code is not null ");
        sb.append("group by pj.id order by pj.pm_code desc");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //header
        List<Map<String, Object>> strList = myJdbcTemplate.queryForList("select po.`NAME` as PROJECT_POST from PM_ROSTER pr left join post_info po on pr.POST_INFO_ID = po.id where POST_INFO_ID is not null AND po.`NAME` is not null group by po.id  order by po.seq_no");
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
                    List<Map<String, Object>> rosterList = myJdbcTemplate.queryForList("select pp.*,au.`NAME` as user_name from PM_ROSTER pp left join ad_user au on pp.AD_USER_ID = au.id left join post_info po on po.id = pp.POST_INFO_ID where PM_PRJ_ID=? and po.`NAME`=? and pp.AD_USER_ID != '1641281525532323840'", prjId, s);
                    String users = "";
                    if (!CollectionUtils.isEmpty(rosterList)) {
                        users = rosterList.stream().map(mm -> JdbcMapUtil.getString(mm, "user_name")).collect(Collectors.joining(","));
                        if ("null".equals(users)) {
                            users = "";
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
        sb.append("select id,name from post_info where `STATUS`='ap' ");
        if (StringUtils.isEmpty(map.get("name"))) {
            sb.append(" and name like '%").append(map.get("name")).append("%'");
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> strList = myJdbcTemplate.queryForList(sb.toString());

        List<Post> postList = strList.stream().map(p -> {
            Post info = new Post();
            info.id = JdbcMapUtil.getString(p, "id");
            info.name = JdbcMapUtil.getString(p, "name");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(postList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.postList = postList;
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
        sb.append("select pj.* from pm_prj pj left join PM_ROSTER pr on pj.id = pr.PM_PRJ_ID left join post_info po on po.id = pr.POST_INFO_ID where pr.post_info_id = '").append(map.get("postId")).append("' ");
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_ROSTER pr left join post_info po on pr.POST_INFO_ID = po.id where pr.AD_USER_ID=? and po.`NAME`=? ", map.get("userId"), map.get("postName"));
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
        queryParams.put("postId", projectPostUser.postId);
        queryParams.put("userId", projectPostUser.userId);
        myNamedParameterJdbcTemplate.update("update PM_ROSTER set AD_USER_ID=null where POST_INFO_ID= :postId and AD_USER_ID= :userId", queryParams);

        if (!CollectionUtils.isEmpty(dataList)) {
            queryParams.put("ids", dataList);
            queryParams.put("ancestral", projectPostUser.ancestral);
            myNamedParameterJdbcTemplate.update("update PM_ROSTER set AD_USER_ID= :userId,ANCESTRAL=:ancestral where POST_INFO_ID= :postId and PM_PRJ_ID in (:ids) ", queryParams);
        }
        //刷新项目的进度节点用户
        dataList.forEach(PrjPlanUtil::refreshProPlanUser);
    }


    /**
     * 查询项目岗位
     */
    public void getProjectPost() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from post_info where id in(select POST_INFO_ID from PM_ROSTER where PM_PRJ_ID=?)", map.get("projectId"));
        List<Post> postList = list.stream().map(p -> {
            Post post = new Post();
            post.id = JdbcMapUtil.getString(p, "ID");
            post.name = JdbcMapUtil.getString(p, "NAME");
            return post;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(postList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.postList = postList;
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
                "where  PM_PRJ_ID=? and post_info_id=? ", map.get("projectId"), map.get("postId"));
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
            myJdbcTemplate.update("update PM_ROSTER set AD_USER_ID=null where PM_PRJ_ID=? and post_info_id=?", editObj.projectId, postUser.postId);
            if (!Strings.isNullOrEmpty(postUser.userId)) {
                myJdbcTemplate.update("update PM_ROSTER set AD_USER_ID =?,ancestral=? where post_info_id=? and PM_PRJ_ID=?", postUser.userId, postUser.ancestral, postUser.postId, editObj.projectId);
            }
        }
        PrjPlanUtil.refreshProPlanUser(editObj.projectId);
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pr.* from PM_ROSTER pr left join post_info po on pr.post_info_id = po.id  where po.`name`=?", map.get("postName"));
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
        myJdbcTemplate.update("update PM_ROSTER set HR_DEPT_ID=? where post_info_id=?", map.get("deptId"), map.get("postId"));
    }


    /**
     * 查询项目岗位人员
     */
    public void getPostUserByPrj() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pr.*,po.name as post_name from PM_ROSTER pr left join post_info po on pr.post_info_id = po.id  where PM_PRJ_ID=?", map.get("projectId"));
        List<PostUser> postUserList = list.stream().map(p -> {
            PostUser user = new PostUser();
            user.postId = JdbcMapUtil.getString(p, "post_info_id");
            user.postName = JdbcMapUtil.getString(p, "post_name");
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from ad_user where `status` = 'ap' and id in (select AD_USER_ID from hr_dept_user where HR_DEPT_ID=? and AD_USER_ID != '1641281525532323840')", deptId);
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

        public List<Post> postList;

        public Map<String, List<ObjInfo>> collect;
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

        public String postId;

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
        public String postId;

        public String postName;

        public String userId;

        public String ancestral;
    }

    public static class Post {
        public String id;
        public String name;
    }


    /**
     * 岗位指派流程完结-数据写入
     *
     * @param entityRecord 数据值
     * @param projectId    项目id
     * @param map          岗位指派流程字段与岗位对应关系 key：流程表单字段 value：岗位名称
     */
    public static void createDataByProcess(EntityRecord entityRecord, String projectId, Map<String, String> map) {
        List<PmRoster> insertList = new ArrayList<>();
        //业主单位
        String customerUnit = JdbcMapUtil.getString(entityRecord.valueMap, "CUSTOMER_UNIT");
        for (String key : map.keySet()) {
            String valueUser = JdbcMapUtil.getString(entityRecord.valueMap, key);
            if (!SharedUtil.isEmptyString(valueUser)) {
                String value = map.get(key);
                String postId = PostInfo.selectByWhere(new Where().eq(PostInfo.Cols.SYS_TRUE, "1")
                        .eq(PostInfo.Cols.STATUS, "AP").eq(PostInfo.Cols.NAME, value)).get(0).getId();
                //部门id查询
                String deptName = getDeptNameByCode(key);
                String deptId = HrDept.selectByWhere(new Where().eq(HrDept.Cols.CUSTOMER_UNIT, customerUnit).eq(HrDept.Cols.NAME, deptName)).get(0).getHrDeptPid();
                PmRoster pmRoster = new PmRoster();
                pmRoster.setPmPrjId(projectId);
                pmRoster.setAdUserId(valueUser);
                pmRoster.setCustomerUnit(customerUnit);
                pmRoster.setHrDeptId(deptId);
                pmRoster.setPostInfoId(postId);
                insertList.add(pmRoster);
            }
        }
        if (!CollectionUtils.isEmpty(insertList)) {
            for (PmRoster tmp : insertList) {
                //判断该项目该岗位是否存在，获取id
                String pmPosterId;
                List<PmRoster> list = PmRoster.selectByWhere(new Where().eq(PmRoster.Cols.PM_PRJ_ID, projectId).eq(PmRoster.Cols.POST_INFO_ID, tmp.getPostInfoId()));
                if (CollectionUtils.isEmpty(list)) {
                    pmPosterId = Crud.from("PM_ROSTER").insertData();
                } else {
                    pmPosterId = list.get(0).getId();
                }
                Crud.from("PM_ROSTER").where().eq("ID", pmPosterId).update()
                        .set("PM_PRJ_ID", projectId).set("CUSTOMER_UNIT", customerUnit)
                        .set("POST_INFO_ID", tmp.getPostInfoId()).set("AD_USER_ID", tmp.getAdUserId())
                        .set("HR_DEPT_ID", tmp.getHrDeptId())
                        .exec();
            }
        }
    }

    /**
     * 获取部门名称
     *
     * @param key 流程字段code
     * @return 部门名称
     */
    private static String getDeptNameByCode(String key) {
        String deptName = "";
        for (String tmp : PmRosterExt.postDeptMap.keySet()) {
            if (key.equals(tmp)) {
                deptName = PmRosterExt.postDeptMap.get(tmp);
            }
        }
        return deptName;
    }

    /**
     * 通过流程岗位id和业主单位id查询
     *
     * @param deptId    流程岗位id
     * @param companyId 业主单位id
     * @param projectId 项目id
     * @return 项目岗位人员id
     */
    public static List<String> getDeptUserByDept(List<String> deptId, String companyId, String projectId) {
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> map = new HashMap<>();
        map.put("ids", deptId);
        map.put("companyId", companyId);
        List<String> userList = new ArrayList<>();
        String user;
        String[] arr = projectId.split(",");
        for (String prjId : arr) {
            map.put("prjId", prjId);
            String sql = "select a.AD_USER_ID FROM PM_ROSTER a " +
                    "LEFT JOIN post_info B ON A.POST_INFO_ID = B.ID " +
                    "LEFT JOIN PM_POST_PROPRJ C ON B.ID = C.POST_INFO_ID " +
                    "WHERE a.PM_PRJ_ID = :prjId and a.CUSTOMER_UNIT = :companyId AND C.BASE_PROCESS_POST_ID in (:ids) AND A.STATUS = 'AP' AND B.STATUS = 'AP' AND C.STATUS = 'AP' and a.ad_user_id != '1641281525532323840'";
            List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList(sql, map);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> tmp : list) {
                    user = JdbcMapUtil.getString(tmp, "AD_USER_ID");
                    if (!SharedUtil.isEmptyString(user)) {
                        if (!userList.contains(user)) {
                            userList.add(user);
                        }
                    }
                }

            }
        }
        return userList;
    }

    /**
     * 流程完结后更新项目花名册人员
     *
     * @param rosterList 人员信息
     */
    public static void updatePrjUser(List<PmRoster> rosterList) {
        if (!CollectionUtils.isEmpty(rosterList)) {
            for (PmRoster tmp : rosterList) {
                List<PmRoster> list = PmRoster.selectByWhere(new Where().eq(PmRoster.Cols.PM_PRJ_ID, tmp.getPmPrjId())
                        .eq(PmRoster.Cols.CUSTOMER_UNIT, tmp.getCustomerUnit())
                        .eq(PmRoster.Cols.POST_INFO_ID, tmp.getPostInfoId()));
                String id;
                if (CollectionUtils.isEmpty(list)) {
                    id = Crud.from("PM_ROSTER").insertData();
                } else {
                    id = list.get(0).getId();
                }
                Crud.from("PM_ROSTER").where().eq("id", id).update()
                        .set("CUSTOMER_UNIT", tmp.getCustomerUnit())
                        .set("PM_PRJ_ID", tmp.getPmPrjId())
                        .set("POST_INFO_ID", tmp.getPostInfoId())
                        .set("AD_USER_ID", tmp.getAdUserId())
                        .exec();
            }
        }
    }

    /**
     * 根据花名册信息查询人员所在岗位信息
     *
     * @param userId         人员id
     * @param projectId      项目id
     * @param companyId      业主单位id
     * @param myJdbcTemplate 数据源
     * @return 查询数据结果
     */
    public static Map<String, Object> getUserDeptCodeByRoster(String userId, String projectId, String companyId, MyJdbcTemplate myJdbcTemplate) {
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> list = LinkSql.getUserDeptCodeByRoster(userId, projectId, companyId, myJdbcTemplate);
        if (!CollectionUtils.isEmpty(list)) {
            map.put("deptCode", JdbcMapUtil.getString(list.get(0), "code"));
        }
        return map;
    }


    /**
     * app花名册详情
     */
    public void rosterView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pr.id as id,pi.`NAME` as postName,au.`NAME` as userName,au.code as phone from pm_roster pr left join post_info pi on pr.POST_INFO_ID = pi.id left join ad_user au on pr.AD_USER_ID = au.id where PM_PRJ_ID=? and pr.ad_user_id is not null", map.get("projectId"));
        if (!CollectionUtils.isEmpty(list)) {
            List<ObjInfo> objInfoList = list.stream().map(p -> {
                ObjInfo info = new ObjInfo();
                info.id = JdbcMapUtil.getString(p, "id");
                info.postName = JdbcMapUtil.getString(p, "postName");
                info.userName = JdbcMapUtil.getString(p, "userName");
                info.phone = JdbcMapUtil.getString(p, "phone");
                return info;
            }).collect(Collectors.toList());
            Map<String, List<ObjInfo>> collect = objInfoList.stream().collect(Collectors.groupingBy(m -> m.postName));
            OutSide outSide = new OutSide();
            outSide.collect = collect;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }else{
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
    }


    public static class ObjInfo {
        public String id;

        public String postName;

        public String userName;

        public String phone;
    }
}
