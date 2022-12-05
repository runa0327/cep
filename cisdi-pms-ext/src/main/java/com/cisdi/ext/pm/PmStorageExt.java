package com.cisdi.ext.pm;

import com.cisdi.ext.fund.FundReachApi;
import com.cisdi.ext.util.JsonUtil;
import com.itextpdf.text.pdf.PdfICCBased;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmStorageExt
 * @package com.cisdi.ext.pm
 * @description 项目入库信息
 * @date 2022/12/1
 */
public class PmStorageExt {

    /**
     * 项目入库信息上传时回显
     */
    public void PmStorageFileUploadView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select psf.id as id,ff.DSP_NAME as fileName,gsv.`NAME` as type ,au.`NAME` as uploadUser,psf.UPLOAD_DTTM as uploadTime from PM_STORAGE_FILE psf \n" +
                "left join fl_file ff on ff.id = psf.FL_FILE_ID \n" +
                "left join gr_set_value gsv on psf.PM_STORAGE_TYPE = gsv.id \n" +
                "left join ad_user au on psf.AD_USER_ID = au.id \n" +
                "where PM_PRJ_ID=?", projectId);
        List<PmStorageInfo> infoList = list.stream().map(p -> {
            PmStorageInfo info = new PmStorageInfo();
            info.id = JdbcMapUtil.getString(p, "id");
            info.fileName = JdbcMapUtil.getString(p, "fileName");
            info.type = JdbcMapUtil.getString(p, "type");
            info.uploadUser = JdbcMapUtil.getString(p, "uploadUser");
            info.uploadTime = JdbcMapUtil.getString(p, "uploadTime");
            return info;
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(infoList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            viewInfo viewInfo = new viewInfo();
            viewInfo.list = infoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(viewInfo), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }

    }

    /**
     * 项目入库详情
     */
    public void PmStorageFileView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String projectId = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap("select pm.id as projectId,pm.`NAME` as projectName,pp.`NAME` as projectOwner,gsvt.`NAME` as projectType,gsvl.`NAME` as location from pm_prj pm \n" +
                    "left join PM_PARTY pp on pm.CUSTOMER_UNIT = pp.id \n" +
                    "left join gr_set_value gsvt on gsvt.id = pm.PROJECT_TYPE_ID \n" +
                    "left join gr_set_value gsvl on gsvl.id = pm.BASE_LOCATION_ID \n" +
                    "where pm.id=? ", projectId);
            viewInfo viewInfo = new viewInfo();
            viewInfo.projectId = JdbcMapUtil.getString(stringObjectMap, "projectId");
            viewInfo.projectName = JdbcMapUtil.getString(stringObjectMap, "projectName");
            viewInfo.projectOwner = JdbcMapUtil.getString(stringObjectMap, "projectOwner");
            viewInfo.projectType = JdbcMapUtil.getString(stringObjectMap, "projectType");
            viewInfo.location = JdbcMapUtil.getString(stringObjectMap, "location");

            List<Map<String, Object>> list = myJdbcTemplate.queryForList("select psf.id as id,ff.DSP_NAME as fileName,gsv.`NAME` as type ,au.`NAME` as uploadUser,psf.UPLOAD_DTTM as uploadTime from PM_STORAGE_FILE psf \n" +
                    "left join fl_file ff on ff.id = psf.FL_FILE_ID \n" +
                    "left join gr_set_value gsv on psf.PM_STORAGE_TYPE = gsv.id \n" +
                    "left join ad_user au on psf.AD_USER_ID = au.id \n" +
                    "where PM_PRJ_ID=?", projectId);
            List<PmStorageInfo> infoList = list.stream().map(p -> {
                PmStorageInfo info = new PmStorageInfo();
                info.id = JdbcMapUtil.getString(p, "id");
                info.fileName = JdbcMapUtil.getString(p, "fileName");
                info.type = JdbcMapUtil.getString(p, "type");
                info.uploadUser = JdbcMapUtil.getString(p, "uploadUser");
                info.uploadTime = JdbcMapUtil.getString(p, "uploadTime");
                return info;
            }).collect(Collectors.toList());

            viewInfo.list = infoList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(viewInfo), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            throw new BaseException(e.getMessage());
        }

    }

    /**
     * 上传项目入库信息
     */
    public void uploadPmStorageFile() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PmStorageInput input = JsonUtil.fromJson(json, PmStorageInput.class);
        String id = Crud.from("PM_STORAGE_FILE").insertData();
        Crud.from("PM_STORAGE_FILE").where().eq("ID", id).update()
                .set("PM_PRJ_ID", input.projectId).set("FL_FILE_ID", input.fileId).set("AD_USER_ID", input.userId)
                .set("UPLOAD_DTTM", new Date()).set("PM_STORAGE_TYPE", input.typeId).exec();

    }

    /**
     * 删除项目入库信息
     */
    public void delPmStorageFile() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("id"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        myJdbcTemplate.update("delete from PM_STORAGE_FILE where id = ?", id);
    }


    public static class PmStorageInput {
        public String projectId;

        public String fileId;

        public String typeId;

        public String userId;
    }

    public static class PmStorageInfo {
        public String id;
        public String fileName;
        public String type;
        public String uploadUser;
        public String uploadTime;
    }

    public static class viewInfo {
        public String projectId;
        public String projectName;
        public String projectOwner;
        public String projectType;
        public String location;

        public List<PmStorageInfo> list;
    }

}
