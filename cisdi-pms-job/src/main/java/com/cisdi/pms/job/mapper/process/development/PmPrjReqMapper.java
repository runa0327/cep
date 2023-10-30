package com.cisdi.pms.job.mapper.process.development;

import com.cisdi.pms.job.domain.process.development.PmPrjReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmPrjReqMapper {

    /**
     * 查询内部单位管理为空的流程数据
     * @param id id
     * @return 查询结果
     */
    List<PmPrjReq> queryCompanyIsNull(@Param("id") String id);
}
