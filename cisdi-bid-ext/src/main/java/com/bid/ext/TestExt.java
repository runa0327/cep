package com.bid.ext;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.bid.ext.model.*;
import com.bid.ext.utils.SysSettingUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.bid.ext.cc.GenExt.saveWordToFile;
import static com.bid.ext.utils.ImportValueUtils.*;

public class TestExt {
    public void importContract() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        FlFile flFile = FlFile.selectById(varMap.get("P_CC_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
        if (!"xls".equals(flFile.getExt()) && !"xlsx".equals(flFile.getExt())) {
            throw new BaseException("请上传'xls'或'xlsx'格式的Excel文件");
        }
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            // 遍历每一行
            for (int i = 1; i <= Objects.requireNonNull(sheet).getLastRowNum(); i++) {
                Row row = sheet.getRow(i);
                // 检查行是否为空
                if (row == null) {
                    continue; // 如果为空，跳过该行
                }

                String bidCode = getStringCellValue(row.getCell(0)); //合同编号
                String prjStructNodeCode = getStringCellValue(row.getCell(1)); //单元工程编号
                if (prjStructNodeCode != null && prjStructNodeCode.endsWith(".0")) {
                    prjStructNodeCode = prjStructNodeCode.substring(0, prjStructNodeCode.length() - 2);
                }
                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.CODE, prjStructNodeCode));
                String ccPrjStructNodeId = ccPrjStructNode.getId(); //单元工程名称
                String ccPrjId = ccPrjStructNode.getCcPrjId(); //项目名称
                String bidName = getStringCellValue(row.getCell(3)); //合同名称

                String ccCurrencyTypeId = getStringCellValue(row.getCell(6)); //币种
                switch (ccCurrencyTypeId) {
                    case "人民币":
                        ccCurrencyTypeId = "CNY";
                        break;
                }
                String projectUnit = getStringCellValue(row.getCell(7)); //项目单位
                String partyB = getStringCellValue(row.getCell(8)); //乙方
                String ccPoTypeId = getStringCellValue(row.getCell(10)); //合同类型

                switch (ccPoTypeId) {
                    case "技术服务合同":
                        ccPoTypeId = "TechnicalServiceContract";
                        break;
                    case "工程设计合同":
                        ccPoTypeId = "EngineeringDesignContract";
                        break;
                    case "技术咨询合同":
                        ccPoTypeId = "TechnicalConsultingContract";
                        break;
                    case "监理合同":
                        ccPoTypeId = "SupervisionContract";
                        break;
                    case "勘察合同":
                        ccPoTypeId = "SurveyContract";
                        break;
                    case "施工合同":
                        ccPoTypeId = "ConstructionContract";
                        break;
                    case "施工准备合同":
                        ccPoTypeId = "ConstructionPreparationContract";
                        break;
                }

                String ccPoStatusId = getStringCellValue(row.getCell(11)); //合同状态
                switch (ccPoStatusId) {
                    case "生效":
                        ccPoStatusId = "Effective";
                        break;
                    case "已发送":
                        ccPoStatusId = "Sent";
                        break;
                    case "关闭":
                        ccPoStatusId = "Closed";
                        break;
                    case "结案":
                        ccPoStatusId = "Closed";
                        break;
                    case "关闭(结案)":
                        ccPoStatusId = "Closed";
                        break;
                }

                String ccRegisteredStatusId = getStringCellValue(row.getCell(16)); //备案状态
                switch (ccRegisteredStatusId) {
                    case "备案成功":
                        ccRegisteredStatusId = "SUCC";
                        break;
                    case "待备案":
                        ccRegisteredStatusId = "TODO";
                        break;
                }


                CcPo2 ccPo = CcPo2.selectOneByWhere(new Where().eq(CcPo.Cols.CODE, bidCode));
                LocalDate trxDate = getStringCellValue(row.getCell(9)) == null || getStringCellValue(row.getCell(9)).isEmpty() ? null : getLocalDateCellValue(row.getCell(9));
                LocalDate planFr = getStringCellValue(row.getCell(12)) == null || getStringCellValue(row.getCell(12)).isEmpty() ? null : getLocalDateCellValue(row.getCell(12));
                LocalDate planTo = getStringCellValue(row.getCell(13)) == null || getStringCellValue(row.getCell(13)).isEmpty() ? null : getLocalDateCellValue(row.getCell(13));
                if (SharedUtil.isEmpty(ccPo)) {

                    CcPo2 po = CcPo2.newData();
                    po.setCcPrjId(ccPrjId);
                    po.setCode(bidCode);
                    po.setCcPrjStructNodeId(ccPrjStructNodeId);
                    po.setName(bidName);
                    po.setTrxAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(4))));
                    po.setSettlementAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(5))));
                    po.setCcCurrencyTypeId(ccCurrencyTypeId);
                    po.setProjectUnit(projectUnit);
                    po.setPartyB(partyB);
                    if (trxDate != null) {
                        po.setTrxDate(trxDate);
                    }
                    po.setCcPoTypeId(ccPoTypeId);
                    po.setCcPoStatusId(ccPoStatusId);
                    if (planFr != null) {
                        po.setPlanFr(planFr);
                    }
                    if (planTo != null) {
                        po.setPlanTo(planTo);
                    }
                    po.setCcBidCreateUserId(getStringCellValue(row.getCell(14)));
                    po.setIsRegistered(getBooleanCellValue(row.getCell(15)));
                    po.setCcRegisteredStatusId(ccRegisteredStatusId);

                    po.insertById();
                } else {
                    ccPo.setCcPrjId(ccPrjId);
                    ccPo.setCode(bidCode);
                    ccPo.setCcPrjStructNodeId(ccPrjStructNodeId);
                    ccPo.setName(bidName);
                    ccPo.setTrxAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(4))));
                    ccPo.setSettlementAmt(BigDecimal.valueOf(getNumericCellValue(row.getCell(5))));
                    ccPo.setCcCurrencyTypeId(ccCurrencyTypeId);
                    ccPo.setProjectUnit(projectUnit);
                    ccPo.setPartyB(partyB);
                    if (trxDate != null) {
                        ccPo.setTrxDate(trxDate);
                    }
                    ccPo.setCcPoTypeId(ccPoTypeId);
                    ccPo.setCcPoStatusId(ccPoStatusId);
                    if (planFr != null) {
                        ccPo.setPlanFr(planFr);
                    }
                    if (planTo != null) {
                        ccPo.setPlanTo(planTo);
                    }
                    ccPo.setCcBidCreateUserId(getStringCellValue(row.getCell(14)));
                    ccPo.setIsRegistered(getBooleanCellValue(row.getCell(15)));
                    ccPo.setCcRegisteredStatusId(ccRegisteredStatusId);
                    ccPo.updateById();
                }
            }

        } catch (IOException e) {
            throw new BaseException("上传文件失败", e);
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    /**
     * 简化版预览文件。采用KK进行预览。
     *
     * @throws UnsupportedEncodingException
     */
    public void simplePreview() throws UnsupportedEncodingException {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();

        String fileDownloadUrl1 = SysSettingUtil.getValue("FILE_DOWNLOAD_URL");
        String kkPreviewUrl = SysSettingUtil.getValue("KK_PREVIEW_URL");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            Object ccAttachment = entityRecord.valueMap.get("CC_ATTACHMENT");
            if (SharedUtil.isEmpty(ccAttachment)) {
                throw new BaseException("资料文件的CC_ATTACHMENT字段为空！");
            }

            List<Map<String, Object>> fileList = ExtJarHelper.getMyJdbcTemplate().queryForList("select * from fl_file f where f.id=?", ccAttachment);
            if (SharedUtil.isEmpty(fileList)) {
                throw new BaseException("资料文件的CC_ATTACHMENT字段对应FL_FILE记录不存在！");
            }

            Map<String, Object> file = fileList.get(0);

            String fileId = JdbcMapUtil.getString(file, "ID");
            String fileExt = JdbcMapUtil.getString(file, "EXT");

            String fileDownloadUrl = fileDownloadUrl1 + "?fileId=" + fileId + "&qygly-session-id=" + ExtJarHelper.getLoginInfo().sessionId + "&fullfilename=" + fileId + (SharedUtil.isEmpty(fileExt) ? "" : ("." + fileExt));

            // 采用KK进行预览时，要对url部分做2次编码。第1次是Base64编码、第2次是URL编码：
            String previewUrl = kkPreviewUrl + "?url=" + URLEncoder.encode(cn.hutool.core.codec.Base64.encode(fileDownloadUrl), "UTF-8");

            UrlToOpen urlToOpen = new UrlToOpen();
            urlToOpen.url = previewUrl;
            urlToOpen.title = "预览";
            invokeActResult.urlToOpenList.add(urlToOpen);
        }

        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 压缩包上传图纸
     */
    public void uploadZipDrawingPlan() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
        String pRemark = JdbcMapUtil.getString(varMap, "P_REMARK");
        String pActDate = JdbcMapUtil.getString(varMap, "P_ACT_DATE");

        List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
        for (String attachmentId : ccAttachmentList) {
            FlFile flFile = FlFile.selectById(attachmentId);
            String dspName = flFile.getDspName(); //获取文件名
            String name = flFile.getName();
            int index = dspName.indexOf('-');

            String ccConstructionDrawingId = dspName.substring(0, index);
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectOneByWhere(new Where().eq(CcDrawingManagement.Cols.CC_CONSTRUCTION_DRAWING_ID, ccConstructionDrawingId));
            if (SharedUtil.isEmpty(ccDrawingManagement)) {
                throw new BaseException("套图号：" + ccConstructionDrawingId + "不存在！");
            }

            String csCommId = ccDrawingManagement.getId();
            List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId));
            if (!SharedUtil.isEmpty(ccStructDrawingVersions)) {
                for (CcStructDrawingVersion ccStructDrawingVersion1 : ccStructDrawingVersions) {
                    ccStructDrawingVersion1.setIsDefault(false);
                    ccStructDrawingVersion1.updateById();
                }
            }

            String[] versionOrder = {"A", "B", "C", "D", "E", "F", "G"};
            String ccDrawingVersionId = null;

            // 依次判断版本的存在性
            for (String version : versionOrder) {
                List<CcStructDrawingVersion> ccStructDrawingVersions1 = CcStructDrawingVersion.selectByWhere(
                        new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_VERSION_ID, version)
                                .eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId)
                );
                if (SharedUtil.isEmpty(ccStructDrawingVersions1)) {
                    ccDrawingVersionId = version;
                    break;
                }
            }

            // 套图信息
            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();

            // 套图版本
            CcStructDrawingVersion ccStructDrawingVersion = CcStructDrawingVersion.newData();
            ccStructDrawingVersion.setCcDrawingVersionId(ccDrawingVersionId);
            CcDrawingVersion ccDrawingVersion = CcDrawingVersion.selectById(ccDrawingVersionId);
            String ccDrawingVersionName = ccDrawingVersion.getName();
            ccStructDrawingVersion.setName(ccDrawingVersionName);
            ccStructDrawingVersion.setCcDrawingManagementId(csCommId);
            ccStructDrawingVersion.setCcPrjStructNodeId(ccPrjStructNodeId);
            ccStructDrawingVersion.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
            ccStructDrawingVersion.setIsDefault(true);


            String zipFilePath = flFile.getOriginFilePhysicalLocation(); //zip文件物理路径
            // 获取属性：
            Where attWhere = new Where();
            attWhere.eq(AdAtt.Cols.CODE, CcDrawingUpload.Cols.CC_ATTACHMENT);
            AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

            // 获取路径：
            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            LocalDate now = LocalDate.now();
            int year = now.getYear();
            String month = String.format("%02d", now.getMonthValue());
            String day = String.format("%02d", now.getDayOfMonth());

            // 解压输出路径
            String outputDir = flPath.getDir() + year + "/" + month + "/" + day + "/" + name;

            File zipFile = FileUtil.file(zipFilePath);
            File outputDirectory = FileUtil.mkdir(outputDir);

            // 解压
            ZipUtil.unzip(zipFile, outputDirectory);

            ccStructDrawingVersion.insertById();

            // 遍历解压后的文件夹
            File[] files = outputDirectory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (!file.isDirectory()) { // 确保是文件，不是目录
                        try (InputStream inputStream = Files.newInputStream(file.toPath());
                             ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                            // 将文件内容读取到byte数组
                            IoUtil.copy(inputStream, outputStream);
                            byte[] fileBytes = outputStream.toByteArray();
                            if (index != -1) {

                                FlFile newFile = FlFile.newData();
                                // 将String写入文件，覆盖模式，字符集为UTF-8x``
                                String fileId = newFile.getId();

                                // 构建文件名和路径
                                String path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";
                                saveWordToFile(fileBytes, path);
                                boolean fileExists = checkFileExists(path);
                                if (fileExists) {
                                    //获取文件属性
                                    File file0 = new File(path);
                                    long bytes = file0.length();
                                    double kilobytes = bytes / 1024.0;
                                    String fileName = file.getName();

                                    BigDecimal sizeKb = BigDecimal.valueOf(kilobytes).setScale(9, BigDecimal.ROUND_HALF_UP);
                                    String dspSize = String.format("%d KB", Math.round(kilobytes));
                                    newFile.setCrtUserId(loginInfo.userInfo.id);
                                    newFile.setLastModiUserId(loginInfo.userInfo.id);
                                    newFile.setFlPathId(flPath.getId());
                                    newFile.setCode(fileId);
                                    newFile.setName(fileName);
                                    newFile.setExt(fileName.substring(fileName.lastIndexOf('.') + 1));
                                    newFile.setDspName(fileName);
                                    newFile.setFileInlineUrl(flPath.getFileInlineUrl() + "?fileId=" + fileId);
                                    newFile.setFileAttachmentUrl(flPath.getFileAttachmentUrl() + "?fileId=" + fileId);
                                    newFile.setSizeKb(sizeKb);
                                    newFile.setDspSize(dspSize);
                                    newFile.setUploadDttm(LocalDateTime.now());
                                    newFile.setPhysicalLocation(path);
                                    newFile.setOriginFilePhysicalLocation(path);
//                flFile.setIsPublicRead(flPath.getIsPublicRead());
                                    newFile.setIsPublicRead(false);
                                    newFile.insertById();

                                    //分图文件
                                    CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                                    ccDrawingUpload.setCcStructDrawingVersionId(ccStructDrawingVersion.getId());
                                    ccDrawingUpload.setCcAttachment(fileId);
                                    ccDrawingUpload.setCcDrawingVersionId(ccDrawingVersionId);
                                    ccDrawingUpload.setRemark(pRemark);
                                    ccDrawingUpload.setCcDrawingManagementId(csCommId);
                                    ccDrawingUpload.setName(fileName);
                                    ccDrawingUpload.insertById();

                                    //若没有实际发图日期则更新套图实际发图日期
                                    if (SharedUtil.isEmpty(ccDrawingManagement.getActDate())) {
                                        ccDrawingManagement.setActDate(LocalDate.parse(pActDate));
                                        ccDrawingManagement.setCcDrawingStatusId("DONE");
                                        ccDrawingManagement.updateById();
                                    }
                                } else {
                                    System.out.println("文件未找到：" + path);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 同步文件名称
     */
    public void syncFileName() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        String csCommId = entityRecord.csCommId;
        Map<String, Object> valueMap = entityRecord.valueMap;
        SevInfo sevInfo = ExtJarHelper.getSevInfo();
        EntityInfo entityInfo = sevInfo.entityInfo;
        String entityCode = entityInfo.code;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Object name = valueMap.get("NAME");
        if (SharedUtil.isEmpty(name)) {
            String fileId = String.valueOf(valueMap.get("CC_ATTACHMENT"));
            String extSql = "select f.ext,f.dsp_name from fl_file f where id = ?";
            String fileName = myJdbcTemplate.queryForMap(extSql, fileId).get("dsp_name").toString();
            int update = myJdbcTemplate.update("update " + entityCode + " t set t.NAME = ? where t.id=?", fileName, csCommId);
        }
    }

    public static boolean checkFileExists(String path) {
        File file = new File(path);
        return file.exists();
    }

    /**
     * 更新时检查已有套图号
     */
    public void updateCheckDrawing() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccConstructionDrawingId = JdbcMapUtil.getString(valueMap, "CC_CONSTRUCTION_DRAWING_ID");
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectOneByWhere(new Where().eq(CcDrawingManagement.Cols.CC_CONSTRUCTION_DRAWING_ID, ccConstructionDrawingId));
            if (!SharedUtil.isEmpty(ccDrawingManagement)) {
                throw new BaseException("已存在套图：" + ccConstructionDrawingId + "!");
            }

        }
    }

}
