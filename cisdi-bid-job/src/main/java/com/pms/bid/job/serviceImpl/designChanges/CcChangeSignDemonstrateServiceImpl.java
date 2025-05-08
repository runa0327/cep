package com.pms.bid.job.serviceImpl.designChanges;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pms.bid.job.domain.designChaneges.CcChangeDesignDemonstrate;
import com.pms.bid.job.domain.designChaneges.CcChangeSignDemonstrate;
import com.pms.bid.job.domain.designChaneges.TaskToBusiData;
import com.pms.bid.job.domain.qbq.QbqCallbackRequest;
import com.pms.bid.job.domain.qbq.QbqCallbackResponse;
import com.pms.bid.job.domain.ru.AdUser;
import com.pms.bid.job.domain.ru.RuEmployeeEntryInfo;
import com.pms.bid.job.domain.ru.RuVisaExpireWarning;
import com.pms.bid.job.mapper.designChanges.CcChangeDesignDemonstrateMapper;
import com.pms.bid.job.mapper.designChanges.CcChangeSignDemonstrateMapper;
import com.pms.bid.job.mapper.designChanges.TaskToBusiDataMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessInstanceMapper;
import com.pms.bid.job.mapper.ru.AdUserMapper;
import com.pms.bid.job.mapper.ru.CcPrjMemberMapper;
import com.pms.bid.job.mapper.ru.RuEmployeeEntryInfoMapper;
import com.pms.bid.job.mapper.ru.RuVisaExpireWarningMapper;
import com.pms.bid.job.service.designChanges.CcChangeSignDemonstrateService;
import com.pms.bid.job.service.processInstance.WfProcessInstanceService;
import com.pms.bid.job.service.ru.RuEmployeeEntryInfoService;
import com.pms.bid.job.util.DateUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

@Slf4j
@Service
public class CcChangeSignDemonstrateServiceImpl implements CcChangeSignDemonstrateService {

    @Autowired
    private CcChangeSignDemonstrateMapper  changeSignDemonstrateMapper;

    @Autowired
    private CcChangeDesignDemonstrateMapper changeDesignDemonstrateMapper;

    @Autowired
    private TaskToBusiDataMapper taskToBusiDataMapper;

    @Autowired
    private AdUserMapper adUserMapper;

    @Autowired
    private WfProcessInstanceService wfProcessInstanceService;

    @Override
    @Transactional
    public QbqCallbackResponse checkChangeSignStatus(QbqCallbackRequest request) {
        QbqCallbackResponse qbqCallbackResponse = new QbqCallbackResponse();

        //operateType为2,3,4,7,有changeTaskInfo数据（  2 签署 3 拒签 4 撤销  7 任务签署方状态变更）
        if (request.getOperateType()==7) {
            String taskCode = request.getChangeTaskInfo().getTaskCode();
            Integer taskStatus = request.getChangeTaskInfo().getTaskStatus();

            //查询签署任务关联的业务数据
            LambdaQueryWrapper<TaskToBusiData>  queryTaskToBusiData = new LambdaQueryWrapper<>();
            queryTaskToBusiData.eq(TaskToBusiData::getTaskCode, taskCode);
            queryTaskToBusiData.eq(TaskToBusiData::getIsCurrent, 1);
            TaskToBusiData taskToBusiData = taskToBusiDataMapper.selectOne(queryTaskToBusiData);
            taskToBusiData.setSignFileStatusId(taskStatus+"");

            taskToBusiDataMapper.updateById(taskToBusiData);//更新业务系统中关联数据的状态

            if(taskStatus==4){ //已办结

                String  entCode = taskToBusiData.getEntCode();
                String entityRecordId = taskToBusiData.getEntityRecordId();
                if ("CC_CHANGE_DESIGN_DEMONSTRATE".equals(entCode)) {
                    CcChangeDesignDemonstrate ccChangeDesignDemonstrate = changeDesignDemonstrateMapper.selectById(entityRecordId);
                   if(ccChangeDesignDemonstrate!=null){
//                    签署状态改为完成
                        ccChangeDesignDemonstrate.setChangeSignDemonstrateStatusId("SF");
                        changeDesignDemonstrateMapper.updateById(ccChangeDesignDemonstrate);
                    }

                }else if("CC_CHANGE_SIGN_DEMONSTRATE".equals(entCode)) {
                    CcChangeSignDemonstrate ccChangeSignDemonstrate = changeSignDemonstrateMapper.selectById(entityRecordId);

                    //更新变更申请状态
                    ccChangeSignDemonstrate.setCcChangeSignDemonstrateStatusId("SF");
                    changeSignDemonstrateMapper.updateById(ccChangeSignDemonstrate);

                    //查询是否需要设计变更
                    if(ccChangeSignDemonstrate.getIsChange()==1){
                        //查询流程中的设计单位人员

                        //发起设计变更流程
                        //新建设计变更
                        CcChangeDesignDemonstrate ccChangeDesignDemonstrate = new CcChangeDesignDemonstrate();
                        ccChangeDesignDemonstrate.setId(IdUtil.getSnowflakeNextIdStr());
                        ccChangeDesignDemonstrate.setCcChageSignDemonstrateId(entityRecordId);
                        ccChangeDesignDemonstrate.setCC_PRJ_ID(ccChangeSignDemonstrate.getCC_PRJ_ID());

                        changeDesignDemonstrateMapper.insert(ccChangeDesignDemonstrate);

                        AdUser adUser = adUserMapper.selectByWfProcessInstanceId(ccChangeSignDemonstrate.getLkWfInstId());

                        //发起流程
                        Map<String, String> wfProcessToSendNode = wfProcessInstanceService.createWfProcessToSendNode(ccChangeDesignDemonstrate, "1871732193448214528", "1871731495553777664", ccChangeDesignDemonstrate.getId()
                                , "CC_CHANGE_DESIGN_DEMONSTRATE", adUser.getId());

                        //流程实例id
                        String wfProcessInstanceId = wfProcessToSendNode.get("wfProcessInstanceId");
                        ccChangeDesignDemonstrate.setLkWfInstId(wfProcessInstanceId);

                        changeDesignDemonstrateMapper.updateById(ccChangeDesignDemonstrate);
                    }

                }

            }
        }

        qbqCallbackResponse.setCode(200);
        qbqCallbackResponse.setMsg("ok");

        return qbqCallbackResponse;
    }


}
