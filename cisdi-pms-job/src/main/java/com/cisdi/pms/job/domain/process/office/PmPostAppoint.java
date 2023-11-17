package com.cisdi.pms.job.domain.process.office;

import com.cisdi.pms.job.domain.base.BaseCommon;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 岗位指派
 */
@Data
public class PmPostAppoint extends BaseCommon {

    // id
    private String id;

    // 项目id
    private String projectId;

    // 资金来源
    private String investSourceId;

    // 总投资/资金总额
    private BigDecimal projectTotalInvest;

    // 项目类型
    private String projectTypeId;

    // 启动日期
    private String startDate;

    // 建设单位/业主单位
    private String customerUnitId;

    // 招标模式
    private String tenderModeId;

    // 项目概况/项目简介
    private String projectSituation;

    // 启动说明
    private String startRemark;

    // 启动依据
    private String startFile;

    // 前期报建岗人员
    private String earlyConstructionPostUserId;

    // 土地管理岗人员
    private String landManagePostUserId;

    // 管线迁改岗人员
    private String pipeChangePostUserId;

    // 计划运营岗人员
    private String planOperatePostUserId;

    // 前期设备岗人员
    private String earlyEquipPostUserId;

    // 成本管理岗人员
    private String costManagePostUserId;

    // 合约管理岗人员
    private String contractManagePostUserId;

    // 设备成本岗人员
    private String equipCostPostUserId;

    // 采购管理岗人员
    private String buyManagePostUserId;

    // 设计管理岗人员
    private String designManagePostUserId;

    // 工程管理岗人员
    private String engineerManagerPostUserId;

    // 征拆对接岗人员
    private String collectAndRemovePostUserId;

    // 财务管理岗
    private String financeManagePostUserId;

    // 部门id
    private String deptId;

    // 流程实例id
    private String wfProcessInstanceId;

    // 接口调用详情表id
    private String interfaceId;
}
