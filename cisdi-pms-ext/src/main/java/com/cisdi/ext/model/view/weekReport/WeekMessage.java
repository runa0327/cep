package com.cisdi.ext.model.view.weekReport;

import com.cisdi.ext.model.view.file.BaseFileView;

import java.util.List;

/**
 * 形象进度工程周报填写记录
 */
public class WeekMessage {

    //id
    public String id;
    //项目id
    public String projectId;
    //项目名称
    public String projectName;
    //填报周期
    public String writeDate;
    //是否符合开工条件 1符合0不符合
    public Integer weatherStart;
    //是否竣工 1已竣工0未竣工
    public Integer weatherCompleted;
    //整体形象进度
    public Integer progress;
    //累计形象进度说明
    public String progressDescribe;
    //本周项目进展
    public String progressWeek;
    //备注说明
    public String progressRemark;
    //形象进度影像
    public String fileId;
    public List<BaseFileView> fileList;
    //数据显示级别 old new
    public String dataType;
    //进度周报-周信息
    public String weekId;
    //进度周报-周项目信息
    public String weekPrjId;
    //类型 0=开工条件按钮 1=竣工按钮
    public Integer buttonType;
    //状态 0关闭 1开启
    public Integer buttonStatus;
    //是否填写 0未填写1已填写
    public Integer izWrite;
}
