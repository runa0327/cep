package com.bid.ext.cc;

import com.bid.ext.model.CcPo;
import com.bid.ext.model.CcPoEngineering;
import com.bid.ext.model.CcPoEngineeringType;
import com.bid.ext.model.CcPrjStructNode;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;

import java.util.ArrayList;
import java.util.List;

public class BidExt {

    /**
     * 创建合同工程量分类
     */
    public void creatPoEngineeringType() {
        for (EntityRecord entityRecord : ExtJarHelper.getEntityRecordList()) {
            String csCommId = entityRecord.csCommId;
            CcPo ccPo = CcPo.selectById(csCommId);
            String ccPrjId = ccPo.getCcPrjId();

            String[] engineeringType = {"PILE", "FOUNDATION", "STEELSTRUCTURE", "PIPELINE", "CABLETRAY"};
            List<CcPrjStructNode> ccPrjStructNodes = CcPrjStructNode.selectByWhere(new Where().eq(CcPrjStructNode.Cols.IS_TEMPLATE, 0).eq(CcPrjStructNode.Cols.IS_PBS, 1).eq(CcPrjStructNode.Cols.CC_PRJ_ID, ccPrjId).neq(CcPrjStructNode.Cols.CC_PRJ_STRUCT_NODE_PID, null));
            List<String> structNodeIds = new ArrayList<>();
            for (CcPrjStructNode ccPrjStructNode : ccPrjStructNodes) {
                structNodeIds.add(ccPrjStructNode.getId());

            }

            for (String type : engineeringType) {
                CcPoEngineeringType ccPoEngineeringType = CcPoEngineeringType.newData();
                ccPoEngineeringType.setCcPrjId(ccPrjId);
                ccPoEngineeringType.setCcPoId(csCommId);
                ccPoEngineeringType.setCcEngineeringQuantityTypeId(type);
                ccPoEngineeringType.insertById();

                for (String structNodeId : structNodeIds) {
                    CcPoEngineering ccPoEngineering = CcPoEngineering.newData();
                    ccPoEngineering.setCcEngineeringQuantityTypeId(ccPoEngineeringType.getId());
                    ccPoEngineering.setCcPrjId(ccPrjId);
                    ccPoEngineering.setCcPoId(csCommId);
                    ccPoEngineering.setCcEngineeringQuantityTypeId(type);
                    ccPoEngineering.setCcPrjStructNodeId(structNodeId);
                    ccPoEngineering.insertById();
                }
            }
        }
    }

}
