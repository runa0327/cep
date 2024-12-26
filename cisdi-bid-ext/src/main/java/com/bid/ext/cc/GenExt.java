package com.bid.ext.cc;

import com.bid.ext.model.AdAtt;
import com.bid.ext.model.CcQsInspection;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.FlPath;
import com.bid.ext.utils.DownloadUtils;
import com.deepoove.poi.data.Pictures;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
public class GenExt {

    /**
     * 生成pdf
     */
    public void genPdf() {
        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String sessionId = loginInfo.sessionId;


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

            String prjName = fetchNameFromTable("CC_PRJ",
                    valueMap.get("CC_PRJ_ID").toString(),
                    loginInfo.currentLangId.toString());

            Object ccQsIssuePointTypeId = valueMap.get("CC_QS_ISSUE_POINT_TYPE_ID");
            String issuePointTypeName = null;
            if (!SharedUtil.isEmpty(ccQsIssuePointTypeId)) {
                issuePointTypeName = fetchNameFromTable("CC_QS_ISSUE_POINT_TYPE",
                        valueMap.get("CC_QS_ISSUE_POINT_TYPE_ID").toString(),
                        loginInfo.currentLangId.toString());
            }

            String inspectionTypeName = fetchNameFromTable("CC_QS_INSPECTION_TYPE",
                    valueMap.get("CC_QS_INSPECTION_TYPE_ID").toString(),
                    loginInfo.currentLangId.toString());

            Object ccQsIssuePointIds = valueMap.get("CC_QS_ISSUE_POINT_IDS");
            List<Map<String, Object>> issuePointNames = null;
            if (!SharedUtil.isEmpty(ccQsIssuePointIds)) {
                issuePointNames = fetchAndFormatIssuePointNames("CC_QS_ISSUE_POINT",
                        ccQsIssuePointIds.toString(),
                        loginInfo.currentLangId.toString());
            }

            String issuesImgId = null;
            if (!SharedUtil.isEmpty(valueMap.get("CC_QS_ISSUES_IMG"))) {
                issuesImgId = valueMap.get("CC_QS_ISSUES_IMG").toString();
            }
            List<Map<String, Object>> imageEntries = fetchAndFormatImages(issuesImgId);

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("csCommId", entityRecord.csCommId);
            map.put("year", year);
            map.put("prjName", prjName);
            map.put("issuePointTypeName", issuePointTypeName);
            map.put("inspectionTypeName", inspectionTypeName);
            map.put("issuePointNames", issuePointNames);
            map.put("imgs", imageEntries);

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
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                flFile.setIsPublicRead(false);
                flFile.insertById();

                // 将文件ID设置到CC_QS_NOTICE_ID上：
                CcQsInspection ccQsInspection = CcQsInspection.selectById(EntityRecordUtil.getId(entityRecord));
                ccQsInspection.setCcQsNoticeId(fileId);
                ccQsInspection.updateById();
            } else {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileNotFound", path);
                throw new BaseException(message);
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
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
            throw new BaseException(e);
        }
    }

    public static boolean checkFileExists(String path) {
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

            // 获取当前操作系统的具体发行版信息
            String osReleaseFile = "/etc/os-release";
            String distro = null;

            try (BufferedReader br = new BufferedReader(new FileReader(osReleaseFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.startsWith("ID=")) {
                        distro = line.split("=")[1].replace("\"", "").trim();
                        break;
                    }
                }
            } catch (IOException e) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.unableToReadSystemInfo", e);
                throw new BaseException(message);
            }

            if (distro == null) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.unrecognizedLinuxDistro");
                throw new BaseException(message);
            }

            // 根据发行版设置 code
            String code;
            if (distro.contains("centos")) {
                code = "LIBRE_PATH_CENTOS";
            } else if (distro.contains("ubuntu")) {
                code = "LIBRE_PATH_UBUNTU";
            } else {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.unsupportedLinuxDistro", distro);
                throw new BaseException(message);
            }

            // 构造 SQL 语句
            String sql = "select SETTING_VALUE from AD_SYS_SETTING where code = '" + code + "'";

            // 查询数据库获取 LibreOffice 的路径
            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);
            if (list.isEmpty()) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.libreOfficePathNotFound", code);
                throw new BaseException(message);
            }
            String libreOfficePath = (String) list.get(0).get("SETTING_VALUE");

