package com.cisdi.pms.job.domain.weeklyReport;

import com.cisdi.pms.job.domain.BaseDomain;
import lombok.Data;

/**
 * 形象进度周报-项目问题明细
 */
@Data
public class PmProgressWeeklyPrjProblemDetail extends BaseDomain {

    // id
    private String id;

    //形象进度周报id
    private String pmProgressWeeklyPrjId;

    //问题类型id
    private String prjPushProblemTypeId;

    // 问题类型名称
    private String prjPushProblemTypeName;

    // 问题说明
    private String problemDescribe;

    // 项目id
    private String projectId;

    // 周id
    private String weekId;
}
