package com.cisdi.ext.home;

import com.cisdi.ext.pm.PmChangeExt;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title RecentUseExt
 * @package com.cisdi.ext.home
 * @description 首页最近使用
 * @date 2023/2/8
 */
public class RecentUseExt {

    /**
     * 记录当前使用
     */
    public void recordRecentUse() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from RECENT_USE where DSP_NAME=?", map.get("menuName"));
        if (!CollectionUtils.isEmpty(list)) {
            List<String> ids = list.stream().map(p -> String.valueOf(p.get("id"))).collect(Collectors.toList());
            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
            Map<String, Object> queryParams = new HashMap<>();// 创建入参map
            queryParams.put("ids", ids);
            myNamedParameterJdbcTemplate.update("delete from RECENT_USE where id in (:ids)", queryParams);
        }

        String id = Crud.from("RECENT_USE").insertData();
        Crud.from("RECENT_USE").where().eq("ID", id).update()
                .set("DSP_NAME", map.get("menuName")).set("AD_USER_ID", map.get("userId")).set("MENU_TYPE", map.get("type"))
                .set("RECORDED_VALUE", map.get("recordValue")).set("CRT_DT", new Date()).set("NAME",map.get("name")).set("OTHER",map.get("other"))
                .exec();
    }

    /**
     * 最近使用列表
     */
    public void recentUseList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from RECENT_USE where AD_USER_ID = ? order by CRT_DT desc limit 0,10 ", map.get("userId"));
        List<InputData> dataList = list.stream().map(p -> {
            InputData data = new InputData();
            data.userId = JdbcMapUtil.getString(p, "AD_USER_ID");
            data.type = JdbcMapUtil.getString(p, "MENU_TYPE");
            data.recordValue = JdbcMapUtil.getString(p, "RECORDED_VALUE");
            data.menuName = JdbcMapUtil.getString(p, "DSP_NAME");
            data.name = JdbcMapUtil.getString(p, "NAME");
            data.other = JdbcMapUtil.getString(p, "OTHER");
            return data;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(dataList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.dataList = dataList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    public static class InputData {
        public String userId;

        public String type;

        public String recordValue;

        public String menuName;

        public String name;

        public String other;

    }

    public static class OutSide {
        public List<InputData> dataList;
    }
}
