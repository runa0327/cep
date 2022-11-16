package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.PmPrjInvest1Model;
import com.cisdi.pms.job.excel.model.PmPrjInvest2Model;
import com.cisdi.pms.job.excel.model.PmPrjModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目基础信息导入
 */
@RestController
@RequestMapping(value = "/import/baseMessage")
public class BaseProjectMessageImport {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    
    @PostMapping(value = "/importPrj")
    public Map<String,Object> importPrj(MultipartFile file){
        Map<String, Object> map = new HashMap<>();
        File excelFile = EasyExcelUtil.parseFile(file);
        if(excelFile == null || !excelFile.exists()){
            throw new RuntimeException("要解析的Excel文件不存在!");
        }
        String fileName = excelFile.getName();
        String prefix = fileName.substring(fileName.indexOf(".")+1, fileName.length());
        if (!"xlsx".equals(prefix) && !"xls".equals(prefix)){
            throw new RuntimeException("要解析的文件格式不正确");
        }

        String msg = importExcel(excelFile);
        map.put("success",msg);
        return map;
    }

    private String importExcel(File excelFile) {
        //查询项目信息
        String sql1 = "select id,name from pm_prj where status = 'ap' and name is not null order by name asc";
        List<Map<String,Object>> projectList = jdbcTemplate.queryForList(sql1);
        if (CollectionUtils.isEmpty(projectList)){
            throw new BaseException("项目库信息不能为空");
        }
        //查询业主单位
        String sql2 = "select * from pm_party where name in ('三亚崖州湾科技城开发建设有限公司','三亚崖州湾科技城投资控股有限公司','海南城发实业集团有限公司','海南城发建设工程有限公司','三亚市创意新城开发建设有限公司')";
        List<Map<String,Object>> partyList = jdbcTemplate.queryForList(sql2);

        //查询项目管理模式
        String sql3 = "select * from gr_set_value where GR_SET_ID = '99799190825080621'";
        List<Map<String,Object>> prjManList = jdbcTemplate.queryForList(sql3);

        //建设地点
        String sql4 = "select * from gr_set_value where GR_SET_ID = '99799190825080637'";
        List<Map<String,Object>> localList = jdbcTemplate.queryForList(sql4);

        //项目类型
        String sql5 = "select * from gr_set_value where GR_SET_ID = '99799190825080620'";
        List<Map<String,Object>> prjTypeList = jdbcTemplate.queryForList(sql5);

        //建设规模类型
        String sql6 = "select * from gr_set_value where GR_SET_ID = '99799190825087113'";
        List<Map<String,Object>> buildTypeList = jdbcTemplate.queryForList(sql6);

        //建设规模单位
        String sql7 = "select * from gr_set_value where GR_SET_ID = '99799190825087115'";
        List<Map<String,Object>> buildUnitList = jdbcTemplate.queryForList(sql7);

        //投资来源
        String sql8 = "select * from gr_set_value where GR_SET_ID = '99799190825080625'";
        List<Map<String,Object>> sourceList = jdbcTemplate.queryForList(sql8);

        //项目状态
        String sql9 = "select * from gr_set_value where GR_SET_ID = '99799190825080626'";
        List<Map<String,Object>> prjStatusList = jdbcTemplate.queryForList(sql9);

        String msg = "";
        StringBuffer errBuffer = new StringBuffer();
        try {

            Workbook workbook = WorkbookFactory.create(new FileInputStream(excelFile));
            Sheet hssfSheet = workbook.getSheetAt(0);
            int rowStart = hssfSheet.getFirstRowNum();//起始行数
            int rowEnd = hssfSheet.getLastRowNum();//总行数

            List<PmPrjModel> prjList = new ArrayList<>();
            List<PmPrjInvest1Model> keYanList = new ArrayList<>();
            List<PmPrjInvest2Model> chuGaiList = new ArrayList<>();
            int num = 0, num2 = 0, num3 = 0, num4 = 0;
            for (int i = 1; i <= rowEnd; i++) {
                Row row = hssfSheet.getRow(i);
                PmPrjModel pmPrjModel = new PmPrjModel();
                PmPrjInvest1Model pmPrjInvest1Model = new PmPrjInvest1Model();
                PmPrjInvest2Model pmPrjInvest2Model = new PmPrjInvest2Model();

                //项目名称
                String prjId = "";
                String projectName = EasyExcelUtil.getCellValueAsString(row.getCell(1)).trim();
                int num1 = 0;
                for (Map<String, Object> tmp : projectList) {
                    String name = JdbcMapUtil.getString(tmp,"name");
                    if (projectName.equals(name)){
                        prjId = JdbcMapUtil.getString(tmp,"id");
                        pmPrjModel.setId(prjId);
                        pmPrjModel.setNAME(name);
                        break;
                    } else {
                        num1++;
                    }
                }
                if (num1 == projectList.size()){
                    errBuffer = errBuffer.append("新增项目： "+projectName).append(" ; ");
                }
                if (SharedUtil.isEmptyString(prjId)){
                    prjId = Util.insertData(jdbcTemplate,"pm_prj");
                    pmPrjModel.setNAME(projectName);
                    pmPrjModel.setId(prjId);
                    num4++;
                }

                //项目状态
                String prjStatus = EasyExcelUtil.getCellValueAsString(row.getCell(2));
                if (!SharedUtil.isEmptyString(prjStatus)){
                    for (Map<String, Object> tmp : prjStatusList) {
                        if (prjStatus.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setPROJECT_PHASE_ID(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //业主单位
                String unitName = EasyExcelUtil.getCellValueAsString(row.getCell(3));
                if (!SharedUtil.isEmptyString(unitName)){
                    for (Map<String, Object> tmp : partyList) {
                        if (unitName.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setCUSTOMER_UNIT(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //项目管理模式
                String prjMan = EasyExcelUtil.getCellValueAsString(row.getCell(4));
                if (!SharedUtil.isEmptyString(unitName)){
                    for (Map<String, Object> tmp : prjManList) {
                        if (prjMan.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setPRJ_MANAGE_MODE_ID(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //建设地点
                String local = EasyExcelUtil.getCellValueAsString(row.getCell(5));
                if (!SharedUtil.isEmptyString(unitName)){
                    for (Map<String, Object> tmp : localList) {
                        if (local.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setBASE_LOCATION_ID(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //占地面积
                String FLOOR_AREA = EasyExcelUtil.getCellValueAsString(row.getCell(6));
                if (!SharedUtil.isEmptyString(FLOOR_AREA)){
                    pmPrjModel.setFLOOR_AREA(FLOOR_AREA);
                }

                //项目类型
                String prjType = EasyExcelUtil.getCellValueAsString(row.getCell(7));
                if (!SharedUtil.isEmptyString(prjType)){
                    for (Map<String, Object> tmp : prjTypeList) {
                        if (prjType.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setPROJECT_TYPE_ID(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //建设规模类型
                String build = EasyExcelUtil.getCellValueAsString(row.getCell(8));
                if (!SharedUtil.isEmptyString(build)){
                    for (Map<String, Object> tmp : buildTypeList) {
                        if (build.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setCON_SCALE_TYPE_ID(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //长
                String CON_SCALE_QTY = EasyExcelUtil.getCellValueAsString(row.getCell(9));
                if (!SharedUtil.isEmptyString(CON_SCALE_QTY)){
                    pmPrjModel.setCON_SCALE_QTY(CON_SCALE_QTY);
                }

                //宽
                String CON_SCALE_QTY2 = EasyExcelUtil.getCellValueAsString(row.getCell(10));
                if (!SharedUtil.isEmptyString(CON_SCALE_QTY2)){
                    pmPrjModel.setCON_SCALE_QTY2(CON_SCALE_QTY2);
                }

                //建筑面积
                String QTY_ONE = EasyExcelUtil.getCellValueAsString(row.getCell(11));
                if (!SharedUtil.isEmptyString(QTY_ONE)){
                    pmPrjModel.setQTY_ONE(QTY_ONE);
                }

                //海域面积
                String QTY_THREE = EasyExcelUtil.getCellValueAsString(row.getCell(12));
                if (!SharedUtil.isEmptyString(QTY_THREE)){
                    pmPrjModel.setQTY_THREE(QTY_THREE);
                }

                //其他
                String QTY_TWO = EasyExcelUtil.getCellValueAsString(row.getCell(13));
                if (!SharedUtil.isEmptyString(QTY_TWO)){
                    pmPrjModel.setQTY_TWO(QTY_TWO);
                }

                //建设规模单位
                String buildUnit = EasyExcelUtil.getCellValueAsString(row.getCell(14));
                if (!SharedUtil.isEmptyString(buildUnit)){
                    for (Map<String, Object> tmp : buildUnitList) {
                        if (buildUnit.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setCON_SCALE_UOM_ID(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //建设年限
                String BUILD_YEARS = EasyExcelUtil.getCellValueAsString(row.getCell(15));
                if (!SharedUtil.isEmptyString(BUILD_YEARS)){
                    pmPrjModel.setBUILD_YEARS(BUILD_YEARS);
                }

                //项目介绍
                String PRJ_SITUATION = EasyExcelUtil.getCellValueAsString(row.getCell(16));
                if (!SharedUtil.isEmptyString(PRJ_SITUATION)){
                    pmPrjModel.setPRJ_SITUATION(PRJ_SITUATION);
                }

                //项目代码
                String PRJ_CODE = EasyExcelUtil.getCellValueAsString(row.getCell(17));
                if (!SharedUtil.isEmptyString(PRJ_CODE)){
                    pmPrjModel.setPRJ_CODE(PRJ_CODE);
                }

                //资金来源
                String source = EasyExcelUtil.getCellValueAsString(row.getCell(20));
                if (!SharedUtil.isEmptyString(source)){
                    for (Map<String, Object> tmp : sourceList) {
                        if (source.equals(JdbcMapUtil.getString(tmp,"name"))){
                            pmPrjModel.setINVESTMENT_SOURCE_ID(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                prjList.add(pmPrjModel);

                //批复类型
                String replyType = EasyExcelUtil.getCellValueAsString(row.getCell(18));
                if (!SharedUtil.isEmptyString(replyType)){
                    //批复文号
                    String REPLY_NO_WR = EasyExcelUtil.getCellValueAsString(row.getCell(19));
                    //总投资
                    String PRJ_TOTAL_INVEST = EasyExcelUtil.getCellValueAsString(row.getCell(21));
                    //工程费用
                    String PROJECT_AMT = EasyExcelUtil.getCellValueAsString(row.getCell(22));
                    //建安工程费
                    String CONSTRUCT_AMT = EasyExcelUtil.getCellValueAsString(row.getCell(23));
                    //设备费
                    String EQUIP_AMT = EasyExcelUtil.getCellValueAsString(row.getCell(24));
                    //工程其他费用
                    String PROJECT_OTHER_AMT = EasyExcelUtil.getCellValueAsString(row.getCell(25));
                    //土地拆迁费
                    String LAND_AMT = EasyExcelUtil.getCellValueAsString(row.getCell(26));
                    //预备费
                    String PREPARE_AMT = EasyExcelUtil.getCellValueAsString(row.getCell(27));
                    //建设期利息
                    String CONSTRUCT_PERIOD_INTEREST = EasyExcelUtil.getCellValueAsString(row.getCell(28));

                    BigDecimal amt1 = getAmt(PRJ_TOTAL_INVEST);//总投资
                    BigDecimal amt2 = getAmt(PROJECT_AMT);//工程费用
                    BigDecimal amt3 = getAmt(CONSTRUCT_AMT);//建安工程费
                    BigDecimal amt4 = getAmt(EQUIP_AMT);//设备费
                    BigDecimal amt5 = getAmt(PROJECT_OTHER_AMT);//工程其他费用
                    BigDecimal amt6 = getAmt(LAND_AMT);//土地拆迁费
                    BigDecimal amt7 = getAmt(PREPARE_AMT);//预备费
                    BigDecimal amt8 = getAmt(CONSTRUCT_PERIOD_INTEREST);//建设期利息
                    if ("可研".equals(replyType)){
                        pmPrjInvest1Model.setPM_PRJ_ID(prjId);
                        if (!SharedUtil.isEmptyString(REPLY_NO_WR)){
                            pmPrjInvest1Model.setREPLY_NO_WR(REPLY_NO_WR);
                        }
                        pmPrjInvest1Model.setPRJ_TOTAL_INVEST(amt1);
                        pmPrjInvest1Model.setPROJECT_AMT(amt2);
                        pmPrjInvest1Model.setCONSTRUCT_AMT(amt3);
                        pmPrjInvest1Model.setEQUIP_AMT(amt4);
                        pmPrjInvest1Model.setPROJECT_OTHER_AMT(amt5);
                        pmPrjInvest1Model.setLAND_AMT(amt6);
                        pmPrjInvest1Model.setPREPARE_AMT(amt7);
                        pmPrjInvest1Model.setCONSTRUCT_PERIOD_INTEREST(amt8);
                        keYanList.add(pmPrjInvest1Model);
                    } else if ("初概".equals(replyType)){
                        pmPrjInvest2Model.setPM_PRJ_ID(prjId);
                        if (!SharedUtil.isEmptyString(REPLY_NO_WR)){
                            pmPrjInvest2Model.setREPLY_NO_WR(REPLY_NO_WR);
                        }
                        pmPrjInvest2Model.setPRJ_TOTAL_INVEST(amt1);
                        pmPrjInvest2Model.setPROJECT_AMT(amt2);
                        pmPrjInvest2Model.setCONSTRUCT_AMT(amt3);
                        pmPrjInvest2Model.setEQUIP_AMT(amt4);
                        pmPrjInvest2Model.setPROJECT_OTHER_AMT(amt5);
                        pmPrjInvest2Model.setLAND_AMT(amt6);
                        pmPrjInvest2Model.setPREPARE_AMT(amt7);
                        pmPrjInvest2Model.setCONSTRUCT_PERIOD_INTEREST(amt8);
                        chuGaiList.add(pmPrjInvest2Model);
                    }
                }

            }

            if (!CollectionUtils.isEmpty(prjList)){
                for (PmPrjModel tmp : prjList) {
                    String updateSql1 = "update pm_prj set LAST_MODI_DT = now(),VER = '99',NAME = ?,CUSTOMER_UNIT=?,PRJ_MANAGE_MODE_ID=?,BASE_LOCATION_ID=?,FLOOR_AREA=?,PROJECT_TYPE_ID=?," +
                            "CON_SCALE_TYPE_ID=?,CON_SCALE_QTY=?,QTY_ONE=?,QTY_TWO=?,QTY_THREE=?,CON_SCALE_QTY2=?,CON_SCALE_UOM_ID=?,BUILD_YEARS=?,PRJ_SITUATION=?,INVESTMENT_SOURCE_ID=?," +
                            "PROJECT_PHASE_ID=?,PRJ_CODE=?,PROJECT_SOURCE_TYPE_ID='99952822476441374' where id = ?";
                    int update1 = jdbcTemplate.update(updateSql1,tmp.getNAME(),tmp.getCUSTOMER_UNIT(),tmp.getPRJ_MANAGE_MODE_ID(),tmp.getBASE_LOCATION_ID(),tmp.getFLOOR_AREA(),tmp.getPROJECT_TYPE_ID(),
                            tmp.getCON_SCALE_TYPE_ID(),tmp.getCON_SCALE_QTY(),tmp.getQTY_ONE(),tmp.getQTY_TWO(),tmp.getQTY_THREE(),tmp.getCON_SCALE_QTY2(),tmp.getCON_SCALE_UOM_ID(),tmp.getBUILD_YEARS(),
                            tmp.getPRJ_SITUATION(),tmp.getINVESTMENT_SOURCE_ID(),tmp.getPROJECT_PHASE_ID(),tmp.getPRJ_CODE(),tmp.getId());
                    num = num + update1;
                }
            }
            if (!CollectionUtils.isEmpty(keYanList)){
                for (PmPrjInvest1Model tmp : keYanList) {
                    String insertSql = "insert into PM_PRJ_INVEST1 (id,CRT_DT,ver,status,PM_PRJ_ID,REPLY_NO_WR,PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,EQUIP_AMT,PROJECT_OTHER_AMT,LAND_AMT,PREPARE_AMT," +
                            "CONSTRUCT_PERIOD_INTEREST) values ((select uuid_short()),now(),'99','AP',?,?,?,?,?,?,?,?,?,?)";
                    int update1 = jdbcTemplate.update(insertSql,tmp.getPM_PRJ_ID(),tmp.getREPLY_NO_WR(),tmp.getPRJ_TOTAL_INVEST(),tmp.getPROJECT_AMT(),tmp.getCONSTRUCT_AMT(),tmp.getEQUIP_AMT(),
                            tmp.getPROJECT_OTHER_AMT(),tmp.getLAND_AMT(),tmp.getPREPARE_AMT(),tmp.getCONSTRUCT_PERIOD_INTEREST());
                    num2 = num2 + update1;
                }
            }
            if (!CollectionUtils.isEmpty(chuGaiList)){
                for (PmPrjInvest2Model tmp : chuGaiList) {
                    String insertSql = "insert into PM_PRJ_INVEST2 (id,CRT_DT,ver,status,PM_PRJ_ID,REPLY_NO_WR,PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,EQUIP_AMT,PROJECT_OTHER_AMT,LAND_AMT,PREPARE_AMT," +
                            "CONSTRUCT_PERIOD_INTEREST) values ((select uuid_short()),now(),'99','AP',?,?,?,?,?,?,?,?,?,?)";
                    int update1 = jdbcTemplate.update(insertSql,tmp.getPM_PRJ_ID(),tmp.getREPLY_NO_WR(),tmp.getPRJ_TOTAL_INVEST(),tmp.getPROJECT_AMT(),tmp.getCONSTRUCT_AMT(),tmp.getEQUIP_AMT(),
                            tmp.getPROJECT_OTHER_AMT(),tmp.getLAND_AMT(),tmp.getPREPARE_AMT(),tmp.getCONSTRUCT_PERIOD_INTEREST());
                    num3 = num3 + update1;
                }
            }
            msg = "成功执行。项目表更新： " + num + " 条数据，可研表更新： " + num2 + " 条数据，初概表更新： " + num3 + " 条数据,新增项目： " + num4 + " 条数据";
            errBuffer.append("\n").append(msg);
        } catch (Exception e){
            e.printStackTrace();
        }
        return errBuffer.toString();
    }

    // 金额万元转元
    private BigDecimal getAmt(String str) {
        BigDecimal amt = new BigDecimal(0);
        BigDecimal wan = new BigDecimal(10000);
        if (!SharedUtil.isEmptyString(str)){
            str = str.replace(" ","");
            amt = new BigDecimal(str).multiply(wan);
        }
        return amt;
    }
}
