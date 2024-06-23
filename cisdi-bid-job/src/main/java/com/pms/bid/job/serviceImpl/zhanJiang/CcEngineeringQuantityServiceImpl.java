package com.pms.bid.job.serviceImpl.zhanJiang;

import cn.hutool.core.util.IdUtil;
import com.pms.bid.job.domain.rocketmq.EngineeringMetrics;
import com.pms.bid.job.domain.zhanJiang.CcEngineeringQuantity;
import com.pms.bid.job.domain.zhanJiang.CcPrjStructNode;
import com.pms.bid.job.mapper.zhanJiang.CcEngineeringQuantityMapper;
import com.pms.bid.job.mapper.zhanJiang.CcPrjStructNodeMapper;
import com.pms.bid.job.service.zhanJiang.CcEngineeringQuantityService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CcEngineeringQuantityServiceImpl implements CcEngineeringQuantityService {

    @Resource
    private CcEngineeringQuantityMapper ccEngineeringQuantityMapper;

    @Resource
    private CcPrjStructNodeMapper ccPrjStructNodeMapper;

    /**
     * 通过rocketMQ获取消息写入工程量填报
     *
     * @param sourceList 数据详情
     */
    @Override
    public void dealRocketMQData(List<EngineeringMetrics> sourceList,String message, String now) {
        if (!CollectionUtils.isEmpty(sourceList)){
            //  获取单元工程名称编码信息
            List<CcPrjStructNode> unitProjectList = ccPrjStructNodeMapper.queryUnitProjectList();
            String createBy = "0099250247095871681";
            for (EngineeringMetrics tmp : sourceList) {
                //  单条数据处理并写入工程量填报
                dealAndInsertEngineeringQuantity(tmp,unitProjectList,message,now,createBy);
            }
        }
    }

    /**
     * 单条数据处理并写入工程量填报
     * @param tmp rocketmq数据
     * @param unitProjectList 单元工程集合
     */
    private void dealAndInsertEngineeringQuantity(EngineeringMetrics tmp, List<CcPrjStructNode> unitProjectList,String message, String now, String createBy) {
        List<CcEngineeringQuantity> list = new ArrayList<>();

        //  桩（m³）
        BigDecimal pile = tmp.getPile();
        if (pile != null) {
            getCcEngineeringQuantityAndAddList(pile,getPrjStructNodeIdByCode(tmp.getUnitProjectCode(),unitProjectList),"PILE","m³",list,now,createBy,message);
        }

        //  基础砼（m³）
        BigDecimal infrastructure = tmp.getInfrastructure();
        if (infrastructure != null){
            getCcEngineeringQuantityAndAddList(infrastructure,getPrjStructNodeIdByCode(tmp.getUnitProjectCode(),unitProjectList),"INFRASTRUCTURE","m³",list,now,createBy,message);
        }

        //  管道（t）
        BigDecimal pipeline = tmp.getPipeline();
        if (pipeline != null){
            getCcEngineeringQuantityAndAddList(pipeline,getPrjStructNodeIdByCode(tmp.getUnitProjectCode(),unitProjectList),"PIPELINE","t",list,now,createBy,message);
        }

        //  桥架（t）
        BigDecimal qj = tmp.getQj();
        if (qj != null){    //  桥架（t）
            getCcEngineeringQuantityAndAddList(qj,getPrjStructNodeIdByCode(tmp.getUnitProjectCode(),unitProjectList),"CABLETRAY","t",list,now,createBy,message);
        }

        //  钢结构（t）
        BigDecimal steelwork = tmp.getSteelwork();
        if (steelwork != null){
            getCcEngineeringQuantityAndAddList(steelwork,getPrjStructNodeIdByCode(tmp.getUnitProjectCode(),unitProjectList),"STEELSTRUCTURE","t",list,now,createBy,message);
        }

        //  上部结构砼（m³）
        BigDecimal superstructureConcrete = tmp.getSuperstructureConcrete();
        if (superstructureConcrete != null) {
            getCcEngineeringQuantityAndAddList(superstructureConcrete,getPrjStructNodeIdByCode(tmp.getUnitProjectCode(),unitProjectList),"SUPERSTRUCTURECONCRETE","t",list,now,createBy,message);
        }
        if (!CollectionUtils.isEmpty(list)){
            //  写入工程填报
            ccEngineeringQuantityMapper.inertBatch(list);
        }
    }

    /**
     * 提取获取工程量填报数据方法
     * @param pile 工程量类型
     * @param prjStructNodeId 单元工程id
     * @param engineeringQuantityTypeId 工程量代码id
     * @param uomTypeId 工程量单位
     * @param list 最后集合
     */
    private void getCcEngineeringQuantityAndAddList(BigDecimal pile, String prjStructNodeId, String engineeringQuantityTypeId, String uomTypeId, List<CcEngineeringQuantity> list, String now, String createBy,String message) {
        CcEngineeringQuantity ccEngineeringQuantity = normalNewCcEngineeringQuantity(now,createBy);
        ccEngineeringQuantity.setPrjStructNodeId(prjStructNodeId);
        ccEngineeringQuantity.setEngineeringQuantityTypeId(engineeringQuantityTypeId);
        ccEngineeringQuantity.setUomTypeId(uomTypeId);
        //  此处缺少填报类型
        ccEngineeringQuantity.setTotalWeight(pile);
        ccEngineeringQuantity.setMqMsgJson(message);
        ccEngineeringQuantity.setMqReceiveDateTime(now);
        ccEngineeringQuantity.setEngineeringTypeId("BID");
        list.add(ccEngineeringQuantity);
    }

    /**
     * 根据编码获取id 当前是通过遍历循环 后期可考虑即时查询
     * @param unitProjectCode 单元工程编码
     * @param unitProjectList 单元工程集合
     * @return
     */
    private String getPrjStructNodeIdByCode(String unitProjectCode,List<CcPrjStructNode> unitProjectList) {
        String id = null;
        if (!CollectionUtils.isEmpty(unitProjectList)) {
            for (CcPrjStructNode ccPrjStructNode : unitProjectList) {
                if (unitProjectCode.equals(ccPrjStructNode.getUnitProjectCode())){
                    id = ccPrjStructNode.getId();
                }
            }
        }
        return id;
    }

    /**
     * 实体通用赋值
     * @return 实体
     */
    private CcEngineeringQuantity normalNewCcEngineeringQuantity(String now, String createBy) {
        CcEngineeringQuantity ccEngineeringQuantity = new CcEngineeringQuantity();
        ccEngineeringQuantity.setId(IdUtil.getSnowflakeNextIdStr());
        ccEngineeringQuantity.setVer("1");
        ccEngineeringQuantity.setTs(now);
        ccEngineeringQuantity.setCreateBy(createBy);
        ccEngineeringQuantity.setLastUpdateBy(createBy);
        ccEngineeringQuantity.setCreateDate(now);
        ccEngineeringQuantity.setLastUpdateDate(now);
        ccEngineeringQuantity.setStatus("AP");
        ccEngineeringQuantity.setProjectId("1790672761571196928");  //  当前固定写死，后期在变化
        return ccEngineeringQuantity;
    }
}
