package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmLandUseReqImport;
import com.cisdi.ext.importQYY.model.PmPrjInvest3Import;
import com.cisdi.ext.model.PmLandUseReq;
import com.cisdi.ext.model.PmPrjInvest3;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PmLandUserReqImportExt extends ImportUtil {
    @Override
    public Class getImportClass() {
        return PmLandUseReqImport.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        return myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_LAND_USE_REQ where status = 'AP' and IS_IMPORT = 1 ");
    }

    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        PmLandUseReqImport pmLandUseReqImport = (PmLandUseReqImport) dtl;
        PmLandUseReq pmLandUseReq = PmLandUseReq.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> pmLandUseReqImport.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                pmLandUseReq.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(pmLandUseReqImport,pmLandUseReq,"id");
        pmLandUseReq.setIsImport(true);

        if (needUpdate) {
            pmLandUseReq.updateById();
        }else{
            pmLandUseReq.insertById();
        }

    }
}
