package com.bid.ext.cc;

import com.bid.ext.model.AdUser;
import com.bid.ext.model.CcPartyCompanyPost;
import com.bid.ext.model.CcPrjMember;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MemberExt {
    public void batchInsertMember() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pAdUserIds = JdbcMapUtil.getString(varMap, "P_AD_USER_IDS");
        Boolean pIsPrimaryPos = (Boolean) varMap.get("P_IS_PRIMARY_POS");
        String pCcPartyCompanyPostId = JdbcMapUtil.getString(varMap, "P_CC_PARTY_COMPANY_POST_ID");
        if (pAdUserIds != null && !pAdUserIds.isEmpty()) {
            List<String> userIdList = Arrays.asList(pAdUserIds.split(","));
            for (String userId : userIdList) {
                //获取岗位信息
                CcPartyCompanyPost ccPartyCompanyPost = CcPartyCompanyPost.selectById(pCcPartyCompanyPostId);
                //公司
                String ccCompanyId = ccPartyCompanyPost.getCcCompanyId();
                String ccPartyCompanyId = ccPartyCompanyPost.getCcPartyCompanyId();
                //岗位
                String ccPostId = ccPartyCompanyPost.getCcPostId();
                //项目
                String ccPrjId = ccPartyCompanyPost.getCcPrjId();
                //参建方
                String ccPartyId = ccPartyCompanyPost.getCcPartyId();
                String ccPrjPartyId = ccPartyCompanyPost.getCcPrjPartyId();

                //用户
                AdUser adUser = AdUser.selectById(userId);
                String userName = adUser.getName();

                //新增、编辑为主岗时，查询成员在项目内是否存在主岗，若存在,则将项目内其他岗位的此用户改为不为主岗
                if (pIsPrimaryPos) {
                    List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByWhere(new Where().eq(CcPrjMember.Cols.CC_PRJ_ID, ccPrjId).eq(CcPrjMember.Cols.AD_USER_ID, userId));
                    for (CcPrjMember ccPrjMember : ccPrjMembers) {
                        ccPrjMember.setIsPrimaryPos(false);
                        ccPrjMember.updateById();
                    }
                }

                //插入成员
                CcPrjMember ccPrjMember = CcPrjMember.newData();
                ccPrjMember.setAdUserId(userId);
                ccPrjMember.setName(userName);
                ccPrjMember.setCcPostId(ccPostId);
                ccPrjMember.setCcPartyCompanyPostId(pCcPartyCompanyPostId);
                ccPrjMember.setCcCompanyId(ccCompanyId);
                ccPrjMember.setCcPartyCompanyId(ccPartyCompanyId);
                ccPrjMember.setCcPartyId(ccPartyId);
                ccPrjMember.setCcPrjPartyId(ccPrjPartyId);
                ccPrjMember.setCcPrjId(ccPrjId);
                ccPrjMember.setIsPrimaryPos(pIsPrimaryPos);
                ccPrjMember.insertById();

            }
        }

    }
}
