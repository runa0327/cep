package com.cisdi.pms.job.serviceImpl.process.development;

import com.cisdi.pms.job.domain.process.development.PmPrjInvest1;
import com.cisdi.pms.job.mapper.common.SQLMapper;
import com.cisdi.pms.job.mapper.process.development.PmPrjInvest1Mapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.service.process.development.PmPrjInvest1Service;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmPrjInvest1ServiceImpl implements PmPrjInvest1Service {

    @Resource
    private PmPrjInvest1Mapper pmPrjInvest1Mapper;

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
        List<PmPrjInvest1> list = pmPrjInvest1Mapper.queryCompanyIsNull(id);
        if (!CollectionUtils.isEmpty(list)){
            for (PmPrjInvest1 tmp : list) {
                String companyId = pmPrjMapper.queryCompanyById(tmp.getProjectId());
                if (StringUtils.hasText(companyId)){
                    String sql = "update PM_PRJ_INVEST1 set COMPANY_ID = '" + companyId + "' where id = '" + tmp.getId() + "'";
                    sqlMapper.executeSql(sql);
                }
            }
        }
    }
}
