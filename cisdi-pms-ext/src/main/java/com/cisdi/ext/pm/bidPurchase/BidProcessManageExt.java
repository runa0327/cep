package com.cisdi.ext.pm.bidPurchase;

import com.cisdi.ext.api.PoPublicBidExtApi;
import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.BidProcessManage;
import com.cisdi.ext.pm.bidPurchase.detail.BidProcessManageDetailExt;
import com.cisdi.ext.wf.WfExt;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 招标采购-招标过程管理-扩展
 */
public class BidProcessManageExt {

    /**
     * 招标过程管理-流程完结时扩展
     */
    public void manageProcessEnd(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        //项目id
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_IDS");
        //写入招标台账数据表
        PoPublicBidExtApi.createData(entityRecord,"1630383237438242816",myJdbcTemplate);
        //项目信息写入明细表
        BidProcessManageDetailExt.insertData(entityRecord.csCommId,projectId);
    }

    /**
     * 招标过程管理-历史已完成数据归档(写入招标台账表)
     */
    public void bidProcessHistoryDataCollect(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        //查询所有已经走完的合同
        String sql = "select * from BID_PROCESS_MANAGE where status = 'ap'";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql);
        if (!CollectionUtils.isEmpty(list)){
            for (Map<String, Object> tmp : list) {
                PoPublicBidExtApi.createHistoryData(tmp,"1630383237438242816",myJdbcTemplate);
            }
        }
    }

    /**
     * 流程操作-招标过程管理-确定按钮
     */
    public void bidManageProcessOK(){
        String status = "OK";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程操作-招标过程管理-拒绝按钮
     */
    public void bidManageProcessRefuse(){
        String status = "refuse";
        String nodeId = ExtJarHelper.nodeId.get();
        String nodeStatus = getNodeStatus(status,nodeId);
        processHandle(nodeStatus,status);
    }

    /**
     * 流程流转详细处理逻辑
     * @param nodeStatus 节点状态码
     * @param status 操作状态码
     */
    public void processHandle(String nodeStatus, String status) {
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        if ("OK".equals(status)){
            if ("start".equals(nodeStatus)){ // 1-发起
                WfExt.createProcessTitle(entCode,entityRecord,myJdbcTemplate);
            }
        }
    }

    /**
     * 各审批节点赋值
     * @param status 状态码
     * @param nodeId 节点id
     * @return 节点状态名称
     */
    private String getNodeStatus(String status, String nodeId) {
        String nodeName = "";
        if ("OK".equals(status)){
            if ("1605380525390962688".equals(nodeId)){ // 1-发起
                nodeName = "start";
            }
        }
        return nodeName;
    }


    /**
     * 历史数据处理
     */
    public void manageHistoryData(){

        // 非系统项目转系统项目
        List<BidProcessManage> list1 = BidProcessManage.selectByWhere(new Where().nin(BidProcessManage.Cols.STATUS,"VD","VDING").eq(BidProcessManage.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441375"));
        if (!CollectionUtils.isEmpty(list1)){
            for (BidProcessManage tmp : list1) {
                String projectName = tmp.getProjectNameWr();
                String projectId = PmPrjExt.createPrjByMoreName(projectName);
                String id = tmp.getId();
                Crud.from(BidProcessManage.ENT_CODE).where().eq(BidProcessManage.Cols.ID,id).update()
                        .set(BidProcessManage.Cols.PM_PRJ_IDS,projectId)
                        .exec();
            }
        }

        //系统项目id写入pm_prj_ids
        List<BidProcessManage> list2 = BidProcessManage.selectByWhere(new Where().nin(BidProcessManage.Cols.STATUS,"VD","VDING").eq(BidProcessManage.Cols.PROJECT_SOURCE_TYPE_ID,"0099952822476441374"));
        if (!CollectionUtils.isEmpty(list2)){
            for (BidProcessManage tp : list2) {
                String id = tp.getId();
                String projectId = tp.getPmPrjId();
                Crud.from(BidProcessManage.ENT_CODE).where().eq(BidProcessManage.Cols.ID,id).update()
                        .set(BidProcessManage.Cols.PM_PRJ_IDS,projectId)
                        .exec();
            }
        }

        //已批准流程项目id写入明细表
        List<BidProcessManage> list3 = BidProcessManage.selectByWhere(new Where().eq(BidProcessManage.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list3)){
            for (BidProcessManage tmp : list3) {
                String id = tmp.getId();
                String projectId = tmp.getPmPrjIds();
                BidProcessManageDetailExt.insertData(id,projectId);
            }
        }
    }


}
