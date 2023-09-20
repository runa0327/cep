package com.cisdi.pms.job.commons;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

public class HttpClient {

    public static String doPost(String url, String str, String encoding) {
        String body = "";
        try {
            // 创建httpclient对象
            CloseableHttpClient client = HttpClients.createDefault();
            // 创建post方式请求对象
            HttpPost httpPost = new HttpPost(url);
            // 设置参数到请求对象中
            httpPost.setEntity(new StringEntity(str, encoding));
            // 设置header信息
            // 指定报文头【Content-type】、【User-Agent】
            httpPost.setHeader("Content-type", "application/json;charset=UTF-8");
            // 执行请求操作，并拿到结果（同步阻塞）
            CloseableHttpResponse response = client.execute(httpPost);
            // 获取结果实体
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                // 按指定编码转换结果实体为String类型
                body = EntityUtils.toString(entity, encoding);
            }
            EntityUtils.consume(entity);
            // 释放链接
            response.close();
            return body;
        } catch (Exception e1) {
            e1.printStackTrace();
            return "";

        }
    }

    /**
     * http Get请
     * @param httpUrl 接口地址
     * @param queryParams 参数信息
     * @return 调用结果
     */
    public static String doGet(String httpUrl, String queryParams) {
        {
            HttpURLConnection connection = null;
            InputStream inputStream = null;
            BufferedReader bufferedReader = null;
            String result = null;// 返回结果字符串
            try {
                // 创建远程url连接对象
                URL url = new URL(new StringBuffer(httpUrl).append("?").append(queryParams).toString());
                // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
                connection = (HttpURLConnection) url.openConnection();
                // 设置连接方式：get
                connection.setRequestMethod("GET");
                // 设置连接主机服务器的超时时间：15000毫秒
                connection.setConnectTimeout(5000);
                // 设置读取远程返回的数据时间：60000毫秒
                connection.setReadTimeout(6000);
                // 发送请求
                connection.connect();
                // 通过connection连接，获取输入流
                if (connection.getResponseCode() == 200) {
                    inputStream = connection.getInputStream();
                    // 封装输入流，并指定字符集
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
                    // 存放数据
                    StringBuilder sbf = new StringBuilder();
                    String temp;
                    while ((temp = bufferedReader.readLine()) != null) {
                        sbf.append(temp);
                        sbf.append(System.getProperty("line.separator"));
                    }
                    result = sbf.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // 关闭资源
                if (null != bufferedReader) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (null != inputStream) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (connection != null) {
                    connection.disconnect();// 关闭远程连接
                }
            }
            return result;
        }
    }

}
