package com.cisdi.pms.job.domain.weeklyReport;

import com.cisdi.pms.job.domain.BaseDomain;
import lombok.Data;

import java.util.Date;

@Data
public class PmProgressWeekly extends BaseDomain {

    //id
    private String id;

    //状态
    private String status;

    //记录日期
    private String date;

    //开始日期
    private Date fromDate;

    //结束日期
    private Date toDate;
}
