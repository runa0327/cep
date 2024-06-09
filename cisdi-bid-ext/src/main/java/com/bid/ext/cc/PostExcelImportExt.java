package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import net.sf.mpxj.Task;
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

public class PostExcelImportExt {


    public void postImport(){

        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String   prjId = (String) valueMap.get("P_PRJ_ID");
        String   companyId = (String) valueMap.get("CC_COMPANY_ID");
        String   prjPartyId = (String) valueMap.get("CC_PRJ_PARTY_ID");
        String   companyPartyId = (String) valueMap.get("CC_COMPANY_PARTY_ID");
        String   partyId = (String) valueMap.get("CC_PARTY_ID");

        if(prjId != null && prjId.isEmpty()){
           throw new BaseException("未选中数据，未知数据类型");
        }

        /**
         *  查询系统岗位
         */
        Where queryPostWhere = new Where();
        queryPostWhere.sql("1=1");
        List<CcPost> ccPosts = CcPost.selectByWhere(queryPostWhere);

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();

//       String filePath = "/Users/hejialun/Documents/excel-import-test.xlsx";

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            int   nameIndex =  -1;
            int    sqIndex = -1;

            //循环行
            for (Row row : sheet) {
                //获取指定列的下标
                if(row.getRowNum()==0){
                    for ( Cell cell :  row){
                        String cellValue = getCellValueAsString(cell);
                        if ("序号".equals(cellValue)){
                            nameIndex = cell.getColumnIndex();
                        }else if("岗位名称".equals(cellValue)){
                            sqIndex = cell.getColumnIndex();
                        }
                    }
                    if(nameIndex==-1){
                        throw new BaseException("未找到'序号'列");
                    }else if (sqIndex==-1){
                        throw new BaseException("未找到'岗位名称'列");
                    }
                }else{

                Cell nameCell = row.getCell(nameIndex);

                Cell sqCell = row.getCell(sqIndex);
                if (sqCell.getCellType() == BLANK) {
                    throw  new BaseException("第"+(row.getRowNum()+1)+"行，'岗位名称'不能为空");
                }

                    String  postName = getCellValueAsString(nameCell);
                    String  sqValue = getCellValueAsString(sqCell);
                    String postId = "";

                    boolean exist = false;
                    for(CcPost  post: ccPosts){
                        //岗位表中是否存在，存在就创建岗位
                        if (postName.equals(post.getName())){
                            postId = post.getId();
                            exist = true;
                        }
                    }

                    if (!exist){
                        CcPost ccPost = CcPost.newData();
                        ccPost.setName("{\"EN\": \""+postName+"\", \"ZH_CN\": \""+postName+"\", \"ZH_TW\": \""+postName+"\"}");
                        ccPost.setIsDefault(false);
                        ccPost.setSeqNo(new BigDecimal(0));
                        ccPost.insertById();
                        postId = ccPost.getId();
                    }

                    //插入参建单位岗位
                    CcPartyCompanyPost ccPartyCompanyPost = CcPartyCompanyPost.newData();
                    ccPartyCompanyPost.setCcPostId(postId);
                    ccPartyCompanyPost.setCcPartyCompanyId(companyPartyId);
                    ccPartyCompanyPost.setCcPartyId(partyId);
                    ccPartyCompanyPost.setCcCompanyId(companyId);
                    ccPartyCompanyPost.setCcPrjPartyId(prjPartyId);
                    ccPartyCompanyPost.setSeqNo(new BigDecimal(sqValue));

                    ccPartyCompanyPost.insertById();
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
