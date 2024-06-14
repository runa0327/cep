package com.bid.ext.cc;

import com.bid.ext.model.AdUser;
import com.bid.ext.model.CcPartyCompanyPost;
import com.bid.ext.model.CcPrjMember;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.DrivenInfo;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MemberExt {
    /**
     * 批量新增项目人员
     */
    public void batchInsertMember() {
        InvokeActResult invokeActResult = new InvokeActResult();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();

        for (Map.Entry<String, List<DrivenInfo>> entry : drivenInfosMap.entrySet()) {
            List<DrivenInfo> drivenInfos = entry.getValue();

            String pCcPartyCompanyPostId = null;
            if (drivenInfos != null) {
                Optional<String> value = drivenInfos.stream()
                        .filter(info -> "CC_PARTY_COMPANY_POST_ID".equals(info.code))
                        .map(info -> info.value)
                        .findFirst();
                if (value.isPresent()) {
                    pCcPartyCompanyPostId = value.get();
                }
            } else {
                throw new BaseException("请先创建岗位");
            }

            String pAdUserIds = JdbcMapUtil.getString(varMap, "P_AD_USER_IDS");
            Boolean pIsPrimaryPos = (Boolean) varMap.get("P_IS_PRIMARY_POS");
            if (pAdUserIds != null && !pAdUserIds.isEmpty()) {
                List<String> userIdList = Arrays.asList(pAdUserIds.split(","));
                for (String userId : userIdList) {
                    List<CcPrjMember> prjMembers = CcPrjMember.selectByWhere(new Where().eq(CcPrjMember.Cols.CC_PARTY_COMPANY_POST_ID, pCcPartyCompanyPostId).eq(CcPrjMember.Cols.AD_USER_ID, userId));
                    if (SharedUtil.isEmpty(prjMembers)) {
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
                            if (!SharedUtil.isEmpty(ccPrjMembers)) {
                                for (CcPrjMember ccPrjMember : ccPrjMembers) {
                                    ccPrjMember.setIsPrimaryPos(false);
                                    ccPrjMember.updateById();
                                }
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
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 预检测批量新增项目成员是否具有岗位
     */
    public void preCheckMember() {
        Map<String, List<DrivenInfo>> drivenInfosMap = ExtJarHelper.getDrivenInfosMap();
        if (SharedUtil.isEmpty(drivenInfosMap)) {
            throw new BaseException("请先创建岗位");
        }
    }
}
