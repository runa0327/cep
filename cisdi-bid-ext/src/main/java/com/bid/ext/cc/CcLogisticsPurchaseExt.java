package com.bid.ext.cc;

import com.bid.ext.model.*;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import org.apache.tomcat.util.buf.StringUtils;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 采购物流
 */
public class CcLogisticsPurchaseExt {

    /**
     * 初始化“审批结果”,"审批状态"，“拆分状态”的选项
     */
    public void initStatusData() {
        Where statusWhere = new Where();
        // 审核通过状态
        Map<String, String> approveResultsList = new LinkedHashMap<>();
        approveResultsList.put("PASS", "通过");
        approveResultsList.put("NOT_PASS", "不通过");
        initStatus(CcApproveResult.class, approveResultsList, statusWhere);

        // 合同审核状态
        Map<String, String> approveStatusList = new LinkedHashMap<>();
        approveStatusList.put("NOT_SUBMITTED", "未提交审核");
        approveStatusList.put("PENDING", "待审核");
        approveStatusList.put("IN_REVIEW", "审核中");
        approveStatusList.put("APPROVED", "审核通过");
        approveStatusList.put("REJECTED", "审核不通过");
        initStatus(CcApproveStatusSetting.class, approveStatusList, statusWhere);

        // 拆分状态
        Map<String, String> splitStatusList = new LinkedHashMap<>();
        splitStatusList.put("NOT_SPLIT", "未拆分");
        splitStatusList.put("SPLIT", "已拆分");
        initStatus(CcSplitStatus.class, splitStatusList, statusWhere);

        // 主体/配件/备件
        Map<String, String> mainOrPartsList = new LinkedHashMap<>();
        mainOrPartsList.put("MAIN", "主体");
        mainOrPartsList.put("ACCESSORY", "配件");
        mainOrPartsList.put("SPARE_PART", "备件");
        initStatus(CcMainOrPart.class, mainOrPartsList, statusWhere);
    }

