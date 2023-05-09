package com.cisdi.pms.job.domain.weeklyReport;

import lombok.Data;

import java.util.Date;

@Data
public class PmProgressWeeklyPrj {

    //id
    private String id;

    //填报时间
    private String date;

    //项目id
    private String projectId;

    //项目名称
    private String projectName;

    //是否符合开工条件 1符合0不合格
    private Integer weatherStart;

    //是否竣工 1已竣工0未竣工
    private Integer weatherComplete;

    //是否填写 1已填写0未填写
    private Integer izWrite;

    //记录填报人
    private String recordBy;
    private String recordName;

    //开始时间
    private Date fromDate;

    //结束日期
    private Date toDate;
}
