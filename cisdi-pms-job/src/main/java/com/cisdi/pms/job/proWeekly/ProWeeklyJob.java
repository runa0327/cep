package com.cisdi.pms.job.proWeekly;

import com.cisdi.pms.job.enums.WeeklyEnum;
import com.cisdi.pms.job.utils.Util;
import com.cisdi.pms.job.utils.WeeklyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProWeeklyJob
 * @package com.cisdi.pms.job.proWeekly
 * @description
 * @date 2022/10/18
 */
@Component
@Slf4j
public class ProWeeklyJob {

    @Autowired
    JdbcTemplate jdbcTemplate;


    @Resource(name = "taskExecutor")
    private ThreadPoolTaskExecutor taskExecutor;

    /**
     * 定时产生周报(每周五执行)
     */
//    @Scheduled(fixedDelayString = "${spring.scheduled.fixedDelayString}")
//    @Scheduled(cron = "* * * * * 5 ")
    @Scheduled(cron = "* * * * * 2,5 ")
    @Async("taskExecutor")
    public void invokeCreateWeekly() {
        // 查询周报规则
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select PROJECT_TYPE_ID,PM_PARTY_ROLE_ID,REPOERT_TYPE_ID,PROJECT_PHASE_ID,PM_PRJ_ID, " +
                "gsv.code as REPORT_TYPE_ID from REPORT_RULES pr\n" +
                "left join gr_set_value gsv on pr.REPORT_TYPE_ID = gsv.id\n" +
                "left join gr_set gs on gs.id = gsv.GR_SET_ID and gs.code='REPORT_TYPE'\n" +
                " where pr.`STATUS`='ap'");

        list.forEach(item -> {
            String reportType = String.valueOf(item.get("REPORT_TYPE_ID"));
            if (WeeklyEnum.weekly_report.getCode().equals(reportType)) {
                // 周报 REPORT
                String reportId = Util.insertData(jdbcTemplate, "REPORT");
                Map<String, String> dateMap = WeeklyUtils.weekBeginningAndEnding(WeeklyUtils.addDays(new Date(), 3));
                jdbcTemplate.update("update REPORT set PM_PRJ_ID=? ,REPOERT_TYPE_ID=?,TIME_FROM=?,TIME_TERMINATION=?,FILING_STATUS='0' where ID=?",
                        item.get("PM_PRJ_ID"), item.get("REPOERT_TYPE_ID"), dateMap.get("begin"), dateMap.get("end"), reportId);
            }
//            else if (WeeklyEnum.montly_report.getCode().equals(reportType)) {
//                //月报
//            } else if (WeeklyEnum.quarterly_report.getCode().equals(reportType)) {
//                //季报
//            }

        });
    }

}
