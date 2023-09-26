package com.cisdi.pms.job.serviceImpl.base;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.mapper.base.AdUserMapper;
import com.cisdi.pms.job.mapper.base.SystemMapper;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyMapper;
import com.cisdi.pms.job.service.base.SystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service
public class SystemServiceImpl implements SystemService {

    @Resource
    private SystemMapper systemMapper;

    @Resource
    private PmProgressWeeklyMapper pmProgressWeeklyMapper;

    @Resource
    private AdUserMapper adUserMapper;

    @Resource
    private WfProcessInstanceMapper wfProcessInstanceMapper;

    /**
     * 根据周信息查询系统使用情况
     *
     * @param weekId 周id
     * @return 周内系统使用情况
     */
    @Override
    public Map<String, Object> querySystemUsage(String weekId) {
        Map<String,Object> map = new HashMap<>();
        PmProgressWeekly pmProgressWeekly = pmProgressWeeklyMapper.queryDataById(weekId);
        String startDate = pmProgressWeekly.getStartDate();
        String endDate = pmProgressWeekly.getEndDate();

        int allUserNum = adUserMapper.queryAllUser(); // 查询系统所有人员
        int weekUserLoginNum = adUserMapper.queryWeekUserLoginNums(startDate,endDate); // 查询时间范围内登录系统的用户数
        int loginNums = adUserMapper.queryLoginNums(); // 查询系统上线以来所有登录次数 2022-11-22上线
        int allProcessInstanceNums = wfProcessInstanceMapper.queryAllInstanceNums(); // 查询上线以来所有发起的流程
        int weekProcessInstanceNums = wfProcessInstanceMapper.queryTimeFrameNewInstanceNums(startDate,endDate); // 查询时间范围内新增流程数量




        return map;
    }
}
