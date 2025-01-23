package com.cisdi.cisdipreview.service;

import cn.hutool.core.util.IdUtil;
import com.cisdi.cisdipreview.model.dto.IssueAreaDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qygly.ext.rest.helper.keeper.LoginInfoManager;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static com.cisdi.cisdipreview.utils.ImgUtils.IMG_MAX_HEIGHT;
import static com.cisdi.cisdipreview.utils.ImgUtils.IMG_MAX_WIDTH;

@Service
@Slf4j
@RequiredArgsConstructor
public class MqListener {
    private final JdbcTemplate jdbcTemplate;

    /**
     *
     * `@RabbitListener`注解中的监听队列名称queues根据application.yml中的qygly.ext-rest-helper.login-keeper.orgCode动态获取，
     * 每个应用的微服务通过不同的配置文件监听不同队列。
     * @param message MQ返回的消息
     */
    @RabbitListener(queues = "#{'inspection.rsp.' + '${qygly.ext-rest-helper.login-keeper.orgCode}'}")
    public void processDetectedIssue(String message) {
        ObjectMapper objectMapper = new ObjectMapper();
        IssueAreaDTO issueAreaDTO;
        try {
            issueAreaDTO = objectMapper.readValue(message, IssueAreaDTO.class);
        } catch (JsonProcessingException e) {
            log.error(e.toString());
            throw new RuntimeException(e);
        }

        if (issueAreaDTO.getShapes().isEmpty()) {
            // todo 是否需要处理合格巡检请求
            generateQualifiedRecord(issueAreaDTO);
            return;
        }
        generateUnqualifiedRecord(issueAreaDTO);
    }

    private void generateQualifiedRecord(IssueAreaDTO issueAreaDTO) {
        String ccQsInspectionId = issueAreaDTO.getAiInspectionReqId();
        String ccOriginFileId = issueAreaDTO.getFileId();
        String sql = "INSERT INTO CC_AI_INSPECTION_RESULT" +
                "(ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CC_ORIGIN_FILE_ID, CC_QS_INSPECTION_ID)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        String userId = LoginInfoManager.loginInfo.userInfo.id;
        LocalDateTime now = LocalDateTime.now();
        jdbcTemplate.update(
                sql,
                IdUtil.getSnowflakeNextIdStr(), 1, now, now, userId, now, userId, "AP",ccOriginFileId, ccQsInspectionId);
    }

    private void generateUnqualifiedRecord(IssueAreaDTO issueAreaDTO) {
        String ccQsInspectionId = issueAreaDTO.getAiInspectionReqId();
        String ccOriginFileId = issueAreaDTO.getFileId();
        String sql4Path = "SELECT p.DIR DIR, f.PHYSICAL_LOCATION LOC, f.FL_PATH_ID PATH, p.FILE_INLINE_URL FIU, p.FILE_ATTACHMENT_URL FAU FROM FL_FILE f LEFT JOIN FL_PATH p ON f.FL_PATH_ID = p.ID WHERE f.id = ?";
        Map<String, Object> map = jdbcTemplate.queryForMap(sql4Path, ccOriginFileId);
        String dir = JdbcMapUtil.getString(map, "DIR");
        String loc = JdbcMapUtil.getString(map, "LOC");
        String path = JdbcMapUtil.getString(map, "PATH");
        String fiu = JdbcMapUtil.getString(map, "FIU");
        String fau = JdbcMapUtil.getString(map, "FAU");
        String previewFileDir = getDir(dir);
        cn.hutool.core.io.FileUtil.mkdir(previewFileDir);
        String Id = IdUtil.getSnowflakeNextIdStr();
        String previewFilePath = previewFileDir + Id + ".jpg";
        File file = new File(loc);
        if (!file.exists()) {
            log.info("文件【" + loc + "】不存在");
        }
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(file);
        } catch (IOException e) {
            log.error(e.toString());
            throw new BaseException(e);
        }
        // Step 2: 获取 Graphics 对象
        Graphics2D g2d = (Graphics2D) originalImage.getGraphics();
        g2d.setStroke(new BasicStroke(Math.max(1, Math.min(originalImage.getHeight(), originalImage.getWidth()) / 150)));
        // Step 3: 设置绘制颜色 (可以自定义颜色)
        g2d.setColor(Color.decode( "#FF0000"));

