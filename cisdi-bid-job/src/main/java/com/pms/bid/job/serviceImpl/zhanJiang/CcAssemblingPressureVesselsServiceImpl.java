package com.pms.bid.job.serviceImpl.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcAssemblingPressureVessels;
import com.pms.bid.job.domain.zhanJiang.CcElevator;
import com.pms.bid.job.domain.zhanJiang.CcSpecialEquip;
import com.pms.bid.job.mapper.zhanJiang.CcAssemblingPressureVesselsMapper;
import com.pms.bid.job.mapper.zhanJiang.CcElevatorMapper;
import com.pms.bid.job.mapper.zhanJiang.CcSpecialEquipTodoMapper;
import com.pms.bid.job.service.zhanJiang.CcAssemblingPressureVesselsService;
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
public class CcAssemblingPressureVesselsServiceImpl implements CcAssemblingPressureVesselsService {

    @Resource
    private CcSpecialEquipService ccSpecialEquipService;

    @Resource
    private CcAssemblingPressureVesselsMapper assemblingPressureVesselsMapper;

    @Resource
    private CcSpecialEquipTodoMapper specialEquipTodoMapper;



    @Override
    public void checkDate() {

        log.info("拼装压力容器-检查数据是否需要发送待办！");

        //查询数据
        List<CcAssemblingPressureVessels> assemblingPressureVessels = assemblingPressureVesselsMapper.queryList();
        Date now = new Date();

        assemblingPressureVessels.forEach(vessel -> {

            //检查数据
           String conHeadId = specialEquipTodoMapper.getUserIdByMemberId( vessel.getConHeadId());
           String regProHeadId = specialEquipTodoMapper.getUserIdByMemberId( vessel.getRegProHeadId());
           String superviseId = specialEquipTodoMapper.getUserIdByMemberId( vessel.getSupervisorId());
           Integer slippageWarningDays=  vessel.getSlippageWarningDays();

            //计划到货时间
            if(vessel.getCcEquipPlanArriveDate()!=null) {
                if(!now.before(vessel.getCcEquipPlanArriveDate())){
                    if(vessel.getCcEquipActArriveDate()==null ||
                            vessel.getCcPlanConstructionNoticeDate()==null ||
                            vessel.getCcPlanCompleteInstallTime()==null ||
                            vessel.getCcPlanSuperviseInspection()==null ||
                            vessel.getCcPlanSupInsAgeSceneCheckDate()==null ||
                            vessel.getCcPlanPutIntoUseDate()==null){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.ARRIVE_PLAN_DATE_EXPIRE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcEquipPlanArriveDate(),"电梯-"+ vessel.getName()+"，计划到货时间超期！",0);
                    }
                }
            }
            //实际到货时间
            if (vessel.getCcEquipActArriveDate()!=null){
                if (!now.before(vessel.getCcEquipActArriveDate())){
                    if (vessel.getCcFactoryNumber()==null ||
                            vessel.getCcSpecialEquipVolume()==null ||
                            vessel.getCcSpecialEquipPressure()==null ){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.ARRIVE_ACT_DATE_EXPIRE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcEquipActArriveDate(),"电梯-"+ vessel.getName()+"，实际到货时间超期！",0);
                    }
                }
            }
            //计划施工告知时间
            if (vessel.getCcPlanConstructionNoticeDate()!=null){
                if (!now.before(vessel.getCcPlanConstructionNoticeDate())){
                    if (vessel.getCcCompleteConstructionNoticeDate()==null
                            || !StringUtils.hasText(vessel.getCcConstructionNoticeReceipt())){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.CONSTRUCTION_NOTICE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcPlanConstructionNoticeDate(),"电梯-"+ vessel.getName()+"，计划施工告知时间超期！",0);
                    }
                }
            }

