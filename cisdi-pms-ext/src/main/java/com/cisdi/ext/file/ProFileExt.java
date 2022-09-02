package com.cisdi.ext.file;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select id,name,'' as upload_user,round(file_size,2) as size,'' as upload_time ,''as url,'1' as type,PF_FOLDER_PID as pid,'' as ext ,file_count from PF_FOLDER where PF_FOLDER_PID = ? \n" +
                "union all \n" +
                "select fl.id as id,fl.`NAME`as name,pf.CHIEF_USER_ID as upload_user,round(SIZE_KB,2) as size,UPLOAD_DTTM as upload_time,FILE_ATTACHMENT_URL as url ,'2' as type,'' as pid ,ext,0 as file_count \n" +
                "from FL_FILE fl \n" +
                "left join PF_FILE PF on fl.id = pf.FL_FILE_ID \n" +
                "where pf.PF_FOLDER_ID = ?", folderId, folderId);
        List<FileInfo> fileInfoList = list.stream().map(p -> {
            FileInfo fileInfo = new FileInfo();
            fileInfo.id = JdbcMapUtil.getString(p, "ID");
            fileInfo.pid = JdbcMapUtil.getString(p, "pid");
            fileInfo.name = JdbcMapUtil.getString(p, "name");
            fileInfo.uploadUser = JdbcMapUtil.getString(p, "upload_user");
            fileInfo.uploadTime = JdbcMapUtil.getString(p, "upload_time");
            fileInfo.size = JdbcMapUtil.getString(p, "size");
            fileInfo.url = JdbcMapUtil.getString(p, "url");
            fileInfo.type = JdbcMapUtil.getString(p, "type");
            fileInfo.ext = JdbcMapUtil.getString(p, "ext");
            fileInfo.fileCount = JdbcMapUtil.getString(p, "file_count");
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
        public String pid;
        public String name;
        public String uploadUser;
        public String size;
        public String uploadTime;
        public String url;
        public String type;
        public String ext;
        public String fileCount;
        public String fileSize;
    }

    public void collectProFileSize() {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            //查询文件夹
            List<Map<String, Object>> folderList = jdbcTemplate.queryForList("select ID,VER,TS,IS_PRESET,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,LK_WF_INST_ID,CODE,NAME,REMARK,PM_PRJ_ID,SEQ_NO,ifnull(PF_FOLDER_PID,'0') as PF_FOLDER_PID,IS_TEMPLATE,FILE_SIZE,FILE_COUNT " +
                    "from pf_folder where PM_PRJ_ID=?",csCommId);
            //查询所有文件
            List<Map<String, Object>> fileList = jdbcTemplate.queryForList("select fl.ID,fl.CODE,fl.NAME,fl.REMARK,fl.VER,fl.FL_PATH_ID,fl.EXT,fl.LK_WF_INST_ID,fl.STATUS,fl.CRT_DT,fl.CRT_USER_ID,fl.LAST_MODI_DT,fl.LAST_MODI_USER_ID,ifnull(SIZE_KB,0) as SIZE_KB,fl.IS_PRESET,fl.FILE_INLINE_URL," +
                    "fl.FILE_ATTACHMENT_URL,fl.TS,UPLOAD_DTTM,fl.PHYSICAL_LOCATION,fl.DSP_NAME,DSP_SIZE,pff.PF_FOLDER_ID as PF_FOLDER_ID from fl_file fl left join pf_file pff on fl.id = pff.FL_FILE_ID " +
                    "left join pf_folder pfr on pff.PF_FOLDER_ID = pfr.id where  pfr.PM_PRJ_ID=?",csCommId);


            folderList.stream().filter(p -> Objects.equals("0", p.get("PF_FOLDER_PID"))).peek(m -> {
                List<Map<String, Object>> children = getChildren(m, folderList, fileList);
                //获取子级的ID集合
                List<Object> ids = children.stream().map(q -> q.get("ID")).collect(Collectors.toList());
                ids.add(m.get("ID"));
                //取出文件
                List<Map<String, Object>> files = fileList.stream().filter(t -> ids.contains(t.get("PF_FOLDER_ID"))).collect(Collectors.toList());
                BigDecimal totalSize = files.stream().map(n -> new BigDecimal(String.valueOf(n.get("SIZE_KB")))).reduce(BigDecimal.ZERO, BigDecimal::add);
                Crud.from("pf_folder").where().eq("ID", m.get("ID")).update().set("FILE_COUNT", children.size()).set("FILE_SIZE", totalSize).exec();
            }).collect(Collectors.toList());
        }
    }

    private List<Map<String, Object>> getChildren(Map<String, Object> root, List<Map<String, Object>> allData, List<Map<String, Object>> fileData) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(root.get("ID")), String.valueOf(p.get("PF_FOLDER_PID")))).peek(m -> {
            List<Map<String, Object>> children = getChildren(m, allData, fileData);
            //获取子级的ID集合
            List<Object> ids = children.stream().map(q -> q.get("ID")).collect(Collectors.toList());
            ids.add(m.get("ID"));
            //取出文件
            List<Map<String, Object>> files = fileData.stream().filter(t -> ids.contains(t.get("PF_FOLDER_ID"))).collect(Collectors.toList());
            BigDecimal totalSize = files.stream().map(n -> new BigDecimal(String.valueOf(n.get("SIZE_KB")))).reduce(BigDecimal.ZERO, BigDecimal::add);
            Crud.from("pf_folder").where().eq("ID", m.get("ID")).update().set("FILE_COUNT", children.size()).set("FILE_SIZE", totalSize).exec();
        }).collect(Collectors.toList());
    }
}
