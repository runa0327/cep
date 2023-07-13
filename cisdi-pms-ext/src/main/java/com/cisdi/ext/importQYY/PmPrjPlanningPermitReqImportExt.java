package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmPrjInvest3Import;
import com.cisdi.ext.importQYY.model.PmPrjPlanningPermitReqImport;
import com.cisdi.ext.model.PmPrjInvest3;
import com.cisdi.ext.model.PmPrjPlanningPermitReq;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PmPrjPlanningPermitReqImportExt extends ImportUtil {
    @Override
    public Class getImportClass() {
        return PmPrjPlanningPermitReqImport.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_PRJ_PLANNING_PERMIT_REQ where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        PmPrjPlanningPermitReqImport pmPrjPlanningPermitReqImport = (PmPrjPlanningPermitReqImport) dtl;
        PmPrjPlanningPermitReq pmPrjPlanningPermitReq = PmPrjPlanningPermitReq.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> pmPrjPlanningPermitReqImport.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                pmPrjPlanningPermitReq.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(pmPrjPlanningPermitReqImport,pmPrjPlanningPermitReq,"id");
        pmPrjPlanningPermitReq.setIsImport(true);

        if (needUpdate) {
            pmPrjPlanningPermitReq.updateById();
        }else{
            pmPrjPlanningPermitReq.insertById();
        }

    }
}
