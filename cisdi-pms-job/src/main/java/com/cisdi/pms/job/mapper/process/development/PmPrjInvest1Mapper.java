package com.cisdi.pms.job.mapper.process.development;

import com.cisdi.pms.job.domain.process.development.PmPrjInvest1;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PmPrjInvest1Mapper {

    /**
     * 查询内部单位管理为空的流程数据
     * @param id id
     * @return 查询结果
     */
    List<PmPrjInvest1> queryCompanyIsNull(@Param("id") String id);
}
