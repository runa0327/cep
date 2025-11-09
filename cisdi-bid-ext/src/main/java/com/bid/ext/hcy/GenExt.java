package com.bid.ext.hcy;

import com.bid.ext.config.FlPathConfig;
import com.bid.ext.model.*;
import com.bid.ext.utils.DownloadUtils;
import com.bid.ext.utils.JsonUtil;
import com.deepoove.poi.data.PictureRenderData;
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
import com.tencentcloudapi.csip.v20221121.models.VULRiskAdvanceCFGList;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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
     * 生成工程联系单
     */
    public void genWorkConcatFormPdf() {


        InvokeActResult invokeActResult = new InvokeActResult();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());


        for (EntityRecord entityRecord : entityRecordList) {

            // 获取属性：
            Where attWhere = new Where();
            attWhere.eq(AdAtt.Cols.CODE, HcWorkConcatFormFileTemplate.Cols.ATTACHMENT);
            AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

            // 获取路径：
            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            Map<String, Object> valueMap = entityRecord.valueMap;

            //项目名称
//            String prjName = fetchNameFromTable("CC_PRJ",
//                    valueMap.get("CC_PRJ_ID").toString(),
//                    loginInfo.currentLangId.toString());//
//
            String ccPrjId = (String)valueMap.get("CC_PRJ_ID");
            CcPrj ccPrj = CcPrj.selectById(ccPrjId);
            String prjName = JsonUtil.getCN(ccPrj.getFullName());

            //接收单位
            String receivingUnit = fetchNameFromTable("CC_PARTY_COMPANY",
                    valueMap.get("HC_WCF_RECEIVING_UNIT").toString(),
                    loginInfo.currentLangId.toString());

            //发送单位
            String publishUnit = fetchNameFromTable("CC_PARTY_COMPANY",
                    valueMap.get("HC_WCF_PUBLISH_UNIT").toString(),
                    loginInfo.currentLangId.toString());

            //抄送单位
            List<Map<String, Object>> ccUnits = fetchNamesFromTable("CC_PARTY_COMPANY",
                    valueMap.get("HC_WCF_CC_UNIT").toString(),
                    loginInfo.currentLangId.toString());
            String ccUnitNames = "";
            for (Map<String, Object> ccUnit:ccUnits) {
                ccUnitNames+=ccUnit.get("name\n");
            }

            //事由
            String  reason = (String) valueMap.get("HC_WCF_REASON");

            //内容
            String content = (String) valueMap.get("REMARK");

            //项目部
            String  projectDepartment = (String) valueMap.get("HC_WCF_PRJ_NAME");

            //日期
            LocalDate concatDate = (LocalDate) valueMap.get("HC_WCF_CONCAT_DATE");

            //问题图片
            String issuesImgId = null;
            if (!SharedUtil.isEmpty(valueMap.get("ATTACHMENT"))) {
                issuesImgId = valueMap.get("ATTACHMENT").toString();
            }
            List<Map<String, Object>> imageEntries = fetchAndFormatImagesByPl(issuesImgId);

            Map<String, Object> map = new HashMap<String, Object>();

            map.put("csCommId", entityRecord.csCommId);
//            map.put("year", year);
            map.put("prjName", prjName);
            //接收单位
            map.put("receivingUnit",receivingUnit);
            //抄送单位
            map.put("ccUnits",ccUnitNames);
            //事由
            map.put("reason", reason);
            //内容
            map.put("content", content);
            //发送单位
            map.put("publishUnit", publishUnit);
            //项目部
            map.put("projectDepartment", projectDepartment);
//            图片
            map.put("imgs", imageEntries);

            MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
            String sql = "SELECT  f.PHYSICAL_LOCATION as physicalLocation from  fl_file  f,hc_work_concat_form_file_template t WHERE  t.ATTACHMENT LIKE   CONCAT('%',f.ID,'%') limit 0,1";
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap(sql);
            String   flLocation = (String) stringObjectMap.get("physicalLocation");

            byte[] word = DownloadUtils.createWordByResource(map, flLocation);

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
                flFile.setName("工作联系单");
                flFile.setExt("pdf");
                flFile.setDspName("工作联系单.pdf");
                flFile.setFileInlineUrl(FlPathConfig.FILE_INLINE_URL_FIX + "?fileId=" + fileId);
                flFile.setFileAttachmentUrl(FlPathConfig.FILE_ATTACHMENT_URL_FIX + "?fileId=" + fileId);
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

    //监理通知单



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

//            String libreOfficePath = "E:\\Program Files\\LibreOffice\\program\\soffice.exe";
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
     * 从指定表中获取名称
     *
     * @param tableName     表名
     * @param id            记录的ID
     * @param currentLangId 当前语言ID
     * @return 名称字段
     */
    private static List<Map<String, Object>> fetchNamesFromTable(String tableName, String id, String currentLangId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "select IF(JSON_VALID(NAME), NAME->>'$." + currentLangId + "', NAME) as name from " + tableName + " where NAME is not null and  ? like CONCAT('%',ID,'%')";
        return myJdbcTemplate.queryForList(sql, id);//返回名称集合
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

    /**
     * 处理图片 - 使用物理路径并转换 WebP
     *
     * @param idsString 图片ID字符串，逗号分隔
     * @return 格式化后的图片列表
     */
    private static List<Map<String, Object>> fetchAndFormatImagesByPl(String idsString) {
        if (idsString == null || idsString.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<String> ids = Arrays.asList(idsString.split(","));
        List<Map<String, Object>> imgs = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            FlFile flFile = FlFile.selectById(ids.get(i)); // 获取文件对象
            if (flFile != null) {
                String physicalPath = flFile.getPhysicalLocation(); // 获取文件的物理路径
                File imageFile = new File(physicalPath);
                if (imageFile.exists()) {
                    try {
                        String imagePathToUse = physicalPath;
                        String lowerCasePath = physicalPath.toLowerCase();
                        if (lowerCasePath.endsWith(".webp")) {
                            // 转换 WebP 为 PNG
                            imagePathToUse = convertWebPToPng(physicalPath);
                        }

                        PictureRenderData picture = Pictures.ofLocal(imagePathToUse)
                                .size(350, 350) // 设置图片大小，可根据需要调整
                                .create();

                        Map<String, Object> imgEntry = new HashMap<>();
                        imgEntry.put("order", (i + 1) + "、");
                        imgEntry.put("img", picture);
                        imgs.add(imgEntry);
                    } catch (Exception e) {
                        // 处理图片加载异常
                        e.printStackTrace();
                    }
                } else {
                    // 处理文件不存在的情况
                    String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.fileUploadFailed",physicalPath);
                    throw new BaseException(message);
                }
            }
        }

        return imgs;
    }

    /**
     * 将 WebP 图片转换为 PNG 格式
     *
     * @param webpPath 原始 WebP 图片路径
     * @return 转换后的 PNG 图片路径
     * @throws IOException 如果读取或写入图片失败
     */
    private static String convertWebPToPng(String webpPath) throws IOException {
        File webpFile = new File(webpPath);
        BufferedImage image = ImageIO.read(webpFile);
        String pngPath = webpPath.replaceFirst("(?i)\\.webp$", ".png");
        File pngFile = new File(pngPath);
        ImageIO.write(image, "png", pngFile);
        return pngPath;
    }
}
