package com.bid.ext.cc;

import com.bid.ext.model.AdUser;
import com.bid.ext.model.CcCompany;
import com.bid.ext.model.CcPartyCompany;
import com.bid.ext.model.CcPrjMember;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.EntityRecordUtil;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

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
            String ccCompanyId = ccPartyCompany.getCcCompanyId();
            CcCompany ccCompany = CcCompany.selectById(ccCompanyId);
            String ccCompanyName = ccCompany.getName();
            ccPartyCompany.setName(ccCompanyName);
            ccPartyCompany.updateById();
        }
    }

    public void dateCheck() throws Exception {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            Map<String, Object> valueMap = entityRecord.valueMap;
            Object fromDate = valueMap.get("FROM_DATE");
            Object toDate = valueMap.get("TO_DATE");
            if (!SharedUtil.isEmpty(toDate)) {
                LocalDate from = LocalDate.parse((String) fromDate, formatter);
                LocalDate to = LocalDate.parse((String) toDate, formatter);
                if (from.isAfter(to)) {
                    throw new Exception("请检查并确保开工日期早于竣工日期");
                }
            }
        }
    }
}
