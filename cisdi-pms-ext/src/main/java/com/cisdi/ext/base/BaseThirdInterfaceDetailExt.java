package com.cisdi.ext.base;

import com.cisdi.ext.commons.HttpClient;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.MyJdbcTemplate;

import java.util.Date;

public class BaseThirdInterfaceDetailExt {

    /**
     * 第三方接口调用记录信息
     * @param userId 调用人
     * @param str 参数信息
     * @param interfaceId 接口详情表id
     * @param method 接口请求方式 GET/POST
     * @param urlCode 接口编码
     * @param myJdbcTemplate 数据源
     */
    public static void insert(String userId, String str, String interfaceId, String method, String urlCode,MyJdbcTemplate myJdbcTemplate) {
        String now = DateTimeUtil.dttmToString(new Date());
        String url = HttpClient.getUrl(urlCode,myJdbcTemplate);
        String sql = "insert into base_third_interface_detail (ID,VER,TS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID,STATUS,URL,TEXT_REMARK_ONE,REMARK_ONE,SYS_TRUE_ONE,FAIL_CT) values (?,'1',?,?,?,?,?,'AP',?,?,?,'0','0')";
        myJdbcTemplate.update(sql,interfaceId,now,now,userId,now,userId,url,str,method);
        if ("GET".equals(method)){
            HttpClient.doGet(url,str);
        } else if ("POST".equals(method)){
            HttpClient.doPost(url,str,"UTF-8");
        }
    }
}
