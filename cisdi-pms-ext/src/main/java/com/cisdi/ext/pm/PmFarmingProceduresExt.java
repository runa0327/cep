package com.cisdi.ext.pm;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * 农转用手续办理扩展
 */
public class PmFarmingProceduresExt {

    //选择否办理，需要上传附件
    public void checkFile(){
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //获取是否需要办理字段  是：99799190825080669   否：99799190825080670
        String value = entityRecord.valueMap.get("YES_NO_ONE").toString();
        if ("99799190825080670".equals(value)){
            //查询文件信息
            String file = JdbcMapUtil.getString(entityRecord.valueMap,"ATT_FILE_GROUP_ID");
            if (SharedUtil.isEmptyString(file)){
                throw new BaseException("‘是否需要办理’选否，需要上传附件！");
            }
        }

    }

}
