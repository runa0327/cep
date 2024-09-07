package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.rocketmq.DeviceMsg;
import com.pms.bid.job.domain.zhanJiang.CcDevicePurchaseData;
import com.pms.bid.job.mapper.zhanJiang.CcDevicePurchaseDataMapper;
import com.pms.bid.job.mapper.zhanJiang.CcIotEquipMapper;
import com.pms.bid.job.mapper.zhanJiang.CcPrjStructNodeMapper;
import com.pms.bid.job.service.zhanJiang.CcDevicePurchaseDataService;
import com.pms.bid.job.service.zhanJiang.CcIotEquipService;
import com.pms.bid.job.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class CcIotEquipServiceImpl implements CcIotEquipService {

    @Resource
    private CcIotEquipMapper iotEquipMapper;

    @Override
    public void checkOnlineStatus() {

        int i = iotEquipMapper.checkAndUpdateOnlineStatus();
        log.info("更新物联网设备状态数量："+i);
    }
}
