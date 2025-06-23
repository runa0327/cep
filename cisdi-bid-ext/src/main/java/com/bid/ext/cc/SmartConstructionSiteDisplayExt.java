package com.bid.ext.cc;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 智慧工地展示相关接口
 */
public class SmartConstructionSiteDisplayExt {

    /**
     * 智慧工地人员统计
     */
    public void getUserStat(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Map<String, Object> outputList = new HashMap<>() ;

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        CcSmartConstructionSiteUser user = CcSmartConstructionSiteUser.selectOneByWhere(where);
        List<Map<String, Object>> list = new ArrayList<>() ;
        if (user == null) {
            //如果结果为空，那就提供随机测试数据
            int count = (int) (Math.random() * 200);
            outputList.put("count", count);
            Map<String, Object> outputMap = new HashMap<>();
            outputMap.put("name", "进场人数");
            int value1 = (int) (Math.random() * 100);
            outputMap.put("value", value1);
            list.add(outputMap);
            outputMap = new HashMap<>();
            outputMap.put("name", "离场人数");
            outputMap.put("value", count - value1);
            list.add(outputMap);
        }else{
            outputList.put("count", user.getCcScsCurrentAllPersons());
            Map<String, Object> outputMap = new HashMap<>();
            outputMap.put("name", "进场人数");
            outputMap.put("value", user.getCcScsEntryPersons());
            list.add(outputMap);
            outputMap = new HashMap<>();
            outputMap.put("name", "离场人数");
            outputMap.put("value", user.getCcScsExitPersons());
            list.add(outputMap);
        }
        outputList.put("trafficWay", list);

        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", outputList);

        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 智慧工地人员进出记录
     */
    public void getUserEntryExitRecords(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteEntryExitRecords> records = CcSmartConstructionSiteEntryExitRecords.selectByWhere(where);
        List<Map<String, Object>> outputList = new ArrayList<>() ;
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,6条测试数据
            for (int i = 0; i < 6; i++) {
                Map<String, Object> outputMap = new HashMap<>();
                //当天的早上八点过1分到2分
                outputMap.put("date", LocalDate.now().atTime(8, 1, (int)Math.floor(Math.random() * 60)));
                int randomNum = (int) (Math.floor(Math.random() * 5) + 1);//随机生成1-5的数字
                String sn = "南门";//南北
                String inOut = "进场";//进出
                if(randomNum > 3){
                    sn = "北门";//南北
                    inOut = "离场";//进出
                }
                outputMap.put("name", "0"+randomNum+"号"+sn+inOut);
                outputList.add(outputMap);
            }
        }else{
            for (CcSmartConstructionSiteEntryExitRecords record : records) {
                Map<String, Object> outputMap = new HashMap<>();
                outputMap.put("date", record.getCcScsDate());
                outputMap.put("name", record.getCcScsAddr());
                outputList.add(outputMap);
            }
        }

        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", outputList);

        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 智慧工地人员违规处理(无效)
     */
    public void getUserViolationHandle_Invalid(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteViolationHandle> records = CcSmartConstructionSiteViolationHandle.selectByWhere(where);
        Map<String, Object> outputMap = new HashMap<>();
        List<Map<String, Object>> outputList1 = new ArrayList<>() ;
        List<Map<String, Object>> outputList2 = new ArrayList<>() ;
        List<Map<String, Object>> outputList3 = new ArrayList<>() ;
        List<Map<String, Object>> outputList4 = new ArrayList<>() ;
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,
            String[] types = {"瓦工", "木工", "电工", "钢筋工"};
            for (String type : types){
                //4种类型
                String[] statuses = {"待处理", "已处理", "处理中", "取消处理"};
                //总人数
                int count = (int) (Math.floor(Math.random() * 100) + 1);
                int count1 = count;
                for (String status : statuses){
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", status);

                    // 格式化百分比（保留两位小数）
                    DecimalFormat df = new DecimalFormat("0.00%");
                    if(type.equals("瓦工")){
                        int wgCount = (int) (Math.floor(Math.random() * 20) + 1);
                        map.put("value", wgCount);
                        map.put("opacity", df.format((wgCount/(double)count1)));
                        map.put("count", count1);
                        outputList1.add(map);
                        count -= wgCount;
                    } else if (type.equals("木工")) {
                        int mgCount = (int) (Math.floor(Math.random() * 20) + 1);
                        map.put("value", mgCount);
                        map.put("opacity", df.format((mgCount/(double)count1)));
                        map.put("count", count1);
                        outputList2.add(map);
                        count -= mgCount;
                    } else if (type.equals("电工")) {
                        int dgCount = (int) (Math.floor(Math.random() * 20) + 1);
                        map.put("value", dgCount);
                        map.put("opacity", df.format((dgCount/(double)count1)));
                        map.put("count", count1);
                        outputList3.add(map);
                        count -= dgCount;
                    }else if (type.equals("钢筋工")) {
                        map.put("value", count);
                        map.put("opacity", df.format((count/(double)count1)));
                        map.put("count", count1);
                        outputList4.add(map);
                    }else{
                    }
                }
                if(type.equals("瓦工")){
                    outputMap.put(type, outputList1);
                } else if (type.equals("木工")) {
                    outputMap.put(type, outputList2);
                } else if (type.equals("电工")) {
                    outputMap.put(type, outputList3);
                }else if (type.equals("钢筋工")) {
                    outputMap.put(type, outputList4);
                }else{
                }
            }
        }else{
            for (CcSmartConstructionSiteViolationHandle record : records) {
                Map<String, Object> map = new HashMap<>();
                String type = record.getCcScsPersonType();//人员类型
                String status = record.getCcScsHandleStatus();//处理状态
                int num = record.getCcScsHandlePersons();//处理人数
                if(status == "pending"){
                    map.put("name", "待处理");
                }else if(status == "handled"){
                    map.put("name", "已处理");
                } else if (status == "processing") {
                    map.put("name", "处理中");
                }else{
                    //cancelled
                    map.put("name", "取消处理");
                }
                map.put("value", num);
                //固定几个人员类型：瓦工 tiler/ 木工carpenter/ 电工electrician / 钢筋工 steel_fixer/

                if(type.equals("tiler")){
                    outputList1.add(map);
                } else if (type.equals("carpenter")) {
                    outputList2.add(map);
                } else if (type.equals("electrician")) {
                    outputList3.add(map);
                }else if (type.equals("steel_fixer")) {
                    outputList4.add(map);
                }else{
                }
            }
        }

        outputMap.put("瓦工", outputList1);
        outputMap.put("木工", outputList2);
        outputMap.put("电工", outputList3);
        outputMap.put("钢筋工", outputList4);

        ExtJarHelper.setReturnValue(outputMap);
    }


