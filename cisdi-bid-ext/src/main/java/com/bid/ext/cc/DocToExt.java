package com.bid.ext.cc;

import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DocToExt {
    /**
     * 竣工资料关联项目文档
     */
    public void acceptanceToDoc() {
        InvokeActResult invokeActResult = new InvokeActResult();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String ccPrjId = JdbcMapUtil.getString(varMap, "P_CC_PRJ_ID");
        String ccDocDirId = JdbcMapUtil.getString(varMap, "P_DOC_DIR_ID");
        String ccDocFileIdAll = JdbcMapUtil.getString(varMap, "P_CC_DOC_FILE_ID_ALL");
        String[] ccDocFiles = ccDocFileIdAll.split(",");
        ArrayList<String> ccDocFileList = new ArrayList<>(Arrays.asList(ccDocFiles));
        LocalDate now = LocalDate.now();
        for (String ccDocFileId : ccDocFileList) {
            CcDocFile ccDocFile = CcDocFile.selectById(ccDocFileId);
            String ccAttachment = ccDocFile.getCcAttachment();
            String ccDocFileTypeId = ccDocFile.getCcDocFileTypeId();
            FlFile flFile = FlFile.selectById(ccAttachment);
            String flFileName = flFile.getName();
            CcDocFile ccDocFileAcceptance = CcDocFile.newData();
            ccDocFileAcceptance.setName(flFileName);
            ccDocFileAcceptance.setCcPrjId(ccPrjId);
            ccDocFileAcceptance.setCcDocDirId(ccDocDirId);
            ccDocFileAcceptance.setCcDocDate(now);
            ccDocFileAcceptance.setCcDocFileTypeId(ccDocFileTypeId);
            ccDocFileAcceptance.setCcAttachment(ccAttachment);
            ccDocFileAcceptance.setCcDocFileFrom(5);
            ccDocFileAcceptance.insertById();
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }
}
