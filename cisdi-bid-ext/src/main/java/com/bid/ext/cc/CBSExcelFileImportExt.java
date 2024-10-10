package com.bid.ext.cc;

import com.bid.ext.model.CcPrjCostOverview;
import com.bid.ext.model.CcPrjCostOverviewSimple;
import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static com.bid.ext.cc.StructNodeExt.recalculatePlanTotalCost;
import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class CBSExcelFileImportExt {


    public void nodeAnalyzing() {

        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String structUsageId = (String) valueMap.get("CC_PRJ_STRUCT_USAGE_ID");
//        String  structUsageId = "CBS_0";

        if (structUsageId != null && structUsageId.isEmpty()) {
            throw new BaseException("未选中数据，未知数据类型");
        }
        String prjId = varMap.get("P_PRJ_ID").toString();
//        String prjId ="1790672761571196928";

        /**
         *  查询当前指定成本类型数据
         */
        Where queryNodeWhere = new Where();
        queryNodeWhere.sql("T.IS_TEMPLATE=0 AND T.IS_CBS=1 AND T.CC_PRJ_STRUCT_USAGE_ID='" + structUsageId + "' AND  T.CC_PRJ_ID=" + prjId);
        List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(queryNodeWhere);

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();

//       String filePath = "/Users/hejialun/Documents/excel-import-test.xlsx";
        if (!"xlsx".equals(flFile.getExt()))
            throw new BaseException("请上传'xlsx'格式的Excel文件");
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            int nameIndex = -1;
            int costIndex = -1;
            int remarkIdx = -1;

            //循环行
            for (Row row : sheet) {
                //获取指定列的下标
                if (row.getRowNum() == 1) {
                    for (Cell cell : row) {
                        String cellValue = getCellValueAsString(cell);
                        if ("名称".equals(cellValue)) {
                            nameIndex = cell.getColumnIndex();
                        } else if ("金额（元）".equals(cellValue)) {
                            costIndex = cell.getColumnIndex();
                        } else if ("备注".equals(cellValue)) {
                            remarkIdx = cell.getColumnIndex();
                        }
                    }
                    if (nameIndex == -1) {
                        throw new BaseException("未找到'名称'列");
                    } else if (costIndex == -1) {
                        throw new BaseException("未找到'金额（元）'列");
                    } else if (remarkIdx == -1) {
                        throw new BaseException("未找到'备注'列");
                    }
                }

                if (row.getRowNum() > 1) {
                    Cell nameCell = row.getCell(nameIndex);
                    if (nameCell.getCellType() == BLANK) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，科目名称不能为空");
                    }

                    Cell costCell = row.getCell(costIndex);
                    if (costCell.getCellType() == BLANK) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，金额不能为空");
                    }

                    try {
                        String costName = getCellValueAsString(nameCell);
                        String costValue = getCellValueAsString(costCell);

                        for (CcPrjStructNode node : ccPrjStructNodes) {

                            if (costName.equals(node.getName())) {
                                node.setPlanTotalCost(new BigDecimal(costValue));
                            }
                            node.updateById();

                            BigDecimal planTotalCost = node.getPlanTotalCost();
                            String copyFromPrjStructNodeId = node.getCopyFromPrjStructNodeId();
                            String ccPrjId = node.getCcPrjId();
                            List<CcPrjCostOverview> ccPrjCostOverviews = CcPrjCostOverview.selectByWhere(new Where().eq(CcPrjCostOverview.Cols.COPY_FROM_PRJ_STRUCT_NODE_ID, copyFromPrjStructNodeId).eq(CcPrjCostOverview.Cols.CC_PRJ_ID, ccPrjId));
                            if (!SharedUtil.isEmpty(ccPrjCostOverviews)) {
                                for (CcPrjCostOverview ccPrjCostOverview : ccPrjCostOverviews) {
                                    String ccPrjCostOverviewPid = ccPrjCostOverview.getCcPrjCostOverviewPid();
                                    switch (structUsageId + "_AMT") {
                                        case "CBS_0_AMT":
                                            ccPrjCostOverview.setCbs0Amt(planTotalCost);
                                            ccPrjCostOverview.updateById();
                                            recalculatePlanTotalCost(ccPrjCostOverviewPid, structUsageId);
                                            break;
                                        case "CBS_1_AMT":
                                            ccPrjCostOverview.setCbs1Amt(planTotalCost);
                                            ccPrjCostOverview.updateById();
                                            recalculatePlanTotalCost(ccPrjCostOverviewPid, structUsageId);
                                            break;
                                        case "CBS_2_AMT":
                                            ccPrjCostOverview.setCbs2Amt(planTotalCost);
                                            ccPrjCostOverview.updateById();
                                            recalculatePlanTotalCost(ccPrjCostOverviewPid, structUsageId);
                                            break;
                                        case "CBS_3_AMT":
                                            ccPrjCostOverview.setCbs3Amt(planTotalCost);
                                            ccPrjCostOverview.updateById();
                                            recalculatePlanTotalCost(ccPrjCostOverviewPid, structUsageId);
                                            break;
                                        case "CBS_11_AMT":
                                            ccPrjCostOverview.setCbs11Amt(planTotalCost);
                                            ccPrjCostOverview.updateById();
                                            recalculatePlanTotalCost(ccPrjCostOverviewPid, structUsageId);
                                            break;
                                        case "CBS_12_AMT":
                                            ccPrjCostOverview.setCbs12Amt(planTotalCost);
                                            ccPrjCostOverview.updateById();
                                            recalculatePlanTotalCost(ccPrjCostOverviewPid, structUsageId);
                                            break;
                                    }
                                }
                            }

                        }
                    } catch (Exception e) {
                        throw new BaseException("请确认第" + (row.getRowNum() + 1) + "行的成本科目存在且金额值合法");
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


    /**
     * 成本统览导入，简版
     */
    public void costViewNodeAnalyzing() {

//        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
//        Map<String, Object> valueMap = entityRecord.valueMap;
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

//        String  structUsageId = (String) valueMap.get("CC_PRJ_STRUCT_USAGE_ID");
//        String  structUsageId = "CBS_0";

//        if(structUsageId != null && structUsageId.isEmpty()){
//            throw new BaseException("未选中数据，未知数据类型");
//        }
        String prjId = varMap.get("P_PRJ_ID").toString();
//        String prjId ="1790672761571196928";

//        if (prjId.contains(",")) {
//            throw new BaseException("请选择一个项目进行上传");
//        }

        if (prjId != null && prjId.isEmpty()) {
            throw new BaseException("请选择指定项目");
        }


        //查询当前指定成本类型数据
        Where queryCostWhere = new Where();
        queryCostWhere.sql("T.CC_PRJ_ID=" + prjId);
        List<CcPrjCostOverviewSimple> ccPrjCostOverviewSimples = CcPrjCostOverviewSimple.selectByWhere(queryCostWhere);

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();

//       String filePath = "/Users/hejialun/Downloads/成本统览.xlsx";

//        filePath = "C:\\Users\\34451\\Downloads\\test.xlsx";

        if (!"xlsx".equals(flFile.getExt()))
            throw new BaseException("请上传'xlsx'格式的Excel文件");

        //成本科目列下标
        int cbkmIndex = -1;
        //立项匡算列下标
        int lxksIndex = -1;
        //可研估算
        int kygsIndex = -1;
        //初设概算
        int csgsIndex = -1;
        //施工预算
        int sgysIndex = -1;
        //已招标
        int yzbIndex = -1;
        //合同签订额
        int htqdeIndex = -1;
        //已完成产值
        int ywcczIndex = -1;
        //已申请
        int ysqIndex = -1;
        //已付款
        int yfkIndex = -1;

        try {
            Map<String, Integer> indexMap = ExcelUtils.analyzeExcel(filePath, flFile.getExt(), true);
            //成本科目列下标
            cbkmIndex = indexMap.getOrDefault("成本科目", -1);
            //立项匡算列下标
            lxksIndex = indexMap.getOrDefault("立项匡算", -1);
            //可研估算
            kygsIndex = indexMap.getOrDefault("可研估算", -1);
            //初设概算
            csgsIndex = indexMap.getOrDefault("初设概算", -1);
            //施工预算
            sgysIndex = indexMap.getOrDefault("施工预算", -1);
            //已招标
            yzbIndex = indexMap.getOrDefault("已招标", -1);
            //合同签订额
            htqdeIndex = indexMap.getOrDefault("合同签订额", -1);
            //已完成产值
            ywcczIndex = indexMap.getOrDefault("已完成产值", -1);
            //已申请
            ysqIndex = indexMap.getOrDefault("已申请", -1);
            //已付款
            yfkIndex = indexMap.getOrDefault("已付款", -1);
        } catch (IOException e) {
            throw new BaseException("所上传的Excel文件格式不合法");
        }

        // 是否导入成功至少一条
        boolean flg = false;

        try (FileInputStream file = new FileInputStream(filePath)) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
//                if (row.getRowNum() == 0) {
//                    continue;
//                }
//                if (row.getRowNum() == 1) {
//                    for (Cell cell : row) {
//                        String cellValue = getCellValueAsString(cell);
//
//                        //成本科目列下标
//                        if ("成本科目".equals(cellValue)) {
//                            cbkmIndex = cell.getColumnIndex();
//                        }
//                        //立项匡算列下标
//                        else if ("立项匡算".equals(cellValue)) {
//                            lxksIndex = cell.getColumnIndex();
//                        }
//                        //可研估算
//                        else if ("可研估算".equals(cellValue)) {
//                            kygsIndex = cell.getColumnIndex();
//                        }
//                        //初设概算
//                        else if ("初设概算".equals(cellValue)) {
//                            csgsIndex = cell.getColumnIndex();
//                        }
//                        //施工预算
//                        else if ("施工预算".equals(cellValue)) {
//                            sgysIndex = cell.getColumnIndex();
//                        }
//
//                        //已招标
//                        else if ("已招标".equals(cellValue)) {
//                            yzbIndex = cell.getColumnIndex();
//                        }
//
//                        //合同签订额
//                        else if ("合同签订额".equals(cellValue)) {
//                            htqdeIndex = cell.getColumnIndex();
//                        }
//
//                        //已完成产值
//                        else if ("已完成产值".equals(cellValue)) {
//                            ywcczIndex = cell.getColumnIndex();
//                        }
//
//                        //已申请
//                        else if ("已申请".equals(cellValue)) {
//                            ysqIndex = cell.getColumnIndex();
//                        }
//
//                        //已付款
//                        else if ("已付款".equals(cellValue)) {
//                            yfkIndex = cell.getColumnIndex();
//                        }
//
//                    }
//                }
                //获取指定列的下标
                if (row.getRowNum() > 1) {

                    flg = true;

                    CcPrjCostOverviewSimple ccPrjCostOverviewSimple = CcPrjCostOverviewSimple.newData();
                    ccPrjCostOverviewSimple.setCcPrjId(prjId);
                    ccPrjCostOverviewSimple.setCopyFromPrjStructNodeId(null);
                    ccPrjCostOverviewSimple.setCcPrjCostOverviewSimplePid(null);
                    ccPrjCostOverviewSimple.setSeqNo(new BigDecimal(row.getRowNum()));

                    //成本科目
                    if (cbkmIndex == -1) {
                        throw new BaseException("成本科目列不存在");
                    }
                    Cell cell3 = row.getCell(cbkmIndex);
                    if (cell3.getCellType() == BLANK) {
//                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，成本科目为空");
                        continue;//跳过行
                    }
                    ccPrjCostOverviewSimple.setName(getCellValueAsString(cell3));

//                    立项匡算
                    if (lxksIndex == -1) {
                        throw new BaseException("立项匡算列不存在");
                    }
                    Cell cell7 = row.getCell(lxksIndex);
                    ccPrjCostOverviewSimple.setCbs0Amt(getCellValueAsBigDecimal(cell7));

                    //可研估算
                    if (kygsIndex == -1) {
                        throw new BaseException("可研估算列不存在");
                    }
                    Cell cell8 = row.getCell(kygsIndex);
                    ccPrjCostOverviewSimple.setCbs1Amt(getCellValueAsBigDecimal(cell8));

                    //初设概算
                    if (csgsIndex == -1) {
                        throw new BaseException("初设概算列不存在");
                    }
                    Cell cell9 = row.getCell(csgsIndex);
                    ccPrjCostOverviewSimple.setCbs2Amt(getCellValueAsBigDecimal(cell9));

                    //施工预算
                    if (lxksIndex == -1) {
                        throw new BaseException("施工预算列不存在");
                    }
                    Cell cell10 = row.getCell(sgysIndex);
                    ccPrjCostOverviewSimple.setCbs3Amt(getCellValueAsBigDecimal(cell10));
                    //已招标
                    if (yzbIndex != -1) {
                        Cell cell11 = row.getCell(yzbIndex);
                        ccPrjCostOverviewSimple.setBidAmt(getCellValueAsBigDecimal(cell11));
                    } else {
                        ccPrjCostOverviewSimple.setBidAmt(new BigDecimal(0));
                    }

                    //合同签订额
                    if (htqdeIndex == -1) {
                        throw new BaseException("合同签订额列不存在");
                    }
                    Cell cell12 = row.getCell(htqdeIndex);
                    ccPrjCostOverviewSimple.setPurchaseAmt(getCellValueAsBigDecimal(cell12));

                    //已完成产值
                    if (ywcczIndex == -1) {
                        throw new BaseException("已完成产值列不存在");
                    }
                    Cell cell13 = row.getCell(ywcczIndex);
                    ccPrjCostOverviewSimple.setCompleteAmt(getCellValueAsBigDecimal(cell13));

                    //已申请
                    if (ysqIndex == -1) {
                        throw new BaseException("已申请列不存在");
                    }
                    Cell cell15 = row.getCell(ysqIndex);
                    ccPrjCostOverviewSimple.setReqPayAmt(getCellValueAsBigDecimal(cell15));

                    //已付款
                    if (yfkIndex == -1) {
                        throw new BaseException("已付款列不存在");
                    }
                    Cell cell17 = row.getCell(yfkIndex);
                    ccPrjCostOverviewSimple.setPayAmt(getCellValueAsBigDecimal(cell17));

                    ccPrjCostOverviewSimple.insertById();
                }
            }
        } catch (IOException e) {
            throw new BaseException("上传文件失败");
        }

        //删除原来的数据
        if (flg) {
            StringBuilder idsStr = new StringBuilder("");
            if (ccPrjCostOverviewSimples != null) {
                for (int i = 0; i < ccPrjCostOverviewSimples.size(); i++) {
                    if (i == ccPrjCostOverviewSimples.size() - 1) {
                        idsStr.append(ccPrjCostOverviewSimples.get(i).getId());
                    } else {
                        idsStr.append(ccPrjCostOverviewSimples.get(i).getId() + ",");
                    }
                }

                if (ccPrjCostOverviewSimples.size() > 0) {
                    Where where = new Where();
                    where.sql("t.id in (" + idsStr + ")");
                    CcPrjCostOverviewSimple.deleteByWhere(where);

                }
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    private BigDecimal getCellValueAsBigDecimal(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return new BigDecimal("0");
        }
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                cellValue = "0";
                break;
            default:
                cellValue = "0";
        }
        return new BigDecimal(cellValue);
    }

    private String getCellValueAsString(Cell cell) {
        String cellValue = "";
        if (cell == null) {
            return "";
        }
        switch (cell.getCellType()) {
            case STRING:
                cellValue = cell.getStringCellValue();
                break;
            case NUMERIC:
                cellValue = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                cellValue = String.valueOf(cell.getBooleanCellValue());
                break;
            case FORMULA:
                cellValue = String.valueOf(cell.getCellFormula());
                break;
            case BLANK:
                cellValue = "";
                break;
            default:
                cellValue = "";
        }
        return cellValue;
    }
}
