package com.cisdi.data.transfer;

import com.cisdi.data.util.ListUtils;
import com.qygly.ext.jar.helper.debug.event.AsyncConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    @Qualifier("testJdbcTemplate")
    JdbcTemplate testJdbcTemplate;


    @GetMapping("transferData")
    public String transferData() {
        AsyncConfig config = new AsyncConfig();
        Executor executor = config.getAsyncExecutor();
        // 先清除原有的数据
        List<Map<String, Object>> list = testJdbcTemplate.queryForList("select * from PF_FILE where CPMS_ID is not null");
        List<String> ids = list.stream().map(p -> String.valueOf(p.get("FL_FILE_ID"))).collect(Collectors.toList());
        testJdbcTemplate.update("delete from PF_FILE where CPMS_ID is not null");
        List<List<String>> idsList = ListUtils.split(ids, 2000);
        idsList.forEach(obj -> {
            executor.execute(() -> {
                obj.forEach(item -> {
                    testJdbcTemplate.update("delete from FL_FILE where ID =?", item);
                });
            });
        });


        // 同步文件表数据
        List<Map<String, Object>> projectFileList = cpmsJdbcTemplate.queryForList("select * from project_file where del_flag = '0'");

        List<List<Map<String, Object>>> dataList = ListUtils.split(projectFileList, 1000);
        dataList.forEach(obj -> {
            executor.execute(() -> {
                obj.forEach(item -> {
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
                    String id = Util.insertData(testJdbcTemplate, "FL_FILE");
                    String fileInlineUrl = "http://qygly.com/qygly-gateway/qygly-file/viewImage?fileId=" + id;
                    String fileAttachmentUrl = "http://qygly.com/qygly-gateway/qygly-file/downloadCommonFile?fileId=" + id;
                    String physicalLocation = "/data/qygly/file/yzw" + String.valueOf(item.get("file_path"));
                    testJdbcTemplate.update("update FL_FILE set `CODE`=?,`NAME`=?,EXT=?,DSP_NAME=?,SIZE_KB=?,DSP_SIZE=?,FILE_INLINE_URL=?,FILE_ATTACHMENT_URL=?,UPLOAD_DTTM=?,PHYSICAL_LOCATION=?,FL_PATH_ID=? WHERE ID=?",
                            code, item.get("original_name111"), item.get("file_suffix"), item.get("original_name"), sizeKb, item.get("file_size"), fileInlineUrl, fileAttachmentUrl,
                            item.get("upload_time"), physicalLocation, "99902212142322558", id);
                    // 同步PF_FILE
                    String pfId = Util.insertData(testJdbcTemplate, "PF_FILE");
                    testJdbcTemplate.update("update PF_FILE set FL_FILE_ID=?,CPMS_ID=?,CPMS_UUID=? where id=?", id, item.get("id"), item.get("file_id"), pfId);
                });
            });
        });
        return "success";
    }
}
