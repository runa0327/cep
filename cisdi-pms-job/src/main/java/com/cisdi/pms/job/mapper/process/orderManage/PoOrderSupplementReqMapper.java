package com.cisdi.pms.job.mapper.process.orderManage;

import com.cisdi.pms.job.domain.process.orderManage.PoOrderSupplementReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoOrderSupplementReqMapper {

    /**
     * 查询所有已审批通过的流程信息
     * @param poOrderSupplementReq 补充协议实体
     * @return 查询结果
     */
    List<PoOrderSupplementReq> queryAllAPData(PoOrderSupplementReq poOrderSupplementReq);

    /**
     * 根据补充协议id删除补充协议项目明细表数据
     * @param poOrderSupplementReqId 补充协议id
     */
    void deletePrjDetailById(@Param("poOrderSupplementReqId") String poOrderSupplementReqId);

    /**
     * 补充协议-项目明细-新增数据
     * @param poOrderSupplementReq 新增数据详细信息
     */
    void insertPrjDetail(PoOrderSupplementReq poOrderSupplementReq);
}
