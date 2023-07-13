package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmPrjInvest3Import;
import com.cisdi.ext.importQYY.model.PmWorkListReqImport;
import com.cisdi.ext.model.PmPrjInvest3;
import com.cisdi.ext.model.PmWorkListReq;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PmWorkListReqImportExt extends ImportUtil {
    @Override
    public Class getImportClass() {
        return PmWorkListReqImport.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_WORK_LIST_REQ where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        PmWorkListReqImport pmWorkListReqImport = (PmWorkListReqImport) dtl;
        PmWorkListReq pmWorkListReq = PmWorkListReq.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> pmWorkListReqImport.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                pmWorkListReq.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(pmWorkListReqImport,pmWorkListReq,"id");
        pmWorkListReq.setIsImport(true);

        if (needUpdate) {
            pmWorkListReq.updateById();
        }else{
            pmWorkListReq.insertById();
        }

    }
}
