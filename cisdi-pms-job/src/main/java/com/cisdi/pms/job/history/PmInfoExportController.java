package com.cisdi.pms.job.history;

import com.alibaba.excel.EasyExcel;
import com.cisdi.pms.job.excel.export.BaseController;
import com.cisdi.pms.job.history.model.DataObj;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmInfoExportController
 * @package com.cisdi.pms.job.history
 * @description
 * @date 2023/11/8
 */
@Slf4j
@RestController
@RequestMapping("pmInfo")
public class PmInfoExportController extends BaseController {
    @Autowired
    private JdbcTemplate jdbcTemplate;


    /**
     * 项目库导出
     */
    @SneakyThrows(IOException.class)
    @GetMapping("export")
    public void export(HttpServletResponse response, HttpServletRequest req) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select pm.id,pm.`name`, PRJ_SITUATION as `desc`,gsv.`NAME` as phase,pp.`NAME` as sgUnit,DATE_FORMAT(ACTUAL_START_TIME,'%Y-%m-%d') as startTime from pm_prj pm  " +
                "left join gr_set_value gsv on gsv.id = pm.PROJECT_PHASE_ID  " +
                "left join pm_party pp on pp.id = pm.CONSTRUCTOR_UNIT  " +
                "where TENDER_MODE_ID <> '1640259853484171264';");
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream("template/templateInfo.xlsx");
        AtomicInteger index = new AtomicInteger(1);
        List<DataObj> dataObjs = list.stream().map(p -> {
            String prjId = JdbcMapUtil.getString(p, "id");
            DataObj dataObj = new DataObj();
            dataObj.setNum(index.getAndIncrement());
            dataObj.setName(JdbcMapUtil.getString(p, "name"));
            dataObj.setDesc(JdbcMapUtil.getString(p, "des"));
            dataObj.setPhase(JdbcMapUtil.getString(p, "phase"));
            dataObj.setSgUnit(JdbcMapUtil.getString(p, "sgUnit"));
            dataObj.setStartTime(JdbcMapUtil.getString(p, "startTime"));
            dataObj.setImageProcess(getImageProcess(prjId));

            //投资估算-审批金额
            BigDecimal estimateTotal = BigDecimal.ZERO;
            //投资估算-建安费
            BigDecimal estimateJa = BigDecimal.ZERO;
            //投资估算-采购费
            BigDecimal estimateCg = BigDecimal.ZERO;
            //投资估算-土地费
            BigDecimal estimateTd = BigDecimal.ZERO;

            String sql = "select dt.PM_EXP_TYPE_ID,round(ifnull(dt.AMT,0),2) as amt from PM_INVEST_EST_DTL dt left join PM_INVEST_EST pie on dt.PM_INVEST_EST_ID = pie.id where INVEST_EST_TYPE_ID=? and PM_PRJ_ID=?";
            //估算
            List<Map<String, Object>> gsList = jdbcTemplate.queryForList(sql, "0099799190825099305", prjId);
            Optional<Map<String, Object>> estimateTotalOpt = gsList.stream().filter(q -> "0099799190825099546".equals(JdbcMapUtil.getString(q, "PM_EXP_TYPE_ID"))).findAny();
            if (estimateTotalOpt.isPresent()) {
                estimateTotal = new BigDecimal(JdbcMapUtil.getString(estimateTotalOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> estimateJaOpt = gsList.stream().filter(m -> "0099799190825099548".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (estimateJaOpt.isPresent()) {
                estimateJa = new BigDecimal(JdbcMapUtil.getString(estimateJaOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> estimateCgOpt = gsList.stream().filter(m -> "1628674132377997312".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (estimateCgOpt.isPresent()) {
                estimateCg = new BigDecimal(JdbcMapUtil.getString(estimateCgOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> estimateTdOpt = gsList.stream().filter(m -> "0099799190825099551".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (estimateTdOpt.isPresent()) {
                estimateTd = new BigDecimal(JdbcMapUtil.getString(estimateTdOpt.get(), "amt"));
            }

            dataObj.setEstimateTotal(String.valueOf(estimateTotal.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setEstimateJa(String.valueOf(estimateJa.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setEstimateCg(String.valueOf(estimateCg.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setEstimateTd(String.valueOf(estimateTd.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));

            //投资概算-审批金额
            BigDecimal budgetTotal = BigDecimal.ZERO;
            //投资概算-建安费
            BigDecimal budgetJa = BigDecimal.ZERO;
            //投资概算-土地费
            BigDecimal budgetTd = BigDecimal.ZERO;

            List<Map<String, Object>> gasList = jdbcTemplate.queryForList(sql, "0099799190825099306", prjId);
            Optional<Map<String, Object>> budgetTotalOpt = gasList.stream().filter(m -> "0099799190825099546".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (budgetTotalOpt.isPresent()) {
                budgetTotal = new BigDecimal(JdbcMapUtil.getString(budgetTotalOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> budgetJaOpt = gasList.stream().filter(m -> "0099799190825099548".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (budgetJaOpt.isPresent()) {
                budgetJa = new BigDecimal(JdbcMapUtil.getString(budgetJaOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> budgetTdOpt = gasList.stream().filter(m -> "0099799190825099551".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (budgetTdOpt.isPresent()) {
                budgetTd = new BigDecimal(JdbcMapUtil.getString(budgetTdOpt.get(), "amt"));
            }

            dataObj.setBudgetTotal(String.valueOf(budgetTotal.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setBudgetJa(String.valueOf(budgetJa.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setBudgetTd(String.valueOf(budgetTd.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));


            //合同加-合同金额
            BigDecimal contactAmt = BigDecimal.ZERO;
            //合同加-建安费
            BigDecimal contactJa = BigDecimal.ZERO;
            //合同加-采购费
            BigDecimal contactCg = BigDecimal.ZERO;
            //合同加-暂估价
            BigDecimal contactZg = BigDecimal.ZERO;
            dataObj.setContactAmt(String.valueOf(contactAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setContactJa(String.valueOf(contactJa.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setContactCg(String.valueOf(contactCg.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setContactZg(String.valueOf(contactZg.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));


            //预算-审批金额
            BigDecimal rateableTotal = BigDecimal.ZERO;
            //预算-建安费
            BigDecimal rateableJa = BigDecimal.ZERO;
            //预算-采购费
            BigDecimal rateableCg = BigDecimal.ZERO;
            //预算-其他
            BigDecimal rateableQt = BigDecimal.ZERO;
            List<Map<String, Object>> ysList = jdbcTemplate.queryForList(sql, "0099799190825099307", prjId);
            Optional<Map<String, Object>> rateableTotalOpt = ysList.stream().filter(m -> "0099799190825099546".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (rateableTotalOpt.isPresent()) {
                rateableTotal = new BigDecimal(JdbcMapUtil.getString(rateableTotalOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> rateableJaOpt = ysList.stream().filter(m -> "0099799190825099548".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (rateableJaOpt.isPresent()) {
                rateableJa = new BigDecimal(JdbcMapUtil.getString(rateableJaOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> rateableCgOpt = ysList.stream().filter(m -> "1628674132377997312".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (rateableCgOpt.isPresent()) {
                rateableCg = new BigDecimal(JdbcMapUtil.getString(rateableCgOpt.get(), "amt"));
            }
            Optional<Map<String, Object>> rateableQtOpt = ysList.stream().filter(m -> "0099799190825099550".equals(JdbcMapUtil.getString(m, "PM_EXP_TYPE_ID"))).findAny();
            if (rateableQtOpt.isPresent()) {
                rateableQt = new BigDecimal(JdbcMapUtil.getString(rateableQtOpt.get(), "amt"));
            }
            dataObj.setRateableTotal(String.valueOf(rateableTotal.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setRateableJa(String.valueOf(rateableJa.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setRateableCg(String.valueOf(rateableCg.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            dataObj.setRateableQt(String.valueOf(rateableQt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));


            BigDecimal jdAmt = BigDecimal.ZERO;
            dataObj.setJdAmt(String.valueOf(jdAmt.divide(new BigDecimal(10000), 2, BigDecimal.ROUND_HALF_UP)));
            return dataObj;
        }).collect(Collectors.toList());

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "application/vnd.ms-excel");
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode("审计报表", "UTF-8"));
        EasyExcel.write(response.getOutputStream())
                .withTemplate(resourceAsStream)
                .sheet()
                .doFill(dataObjs);

    }

    /**
     * 查询形象进度
     *
     * @param projectId
     * @return
     */
    private String getImageProcess(String projectId) {
        String imageProcess = "0";
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select round(ifnull(VISUAL_PROGRESS,0),2) as VISUAL_PROGRESS from PM_PROGRESS_WEEKLY_PRJ_DETAIL where PM_PRJ_ID=?  order by LAST_MODI_DT desc", projectId);
        if (!CollectionUtils.isEmpty(list)) {
            Map<String, Object> mapData = list.get(0);
            imageProcess = JdbcMapUtil.getString(mapData, "VISUAL_PROGRESS");
        }
        return imageProcess;
    }
}
