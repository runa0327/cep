package com.bid.ext.cc;

import com.bid.ext.model.AdUser;
import com.bid.ext.model.FlFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.tencentcloudapi.iai.v20200303.models.CreatePersonRequest;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.mpp.MPPReader;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.core.io.FileSystemResource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class PlanFileImportExt {


    public void planNodeAnalyzing(){

        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;

        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        String  wbsChiefUserId = (String) valueMap.get("WBS_CHIEF_USER_ID");
        String  status= (String) valueMap.get("STATUS");
        String  planFr = (String) valueMap.get("PLAN_FR");
        String  planTo = (String) valueMap.get("PLAN_to");
        String  pName= (String) valueMap.get("PLAN_to");
        String  wbsRiskId = (String) valueMap.get("CC_WBS_RISK_ID");
        String  progressStatusId = (String) valueMap.get("CC_WBS_PROGRESS_STATUS_ID");
        String  prjId = (String) valueMap.get("CC_PRJ_ID");
        String  nodePid = (String) valueMap.get("CC_PRJ_STRUCT_NODE_PID");
        BigDecimal  seqNo = (BigDecimal) valueMap.get("SEQ_NO");
        String  wbsTypeId = (String) valueMap.get("CC_PRJ_WBS_TYPE_ID");

        //获取上传的图片
        FlFile flFile = FlFile.selectById(varMap.get("P_ATTACHMENT").toString());
        String filePath = flFile.getPhysicalLocation();
        filePath = "/Users/hejialun/Downloads/05 协同创新区-四期房建（一标段）和协同创新区-城市共享核（一标段）两个EPC项目-协同创新区-四期房建5.14晚.mpp";

        File file = new File(filePath);

        MPPReader mppReader = new MPPReader();
        ProjectFile pf = null;
        try {
            pf = mppReader.read(file);
        } catch (MPXJException e) {
            e.printStackTrace();
            throw  new BaseException("mpp文件解析失败");
        }
        // 获取mpp文件数据集合
        List<Task> taskList = pf.getTasks(); //pf.getAllTasks();

        //根节点
        Task topTask = taskList.get(0);

        insertNode(topTask,nodePid,wbsTypeId,seqNo,prjId,wbsChiefUserId);

    }


    private   void   insertNode(Task task,String parentId,String typeId,BigDecimal serialNum,String  projectId,String wbsChiefUserId  ){


        if (task.getResourceAssignments().size() == 0){

            List<Task> childTasks = task.getChildTasks();

            System.out.println(task.getName()+"; start:"+task.getStart()+" end:"+task.getFinish()+" duration:"+task.getDuration());

            for (int i = 0; i < childTasks.size() ; i++) {

                Task t = childTasks.get(i);

                insertNode(task,parentId,typeId,new BigDecimal(i),projectId,wbsChiefUserId);
            }

        }else{
            List<ResourceAssignment> resourceAssignments = task.getResourceAssignments();

            for (ResourceAssignment ra :resourceAssignments){
                System.out.println(ra.toString());
            }
        }

    }


}
