package com.cisdi.ext.model.view.weekReport;

import com.cisdi.ext.model.BasePageEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 工程建安费用需求填报模块实体
 */
@Data
public class PmConstructionLogView extends BasePageEntity {

    // id
    private String id;

    // 修改内容
    private String content;

    // 项目id
    private String projectId;

    // 年-id
    private String baseYearId;
}
