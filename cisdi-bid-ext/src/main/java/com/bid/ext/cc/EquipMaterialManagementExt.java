package com.bid.ext.cc;

import com.bid.ext.model.CcEquipmentMaterialManagement;
import com.bid.ext.model.CcPrjCostOverviewSimple;
import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import javafx.util.converter.LocalDateStringConverter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.jni.Local;
import org.springframework.dao.EmptyResultDataAccessException;
import sun.tools.jconsole.JConsole;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class EquipMaterialManagementExt {

    private static final String[] UNIT_PRJ_CODE = {"222001", "222002", "222003", "222004", "222005", "222006", "222007", "222008", "222009", "222010"};

    /**
     * 导入设备资料数据
     */
    public void importEquipmentMaterial() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//        String filePath = flFile.getPhysicalLocation();

        String filePath = "/Users/hejialun/Documents/副本设备资料导入模板-1.xlsx";

        //

        if (!("xlsx".equals(flFile.getExt()) || "xls".equals(flFile.getExt())))
            throw new BaseException("请上传'xlsx或xls'格式的Excel文件");


        List<CcEquipmentMaterialManagement> equipmentMaterialManagements = new ArrayList<>();

        Workbook workbook =null;
        try (FileInputStream file = new FileInputStream(new File(filePath))) {
             workbook = new XSSFWorkbook(file);
        } catch (IOException e) {
            throw new BaseException("上传文件失败");
        }
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

//            String sheetName = sheet.getSheetName();
//            int numberOfSheets = workbook.getNumberOfSheets();
//            System.out.println(sheetName + "sheetName:" + numberOfSheets);

            //序号
            int xhIndex = -1;
            //WBS编码
            int wbssqIndex = -1;
            //设备编号
            int sbbhIndex = -1;
            //设计方设备位号
            int sjfsbwhIndex = -1;
            //设备名称
            int sbmcIndex = -1;
            //单位
            int dwIndex = -1;
            //数量
            int slIndex = -1;
            //资料返回计划
            int zlfhjhIndex = -1;
            //资料返回完成
            int zlfhwcIndex = -1;
            //是否主要管控设备
            int sfzygksbIndex = -1;


            //单位工程编码
            String unitPrjCode = "";
            //单位工程id
            String unitPrjId = "";


        //循环行
            for (Row row : sheet) {
                if (row.getRowNum() == 0) {
                    //获取当前的单元工程编号
                    for (Cell cell : row) {
                        if (cell.getCellType() == BLANK) {
                            continue;
                        } else {
                            for (String code : UNIT_PRJ_CODE) {
                                String cellValue = getCellValueAsString(cell).trim();
                                if (cellValue.indexOf(code) != -1) {
                                    unitPrjCode = code;
                                    continue;
                                }
                            }
                        }
                    }
                    if ("".equals(unitPrjCode)) {
                        throw new BaseException("表格第一行中未包含单元工程项目号！请检查。");
                    }

                    //单元工程查询
                    Where queryUnitPrj = new Where();
                    queryUnitPrj.sql("T.IS_TEMPLATE=0 AND T.IS_PBS=1 AND T.CC_PRJ_ID = '1790672761571196928' AND T.CC_PRJ_STRUCT_NODE_PID IS NOT NULL AND  T.CODE=" + unitPrjCode);
                    List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(queryUnitPrj);

                    for (CcPrjStructNode node : ccPrjStructNodes) {

                        if (unitPrjCode.equals(node.getCode())) {
                            unitPrjId = node.getId();
                        }
                    }

                    if ("".equals(unitPrjId)) {
                        throw new BaseException("系统未内" + unitPrjCode + "置该项目号的单元工程！");
                    }
                }

                int seqNo = 1;
                if (row.getRowNum() == 1) {
                    for (Cell cell : row) {
                        String cellValue = getCellValueAsString(cell);
                        if ("序号".equals(cellValue)) {
                            xhIndex = cell.getColumnIndex();
                        } else if ("WBS编码".equals(cellValue)) {
                            wbssqIndex = cell.getColumnIndex();
                        } else if ("设备编号".equals(cellValue)) {
                            sbbhIndex = cell.getColumnIndex();
                        } else if ("设计方设备位号".equals(cellValue)) {
                            sjfsbwhIndex = cell.getColumnIndex();
                        } else if ("设备名称".equals(cellValue)) {
                            sbmcIndex = cell.getColumnIndex();
                        } else if ("单位".equals(cellValue)) {
                            dwIndex = cell.getColumnIndex();
                        } else if ("数量".equals(cellValue)) {
                            slIndex = cell.getColumnIndex();
                        } else if ("资料返回计划".equals(cellValue)) {
                            zlfhjhIndex = cell.getColumnIndex();
                        } else if ("资料返回完成".equals(cellValue)) {
                            zlfhwcIndex = cell.getColumnIndex();
                        } else if ("是否主要管控设备".equals(cellValue)) {
                            sfzygksbIndex = cell.getColumnIndex();
                        }
                    }
                }


                //获取指定列的下标
                if (row.getRowNum() > 1) {

                    CcEquipmentMaterialManagement equipmentMaterialManagement = CcEquipmentMaterialManagement.newData();
                    equipmentMaterialManagement.setCcPrjStructNodeId(unitPrjId);

                    if (xhIndex == -1) {
                        throw new BaseException("'序号'列不存在！");
                    }
                    Cell cell3 = row.getCell(xhIndex);
                    if (cell3.getCellType() == BLANK) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，'序号'列为空！");
                    }
                    equipmentMaterialManagement.setCcEquipMaterialDataSqNo(getCellValueAsString(cell3));


                    //设计方设备位号
                    if (sjfsbwhIndex == -1) {
                        throw new BaseException("'设计方设备位号'列不存在");
                    }
                    Cell cell9 = row.getCell(sjfsbwhIndex);
                    equipmentMaterialManagement.setCcDesignPartyEquipTagNo(getCellValueAsString(cell9));

                    //单位
                    if (dwIndex == -1) {
                        throw new BaseException("'单位'列不存在");
                    }
                    Cell cell10 = row.getCell(dwIndex);
                    if (cell10.getCellType() == BLANK) {
                        equipmentMaterialManagement.setCcEquipMaterailUnit(null);
                    } else {
                        equipmentMaterialManagement.setCcEquipMaterailUnit(getCellValueAsString(cell10));
                    }


                    if (slIndex == -1) {
                        throw new BaseException("'数量'列不存在!");
                    }
                    Cell cell11 = row.getCell(slIndex);
                    if (cell11.getCellType() == BLANK) {
                        equipmentMaterialManagement.setCcEquipMaterailNum(null);
                    } else {
                        equipmentMaterialManagement.setCcEquipMaterailNum(getCellValueAsBigDecimal(cell11).intValue());
                    }

                    if (zlfhjhIndex == -1) {
                        throw new BaseException("'资料返回计划'列不存在");
                    }
                    Cell cell12 = row.getCell(zlfhjhIndex);
                    if (cell12.getCellType() != BLANK) {
                        String planBackDate = getCellValueAsString(cell12);
                        String[] split = planBackDate.split("\\.");
                        if (split.length == 1) {
                            split = planBackDate.split("-");
                        }
                        LocalDate date = LocalDate.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]), Integer.parseInt(split[2]));
                        equipmentMaterialManagement.setCcEquipMaterialPlanBackDate(date); //资料返回计划日期

                    }

