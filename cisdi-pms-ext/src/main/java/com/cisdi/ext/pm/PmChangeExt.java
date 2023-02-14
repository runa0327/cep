package com.cisdi.ext.pm;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmChangeExt
 * @package com.cisdi.ext.pm
 * @description 项目名称/业主变更
 * @date 2022/11/23
 */
public class PmChangeExt {


    /**
     * 变更历史
     */
    public void changeList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String id = String.valueOf(map.get("projectId"));
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT \n" +
                "case CHANGE_WAY when '1' then '项目名称变更' when '2' then '项目业主变更' else '' end  as CHANGE_WAY \n" +
                ",NEW_RECORD,ATT_FILE_GROUP_ID,CHANGE_REMARK,au.`name` as CHIEF_USER_ID,CHANGE_DATE \n" +
                "FROM PM_CHANGE_RECORD pcr \n" +
                "left join ad_user au on au.id = pcr.CHIEF_USER_ID " +
                "where PM_PRJ_ID=?", id);
        List<Map<String, Object>> list1 = myJdbcTemplate.queryForList("select * from PM_PARTY");
        List<ChangeRecord> changeRecordList = list.stream().map(p -> {
            ChangeRecord record = new ChangeRecord();
            record.changeWay = JdbcMapUtil.getString(p, "CHANGE_WAY");
            String recordValue = JdbcMapUtil.getString(p, "NEW_RECORD");

            if ("项目业主变更".equals(record.changeWay)) {
                if (p.get("NEW_RECORD") != null) {
                    Optional<Map<String, Object>> optional = list1.stream().filter(m -> Objects.equals(m.get("ID"), p.get("NEW_RECORD"))).findAny();
                    if (optional.isPresent()) {
                        recordValue = String.valueOf(optional.get().get("NAME"));
                    }
                }
            }
            record.newRecord = recordValue;
            String fileCount = "0";
            if (p.get("ATT_FILE_GROUP_ID") != null) {
                String[] str = JdbcMapUtil.getString(p, "ATT_FILE_GROUP_ID").split(",");
                fileCount = String.valueOf(str.length);
            }
            record.fileCount = fileCount;
            record.changeRemark = JdbcMapUtil.getString(p, "CHANGE_REMARK");
            record.chiefUserId = JdbcMapUtil.getString(p, "CHIEF_USER_ID");
            record.changeDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "CHANGE_DATE"));
            record.attFileGroupId = JdbcMapUtil.getString(p, "ATT_FILE_GROUP_ID");
            return record;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(changeRecordList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.recordList = changeRecordList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 变更历史文件列表
     */
    public void changeFileList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        if (map.get("fileIds") == null) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }
        String fileIds = String.valueOf(map.get("fileIds"));
        List<String> ids = Arrays.asList(fileIds.split(","));
        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        Map<String, Object> queryParams = new HashMap<>();// 创建入参map
        queryParams.put("ids", ids);
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
        AtomicInteger index = new AtomicInteger(0);
        List<FileObj> fileObjList = list.stream().map(p -> {
            FileObj obj = new FileObj();
            obj.num = index.getAndIncrement() + 1;
            obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
            obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
            obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
            obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
            obj.id = JdbcMapUtil.getString(p, "ID");
            obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
            obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
            return obj;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(fileObjList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.fileObjList = fileObjList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    /**
     * 项目名称/业主变更
     */
    public void changData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InputData input = JsonUtil.fromJson(json, InputData.class);
        String projectId = input.projectId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PRJ where id=?", projectId);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("数据错误，项目不存在！");
        }
        String id = Crud.from("PM_CHANGE_RECORD").insertData();
        String oldData = "";
        String sql = "";
        if (Objects.equals("1", input.type)) {
            oldData = String.valueOf(list.get(0).get("NAME"));
            sql = "update PM_PRJ set NAME=? where id=?";
        } else {
            oldData = String.valueOf(list.get(0).get("CUSTOMER_UNIT"));
            sql = "update PM_PRJ set CUSTOMER_UNIT=? where id=?";
        }
        Crud.from("PM_CHANGE_RECORD").where().eq("ID", id).update()
                .set("PM_PRJ_ID", projectId).set("CHANGE_WAY", input.type).set("NEW_RECORD", input.changeData).set("OLD_RECORD", oldData).set("CHANGE_DATE", new Date())
                .set("ATT_FILE_GROUP_ID", input.fileIds).set("CHANGE_REMARK", input.changeRemark).set("CHIEF_USER_ID", input.userId).exec();
        //修改项目信息
        myJdbcTemplate.update(sql, input.changeData, projectId);
    }

    /**
     * 合作方下拉
     */
    public void getCustomerUnit() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_PARTY where `status`='ap' and IS_CUSTOMER = 1");
        List<PmParty> pmPartyList = list.stream().map(p -> {
            PmParty pmParty = new PmParty();
            pmParty.id = JdbcMapUtil.getString(p, "ID");
            pmParty.name = JdbcMapUtil.getString(p, "NAME");
            return pmParty;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(pmPartyList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.partyList = pmPartyList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    public static class InputData {
        public String projectId;

        public String changeData;

        /**
         * 类型 1-项目名称 2-业主
         */
        public String type;

        //变更依据
        public String fileIds;

        //变更说明
        public String changeRemark;
        //变更人
        public String userId;
    }

    public static class PmParty {
        public String id;
        public String name;
    }

    public static class OutSide {
        public List<PmParty> partyList;

        public List<ChangeRecord> recordList;

        public List<FileObj> fileObjList;
    }

    public static class ChangeRecord {
        //变更事项
        public String changeWay;
        //变更内容
        public String newRecord;
        //文件
        public String fileCount;
        //变更说明
        public String changeRemark;
        //变更人
        public String chiefUserId;
        //变更时间
        public String changeDate;
        //文件ID
        public String attFileGroupId;
    }

    public static class FileObj {
        //序号
        public Integer num;
        //文件名称
        public String fileName;
        //文件大小
        public String fileSize;
        //上传人
        public String uploadUser;
        //上传时间
        public String uploadDate;

        public String id;

        public String viewUrl;

        public String downloadUrl;

    }

}
