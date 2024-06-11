package com.bid.ext.cc;

import com.bid.ext.model.CcMeeting;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class MeetingExt {

    /**
     * 获取参与人
     */
    public void getMeetingAttendUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcMeeting ccMeeting = CcMeeting.selectById(csCommId);
            String attendUserIds = ccMeeting.getAttendUserIds();
            if (attendUserIds != null && !attendUserIds.isEmpty()) {
                String[] userIds = attendUserIds.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

}
