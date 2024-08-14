package com.bid.ext.cc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.bid.ext.model.*;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
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
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.bid.ext.cc.GenExt.checkFileExists;
import static com.bid.ext.cc.GenExt.saveWordToFile;
import static com.bid.ext.utils.ImportValueUtils.*;

@Slf4j
public class DrawingExt {

    /**
     * 变更图纸状态
     */
    public void updateDrawingStatus() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);

            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();
            String lastLetter = "";
            if (ccSteelOwnerDrawingId != null && ccSteelOwnerDrawingId.length() > 0) {
                for (int i = ccSteelOwnerDrawingId.length() - 1; i >= 0; i--) {
                    char c = ccSteelOwnerDrawingId.charAt(i);
                    if (Character.isLetter(c)) {
                        lastLetter = String.valueOf(c);
                        break;
                    }
                }
            }
            CcPrjProfessionalCode ccPrjProfessionalCode = CcPrjProfessionalCode.selectById(lastLetter);
            if (!SharedUtil.isEmpty(ccPrjProfessionalCode)) {
                ccDrawingManagement.setCcPrjProfessionalCodeId(lastLetter);
            }

            Map<String, Object> valueMap = entityRecord.valueMap;
            String actDate = JdbcMapUtil.getString(valueMap, "ACT_DATE");
            List<CcDrawingUpload> ccDrawingUploads = CcDrawingUpload.selectByWhere(new Where().eq(CcDrawingUpload.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId));
            if (!SharedUtil.isEmpty(ccDrawingUploads) && !SharedUtil.isEmpty(actDate)) {
                ccDrawingManagement.setCcDrawingStatusId("DONE");
            } else {
                ccDrawingManagement.setCcDrawingStatusId("TODO");
            }
            String threeDActDate = JdbcMapUtil.getString(valueMap, "THREE_D_ACT_DATE");
            Boolean isThreeDimensional = ccDrawingManagement.getIsThreeDimensional();
            if (isThreeDimensional && SharedUtil.isEmpty(threeDActDate)) {
                ccDrawingManagement.setCcModelStatusId("TODO");
            } else if (isThreeDimensional && !SharedUtil.isEmpty(threeDActDate)) {
                ccDrawingManagement.setCcModelStatusId("DONE");
            } else if (!isThreeDimensional) {
                ccDrawingManagement.setCcModelStatusId(null);
            }
            ccDrawingManagement.updateById();
        }
    }

    /**
     * 删除项目图纸版本时设置默认版本
     */
    public void deleteVersion() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcStructDrawingVersion ccStructDrawingVersion = CcStructDrawingVersion.selectById(csCommId);
            String ccDrawingManagementId = ccStructDrawingVersion.getCcDrawingManagementId();
            String ccDrawingVersionId = ccStructDrawingVersion.getCcDrawingVersionId();
            char currentChar = ccDrawingVersionId.charAt(0);
            if (currentChar == 'A') {
                ccDrawingVersionId = null;
                CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(ccDrawingManagementId);
                ccDrawingManagement.setCcDrawingStatusId("TODO");
                ccDrawingManagement.updateById();
            } else if (currentChar > 'A' && currentChar <= 'G') {
                char previousChar = (char) (currentChar - 1);
                ccDrawingVersionId = String.valueOf(previousChar);
            }
            List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, ccDrawingManagementId).eq(CcStructDrawingVersion.Cols.CC_DRAWING_VERSION_ID, ccDrawingVersionId));
            if (!SharedUtil.isEmpty(ccStructDrawingVersions)) {
                for (CcStructDrawingVersion ccStructDrawingVersion1 : ccStructDrawingVersions) {
                    ccStructDrawingVersion1.setIsDefault(true);
                    ccStructDrawingVersion1.updateById();
                }
            }
        }
    }

    /**
     * 上传图纸
     */
    public void uploadDrawing() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            // 套图信息
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();

            String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");

            // 套图版本
            CcStructDrawingVersion ccStructDrawingVersion = CcStructDrawingVersion.newData();
            ccStructDrawingVersion.setCcDrawingVersionId("A");
            ccStructDrawingVersion.setCcDrawingManagementId(csCommId);
            ccStructDrawingVersion.setCcPrjStructNodeId(ccPrjStructNodeId);
            ccStructDrawingVersion.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
            ccStructDrawingVersion.insertById();

            List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
            for (String attachmentId : ccAttachmentList) {
                FlFile flFile = FlFile.selectById(attachmentId);
                String dspName = flFile.getDspName();
                CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                ccDrawingUpload.setCcAttachment(attachmentId);
                ccDrawingUpload.setCcDrawingVersionId("A");
                ccDrawingUpload.setCcDrawingManagementId(csCommId);
                ccDrawingUpload.setName(dspName);
                ccDrawingUpload.insertById();
            }
        }
    }

    /**
     * 升级图纸
     */
    public void upgradeDrawing() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
            String pActDate = JdbcMapUtil.getString(varMap, "P_ACT_DATE");

            Boolean isDefault = (Boolean) varMap.get("P_IS_DEFAULT");

            if (isDefault) {
                List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId));
                if (!SharedUtil.isEmpty(ccStructDrawingVersions)) {
                    for (CcStructDrawingVersion ccStructDrawingVersion1 : ccStructDrawingVersions) {
                        ccStructDrawingVersion1.setIsDefault(false);
                        ccStructDrawingVersion1.updateById();
                    }
                }
            }

            String[] versionOrder = {"A", "B", "C", "D", "E", "F", "G"};
            String ccDrawingVersionId = null;

            // 依次判断版本的存在性
            for (String version : versionOrder) {
                List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(
                        new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_VERSION_ID, version)
                                .eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId)
                );
                if (SharedUtil.isEmpty(ccStructDrawingVersions)) {
                    ccDrawingVersionId = version;
                    break;
                }
            }

            // 套图信息
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
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
            ccStructDrawingVersion.setIsDefault(isDefault);
            ccStructDrawingVersion.insertById();

            List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
            for (String attachmentId : ccAttachmentList) {
                FlFile flFile = FlFile.selectById(attachmentId);

                // 加水印开始
                String flPathId = flFile.getFlPathId();
                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, flPathId);
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);
                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());
                String fullPath = flPath.getDir() + year + "/" + month + "/" + day + "/";
