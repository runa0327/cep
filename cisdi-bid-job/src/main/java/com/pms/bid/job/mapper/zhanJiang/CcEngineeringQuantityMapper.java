package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcEngineeringQuantity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CcEngineeringQuantityMapper {

    /**
     * 批量新增
     * @param list 数据详情
     */
    void inertBatch(@Param("list") List<CcEngineeringQuantity> list);
}
