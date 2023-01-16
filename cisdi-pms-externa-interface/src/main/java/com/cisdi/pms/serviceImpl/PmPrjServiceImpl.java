package com.cisdi.pms.serviceImpl;

import com.cisdi.pms.api.PmPrj;
import com.cisdi.pms.service.PmPrjService;
import com.cisdi.pms.util.StringUtilsNew;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PmPrjServiceImpl implements PmPrjService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<PmPrj> getBaseProject(PmPrj pmPrj) {
        StringBuilder sb = new StringBuilder("select a.id,a.name,a.CRT_DT,a.CUSTOMER_UNIT,b.name as constructionUnitName,a.ESTIMATE_AMT,a.BUDGET_AMT,a.SETTLEMENT_AMT from pm_prj a " +
                "left join PM_PARTY b on a.CUSTOMER_UNIT = b.id  where a.status = 'ap' and b.status = 'ap' ");
        if (!SharedUtil.isEmptyString(pmPrj.getProjectId())){
            sb.append("and a.id = '"+pmPrj.getProjectId()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getProjectName())){
            sb.append("and a.name like ('%"+pmPrj.getProjectName()+"%') ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getConstructionUnitId())){
            sb.append("and a.CUSTOMER_UNIT = '"+pmPrj.getConstructionUnitId()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getConstructionUnitName())){
            sb.append("and b.name = '%"+pmPrj.getConstructionUnitName()+"%') ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getCreateTime())){
            sb.append("and a.CRT_DT like ('%"+pmPrj.getCreateTime()+"%') ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getCreateTimeMin())){
            sb.append("and a.CRT_DT >= '"+pmPrj.getCreateTimeMin()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getCreateTimeMax())){
            sb.append("and a.CRT_DT <= '"+pmPrj.getCreateTimeMax()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getEstimateAmt())){
            sb.append("and a.BUDGET_AMT = '"+pmPrj.getEstimateAmt()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getSettlementAmt())){
            sb.append("and a.SETTLEMENT_AMT = '"+pmPrj.getSettlementAmt()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getEstimateAmt())){
            sb.append("and a.ESTIMATE_AMT = '"+pmPrj.getEstimateAmt()+"' ");
        }

        sb.append("order by a.CRT_DT asc ");

        // 起始条数
        int start = (pmPrj.pageIndex - 1) * pmPrj.pageSize;
        String limit = "limit " + start + "," + pmPrj.pageSize;
        sb.append(limit);


        List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
        List<PmPrj> pmPrjList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                PmPrj pmPrj1 = new PmPrj();
                pmPrj1.setProjectId(JdbcMapUtil.getString(tmp,"id"));
                pmPrj1.setProjectName(JdbcMapUtil.getString(tmp,"name"));
                pmPrj1.setCreateTime(StringUtilsNew.replaceByCode(JdbcMapUtil.getString(tmp,"CRT_DT"),"T"," "));
                pmPrj1.setConstructionUnitId(JdbcMapUtil.getString(tmp,"CUSTOMER_UNIT"));
                pmPrj1.setConstructionUnitName(JdbcMapUtil.getString(tmp,"constructionUnitName"));
                pmPrj1.setBudgetAmt(JdbcMapUtil.getString(tmp,"BUDGET_AMT"));
                pmPrj1.setSettlementAmt(JdbcMapUtil.getString(tmp,"SETTLEMENT_AMT"));
                pmPrj1.setEstimateAmt(JdbcMapUtil.getString(tmp,"ESTIMATE_AMT"));
                pmPrjList.add(pmPrj1);
            }
        }
        return pmPrjList;
    }

    @Override
    public int getBaseProjectCount(PmPrj pmPrj) {
        StringBuilder sb = new StringBuilder("select count(*) as num from pm_prj a " +
                "left join PM_PARTY b on a.CUSTOMER_UNIT = b.id  where a.status = 'ap' and b.status = 'ap' ");
        if (!SharedUtil.isEmptyString(pmPrj.getProjectId())){
            sb.append("and a.id = '"+pmPrj.getProjectId()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getProjectName())){
            sb.append("and a.name like ('%"+pmPrj.getProjectName()+"%') ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getConstructionUnitId())){
            sb.append("and a.CUSTOMER_UNIT = '"+pmPrj.getConstructionUnitId()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getConstructionUnitName())){
            sb.append("and b.name = '%"+pmPrj.getConstructionUnitName()+"%') ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getCreateTime())){
            sb.append("and a.CRT_DT like ('%"+pmPrj.getCreateTime()+"%') ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getCreateTimeMin())){
            sb.append("and a.CRT_DT >= '"+pmPrj.getCreateTimeMin()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getCreateTimeMax())){
            sb.append("and a.CRT_DT <= '"+pmPrj.getCreateTimeMax()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getEstimateAmt())){
            sb.append("and a.BUDGET_AMT = '"+pmPrj.getEstimateAmt()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getSettlementAmt())){
            sb.append("and a.SETTLEMENT_AMT = '"+pmPrj.getSettlementAmt()+"' ");
        }
        if (!SharedUtil.isEmptyString(pmPrj.getEstimateAmt())){
            sb.append("and a.ESTIMATE_AMT = '"+pmPrj.getEstimateAmt()+"' ");
        }
        List<Map<String,Object>> list = jdbcTemplate.queryForList(sb.toString());
        return Integer.valueOf(JdbcMapUtil.getString(list.get(0),"num"));
    }
}
