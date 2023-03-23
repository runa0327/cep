package com.cisdi.pms.job.weeklyReport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("weeklyReport")
public class WeeklyReportController {

    @Autowired
    WeeklyReportService weeklyReportService;

    /**
     * http://localhost:11115/cisdi-pms-job/weeklyReport/execute
     */
    @GetMapping("execute")
    public void execute() {
        weeklyReportService.execute();
    }
}
