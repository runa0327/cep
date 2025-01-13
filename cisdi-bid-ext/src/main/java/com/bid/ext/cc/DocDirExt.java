package com.bid.ext.cc;

import com.bid.ext.model.CcDocDir;
import com.bid.ext.model.CcDocDirAuth;
import com.bid.ext.model.CcPrjMember;
import com.bid.ext.utils.TemplateUtils;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

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
                if (!ccDocDir.getCcPrjId().equals(ccDocDir1.getCcPrjId())) {
                    throw new BaseException("不能将目录置于其他项目的目录下！");
                }
            }
        }


    }

    /**
     * 资料目录套用模板
     */
    public void docDirToTemplate() {
        InvokeActResult invokeActResult = new InvokeActResult();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_ID");
        String ccDocFolderTypeId = JdbcMapUtil.getString(varMap, "P_CC_DOC_FOLDER_TYPE_ID");
        String pCcDocDirAcceptanceTemplateTypeIds = JdbcMapUtil.getString(varMap, "P_CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_IDS");
        if (!SharedUtil.isEmpty(pCcDocDirAcceptanceTemplateTypeIds)) {
            List<String> pCcDocDirAcceptanceTemplateTypeIdList = Arrays.asList(pCcDocDirAcceptanceTemplateTypeIds.split(","));
            for (String pCcDocDirAcceptanceTemplateTypeId : pCcDocDirAcceptanceTemplateTypeIdList) {
                //查询该类型下所有模板的根节点
                String sql = "select t.id from cc_doc_dir t where t.CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID = ? and t.CC_DOC_DIR_PID is null and t.CC_DOC_FOLDER_TYPE_ID = 'acceptance' and t.IS_TEMPLATE = 1";
                List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(sql, pCcDocDirAcceptanceTemplateTypeId);
                for (Map<String, Object> map : queryForList) {
                    String templateRootId = JdbcMapUtil.getString(map, "ID");
                    //套用模板

                    List<Map<String, Object>> insertedNodes = TemplateUtils.applyTemplate(templateRootId, "CC_DOC_DIR", "ID", "CC_DOC_DIR_PID", true);
                    // 对插入后的节点进行统一处理
                    for (Map<String, Object> node : insertedNodes) {
//                String id = node.get("ID").toString();
                        String id = JdbcMapUtil.getString(node, "ID");
                        String copyFromId = JdbcMapUtil.getString(node, "COPY_FROM_ID");
                        CcDocDir ccDocDir = CcDocDir.selectById(copyFromId);
                        String ccAttachment = ccDocDir.getCcAttachment();
                        String updateSql = "UPDATE cc_doc_dir SET CC_PRJ_ID = ?, CC_DOC_FOLDER_TYPE_ID = ?, CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID = ?, CC_DOC_DIR_STATUS_ID = 'unlock', CC_ATTACHMENT = ? WHERE id = ?";
                        myJdbcTemplate.update(updateSql, ccPrjId, ccDocFolderTypeId, pCcDocDirAcceptanceTemplateTypeId, ccAttachment, id);
                    }
                }
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 判断目录是否为套用过模板的目录，用于更新后
     */
    public void isCopyFromDir() throws BaseException {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcDocDir ccDocDir = CcDocDir.selectById(csCommId);
            String copyFromId = ccDocDir.getCopyFromId();
            if (!SharedUtil.isEmpty(copyFromId)) {
                throw new BaseException("不能修改模板目录结构！");
            }
        }
    }

    /**
     * 删除模板目录
     */
    public void deleteTemplateDir() {
        InvokeActResult invokeActResult = new InvokeActResult();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_ID");
        String pCcDocDirAcceptanceTemplateTypeIds = JdbcMapUtil.getString(varMap, "P_CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_IDS");
        if (!SharedUtil.isEmpty(pCcDocDirAcceptanceTemplateTypeIds)){
            List<String> pCcDocDirAcceptanceTemplateTypeIdList = Arrays.asList(pCcDocDirAcceptanceTemplateTypeIds.split(","));
            for (String pCcDocDirAcceptanceTemplateTypeId : pCcDocDirAcceptanceTemplateTypeIdList){
                String sql = "select * from cc_doc_dir t where t.CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID = ? and t.CC_PRJ_ID = ?  and t.COPY_FROM_ID is null and t.CC_DOC_DIR_PID in (SELECT ID from cc_doc_dir where COPY_FROM_ID is not null and CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID = ? and CC_PRJ_ID = ?)";
                List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, pCcDocDirAcceptanceTemplateTypeId, ccPrjId, pCcDocDirAcceptanceTemplateTypeId, ccPrjId);
                for (Map<String, Object> map : list) {
                    String ccDocDirId = JdbcMapUtil.getString(map, "ID");
                    CcDocDir ccDocDir = CcDocDir.selectById(ccDocDirId);
                    ccDocDir.setCcDocDirPid(null);
                    ccDocDir.updateById();
                }
                String delSql = "delete from cc_doc_dir where CC_DOC_DIR_ACCEPTANCE_TEMPLATE_TYPE_ID = ? and CC_PRJ_ID = ? and COPY_FROM_ID is not null";
                myJdbcTemplate.update(delSql, pCcDocDirAcceptanceTemplateTypeId, ccPrjId);
            }
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 项目文档目录授权
     */
    public void ccDocDirAuthorize() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pCcMemberTreeIds = JdbcMapUtil.getString(varMap, "P_CC_MEMBER_TREE_IDS");
        Boolean pIsGrant = JdbcMapUtil.getBoolean(varMap, "P_IS_GRANT");
        Boolean pIsEdit = JdbcMapUtil.getBoolean(varMap, "P_IS_EDIT");
        Boolean pIsView = JdbcMapUtil.getBoolean(varMap, "P_IS_VIEW");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            if (pCcMemberTreeIds != null && !pCcMemberTreeIds.isEmpty()) {

                List<String> memberIdList = Arrays.asList(pCcMemberTreeIds.split(","));

                for (String memberId : memberIdList) {
                    CcPrjMember ccPrjMember = CcPrjMember.selectById(memberId);
                    String adUserId = ccPrjMember.getAdUserId();
                    CcDocDirAuth ccDocDirAuth = CcDocDirAuth.newData();
                    ccDocDirAuth.setAdUserId(adUserId);
                    ccDocDirAuth.setCcDocDirId(csCommId);
                    ccDocDirAuth.setIsGrant(pIsGrant);
                    ccDocDirAuth.setIsEdit(pIsEdit);
                    ccDocDirAuth.setIsView(pIsView);
                    ccDocDirAuth.insertById();
                }
            }
        }
    }

    /**
     * 创建目录时初始化文档权限（查看、编辑、授权）
     */
    public void initDocAuth() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
            String userId = loginInfo.userInfo.id;
            String csCommId = entityRecord.csCommId;
            CcDocDirAuth ccDocDirAuth = CcDocDirAuth.newData();
            ccDocDirAuth.setCcDocDirId(csCommId);
            ccDocDirAuth.setIsGrant(true);
            ccDocDirAuth.setIsEdit(true);
            ccDocDirAuth.setIsView(true);
            ccDocDirAuth.setAdUserId(userId);
            ccDocDirAuth.insertById();
        }
    }
}
