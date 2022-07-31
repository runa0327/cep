package com.cisdi.ext.demo;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;

@Slf4j
public class CrudExt {
    public void insertExt() {
//        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
//        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
//        for (EntityRecord entityRecord : entityRecordList) {
//            String csCommId = entityRecord.csCommId;
//            int update = jdbcTemplate.update("update PM_PRJ_INVEST3 t set t.CONSTRUCT_DAYS  = t.CONSTRUCT_DAYS+100 where t.id=?", csCommId);
//            log.info("已更新：{}", update);
//        }
    }
}
