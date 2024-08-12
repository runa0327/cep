package com.bid.ext.cc;

import com.bid.ext.model.CcEquipIot;
import com.bid.ext.model.YjwReviewProgress;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;
import java.util.List;


@Slf4j
public class ZJReviewFillExt {


    /**
     * 检查压力管道状态更新
     */
    public void updateReviewFillStatus() {

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

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

            if (!StringUtils.isEmpty(reviewProgress.getYjwContent())) {
                reviewProgress.setReviewIsFilled(true);
                reviewProgress.updateById();
            }

        }

    }

}
