package com.cisdi.ext.file;

import com.cisdi.ext.api.FileApi;
import com.cisdi.ext.model.view.file.BaseFileView;
import com.cisdi.ext.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

public class BaseFileExt {

    /**
     * 文件信息获取
     * @param fileId 文件id
     * @return 文件详细信息列表
     */
    public static List<BaseFileView> getFile(String fileId) {
        fileId = StringUtil.replaceCode(fileId,",","','");
        return FileApi.getFileList(fileId);
    }
}
