package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmFarmingProceduresImport;
import com.cisdi.ext.model.PmFarmingProcedures;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PmFarmingProceduresImportExt extends ImportUtil {

    @Override
    public Class getImportClass() {
        return PmFarmingProceduresImport.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_FARMING_PROCEDURES where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        PmFarmingProceduresImport pmFarmingProceduresImport = (PmFarmingProceduresImport) dtl;
        PmFarmingProcedures pmFarmingProcedures = PmFarmingProcedures.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> pmFarmingProceduresImport.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                pmFarmingProcedures.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(pmFarmingProceduresImport,pmFarmingProcedures,"id");
        pmFarmingProcedures.setIsImport(true);

        if (needUpdate) {
            pmFarmingProcedures.updateById();
        }else{
            pmFarmingProcedures.insertById();
        }

    }
}
