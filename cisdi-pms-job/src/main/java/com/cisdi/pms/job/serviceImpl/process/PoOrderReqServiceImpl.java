package com.cisdi.pms.job.serviceImpl.process;

import com.cisdi.pms.job.domain.process.PoOrderReq;
import com.cisdi.pms.job.mapper.process.PoOrderReqMapper;
import com.cisdi.pms.job.service.process.PoOrderReqService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PoOrderReqServiceImpl implements PoOrderReqService {

    @Resource
    private PoOrderReqMapper poOrderReqMapper;

    /**
     * 合同签订历史数据导入-合同签订公司及合同类型写入数据表
     */
    @Override
    public void poOrderImportHistoryCompanyHandle() {
        List<PoOrderReq> list = poOrderReqMapper.queryHistoryImport();
        if (!CollectionUtils.isEmpty(list)){
            for (PoOrderReq tmp : list) {
                poOrderReqMapper.updatePoOrderCustomerUnit(tmp.getId(),tmp.getCustomerUnitId(),tmp.getContractCategoryId());
            }

        }
    }
}
