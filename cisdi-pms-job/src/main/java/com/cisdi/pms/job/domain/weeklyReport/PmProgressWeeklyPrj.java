package com.cisdi.pms.job.domain.weeklyReport;

import com.cisdi.pms.job.domain.BaseDomain;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 形象进度周报
 */
@Data
public class PmProgressWeeklyPrj extends BaseDomain {

    //id
    private String id;

    //周id
    private String weekId;

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

    //状态
    private String status;

    //是否提交 0未提交 1已提交
    private Integer izSubmit;

    // 问题明细
    private List<PmProgressWeeklyPrjProblemDetail> problemDetailList;
}
