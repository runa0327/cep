package com.bid.ext.cc;

import com.bid.ext.model.CcPrjStructNode;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.util.I18nUtil;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.interaction.InvokeActResult;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;

import java.io.File;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class WBSPlanFileImportExt {


    public void planNodeAnalyzing(){

        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;

        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String  wbsChiefUserId = (String) valueMap.get("WBS_CHIEF_USER_ID");
//        String  status= (String) valueMap.get("STATUS");
//        String  planFr = (String) valueMap.get("PLAN_FR");
//        String  planTo = (String) valueMap.get("PLAN_to");
//        String  pName= (String) valueMap.get("PLAN_to");
//        String  wbsRiskId = (String) valueMap.get("CC_WBS_RISK_ID");
//        String  progressStatusId = (String) valueMap.get("CC_WBS_PROGRESS_STATUS_ID");
        String  prjId = (String) valueMap.get("CC_PRJ_ID");
//        String  nodePid = (String) valueMap.get("CC_PRJ_STRUCT_NODE_PID");
        String  nodePid = (String) valueMap.get("ID"); //选中节点的id
        BigDecimal  seqNo = (BigDecimal) valueMap.get("SEQ_NO");
        String  wbsTypeId = (String) valueMap.get("CC_PRJ_WBS_TYPE_ID");

        if(nodePid != null && nodePid.isEmpty()){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.WBSPlan.import.nodeNotSelected");
            throw new BaseException(msg);
//           throw new BaseException("未选中节点");
        }
        //获取上传的图片
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();

        if (!"mpp".equals(flFile.getExt())){
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.WBSPlan.import.fileTypeError");
            throw new BaseException(msg);
//            throw new BaseException("非mpp文件格式");
        }

//        filePath = "C:\\Users\\xx\\Desktop\\test-import.mpp";

        File file = new File(filePath);

        MPPReader mppReader = new MPPReader();
        ProjectFile pf = null;
        try {
            pf = mppReader.read(file);
        } catch (MPXJException e) {
            e.printStackTrace();
            String msg = I18nUtil.buildAppI18nMessageInCurrentLang("qygly.backEnd.ext.WBSPlan.import.fileAnalyze");
            throw new BaseException(msg);
//            throw  new BaseException("mpp文件解析失败");
        }
        // 获取mpp文件数据集合
        List<Task> taskList = pf.getTasks(); //pf.getAllTasks();

        //根节点
        Task topTask = taskList.get(0);

        insertNode(topTask,nodePid,wbsTypeId,seqNo,prjId,wbsChiefUserId,0);

        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.reFetchData = true;
        ExtJarHelper.setReturnValue(invokeActResult);


    }

    //节点插入，根节点不插入
    private   void   insertNode(Task task,String parentId,String typeId,BigDecimal serialNum,String  projectId,String wbsChiefUserId ,Integer level ){
        level++;

        //判断是否为顶层叶子节点
        if (task.getResourceAssignments().size() == 0){

            List<Task> childTasks = task.getChildTasks();


            for (int i = 0; i < childTasks.size() ; i++) {

                Task t = childTasks.get(i);

                CcPrjStructNode ccPrjStructNode = CcPrjStructNode.newData();
                ccPrjStructNode.setStatus("DR");
                ccPrjStructNode.setCcPrjId(projectId);
                ccPrjStructNode.setCcPrjStructNodePid(parentId);
                ccPrjStructNode.setName(t.getName());
                ccPrjStructNode.setSeqNo(serialNum);
                serialNum = serialNum.add(new BigDecimal(1));
                ccPrjStructNode.setCcPrjWbsTypeId(typeId);
                ccPrjStructNode.setWbsChiefUserId(wbsChiefUserId);
                ccPrjStructNode.setPlanFr(t.getStart().toLocalDate());
                ccPrjStructNode.setPlanTo(t.getFinish().toLocalDate());
                ccPrjStructNode.setIsTemplate(false);
                ccPrjStructNode.setIsWbs(true);

                ccPrjStructNode.setPlanTotalCost(null);
                ccPrjStructNode.setActTotalCost(null);
                ccPrjStructNode.setPlanUnitCost(null);
                ccPrjStructNode.setActUnitCost(null);
                ccPrjStructNode.setCbsChiefUserId(null);
                ccPrjStructNode.setPbsChiefUserId(null);
                ccPrjStructNode.insertById();

                insertNode(t,ccPrjStructNode.getId(),typeId,new BigDecimal(0),projectId,wbsChiefUserId,level);

            }

        }

    }


}
