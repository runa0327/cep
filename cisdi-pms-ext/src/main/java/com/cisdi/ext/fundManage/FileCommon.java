package com.cisdi.ext.fundManage;

import com.google.common.base.Strings;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileCommon {

    /**
     *
     * @param fileIdStr 以逗号连接fileId
     * @return
     */
    public static List<Map<String,Object>> getFileResp(String fileIdStr,JdbcTemplate jdbcTemplate){
        if (Strings.isNullOrEmpty(fileIdStr)){
            return null;
        }
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        Map<String, Object> queryParams = new HashMap<>();//创建入参map
        List<String> ids = Arrays.asList(fileIdStr.split(","));
        queryParams.put("ids", ids);
        String sql = "select DSP_NAME name,DSP_SIZE size,UPLOAD_DTTM uploadTime,FILE_INLINE_URL viewUrl,FILE_ATTACHMENT_URL downUrl from fl_file " +
                "where id in (:ids)";
        List<Map<String, Object>> fileList = namedParameterJdbcTemplate.queryForList(sql, queryParams);
        return fileList;
    }
}