//            String libreOfficePath = "D:/Program Files/LibreOffice/program/soffice.exe";

            // 调用 LibreOffice 进行 DOCX 转 PDF
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(libreOfficePath, "--convert-to", "pdf:writer_pdf_Export", tempDocx.toString(), "--outdir", tempPdf.getParent().toString());
            Process process = builder.start();
            int exitCode = process.waitFor();

            // 检查转换是否成功
            if (exitCode != 0) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.libreOfficeConversionFailed", exitCode);
                throw new BaseException(message);
            }

            // 计算转换后的 PDF 文件名
            String pdfFileName = tempDocx.getFileName().toString().replaceAll("\\.docx$", ".pdf");
            Path pdfFilePath = tempPdf.getParent().resolve(pdfFileName);

            // 读取生成的 PDF 文件
            byte[] pdfBytes = Files.readAllBytes(pdfFilePath);

            // 清理临时文件
            Files.deleteIfExists(tempDocx);
            Files.deleteIfExists(pdfFilePath);

            return pdfBytes;
        } catch (Exception e) {
            throw new BaseException(e);
        }

    }

    /**
     * 从指定表中获取名称
     *
     * @param tableName
     * @param idString
     * @param currentLangId
     * @return
     */
    public static List<Map<String, Object>> fetchAndFormatIssuePointNames(String tableName, String idString, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<String> ids = Arrays.asList(idString.split(","));
        String inSql = String.join(",", Collections.nCopies(ids.size(), "?")); // 动态创建SQL IN子句
        String sql = "SELECT IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name FROM " + tableName
                + " WHERE NAME IS NOT NULL AND id IN (" + inSql + ")";
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList(sql, ids.toArray());
        List<Map<String, Object>> formattedNames = new ArrayList<Map<String, Object>>();

        int index = 1; // 开始编号
        for (Map<String, Object> result : resultList) {
            Map<String, Object> formattedEntry = new HashMap<>();
            formattedEntry.put("content", index++ + "、" + JdbcMapUtil.getString(result, "name"));
            formattedNames.add(formattedEntry);
        }

        return formattedNames;
    }

    /**
     * 从指定表中获取名称
     *
     * @param tableName     表名
     * @param id            记录的ID
     * @param currentLangId 当前语言ID
     * @return 名称字段
     */
    private static String fetchNameFromTable(String tableName, String id, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "select IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name from " + tableName + " where NAME is not null and id = ?";
        Map<String, Object> resultMap = myJdbcTemplate.queryForMap(sql, id);
        return resultMap.get("name").toString(); // 返回名称
    }

    /**
     * 处理图片
     *
     * @param idsString
     * @return
     */
    private static List<Map<String, Object>> fetchAndFormatImages(String idsString) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "select name from CC_QS_IMG_PREVIEW_URL LIMIT 1";
        Map<String, Object> map = myJdbcTemplate.queryForMap(sql);
        String urlHead = JdbcMapUtil.getString(map, "name");
        String sessionId = ExtJarHelper.getLoginInfo().sessionId;
        if (idsString == null || idsString.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<String> ids = Arrays.asList(idsString.split(","));
        List<Map<String, Object>> imgs = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            FlFile flFile = FlFile.selectById(ids.get(i)); // 获取文件对象
            if (flFile != null) {
                String fileInlineUrl = flFile.getFileInlineUrl(); // 获取文件 URL
                String url = urlHead + fileInlineUrl + "&qygly-session-id=" + sessionId;
                Map<String, Object> imgEntry = new HashMap<>();
                imgEntry.put("order", (i + 1) + "、");
                imgEntry.put("img", Pictures.ofUrl(url).size(350, 350).create());
                imgs.add(imgEntry);
            }
        }

        return imgs;
    }
}
