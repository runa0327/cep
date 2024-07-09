package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.login.LoginInfo;
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
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.*;

import static com.bid.ext.utils.ImportValueUtils.*;

public class DrawingExt {

    /**
     * 变更图纸状态
     */
    public void updateDrawingStatus() {
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

                // 实际发图日期
                LocalDate actDate = getLocalDateCellValue(row.getCell(8));

                // 三维实际日期
                LocalDate threeDPlanDate = getLocalDateCellValue(row.getCell(11));

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


                CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectOneByWhere(new Where().eq(CcDrawingManagement.Cols.CC_CONSTRUCTION_DRAWING_ID, ccConstructionDrawingId));
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

                    // 初始化套图权限
                    CcDrawingAuth ccDrawingAuth = CcDrawingAuth.newData();
                    ccDrawingAuth.setCcDrawingManagementId(drawingManagement.getId());
                    ccDrawingAuth.setIsView(true);
                    ccDrawingAuth.setIsUpload(true);
                    ccDrawingAuth.setAdUserId(userId);
                    ccDrawingAuth.insertById();
                } else {
                    ccDrawingManagement.setSeqNo(BigDecimal.valueOf(getNumericCellValue(row.getCell(0))));
                    ccDrawingManagement.setCcPrjId(ccPrjId);
                    ccDrawingManagement.setCcPrjStructNodeId(ccPrjStructNodeId);
                    ccDrawingManagement.setCcDrawingTypeId(drawingTypeId);
                    ccDrawingManagement.setName(getStringCellValue(row.getCell(4)));
                    ccDrawingManagement.setCcConstructionDrawingId(getStringCellValue(row.getCell(5)));
                    ccDrawingManagement.setCcSteelOwnerDrawingId(getStringCellValue(row.getCell(6)));
                    if (!SharedUtil.isEmpty(ccPrjProfessionalCode)) {
                        ccDrawingManagement.setCcPrjProfessionalCodeId(lastLetter);
                    }
                    ccDrawingManagement.setPlanDate(getLocalDateCellValue(row.getCell(7)));
                    ccDrawingManagement.setActDate(actDate);
                    ccDrawingManagement.setIsThreeDimensional(getBooleanCellValue(row.getCell(9)));
                    ccDrawingManagement.setThreeDPlanDate(getLocalDateCellValue(row.getCell(10)));
                    ccDrawingManagement.setThreeDActDate(threeDPlanDate);

                    if (SharedUtil.isEmpty(actDate)) {
                        ccDrawingManagement.setCcDrawingStatusId("TODO");
                    } else {
                        ccDrawingManagement.setCcDrawingStatusId("DONE");
                    }

                    Boolean isThreeDimensional = getBooleanCellValue(row.getCell(9));
                    if (isThreeDimensional && SharedUtil.isEmpty(threeDPlanDate)) {
                        ccDrawingManagement.setCcModelStatusId("TODO");
                    } else if (isThreeDimensional && !SharedUtil.isEmpty(threeDPlanDate)) {
                        ccDrawingManagement.setCcModelStatusId("DONE");
                    } else if (!isThreeDimensional) {
                        ccDrawingManagement.setCcModelStatusId(null);
                    }
                    ccDrawingManagement.updateById();
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
        String pCcDrawingMemberIds = JdbcMapUtil.getString(varMap, "P_CC_DRAWING_MEMBER_IDS");
        Boolean isView = (Boolean) varMap.get("P_IS_VIEW");
        Boolean isUpload = (Boolean) varMap.get("P_IS_UPLOAD");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            if (pCcDrawingMemberIds != null && !pCcDrawingMemberIds.isEmpty()) {

                List<String> memberIdList = Arrays.asList(pCcDrawingMemberIds.split(","));

                for (String memberId : memberIdList) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                    String adUserId = ccPrjMember.getAdUserId();
                    CcDrawingAuth ccDrawingAuth = CcDrawingAuth.newData();
                    ccDrawingAuth.setCcDrawingManagementId(csCommId);
                    ccDrawingAuth.setAdUserId(adUserId);
                    ccDrawingAuth.setIsUpload(isUpload);
                    ccDrawingAuth.setIsView(isView);
                    ccDrawingAuth.insertById();
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

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
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
}