    /**
     * 获取用户违规处理数据
     */
    public void getUserViolationHandle(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteViolationHandle> records = CcSmartConstructionSiteViolationHandle.selectByWhere(where);

        List<Map<String, Object>> outputList = new ArrayList<>() ;
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,
            String[] types = {"瓦工", "木工", "电工", "钢筋工"};
            DecimalFormat df = new DecimalFormat("0.00%");
            for (String type : types) {
                Map<String, Object> map = new HashMap<>();
                if(type.equals("瓦工")){
                    map.put("c", "color1");
                }else if (type.equals("木工")) {
                    map.put("c", "color2");
                } else if (type.equals("电工")) {
                    map.put("c", "color3");
                }else if (type.equals("钢筋工")) {
                    map.put("c", "color4");
                }else{
                }
                map.put("time", type);

                int totalNum = (int) (Math.floor(Math.random() * 100) + 1); // 总人数
                int violationCount = (int) (Math.floor(Math.random() * Math.min(totalNum, 20)) + 1); // 违规人数，不超过总人数

                // 修正百分比计算和格式化
                double violationRate = (double) violationCount / totalNum;
                map.put("doorplate", df.format(violationRate)); // 直接传入比例值，DecimalFormat会自动乘以100
                map.put("num", totalNum);
                map.put("wg", "违规" + violationCount + "人");

                outputList.add(map);
            }
        }else{
            //暂时不写
        }
        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", outputList);

        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 获取所有违规处理数据
     */
    public void getAllViolationHandle(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteViolationHandle> records = CcSmartConstructionSiteViolationHandle.selectByWhere(where);

        List<Map<String, Object>> outputList = new ArrayList<>();
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,
            String[] statuses = {"待处理", "已处理", "未处理", "忽略"};
            DecimalFormat df = new DecimalFormat("0.00%");
            for (String status : statuses) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", status);
                int num = (int) (Math.floor(Math.random() * 5000) + 500); // 总人数
                map.put("value", num);

                Map<String, Object> colorMap = new HashMap<>();
                if(status.equals("待处理")){
                    colorMap.put("color", "#2A71FF");
                }else if (status.equals("已处理")){
                    colorMap.put("color", "#00EDFE");
                } else if (status.equals("未处理") ){
                    colorMap.put("color", "#FEDB4B");
                }else{
                    colorMap.put("color", "#FE7C2F");
                }
                map.put("itemStyle", colorMap);

                outputList.add(map);
            }
        }else{
            //暂时不写
        }
        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", outputList);

        ExtJarHelper.setReturnValue(outputMap);
    }


    /**
     * 获取监控设备数量
     */
    public void getMonitorEquipmentNum(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteMonitorEquipment> records = CcSmartConstructionSiteMonitorEquipment.selectByWhere(where);

        Map<String, Object> outputMap = new HashMap<>();
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,
            int total = (int) (Math.floor(Math.random() * 20) + 5); // 监控设备数
            outputMap.put("total", total);
            int online = (int) (Math.floor(Math.random() * 10) + 1); // 在线数
            if(online > total){
                online = total;
            }
            outputMap.put("online", online);
            outputMap.put("offline", (total - online));
        }else{
            //暂时不写
        }
        Map<String, Object> outputMap1 = new HashMap<>();
        outputMap1.put("data", outputMap);
        ExtJarHelper.setReturnValue(outputMap1);
    }

    /**
     * 获取大型设备详情
     */
    public void getLargeEquipmentDetail(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteLargeEquipment> records = CcSmartConstructionSiteLargeEquipment.selectByWhere(where);

        Map<String, Object> outputMap = new HashMap<>();
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,
            int height = (int) (Math.floor(Math.random() * 10) + 140); // 高度
            outputMap.put("height", height);
            int torque = (int) (Math.floor(Math.random() * 10) + 150); // 力矩
            outputMap.put("torque", torque);
            int weightCapacity = (int) (Math.floor(Math.random() * 10) + 300); // 承重
            outputMap.put("weightCapacity", weightCapacity);
            int range = (int) (Math.floor(Math.random() * 10) + 50); // 幅度
            outputMap.put("range", range);
            int rotationAngle = (int) (Math.floor(Math.random() * 10) + 250); // 回转角度
            outputMap.put("rotationAngle", rotationAngle);
            int angle = (int) (Math.floor(Math.random() * 10) + 60); // 倾角
            outputMap.put("angle", angle);
        }else{
            //暂时不写
        }
        Map<String, Object> outputMap1 = new HashMap<>();
        outputMap1.put("data", outputMap);
        ExtJarHelper.setReturnValue(outputMap1);
    }

    /**
     * 获取大型设备统计
     */
    public void getLargeEquipmentStat(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteLargeEquipment> records = CcSmartConstructionSiteLargeEquipment.selectByWhere(where);

        List<Map<String, Object>> outputList = new ArrayList<>();
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,
            String[] equipmentTypes = {"塔吊", "升降机", "物料提升机"};
            for (String equipmentType : equipmentTypes){
                Map<String, Object> map = new HashMap<>();
                map.put("name", equipmentType);
                //数量最多为4
                int num = (int) (Math.floor(Math.random() * 4) + 1); // 设备数量
                map.put("total", num);
                int offlineNum = (int) (Math.floor(Math.random() * 4) + 1); // 设备数量
                if(offlineNum > num){
                    offlineNum = num;
                }
                map.put("offline", offlineNum);
                outputList.add(map);
            }
        }else{
            //暂时不写
        }

        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", outputList);
        ExtJarHelper.setReturnValue(outputMap);
    }


    /**
     * 获取智能识别预警
     */
    public void getIntelligentRecognitionWarning(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteWarning> records = CcSmartConstructionSiteWarning.selectByWhere(where);

//        List<Map<String, Object>> outputList = new ArrayList<>();
        Map<String, Object> mapOut = new HashMap<>();
        if (records == null || records.isEmpty()) {
            //如果结果为空，那就提供随机测试数据,
            String[] warningTypes = {"安全帽识别", "烟火识别", "反光衣识别", "物料堆放识别", "起重机超载识别", "安全带佩戴识"};
            for (int i = 0; i < warningTypes.length; i++){
                Map<String, Object> map = new HashMap<>();
                map.put("name", warningTypes[i]);
                int num = (int) (Math.floor(Math.random() * 100) + 1); // 预警次数
                map.put("number", num);
                int percent = (int) (Math.floor(Math.random() * 100) + 1); // 预警次数
                if(percent > 100){
                    percent = 100;
                }
                map.put("percent", percent);
                mapOut.put("data"+i, map);
            }
        }else{
            //暂时不写
        }

        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", mapOut);
        ExtJarHelper.setReturnValue(outputMap);
    }


    /**
     * 获取状态预警统计(饼图)
     */
    public void getStatusWarningStat(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteWarningRecords> records = CcSmartConstructionSiteWarningRecords.selectByWhere(where);
        List<Map<String, Object>> outputList = new ArrayList<>();
        Map<String, Object> listMap = new HashMap<>();
        int sum = 0;
        if (records == null || records.isEmpty()) {
            String[] statuses = {"已处理", "未处理"};
            for (String status : statuses){
                Map<String, Object> map = new HashMap<>();
                map.put("name", status);
                int num = (int) (Math.floor(Math.random() * 200) + 1); // 预警次数
                sum += num;
                map.put("value", num);
                Map<String, Object> colorMap = new HashMap<>();
                if(status.equals("已处理")){
                    colorMap.put("color", "#217AFF");
                }else{
                    colorMap.put("color", "#FF6029");
                }
                map.put("itemStyle", colorMap);

                outputList.add(map);
            }
            listMap.put("list", outputList);
            listMap.put("sum", sum);
        }else{
            //暂时不写
        }

        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", listMap);
        ExtJarHelper.setReturnValue(outputMap);
    }


    /**
     * 获取预警列表
     */
    public void getWarningList(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteWarningRecords> records = CcSmartConstructionSiteWarningRecords.selectByWhere(where);
        List<Map<String, Object>> outputList = new ArrayList<>();

        if (records == null || records.isEmpty()) {
            String[] content = {"摄像头识别到未佩戴安全帽", "传感器检测到起重量超过110%",
                    "检测到位移量达50mm（警戒值30mm）", "检测到材料堆放区出现明火或烟雾",
                    "电梯重量传感器检测到载荷超过额定"};
            String[] status = {"上报", "已处理", "忽略"};
            for (int i = 0; i < 5; i++) {
                Map<String, Object> map = new HashMap<>();
                //随机0~4的整数
                int randomNum = (int) (Math.floor(Math.random() * 5));
                map.put("content", content[randomNum]);
                //随机前两天的时间,随机时分秒,时间格式为yyyy-MM-dd HH:mm:ss
                String date = LocalDateTime.now().minusDays((int) (Math.floor(Math.random() * 2))).minusMinutes((int) (Math.floor(Math.random() * 10)))
                        .minusHours((int) (Math.floor(Math.random() * 3)))
                        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                map.put("timestamp", date);
                //随机0~2的整数
                int randomNum1 = (int) (Math.floor(Math.random() * 3));
                //随机0~1的整数
                map.put("text", status[randomNum1]);
                int randomNum2 = (int) (Math.floor(Math.random() * 2));
                map.put("status", randomNum2);
                map.put("icon", "shangb"+(randomNum2+1));
                outputList.add(map);
            }
        }else{
            //暂时不写
        }

        // 将List封装到Map中
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("data", outputList);
        ExtJarHelper.setReturnValue(outputMap);
    }


    /**
     * 获取环境检测结果
     */
    public void getEnvCheckResult(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteWarningRecords> records = CcSmartConstructionSiteWarningRecords.selectByWhere(where);
        List<Map<String, Object>> outputList = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        if (records == null || records.isEmpty()) {
            //获取月份
            String[] months = {"1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"};
            List<Integer> data = new ArrayList<>();
            int value = 0 ;
            for (int i = 0; i < months.length; i++){
                // 获取当前月份（1-12）
                LocalDate now = LocalDate.now();
                int currentMonth = now.getMonthValue();
                if (i > currentMonth - 1) {
                    //大于当前月
                    value = 0;
                }else{
                    value = (int) (Math.floor(Math.random() * 100) + 22);
                }
                data.add(value);
            }

            map.put("data", data);
            map.put("months", months);
        }else{
            //暂时不写
        }

        // 将List封装到Map中
        ExtJarHelper.setReturnValue(map);
    }

    /**
     * 获取月份，从1月开始，到当前月
     *
     * @return
     */
    private List<String> getMonths() {
        // 获取当前月份（1-12）
        LocalDate now = LocalDate.now();
        int currentMonth = now.getMonthValue();

        // 生成从1月到当前月的列表
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= currentMonth; i++) {
            months.add(i);
        }

        // 输出结果（数字形式）
        System.out.println("月份数字列表：" + months); // 例如：[1, 2, 3, 4, 5, 6]

        // 输出结果（中文月份形式）
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM月");
        List<String> monthNames = new ArrayList<>();
        for (int month : months) {
            monthNames.add(formatter.format(LocalDate.of(now.getYear(), month, 1)));
        }
        return monthNames; // 例如：[01月, 02月, 03月, 04月, 05月, 06月]
    }

    /**
     * 获取视频监控
     */
    public void getVideoMonitoring(){
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String projectId = JdbcMapUtil.getString(inputMap, "projectId");

        CcPrj prj = CcPrj.selectById(projectId);
        if (prj == null) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getUserStat.error.projectNotExist");
            throw new BaseException(message);
        }

        Where where = new Where();
        where.eq("CC_PRJ_ID", projectId);
        List<CcSmartConstructionSiteWarningRecords> records = CcSmartConstructionSiteWarningRecords.selectByWhere(where);
        Map<String, Object> outputMap = new HashMap<>();
        if (records == null || records.isEmpty()) {

            String jsonStr = "[{\n" +
                    "\tdeviceSerial: 'FE8442716',\n" +
                    "\tipcSerial: 'AZ4597988',\n" +
                    "\tchannelNo: 5,\n" +
                    "\tdeviceName: '十七冶零碳炼钢项目',\n" +
                    "\tlocalName: '十七冶零碳炼钢项目',\n" +
                    "\tchannelName: '工地门口',\n" +
                    "\tstatus: 1,\n" +
                    "\tisShared: '1',\n" +
                    "\tpicUrl: 'https://statics.ys7.com/device/assets/imgs/public/homeDevice.jpeg',\n" +
                    "\tisEncrypt: 0,\n" +
                    "\tvideoLevel: 2,\n" +
                    "\trelatedIpc: true,\n" +
                    "\tisAdd: 1,\n" +
                    "\tdevType: 'DS-2DC4423IW-D',\n" +
                    "\tclass: 'online',\n" +
                    "}, {\n" +
                    "\tdeviceSerial: 'FE8442716',\n" +
                    "\tipcSerial: 'AZ4597896',\n" +
                    "\tchannelNo: 6,\n" +
                    "\tdeviceName: '十七冶零碳炼钢项目',\n" +
                    "\tlocalName: '十七冶零碳炼钢项目',\n" +
                    "\tchannelName: '材料堆场',\n" +
                    "\tstatus: 1,\n" +
                    "\tisShared: '1',\n" +
                    "\tpicUrl: 'https://statics.ys7.com/device/assets/imgs/public/homeDevice.jpeg',\n" +
                    "\tisEncrypt: 0,\n" +
                    "\tvideoLevel: 2,\n" +
                    "\trelatedIpc: true,\n" +
                    "\tisAdd: 1,\n" +
                    "\tdevType: 'DS-2DC4423IW-D',\n" +
                    "\tclass: 'online',\n" +
                    "}, {\n" +
                    "\tdeviceSerial: 'FE8442716',\n" +
                    "\tipcSerial: 'FD6939809',\n" +
                    "\tchannelNo: 7,\n" +
                    "\tdeviceName: '十七冶零碳炼钢项目',\n" +
                    "\tlocalName: '十七冶零碳炼钢项目',\n" +
                    "\tchannelName: '项目部办公区',\n" +
                    "\tstatus: 1,\n" +
                    "\tisShared: '1',\n" +
                    "\tpicUrl: 'https://statics.ys7.com/device/assets/imgs/public/homeDevice.jpeg',\n" +
                    "\tisEncrypt: 0,\n" +
                    "\tvideoLevel: 2,\n" +
                    "\trelatedIpc: true,\n" +
                    "\tisAdd: 1,\n" +
                    "\tdevType: 'DS-2DC4423IW-D',\n" +
                    "\tclass: 'online',\n" +
                    "}, {\n" +
                    "\tdeviceSerial: 'FE8442716',\n" +
                    "\tipcSerial: 'FD6939627',\n" +
                    "\tchannelNo: 3,\n" +
                    "\tdeviceName: '十七冶零碳炼钢项目',\n" +
                    "\tlocalName: '十七冶零碳炼钢项目',\n" +
                    "\tchannelName: '宿舍楼施工区(不在线）',\n" +
                    "\tstatus: 1,\n" +
                    "\tisShared: '1',\n" +
                    "\tpicUrl: 'https://statics.ys7.com/device/assets/imgs/public/homeDevice.jpeg',\n" +
                    "\tisEncrypt: 0,\n" +
                    "\tvideoLevel: 2,\n" +
                    "\trelatedIpc: true,\n" +
                    "\tisAdd: 1,\n" +
                    "\tdevType: 'DS-2DC4423IW-D',\n" +
                    "\tclass: 'offline',\n" +
                    "}, ]";
            //字符串转json
            JSONArray jsonArray = JSON.parseArray(jsonStr);

            //随机0~3的整数
            int random = (int)(Math.random()*4);
            for (int i = 0; i < jsonArray.size(); i++){
                if(i == random){
                    outputMap.put("data", jsonArray.get(i));
                }
            }
        }else{
            //暂时不写
        }

        // 将List封装到Map中
        ExtJarHelper.setReturnValue(outputMap);
    }
}