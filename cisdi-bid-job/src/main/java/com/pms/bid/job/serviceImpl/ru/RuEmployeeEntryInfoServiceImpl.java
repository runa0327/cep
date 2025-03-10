package com.pms.bid.job.serviceImpl.ru;

import cn.hutool.core.util.IdUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.pms.bid.job.domain.ru.AdUser;
import com.pms.bid.job.domain.ru.RuEmployeeEntryInfo;
import com.pms.bid.job.domain.ru.RuVisaExpireWarning;
import com.pms.bid.job.domain.zhanJiang.YjwInstallProgress;
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
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
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
        String yellowNoticeMembers = "";
        String redNoticeMembers = "";
        for (RuVisaExpireWarning warning: ruVisaExpireWarnings ) {
            if ("yellow".equals(warning.getLevelOfRiskId())){
                yellowDays = warning.getAdvanceWarningDays();
                yellowNoticeMembers = warning.getPrjMemberIds();;
            }else if("red".equals(warning.getLevelOfRiskId())){
                redDays = warning.getAdvanceWarningDays();
                redNoticeMembers = warning.getPrjMemberIds();
            }
        }

        if(!StringUtils.hasLength(yellowNoticeMembers) || !StringUtils.hasLength(redNoticeMembers)){
            log.error("预警通知未设置！");
         throw new BaseException("预警通知未设置");
        }


        //获取用户
        String[] yMemberIds = yellowNoticeMembers.split(",");
        List<String>  yellowNoticeMemberIds = new ArrayList<>();
        for (int i = 0; i < yMemberIds.length; i++) {
            yellowNoticeMemberIds.add(yMemberIds[i]);
        }
//        AdUser yellowUser = adUserMapper.selectUserByPrjMemberId(yellowNoticeMember);
        List<AdUser> yellowUsers = adUserMapper.selectUserByPrjMemberIds(yellowNoticeMemberIds);

        String[] redMemberIds = redNoticeMembers.split(",");
        List<String>  redNoticeMemberIds = new ArrayList<>();
        for (int i = 0; i < redMemberIds.length; i++) {
            redNoticeMemberIds.add(redMemberIds[i]);
        }
//        AdUser redUser = adUserMapper.selectUserByPrjMemberId(redNoticeMember);
        List<AdUser> redUsers = adUserMapper.selectUserByPrjMemberIds(redNoticeMemberIds);
        //
        Calendar instance = Calendar.getInstance();
        instance.set(Calendar.HOUR,0);
        instance.set(Calendar.MINUTE,0);
        instance.set(Calendar.SECOND,0);
        instance.set(Calendar.MILLISECOND,0);
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

        String  pageUrl = "https://ceecip.com/vue/#/main-view?type=ACCESS&orgId=zj&hideTitleBar=true&hideMenu=true&viewId=1895363459146502144&viewComponent=DEFAULT_VIEW&title=%E4%BA%BA%E5%91%98%E7%AE%A1%E7%90%86";
//        String noticeName = "签证到期预警";
        Calendar calendar1 = Calendar.getInstance();

        for(int i = 1 ; i<= yellowDays ; i++){
            String dateStr = DateUtil.getDateStr(calendar1.getTime(), "yyyy-MM-dd");
            ArrayList<RuEmployeeEntryInfo> ruEmployeeEntryInfos = new ArrayList<>();

            if(i<=redDays) {//红色预警
                for (RuEmployeeEntryInfo info: redInfos ) {
                    if (dateStr.equals(DateUtil.getDateStr(info.getVisaExpirationDate(), "yyyy-MM-dd"))){
                        ruEmployeeEntryInfos.add(info);
                    }
                }
                notice(pageUrl,ruEmployeeEntryInfos, redDays, redUsers, "签证到期红色预警");
            }

            if(i>redDays){//黄色预警
                for (RuEmployeeEntryInfo info: yellowInfos ) {
                    if (dateStr.equals(DateUtil.getDateStr(info.getVisaExpirationDate(), "yyyy-MM-dd"))){
                        ruEmployeeEntryInfos.add(info);
                    }
                }
                notice(pageUrl,ruEmployeeEntryInfos, yellowDays, yellowUsers, "签证到期黄色预警");
            }

            calendar1.add(Calendar.DATE,1);
        }

    }

    /**
     * 预警通知
     */
    private void  notice(String pageUrl,List<RuEmployeeEntryInfo> infos,Integer warningDays,List<AdUser> users,String noticeName){

        if(infos!=null &&infos.size()>0) {
            String warningName = "有" + infos.size() + "人签证不足" + warningDays + "日到期";
            Date expireDate = infos.get(0).getVisaExpirationDate();
            //黄色预警

            for (AdUser user:  users) {
                sendTemplateMessage(pageUrl, user.getExtraInfo(), IdUtil.getSnowflakeNextIdStr(), noticeName, warningName, expireDate);
            }
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

    private void sendTemplateMessage(String pageUrl,String openId, String client_msg_id, String noticeName, String warningName, Date expireDate) {
        if(!StringUtils.hasLength(openId)){
//            throw new BaseException("通知人openid不能为空");
            log.error("通知人openid不能为空");
            return;
        }

        Map<String, Object> requestMap = new HashMap<>();

        requestMap.put("touser", openId);
        requestMap.put("template_id", TEMPLATE_ID);
        requestMap.put("client_msg_id", client_msg_id);
        requestMap.put("url",pageUrl);


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
        data.put("thing9", thing9Map);
        thing9Map.put("value", warningName);

        HashMap<Object, Object> thing3Map = new HashMap<>();
        data.put("time3", thing3Map);
        thing3Map.put("value", DateUtil.getDateStr(expireDate));


        String accessToken = getAccessToken();
//        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken, requestMap, String.class);
        String responseBody = response.getBody();
        Map map = JsonUtil.fromJson(responseBody, Map.class);
        if (map == null || !"ok".equals(map.get("errmsg"))) {
//            String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.sendTemplateMessageFailed");

            throw new BaseException("微信消息发送失败：" + responseBody);
        }
    }

}
