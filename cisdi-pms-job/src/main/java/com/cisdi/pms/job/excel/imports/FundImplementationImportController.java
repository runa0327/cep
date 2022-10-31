package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.cisdi.pms.job.excel.model.FundImplementationExportModel;
import com.cisdi.pms.job.utils.Util;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
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
    @RequestMapping(value = "/import")
    public String importData(MultipartFile file) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(file.getInputStream(), FundImplementationExportModel.class, new PageReadListener<FundImplementationExportModel>(dataList -> {
            Map<String, List<FundImplementationExportModel>> data = dataList.stream().collect(Collectors.groupingBy(FundImplementationExportModel::getSourceName));
            for (String key : data.keySet()) {
                // 这里就是你处理代码保存的逻辑了
                this.importData(data.get(key));
            }
        })).sheet().doRead();

        return "";
    }


    private void importData(List<FundImplementationExportModel> modelList) {
        Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("select * from fund_type where `name`=?", modelList.get(0).getCategoryName());
        String id = Util.insertData(jdbcTemplate, "fund_implementation");

        jdbcTemplate.update("update fund_implementation set FUND_SOURCE_TEXT=?,FUND_CATEGORY_FIRST=?,DECLARED_AMOUNT=?,APPROVAL_TIME=? where ID=?",
                modelList.get(0).getCategoryName(), stringObjectMap.get("ID"), 0, modelList.get(0).getApprovalTime(), id);

        for (FundImplementationExportModel model : modelList) {
            Map<String, Object> pro = jdbcTemplate.queryForMap("select * from pm_prj where `name`=?", model.getProjectName());
            String detailId = Util.insertData(jdbcTemplate, "fund_implementation_detail");
            jdbcTemplate.update("update fund_implementation_detail set FUND_IMPLEMENTATION_ID=?,PM_PRJ_ID=?,APPROVED_AMOUNT=? where ID=?", id, pro.get("ID"), model.getApprovedAmount(), detailId);
        }
    }
}
