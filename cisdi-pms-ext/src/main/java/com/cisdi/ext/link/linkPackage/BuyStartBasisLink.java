package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.base.GrSetValueExt;
import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.link.AttLinkParam;
import com.cisdi.ext.link.AttLinkResult;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

public class BuyStartBasisLink {

    /**
     * 采购启动依据属性联动
     * @param myJdbcTemplate 数据源
     * @param attValue 属性值
     * @param entCode 业务表名
     * @param param 参数
     * @return 属性联动结果
     */
    public static AttLinkResult linkBUY_START_BASIS_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        String code = GrSetValueExt.getGrSetCode(attValue);
        if ("PM_BUY_DEMAND_REQ".equals(entCode)){ //采购需求审批
            //会议纪要(meeting_minutes)，其他(other),启动函(start_up_letter)
            boolean changeToEditable = false; //是否可改
            String value = "";
            String text = "";
            String projectId = JdbcMapUtil.getString(param.valueMap, "PM_PRJ_IDS");
            if (!StringUtils.hasText(projectId)){
                throw new BaseException("项目信息不能为空，请先选择项目名称！");
            }
            if (projectId.contains(",")){
                return attLinkResult;
            }
            // 判断是否是设备项目
            projectId = PmPrjExt.getFatherProject(projectId);
            if ("meeting_minutes".equals(code) || "other".equals(code) || "start_up_letter".equals(code)){
                changeToEditable = true;
                value = null;
                text = null;
                //采购启动依据文件
                LinkUtils.mapAddValueByValueFile("FILE_ID_THREE",null,null,true,false,true,AttDataTypeE.FILE_GROUP,attLinkResult);
            } else {
                // 立项(project_initiation)，可研(feasibility_study)，初概(preliminary_outline),预算财评(budget_financial_evaluation)
                String sql = "";
                String fileValue = null;
                if ("project_initiation".equals(code)){
                    sql = "select PRJ_REPLY_NO as REPLY_NO_WR,REPLY_FILE as file from pm_prj_req WHERE pm_prj_id = ? and `STATUS` = 'ap' order by CRT_DT desc limit 1";
                } else if ("feasibility_study".equals(code)){
                    sql = "select REPLY_NO_WR,REPLY_FILE as file from PM_PRJ_INVEST1 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
                } else if ("preliminary_outline".equals(code)){
                    sql = "select REPLY_NO_WR,REPLY_FILE as file from PM_PRJ_INVEST2 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
                } else if ("budget_financial_evaluation".equals(code)){
                    sql = "select REPLY_NO_WR,FINANCIAL_REVIEW_FILE as file from PM_PRJ_INVEST3 WHERE pm_prj_id = ? and status = 'ap' order by CRT_DT desc limit 1";
                }
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql,projectId);
                if (!CollectionUtils.isEmpty(list2)){
                    value = JdbcMapUtil.getString(list2.get(0),"REPLY_NO_WR");
                    text = value;
                    fileValue = JdbcMapUtil.getString(list2.get(0),"file");
                }
                //采购启动依据文件
                LinkUtils.mapAddValueByValueFile("FILE_ID_THREE",null,fileValue,true,false,true,AttDataTypeE.FILE_GROUP,attLinkResult);
            }
            //启动依据文号
            LinkUtils.mapAddAllValue("REPLY_NO_WR",AttDataTypeE.TEXT_LONG,value,text,true,false,changeToEditable,attLinkResult);
        }
        return attLinkResult;
    }
}
