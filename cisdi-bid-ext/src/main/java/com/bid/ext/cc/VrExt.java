package com.bid.ext.cc;

import com.bid.ext.model.CcVr;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.FlPath;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.awt.Image;

public class VrExt {

    /**
     * 生成VR缩略图
     */
    public void genVrPreview() throws IOException {
        InvokeActResult invokeActResult = new InvokeActResult();
//        Map<String, Object> varMap = ExtJarHelper.getVarMap();
//        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_PRJ_IDS");

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "select name from CC_QS_IMG_PREVIEW_URL LIMIT 1";
        Map<String, Object> map = myJdbcTemplate.queryForMap(sql);
        String urlHead = JdbcMapUtil.getString(map, "name");
        String sessionId = ExtJarHelper.getLoginInfo().sessionId;
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String id = JdbcMapUtil.getString(valueMap, "ID");
            CcVr ccVr = CcVr.selectById(id);


//            List<Map<String, Object>> imgs = new ArrayList<>();
            FlFile flFile = FlFile.selectById(ccVr.getCcVrAttachment()); // 获取文件对象
            if (null != flFile) {
                String fileInlineUrl = flFile.getFileInlineUrl(); // 获取文件 URL
                String flPathId = flFile.getFlPathId();
                String url = urlHead + fileInlineUrl + "&qygly-session-id=" + sessionId;

//                File inputFile = new File(url); // 输入图片文件
//                ImageIO.read(new URL(url););

                BufferedImage originalImage = ImageIO.read(new URL(url));
                int targetWidth = 300; // 目标宽度
                int targetHeight = 300; // 目标高度
                Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
                BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
                outputImage.getGraphics().drawImage(resizedImage, 0, 0, null);

//
//                PictureRenderData pictureRenderData = Pictures.ofUrl(url).size(10, 10).create();
//                byte[] bytes1 = pictureRenderData.readPictureData();

                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, flPathId);
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());

//                String previewPath = flPath.getDir()+ ccVr.getId() + "_preview." + flFile.getExt();
                String previewPath = flPath.getDir() + year + "/" + month + "/" + day + "/" + ccVr.getId() + "_preview." + flFile.getExt();
                File outputFile = new File(previewPath); // 输出图片文件
                ImageIO.write(outputImage, flFile.getExt(), outputFile);

//                saveWordToFile(bytes1, previewPath);


                if (checkFileExists(previewPath)) {
                    FlFile attachmentPreview = FlFile.newData();
                    String fileId = attachmentPreview.getId();

                    File file = new File(previewPath);
                    long bytes = file.length();
                    double kilobytes = bytes / 1024.0;

                    BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                    String dspSize = String.format("%d KB", Math.round(kilobytes));
                    attachmentPreview.setCrtUserId(loginInfo.userInfo.id);
                    attachmentPreview.setLastModiUserId(loginInfo.userInfo.id);
                    attachmentPreview.setFlPathId(flPath.getId());
                    attachmentPreview.setCode(fileId);
                    attachmentPreview.setName(ccVr.getId() + "_preview");
                    attachmentPreview.setExt(flFile.getExt());
                    attachmentPreview.setDspName(ccVr.getId() + "_preview." + flFile.getExt());
                    attachmentPreview.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                    attachmentPreview.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                    attachmentPreview.setSizeKb(sizeKb);
                    attachmentPreview.setDspSize(dspSize);
                    attachmentPreview.setUploadDttm(LocalDateTime.now());
                    attachmentPreview.setPhysicalLocation(previewPath);
                    attachmentPreview.setOriginFilePhysicalLocation(previewPath);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                    attachmentPreview.setIsPublicRead(false);
                    attachmentPreview.setIsPublicRead(true);
                    attachmentPreview.insertById();
//                    attachmentPreview.url;
                    ccVr.setCcVrAttachmentPreview(fileId);
                    ccVr.updateById();
                }
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
            e.printStackTrace();
        }
    }

    public static boolean checkFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }
}
