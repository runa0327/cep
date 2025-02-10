package com.pms.bid.job.serviceImpl.ru;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pms.bid.job.component.ru.RuQzPlatformUtil;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionAtt;
import com.pms.bid.job.mapper.ru.RuQzInspectionAttMapper;
import com.pms.bid.job.service.ru.RuQzInspectionAttService;
import com.pms.bid.job.util.DateUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;

@Slf4j
@Service
public class RuQzInspectionAttServiceImpl implements RuQzInspectionAttService  {

    @Resource
    private RuQzInspectionAttMapper  inspectionAttMapper;

    @Autowired
    private  RestTemplate restTemplate;

    @Autowired
    private RuQzPlatformUtil RuQzPlatformUtil;

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Override
    public void syncQzInspectionAtt() {

        String  token = RuQzPlatformUtil.getQzToken();

        if (!StringUtils.hasLength(token)){
            throw new BaseException("token为空");
        }

        syncSafeInspectionAtt(token);

        syncQualityInspectionAtt(token);

    }

    private void syncSafeInspectionAtt(String token){
        String  requestUrl =  "https://open.qingzhuyun.com/api/inspect/getNatureInspect";

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含
        requestMap.put("projectId", "d16f0c99b8b54feb96b3b7422c3755a0");//轻筑项目ID
        requestMap.put("inspectType",1);//巡检类型1质量，2安全

        HttpHeaders translateHeaders = new HttpHeaders();
        translateHeaders.set("auth-token", token);
        translateHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 设置请求体
        HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);

        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, translateEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject body = JSONUtil.parseObj(response.getBody());
            JSONObject data = body.getJSONObject("data");
            JSONArray nature = data.getJSONArray("nature");
            Iterator<Object> iterator = nature.iterator();
//            List<RuQzInspectionAtt> inspectionAttList = new ArrayList<>();
            while(iterator.hasNext()){
                String next = (String) iterator.next();

                LambdaQueryWrapper<RuQzInspectionAtt> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(RuQzInspectionAtt::getName,next);
                wrapper.eq(RuQzInspectionAtt::getRuQzInspectionType,1);
                RuQzInspectionAtt selectOne = inspectionAttMapper.selectOne(wrapper);
                if (SharedUtil.isEmpty(selectOne)){
                    RuQzInspectionAtt ruQzInspectionAtt = normalNewQzInspectionAtt();
                    ruQzInspectionAtt.setName(next);
                    ruQzInspectionAtt.setRuQzInspectionType(1);
//                    inspectionAttList.add(ruQzInspectionAtt);
                    inspectionAttMapper.insert(ruQzInspectionAtt);
                }

            }

        }else{
            throw new BaseException("获取轻筑安全巡检性质错误："+response);
        }
    }

    private void syncQualityInspectionAtt(String token){
        String  requestUrl =  "https://open.qingzhuyun.com/api/inspect/getNatureInspect";

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含
        requestMap.put("projectId", "d16f0c99b8b54feb96b3b7422c3755a0");//轻筑项目ID
        requestMap.put("inspectType",2);//巡检类型1质量，2安全

        HttpHeaders translateHeaders = new HttpHeaders();
        translateHeaders.set("auth-token", token);
        translateHeaders.setContentType(MediaType.APPLICATION_JSON);

        // 设置请求体
        HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);

        ResponseEntity<String> response = restTemplate.exchange(requestUrl, HttpMethod.POST, translateEntity, String.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            JSONObject body = JSONUtil.parseObj(response.getBody());
            JSONObject data = body.getJSONObject("data");
            JSONArray nature = data.getJSONArray("nature");
            Iterator<Object> iterator = nature.iterator();
//            List<RuQzInspectionAtt> inspectionAttList = new ArrayList<>();
            while(iterator.hasNext()){
                String next = (String) iterator.next();

                LambdaQueryWrapper<RuQzInspectionAtt> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(RuQzInspectionAtt::getName,next);
                wrapper.eq(RuQzInspectionAtt::getRuQzInspectionType,2);
                RuQzInspectionAtt selectOne = inspectionAttMapper.selectOne(wrapper);
                if (SharedUtil.isEmpty(selectOne)){
                    RuQzInspectionAtt ruQzInspectionAtt = normalNewQzInspectionAtt();
                    ruQzInspectionAtt.setName(next);
                    ruQzInspectionAtt.setRuQzInspectionType(2);
//                    inspectionAttList.add(ruQzInspectionAtt);
                    inspectionAttMapper.insert(ruQzInspectionAtt);
                }

            }

        }else{
            throw new BaseException("获取轻筑质量巡检性质错误："+response);
        }
    }

    /**
     * 实体通用赋值
     * @return 实体
     */
    private RuQzInspectionAtt normalNewQzInspectionAtt() {
        String now = DateUtil.getNormalTimeStr(new Date());

        RuQzInspectionAtt inspectionAtt = new RuQzInspectionAtt();
        inspectionAtt.setId(IdUtil.getSnowflakeNextIdStr());
        inspectionAtt.setVer("1");
        inspectionAtt.setTs(now);
        inspectionAtt.setCreateBy("0099250247095871681");
        inspectionAtt.setLastUpdateBy("0099250247095871681");
        inspectionAtt.setCreateDate(now);
        inspectionAtt.setLastUpdateDate(now);
        inspectionAtt.setStatus("AP");
        return inspectionAtt;
    }

}
