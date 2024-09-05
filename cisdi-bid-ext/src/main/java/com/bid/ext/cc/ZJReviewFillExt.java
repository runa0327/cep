package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;


@Slf4j
public class ZJReviewFillExt {


    /**
     * 检查压力管道状态更新
     */
    public void updateReviewFillStatus() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        Boolean  isComplete = true;
        String pipelineId = null;
        for (EntityRecord entityRecord : entityRecordList) {

            Object id = entityRecord.valueMap.get("ID");
            if (id == null) {
                continue;
            }

            YjwReviewProgress reviewProgress = null;
            try {
                reviewProgress = YjwReviewProgress.selectById(id.toString());
            } catch (Exception e) {
                log.error(new Date() + "压力管道竣工资料填报" + id + "查询失败:" + e.getMessage());
            }
            if (reviewProgress==null){
                return;
            }

           pipelineId =  reviewProgress.getYjwPressurePipelineId();
            if (!StringUtils.isEmpty(reviewProgress.getYjwContent())) {
                reviewProgress.setReviewIsFilled(true);
                reviewProgress.updateById();

            }

            Where  queryReviewFill = new Where();
            queryReviewFill.eq("YJW_PRESSURE_PIPELINE_ID",reviewProgress.getYjwPressurePipelineId());
            List<YjwReviewProgress> yjwReviewProgresses = YjwReviewProgress.selectByWhere(queryReviewFill);
            for (YjwReviewProgress reviewFill:yjwReviewProgresses){
                if ( !reviewFill.getReviewIsFilled()){//状态为未填报
                    if(reviewFill.getReviewFillDateFr().isBefore(LocalDate.now())){//且填报开始时间小于今天
                        isComplete =false;
                        break;//终止
                    }
                }
            }
        }


        if (isComplete) {
            YjwPressurePipeline yjwPressurePipeline = YjwPressurePipeline.selectById(pipelineId);
            if(yjwPressurePipeline.getYjwTask19()!=null) {
                //验证是否完成
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask19());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

    }


    /**
     * 检查压力管道安装状态更新
     */
    public void updateInstallFillStatus() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        Boolean  isComplete = true;
        String  pipelineId = null;
        for (EntityRecord entityRecord : entityRecordList) {

            Object id = entityRecord.valueMap.get("ID");
            if (id == null) {
                continue;
            }

            Object progress = entityRecord.valueMap.get("CC_SPECIAL_EQUIP_INSTALL_PROGRESS");

            if(progress == null){
                continue;
            }

            YjwInstallProgress installProgress = null;
            try {
                installProgress = YjwInstallProgress.selectById(id.toString());
            } catch (Exception e) {
                log.error(new Date() + "压力管道竣工资料填报" + id + "查询失败:" + e.getMessage());
            }
            if (installProgress==null){
                return;
            }

            if(Integer.parseInt(progress.toString())>100){
                YjwInstallProgress yjwInstallProgress = new YjwInstallProgress();
                yjwInstallProgress.setId(installProgress.getYjwPressurePipelineId());
                yjwInstallProgress.setCcSpecialEquipInstallProgress(100);
                yjwInstallProgress.updateById();
            }else if (Integer.parseInt(progress.toString())<0){
                YjwInstallProgress yjwInstallProgress = new YjwInstallProgress();
                yjwInstallProgress.setId(installProgress.getYjwPressurePipelineId());
                yjwInstallProgress.setCcSpecialEquipInstallProgress(0);
                yjwInstallProgress.updateById();
            }

            if (Integer.parseInt(progress.toString()) >= 100){ //填报完成
                Object actInstallTime = entityRecord.valueMap.get("YJW_INSTALLATION_TIME");
                YjwPressurePipeline pipeline = new YjwPressurePipeline();
                pipeline.setId(installProgress.getYjwPressurePipelineId());
                if (actInstallTime==null){
                    pipeline.setYjwInstallationTime(LocalDate.now());
                }else{
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    pipeline.setYjwInstallationTime(LocalDate.parse(actInstallTime.toString(), formatter));
                }
                pipeline.updateById();
            }

            pipelineId =  installProgress.getYjwPressurePipelineId();
            if (installProgress.getCcSpecialEquipInstallProgress() != null) {
                installProgress.setReviewIsFilled(true);
                installProgress.updateById();
            }

            Where  queryReviewFill = new Where();
            queryReviewFill.eq("YJW_PRESSURE_PIPELINE_ID",installProgress.getYjwPressurePipelineId());
            List<YjwInstallProgress> yjwReviewProgresses = YjwInstallProgress.selectByWhere(queryReviewFill);
            for (YjwInstallProgress reviewFill:yjwReviewProgresses){
                if ( !reviewFill.getReviewIsFilled()){//状态为未填报
                    if(reviewFill.getInstallFillDateFr().isBefore(LocalDate.now())){//且填报开始时间小于今天
                        isComplete =false;
                        break;//终止
                    }
                }
            }
        }


        if (isComplete) {
            YjwPressurePipeline yjwPressurePipeline = YjwPressurePipeline.selectById(pipelineId);
            if(yjwPressurePipeline.getYjwTask19()!=null) {
                //验证是否完成
                WfTask wfTask = new WfTask();
                wfTask.setId(yjwPressurePipeline.getYjwTask9());
                wfTask.setIsClosed(true);
                wfTask.updateById();
            }
        }

    }

}
