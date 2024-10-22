package com.bid.ext.cc;

import com.bid.ext.model.CcDocDir;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

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
        String pCcDocFolderTypeId = JdbcMapUtil.getString(varMap, "P_CC_DOC_FOLDER_TYPE_ID");
        String sql = "select * from cc_doc_dir t where t.CC_DOC_FOLDER_TYPE_ID = ? and t.CC_PRJ_ID = ?  and t.COPY_FROM_ID is null and t.CC_DOC_DIR_PID in (SELECT ID from cc_doc_dir where COPY_FROM_ID is not null and CC_DOC_FOLDER_TYPE_ID = ? and CC_PRJ_ID = ?)";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, pCcDocFolderTypeId, ccPrjId, pCcDocFolderTypeId, ccPrjId);
        for (Map<String, Object> map : list) {
            String ccDocDirId = JdbcMapUtil.getString(map, "ID");
            CcDocDir ccDocDir = CcDocDir.selectById(ccDocDirId);
            ccDocDir.setCcDocDirPid(null);
            ccDocDir.updateById();
        }
        String delSql = "delete from cc_doc_dir where CC_DOC_FOLDER_TYPE_ID = ? and CC_PRJ_ID = ? and COPY_FROM_ID is not null";
        myJdbcTemplate.update(delSql, pCcDocFolderTypeId, ccPrjId);
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }
}
