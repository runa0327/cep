package com.cisdi.pms.job.serviceImpl.weeklyReport;

import com.cisdi.pms.job.domain.weeklyReport.PmProgressWeekly;
import com.cisdi.pms.job.service.weeklyReport.PmProgressWeeklyService;
import com.cisdi.pms.job.utils.DateUtil;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class PmProgressWeeklyServiceImpl implements PmProgressWeeklyService {

    /**
     * 进度周报-周信息 赋值
     * @param weekId 周id
     * @param nowDate 当前时间
     * @param weekDate 周信息
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param pmProgressWeekly 返回信息
     * @return 周信息
     */
    public static PmProgressWeekly setWeekValue(String weekId, String nowDate, String weekDate, String startDate, String endDate, PmProgressWeekly pmProgressWeekly) {
        pmProgressWeekly.setId(weekId);
        pmProgressWeekly.setVer("1");
        pmProgressWeekly.setTs(nowDate);
        pmProgressWeekly.setCrtDt(nowDate);
        pmProgressWeekly.setCrtUserId("0099250247095871681");
        pmProgressWeekly.setLastModiDt(nowDate);
        pmProgressWeekly.setLastModiUserId("0099250247095871681");
        pmProgressWeekly.setStatus("AP");
        pmProgressWeekly.setDate(weekDate);
        pmProgressWeekly.setToDate(DateUtil.stringToDate(endDate));
        pmProgressWeekly.setFromDate(DateUtil.stringToDate(startDate));
        return pmProgressWeekly;
    }
}
