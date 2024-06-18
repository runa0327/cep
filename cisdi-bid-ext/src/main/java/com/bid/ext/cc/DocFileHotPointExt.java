package com.bid.ext.cc;

import com.bid.ext.model.CcDocFile;
import com.bid.ext.model.CcDocFileHotPoint;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DocFileHotPointExt {

    /**
     * 通过资料文件id获取热点信息
     */
    public void getHotPointByDocFileId() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        String ccDocFileId = JdbcMapUtil.getString(inputMap, "ccDocFileId");
        //根据资料文件id查询热点列表
        List<CcDocFileHotPoint> ccDocFileHotPoints = CcDocFileHotPoint.selectByWhere(new Where().eq(CcDocFileHotPoint.Cols.CC_DOC_FILE_ID, ccDocFileId));
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("ccDocFileId", ccDocFileId);
        List<Map<String, Object>> hotPointList = new ArrayList<>();
        for (CcDocFileHotPoint ccDocFileHotPoint : ccDocFileHotPoints) {
            Map<String, Object> hotPointMap = new HashMap<>();

            hotPointMap.put("ccAttachment", ccDocFileHotPoint.getCcAttachment()); // 热点文件id
            hotPointMap.put("name", ccDocFileHotPoint.getName()); // 热点名称
            hotPointMap.put("position", ccDocFileHotPoint.getPosition()); //坐标
            hotPointMap.put("hotPointId", ccDocFileHotPoint.getId()); // 热点id
            hotPointList.add(hotPointMap);
            outputMap.put("HotPointList", hotPointList);
        }
        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 添加热点
     */
    public void addHotPoint() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        //全景id
        String ccDocFileId = JdbcMapUtil.getString(inputMap, "ccDocFileId");
        //项目id
        CcDocFile ccDocFile = CcDocFile.selectById(ccDocFileId);
        String ccPrjId = ccDocFile.getCcPrjId();
        //坐标
        String position = JdbcMapUtil.getString(inputMap, "position");
        //全景文件id
        String ccAttachment = JdbcMapUtil.getString(inputMap, "ccAttachment");
        //热点名
        String name = JdbcMapUtil.getString(inputMap, "name");
        CcDocFileHotPoint ccDocFileHotPoint = CcDocFileHotPoint.newData();
        ccDocFileHotPoint.setCcDocFileId(ccDocFileId);
        ccDocFileHotPoint.setPosition(position);
        ccDocFileHotPoint.setCcAttachment(ccAttachment);
        ccDocFileHotPoint.setName(name);
        ccDocFileHotPoint.setCcPrjId(ccPrjId);
        ccDocFileHotPoint.insertById();
        String hotPointId = ccDocFileHotPoint.getId();
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("hotPointId", hotPointId);
        ExtJarHelper.setReturnValue(outputMap);
    }

    /**
     * 删除热点
     */
    public void deleteHotPoint() {
        Map<String, Object> inputMap = ExtJarHelper.getExtApiParamMap();
        //全景id
        String ccDocFileId = JdbcMapUtil.getString(inputMap, "hotPointId");
        CcDocFileHotPoint.deleteById(ccDocFileId);
        Map<String, Object> outputMap = new HashMap<>();
        outputMap.put("hotPointId", ccDocFileId);
        ExtJarHelper.setReturnValue(outputMap);
    }

}
