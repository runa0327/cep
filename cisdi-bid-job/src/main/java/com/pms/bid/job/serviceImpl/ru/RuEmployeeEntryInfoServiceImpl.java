package com.pms.bid.job.serviceImpl.ru;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pms.bid.job.domain.ru.AdUser;
import com.pms.bid.job.domain.ru.RuEmployeeEntryInfo;
import com.pms.bid.job.domain.ru.RuVisaExpireWarning;
import com.pms.bid.job.mapper.ru.AdUserMapper;
import com.pms.bid.job.mapper.ru.CcPrjMemberMapper;
import com.pms.bid.job.mapper.ru.RuEmployeeEntryInfoMapper;
import com.pms.bid.job.mapper.ru.RuVisaExpireWarningMapper;
import com.pms.bid.job.service.ru.RuEmployeeEntryInfoService;
import com.pms.bid.job.util.DateUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.util.JsonUtil;
import com.qygly.shared.util.SharedUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import java.util.function.Supplier;

@Slf4j
@Service
public class RuEmployeeEntryInfoServiceImpl implements RuEmployeeEntryInfoService {

    public static final String REDIS_KEY = "WxAccessToken";

    /**
     * 微信服务号“赛迪工程咨询数字化”的APP_ID。
     */
    public static final String APP_ID = ((Supplier<String>) () -> "wx96e1567c48592909").get();
    /**
     * 微信服务号“赛迪工程咨询数字化”的SECRET。
     */
    public static final String SECRET = ((Supplier<String>) () -> "bb863dc7f18175cae068ca6ffed3a3f6").get();
    /**
     * 微信服务号“赛迪工程咨询数字化”的“项目巡查结果通知”模板的TEMPLATE_ID。
     */
    public static final String TEMPLATE_ID = ((Supplier<String>) () -> "gvAGjZL9NRIjelhXQKEN3jmZSKuX87BZBuE8_y-waqc").get();


    @Resource
    private RuVisaExpireWarningMapper visaExpireWarningMapper;

    @Resource
    private AdUserMapper adUserMapper;

    @Resource
    private CcPrjMemberMapper prjMemberMapper;

    @Resource
    private RuEmployeeEntryInfoMapper employeeEntryInfoMapper;

    @Resource
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private  RestTemplate restTemplate;

    @Override
    public void checkUserVisaExpire() {

        //预警设置
        LambdaQueryWrapper<RuVisaExpireWarning> warningLambdaQueryWrapper = new LambdaQueryWrapper<>();
        warningLambdaQueryWrapper.eq(RuVisaExpireWarning::getProjectId,"1790672761571196928");
        List<RuVisaExpireWarning> ruVisaExpireWarnings = visaExpireWarningMapper.selectList(warningLambdaQueryWrapper);

        int  yellowDays  = 10; //黄色预警天数
        int  redDays = 7; //红色预警天数
        String yellowNoticeMember = "";
        String redNoticeMember = "";
        for (RuVisaExpireWarning warning: ruVisaExpireWarnings ) {
            if ("yellow".equals(warning.getLevelOfRiskId())){
                yellowDays = warning.getAdvanceWarningDays();
                yellowNoticeMember = warning.getPrjMemberId();;
            }else if("red".equals(warning.getLevelOfRiskId())){
                redDays = warning.getAdvanceWarningDays();
                redNoticeMember = warning.getPrjMemberId();
            }
        }

        if(!StringUtils.hasLength(yellowNoticeMember) || !StringUtils.hasLength(redNoticeMember)){
            log.error("预警通知未设置！");
         throw new BaseException("预警通知未设置");
        }

        //获取用户
        AdUser yellowUser = adUserMapper.selectUserByPrjMemberId(yellowNoticeMember);
        AdUser redUser = adUserMapper.selectUserByPrjMemberId(redNoticeMember);

        //
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        Date  now  = instance.getTime();
        instance.add(Calendar.DATE,redDays);
        Date redDate = instance.getTime();
        instance.add(Calendar.DATE,(yellowDays-redDays));
        Date yellowDate = instance.getTime();


        //红色预警数据
        LambdaQueryWrapper<RuEmployeeEntryInfo> employeeQuery =   new LambdaQueryWrapper<>();
        employeeQuery.isNull(RuEmployeeEntryInfo::getExitACountryDate);
        employeeQuery.ge(RuEmployeeEntryInfo::getVisaExpirationDate,now);
        employeeQuery.eq(RuEmployeeEntryInfo::getProjectId,"1790672761571196928");
        employeeQuery.le(RuEmployeeEntryInfo::getVisaExpirationDate,redDate); //过期时间到达红色预警时间
        List<RuEmployeeEntryInfo> redInfos = employeeEntryInfoMapper.selectList(employeeQuery);

        //黄色预警数据
        LambdaQueryWrapper<RuEmployeeEntryInfo> employeeQuery2 =   new LambdaQueryWrapper<>();
        employeeQuery2.isNull(RuEmployeeEntryInfo::getExitACountryDate);
        employeeQuery2.eq(RuEmployeeEntryInfo::getProjectId,"1790672761571196928");
        employeeQuery2.le(RuEmployeeEntryInfo::getVisaExpirationDate,yellowDate); //过期时间到达红色预警时间
        employeeQuery2.gt(RuEmployeeEntryInfo::getVisaExpirationDate,redDate);
        List<RuEmployeeEntryInfo> yellowInfos = employeeEntryInfoMapper.selectList(employeeQuery2);

        String noticeName = "签证到期预警";
        if (redInfos!=null && redInfos.size()>0) {
            String redWarningName = "有" + redInfos.size() + "人签证不足" + redDays + "日到期";
            String redExpireDate = redInfos.get(0).getVisaExpirationDate().toString();

            //红色预警
            sendTemplateMessage(redUser.getExtraInfo(), IdUtil.getSnowflakeNextIdStr(), noticeName, redWarningName, redExpireDate);

        }

        if(yellowInfos!=null &&yellowInfos.size()>0) {
            String yellowWarningName = "有" + yellowInfos.size() + "人签证不足" + yellowDays + "日到期";
            String yellowExpireDate = yellowInfos.get(0).getVisaExpirationDate().toString();
            //黄色预警
            sendTemplateMessage(yellowUser.getExtraInfo(), IdUtil.getSnowflakeNextIdStr(), noticeName, yellowWarningName, yellowExpireDate);

        }
    }


