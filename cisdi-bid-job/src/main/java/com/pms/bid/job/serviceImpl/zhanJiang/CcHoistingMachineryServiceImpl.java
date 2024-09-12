package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.json.Internationalization;
import com.pms.bid.job.domain.processInstance.*;
import com.pms.bid.job.domain.zhanJiang.*;
import com.pms.bid.job.mapper.processInstance.WfNodeInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfNodeMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessInstanceMapper;
import com.pms.bid.job.mapper.processInstance.WfProcessMapper;
import com.pms.bid.job.mapper.zhanJiang.CcEarlyWarningMapper;
import com.pms.bid.job.mapper.zhanJiang.CcHoistingMachineryMapper;
import com.pms.bid.job.mapper.zhanJiang.CcSpecialEquipTodoMapper;
import com.pms.bid.job.mapper.zhanJiang.PressurePipelineMapper;
import com.pms.bid.job.service.processInstance.WfTaskService;
import com.pms.bid.job.service.zhanJiang.CcHoistingMachineryService;
import com.pms.bid.job.service.zhanJiang.CcSpecialEquipService;
import com.pms.bid.job.service.zhanJiang.PressurePipelineService;
import com.pms.bid.job.util.CcSpecialEquipConstant;
import com.pms.bid.job.util.DateUtil;
import com.pms.bid.job.util.JsonUtil;
import com.qygly.shared.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
public class CcHoistingMachineryServiceImpl implements CcHoistingMachineryService {

    @Resource
    private CcSpecialEquipService ccSpecialEquipService;
    @Resource
    private CcHoistingMachineryMapper hoistingMachineryMapper;
    @Resource
    private CcSpecialEquipTodoMapper specialEquipTodoMapper;



