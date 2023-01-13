package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.excel.model.FundImplementationExportModel;
import com.cisdi.pms.job.excel.model.FundReachExportModel;
import com.cisdi.pms.job.utils.EasyExcelUtil;
import com.cisdi.pms.job.utils.ReflectUtil;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
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
 * @title FundImplementationImportController
 * @package com.cisdi.pms.job.excel.imports
 * @description 资金批复导入
 * @date 2022/10/31
 */
@RestController
@RequestMapping("implementation")
public class FundImplementationImportController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("test")
    public String sayHello() {
        return "Hello！资金批复导入";
    }

    @SneakyThrows(IOException.class)
    @PostMapping(value = "/import")
    public Map<String, Object> importData(MultipartFile file, HttpServletResponse response) {
        System.out.println("进入导入，导入开始！");
        Map<String, Object> result = new HashMap<>();
        List<String> res = new ArrayList<>();
        List<FundImplementationExportModel> list = EasyExcelUtil.read(file.getInputStream(), FundImplementationExportModel.class);

        List<FundImplementationExportModel> dataList = list.stream().filter(p -> !ReflectUtil.isObjectNull(p)).collect(Collectors.toList());

        Map<String, List<FundImplementationExportModel>> data = dataList.stream().collect(Collectors.groupingBy(FundImplementationExportModel::getSourceName));

        //检查和已有资金来源是否重复，并去重
        Map<String, Set<String>> duplicateMap = this.checkDuplicate(data.keySet());
        Set<String> duplicatedSources = duplicateMap.get("duplicatedSources");
        Set<String> keySet = duplicateMap.get("keySet");
        if (!CollectionUtils.isEmpty(keySet)) {
            for (String key : keySet) {
                // 这里就是你处理代码保存的逻辑了
                res = this.importData(data.get(key));
            }
        }

        if (CollectionUtils.isEmpty(res) && CollectionUtils.isEmpty(duplicatedSources)) {
            result.put("code", 200);
            result.put("message", "导入成功！");
            return result;
        } else {
            for (String duplicatedSource : duplicatedSources) {
                String msg = "资金来源为:" + duplicatedSource + "重复，未导入！";
                res.add(msg);
            }
            super.exportTxt(response, res,"资金批复导入日志");
            return null;
        }
    }


    private List<String> importData(List<FundImplementationExportModel> modelList) {
        List<String> res = new ArrayList<>();
        String typeName = modelList.get(0).getCategoryName().toLowerCase();
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
                modelList.get(0).getSourceName(), typeId, 0, modelList.get(0).getApprovalTime(), id);

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_prj where status='AP'");
        for (FundImplementationExportModel model : modelList) {
            Optional<Map<String, Object>> optional = list.stream().filter(p -> model.getProjectName().equals(String.valueOf(p.get("NAME")))).findAny();
            if (optional.isPresent()) {
                Map<String, Object> pro = optional.get();
                String detailId = Util.insertData(jdbcTemplate, "fund_implementation_detail");
                jdbcTemplate.update("update fund_implementation_detail set FUND_IMPLEMENTATION_ID=?,PM_PRJ_ID=?,APPROVED_AMOUNT=? where ID=?", id, pro.get("ID"), model.getApprovedAmount(), detailId);
            } else {
                res.add("项目名称为:" + model.getProjectName() + "不存在，未导入！");
            }
        }
        return res;
    }


    /**
     * 检查和已有资金来源是否重复，并去重
     *
     * @param keySet 待导入的资金来源名称
     */
    private Map<String, Set<String>> checkDuplicate(Set<String> keySet) {
        Map<String, Set<String>> result = new HashMap<>();
        List<Map<String, Object>> existedSource = jdbcTemplate.queryForList("select FUND_SOURCE_TEXT from fund_implementation");
        if (!CollectionUtils.isEmpty(keySet) && !CollectionUtils.isEmpty(existedSource)) {
            Set<String> duplicatedSources = existedSource.stream()
                    .map(sourceMap -> String.valueOf(sourceMap.get("FUND_SOURCE_TEXT")))
                    .filter(sourceName -> keySet.contains(sourceName))
                    .collect(Collectors.toSet());
            keySet.removeAll(duplicatedSources);
            result.put("duplicatedSources", duplicatedSources);
        }
        result.put("keySet", keySet);
        return result;
    }
}
