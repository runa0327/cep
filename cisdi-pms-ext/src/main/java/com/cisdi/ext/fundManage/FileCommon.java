package com.cisdi.ext.fundManage;

import com.cisdi.ext.model.view.File;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.*;

public class FileCommon {

    /**
     * @param fileIdStr 以逗号连接fileId
     * @return
     */
    public static List<File> getFileResp(String fileIdStr, MyJdbcTemplate myJdbcTemplate) {
        if (Strings.isNullOrEmpty(fileIdStr)) {
            return null;
        }
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        List<String> ids = Arrays.asList(fileIdStr.split(","));
        queryParams.put("ids", ids);
        String sql = "select f.id,f.DSP_NAME name,f.DSP_SIZE size,f.UPLOAD_DTTM uploadTime,f.FILE_INLINE_URL viewUrl,f.FILE_ATTACHMENT_URL downUrl,u.name username from fl_file f " +
                "left join ad_user u on u.id = f.CRT_USER_ID where f.id in (:ids)";
        List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList(sql, queryParams);
        List<File> files = new ArrayList<>();
        for (Map<String, Object> fileMap : fileList) {
            File file = new File();
            file.id = JdbcMapUtil.getString(fileMap, "id");
            file.name = JdbcMapUtil.getString(fileMap, "name");
            file.size = JdbcMapUtil.getString(fileMap, "size");
            file.uploadTime = StringUtil.withOutT(JdbcMapUtil.getString(fileMap, "uploadTime"));
            file.viewUrl = JdbcMapUtil.getString(fileMap, "viewUrl");
            file.downUrl = JdbcMapUtil.getString(fileMap, "downUrl");
            file.username =JdbcMapUtil.getString(fileMap,"username");
            files.add(file);
        }
        return files;
    }
}
