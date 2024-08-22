package com.bid.ext.cc;

import com.bid.ext.model.CcDocDir;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;

import java.util.prefs.BackingStoreException;

public class DocDirExt {

    /**
     * 判断目录和父目录是否为同一项目下，用于更新后
     */
    public void isSamePrjWithFa() throws BaseException {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;

            CcDocDir ccDocDir = CcDocDir.selectById(csCommId);
            String ccDocDirPid = ccDocDir.getCcDocDirPid();

            if (ccDocDirPid != null) {
                CcDocDir ccDocDir1 = CcDocDir.selectById(ccDocDirPid);
                if(!ccDocDir.getCcPrjId().equals(ccDocDir1.getCcPrjId())) {
                    throw new BaseException("不能将目录置于其他项目的目录下！");
                }
            }
        }


    }
}
