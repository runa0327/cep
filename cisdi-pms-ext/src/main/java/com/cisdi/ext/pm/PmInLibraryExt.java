package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 各合作商入库
 */

public class PmInLibraryExt {

    /**
     * 评审组织单位-入库
     */
    public void reviewInLibrary(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);

        //获取评审单位
        String reviewName = JdbcMapUtil.getString(entityRecord.valueMap,"REVIEW_ORGANIZATION_UNIT");
        if (!SharedUtil.isEmptyString(reviewName)){ //写入合作方
            //查询是否存在该单位
            String sql1 = "select id,name,IS_REVIEW from PM_PARTY where name = ?";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,reviewName);
            if (CollectionUtils.isEmpty(list1)){
                String id = Crud.from("PM_PARTY").insertData();
                Crud.from("PM_PARTY").where().eq("id",id).update()
                        .set("name",reviewName)
                        .set("IS_REVIEW","1")
                        .exec();
            } else {
                String res = JdbcMapUtil.getString(list1.get(0),"IS_REVIEW");
                if ( SharedUtil.isEmptyString(res) || !"1".equals(res)){
                    String id = JdbcMapUtil.getString(list1.get(0),"id");
                    Crud.from("PM_PARTY").where().eq("id",id).update()
                            .set("IS_REVIEW","1")
                            .exec();
                }
            }

        }
    }
}
