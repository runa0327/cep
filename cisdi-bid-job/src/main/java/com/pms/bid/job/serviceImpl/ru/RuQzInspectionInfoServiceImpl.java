package com.pms.bid.job.serviceImpl.ru;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pms.bid.job.component.ru.RuQzPlatformUtil;
import com.pms.bid.job.domain.AdAtt;
import com.pms.bid.job.domain.FlFile;
import com.pms.bid.job.domain.FlPath;
import com.pms.bid.job.domain.qingZhu.*;
import com.pms.bid.job.mapper.file.AdAttMapper;
import com.pms.bid.job.mapper.file.FlFileMapper;
import com.pms.bid.job.mapper.file.FlPathMapper;
import com.pms.bid.job.mapper.ru.*;
import com.pms.bid.job.mapper.ru.RuQzInspectionInfoMapper;
import com.pms.bid.job.service.ru.RuQzInspectionInfoService;
import com.pms.bid.job.service.ru.RuQzInspectionInfoService;
import com.pms.bid.job.util.DateUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.querydsl.QuerydslRepositoryInvokerAdapter;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
@Transactional
public class RuQzInspectionInfoServiceImpl implements RuQzInspectionInfoService {

    @Resource
    private RuQzInspectionInfoMapper inspectionInfoMapper;

    @Resource
    private RuQzInspectionInfoReplayMapper inspectionInfoReplayMapper;

    @Resource
    private RuQzInspectionItemMapper inspectionItemMapper;

    @Resource
    private RuQzInspectionAttMapper inspectionAttMapper;

    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    private RuQzPlatformUtil ruQzPlatformUtil;

    @Resource
    private AdAttMapper adAttMapper;

    @Resource
    private FlPathMapper flPathMapper;

    @Resource
    private FlFileMapper flFileMapper;

    @Resource
    private RuQzInspectionResultMapper inspectionResultMapper;

    @Resource
    private RuQzInspectionDangerLevelMapper inspectionDangerLevelMapper;

    @Override
    public void syncQzInspectionInfo() {

        String  token = ruQzPlatformUtil.getQzToken();

        if (!StringUtils.hasLength(token)){
            throw new BaseException("token为空");
        }

        syncSafeInspectionInfo(token);//同步安全巡检

    }

    /**
     * 获取巡检列表
     * @param token
     */
    private void syncSafeInspectionInfo(String token)  {
        String  requestUrl =  "https://open.qingzhuyun.com/api/inspect/getSafetyListPage";

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含
        requestMap.put("projectId", "d16f0c99b8b54feb96b3b7422c3755a0");//轻筑项目ID
        requestMap.put("pageNum",1);//页码
        requestMap.put("pageSize",9999);//大小

        HttpHeaders translateHeaders = new HttpHeaders();
        translateHeaders.set("auth-token", token);
        translateHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 设置请求体
        HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);
        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, translateEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject body = JSONUtil.parseObj(response.getBody());
            JSONObject res = body.getJSONObject("data");
            JSONObject data = res.getJSONObject("data");
            JSONArray dataList = data.getJSONArray("dataList");

