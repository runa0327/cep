package com.bid.ext.cc;

import com.bid.ext.model.CcWorkSchedule;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.bid.ext.utils.FlowUtils.sendNotify;

public class workbenchesExt {

    /**
     * 立即通知（工作日程）
     */
    public void notifyImmediatelyWorkSchedule() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcWorkSchedule ccWorkSchedule = CcWorkSchedule.selectById(csCommId);
            String crtUserId = ccWorkSchedule.getCrtUserId();
            Set<String> userIds = new HashSet<>();
            userIds.addAll(Arrays.asList(crtUserId));
            // 发送通知
            sendNotify(new ArrayList<>(userIds), csCommId);
        }
    }

}