    @Override
    public void checkDate() {

        log.info("开始检查数据是否需要发送待办！");

        //查询数据
        List<CcHoistingMachinery> ccHoistingMachineries = hoistingMachineryMapper.queryList();
        Date now = new Date();

        ccHoistingMachineries.forEach(ccHoistingMachinery -> {
            //检查数据

           String conHeadId = specialEquipTodoMapper.getUserIdByMemberId( ccHoistingMachinery.getConHeadId());
           String regProHeadId = specialEquipTodoMapper.getUserIdByMemberId( ccHoistingMachinery.getRegProHeadId());
           String superviseId = specialEquipTodoMapper.getUserIdByMemberId( ccHoistingMachinery.getSupervisorId());
           Integer slippageWarningDays=  ccHoistingMachinery.getSlippageWarningDays();

            //计划到货时间
            if(ccHoistingMachinery.getCcEquipPlanArriveDate()!=null) {
                if(ccSpecialEquipService.isMoreThanDaysOld(ccHoistingMachinery.getCcEquipPlanArriveDate(),7)){
                    if(ccHoistingMachinery.getCcEquipActArriveDate()==null ||
                            ccHoistingMachinery.getCcPlanConstructionNoticeDate()==null ||
                            ccHoistingMachinery.getCcPlanCompleteInstallTime()==null ||
                            ccHoistingMachinery.getCcPlanSuperviseInspection()==null ||
                            ccHoistingMachinery.getCcPlanSupInsAgeSceneCheckDate()==null ||
                            ccHoistingMachinery.getCcPlanPutIntoUseDate()==null){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.ARRIVE_PLAN_DATE_EXPIRE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcEquipPlanArriveDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，计划到货时间超期！",0);
                    }
                }
            }
            //实际到货时间
            if (ccHoistingMachinery.getCcEquipActArriveDate()!=null){
                if (!now.before(ccHoistingMachinery.getCcEquipActArriveDate())){
                    if (ccHoistingMachinery.getCcSpecificationAndModel()==null ||
                            ccHoistingMachinery.getCcFactoryNumber()==null ||
                            ccHoistingMachinery.getCcWorkLevel()==null ||
                            ccHoistingMachinery.getCcMainHookRatedLiftingCapacity()==null ||
                            ccHoistingMachinery.getCcAuxiliaryHookRatedLiftingCapacity()==null){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.ARRIVE_ACT_DATE_EXPIRE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcEquipActArriveDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，实际到货时间超期！",0);
                    }
                }
            }
            //计划施工告知时间
            if (ccHoistingMachinery.getCcPlanConstructionNoticeDate()!=null){
                if (!now.before(ccHoistingMachinery.getCcPlanConstructionNoticeDate())){
                    if (ccHoistingMachinery.getCcCompleteConstructionNoticeDate()==null
                            || !StringUtils.hasText(ccHoistingMachinery.getCcConstructionNoticeReceipt())){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.CONSTRUCTION_NOTICE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcPlanConstructionNoticeDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，计划施工告知时间超期！",0);
                    }
                }
            }

            //完成安装
            if(ccHoistingMachinery.getCcPlanCompleteInstallTime()!=null){
                if (!now.before(ccHoistingMachinery.getCcPlanCompleteInstallTime())){
                    if (ccHoistingMachinery.getCcActCompleteInstallDate()==null){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.INSTALL_ACT_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcPlanCompleteInstallTime(),"起重机械-"+ ccHoistingMachinery.getName()+"，计划安装完场时间超时期！",0);
                    }
                }
            }

            //监督检验计划报检时间
            if (ccHoistingMachinery.getCcPlanSuperviseInspection()!=null){
                if (!now.before(ccHoistingMachinery.getCcPlanSuperviseInspection())){
                    if (ccHoistingMachinery.getCcCompleteSuperviseInspection() == null
                        || !StringUtils.hasText(ccHoistingMachinery.getCcInspectionReport())){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.SUPERVISE_INSPECTION_REPORT_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcPlanSuperviseInspection(),"起重机械-"+ ccHoistingMachinery.getName()+"，监督检验计划报检时间超期！",0);
                    }
                }
            }

            //计划投用前30天
            if(ccHoistingMachinery.getCcPlanPutIntoUseDate()!=null){
                if (ccSpecialEquipService.before30DaysLater(now,ccHoistingMachinery.getCcPlanPutIntoUseDate())){
                    if (ccHoistingMachinery.getCcPrjUnitPlanHandleUsageRegDate()==null){
                        this.remind(ccHoistingMachinery, regProHeadId,CcSpecialEquipConstant.HANDLE_USAGE_REG_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcPlanPutIntoUseDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，计划投用时间超期，未填写'项目单位计划办理使用登记的时间'！",0);
                    }
                }
            }

            //计划投用时间
            if (ccHoistingMachinery.getCcPlanPutIntoUseDate()!=null){
                if (!now.before(ccHoistingMachinery.getCcPlanPutIntoUseDate())){

                    if (ccHoistingMachinery.getCcCompleteSupInsAgeSceneCheckDate()==null
                            || !StringUtils.hasText(ccHoistingMachinery.getCcSceneCheckOpinion())){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcPlanPutIntoUseDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，计划投用时间超期,未填写'完成现场验收检验的时间'、'现场检验意见书'！",0);
                    }
                }
            }
            //计划投用时间
            if (ccHoistingMachinery.getCcPlanPutIntoUseDate()!=null){
                if (!now.before(ccHoistingMachinery.getCcPlanPutIntoUseDate())){
                    if (ccHoistingMachinery.getCcActPutIntoUseDate()==null){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.USAGE_ACT_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcPlanPutIntoUseDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，计划投用时间超期，未填写'实际投用时间'！",0);
                    }
                }
            }

            //实际投用时间,取得监督检验合格报告的时间、监督检验报告
            if (ccHoistingMachinery.getCcActPutIntoUseDate()!=null){
                if (!now.before(ccHoistingMachinery.getCcActPutIntoUseDate())){

                    if (ccHoistingMachinery.getCcGetSupInsQualifiedReportDate()==null
                            || !StringUtils.hasText(ccHoistingMachinery.getCcSupInsQualifiedReport())){
                        this.remind(ccHoistingMachinery, conHeadId,CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_REPORT_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcActPutIntoUseDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，实际投用时间超期，未填写'取得监督检验合格报告的时间'、'监督检验报告'！",0);
                    }
                }
            }

            //计划登记办理时间
            if (ccHoistingMachinery.getCcPrjUnitPlanHandleUsageRegDate()!=null){
                if (!now.before(ccHoistingMachinery.getCcPrjUnitPlanHandleUsageRegDate())){
                    if (!StringUtils.hasText(ccHoistingMachinery.getCcSpecialEquipUseRegistrationCart())){
                        this.remind(ccHoistingMachinery,regProHeadId,CcSpecialEquipConstant.USAGE_REG_CART_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccHoistingMachinery.getCcPrjUnitPlanHandleUsageRegDate(),"起重机械-"+ ccHoistingMachinery.getName()+"，计划登记办理登记时间超期！",0);
                    }
                }

            }
        });
    }


    /**
     * 发送待办
     * @param specialEquip
     * @param toUser
     * @param taskType
     */
    private  void remind(CcSpecialEquip specialEquip,String toUser,String taskType){
        ccSpecialEquipService.remind(specialEquip.getId(), toUser, CcSpecialEquipConstant.E_TYPE_HOISTING_MACHINERY,taskType,specialEquip);
    }


}
