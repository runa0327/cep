package com.cisdi.pms.job.serviceImpl.base;

import cn.hutool.core.util.IdUtil;
import com.cisdi.pms.job.domain.base.AdRemindLog;
import com.cisdi.pms.job.domain.process.WfProcessInstanceWX;
import com.cisdi.pms.job.mapper.base.AdRemindLogMapper;
import com.cisdi.pms.job.service.base.AdRemindLogService;
import com.cisdi.pms.job.utils.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class AdRemindLogServiceImpl implements AdRemindLogService {

    @Resource
    private AdRemindLogMapper adRemindLogMapper;

    /**
     * 紧急待办消息发送政务微信日志写入
     * @param txt        提醒文本
     * @param remindType 提醒类别
     * @param tmp        其他消息体
     */
    @Override
    public void insertLog(String txt, String remindType, WfProcessInstanceWX tmp) {
        AdRemindLog adRemindLog = new AdRemindLog();
        String now = DateUtil.getNormalTimeStr(new Date());
        String id = IdUtil.getSnowflakeNextIdStr();
        String userId = tmp.getUserId();
        String phone = tmp.getUserCode();

        adRemindLog.setId(id);
        adRemindLog.setVer("1");
        adRemindLog.setTs(now);
        adRemindLog.setCreateBy(userId);
        adRemindLog.setLastUpdateBy(userId);
        adRemindLog.setCreateDate(now);
        adRemindLog.setLastUpdateDate(now);
        adRemindLog.setStatus("AP");
        adRemindLog.setCode("WF_TASK_REMIND_LOCK");
        adRemindLog.setAdEntId("0099250247095866647");
        adRemindLog.setEntCode("WF_TASK");
        adRemindLog.setEntityRecordId(tmp.getTaskId());
        adRemindLog.setRemindUserId(userId);
        adRemindLog.setRemindMethod("WX");
        adRemindLog.setRemindTarget(phone);
        adRemindLog.setRemindTime(now);
        adRemindLog.setRemindText(txt);
        adRemindLog.setMessageNotifyLogType(remindType);

        adRemindLogMapper.insert(adRemindLog);

    }
}
