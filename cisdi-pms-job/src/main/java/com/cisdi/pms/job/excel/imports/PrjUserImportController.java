package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.BasePrjPartyUser;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.StringUtils;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目人员信息导入
 */
@RestController
@RequestMapping(value = "/prjImport")
@Slf4j
public class PrjUserImportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping(value = "/importPrjUser")
    public Map<String, Object> importPrjUser(MultipartFile file){
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

//        String msg = importExcel(excelFile);
        String msg = importExcelNew(excelFile);
        map.put("code",200);
        map.put("success",msg);
        return map;
    }

    /** 项目成员配置 新模板 **/
    @Transactional(rollbackFor = Exception.class)
    public String importExcelNew(File excelFile) {
        //查询项目信息
        String sql1 = "select id,name from pm_prj where status = 'ap' order by name asc";
        List<Map<String,Object>> projectList = jdbcTemplate.queryForList(sql1);
        if (CollectionUtils.isEmpty(projectList)){
            throw new BaseException("项目库信息不能为空");
        }

        //查询合作方
        String sql3 = "select id,name from pm_party where status = 'AP'";
        List<Map<String,Object>> partyList = jdbcTemplate.queryForList(sql3);

        //查询单位
        String sql4 = "select id,name from gr_set_value where GR_SET_ID = '99952822476391029'";
        List<Map<String,Object>> localList = jdbcTemplate.queryForList(sql4);

        String msg = "";
        StringBuffer errBuffer = new StringBuffer();
        try {
            Workbook workbook = WorkbookFactory.create(new FileInputStream(excelFile));
            Sheet hssfSheet = workbook.getSheetAt(0);
            int rowStart = hssfSheet.getFirstRowNum();//起始行数
            int rowEnd = hssfSheet.getLastRowNum();//总行数

            List<BasePrjPartyUser> importList = new ArrayList<>();
            for (int i = 2; i <= rowEnd; i++) {
                Row row = hssfSheet.getRow(i);

                BasePrjPartyUser basePrjPartyUser = new BasePrjPartyUser();
                basePrjPartyUser.setVer("99");

                //项目名称
                String projectName = EasyExcelUtil.getCellValueAsString(row.getCell(1)).trim();
                String projectId = "";
                int num1 = 0;
                for (Map<String, Object> tmp : projectList) {
                    String name = JdbcMapUtil.getString(tmp,"name");
                    if (projectName.equals(name)){
                        projectId = JdbcMapUtil.getString(tmp,"id");
                        basePrjPartyUser.setPmPrjId(projectId);
                    } else {
                        num1++;
                    }
                }
                if (num1 == projectList.size()){
                    errBuffer = errBuffer.append("新增项目： "+projectName).append(" ; ");
                }
                if (SharedUtil.isEmptyString(projectId)){
                    projectId = Util.insertData(jdbcTemplate,"pm_prj");
                    String updateSql = "update pm_prj set ver = '99',CRT_DT = now(),CRT_USER_ID='99250247095871681',STATUS='AP',name = ?,PROJECT_SOURCE_TYPE_ID='99952822476441374' where id = ?";
                    int s = jdbcTemplate.update(updateSql,projectName,projectId);
                    projectList = jdbcTemplate.queryForList(sql1);
                    basePrjPartyUser.setPmPrjId(projectId);
                }

                //业主单位
                String unitName = EasyExcelUtil.getCellValueAsString(row.getCell(2));
                if (!SharedUtil.isEmptyString(unitName)){
                    for (Map<String, Object> tmp : partyList) {
                        if (unitName.equals(JdbcMapUtil.getString(tmp,"name"))){
                            basePrjPartyUser.setPmPartyId(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //单位
                String unit = EasyExcelUtil.getCellValueAsString(row.getCell(3));
                if (!SharedUtil.isEmptyString(unit)){
                    for (Map<String, Object> tmp : localList) {
                        if (unit.equals(JdbcMapUtil.getString(tmp,"name"))){
                            basePrjPartyUser.setPmPartyRoleId(JdbcMapUtil.getString(tmp,"id"));
                            break;
                        }
                    }
                }

                //人员
                String users = EasyExcelUtil.getCellValueAsString(row.getCell(4));
//                System.out.println(users);
//                if ("温剑".equals(users)){
//                    System.out.println(projectName);
//                }
                StringBuilder sb = new StringBuilder();
                if (!SharedUtil.isEmptyString(users)){
                    users = StringUtils.replaceCode(users,",");
                    String[] userArr = users.split(",");
                    //查询人员信息
                    String sql2 = "select USER_IDS from pm_dept where status = 'ap' and PM_PRJ_ID = ? ";
                    List<Map<String,Object>> userList = jdbcTemplate.queryForList(sql2,projectId);
                    if (CollectionUtils.isEmpty(userList)){
                        errBuffer.append("项目： "+projectName+" 没有配置人员信息; ");
                    } else {
                        StringBuilder sbb = new StringBuilder();
                        for (Map<String, Object> tmp : userList) {
                            String userIds = JdbcMapUtil.getString(tmp,"USER_IDS");
                            if (!SharedUtil.isEmptyString(userIds)){
                                sbb.append(userIds).append(",");
                            }
                        }
                        String newUserIds = "";
                        if (sbb.length() > 0){
                            String userIds = sbb.substring(0,sbb.length()-1).toString();
                            List<String> userList1 = Arrays.asList(userIds.split(","));
                            userList1 = userList1.stream().distinct().collect(Collectors.toList());
                            newUserIds = String.join(",",userList1);
                        }
                        if (!SharedUtil.isEmptyString(newUserIds)){
                            newUserIds = newUserIds.replace(",","','");
                            String sql5 = "select distinct id,name from ad_user where id in ('"+newUserIds+"')";
                            List<Map<String,Object>> list5 = jdbcTemplate.queryForList(sql5);
                            if (!CollectionUtils.isEmpty(list5)){
                                for (String s : userArr) {
                                    s = s.trim();
                                    int errNum = 0;
                                    for (Map<String, Object> tmp : list5) {
                                        String name = JdbcMapUtil.getString(tmp,"name");
                                        if (s.equals(name)){
                                            sb.append(JdbcMapUtil.getString(tmp,"id")).append(",");
                                            break;
                                        } else {
                                            errNum++;
                                        }
                                    }
                                    if (errNum == list5.size()){
                                        errBuffer.append("在项目： "+projectName+" 中没有配置人员： "+s+" ,该人员导入失败; ");
                                    }
                                }
                            }
                            if (sb.length()>0){
                                users = sb.substring(0,sb.length()-1);
                                basePrjPartyUser.setUserId(users);
                            }
                        }

                    }
                }

                importList.add(basePrjPartyUser);
            }

            //插入数据
            if (!CollectionUtils.isEmpty(importList)){
                int fromIndex = 0;
                int toIndex = 100;
                int num = 0;
                while (true){
                    if (toIndex < importList.size()){
                        List<BasePrjPartyUser> list = importList.subList(fromIndex,toIndex);
                        log.info("索引{}-{}正在执行插入",fromIndex,toIndex);
                        int succ = insertDate(list);
                        num = num + succ;
                    } else if (toIndex == importList.size()){
                        List<BasePrjPartyUser> list = importList.subList(fromIndex,toIndex);
                        if (!CollectionUtils.isEmpty(list)){
                            log.info("索引{}-{}正在执行插入",fromIndex,toIndex);
                            int succ = insertDate(list);
                            num = num + succ;
                        }
                        break;
                    } else {
                        List<BasePrjPartyUser> list = importList.subList(fromIndex,importList.size());
                        if (!CollectionUtils.isEmpty(list)){
                            log.info("索引{}-{}正在执行插入",fromIndex,toIndex);
                            int succ = insertDate(list);
                            num = num + succ;
                        }
                        break;
                    }
                    fromIndex = toIndex;
                    toIndex = toIndex + 100;
                }
                errBuffer.append("执行成功，共导入数据： " + num + " 条数据");
                msg = errBuffer.toString();
            } else {
                errBuffer.append("导入失败，没有可导入数据");
                msg = errBuffer.toString();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }

    // 数据写入项目成员配置
    private int insertDate(List<BasePrjPartyUser> list) {
        int num = 0;
        for (BasePrjPartyUser tmp : list) {

            int num1 = 0;
            String sql = "";
            String prj = tmp.getPmPrjId();
            String roleId = tmp.getPmPartyRoleId();
            String userId = tmp.getUserId();
            //查询该项目该类型角色是否已存在
            String sql3 = "select * from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = ?";
            List<Map<String,Object>> list3 = jdbcTemplate.queryForList(sql3,prj,roleId);
            if (CollectionUtils.isEmpty(list3)){
                sql = "insert into BASE_PRJ_PARTY_USER (id,VER,CRT_DT,PM_PRJ_ID,PM_PARTY_ROLE_ID,USER_IDS,status) values ((select uuid_short()),'99',now(),?,?,?,'AP')";
                num1 = jdbcTemplate.update(sql,prj,roleId,userId);
            } else {
                sql = "update BASE_PRJ_PARTY_USER set USER_IDS = ?,ver = '99',CRT_DT=now() where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = ?";
                num1 = jdbcTemplate.update(sql,userId,prj,roleId);
            }

            num = num + num1;

        }
        return num;
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
            for (int i = 1; i <= rowEnd; i++) {
                Row row = hssfSheet.getRow(i);

                BasePrjPartyUser basePrjPartyUser1 = new BasePrjPartyUser();
                BasePrjPartyUser basePrjPartyUser2 = new BasePrjPartyUser();

                //项目名称
                String projectName = EasyExcelUtil.getCellValueAsString(row.getCell(1));
                int num1 = 0;
                for (Map<String, Object> tmp : projectList) {
                    String name = JdbcMapUtil.getString(tmp,"name");
                    if (projectName.equals(name)){
                        basePrjPartyUser1.setPmPrjId(JdbcMapUtil.getString(tmp,"id"));
                        basePrjPartyUser2.setPmPrjId(JdbcMapUtil.getString(tmp,"id"));
                    } else {
                        num1++;
                    }
                }
                if (num1 == projectList.size()){
                    errBuffer = errBuffer.append("没有找到 ‘"+projectName+"' 项目的id");
                }

                //施工单位联系人

                String buildUser1 = EasyExcelUtil.getCellValueAsString(row.getCell(7));
                StringBuffer userSB = new StringBuffer();
                List<String> user = StringUtils.splitByCode(buildUser1,",");
                for (String s : user) {
                    int num2 = 0;
                    for (Map<String, Object> tmp : userList) {
                        String name = JdbcMapUtil.getString(tmp,"code");
                        if (s.equals(name)){
                            userSB = userSB.append(JdbcMapUtil.getString(tmp,"id")).append(",");
                            break;
                        } else {
                            num2++;
                        }
                    }
                    if (num2 == userList.size()){
                        errBuffer = errBuffer.append("没有找到 ’"+s+"' 的人员");
                    }
                }

                if (userSB.length() > 0){
                    basePrjPartyUser1.setPmPartyRoleId("99952822476391092");
                    String buildUser = userSB.substring(0,userSB.length()-1);
                    basePrjPartyUser1.setUserId(buildUser);
                    importList.add(basePrjPartyUser1);
                }



                //监理单位联系人
                String buildUser2 = EasyExcelUtil.getCellValueAsString(row.getCell(10));
                StringBuffer userSB2 = new StringBuffer();
                List<String> user2 = StringUtils.splitByCode(buildUser2,",");
                for (String s : user2) {
                    int num3 = 0;
                    for (Map<String, Object> tmp : userList) {
                        String name = JdbcMapUtil.getString(tmp,"code");
                        if (s.equals(name)){
                            userSB2 = userSB2.append(JdbcMapUtil.getString(tmp,"id")).append(",");
                            break;
                        } else {
                            num3++;
                        }
                    }
                    if (num3 == userList.size()){
                        errBuffer = errBuffer.append("没有找到 ’"+s+"' 的人员");
                    }
                }
                if (userSB2.length()>0){
                    basePrjPartyUser2.setPmPartyRoleId("99952822476391096");
                    String reviewUser = userSB2.substring(0,userSB2.length()-1);
                    basePrjPartyUser2.setUserId(reviewUser);
                    importList.add(basePrjPartyUser2);
                }


            }

            if (errBuffer.length() > 0){
                return errBuffer.toString();
            } else {
                if (!CollectionUtils.isEmpty(importList)){
                    int success = 0;
                    for (BasePrjPartyUser tmp : importList) {
                        int num = 0;
                        String sql = "";
                        String prj = tmp.getPmPrjId();
                        String roleId = tmp.getPmPartyRoleId();
                        String userId = tmp.getUserId();
                        //查询该项目该类型角色是否已存在
                        String sql3 = "select * from BASE_PRJ_PARTY_USER where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = ?";
                        List<Map<String,Object>> list3 = jdbcTemplate.queryForList(sql3,prj,roleId);
                        if (CollectionUtils.isEmpty(list3)){
                            sql = "insert into BASE_PRJ_PARTY_USER (id,VER,PM_PRJ_ID,PM_PARTY_ROLE_ID,USER_IDS,status) values ((select uuid_short()),'1',?,?,?,'AP')";
                            num = jdbcTemplate.update(sql,prj,roleId,userId);
                        } else {
                            sql = "update BASE_PRJ_PARTY_USER set USER_IDS = ? where PM_PRJ_ID = ? and PM_PARTY_ROLE_ID = ?";
                            num = jdbcTemplate.update(sql,userId,prj,roleId);
                        }

                        success = success + num;
                    }
                    msg = "成功："+success+" 条数据";
                }

            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return msg;
    }
}
