package com.bid.ext.cc;

import com.bid.ext.model.AdUser;
import com.bid.ext.model.CcCompany;
import com.bid.ext.model.CcPartyCompany;
import com.bid.ext.model.CcPrjMember;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;

public class PrjExt {
    /**
     * 项目成员名称同步用户名称
     */
    public void AdUserNameToCcPrjMemberName() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            CcPrjMember ccPrjMember = CcPrjMember.selectById(id);
            String adUserId = ccPrjMember.getAdUserId();
            AdUser adUser = AdUser.selectById(adUserId);
            String userName = adUser.getName();
            ccPrjMember.setName(userName);
            ccPrjMember.updateById();
        }
    }

    /**
     * 项目参建方公司名称同步公司名称
     */
    public void CcCompanyNameToCcPartyCompanyName() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = EntityRecordUtil.getId(entityRecord);
            CcPartyCompany ccPartyCompany = CcPartyCompany.selectById(id);
            String ccCompanyIds = ccPartyCompany.getCcCompanyIds();
            CcCompany ccCompany = CcCompany.selectById(ccCompanyIds);
            String ccCompanyName = ccCompany.getName();
            ccPartyCompany.setName(ccCompanyName);
            ccPartyCompany.updateById();
        }
    }
}
