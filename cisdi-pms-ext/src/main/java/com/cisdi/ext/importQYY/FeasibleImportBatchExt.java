package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.FeasibleImport;
import com.cisdi.ext.model.PmPrjInvest1;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.util.List;

/**
 * @author dlt
 * @date 2023/2/28 周二
 */
public class FeasibleImportBatchExt {
    /**
     * 生成明细。
     */
    public void generateDtl() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 获取批准的可研：
            List<PmPrjInvest1> feasibleList = PmPrjInvest1.selectByWhere(new Where().eq(PmPrjInvest1.Cols.STATUS, "AP"));
            if (!SharedUtil.isEmptyList(feasibleList)) {
                // 先按名称排序，再逐条取数并插入明细：
                feasibleList.stream().sorted((o1, o2) -> {
                    if (o1.getName() == null && o2.getName() == null) {
                        return 0;
                    } else if (o1.getName() == null && o2.getName() != null) {
                        return -1;
                    } else if (o1.getName() != null && o2.getName() == null) {
                        return 1;
                    } else {
                        return o1.getName().compareTo(o2.getName());
                    }
                })
                        // .skip(50).limit(5)
                        .forEach(feasible -> {
//                            FeasibleImport feasibleImport = doGetDtl(feasible);
//                            feasibleImport.setFeasibleImportBatchId(csCommId);
//                            feasibleImport.insertById();
                        });

            }
        }
    }
}
