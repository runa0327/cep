package com.cisdi.pms.job.serviceImpl.base;

import com.cisdi.pms.job.domain.base.AdUser;
import com.cisdi.pms.job.domain.process.WfProcessInstance;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.mapper.base.AdUserMapper;
import com.cisdi.pms.job.mapper.base.SystemMapper;
import com.cisdi.pms.job.mapper.process.WfProcessInstanceMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyMapper;
import com.cisdi.pms.job.service.base.SystemService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Resource
    private PmPrjMapper pmPrjMapper;

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
        List<WfProcessInstance> weekProcessInstanceNums = wfProcessInstanceMapper.queryTimeFrameNewInstanceNums(startDate,endDate); // 查询时间范围内新增流程详情
        int needWriteNums = pmProgressWeeklyMapper.queryWeekNeedWritePrjNums(weekId); // 获取周期范围内应填报周报数量
        int writeNums = pmProgressWeeklyMapper.queryWeekWritePrjNums(weekId); // 获取周期范围内实际填报周报数量

        List<String> chargeUser = adUserMapper.queryChargeUser(); // 获取系统分管领导
        List<AdUser> chargeUserLogin = adUserMapper.queryChargeUserLoginNums(chargeUser,startDate,endDate); // 获取分管领导时间范围内登录次数
        List<WfProcessInstance> chargeUserCheckNums = wfProcessInstanceMapper.queryChargeUserCheckNums(chargeUser,startDate,endDate); // 获取分管领导时间范围内审批次数

        List<PmPrj> prjList = pmPrjMapper.queryAllSystemProject(); // 获取所有系统项目
        List<String> projectIdList = prjList.stream().map(PmPrj::getProjectId).collect(Collectors.toList());
        List<String> zaiJianPrj = prjList.stream().filter(p->"0099799190825080706".equals(p.getProjectPhaseTypeId()) || "1673502467645648896".equals(p.getProjectPhaseTypeId())).map(PmPrj::getProjectPhaseTypeId).collect(Collectors.toList()); // 在建项目数 只取项目状态为 前期 施工中
        int timeFrameNewPrjNums = pmPrjMapper.queryTimeFrameNewProjectNums(startDate,endDate); // 获取时间范围内新增的项目数
        int pmPrjReqNums = pmPrjMapper.queryReqNums(); // 获取所有立项完成数量 含历史导入数据
        int invest1Nums = pmPrjMapper.queryInvest1Nums(); // 获取所有可研完成数量 含历史导入数据
        int invest2Nums = pmPrjMapper.queryInvest2Nums(); // 获取所有初设概算完成数量 含历史导入数据
        int invest3Nums = pmPrjMapper.queryInvest3Nums(); // 获取所有预算财评完成数量 含历史导入数据
        String projectAllTotalInvest = pmPrjMapper.queryTotalAmtByProjectIds(projectIdList); // 查询所有系统项目项目投资总额


        return map;
    }
}
