package com.bid.ext;

import com.bid.ext.cc.BidExt;
import com.bid.ext.utils.SysSettingUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestExt {
    public void importContract() {
        new BidExt().importContract();
    }

    /**
     * 简化版预览文件。采用KK进行预览。
     *
     * @throws UnsupportedEncodingException
     */
    public void simplePreview() throws UnsupportedEncodingException {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();

        String fileDownloadUrl1 = SysSettingUtil.getValue("FILE_DOWNLOAD_URL");
        String kkPreviewUrl = SysSettingUtil.getValue("KK_PREVIEW_URL");

        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {

            Object ccAttachment = entityRecord.valueMap.get("CC_ATTACHMENT");
            if (SharedUtil.isEmpty(ccAttachment)) {
                throw new BaseException("资料文件的CC_ATTACHMENT字段为空！");
            }

            List<Map<String, Object>> fileList = ExtJarHelper.getMyJdbcTemplate().queryForList("select * from fl_file f where f.id=?", ccAttachment);
            if (SharedUtil.isEmpty(fileList)) {
                throw new BaseException("资料文件的CC_ATTACHMENT字段对应FL_FILE记录不存在！");
            }

            Map<String, Object> file = fileList.get(0);

            String fileId = JdbcMapUtil.getString(file, "ID");
            String fileExt = JdbcMapUtil.getString(file, "EXT");

            String fileDownloadUrl = fileDownloadUrl1 + "?fileId=" + fileId + "&qygly-session-id=" + ExtJarHelper.getLoginInfo().sessionId + "&fullfilename=" + fileId + (SharedUtil.isEmpty(fileExt) ? "" : ("." + fileExt));

            // 采用KK进行预览时，要对url部分做2次编码。第1次是Base64编码、第2次是URL编码：
            String previewUrl = kkPreviewUrl + "?url=" + URLEncoder.encode(cn.hutool.core.codec.Base64.encode(fileDownloadUrl), "UTF-8");

            UrlToOpen urlToOpen = new UrlToOpen();
            urlToOpen.url = previewUrl;
            urlToOpen.title = "预览";
            invokeActResult.urlToOpenList.add(urlToOpen);
        }

        ExtJarHelper.setReturnValue(invokeActResult);
    }
    
}
