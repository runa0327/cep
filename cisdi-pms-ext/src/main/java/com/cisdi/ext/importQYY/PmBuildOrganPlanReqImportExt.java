package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmBuildOrganPlanReqImport;
import com.cisdi.ext.model.PmBuildOrganPlanReq;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PmBuildOrganPlanReqImportExt extends ImportUtil {
    @Override
    public Class getImportClass() {
        return PmBuildOrganPlanReqImport.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_BUILD_ORGAN_PLAN_REQ where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        PmBuildOrganPlanReqImport pmBuildOrganPlanReqImport = (PmBuildOrganPlanReqImport) dtl;
        PmBuildOrganPlanReq pmBuildOrganPlanReq = PmBuildOrganPlanReq.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> pmBuildOrganPlanReqImport.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                pmBuildOrganPlanReq.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(pmBuildOrganPlanReqImport,pmBuildOrganPlanReq,"id");
        pmBuildOrganPlanReq.setIsImport(true);

        if (needUpdate) {
            pmBuildOrganPlanReq.updateById();
        }else{
            pmBuildOrganPlanReq.insertById();
        }

    }
}
