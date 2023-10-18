package com.cisdi.pms.job.serviceImpl.process.orderManage;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.process.orderManage.PoOrderSupplementReq;
import com.cisdi.pms.job.mapper.process.orderManage.PoOrderSupplementReqMapper;
import com.cisdi.pms.job.service.process.orderManage.PoOrderSupplementReqService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PoOrderSupplementReqServiceImpl implements PoOrderSupplementReqService {

    @Resource
    private PoOrderSupplementReqMapper poOrderSupplementReqMapper;

    /**
     * 补充协议-历史已批准数据-写入项目明细表
     *
     * @param poOrderSupplementReq 实体信息
     */
    @Override
    public void supplementHistoryPrjToDetail(PoOrderSupplementReq poOrderSupplementReq) {
        List<PoOrderSupplementReq> list = poOrderSupplementReqMapper.queryAllAPData(poOrderSupplementReq);
        if (!CollectionUtils.isEmpty(list)){
            for (PoOrderSupplementReq tmp : list) {
                String poOrderSupplementReqId = tmp.getPoOrderSupplementReqId();
                String projectIds = tmp.getProjectIds();
                if (StringUtils.hasText(projectIds)){
                    poOrderSupplementReqMapper.deletePrjDetailById(poOrderSupplementReqId);
                    String[] projectArr = projectIds.split(",");
                    for (String projectId : projectArr) {
                        tmp.setProjectId(projectId);
                        tmp.setId(IdUtil.getSnowflakeNextIdStr());
                        poOrderSupplementReqMapper.insertPrjDetail(tmp);
                    }
                }
            }
        }
    }
}
