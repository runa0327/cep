package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.DownloadUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class GenExt {

    public void genPdf() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());

        if (entityRecordList != null) {
            for (EntityRecord entityRecord : entityRecordList) {

                // 获取属性：
                Where attWhere = new Where();
                attWhere.eq(AdAtt.Cols.CODE, CcQsInspection.Cols.CC_QS_NOTICE_ID);
                AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

                // 获取路径：
                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                Map<String, Object> valueMap = entityRecord.valueMap;
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("year", year);
//                map.put("CC_QS_ISSUE_POINT_TYPE_ID",valueMap.get("CC_QS_ISSUE_POINT_TYPE_ID").toString());
                byte[] b = DownloadUtils.createWord(map, "check.docx");

                FlFile flFile = FlFile.newData();

                // 将String写入文件，覆盖模式，字符集为UTF-8
                String fileId = flFile.getId();

                // 构建文件名和路径
                String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".docx";
//                String ownPath = "D:/" + year + "/" + month + "/" + day + "/" + fileId + ".docx";
                saveWordToFile(b, path);
                boolean fileExists = checkFileExists(path);
                if(fileExists) {
                    System.out.println("文件已成功生成：" + path);
                } else {
                    System.out.println("文件未找到：" + path);
                }
                //
                flFile.setFlPathId(flPath.getId());
                flFile.setCode(fileId);
                flFile.setName("整改通知单");
                flFile.setExt("docx");
                flFile.setDspName("整改通知单.docx");
                flFile.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                flFile.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                flFile.setUploadDttm(LocalDateTime.now());
                flFile.setPhysicalLocation(path);
                flFile.setOriginFilePhysicalLocation(path);
                flFile.setIsPublicRead(flPath.getIsPublicRead());
                flFile.insertById();

                // 将文件ID设置到CC_QS_NOTICE_ID上：
                CcQsInspection ccQsInspection = CcQsInspection.selectById(EntityRecordUtil.getId(entityRecord));
                ccQsInspection.setCcQsNoticeId(fileId);
                ccQsInspection.updateById();
            }
        }
    }

    public static void saveWordToFile(byte[] wordContent, String outputPath) {
        try {
            // 创建一个File对象，代表输出路径
            File outputFile = new File(outputPath);

            // 获取输出文件的父目录
            File parentDir = outputFile.getParentFile();

            // 如果父目录不存在，则创建它
            if (!parentDir.exists()) {
                parentDir.mkdirs();
            }

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(wordContent);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