//                addWaterMark("(注:本平台所有图纸仅供湛江零碳项目建设过程参考使用，施工应以设计单位正式提交的纸质图纸为准。)",flFile.getPhysicalLocation(),fullPath);
                addWaterMark("(注:本平台所有图纸仅供湛江零碳项目建设过程参考使用，施工应以设计单位正式提交的纸质图纸为准。)", flFile.getPhysicalLocation(), fullPath);
                // 加水印结束

                String dspName = flFile.getDspName();
                String pRemark = JdbcMapUtil.getString(varMap, "P_REMARK");
                CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                ccDrawingUpload.setCcStructDrawingVersionId(ccStructDrawingVersion.getId());
                ccDrawingUpload.setCcAttachment(attachmentId);
                ccDrawingUpload.setCcDrawingVersionId(ccDrawingVersionId);
                ccDrawingUpload.setRemark(pRemark);
                ccDrawingUpload.setCcDrawingManagementId(csCommId);
                ccDrawingUpload.setName(dspName);
                ccDrawingUpload.insertById();
            }
            // 若没有实际发图日期则更新套图实际发图日期
            if (SharedUtil.isEmpty(ccDrawingManagement.getActDate())) {
                ccDrawingManagement.setActDate(LocalDate.parse(pActDate));
                ccDrawingManagement.setCcDrawingStatusId("DONE");
                ccDrawingManagement.updateById();
            }
        }
    }

    /**
     * 更新图纸时同步更新图纸版本的业主图号
     */
    public void updateDrawing() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            // 业主图号
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();
            List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId));
            if (!SharedUtil.isEmpty(ccStructDrawingVersions)) {
                for (CcStructDrawingVersion ccStructDrawingVersion : ccStructDrawingVersions) {
                    ccStructDrawingVersion.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
                    ccStructDrawingVersion.updateById();
                }
            }
        }
    }


    /**
     * 设为默认套图
     */
    public void setDefaultDrawingVersion() {
        InvokeActResult invokeActResult = new InvokeActResult();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcStructDrawingVersion ccStructDrawingVersion = CcStructDrawingVersion.selectById(csCommId);
            // 图纸
            String ccDrawingManagementId = ccStructDrawingVersion.getCcDrawingManagementId();
            List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, ccDrawingManagementId));
            if (!SharedUtil.isEmpty(ccStructDrawingVersions)) {
                for (CcStructDrawingVersion ccStructDrawingVersion1 : ccStructDrawingVersions) {
                    ccStructDrawingVersion1.setIsDefault(false);
                    ccStructDrawingVersion1.updateById();
                }
            }
            ccStructDrawingVersion.setIsDefault(true);
            ccStructDrawingVersion.updateById();
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 导入图纸计划
     */
    public void importDrawingPlan() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

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
                // 3保险：
                if (row == null) {
                    break; // 如果为空，直接跳出。
                }
                Cell cell = row.getCell(1);
                if (cell == null) {
                    break;
                }
                String stringCellValue = getStringCellValue(cell);
                if (SharedUtil.isEmpty(stringCellValue)) {
                    break;
                }

                try {

                    String prjStructNodeCode = getStringCellValue(row.getCell(1));
                    if (prjStructNodeCode != null && prjStructNodeCode.endsWith(".0")) {
                        prjStructNodeCode = prjStructNodeCode.substring(0, prjStructNodeCode.length() - 2);
                    }
                    CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.CODE, prjStructNodeCode));
                    String ccPrjStructNodeId = ccPrjStructNode.getId();
                    String ccPrjId = ccPrjStructNode.getCcPrjId();

                    String drawingType = getStringCellValue(row.getCell(3));
                    String drawingTypeId = null;
                    switch (drawingType) {
                        case "普通施工图":
                            drawingTypeId = "common";
                            break;
                        case "安装图":
                            drawingTypeId = "installation";
                            break;
                        case "施工设计文本":
                            drawingTypeId = "constructionDesign";
                            break;
                        case "压力管道施工图":
                            drawingTypeId = "pressurePipelineConstruction";
                            break;
                        case "转化图":
                            drawingTypeId = "conversion";
                            break;
                    }


                    // 实际发图日期
                    LocalDate actDate = getLocalDateCellValue(row.getCell(8));

                    // 三维实际日期
