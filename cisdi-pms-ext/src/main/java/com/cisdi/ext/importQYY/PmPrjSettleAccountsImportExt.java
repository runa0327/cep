package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PmPrjSettleAccountsImport;
import com.cisdi.ext.model.PmPrjSettleAccounts;
import com.cisdi.ext.util.ImportUtil;
import com.cisdi.ext.util.WfPmInvestUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 项目结算审批-批量导入
 */
public class PmPrjSettleAccountsImportExt extends ImportUtil {
    /**
     * 获取导入表对应的类型（需要企业云生成模板文件）
     */
    @Override
    public Class getImportClass() {
        return PmPrjSettleAccountsImport.class;
    }

    /**
     * 获取历史导入数据，方便查重
     *
     * @return
     */
    @Override
    public List<Map<String, Object>> getOldData() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> oldImports = myJdbcTemplate.queryForList("select id ,PM_PRJ_ID prjId from PM_PRJ_SETTLE_ACCOUNTS where status = 'AP' and IS_IMPORT = 1 ");
        return oldImports;
    }

    /**
     * 真正导入单条数据
     *
     * @param dtl               单条数据，需要强转
     * @param oldImportDataList 需要对比的旧数据
     */
    @Override
    public void doImport(Object dtl, List<Map<String, Object>> oldImportDataList) throws Exception {
        PmPrjSettleAccountsImport pmPrjSettleAccountsImport = (PmPrjSettleAccountsImport) dtl;
        PmPrjSettleAccounts pmPrjSettleAccounts = PmPrjSettleAccounts.newData();
        //项目结算不需要进行新老数据对比，仅进行批次核对，一个批次的项目只能导入一次
        boolean needUpdate = false;
//        if (!CollectionUtils.isEmpty(oldImportDataList)){
//            Optional<Map<String, Object>> samePrjIdData = oldImportDataList.stream()
//                    .filter(singleData -> pmPrjSettleAccountsImport.getPmPrjId().equals(singleData.get("prjId")))
//                    .findAny();
//            if (samePrjIdData.isPresent()){
//                needUpdate = true;
//                pmPrjSettleAccounts.setId(samePrjIdData.get().get("id").toString());
//            }
//        }
        BeanUtils.copyProperties(pmPrjSettleAccountsImport,pmPrjSettleAccounts,"id");
        pmPrjSettleAccounts.setIsImport(true);

        pmPrjSettleAccounts.insertById();

        WfPmInvestUtil.calculateData(pmPrjSettleAccounts.getId(), "PM_PRJ_SETTLE_ACCOUNTS", pmPrjSettleAccounts.getPmPrjId());

    }
}
