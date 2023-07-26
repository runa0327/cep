package com.pms.cisdipmswordtopdf.serviceImpl;

import cn.hutool.core.util.IdUtil;
import com.pms.cisdipmswordtopdf.mapper.AdAttMapper;
import com.pms.cisdipmswordtopdf.mapper.BaseProcessMessageBakMapper;
import com.pms.cisdipmswordtopdf.model.BaseProcessMessageBak;
import com.pms.cisdipmswordtopdf.service.BaseProcessMessageBakService;
import com.pms.cisdipmswordtopdf.util.DateUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class BaseProcessMessageBakServiceImpl implements BaseProcessMessageBakService {

    @Resource
    private BaseProcessMessageBakMapper baseProcessMessageBakMapper;

    @Resource
    private AdAttMapper adAttMapper;

    /**
     * 查询所有列表数据
     * @param baseProcessMessageBak 入参条件
     */
    @Override
    public List<BaseProcessMessageBak> queryList(BaseProcessMessageBak baseProcessMessageBak) {
        List<BaseProcessMessageBak> list = baseProcessMessageBakMapper.queryList(baseProcessMessageBak);
        return list;
    }

    /**
     * 流程数据备份
     * @param oldMessage 原始信息
     * @param attCode 字段编码
     * @param tableCode 表名
     * @param processInstanceId 流程实例
     * @param processId 流程id
     */
    @Override
    public void insertBak(String oldMessage, String attCode, String tableCode, String processInstanceId, String processId) {

        String now = DateUtil.getNowDateStr(new Date());
        String adAttId = adAttMapper.getIdByCode(attCode);

        BaseProcessMessageBak baseProcessMessageBak = new BaseProcessMessageBak();
        baseProcessMessageBak.setId(IdUtil.getSnowflakeNextIdStr());
        baseProcessMessageBak.setVer("1");
        baseProcessMessageBak.setNowDate(now);
        baseProcessMessageBak.setCreateDate(now);
        baseProcessMessageBak.setCreateBy("0099250247095871681");
        baseProcessMessageBak.setLastUpdateBy("0099250247095871681");
        baseProcessMessageBak.setLastUpdateDate(now);
        baseProcessMessageBak.setStatus("AP");
        baseProcessMessageBak.setProcessId(processId);
        baseProcessMessageBak.setAdAttId(adAttId);
        baseProcessMessageBak.setAttValue(oldMessage);
        baseProcessMessageBak.setWfProcessInstanceId(processInstanceId);

        baseProcessMessageBakMapper.insert(baseProcessMessageBak);
    }
}
