package com.cisdi.pms.job.excel.imports;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.cisdi.pms.job.excel.model.FundImplementationExportModel;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

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


    @SneakyThrows(IOException.class)
    @RequestMapping(value = "/import")
    public String importData(MultipartFile file) {
        // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
        // 这里每次会读取100条数据 然后返回过来 直接调用使用数据就行
        EasyExcel.read(file.getInputStream(), FundImplementationExportModel.class, new PageReadListener<FundImplementationExportModel>(dataList -> {
            for (FundImplementationExportModel model : dataList) {
                // 这里就是你处理代码保存的逻辑了

            }
        })).sheet().doRead();

        return "";
    }
}
