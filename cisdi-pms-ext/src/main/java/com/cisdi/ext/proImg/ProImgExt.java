package com.cisdi.ext.proImg;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title ProImgExt
 * @package com.cisdi.ext.proImg
 * @description 形象进度
 * @date 2022/9/2
 */
public class ProImgExt {


    /**
     * 形象进度左侧分类文件数据
     */
    public void imageTypeData() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String projectId = param.pmPrjId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select ID,CODE,NAME,REMARK,PM_PRJ_ID,CPMS_UUID,SEQ_NO,CPMS_ID," +
                "ifnull(PM_IMG_PRO_TYPE_PID,0) PM_IMG_PRO_TYPE_PID from PM_IMG_PRO_TYPE where PM_PRJ_ID=?", projectId);
        List<ProImgType> typeList = list.stream().map(this::convertProImgType).collect(Collectors.toList());
        List<ImageType> res = typeList.stream().map(p -> {
            ImageType type = new ImageType();
            type.id = p.id;
            type.name = p.name;
            type.seqNo = p.seqNo;
            type.pid = p.pid;
            type.list = getImageData(p.id, jdbcTemplate);
            return type;
        }).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(res)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.typeList = res;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    private List<ProImg> getImageData(String typeId, JdbcTemplate jdbcTemplate) {
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select pip.ID,pip.CODE,pip.NAME,pip.REMARK,PHOTO_DATE,pip.PM_PRJ_ID,PM_IMG_PRO_TYPE_ID,VISUAL_PROGRESS,CPMS_UUID,CPMS_ID,\n" +
                "fl.id as c_img_id,fl.FILE_INLINE_URL as c_img_url,fe.id as p_img_id,fe.FILE_INLINE_URL as p_img_url\n" +
                "from PM_IMG_PRO pip \n" +
                "left join fl_file fl on pip.COVER_IMAGE = fl.id\n" +
                "left join fl_file fe on pip.PANORAMA = fe.id\n" +
                "where PM_IMG_PRO_TYPE_ID=?", typeId);
        return list.stream().map(this::covertProImg).collect(Collectors.toList());
    }


    /**
     * 查询形象进度分类
     */
    public void getProImgType() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String projectId = param.pmPrjId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select ID,CODE,NAME,REMARK,PM_PRJ_ID,CPMS_UUID,SEQ_NO,CPMS_ID," +
                "ifnull(PM_IMG_PRO_TYPE_PID,0) PM_IMG_PRO_TYPE_PID from PM_IMG_PRO_TYPE where PM_PRJ_ID=?", projectId);

        List<ProImgType> typeList = list.stream().map(this::convertProImgType).collect(Collectors.toList());

        List<ProImgType> treeList = typeList.stream().filter(p -> Objects.equals("0", p.pid)).peek(m -> {
            m.children = getChildren(m, typeList);
        }).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(treeList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.list = treeList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 获取形象进度数据
     */
    public void getProImgList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String typeId = param.typeId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("select pip.ID,pip.CODE,pip.NAME,pip.REMARK,PHOTO_DATE,pip.PM_PRJ_ID,PM_IMG_PRO_TYPE_ID,VISUAL_PROGRESS,CPMS_UUID,CPMS_ID,\n" +
                "fl.id as c_img_id,fl.FILE_INLINE_URL as c_img_url,fe.id as p_img_id,fe.FILE_INLINE_URL as p_img_url\n" +
                "from PM_IMG_PRO pip \n" +
                "left join fl_file fl on pip.COVER_IMAGE = fl.id\n" +
                "left join fl_file fe on pip.PANORAMA = fe.id\n" +
                "where PM_IMG_PRO_TYPE_ID=?", typeId);
        List<ProImg> proImgList = list.stream().map(this::covertProImg).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(proImgList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide outSide = new OutSide();
            outSide.proImgList = proImgList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(outSide), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }


    /**
     * 递归构建树
     *
     * @param parent
     * @param allData
     * @return
     */
    private List<ProImgType> getChildren(ProImgType parent, List<ProImgType> allData) {
        return allData.stream().filter(p -> parent.id.equals(p.pid)).peek(m -> {
            m.children = getChildren(m, allData);
        }).collect(Collectors.toList());
    }

    /**
     * 数据转换
     *
     * @param data
     * @return
     */
    private ProImgType convertProImgType(Map<String, Object> data) {
        ProImgType type = new ProImgType();
        type.id = JdbcMapUtil.getString(data, "ID");
        type.pid = JdbcMapUtil.getString(data, "PM_IMG_PRO_TYPE_PID");
        type.name = JdbcMapUtil.getString(data, "NAME");
        type.seqNo = JdbcMapUtil.getString(data, "SEQ_NO");
        return type;
    }

    /**
     * 数据转换
     *
     * @param data
     * @return
     */
    private ProImg covertProImg(Map<String, Object> data) {
        ProImg proImg = new ProImg();
        proImg.id = JdbcMapUtil.getString(data, "ID");
        proImg.name = JdbcMapUtil.getString(data, "NAME");
        proImg.typeId = JdbcMapUtil.getString(data, "PM_IMG_PRO_TYPE_ID");
        proImg.photoDate = JdbcMapUtil.getString(data, "PHOTO_DATE");
        proImg.cImgId = JdbcMapUtil.getString(data, "c_img_id");
        proImg.cImgUrl = JdbcMapUtil.getString(data, "c_img_url");
        proImg.pImgId = JdbcMapUtil.getString(data, "p_img_id");
        proImg.pImgUrl = JdbcMapUtil.getString(data, "p_img_url");
        proImg.visualPro = JdbcMapUtil.getString(data, "VISUAL_PROGRESS");
        return proImg;
    }


    public static class RequestParam {
        public String pmPrjId;

        public String typeId;
    }


    /**
     * 形象进度分类
     */
    public static class ProImgType {
        public String id;
        public String pid;
        public String name;
        public String seqNo;
        public List<ProImgType> children;
    }

    /**
     * 形象进度数据
     */
    public static class ProImg {
        public String id;
        public String name;
        public String typeId;
        public String photoDate;
        public String cImgId;
        public String cImgUrl;
        public String pImgId;
        public String pImgUrl;
        public String visualPro;

    }


    public static class OutSide {
        public List<ProImgType> list;
        public List<ProImg> proImgList;

        public List<ImageType> typeList;
    }

    public static class ImageType {
        public String id;
        public String pid;
        public String name;
        public String seqNo;

        public List<ProImg> list;
    }
}
