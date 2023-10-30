package com.cisdi.pms.job.serviceImpl.process.development;

import com.cisdi.pms.job.domain.process.development.PmPrjReq;
import com.cisdi.pms.job.mapper.common.SQLMapper;
import com.cisdi.pms.job.mapper.process.development.PmPrjReqMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.process.development.PmPrjReqService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmPrjReqServiceImpl implements PmPrjReqService {

    @Resource
    private PmPrjReqMapper pmPrjReqMapper;

    @Resource
    private PmPrjMapper pmPrjMapper;

    @Resource
    private SQLMapper sqlMapper;

    /**
     * 更新流程中内部管理单位为空的数据
     *
     * @param id id
     */
    @Override
    public void updateCompany(String id) {
        List<PmPrjReq> list = pmPrjReqMapper.queryCompanyIsNull(id);
        if (!CollectionUtils.isEmpty(list)){
            for (PmPrjReq tmp : list) {
                String companyId = pmPrjMapper.queryCompanyById(tmp.getProjectId());
                if (StringUtils.hasText(companyId)){
                    String sql = "update PM_PRJ_REQ set COMPANY_ID = '" + companyId + "' where id = '" + tmp.getId() + "'";
                    sqlMapper.executeSql(sql);
                }
            }
        }
    }
}
