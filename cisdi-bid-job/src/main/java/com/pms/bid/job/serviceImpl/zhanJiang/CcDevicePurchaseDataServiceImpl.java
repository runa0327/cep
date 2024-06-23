package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.rocketmq.DeviceMsg;
import com.pms.bid.job.domain.zhanJiang.CcDevicePurchaseData;
import com.pms.bid.job.mapper.zhanJiang.CcDevicePurchaseDataMapper;
import com.pms.bid.job.mapper.zhanJiang.CcPrjStructNodeMapper;
import com.pms.bid.job.service.zhanJiang.CcDevicePurchaseDataService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CcDevicePurchaseDataServiceImpl implements CcDevicePurchaseDataService {

    @Resource
    private CcDevicePurchaseDataMapper ccDevicePurchaseDataMapper;

    @Resource
    private CcPrjStructNodeMapper ccPrjStructNodeMapper;

    /**
     * 消费设备信息
     *
     * @param list    mq数据详情
     * @param message mq原始json数据
     * @param now     当前时间
     */
    @Override
    public void dealRocketMQData(List<DeviceMsg> list, String message, String now) {

        if (!CollectionUtils.isEmpty(list)) {
            String createBy = "0099250247095871681";
            for (DeviceMsg tmp : list) {

                //  根据设备位号判断是否存在
                String id = ccDevicePurchaseDataMapper.queryIdByDeviceCode(tmp.getDeviceTagNo());
                if (!StringUtils.hasText(id)) { //  新增
                    createDevice(tmp,message,now,createBy);
                } else {
                    updateDeviceById(tmp,message,id,now,createBy);
                }
            }
        }

    }

    /**
     * 根据id匹配修改设备信息
     * @param tmp mq获取的转换后数据
     * @param message 从mq获取的原始数据
     * @param id 采购设备进度表id
     * @param now 当前时间
     * @param createBy 操作人
     */
    private void updateDeviceById(DeviceMsg tmp, String message, String id, String now, String createBy) {
        CcDevicePurchaseData ccDevicePurchaseData = new CcDevicePurchaseData();
        ccDevicePurchaseData.setId(id);
        ccDevicePurchaseData.setTs(now);
        ccDevicePurchaseData.setLastUpdateBy(createBy);
        ccDevicePurchaseData.setLastUpdateDate(now);
        ccDevicePurchaseData.setUnitProjectCode(tmp.getUnitProjectCode());
        ccDevicePurchaseData.setUnitProjectName(tmp.getUnitProjectName());
        ccDevicePurchaseData.setPurchaseDeviceNo(tmp.getDeviceNo());
        if (4 == tmp.getDeviceStatus()) {
            ccDevicePurchaseData.setDeviceInstallStatus(1);
        } else {
            ccDevicePurchaseData.setDeviceInstallStatus(0);
        }
        ccDevicePurchaseData.setDeviceTagNo(tmp.getDeviceTagNo());
        ccDevicePurchaseData.setMqMsgJson(message);
        ccDevicePurchaseData.setMqReceiveDateTime(now);
        ccDevicePurchaseDataMapper.updateConditionById(ccDevicePurchaseData);
    }

    /**
     * 新增设备采购进度数据
     * @param tmp mq获取的转换后数据
     * @param message 从mq获取的原始数据
     * @param now 当前时间
     * @param createBy 操作人
     */
    private void createDevice(DeviceMsg tmp, String message, String now, String createBy) {
        CcDevicePurchaseData ccDevicePurchaseData = new CcDevicePurchaseData();
        ccDevicePurchaseData.setId(IdUtil.getSnowflakeNextIdStr());
        ccDevicePurchaseData.setVer("1");
        ccDevicePurchaseData.setTs(now);
        ccDevicePurchaseData.setCreateBy(createBy);
        ccDevicePurchaseData.setLastUpdateBy(createBy);
        ccDevicePurchaseData.setCreateDate(now);
        ccDevicePurchaseData.setLastUpdateDate(now);
        ccDevicePurchaseData.setStatus("AP");
        ccDevicePurchaseData.setUnitProjectCode(tmp.getUnitProjectCode());
        ccDevicePurchaseData.setUnitProjectName(tmp.getUnitProjectName());
        ccDevicePurchaseData.setPurchaseDeviceNo(tmp.getDeviceNo());
        if (4 == tmp.getDeviceStatus()) {
            ccDevicePurchaseData.setDeviceInstallStatus(1);
        } else {
            ccDevicePurchaseData.setDeviceInstallStatus(0);
        }
        ccDevicePurchaseData.setDeviceTagNo(tmp.getDeviceTagNo());
        ccDevicePurchaseData.setMqMsgJson(message);
        ccDevicePurchaseData.setMqReceiveDateTime(now);
        ccDevicePurchaseDataMapper.create(ccDevicePurchaseData);
    }
}