    // 通用的初始化状态数据方法
    private <T> void initStatus(Class<T> clazz, Map<String, String> statusMap, Where where) {
        try {
            Method selectByWhereMethod = clazz.getMethod("selectByWhere", Where.class);
            List<?> results = (List<?>) selectByWhereMethod.invoke(null, where);
            if (results == null || results.isEmpty()) {
                Method newDataMethod = clazz.getMethod("newData");
                Method setNameMethod = clazz.getMethod("setName", String.class);
                Method setCodeMethod = clazz.getMethod("setCode", String.class);
                Method insertByIdMethod = clazz.getMethod("insertById");
                for (Map.Entry<String, String> entry : statusMap.entrySet()) {
                    Object instance = newDataMethod.invoke(null);
                    String json = JsonUtil.toJson(new Internationalization(null, entry.getValue(), null));
                    setNameMethod.invoke(instance, json);
                    setCodeMethod.invoke(instance, entry.getKey());
                    insertByIdMethod.invoke(instance);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    public void creatApprove(){
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        if (entityRecordList == null) {
            return;
        }

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            if (valueMap == null ||!valueMap.containsKey("ID")) {
                continue;
            }
            String contractId = valueMap.get("ID").toString();
            CcLogisticsContract contract = CcLogisticsContract.selectById(contractId);
            if (contract == null) {
                continue;
            }

            // 更新审批状态
            CcApproveStatusSetting statusSetting = getStatusRecordByStatusCode(CcApproveStatusSetting.class, "NOT_SUBMITTED");
            if (statusSetting != null) {
                String status = statusSetting.getId();
                contract.setCcApproveStatusSettingId(status);
            }

            // 更新拆分状态
            CcSplitStatus splitStatus = getStatusRecordByStatusCode(CcSplitStatus.class, "NOT_SPLIT");
            if (splitStatus != null) {
                String status2 = splitStatus.getId();
                contract.setCcSplitStatusId(status2);
            }

            contract.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 根据状态代码查询对应的状态记录
     *
     * @param clazz      状态类的 Class 对象
     * @param statusCode 状态代码
     * @param <T>        状态类的泛型类型
     * @return 状态记录对象，如果未找到则返回 null
     */
    private <T> T getStatusRecordByStatusCode(Class<T> clazz, String statusCode) {
        Where where = new Where();
        if (clazz == CcApproveStatusSetting.class) {
            where.eq(CcApproveStatusSetting.Cols.CODE, statusCode);
        } else if (clazz == CcSplitStatus.class) {
            where.eq(CcSplitStatus.Cols.CODE, statusCode);
        } else if (clazz == CcMainOrPart.class) {
            where.eq(CcMainOrPart.Cols.CODE, statusCode);
        }
        try {
            Method selectOneByWhereMethod = clazz.getMethod("selectOneByWhere", Where.class);
            return (T) selectOneByWhereMethod.invoke(null, where);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 提交物流合同审批
     */
    public void commitApprove() {
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            CcLogisticsContract contract = CcLogisticsContract.selectById(valueMap.get("ID").toString());

            CcApproveStatusSetting statusSetting = getStatusRecordByStatusCode(CcApproveStatusSetting.class, "PENDING");
            if (statusSetting != null) {
                String status = statusSetting.getId();
                contract.setCcApproveStatusSettingId(status);
            }
            contract.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 物流合同审批开始
     */
    public void startApprove() {
        // 表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        // 选中数据信息
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            // 更新合同数据
            try {
                CcLogisticsContract contract = CcLogisticsContract.selectById(JdbcMapUtil.getString(valueMap, "ID"));
                // 判断审核结果的选择，选择通过，状态改成审核通过，选择不通过，状态改成审核不通过
                String result = JdbcMapUtil.getString(varMap, "P_CC_APPROVE_RESULT_ID");// 选择的状态
                // 查询状态对应的NAME
                CcApproveResult ccApproveResult = CcApproveResult.selectById(result);
                Map map = JsonUtil.fromJson(ccApproveResult.getName(), Map.class);
                String name = map.get("ZH_CN").toString();
                String targetStatusCode;
                if (name.equals("通过")) {
                    targetStatusCode = "APPROVED";
                } else {
                    targetStatusCode = "REJECTED";
                }
                // 更新审批状态
                CcApproveStatusSetting statusSetting = getStatusRecordByStatusCode(CcApproveStatusSetting.class, targetStatusCode);
                if (statusSetting != null) {
                    String status = statusSetting.getId();
                    contract.setCcApproveStatusSettingId(status);
                }
                if (JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION") != null) {
                    contract.setCcApproveOpinion(JdbcMapUtil.getString(varMap, "P_CC_APPROVE_OPINION"));
                }
                contract.updateById();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 判断零部件是否还有剩余可拆分（废弃，去掉剩余可拆分的概念）
     * @throws Exception
     */
    public void updateSplitDataPre() throws Exception {
//        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
//        for (EntityRecord entityRecord : entityRecordList) {
//            Map<String, Object> valueMap = entityRecord.valueMap;
//            if (valueMap.get("CC_REMAIN_QTY") instanceof Integer) {
//                if(Integer.valueOf(valueMap.get("CC_REMAIN_QTY").toString()) <= 0){
//                    throw new Exception("零部件已无可拆分数量");
//                }
//            }
//        }
    }

    /**
     * 更新合同信息的拆分状态，只要是新增了零部件信息或者选择无需拆分，都默认是已拆分的行为
     */
    public void updateSplitData(){
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            //订单数量判断
            CcSparePartsInfo sparePartsInfo = CcSparePartsInfo.selectById(valueMap.get("ID").toString());

            CcLogisticsContract contract = CcLogisticsContract.selectById(valueMap.get("CC_LOGISTICS_CONTRACT_ID").toString());

            //零部件编号
            Where where = new Where();
            where.eq("CC_LOGISTICS_CONTRACT_ID", valueMap.get("CC_LOGISTICS_CONTRACT_ID").toString());
            List<CcSparePartsInfo> ccSparePartsInfos = CcSparePartsInfo.selectByWhere(where);
            int count = 1;
            if(ccSparePartsInfos != null && ccSparePartsInfos.size() > 0){
                count = ccSparePartsInfos.size();
            }
            String countStr = String.format("%04d", count);
            sparePartsInfo.setCcSparePartsNum(contract.getCcEquipmentNum() + '-' + countStr);//编号 = 合同设备编号 + 0001（根据拆分数量）
            sparePartsInfo.setCcEquipmentNum(contract.getCcEquipmentNum());
            //剩余数量和装箱数量初始化（需整改）
            sparePartsInfo.setCcRemainQty(0);//默认剩余数量为0
            if(Integer.valueOf(valueMap.get("CC_REMAIN_QTY").toString()) > 0){
                sparePartsInfo.setCcPackQty(Integer.valueOf(valueMap.get("CC_SPARE_PARTS_QTY").toString()));//默认装箱数量为拆分的数量
            }else{
                sparePartsInfo.setCcSparePartsQty(0);
                sparePartsInfo.setCcPackQty(0);
                sparePartsInfo.setCcTotalWeight(BigDecimal.valueOf(0));
            }

            sparePartsInfo.updateById();

            CcSplitStatus splitStatus = getStatusRecordByStatusCode(CcSplitStatus.class, "SPLIT");
            if (splitStatus != null) {
                String status = splitStatus.getId();
                // 判断拆分状态是否已经更新过，更新过就不必在更新
                String latestSplitStatus = contract.getCcSplitStatusId();// 数据库中的拆分状态
                if (latestSplitStatus != null && latestSplitStatus.equals(status)) {
                    //不做处理
                }else{
                    contract.setCcSplitStatusId(status);
                }
            }
            contract.updateById();
        }
    }

    /**
     * 合同物料无需拆分的情况下，更新合同数据和插入零部件信息
     */
    public void updateNotSplitData(){

        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            //新增零部件信息
            CcSparePartsInfo sparePartsInfo = CcSparePartsInfo.newData();
            sparePartsInfo.setTs(LocalDateTime.now());
            sparePartsInfo.setCrtDt(LocalDateTime.now());
            sparePartsInfo.setCrtUserId(userId);
            sparePartsInfo.setLastModiDt(LocalDateTime.now());
            sparePartsInfo.setLastModiUserId(userId);
            sparePartsInfo.setStatus("AP");
            sparePartsInfo.setName(JsonUtil.toJson(new Internationalization(null, varMap.get("P_NAME").toString(), null)));
            sparePartsInfo.setCcFuncSpecs(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_FUNC_SPECS").toString(), null)));
            sparePartsInfo.setCcOrderQty(varMap.get("P_CC_ORDER_QTY").toString());
            sparePartsInfo.setCcProductUnit(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_PRODUCT_UNIT").toString(), null)));
            BigDecimal unitWeight = new BigDecimal(varMap.get("P_CC_UNIT_WEIGHT").toString());
            sparePartsInfo.setCcUnitWeight(unitWeight);
            BigDecimal totalWeight = new BigDecimal(varMap.get("P_CC_TOTAL_WEIGHT").toString());
            sparePartsInfo.setCcTotalWeight(totalWeight);
            sparePartsInfo.setCcLogisticsContractId(valueMap.get("ID").toString());
            //主体/配件
            CcMainOrPart mainOrPart = getStatusRecordByStatusCode(CcMainOrPart.class, "MAIN");
            if (mainOrPart != null) {
                String status = mainOrPart.getId();
                sparePartsInfo.setCcMainOrPartId(status);
            }
            //零部件编号
            CcLogisticsContract contract = CcLogisticsContract.selectById(valueMap.get("ID").toString());
            //判断该合同下的零部件数据条数
            Where where = new Where();
            where.eq("CC_LOGISTICS_CONTRACT_ID", valueMap.get("ID").toString());
            List<CcSparePartsInfo> ccSparePartsInfos = CcSparePartsInfo.selectByWhere(where);
            int count = 0;
            if(ccSparePartsInfos != null && ccSparePartsInfos.size() > 0){
                count = ccSparePartsInfos.size() + 1;
            }else{
                count = count + 1;
            }
            String countStr = String.format("%04d", count);
            sparePartsInfo.setCcSparePartsNum(contract.getCcEquipmentNum() + '-' + countStr);//编号 = 合同设备编号 + 0001（根据拆分数量）
            //零部件的设备编号，与合同相同，区别于零部件自身的设备编号
            sparePartsInfo.setCcEquipmentNum(contract.getCcEquipmentNum());
            //剩余数量和装箱数量初始化
            sparePartsInfo.setCcRemainQty(0);//默认剩余数量为0
            sparePartsInfo.setCcPackQty(Integer.valueOf(sparePartsInfo.getCcOrderQty()));//默认装箱数量为拆分的数量
            sparePartsInfo.setCcSparePartsQty(Integer.valueOf(varMap.get("P_CC_ORDER_QTY").toString()));//零部件数量
            sparePartsInfo.insertById();

            //更新合同的拆分状态
            CcSplitStatus splitStatus = getStatusRecordByStatusCode(CcSplitStatus.class, "SPLIT");
            if (splitStatus != null) {
                String status = splitStatus.getId();
                contract.setCcSplitStatusId(status);
            }
            contract.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 物流装箱数据新增后更新
     */
    public void createLogisticsPack(){
        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        //插入物流装箱数据
        CcLogisticsPack ccLogisticsPack = CcLogisticsPack.newData();
        ccLogisticsPack.setTs(LocalDateTime.now());
        ccLogisticsPack.setCrtDt(LocalDateTime.now());
        ccLogisticsPack.setCrtUserId(userId);
        ccLogisticsPack.setLastModiDt(LocalDateTime.now());
        ccLogisticsPack.setLastModiUserId(userId);
        ccLogisticsPack.setStatus("AP");
        ccLogisticsPack.setName(JsonUtil.toJson(new Internationalization(null, varMap.get("P_NAME").toString(), null)));


//        CcSparePartsInfo sparePartsInfo = CcSparePartsInfo.selectById(sparePartsInfoId);
//        String logisticsContractId = sparePartsInfo.getCcLogisticsContractId();
//        ccLogisticsPack.setCcLogisticsContractId(logisticsContractId);//物流合同ID
//        ccLogisticsPack.setCcSparePartsInfoId(sparePartsInfoId);//零部件信息ID
        ccLogisticsPack.setCcPackagingTypeId(varMap.get("P_CC_PACKAGING_TYPE_ID").toString());
        ccLogisticsPack.setCcPackNum(varMap.get("P_CC_PACK_NUM").toString());
        ccLogisticsPack.setCcHaveEnclosedDocs(stringToBoolean(varMap.get("P_CC_HAVE_ENCLOSED_DOCS").toString()));
        ccLogisticsPack.setCcHaveRepairParts(stringToBoolean(varMap.get("P_CC_HAVE_REPAIR_PARTS").toString()));
        ccLogisticsPack.setCcHaveSpecialTools(stringToBoolean(varMap.get("P_CC_HAVE_SPECIAL_TOOLS").toString()));
        ccLogisticsPack.setCcIsBreakUp(stringToBoolean(varMap.get("P_CC_IS_BREAK_UP").toString()));
        ccLogisticsPack.setCcPackgingWeight(new BigDecimal(varMap.get("P_CC_PACKGING_WEIGHT").toString()));
        //判断varMap中是否存在P_CC_PRODUCT_MAX_LENGTH这个键
        if(varMap.containsKey("P_CC_PRODUCT_MAX_LENGTH") && varMap.get("P_CC_PRODUCT_MAX_LENGTH") != null){
            ccLogisticsPack.setCcProductMaxLength(new BigDecimal(varMap.get("P_CC_PRODUCT_MAX_LENGTH").toString()));
        }
        if(varMap.containsKey("P_CC_PRODUCT_MAX_WIDTH") && varMap.get("P_CC_PRODUCT_MAX_WIDTH") != null){
            ccLogisticsPack.setCcProductMaxLength(new BigDecimal(varMap.get("P_CC_PRODUCT_MAX_WIDTH").toString()));
        }
        if(varMap.containsKey("P_CC_PRODUCT_MAX_HEIGHT") && varMap.get("P_CC_PRODUCT_MAX_HEIGHT") != null){
            ccLogisticsPack.setCcProductMaxLength(new BigDecimal(varMap.get("P_CC_PRODUCT_MAX_HEIGHT").toString()));
        }
        ccLogisticsPack.setContactName(varMap.get("P_CONTACT_NAME").toString());
        ccLogisticsPack.setContactMobile(varMap.get("P_CONTACT_MOBILE").toString());
        ccLogisticsPack.setCcCarryTypeId(varMap.get("P_CC_CARRY_TYPE_ID").toString());
        ccLogisticsPack.setCcPackStatusId("PACKED");
        //设备编号
        String sparePartsInfoIds = varMap.get("P_CC_SPARE_PARTS_INFO_IDS").toString();//多个零部件信息
        String[] sparePartsInfoIdsArr = sparePartsInfoIds.split(",");
        ArrayList<String> sparePartsEquipmentNum = new ArrayList<String>();//设备编号
        for(String sparePartsInfoId : sparePartsInfoIdsArr){
            CcSparePartsInfo info = CcSparePartsInfo.selectById(sparePartsInfoId);
            //去掉重复的设备编号
            if(sparePartsEquipmentNum.contains(info.getCcEquipmentNum())){
                continue;
            }else{
                sparePartsEquipmentNum.add(info.getCcEquipmentNum());
            }
        }
        //ArrayList<String> 转成字符串
        String sparePartsEquipmentNumStr = StringUtils.join(sparePartsEquipmentNum, ',');
        ccLogisticsPack.setCcEquipmentNum(sparePartsEquipmentNumStr);

        //装箱中零部件的份量
        ccLogisticsPack.setCcPackQty(sparePartsInfoIdsArr.length);

//        CcLogisticsContract contract = CcLogisticsContract.selectById(logisticsContractId);
//        ccLogisticsPack.setCcEquipmentNum(contract.getCcEquipmentNum());

        ccLogisticsPack.insertById();

        //得到物流合同ID
        for(String sparePartsInfoId : sparePartsInfoIdsArr){
            CcPackRelateSpareParts info = CcPackRelateSpareParts.newData();
            info.setCcSparePartsInfoId(sparePartsInfoId);
            info.setCcLogisticsPackId(ccLogisticsPack.getId());
            CcSparePartsInfo ccSparePartsInfo = CcSparePartsInfo.selectById(sparePartsInfoId);
            info.setCcPackQty(ccSparePartsInfo.getCcSparePartsQty());
            info.insertById();
        }

        //更新零部件信息的数量
        //订单总数量是不变的，变化的只有装箱数量和剩余数量
//        sparePartsInfo.setCcPackQty(Integer.valueOf(varMap.get("P_CC_REMAIN_QTY").toString()));//可装箱数量
//        sparePartsInfo.setCcRemainQty(0);//剩余数量的值一直是0，只有在执行过程中，赋给其值
//        sparePartsInfo.updateById();

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private boolean stringToBoolean(String var) {
        if (var.equals("1")) {
            return true;
        }else{
            return false;
        }
    }

    /**
     * 新增物流发货
     */
    public void createLogisticsShip(){
        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        //插入物流装箱数据
        CcLogisticsShip ccLogisticsShip = CcLogisticsShip.newData();
        //本批箱件总数
        int num = entityRecordList.size();
        float total = 0.0f;
        ArrayList<String> ids = new ArrayList<String>();
        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;
            total = total + Float.valueOf(valueMap.get("CC_PACKGING_WEIGHT").toString());

            //更新装箱状态，将状态改成已发货
            CcLogisticsPack ccLogisticsPack = CcLogisticsPack.selectById(valueMap.get("ID").toString());
            ccLogisticsPack.setCcPackStatusId("SHIPPED");
            //到货状态初始化
            ccLogisticsPack.setCcArrivalStatusId("NOT_ARRIVED");
            ccLogisticsPack.updateById();
            ids.add(valueMap.get("ID").toString());//后面用于新增发货装箱表的数据
        }

        ccLogisticsShip.setTs(LocalDateTime.now());
        ccLogisticsShip.setCrtDt(LocalDateTime.now());
        ccLogisticsShip.setCrtUserId(userId);
        ccLogisticsShip.setLastModiDt(LocalDateTime.now());
        ccLogisticsShip.setLastModiUserId(userId);
        ccLogisticsShip.setStatus("AP");
        ccLogisticsShip.setCcShipNum(varMap.get("P_CC_SHIP_NUM").toString());
        ccLogisticsShip.setCcShipperName(varMap.get("P_CC_SHIPPER_NAME").toString());
        ccLogisticsShip.setCcShipperPhone(varMap.get("P_CC_SHIPPER_PHONE").toString());
        ccLogisticsShip.setCcShipperPhone(varMap.get("P_CC_SHIPPER_PHONE").toString());
        ccLogisticsShip.setCcLogisticsSendAddr(varMap.get("P_CC_LOGISTICS_SEND_ADDR").toString());
        ccLogisticsShip.setCcLogisticsReceiveAddr(varMap.get("P_CC_LOGISTICS_RECEIVE_ADDR").toString());
        ccLogisticsShip.setCcShipTypeId(varMap.get("P_CC_SHIP_TYPE_ID").toString());
        //本批箱件总数
        ccLogisticsShip.setCcPackageTotal(num);
        ccLogisticsShip.setCcTotalWeight(BigDecimal.valueOf(total));
//        ccLogisticsShip.setCcLogisticsPackIds(String.join(",", ids));
        ccLogisticsShip.insertById();

        for(String id : ids){
            //插入发货装箱关系表
            CcShipRelatePack ccShipRelatePack = CcShipRelatePack.newData();
            ccShipRelatePack.setCcLogisticsPackId(id);
            ccShipRelatePack.setCcLogisticsShipId(ccLogisticsShip.getId());
            ccShipRelatePack.setTs(LocalDateTime.now());
            ccShipRelatePack.setCrtDt(LocalDateTime.now());
            ccShipRelatePack.setCrtUserId(userId);
            ccShipRelatePack.setLastModiDt(LocalDateTime.now());
            ccShipRelatePack.setLastModiUserId(userId);
            ccShipRelatePack.setStatus("AP");
            ccShipRelatePack.insertById();
        }
        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 新增报关信息
     */
    public void createLogisticsDeclare(){
        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcLogisticsPack ccLogisticsPack = CcLogisticsPack.selectById(valueMap.get("ID").toString());
            ccLogisticsPack.setCcPackDate(LocalDate.parse(varMap.get("P_CC_PACK_DATE").toString()));
            ccLogisticsPack.setCcExportDeclareDate(LocalDate.parse(varMap.get("P_CC_EXPORT_DECLARE_DATE").toString()));
            ccLogisticsPack.setCcImportDeclareDate(LocalDate.parse(varMap.get("P_CC_IMPORT_DECLARE_DATE").toString()));
            ccLogisticsPack.setCcShippingCycle(new BigDecimal(varMap.get("P_CC_SHIPPING_CYCLE").toString()));
            ccLogisticsPack.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 查看弹窗确认操作，不做任何处理
     */
    public void check(){

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 到货登记——新增
     */
    public void createArrivalRecord(){
        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcArrivalRecord arrivalRecord = CcArrivalRecord.newData();
            arrivalRecord.setTs(LocalDateTime.now());
            arrivalRecord.setCrtDt(LocalDateTime.now());
            arrivalRecord.setCrtUserId(userId);
            arrivalRecord.setLastModiDt(LocalDateTime.now());
            arrivalRecord.setLastModiUserId(userId);
            arrivalRecord.setStatus("AP");
            arrivalRecord.setCcLogisticsPackId(valueMap.get("ID").toString());
            arrivalRecord.setCcArrivalDate(LocalDate.parse(varMap.get("P_CC_ARRIVAL_DATE").toString()));
            arrivalRecord.setCcAnomalyDescribe(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_ANOMALY_DESCRIBE").toString(), null)));
            arrivalRecord.setCcReceiver(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_RECEIVER").toString(), null)));
            arrivalRecord.setCcReceiveAndStoreOrg(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_RECEIVE_AND_STORE_ORG").toString(), null)));
            arrivalRecord.setCcStackSite(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_STACK_SITE").toString(), null)));
            arrivalRecord.setCcEquipmentAppearanceInspection(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_EQUIPMENT_APPEARANCE_INSPECTION").toString(), null)));
            arrivalRecord.setCcArrivalRecordStatusId(varMap.get("P_CC_ARRIVAL_RECORD_STATUS_ID").toString());
            //考虑到批量操作的情况，不从表单中获取，而是从valueMap中获取
            String packId = valueMap.get("ID").toString();//装箱ID
            Where where = new Where();
            where.eq("CC_LOGISTICS_PACK_ID ", packId);
            CcShipRelatePack ccShipRelatePack = CcShipRelatePack.selectOneByWhere(where);
            //一个运输关联多个装箱，表设计有问题，这里查出的ccShipRelatePackList只有一条数据
            Where  where1 = new Where();
            where1.eq("ID ", ccShipRelatePack.getCcLogisticsShipId());
            CcLogisticsShip ccLogisticsShip = CcLogisticsShip.selectOneByWhere(where1);
            arrivalRecord.setCcArrivalNum(ccLogisticsShip.getCcShipNum());
            arrivalRecord.insertById();

            //装箱到货状态更新
            CcLogisticsPack ccLogisticsPack = CcLogisticsPack.selectById(valueMap.get("ID").toString());
            ccLogisticsPack.setCcArrivalStatusId("HAVE_ARRIVED");
            //开箱验收状态初始化
            ccLogisticsPack.setCcUnpackingInspectionStatusId("UNINSPECTED");
            ccLogisticsPack.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 开箱验收新增
     */
    public void createUnpackingInspection(){
        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcUnpackingInspection ccUnpackingInspection = CcUnpackingInspection.newData();
            ccUnpackingInspection.setTs(LocalDateTime.now());
            ccUnpackingInspection.setCrtDt(LocalDateTime.now());
            ccUnpackingInspection.setCrtUserId(userId);
            ccUnpackingInspection.setLastModiDt(LocalDateTime.now());
            ccUnpackingInspection.setLastModiUserId(userId);
            ccUnpackingInspection.setStatus("AP");
            ccUnpackingInspection.setCcLogisticsPackId(valueMap.get("ID").toString());
            ccUnpackingInspection.setCcUnpackingDate(LocalDate.parse(varMap.get("P_CC_UNPACKING_DATE").toString()));
            ccUnpackingInspection.setCcAnomalyDescribe(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_ANOMALY_DESCRIBE").toString(), null)));
            ccUnpackingInspection.setCcInvolvedPerson(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_INVOLVED_PERSON").toString(), null)));
            ccUnpackingInspection.setCcInvolvedOrg(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_INVOLVED_ORG").toString(), null)));
            ccUnpackingInspection.setCcUnpackingSite(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_UNPACKING_SITE").toString(), null)));
            //考虑到批量操作的情况，不从表单中获取，而是从valueMap中获取
            String unpackingNum = valueMap.get("CC_PACK_NUM").toString();
            ccUnpackingInspection.setCcUnpackingNum(unpackingNum);
//            ccUnpackingInspection.setCcUnpackingNum(varMap.get("P_CC_UNPACKING_NUM").toString());
            ccUnpackingInspection.insertById();

            //装箱验收状态更新
            CcLogisticsPack ccLogisticsPack = CcLogisticsPack.selectById(valueMap.get("ID").toString());
            ccLogisticsPack.setCcUnpackingInspectionStatusId("INSPECTED");
            //初始化移交状态
            ccLogisticsPack.setCcHandOverStatusId("UNHANDED_OVER");//未移交
            ccLogisticsPack.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    /**
     * 开箱验收新增
     */
    public void createHandOverReg(){
        //获取表单提交信息
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String userId = loginInfo.userInfo.id;

        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();

        for (EntityRecord entityRecord : entityRecordList) {
            Map<String, Object> valueMap = entityRecord.valueMap;

            CcHandOverReg ccHandOverReg = CcHandOverReg.newData();
            ccHandOverReg.setTs(LocalDateTime.now());
            ccHandOverReg.setCrtDt(LocalDateTime.now());
            ccHandOverReg.setCrtUserId(userId);
            ccHandOverReg.setLastModiDt(LocalDateTime.now());
            ccHandOverReg.setLastModiUserId(userId);
            ccHandOverReg.setStatus("AP");
            ccHandOverReg.setCcLogisticsPackId(valueMap.get("ID").toString());
            ccHandOverReg.setCcHandOverDate(LocalDate.parse(varMap.get("P_CC_HAND_OVER_DATE").toString()));
            ccHandOverReg.setCcHandOverWayId(varMap.get("P_CC_HAND_OVER_WAY_ID").toString());
            ccHandOverReg.setCcInspectionOpinion(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_INSPECTION_OPINION").toString(), null)));
            ccHandOverReg.setCcHandOverReceiver(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_HAND_OVER_RECEIVER").toString(), null)));
            ccHandOverReg.setCcHandOverOrg(JsonUtil.toJson(new Internationalization(null, varMap.get("P_CC_HAND_OVER_ORG").toString(), null)));
            ccHandOverReg.insertById();

            //装箱移交状态更新
            CcLogisticsPack ccLogisticsPack = CcLogisticsPack.selectById(valueMap.get("ID").toString());
            ccLogisticsPack.setCcHandOverStatusId("HANDED_OVER");
            ccLogisticsPack.updateById();
        }

        // 刷新页面
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }
}
