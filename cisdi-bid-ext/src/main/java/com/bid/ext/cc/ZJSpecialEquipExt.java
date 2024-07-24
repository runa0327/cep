package com.bid.ext.cc;

import com.bid.ext.model.CcEquipIot;
import com.bid.ext.model.CcPrjMember;
import com.bid.ext.model.CcSpecialEquipPreVe;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 质量评估扩展
 */
public class ZJSpecialEquipExt {

    //导入压力容器
    public void importPressureVessel() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
//        String filePath = flFile.getPhysicalLocation();
        String filePath = "/Users/hejialun/Documents/湛江/导入/一般压力容器导入模版.xlsx";

        //施工责任人
        String  conHeadId = varMap.get("P_CON_HEAD_ID").toString();
        if (conHeadId==null){
            throw new BaseException("施工责任人不能为空");
        }
        //督办人
        String  superisorId = varMap.get("P_CON_SUPERISOR_ID").toString();
        if (superisorId==null){
            throw new BaseException("督办人不能为空");
        }
        //使用登记办理责任人
        String  regProId = varMap.get("P_REG_PRO_ID").toString();
        if (regProId==null){
            throw new BaseException("使用登记办理人不能为空");
        }

        List<String> ids = new ArrayList<>();
        ids.add(conHeadId);
        ids.add(superisorId);
        ids.add(regProId);

        List<CcPrjMember> ccPrjMembers ;

       try {
           ccPrjMembers = CcPrjMember.selectByIds(ids);
       }catch (BaseException e){
//           throw new BaseException("不能设置同一人");
       }


        try {
            FileInputStream file = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if(row.getRowNum()==0) {
                    continue;
                }

                //获取指定列的下标
                if (row.getRowNum()>0){

                    String  equipName = "";
                    //设备名称
                    Cell cell1 = row.getCell(1);
                    if (cell1==null){
                        throw new BaseException("第"+row.getRowNum()+"行，设备名称列为空");
                    }
                    equipName = getCellValueAsString(cell1);

                    String installLocation = "";
                    //安装地点
                    Cell cell2 = row.getCell(3);
                    if (cell2==null){
                        throw new BaseException("第"+row.getRowNum()+"行，安装地点列为空");
                    }
                    installLocation = getCellValueAsString(cell2);

                    //介质
                    Cell cell3 = row.getCell(4);
                    if (cell3==null){
                        throw new BaseException("第"+row.getRowNum()+"行，介质列为空");
                    }
                    String medium = getCellValueAsString(cell3);

                    //安装单位
                    Cell cell4 = row.getCell(7);
                    if (cell4==null){
                        throw new BaseException("第"+row.getRowNum()+"行，安装单位列为空");
                    }
                    String installCompany = getCellValueAsString(cell4);

                    //设备计划到货时间
                    Cell cell5 = row.getCell(8);
                    if (cell5==null){
                        throw new BaseException("第"+row.getRowNum()+"行，设备计划到货时间列为空");
                    }
                    LocalDate planArriveDate = cell5.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

                    Where queryEquip =  new Where();
                    queryEquip.sql("T.STATUS = 'AP' AND  T.`NAME` = '"+equipName+"' AND T.CC_SPECIAL_EQUIP_INS_LOCATION='"+installLocation+"'");
                    CcSpecialEquipPreVe ccSpecialEquipPreVe = CcSpecialEquipPreVe.selectOneByWhere(queryEquip);

                    if (ccSpecialEquipPreVe!=null){
                        throw  new BaseException("第"+row.getRowNum()+"行，设备已存在，可删除再导入！");
                    }

                    CcSpecialEquipPreVe ccSpecialEquipPreVe1 = CcSpecialEquipPreVe.newData();
                    ccSpecialEquipPreVe1.setName(equipName);
                    ccSpecialEquipPreVe1.setCcSpecialEquipInsLocation(installLocation);
                    ccSpecialEquipPreVe1.setCcSpecialEquipMedium(medium);
                    ccSpecialEquipPreVe1.setCcSpecialEquipInsCompany(installCompany);
                    ccSpecialEquipPreVe1.setCcSpecialEquipPlanArriveDate(planArriveDate);
                    ccSpecialEquipPreVe1.setCcSpecialEquipConHeadId(conHeadId);
                    ccSpecialEquipPreVe1.setCcSpecialEquipSupervisorId(superisorId);
                    ccSpecialEquipPreVe1.setCcSpecialEquipRegProHeadId(regProId);

                    ccSpecialEquipPreVe1.insertById();

                }
            }
        } catch (IOException e) {
            throw  new BaseException("上传文件失败");
        }

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);

    }

    private  String getCellValueAsString(Cell cell) {
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
            default:
                cellValue = "";
        }
        return cellValue;
    }

}
