package com.cisdi.ext.pm;

import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.shared.BaseException;

import java.util.List;
import java.util.Map;

/**
 * @author dlt
 * @date 2023/3/9 周四
 * 进度节点问题
 */
public class NodeProblemExt {
    /**
     * 新增/编辑 问题
     */
    public void addProblems(){
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        Req req = JSONObject.parseObject(JSONObject.toJSONString(inputMap), Req.class);
        String userId = ExtJarHelper.loginInfo.get().userId;
        if (req.reqs == null){
            return;
        }
        for (ProblemReq problemReq : req.reqs) {
            String problemId = "";
            if (Strings.isNullOrEmpty(problemReq.id)){//没有问题id，新增
                 problemId = Crud.from("NODE_PROBLEM").insertData();
            }else {
                problemId = problemReq.id;
            }

            Crud.from("NODE_PROBLEM").where().eq("ID",problemId).update()
                    .set("PM_PRO_PLAN_NODE_ID",problemReq.nodeId)
                    .set("SOLUTION",problemReq.solution)
                    .set("PROBLEM_DESCRIBE",problemReq.describe)
                    .set("IS_SOLVE",problemReq.isSolve)
                    .set("AD_USER_ID",userId)
                    .set("PROPOSAL_DATE",problemReq.proposalDate)
                    .exec();
        }
    }

    /**
     * 删除
     */
    public void delProblems(){
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        DelReq delReq = JSONObject.parseObject(JSONObject.toJSONString(inputMap), DelReq.class);
        if (delReq.ids == null){
            return;
        }
        for (String id : delReq.ids) {
            Crud.from("NODE_PROBLEM").where().eq("ID",id).delete().exec();
        }
    }

    public static class ProblemReq{
        //问题id
        public String id;
        //节点id
        public String nodeId;
        //描述
        public String describe;
        //解决方案
        public String solution;
        //是否解决
        public boolean isSolve;
        //提出人
        public String introducerId;
        //提出日期
        public String proposalDate;
    }

    public static class Req{
        public List<ProblemReq> reqs;
    }

    public static class DelReq{
        public List<String> ids;
    }
}
