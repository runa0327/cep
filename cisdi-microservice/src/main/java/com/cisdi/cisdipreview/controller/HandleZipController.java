package com.cisdi.cisdipreview.controller;

import cn.hutool.core.util.IdUtil;
import com.cisdi.cisdipreview.utils.FileUtils;
import com.qygly.shared.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.math.BigDecimal;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

@RestController
@RequestMapping("/handleZip")
@Slf4j
public class HandleZipController {


    @Resource
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/decompress-and-store")
    public void decompressPackageAndStore( HttpServletRequest request, @RequestBody Map<String, Object> fileData) {

        try {
            String ccAttachment = getFirstValue(fileData, "cc_attachment");
            String ccProcedureLedgerId = getFirstValue(fileData, "cc_procedure_ledger_id");
            String ccDrawingUpdateRecordId = getFirstValue(fileData, "cc_drawing_update_record_id");
            String userId = getFirstValue(fileData, "user_id");

            if (ccAttachment != null && !ccAttachment.isEmpty()) {
                //
                // 获取文件地址
                String filePath = getFilePath(ccAttachment);
                if (filePath != null && !filePath.isEmpty()) {
                    //解压并存储文件
                    unzipAndStoreFiles(filePath, ccProcedureLedgerId, ccAttachment, ccDrawingUpdateRecordId, userId);
                }
            }
        } catch (Exception e) {
            log.error("解压并存储文件时出现错误", e);
            throw new RuntimeException("解压并存储文件时出现错误", e);
        }
    }


    /**
     * 从 Map 中获取指定键的第一个值
     *
     * @param fileData 数据 Map
     * @param key      键
     * @return 第一个值
     */
    private String getFirstValue(Map<String, Object> fileData, String key) {
        List<?> list = (List<?>) fileData.get(key);
        return list != null && !list.isEmpty() ? (String) list.get(0) : null;
    }

    /**
     * 获取文件地址
     * @param attachments 文件ID
     * @return 文件地址
     * @throws IOException 输入输出异常
     */
    private String getFilePath(String attachments) throws IOException {
        // 资源文件的相对路径，相对于 resources 目录
        Map<String, Object> flFile = jdbcTemplate.queryForMap("select * from fl_file where id = ?", attachments);
//        FlFile flFile = FlFile.selectById(attachments); // 获取文件对象
        if (flFile != null) {
            String resourcePath = (String) flFile.get("PHYSICAL_LOCATION"); // 获取文件的物理路径
//            resourcePath =  "E:\\data\\qygly\\file3\\prod\\FileStore\\2025\\03\\12\\1111112.zip";
            FileSystemResource fileSystemResource = new FileSystemResource(resourcePath);

            try (InputStream inputStream = fileSystemResource.getInputStream()) {
                if (inputStream != null) {
                    // 创建临时文件
                    File tempFile = File.createTempFile("temp", ".zip");
                    tempFile.deleteOnExit(); // 程序退出时删除临时文件
                    // 将资源文件内容复制到临时文件
                    copyInputStreamToFile(inputStream, tempFile);
                    // 现在可以使用临时文件的 File 对象进行操作
                    File file = new File(tempFile.getAbsolutePath());
                    if (file.exists()) {
                        return file.getAbsolutePath();
                    }

                } else {
                    throw new BaseException("未找到资源文件");
                }
            }
        }
        return null;
    }

    /**
     * 将输入流复制到文件
     *
     * @param inputStream 输入流
     * @param file        文件
     * @throws IOException 输入输出异常
     */
    private void copyInputStreamToFile(InputStream inputStream, File file) throws IOException {
        try (OutputStream outputStream = Files.newOutputStream(file.toPath())) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
    }

