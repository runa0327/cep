package com.bid.ext.cc;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.dao.EmptyResultDataAccessException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.apache.poi.ss.usermodel.CellType.BLANK;

public class ZJConstructionPlanImportExt {

    /**
     * 施工方案导入，简版
     */
    public void constructionPlanImport(){

        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        //获取上传的excel文件
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
//        String filePath = "/Users/hejialun/Documents/副本附件：施工方案计划模板.xlsx";

        if (!("xlsx".equals(flFile.getExt()) || "xls".equals(flFile.getExt())))
            throw new BaseException("请上传'xlsx或xls'格式的Excel文件");


        /**
         *  获取单元工程
         */
        Where queryUnitPrj = new Where();
        queryUnitPrj.sql("T.IS_TEMPLATE=0 AND T.IS_PBS=1 AND T.CC_PRJ_ID = '1790672761571196928' AND T.CC_PRJ_STRUCT_NODE_PID IS NOT NULL");
        List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(queryUnitPrj);

        /**
         *  参建公司
         */
        Where queryPartCompanyWhere = new Where();
        queryPartCompanyWhere.sql("1=1");
        List<CcPartyCompany> ccPartyCompanies = CcPartyCompany.selectByWhere(queryPartCompanyWhere);

        /**
         *  所有公司
         */
        Where queryCompanyWhere = new Where();
        queryCompanyWhere.sql("1=1");
        List<CcCompany> ccCompanies = CcCompany.selectByWhere(queryCompanyWhere);

        /**
         *  人员查询
         */

        Where queryPrjUserWhere = new Where();
        queryPrjUserWhere.sql("T.STATUS = 'AP' ");
        List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByWhere(queryPrjUserWhere);

        try (FileInputStream file = new FileInputStream(new File(filePath))) {
            Workbook workbook = new XSSFWorkbook(file);
            Sheet sheet = workbook.getSheetAt(0); // 获取第一个Sheet

            //循环行
            for (Row row : sheet) {
                if(row.getRowNum()==0) {
                    continue;
                }

                //获取指定列的下标
                if (row.getRowNum()>0){

                    CcConstructionplan  constructionPlan = CcConstructionplan.newData();

                    String  companyName = "";//责任单位
                    String   unitPrjName = ""; //单元工程名称
                    String  userName = ""; //责任人
                    String planName = "";//计划名称

                    //单位工程
                    Cell cell1 = row.getCell(0);
                    if (cell1.getCellType() == BLANK) {
                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'单元工程' 不能为空");
                    }
                    unitPrjName = getCellValueAsString(cell1);


                    //方案名称
                    Cell cell2 = row.getCell(1);
                    if (cell2.getCellType() == BLANK) {
                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'方案名称'不能为空");
                    }
                    planName = getCellValueAsString(cell2);

                    Where qPlan = new Where();
                    qPlan.sql("t.plan_name='"+planName+"'");
                    List<CcConstructionplan> ccConstructionplans = CcConstructionplan.selectByWhere(qPlan);

                    if (ccConstructionplans!=null && ccConstructionplans.size()>0){
                        throw new BaseException("第"+(row.getRowNum()+1)+"行，'方案名称'为'"+planName+"'已存在");
                    }


                    Cell cell3 = row.getCell(2);
                    if (cell3.getCellType() == BLANK) {
                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'责任单位'不能为空");
                    }
                    companyName = getCellValueAsString(cell3);


                    //计划时间
                    Cell cell4= row.getCell(3);
                    if (cell4.getCellType() == BLANK) {
                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'时间'不能为空");
                    }

                    LocalDate frDate = cell4.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    constructionPlan.setComplPlanDate(frDate);

                    //预警天数
                    Cell cell5 = row.getCell(4);
                    if (cell5.getCellType() == BLANK) {
                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'提前预警天数'不能为空");
                    }
                    constructionPlan.setAdvanceWarningDays(getCellValueAsString(cell5 ));


                    //责任人
                    Cell cell6 = row.getCell(5);
                    if (cell6.getCellType() == BLANK) {
                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'责任人'不能为空");
                    }
                      userName = getCellValueAsString(cell6 );

                    //是否专家论证
                    Cell cell7 = row.getCell(6);
                    if (cell7.getCellType() == BLANK) {
                        throw  new BaseException("第"+(row.getRowNum()+1)+"行，'是否专家论证'不能为空");
                    }
                    if("是".equals(getCellValueAsString(cell7))){
                        constructionPlan.setIsExpertValidation(true);
                    }else{
                        constructionPlan.setIsExpertValidation(false);
                    }

                    //备注
                    Cell cell8 = row.getCell(7);
                    constructionPlan.setRemark(getCellValueAsString(cell8));

                    //公司id
                    String companyId = "";
                    boolean  companyExist = false;
                    for (CcCompany company : ccCompanies){
                        String nameJson = company.getName();
                        JSONObject entries = JSONUtil.parseObj(nameJson);
                        String zh_cn = entries.getStr("ZH_CN");
                        if (companyName.equals(zh_cn)){
                            companyId = company.getId();
                            companyExist = true;
                        }
                    }
                    if(!companyExist)
                        throw new BaseException("请检"+row.getRowNum()+"行'查责任单位'名称是否正确！");

                    //公司id
                    String partCompanyId = "";
                    boolean  partCompanyExist = false;
                    for (CcPartyCompany partyCompany : ccPartyCompanies){

                        if (partyCompany.getCcCompanyId().equals(companyId)){
                            partCompanyId = partyCompany.getId();
                            partCompanyExist = true;
                        }
                    }
                    if(!partCompanyExist)
                        throw new BaseException("请检"+row.getRowNum()+"行'查责任单位'是否已配置，名称是否正确！");


                    String unitPrjId = "";//单元工程id
                    boolean  unitPrjIdExist = false;
                    for (CcPrjStructNode node : ccPrjStructNodes) {
                        if (unitPrjName.equals(node.getName())) {
                            unitPrjId = node.getId();
                            unitPrjIdExist = true;
                        }
                    }
                    if(!unitPrjIdExist)
                        throw new BaseException("请检"+row.getRowNum()+"行'单元工程'是否已配置，名称是否正确！");


                    //人员id
                    String adUserId = "";
                    boolean  userExist = false;
                    for (CcPrjMember member : ccPrjMembers){
                        String nameJson = member.getName();

                        JSONObject entries = JSONUtil.parseObj(nameJson);
                        String zh_cn = entries.getStr("ZH_CN");
                        if (userName.equals(zh_cn) &&  partCompanyId.equals(member.getCcPartyCompanyId())){
                            adUserId = member.getAdUserId();
                            userExist = true;
                        }
                    }

                    if (!userExist)
                        throw new BaseException("请检"+row.getRowNum()+"行'责任人'名称是否正确！");


//                    constructionPlan.setName(planName);

                    constructionPlan.setPlanName(planName);
                    constructionPlan.setCcCompanyId(companyId);
                    constructionPlan.setAdUserId(adUserId);
                    constructionPlan.setIsStart(false);
                    constructionPlan.setIsComplete(false);
                    constructionPlan.setCcPrjStructNodeId(unitPrjId);

                    constructionPlan.insertById();
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


    //计划确认完成
    public void completePlan() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for( EntityRecord  entityRecord : entityRecordList) {

            Map<String, Object> valueMap = entityRecord.valueMap;

//        Map<String, Object> varMap = ExtJarHelper.getVarMap();

            String planId = (String) valueMap.get("ID");


            CcConstructionplan constructionplan = null;

            try {
                constructionplan = CcConstructionplan.selectById(planId);

            } catch (EmptyResultDataAccessException e) {
                throw new BaseException("计划不存在！");
            }

            if (constructionplan == null) {
                throw new BaseException("计划不存在！");
            }

            String attachments = constructionplan.getCcAttachments();

            if (attachments == null) {
                throw new BaseException("所选记录返回资料为空，不可完成");
            }
            constructionplan.setIsComplete(true);

            constructionplan.updateById();

            InvokeActResult invokeActResult = new InvokeActResult();
            invokeActResult.reFetchData = true;
            ExtJarHelper.setReturnValue(invokeActResult);
        }

    }

}
