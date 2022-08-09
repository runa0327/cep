package com.cisdi.pms.mq;

import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.wf.callback.CallbackAtt;
import com.qygly.shared.wf.callback.CallbackInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class MqProcessor {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public void process(CallbackInfo callbackInfo) {
        log.info(callbackInfo.toString());

        // 看一下当前连的哪个数据库，别搞错了：
        String db = jdbcTemplate.queryForMap("select database() db").get("db").toString();
        log.info(db);

        // 放入map，便于查找：
        CallbackAttMap map = new CallbackAttMap();
        for (CallbackAtt callbackAtt : callbackInfo.attList) {
            map.put(callbackAtt.attCode, callbackAtt);
        }

        Map<String, String> procInstMap = callbackInfo.procInst;
        Map<String, String> currentNodeInstMap = callbackInfo.currentNodeInst;

        String entityRecordId = callbackInfo.procInst.get("ENTITY_RECORD_ID");
        String entCode = callbackInfo.procInst.get("ENT_CODE");


        try {
            //查询项目ID
            Map<String, Object> dataMap = jdbcTemplate.queryForMap("select * from " + entCode + " where id=?", entityRecordId);
            String projectId = String.valueOf(dataMap.get("PM_PRJ_ID"));

            //查询流程相关的进度计划节点
            List<Map<String, Object>> planNodeList = jdbcTemplate.queryForList("select pppn.* from pm_pro_plan_node pppn \n" +
                    "left join pm_pro_plan ppp on pppn.PM_PRO_PLAN_ID = ppp.id\n" +
                    "left join wf_node wn on pppn.LINKED_WF_NODE_ID = wn.id and pppn.LINKED_WF_PROCESS_ID = wn.WF_PROCESS_ID\n" +
                    "where ppp.PM_PRJ_ID=? and pppn.LINKED_WF_NODE_ID=? \n" +
                    "and pppn.LINKED_WF_PROCESS_ID =?", projectId, currentNodeInstMap.get("node_id"), procInstMap.get("WF_PROCESS_ID"));
            if (planNodeList.size() > 0) {
                List<Map<String, Object>> grSetValueList = jdbcTemplate.queryForList("select gsv.* from gr_set_value gsv " +
                        "left join gr_set gs on gs.id = gsv.gr_set_id where gs.`code`='PROGRESS_STATUS' and gsv.`code`='2'");
                String proStatus = "";
                if (grSetValueList.size() > 0) {
                    proStatus = String.valueOf(grSetValueList.get(0).get("ID"));
                }
                String procInstId = String.valueOf(procInstMap.get("ID"));
                String nodeInstId = String.valueOf(currentNodeInstMap.get("node_inst_id"));
                //修改进度计划节点状态和实际完成时间
                for (Map<String, Object> item : planNodeList) {
                    jdbcTemplate.update("update PM_PRO_PLAN_NODE set ACTUAL_COMPL_DATE=?,PROGRESS_STATUS_ID=?,LINKED_WF_PROCESS_INSTANCE_ID=?,LINKED_WF_NODE_INSTANCE_ID=? where ID=?",
                            new Date(), proStatus, procInstId, nodeInstId, item.get("ID"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }


    }


}
