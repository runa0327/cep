package com.cisdi.pms.job.serviceImpl.weeklyReport;

import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrj;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyPrjMapper;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class PmProgressWeeklyPrjServiceImpl implements PmProgressWeeklyPrjService {

    @Resource
    private PmProgressWeeklyPrjMapper pmProgressWeeklyPrjMapper;

    @Override
    public List<PmProgressWeeklyPrj> getWeekPrj(String projectId, String weekId) {
        PmProgressWeeklyPrj pmProgressWeeklyPrj = new PmProgressWeeklyPrj();
        pmProgressWeeklyPrj.setWeekId(weekId);
        pmProgressWeeklyPrj.setProjectId(projectId);
        return pmProgressWeeklyPrjMapper.selectPrjWeek(pmProgressWeeklyPrj);
    }

    /**
     * 形象进度工程周报-赋值写入数据
     * @param weekId 周id
     * @param weekPrjId 本周项目id
     * @param tmp 项目信息
     * @param pmProgressWeekly 周信息
     */
    @Override
    public void createData(String weekId, String weekPrjId, PmPrj tmp, PmProgressWeekly pmProgressWeekly) {
        PmProgressWeeklyPrj pmProgressWeeklyPrj = new PmProgressWeeklyPrj();
        pmProgressWeeklyPrj.setId(weekPrjId);
        pmProgressWeeklyPrj.setVer("1");
        pmProgressWeeklyPrj.setTs(pmProgressWeekly.getTs());
        pmProgressWeeklyPrj.setCrtDt(pmProgressWeekly.getCrtDt());
        pmProgressWeeklyPrj.setCrtUserId(pmProgressWeekly.getCrtUserId());
        pmProgressWeeklyPrj.setLastModiDt(pmProgressWeekly.getLastModiDt());
        pmProgressWeeklyPrj.setLastModiUserId(pmProgressWeekly.getLastModiUserId());
        pmProgressWeeklyPrj.setStatus("AP");
        pmProgressWeeklyPrj.setWeekId(weekId);
        pmProgressWeeklyPrj.setDate(pmProgressWeekly.getDate());
        pmProgressWeeklyPrj.setProjectId(tmp.getProjectId());
        pmProgressWeeklyPrj.setToDate(pmProgressWeekly.getToDate());
        pmProgressWeeklyPrj.setFromDate(pmProgressWeekly.getFromDate());
        pmProgressWeeklyPrj.setWeatherStart(tmp.getIzStart());
        pmProgressWeeklyPrj.setIzWrite(0);
        pmProgressWeeklyPrj.setWeatherComplete(tmp.getIzEnd());
        pmProgressWeeklyPrj.setIzSubmit(0);
        pmProgressWeeklyPrjMapper.insertData(pmProgressWeeklyPrj);
    }
}
