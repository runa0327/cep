package com.bid.ext.ai;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SmartMatchExt {

    public void fuzzymatch() {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<EntityRecord> entityRecordList = ExtJarHelper.getEntityRecordList();
        Map<String, Object> valueMap = entityRecordList.get(0).valueMap;

        String ccPrjId = JdbcMapUtil.getString(valueMap, "CC_PRJ_ID");
        if (!StringUtils.hasText(ccPrjId)) {
            throw new BaseException("项目为空");
        }

        String ccQsInspectionTypeId = JdbcMapUtil.getString(valueMap, "CC_QS_INSPECTION_TYPE_ID");
        if (!StringUtils.hasText(ccQsInspectionTypeId)) {
            throw new BaseException("巡检类型为空");
        }

        String remark = JdbcMapUtil.getString(valueMap, "REMARK");
        if (!StringUtils.hasText(remark)) {
            throw new BaseException("巡检描述为空");
        }

        String prjTypeId = getPrjTypeId(ccPrjId);
        if (!StringUtils.hasText(prjTypeId)) {
            throw new BaseException("项目类型错误");
        }

        String sql = "SELECT ID, NAME FROM CC_QS_ISSUE_POINT_TYPE WHERE CC_QS_INSPECTION_TYPE_ID = ? AND CC_PRJ_TYPE_ID = ?";
        List<Map<String, Object>> queryForList = myJdbcTemplate.queryForList(sql, ccQsInspectionTypeId, prjTypeId);
        if (CollectionUtils.isEmpty(queryForList)) {
            throw new BaseException("没有找到问题分类.");
        }

        List<String> combinePointList = new ArrayList<>(); // 改用 List 动态存储
        for (Map<String, Object> queryForMap : queryForList) {
            String name = JdbcMapUtil.getString(queryForMap, "NAME");
            String typeId = JdbcMapUtil.getString(queryForMap, "ID");
            String pointSql = "SELECT ID,NAME FROM CC_QS_ISSUE_POINT WHERE CC_QS_ISSUE_POINT_TYPE_ID = ?";
            List<Map<String, Object>> queryForList1 = myJdbcTemplate.queryForList(pointSql, typeId);
            for (Map<String, Object> stringObjectMap : queryForList1) {
                String subId = JdbcMapUtil.getString(stringObjectMap, "ID");
                String subname = JdbcMapUtil.getString(stringObjectMap, "NAME");
                // 将 name-subname的字符串，放入combinePoint
                // 将 name-subname 组合后加入 List
                combinePointList.add(typeId + "-" + subId + ":"+ name + "-" + subname);
            }
        }
        // 构建一个包含 "data" 键的 Map，值为列表
        Map<String, List<String>> jsonMap = new HashMap<>();
        jsonMap.put("data", combinePointList);

        // 将 Map 转换为 JSON 字符串
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResult = objectMapper.writeValueAsString(jsonMap);
            System.out.println(jsonResult);
            // 输出示例: {"data":["1-10:分类-要点"]}
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getPrjTypeId(String ccPrjId) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        String sql = "SELECT CC_PRJ_TYPE_ID from CC_PRJ WHERE ID = ?";
        Map<String, Object> queryForMap = myJdbcTemplate.queryForMap(sql, ccPrjId);
        String ccPrjTypeId = JdbcMapUtil.getString(queryForMap, "CC_PRJ_TYPE_ID");

        return ccPrjTypeId;
    }


}
