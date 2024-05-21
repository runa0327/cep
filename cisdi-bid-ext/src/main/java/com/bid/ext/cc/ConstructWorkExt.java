package com.bid.ext.cc;

import com.bid.ext.model.CcConstructWork;
import com.bid.ext.model.CcConstructWorkProg;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ConstructWorkExt {

    /**
     * 填报每日工作量
     */
    public void reportDailyWorkload() {
        InvokeActResult invokeActResult = new InvokeActResult();
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        String pRemark = varMap.get("P_REMARK") != null ? varMap.get("P_REMARK").toString() : null;
        String pAttachments = varMap.get("P_ATTACHMENTS") != null ? varMap.get("P_ATTACHMENTS").toString() : null;
        Integer pActWbsPct = JdbcMapUtil.getInt(varMap, "P_ACT_WBS_PCT");

        if (pActWbsPct > 100 || pActWbsPct < 0) {
            throw new BaseException("“实际进度比例”超出数据范围!");
        }
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
