package com.pms.bid.job.service.zhanJiang;

import com.pms.bid.job.domain.processInstance.WfNodeInstance;
import com.pms.bid.job.domain.zhanJiang.CcSpecialEquip;
import com.pms.bid.job.domain.zhanJiang.FillingCycle;
import com.pms.bid.job.domain.zhanJiang.PressurePipeline;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface CcSpecialEquipService {

    /**
     * 发起流程
     */
    Map<String, String> createWfProcessToSendNode(CcSpecialEquip specialEquip, String firstNodeId, String wfProcessId, String entId, String entCode, String adUserId,String name);

    /**
     * 创建任务
     * @param wfNodeInstance
     * @param taskUser
     * @param isCurrent
     * @param now
     * @param createBy
     */
    void createUserTask(WfNodeInstance wfNodeInstance, String taskUser, Integer isCurrent, String now, String createBy);

    /**
     * 生成填报时间范围
     * @param start
     * @param end
     * @param type
     * @return
     */
    List<FillingCycle> generateDateRanges(Date start, Date end, int type);

    /**
     * 发送待办
     */
    void remind(String specialEquipId,String toUserId,String specialEquipCategory,String taskType,CcSpecialEquip specialEquip);

    /**
     * 判断时间是否超过30天
     *
     * @param date
     * @return
     */
      boolean isMoreThan30DaysOld(Date date);

    public boolean isMoreThanDaysOld(Date date,int days);

    /**
     * 判断当前时间是否在目标时间之前（30天）
     *
     * @param date
     * @return
     */
     boolean before30DaysLater(Date date, Date target);

    /**
     * 检查是否需要发送预警预警通知
     * @param warningDay
     * @param userIds
     * @param planDate
     * @param message
     * @param moreThanDay
     */
    void checkWarningDay(Integer warningDay, String userIds, Date planDate, String message, int moreThanDay);

}
