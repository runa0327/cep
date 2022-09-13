package com.cisdi.data.util;

import com.cisdi.data.transfer.Util;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
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
    public static void createFolder(String projectId, JdbcTemplate jdbcTemplate) {

        // 查询已经有的文件夹
        List<Map<String, Object>> folderList = jdbcTemplate.queryForList("select * from PF_FOLDER where  PM_PRJ_ID=?", projectId);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select ID, `CODE`,`NAME`,REMARK,PM_PRJ_ID,SEQ_NO,ifnull(PF_FOLDER_PID,'0') as " +
                "PF_FOLDER_PID from PF_FOLDER where IS_TEMPLATE ='1';");
        // 新增项目文件夹目录
        list.stream().filter(p -> Objects.equals("0", String.valueOf(p.get("PF_FOLDER_PID")))).peek(m -> {
            String id = "";
            Optional<Map<String, Object>> optional = folderList.stream().filter(o -> Objects.equals(String.valueOf(m.get("CODE")),
                    String.valueOf(o.get("CODE")))).findAny();
            if (optional.isPresent()) {
                id = String.valueOf(optional.get().get("ID"));
            } else {
                id = Util.insertData(jdbcTemplate, "PF_FOLDER");
                jdbcTemplate.update("update PF_FOLDER set PM_PRJ_ID = ?,NAME = ?,SEQ_NO = ?,CODE = ?,IS_TEMPLATE = '0' where id = ?", projectId,
                        m.get("NAME"), m.get("SEQ_NO"), m.get("CODE"), id);
//                Crud.from("PF_FOLDER").where().eq("ID", id).update().set("PM_PRJ_ID", projectId).set("NAME", m.get("NAME"))
//                        .set("SEQ_NO", m.get("SEQ_NO")).set("CODE", m.get("CODE")).set("IS_TEMPLATE", "0").exec();
            }
            createSonFolder(m, list, id, projectId, folderList, jdbcTemplate);
        }).collect(Collectors.toList());
    }

    public static List<Map<String, Object>> createSonFolder(Map<String, Object> root, List<Map<String, Object>> allData, String pid,
                                                            String projectId, List<Map<String, Object>> folderList, JdbcTemplate jdbcTemplate) {
        List<Map<String, Object>> collect = allData.stream()
                .filter(p -> Objects.equals(String.valueOf(root.get("ID")), String.valueOf(p.get("PF_FOLDER_PID"))))
                .peek(m -> {
                    String id = "";
                    Optional<Map<String, Object>> optional = folderList.stream().filter(o -> Objects.equals(String.valueOf(m.get("CODE")),
                            String.valueOf(o.get("CODE")))).findAny();
                    if (optional.isPresent()) {
                        id = String.valueOf(optional.get().get("ID"));
                    } else {
                        id = Util.insertData(jdbcTemplate, "PF_FOLDER");
                        jdbcTemplate.update("update PF_FOLDER set PM_PRJ_ID = ?,NAME = ?,SEQ_NO = ?,CODE = ?,IS_TEMPLATE = '0', PF_FOLDER_PID = ? where id" +
                                " = ?", projectId, m.get("NAME"), m.get("SEQ_NO"), m.get("CODE"), pid, id);
                    }
                    createSonFolder(m, allData, id, projectId, folderList, jdbcTemplate);
                })
                .collect(Collectors.toList());
        return collect;
    }

}
