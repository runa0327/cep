package com.cisdi.cisdipreview.interceptor;


import com.cisdi.cisdipreview.domain.Login;
import com.cisdi.cisdipreview.utils.JsonUtil;
import com.qygly.shared.BaseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class SessionInterceptor implements HandlerInterceptor {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    public SessionInterceptor(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {


        String userSession = request.getHeader("qygly-session-id");
        boolean ok = false;
        log.info(">>>>>>>拦截到api相关请求头<<<<<<<<" + userSession);

        Login login = new Login();

        userSession = "Q$SESSION:" + userSession;
        if (redisTemplate.hasKey(userSession)) {
            Map<String, Object> value = (Map<String, Object>) redisTemplate.opsForValue().get(userSession);
            String json = JsonUtil.toJson(value);
            login = JsonUtil.fromJson(json, Login.class);
            String userId = login.getUserInfo().getId();
            if (StringUtils.hasText(userId)) {
                new RequestHeaderContext.RequestHeaderContextBuild()
                        .userSession(userSession)
                        .userCode(login.getUserInfo().getCode())
                        .userId(login.getUserInfo().getId())
                        .userName(JsonUtil.getCN(login.getUserInfo().getName()))
                        .pCcPrjIds(login.getGlobalVarMap().getPCcPrjIds())
                        .bulid();
                log.info("token有效!");
                ok = true;
            }
        }


        if (!ok) {
            throw new BaseException("登录已过期,请重新登录");
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }

}