//                    LocalDate threeDPlanDate = getLocalDateCellValue(row.getCell(11));

                    String ccConstructionDrawingId = getStringCellValue(row.getCell(5));
                    String ccSteelOwnerDrawingId = getStringCellValue(row.getCell(6));
                    String lastLetter = "";
                    if (ccSteelOwnerDrawingId != null && ccSteelOwnerDrawingId.length() > 0) {
                        for (int j = ccSteelOwnerDrawingId.length() - 1; j >= 0; j--) {
                            char c = ccSteelOwnerDrawingId.charAt(j);
                            if (Character.isLetter(c)) {
                                lastLetter = String.valueOf(c);
                                break;
                            }
                        }
                    }
                    CcPrjProfessionalCode ccPrjProfessionalCode = CcPrjProfessionalCode.selectById(lastLetter);

                    String ccPartyCompanyId = null;
                    String designUnit = getStringCellValue(row.getCell(12));
                    String sql = "SELECT t.id FROM cc_party_company t JOIN cc_company c ON c.id = t.cc_company_id JOIN cc_prj_party pp ON pp.id = t.CC_PRJ_PARTY_ID JOIN cc_party p ON pp.CC_PARTY_ID = p.id WHERE c.FULL_NAME ->> '$.ZH_CN' = ? AND p.NAME ->> '$.ZH_CN' = ?";
                    Map<String, Object> ccPartyCompany = myJdbcTemplate.queryForMap(sql, designUnit, "设计单位");
                    if (!SharedUtil.isEmpty(ccPartyCompany)) {
                        ccPartyCompanyId = JdbcMapUtil.getString(ccPartyCompany, "id");
                    }

                    CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectOneByWhere(new Where().eq(CcDrawingManagement.Cols.CC_STEEL_OWNER_DRAWING_ID, ccSteelOwnerDrawingId));
                    if (SharedUtil.isEmpty(ccDrawingManagement)) {

                        CcDrawingManagement drawingManagement = CcDrawingManagement.newData();

                        drawingManagement.setSeqNo(BigDecimal.valueOf(getNumericCellValue(row.getCell(0))));
                        drawingManagement.setCcPrjId(ccPrjId);
                        drawingManagement.setCcPrjStructNodeId(ccPrjStructNodeId);
                        drawingManagement.setCcDrawingTypeId(drawingTypeId);
                        drawingManagement.setName(getStringCellValue(row.getCell(4)));
                        drawingManagement.setCcConstructionDrawingId(ccConstructionDrawingId);
                        drawingManagement.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
                        if (!SharedUtil.isEmpty(ccPrjProfessionalCode)) {
                            drawingManagement.setCcPrjProfessionalCodeId(lastLetter);
                        }
                        drawingManagement.setPlanDate(getLocalDateCellValue(row.getCell(7)));
                        drawingManagement.setActDate(actDate);
                        drawingManagement.setIsThreeDimensional(getBooleanCellValue(row.getCell(9)));
                        drawingManagement.setThreeDPlanDate(getLocalDateCellValue(row.getCell(10)));
//                        drawingManagement.setThreeDActDate(threeDPlanDate);
                        drawingManagement.setCcDrawingStatusId("TODO");

                        Boolean isThreeDimensional = getBooleanCellValue(row.getCell(9));
                        if (isThreeDimensional) {
                            drawingManagement.setCcModelStatusId("TODO");
                        }
                        drawingManagement.setCcPartyCompanyId(ccPartyCompanyId);
                        drawingManagement.insertById();

                        // 初始化套图权限
                        CcDrawingAuth ccDrawingAuth = CcDrawingAuth.newData();
                        ccDrawingAuth.setCcDrawingManagementId(drawingManagement.getId());
                        ccDrawingAuth.setIsView(true);
                        ccDrawingAuth.setIsUpload(true);
                        ccDrawingAuth.setAdUserId(userId);
                        ccDrawingAuth.insertById();
                    } else {
                        ccDrawingManagement.setSeqNo(!SharedUtil.isEmpty(getNumericCellValue(row.getCell(0))) ? BigDecimal.valueOf(getNumericCellValue(row.getCell(0))) : ccDrawingManagement.getSeqNo());
                        ccDrawingManagement.setCcPrjId(!SharedUtil.isEmpty(ccPrjId) ? ccPrjId : ccDrawingManagement.getCcPrjId());
                        ccDrawingManagement.setCcPrjStructNodeId(!SharedUtil.isEmpty(ccPrjStructNodeId) ? ccPrjStructNodeId : ccDrawingManagement.getCcPrjStructNodeId());
                        ccDrawingManagement.setCcDrawingTypeId(!SharedUtil.isEmpty(drawingTypeId) ? drawingTypeId : ccDrawingManagement.getCcDrawingTypeId());
                        ccDrawingManagement.setName(!SharedUtil.isEmpty(getStringCellValue(row.getCell(4))) ? getStringCellValue(row.getCell(4)) : ccDrawingManagement.getName());
                        ccDrawingManagement.setCcConstructionDrawingId(!SharedUtil.isEmpty(ccConstructionDrawingId) ? ccConstructionDrawingId : ccDrawingManagement.getCcConstructionDrawingId());
                        ccDrawingManagement.setCcSteelOwnerDrawingId(!SharedUtil.isEmpty(getStringCellValue(row.getCell(6))) ? getStringCellValue(row.getCell(6)) : ccDrawingManagement.getCcSteelOwnerDrawingId());
                        ccDrawingManagement.setCcPrjProfessionalCodeId(!SharedUtil.isEmpty(ccPrjProfessionalCode) ? lastLetter : ccDrawingManagement.getCcPrjProfessionalCodeId());
                        ccDrawingManagement.setPlanDate(!SharedUtil.isEmpty(getLocalDateCellValue(row.getCell(7))) ? getLocalDateCellValue(row.getCell(7)) : ccDrawingManagement.getPlanDate());
                        ccDrawingManagement.setActDate(!SharedUtil.isEmpty(actDate) ? actDate : ccDrawingManagement.getActDate());
                        ccDrawingManagement.setIsThreeDimensional(!SharedUtil.isEmpty(getBooleanCellValue(row.getCell(9))) ? getBooleanCellValue(row.getCell(9)) : ccDrawingManagement.getIsThreeDimensional());
                        ccDrawingManagement.setThreeDPlanDate(!SharedUtil.isEmpty(getLocalDateCellValue(row.getCell(10))) ? getLocalDateCellValue(row.getCell(10)) : ccDrawingManagement.getThreeDPlanDate());
                        // ccDrawingManagement.setThreeDActDate(!SharedUtil.isEmpty(threeDActDate) ? threeDActDate : ccDrawingManagement.getThreeDActDate());
                        ccDrawingManagement.setCcPartyCompanyId(!SharedUtil.isEmpty(ccPartyCompanyId) ? ccPartyCompanyId : ccDrawingManagement.getCcPartyCompanyId());
                        ccDrawingManagement.updateById();
                    }
                } catch (Exception e) {
                    throw new BaseException("在第 " + (i + 1) + " 行的单元格格式错误!" + e);
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
     * 图纸权限
     */
    public void drawingAuth() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pCcPrjStructNodeIds = JdbcMapUtil.getString(varMap, "P_CC_PRJ_STRUCT_NODE_ID");
        String pCcDrawingMemberIds = JdbcMapUtil.getString(varMap, "P_CC_DRAWING_MEMBER_IDS");
        Boolean isView = (Boolean) varMap.get("P_IS_VIEW");
        Boolean isUpload = (Boolean) varMap.get("P_IS_UPLOAD");

        if (SharedUtil.isEmpty(pCcPrjStructNodeIds)) {
            for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
                String csCommId = entityRecord.csCommId;
                if (pCcDrawingMemberIds != null && !pCcDrawingMemberIds.isEmpty()) {

                    List<String> memberIdList = Arrays.asList(pCcDrawingMemberIds.split(","));

                    for (String memberId : memberIdList) {
                        CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                        String adUserId = ccPrjMember.getAdUserId();

                        List<CcDrawingAuth> ccDrawingAuths = CcDrawingAuth.selectByWhere(new Where().eq(CcDrawingAuth.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId).eq(CcDrawingAuth.Cols.AD_USER_ID, adUserId));
                        if (SharedUtil.isEmpty(ccDrawingAuths)) {
                            CcDrawingAuth ccDrawingAuth = CcDrawingAuth.newData();
                            ccDrawingAuth.setCcDrawingManagementId(csCommId);
                            ccDrawingAuth.setAdUserId(adUserId);
                            ccDrawingAuth.setIsUpload(isUpload);
                            ccDrawingAuth.setIsView(isView);
                            ccDrawingAuth.insertById();
                        } else {
                            for (CcDrawingAuth ccDrawingAuth : ccDrawingAuths) {
                                ccDrawingAuth.setIsUpload(isUpload);
                                ccDrawingAuth.setIsView(isView);
                                ccDrawingAuth.updateById();
                            }
                        }
                    }
                }
            }
        } else {
            List<String> memberIdList = Arrays.asList(pCcDrawingMemberIds.split(","));

            for (String memberId : memberIdList) {
                CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                String adUserId = ccPrjMember.getAdUserId();
                List<String> pCcPrjStructNodeIdList = Arrays.asList(pCcPrjStructNodeIds.split(","));
                for (String pCcPrjStructNodeId : pCcPrjStructNodeIdList) {
                    List<CcDrawingAuth> ccDrawingAuths = CcDrawingAuth.selectByWhere(new Where().eq(CcDrawingAuth.Cols.CC_PRJ_STRUCT_NODE_ID, pCcPrjStructNodeId).eq(CcDrawingAuth.Cols.AD_USER_ID, adUserId));
                    if (SharedUtil.isEmpty(ccDrawingAuths)) {
                        CcDrawingAuth ccDrawingAuth = CcDrawingAuth.newData();
                        ccDrawingAuth.setCcPrjStructNodeId(pCcPrjStructNodeId);
                        ccDrawingAuth.setAdUserId(adUserId);
                        ccDrawingAuth.setIsUpload(isUpload);
                        ccDrawingAuth.setIsView(isView);
                        ccDrawingAuth.insertById();
                    } else {
                        for (CcDrawingAuth ccDrawingAuth : ccDrawingAuths) {
                            ccDrawingAuth.setIsUpload(isUpload);
                            ccDrawingAuth.setIsView(isView);
                            ccDrawingAuth.updateById();
                        }
                    }
                }
            }
        }
    }

    /**
     * 创建套图时初始化套图权限（查看、上传/升级）
     */
    public void initDrawingAuth() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
            String userId = loginInfo.userInfo.id;
            String csCommId = entityRecord.csCommId;
            CcDrawingAuth ccDrawingAuth = CcDrawingAuth.newData();
            ccDrawingAuth.setCcDrawingManagementId(csCommId);
            ccDrawingAuth.setIsView(true);
            ccDrawingAuth.setIsUpload(true);
            ccDrawingAuth.setAdUserId(userId);
            ccDrawingAuth.insertById();
        }
    }

    /**
     * BIM工程量导入
     */
    public void importBIMQuantities() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        FlFile flFile = FlFile.selectById(varMap.get("P_CC_ATTACHMENT").toString());

        // 工程量类型
        String pCcEngineeringQuantityTypeId = varMap.get("P_CC_ENGINEERING_QUANTITY_TYPE_ID").toString();
        // 填报类型
        String ccEngineeringTypeId = "BIM";

        // 验证文件类型
        String fileExt = flFile.getExt();
        if (!"xls".equals(fileExt) && !"xlsx".equals(fileExt)) {
            throw new BaseException("请上传'xls'或'xlsx'格式的Excel文件");
        }

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccPrjId = ccDrawingManagement.getCcPrjId();

            String filePath = flFile.getPhysicalLocation();
            String ccUomTypeId = null;
            BigDecimal totalWeight = BigDecimal.ZERO; // 初始化总重量为 0

            try (FileInputStream file = new FileInputStream(new File(filePath))) {
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                // 遍历每一行
                for (int i = 1; i <= Objects.requireNonNull(sheet).getLastRowNum(); i++) {
                    Row row = sheet.getRow(i);
                    if (row == null) continue; // 如果为空，跳过该行

                    Cell cell = null;

                    switch (pCcEngineeringQuantityTypeId) {
                        case "STEELSTRUCTURE":
                            cell = row.getCell(3); // 获取第4列
                            ccUomTypeId = "t";
                            break;
                        case "PIPELINE":
                            cell = row.getCell(7); // 获取第8列
                            ccUomTypeId = "t";
                            break;
                        case "CABLETRAY":
                            cell = row.getCell(6); // 获取第7列
                            ccUomTypeId = "t";
                            break;
                        case "PILE":
                        case "FOUNDATION":
                        case "SUPERSTRUCTURECONCRETE":
                            cell = row.getCell(3); // 获取第4列
                            ccUomTypeId = "m³";
                            break;
                    }

                    if (cell == null) continue;

                    BigDecimal weight = null;
                    if (cell.getCellType() == CellType.NUMERIC) {
                        weight = BigDecimal.valueOf(cell.getNumericCellValue());
                    } else if (cell.getCellType() == CellType.FORMULA) {
                        weight = BigDecimal.valueOf(evaluator.evaluate(cell).getNumberValue());
                    }

                    if (weight != null) {
                        totalWeight = totalWeight.add(weight); // 累加重量
                    }
                }

                // 保留两位小数
                totalWeight = totalWeight.setScale(2, RoundingMode.HALF_UP);

                CcEngineeringQuantity ccEngineeringQuantity = CcEngineeringQuantity.newData();
                ccEngineeringQuantity.setCcPrjId(ccPrjId);
                ccEngineeringQuantity.setCcPrjStructNodeId(ccPrjStructNodeId);
                ccEngineeringQuantity.setCcEngineeringTypeId(ccEngineeringTypeId);
                ccEngineeringQuantity.setCcEngineeringQuantityTypeId(pCcEngineeringQuantityTypeId);
                ccEngineeringQuantity.setCcUomTypeId(ccUomTypeId);
                ccEngineeringQuantity.setTotalWeight(totalWeight);
                ccEngineeringQuantity.setCcDrawingManagementId(csCommId);
                ccEngineeringQuantity.insertById();

            } catch (IOException e) {
                throw new BaseException("上传文件失败", e);
            }
        }

    }


    /**
     * 跳转BIM页面
     */
    public void redirect() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        String csCommId = entityRecord.csCommId;
        CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
        String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();
        String sql = "select t.name from CC_BIM_UPLOAD_URL t where t.id = 'ZJ'";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql);
        Map<String, Object> map = list.get(0);

        String http = map.get("NAME").toString();
        String url = http + "/modelUpload/index?projectId=165&catelogId=7777&ttNumber=" + ccSteelOwnerDrawingId;

        UrlToOpen extBrowserWindowToOpen = new UrlToOpen();
        extBrowserWindowToOpen.url = url;
        extBrowserWindowToOpen.title = "BIM模型";
        // 打开时还要携带身份token：
        extBrowserWindowToOpen.carryIdentity = true;
        invokeActResult.urlToOpenList.add(extBrowserWindowToOpen);
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 压缩包上传图纸
     */
    public void uploadZipDrawingPlan() throws IOException {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
        String pRemark = JdbcMapUtil.getString(varMap, "P_REMARK");
        String pActDate = JdbcMapUtil.getString(varMap, "P_ACT_DATE");

        List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
        int count1 = ccAttachmentList.size();
        int count2 = 0;
        int count3 = 0;
        int count4 = 0;
        List<String> idList = new ArrayList<>();

        for (String attachmentId : ccAttachmentList) {
            FlFile flFile = FlFile.selectById(attachmentId);
            String dspName = flFile.getDspName(); // 获取文件名
            String name = flFile.getName();
            int index = dspName.indexOf('-');

            if (index == -1) {
                count3++;
                continue; // 跳过命名不规范的文件
            }

            String ccConstructionDrawingId = dspName.substring(0, index);
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectOneByWhere(new Where().eq(CcDrawingManagement.Cols.CC_CONSTRUCTION_DRAWING_ID, ccConstructionDrawingId));
            if (SharedUtil.isEmpty(ccDrawingManagement)) {
                count4++;
                idList.add(ccConstructionDrawingId);
                continue; // 跳过在图纸列表中未找到对应图纸套图号的文件
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

            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();

            CcStructDrawingVersion ccStructDrawingVersion = CcStructDrawingVersion.newData();
            ccStructDrawingVersion.setCcDrawingVersionId(ccDrawingVersionId);
            CcDrawingVersion ccDrawingVersion = CcDrawingVersion.selectById(ccDrawingVersionId);
            String ccDrawingVersionName = ccDrawingVersion.getName();
            ccStructDrawingVersion.setName(ccDrawingVersionName);
            ccStructDrawingVersion.setCcDrawingManagementId(csCommId);
            ccStructDrawingVersion.setCcPrjStructNodeId(ccPrjStructNodeId);
            ccStructDrawingVersion.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
            ccStructDrawingVersion.setIsDefault(true);

            String zipFilePath = flFile.getOriginFilePhysicalLocation();
            Where attWhere = new Where();
            attWhere.eq(AdAtt.Cols.CODE, CcDrawingUpload.Cols.CC_ATTACHMENT);
            AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);

            Where pathWhere = new Where();
            pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
            FlPath flPath = FlPath.selectOneByWhere(pathWhere);

            LocalDate now = LocalDate.now();
            int year = now.getYear();
            String month = String.format("%02d", now.getMonthValue());
            String day = String.format("%02d", now.getDayOfMonth());

            String outputDir = flPath.getDir() + year + "/" + month + "/" + day + "/" + name + ccDrawingVersionName;

            File zipFile = FileUtil.file(zipFilePath);
            File outputDirectory = FileUtil.mkdir(outputDir);

            java.util.zip.ZipFile tempZipFile = new java.util.zip.ZipFile(zipFile, Charset.forName("GBK"));
            ZipUtil.unzip(tempZipFile, outputDirectory);

            ccStructDrawingVersion.insertById();
            processUnzippedFiles(outputDirectory, flPath, year, month, day, loginInfo, ccStructDrawingVersion, pRemark, pActDate, ccDrawingManagement);
            ccDrawingManagement.setCcDrawingStatusId("DONE");
            ccDrawingManagement.updateById();
            count2++;
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        invokeActResult.msg = "本次上传" + count1 + "套图纸，成功上传" + count2 + "套，命名不规范" + count3 + "套，图纸列表中未找到对应图纸套图号" + count4 + "套；未找到对应图纸套图号为：" + idList;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private void processUnzippedFiles(File file, FlPath flPath, int year, String month, String day, LoginInfo loginInfo, CcStructDrawingVersion ccStructDrawingVersion, String pRemark, String pActDate, CcDrawingManagement ccDrawingManagement) {
        if (file.isDirectory()) {
            for (File subFile : file.listFiles()) {
                processUnzippedFiles(subFile, flPath, year, month, day, loginInfo, ccStructDrawingVersion, pRemark, pActDate, ccDrawingManagement);
            }
        } else {
            String faPath = "";
            String path = "";
            // 处理文件
            try (InputStream inputStream = Files.newInputStream(file.toPath());
                 ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                IoUtil.copy(inputStream, outputStream);
                byte[] fileBytes = outputStream.toByteArray();

                FlFile newFile = FlFile.newData();
                String fileId = newFile.getId();
                faPath = flPath.getDir() + year + "/" + month + "/" + day + "/";
                path = flPath.getDir() + year + "/" + month + "/" + day + "/" + fileId + ".pdf";
                saveWordToFile(fileBytes, path);
                boolean fileExists = checkFileExists(path);
                if (fileExists) {
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
                    newFile.setIsPublicRead(false);
                    newFile.insertById();
                    CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                    ccDrawingUpload.setCcStructDrawingVersionId(ccStructDrawingVersion.getId());
                    ccDrawingUpload.setCcAttachment(fileId);
                    ccDrawingUpload.setCcDrawingVersionId(ccStructDrawingVersion.getCcDrawingVersionId());
                    ccDrawingUpload.setRemark(pRemark);
                    ccDrawingUpload.setCcDrawingManagementId(ccDrawingManagement.getId());
                    ccDrawingUpload.setName(fileName);
                    ccDrawingUpload.insertById();

                    if (SharedUtil.isEmpty(ccDrawingManagement.getActDate())) {
                        ccDrawingManagement.setActDate(LocalDate.parse(pActDate));
                        ccDrawingManagement.setCcDrawingStatusId("DONE");
                        ccDrawingManagement.updateById();
                    }
                } else {
                    throw new BaseException("文件未找到：" + path);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 加水印
            addWaterMark("(注:本平台所有图纸仅供湛江零碳项目建设过程参考使用，施工应以设计单位正式提交的纸质图纸为准。)", path, faPath);
        }
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

    /**
     * 更新时检查已有套图号
     */
    public void updateCheckDrawing() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagementOrigin = CcDrawingManagement.selectById(csCommId);
            String ccConstructionDrawingIdOrigin = ccDrawingManagementOrigin.getCcConstructionDrawingId();
            String ccConstructionDrawingId = JdbcMapUtil.getString(valueMap, "CC_CONSTRUCTION_DRAWING_ID");
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectOneByWhere(new Where().eq(CcDrawingManagement.Cols.CC_CONSTRUCTION_DRAWING_ID, ccConstructionDrawingId));
            if (!ccConstructionDrawingIdOrigin.equals(ccConstructionDrawingId) && !SharedUtil.isEmpty(ccDrawingManagement)) {
                throw new BaseException("已存在套图：" + ccConstructionDrawingId + "!");
            }

        }
    }

    /**
     * @param name
     * @param copyPath
     * @param pdfPath
     * @return
     */
    public Boolean addWaterMark(String name, String copyPath, String pdfPath) {
        boolean res = true;
        try {
            PdfReader reader = new PdfReader(copyPath);
            int n = reader.getNumberOfPages();
            PdfStamper stamper;
            stamper = new PdfStamper(reader, new FileOutputStream(pdfPath + "tmp.pdf"));
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.2f);
            PdfContentByte over;
            Rectangle pagesize;
            float x, y;
            for (int i = 1; i <= n; i++) {
                pagesize = reader.getPageSizeWithRotation(i);
                x = pagesize.getWidth();
                y = pagesize.getHeight();
                over = stamper.getOverContent(i);
                over.saveState();
                over.setGState(gs1);
                over.setFontAndSize(BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED), 0.02f * x);
//                over.showTextAligned(Element.ALIGN_CENTER , name, x, y*0.6f, 20);
//                over.showTextAligned(Element.ALIGN_CENTER , name, x, y*1.5f, 20);
                over.showTextAligned(Element.ALIGN_CENTER, name, x * 0.5f, y * 0.5f, 30);
                over.restoreState();
            }
            stamper.close();
            reader.close();

            File originalFile = new File(copyPath);
            if (originalFile.delete()) {
                File tempFile = new File(pdfPath + "tmp.pdf");
                tempFile.renameTo(originalFile);
            } else {
                throw new DocumentException("重命名添加水印后的pdf失败");
            }

        } catch (DocumentException | IOException e) {
            res = false;
//            errorBuilder.append("添加水印失败\n ");
            log.error("添加水印失败，详情： ", e);
        }
        return res;
    }

    /**
     * 升级图纸
     */
    public void upgradeDrawingWithWaterMark() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
            String pActDate = JdbcMapUtil.getString(varMap, "P_ACT_DATE");

            Boolean isDefault = (Boolean) varMap.get("P_IS_DEFAULT");

            if (isDefault) {
                List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId));
                if (!SharedUtil.isEmpty(ccStructDrawingVersions)) {
                    for (CcStructDrawingVersion ccStructDrawingVersion1 : ccStructDrawingVersions) {
                        ccStructDrawingVersion1.setIsDefault(false);
                        ccStructDrawingVersion1.updateById();
                    }
                }
            }

            String[] versionOrder = {"A", "B", "C", "D", "E", "F", "G"};
            String ccDrawingVersionId = null;

            // 依次判断版本的存在性
            for (String version : versionOrder) {
                List<CcStructDrawingVersion> ccStructDrawingVersions = CcStructDrawingVersion.selectByWhere(
                        new Where().eq(CcStructDrawingVersion.Cols.CC_DRAWING_VERSION_ID, version)
                                .eq(CcStructDrawingVersion.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId)
                );
                if (SharedUtil.isEmpty(ccStructDrawingVersions)) {
                    ccDrawingVersionId = version;
                    break;
                }
            }

            // 套图信息
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
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
            ccStructDrawingVersion.setIsDefault(isDefault);
            ccStructDrawingVersion.insertById();

            List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
            for (String attachmentId : ccAttachmentList) {
                FlFile flFile = FlFile.selectById(attachmentId);
                String flPathId = flFile.getFlPathId();
                Where pathWhere = new Where();
                pathWhere.eq(FlPath.Cols.ID, flPathId);
                FlPath flPath = FlPath.selectOneByWhere(pathWhere);

                LocalDate now = LocalDate.now();
                int year = now.getYear();
                String month = String.format("%02d", now.getMonthValue());
                String day = String.format("%02d", now.getDayOfMonth());
                String fullPath = flPath.getDir() + year + "/" + month + "/" + day + "/";

//                addWaterMark("(注:本平台所有图纸仅供湛江零碳项目建设过程参考使用，施工应以设计单位正式提交的纸质图纸为准。)",flFile.getPhysicalLocation(),fullPath);
                addWaterMark("(注:本平台所有图纸仅供湛江零碳项目建设过程参考使用，施工应以设计单位正式提交的纸质图纸为准。)", flFile.getPhysicalLocation(), fullPath);

                String dspName = flFile.getDspName();
                String pRemark = JdbcMapUtil.getString(varMap, "P_REMARK");
                CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                ccDrawingUpload.setCcStructDrawingVersionId(ccStructDrawingVersion.getId());
                ccDrawingUpload.setCcAttachment(attachmentId);
                ccDrawingUpload.setCcDrawingVersionId(ccDrawingVersionId);
                ccDrawingUpload.setRemark(pRemark);
                ccDrawingUpload.setCcDrawingManagementId(csCommId);
                ccDrawingUpload.setName(dspName);
                ccDrawingUpload.insertById();
            }
            // 若没有实际发图日期则更新套图实际发图日期
            if (SharedUtil.isEmpty(ccDrawingManagement.getActDate())) {
                ccDrawingManagement.setActDate(LocalDate.parse(pActDate));
                ccDrawingManagement.setCcDrawingStatusId("DONE");
                ccDrawingManagement.updateById();
            }
        }
    }


}
