package com.pms.bid.job.serviceImpl.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcElevator;
import com.pms.bid.job.domain.zhanJiang.CcSpecialEquip;
import com.pms.bid.job.mapper.zhanJiang.CcElevatorMapper;
import com.pms.bid.job.mapper.zhanJiang.CcSpecialEquipTodoMapper;
import com.pms.bid.job.service.zhanJiang.CcElevatorService;
import com.pms.bid.job.service.zhanJiang.CcElevatorService;
import com.pms.bid.job.service.zhanJiang.CcSpecialEquipService;
import com.pms.bid.job.util.CcSpecialEquipConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class CcElevatorServiceImpl implements CcElevatorService {

    @Resource
    private CcSpecialEquipService ccSpecialEquipService;
    @Resource
    private CcElevatorMapper elevatorMapper;
    @Resource
    private CcSpecialEquipTodoMapper specialEquipTodoMapper;



    @Override
    public void checkDate() {

        log.info("开始检查数据是否需要发送待办！");

        //查询数据
        List<CcElevator> ccElevators = elevatorMapper.queryList();
        Date now = new Date();

        ccElevators.forEach(ccElevator -> {
            //检查数据

           String conHeadId = specialEquipTodoMapper.getUserIdByMemberId( ccElevator.getConHeadId());
           String regProHeadId = specialEquipTodoMapper.getUserIdByMemberId( ccElevator.getRegProHeadId());
           String superviseId = specialEquipTodoMapper.getUserIdByMemberId( ccElevator.getSupervisorId());
           Integer slippageWarningDays=  ccElevator.getSlippageWarningDays();

            //计划到货时间
            if(ccElevator.getCcEquipPlanArriveDate()!=null) {
                if(!now.before(ccElevator.getCcEquipPlanArriveDate())){
                    if(ccElevator.getCcEquipActArriveDate()==null ||
                            ccElevator.getCcPlanConstructionNoticeDate()==null ||
                            ccElevator.getCcPlanCompleteInstallTime()==null ||
                            ccElevator.getCcPlanSuperviseInspection()==null ||
                            ccElevator.getCcPlanSupInsAgeSceneCheckDate()==null ||
                            ccElevator.getCcPlanPutIntoUseDate()==null){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.ARRIVE_PLAN_DATE_EXPIRE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcEquipPlanArriveDate(),"电梯-"+ ccElevator.getName()+"，计划到货时间超期！",0);
                    }
                }
            }
            //实际到货时间
            if (ccElevator.getCcEquipActArriveDate()!=null){
                if (!now.before(ccElevator.getCcEquipActArriveDate())){
                    if (ccElevator.getCcElevatorRatedLoad()==null ||
                            ccElevator.getCcFactoryNumber()==null ||
                            ccElevator.getCcSpecialEquipManufacturer()==null ){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.ARRIVE_ACT_DATE_EXPIRE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcEquipActArriveDate(),"电梯-"+ ccElevator.getName()+"，实际到货时间超期！",0);
                    }
                }
            }
            //计划施工告知时间
            if (ccElevator.getCcPlanConstructionNoticeDate()!=null){
                if (!now.before(ccElevator.getCcPlanConstructionNoticeDate())){
                    if (ccElevator.getCcCompleteConstructionNoticeDate()==null
                            || !StringUtils.hasText(ccElevator.getCcConstructionNoticeReceipt())){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.CONSTRUCTION_NOTICE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcPlanConstructionNoticeDate(),"电梯-"+ ccElevator.getName()+"，计划施工告知时间超期！",0);
                    }
                }
            }

            //完成安装
            if(ccElevator.getCcPlanCompleteInstallTime()!=null){
                if (!now.before(ccElevator.getCcPlanCompleteInstallTime())){
                    if (ccElevator.getCcActCompleteInstallDate()==null){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.INSTALL_ACT_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcPlanCompleteInstallTime(),"电梯-"+ ccElevator.getName()+"，计划安装完场时间超时期！",0);
                    }
                }
            }

            //监督检验计划报检时间
            if (ccElevator.getCcPlanSuperviseInspection()!=null){
                if (!now.before(ccElevator.getCcPlanSuperviseInspection())){
                    if (ccElevator.getCcCompleteSuperviseInspection() == null
                        || !StringUtils.hasText(ccElevator.getCcInspectionReport())){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.SUPERVISE_INSPECTION_REPORT_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcPlanSuperviseInspection(),"电梯-"+ ccElevator.getName()+"，监督检验计划报检时间超期！",0);
                    }
                }
            }

            //计划投用前30天
            if(ccElevator.getCcPlanPutIntoUseDate()!=null){
                if (ccSpecialEquipService.before30DaysLater(now,ccElevator.getCcPlanPutIntoUseDate())){
                    if (ccElevator.getCcPrjUnitPlanHandleUsageRegDate()==null){
                        this.remind(ccElevator, regProHeadId,CcSpecialEquipConstant.HANDLE_USAGE_REG_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcPlanPutIntoUseDate(),"电梯-"+ ccElevator.getName()+"，计划投用时间超期，未填写'项目单位计划办理使用登记的时间'！",-30);
                    }
                }
            }

            //计划投用时间
            if (ccElevator.getCcPlanPutIntoUseDate()!=null){
                if (!now.before(ccElevator.getCcPlanPutIntoUseDate())){

                    if (ccElevator.getCcCompleteSupInsAgeSceneCheckDate()==null
                            || !StringUtils.hasText(ccElevator.getCcSceneCheckOpinion())){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcPlanPutIntoUseDate(),"电梯-"+ ccElevator.getName()+"，计划投用时间超期,未填写'完成现场验收检验的时间'、'现场检验意见书'！",0);
                    }
                }
            }
            //计划投用时间
            if (ccElevator.getCcPlanPutIntoUseDate()!=null){
                if (!now.before(ccElevator.getCcPlanPutIntoUseDate())){
                    if (ccElevator.getCcActPutIntoUseDate()==null){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.USAGE_ACT_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcPlanPutIntoUseDate(),"电梯-"+ ccElevator.getName()+"，计划投用时间超期，未填写'实际投用时间'！",0);
                    }
                }
            }

            //实际投用时间,取得监督检验合格报告的时间、监督检验报告
            if (ccElevator.getCcActPutIntoUseDate()!=null){
                if (!now.before(ccElevator.getCcActPutIntoUseDate())){

                    if (ccElevator.getCcGetSupInsQualifiedReportDate()==null
                            || !StringUtils.hasText(ccElevator.getCcSupInsQualifiedReport())){
                        this.remind(ccElevator, conHeadId,CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_REPORT_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcActPutIntoUseDate(),"电梯-"+ ccElevator.getName()+"，实际投用时间超期，未填写'取得监督检验合格报告的时间'、'监督检验报告'！",0);
                    }
                }
            }

            //计划登记办理时间
            if (ccElevator.getCcPrjUnitPlanHandleUsageRegDate()!=null){
                if (!now.before(ccElevator.getCcPrjUnitPlanHandleUsageRegDate())){
                    if (ccElevator.getCcSpecialEquipActUseReg()!=null ||!StringUtils.hasText(ccElevator.getCcSpecialEquipUseRegistrationCart())){
                        this.remind(ccElevator,regProHeadId,CcSpecialEquipConstant.USAGE_REG_CART_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,ccElevator.getCcPrjUnitPlanHandleUsageRegDate(),"电梯-"+ ccElevator.getName()+"，计划登记办理登记时间超期！",0);
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
        ccSpecialEquipService.remind(specialEquip.getId(), toUser, CcSpecialEquipConstant.E_TYPE_ELEVATOR,taskType,specialEquip);
    }


}
