package com.cisdi.ext.model.view.weekReport;

import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.view.file.BaseFileView;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 形象进度工程周报填写记录
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WeekMessage extends BasePageEntity {

    //id
    private String id;

    //项目id
    private String projectId;

    //项目名称
    private String projectName;

    //填报时间
    private String writeDate;
    private String writeDateMin;
    private String writeDateMax;

    //是否符合开工条件 1符合0不符合2全部
    private Integer weatherStart;

    //是否竣工 1已竣工0未竣工2全部
    private Integer weatherCompleted;

    //整体形象进度 - 整体形象进度
    private BigDecimal progress;

    private BigDecimal progressMin;

    private BigDecimal progressMax;

    //累计形象进度说明 - 累计详细进度/问题说明
    private String progressDescribe;

    //本周项目进展 - 本周工作进展
    private String progressWeek;

    //备注说明
    private String progressRemark;

    //形象进度影像
    private String fileId;
    private List<BaseFileView> fileList;

    //数据显示级别 old new
    private String dataType;

    //进度周报-周信息
    private String weekId;

    //进度周报-周项目信息
    private String weekPrjId;

    //类型 0=开工条件按钮 1=竣工按钮
    private Integer buttonType;

    //状态 0关闭 1开启
    private Integer buttonStatus;

    //是否填写 0未填写1已填写
    private Integer izWrite;

    //记录人
    private String recordById;

    private String recordByName;

    //项目负责人
    private String manageUserId;

    private String manageUserName;

    // 排序字段
    private List<SortBean> sort;

    // 航拍图
    private String aerialImgId;
    private List<BaseFileView> aerialImg;

    // 问题明细
    private List<PmProgressWeeklyPrjProblemDetailView> problemDetailList;

    // 项目问题推进类型
    private String prjPushProblemTypeId;
    private String prjPushProblemTypeName;
}