    /**
     * 获取AccessToke。先从reids取，若无则换取并放入redis。
     *
     * @return
     */
    private String getAccessToken() {

        String wxAccessToken = (String) redisTemplate.opsForValue().get(REDIS_KEY);
        if (SharedUtil.notEmpty(wxAccessToken)) {
            return wxAccessToken;
        } else {
//            RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
            ResponseEntity<String> response = restTemplate.getForEntity("https://api.weixin.qq.com/cgi-bin/token?appid=" + APP_ID + "&secret=" + SECRET + "&grant_type=client_credential", String.class);
            String responseBody = response.getBody();

            Map map = JsonUtil.fromJson(responseBody, Map.class);
            Object accessToken = map.get("access_token");
            if (SharedUtil.isEmpty(accessToken)) {
//                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.getAccessTokenFailed");
                String message = "获取微信access_token失败";
                throw new BaseException(message + responseBody);
            } else {
                // 微信官方表示7200秒失效，我们自行另其6000秒失效，便于更换新的：
                String accessTokenString = accessToken.toString();
                redisTemplate.opsForValue().set(REDIS_KEY, accessTokenString, 6000, TimeUnit.SECONDS);
                return accessTokenString;
            }
        }
    }

    private void sendTemplateMessage(String openId, String client_msg_id, String noticeName, String warningName, String expireDate) {

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("touser", openId);
        requestMap.put("template_id", TEMPLATE_ID);
        requestMap.put("client_msg_id", client_msg_id);


        // 模板详情：
        // 巡查项目{{thing2.DATA}}
        // 整改期限{{time10.DATA}}
        // 处置结果{{thing5.DATA}}

        HashMap<Object, Object> data = new HashMap<>();
        requestMap.put("data", data);

        HashMap<Object, Object> thing4Map = new HashMap<>();
        data.put("thing4", thing4Map);
        thing4Map.put("value", noticeName);

        HashMap<Object, Object> thing9Map = new HashMap<>();
        data.put("time9", thing9Map);
        thing9Map.put("value", warningName);

        HashMap<Object, Object> thing3Map = new HashMap<>();
        data.put("thing3", thing3Map);
        thing3Map.put("value", expireDate);

        String accessToken = getAccessToken();
//        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken, requestMap, String.class);
        String responseBody = response.getBody();
        Map map = JsonUtil.fromJson(responseBody, Map.class);
        if (map == null || !"ok".equals(map.get("errmsg"))) {
            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.sendTemplateMessageFailed");
            throw new BaseException(message + responseBody);
        }
    }

}
