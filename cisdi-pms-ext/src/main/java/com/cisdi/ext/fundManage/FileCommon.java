package com.cisdi.ext.fundManage;

import com.cisdi.ext.model.view.File;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

public class FileCommon {

    /**
     *
     * @param fileIdStr 以逗号连接fileId
     * @return
     */
    public static List<File> getFileResp(String fileIdStr,JdbcTemplate jdbcTemplate){
        if (Strings.isNullOrEmpty(fileIdStr)){
            return null;
        }
        NamedParameterJdbcTemplate namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate.getDataSource());
        Map<String, Object> queryParams = new HashMap<>();//创建入参map
        List<String> ids = Arrays.asList(fileIdStr.split(","));
        queryParams.put("ids", ids);
        String sql = "select id,DSP_NAME name,DSP_SIZE size,UPLOAD_DTTM uploadTime,FILE_INLINE_URL viewUrl,FILE_ATTACHMENT_URL downUrl from fl_file " +
                "where id in (:ids)";
        List<Map<String, Object>> fileList = namedParameterJdbcTemplate.queryForList(sql, queryParams);
        List<File> files = new ArrayList<>();
        for (Map<String, Object> fileMap : fileList) {
            File file = new File();
            file.id = JdbcMapUtil.getString(fileMap,"id");
            file.name = JdbcMapUtil.getString(fileMap,"name");
            file.size = JdbcMapUtil.getString(fileMap,"size");
            file.uploadTime = StringUtil.withOutT(JdbcMapUtil.getString(fileMap,"uploadTime"));
            file.viewUrl = JdbcMapUtil.getString(fileMap,"viewUrl");
            file.downUrl = JdbcMapUtil.getString(fileMap,"downUrl");
            files.add(file);
        }
        return files;
    }
}
