package com.cisdi.ext.model.view.weekReport;

import lombok.Data;

/**
 * 形象进度周报-项目问题明细
 */
@Data
public class PmProgressWeeklyPrjProblemDetailView {

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

    // 处理人id
    private String handleUserIds;

    // 处理人名称
    private String handleUserName;
}
