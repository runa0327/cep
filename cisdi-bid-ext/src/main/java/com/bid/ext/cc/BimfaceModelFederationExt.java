package com.bid.ext.cc;

import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.CcModelFederationToFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.BaseInfo;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.bid.ext.cc.PreViewExt.doGetStringStringMap;

@Slf4j
public class BimfaceModelFederationExt {

    public void integrate() {
        InvokeActResult invokeActResult = new InvokeActResult();
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        BaseInfo currentOrgInfo = loginInfo.currentOrgInfo;
        String orgCode = currentOrgInfo.code;
        RestTemplate restTemplate = ExtJarHelper.getRestTemplate();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String token = doGetStringStringMap();

        StringBuilder ccBimfaceFileIdsBuilder = new StringBuilder();
        List<String> nameList = new ArrayList<>();
        String previousCcPrjId = null;

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String id = entityRecord.csCommId;
            CcDocFile ccDocFile = CcDocFile.selectById(id);
            String ccDocFileName = I18nUtil.tryGetInCurrentLang(ccDocFile.getName());
            String ccPreviewFileId = ccDocFile.getCcPreviewFileId();
            String ccPreviewConversionStatusId = ccDocFile.getCcPreviewConversionStatusId();
            String ccPrjId = ccDocFile.getCcPrjId();

            if ("SUCC".equals(ccPreviewConversionStatusId)) {
                nameList.add(ccDocFileName);

                if (ccBimfaceFileIdsBuilder.length() > 0) {
                    ccBimfaceFileIdsBuilder.append(",");
                }
                ccBimfaceFileIdsBuilder.append(ccPreviewFileId);

                if (previousCcPrjId == null) {
                    previousCcPrjId = ccPrjId;
                } else {
                    if (!previousCcPrjId.equals(ccPrjId)) {
                        String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.differentPrjId");
                        throw new BaseException(message);
                    }
                }
            } else if ("DOING".equals(ccPreviewConversionStatusId)) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.previewConverting");
                throw new BaseException(message);
            } else {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.bimfaceModelFederation.integrate");
                throw new BaseException(message);
            }
        }

        String ccBimfaceFileIds = ccBimfaceFileIdsBuilder.toString();
        String integrateName = generateIntegrateName(nameList);

        CcModelFederationToFile ccModelFederationToFile = CcModelFederationToFile.newData();
        ccModelFederationToFile.setCcPrjId(previousCcPrjId);
        ccModelFederationToFile.setCcBimfaceFileIds(ccBimfaceFileIds);
        ccModelFederationToFile.setName(integrateName);
        ccModelFederationToFile.insertById();

        Map<String, Object> map = myJdbcTemplate.queryForMap("SELECT SETTING_VALUE FROM ad_sys_setting WHERE CODE = 'GATEWAY_URL'");
        String gateWayUrl = JdbcMapUtil.getString(map, "SETTING_VALUE");
        String integrateUrl = gateWayUrl + "cisdi-microservice-" + orgCode + "/modelFederation/integrate";

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("integrateName", integrateName);
        body.add("token", token);
        body.add("ccBimfaceFileIds", ccBimfaceFileIds);
        body.add("ccModelFederationToFile", ccModelFederationToFile.getId());
        body.add("orgCode", orgCode);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<MultiValueMap<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<String> response = restTemplate.exchange(integrateUrl, HttpMethod.POST, entity, String.class);
        log.info(response.toString());
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);
    }

    private String generateIntegrateName(List<String> names) {
        if (names == null || names.isEmpty()) {
            return "";
        }
        if (names.size() <= 3) {
            return String.join("+", names);
        }

        String baseName = names.get(0) + "+" + names.get(1);
        String suffix = "+...等" + names.size() + "个模型";

        int maxLength = 200;
        if ((baseName + suffix).length() > maxLength) {
            String first = names.get(0);
            int cutLen = maxLength - suffix.length() - 5;
            if (cutLen < 0) cutLen = 10;
            if (first.length() > cutLen) {
                baseName = first.substring(0, cutLen) + "...";
            } else {
                baseName = first;
            }
        }

        return baseName + suffix;
    }

    public void previewIntegrate() {
        InvokeActResult invokeActResult = new InvokeActResult();
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcModelFederationToFile ccModelFederationToFile = CcModelFederationToFile.selectById(csCommId);
            String ccPreviewConversionStatusId = ccModelFederationToFile.getCcPreviewConversionStatusId();
            String ccPreviewUrl = ccModelFederationToFile.getCcPreviewUrl();
            if ("SUCC".equals(ccPreviewConversionStatusId)) {
                UrlToOpen extBrowserWindowToOpen = new UrlToOpen();
                extBrowserWindowToOpen.url = ccPreviewUrl;
                extBrowserWindowToOpen.title = "预览";
                invokeActResult.urlToOpenList = new ArrayList<>();
                invokeActResult.urlToOpenList.add(extBrowserWindowToOpen);
                ExtJarHelper.setReturnValue(invokeActResult);
                return;
            } else if ("DOING".equals(ccPreviewConversionStatusId)) {
                String message = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.gczx.ql.previewConverting");
                throw new BaseException(message);
            }
        }
    }
}
