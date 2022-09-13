package com.cisdi.ext.util;

import com.cisdi.ext.enums.FileCodeEnum;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PrFileUtils
 * @package com.cisdi.ext.util
 * @description 文件通用方法类
 * @date 2022/8/25
 */
public class ProFileUtils {

    /**
     * 新增项目资料文件夹层级
     */
    public static void createFolder(String projectId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 查询已经有的文件夹
        List<Map<String, Object>> folderList = myJdbcTemplate.queryForList("select * from PF_FOLDER where  PM_PRJ_ID=?", projectId);

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select ID,`CODE`,`NAME`,REMARK,PM_PRJ_ID,SEQ_NO,ifnull(PF_FOLDER_PID,'0') as PF_FOLDER_PID from PF_FOLDER where IS_TEMPLATE ='1';");
        // 新增项目文件夹目录
        list.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PF_FOLDER_PID")))).peek(m -> {
            String id = "";
            Optional<Map<String, Object>> optional = folderList.stream().filter(o -> Objects.equals(String.valueOf(m.get("CODE")), String.valueOf(o.get("CODE")))).findAny();
            if (optional.isPresent()) {
                id = String.valueOf(optional.get().get("ID"));
            } else {
                id = Crud.from("PF_FOLDER").insertData();
                Crud.from("PF_FOLDER").where().eq("ID", id).update().set("PM_PRJ_ID", projectId).set("NAME", m.get("NAME"))
                        .set("SEQ_NO", m.get("SEQ_NO")).set("CODE", m.get("CODE")).set("IS_TEMPLATE", "0").exec();
            }
            createSonFolder(m, list, id, projectId, folderList);
        }).collect(Collectors.toList());
    }

    public static List<Map<String, Object>> createSonFolder(Map<String, Object> root, List<Map<String, Object>> allData, String pid, String projectId, List<Map<String, Object>> folderList) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(root.get("ID")), String.valueOf(p.get("PF_FOLDER_PID")))).peek(m -> {
            String id = "";
            Optional<Map<String, Object>> optional = folderList.stream().filter(o -> Objects.equals(String.valueOf(m.get("CODE")), String.valueOf(o.get("CODE")))).findAny();
            if (optional.isPresent()) {
                id = String.valueOf(optional.get().get("ID"));
            } else {
                id = Crud.from("PF_FOLDER").insertData();
                Crud.from("PF_FOLDER").where().eq("ID", id).update().set("PM_PRJ_ID", projectId).set("NAME", m.get("NAME"))
                        .set("SEQ_NO", m.get("SEQ_NO")).set("CODE", m.get("CODE")).set("IS_TEMPLATE", "0").set("PF_FOLDER_PID", pid).exec();
            }
            createSonFolder(m, allData, id, projectId, folderList);
        }).collect(Collectors.toList());
    }


    /**
     * 流程审批中的文件进行归档
     *
     * @param projectId
     * @param fileIds
     * @param codeEnum
     */
    public static void insertProFile(String projectId, String fileIds, FileCodeEnum codeEnum) {
        if (Strings.isNullOrEmpty(fileIds)) {
            return;
        }
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            String fid = "";
            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from pf_folder where PM_PRJ_ID=?", projectId);
            List<Map<String, Object>> folderList = myJdbcTemplate.queryForList("select `CODE`,`NAME`,REMARK,PM_PRJ_ID,SEQ_NO,ifnull(PF_FOLDER_PID,'0') as PF_FOLDER_PID from PF_FOLDER where IS_TEMPLATE ='1';");
            if (CollectionUtils.isEmpty(list) || folderList.size() != list.size()) {
                ProFileUtils.createFolder(projectId);
                Map<String, Object> map = myJdbcTemplate.queryForMap("select * from pf_folder where PM_PRJ_ID=? and `CODE`=?", projectId, codeEnum.getCode());
                fid = String.valueOf(map.get("ID"));
            } else {
                Optional<Map<String, Object>> obj = list.stream().filter(p -> Objects.equals(codeEnum.getCode(), String.valueOf(p.get("CODE")))).findAny();
                if (obj.isPresent()) {
                    fid = String.valueOf(obj.get().get("ID"));
                }
            }
            List<String> fileIDs = Arrays.asList(fileIds.split(","));
            for (String fileId : fileIDs) {
                String id = Crud.from("PF_FILE").insertData();
                Crud.from("PF_FILE").where().eq("ID", id).update().set("FL_FILE_ID", fileId).set("PF_FOLDER_ID", fid).exec();
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }


    }
}
