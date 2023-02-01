package com.cisdi.pms.job.excel.imports;

import com.cisdi.pms.job.excel.model.DynamicTempModel;
import com.cisdi.pms.job.excel.model.FundImplementationExportModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author dlt
 * @date 2023/2/1 周三
 */
@RestController
@RequestMapping("dynamic")
public class DynamicTempImport {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @PostMapping(value = "/import")
    public void importData(MultipartFile file, HttpServletResponse response) {
        System.out.println("进入导入，导入开始！");
        List<DynamicTempModel> list = EasyExcelUtil.read(file.getInputStream(), DynamicTempModel.class);
        List<DynamicTempModel> dataList = list.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());
        for (DynamicTempModel model : dataList) {
            String id = Util.insertData(jdbcTemplate, "DYNAMIC_TEMP");
            jdbcTemplate.update("update DYNAMIC_TEMP set NAME_ONE = ?,DATE_ONE = ?,REMARK_SHORT = ?,DEMAND_PROMOTER = ?,BANK_TYPE = ? where id = ?",
                    model.getNameOne(),model.getDateOne(),model.getRemarkShort(),model.getPromoter(),model.getType(),id);
        }
        System.out.println("导入完成");
    }
}
