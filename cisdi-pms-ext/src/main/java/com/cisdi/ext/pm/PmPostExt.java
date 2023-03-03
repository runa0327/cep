package com.cisdi.ext.pm;

import com.cisdi.ext.fund.FundReachApi;
import com.cisdi.ext.home.WorkScheduleExt;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.coyote.OutputBuffer;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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
     * 项目岗位配置查询
     */
    public void pmPostList() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select gsv.* from gr_set_value gsv left join gr_set gs on gs.id = gsv.GR_SET_ID where gs.`CODE`='project_post'");
        List<Post> postList = list.stream().map(p -> {
            Post post = new Post();
            post.id = JdbcMapUtil.getString(p, "ID");
            post.name = JdbcMapUtil.getString(p, "NAME");
            post.grSetId = JdbcMapUtil.getString(p, "GR_SET_ID");
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
     * 新增/修改项目部门配置
     */
    public void pmPostModify() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        if (StringUtils.isEmpty(id)) {
            id = Crud.from("gr_set_value").insertData();
        }
        Crud.from("gr_set_value").where().eq("ID", id).update()
                .set("NAME", map.get("name")).set("GR_SET_ID", map.get("grSetId")).exec();
    }

    /**
     * 删除项目部门配置
     */
    public void delPmPost() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from gr_set_value where id=?", id);
    }


    /**
     * 项目岗位列表查询
     */
    public void projectPostList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        int pageSize = Integer.parseInt(String.valueOf(map.get("pageSize")));
        int pageIndex = Integer.parseInt(String.valueOf(map.get("pageIndex")));
        String projectName = String.valueOf(map.get("projectName"));
        StringBuffer sb = new StringBuffer();
        sb.append("select pj.id as id, pj.`NAME` as project_name,GROUP_CONCAT(pp.PROJECT_POST) as post from pm_prj pj " +
                "left join PM_ROSTER pp on pj.id = pp.PM_PRJ_ID where pj.`STATUS`='ap' and PROJECT_SOURCE_TYPE_ID = '0099952822476441374'");
        if (!StringUtils.isEmpty(projectName)) {
            sb.append(" and pj.`NAME` like '%").append(projectName).append("%'");
        }
        sb.append("group by pj.id ");
        String totalSql = sb.toString();
        int start = pageSize * (pageIndex - 1);
        sb.append(" limit ").append(start).append(",").append(pageSize);
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<ProjectPost> projectPostList = list.stream().map(p -> {
            ProjectPost ps = new ProjectPost();
            ps.projectId = JdbcMapUtil.getString(p, "id");
            ps.projectName = JdbcMapUtil.getString(p, "project_name");
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
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_ROSTER where PM_PRJ_ID =?", projectId);
        List<ProjectPostView> projectPostViewList = list.stream().map(p -> {
            ProjectPostView view = new ProjectPostView();
            view.id = JdbcMapUtil.getString(p, "ID");
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
        String id = String.valueOf(map.get("id"));
        if (StringUtils.isEmpty(id)) {
            id = Crud.from("PM_ROSTER").insertData();
        }
        Crud.from("PM_ROSTER").where().eq("ID", id).update()
                .set("PROJECT_POST", postName).set("PM_PRJ_ID", projectId).exec();

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
        public String grSetId;
    }

    public static class OutSide {

        public Integer total;
        public List<Post> postList;

        public List<ProjectPost> projectPostList;

        public List<ProjectPostView> projectPostViewList;
    }

    public static class ProjectPost {
        public String projectId;

        public String projectName;

        public String postName;

    }

    public static class ProjectPostView {
        public String id;
        public String postName;
        public String projectId;
    }
}
