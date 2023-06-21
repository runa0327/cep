package com.cisdi.ext.file;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.api.FileApi;
import com.cisdi.ext.model.FlFile;
import com.cisdi.ext.model.view.file.BaseFileView;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseFileExt {

    /**
     * 获取文件信息
     */
    public void getFileInfos(){
        Map<String, Object> input = ExtJarHelper.extApiParamMap.get();
        FileReq req = JSONObject.parseObject(JSONObject.toJSONString(input), FileReq.class);
        if (CollectionUtils.isEmpty(req.fileGroupIds)){
            throw new BaseException("需要文件id！");
        }
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        List<Map<String, Object>> fileMaps = myNamedParameterJdbcTemplate.queryForList("select f.id,f.code,f.FILE_ATTACHMENT_URL attachmentUrl,u.code " +
                "crtUserCode,u.name crtUserName,f" +
                ".DSP_NAME dspName,f.DSP_SIZE dspSize,f.ext,f.FILE_INLINE_URL inlineUrl,f.IS_PUBLIC_READ isPublicRead,f.name,f.SIZE_KB " +
                "sizeKiloByte,f.UPLOAD_DTTM uploadDttm\n" +
                "from fl_file f left join ad_user u on u.id = f.CRT_USER_ID where f.id in (:fileGroupIds)", input);
        List<FileResp> fileResps = null;
        if (!CollectionUtils.isEmpty(fileMaps)){
            fileResps = JSONObject.parseArray(JSONObject.toJSONString(fileMaps), FileResp.class);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("fileResps",fileResps);
        Map output = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(output);
    }

    /**
     * 文件信息获取
     * @param fileId 文件id
     * @return 文件详细信息列表
     */
    public static List<BaseFileView> getFile(String fileId) {
        fileId = StringUtil.replaceCode(fileId,",","','");
        return FileApi.getFileList(fileId);
    }

    @Data
    private static class FileReq{
        private List<String> fileGroupIds;
    }
    @Data
    private static class FileResp{
        private String id;
        private String code;
        private String attachmentUrl;
        private String crtUserCode;
        private String dspName;
        private String dspSize;
        private String ext;
        private String inlineUrl;
        private Boolean isPublicRead;
        private String name;
        private BigDecimal sizeKiloByte;
        private String uploadDttm;

        public String getUploadDttm() {
            this.uploadDttm = StringUtil.withOutT(this.uploadDttm);
            return uploadDttm;
        }
    }
}
