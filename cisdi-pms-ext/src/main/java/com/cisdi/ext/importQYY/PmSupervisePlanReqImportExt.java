package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmPrjInvest3Import;
import com.cisdi.ext.importQYY.model.PmSupervisePlanReqImport;
import com.cisdi.ext.model.PmPrjInvest3;
import com.cisdi.ext.model.PmSupervisePlanReq;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PmSupervisePlanReqImportExt extends ImportUtil {
    @Override
    public Class getImportClass() {
        return PmSupervisePlanReqImport.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_SUPERVISE_PLAN_REQ where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        PmSupervisePlanReqImport pmSupervisePlanReqImport = (PmSupervisePlanReqImport) dtl;
        PmSupervisePlanReq pmSupervisePlanReq = PmSupervisePlanReq.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> pmSupervisePlanReqImport.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                pmSupervisePlanReq.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(pmSupervisePlanReqImport,pmSupervisePlanReq,"id");
        pmSupervisePlanReq.setIsImport(true);

        if (needUpdate) {
            pmSupervisePlanReq.updateById();
        }else{
            pmSupervisePlanReq.insertById();
        }

    }
}
