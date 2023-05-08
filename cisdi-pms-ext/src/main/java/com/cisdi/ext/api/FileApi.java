package com.cisdi.ext.api;

import com.cisdi.ext.model.view.file.BaseFileView;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FileApi {

    /**
     * 查询文件信息-扩展
     */
    public void getFileList() {
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        BaseFileView param = JsonUtil.fromJson(json, BaseFileView.class);
        String fileId = param.fileId;
        if (SharedUtil.isEmptyString(fileId)) {
            throw new BaseException("接口调用失败，文件id不能为空");
        }
        fileId = StringUtil.codeToSplit(fileId);

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select a.id,a.DSP_NAME,a.SIZE_KB,a.PHYSICAL_LOCATION,a.UPLOAD_DTTM,a.CRT_USER_ID,b.NAME AS userName,A.DSP_SIZE AS DSP_SIZE " +
                "from fl_file a left join ad_user b on a.CRT_USER_ID = b.id where a.id in ('" + fileId + "')";
        List<Map<String, Object>> fileList = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(fileList)) {
            List<BaseFileView> baseFile = fileList.stream().map(q -> {
                BaseFileView baseFileView = new BaseFileView();
                baseFileView.id = JdbcMapUtil.getString(q, "id");
                baseFileView.fileName = JdbcMapUtil.getString(q, "DSP_NAME");// 显示文件名称
                baseFileView.fileSize = JdbcMapUtil.getString(q, "SIZE_KB");// 文件大小
                baseFileView.fileAddress = JdbcMapUtil.getString(q, "PHYSICAL_LOCATION");// 文件位置
                baseFileView.uploadTime = JdbcMapUtil.getString(q, "UPLOAD_DTTM").replace("T", " ");// 上传时间
                baseFileView.uploadById = JdbcMapUtil.getString(q, "CRT_USER_ID");// 上传人id
                baseFileView.uploadByName = JdbcMapUtil.getString(q, "userName");
                return baseFileView;
            }).collect(Collectors.toList());
            Map<String, Object> map1 = new HashMap<>();
            map1.put("result", baseFile);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(map1), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(null);
        }
    }

    /**
     * 查询获取文件信息-外部调用
     *
     * @param str str
     * @return list
     */
    public static List<BaseFileView> getFileList(String str) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql = "select a.id,a.DSP_NAME,a.SIZE_KB,a.PHYSICAL_LOCATION,a.UPLOAD_DTTM,a.CRT_USER_ID,b.NAME AS userName,A.DSP_SIZE AS DSP_SIZE " +
                "from fl_file a left join ad_user b on a.CRT_USER_ID = b.id where a.id in ('" + str + "')";
        List<Map<String, Object>> fileList = myJdbcTemplate.queryForList(sql);
        List<BaseFileView> baseFile = new ArrayList<>();
        if (!CollectionUtils.isEmpty(fileList)) {
            baseFile = fileList.stream().map(q -> {
                BaseFileView baseFileView = new BaseFileView();
                baseFileView.id = JdbcMapUtil.getString(q, "id");
                baseFileView.fileName = JdbcMapUtil.getString(q, "DSP_NAME");// 显示文件名称
                baseFileView.fileSize = JdbcMapUtil.getString(q, "SIZE_KB");// 文件大小
                baseFileView.dspSize = JdbcMapUtil.getString(q, "DSP_SIZE");// 文件大小
                baseFileView.fileAddress = JdbcMapUtil.getString(q, "PHYSICAL_LOCATION");// 文件位置
                baseFileView.uploadTime = JdbcMapUtil.getString(q, "UPLOAD_DTTM").replace("T", " ");// 上传时间
                baseFileView.uploadById = JdbcMapUtil.getString(q, "CRT_USER_ID");// 上传人id
                baseFileView.uploadByName = JdbcMapUtil.getString(q, "userName");
                return baseFileView;
            }).collect(Collectors.toList());
        }
        return baseFile;
    }
}