            if (dataList==null) {
                log.error("获取数据列表失败");
                return;
            }
            Iterator<Object> iterator = dataList.iterator();
            while(iterator.hasNext()){//循环列表数据
                Object next =  iterator.next();
                JSONObject entries = JSONUtil.parseObj(next);
                String recordId = (String) entries.get("id");//轻筑记录id
                String rectTime = (String) entries.get("rectTime");//整改时间
                String result = (String) entries.get("result");//巡检结果
                String noticePerson = (String) entries.get("noticePerson");
                String property = (String) entries.get("property");
                String inspectPerson = (String) entries.get("inspectPerson");//巡检人
                String remarks = (String) entries.get("remarks");//巡检描述
//                String initiationTime = (String) entries.get("initiationTime");
                String inspectItem = (String) entries.get("inspectItem");//巡检项
                String status = (String) entries.get("status");//巡检状态
//                JSONArray images = entries.getJSONArray("image");//巡检图片
                String initiationTime = (String) entries.get("initiationTime");//巡检时间

                //查询数据是否存在
                LambdaQueryWrapper<RuQzInspectionInfo> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(RuQzInspectionInfo::getRuQzInspectionRecordId,recordId); //查询巡检记录是否存在
                wrapper.eq(RuQzInspectionInfo::getRuQzInspectionType,1);

                List<RuQzInspectionInfo> ruQzInspectionInfos = inspectionInfoMapper.selectList(wrapper);

                if (ruQzInspectionInfos==null || ruQzInspectionInfos.size()<1){
                    //查询详情
                    JSONObject inspectionDetail = getInspectionDetail(recordId, token);

                    JSONArray patrolList = inspectionDetail.getJSONArray("patrolList");//巡检内容
                    if(patrolList==null){
                        log.error("巡检内容获取错误");
                        throw new BaseException("巡检内容获取错误");
                    }

                    Iterator<Object> iterator1 = patrolList.iterator();

                    while(iterator1.hasNext()){
                        //创建主记录
                        RuQzInspectionInfo ruQzInspectionInfo = normalNewQzInspectionInfo();
                        ruQzInspectionInfo.setRuQzInspectionRecordId(recordId);
                        ruQzInspectionInfo.setRuQzInspectionType(1);//巡检类型，安全巡检

                        ruQzInspectionInfo.setRemark(remarks);//巡检描述
//                        ruQzInspectionInfo.setRuQzInspectionCheckItems(inspectItem);//巡检项
//                        ruQzInspectionInfo.setRuQzInspectionCheckResult(result);//巡检结果
                        ruQzInspectionInfo.setRuQzInspectionChecker(inspectPerson);//巡检人
                        ruQzInspectionInfo.setRuQzInspectionStatus(status);//巡检状态
                        ruQzInspectionInfo.setRuQzInspectionRectTime(rectTime);//整改时间
                        ruQzInspectionInfo.setRuQzInspectionCheckTime(initiationTime);// 检查时间
                        ruQzInspectionInfo.setRuQzInspectionReorganizer(noticePerson);//通知人

                        Object patro = iterator1.next();
                        JSONObject entries1 = JSONUtil.parseObj(patro);//巡检内容
                        String projectLocation = (String)entries1.get("projectLocation");//项目地址
                        ruQzInspectionInfo.setRuQzInspectionProjectLocation(projectLocation);
                        String dangerLevel = (String)entries1.get("dangerLevel");//隐患等级
                        if (StringUtils.hasLength(dangerLevel)){ //隐患等级
                            LambdaQueryWrapper<RuQzInspectionDangerLevel> levelLambdaQueryWrapper = new LambdaQueryWrapper<>();
                            levelLambdaQueryWrapper.eq(RuQzInspectionDangerLevel::getName,dangerLevel);
                            levelLambdaQueryWrapper.eq(RuQzInspectionDangerLevel::getRuQzInspectionType,1);
                            RuQzInspectionDangerLevel ruQzInspectionDangerLevel = inspectionDangerLevelMapper.selectOne(levelLambdaQueryWrapper);

                            if(ruQzInspectionDangerLevel==null){
                                ruQzInspectionInfo.setRuQzInspectionDangerLevelId("1888887369215811584");
                            }else{
                                ruQzInspectionInfo.setRuQzInspectionDangerLevelId(ruQzInspectionDangerLevel.getId());
                            }
                        }else{
                            ruQzInspectionInfo.setRuQzInspectionDangerLevelId("1888887369215811584");
                        }

                        ruQzInspectionInfo.setRuQzInspectionDangerLevel(dangerLevel);
                        String checkItems = (String)entries1.get("checkItems");//巡检项
                        String checkResult = (String)entries1.get("checkResult");//巡检内容
                        ruQzInspectionInfo.setRuQzInspectionCheckResult(checkResult);//巡检结果
                        if(StringUtils.hasLength(checkResult)) {
                            LambdaQueryWrapper<RuQzInspectionResult> resultLambdaQueryWrapper = new LambdaQueryWrapper<>();
                            resultLambdaQueryWrapper.eq(RuQzInspectionResult::getName,checkResult);
                            RuQzInspectionResult ruQzInspectionResult = inspectionResultMapper.selectOne(resultLambdaQueryWrapper);
                            if (ruQzInspectionResult!=null){
                                System.out.println(ruQzInspectionResult);
                                ruQzInspectionInfo.setRuQzInspectionResultId(ruQzInspectionResult.getId());
                            }else{
                                ruQzInspectionInfo.setRuQzInspectionResultId("3");//书面整改
                            }
                        }else {
                            ruQzInspectionInfo.setRuQzInspectionResultId("3");//书面整改
                        }

                        String outcomeMap = (String)entries1.get("outcomeMap");//可能后果
                        ruQzInspectionInfo.setRuQzInspectionOutcomeMap(outcomeMap);
                        String dangerMap = (String)entries1.get("dangerMap");//原因分析
                        ruQzInspectionInfo.setRuQzInspectionDangerMap(dangerMap);
                        String opinionMap = (String)entries1.get("opinionMap");//处理意见
                        ruQzInspectionInfo.setRuQzInspectionOpinionMap(opinionMap);
                        JSONArray urls = entries1.getJSONArray("urls");//巡检图片

                        if (StringUtils.hasLength(property)) {//巡检性质
                            LambdaQueryWrapper<RuQzInspectionAtt> attWrapper = new LambdaQueryWrapper<>();
                            attWrapper.eq(RuQzInspectionAtt::getName,property);
                            attWrapper.eq(RuQzInspectionAtt::getRuQzInspectionType,1);
                            RuQzInspectionAtt selectOne = inspectionAttMapper.selectOne(attWrapper);
                            ruQzInspectionInfo.setRuQzInspectionAttId(selectOne.getId());//巡检性质
                        }else{
                            ruQzInspectionInfo.setRuQzInspectionAttId("1888069481003880448");//巡检性质
                        }

                        if(StringUtils.hasLength(checkItems)) {//巡检项
                            ruQzInspectionInfo.setRuQzInspectionCheckItems(checkItems);
                            String[] split = checkItems.split("/");

                                String str = split[0];

                                LambdaQueryWrapper<RuQzInspectionItem> itemWrapper = new LambdaQueryWrapper<>();
                                itemWrapper.eq(RuQzInspectionItem::getName,str);
                                itemWrapper.eq(RuQzInspectionItem::getRuQzInspectionType,1);
                                RuQzInspectionItem inspectionItem = inspectionItemMapper.selectOne(itemWrapper);

                                if (inspectionItem==null){ //没有匹配的数据
                                    System.out.println(str+";;");
                                    break;
                                }

                            if (inspectionItem!=null){

                                ruQzInspectionInfo.setRuQzInspectionItemIds(inspectionItem.getId());
                            }else{
                                ruQzInspectionInfo.setRuQzInspectionItemIds("1888851802825105408");//巡检项
                            }
                        }else{
                            ruQzInspectionInfo.setRuQzInspectionItemIds("1888851802825105408");//巡检项
                        }

                        if (urls!=null) {//判断图片列表是否存在
                            Iterator<Object> imageIterator = urls.iterator(); //巡检图片迭代

                            String insImgs = "";
                            while (imageIterator.hasNext()) { //整改图片列表
                                String imgUrl = (String) imageIterator.next();
                                //保存图片到本地
                                try {
                                    FlFile flFile = saveImg(imgUrl, "巡检图片",initiationTime);

                                    insImgs += flFile.getId();
                                    if (imageIterator.hasNext()) {
                                        insImgs += ",";
                                    }
                                } catch (IOException e) {
                                    log.error("巡检图片保存失败：" + new Date());
                                }
                            }
                            if (StringUtils.hasText(insImgs)) {
                                ruQzInspectionInfo.setRuQzInspectionUrls(insImgs);//设置巡检图片
                            }
                        }

                        inspectionInfoMapper.insert(ruQzInspectionInfo);

                        //整改记录
                        JSONArray replayList = entries1.getJSONArray("replayList");
                        if(replayList == null){
                            break;
                        }

                        Iterator<Object> iterator2 = replayList.iterator();
                        //保存记录
                        while (iterator2.hasNext()){
                            Object replayStr = iterator2.next();
                            JSONObject replay = JSONUtil.parseObj(replayStr);

                            RuQzInspectionInfoReplay ruQzInspectionInfoReplay = normalNewQzInspectionInfoReplay();
                            ruQzInspectionInfoReplay.setRuQzInspectionInfoId(ruQzInspectionInfo.getId());

                            String replayRecordId = (String) replay.get("recordId");//记录id
                            ruQzInspectionInfoReplay.setRuQzInspectionRepRecordId(replayRecordId);
                            Long createTime = (Long) replay.get("createTime");//创建时间
                            ruQzInspectionInfoReplay.setRuQzInspectionRepCreateTime(DateUtil.convertTimestampToDateString(createTime.toString(),"yyyy-MM-dd HH:mm:ss"));
                            String replayType = (String) replay.get("replayType");//类型：0通知回复, 1整改回复(记录)，2回检回复(记录)，3审批(流程)，4整改(流程)，5复检(流程)
                            ruQzInspectionInfoReplay.setRuQzInspectionReplayType(replayType);
                            String recheckStatus = (String) replay.get("recheckStatus");//状态：0未通过，1整改回复-已确认，2整改回复-已处理，3复检-通过，4复检-退回，5审批-通过，6审批-退回
                            ruQzInspectionInfoReplay.setRuQzInspectionRecheckStatus(recheckStatus);
                            String emergencyResponseMeasures = (String) replay.get("emergencyResponseMeasures");//应急处置措施
                            ruQzInspectionInfoReplay.setRuQzInspectionEmrRespMea(emergencyResponseMeasures);
                            String laborOrgName = (String) replay.get("laborOrgName");//人员组织
                            ruQzInspectionInfoReplay.setRuQzInspectionRepLaborOrgName(laborOrgName);
                            String actualRectificationCompletionTime = (String) replay.get("actualRectificationCompletionTime");//实际整改完成时间
                            ruQzInspectionInfoReplay.setRuQzInspectionActRectComTime(actualRectificationCompletionTime);
                            String laborName = (String) replay.get("laborName");//处理人姓名
                            ruQzInspectionInfoReplay.setRuQzInspectionRepLaborName(laborName);
                            String replayContent = (String) replay.get("replayContent");//填写内容
                            ruQzInspectionInfoReplay.setRuQzInspectionReplayContent(replayContent);
                            Long replayTime = (Long) replay.get("replayTime");//回复时间（可能是前端页面选择时间）
                            ruQzInspectionInfoReplay.setRuQzInspectionRepReplayTime(DateUtil.convertTimestampToDateString(replayTime.toString(),"yyyy-MM-dd HH:mm:ss"));

                            JSONArray accessoryList = replay.getJSONArray("accessoryList");//整改图片
                            String replayImgs = "";
                            if (accessoryList!=null) {
                                Iterator<Object> iterator3 = accessoryList.iterator();
                                while (iterator3.hasNext()) { //整改图片列表
                                    String imgUrl = (String) iterator3.next();
                                    //保存图片到本地
                                    try {
                                        FlFile flFile = saveImg(imgUrl, "整改图片",DateUtil.convertTimestampToDateString(replayTime.toString(),"yyyy-MM-dd HH:mm:ss"));
                                        replayImgs += flFile.getId();
                                        if (iterator3.hasNext()) {
                                            replayImgs += ",";
                                        }
                                    } catch (IOException e) {
                                        log.error("巡检整改图片保存失败：" + new Date());
                                    }
                                }
                                if (StringUtils.hasText(replayImgs)) {
                                    ruQzInspectionInfoReplay.setRuQzInspectionReplayImgs(replayImgs);
                                }
                            }
                            inspectionInfoReplayMapper.insert(ruQzInspectionInfoReplay);

                            if ("1".equals(replayType)&& "2".equals(recheckStatus)){ //整改回复完成

                                ruQzInspectionInfo.setRuQzInspectionReplayContent(replayContent);//整改回复内容
                                if(StringUtils.hasText(replayImgs)) {
                                    ruQzInspectionInfo.setRuQzInspectionReplayImgs(replayImgs);//整改图片
                                }

                                inspectionInfoMapper.updateById(ruQzInspectionInfo);

                            }

                        }
                    }

                }else{

                    for (RuQzInspectionInfo info: ruQzInspectionInfos) {

                        //查询详情
                        JSONObject inspectionDetail = getInspectionDetail(recordId, token);

                        JSONArray patrolList = inspectionDetail.getJSONArray("patrolList");//巡检内容

                        if (patrolList == null){
                            break;
                        }
                        Iterator<Object> iterator1 = patrolList.iterator();

                        while(iterator1.hasNext()) {
                            Object patro = iterator1.next();
                            JSONObject entries1 = JSONUtil.parseObj(patro);//巡检内容
                            String projectLocation = (String) entries1.get("projectLocation");//项目地址
                            String dangerLevel = (String) entries1.get("dangerLevel");//隐患等级
                            if (StringUtils.hasLength(dangerLevel)){ //隐患等级
                                LambdaQueryWrapper<RuQzInspectionDangerLevel> levelLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                levelLambdaQueryWrapper.eq(RuQzInspectionDangerLevel::getName,dangerLevel);
                                levelLambdaQueryWrapper.eq(RuQzInspectionDangerLevel::getRuQzInspectionType,1);
                                RuQzInspectionDangerLevel ruQzInspectionDangerLevel = inspectionDangerLevelMapper.selectOne(levelLambdaQueryWrapper);

                                if(ruQzInspectionDangerLevel==null){
                                    info.setRuQzInspectionDangerLevelId("1888887369215811584");
                                }else{
                                    info.setRuQzInspectionDangerLevelId(ruQzInspectionDangerLevel.getId());
                                }
                            }else{
                                info.setRuQzInspectionDangerLevelId("1888887369215811584");
                            }
                        String checkItems = (String)entries1.get("checkItems");//巡检项
                        String checkResult = (String)entries1.get("checkResult");//巡检内容
                            String outcomeMap = (String) entries1.get("outcomeMap");//可能后果
                            String dangerMap = (String) entries1.get("dangerMap");//原因分析
                            String opinionMap = (String) entries1.get("opinionMap");//处理意见

                            if (info.getRuQzInspectionProjectLocation().equals(projectLocation)&& info.getRuQzInspectionDangerLevel().equals(dangerLevel)
                            &&info.getRuQzInspectionCheckItems().equals(checkItems) && info.getRuQzInspectionCheckResult().equals(checkResult)) {

                                info.setRuQzInspectionProjectLocation(projectLocation);//工程部位
                                info.setRuQzInspectionDangerLevel(dangerLevel);//隐患等级
                                info.setRuQzInspectionOutcomeMap(outcomeMap);//可能后果
                                info.setRuQzInspectionDangerMap(dangerMap);//原因分析
                                info.setRuQzInspectionOpinionMap(opinionMap);//处理意见
//                                info.setRuQzInspectionRecordId(recordId); //记录id
//                                info.setRemark(remarks);//巡检描述
                                info.setRuQzInspectionReorganizer(noticePerson);//通知人

                                info.setRuQzInspectionCheckResult(checkResult);//巡检结果
                                if(StringUtils.hasLength(checkResult)) {
                                    LambdaQueryWrapper<RuQzInspectionResult> resultLambdaQueryWrapper = new LambdaQueryWrapper<>();
                                    resultLambdaQueryWrapper.eq(RuQzInspectionResult::getName,checkResult);
                                    RuQzInspectionResult ruQzInspectionResult = inspectionResultMapper.selectOne(resultLambdaQueryWrapper);
                                    if (ruQzInspectionResult!=null){

                                        info.setRuQzInspectionResultId(ruQzInspectionResult.getId());
                                    }else{
                                        info.setRuQzInspectionResultId("3");//书面整改
                                    }
                                }else {
                                    info.setRuQzInspectionResultId("3");//书面整改
                                }
//                                info.setRuQzInspectionChecker(inspectPerson);//巡检人
                                info.setRuQzInspectionStatus(status);//巡检状态
                                info.setRuQzInspectionRectTime(rectTime);//整改时间
                                info.setRuQzInspectionCheckTime(initiationTime);//巡检时间

                                if (StringUtils.hasText(property)) {//巡检性质
                                    LambdaQueryWrapper<RuQzInspectionAtt> attWrapper = new LambdaQueryWrapper<>();
                                    attWrapper.eq(RuQzInspectionAtt::getName,property);
                                    attWrapper.eq(RuQzInspectionAtt::getRuQzInspectionType,1);
                                    RuQzInspectionAtt selectOne = inspectionAttMapper.selectOne(attWrapper);
                                    info.setRuQzInspectionAttId(selectOne.getId());//巡检性质
                                }else{
                                    info.setRuQzInspectionAttId("1888069481003880448");//巡检性质
                                }
//                                inspectionInfoMapper.update(info);

                                //整改记录
                                JSONArray replayList = entries1.getJSONArray("replayList");
                                if (replayList==null) {
                                    break;
                                }
                                Iterator<Object> iterator2 = replayList.iterator();

                                //保存记录
                                while (iterator2.hasNext()) {
                                    Object replayStr = iterator2.next();
                                    JSONObject replay = JSONUtil.parseObj(replayStr);

                                    String replayRecordId = (String) replay.get("recordId");//记录id
                                    Long createTime = (Long) replay.get("createTime");//创建时间
                                    String replayType = (String) replay.get("replayType");//类型：0通知回复, 1整改回复(记录)，2回检回复(记录)，3审批(流程)，4整改(流程)，5复检(流程)
                                    String recheckStatus = (String) replay.get("recheckStatus");//状态：0未通过，1整改回复-已确认，2整改回复-已处理，3复检-通过，4复检-退回，5审批-通过，6审批-退回
                                    String emergencyResponseMeasures = (String) replay.get("emergencyResponseMeasures");//应急处置措施
                                    String laborOrgName = (String) replay.get("laborOrgName");//人员组织
                                    String actualRectificationCompletionTime = (String) replay.get("actualRectificationCompletionTime");//实际整改完成时间
                                    String laborName = (String) replay.get("laborName");//处理人姓名
                                    String replayContent = (String) replay.get("replayContent");//填写内容
                                    Long replayTime = (Long) replay.get("replayTime");//回复时间（可能是前端页面选择时间）

                                    //查询整改流程记录是否存在
                                    LambdaQueryWrapper<RuQzInspectionInfoReplay> queryReplayWrapper = new LambdaQueryWrapper();
                                    queryReplayWrapper.eq(RuQzInspectionInfoReplay::getRuQzInspectionRepRecordId,replayRecordId);
                                    queryReplayWrapper.eq(RuQzInspectionInfoReplay::getRuQzInspectionReplayType,replayType);
                                    RuQzInspectionInfoReplay ruQzInspectionInfoReplay1 = inspectionInfoReplayMapper.selectOne(queryReplayWrapper);

                                    if (ruQzInspectionInfoReplay1==null) {//记录不存在
                                        RuQzInspectionInfoReplay ruQzInspectionInfoReplay = normalNewQzInspectionInfoReplay();
                                        ruQzInspectionInfoReplay.setRuQzInspectionInfoId(info.getId());

                                        ruQzInspectionInfoReplay.setRuQzInspectionRepRecordId(replayRecordId);
                                        ruQzInspectionInfoReplay.setRuQzInspectionRepCreateTime(DateUtil.convertTimestampToDateString(createTime.toString(), "yyyy-MM-dd HH:mm:ss"));
                                        ruQzInspectionInfoReplay.setRuQzInspectionReplayType(replayType);
                                        ruQzInspectionInfoReplay.setRuQzInspectionRecheckStatus(recheckStatus);
                                        ruQzInspectionInfoReplay.setRuQzInspectionEmrRespMea(emergencyResponseMeasures);
                                        ruQzInspectionInfoReplay.setRuQzInspectionRepLaborOrgName(laborOrgName);
                                        ruQzInspectionInfoReplay.setRuQzInspectionActRectComTime(actualRectificationCompletionTime);
                                        ruQzInspectionInfoReplay.setRuQzInspectionRepLaborName(laborName);
                                        ruQzInspectionInfoReplay.setRuQzInspectionReplayContent(replayContent);
                                        ruQzInspectionInfoReplay.setRuQzInspectionRepReplayTime(DateUtil.convertTimestampToDateString(replayTime.toString(),"yyyy-MM-dd HH:mm:ss"));

                                        JSONArray accessoryList = replay.getJSONArray("accessoryList");//整改图片
                                        String replayImgs = "";
                                        if (accessoryList != null) {
                                            Iterator<Object> iterator3 = accessoryList.iterator();
                                            while (iterator3.hasNext()) { //整改图片列表
                                                String imgUrl = (String) iterator3.next();
                                                //保存图片到本地
                                                try {
                                                    FlFile flFile = saveImg(imgUrl, "整改图片",DateUtil.convertTimestampToDateString(replayTime.toString(),"yyyy-MM-dd HH:mm:ss"));
                                                    replayImgs += flFile.getId();
                                                    if (iterator3.hasNext()) {
                                                        replayImgs += ",";
                                                    }
                                                } catch (IOException e) {
                                                    log.error("巡检整改图片保存失败：" + new Date());
                                                }
                                            }
                                            if (StringUtils.hasText(replayImgs)) {
                                                ruQzInspectionInfoReplay.setRuQzInspectionReplayImgs(replayImgs);
                                            }
                                        }
                                        inspectionInfoReplayMapper.insert(ruQzInspectionInfoReplay);

                                        //判断整改图片是否设置
                                        if ("1".equals(replayType) && "2".equals(recheckStatus)) { //整改回复完成
                                            info.setRuQzInspectionReplayContent(replayContent);//整改回复内容
                                            if (StringUtils.hasText(replayImgs)) {
                                                info.setRuQzInspectionReplayImgs(replayImgs);//整改图片
                                            }
                                        }
                                    }else{

                                        ruQzInspectionInfoReplay1.setRuQzInspectionRepRecordId(replayRecordId);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionRepCreateTime(DateUtil.convertTimestampToDateString(createTime.toString(), "yyyy-MM-dd HH:mm:ss"));
                                        ruQzInspectionInfoReplay1.setRuQzInspectionReplayType(replayType);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionRecheckStatus(recheckStatus);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionEmrRespMea(emergencyResponseMeasures);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionRepLaborOrgName(laborOrgName);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionActRectComTime(actualRectificationCompletionTime);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionRepLaborName(laborName);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionReplayContent(replayContent);
                                        ruQzInspectionInfoReplay1.setRuQzInspectionRepReplayTime(DateUtil.convertTimestampToDateString(replayTime.toString(), "yyyy-MM-dd HH:mm:ss"));

                                        JSONArray accessoryList = replay.getJSONArray("accessoryList");//整改图片
                                        String replayImgs = "";
                                        if (accessoryList!=null) {
                                            Iterator<Object> iterator3 = accessoryList.iterator();

                                            if (recheckStatus != null && !recheckStatus.equals(ruQzInspectionInfoReplay1.getRuQzInspectionRecheckStatus())) {//如果流程状态发生改变
                                                while (iterator3.hasNext()) { //整改图片列表
                                                    String imgUrl = (String) iterator3.next();
                                                    //保存图片到本地
                                                    try {
                                                        FlFile flFile = saveImg(imgUrl, "整改图片",DateUtil.convertTimestampToDateString(replayTime.toString(), "yyyy-MM-dd HH:mm:ss"));
                                                        replayImgs += flFile.getId();
                                                        if (iterator3.hasNext()) {
                                                            replayImgs += ",";
                                                        }
                                                    } catch (IOException e) {
                                                        log.error("巡检整改图片保存失败：" + new Date());
                                                    }
                                                }
                                                if (StringUtils.hasText(replayImgs)) {
                                                    ruQzInspectionInfoReplay1.setRuQzInspectionReplayImgs(replayImgs);
                                                }

                                            }
                                        }
                                        inspectionInfoReplayMapper.updateById(ruQzInspectionInfoReplay1);

                                        //判断整改图片是否设置,为整改，整改完成
                                        if (recheckStatus!=null && !recheckStatus.equals(ruQzInspectionInfoReplay1.getRuQzInspectionRecheckStatus())
                                                && ("1".equals(replayType) && "2".equals(recheckStatus))) { //整改回复完成
                                            info.setRuQzInspectionReplayContent(replayContent);//整改回复内容
                                            if (StringUtils.hasText(replayImgs)) {
                                                info.setRuQzInspectionReplayImgs(replayImgs);//整改图片
                                            }
                                        }
                                    }

                                    inspectionInfoMapper.updateById(info);
                                }
                            }

                        }

                    }

                }

            }

        }else{
            throw new BaseException("获取轻筑安全巡检性质错误："+response);
        }
    }

    /**
     * 在线图片保存到本地
     * @param imgUrl 图片互联网地址
     * @return
     * @throws IOException
     */
    private FlFile saveImg(String imgUrl,String orgName,String createDate) throws IOException {
        String pictureUrl = imgUrl;
        //建立图片连接
        URL url = new URL(pictureUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        //设置请求方式
        connection.setRequestMethod("GET");
        //设置超时时间
        connection.setConnectTimeout(10*1000);

        //输入流
        InputStream stream = connection.getInputStream();
        int len = 0;
        byte[] test = new byte[1024];


        //获取项目路径
        File directory = new File(getBaseFilePath()+"/qzInspectionImg");
//        File directory = new File("/Users/hejialun/Documents/qzInspectionImg");
        String path = directory.getCanonicalPath();
        //如果没有文件夹则创建
        File file = new File(path);
        if (!file.exists()){
            file.mkdirs();
        }

        //设置图片名称，这个随意，我是用的当前时间命名
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyymmddhhmmss");
        String date = dateFormat.format(new Date());
        String fileName = date + ".png";

        String filePath = path +"/" + fileName;
        //输出流，图片输出的目的文件
        BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(filePath));

        //以流的方式上传
        while ((len =stream.read(test)) !=-1){
            fos.write(test,0,len);
        }

        //记得关闭流，不然消耗资源
        stream.close();
        fos.close();


        long fileSize = Files.size(Paths.get(filePath));//字节
        double size = Math.round(fileSize / 1024.0);//保留两位小鼠

        FlFile flFile = normalNewFlFile(createDate);
        flFile.setDspName(orgName+".png");
        flFile.setDspSize(size+"KB");
        flFile.setSizeKb(size);
        flFile.setPhysicalLocation(filePath);
        flFile.setOriginFilePhysicalLocation(filePath);
        flFile.setName(orgName);
        flFile.setFileInlineUrl("/qygly-gateway/qygly-file/viewImage?fileId="+ flFile.getId()+"");
        flFile.setFileAttachmentUrl("/qygly-gateway/qygly-file/downloadCommonFile?fileId="+flFile.getId()+"");

        flFileMapper.insert(flFile);

        return flFile;
    }


    public   JSONObject getInspectionDetail(String infoId,String token){

        JSONObject data  = null;
        String  requestUrl =  "https://open.qingzhuyun.com/api/inspect/info/findCriticalById?recordId="+infoId;

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含

        HttpHeaders translateHeaders = new HttpHeaders();

        // 设置请求体
        HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);
        translateHeaders.set("auth-token", token);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, translateEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            JSONObject entries = JSONUtil.parseObj(responseEntity.getBody());
             data = entries.getJSONObject("data");
        }else{
            throw new BaseException("详情获取失败："+infoId+","+responseEntity);
        }

        return data;
    }

    /**
     * 巡检记录通用赋值
     * @return 实体
     */
    private RuQzInspectionInfo normalNewQzInspectionInfo() {
        String now = DateUtil.getNormalTimeStr(new Date());

        RuQzInspectionInfo inspectionInfo = new RuQzInspectionInfo();
        inspectionInfo.setId(IdUtil.getSnowflakeNextIdStr());
        inspectionInfo.setVer("1");
        inspectionInfo.setTs(now);
        inspectionInfo.setCreateBy("0099250247095871681");
        inspectionInfo.setLastUpdateBy("0099250247095871681");
        inspectionInfo.setCreateDate(now);
        inspectionInfo.setLastUpdateDate(now);
        inspectionInfo.setStatus("AP");
        return inspectionInfo;
    }

    /**
     * 巡检流程赋值
     * @return 实体
     */
    private RuQzInspectionInfoReplay normalNewQzInspectionInfoReplay() {
        String now = DateUtil.getNormalTimeStr(new Date());

        RuQzInspectionInfoReplay inspectionInfoReplay = new RuQzInspectionInfoReplay();
        inspectionInfoReplay.setId(IdUtil.getSnowflakeNextIdStr());
        inspectionInfoReplay.setVer("1");
        inspectionInfoReplay.setTs(now);
        inspectionInfoReplay.setCreateBy("0099250247095871681");
        inspectionInfoReplay.setLastUpdateBy("0099250247095871681");
        inspectionInfoReplay.setCreateDate(now);
        inspectionInfoReplay.setLastUpdateDate(now);
        inspectionInfoReplay.setStatus("AP");
        return inspectionInfoReplay;
    }

    /**
     * 图片文件实体赋值
     * @return 实体
     */
    private FlFile normalNewFlFile(String createDate) {
        String now = DateUtil.getNormalTimeStr(new Date());

        FlFile flFile = new FlFile();
        flFile.setId(IdUtil.getSnowflakeNextIdStr());
        flFile.setVer("1");
        flFile.setTs(now);
        flFile.setCreateBy("0099250247095871681");
        flFile.setLastUpdateBy("0099250247095871681");
        flFile.setCreateDate(createDate);
        flFile.setLastUpdateDate(now);
        flFile.setStatus("AP");
        flFile.setFlPathId("0099250247095872688");
        flFile.setUploadDttm(now);
        flFile.setIsPublicRead(1);

        return flFile;
    }


    /**
     * 获取上传文件文件夹
     */
    private  String getBaseFilePath(){
        LambdaQueryWrapper<AdAtt>  adWrapper = new LambdaQueryWrapper<>();
        adWrapper.eq(AdAtt::getCode,"CC_ATTACHMENT");
        AdAtt adAtt = adAttMapper.selectOne(adWrapper);

        LambdaQueryWrapper<FlPath>  fileWrapper = new LambdaQueryWrapper<>();
        fileWrapper.eq(FlPath::getId,adAtt.getFilePathId());
        FlPath flPath = flPathMapper.selectOne(fileWrapper);
        return flPath.getFileAttachmentUrl();
    }


}
