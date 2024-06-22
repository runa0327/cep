package com.pms.bid.job.domain.zhanJiang;

import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 工程量填报
 */
@Data
public class CcEngineeringQuantity extends BaseDomain {

    /**
     * id
     */
    private String id;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 单元工程id
     */
    private String prjStructNodeId;

    /**
     * 单元工程名称
     */
    private String prjStructNodeName;

    /**
     * 填报类型id
     */
    private String engineeringTypeId;

    /**
     * 工程量类型id
     */
    private String engineeringQuantityTypeId;

    /**
     * 工程量类型名称
     */
    private String engineeringQuantityTypeName;

    /**
     * 结构数量
     */
    private BigDecimal structureCount;

    /**
     * 计量单位类型id
     */
    private String uomTypeId;

    /**
     * 计量单位类型名称
     */
    private String uomTypeName;

    /**
     * 单位重量
     */
    private BigDecimal unitWeight;

    /**
     * 总量
     */
    private BigDecimal totalWeight;

    /**
     * 采购合同id
     */
    private String poId;
}
