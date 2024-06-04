package com.bid.ext.cc;

import com.bid.ext.model.CcSpotExami;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class CcSpotExamiExt {
    /**
     * 获取现场检查抄送人
     */
    public void getSpotExamiCopyUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            CcSpotExami ccSpotExami = CcSpotExami.selectById(csCommId);
            String copyUserIds = ccSpotExami.getCarbonCopyUserIds();
            if (copyUserIds != null && !copyUserIds.isEmpty()) {
                String[] userIds = copyUserIds.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }
}
