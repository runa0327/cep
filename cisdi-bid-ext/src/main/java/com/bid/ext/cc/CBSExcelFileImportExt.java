package com.bid.ext.cc;

import com.bid.ext.model.CcPrjCostOverviewSimple;
import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            int nameIndex = -1;
            int costIndex = -1;


            //循环行
            for (Row row : sheet) {
                //获取指定列的下标
                if (row.getRowNum() == 0) {
                    for (Cell cell : row) {
                        String cellValue = getCellValueAsString(cell);
                        if ("名称".equals(cellValue)) {
                            nameIndex = cell.getColumnIndex();
                        } else if ("总成本".equals(cellValue)) {
                            costIndex = cell.getColumnIndex();
                        }
                    }
                    if (nameIndex == -1) {
                        throw new BaseException("未找到'名称'列");
                    } else if (costIndex == -1) {
                        throw new BaseException("未找到'总成本'列");
                    }
                } else {

                    Cell nameCell = row.getCell(nameIndex);
                    if (nameCell.getCellType() == BLANK) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，科目名称不能为空");
                    }

                    Cell costCell = row.getCell(costIndex);
                    if (costCell.getCellType() == BLANK) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，成本不能为空");
                    }

                    String costName = getCellValueAsString(nameCell);
                    String costValue = getCellValueAsString(costCell);

                    for (CcPrjStructNode node : ccPrjStructNodes) {

                        if (costName.equals(node.getName())) {
                            node.setPlanTotalCost(new BigDecimal(costValue));
                        }
                        node.updateById();
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

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    continue;
                }

                //获取指定列的下标
                if (row.getRowNum() > 1) {

                    CcPrjCostOverviewSimple ccPrjCostOverviewSimple = CcPrjCostOverviewSimple.newData();
                    ccPrjCostOverviewSimple.setCcPrjId(prjId);
                    ccPrjCostOverviewSimple.setCopyFromPrjStructNodeId(null);
                    ccPrjCostOverviewSimple.setCcPrjCostOverviewPid(null);
                    ccPrjCostOverviewSimple.setSeqNo(new BigDecimal(row.getRowNum()));

                    //成本科目
                    Cell cell3 =  row.getCell(3);
                    if ( cell3.getCellType()==BLANK ){
                        throw   new BaseException("第"+(row.getRowNum()+1)+"行，成本科目为空");
                    }
                    ccPrjCostOverviewSimple.setName(getCellValueAsString(cell3));

//                    立项匡算
                    Cell cell7 = row.getCell(9);
                    ccPrjCostOverviewSimple.setCbs0Amt(getCellValueAsBigDecimal(cell7));

                    //可研估算
                    Cell cell8 = row.getCell(10);
                    ccPrjCostOverviewSimple.setCbs1Amt(getCellValueAsBigDecimal(cell8));

                    //初设概算
                    Cell cell9 = row.getCell(11);
                    ccPrjCostOverviewSimple.setCbs2Amt(getCellValueAsBigDecimal(cell9));
                    //施工预算
                    Cell cell10 = row.getCell(12);
                    ccPrjCostOverviewSimple.setCbs3Amt(getCellValueAsBigDecimal(cell10));
                    //已招标
                    Cell cell11 = row.getCell(13);
                    ccPrjCostOverviewSimple.setBidAmt(getCellValueAsBigDecimal(cell11));

                    //合同签订额
                    Cell cell12 = row.getCell(14);
                    ccPrjCostOverviewSimple.setPurchaseAmt(getCellValueAsBigDecimal(cell12));

                    //已完成产值
                    Cell cell13 = row.getCell(15);
                    ccPrjCostOverviewSimple.setCompleteAmt(getCellValueAsBigDecimal(cell13));

                    //已申请
                    Cell cell15 = row.getCell(17);
                    ccPrjCostOverviewSimple.setReqPayAmt(getCellValueAsBigDecimal(cell15));

                    //已付款
                    Cell cell17 = row.getCell(18);
                    ccPrjCostOverviewSimple.setPayAmt(getCellValueAsBigDecimal(cell17));

                    ccPrjCostOverviewSimple.insertById();
                }
            }
        } catch (IOException e) {
            throw new BaseException("上传文件失败");
        }

        //删除原来的数据
        List<String> ids = new ArrayList<>();
        if (ccPrjCostOverviewSimples!=null){
            for (CcPrjCostOverviewSimple cost : ccPrjCostOverviewSimples) {
                ids.add(cost.getId());
            }
        }

        CcPrjCostOverviewSimple.deleteByIds(ids);

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    private BigDecimal getCellValueAsBigDecimal(Cell cell) {
        String cellValue = "";
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
