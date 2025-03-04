package com.bid.ext.cc;

import cn.hutool.core.util.IdUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.FileSystemResource;

import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@Slf4j
public class ZipProcessorExt {

//    private final ExecutorService executorService = Executors.newFixedThreadPool(5); // 创建一个固定大小为 5 的线程池

    /**
     * 当状态为“已完成”时，异步解压文件，保存相关文件
     * @param attachments 文件 ID
     * @param ccProcedureLedgerId 手续台账 ID
     */
//    public void decompressPackageAndStoreAsync(String attachments, String ccProcedureLedgerId) {
//        executorService.submit(() -> {
//            try {
//                decompressPackageAndStore(attachments, ccProcedureLedgerId);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//    }


//    public void shutdownExecutor() {
//        executorService.shutdown();
//    }

    /**
     * 当状态为“已完成”时，解压文件，保存相关文件
     * @param attachments 文件ID
     */
    public void decompressPackageAndStore(String attachments, String ccProcedureLedgerId, String ccDrawingUpdateRecordId) {
        try {
            if (attachments != null && !attachments.isEmpty()) {
                // 获取文件地址
                String filePath = getFilePath(attachments);
                if (filePath != null && !filePath.isEmpty()) {
                    unzipAndStoreFiles(filePath, ccProcedureLedgerId, attachments,ccDrawingUpdateRecordId);
                }
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取文件地址
     * @param attachments 文件ID
     * @return 文件地址
     */
    private String getFilePath(String attachments) throws IOException {
        // 资源文件的相对路径，相对于 resources 目录
        log.info("附件ID：{}", attachments);
        FlFile flFile = FlFile.selectById(attachments); // 获取文件对象
        log.info("文件对象：{}", flFile);
        if (flFile != null) {
            String resourcePath = flFile.getPhysicalLocation(); // 获取文件的物理路径
            log.info("文件路径：{}", resourcePath);
//            String resourcePath = "/data/qygly/file3/prod/FileStore/2025/02/20/1895388916898254848.zip";
//        filePath = "/Users/hejialun/Pictures/hjl-face.jpg";
            FileSystemResource fileSystemResource = new FileSystemResource(resourcePath);

            InputStream inputStream = null;
            try {
                inputStream = fileSystemResource.getInputStream();
//                byte[] imageBytes = new byte[inputStream.available()];
//                inputStream.read(imageBytes);
//                inputStream.close();
//                img = Base64.encodeBase64String(imageBytes);
//            } catch (IOException e) {
//                throw new BaseException("人脸图片未找到！");
//            }
            // 获取类加载器
//            ClassLoader classLoader = StructNodeExt.class.getClassLoader();
//            log.info("类加载器：{}", classLoader);
//            try (InputStream inputStream = classLoader.getResourceAsStream(resourcePath)) {
                log.info("输入流：{}", inputStream);
                if (inputStream != null) {
                    // 创建临时文件
                    File tempFile = File.createTempFile("temp", ".zip");
                    tempFile.deleteOnExit(); // 程序退出时删除临时文件
                    // 将资源文件内容复制到临时文件
                    try (OutputStream outputStream = new FileOutputStream(tempFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                    // 现在可以使用临时文件的 File 对象进行操作
                    System.out.println("临时文件路径：" + tempFile.getAbsolutePath());
                    File file = new File(tempFile.getAbsolutePath());
                    if (file.exists()) {
                        return file.getAbsolutePath();
                    }

                } else {
                    throw new BaseException("未找到资源文件");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 解压文件
     *
     * @param filePath                文件地址
     * @param ccDrawingUpdateRecordId
     */
    private void unzipAndStoreFiles(String filePath, String ccProcedureLedgerId, String attachments, String ccDrawingUpdateRecordId) throws IOException, SQLException {
        FlFile flFile = FlFile.selectById(attachments);
        String zipFileName = flFile.getName();

        // 创建 ZIP 文件根目录
        String rootDirId = createRootDirectory(zipFileName, ccProcedureLedgerId, ccDrawingUpdateRecordId);
        try (ZipFile zipFile = new ZipFile(filePath, StandardCharsets.UTF_8)) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            Map<String, String> dirPathToIdMap = new HashMap<>();

            //遍历压缩包中的所有条目（文件和文件夹）
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (!entryName.contains("MACOSX") && !entryName.contains(".DS_Store")) {
                    //苹果电脑会默认存放带有MACOSX的目录，进行排除
                    if (entry.isDirectory()) {
                        //存储文件夹
                        storeDirectory(entryName, dirPathToIdMap, ccProcedureLedgerId, rootDirId, ccDrawingUpdateRecordId);
                    } else {
                        //存储文件
                        storeFile(entryName, zipFile.getInputStream(entry), dirPathToIdMap, attachments, rootDirId);
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 创建 ZIP 文件根目录
     *
     * @param zipFileName             ZIP 文件名称
     * @param ccProcedureLedgerId     手续台账 ID
     * @param ccDrawingUpdateRecordId
     * @return 根目录 ID
     * @throws SQLException SQL 异常
     */
    private String createRootDirectory(String zipFileName, String ccProcedureLedgerId, String ccDrawingUpdateRecordId) throws SQLException {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        CcProcedureLedger ccProcedureLedger = CcProcedureLedger.selectById(ccProcedureLedgerId);
        String ccPrjId = ccProcedureLedger.getCcPrjId();

        CcDocDir ccDocDir = CcDocDir.newData();
        ccDocDir.setTs(LocalDateTime.now());
        ccDocDir.setCrtDt(LocalDateTime.now());
        ccDocDir.setCrtUserId(userId);
        ccDocDir.setLastModiDt(LocalDateTime.now());
        ccDocDir.setLastModiUserId(userId);
        ccDocDir.setCcDocDirPid(null);
        ccDocDir.setName(zipFileName);
        ccDocDir.setStatus("AP");
        ccDocDir.setCcPrjId(ccPrjId);
        ccDocDir.setCcDrawingUpdateRecordId(ccDrawingUpdateRecordId);
        ccDocDir.insertById();

        String rootDirId = ccDocDir.getId();

        // cc_package_file 表中插入数据
        String id = IdUtil.getSnowflakeNextIdStr();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String insertSql = "INSERT INTO CC_PACKAGE_FILE (ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CC_DOC_DIR_ID, CC_PROCEDURE_LEDGER_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        myJdbcTemplate.update(insertSql, id, 1, LocalDateTime.now(), LocalDateTime.now(), userId,
                LocalDateTime.now(), userId, "AP", rootDirId, ccProcedureLedgerId);

        return rootDirId;
    }

    /**
     * 存储目录到 cc_doc_dir 表
     *
     * @param directoryPath           文件夹路径
     * @param ccDrawingUpdateRecordId
     */
    private void storeDirectory(String directoryPath, Map<String, String> dirPathToIdMap, String ccProcedureLedgerId, String rootDirId, String ccDrawingUpdateRecordId) throws SQLException {
        String[] parts = directoryPath.split("/");
        String parentDirId = rootDirId;
        StringBuilder currentPath = new StringBuilder();

        for (String part : parts) {
            if (!part.isEmpty()) {
                if (currentPath.length() > 0) {
                    currentPath.append("/");
                }
                currentPath.append(part);
                String currentDirPath = currentPath.toString();

                String dirId = getOrCreateDirectory(part, parentDirId, ccProcedureLedgerId, currentDirPath, dirPathToIdMap, ccDrawingUpdateRecordId);
                parentDirId = dirId;
            }
        }
    }

    /**
     * 获取或创建目录
     *
     * @param dirName                 目录名称
     * @param parentDirId             父目录 ID
     * @param ccProcedureLedgerId     手续台账 ID
     * @param currentDirPath          当前目录路径
     * @param dirPathToIdMap          目录路径到 ID 的映射
     * @param ccDrawingUpdateRecordId
     * @return 目录 ID
     * @throws SQLException SQL 异常
     */
    private String getOrCreateDirectory(String dirName, String parentDirId, String ccProcedureLedgerId, String currentDirPath, Map<String, String> dirPathToIdMap, String ccDrawingUpdateRecordId) throws SQLException {
        if (dirPathToIdMap.containsKey(currentDirPath)) {
            return dirPathToIdMap.get(currentDirPath);
        }

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String querySql = "SELECT\n" +
                "\tdd.ID \n" +
                "FROM\n" +
                "\tcc_doc_dir dd\n" +
                "\tLEFT JOIN cc_package_file pf ON pf.CC_DOC_DIR_ID = dd.ID\n" +
                "\tLEFT JOIN cc_procedure_ledger pl ON pl.ID = pf.CC_PROCEDURE_LEDGER_ID \n" +
                "WHERE\n" +
                "\tdd.`NAME` = ? AND pf.CC_PROCEDURE_LEDGER_ID = ?";
        List<Map<String, Object>> resultList = myJdbcTemplate.queryForList(querySql, dirName, ccProcedureLedgerId);
        String dirId;
        if (!resultList.isEmpty()) {
            Map<String, Object> map = resultList.get(0);
            if (map.get("ID") != null) {
                dirId = (String) map.get("ID");
            } else {
                dirId = createNewDirectory(dirName, parentDirId, ccProcedureLedgerId, ccDrawingUpdateRecordId);
            }
        } else {
            dirId = createNewDirectory(dirName, parentDirId, ccProcedureLedgerId, ccDrawingUpdateRecordId);
        }
        dirPathToIdMap.put(currentDirPath, dirId);
        return dirId;
    }

    /**
     * 创建新目录
     *
     * @param dirName                 目录名称
     * @param parentDirId             父目录 ID
     * @param ccProcedureLedgerId     手续台账 ID
     * @param ccDrawingUpdateRecordId
     * @return 新目录的 ID
     * @throws SQLException SQL 异常
     */
    private String createNewDirectory(String dirName, String parentDirId, String ccProcedureLedgerId, String ccDrawingUpdateRecordId) throws SQLException {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        CcProcedureLedger ccProcedureLedger = CcProcedureLedger.selectById(ccProcedureLedgerId);
        String ccPrjId = ccProcedureLedger.getCcPrjId();

        CcDocDir ccDocDir = CcDocDir.newData();
        ccDocDir.setTs(LocalDateTime.now());
        ccDocDir.setCrtDt(LocalDateTime.now());
        ccDocDir.setCrtUserId(userId);
        ccDocDir.setLastModiDt(LocalDateTime.now());
        ccDocDir.setLastModiUserId(userId);
        ccDocDir.setCcDocDirPid(parentDirId);
        ccDocDir.setName(dirName);
        ccDocDir.setStatus("AP");
        ccDocDir.setCcPrjId(ccPrjId);
        ccDocDir.setCcDrawingUpdateRecordId(ccDrawingUpdateRecordId);
        ccDocDir.insertById();

        String dirId = ccDocDir.getId();

        // cc_package_file 表中插入数据
        String id = IdUtil.getSnowflakeNextIdStr();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String insertSql = "INSERT INTO CC_PACKAGE_FILE (ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CC_DOC_DIR_ID, CC_PROCEDURE_LEDGER_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        myJdbcTemplate.update(insertSql, id, 1, LocalDateTime.now(), LocalDateTime.now(), userId,
                LocalDateTime.now(), userId, "AP", dirId, ccProcedureLedgerId);

        return dirId;
    }

    private void storeFile(String filePath, InputStream fileInputStream, Map<String, String> dirPathToIdMap, String attachments, String rootDirId) throws SQLException, IOException {
        // 检查输入流是否为 null
        if (fileInputStream == null) {
            throw new IllegalArgumentException("fileInputStream cannot be null");
        }
        String[] parts = filePath.split("/");
        String fileName = parts[parts.length - 1];

        String firstFileName = fileName.split("\\.")[0];
        String lastFileExt = fileName.split("\\.")[1].toLowerCase();

        String directoryPath = String.join("/", Arrays.copyOf(parts, parts.length - 1));
        String parentDirId = dirPathToIdMap.getOrDefault(directoryPath, rootDirId);

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        FlFile flFile = FlFile.selectById(attachments);
        FlPath flPath = FlPath.selectById(flFile.getFlPathId());

        // 插入文件到 cc_fl_file 表
        FlFile packageFile = FlFile.newData();
        String fileId = packageFile.getId();

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        String previewPath = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + "." + lastFileExt;

        long fileSize = 0;

        // 将文件内容写入到指定路径
//        previewPath = System.getProperty("user.home")+"/data/qygly/file3/prod/FileStore/"+ year + "/" + month + "/" + day + "/" + fileId + "." + lastFileExt;
        File outputFile = new File(previewPath);
        // 创建父目录
        outputFile.getParentFile().mkdirs(); // 修复方式：确保目录存在
        try (BufferedInputStream bis = new BufferedInputStream(fileInputStream);
             FileOutputStream fos = new FileOutputStream(outputFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                fileSize += bytesRead;
            }
        } catch (IOException e) {
            System.err.println("Error writing file: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }

//        File outputFile = new File(previewPath);
        // 创建父目录
//        outputFile.getParentFile().mkdirs();
//        try (BufferedInputStream bis = new BufferedInputStream(fileInputStream)) {
//            File outputFile = new File(previewPath);
//            // 确保文件流在使用前不被关闭
//            if (fileInputStream == null || fileInputStream.available() <= 0) {
//                throw new IOException("Stream is closed or not available");
//            }
//            fileSize = getSizeFromInputStream(bis);
//            // 创建父目录
//            outputFile.getParentFile().mkdirs();
//
//            try (FileOutputStream fos = new FileOutputStream(outputFile);
//                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = bis.read(buffer)) != -1) {
//                    bos.write(buffer, 0, bytesRead);
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Error writing file: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }



//        long fileSize = getSizeFromInputStream(fileInputStream);
//        fileSize = 1000;
//        if (fileSize <= 0) {
//            throw new IOException("File size is zero or negative");
//        }
        double kilobytes = fileSize / 1024.0;

        BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
        String previewDspSize = String.format("%d KB", Math.round(kilobytes));
        packageFile.setCrtUserId(loginInfo.userInfo.id);
        packageFile.setLastModiUserId(loginInfo.userInfo.id);
        packageFile.setFlPathId(flFile.getFlPathId());
        packageFile.setCode(fileId);
        packageFile.setName(firstFileName);
        packageFile.setExt(lastFileExt);
        packageFile.setDspName(fileName);
        packageFile.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
        packageFile.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
        packageFile.setSizeKb(sizeKb);
        packageFile.setDspSize(previewDspSize);
        packageFile.setUploadDttm(LocalDateTime.now());

         packageFile.setPhysicalLocation(previewPath);
        packageFile.setOriginFilePhysicalLocation(previewPath);
        packageFile.setIsPublicRead(false);
        packageFile.insertById();

        // 根据 parentDirId 得到项目 ID
        CcDocDir ccDocDir = CcDocDir.selectById(parentDirId);
        String projectId = ccDocDir.getCcPrjId();

        String fileType = "OTHER";
        if (lastFileExt.equals("dwg") || lastFileExt.equals("dwf") || lastFileExt.equals("dxf") || lastFileExt.equals("dxg")) {
            fileType = "CAD";
        } else if (lastFileExt.equals("jpg") || lastFileExt.equals("png") || lastFileExt.equals("jpeg")) {
            fileType = "VR";
        } else if (lastFileExt.equals("txt") || lastFileExt.equals("doc") || lastFileExt.equals("docx") || lastFileExt.equals("xls") || lastFileExt.equals("xlsx") || lastFileExt.equals("ppt") || lastFileExt.equals("pptx") || lastFileExt.equals("pdf")) {
            fileType = "DOC";
        } else if (lastFileExt.equals("mp4") || lastFileExt.equals("avi") || lastFileExt.equals("mov") || lastFileExt.equals("mpg")) {
            fileType = "MEDIA";
        } else if (lastFileExt.equals("zip") || lastFileExt.equals("rar")) {
            fileType = "ARCHIVE";
        } else if (lastFileExt.equals("rvt") || lastFileExt.equals("dgn") || lastFileExt.equals("ifc") || lastFileExt.equals("nwd")) {
            fileType = "BIM";
        }

        // 插入文件到 cc_doc_file 表
        CcDocFile ccDocFile = CcDocFile.newData();
        ccDocFile.setTs(LocalDateTime.now());
        ccDocFile.setCrtDt(LocalDateTime.now());
        ccDocFile.setCrtUserId(userId);
        ccDocFile.setLastModiDt(LocalDateTime.now());
        ccDocFile.setLastModiUserId(userId);
        ccDocFile.setCcDocDirId(parentDirId);
        ccDocFile.setCcAttachment(fileId);
        ccDocFile.setName(firstFileName);
        ccDocFile.setCcPrjId(projectId);
        ccDocFile.setCcDocFileTypeId(fileType);
        ccDocFile.setCcPreviewDspSize(previewDspSize);
        ccDocFile.setStatus("AP");
        ccDocFile.insertById();

        // 将文件内容写入到指定路径
//        previewPath = "~/data/qygly/file3/prod/FileStore/"+ year + "/" + month + "/" + day + "/" + fileId + "." + lastFileExt;
//        File outputFile = new File(previewPath);
        // 创建父目录
//        outputFile.getParentFile().mkdirs();

//        try (BufferedInputStream bis = new BufferedInputStream(fileInputStream)) {
//            File outputFile = new File(previewPath);
//            // 确保文件流在使用前不被关闭
//            if (fileInputStream == null || fileInputStream.available() <= 0) {
//                throw new IOException("Stream is closed or not available");
//            }
//            // 创建父目录
//            outputFile.getParentFile().mkdirs();
//
//            try (FileOutputStream fos = new FileOutputStream(outputFile);
//                 BufferedOutputStream bos = new BufferedOutputStream(fos)) {
//                byte[] buffer = new byte[4096];
//                int bytesRead;
//                while ((bytesRead = bis.read(buffer)) != -1) {
//                    bos.write(buffer, 0, bytesRead);
//                }
//            }
//        } catch (IOException e) {
//            System.err.println("Error writing file: " + e.getMessage());
//            e.printStackTrace();
//            throw e;
//        }
    }

    public long getSizeFromInputStream(InputStream inputStream) throws IOException {
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.size();
        }
    }

}
