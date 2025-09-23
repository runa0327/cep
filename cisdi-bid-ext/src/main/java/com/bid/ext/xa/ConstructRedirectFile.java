package com.bid.ext.xa;

import com.bid.ext.model.CcPrjStructNode;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;

import java.util.ArrayList;

public class ConstructRedirectFile {

    public void redirectFile() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPrjStructNode ccPrjStructNode = CcPrjStructNode.selectById(csCommId);
            String redirectUrl = ccPrjStructNode.getRedirectUrl();
            InvokeActResult invokeActResult = new InvokeActResult();
            invokeActResult.urlToOpenList = new ArrayList<>();

            UrlToOpen urlToOpen = new UrlToOpen();
            urlToOpen.url = redirectUrl;
            invokeActResult.urlToOpenList.add(urlToOpen);
            ExtJarHelper.setReturnValue(invokeActResult);
        }


    }

}
