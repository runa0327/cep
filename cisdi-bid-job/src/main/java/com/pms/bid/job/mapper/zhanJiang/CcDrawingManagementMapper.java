package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcDrawingManagement;

public interface CcDrawingManagementMapper {

    /**
     * 根据套图号匹配湛江钢铁业主图号查询id
     * @param ttNumber 套图号
     * @return id
     */
    String queryIdByCcSteelOwnerDrawingId(String ttNumber);

    /**
     * 新增图纸管理
     * @param ccDrawingManagement 图纸管理实体
     */
    void create(CcDrawingManagement ccDrawingManagement);

    /**
     * 修改图纸管理
     * @param ccDrawingManagement 图纸管理实体
     */
    void updateConditionById(CcDrawingManagement ccDrawingManagement);
}
