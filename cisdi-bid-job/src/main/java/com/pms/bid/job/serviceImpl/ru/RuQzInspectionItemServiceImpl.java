package com.pms.bid.job.serviceImpl.ru;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pms.bid.job.component.ru.RuQzPlatformUtil;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionAtt;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionItem;
import com.pms.bid.job.mapper.ru.RuQzInspectionAttMapper;
import com.pms.bid.job.mapper.ru.RuQzInspectionItemMapper;
import com.pms.bid.job.service.ru.RuQzInspectionAttService;
import com.pms.bid.job.service.ru.RuQzInspectionItemService;
import com.pms.bid.job.util.DateUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

@Slf4j
@Service
public class RuQzInspectionItemServiceImpl implements RuQzInspectionItemService {

    @Resource
    private RuQzInspectionItemMapper inspectionItemMapper;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private RuQzPlatformUtil ruQzPlatformUtil;

    @Override
    public void syncQzInspectionItem() {
        String  token = ruQzPlatformUtil.getQzToken();

        if (!StringUtils.hasLength(token)){
            throw new BaseException("token为空");
        }

        syncSafeInspectionItem(token); //安全
        syncQualityInspectionItem(token); //质量
    }

    private void syncSafeInspectionItem(String token){
        String  requestUrl =  "https://open.qingzhuyun.com/api/inspect/getItemsNameInspect?projectId=d16f0c99b8b54feb96b3b7422c3755a0&inspectType=2";

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含
//        requestMap.put("projectId", "d16f0c99b8b54feb96b3b7422c3755a0");//轻筑项目ID
//        requestMap.put("inspectType",2);//巡检类型1质量，2安全

        HttpHeaders translateHeaders = new HttpHeaders();
        translateHeaders.set("auth-token", token);
//        translateHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 设置请求体
        HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);

        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, translateEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject body = JSONUtil.parseObj(response.getBody());
            System.out.println(body);
            JSONObject data = body.getJSONObject("data");
            System.out.println(data);
            JSONArray nature = data.getJSONArray("inspection");
            Iterator<Object> iterator = nature.iterator();
            while(iterator.hasNext()){
                String next = (String) iterator.next();

                LambdaQueryWrapper<RuQzInspectionItem> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(RuQzInspectionItem::getName,next);
                wrapper.eq(RuQzInspectionItem::getRuQzInspectionType,2);
                RuQzInspectionItem selectOne = inspectionItemMapper.selectOne(wrapper);
                if (SharedUtil.isEmpty(selectOne)){
                    RuQzInspectionItem ruQzInspectionItem = normalNewQzInspectionItem();
                    ruQzInspectionItem.setName(next);
                    ruQzInspectionItem.setRuQzInspectionType(2);
                    inspectionItemMapper.insert(ruQzInspectionItem);
                }

            }

        }else{
            throw new BaseException("获取庆轻筑巡检项错误："+response);
        }
    }

    private void syncQualityInspectionItem(String token){
        String  requestUrl =  "https://open.qingzhuyun.com/api/inspect/getItemsNameInspect?projectId=d16f0c99b8b54feb96b3b7422c3755a0&inspectType=1";

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含
//        requestMap.put("projectId", "d16f0c99b8b54feb96b3b7422c3755a0");//轻筑项目ID
//        requestMap.put("inspectType",1);//巡检类型1质量，2安全

        HttpHeaders translateHeaders = new HttpHeaders();
        translateHeaders.set("auth-token", token);
//        translateHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 设置请求体
        HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);

        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.GET, translateEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject body = JSONUtil.parseObj(response.getBody());
            System.out.println(body);
            JSONObject data = body.getJSONObject("data");
            if (data==null){
                return;
            }
            JSONArray nature = data.getJSONArray("inspection");
            Iterator<Object> iterator = nature.iterator();
            while(iterator.hasNext()){
                String next = (String) iterator.next();

                LambdaQueryWrapper<RuQzInspectionItem> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(RuQzInspectionItem::getName,next);
                wrapper.eq(RuQzInspectionItem::getRuQzInspectionType,1);
                RuQzInspectionItem selectOne = inspectionItemMapper.selectOne(wrapper);
                if (SharedUtil.isEmpty(selectOne)){
                    RuQzInspectionItem ruQzInspectionItem = normalNewQzInspectionItem();
                    ruQzInspectionItem.setName(next);
                    ruQzInspectionItem.setRuQzInspectionType(1);
                    inspectionItemMapper.insert(ruQzInspectionItem);
                }

            }

        }else{
            throw new BaseException("获取轻筑巡检项错误："+response);
        }
    }


    /**
     * 实体通用赋值
     * @return 实体
     */
    private RuQzInspectionItem normalNewQzInspectionItem() {
        String now = DateUtil.getNormalTimeStr(new Date());

        RuQzInspectionItem inspectionItem = new RuQzInspectionItem();
        inspectionItem.setId(IdUtil.getSnowflakeNextIdStr());
        inspectionItem.setVer("1");
        inspectionItem.setTs(now);
        inspectionItem.setCreateBy("0099250247095871681");
        inspectionItem.setLastUpdateBy("0099250247095871681");
        inspectionItem.setCreateDate(now);
        inspectionItem.setLastUpdateDate(now);
        inspectionItem.setStatus("AP");
        return inspectionItem;
    }
}
