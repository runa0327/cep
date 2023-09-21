package com.cisdi.pms.job.serviceImpl.base;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.base.BaseYear;
import com.cisdi.pms.job.mapper.base.BaseYearMapper;
import com.cisdi.pms.job.service.base.BaseYearService;
import com.cisdi.pms.job.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class BaseYearServiceImpl implements BaseYearService {

    @Resource
    private BaseYearMapper baseYearMapper;

    /**
     * 自动生成年份信息
     */
    @Override
    public void generateYear() {
        String year = DateUtil.getYear(new Date());
        BaseYear baseYear = baseYearMapper.queryByYear(year);
        if (baseYear == null){
            insert(year);
        }
    }

    /**
     * 根据年份
     * @param year
     */
    public void insert(String year) {
        String now = DateUtil.getNormalTimeStr(new Date());
        BaseYear baseYear = new BaseYear();
        baseYear.setId(IdUtil.getSnowflakeNextIdStr());
        baseYear.setCode(year);
        baseYear.setName(year);
        baseYear.setVer("1");
        baseYear.setCreateBy("0099250247095871681");
        baseYear.setCreateDate(now);
        baseYear.setLastUpdateBy("0099250247095871681");
        baseYear.setLastUpdateDate(now);
        baseYear.setStatus("AP");
        baseYear.setTs(now);
        baseYearMapper.insert(baseYear);
    }
}
