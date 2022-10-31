package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.cisdi.pms.job.excel.model.FundImplementationExportModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.Util;
import com.qygly.shared.BaseException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundImplementationImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description 资金批复导入
 * @date 2022/10/31
 */
@RestController
@RequestMapping("implementation")
public class FundImplementationImportController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @SneakyThrows(IOException.class)
    @PostMapping(value = "/import")
    public String importData(MultipartFile file) {
        List<String> res = new ArrayList<>();
        List<FundImplementationExportModel> dataList = EasyExcelUtil.read(file.getInputStream(), FundImplementationExportModel.class);
        Map<String, List<FundImplementationExportModel>> data = dataList.stream().collect(Collectors.groupingBy(FundImplementationExportModel::getSourceName));
        for (String key : data.keySet()) {
            // 这里就是你处理代码保存的逻辑了
            res = this.importData(data.get(key));
        }
        if (CollectionUtils.isEmpty(res)) {
            return "导入成功！";
        } else {
            return "导入失败！" + res.stream().collect(Collectors.joining(",")) + "不存在！";
        }

    }


    private List<String> importData(List<FundImplementationExportModel> modelList) {
        List<String> res = new ArrayList<>();
        String typeName = modelList.get(0).getCategoryName();
        List<Map<String, Object>> typeList = jdbcTemplate.queryForList("select * from fund_type where `name`=?", typeName);
        String typeId = "";
        if (CollectionUtils.isEmpty(typeList)) {
            typeId = Util.insertData(jdbcTemplate, "FUND_TYPE");
            jdbcTemplate.update("update FUND_TYPE set NAME =? where ID=?", typeName, typeId);
        } else {
            typeId = String.valueOf(typeList.get(0).get("ID"));
        }
        String id = Util.insertData(jdbcTemplate, "fund_implementation");

        jdbcTemplate.update("update fund_implementation set FUND_SOURCE_TEXT=?,FUND_CATEGORY_FIRST=?,DECLARED_AMOUNT=?,APPROVAL_TIME=? where ID=?",
                modelList.get(0).getCategoryName(), typeId, 0, modelList.get(0).getApprovalTime(), id);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_prj where status='AP'");
        for (FundImplementationExportModel model : modelList) {
            Optional<Map<String, Object>> optional = list.stream().filter(p -> model.getProjectName().equals(String.valueOf(p.get("NAME")))).findAny();
            if (optional.isPresent()) {
                Map<String, Object> pro = optional.get();
                String detailId = Util.insertData(jdbcTemplate, "fund_implementation_detail");
                jdbcTemplate.update("update fund_implementation_detail set FUND_IMPLEMENTATION_ID=?,PM_PRJ_ID=?,APPROVED_AMOUNT=? where ID=?", id, pro.get("ID"), model.getApprovedAmount(), detailId);
            } else {
                res.add(model.getProjectName());
            }
        }
        return res;
    }
}
