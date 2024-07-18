package com.bid.ext.cc;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ZipUtil;
import com.bid.ext.model.*;
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
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static com.bid.ext.cc.GenExt.checkFileExists;
import static com.bid.ext.cc.GenExt.saveWordToFile;
import static com.bid.ext.utils.ImportValueUtils.*;

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
            String sql = "select 1 from CC_DRAWING_UPLOAD du where du.CC_DRAWING_MANAGEMENT_ID = ?";
            Map<String, Object> map = myJdbcTemplate.queryForMap(sql, csCommId);
            if (SharedUtil.isEmpty(map) && SharedUtil.isEmpty(actDate)) {
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
//                        drawingManagement.setThreeDActDate(threeDPlanDate);
                        drawingManagement.setCcDrawingStatusId("DONE");

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
//                        ccDrawingManagement.setThreeDActDate(threeDPlanDate);
                        ccDrawingManagement.setCcPartyCompanyId(ccPartyCompanyId);
                        ccDrawingManagement.updateById();
                    }
                } catch (Exception e) {
                    throw new BaseException("在第 " + (i + 1) + " 行的单元格格式错误!");
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
    public void uploadZipDrawingPlan() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccAttachment = JdbcMapUtil.getString(varMap, "P_CC_ATTACHMENTS");
        String pRemark = JdbcMapUtil.getString(varMap, "P_REMARK");
        String pActDate = JdbcMapUtil.getString(varMap, "P_ACT_DATE");

        List<String> ccAttachmentList = Arrays.asList(ccAttachment.split(","));
        for (String attachmentId : ccAttachmentList) {
            FlFile flFile = FlFile.selectById(attachmentId);
            String dspName = flFile.getDspName(); // 获取文件名
            String name = flFile.getName();
            int index = dspName.indexOf('-');

            if (index == -1) {
                throw new BaseException("压缩包命名不规范!");
            }

            String ccConstructionDrawingId = dspName.substring(0, index);
            CcDrawingManagement ccDrawingManagement = CcDrawingManagement.selectOneByWhere(new Where().eq(CcDrawingManagement.Cols.CC_CONSTRUCTION_DRAWING_ID, ccConstructionDrawingId));
            if (SharedUtil.isEmpty(ccDrawingManagement)) {
                throw new BaseException("图纸列表中未找到对应图纸套图号:" + ccConstructionDrawingId + "，请补充图纸列表后再次上传!");
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


            String zipFilePath = flFile.getOriginFilePhysicalLocation(); // zip文件物理路径
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
                                    // 获取文件属性
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

                                    // 分图文件
                                    CcDrawingUpload ccDrawingUpload = CcDrawingUpload.newData();
                                    ccDrawingUpload.setCcStructDrawingVersionId(ccStructDrawingVersion.getId());
                                    ccDrawingUpload.setCcAttachment(fileId);
                                    ccDrawingUpload.setCcDrawingVersionId(ccDrawingVersionId);
                                    ccDrawingUpload.setRemark(pRemark);
                                    ccDrawingUpload.setCcDrawingManagementId(csCommId);
                                    ccDrawingUpload.setName(fileName);
                                    ccDrawingUpload.insertById();

                                    // 若没有实际发图日期则更新套图实际发图日期
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
}
