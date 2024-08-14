package com.bid.ext.cc;

import com.bid.ext.model.CcEquipIot;
import com.bid.ext.model.WfTask;
import com.bid.ext.model.YjwPressurePipeline;
import com.bid.ext.model.YjwReviewProgress;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
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

}
