package com.bid.ext.tencentCloud;


import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.bid.ext.model.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.iai.v20200303.IaiClient;
import com.tencentcloudapi.iai.v20200303.models.*;
import org.apache.commons.codec.binary.Base64;
import org.springframework.core.io.FileSystemResource;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.StringUtils;


import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Supplier;

public class TencentCloudExt {

    private static final String SECRET_ID = ((Supplier<String>) () -> "AKIDVrN2fs8T4Z8f9dBteKxUxcOHCIRYjO2W").get();
    private static final String SECRET_KEY = ((Supplier<String>) () -> "iuCQh7QuQoFc3WxKkMyXBpRytqifi0HY").get();
    private static final String REGION = ((Supplier<String>) () -> "ap-chongqing").get();
    private static final String GROUP_ID = ((Supplier<String>) () -> "ezlinker-user1").get();

    // 地球半径
    private static final double EARTH_RADIUS = 6378137.0;

    // 转换PI值
    private static final double PI = 3.1415926535897932384626;

    //初始化IaiClient
    public static IaiClient iaiClient;

    static {
        Credential credential = new Credential(SECRET_ID, SECRET_KEY);
        iaiClient = new IaiClient(credential, REGION);
    }


    //人脸入库
    public void createPerson() {
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;

        //获取上传的图片
        FlFile flFile = FlFile.selectById(valueMap.get("FACE_PIC").toString());
        String filePath = flFile.getPhysicalLocation();
//        filePath = "/Users/hejialun/Pictures/hjl-face.jpg";
        FileSystemResource fileSystemResource = new FileSystemResource(filePath);

        InputStream inputStream = null;
        String img = null;
        try {
            inputStream = fileSystemResource.getInputStream();
            byte[] imageBytes = new byte[inputStream.available()];
            inputStream.read(imageBytes);
            inputStream.close();
            img = Base64.encodeBase64String(imageBytes);
        } catch (IOException e) {
            throw new BaseException("人脸图片未找到！");
        }

        AdUser adUser = AdUser.selectById(valueMap.get("AD_USER_ID").toString());

        CreatePersonRequest createPersonRequest = new CreatePersonRequest();
        createPersonRequest.setGroupId(GROUP_ID);
        createPersonRequest.setPersonId(valueMap.get("AD_USER_ID").toString());
        createPersonRequest.setImage(img);

        JSONObject entries = JSONUtil.parseObj(adUser.getName());

        createPersonRequest.setPersonName(entries.get("ZH_CN").toString());
        createPersonRequest.setNeedRotateDetection(0L);
        createPersonRequest.setQualityControl(3L);
        createPersonRequest.setUniquePersonControl(1L);

        CreatePersonResponse createPersonResponse = null;
        try {
            createPersonResponse = iaiClient.CreatePerson(createPersonRequest);
        } catch (TencentCloudSDKException e) {
            throw new BaseException(e.getMessage());
        }

        if (StringUtils.hasLength(createPersonResponse.getSimilarPersonId())) {
            throw new BaseException("人脸库存在ID为'" + createPersonResponse.getSimilarPersonId() + "'相似人脸！");
        }

        if (createPersonResponse.getFaceRect() == null) {
            throw new BaseException("入库失败，人脸照片可能模糊、过暗、面部遮挡！");
        }

    }

