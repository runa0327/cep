package com.cisdi.pms.job.domain.process;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class PoOrderReq {
    // id
    private String id;
    // 合同名称
    private String name;
    // 项目id
    private String projectId;
    // 项目名称
    private String projectName;
    // 建设单位/业主单位
    private String customerUnitId;
    //公司名称
    private String companyName;
    // 公司id
    private String companyId;
    // 附件信息
    private String fileId;
    //是否标准模板 0099799190825080669 = 是，0099799190825080670=否
    private String isModel;
    //流程实例id
    private String processInstanceId;
    //创建人
    private String createBy;
    //更新字段及对应文件
    private List<Map<String,String>> colMap;

    // 合同类型
    private String contractCategoryId;
}
