package com.cisdi.ext.proPlan;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.List;

public class ProPlanExt {
    public void calcPlanTotalDays() {
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            calcPlanTotalDays(entityRecord);
        }
    }

    private void calcPlanTotalDays(EntityRecord entityRecord) {
        // 若无节点，则将计划的PLAN_TOTAL_DAYS设为0，返回：

        // 若有节点：

        // 至少1个最末级节点的START_DAY要为1（至少1个最末级节点，要从第1天开始做），否则报错、返回：

        // 所有最末级节点，必须START_DAY>=1，PLAN_TOTAL_DAYS>=1，否则报错、返回：

        // 从倒数第2级节点往上推导START_DAY、PLAN_TOTAL_DAYS，直到计划上：
        // 父节点的START_DAY=所有直接子节点的START_DAY的最小值
        // 父节点的PLAN_TOTAL_DAYS=所有直接子节点的（START_DAY+PLAN_TOTAL_DAYS-1）的最大值-父节点的START_DAY+1

        // 成功返回。
    }
}
