package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.ProjectRosterModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProjectRosterImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description 项目花名册导入
 * @date 2023/2/14
 */
@RestController
@RequestMapping("/projectRoster")
public class ProjectRosterImportController extends BaseController {


    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @GetMapping("exportTemplate")
    public void exportExcel(HttpServletResponse response) {
        List<Map<String, Object>> strList = jdbcTemplate.queryForList("select p.`NAME` as PROJECT_POST from PM_ROSTER t  left join post_info p on t.POST_INFO_ID = p.id where p.`NAME` is not null GROUP BY p.id");
        List<String> headerList = strList.stream().map(p -> JdbcMapUtil.getString(p, "PROJECT_POST")).collect(Collectors.toList());
        headerList.add(0, "项目名称");
        headerList.removeAll(Collections.singleton(null));

        super.setExcelRespProp(response, "项目花名册导入模板");
        EasyExcel.write(response.getOutputStream())
                .head(EasyExcelUtil.head(headerList))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("项目花名册导入模板")
                .doWrite(new ArrayList<>());
    }

    /**
     * 新版导入
     *
     * @param file
     * @param response
     * @return
     */
    @SneakyThrows(IOException.class)
    @RequestMapping("/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<>();
        //如果有不能处理的字段，响应提示
        List<String> res = new ArrayList<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheetAt(0);
        Row firstRow = sheet.getRow(sheet.getFirstRowNum()); //获取第一行数据
        if (null == firstRow) { //判断第一行数据是否为空
            res.add("第一行没有读取到任何数据！");
        }
        int rowStart = sheet.getFirstRowNum() + 1; //根据模板确定开始解析内容的行标
        int rowEnd = sheet.getPhysicalNumberOfRows(); //结束的行标
        List<ProjectRosterModel> list = new ArrayList<>();
        for (int rowNum = rowStart; rowNum < rowEnd; rowNum++) {
            Row row = sheet.getRow(rowNum); //根据下标逐次获取行对象
            String projectName = row.getCell(0).toString();
            int rowNum1 = row.getLastCellNum() - row.getFirstCellNum();
            for (int i = 1; i < rowNum1; i++) {
                String userName = row.getCell(i).toString();
                if (!"".equals(userName) && !"/".equals(userName)) {
                    ProjectRosterModel model = new ProjectRosterModel();
                    model.setProjectName(projectName);
                    model.setPostName(firstRow.getCell(i).toString());
                    model.setUserName(userName);
                    list.add(model);
                }
            }
        }
        //去除空行
        List<ProjectRosterModel> rosterModelList = list.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());

        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");

        List<Map<String, Object>> postList = jdbcTemplate.queryForList("select * from post_info where status ='ap'");

        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select id,name from ad_user where status = 'AP'");

        for (int i = rosterModelList.size() - 1; i >= 0; i--) {//保证列表显示顺序和表格顺序一致
            List<String> singleRes = this.insertData(rosterModelList.get(i), prjList, postList, userList);
            if (!CollectionUtils.isEmpty(singleRes)) {
                res.addAll(singleRes);
            }
        }

        if (CollectionUtils.isEmpty(res)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            super.exportTxt(response, res, "项目岗位导入日志");
            return null;
        }
    }

    //旧版导入
//    @SneakyThrows(IOException.class)
//    @RequestMapping("/import")
//    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
//        Map<String, Object> result = new HashMap<>();
//        List<ProjectRosterModel> list = EasyExcelUtil.read(file.getInputStream(), ProjectRosterModel.class);
//        //去除空行
//        List<ProjectRosterModel> rosterModelList = list.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
//        //如果有不能处理的字段，响应提示
//        List<String> res = new ArrayList<>();
//        List<Map<String, Object>> prjList = jdbcTemplate.queryForList("select id,name from pm_prj where status = 'AP'");
//
//        List<Map<String, Object>> postList = jdbcTemplate.queryForList("select * from post_info where status ='ap'");
//
//        List<Map<String, Object>> userList = jdbcTemplate.queryForList("select id,name from ad_user where status = 'AP'");
//
//        for (int i = rosterModelList.size() - 1; i >= 0; i--) {//保证列表显示顺序和表格顺序一致
//            List<String> singleRes = this.insertData(rosterModelList.get(i), prjList, postList, userList);
//            if (!CollectionUtils.isEmpty(singleRes)) {
//                res.addAll(singleRes);
//            }
//        }
//
//        if (CollectionUtils.isEmpty(res)) {
//            result.put("code", 200);
//            result.put("message", "导入成功！");
//            return result;
//        } else {
//            super.exportTxt(response, res, "项目岗位导入日志");
//            return null;
//        }
//    }
//
    private List<String> insertData(ProjectRosterModel model, List<Map<String, Object>> prjList, List<Map<String, Object>> postList, List<Map<String, Object>> userList) {
        List<String> res = new ArrayList<>();
        if (model == null) {
            return null;
        }
        //项目 先判断项目没有对应项目直接返回
        Optional<Map<String, Object>> optional = prjList.stream().filter(p -> String.valueOf(p.get("name")).equals(model.getProjectName())).findAny();
        if (!optional.isPresent()) {
            res.add("项目名称为:" + model.getProjectName() + "不存在，未导入！");
            return res;
        }
        String prjId = String.valueOf(optional.get().get("id"));

        Optional<Map<String, Object>> postOptional = postList.stream().filter(p -> String.valueOf(p.get("name")).equals(model.getPostName())).findAny();
        if (!postOptional.isPresent()) {
            res.add("岗位名称为:" + model.getPostName() + "不存在，未导入！");
            return res;
        }
        String postId = String.valueOf(postOptional.get().get("id"));

        Optional<Map<String, Object>> userOptional = userList.stream().filter(p -> String.valueOf(p.get("name")).equals(model.getUserName())).findAny();
        if (!userOptional.isPresent()) {
            res.add("用户名称为:" + model.getUserName() + "不存在，未导入！");
            return res;
        }
        String userId = String.valueOf(userOptional.get().get("id"));

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from PM_ROSTER where PM_PRJ_ID=? and post_info_id=?", prjId, postId);
        if (!CollectionUtils.isEmpty(list)) {
            jdbcTemplate.update("update PM_ROSTER set AD_USER_ID=? where id=?", userId, list.get(0).get("ID"));
        } else {
            String id = Util.insertData(jdbcTemplate, "PM_ROSTER");
            jdbcTemplate.update("update PM_ROSTER set PM_PRJ_ID =?,post_info_id=?,AD_USER_ID=? where id=?", prjId, postId, userId, id);
        }
        return res;
    }
}
