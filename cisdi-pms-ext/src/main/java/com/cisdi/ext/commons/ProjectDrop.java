package com.cisdi.ext.commons;

import com.cisdi.ext.model.view.CommonDrop;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.*;

public class ProjectDrop {
    // 获取项目名称下拉框
    public void getPrjDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String prjName = Objects.isNull(inputMap.get("prjName")) ? null : inputMap.get("prjName").toString();

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select name,id from pm_prj where STATUS = 'AP' ");
        if (!Strings.isNullOrEmpty(prjName)) {
            baseSql.append("and name like '%" + prjName + "%' ");
        }
        baseSql.append("order by CRT_DT desc ");

        List<Map<String, Object>> projects = myJdbcTemplate.queryForList(baseSql.toString());
        List<CommonDrop> commonDrops = new ArrayList<>();
        for (Map<String, Object> project : projects) {
            CommonDrop projectDrop = new CommonDrop();
            projectDrop.id = JdbcMapUtil.getString(project, "id");
            projectDrop.name = JdbcMapUtil.getString(project, "name");
            commonDrops.add(projectDrop);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("projects", commonDrops);
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);

    }
}
