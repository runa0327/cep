package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmSupervisePlanReqImport;
import com.cisdi.ext.importQYY.model.SkillDisclosurePaperRecheckRecordImport;
import com.cisdi.ext.model.PmSupervisePlanReq;
import com.cisdi.ext.model.SkillDisclosurePaperRecheckRecord;
import com.cisdi.ext.util.ImportUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class SkillDisclosurePaperRecheckRecordImportExt extends ImportUtil {
    @Override
    public Class getImportClass() {
        return SkillDisclosurePaperRecheckRecordImport.class;
    }

    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from SKILL_DISCLOSURE_PAPER_RECHECK_RECORD where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        SkillDisclosurePaperRecheckRecordImport skillDisclosurePaperRecheckRecordImport = (SkillDisclosurePaperRecheckRecordImport) dtl;
        SkillDisclosurePaperRecheckRecord skillDisclosurePaperRecheckRecord = SkillDisclosurePaperRecheckRecord.newData();
        //比对是否导入过
        boolean needUpdate = false;
        if (!CollectionUtils.isEmpty(oldImportDataList)){
            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
                    .filter(singleData -> skillDisclosurePaperRecheckRecordImport.getPmPrjId().equals(singleData.get("prjId")))
                    .findAny();
            if (samePrjIdData.isPresent()){
                needUpdate = true;
                skillDisclosurePaperRecheckRecord.setId(samePrjIdData.get().get("id").toString());
            }
        }
        BeanUtils.copyProperties(skillDisclosurePaperRecheckRecordImport,skillDisclosurePaperRecheckRecord,"id");
        skillDisclosurePaperRecheckRecord.setIsImport(true);

        if (needUpdate) {
            skillDisclosurePaperRecheckRecord.updateById();
        }else{
            skillDisclosurePaperRecheckRecord.insertById();
        }

    }
}
