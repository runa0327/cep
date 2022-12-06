package com.cisdi.ext.util;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.artofsolving.jodconverter.openoffice.converter.StreamOpenOfficeDocumentConverter;
import com.cisdi.ext.enums.FileCodeEnum;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
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
        String userId = ExtJarHelper.loginInfo.get().userId;
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
                Crud.from("PF_FOLDER").where().eq("ID", id).update()
                        .set("PM_PRJ_ID", projectId)
                        .set("NAME", m.get("NAME"))
                        .set("SEQ_NO", m.get("SEQ_NO"))
                        .set("CODE", m.get("CODE"))
                        .set("IS_TEMPLATE", "0")
//                        .set("CHIEF_USER_ID",userId)
                        .exec();
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
            if(!CollectionUtils.isEmpty(list) && folderList.size() == list.size()){
                Optional<Map<String, Object>> obj = list.stream().filter(p -> Objects.equals(codeEnum.toString(), String.valueOf(p.get("CODE")))).findAny();
                if (obj.isPresent()) {
                    fid = String.valueOf(obj.get().get("ID"));
                }
            }else{
                ProFileUtils.createFolder(projectId);
                Map<String, Object> map = myJdbcTemplate.queryForMap("select * from pf_folder where PM_PRJ_ID=? and `CODE`=?", projectId, codeEnum.toString());
                fid = String.valueOf(map.get("ID"));
            }

            List<String> fileIDs = Arrays.asList(fileIds.split(","));
            for (String fileId : fileIDs) {
                String sql = "insert into PF_FILE (id,FL_FILE_ID,PF_FOLDER_ID) values((select UUID_SHORT()),'"+fileId+"','"+fid+"')";
                myJdbcTemplate.update(sql);
            }
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }
    }

    /**
     * word转pdf 返回文件大小
     */
    public static Map testExt(String input, String output) throws IOException {

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
//        OpenOfficeConnection connection = new SocketOpenOfficeConnection("124.222.60.191",8100);
        connection.connect();

        File inputFile = new File(input);
        File outputFile = new File(output);

//        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        StreamOpenOfficeDocumentConverter converter = new StreamOpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);
        File file = new File(output);
        if (!file.exists() || !file.isFile()){
            throw new BaseException("'"+output+"'该文件不存在");
        }
        float length = file.length();
        String fileName = file.getName();
        Map map = new HashMap();
        map.put("size",length);
        map.put("name",fileName);
        return map;
    }
}
