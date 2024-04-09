package com.bid.ext.cc;

import com.bid.ext.model.AdAtt;
import com.bid.ext.model.CcQsInspection;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.FlPath;
import com.bid.ext.utils.DownloadUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class GenExt {

    public void genPdf() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());


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
            map.put("CC_QS_ISSUE_POINT_TYPE_ID", valueMap.get("CC_QS_ISSUE_POINT_TYPE_ID").toString());
            byte[] word = DownloadUtils.createWord(map, "check.docx");
            byte[] b = convertWordToPDF(word);

            FlFile flFile = FlFile.newData();

            // 将String写入文件，覆盖模式，字符集为UTF-8x``
            String fileId = flFile.getId();

            // 构建文件名和路径
            String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";
            saveWordToFile(b, path);
            boolean fileExists = checkFileExists(path);
            if (fileExists) {
                //获取文件属性
                File file = new File(path);
                long bytes = file.length();
                double kilobytes = bytes / 1024.0;

                BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                String dspSize = String.format("%d KB", Math.round(kilobytes));
                flFile.setCrtUserId(loginInfo.userInfo.id);
                flFile.setLastModiUserId(loginInfo.userInfo.id);
                flFile.setFlPathId(flPath.getId());
                flFile.setCode(fileId);
                flFile.setName("整改通知单");
                flFile.setExt("pdf");
                flFile.setDspName("整改通知单.pdf");
                flFile.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                flFile.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                flFile.setSizeKb(sizeKb);
                flFile.setDspSize(dspSize);
                flFile.setUploadDttm(LocalDateTime.now());
                flFile.setPhysicalLocation(path);
                flFile.setOriginFilePhysicalLocation(path);
//                    flFile.setIsPublicRead(flPath.getIsPublicRead());
                flFile.setIsPublicRead(false);
                flFile.insertById();

                // 将文件ID设置到CC_QS_NOTICE_ID上：
                CcQsInspection ccQsInspection = CcQsInspection.selectById(EntityRecordUtil.getId(entityRecord));
                ccQsInspection.setCcQsNoticeId(fileId);
                ccQsInspection.updateById();
            } else {
                System.out.println("文件未找到：" + path);
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

    public static byte[] convertWordToPDF(byte[] docxBytes) {
        try {
            // 创建临时文件保存 DOCX 内容
            Path tempDocx = Files.createTempFile(null, ".docx");
            Files.write(tempDocx, docxBytes);

            // 定义 PDF 临时文件的路径
            Path tempPdf = Files.createTempFile(null, ".pdf");

            // 指定 LibreOffice 的安装路径及命令行工具
            String libreOfficePath = "C:\\Program Files\\LibreOffice\\program\\soffice";

            // 调用 LibreOffice 进行转换
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(libreOfficePath, "--convert-to", "pdf:writer_pdf_Export", tempDocx.toString(), "--outdir", tempPdf.getParent().toString());
            Process process = builder.start();
            process.waitFor();

            // 计算转换后的 PDF 文件名
            String pdfFileName = tempDocx.getFileName().toString().replaceAll("\\.docx$", ".pdf");
            Path pdfFilePath = tempPdf.getParent().resolve(pdfFileName);

            // 读取生成的 PDF 文件
            byte[] pdfBytes = Files.readAllBytes(pdfFilePath);

            // 清理临时文件
            Files.delete(tempDocx);
            Files.delete(pdfFilePath);

            return pdfBytes;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
