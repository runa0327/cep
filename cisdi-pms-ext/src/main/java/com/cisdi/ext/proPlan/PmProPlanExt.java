package com.cisdi.ext.proPlan;

import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PmProPlanExt
 * @package com.cisdi.ext.proPlan
 * @description 项目进展
 * @date 2023/3/1
 */
public class PmProPlanExt {

    public void proPlanView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String projectId = String.valueOf(map.get("projectId"));
        String nodeId = String.valueOf(map.get("nodeId"));

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select PM_PRJ_ID,pppl.PM_PRO_PLAN_NODE_ID as node_id, " +
                "pppl.AD_ENT_ID_IMP,pppl.AD_ATT_ID_IMP,ae.`NAME` as ant_name,aa.`NAME` as att_name, " +
                "ae.`CODE` as ant_code ,aa.`CODE` as att_code  " +
                "from PM_PRO_PLAN_LEDGER  pppl " +
                "left join ad_ent ae on ae.id = pppl.AD_ENT_ID_IMP " +
                "left join ad_att aa on aa.id = pppl.AD_ATT_ID_IMP " +
                "where PM_PRJ_ID=? and PM_PRO_PLAN_NODE_ID=?", projectId, nodeId);
        if (!CollectionUtils.isEmpty(list)) {
            viewObj viewObj = new viewObj();
            List<Map<String, Object>> nodeList = myJdbcTemplate.queryForList("select hd.`NAME` as post,PLAN_START_DATE,PLAN_COMPL_DATE,PLAN_TOTAL_DAYS, " +
                    "gsv.`NAME` as `status`,ACTUAL_START_DATE,ACTUAL_COMPL_DATE,PLAN_CARRY_DAYS  " +
                    "from PM_PRO_PLAN_NODE  pppn " +
                    "left join hr_dept hd on hd.id = pppn.CHIEF_DEPT_ID " +
                    "left join gr_set_value gsv on gsv.id = pppn.PROGRESS_STATUS_ID " +
                    "where pppn.id=?", list.get(0).get("node_id"));

            if (!CollectionUtils.isEmpty(nodeList)) {
                Map<String, Object> node = nodeList.get(0);
                viewObj.post = JdbcMapUtil.getString(node, "post");
                viewObj.planStartTime = JdbcMapUtil.getString(node, "PLAN_START_DATE");
                viewObj.planCompleteTime = JdbcMapUtil.getString(node, "PLAN_COMPL_DATE");
                viewObj.predictDays = JdbcMapUtil.getString(node, "PLAN_TOTAL_DAYS");
                viewObj.status = JdbcMapUtil.getString(node, "status");
                viewObj.actualStartTime = JdbcMapUtil.getString(node, "ACTUAL_START_DATE");
                viewObj.actualCompleteTime = JdbcMapUtil.getString(node, "ACTUAL_COMPL_DATE");
                viewObj.surplusDays = JdbcMapUtil.getString(node, "PLAN_CARRY_DAYS");

                List<FileListObj> fileListObjList = new ArrayList<>();
                for (Map<String, Object> stringObjectMap : list) {
                    String tableName = String.valueOf(list.get(0).get("ant_code"));
                    String column = String.valueOf(list.get(0).get("att_code"));
                    FileListObj fileListObj = new FileListObj();
                    fileListObj.title = JdbcMapUtil.getString(stringObjectMap, "att_name");
                    StringBuilder sb = new StringBuilder();
                    sb.append("select * from ").append(tableName).append(" where PM_PRJ_ID ='").append(projectId).append("'");
                    List<Map<String, Object>> dataList = myJdbcTemplate.queryForList(sb.toString());
                    if (!CollectionUtils.isEmpty(dataList)) {
                        String fileIds = JdbcMapUtil.getString(dataList.get(0), column);
                        if (!"null".equals(fileIds)) {
                            List<String> ids = Arrays.asList(fileIds.split(","));
                            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
                            Map<String, Object> queryParams = new HashMap<>();// 创建入参map
                            queryParams.put("ids", ids);
                            List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
                            AtomicInteger index = new AtomicInteger(0);
                            List<FileObj> fileObjList = fileList.stream().map(p -> {
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
                            fileListObj.fileObjList = fileObjList;
                            fileListObjList.add(fileListObj);
                        }
                    }
                }
                viewObj.fileListObjList = fileListObjList;
                Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(viewObj), Map.class);
                ExtJarHelper.returnValue.set(outputMap);
            }
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }

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

    public static class FileListObj {
        public String title;
        public List<FileObj> fileObjList;
    }

    public static class viewObj {
        //岗位
        public String post;
        //计划开始实际
        public String planStartTime;
        //计划完成时间
        public String planCompleteTime;
        //预计用时
        public String predictDays;
        //状态
        public String status;
        //实际开始时间
        public String actualStartTime;
        //实际结束时间
        public String actualCompleteTime;
        //剩余用时
        public String surplusDays;

        public List<FileListObj> fileListObjList;

    }
}
