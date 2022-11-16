package com.cisdi.ext.pm;

import com.cisdi.ext.fund.FundReachApi;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.logging.log4j.util.Strings;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
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

    //项目部门人员列表查询
    public void pmDeptList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_DEPT where PM_PRJ_ID=?", projectId);
        List<PmDeptInput> pmDeptInputList = list.stream().map(p -> {
            PmDeptInput input = new PmDeptInput();
            input.id = JdbcMapUtil.getString(p, "ID");
            input.projectId = JdbcMapUtil.getString(p, "PM_PRJ_ID");
            input.deptId = JdbcMapUtil.getString(p, "HR_DEPT_ID");
            input.userIds = JdbcMapUtil.getString(p, "USER_IDS");
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

    //项目部门人员详情
    public void pmDeptView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select * from PM_DEPT where id=?", id);
            PmDeptInput input = new PmDeptInput();
            input.id = JdbcMapUtil.getString(stringObjectMap, "ID");
            input.projectId = JdbcMapUtil.getString(stringObjectMap, "PM_PRJ_ID");
            input.deptId = JdbcMapUtil.getString(stringObjectMap, "HR_DEPT_ID");
            input.userIds = JdbcMapUtil.getString(stringObjectMap, "USER_IDS");
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(input), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            throw new BaseException("查询数据错误");
        }
    }


    //项目部门人员删除
    public void pmDeptDel() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PM_DEPT where id = ?", id);
    }


    public static class PmDeptInput {
        public String id;
        //项目ID
        public String projectId;
        //部门ID
        public String deptId;
        //多选用户ID
        public String userIds;

    }

    public static class outSide {
        public List<PmDeptInput> pmDeptInputList;
    }

}
