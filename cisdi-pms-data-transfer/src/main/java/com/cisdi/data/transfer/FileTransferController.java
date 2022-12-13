package com.cisdi.data.transfer;

import com.cisdi.data.util.ListUtils;
import com.qygly.ext.jar.helper.debug.event.AsyncConfig;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FileTransferController
 * @package com.cisdi.data.transfer
 * @description
 * @date 2022/8/31
 */
@RestController
@RequestMapping("fileTransfer")
public class FileTransferController {

    @Autowired
    @Qualifier("cpmsJdbcTemplate")
    JdbcTemplate cpmsJdbcTemplate;

    @Autowired
//    @Qualifier("testJdbcTemplate")
    @Qualifier("prodJdbcTemplate")
    JdbcTemplate jdbcTemplate;


    @GetMapping("transferData")
    public String transferData() {
        AsyncConfig config = new AsyncConfig();
        Executor executor = config.getAsyncExecutor();
        // 先清除原有的数据
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PF_FILE where CPMS_ID is not null");
        List<String> ids = list.stream().map(p -> String.valueOf(p.get("FL_FILE_ID"))).collect(Collectors.toList());
        jdbcTemplate.update("delete from PF_FILE where CPMS_ID is not null");
        List<List<String>> idsList = ListUtils.split(ids, 2000);
        idsList.forEach(obj -> {
            executor.execute(() -> {
                obj.forEach(item -> {
                    jdbcTemplate.update("delete from FL_FILE where ID =?", item);
                });
            });
        });

        List<Map<String, Object>> projectList = jdbcTemplate.queryForList("select * from PM_PRJ where `STATUS` = 'ap'");
        // 同步文件表数据
        List<Map<String, Object>> projectFileList = cpmsJdbcTemplate.queryForList("select v.projectId projectId,pf.* from v_project_archives_type_file v left join project_file pf on  v.fileId = pf.file_id where projectId is not null");

        List<List<Map<String, Object>>> dataList = ListUtils.split(projectFileList, 1000);
        dataList.forEach(obj -> {
            executor.execute(() -> {
                obj.forEach(item -> {
                    String pmsProjectId = String.valueOf(item.get("projectId"));
                    Optional<Map<String, Object>> projectOptional = projectList.stream().filter(pr -> Objects.equals(pmsProjectId, JdbcMapUtil.getString(pr, "CPMS_UUID"))).findAny();
                    //判断企业云项目是否存在
                    if (projectOptional.isPresent()) {
                        // 同步文件数据
                        String code = null;
                        if (Objects.nonNull(item.get("file_name"))) {
                            code = String.valueOf(item.get("file_name")).split("/")[3].split("\\.")[0];
                        }
                        BigDecimal sizeKb = new BigDecimal("0.00");
                        String fileSize = String.valueOf(item.get("file_size"));
                        if (fileSize.contains("M")) {
                            String data = fileSize.split("M")[0];
                            sizeKb = new BigDecimal(data).multiply(new BigDecimal("1024"));
                        }
                        String id = Util.insertData(jdbcTemplate, "FL_FILE");
                        String fileInlineUrl = "http://qygly.com/qygly-gateway/qygly-file/viewImage?fileId=" + id;
                        String fileAttachmentUrl = "http://qygly.com/qygly-gateway/qygly-file/downloadCommonFile?fileId=" + id;
                        String physicalLocation = null;
                        String filePath = String.valueOf(item.get("file_path"));
                        String ojj = "";
                        if (filePath.contains("filedisk")) {
                            ojj = filePath.replaceAll("/filedisk", "");
                        } else {
                            ojj = filePath;
                        }
                        physicalLocation = "/data/qygly/file" + ojj + "/" + item.get("file_name");


                        String originalName = String.valueOf(item.get("original_name"));
                        String name = originalName;
                        String ext = String.valueOf(item.get("file_suffix"));
                        if (!StringUtils.isEmpty(originalName)) {
                            if (originalName.contains(ext)) {
                                name = originalName.replaceAll("." + ext, "");
                            }
                        }

                        jdbcTemplate.update("update FL_FILE set `CODE`=?,`NAME`=?,EXT=?,DSP_NAME=?,SIZE_KB=?,DSP_SIZE=?,FILE_INLINE_URL=?,FILE_ATTACHMENT_URL=?,UPLOAD_DTTM=?,PHYSICAL_LOCATION=?,FL_PATH_ID=? WHERE ID=?",
                                code, name, item.get("file_suffix"), item.get("original_name"), sizeKb, item.get("file_size"), fileInlineUrl, fileAttachmentUrl,
                                item.get("upload_time"), physicalLocation, "0099902212142322558", id);


                        String qyyProjectId = String.valueOf(projectOptional.get().get("ID"));
                        //查询项目是否有历史文件夹
                        String pFloderId = "";
                        List<Map<String, Object>> list1 = jdbcTemplate.queryForList("select * from PF_FOLDER where `NAME`='历史文件' and PM_PRJ_ID=?", qyyProjectId);
                        if (CollectionUtils.isEmpty(list1)) {
                            //给项目创建一个历史问价夹
                            pFloderId = Util.insertData(jdbcTemplate, "PF_FOLDER");
                            jdbcTemplate.update("update PF_FOLDER set PM_PRJ_ID=?,NAME='历史文件',IS_TEMPLATE='0',SEQ_NO=100 where id =?", qyyProjectId, pFloderId);
                        } else {
                            pFloderId = String.valueOf(list1.get(0).get("ID"));
                        }



                        // 同步PF_FILE
                        String pfId = Util.insertData(jdbcTemplate, "PF_FILE");
                        jdbcTemplate.update("update PF_FILE set FL_FILE_ID=?,CPMS_ID=?,CPMS_UUID=?,PF_FOLDER_ID=? where id=?", id, item.get("id"), item.get("file_id"), pFloderId, pfId);
                    }
                });
            });
        });
        return "success";
    }

    @GetMapping("repeat")
    public String repeat() {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PF_FOLDER where `NAME`='历史文件'");
        Map<String, List<Map<String, Object>>> dataMap = list.stream().collect(Collectors.groupingBy(p -> String.valueOf(p.get("PM_PRJ_ID"))));
        for (String key : dataMap.keySet()) {
            List<Map<String, Object>> ojb = dataMap.get(key);
            if (ojb.size() > 1) {
                //给项目创建一个历史问价夹
                String pFloderId = Util.insertData(jdbcTemplate, "PF_FOLDER");
                jdbcTemplate.update("update PF_FOLDER set PM_PRJ_ID=?,NAME='历史文件',IS_TEMPLATE='0',SEQ_NO=100 where id =?", key, pFloderId);
                for (int i = 0; i < ojb.size(); i++) {
                    jdbcTemplate.update("update PF_FILE set PF_FOLDER_ID=? where PF_FOLDER_ID=?", pFloderId, ojb.get(i).get("ID"));
                    jdbcTemplate.update("delete from PF_FOLDER where id=?", ojb.get(i).get("ID"));
                }

            }
        }
        return "success";

    }


    public static void main(String[] args) {
        String aa = "/filedisk/yzw/pms/uploadPath/upload";
        String ba = "/filedisk";
        String res = aa.replaceAll(ba, "");
        System.out.println(res);

    }

    public static class FloderClass {
        public String id;
        public String name;
    }
}
