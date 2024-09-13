package com.bid.ext.cc;

import com.bid.ext.model.CcMeeting;
import com.bid.ext.utils.FlowUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    /**
     * 立即通知（会议）
     */
    public void notifyImmediatelyMeeting() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcMeeting ccMeeting = CcMeeting.selectById(csCommId);
            // 参与人
            String attendMemberIds = ccMeeting.getAttendUserIds();
            // 主持人
            String hostUserId = ccMeeting.getHostUserId();
            List<String> userIds = new ArrayList<>();
            userIds.add(hostUserId);
            // 检查参与人字符串是否不为空
            if (attendMemberIds != null && !attendMemberIds.isEmpty()) {
                String[] members = attendMemberIds.split(",");
                List<String> membersList = Arrays.asList(members);
                // 如果主持人不在参与人列表中，则添加所有参与人
                if (!membersList.contains(hostUserId)) {
                    userIds.addAll(membersList);
                } else {
                    // 如果主持人已经在参与人列表中，则去重后添加
                    for (String member : membersList) {
                        if (!member.equals(hostUserId)) {
                            userIds.add(member);
                        }
                    }
                }
            }
            // 发送通知
            FlowUtils.sendNotify(userIds, csCommId);
        }
    }

}
