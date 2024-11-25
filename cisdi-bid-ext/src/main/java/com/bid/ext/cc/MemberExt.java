package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.DrivenInfo;
import com.qygly.shared.interaction.EntityRecord;
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
//            Boolean pIsPrimaryPos = (Boolean) varMap.get("P_IS_PRIMARY_POS");
            Boolean pIsPrimaryPos = JdbcMapUtil.getBoolean(varMap, "P_IS_PRIMARY_POS");
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

    /**
     * 插入前扩展，项目参建方是否已存在
     */
    public void isCcPrjPartyExists() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPartyId = JdbcMapUtil.getString(valueMap, "CC_PARTY_ID");
            List<CcPrjParty> ccPrjParties = CcPrjParty.selectByWhere(
                    new Where().eq(CcPrjParty.Cols.CC_PRJ_ID, JdbcMapUtil.getString(valueMap, "CC_PRJ_ID"))
                            .eq(CcPrjParty.Cols.CC_PARTY_ID, ccPartyId)
            );
            if (ccPrjParties != null) {
                String sql = "SELECT `NAME`->'$.ZH_CN' AS NAME FROM CC_PARTY WHERE ID = ?";
                Map<String, Object> map = myJdbcTemplate.queryForMap(sql, ccPartyId);
                String name = JdbcMapUtil.getString(map, "NAME");
                throw new BaseException("参建方中已存在【" + name + "】");
            }
        }
    }

    /**
     * 插入前扩展，项目参建方公司是否已存在
     */
    public void isCcPrjCompanyExists() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccCompanyId = JdbcMapUtil.getString(valueMap, "CC_COMPANY_ID");
            List<CcPartyCompany> ccPartyCompanies = CcPartyCompany.selectByWhere(
                    new Where().eq(CcPartyCompany.Cols.CC_PRJ_ID, JdbcMapUtil.getString(valueMap, "CC_PRJ_ID"))
                            .eq(CcPartyCompany.Cols.CC_COMPANY_ID, ccCompanyId)
                            .eq(CcPartyCompany.Cols.CC_PRJ_PARTY_ID, JdbcMapUtil.getString(valueMap, "CC_PRJ_PARTY_ID"))
            );
            if (ccPartyCompanies != null) {
                String sql = "SELECT `NAME`->'$.ZH_CN' AS NAME FROM CC_COMPANY WHERE ID = ?";
                Map<String, Object> map = myJdbcTemplate.queryForMap(sql, ccCompanyId);
                String name = JdbcMapUtil.getString(map, "NAME");
                throw new BaseException("项目参建方公司中已存在【" + name + "】");
            }
        }
    }

    /**
     * 插入前扩展，项目参建方公司岗位是否已存在
     */
    public void isCcPrjPostExists() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            String ccPostId = JdbcMapUtil.getString(valueMap, "CC_POST_ID");
            List<CcPartyCompanyPost> ccPartyCompanyPosts = CcPartyCompanyPost.selectByWhere(
                    new Where().eq(CcPartyCompanyPost.Cols.CC_PRJ_ID, JdbcMapUtil.getString(valueMap, "CC_PRJ_ID"))
                            .eq(CcPartyCompanyPost.Cols.CC_POST_ID, ccPostId)
                            .eq(CcPartyCompanyPost.Cols.CC_PARTY_COMPANY_ID, JdbcMapUtil.getString(valueMap, "CC_PARTY_COMPANY_ID"))
            );
            if (ccPartyCompanyPosts != null) {
                String sql = "SELECT `NAME`->'$.ZH_CN' AS NAME FROM CC_POST WHERE ID = ?";
                Map<String, Object> map = myJdbcTemplate.queryForMap(sql, ccPostId);
                String name = JdbcMapUtil.getString(map, "NAME");
                throw new BaseException("项目参建方公司岗位中已存在【" + name + "】");
            }
        }


    }

    /**
     * 更新前扩展，更新项目参建方的【参建方】属性时同步更新项目参建方公司、项目参建方公司岗位、项目成员的【参建方】属性
     */
    public void updateCcPartyId() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcPrjParty ccPrjPartyBefore = CcPrjParty.selectById(csCommId);
            String ccPrjIdAft = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
            if (!(ccPrjIdAft == null ? "" : ccPrjIdAft).equals(ccPrjPartyBefore.getCcPrjId())) {
                throw new BaseException("不能修改参项目参建方所属项目！");
            }

            String ccPartyIdAft = JdbcMapUtil.getString(valueMap, "CC_PARTY_ID");
            String ccPartyIdBefore = ccPrjPartyBefore.getCcPartyId();
            if (!(ccPartyIdAft.equals(ccPartyIdBefore))) {
                List<CcPrjParty> ccPrjParties = CcPrjParty.selectByWhere(
                        new Where().eq(CcPrjParty.Cols.CC_PRJ_ID, ccPrjIdAft)
                                .eq(CcPrjParty.Cols.CC_PARTY_ID, ccPartyIdAft)
                );
                if (ccPrjParties != null) {
                    String sql = "SELECT `NAME`->'$.ZH_CN' AS NAME FROM CC_PARTY WHERE ID = ?";
                    Map<String, Object> map = myJdbcTemplate.queryForMap(sql, ccPartyIdAft);
                    String name = JdbcMapUtil.getString(map, "NAME");
                    throw new BaseException("参建方中已存在【" + name + "】");
                }

                // 更新项目参建方公司的【参建方】属性
                List<CcPartyCompany> ccPartyCompanies = CcPartyCompany.selectByWhere(new Where().eq(CcPartyCompany.Cols.CC_PRJ_PARTY_ID, csCommId).eq(CcPartyCompany.Cols.CC_PRJ_ID, ccPrjIdAft));
                if (ccPartyCompanies != null) {
                    for (CcPartyCompany ccPartyCompany : ccPartyCompanies) {
                        ccPartyCompany.setCcPartyId(ccPartyIdAft);
                        ccPartyCompany.updateById();
                    }
                }


                // 更新项目参建方公司岗位的【参建方】属性
                List<CcPartyCompanyPost> ccPartyCompanyPosts = CcPartyCompanyPost.selectByWhere(new Where().eq(CcPartyCompanyPost.Cols.CC_PRJ_PARTY_ID, csCommId).eq(CcPartyCompanyPost.Cols.CC_PRJ_ID, ccPrjIdAft));
                if (ccPartyCompanyPosts != null) {
                    for (CcPartyCompanyPost ccPartyCompanyPost : ccPartyCompanyPosts) {
                        ccPartyCompanyPost.setCcPartyId(ccPartyIdAft);
                        ccPartyCompanyPost.updateById();
                    }
                }


                // 更新项目成员的【参建方】属性
                List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByWhere(new Where().eq(CcPrjMember.Cols.CC_PRJ_PARTY_ID, csCommId).eq(CcPrjMember.Cols.CC_PRJ_ID, ccPrjIdAft));
                if (ccPrjMembers != null) {
                    for (CcPrjMember ccPrjMember : ccPrjMembers) {
                        ccPrjMember.setCcPartyId(ccPartyIdAft);
                        ccPrjMember.updateById();
                    }
                }

                InvokeActResult invokeActResult = new InvokeActResult();
                invokeActResult.reFetchData = true;
                ExtJarHelper.setReturnValue(invokeActResult);
            }
        }

    }

    /**
     * 更新前扩展，更新项目参建方公司的【公司】属性时同步更新项目参建方公司岗位、项目成员的【公司】属性
     */
    public void updateCcCompanyId() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcPartyCompany ccPartyCompanyBefore = CcPartyCompany.selectById(csCommId);
            String ccPrjIdAft = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
            if (!(ccPrjIdAft == null ? "" : ccPrjIdAft).equals(ccPartyCompanyBefore.getCcPrjId())) {
                throw new BaseException("不能修改项目参建方公司所属项目！");
            }

            String ccCompanyIdAft = JdbcMapUtil.getString(valueMap, "CC_COMPANY_ID");
            String ccCompanyIdBefore = ccPartyCompanyBefore.getCcCompanyId();
            if (!(ccCompanyIdAft.equals(ccCompanyIdBefore))) {
                List<CcPartyCompany> ccPartyCompanies = CcPartyCompany.selectByWhere(
                        new Where().eq(CcPartyCompany.Cols.CC_PRJ_ID, ccPrjIdAft)
                                .eq(CcPartyCompany.Cols.CC_COMPANY_ID, ccCompanyIdAft)
                                .eq(CcPartyCompany.Cols.CC_PRJ_PARTY_ID, JdbcMapUtil.getString(valueMap, "CC_PRJ_PARTY_ID"))
                );
                if (ccPartyCompanies != null) {
                    String sql = "SELECT `NAME`->'$.ZH_CN' AS NAME FROM CC_COMPANY WHERE ID = ?";
                    Map<String, Object> map = myJdbcTemplate.queryForMap(sql, ccCompanyIdAft);
                    String name = JdbcMapUtil.getString(map, "NAME");
                    throw new BaseException("项目参建方公司中已存在【" + name + "】");
                }

                // 更新项目参建方公司岗位的【公司】属性
                List<CcPartyCompanyPost> ccPartyCompanyPosts = CcPartyCompanyPost.selectByWhere(
                        new Where()
                                .eq(CcPartyCompanyPost.Cols.CC_PARTY_COMPANY_ID, csCommId)
                                .eq(CcPartyCompanyPost.Cols.CC_PRJ_ID, ccPrjIdAft)
                );
                if (ccPartyCompanyPosts != null) {
                    for (CcPartyCompanyPost ccPartyCompanyPost : ccPartyCompanyPosts) {
                        ccPartyCompanyPost.setCcCompanyId(ccCompanyIdAft);
                        ccPartyCompanyPost.updateById();
                    }
                }

                // 更新项目成员的【公司】属性
                List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByWhere(
                        new Where()
                                .eq(CcPrjMember.Cols.CC_PARTY_COMPANY_ID, csCommId)
                                .eq(CcPrjMember.Cols.CC_PRJ_ID, ccPrjIdAft)
                );
                if (ccPrjMembers != null) {
                    for (CcPrjMember ccPrjMember : ccPrjMembers) {
                        ccPrjMember.setCcCompanyId(ccCompanyIdAft);
                        ccPrjMember.updateById();
                    }
                }

                InvokeActResult invokeActResult = new InvokeActResult();
                invokeActResult.reFetchData = true;
                ExtJarHelper.setReturnValue(invokeActResult);
            }
        }
    }

    /**
     * 更新前扩展，更新项目参建方公司岗位的【岗位】属性时同步更新项目成员的【岗位】属性
     */
    public void updateCcPostId() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcPartyCompanyPost ccPartyCompanyPostBefore = CcPartyCompanyPost.selectById(csCommId);
            String ccPrjIdAft = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
            if (!(ccPrjIdAft == null ? "" : ccPrjIdAft).equals(ccPartyCompanyPostBefore.getCcPrjId())) {
                throw new BaseException("不能修改项目参建方公司岗位所属项目！");
            }

            String ccPostIdAft = JdbcMapUtil.getString(valueMap, "CC_POST_ID");
            String ccPostIdBefore = ccPartyCompanyPostBefore.getCcPostId();
            if (!(ccPostIdAft.equals(ccPostIdBefore))) {
                List<CcPartyCompanyPost> ccPartyCompanyPosts = CcPartyCompanyPost.selectByWhere(
                        new Where().eq(CcPartyCompanyPost.Cols.CC_PRJ_ID, ccPrjIdAft)
                                .eq(CcPartyCompanyPost.Cols.CC_POST_ID, ccPostIdAft)
                                .eq(CcPartyCompanyPost.Cols.CC_PARTY_COMPANY_ID, JdbcMapUtil.getString(valueMap, "CC_PARTY_COMPANY_ID"))
                );
                if (ccPartyCompanyPosts != null) {
                    String sql = "SELECT `NAME`->'$.ZH_CN' AS NAME FROM CC_POST WHERE ID = ?";
                    Map<String, Object> map = myJdbcTemplate.queryForMap(sql, ccPostIdAft);
                    String name = JdbcMapUtil.getString(map, "NAME");
                    throw new BaseException("项目参建方公司岗位中已存在【" + name + "】");
                }

                // 更新项目成员的【岗位】属性
                List<CcPrjMember> ccPrjMembers = CcPrjMember.selectByWhere(
                        new Where()
                                .eq(CcPrjMember.Cols.CC_PARTY_COMPANY_POST_ID, csCommId)
                                .eq(CcPrjMember.Cols.CC_PRJ_ID, ccPrjIdAft)
                );
                if (ccPrjMembers != null) {
                    for (CcPrjMember ccPrjMember : ccPrjMembers) {
                        ccPrjMember.setCcPostId(ccPostIdAft);
                        ccPrjMember.updateById();
                    }
                }

                InvokeActResult invokeActResult = new InvokeActResult();
                invokeActResult.reFetchData = true;
                ExtJarHelper.setReturnValue(invokeActResult);
            }
        }
    }

}
