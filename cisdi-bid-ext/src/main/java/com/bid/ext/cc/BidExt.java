package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static com.bid.ext.utils.ImportValueUtils.*;

public class BidExt {

    /**
     * 创建合同工程量分类
     */
    public void creatPoEngineeringType() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPo ccPo = CcPo.selectById(csCommId);
            String ccPrjId = ccPo.getCcPrjId();

            String[] engineeringType = {"PILE", "FOUNDATION", "STEELSTRUCTURE", "PIPELINE", "CABLETRAY"};
            List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.IS_TEMPLATE, 0).eq(CcPrjStructNode.Cols.IS_PBS, 1).eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).neq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null));
            List<String> structNodeIds = new ArrayList<>();
            for (CcPrjStructNode ccPrjStructNode : ccPrjStructNodes) {
                structNodeIds.add(ccPrjStructNode.getId());

            }

            for (String type : engineeringType) {
                CcPoEngineeringType ccPoEngineeringType = CcPoEngineeringType.newData();
                ccPoEngineeringType.setCcPrjId(ccPrjId);
                ccPoEngineeringType.setCcPoId(csCommId);
                ccPoEngineeringType.setCcEngineeringQuantityTypeId(type);
                ccPoEngineeringType.insertById();

                for (String structNodeId : structNodeIds) {
                    CcPoEngineering ccPoEngineering = CcPoEngineering.newData();
                    ccPoEngineering.setCcEngineeringQuantityTypeId(ccPoEngineeringType.getId());
                    ccPoEngineering.setCcPrjId(ccPrjId);
                    ccPoEngineering.setCcPoId(csCommId);
                    ccPoEngineering.setCcEngineeringQuantityTypeId(type);
                    ccPoEngineering.setCcPrjStructNodeId(structNodeId);
                    ccPoEngineering.insertById();
                }
            }
        }
    }

    /**
     * 导入合同工程量
     */
    public void importContractQuantities() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String ccPoId = entityRecord.csCommId;
            Map<String, Object> varMap = ExtJarHelper.getVarMap();
            FlFile flFile = FlFile.selectById(varMap.get("P_CC_ATTACHMENT").toString());
            String filePath = flFile.getPhysicalLocation();
            if (!"xls".equals(flFile.getExt()) && !"xlsx".equals(flFile.getExt())) {
                throw new BaseException("请上传'xls'或'xlsx'格式的Excel文件");
            }

            try (FileInputStream file = new FileInputStream(new File(filePath))) {
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet
                FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

                // 遍历所有行，从第二行开始
                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);
                    if (row == null) continue;

                    Cell cellB = row.getCell(1); // B列对应的索引是1
                    if (cellB == null || cellB.getCellType() != CellType.STRING) continue;

                    String cellValue = cellB.getStringCellValue();
                    String ccEngineeringQuantityTypeId = null;
                    switch (cellValue) {
                        case "桩":
                            ccEngineeringQuantityTypeId = "PILE";
                            break;
                        case "基础":
                            ccEngineeringQuantityTypeId = "FOUNDATION";
                            break;
                        case "钢结构":
                            ccEngineeringQuantityTypeId = "STEELSTRUCTURE";
                            break;
                        case "桥架":
                            ccEngineeringQuantityTypeId = "CABLETRAY";
                            break;
                        case "管道":
                            ccEngineeringQuantityTypeId = "PIPELINE";
                            break;
                    }

                    if (ccEngineeringQuantityTypeId == null) continue;

                    for (int colIndex = 3; colIndex <= 12; colIndex++) { // 处理D到M列
                        Cell cell = row.getCell(colIndex);
                        if (cell == null) continue;

                        // 获取相关单元格的值
                        String structCode = sheet.getRow(3).getCell(colIndex).getStringCellValue().substring(0, 6);
                        CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.CODE, structCode));
                        String ccPrjStructNodeId = ccPrjStructNode.getId();

                        String ccUomTypeIdId = row.getCell(2).getStringCellValue();
                        BigDecimal totalWeight = null;
                        if (cell.getCellType() == CellType.NUMERIC) {
                            totalWeight = BigDecimal.valueOf(cell.getNumericCellValue());
                        } else if (cell.getCellType() == CellType.FORMULA) {
                            totalWeight = BigDecimal.valueOf(evaluator.evaluate(cell).getNumberValue());
                        }

                        if (totalWeight == null) continue;

                        // 检查是否已经存在相同的记录
                        CcEngineeringQuantity existingRecord = CcEngineeringQuantity.selectOneByWhere(
                                new Where()
                                        .eq(CcEngineeringQuantity.Cols.CC_PRJ_STRUCT_NODE_ID, ccPrjStructNodeId)
                                        .eq(CcEngineeringQuantity.Cols.CC_PO_ID, ccPoId)
                                        .eq(CcEngineeringQuantity.Cols.CC_ENGINEERING_QUANTITY_TYPE_ID, ccEngineeringQuantityTypeId)
                                        .eq(CcEngineeringQuantity.Cols.CC_UOM_TYPE_ID, ccUomTypeIdId)
                        );

                        if (existingRecord != null) {
                            // 更新已有记录
                            existingRecord.setTotalWeight(totalWeight);
                            existingRecord.updateById();
                        } else {
                            // 插入新记录
                            CcEngineeringQuantity eq = CcEngineeringQuantity.newData();
                            eq.setCcPrjStructNodeId(ccPrjStructNodeId);
                            eq.setCcEngineeringTypeId("BID");
                            eq.setCcEngineeringQuantityTypeId(ccEngineeringQuantityTypeId);
                            eq.setCcUomTypeId(ccUomTypeIdId);
                            eq.setTotalWeight(totalWeight);
                            eq.setCcPoId(ccPoId);
                            eq.insertById();
                        }
                    }
                }

            } catch (IOException e) {
                throw new BaseException("上传文件失败");
            }

            InvokeActResult invokeActResult = new InvokeActResult();
            invokeActResult.reFetchData = true;
            ExtJarHelper.setReturnValue(invokeActResult);
        }
    }

    /**
     * 导入合同台账
     */
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


                CcPo ccPo = CcPo.selectOneByWhere(new Where().eq(CcPo.Cols.CODE, bidCode));
                LocalDate trxDate = getStringCellValue(row.getCell(9)) == null || getStringCellValue(row.getCell(9)).isEmpty() ? null : getLocalDateCellValue(row.getCell(9));
                LocalDate planFr = getStringCellValue(row.getCell(12)) == null || getStringCellValue(row.getCell(12)).isEmpty() ? null : getLocalDateCellValue(row.getCell(12));
                LocalDate planTo = getStringCellValue(row.getCell(13)) == null || getStringCellValue(row.getCell(13)).isEmpty() ? null : getLocalDateCellValue(row.getCell(13));
                if (SharedUtil.isEmpty(ccPo)) {

                    CcPo po = CcPo.newData();
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


}
