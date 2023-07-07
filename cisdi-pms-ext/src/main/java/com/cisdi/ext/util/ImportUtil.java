package com.cisdi.ext.util;

import com.alibaba.fastjson.JSONObject;
import com.cisdi.ext.importQYY.ImportSum;
import com.cisdi.ext.importQYY.model.ImportBatch;
import com.cisdi.ext.importQYY.model.PmPrjInvest3Import;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2023/6/8 周四
 * 企业云导入通用工具
 */
public abstract class ImportUtil {

    /**
     * 通用导入台账
     */
    public void importAccountCommon() throws Exception {
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
        if (!loginInfo.userCode.equalsIgnoreCase("admin")) {
            throw new BaseException("只有admin才能操作！");
        }

        //导入的类型
        Class importClass = getImportClass();
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            //历史导入数据
            List<Map<String, Object>> oldImportDataList = getOldData();
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为2
            ImportBatch batch = ImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq("IMPORT_BATCH_ID", csCommId);

            Object selectByWhere = importClass.getDeclaredMethod("selectByWhere", where.getClass()).invoke(null,where);
            List importList = JSONObject.parseArray(JSONObject.toJSONString(selectByWhere), importClass);

            if (!SharedUtil.isEmptyList(importList)) {
                for (Object dtlObject : importList) {
                    // 真正执行导入：
                    boolean succ = true;
                    String errorInfo = "";
                    try {
                        doImport(dtlObject,oldImportDataList);
                    }catch (Exception e){
                        succ = false;
                        errorInfo = e.toString();
                    }
                    new PmPrjInvest3Import().updateById();
                    //记录单条数据导入信息
                    importClass.getDeclaredMethod("setImportStatusId",String.class).invoke(dtlObject,"3");
                    importClass.getDeclaredMethod("setImportTime",LocalDateTime.class).invoke(dtlObject,LocalDateTime.now());
                    importClass.getDeclaredMethod("setIsSuccess",Boolean.class).invoke(dtlObject,succ);
                    importClass.getDeclaredMethod("setErrInfo",String.class).invoke(dtlObject,errorInfo);
                    importClass.getDeclaredMethod("updateById").invoke(dtlObject);
                    // 累计成功或失败数量：
                    if (succ) {
                        importSum.succCt++;
                    } else {
                        importSum.failCt++;
                    }
                }
            }

            // 对于批次，修改导入状态为3：
            batch.setImportStatusId("3");
            batch.setImportTime(LocalDateTime.now());
            batch.setSccCt(importSum.succCt);
            batch.setFailCt(importSum.failCt);
            batch.updateById();

        }
    }

    /**
     * 获取类型
     */
    public abstract Class getImportClass();

    /**
     * 获取历史导入数据，方便查重
     * @return
     */
    public abstract List<Map<String,Object>> getOldData();

    /**
     * 执行导入
     * @param dlt
     * @param oldImportDataList
     */
    public abstract void doImport(Object dlt, List<Map<String,Object>> oldImportDataList) throws Exception;
}
