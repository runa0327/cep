package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
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
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class CBSExcelFileImportExt {


    public void nodeAnalyzing(){

        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String  structUsageId = (String) valueMap.get("CC_PRJ_STRUCT_USAGE_ID");

        if(structUsageId != null && structUsageId.isEmpty()){
           throw new BaseException("未选中数据，未知数据类型");
        }
        String prjId = varMap.get("P_PRJ_ID").toString();

        //查询当前指定成本类型数据
        Where queryNodeWhere = new Where();
        queryNodeWhere.sql("T.IS_TEMPLATE=0 AND T.IS_CBS=1 AND T.CC_PRJ_STRUCT_USAGE_ID='"+structUsageId+"' AND  CC_PRJ_ID="+prjId);
        List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(queryNodeWhere);


        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();

        filePath = "C:\\Users\\xx\\Documents\\办公文档\\cbs-import-test.xlsx";

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            int   nameIndex =  -1;
            int    costIndex = -1;
            //循环行
            for (Row row : sheet) {
                //获取指定列的下标
                if(row.getRowNum()==0){
                    for ( Cell cell :  row){
                        String cellValue = getCellValueAsString(cell);
                        if ("名称".equals(cellValue)){
                            nameIndex = cell.getColumnIndex();
                        }else if("总成本".equals(cellValue)){
                            costIndex = cell.getColumnIndex();
                        }
                    }
                }else{

                Cell nameCell = row.getCell(nameIndex);
                if (nameCell.getCellType() == BLANK) {
                    throw  new BaseException("第"+(nameIndex+1)+"行，科目名称不能为空");
                }

                Cell costCell = row.getCell(costIndex);
                if (costCell.getCellType() == BLANK) {
                    throw  new BaseException("第"+(costIndex+1)+"行，成本不能为空");
                }

                    String  costName = getCellValueAsString(nameCell);
                    String  costValue = getCellValueAsString(costCell);

                    System.out.println(costName+":"+costValue);
            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //插入数据
    private   void   insertNode(Task task,String parentId,String typeId,BigDecimal serialNum,String  projectId,String wbsChiefUserId ,Integer level ){
        level++;

        //判断是否为顶层叶子节点
        if (task.getResourceAssignments().size() == 0){

            List<Task> childTasks = task.getChildTasks();


            for (int i = 0; i < childTasks.size() ; i++) {

                Task t = childTasks.get(i);

                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.newData();
                ccPrjStructNode.setStatus("DR");
                ccPrjStructNode.setCcPrjId(projectId);
                ccPrjStructNode.setCcPrjStructNodePid(parentId);
                ccPrjStructNode.setName(t.getName());
                ccPrjStructNode.setSeqNo(serialNum);
                serialNum = serialNum.add(new BigDecimal(1));
                ccPrjStructNode.setCcPrjWbsTypeId(typeId);
                ccPrjStructNode.setWbsChiefUserId(wbsChiefUserId);
                ccPrjStructNode.setPlanFr(t.getStart().toLocalDate());
                ccPrjStructNode.setPlanTo(t.getFinish().toLocalDate());
                ccPrjStructNode.setIsTemplate(false);
                ccPrjStructNode.setIsWbs(true);


                insertNode(t,ccPrjStructNode.getId(),typeId,new BigDecimal(0),projectId,wbsChiefUserId,level);

            }

        }

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
