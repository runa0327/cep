package com.cisdi.pms.job.serviceImpl.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.base.BaseYear;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmConstruction;
import com.cisdi.pms.job.mapper.base.AdRemindLogMapper;
import com.cisdi.pms.job.mapper.base.BaseYearMapper;
import com.cisdi.pms.job.mapper.project.PmPrjMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmConstructionMapper;
import com.cisdi.pms.job.service.base.AdRemindLogService;
import com.cisdi.pms.job.service.weeklyReport.PmConstructionService;
import com.cisdi.pms.job.utils.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PmConstructionServiceImpl implements PmConstructionService {

    @Resource
    private PmConstructionMapper pmConstructionMapper;

    @Resource
    private BaseYearMapper baseYearMapper;

    @Resource
    private AdRemindLogService adRemindLogService;

    @Resource
    private PmPrjMapper pmPrjMapper;

    /**
     * 工程建安需求填报生成 定时任务
     */
    @Override
    public void generateJianAn() {
        String year = DateUtil.getYear(new Date());
        String yearId = baseYearMapper.queryIdByCode(year); // 当前年份id
        if (StringUtils.hasText(yearId)){
            // 获取需要生成任务的项目
            List<PmPrj> prjList = pmPrjMapper.getNeedWeekPrj();
            if (!CollectionUtils.isEmpty(prjList)){
                for (PmPrj pmPrj : prjList) {
                    projectGenerateTask(yearId,pmPrj);
                }
            }
        } else {
            adRemindLogService.insertByMsg("工程建安需求生成时没有匹配到当前年份信息，请维护年份信息！");
        }
    }

    /**
     * 项目生成当年工程建安费用需求
     * @param yearId 当前年份id
     * @param pmPrj 项目信息
     */
    private void projectGenerateTask(String yearId, PmPrj pmPrj) {
        String projectId = pmPrj.getProjectId();
        PmConstruction pmConstruction = pmConstructionMapper.queryByYearIdAndProjectId(yearId,projectId);
        if (pmConstruction == null){
            String fatherId = IdUtil.getSnowflakeNextIdStr();
            String now = DateUtil.getNormalTimeStr(new Date());
            fatherCreate(fatherId,now,yearId,projectId); // 生成数据插入父表
            childCreate(fatherId,now); // 生成数据插入子级月份明细表
        }
    }

    /**
     * 生成数据插入父表
     * @param id id
     * @param now 当前时间
     * @param yearId 年份id
     * @param projectId 项目id
     */
    private void fatherCreate(String id, String now, String yearId, String projectId) {
        PmConstruction pmConstruction = new PmConstruction();
        pmConstruction.setId(id);
        pmConstruction.setVer("1");
        pmConstruction.setTs(now);
        pmConstruction.setCreateBy("0099250247095871681");
        pmConstruction.setLastUpdateDate(now);
        pmConstruction.setLastUpdateBy("0099250247095871681");
        pmConstruction.setStatus("AP");
        pmConstruction.setBaseYearId(yearId);
        pmConstruction.setProjectId(projectId);
        pmConstruction.setYearAmtNeed(0);
        pmConstructionMapper.insertFather(pmConstruction);
    }

    /**
     * 生成数据插入子级月份明细表
     * @param fatherId 父级id
     * @param now 当前时间
     */
    private void childCreate(String fatherId, String now) {
        List<PmConstruction> list = new ArrayList<>();
        BigDecimal zero = new BigDecimal(0);
        for (int i = 1; i <= 12; i++) {
            PmConstruction pmConstruction = new PmConstruction();
            pmConstruction.setId(IdUtil.getSnowflakeNextIdStr());
            pmConstruction.setPmConstructionId(fatherId);
            pmConstruction.setVer("1");
            pmConstruction.setTs(now);
            pmConstruction.setCreateBy("0099250247095871681");
            pmConstruction.setLastUpdateDate(now);
            pmConstruction.setLastUpdateBy("0099250247095871681");
            pmConstruction.setStatus("AP");
            pmConstruction.setMonth(i+"月");
            pmConstruction.setFirstAmt(zero);
            pmConstruction.setCheckAmt(zero);
            pmConstruction.setMonthCheck(0);
            pmConstruction.setPushWeekTask(0);
            list.add(pmConstruction);
        }
        pmConstructionMapper.insertBatchDetail(list);
    }
}
