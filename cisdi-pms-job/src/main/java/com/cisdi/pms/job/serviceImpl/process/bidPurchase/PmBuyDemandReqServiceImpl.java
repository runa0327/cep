package com.cisdi.pms.job.serviceImpl.process.bidPurchase;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.process.bidPurchase.PmBuyDemandReq;
import com.cisdi.pms.job.mapper.process.bidPurchase.PmBuyDemandReqMapper;
import com.cisdi.pms.job.service.process.bidPurchase.PmBuyDemandReqService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmBuyDemandReqServiceImpl implements PmBuyDemandReqService {

    @Resource
    private PmBuyDemandReqMapper pmBuyDemandReqMapper;

    /**
     * 采购需求审批-历史审批数据写入项目明细表
     * @param pmBuyDemandReq 采购需求审批-实体
     */
    @Override
    public void buyDemandHistoryPrjToDetail(PmBuyDemandReq pmBuyDemandReq) {
        List<PmBuyDemandReq> list = pmBuyDemandReqMapper.queryAllAPData(pmBuyDemandReq);
        if (!CollectionUtils.isEmpty(list)){
            for (PmBuyDemandReq tmp : list) {
                String pmBuyDemandReqId = tmp.getPmBuyDemandReqId();
                String projectIds = tmp.getProjectIds();
                if (StringUtils.hasText(projectIds)){
                    pmBuyDemandReqMapper.deletePrjDetailById(pmBuyDemandReqId);
                    String[] projectArr = projectIds.split(",");
                    for (String projectId : projectArr) {
                        tmp.setProjectId(projectId);
                        tmp.setId(IdUtil.getSnowflakeNextIdStr());
                        pmBuyDemandReqMapper.insertPrjDetail(tmp);
                    }
                }
            }
        }
    }
}