        StringBuilder sb = new StringBuilder();
        for (IssueAreaDTO.IssueBox issueBox : issueAreaDTO.getShapes()) {
            List<List<Integer>> points = issueBox.getPoints();
            Integer issueAreaLeftTopX = points.get(0).get(0);
            Integer issueAreaLeftTopY = points.get(0).get(1);
            Integer issueAreaRightBottomX = points.get(1).get(0);
            Integer issueAreaRightBottomY = points.get(1).get(1);
            g2d.drawRect(
                    issueAreaLeftTopX, issueAreaLeftTopY,
                    issueAreaRightBottomX-issueAreaLeftTopX, issueAreaRightBottomY-issueAreaLeftTopY
            );
            sb.append(issueBox.getRemark());
            // Step 5: 释放 Graphics 资源
            g2d.dispose();
        }
        // Step 6: 保存修改后的图像
        File outputFile = new File(previewFilePath);
        try {
//            ImageIO.write(originalImage, "jpg", outputFile);
            Thumbnails.of(originalImage).outputFormat("jpg").size(IMG_MAX_WIDTH, IMG_MAX_HEIGHT).toFile(outputFile);
        } catch (IOException e) {
            log.error(e.toString());
            throw new BaseException(e);
        }
        String userId = LoginInfoManager.loginInfo.userInfo.id;
        LocalDateTime now = LocalDateTime.now();
        if (!outputFile.exists()) {
            throw new BaseException("未成功保存标记后巡检图像。");
        }
        long bytes = outputFile.length();
        double kilobytes = bytes / 1024.0;
        BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
        String previewDspSize = String.format("%d KB", Math.round(kilobytes));
//        flFile.setSizeKb(sizeKb);
//        flFile.setDspSize(previewDspSize);
        String sql4File = "INSERT INTO FL_FILE" +
                "(ID, NAME, VER, FL_PATH_ID, EXT, STATUS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, SIZE_KB, " +
                "FILE_INLINE_URL, FILE_ATTACHMENT_URL, TS, UPLOAD_DTTM, PHYSICAL_LOCATION, DSP_NAME, DSP_SIZE, IS_PUBLIC_READ, ORIGIN_FILE_PHYSICAL_LOCATION)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql4File,
                Id, Id, 1, path, "jpg", "AP", now, userId, now, userId, sizeKb, fiu + "?fileId=" + Id,
                fau + "?fileId=" + Id, now, now, previewFilePath, Id + ".jpg", previewDspSize, 1, previewFilePath);

        String sql = "INSERT INTO CC_AI_INSPECTION_RESULT" +
                "(ID, VER, TS, CRT_DT, CRT_USER_ID, LAST_MODI_DT, LAST_MODI_USER_ID, STATUS, CC_ORIGIN_FILE_ID, CC_QS_INSPECTION_ID, CC_AI_MARKED_FILE_ID, REMARK)" +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(
                sql,
                IdUtil.getSnowflakeNextIdStr(), 1, now, now, userId, now, userId, "AP",ccOriginFileId, ccQsInspectionId, Id, sb.toString());
    }

    public static String getDir(String flPathDir) {
        LocalDate nowDt = LocalDate.now();
        int year = nowDt.getYear();
        String month = String.format("%02d", nowDt.getMonthValue());
        String day = String.format("%02d", nowDt.getDayOfMonth());
        String dir = flPathDir + year + "/" + month + "/" + day + "/";
        cn.hutool.core.io.FileUtil.mkdir(dir);
        return dir;
    }
}
