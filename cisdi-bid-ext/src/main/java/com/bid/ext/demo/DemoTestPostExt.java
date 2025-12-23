package com.bid.ext.demo;

import cn.hutool.core.util.IdUtil;
import com.bid.ext.model.FlFile;
import com.bid.ext.model.PostUser;
import com.bid.ext.utils.ExcelFileUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.InvokeActResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.StringUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public class DemoTestPostExt {


    //导入帖子用户
    public void importPostUser(){
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        Boolean support = (Integer) varMap.get("SUPPORT") != 0;
//        String filePath = flFile.getPhysicalLocation();
        String filePath = "C:\\Users\\15798\\Desktop\\用户模板.xlsx";
//        log.info("filePath:{}",filePath);
        int successNum = 0;
        int failureNum = 0;
        StringBuilder successMsg = new StringBuilder();
        StringBuilder failureMsg = new StringBuilder();
        try(FileInputStream file = new FileInputStream(filePath)){
            Workbook workbook = WorkbookFactory.create(file);
            // 获取第一个工作簿
            Sheet sheet = workbook.getSheetAt(0);
            // 获取行数
            int rowNum = sheet.getLastRowNum();
            // 循环遍历内容
            for (int index = 1; index <= rowNum; index++){
                // 获取每行
                Row row = sheet.getRow(index);
                if (!ExcelFileUtils.isRowEmpty(row)) {
                    PostUser postUser;
                    // 用户id
                    String userId = "";
                    // 用户名
//                    String username = (String)getCellValue(row,0, String.class,false);
                    String username = ExcelFileUtils.getCellValueAsString(row.getCell(0));
                    // 用户昵称
//                    String nickName = (String)getCellValue(row,1, String.class,false);
                    String nickName = ExcelFileUtils.getCellValueAsString(row.getCell(1));
                    // 用户邮箱
//                    String email = (String)getCellValue(row,2, String.class,false);
                    String email = ExcelFileUtils.getCellValueAsString(row.getCell(2));
                    // 账号状态
//                    String status = (String)getCellValue(row,3, String.class,false);
                    String status = ExcelFileUtils.getCellValueAsString(row.getCell(3));
                    List<Map<String, Object>> statusMaps = myJdbcTemplate.queryForList("SELECT ID FROM post_user_status WHERE `NAME`->>'$.ZH_CN'= ? OR `NAME`= ?",status,status);
                    if (statusMaps == null || statusMaps.size()<1){
                        throw new BaseException("第" + (row.getRowNum() + 1) + "行，【账号状态】填写错误");
                    }
                    String statusId = (String) statusMaps.get(0).get("ID");
                    // 备注
//                    String remark = (String)getCellValue(row,4, String.class,true);
                    String remark = ExcelFileUtils.getCellValueAsString(row.getCell(4));
                    // 查询用户名是否存在
                    List<Map<String, Object>> userMaps = myJdbcTemplate.queryForList("SELECT ID FROM post_user WHERE (JSON_VALID(`NAME`) AND `NAME`->>'$.ZH_CN' = ?) OR `NAME`= ?", username, username);
                    if (Objects.isNull(userMaps) || userMaps.size() < 1){
                        userId = IdUtil.getSnowflakeNextIdStr();
                        postUser = PostUser.newData();
                        postUser.setId(userId);
                        postUser.setName(username);
                        postUser.setPostUserNick(nickName);
                        postUser.setPostUserPwd("123456");
                        postUser.setPostUserEmail(email);
                        postUser.setPostUserStatusId(statusId);
                        postUser.setRemark(remark);
                        postUser.insertById();
                        successNum++;
                        successMsg.append("\n" + successNum + "、账号 " + username + " 导入成功");
                    } else if (support) {
                        userId = (String) userMaps.get(0).get("ID");
                        postUser = PostUser.selectById(userId);
                        postUser.setName(username);
                        postUser.setPostUserNick(nickName);
                        postUser.setPostUserPwd("123456");
                        postUser.setPostUserEmail(email);
                        postUser.setPostUserStatusId(statusId);
                        postUser.setRemark(remark);
                        postUser.updateById();
                        successNum++;
                        successMsg.append("\n" + successNum + "、账号 " + username + " 更新成功");
                    }else {
                        failureNum++;
                        failureMsg.append("\n" + failureNum + "、账号 " + username + " 已存在");
                    }
                }
            }
        }catch (IOException e) {
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importFileError");
            throw new BaseException(msg);
        }
        if (failureNum > 0) {
            failureMsg.insert(0, "很抱歉，导入失败！共 " + failureNum + " 条数据格式不正确，错误如下：");
            throw new BaseException(failureMsg.toString());
        } else {
            successMsg.insert(0, "恭喜您，数据已全部导入成功！共 " + successNum + " 条，数据如下：");
        }
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        invokeActResult.msg = successMsg.toString();
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     *
     * @param row
     * @param serialNumber
     * @param clazz
     * @param canBeEmpty
     * @return
     */
    private Object getCellValue(Row row,Integer serialNumber,Class clazz,Boolean canBeEmpty){
        Cell cell = row.getCell(serialNumber);
        if (cell == null || getCellValueAsString(cell) == null || !StringUtils.hasLength(getCellValueAsString(cell))) {
            if (canBeEmpty){
                return  null;
            }else {
                String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.extSpecialEquip.importExcelCellIsNull", row.getRowNum() + 1, serialNumber+1);
                throw new BaseException(msg);
            }
        }
        Object object;
        if ("String".equals(clazz.getSimpleName())){
            object = getCellValueAsString(cell);
        }else if("LocalDate".equals(clazz.getSimpleName()) ){
            object = getDate(cell);
        }else if("Integer".equals(clazz.getSimpleName())){
            object = new BigDecimal(getCellValueAsString(cell)).intValue();
        }else if("BigDecimal".equals(clazz.getSimpleName())){
            object = new BigDecimal(getCellValueAsString(cell));
        }else {
            throw new BaseException("不能转换"+clazz.getName());
        }
        return object;
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
            default:
                cellValue = "";
        }
        return cellValue;
    }


    private LocalDate getDate(Cell cell) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String cellValueAsString = getCellValueAsString(cell);
        Date date = null;
        if (cell != null) {
            try {
                date = sdf.parse(cellValueAsString);
            } catch (ParseException e) {
                e.printStackTrace();
                double excelDate = cell.getNumericCellValue();
                date = DateUtil.getJavaDate(excelDate);
            }
        }
        if (date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    private String getNormalTimeStr(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

}
