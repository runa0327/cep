package com.cisdi.ext.util;

import com.cisdi.ext.enums.FileCodeEnum;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PrFileUtils
 * @package com.cisdi.ext.util
 * @description
 * @date 2022/8/25
 */
public class ProFileUtils {

    /**
     * 新增项目资料文件夹层级
     */
    public static void createFolder(String projectId) {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select `CODE`,`NAME`,REMARK,PM_PRJ_ID,SEQ_NO,ifnull(PF_FOLDER_PID,'0') as PF_FOLDER_PID from PF_FOLDER where IS_TEMPLATE ='1';");
        //新增项目文件夹目录
        list.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PF_FOLDER_PID")))).peek(m -> {
            String id = Crud.from("PF_FOLDER").insertData();
            Crud.from("PF_FOLDER").where().eq("ID", id).update().set("PM_PRJ_ID", projectId).set("NAME", m.get("NAME"))
                    .set("SEQ_NO", m.get("SEQ_NO")).set("CODE", m.get("CODE")).set("IS_TEMPLATE", "0").exec();
            createSonFolder(m, list, id, projectId);
        }).collect(Collectors.toList());
    }

    public static List<Map<String, Object>> createSonFolder(Map<String, Object> root, List<Map<String, Object>> allData, String pid, String projectId) {
        return allData.stream().filter(p -> Objects.equals(String.valueOf(root.get("ID")), String.valueOf(p.get("PF_FOLDER_PID")))).peek(m -> {
            String id = Crud.from("PF_FOLDER").insertData();
            Crud.from("PF_FOLDER").where().eq("ID", id).update().set("PM_PRJ_ID", projectId).set("NAME", m.get("NAME"))
                    .set("SEQ_NO", m.get("SEQ_NO")).set("CODE", m.get("CODE")).set("IS_TEMPLATE", "0").set("PF_FOLDER_PID", pid).exec();
            createSonFolder(m, allData, id, projectId);
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
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        try {
            Map<String, Object> map = jdbcTemplate.queryForMap("select * from pf_folder where PM_PRJ_ID=? and `CODE`=?", projectId, codeEnum.getCode());
            String fid = String.valueOf(map.get("ID"));
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
