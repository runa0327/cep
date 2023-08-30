package com.cisdi.pms.job.service.base;

import com.cisdi.pms.job.domain.process.WfProcessInstanceWX;

public interface AdRemindLogService {

    /**
     * 紧急待办消息发送政务微信日志写入
     * @param txt 提醒文本
     * @param remindType 提醒类别
     * @param tmp 其他消息体
     */
    void insertLog(String txt, String remindType, WfProcessInstanceWX tmp);

    /**
     * 根据单个提醒信息插入数据
     * @param msg 消息体
     */
    void insertByMsg(String msg);
}
