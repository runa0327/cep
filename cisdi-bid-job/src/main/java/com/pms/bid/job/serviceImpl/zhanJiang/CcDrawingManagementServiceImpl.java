package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.rocketmq.ModelStatusUpdate;
import com.pms.bid.job.domain.zhanJiang.CcDrawingManagement;
import com.pms.bid.job.mapper.zhanJiang.CcDrawingManagementMapper;
import com.pms.bid.job.mapper.zhanJiang.CcPrjStructNodeMapper;
import com.pms.bid.job.service.zhanJiang.CcDrawingManagementService;
import com.pms.bid.job.util.DateUtil;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CcDrawingManagementServiceImpl implements CcDrawingManagementService {

    @Resource
    private CcDrawingManagementMapper ccDrawingManagementMapper;

    @Resource
    private CcPrjStructNodeMapper ccPrjStructNodeMapper;

    /**
     * 消费模型解析状态
     *
     * @param data    mq数据详情
     * @param message mq原始json数据
     * @param now     当前时间
     */
    @Override
    public void dealRocketMQData(ModelStatusUpdate data, String message, String now) {
        if (data != null) {
            String createBy = "0099250247095871681";
            String id = ccDrawingManagementMapper.queryIdByCcSteelOwnerDrawingId(data.getTtNumber());
            if (StringUtils.hasText(id)) {
                updateByMQ(data,id,message,now,createBy);
            }
//            else {
//                createCcDrawingManagementByMQ(data,message,now,createBy);
//            }
        }
    }

    /**
     * 新增图纸管理
     * @param tmp 通过mq获取的数据
     * @param message mq原始数据
     * @param now 当前时间
     * @param createBy 操作人
     */
    private void createCcDrawingManagementByMQ(ModelStatusUpdate tmp, String message, String now, String createBy) {
        CcDrawingManagement ccDrawingManagement = new CcDrawingManagement();
        ccDrawingManagement.setId(IdUtil.getSnowflakeNextIdStr());
        ccDrawingManagement.setVer("1");
        ccDrawingManagement.setTs(now);
        ccDrawingManagement.setCreateBy(createBy);
        ccDrawingManagement.setLastUpdateBy(createBy);
        ccDrawingManagement.setCreateDate(now);
        ccDrawingManagement.setLastUpdateDate(now);
        ccDrawingManagement.setStatus("AP");
        ccDrawingManagement.setCcSteelOwnerDrawingId(tmp.getTtNumber());
        if (30 == tmp.getStatus()){
            ccDrawingManagement.setCcDrawingStatusId("DONE");
        } else {
            ccDrawingManagement.setCcDrawingStatusId("TODO");
        }
        ccDrawingManagement.setThreeDActDate(tmp.getCreateTime());
        ccDrawingManagement.setMqMsgJson(message);
        ccDrawingManagement.setMqReceiveDateTime(now);
        ccDrawingManagementMapper.create(ccDrawingManagement);
    }

    /**
     * 修改图纸管理
     * @param tmp 通过mq获取的数据
     * @param id id
     * @param message mq原始数据
     * @param now 当前时间
     * @param createBy 操作人
     */
    private void updateByMQ(ModelStatusUpdate tmp, String id, String message, String now, String createBy) {
        String ccSteelOwnerDrawingId = tmp.getTtNumber();
        if (StringUtils.hasText(ccSteelOwnerDrawingId)) {
            String unitProjectCode = ccSteelOwnerDrawingId.substring(0,6);
            //  根据单元工程编码获取id
            String unitProjectId = ccPrjStructNodeMapper.queryIdByCode(unitProjectCode);
            CcDrawingManagement ccDrawingManagement = new CcDrawingManagement();
            ccDrawingManagement.setId(id);
            ccDrawingManagement.setTs(now);
            ccDrawingManagement.setLastUpdateBy(createBy);
            ccDrawingManagement.setLastUpdateDate(now);
            ccDrawingManagement.setCcSteelOwnerDrawingId(ccSteelOwnerDrawingId);
            if (30 == tmp.getStatus()){
                ccDrawingManagement.setCcDrawingStatusId("DONE");
            } else {
                ccDrawingManagement.setCcDrawingStatusId("TODO");
            }
            String time = tmp.getCreateTime();
            if (StringUtils.hasText(time)){
                ccDrawingManagement.setThreeDActDate(DateUtil.convertTimestampToDateString(time,"yyyy-MM-dd HH:mm:ss"));
            }
            ccDrawingManagement.setMqMsgJson(message);
            ccDrawingManagement.setMqReceiveDateTime(now);
            ccDrawingManagement.setUnitProjectCode(unitProjectCode);
            ccDrawingManagement.setUnitProjectId(unitProjectId);
            ccDrawingManagementMapper.updateConditionById(ccDrawingManagement);
        }
    }
}