//                    if (zlfhwcIndex == -1) {
//                        throw new BaseException("'资料返回完成'列不存在");
//                    }
//                    Cell cell13 = row.getCell(zlfhwcIndex);


                    if (sfzygksbIndex == -1) {
                        throw new BaseException("'是否主要管控设备'列不存在");
                    }
                    Cell cell15 = row.getCell(sfzygksbIndex);
                    String cellValueAsString = getCellValueAsString(cell15);
                    if ("是".equals(cellValueAsString)) {
                        equipmentMaterialManagement.setCcIsMainEquip(true);
                    } else if ("否".equals(cellValueAsString)) {
                        equipmentMaterialManagement.setCcIsMainEquip(false);
                    } else {
                        equipmentMaterialManagement.setCcIsMainEquip(null);
                    }

                    if (sbmcIndex == -1) {
                        throw new BaseException("'设备名称'列不存在");
                    }
                    Cell cell17 = row.getCell(sbmcIndex);
                    if (cell17.getCellType() == BLANK) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，'设备名称'列为空！");
                    }
                    equipmentMaterialManagement.setName(getCellValueAsString(cell17));

                    if (wbssqIndex == -1) {
                        throw new BaseException("'WBS编码'列不存在！");
                    }
                    Cell cell7 = row.getCell(wbssqIndex);
                    if (cell7.getCellType() == BLANK && equipmentMaterialManagement.getCcEquipMaterailUnit() != null) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，'WBS编码'列为空！");
                    }
                    equipmentMaterialManagement.setCcEquipMaterialWbsNo(getCellValueAsString(cell7));

                    if (sbbhIndex == -1) {
                        throw new BaseException("'设备编号' 列不存在");
                    }
                    Cell cell8 = row.getCell(sbbhIndex);
                    if (cell7.getCellType() == BLANK && equipmentMaterialManagement.getCcEquipMaterailUnit() != null) {
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，'设备编号'列为空！");
                    }
                    equipmentMaterialManagement.setCcEquipNo(getCellValueAsString(cell8));

                    equipmentMaterialManagement.setSeqNo(new BigDecimal(seqNo));

                    //判断是否存在相同数据
