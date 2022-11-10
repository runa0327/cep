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
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
//    @Scheduled(cron = "0 */1 * * * ?")//-- 每分钟执行一次
    @Scheduled(cron = "0 0 1 ? * L") //每周星期六凌晨1点实行一次
    @Async("taskExecutor")
    public void invokeCreateWeekly() {
        // 查询周报规则
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select REPOERT_TYPE_ID,PM_PRJ_ID,REPORT_USER,REPORT_TYPE_ID, \n" +
                "gsv.code as REPORT_TYPE from REPORT_RULES pr\n" +
                "left join gr_set_value gsv on pr.REPORT_TYPE_ID = gsv.id\n" +
                "left join gr_set gs on gs.id = gsv.GR_SET_ID and gs.code='REPORT_TYPE'\n" +
                "where pr.`STATUS`='ap'");


        List<Map<String, Object>> statusList = jdbcTemplate.queryForList("select gsv.* from gr_set_value gsv\n" +
                "left join gr_set gs on gs.id = gsv.GR_SET_ID \n" +
                "where  gs.code='REPORT_TYPE'");
        String status = null;
        Optional<Map<String, Object>> stringObjectMap = statusList.stream().filter(p -> "NOT_FILING".equals(String.valueOf(p.get("code")))).findAny();
        if (stringObjectMap.isPresent()) {
            status = String.valueOf(stringObjectMap.get().get("ID"));
        }

        int currentYear = LocalDate.now().getYear();
        Map<String, String> dateMap = WeeklyUtils.weekBeginningAndEnding(WeeklyUtils.addDays(new Date(), 3));
        //今天是第几周
        int currentWeek = WeeklyUtils.getWeekCount(new Date());
        for (int i = 0; i < list.size(); i++) {
            Map<String, Object> item = list.get(i);
            String reportType = String.valueOf(item.get("REPORT_TYPE"));
            if (WeeklyEnum.weekly_report.getCode().equals(reportType)) {
                //判断是否已经产生了周报
                List<Map<String, Object>> reportList = jdbcTemplate.queryForList("select * from report where PM_PRJ_ID=? and `year`=? and WEEKS=? and REPOERT_TYPE_ID=?", item.get("PM_PRJ_ID"), currentYear, currentWeek + 1, item.get("REPOERT_TYPE_ID"));
                if (CollectionUtils.isEmpty(reportList)) {
                    // 周报 REPORT
                    String reportId = Util.insertData(jdbcTemplate, "REPORT");
                    jdbcTemplate.update("update REPORT set PM_PRJ_ID=? ,REPOERT_TYPE_ID=?,TIME_FROM=?,TIME_TERMINATION=?,FILING_STATUS=?,`year`=?,WEEKS=? where ID=?",
                            item.get("PM_PRJ_ID"), item.get("REPOERT_TYPE_ID"), dateMap.get("begin"), dateMap.get("end"), status, currentYear, currentWeek + 1, reportId);
                    String[] users = String.valueOf(item.get("REPORT_USER")).split(",");
                    for (String user : users) {
                        Map<String, Object> userData = jdbcTemplate.queryForMap("select * from ad_user where id =?", user);
                        String id = Util.insertData(jdbcTemplate, "REPORT_REMINDER_RECORD");
                        jdbcTemplate.update("update REPORT_REMINDER_RECORD REPORT_ID=?,AD_USER_ID=?,MOBILE=?,REMINDER_TIME=?,REMINDER_CONTENT=? where id =?", reportId, user, userData.get("MOBILE"), new Date(), "", id);
                    }
                }
            }
        }

    }


}
