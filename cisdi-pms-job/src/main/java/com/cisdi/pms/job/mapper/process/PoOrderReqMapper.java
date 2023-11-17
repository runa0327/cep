package com.cisdi.pms.job.mapper.process;

import com.cisdi.pms.job.domain.process.orderManage.PoOrderReq;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PoOrderReqMapper {

    /**
     * 根据合同id数组查询合同名称
     * @param contractIdArr 合同id
     * @return 合同名称
     */
    String getContractNameByIdArr(@Param("list") String[] contractIdArr);

    /**
     * 查询所有历史导入数据
     * @return 查询结果
     */
    List<PoOrderReq> queryHistoryImport();

    /**
     * 修改合同数据表合同签订公司、合同类型
     * @param poOrderReqId 流程id
     * @param customerUnitId 业主单位
     * @param contractCategoryId 合同类型
     */
    void updatePoOrderCustomerUnit(@Param("poOrderReqId") String poOrderReqId, @Param("customerUnitId")String customerUnitId, @Param("contractCategoryId")String contractCategoryId);

    /**
     * 查询所有合同签订数量
     * @return 合同数量
     */
    int queryOrderNums();

    /**
     * 查询时间范围内新签订的合同总金额-刨除历史数据导入
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return 合同总金额
     */
    List<PoOrderReq> queryTimeFrameNewOrderAmt(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
