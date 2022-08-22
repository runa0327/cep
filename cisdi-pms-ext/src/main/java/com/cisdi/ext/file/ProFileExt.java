package com.cisdi.ext.file;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProFile
 * @package com.cisdi.ext.file
 * @description
 * @date 2022/8/19
 */
public class ProFileExt {

    /**
     * 获取左侧资料类型树
     */
    public void getFolder() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        ReqParam param = JsonUtil.fromJson(json, ReqParam.class);
        String projectId = param.pmPrjId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select ID,`CODE`,`NAME`,REMARK,PM_PRJ_ID,SEQ_NO,PF_FOLDER_PID from pf_folder where PF_FOLDER_PID is null and IS_TEMPLATE = '0' and  PM_PRJ_ID=? order by SEQ_NO", projectId);
        List<FolderInfo> folderList = list.stream().map(p -> {
            FolderInfo f = new FolderInfo();
            f.id = JdbcMapUtil.getString(p, "ID");
            f.name = JdbcMapUtil.getString(p, "NAME");
            f.pmPrjId = JdbcMapUtil.getString(p, "PM_PRJ_ID");
            f.pid = JdbcMapUtil.getString(p, "PF_FOLDER_PID") == null ? "0" : JdbcMapUtil.getString(p, "PF_FOLDER_PID");
            f.seqNo = JdbcMapUtil.getString(p, "SEQ_NO");
            return f;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(folderList)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            outSide outSide = new outSide();
            outSide.folderInfoList = folderList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 查询文件
     */
    public void getProjectFileList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        FileReqParam param = JsonUtil.fromJson(json, FileReqParam.class);
        String folderId = param.pfFolderId;
        //查询文件夹及其文件
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        // TODO 加过滤查询条件
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,name,'' as upload_user,'' as size,'' as upload_time ,''as url,'1' as type from PF_FOLDER where PF_FOLDER_PID = ? \n" +
                "union all \n" +
                "select fl.id as id,fl.`NAME`as name,pf.CHIEF_USER_ID as upload_user,SIZE_KB as size,UPLOAD_DTTM as upload_time,FILE_ATTACHMENT_URL as url ,'2' as type\n" +
                "from FL_FILE fl \n" +
                "left join PF_FILE PF on fl.id = pf.FL_FILE_ID \n" +
                "where pf.PF_FOLDER_ID = ?", folderId, folderId);
        List<FileInfo> fileInfoList = list.stream().map(p -> {
            FileInfo fileInfo = new FileInfo();
            fileInfo.id = JdbcMapUtil.getString(p, "ID");
            fileInfo.name = JdbcMapUtil.getString(p, "name");
            fileInfo.uploadUser = JdbcMapUtil.getString(p, "upload_user");
            String uploadTime = JdbcMapUtil.getString(p, "upload_time");
            fileInfo.uploadTime = "".equals(uploadTime) ? null : DateTimeUtil.stringToTime(uploadTime);
            fileInfo.size = JdbcMapUtil.getString(p, "size");
            fileInfo.url = JdbcMapUtil.getString(p, "url");
            fileInfo.type = JdbcMapUtil.getString(p, "type");
            return fileInfo;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(fileInfoList)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            outSide outSide = new outSide();
            outSide.fileInfoList = fileInfoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class ReqParam {
        public String pmPrjId;
    }

    public static class FileReqParam {
        public String pfFolderId;
    }

    public static class outSide {
        public List<FolderInfo> folderInfoList;

        public List<FileInfo> fileInfoList;
    }

    /**
     * 文件夹
     */
    public static class FolderInfo {
        public String id;
        public String name;
        public String pmPrjId;
        public String pid;
        public String seqNo;
    }

    /**
     * 文件
     */
    public static class FileInfo {
        public String id;
        public String name;
        public String uploadUser;
        public String size;
        public Date uploadTime;
        public String url;
        public String type;
    }
}
