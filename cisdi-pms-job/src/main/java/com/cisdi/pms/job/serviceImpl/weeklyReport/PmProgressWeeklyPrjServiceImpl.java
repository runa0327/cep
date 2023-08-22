package com.cisdi.pms.job.serviceImpl.weeklyReport;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.base.BaseJobConfig;
import com.cisdi.pms.job.domain.project.PmPrj;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeeklyPrj;
import com.cisdi.pms.job.mapper.base.BaseJobConfigMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyMapper;
import com.cisdi.pms.job.mapper.weeklyReport.PmProgressWeeklyPrjMapper;
import com.cisdi.pms.job.service.project.PmPrjService;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjDetailService;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyPrjService;
import com.cisdi.pms.job.utils.DateUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class PmProgressWeeklyPrjServiceImpl implements PmProgressWeeklyPrjService {

    @Resource
    private PmProgressWeeklyPrjMapper pmProgressWeeklyPrjMapper;

    @Resource
    private BaseJobConfigMapper baseJobConfigMapper;

    @Resource
    private PmProgressWeeklyPrjDetailService pmProgressWeeklyPrjDetailService;

    @Resource
    private PmProgressWeeklyMapper pmProgressWeeklyMapper;

    @Resource
    private PmPrjService pmPrjService;

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
        pmProgressWeeklyPrj.setCreateDate(pmProgressWeekly.getCreateDate());
        pmProgressWeeklyPrj.setCreateUserId(pmProgressWeekly.getCreateUserId());
        pmProgressWeeklyPrj.setLastUpdateDate(pmProgressWeekly.getLastUpdateDate());
        pmProgressWeeklyPrj.setLastUpdateUserId(pmProgressWeekly.getLastUpdateUserId());
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

    /**
     * 形象进度周报定时任务生成
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createProgressWeekly() {
        List<BaseJobConfig> jobList = baseJobConfigMapper.getJobByCode("createProgressWeekly");
        if (CollectionUtils.isEmpty(jobList)){
            log.error("没有找到定时任务编码为【createProgressWeekly】的任务");
        } else {
            String status = jobList.get(0).getStatus();
            if ("AP".equals(status)){
                // 周五作为一周第一天
                Date date = new Date();
                int week = DateUtil.getWeekDay(date);

                Map<String,String> map = DateUtil.getDateMap(week,date);
                autoCreateProgressWeekNew(map.get("startDate"),map.get("endDate"),date);
//                if (week == 5){
//                    String startDate = DateUtil.getTimeStrDay(date);
//                    Date date2 = DateUtil.addDays(date,6);
//                    String endDate = DateUtil.getTimeStrDay(date2);
////                autoCreateProgressWeek(startDate,endDate,date);
//                    autoCreateProgressWeekNew(startDate,endDate,date);
//                } else if ( week != 5 && "1".equals(sysTrue)){
//
////                autoCreateProgressWeek(map.get("startDate"),map.get("endDate"),date);
//                    autoCreateProgressWeekNew(map.get("startDate"),map.get("endDate"),date);
//                }
            }
        }
    }

    /**
     * 开始自动生成形象进度周报
     * startDate 开始时间
     * endDate 结束时间
     * date 当前时间
     */
    private void autoCreateProgressWeekNew(String startDate, String endDate, Date date) {
        String nowDate = DateUtil.getNormalTimeStr(date);
        String weekDate = startDate + "到" + endDate;

        PmProgressWeekly pmProgressWeekly = new PmProgressWeekly();

        //获取周id
        String weekId = pmProgressWeeklyMapper.getWeekIdByDate(startDate,endDate);
        if (SharedUtil.isEmptyString(weekId)){
            weekId = IdUtil.getSnowflakeNextIdStr();
            pmProgressWeekly = PmProgressWeeklyServiceImpl.setWeekValue(weekId,nowDate,weekDate,startDate,endDate,pmProgressWeekly);
            pmProgressWeeklyMapper.createData(pmProgressWeekly);
        } else {
            pmProgressWeekly = PmProgressWeeklyServiceImpl.setWeekValue(weekId,nowDate,weekDate,startDate,endDate,pmProgressWeekly);
        }
        pmProgressWeekly.setTs(nowDate);

        //获取上周weekId
        String lastWeekId = pmProgressWeeklyMapper.getLastWeekId(startDate,endDate,weekId);

        //获取项目信息
        List<PmPrj> prjList = pmPrjService.getNeedWeekPrj();
        if (!CollectionUtils.isEmpty(prjList)){
            for (PmPrj tmp : prjList) {
                String projectId = tmp.getProjectId();
                String weekPrjId = "";
                //判断本周该项目是否已生成
                List<PmProgressWeeklyPrj> weekPrj = getWeekPrj(projectId,weekId);
                if (CollectionUtils.isEmpty(weekPrj)){
                    weekPrjId = IdUtil.getSnowflakeNextIdStr();
                    createData(weekId,weekPrjId,tmp,pmProgressWeekly);

                    //填报明细生产
                    if (SharedUtil.isEmptyString(lastWeekId)){
                        pmProgressWeeklyPrjDetailService.createData(weekId,weekPrjId,tmp,pmProgressWeekly);
                    } else {
                        //新增数据，将上周值赋值到本周
                        pmProgressWeeklyPrjDetailService.createDataByLastWeek(lastWeekId,weekId,weekPrjId,tmp,pmProgressWeekly);
                    }
                }
            }
        }
    }
}