    //人脸打卡，验证人脸，添加打卡记录
    public void verifyPerson() {

        LocalDateTime now = LocalDateTime.now();

        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        //坐标
        String lot = String.valueOf(extApiParamMap.get("lot"));
        String lat = String.valueOf(extApiParamMap.get("lat"));
        String img = String.valueOf(extApiParamMap.get("faceImg"));
        String hitLocation = String.valueOf(extApiParamMap.get("hitLocation"));

        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //活体检测
        DetectLiveFaceAccurateRequest request = new DetectLiveFaceAccurateRequest();
        request.setImage(img);
        DetectLiveFaceAccurateResponse detectLiveFaceAccurateResponse = null;
        try {
            detectLiveFaceAccurateResponse = iaiClient.DetectLiveFaceAccurate(request);
        } catch (TencentCloudSDKException e) {
            throw new BaseException(e.getMessage());
        }

        Map<String, Object> resultMap = new HashMap<>();

        //推荐阈值40
        if (detectLiveFaceAccurateResponse.getScore() < 40) {
            resultMap.put("code", 500);
            resultMap.put("msg", "请将人脸移入框内");
        } else {
            //人脸验证
            VerifyPersonRequest verifyPersonRequest = new VerifyPersonRequest();
            verifyPersonRequest.setPersonId(userId);
            verifyPersonRequest.setImage(img);
            VerifyPersonResponse verifyPersonResponse = null;
            try {
                verifyPersonResponse = iaiClient.VerifyPerson(verifyPersonRequest);
            } catch (TencentCloudSDKException e) {
                throw new BaseException(e.getMessage());
            }

            //匹配成功判定
            if (verifyPersonResponse.getIsMatch()) {
                MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
                String queryRule = "SELECT  MEMBER.AD_USER_ID,SHIFT.ATD_TIME_FR,SHIFT.ATD_TIME_TO ,AREA.LONGITUDE,AREA.LATITUDE,AREA.RADIUS,`GROUP`.IS_OUT_OF_ATD_AREA_ALLOWED,AREA.ID AREA_ID" +
                        " FROM   cc_atd_group_member MEMBER  , cc_atd_group `GROUP` ,  cc_atd_work_shift SHIFT , cc_atd_area AREA " +
                        " WHERE MEMBER.AD_USER_ID = ? AND  MEMBER.CC_ATD_GROUP_ID=`GROUP`.ID AND  `GROUP`.CC_ATD_RULE_ID =  SHIFT.CC_ATD_RULE_ID AND  `GROUP`.CC_ATD_RULE_ID = AREA.CC_ATD_RULE_ID";

                List<Map<String, Object>> maps = null;
                try {
                    maps = myJdbcTemplate.queryForList(queryRule, userId);

                } catch (EmptyResultDataAccessException e) {
                    throw new BaseException("人员未加入考勤组！");
                }

                boolean inAtdArea = false;
                boolean isOutOfAtdAreaAllowed = false;
                String areaId = null;
                Time timeFr = null;
                Time timeTo = null;
                for (Map<String, Object> map : maps) {
                    BigDecimal longitude = (BigDecimal) map.get("LONGITUDE");
                    BigDecimal latitude = (BigDecimal) map.get("LATITUDE");
                    Integer radius = (Integer) map.get("RADIUS");

                    timeFr = (Time) map.get("ATD_TIME_FR");
                    timeTo = (Time) map.get("ATD_TIME_TO");

                    double distance = getDistance(Double.valueOf(lot), Double.valueOf(lat), longitude.doubleValue(), latitude.doubleValue());

                    if (distance <= radius.doubleValue()) {
                        inAtdArea = true;
                        areaId = (String) map.get("AREA_ID");
                    }

                    if ((Integer) map.get("IS_OUT_OF_ATD_AREA_ALLOWED") == 1) {
                        isOutOfAtdAreaAllowed = true;
                    }
                }

                if (!inAtdArea && !isOutOfAtdAreaAllowed) {
                    throw new BaseException("打卡失败，不在考勤范围内！");
                }

                //中午时间
                LocalDateTime of = LocalDateTime.of(now.getYear(), now.getMonthValue(), now.getDayOfMonth(), 12, 0, 0);

                //打开是否正常
                boolean isNormal = false;
                if (of.compareTo(now) > 0) {//上班
                    isNormal = now.compareTo(timeFr.toLocalTime().atDate(now.toLocalDate())) < 1;
                } else {//下班
                    isNormal = now.compareTo(timeTo.toLocalTime().atDate(now.toLocalDate())) > -1;
                }

                //CC_ATD_HIT  考勤记录
                CcAtdHit ccAtdHit = CcAtdHit.newData();
                ccAtdHit.setCcAtdAreaId(areaId);
                ccAtdHit.setIsOutOfAtdArea(inAtdArea ? false : true);
                ccAtdHit.setHitDttm(now);
                ccAtdHit.setHitLocation(hitLocation);
                ccAtdHit.setHitLatitude(new BigDecimal(lat));
                ccAtdHit.setHitLongitude(new BigDecimal(lot));
                ccAtdHit.setIsSupplementHit(false);
                ccAtdHit.setIsOnDuty(of.compareTo(now) > 0 ? true : false);
                ccAtdHit.setIsNormal(isNormal);

                ccAtdHit.insertById();

                resultMap.put("code", 200);
                resultMap.put("msg", "打卡成功");
                resultMap.put("siOnDuty", of.compareTo(now) > 0 ? true : false);
                resultMap.put("isNormal", isNormal);
                resultMap.put("isOutOfAtdArea", inAtdArea ? false : true);
                resultMap.put("hitLocation", hitLocation);
                resultMap.put("hitDttm", ccAtdHit.getHitDttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));


            } else {
                resultMap.put("code", 500);
                resultMap.put("msg", "打卡失败，请将人脸移入相机！");
            }

        }

