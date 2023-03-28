package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmPostExt
 * @package com.cisdi.ext.pm
 * @description 项目岗位配置
 * @date 2023/2/9
 */
public class PmPostExt {

    /**
     * 业主单位列表
     */
    public void customerUnit() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        StringBuilder sb = new StringBuilder();
        sb.append("select * from PM_PARTY where status='ap' and IS_CUSTOMER='1' ");
        if (Objects.nonNull(map.get("name"))) {
            sb.append(" and name like '%").append(map.get("name")).append("%'");
        }
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<Party> partyList = list.stream().map(p -> {
            Party party = new Party();
            party.id = JdbcMapUtil.getString(p, "ID");
            party.name = JdbcMapUtil.getString(p, "NAME");
            return party;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(partyList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.partyList = partyList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 默认岗位配置查询
     */
    public void pmPostList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String unit = JdbcMapUtil.getString(map, "unitId");
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from post_info where SYS_TRUE='1' and CUSTOMER_UNIT =? ", unit);
        List<Post> postList = list.stream().map(p -> {
            Post post = new Post();
            post.id = JdbcMapUtil.getString(p, "ID");
            post.name = JdbcMapUtil.getString(p, "NAME");
            return post;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(postList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.postList = postList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 新增/修改默认岗位配置
     */
    public void pmPostModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        String unit = JdbcMapUtil.getString(map, "unitId");
        if (StringUtils.isEmpty(id)) {
            id = Crud.from("post_info").insertData();
        }
        Crud.from("post_info").where().eq("ID", id).update()
                .set("NAME", map.get("name")).set("SYS_TRUE", "1").set("CUSTOMER_UNIT", unit).exec();
    }

    /**
     * 删除默认岗位配置
     */
    public void delPmPost() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from post_info where id=?", id);
    }


    /**
     * 项目岗位列表查询
     */
    public void projectPostList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String projectName = String.valueOf(map.get("projectName"));
        StringBuilder sb = new StringBuilder();
        sb.append("select pj.id as id, pj.`NAME` as project_name,pt.`name` as unit,GROUP_CONCAT(po.`NAME`) as post from pm_prj pj \n" +
                "left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID \n" +
                "left join post_info po on po.id = pp.POST_INFO_ID \n" +
                "left join PM_PARTY pt on pt.id = pj.CUSTOMER_UNIT " +
                "where pj.`STATUS`='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374' ");
        if (!StringUtils.isEmpty(projectName)) {
            sb.append(" and pj.`NAME` like '%").append(projectName).append("%'");
        }
        sb.append("group by pj.id order by pj.pm_code desc");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<ProjectPost> projectPostList = list.stream().map(p -> {
            ProjectPost ps = new ProjectPost();
            ps.projectId = JdbcMapUtil.getString(p, "id");
            ps.projectName = JdbcMapUtil.getString(p, "project_name");
            ps.unit = JdbcMapUtil.getString(p, "unit");
            ps.postName = JdbcMapUtil.getString(p, "post");
            return ps;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(projectPostList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql);
            OutSide outSide = new OutSide();
            outSide.total = totalList.size();
            outSide.projectPostList = projectPostList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 编辑详情
     */
    public void projectPostView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select pr.id as id,po.`NAME` as PROJECT_POST,PM_PRJ_ID,post_info_id from PM_ROSTER pr \n" +
                "left join post_info po on pr.POST_INFO_ID = po.id where PM_PRJ_ID =?", projectId);
        List<ProjectPostView> projectPostViewList = list.stream().map(p -> {
            ProjectPostView view = new ProjectPostView();
            view.id = JdbcMapUtil.getString(p, "ID");
            view.postId = JdbcMapUtil.getString(p, "post_info_id");
            view.postName = JdbcMapUtil.getString(p, "PROJECT_POST");
            view.projectId = JdbcMapUtil.getString(p, "PM_PRJ_ID");
            return view;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(projectPostViewList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.projectPostViewList = projectPostViewList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 新增/编辑项目岗位
     */
    public void projectPostModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String postName = String.valueOf(map.get("postName"));
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //先根据岗位名称查询，是否存在这个岗位
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from post_info where name=?", postName);
        String postId = null;
        if (CollectionUtils.isEmpty(list)) {
            //如果不存在，新增一个岗位
            postId = Crud.from("post_info").insertData();
            Crud.from("post_info").where().eq("ID", postId).update().set("NAME", postName).set("SYS_TRUE", "0").exec();
        } else {
            Map<String, Object> dataMap = list.get(0);
            postId = JdbcMapUtil.getString(dataMap, "ID");
            //把岗位提升为默认岗位
            Crud.from("post_info").where().eq("ID", postId).update().set("SYS_TRUE", "1").exec();
        }
        String id = String.valueOf(map.get("id"));
        if (StringUtils.isEmpty(id)) {
            id = Crud.from("PM_ROSTER").insertData();
        }
        Crud.from("PM_ROSTER").where().eq("ID", id).update().set("POST_INFO_ID", postId).set("PM_PRJ_ID", projectId).exec();
    }

    /**
     * 删除项目岗位
     */
    public void delProjectPost() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PM_ROSTER where id=?", id);
    }

    public static class Post {
        public String id;
        public String name;
    }

    public static class OutSide {

        public Integer total;
        public List<Post> postList;

        public List<ProjectPost> projectPostList;

        public List<ProjectPostView> projectPostViewList;

        public List<Party> partyList;
    }

    public static class ProjectPost {
        public String projectId;

        public String projectName;

        public String unit;

        public String postName;

    }

    public static class ProjectPostView {
        public String id;
        public String postId;
        public String postName;
        public String projectId;
    }

    public static class Party {
        public String id;
        public String name;
    }
}
