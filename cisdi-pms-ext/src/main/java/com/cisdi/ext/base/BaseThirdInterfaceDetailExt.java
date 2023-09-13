package com.cisdi.ext.base;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.commons.HttpClient;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.Date;

public class BaseThirdInterfaceDetailExt {

    /**
     * 第三方接口调用记录信息
     * @param userId 调用人
     * @param str 参数信息
     * @param method 接口请求方式 GET/POST
     * @param urlCode 接口编码
     * @param myJdbcTemplate 数据源
     */
    public static void insert(String userId, String str, String method, String urlCode,MyJdbcTemplate myJdbcTemplate) {
        String id = IdUtil.getSnowflakeNextIdStr();
        String now = DateTimeUtil.dttmToString(new Date());
        String url = HttpClient.getUrl(urlCode,myJdbcTemplate);
        String sql = "insert into base_third_interface_detail (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,URL,TEXT_REMARK_ONE,REMARK_ONE,SYS_TRUE_ONE) values (?,'1',?,?,?,?,?,'AP',?,?,?,'0')";
        myJdbcTemplate.update(sql,id,now,now,userId,now,userId,url,str,method);
        if ("GET".equals(method)){
            HttpClient.doGet(url,str);
        } else if ("POST".equals(method)){
            HttpClient.doPost(url,str,"UTF-8");
        }
    }
}
