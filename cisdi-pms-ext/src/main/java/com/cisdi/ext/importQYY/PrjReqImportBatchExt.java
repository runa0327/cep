package com.cisdi.ext.importQYY;

import com.cisdi.ext.base.PmInvestEst;
import com.cisdi.ext.importQYY.model.PrjReqImport;
import com.cisdi.ext.importQYY.model.PrjReqImportBatch;
import com.cisdi.ext.model.PmPrj;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
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
import java.util.*;
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
                    amtValue = new BigDecimal(amt);
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

//        if ("1".equals("1")) {
//            throw new BaseException("导入功能实现中...暂未上线！");
//        }

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
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        boolean succ = true;
        List<String> errInfoList = new ArrayList<>();
        String newImportId = newImport.getId();

        // 无论新、旧，项目ID都是相同的，通过项目ID获取项目：
        String pmPrjId = newImport.getPmPrjId();

        // 通过项目ID，获取旧的导入记录：
        PmPrj pmPrj = PmPrj.selectById(pmPrjId);
        PrjReqImport oldImport = doGetDtl(pmPrj);

        // 若字段的值已不同，则予以处理：
        String projectName = pmPrj.getName();
        newImport.setName(projectName);

        // 示例，处理某个字段：
        try {
            //业主单位
            if (!SharedUtil.toStringEquals(oldImport.getCustomerUnit(), newImport.getCustomerUnit())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.CUSTOMER_UNIT, newImport.getCustomerUnit());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //项目管理模式
            if (!SharedUtil.toStringEquals(oldImport.getPrjManageModeId(), newImport.getPrjManageModeId())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PRJ_MANAGE_MODE_ID, newImport.getPrjManageModeId());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //建设地点
            if (!SharedUtil.toStringEquals(oldImport.getBaseLocationId(), newImport.getBaseLocationId())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.BASE_LOCATION_ID, newImport.getBaseLocationId());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //项目类型
            if (!SharedUtil.toStringEquals(oldImport.getProjectTypeId(), newImport.getProjectTypeId())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PROJECT_TYPE_ID, newImport.getProjectTypeId());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //项目介绍
            if (!SharedUtil.toStringEquals(oldImport.getPrjSituation(), newImport.getPrjSituation())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PRJ_SITUATION, newImport.getPrjSituation());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //建设规模类型
            if (!SharedUtil.toStringEquals(oldImport.getConScaleTypeId(), newImport.getConScaleTypeId())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.CON_SCALE_TYPE_ID, newImport.getConScaleTypeId());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //占地面积
            if (!SharedUtil.toStringEquals(oldImport.getFloorArea(), newImport.getFloorArea())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.FLOOR_AREA, newImport.getFloorArea());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //建筑面积
            if (!SharedUtil.toStringEquals(oldImport.getBuildingArea(), newImport.getBuildingArea())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.BUILDING_AREA, newImport.getBuildingArea());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //道路长度
            if (!SharedUtil.toStringEquals(oldImport.getRoadLength(), newImport.getRoadLength())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.CON_SCALE_QTY, newImport.getRoadLength());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //道路宽度
            if (!SharedUtil.toStringEquals(oldImport.getRoadWidth(), newImport.getRoadWidth())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.CON_SCALE_QTY2, newImport.getRoadWidth());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //投资来源
            if (!SharedUtil.toStringEquals(oldImport.getInvestmentSourceId(), newImport.getInvestmentSourceId())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.INVESTMENT_SOURCE_ID, newImport.getInvestmentSourceId());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //总投资
            if (!SharedUtil.toStringEquals(oldImport.getEstimatedTotalInvest(), newImport.getEstimatedTotalInvest())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.ESTIMATED_TOTAL_INVEST, newImport.getEstimatedTotalInvest());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //建安工程费
            if (!SharedUtil.toStringEquals(oldImport.getConstructPrjAmt(), newImport.getConstructPrjAmt())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.CONSTRUCT_PRJ_AMT, newImport.getConstructPrjAmt());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //设备采购费
            if (!SharedUtil.toStringEquals(oldImport.getEquipBuyAmt(), newImport.getEquipBuyAmt())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.EQUIP_BUY_AMT, newImport.getEquipBuyAmt());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //科研设备费
            if (!SharedUtil.toStringEquals(oldImport.getEquipmentCost(), newImport.getEquipmentCost())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.EQUIPMENT_COST, newImport.getEquipmentCost());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //工程其他费用
            if (!SharedUtil.toStringEquals(oldImport.getProjectOtherAmt(), newImport.getProjectOtherAmt())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PROJECT_OTHER_AMT, newImport.getProjectOtherAmt());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //土地征迁费
            if (!SharedUtil.toStringEquals(oldImport.getLandBuyAmt(), newImport.getLandBuyAmt())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.LAND_BUY_AMT, newImport.getLandBuyAmt());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //预备费
            if (!SharedUtil.toStringEquals(oldImport.getPrepareAmt(), newImport.getPrepareAmt())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PREPARE_AMT, newImport.getPrepareAmt());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //项目建议书完成日期
            if (!SharedUtil.toStringEquals(oldImport.getProjectProposalDate(), newImport.getProjectProposalDate())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PROJECT_PROPOSAL_ACTUAL_DATE, newImport.getProjectProposalDate());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //项目建议书编制人
            if (!SharedUtil.toStringEquals(oldImport.getProjectProposalAuthor(), newImport.getProjectProposalAuthor())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.AUTHOR, newImport.getProjectProposalAuthor());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //项目建议书
            if (!SharedUtil.toStringEquals(oldImport.getProjectProposalFile(), newImport.getProjectProposalFile())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.STAMPED_PRJ_REQ_FILE, newImport.getProjectProposalFile());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //项目编号
            if (!SharedUtil.toStringEquals(oldImport.getPrjCode(), newImport.getPrjCode())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PRJ_CODE, newImport.getPrjCode());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //批复文号
            if (!SharedUtil.toStringEquals(oldImport.getReplyNo(), newImport.getReplyNo())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PRJ_REPLY_NO, newImport.getReplyNo());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //批复日期
            if (!SharedUtil.toStringEquals(oldImport.getReplyDate(), newImport.getReplyDate())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PRJ_REPLY_DATE, newImport.getReplyDate());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
            //批复文件
            if (!SharedUtil.toStringEquals(oldImport.getReplyFile(), newImport.getReplyFile())) {
                HashMap<String, Object> keyValueMap = new HashMap<>();
                keyValueMap.put(PmPrj.Cols.PRJ_REPLY_FILE, newImport.getReplyFile());
                PmPrj.updateById(pmPrjId, keyValueMap);
            }
        } catch (Exception ex) {
            succ = false;
            errInfoList.add(ex.toString());
        }

        // TODO 其他字段的处理逻辑。
        //信息写入立项申请流程表
        String error1 = insertPjrReq(newImport,myJdbcTemplate);
        if (!SharedUtil.isEmptyString(error1)){
            errInfoList.add(error1);
        }

        //写入投资测算明细主父表
        PmInvestEst.createPrjData(pmPrjId,newImport,myJdbcTemplate);

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

    /**
     * 立项申请流程表数据写入
     * @param newImport 需要写入的数据明细
     * @param myJdbcTemplate 数据源信息
     * @return 写入时错误信息
     */
    private String insertPjrReq(PrjReqImport newImport, MyJdbcTemplate myJdbcTemplate) {
        String projectId = newImport.getPmPrjId();
        String id = "";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select id from pm_prj_req where pm_prj_id = ? AND STATUS NOT IN ('VD','VDING')",projectId);
        if (CollectionUtils.isEmpty(list)){
            id = Crud.from("pm_prj_req").insertData();
        } else {
            id = JdbcMapUtil.getString(list.get(0),"id");
        }
        String error = "";
        try {
            Crud.from("pm_prj_req").where().eq("id",id).update()
                    .set("PM_PRJ_ID",projectId) //项目
                    .set("PRJ_NAME",newImport.getName()) //项目
                    .set("PRJ_CODE",newImport.getPrjCode()) //项目
                    .set("STATUS","AP") //状态
                    .set("CUSTOMER_UNIT",newImport.getCustomerUnit()) //业主单位
                    .set("PRJ_MANAGE_MODE_ID",newImport.getPrjManageModeId()) //项目管理模式
                    .set("BASE_LOCATION_ID",newImport.getBaseLocationId()) //建设地点
                    .set("FLOOR_AREA",newImport.getFloorArea()) //占地面积
                    .set("PROJECT_TYPE_ID",newImport.getProjectTypeId()) //项目类型
                    .set("PRJ_SITUATION",newImport.getPrjSituation()) //项目介绍
                    .set("CON_SCALE_TYPE_ID",newImport.getConScaleTypeId()) //建设规模类型
                    .set("QTY_ONE",newImport.getBuildingArea()) //建筑面积
                    .set("CON_SCALE_QTY",newImport.getRoadLength()) //道路长度
                    .set("CON_SCALE_QTY2",newImport.getRoadWidth()) //道路宽度
                    .set("OTHER",newImport.getOther()) //其他
                    .set("INVESTMENT_SOURCE_ID",newImport.getInvestmentSourceId()) //投资来源
                    .set("PRJ_TOTAL_INVEST",newImport.getEstimatedTotalInvest()) //总投资
                    .set("CONSTRUCT_AMT",newImport.getConstructPrjAmt()) //建安工程费
                    .set("EQUIP_AMT",newImport.getEquipBuyAmt()) //设备采购费
                    .set("EQUIPMENT_COST",newImport.getEquipmentCost()) //科研设备费
                    .set("PROJECT_OTHER_AMT",newImport.getProjectOtherAmt()) //工程其他费用
                    .set("LAND_AMT",newImport.getLandBuyAmt()) //土地征迁费
                    .set("PREPARE_AMT",newImport.getPrepareAmt()) //预备费
                    .set("COMPL_DATE",newImport.getProjectProposalDate()) //项目建议书完成日期
                    .set("AUTHOR",newImport.getProjectProposalAuthor()) //项目建议书编制人
                    .set("STAMPED_PRJ_REQ_FILE",newImport.getProjectProposalFile()) //项目建议书
                    .set("CONSTRUCTION_PROJECT_CODE",newImport.getPrjCode()) //项目编号
                    .set("PRJ_REPLY_NO",newImport.getReplyNo()) //批复文号
                    .set("PRJ_REPLY_DATE",newImport.getReplyDate()) //批复日期
                    .set("REPLY_FILE",newImport.getReplyFile()) //批复文件
                    .set("IS_OMPORT","0099799190825080669") //是导入
                    .exec();
        } catch (Exception e){
            System.out.println("错误信息："+newImport);
            error = "写入流程表异常;";
        }
        return error;
    }
}
