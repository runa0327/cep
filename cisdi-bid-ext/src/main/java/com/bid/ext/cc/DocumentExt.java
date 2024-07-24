package com.bid.ext.cc;

import com.bid.ext.model.CcDocument;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.Arrays;

public class DocumentExt {

    /**
     * 获取公文接收人
     */

    public void getDocumentRcvUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            // CcDocument
            CcDocument ccDocument = CcDocument.selectById(csCommId);
            String rcvUserIds = ccDocument.getRcvUserIds();
            if (rcvUserIds != null && !rcvUserIds.isEmpty()) {
                String[] userIds = rcvUserIds.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }
    /**
     * 获取公文抄送人
     */

    public void getDocumentCarbonCopyUser() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            String csCommId = entityRecord.csCommId;
            // CcDocument
            CcDocument ccDocument = CcDocument.selectById(csCommId);
            String carbonCopyUserIds = ccDocument.getCarbonCopyUserIds();
            if (carbonCopyUserIds != null && !carbonCopyUserIds.isEmpty()) {
                String[] userIds = carbonCopyUserIds.split(",");
                ArrayList<String> userIdList = new ArrayList<>(Arrays.asList(userIds));
                ExtJarHelper.setReturnValue(userIdList);
            }
        }
    }

}
