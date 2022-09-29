package com.cisdi.pms.job.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.model.FundImplementationExportModel;
import com.cisdi.pms.job.model.FundReachExportModel;
import com.cisdi.pms.job.model.request.FundImplementationReq;
import com.google.common.base.Strings;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 资金批复（落实）控制层
 */
@RestController
@RequestMapping("implementation")
public class FundImplementationController extends BaseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 资金批复（落实）导出
     */
    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void implementationExcel(FundImplementationReq req, HttpServletResponse response){
        String sourceName = req.getSourceName();
        String projectId = req.getProjectId();
        String beginTime = req.getBeginTime();
        String endTime = req.getEndTime();

        StringBuffer baseSql = new StringBuffer("select t1.name FUND_CATEGORY_FIRST,i.FUND_SOURCE_TEXT,p.name project_name,d.APPROVED_AMOUNT,i" +
                ".APPROVAL_TIME,i.remark " +
                "from fund_implementation i " +
                "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                "left join fund_type t2 on t2.id = i.FUND_CATEGORY_SECOND " +
                "left join fund_implementation_detail d on d.FUND_IMP_ID = i.id " +
                "left join pm_prj p on p.id = d.PM_PRJ_ID " +
                "where 1 = 1 ");
        if (!Strings.isNullOrEmpty(sourceName)) {
            baseSql.append("and i.FUND_SOURCE_TEXT like '%" + sourceName + "%' ");
        }
        if (!Strings.isNullOrEmpty(projectId)) {
            baseSql.append("and d.PM_PRJ_ID = '").append(projectId).append("' ");
        }
        if (!Strings.isNullOrEmpty(beginTime) && !Strings.isNullOrEmpty(endTime)) {
            baseSql.append("and i.APPROVAL_TIME BETWEEN '" + beginTime + "' and '" + endTime + "' ");
        }
        baseSql.append("order by i.CRT_DT desc ");
        List<Map<String, Object>> impList = jdbcTemplate.queryForList(baseSql.toString());
        List<FundImplementationExportModel> resList = impList.stream().map(this::dataCovert).collect(Collectors.toList());
        super.setExcelRespProp(response,"资金批复明细表");
        EasyExcel.write(response.getOutputStream())
                .head(FundImplementationExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金批复明细表")
                .doWrite(resList);
    }

    //封装实体
    private FundImplementationExportModel dataCovert(Map<String,Object> data){
        FundImplementationExportModel model = new FundImplementationExportModel();
        model.setCategoryName(JdbcMapUtil.getString(data,"FUND_CATEGORY_FIRST"));
        model.setSourceName(JdbcMapUtil.getString(data,"FUND_SOURCE_TEXT"));
        model.setProjectName(JdbcMapUtil.getString(data,"project_name"));
        model.setApprovedAmount(JdbcMapUtil.getDouble(data,"APPROVED_AMOUNT"));
        model.setApprovalTime(JdbcMapUtil.getString(data,"APPROVAL_TIME"));
        model.setRemark(JdbcMapUtil.getString(data,"remark"));
        return model;
    }

}
