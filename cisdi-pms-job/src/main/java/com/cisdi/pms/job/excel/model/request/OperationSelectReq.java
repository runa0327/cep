package com.cisdi.pms.job.excel.model.request;

import lombok.Data;

import java.util.List;

/**
 * 计划运营库查询条件
 * @author dlt
 * @date 2023/5/25 周四
 */
@Data
public class OperationSelectReq {
    //项目ids
    private List<String> prjIds;
    //总投资开始金额
    private String totalInvestStart;
    //总投资结束金额
    private String totalInvestEnd;
    //前期岗人员ids
    private List<String> earlyUserIds;
    //重点项目类型ids
    private List<String> keyProjectTypeIds;
    //项目管理模式
    private List<String> prjManageModeIds;
    //项目标签
    private List<String> prjTagIds;
    //项目状态
    private List<String> prjPhaseIds;
    //建设地点
    private List<String> locationIds;
    //项目类型
    private List<String> prjTypeIds;
    //页码
    private Integer pageIndex;
    //页面大小
    private Integer pageSize;
    //是否关注
    private Boolean isFollow;
}
