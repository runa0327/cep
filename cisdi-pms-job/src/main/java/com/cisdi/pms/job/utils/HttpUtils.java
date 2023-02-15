package com.cisdi.pms.job.utils;

import com.cisdi.pms.job.config.UploadParamConfig;
import com.qygly.ext.rest.helper.keeper.LoginInfoManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author dlt
 * @date 2023/2/15 周三
 */
@Slf4j
public class HttpUtils {
    public static String uploadFile(String absPath,UploadParamConfig config) throws IOException {
        File file=new File(absPath);
        return uploadFile(file,config);
    }

    public static String uploadFile(File file,UploadParamConfig config) throws IOException{
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(config.getRequestPath());

        httpPost.addHeader("qygly-session-id",LoginInfoManager.loginInfo.sessionId);
        //设置参数
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        // 解决乱码问题
        meBuilder.setCharset(StandardCharsets.UTF_8);
        meBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        FileBody fileBody = new FileBody(file , ContentType.create("multipart/form-data",Charset.forName("UTF-8")));
        meBuilder.addPart("Filedata", fileBody);
        meBuilder.addTextBody("sevId",config.getSevId());
        meBuilder.addTextBody("fileGroupAttCode",config.getFileGroupAttCode());
        meBuilder.addTextBody("erId",config.getErId());
        HttpEntity reqEntity = meBuilder.build();

        httpPost.setEntity(reqEntity);
        HttpResponse response = httpClient.execute(httpPost);

        if(response != null){
            int statusCode = response.getStatusLine().getStatusCode();
            if (200 != statusCode){//上传成功 200，失败 500
                log.error(EntityUtils.toString(response.getEntity()));
                return String.valueOf(statusCode);
            }
            HttpEntity resEntity = response.getEntity();
            if(resEntity != null){
                String result = EntityUtils.toString(resEntity,"utf-8");
                return result;
            }
        }
        return "500";
    }
}
