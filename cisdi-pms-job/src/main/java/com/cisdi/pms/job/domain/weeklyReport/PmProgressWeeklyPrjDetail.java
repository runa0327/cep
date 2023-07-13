package com.cisdi.pms.job.domain.weeklyReport;

import com.cisdi.pms.job.domain.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class PmProgressWeeklyPrjDetail extends BasePageEntity {
    //id
    public String id;
    //项目id
    public String projectId;
    //项目名称
    public String projectName;
    //填报时间
    public String writeDate;
    public String writeDateMin;
    public String writeDateMax;
    //是否符合开工条件 1符合0不符合2全部
    public Integer weatherStart;
    //是否竣工 1已竣工0未竣工2全部
    public Integer weatherCompleted;
    //整体形象进度
    public BigDecimal progress;
    public BigDecimal progressMin;
    public BigDecimal progressMax;
    public String progressStr;
    //累计形象进度说明
    public String progressDescribe;
    //本周项目进展
    public String progressWeek;
    //备注说明
    public String progressRemark;
    //形象进度影像
    public String fileId;
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
    //记录人
    public String recordById;
    public String recordByName;
    //项目负责人
    public String manageUserId;
    public String manageUserName;
    //导出类型 1按项目维度导出 0按周维度导出
    public Integer exportType;
    //是否提交 0未提交1已提交
    public Integer izSubmit;

    //状态
    private String status;

    //版本
    private String ver;

    //时间戳
    private String ts;

    //创建时间
    private String crtDt;

    //创建人
    private String crtUserId;

    //上次修改时间
    private String lastModiDt;

    //上次修改人
    private String lastModiUserId;

    //开始时间
    private Date fromDate;

    //结束日期
    private Date toDate;

}
