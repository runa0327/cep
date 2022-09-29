package com.cisdi.pms.job.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.model.FundReachExportModel;
import com.cisdi.pms.job.model.request.FundReachRequest;
import com.qygly.shared.util.JdbcMapUtil;
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
 * @description 资金到位导出
 * @date 2022/9/27
 */
@RestController
@RequestMapping("reach")
public class FundReachController extends BaseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 资金到位导出
     *
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
        sb.append("select r.ID,p.name projectName,r.FUND_SOURCE_TEXT sourceName,r.FUND_REACH_CATEGORY reachCategory,IFNULL(REACH_AMOUNT,0) amt," +
                "REACH_DATE reachDate,t1.name firstTypeName,r.payee,b1.name bank,b2.name account,r.remark,temp.count,temp.sumAmt " +
                "from fund_reach r left join pm_prj p on p.id = r.PM_PRJ_ID " +
                "left join fund_implementation i on i.FUND_SOURCE_TEXT = r.FUND_SOURCE_TEXT " +
                "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                "left join receiving_bank b1 on b1.id = r.RECEIVING_BANK_ID " +
                "left join receiving_bank b2 on b2.id = r.RECEIPT_ACCOUNT " +
                "left join (SELECT count(*) count,sum( REACH_AMOUNT ) sumAmt,FUND_SOURCE_TEXT,PM_PRJ_ID,FUND_REACH_CATEGORY FROM fund_reach group " +
                "by FUND_SOURCE_TEXT,PM_PRJ_ID,FUND_REACH_CATEGORY) temp on temp.FUND_SOURCE_TEXT = r.FUND_SOURCE_TEXT and temp.PM_PRJ_ID = r" +
                ".PM_PRJ_ID and temp.FUND_REACH_CATEGORY = r.FUND_REACH_CATEGORY " +
                "where 1=1 ");
        if (Strings.isNotEmpty(sourceName)) {
            sb.append(" and r.FUND_SOURCE_TEXT like '%").append(sourceName).append("%'");
        }
        if (Strings.isNotEmpty(projectId)) {
            sb.append(" and r.PM_PRJ_ID = '").append(projectId).append("'");
        }
        if (Strings.isNotEmpty(beginTime) && Strings.isNotEmpty(endTime)) {
            sb.append(" and r.REACH_DATE between '").append(beginTime).append("' and '").append(endTime).append("'");
        }
        sb.append(" order by r.CRT_DT desc ");
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<FundReachExportModel> resList = list.stream().map(this::dataConvert).collect(Collectors.toList());
        super.setExcelRespProp(response, "资金到位明细");
        EasyExcel.write(response.getOutputStream())
                .head(FundReachExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金到位明细")
                .doWrite(resList);
    }


    private FundReachExportModel dataConvert(Map<String, Object> data) {
        FundReachExportModel model = new FundReachExportModel();
        model.setCategoryName(JdbcMapUtil.getString(data,"firstTypeName"));
        model.setSourceName(JdbcMapUtil.getString(data,"sourceName"));
        model.setProjectName(JdbcMapUtil.getString(data,"projectName"));
        model.setCount(JdbcMapUtil.getString(data,"count"));
        model.setTypeName(JdbcMapUtil.getString(data,"reachCategory"));
        model.setDwAmt(JdbcMapUtil.getDouble(data,"amt"));
        model.setTotalDwAmt(JdbcMapUtil.getDouble(data,"sumAmt"));
        model.setDwDate(JdbcMapUtil.getString(data,"reachDate"));
        model.setUnit(JdbcMapUtil.getString(data,"payee"));
        model.setBank(JdbcMapUtil.getString(data,"bank"));
        model.setRemark(JdbcMapUtil.getString(data,"remark"));
        model.setAccount(JdbcMapUtil.getString(data,"account"));
        return model;
    }

    /**
     * 测试excel 导出
     *
     * @param request
     * @param response
     */
    @SneakyThrows(IOException.class)
    @GetMapping("exportTest")
    public void exportTest(FundReachRequest request, HttpServletResponse response) {
        String sourceName = request.getSourceName();
        List<FundReachExportModel> resList = new ArrayList<>();
        FundReachExportModel model = new FundReachExportModel();
        model.setCategoryName("大类XXX");
        model.setSourceName("2019财政拨款");
        resList.add(model);

        FundReachExportModel model1 = new FundReachExportModel();
        model1.setCategoryName("大类XXX111");
        model1.setSourceName("2020财政拨款");
        resList.add(model1);


        super.setExcelRespProp(response, "资金到位明细");
        EasyExcel.write(response.getOutputStream())
                .head(FundReachExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金到位明细")
                .doWrite(resList);

    }
}