//                    Where query = new Where();
//                    query.sql("t.NAME='" + equipmentMaterialManagement.getName()+"'");
//                    CcEquipmentMaterialManagement mm = CcEquipmentMaterialManagement.selectOneByWhere(query);
//
//                    if (mm != null){
//                        mm.setVer(2);
//                        mm.setCcEquipMaterialDataSqNo(equipmentMaterialManagement.getCcEquipMaterialDataSqNo());
//                        equipmentMaterialManagements.add(mm);
//                    }else{
//                        equipmentMaterialManagements.add(equipmentMaterialManagement);
//                    }
                    equipmentMaterialManagements.add(equipmentMaterialManagement);
                }
            }

        setPid(equipmentMaterialManagements);

        for (CcEquipmentMaterialManagement materialManagement : equipmentMaterialManagements) {
            if(materialManagement.getVer()!=2){
                materialManagement.insertById();
            }
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    //设置父节点

    private void setPid(List<CcEquipmentMaterialManagement> equipmentMaterialManagements) {

        for (CcEquipmentMaterialManagement equipmentMaterialManagement : equipmentMaterialManagements) {

            String parentSqNo = equipmentMaterialManagement.getCcEquipMaterialDataSqNo();//父序号

            String pid = equipmentMaterialManagement.getId();//获取 当前节点的id

            parentSqNo = parentSqNo.replace(".0", "");//父节点等级

            String[] pSplit = parentSqNo.split("\\.");//父节点等级

            for (CcEquipmentMaterialManagement mm : equipmentMaterialManagements) {

                String childSqNo = mm.getCcEquipMaterialDataSqNo();//子节点序号

                String[] cSplit = childSqNo.replace(".0", "").split("\\.");//子节点等级

                if (cSplit.length == pSplit.length + 1) {//子级等级+1
                    boolean isChild = true;
                    for (int i = 0; i < pSplit.length; i++) {//父级对比
                        if (!pSplit[i].equals(cSplit[i])) {//等级不匹配
                            isChild = false;
                        }
                    }
                    if (isChild) {//等级匹配
                        mm.setCcEquipMaterialPid(pid);
                    }
                }
            }
        }
    }


    //资料返回完成确认
    public void completeMaterialBack() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for( EntityRecord  entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            String materialId = (String) valueMap.get("ID");
            CcEquipmentMaterialManagement equipmentMaterialManagement = null;

            try {
                equipmentMaterialManagement = CcEquipmentMaterialManagement.selectById(materialId);

            } catch (EmptyResultDataAccessException e) {
                throw new BaseException("资料记录不存在！");
            }

            if (equipmentMaterialManagement == null) {
                throw new BaseException("资料记录不存在！");
            }

            String ccEquipMaterialDataAtts = equipmentMaterialManagement.getCcEquipMaterialDataAtts();

            System.out.println(ccEquipMaterialDataAtts);

            if (ccEquipMaterialDataAtts == null) {
                throw new BaseException("当前记录返回资料为空，不可完成");
            }
            equipmentMaterialManagement.setCcEquipMaterialBackDate(LocalDate.now());

            equipmentMaterialManagement.updateById();

            InvokeActResult invokeActResult = new InvokeActResult();
            invokeActResult.reFetchData = true;
            ExtJarHelper.setReturnValue(invokeActResult);
        }

    }


    //获取excel表格列值
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
