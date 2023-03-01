package com.cisdi.ext.importQYY;

import com.cisdi.ext.importQYY.model.PrjReqImport;
import com.cisdi.ext.importQYY.model.PrjReqImportBatch;
import com.cisdi.ext.model.PmPrj;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityInfo;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.ad.sev.SevInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PrjReqImportBatchExt {

    public static final Map<String, String> investMap = new HashMap<>();

    static {
        investMap.put("ESTIMATED_TOTAL_INVEST", "PRJ_TOTAL_INVEST"); // 总投资
        investMap.put("CONSTRUCT_PRJ_AMT", "CONSTRUCT_AMT"); // 建安工程费
        investMap.put("EQUIP_BUY_AMT", "EQUIP_AMT"); // 设备采购费
        investMap.put("EQUIPMENT_COST", "SCIENTIFIC_EQUIPMENT_AMT"); // 科研设备费
        investMap.put("PROJECT_OTHER_AMT", "PROJECT_AMT-OTHER"); // 工程其他费用
        investMap.put("LAND_BUY_AMT", "LAND_AMT"); // 土地征迁费
        investMap.put("PREPARE_AMT", "PREPARE_AMT"); // 预备费
    }

    /**
     * 生成明细。
     */
    public void generateDtl() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 获取批准的项目：
            List<PmPrj> pmPrjList = PmPrj.selectByWhere(new Where().eq(PmPrj.Cols.STATUS, "AP"));
            if (!SharedUtil.isEmptyList(pmPrjList)) {
                // 先按名称排序，再逐条取数并插入明细：
                pmPrjList.stream().sorted((o1, o2) -> {
                            if (o1.getName() == null && o2.getName() == null) {
                                return 0;
                            } else if (o1.getName() == null && o2.getName() != null) {
                                return -1;
                            } else if (o1.getName() != null && o2.getName() == null) {
                                return 1;
                            } else {
                                return o1.getName().compareTo(o2.getName());
                            }
                        })
                        // .skip(50).limit(5)
                        .forEach(pmPrj -> {
                            PrjReqImport prjReqImport = doGetDtl(pmPrj);
                            prjReqImport.setPrjReqImportBatchId(csCommId);
                            prjReqImport.insertById();
                        });

            }
        }
    }

    /**
     * 真正获取明细。
     *
     * @param pmPrj
     */
    private PrjReqImport doGetDtl(PmPrj pmPrj) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        PrjReqImport prjReqImport = PrjReqImport.newData();
        String prjId = pmPrj.getId();
        prjReqImport.setPmPrjId(prjId); // 项目
        prjReqImport.setCustomerUnit(pmPrj.getCustomerUnit()); // 业主单位
        prjReqImport.setPrjManageModeId(pmPrj.getPrjManageModeId()); // 项目管理模式
        prjReqImport.setBaseLocationId(pmPrj.getBaseLocationId()); // 建设地点
        prjReqImport.setProjectTypeId(pmPrj.getProjectTypeId()); // 项目类型
        prjReqImport.setPrjSituation(pmPrj.getPrjSituation()); // 项目介绍
        prjReqImport.setConScaleTypeId(pmPrj.getConScaleTypeId()); // 建设规模类型
        prjReqImport.setFloorArea(pmPrj.getFloorArea()); // 占地面积
        prjReqImport.setBuildingArea(pmPrj.getBuildingArea()); // 建筑面积
        prjReqImport.setRoadLength(pmPrj.getConScaleQty()); // 道路长度
        prjReqImport.setRoadWidth(pmPrj.getConScaleQty2()); // 道路宽度
        prjReqImport.setOther(pmPrj.getOther()); // 其他

        prjReqImport.setInvestmentSourceId(pmPrj.getInvestmentSourceId()); // 投资来源

        BigDecimal prjTotalInvest = prjAmt(pmPrj.getEstimatedTotalInvest(), prjId, "ESTIMATED_TOTAL_INVEST", myJdbcTemplate);
        prjReqImport.setEstimatedTotalInvest(prjTotalInvest); // 总投资

        BigDecimal constructPrjAmt = prjAmt(pmPrj.getConstructPrjAmt(), prjId, "CONSTRUCT_PRJ_AMT", myJdbcTemplate);
        prjReqImport.setConstructPrjAmt(constructPrjAmt); // 建安工程费

        BigDecimal equipBuyAmt = prjAmt(pmPrj.getEquipBuyAmt(), prjId, "EQUIP_BUY_AMT", myJdbcTemplate);
        prjReqImport.setEquipBuyAmt(equipBuyAmt); // 设备采购费

        BigDecimal equipmentCost = prjAmt(pmPrj.getEquipmentCost(), prjId, "EQUIPMENT_COST", myJdbcTemplate);
        prjReqImport.setEquipmentCost(equipmentCost); // 科研设备费

        BigDecimal projectOtherAmt = prjAmt(pmPrj.getProjectOtherAmt(), prjId, "PROJECT_OTHER_AMT", myJdbcTemplate);
        prjReqImport.setProjectOtherAmt(projectOtherAmt); // 工程其他费用

        BigDecimal landBuyAmt = prjAmt(pmPrj.getLandBuyAmt(), prjId, "LAND_BUY_AMT", myJdbcTemplate);
        prjReqImport.setLandBuyAmt(landBuyAmt); // 土地征迁费

        BigDecimal prepareAmt = prjAmt(pmPrj.getPrepareAmt(), prjId, "PREPARE_AMT", myJdbcTemplate);
        prjReqImport.setPrepareAmt(prepareAmt); // 预备费

        prjReqImport.setProjectProposalDate(pmPrj.getProjectProposalActualDate()); // 项目建议书完成日期
        prjReqImport.setProjectProposalAuthor(pmPrj.getAuthor()); // 项目建议书编制人
        prjReqImport.setProjectProposalFile(pmPrj.getStampedPrjReqFile()); // 项目建议书
        prjReqImport.setPrjCode(pmPrj.getPrjCode()); // 项目编号

        prjReqImport.setReplyNo(pmPrj.getPrjReplyNo()); // 批复文号
        prjReqImport.setReplyDate(pmPrj.getPrjReplyDate()); // 批复日期
        prjReqImport.setReplyFile(pmPrj.getPrjReplyFile()); // 批复文件
        return prjReqImport;
    }

    /**
     * 查询资金赋值信息
     *
     * @param val            原值
     * @param prjId          项目id
     * @param code           需要匹配的字段
     * @param myJdbcTemplate 数据源
     * @return
     */
    private BigDecimal prjAmt(BigDecimal val, String prjId, String code, MyJdbcTemplate myJdbcTemplate) {
        String trueCode = getCode(code, investMap);
        BigDecimal amtValue = new BigDecimal(0);
        if (val == null) {
            // 从项目投资测算明细取数
            String sql1 = "select a.amt from PM_INVEST_EST_DTL a left join PM_INVEST_EST b on a.PM_INVEST_EST_ID = b.id " +
                    "left join PM_EXP_TYPE c on a.PM_EXP_TYPE_ID = c.id where b.PM_PRJ_ID = ? and c.code = ?";
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql1, prjId, trueCode);
            if (!CollectionUtils.isEmpty(list1)) {
                String amt = JdbcMapUtil.getString(list1.get(0), "amt");
                if (!SharedUtil.isEmptyString(amt)) {
                    amtValue = new BigDecimal(amt).divide(new BigDecimal(10000));
                } else {
                    amtValue = new BigDecimal(0);
                }
            } else {
                amtValue = new BigDecimal(0);
            }
        } else {
            amtValue = val;
        }
        return amtValue;
    }

    /**
     * 根据立项中的字段获取测算中的字段
     *
     * @param code
     * @param investMap
     * @return
     */
    private String getCode(String code, Map<String, String> investMap) {
        String value = "";
        for (Map.Entry entry : investMap.entrySet()) {
            String key = (String) entry.getKey();
            if (code.equals(key)) {
                value = (String) entry.getValue();
                break;
            }
        }
        return value;
    }

    /**
     * 准予导入。
     */
    public void allowImport() {
        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为1，再修改导入状态为2：
            PrjReqImportBatch batch = PrjReqImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("1")) {
                throw new BaseException("只有导入状态为“1-数据收集”才能操作！");
            }
            batch.setImportStatusId("2");
            batch.updateById();

            // 对于明细，修改导入状态为2：
            Where where = new Where().eq(PrjReqImport.Cols.PRJ_REQ_IMPORT_BATCH_ID, csCommId);
            HashMap<String, Object> keyValueMap = new HashMap<>();
            keyValueMap.put(PrjReqImport.Cols.IMPORT_STATUS_ID, "2");
            PrjReqImport.updateByWhere(where, keyValueMap);
        }
    }


    /**
     * 导入项目。
     */
    public void importPrj() {
        LoginInfo loginInfo = ExtJarHelper.loginInfo.get();
        if (!loginInfo.userCode.equalsIgnoreCase("admin")) {
            throw new BaseException("只有admin才能操作！");
        }

        if ("1".equals("1")) {
            throw new BaseException("导入功能实现中...暂未上线！");
        }

        SevInfo sevInfo = ExtJarHelper.sevInfo.get();
        EntityInfo entityInfo = sevInfo.entityInfo;
        List<EntityRecord> entityRecordList = ExtJarHelper.entityRecordList.get();
        for (EntityRecord entityRecord : entityRecordList) {
            String csCommId = entityRecord.csCommId;

            // 对于批次，先检查状态是否为2
            PrjReqImportBatch batch = PrjReqImportBatch.selectById(csCommId);
            if (!batch.getImportStatusId().equals("2")) {
                throw new BaseException("只有导入状态为“2-准予导入”才能操作！");
            }

            ImportSum importSum = new ImportSum();
            Where where = new Where().eq(PrjReqImport.Cols.PRJ_REQ_IMPORT_BATCH_ID, csCommId);
            List<PrjReqImport> prjReqImportList = PrjReqImport.selectByWhere(where);
            if (!SharedUtil.isEmptyList(prjReqImportList)) {
                for (PrjReqImport prjReqImport : prjReqImportList) {
                    // 真正执行导入：
                    boolean succ = doImportPrj(prjReqImport);
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
     * 真正执行导入项目。
     *
     * @return 是否成功。
     */
    private boolean doImportPrj(PrjReqImport newImport) {
        boolean succ = true;
        List<String> errInfoList = new ArrayList<>();
        String newImportId = newImport.getId();

        // 无论新、旧，项目ID都是相同的，通过项目ID获取项目：
        String pmPrjId = newImport.getPmPrjId();

        // 通过项目ID，获取旧的导入记录：
        PmPrj pmPrj = PmPrj.selectById(pmPrjId);
        PrjReqImport oldImport = doGetDtl(pmPrj);

        // 若字段的值已不同，则予以处理：

        // 示例，处理某个字段：
        try {
            if (!SharedUtil.toStringEquals(oldImport.getCustomerUnit(), newImport.getCustomerUnit())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.CUSTOMER_UNIT, newImport.getCustomerUnit());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
        } catch (Exception ex) {
            succ = false;
            errInfoList.add(ex.toString());
        }

        // TODO 其他字段的处理逻辑。

        // 执行过程中，可能会自动抛出异常。
        // 若希望自行抛出异常，则throw：
        // throw new BaseException("业务异常！");
        // 但要记得catch住：
        // catch (Exception ex) {
        //     succ = false;
        //     errInfoList.add(ex.toString());
        // }

        HashMap<String, Object> keyValueMap = new HashMap<>();
        keyValueMap.put(PrjReqImport.Cols.IMPORT_STATUS_ID, "3");
        keyValueMap.put(PrjReqImport.Cols.IMPORT_TIME, LocalDateTime.now());
        keyValueMap.put(PrjReqImport.Cols.IS_SUCCESS, succ);
        keyValueMap.put(PrjReqImport.Cols.ERR_INFO, SharedUtil.isEmptyList(errInfoList) ? null : errInfoList.stream().collect(Collectors.joining("；")));
        PrjReqImport.updateById(newImportId, keyValueMap);

        return succ;
    }
}
