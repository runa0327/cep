package com.cisdi.pms.job.excel.export;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.cisdi.pms.job.excel.model.FundImplementationExportModel;
import com.cisdi.pms.job.excel.model.request.FundImplementationReq;
import com.google.common.base.Strings;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
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
    private NamedParameterJdbcTemplate myNamedParameterJdbcTemplate;

    /**
     * 资金批复（落实）导出
     */
    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void implementationExcel(FundImplementationReq req, HttpServletResponse response) {
        String sourceName = req.getSourceName();
        String beginTime = req.getBeginTime();
        String endTime = req.getEndTime();
        String projectIds = req.getProjectIds();

        StringBuilder baseSql = new StringBuilder("select t1.name FUND_CATEGORY_FIRST,i.FUND_SOURCE_TEXT,p.name project_name,d.APPROVED_AMOUNT,i" +
                ".APPROVAL_TIME,i.remark " +
                "from fund_implementation i " +
                "left join fund_type t1 on t1.id = i.FUND_CATEGORY_FIRST " +
                "left join fund_type t2 on t2.id = i.FUND_CATEGORY_SECOND " +
                "left join fund_implementation_detail d on d.FUND_IMPLEMENTATION_ID = i.id " +
                "left join pm_prj p on p.id = d.PM_PRJ_ID " +
                "where 1 = 1 ");
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        if (!Strings.isNullOrEmpty(sourceName)) {
            baseSql.append("and i.FUND_SOURCE_TEXT like :sourceName ");
            queryParams.put("sourceName", "%" + sourceName + "%");
        }
        if (!Strings.isNullOrEmpty(projectIds)) {
            baseSql.append("and d.PM_PRJ_ID in (:ids) ");
            queryParams.put("ids", Arrays.asList(projectIds.split(",")));
        }
        if (!Strings.isNullOrEmpty(beginTime) && !Strings.isNullOrEmpty(endTime)) {
            baseSql.append("and i.APPROVAL_TIME BETWEEN :beginTime and :endTime ");
            queryParams.put("beginTime", beginTime);
            queryParams.put("endTime", endTime);
        }
        baseSql.append("order by i.CRT_DT desc ");

        List<Map<String, Object>> impList = myNamedParameterJdbcTemplate.queryForList(baseSql.toString(), queryParams);
        List<FundImplementationExportModel> resList = impList.stream().map(this::dataCovert).collect(Collectors.toList());
        super.setExcelRespProp(response, "资金批复明细表");
        EasyExcel.write(response.getOutputStream())
                .head(FundImplementationExportModel.class)
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("资金批复明细表")
                .doWrite(resList);
    }

    //封装实体
    private FundImplementationExportModel dataCovert(Map<String, Object> data) {
        FundImplementationExportModel model = new FundImplementationExportModel();
        model.setCategoryName(JdbcMapUtil.getString(data, "FUND_CATEGORY_FIRST"));
        model.setSourceName(JdbcMapUtil.getString(data, "FUND_SOURCE_TEXT"));
        model.setProjectName(JdbcMapUtil.getString(data, "project_name"));
        model.setApprovedAmount(Double.parseDouble(JdbcMapUtil.getString(data, "APPROVED_AMOUNT")));
        model.setApprovalTime(JdbcMapUtil.getString(data, "APPROVAL_TIME"));
        model.setRemark(JdbcMapUtil.getString(data, "remark"));
        return model;
    }

}
