package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
    /**
     * 导入合同工程量
     */
    public void importContractQuantities() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String ccPoId = entityRecord.csCommId;
            Map<String, Object> varMap = ExtJarHelper.getVarMap();
            FlFile flFile = FlFile.selectById(varMap.get("P_CC_ATTACHMENT").toString());
            String filePath = flFile.getPhysicalLocation();
            if (!"xlsx".equals(flFile.getExt())) {
                throw new BaseException("请上传'xlsx'格式的Excel文件");
            }

            try (FileInputStream file = new FileInputStream(new File(filePath))) {
                Workbook workbook = new XSSFWorkbook(file);
                Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

                // 只取第5、9、13、19、21行的数据
                int[] rowsToProcess = {5, 9, 13, 19, 21};

                for (int rowIndex : rowsToProcess) {
                    Row row = sheet.getRow(rowIndex - 1); // 行索引从0开始，因此减1
                    if (row == null) continue;

                    for (int colIndex = 3; colIndex <= 12; colIndex++) { // 处理D到M列
                        Cell cell = row.getCell(colIndex);
                        if (cell == null || cell.getCellType() != CellType.NUMERIC) continue;

                        // 获取相关单元格的值
                        String structCode = sheet.getRow(3).getCell(colIndex).getStringCellValue().substring(0, 6);
                        CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectOneByWhere(new Where().eq(CcPrjStructNode.Cols.CODE, structCode));
                        String ccPrjStructNodeId = ccPrjStructNode.getId();

                        String cellValue = row.getCell(1).getStringCellValue();
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

                        String ccUomTypeIdId = row.getCell(2).getStringCellValue();
                        BigDecimal totalWeight = BigDecimal.valueOf(cell.getNumericCellValue());

                        // 检查是否已经存在相同的记录
                        CcEngineeringQuantity existingRecord = CcEngineeringQuantity.selectOneByWhere(
                                new Where()
                                        .eq(CcEngineeringQuantity.Cols.CC_PRJ_STRUCT_NODE_ID, ccPrjStructNodeId)
                                        .eq(CcEngineeringQuantity.Cols.CC_PO_ID, ccPoId)
                                        .eq(CcEngineeringQuantity.Cols.CC_ENGINEERING_QUANTITY_TYPE_ID, ccEngineeringQuantityTypeId)
                                        .eq(CcEngineeringQuantity.Cols.CC_UOM_TYPE_ID_ID, ccUomTypeIdId)
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
                            eq.setCcUomTypeIdId(ccUomTypeIdId);
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
}
