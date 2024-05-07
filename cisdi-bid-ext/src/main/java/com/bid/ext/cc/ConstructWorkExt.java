package com.bid.ext.cc;

import com.bid.ext.model.CcConstructWork;
import com.bid.ext.model.CcConstructWorkProg;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ConstructWorkExt {

    /**
     * 填报施工工作进展
     */
    public void reportDailyWorkload() {
        InvokeActResult invokeActResult = new InvokeActResult();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pRemark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : null;
        String pAttachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : null;
        BigDecimal pActWbsPct = new BigDecimal(varMap.get("P_ACT_WBS_PCT").toString());
        String progTimeStr = varMap.get("P_PROG_TIME").toString(); // 获取日期时间字符串
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime pProgTime = LocalDateTime.parse(progTimeStr, formatter);
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcConstructWork ccConstructWork = CcConstructWork.selectById(csCommId);
            //插入进展
            CcConstructWorkProg ccConstructWorkProg = CcConstructWorkProg.insertData();
            ccConstructWorkProg.setProgTime(LocalDateTime.now());
            ccConstructWorkProg.setRemark(pRemark);
            ccConstructWorkProg.setCcAttachments(pAttachments);
            ccConstructWorkProg.setActWbsPct(pActWbsPct);
            ccConstructWorkProg.updateById();
            ccConstructWorkProg.setCcConstructWorkId(csCommId);
            ccConstructWorkProg.setCcPrjId(ccConstructWork.getCcPrjId());
            ccConstructWorkProg.setProgTime(pProgTime);
            ccConstructWorkProg.updateById();
            //更新最新情况
            ccConstructWork.setProgTime(pProgTime);
            ccConstructWork.setActWbsPct(pActWbsPct);
            ccConstructWork.updateById();
        }
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

}
