package com.bid.ext.cc;

import com.bid.ext.entity.FileInfo;
import com.bid.ext.entity.Folder;
import com.bid.ext.model.AdShare;
import com.bid.ext.model.CcDocDir;
import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.FlFile;
import com.google.gson.Gson;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.login.LoginInfo;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.util.*;

public class DocShareExt {

    /**
     * 获取文档目录分享数据
     */
    public void getDocDirShareStruct() {
        LoginInfo loginInfo = ExtJarHelper.getLoginInfo();
        String orgId = loginInfo.currentOrgInfo.id;
        String userId = loginInfo.userInfo.id;

        List<Folder> originData = new ArrayList<>();

        AdShare adShare = AdShare.insertData();
        String adShareId = adShare.getId();
        String shareUrl = "../cisdi-gczx-jszt/#/filelist?orgId=" + orgId + "&shareId=" + adShareId;

        // 存储所有目录
        Map<String, CcDocDir> allDirs = new HashMap<>();
        // 存储父子关系的结构
        Map<String, Set<CcDocDir>> parentChildMap = new HashMap<>();
        Set<String> processedDirs = new HashSet<>();  // 用于记录已处理过的目录

        // 1. 遍历传入的所有目录
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String ccDocDirId = entityRecord.csCommId;
            CcDocDir ccDocDir = CcDocDir.selectById(ccDocDirId);
            allDirs.put(ccDocDirId, ccDocDir);

            // 初始化父子关系映射
            String parentDirId = ccDocDir.getCcDocDirPid();
            parentChildMap.putIfAbsent(parentDirId, new HashSet<>());
            parentChildMap.get(parentDirId).add(ccDocDir);
        }

        // 2. 根据父子关系构建目录树
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String ccDocDirId = entityRecord.csCommId;
            CcDocDir ccDocDir = allDirs.get(ccDocDirId);

            // 获取当前目录下的文件列表
            List<CcDocFile> ccDocFiles = CcDocFile.selectByWhere(
                    new Where().eq(CcDocFile.Cols.CC_DOC_DIR_ID, ccDocDirId)
            );

            // 创建目录对象并递归处理子目录
            Folder folder = buildFolder(ccDocDir, ccDocFiles, parentChildMap, processedDirs);  // 修改调用，传入 processedDirs
            if (folder != null) {  // 防止空的文件夹
                originData.add(folder);
            }
        }

        adShare.setShareUrl(shareUrl);
        adShare.setShareByUserId(userId);
        adShare.setShareContextData(new Gson().toJson(originData));
        adShare.updateById();

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.msg = adShareId;
        ExtJarHelper.setReturnValue(invokeActResult);
    }


    /**
     * 根据父子关系构建目录树
     */
    private Folder buildFolder(CcDocDir ccDocDir, List<CcDocFile> ccDocFiles, Map<String, Set<CcDocDir>> parentChildMap, Set<String> processedDirs) {
        // 如果当前目录已经处理过，直接返回
        if (processedDirs.contains(ccDocDir.getId())) {
            return null; // 如果已经处理过，则不再处理
        }
        processedDirs.add(ccDocDir.getId());  // 标记当前目录已处理

        Folder folder = new Folder();
        folder.setFileName(ccDocDir.getName());
        folder.setCreateTime(ccDocDir.getCrtDt().toString());
        folder.setCreateBy(ccDocDir.getCrtUserId());
        folder.setType("DIR");

        // 计算目录大小（目录下所有文件大小的总和）
        BigDecimal totalSize = BigDecimal.ZERO;
        folder.setChildren(new ArrayList<>());

        // 处理当前目录下的文件
        if (!SharedUtil.isEmpty(ccDocFiles)) {
            for (CcDocFile ccDocFile : ccDocFiles) {
                String ccPreviewDspSize = ccDocFile.getCcPreviewDspSize();
                BigDecimal fileSize = getFileSize(ccDocFile);

                FileInfo fileInfo = new FileInfo();
                fileInfo.setFileId(ccDocFile.getCcAttachment());
                fileInfo.setFileName(ccDocFile.getName());
                fileInfo.setCreateTime(ccDocFile.getCrtDt().toString());
                fileInfo.setCreateBy(ccDocFile.getCrtUserId());
                fileInfo.setFileSize(ccPreviewDspSize);
                fileInfo.setType(ccDocFile.getCcDocFileTypeId());
                folder.getChildren().add(fileInfo);

                totalSize = totalSize.add(fileSize);
            }
        }

        // 递归处理子目录
        Set<CcDocDir> subDirs = parentChildMap.get(ccDocDir.getId());
        if (subDirs != null && !subDirs.isEmpty()) {
            for (CcDocDir subDir : subDirs) {
                List<CcDocFile> subDirFiles = CcDocFile.selectByWhere(
                        new Where().eq(CcDocFile.Cols.CC_DOC_DIR_ID, subDir.getId())
                );
                Folder subFolder = buildFolder(subDir, subDirFiles, parentChildMap, processedDirs); // 递归调用
                if (subFolder != null) {  // 确保不添加空的子文件夹
                    folder.getChildren().add(subFolder);
                }
            }
        }

        folder.setFileSize(formatFileSize(totalSize));
        return folder;
    }


    private BigDecimal getFileSize(CcDocFile ccDocFile) {
        String ccAttachment = ccDocFile.getCcAttachment();
        FlFile flFile = FlFile.selectById(ccAttachment);

        // 返回文件的大小（单位：KB）
        return flFile != null ? flFile.getSizeKb() : BigDecimal.ZERO;
    }

    // 将文件大小（以字节为单位）转换为适当的格式（例如：1.55MB）
    private String formatFileSize(BigDecimal sizeInKb) {
        if (sizeInKb.compareTo(BigDecimal.valueOf(1024)) < 0) {
            return sizeInKb + "KB";
        } else if (sizeInKb.compareTo(BigDecimal.valueOf(1024 * 1024)) < 0) {
            return String.format("%.2fMB", sizeInKb.divide(BigDecimal.valueOf(1024), 2, BigDecimal.ROUND_HALF_UP));
        } else {
            return String.format("%.2fGB", sizeInKb.divide(BigDecimal.valueOf(1024 * 1024), 2, BigDecimal.ROUND_HALF_UP));
        }
    }
}