        ExtJarHelper.setReturnValue(resultMap);
    }

    //检查是否在考勤范围
    public void checkAtdLocation() {

        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        //用户当前坐标
        String lot1 = String.valueOf(extApiParamMap.get("lot"));
        String lat1 = String.valueOf(extApiParamMap.get("lat"));

        String userId = ExtJarHelper.getLoginInfo().userInfo.id;

        //考勤坐标
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        String queryLocation = "SELECT DISTINCT `GROUP`.IS_OUT_OF_ATD_AREA_ALLOWED,AREA.LONGITUDE,AREA.LATITUDE,AREA.RADIUS,AREA.NAME,AREA.ID ,SHIFT.ATD_TIME_FR,SHIFT.ATD_TIME_TO" +
                " FROM  CC_ATD_GROUP_MEMBER  MEMBER, CC_ATD_GROUP `GROUP`, CC_ATD_RULE RULE, CC_ATD_AREA AREA, cc_atd_work_shift SHIFT " +
                " WHERE MEMBER.CC_ATD_GROUP_ID = `GROUP`.ID AND `GROUP`.CC_ATD_RULE_ID = RULE.ID AND RULE.ID = AREA.CC_ATD_RULE_ID AND `GROUP`.CC_ATD_RULE_ID =  SHIFT.CC_ATD_RULE_ID" +
                " AND MEMBER.AD_USER_ID = ?";

        List<Map<String, Object>> result = null;
        try {
            result = myJdbcTemplate.queryForList(queryLocation, userId);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException("人员未加入考勤组！");
        }

        String atdAreaName = "";
        String atdAreaId = "";
        boolean inAtdArea = false;
        boolean isOutOfAtdAreaAllowed = false;

        for (Map<String, Object> map : result) {
            BigDecimal longitude = (BigDecimal) map.get("LONGITUDE");
            BigDecimal latitude = (BigDecimal) map.get("LATITUDE");
            Integer radius = (Integer) map.get("RADIUS");

            double distance = getDistance(Double.valueOf(lot1), Double.valueOf(lat1), longitude.doubleValue(), latitude.doubleValue());

            if (distance <= radius.doubleValue()) {
                atdAreaName = (String) map.get("NAME");
                atdAreaId = (String) map.get("ID");

                inAtdArea = true;
            }
            if ((Integer) map.get("IS_OUT_OF_ATD_AREA_ALLOWED") == 1) {
                isOutOfAtdAreaAllowed = true;
            }

        }

        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("atdAreaName", atdAreaName);//考勤区名称
        resultMap.put("atdAreaId", atdAreaId);//考勤区id
        resultMap.put("inAtdArea", inAtdArea);//是否在考勤区内
        resultMap.put("isOutOfAtdAreaAllowed", isOutOfAtdAreaAllowed);//是否允许外勤

        //今日考勤记录
        LocalDate today = LocalDate.now();
        LocalDateTime todayStart = LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 0, 0, 0);
        LocalDateTime todayEnd = LocalDateTime.of(today.getYear(), today.getMonthValue(), today.getDayOfMonth(), 23, 59, 59);

        List<CcAtdHit> atdHits = null;
        try {
            atdHits = CcAtdHit.selectByWhere(new Where().betweenAnd("HIT_DTTM", todayStart, todayEnd));
        } catch (EmptyResultDataAccessException e) {
            atdHits = new ArrayList<>();
        }
        if(atdHits==null){
            atdHits = new ArrayList<>();
        }

        List<Map<String, Object>> hits = new ArrayList<>();

        for (CcAtdHit ccAtdHit : atdHits) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("hitDttm", ccAtdHit.getHitDttm().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            map.put("isOutOfAtdArea", ccAtdHit.getIsOutOfAtdArea());
            map.put("isNormal", ccAtdHit.getIsNormal());
            map.put("isOnDuty", ccAtdHit.getIsOnDuty());
            map.put("ccAtdAreaId", ccAtdHit.getCcAtdAreaId());
            hits.add(map);
        }

        //最新考勤记录
        resultMap.put("atdHits", hits);

        ExtJarHelper.setReturnValue(resultMap);

    }

    //考勤统计
    public void calculateAtdHit() {

        Map<String, Object> varMap = ExtJarHelper.getVarMap();
        //请求参数
        String startDate = varMap.get("P_FROM_DATE") == null ? null : varMap.get("P_FROM_DATE").toString();
        String endDate = varMap.get("P_TO_DATE") == null ? null : varMap.get("P_TO_DATE").toString();
        String groupIds = varMap.get("P_CC_ATD_GROUP_IDS") == null ? null : varMap.get("P_CC_ATD_GROUP_IDS").toString();
        String userIds = varMap.get("P_AD_USER_IDS") == null ? null : varMap.get("P_AD_USER_IDS").toString();


        String queryHit = "SELECT " +
                "HIT.AD_USER_ID ,  HIT.HIT_DTTM  ,HIT.CC_ATD_AREA_ID ,HIT.IS_NORMAL,HIT.IS_ON_DUTY" +
                ",AREA.ID AREA_ID ,AREA.CC_ATD_RULE_ID " +
                " FROM  cc_atd_hit  HIT, cc_atd_area AREA " +
                " WHERE  HIT.CC_ATD_AREA_ID=AREA.ID   AND  HIT.HIT_DTTM BETWEEN ? AND ? ";

        if (StringUtils.hasLength(userIds)) {
            queryHit += "AND  HIT.AD_USER_ID IN (" + userIds + ")";
        }
        if (!StringUtils.hasLength(startDate)) {
            startDate = LocalDate.now().toString();

        }
        if (!StringUtils.hasLength(endDate)) {
            endDate = LocalDate.now().toString();
        } else {
            if (LocalDate.parse(endDate).compareTo(LocalDate.now()) > 0) {
                endDate = LocalDate.now().toString();
            }
        }

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();

        //所有考勤记录
        List<Map<String, Object>> hits = null;
        try {
            hits = myJdbcTemplate.queryForList(queryHit, startDate, LocalDate.parse(endDate).plusDays(1));
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException("所选日期内没有打卡记录！");
        }

        List<Map<String, Object>> groups = null;
        //查询考勤组
        if (StringUtils.hasLength(groupIds)) {
            String queryGroup = "SELECT `GROUP`.CC_ATD_RULE_ID ,  `GROUP`.ID GROUP_ID" +
                    " FROM cc_atd_group `GROUP` " +
                    " WHERE `GROUP`.ID  IN (" + groupIds +
                    ")";
            try {
                groups = myJdbcTemplate.queryForList(queryGroup);
            } catch (EmptyResultDataAccessException e) {
                throw new BaseException("当前所选考勤组记录为空！");
            }
        }

        String queryMember = "SELECT  MEMBER.CC_ATD_GROUP_ID , MEMBER.AD_USER_ID,`GROUP`.CC_ATD_RULE_ID " +
                " FROM cc_atd_group_member MEMBER,cc_atd_group `group` " +
                " WHERE MEMBER.CC_ATD_GROUP_ID = `group`.ID";
        //查询考勤人员
        if (StringUtils.hasLength(userIds)) {
            queryMember += " AND  MEMBER.AD_USER_ID IN (" + userIds + ")";
        }
        List<Map<String, Object>> members = null;
        try {
            members = myJdbcTemplate.queryForList(queryMember);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException("当前所选人员未加入考勤组！");
        }

        List<Map<String, Object>> calculateMembers = new ArrayList<>();
        //应考勤人员
        if (groups != null) {

            for (Map<String, Object> groupMap : groups) {
                String groupId = (String) groupMap.get("GROUP_ID");

                for (Map<String, Object> memberMap : members) {
                    if (groupId.equals(memberMap.get("CC_ATD_GROUP_ID"))) {
                        calculateMembers.add(memberMap);
                    }
                }
            }
        } else {
            calculateMembers = members;
        }

        if (calculateMembers.isEmpty()) {
            throw new BaseException("所选考勤人员未在所选考勤组中！");
        }

        String queryRules = " SELECT   RULE.ID RULE_ID,RULE.CC_DAY_OF_WEEK_IDS " +
                "FROM cc_atd_rule RULE ";
        if (StringUtils.hasLength(groupIds)) {
            queryRules += ", cc_atd_group `GROUP` " +
                    "WHERE  `GROUP`.CC_ATD_RULE_ID = RULE.ID AND  `GROUP`.ID IN(" + groupIds + ")";
        }

        List<Map<String, Object>> ruleMaps = null;
        try {
            ruleMaps = myJdbcTemplate.queryForList(queryRules);
        } catch (EmptyResultDataAccessException e) {
            throw new BaseException("考勤规则未配置！");
        }

        //特殊考勤日期
        String querySpData = " SELECT   SP.ATD_DATE,SP.IS_WORKDAY,SP.CC_ATD_RULE_ID " +
                "FROM   cc_atd_sp_date SP " +
                "where  sp.ATD_DATE BETWEEN ? AND ? ";

        List<Map<String, Object>> spDateMaps = null;
        try {
            spDateMaps = myJdbcTemplate.queryForList(querySpData, startDate, endDate);
        } catch (EmptyResultDataAccessException e) {
            spDateMaps = new ArrayList<>();
        }

        //需要计算的日期范围
        LocalDate fromDate = LocalDate.parse(startDate);
        LocalDate toDate = LocalDate.parse(endDate);
        Set<LocalDate> dateRang = new HashSet<>();
        dateRang.add(fromDate);
        if (fromDate.compareTo(toDate) < 0) {
            long between = ChronoUnit.DAYS.between(fromDate, toDate);
            for (int i = 0; i < between; i++) {
                dateRang.add(fromDate.plusDays(i + 1));
            }
        }

        //考勤规则的应考勤日期
        Map<String, Set<LocalDate>> workDayOfRule = new HashMap<>();
        for (Map<String, Object> rule : ruleMaps) {
            String dayOfWeeks = (String) rule.get("CC_DAY_OF_WEEK_IDS");//每周考勤日
            String[] dayOfWeekA = dayOfWeeks.split(",");

            //应考勤日日期
            Set<LocalDate> workDay = new HashSet<>();

            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            for (LocalDate localDate : dateRang) {//日期范围内

                for (String dayOfWeek : dayOfWeekA) {//当前规则的每周考勤日
                    if (localDate.getDayOfWeek().getValue() - 1 == Integer.parseInt(dayOfWeek)) {//常规打卡日期
                        workDay.add(localDate);
                    }
                }

                //特殊考勤日期
                for (Map<String, Object> spDate : spDateMaps) {
                    //当前规则的特殊日期
                    if (rule.get("RULE_ID").equals(spDate.get("CC_ATD_RULE_ID"))) {
                        Date atdDate = (Date) spDate.get("ATD_DATE");
                        LocalDate spD = LocalDate.parse(formatter.format(atdDate));
                        if (spD.compareTo(localDate) == 0) {
                            Integer isWorkday = (Integer) spDate.get("IS_WORKDAY");
                            if (isWorkday == 0) {//当前日期不工作
                                workDay.remove(localDate);
                            } else {
                                workDay.add(localDate);//当前日期为工作日
                            }
                        }
                    }
                }
            }
            //当前规则考勤日期
            workDayOfRule.put((String) rule.get("RULE_ID"), workDay);
        }

        //考勤结果查询
        Where queryResultWhere = new Where();
        queryResultWhere.sql("T.ATD_DATE BETWEEN '" + startDate + "' AND '" + endDate + "'");
        List<CcAtdResult> ccAtdResults1 = CcAtdResult.selectByWhere(queryResultWhere);
        if (ccAtdResults1 == null) {
            ccAtdResults1 = new ArrayList<>();
        }

        //需考勤人员考勤计算
        for (Map<String, Object> memner : calculateMembers) {
            String memberUserId = (String) memner.get("AD_USER_ID");
            String ruleId = (String) memner.get("CC_ATD_RULE_ID");

            //对应规则下的考勤日
            Set<LocalDate> workDay = workDayOfRule.get(ruleId);
            if (workDay == null) {
                throw new BaseException("考勤规则不存在！");
            }
            for (LocalDate day : workDay) {
                //当日考勤是否正常
                boolean resultIsNormal = false;
                boolean clockInNormal = false;
                boolean clockOutNormal = false;
                boolean haveClockIn = false;
                boolean haveClockOut = false;
                String remark = null;

                for (Map<String, Object> hit : hits) {//考勤结果
                    LocalDateTime hitTime = (LocalDateTime) hit.get("HIT_DTTM");//考勤时间
                    String userId = (String) hit.get("AD_USER_ID");//考勤时间

                    if (hitTime.toLocalDate().compareTo(day) == 0 && memberUserId.equals(userId)) {
                        Integer isNormal = (Integer) hit.get("IS_NORMAL");
                        Integer isOnDuty = (Integer) hit.get("IS_ON_DUTY");

                        if (isOnDuty == 1) {//上班考勤
                            if (isNormal == 1) {
                                clockInNormal = true;
                            }
                            haveClockIn = true;
                        } else {//下班考勤
                            if (isNormal == 1) {
                                clockOutNormal = true;
                            }
                            haveClockOut = true;
                        }
                    }
                }

                if (clockInNormal && clockOutNormal) {//上下班考勤都正常
                    resultIsNormal = true;
                } else {
                    if (haveClockIn && haveClockOut) {
                        if (clockInNormal && clockOutNormal) {//上下班考勤都正常
                            remark = "正常";
                        } else if (!clockInNormal && clockOutNormal) {//迟到
                            remark = "迟到";
                        } else if (clockInNormal && !clockOutNormal) {//早退
                            remark = "早退";
                        } else if (!clockInNormal && !clockOutNormal) {//迟到
                            remark = "迟到、早退";
                        }
                    } else if (haveClockIn && !haveClockOut) {
                        if (clockInNormal) {//缺下班卡
                            remark = "下班缺卡";
                        } else {//迟到
                            remark = "迟到、下班缺卡";
                        }
                    } else if (!haveClockIn && haveClockOut) {
                        if (clockOutNormal) {//缺下班卡
                            remark = "上班缺卡";
                        } else {//早退
                            remark = "上班缺卡、早退";
                        }
                    } else if (!haveClockIn && !haveClockOut) {
                        remark = "上班缺卡、下班缺卡";
                    }
                }

                //当前人员是否已有当前日期考勤
//                Where where = new Where();
//                where.eq("ATD_DATE", day);
//                where.eq("AD_USER_ID", memberUserId);
//                List<CcAtdResult> ccAtdResults = CcAtdResult.selectByWhere(where);
                List<CcAtdResult> ccAtdResults = new ArrayList<>();
                for (CcAtdResult res : ccAtdResults1) {
                    if (memberUserId.equals(res.getAdUserId()) && day.compareTo(res.getAtdDate()) == 0) {
                        ccAtdResults.add(res);
                    }
                }

                if (ccAtdResults.size() > 1) {
                    for (int i = 1; i < ccAtdResults.size(); i++) {
                        //删除
                        CcAtdResult result = ccAtdResults.get(i);
                        result.deleteById();
                    }
                }

                CcAtdResult result = null;
                if (ccAtdResults.size() > 0) {//存在，更新
                    result = ccAtdResults.get(0);
                    result.setIsNormal(resultIsNormal);
                    result.setRemark(remark);
                    result.updateById();
                } else {//不存在，新增
                    result = CcAtdResult.newData();
                    result.setAtdDate(day);
                    result.setAdUserId(memberUserId);
                    result.setIsNormal(resultIsNormal);
                    result.setRemark(remark);
                    result.insertById();
                }
            }
        }
    }


    // 经纬度转换为弧度
    private double getRad(double d) {
        return d * PI / 180.0;
    }

    // 两点间距计算
    public double getDistance(double longtitude1, double latitude1, double longtitude2, double latitude2) {
        double radLat1 = getRad(latitude1);
        double radLat2 = getRad(latitude2);
        double a = radLat1 - radLat2;
        double b = getRad(longtitude1) - getRad(longtitude2);

        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2) * Math.pow(Math.sin(b / 2), 2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        return s;
    }

}
