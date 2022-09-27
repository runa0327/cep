package com.cisdi.pms.job.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.model.FundReachExportModel;
import com.cisdi.pms.job.model.request.FundReachRequest;
import lombok.SneakyThrows;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 资金落实导出
     * @param fundReachRequest
     * @param response
     */
    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void reachExcel(FundReachRequest fundReachRequest, HttpServletResponse response) {
        String sourceName = fundReachRequest.getSourceName();
        String projectId = fundReachRequest.getProjectId();
        String beginTime = fundReachRequest.getBeginTime();
        String endTime = fundReachRequest.getEndTime();

        StringBuilder sb = new StringBuilder();
        //TODO 查询优化
        sb.append("select PM_PRJ_ID,FUND_SOURCE_TEXT,FUND_REACH_CATEGORY,IFNULL(REACH_AMOUNT,0) AS REACH_AMOUNT,PAYEE,RECEIPT_ACCOUNT,REACH_DATE,ATT_FILE_GROUP_ID from fund_reach where 1=1 ");
        if (Strings.isNotEmpty(sourceName)) {
            sb.append(" and FUND_SOURCE_TEXT like '%").append(sourceName).append("%'");
        }
        if (Strings.isNotEmpty(projectId)) {
            sb.append(" and PM_PRJ_ID =").append(projectId);
        }
        if (Strings.isNotEmpty(beginTime) && Strings.isNotEmpty(endTime)) {
            sb.append(" and REACH_DATE between ").append(beginTime).append(" and ").append(endTime);
        }
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<FundReachExportModel> resList = list.stream().map(this::dataConvert).collect(Collectors.toList());
        super.setExcelRespProp(response, "资金到位明显");
        EasyExcel.write(response.getOutputStream())
                .head(FundReachExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金到位明显")
                .doWrite(resList);
    }


    private FundReachExportModel dataConvert(Map<String, Object> data) {
        FundReachExportModel model = new FundReachExportModel();
        return model;
    }

}