            //完成安装
            if(vessel.getCcPlanCompleteInstallTime()!=null){
                if (!now.before(vessel.getCcPlanCompleteInstallTime())){
                    if (vessel.getCcActCompleteInstallDate()==null){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.INSTALL_ACT_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcPlanCompleteInstallTime(),"电梯-"+ vessel.getName()+"，计划安装完场时间超时期！",0);
                    }
                }
            }

            //监督检验计划报检时间
            if (vessel.getCcPlanSuperviseInspection()!=null){
                if (!now.before(vessel.getCcPlanSuperviseInspection())){
                    if (vessel.getCcCompleteSuperviseInspection() == null
                        || !StringUtils.hasText(vessel.getCcInspectionReport())){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.SUPERVISE_INSPECTION_REPORT_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcPlanSuperviseInspection(),"电梯-"+ vessel.getName()+"，监督检验计划报检时间超期！",0);
                    }
                }
            }

            //计划投用前30天
            if(vessel.getCcPlanPutIntoUseDate()!=null){
                if (ccSpecialEquipService.before30DaysLater(now,vessel.getCcPlanPutIntoUseDate())){
                    if (vessel.getCcPrjUnitPlanHandleUsageRegDate()==null){
                        this.remind(vessel, regProHeadId,CcSpecialEquipConstant.HANDLE_USAGE_REG_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcPlanPutIntoUseDate(),"电梯-"+ vessel.getName()+"，计划投用时间超期，未填写'项目单位计划办理使用登记的时间'！",-30);
                    }
                }
            }

            //计划投用时间
            if (vessel.getCcPlanPutIntoUseDate()!=null){
                if (!now.before(vessel.getCcPlanPutIntoUseDate())){
                    if (vessel.getCcCompleteSupInsAgeSceneCheckDate()==null
                            || !StringUtils.hasText(vessel.getCcSceneCheckOpinion())){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcPlanPutIntoUseDate(),"电梯-"+ vessel.getName()+"，计划投用时间超期,未填写'完成现场验收检验的时间'、'现场检验意见书'！",0);
                    }
                }
            }
            //计划投用时间
            if (vessel.getCcPlanPutIntoUseDate()!=null){
                if (!now.before(vessel.getCcPlanPutIntoUseDate())){
                    if (vessel.getCcActPutIntoUseDate()==null){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.USAGE_ACT_DATE_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcPlanPutIntoUseDate(),"电梯-"+ vessel.getName()+"，计划投用时间超期，未填写'实际投用时间'！",0);
                    }
                }
            }

            //实际投用时间,取得监督检验合格报告的时间、监督检验报告
            if (vessel.getCcActPutIntoUseDate()!=null){
                if (!now.before(vessel.getCcActPutIntoUseDate())){
                    if (vessel.getCcGetSupInsQualifiedReportDate()==null
                            || !StringUtils.hasText(vessel.getCcSupInsQualifiedReport())){
                        this.remind(vessel, conHeadId,CcSpecialEquipConstant.SCENE_SUPERVISE_INSPECTION_REPORT_TASK);
                ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcActPutIntoUseDate(),"电梯-"+ vessel.getName()+"，实际投用时间超期，未填写'取得监督检验合格报告的时间'、'监督检验报告'！",0);
                    }
                }
            }

            //计划登记办理时间
            if (vessel.getCcPrjUnitPlanHandleUsageRegDate()!=null){
                if (!now.before(vessel.getCcPrjUnitPlanHandleUsageRegDate())){
                    if (!StringUtils.hasText(vessel.getCcSpecialEquipUseRegistrationCart())){
                        this.remind(vessel,regProHeadId,CcSpecialEquipConstant.USAGE_REG_CART_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcPrjUnitPlanHandleUsageRegDate(),"电梯-"+ vessel.getName()+"，计划登记办理登记时间超期！",0);
                    }
                }

            }

            //取得监督检验报告时间
            if (vessel.getCcGetSupInsQualifiedReportDate()!=null){
                if (!now.before(vessel.getCcGetSupInsQualifiedReportDate())){
                    if (!StringUtils.hasText(vessel.getCcSpecialEquipInsQualityCert())){
                        this.remind(vessel,regProHeadId,CcSpecialEquipConstant.INSTALL_QUALITY_CERTIFICATE_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcGetSupInsQualifiedReportDate(),"电梯-"+ vessel.getName()+"，计划登记办理登记时间超期！",0);
                    }
                }

            }

            //安全阀计划检验时间
            if (vessel.getCcSpecialEquipSecValCheckDate()!=null){
                if (!now.before(vessel.getCcSpecialEquipSecValCheckDate())){
                    if (!StringUtils.hasText(vessel.getCcSpecialEquipComlSecValRep())){
                        this.remind(vessel,regProHeadId,CcSpecialEquipConstant.SAFETY_VALVE_CHECK_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcSpecialEquipSecValCheckDate(),"电梯-"+ vessel.getName()+"，计划登记办理登记时间超期！",0);
                    }
                }

            }

            //压力表计划检验时间
            if (vessel.getCcSpecialEquipPreGageCheckDate()!=null){
                if (!now.before(vessel.getCcSpecialEquipPreGageCheckDate())){
                    if (!StringUtils.hasText(vessel.getCcSpecialEquipComlPreGageRep())){
                        this.remind(vessel,regProHeadId,CcSpecialEquipConstant.PRESSURE_GAGE_CHECK_TASK);
                        ccSpecialEquipService.checkWarningDay(slippageWarningDays,superviseId,vessel.getCcSpecialEquipPreGageCheckDate(),"电梯-"+ vessel.getName()+"，计划登记办理登记时间超期！",0);
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
        ccSpecialEquipService.remind(specialEquip.getId(), toUser, CcSpecialEquipConstant.E_TYPE_ASSEMBLING_PRESSURE_VESSELS,taskType,specialEquip);
    }


}
