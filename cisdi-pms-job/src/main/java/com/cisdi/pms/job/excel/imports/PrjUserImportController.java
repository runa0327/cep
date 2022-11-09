package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.BasePrjPartyUser;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.StringUtils;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 项目人员信息导入
 */
@RestController
@RequestMapping(value = "/prjImport")
public class PrjUserImportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping(value = "/importPrjUser")
    public void importPrjUser(MultipartFile file){
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
    }

    private String importExcel(File excelFile) {

        //查询项目信息
        String sql1 = "select id,name from pm_prj where status = 'ap' order by name asc";
        List<Map<String,Object>> projectList = jdbcTemplate.queryForList(sql1);
        if (CollectionUtils.isEmpty(projectList)){
            throw new BaseException("项目库信息不能为空");
        }

        //查询人员信息
        String sql2 = "select id,code from ad_user where status = 'ap'";
        List<Map<String,Object>> userList = jdbcTemplate.queryForList(sql2);
        if (CollectionUtils.isEmpty(userList)){
            throw new BaseException("人员信息不能为空！");
        }

        String msg = "";
        StringBuffer errBuffer = new StringBuffer();
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(excelFile));
            Sheet hssfSheet = workbook.getSheetAt(0);
            int rowStart = hssfSheet.getFirstRowNum();//起始行数
            int rowEnd = hssfSheet.getLastRowNum();//总行数

            List<BasePrjPartyUser> importList = new ArrayList<>();
            for (int i = 0; i < rowStart; i++) {
                Row row = hssfSheet.getRow(i);

                BasePrjPartyUser basePrjPartyUser = new BasePrjPartyUser();

                //项目名称
                String projectName = EasyExcelUtil.getCellValueAsString(row.getCell(1));
                int num1 = 0;
                for (Map<String, Object> tmp : projectList) {
                    String name = JdbcMapUtil.getString(tmp,"name");
                    if (projectName.equals(name)){
                        basePrjPartyUser.setPmPrjId(JdbcMapUtil.getString(tmp,"id"));
                    } else {
                        num1++;
                    }
                }
                if (num1 == projectList.size()){
                    errBuffer = errBuffer.append("没有找到 ‘"+projectName+"' 项目的id");
                }

                //施工单位联系人
                int num2 = 0;
                String buildUser1 = EasyExcelUtil.getCellValueAsString(row.getCell(7));
                StringBuffer userSB = new StringBuffer();
                List<String> user = StringUtils.splitByCode(buildUser1,",");
                for (String s : user) {
                    for (Map<String, Object> tmp : userList) {
                        String name = JdbcMapUtil.getString(tmp,"code");
                        if (s.equals(name)){
                            userSB = userSB.append(JdbcMapUtil.getString(tmp,"id")).append(",");
                        } else {
                            num2++;
                        }
                    }
                    if (num2 == userList.size()){
                        errBuffer = errBuffer.append("没有找到 ’"+s+"' 的人员");
                    }
                }
                basePrjPartyUser.setPmPartyRoleId("99952822476391092");
                basePrjPartyUser.setUserId(userSB.substring(0,userSB.length()-1));
                importList.add(basePrjPartyUser);

                //监理单位联系人
                int num3 = 0;
                String buildUser2 = EasyExcelUtil.getCellValueAsString(row.getCell(10));
                StringBuffer userSB2 = new StringBuffer();
                List<String> user2 = StringUtils.splitByCode(buildUser2,",");
                for (String s : user2) {
                    for (Map<String, Object> tmp : userList) {
                        String name = JdbcMapUtil.getString(tmp,"code");
                        if (s.equals(name)){
                            userSB = userSB.append(JdbcMapUtil.getString(tmp,"id")).append(",");
                        } else {
                            num2++;
                        }
                    }
                    if (num2 == userList.size()){
                        errBuffer = errBuffer.append("没有找到 ’"+s+"' 的人员");
                    }
                }
                basePrjPartyUser.setPmPartyRoleId("99952822476391096");
                basePrjPartyUser.setUserId(userSB2.substring(0,userSB2.length()-1));
                importList.add(basePrjPartyUser);

            }

            if (errBuffer.length() > 0){
                return errBuffer.toString();
            } else {
                for (BasePrjPartyUser tmp : importList) {
                    String sql = "insert into BASE_PRJ_PARTY_USER (id,PM_PRJ_ID,PM_PARTY_ROLE_ID,USER_IDS,status) values ((select uuid_short()),?,?,?,'AP')";
                    jdbcTemplate.update(sql,tmp.getPmPrjId(),tmp.getPmPartyRoleId(),tmp.getUserId());
                }
            }


        } catch (Exception e){

        }
        return msg;
    }
}
