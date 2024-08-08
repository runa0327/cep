package com.bid.ext.cc;

import com.bid.ext.model.CcDesignInqui;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class DesignInquiExt {
    /**
     * 获取设计咨询指派人员
     */
    public void getDesignInquiUserId() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDesignInqui ccDesignInqui = CcDesignInqui.selectById(csCommId);
            String assignPersonnel = ccDesignInqui.getAssignPersonnel();
            if (assignPersonnel != null && !assignPersonnel.isEmpty()) {
                String[] userIds = assignPersonnel.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

    /**
     * 获取设计咨询发起人员
     */
    public void getDesignCrtUserId() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDesignInqui ccDesignInqui = CcDesignInqui.selectById(csCommId);
            String crtUserId = ccDesignInqui.getCrtUserId();

            ExtJarHelper.setReturnValue(crtUserId);

            if (crtUserId != null) {
//                String[] userIds = assignPersonnel.split(",");
                ArrayList<String> userIdList = new ArrayList<>();
                userIdList.add(crtUserId);
                ExtJarHelper.setReturnValue(userIdList);
            }


//            String assignPersonnel = ccDesignInqui.getAssignPersonnel();
//            if (assignPersonnel != null && !assignPersonnel.isEmpty()) {
//                String[] userIds = assignPersonnel.split(",");
//                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
//                ExtJarHelper.setReturnValue(userIdList);
//            }
        }
    }
}
