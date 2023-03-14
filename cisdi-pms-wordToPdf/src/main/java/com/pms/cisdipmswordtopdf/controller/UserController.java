package com.pms.cisdipmswordtopdf.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.pms.cisdipmswordtopdf.model.BriskUser;
import com.pms.cisdipmswordtopdf.model.BriskUserExportModel;
import com.pms.cisdipmswordtopdf.service.UserService;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Resource
    private UserService userService;

    @GetMapping(value = "/downloadBriskUser")
    @SneakyThrows
    public void BriskUserExport(BriskUser briskUser, HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode("活跃用户情况", "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
        List<BriskUserExportModel> models = userService.briskUserExport(briskUser);
        EasyExcel.write(response.getOutputStream())
                .head(BriskUserExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("活跃用户情况")
                .doWrite(models);
    }

}
