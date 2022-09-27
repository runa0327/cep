package com.cisdi.pms.job.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.model.FundReachExportModel;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title FundReachController
 * @package com.cisdi.pms.excel
 * @description
 * @date 2022/9/27
 */
@RestController
@RequestMapping("reach")
public class FundReachController extends BaseController {

    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void reachExcel(HttpServletResponse response) {
        super.setExcelRespProp(response, "测试导出");

        List<FundReachExportModel> list = new ArrayList<>();

        FundReachExportModel model = new FundReachExportModel();
        model.setName("小明");
        model.setRemark("你好");
        list.add(model);

        FundReachExportModel model1 = new FundReachExportModel();
        model1.setName("小明2222");
        model1.setRemark("你好222");
        list.add(model1);


        EasyExcel.write(response.getOutputStream())
                .head(FundReachExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("测试导出")
                .doWrite(list);
    }


}
