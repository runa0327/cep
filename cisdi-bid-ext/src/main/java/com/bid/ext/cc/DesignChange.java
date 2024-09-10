package com.bid.ext.cc;

import com.bid.ext.model.CcDesignChange;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class DesignChange {
    /**
     * 获取设计变更经办人
     */
    public void getTrxUerId() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcDesignChange ccDesignChange = CcDesignChange.selectById(csCommId);
            String trxUserId = ccDesignChange.getTrxUserId();
            if (trxUserId != null && !trxUserId.isEmpty()) {
                String[] userIds = trxUserId.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }
}
