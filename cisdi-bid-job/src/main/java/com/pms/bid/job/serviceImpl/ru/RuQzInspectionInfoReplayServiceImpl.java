package com.pms.bid.job.serviceImpl.ru;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionAtt;
import com.pms.bid.job.mapper.ru.RuQzInspectionInfoMapper;
import com.pms.bid.job.mapper.ru.RuQzInspectionInfoReplayMapper;
import com.pms.bid.job.service.ru.RuQzInspectionInfoReplayService;
import com.pms.bid.job.service.ru.RuQzInspectionInfoService;
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
public class RuQzInspectionInfoReplayServiceImpl implements RuQzInspectionInfoReplayService {

    @Resource
    private RuQzInspectionInfoReplayMapper inspectionInfoReplayMapper;

    @Autowired
    private  RestTemplate restTemplate;

    @Override
    public void syncQzInspectionInfoReplay(String infoId) {

        String  token = getQzToken();

        if (!StringUtils.hasLength(token)){
            throw new BaseException("token为空");
        }

        syncSafeInspectionAtt(token,infoId);

    }

    private void syncSafeInspectionAtt(String token,String infoId){

    }


    private  String getQzToken(){

        String token = null;
        String  requestUrl =  "https://open.qingzhuyun.com/api/platform/token?appId=a0658b6f47f443729e28eafcbfccfad6&secret=958796e25fea43b2aa0574562ec0a3c1";

        Map<String, Object> requestMap = new HashMap<>();//请求参数，只包含
        requestMap.put("appId", "a0658b6f47f443729e28eafcbfccfad6");//轻筑项目ID
        requestMap.put("secret","958796e25fea43b2aa0574562ec0a3c1");//巡检类型1质量，2安全

        HttpHeaders translateHeaders = new HttpHeaders();


        // 设置请求体
        HttpEntity<Map> translateEntity = new HttpEntity<>(requestMap, translateHeaders);

        ResponseEntity<String> responseEntity = restTemplate.exchange(requestUrl, HttpMethod.GET, translateEntity, String.class);

        if (responseEntity.getStatusCode() == HttpStatus.OK) {

            JSONObject entries = JSONUtil.parseObj(responseEntity.getBody());

            JSONObject data = entries.getJSONObject("data");
             token  = data.getStr("authToken");

        }else{
            throw new BaseException("token获取失败"+responseEntity);
        }

        return token;
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
