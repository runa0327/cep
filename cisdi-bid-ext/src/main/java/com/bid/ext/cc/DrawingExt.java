package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;

public class DrawingExt {

    /**
     * 变更图纸状态
     */
    public void updateDrawingStatus() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            Map<String, Object> valueMap = entityRecord.valueMap;
            String actDate = JdbcMapUtil.getString(valueMap, "ACT_DATE");
            if (SharedUtil.isEmpty(actDate)) {
                ccDrawingManagement.setCcDrawingStatusId("TODO");
            } else {
                ccDrawingManagement.setCcDrawingStatusId("DONE");
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
     * 上传图纸
     */
    public void uploadDrawing() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            //套图信息
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();

            String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");

            //套图版本
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
                List<CcDrawingUpload> ccDrawingUploads = CcDrawingUpload.selectByWhere(
                        new Where().eq(CcDrawingUpload.Cols.CC_DRAWING_VERSION_ID, version)
                                .eq(CcDrawingUpload.Cols.CC_DRAWING_MANAGEMENT_ID, csCommId)
                );
                if (SharedUtil.isEmpty(ccDrawingUploads)) {
                    ccDrawingVersionId = version;
                    break;
                }
            }

            //套图信息
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            String ccPrjStructNodeId = ccDrawingManagement.getCcPrjStructNodeId();
            String ccSteelOwnerDrawingId = ccDrawingManagement.getCcSteelOwnerDrawingId();

            //套图版本
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
        }
    }

    /**
     * 更新图纸时同步更新图纸版本的业主图号
     */
    public void updateDrawing() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectById(csCommId);
            //业主图号
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
            //图纸
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
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        FlFile flFile = FlFile.selectById(varMap.get("P_CC_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
        if (!"xls".equals(flFile.getExt())) {
            throw new BaseException("请上传'xls'格式的Excel文件");
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
                }

                //实际发图日期
                LocalDate actDate = getLocalDateCellValue(row.getCell(8));

                //三维实际日期
                LocalDate threeDPlanDate = getLocalDateCellValue(row.getCell(11));

                CcDrawingManagement drawingManagement = CcDrawingManagement.newData();

                drawingManagement.setSeqNo(BigDecimal.valueOf(getNumericCellValue(row.getCell(0))));
                drawingManagement.setCcPrjId(ccPrjId);
                drawingManagement.setCcPrjStructNodeId(ccPrjStructNodeId);
                drawingManagement.setCcDrawingTypeId(drawingTypeId);
                drawingManagement.setName(getStringCellValue(row.getCell(4)));
                drawingManagement.setCcConstructionDrawingId(getStringCellValue(row.getCell(5)));
                drawingManagement.setCcSteelOwnerDrawingId(getStringCellValue(row.getCell(6)));
                drawingManagement.setPlanDate(getLocalDateCellValue(row.getCell(7)));
                drawingManagement.setActDate(actDate);
                drawingManagement.setIsThreeDimensional(getBooleanCellValue(row.getCell(9)));
                drawingManagement.setThreeDPlanDate(getLocalDateCellValue(row.getCell(10)));
                drawingManagement.setThreeDActDate(threeDPlanDate);

                if (SharedUtil.isEmpty(actDate)) {
                    drawingManagement.setCcDrawingStatusId("TODO");
                } else {
                    drawingManagement.setCcDrawingStatusId("DONE");
                }

                Boolean isThreeDimensional = getBooleanCellValue(row.getCell(9));
                if (isThreeDimensional && SharedUtil.isEmpty(threeDPlanDate)) {
                    drawingManagement.setCcModelStatusId("TODO");
                } else if (isThreeDimensional && !SharedUtil.isEmpty(threeDPlanDate)) {
                    drawingManagement.setCcModelStatusId("DONE");
                } else if (!isThreeDimensional) {
                    drawingManagement.setCcModelStatusId(null);
                }
                drawingManagement.insertById();
            }

        } catch (IOException e) {
            throw new BaseException("上传文件失败", e);
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private String getStringCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf(cell.getNumericCellValue());
        } else if (cell.getCellType() == CellType.BOOLEAN) {
            return String.valueOf(cell.getBooleanCellValue());
        } else {
            return null;
        }
    }

    private double getNumericCellValue(Cell cell) {
        if (cell == null) {
            return 0;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            return Double.parseDouble(cell.getStringCellValue());
        } else {
            return 0;
        }
    }

    private boolean getBooleanCellValue(Cell cell) {
        if (cell == null) {
            return false;
        }
        if (cell.getCellType() == CellType.BOOLEAN) {
            return cell.getBooleanCellValue();
        } else if (cell.getCellType() == CellType.STRING) {
            String cellValue = cell.getStringCellValue();
            return "是".equals(cellValue) || "true".equalsIgnoreCase(cellValue);
        } else if (cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue() != 0;
        } else {
            return false;
        }
    }

    private LocalDate getLocalDateCellValue(Cell cell) {
        if (cell == null) {
            return null;
        }
        if (cell.getCellType() == CellType.NUMERIC) {
            Date date = cell.getDateCellValue();
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else if (cell.getCellType() == CellType.STRING) {
            return LocalDate.parse(cell.getStringCellValue());
        } else {
            return null;
        }
    }

    /**
     * 图纸权限
     */
    public void drawingAuth() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pAdUserIds = JdbcMapUtil.getString(varMap, "P_AD_USER_IDS");
        Boolean isView = (Boolean) varMap.get("P_IS_VIEW");
        Boolean isUpload = (Boolean) varMap.get("P_IS_UPLOAD");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            if (pAdUserIds != null && !pAdUserIds.isEmpty()) {
                List<String> userIdList = Arrays.asList(pAdUserIds.split(","));
                for (String userId : userIdList) {
                    CcDrawingAuth ccDrawingAuth = CcDrawingAuth.newData();
                    ccDrawingAuth.setCcDrawingManagementId(csCommId);
                    ccDrawingAuth.setAdUserId(userId);
                    ccDrawingAuth.setIsUpload(isUpload);
                    ccDrawingAuth.setIsView(isView);
                    ccDrawingAuth.insertById();
                }
            }

        }
    }
}
