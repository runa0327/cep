package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmChangeExt
 * @package com.cisdi.ext.pm
 * @description 项目名称/业主变更
 * @date 2022/11/23
 */
public class PmChangeExt {

    /**
     * 项目名称/业主变更
     */
    public void changData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputData input = JsonUtil.fromJson(json, InputData.class);
        String projectId = input.projectId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRJ where id=?", projectId);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("数据错误，项目不存在！");
        }
        String id = Crud.from("PM_CHANGE_RECORD").insertData();
        String oldData = "";
        String sql = "";
        if (Objects.equals("1", input.type)) {
            oldData = String.valueOf(list.get(0).get("NAME"));
            sql = "update PM_PRJ set NAME=? where id=?";
        } else {
            oldData = String.valueOf(list.get(0).get("CUSTOMER_UNIT"));
            sql = "update PM_PRJ set CUSTOMER_UNIT=? where id=?";
        }
        Crud.from("PM_CHANGE_RECORD").where().eq("ID", id).update()
                .set("PM_PRJ_ID", projectId).set("CHANGE_WAY", input.type).set("NEW_RECORD", input.changeData).set("OLD_RECORD", oldData).set("CHANGE_DATE", new Date()).exec();
        //修改项目信息
        myJdbcTemplate.update(sql, input.changeData, projectId);
    }


    /**
     * 合作方下拉
     */
    public void getCustomerUnit() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PARTY where `status`='ap'");
        List<PmParty> pmPartyList = list.stream().map(p -> {
            PmParty pmParty = new PmParty();
            pmParty.id = JdbcMapUtil.getString(p, "ID");
            pmParty.name = JdbcMapUtil.getString(p, "NAME");
            return pmParty;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(pmPartyList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.partyList = pmPartyList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class InputData {
        public String projectId;

        public String changeData;

        /**
         * 类型 1-项目名称 2-业主
         */
        public String type;
    }

    public static class PmParty {
        public String id;
        public String name;
    }

    public static class OutSide {
        public List<PmParty> partyList;
    }

}