    /**
     * 解压文件
     *
     * @param filePath                文件地址
     * @param ccProcedureLedgerId     手续台账 ID
     * @param attachments             附件 ID
     * @param ccDrawingUpdateRecordId 图纸更新记录 ID
     * @param userId                  用户 ID
     * @throws IOException  输入输出异常
     * @throws SQLException SQL 异常
     */
    private void unzipAndStoreFiles(String filePath, String ccProcedureLedgerId, String attachments, String ccDrawingUpdateRecordId, String userId) throws IOException, SQLException {
        Map<String, Object> flFile = jdbcTemplate.queryForMap("select * from fl_file where id = ?", attachments);
        String zipFileName = flFile.get("NAME").toString();

        // 创建 ZIP 文件根目录
        String rootDirId = createRootDirectory(zipFileName, ccProcedureLedgerId, ccDrawingUpdateRecordId, userId);

//        String encoding = detectEncoding(filePath);
//        if (encoding == null) {
//            encoding = "UTF-8"; // 默认使用 UTF-8
//        }

        try (ZipFile zipFile = new ZipFile(filePath,  Charset.forName("GB2312"))) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            Map<String, String> dirPathToIdMap = new HashMap<>();
            dirPathToIdMap.put(zipFileName, rootDirId);

            // 先处理所有目录，确保层级关系正确
            List<ZipEntry> directoryEntries = new ArrayList<>();
            List<ZipEntry> fileEntries = new ArrayList<>();
            //遍历压缩包中的所有条目（文件和文件夹）
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                String entryName = entry.getName();
                // 过滤非法字符
                entryName = entryName.replaceAll("[^a-zA-Z0-9./_-]", "");
                if (!entryName.contains("MACOSX") && !entryName.contains(".DS_Store")) {
                    //苹果电脑会默认存放带有MACOSX的目录，进行排除
                    if (entry.isDirectory()) {
                        directoryEntries.add(entry);
                    } else {
                        fileEntries.add(entry);
                    }
                }
            }
            // 按路径长度排序，确保先处理父目录
            directoryEntries.sort(Comparator.comparingInt(e -> e.getName().split("/").length));
            log.info("处理目录");
            for (ZipEntry directoryEntry : directoryEntries) {
                String entryName = directoryEntry.getName();
                String fullPath = zipFileName + "/" + entryName;
                storeDirectory(fullPath, dirPathToIdMap, ccProcedureLedgerId, rootDirId, ccDrawingUpdateRecordId, userId);
            }
            log.info("处理文件");
            for (ZipEntry fileEntry : fileEntries) {
                String entryName = fileEntry.getName();
                String fullPath = zipFileName + "/" + entryName;
                storeFile(fullPath, zipFile.getInputStream(fileEntry), dirPathToIdMap, attachments, rootDirId, userId);
            }
        }
    }


    /**
     * 创建 ZIP 文件根目录
     *
     * @param zipFileName             ZIP 文件名称
     * @param ccProcedureLedgerId     手续台账 ID
     * @param ccDrawingUpdateRecordId 图纸更新记录 ID
     * @param userId                  用户 ID
     * @return 根目录 ID
     * @throws SQLException SQL 异常
     */
    private String createRootDirectory(String zipFileName, String ccProcedureLedgerId, String ccDrawingUpdateRecordId, String userId) throws SQLException {

        Map<String, Object> ccProcedureLedger = jdbcTemplate.queryForMap("select * from cc_procedure_ledger where id = ?", ccProcedureLedgerId);
        String ccPrjId = ccProcedureLedger.get("CC_PRJ_ID").toString();

        // cc_doc_dir 表中插入数据
        String docDirId = IdUtil.getSnowflakeNextIdStr();
        String insertSql = "INSERT INTO CC_DOC_DIR (ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, CC_DOC_DIR_PID, NAME, STATUS, CC_PRJ_ID, CC_DRAWING_UPDATE_RECORD_ID, CC_DOC_FOLDER_TYPE_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSql,
                docDirId, 1, LocalDateTime.now(), LocalDateTime.now(), userId,
                LocalDateTime.now(), userId, null, zipFileName, "AP", ccPrjId, ccDrawingUpdateRecordId, "CAD");//相关的文件和文件夹都属于CAD管理

        // cc_package_file 表中插入数据
        String packageFileId = IdUtil.getSnowflakeNextIdStr();
        String insertSql2 = "INSERT INTO CC_PACKAGE_FILE (ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CC_DOC_DIR_ID, CC_PROCEDURE_LEDGER_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSql2, packageFileId, 1, LocalDateTime.now(), LocalDateTime.now(), userId,
                LocalDateTime.now(), userId, "AP", docDirId, ccProcedureLedgerId);

        return docDirId;
    }

    /**
     * 存储目录到 cc_doc_dir 表
     *
     * @param directoryPath           文件夹路径
     * @param dirPathToIdMap          目录路径到 ID 的映射
     * @param ccProcedureLedgerId     手续台账 ID
     * @param rootDirId               根目录 ID
     * @param ccDrawingUpdateRecordId 图纸更新记录 ID
     * @param userId                  用户 ID
     * @throws SQLException SQL 异常
     */
    private void storeDirectory(String directoryPath, Map<String, String> dirPathToIdMap, String ccProcedureLedgerId, String rootDirId, String ccDrawingUpdateRecordId, String userId) throws SQLException {
        String[] parts = directoryPath.split("/");
        String parentDirId = rootDirId;// 初始父目录ID设为根目录ID
        StringBuilder currentPath = new StringBuilder();

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];
            if (!part.isEmpty()) {
                if (currentPath.length() > 0) {
                    currentPath.append("/");
                }
                currentPath.append(part);
                String currentDirPath = currentPath.toString();

                if (i > 0) {
                    parentDirId = dirPathToIdMap.get(currentDirPath.substring(0, currentDirPath.lastIndexOf("/")));
                }

                String dirId = getOrCreateDirectory(currentDirPath, part, parentDirId, ccProcedureLedgerId, dirPathToIdMap, ccDrawingUpdateRecordId, userId);
                dirPathToIdMap.put(currentDirPath, dirId);
            }
        }
    }

    /**
     * 获取或创建目录
     *
     * @param dirName                 目录名称
     * @param parentDirId             父目录 ID
     * @param ccProcedureLedgerId     手续台账 ID
     * @param dirPathToIdMap          目录路径到 ID 的映射
     * @param ccDrawingUpdateRecordId 图纸更新记录 ID
     * @param userId                  用户 ID
     * @return 目录 ID
     * @throws SQLException SQL 异常
     */
    private String getOrCreateDirectory(String fullPath, String dirName, String parentDirId, String ccProcedureLedgerId, Map<String, String> dirPathToIdMap, String ccDrawingUpdateRecordId, String userId) throws SQLException {
        if (dirPathToIdMap.containsKey(fullPath)) {
            return dirPathToIdMap.get(fullPath);
        }

        // 根据完整路径和父目录 ID 检查目录是否已存在
        String querySql = "SELECT dd.ID FROM cc_doc_dir dd " +
                "LEFT JOIN cc_package_file pf ON pf.CC_DOC_DIR_ID = dd.ID " +
                "LEFT JOIN cc_procedure_ledger pl ON pl.ID = pf.CC_PROCEDURE_LEDGER_ID " +
                "WHERE dd.NAME = ? AND dd.CC_DOC_DIR_PID = ? AND pf.CC_PROCEDURE_LEDGER_ID = ?";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(querySql, dirName, parentDirId, ccProcedureLedgerId);
        String dirId;
        if (!resultList.isEmpty()) {
            Map<String, Object> map = resultList.get(0);
            if (map.get("ID") != null) {
                dirId = (String) map.get("ID");
            } else {
                dirId = createNewDirectory(dirName, parentDirId, ccProcedureLedgerId, ccDrawingUpdateRecordId, userId);
            }
        } else {
            dirId = createNewDirectory(dirName, parentDirId, ccProcedureLedgerId, ccDrawingUpdateRecordId, userId);
        }
        dirPathToIdMap.put(fullPath, dirId);
        return dirId;
    }

    /**
     * 创建新目录
     *
     * @param dirName                 目录名称
     * @param parentDirId             父目录 ID
     * @param ccProcedureLedgerId     手续台账 ID
     * @param ccDrawingUpdateRecordId 图纸更新记录 ID
     * @param userId                  用户 ID
     * @return 新目录的 ID
     * @throws SQLException SQL 异常
     */
    private String createNewDirectory(String dirName, String parentDirId, String ccProcedureLedgerId, String ccDrawingUpdateRecordId, String userId) throws SQLException {
        // 获取 cc_procedure_ledger 表中的 CC_PRJ_ID
        String ccPrjId = getCcPrjId(ccProcedureLedgerId);

        // 插入目录到 cc_doc_dir 表
        String dirId = IdUtil.getSnowflakeNextIdStr();
        String insertSql = "INSERT INTO CC_DOC_DIR (ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, CC_DOC_DIR_PID, NAME, STATUS, CC_PRJ_ID, CC_DRAWING_UPDATE_RECORD_ID, CC_DOC_FOLDER_TYPE_ID) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSql,
                dirId, 1, LocalDateTime.now(), LocalDateTime.now(), userId,
                LocalDateTime.now(), userId, parentDirId, dirName, "AP", ccPrjId, ccDrawingUpdateRecordId, "CAD");

        // cc_package_file 表中插入数据
        String id = IdUtil.getSnowflakeNextIdStr();
        String insertSql2 = "INSERT INTO CC_PACKAGE_FILE (ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CC_DOC_DIR_ID, CC_PROCEDURE_LEDGER_ID) VALUES (?,?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(insertSql2, id, 1, LocalDateTime.now(), LocalDateTime.now(), userId,
                LocalDateTime.now(), userId, "AP", dirId, ccProcedureLedgerId);

        return dirId;
    }

    /**
     * 根据手续台账 ID 获取项目 ID
     *
     * @param ccProcedureLedgerId 手续台账 ID
     * @return 项目 ID
     * @throws SQLException SQL 异常
     */
    private String getCcPrjId(String ccProcedureLedgerId) throws SQLException {
        String querySql = "SELECT CC_PRJ_ID FROM cc_procedure_ledger WHERE ID = ?";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(querySql, ccProcedureLedgerId);
        if (resultList.isEmpty()) {
            throw new BaseException("无法找到对应的手续台账");
        }
        return (String) resultList.get(0).get("CC_PRJ_ID");
    }

    /**
     * 存储文件
     *
     * @param filePath          文件路径
     * @param fileInputStream   文件输入流
     * @param dirPathToIdMap    目录路径到 ID 的映射
     * @param attachments       附件 ID
     * @param rootDirId         根目录 ID
     * @param userId            用户 ID
     * @throws SQLException SQL 异常
     * @throws IOException  输入输出异常
     */
    private void storeFile(String filePath, InputStream fileInputStream, Map<String, String> dirPathToIdMap, String attachments, String rootDirId, String userId) throws SQLException, IOException {
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

        // 根据 attachments 获取 FlFile 和 FlPath
        if (attachments == null || attachments.isEmpty()) {
            throw new IllegalArgumentException("文件不存在！");
        }
        //获取文件存储路径ID （FlPath ID）
        Map<String, Object> result = getFlPathId(attachments);
        String flPathId = (String) result.get("FL_PATH_ID");//fl_path ID
        String dir = (String) result.get("DIR");
        String fileInlineUrl = (String) result.get("FILE_INLINE_URL");
        String fileAttachmentUrl = (String) result.get("FILE_ATTACHMENT_URL");

        // 插入文件到 fl_file 表
        String fileId = IdUtil.getSnowflakeNextIdStr();//fl_file 表 ID
        fileInlineUrl += "?fileId=" + fileId;
        fileAttachmentUrl += "?fileId=" + fileId;

        LocalDate now = LocalDate.now();
        int year = now.getYear();
        String month = String.format("%02d", now.getMonthValue());
        String day = String.format("%02d", now.getDayOfMonth());
        String previewPath = dir + year + "/" + month + "/" + day + "/" + fileId + "." + lastFileExt;

        // 将文件内容写入到指定路径
//        previewPath = System.getProperty("user.home")+"/data/qygly/file3/prod/FileStore/"+ year + "/" + month + "/" + day + "/" + fileId + "." + lastFileExt;
        // 将文件内容从输入流复制到本地文件，并返回文件大小
        long fileSize = copyInputStreamToFile(fileInputStream, previewPath);
        // 计算文件大小（KB）
        double kilobytes = fileSize / 1024.0;
        // 使用 BigDecimal 保留 9 位小数并进行四舍五入
        BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
        // 格式化文件大小显示字符串，例如 "1.23 KB" 或 "123 MB"
        String previewDspSize = FileUtils.formatFileSize(fileSize);

        // 将文件信息插入到 fl_file 表中
        insertFlFile(fileId, sizeKb, previewDspSize, flPathId, firstFileName, lastFileExt, userId, previewPath, fileName, fileInlineUrl, fileAttachmentUrl);

        // 根据 parentDirId 得到项目 ID
        String projectId = getProjectId(parentDirId);

        // 根据文件扩展名判断文件类型
        String fileType = getFileType(lastFileExt);

        // 将文件信息插入到 cc_doc_file 表中
        insertCcDocFile(fileId, projectId, fileType, previewDspSize, parentDirId, firstFileName, userId);
    }

    /**
     * 根据附件 ID 获取 FlPath ID
     *
     * @param attachments 附件 ID
     * @return FlPath ID
     * @throws SQLException SQL 异常
     */
    private Map<String, Object> getFlPathId(String attachments) throws SQLException {
        String fileAndPathSql = "select ff.FL_PATH_ID,fp.DIR,fp.FILE_INLINE_URL,fp.FILE_ATTACHMENT_URL from fl_path fp left join fl_file ff on fp.ID = ff.FL_PATH_ID where ff.ID=?";
        List<Map<String, Object>> resultList = jdbcTemplate.queryForList(fileAndPathSql, attachments);
        if (resultList.isEmpty()) {
            throw new IllegalArgumentException("文件不存在！");
        }
        return resultList.get(0);
    }

    /**
     * 将输入流的内容复制到指定路径的文件中，并返回文件大小。
     *
     * @param inputStream 输入流
     * @param previewPath 文件预览路径
     * @return 文件大小（字节）
     * @throws IOException 如果文件读写操作出现错误
     */
    private long copyInputStreamToFile(InputStream inputStream, String previewPath) throws IOException {
        // 创建文件对象
        File outputFile = new File(previewPath);
        // 创建文件所在目录（如果不存在）
        outputFile.getParentFile().mkdirs();
        long fileSize = 0;
        try (BufferedInputStream bis = new BufferedInputStream(inputStream);
             FileOutputStream fos = new FileOutputStream(outputFile);
             BufferedOutputStream bos = new BufferedOutputStream(fos)) {
            byte[] buffer = new byte[4096];
            int bytesRead;
            // 循环读取输入流并写入文件
            while ((bytesRead = bis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
                fileSize += bytesRead;
            }
        }
        return fileSize;
    }

    /**
     * 将文件信息插入到 fl_file 表中。
     *
     * @param sizeKb          文件大小（KB，保留 9 位小数）
     * @param previewDspSize  格式化后的文件大小显示字符串
     * @param flPathId        文件存储路径 ID
     * @param firstFileName   文件名主体部分
     * @param lastFileExt     文件扩展名
     * @param userId          用户 ID
     * @param previewPath     文件预览路径
     * @param fileName        文件名
     * @throws SQLException 如果数据库操作出现错误
     */
    public void insertFlFile(String fileId, BigDecimal sizeKb, String previewDspSize, String flPathId, String firstFileName, String lastFileExt, String userId, String previewPath, String fileName, String fileInlineUrl, String fileAttachmentUrl) throws SQLException {
        // 生成唯一的文件 ID
//        String fileId = IdUtil.getSnowflakeNextIdStr();
        // 插入文件信息到 fl_file 表的 SQL 语句
        String insertFlFileSql = "INSERT INTO FL_FILE (ID, CODE, NAME, VER, FL_PATH_ID, EXT, STATUS, CRT_DT, CRT_USER_ID, " +
                "LAST_MODI_DT, LAST_MODI_USER_ID, SIZE_KB, FILE_INLINE_URL, FILE_ATTACHMENT_URL, TS, UPLOAD_DTTM, " +
                "PHYSICAL_LOCATION, DSP_NAME, DSP_SIZE, IS_PUBLIC_READ, ORIGIN_FILE_PHYSICAL_LOCATION) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // 执行 SQL 插入操作
        jdbcTemplate.update(insertFlFileSql,
                fileId, fileId, firstFileName, 1, flPathId, lastFileExt, "AP", LocalDateTime.now(), userId,
                LocalDateTime.now(), userId, sizeKb, fileInlineUrl, fileAttachmentUrl, LocalDateTime.now(), LocalDateTime.now(),
                previewPath, fileName, previewDspSize, false, previewPath);
    }

    /**
     * 根据父目录 ID 获取项目 ID。
     *
     * @param parentDirId 父目录 ID
     * @return 项目 ID
     * @throws SQLException 如果数据库操作出现错误
     */
    private String getProjectId(String parentDirId) throws SQLException {
        // 查询数据库，根据父目录 ID 获取项目 ID
        String selectProjectIdSql = "SELECT CC_PRJ_ID FROM cc_doc_dir WHERE ID=?";
        Map<String, Object> docDirMap = jdbcTemplate.queryForMap(selectProjectIdSql, parentDirId);
        // 若查询结果为空，抛出异常
        if (docDirMap == null || docDirMap.isEmpty()) {
            throw new IllegalArgumentException("项目 ID 不存在！");
        }
        // 返回项目 ID
        return (String) docDirMap.get("CC_PRJ_ID");
    }

    /**
     * 根据文件扩展名判断文件类型。
     *
     * @param lastFileExt 文件扩展名
     * @return 文件类型
     */
    private String getFileType(String lastFileExt) {
        String fileType = "OTHER";
        // 根据不同的文件扩展名判断文件类型
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
        return fileType;
    }

    /**
     * 将文件信息插入到 cc_doc_file 表中。
     *
     * @param projectId           项目 ID
     * @param fileType            文件类型
     * @param previewDspSize      格式化后的文件大小显示字符串
     * @param parentDirId         父目录 ID
     * @param firstFileName       文件名主体部分
     * @param userId              用户 ID
     * @throws SQLException 如果数据库操作出现错误
     */
    private void insertCcDocFile(String fileId, String projectId, String fileType, String previewDspSize, String parentDirId, String firstFileName, String userId) throws SQLException {
        // 生成唯一的文档文件 ID
        String docFileId = IdUtil.getSnowflakeNextIdStr();
        // 插入文件信息到 cc_doc_file 表的 SQL 语句
        String insertCcDocFileSql = "INSERT INTO CC_DOC_FILE (ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, " +
                "CC_DOC_DIR_ID, CC_ATTACHMENT, NAME, CC_PRJ_ID, CC_DOC_FILE_TYPE_ID, CC_PREVIEW_DSP_SIZE, STATUS) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        // 执行 SQL 插入操作
        jdbcTemplate.update(insertCcDocFileSql,
                docFileId, 1, LocalDateTime.now(), LocalDateTime.now(), userId, LocalDateTime.now(), userId,
                parentDirId, fileId, firstFileName, projectId, fileType, previewDspSize, "AP");
    }

}
