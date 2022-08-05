package com.cisdi.ext.link;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.interaction.TypeValueText;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttLinkExt {

    public void attLink() {
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        AttLinkParam input = JsonUtil.fromJson(json, AttLinkParam.class);

        // 逻辑处理：
        AttLinkResult output = attLink(input);


        // 返回输出：
        // 转换为Map再设置到返回值；若直接将对象设置到返回值，调试时（通过MQ返回给平台）可能无法解析出相应的类：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(output), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private AttLinkResult attLink(AttLinkParam param) {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        String attCode = param.attCode;
        String attValue = param.attValue;
        if ("PROJECT_TYPE_ID".equals(attCode)) {
            AttLinkResult attLinkResult = new AttLinkResult();
            attLinkResult.attMap = new HashMap<>();

            List<Map<String, Object>> list = jdbcTemplate.queryForList("select v0.id project_type_id,v0.name project_type_name,v1.id CON_SCALE_TYPE_ID,v1.name CON_SCALE_TYPE_NAME,v2.id CON_SCALE_UOM_ID,v2.name CON_SCALE_UOM_NAME from pm_prj_type_link t join gr_set_value v0 on t.PROJECT_TYPE_ID=v0.id join gr_set_value v1 on t.CON_SCALE_TYPE_ID=v1.id join gr_set_value v2 on t.CON_SCALE_UOM_ID=v2.id where t.PROJECT_TYPE_ID=?", attValue);

            if (SharedUtil.isEmptyList(list)) {
                throw new BaseException("未设置相应项目类型的联动！");
            } else if (list.size() > 1) {
                throw new BaseException("重复设置相应项目类型的联动！");
            }

            Map row = list.get(0);

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_ID");
                typeValueText.text = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_NAME");

                attLinkResult.attMap.put("CON_SCALE_TYPE_ID", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "CON_SCALE_UOM_ID");
                typeValueText.text = JdbcMapUtil.getString(row, "CON_SCALE_UOM_NAME");

                attLinkResult.attMap.put("CON_SCALE_UOM_ID", typeValueText);
            }

            return attLinkResult;
        } else if ("PM_PRJ_ID".equals(attCode)) {
            AttLinkResult attLinkResult = new AttLinkResult();
            attLinkResult.attMap = new HashMap<>();

            //项目基础信息
            List<Map<String, Object>> list = jdbcTemplate
                    .queryForList("select t.code prj_code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name," +
                            "l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,st.id st_id,st.name st_name," +
                            "su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION, t.BUILD_YEARS," +
                            "t.PRJ_REPLY_NO, t.PRJ_REPLY_DATE, t.PRJ_REPLY_FILE, t.INVESTMENT_SOURCE_ID, " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST1 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'FS', " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST2 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'PD', " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST3 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'budget' " +
                            "from pm_prj t join PM_PARTY c on t.id=? and t.CUSTOMER_UNIT=c.id " +
                            "join gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                            "join gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                            "join gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                            "join gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                            "join gr_set_value su on t.CON_SCALE_UOM_ID=su.id", attValue);

            //查询项目可研/初概流程完成情况
            List<Map<String, Object>> list2 = jdbcTemplate.queryForList("SELECT * FROM (SELECT " +
                    "ifnull(b.END_DATETIME,0) as END_DATETIME, a.PRJ_TOTAL_INVEST, a.PROJECT_AMT, a.PROJECT_OTHER_AMT, a.PREPARE_AMT, a.CONSTRUCT_PERIOD_INTEREST, '1' as id " +
                    "FROM PM_PRJ_INVEST1 a " +
                    "LEFT JOIN wf_process_instance b on b.id = a.LK_WF_INST_ID " +
                    "WHERE a.PM_PRJ_ID = ? " +
                    "ORDER BY b.CRT_DT desc LIMIT 1) a union all select * FROM( " +
                    "SELECT ifnull(b.END_DATETIME,0) as END_DATETIME, a.PRJ_TOTAL_INVEST, a.PROJECT_AMT, a.PROJECT_OTHER_AMT, a.PREPARE_AMT, a.CONSTRUCT_PERIOD_INTEREST, '2' as id " +
                    "FROM PM_PRJ_INVEST2 a " +
                    "LEFT JOIN wf_process_instance b on b.id = a.LK_WF_INST_ID " +
                    "WHERE a.PM_PRJ_ID = ? " +
                    "ORDER BY b.CRT_DT desc LIMIT 1 ) b ORDER BY id desc", attValue,attValue);

            if (CollectionUtils.isEmpty(list)) {
                throw new BaseException("项目的相关属性不完整！");
            }

            Map row = list.get(0);
            if (!CollectionUtils.isEmpty(list2)){
                String date0 = "";
                String date1 = "";
                if (list2.size() == 2){
                    date0 = list2.get(0).get("END_DATETIME").toString();
                    date1 = list2.get(1).get("END_DATETIME").toString();
                    if ("0".equals(date0) || "0".equals(date1)){
                        if ("0".equals(date0)){
                            attLinkResult = getResult(list2.get(1));
                        } else {
                            attLinkResult = getResult(list2.get(0));
                        }
                    }
                } else {
                    date0 = list2.get(0).get("END_DATETIME").toString();
                    if (!"0".equals(date0)){
                        attLinkResult = getResult(list2.get(0));
                    }
                }

            }


            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_SHORT;
                typeValueText.value = JdbcMapUtil.getString(row, "prj_code");
                typeValueText.text = JdbcMapUtil.getString(row, "prj_code");

                attLinkResult.attMap.put("PRJ_CODE", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "customer_id");
                typeValueText.text = JdbcMapUtil.getString(row, "customer_name");

                attLinkResult.attMap.put("CUSTOMER_UNIT", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "m_id");
                typeValueText.text = JdbcMapUtil.getString(row, "m_name");

                attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "l_id");
                typeValueText.text = JdbcMapUtil.getString(row, "l_name");

                attLinkResult.attMap.put("BASE_LOCATION_ID", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row, "FLOOR_AREA");
                typeValueText.text = JdbcMapUtil.getString(row, "FLOOR_AREA");

                attLinkResult.attMap.put("FLOOR_AREA", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "pt_id");
                typeValueText.text = JdbcMapUtil.getString(row, "pt_name");

                attLinkResult.attMap.put("PROJECT_TYPE_ID", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "st_id");
                typeValueText.text = JdbcMapUtil.getString(row, "st_name");

                attLinkResult.attMap.put("CON_SCALE_TYPE_ID", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "su_id");
                typeValueText.text = JdbcMapUtil.getString(row, "su_name");

                attLinkResult.attMap.put("CON_SCALE_UOM_ID", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row, "CON_SCALE_QTY");
                typeValueText.text = JdbcMapUtil.getString(row, "CON_SCALE_QTY");

                attLinkResult.attMap.put("CON_SCALE_QTY", typeValueText);
            }


            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row, "CON_SCALE_QTY2");
                typeValueText.text = JdbcMapUtil.getString(row, "CON_SCALE_QTY2");

                attLinkResult.attMap.put("CON_SCALE_QTY2", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "BUILD_YEARS");
                typeValueText.text = JdbcMapUtil.getString(row, "BUILD_YEARS");
                attLinkResult.attMap.put("BUILD_YEARS", typeValueText);
            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "PRJ_SITUATION");
                typeValueText.text = JdbcMapUtil.getString(row, "PRJ_SITUATION");

                attLinkResult.attMap.put("PRJ_SITUATION", typeValueText);
            }
            //批复文号
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");
                typeValueText.text = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");

                attLinkResult.attMap.put("PRJ_REPLY_NO", typeValueText);
            }
            //批复日期
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DATE;
                typeValueText.value = JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");
                typeValueText.text = JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");

                attLinkResult.attMap.put("PRJ_REPLY_DATE", typeValueText);
            }
            //批复材料
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.FILE_GROUP;
                typeValueText.value = JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");
                typeValueText.text = JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");

                attLinkResult.attMap.put("PRJ_REPLY_FILE", typeValueText);
            }
            //资金来源
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
                typeValueText.text = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");

                attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", typeValueText);
            }
            //可研批复资金
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row, "FS");
                typeValueText.text = JdbcMapUtil.getString(row, "FS");

                attLinkResult.attMap.put("FEASIBILITY_APPROVE_FUND", typeValueText);
            }
            //初概批复资金
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row, "PD");
                typeValueText.text = JdbcMapUtil.getString(row, "PD");

                attLinkResult.attMap.put("ESTIMATE_APPROVE_FUND", typeValueText);
            }
            //财评批复资金
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row, "budget");
                typeValueText.text = JdbcMapUtil.getString(row, "budget");

                attLinkResult.attMap.put("EVALUATION_APPROVE_FUND", typeValueText);
            }
            return attLinkResult;
        } else if ("PMS_RELEASE_WAY_ID".equals(attCode) || "GUARANTEE_LETTER_TYPE_ID".equals(attCode) || "CONTRACT_CATEGORY_ID".equals(attCode)) {
            // 1.PMS_RELEASE_WAY_ID 招标类别下拉框
            // 2.GUARANTEE_LETTER_TYPE_ID 保函类别下拉框
            // 3.PMS_RELEASE_WAY_ID 项目类别下拉框
            AttLinkResult attLinkResult = new AttLinkResult();
            attLinkResult.attMap = new HashMap<>();

            List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT t.id, t.name FROM gr_set_value t WHERE t.id = ?", attValue);

            if (SharedUtil.isEmptyList(list)) {
                throw new BaseException("未设置相应项目类型的联动！");
            } else if (list.size() > 1) {
                throw new BaseException("重复设置相应项目类型的联动！");
            }

            Map row = list.get(0);

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "id");
                typeValueText.text = JdbcMapUtil.getString(row, "name");

                attLinkResult.attMap.put(attCode, typeValueText);
            }

            return attLinkResult;
        } else if("BIDDING_NAME_ID".equals(attCode)){
            AttLinkResult attLinkResult = new AttLinkResult();
            attLinkResult.attMap = new HashMap<>();
            //根据id查询招投标信息
            List<Map<String,Object>> list = jdbcTemplate.queryForList("SELECT a.PMS_RELEASE_WAY_ID,a.BID_CTL_PRICE_LAUNCH,a.APPROVE_PURCHASE_TYPE,a.WIN_BID_UNIT_TXT,a.TENDER_OFFER,a.CONTACT_MOBILE_WIN,a.CONTACT_NAME_RECORD,a.BID_USER_ID,a.STATUS,a.BID_UNIT, ifnull((SELECT END_DATETIME FROM wf_process_instance WHERE id = a.LK_WF_INST_ID ),0) as END_DATETIME, a.SERVICE_DAYS FROM po_public_bid_req a WHERE id = ?",attValue);
            if (CollectionUtils.isEmpty(list)){
                throw new BaseException("采购流程相关属性不完善！");
            }
            Map row = list.get(0);
            //招采类型
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"PMS_RELEASE_WAY_ID");
                typeValueText.text = JdbcMapUtil.getString(row,"PMS_RELEASE_WAY_ID");

                attLinkResult.attMap.put("PMS_RELEASE_WAY_ID", typeValueText);
            }
            //招标控制价
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row,"BID_CTL_PRICE_LAUNCH");
                typeValueText.text = JdbcMapUtil.getString(row,"BID_CTL_PRICE_LAUNCH");

                attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH", typeValueText);
            }
            //采购方式
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"APPROVE_PURCHASE_TYPE");
                typeValueText.text = JdbcMapUtil.getString(row,"APPROVE_PURCHASE_TYPE");

                attLinkResult.attMap.put("PURCHASE_TYPE", typeValueText);
            }
            //中标单位
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"WIN_BID_UNIT_TXT");
                typeValueText.text = JdbcMapUtil.getString(row,"WIN_BID_UNIT_TXT");

                attLinkResult.attMap.put("AUTHOR_UNIT", typeValueText);
            }
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"WIN_BID_UNIT_TXT");
                typeValueText.text = JdbcMapUtil.getString(row,"WIN_BID_UNIT_TXT");

                attLinkResult.attMap.put("WIN_BID_UNIT_TXT", typeValueText);
            }
            //中标单位报价
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"TENDER_OFFER");
                typeValueText.text = JdbcMapUtil.getString(row,"TENDER_OFFER");

                attLinkResult.attMap.put("ENTRUSTING_UNIT", typeValueText);
            }
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"TENDER_OFFER");
                typeValueText.text = JdbcMapUtil.getString(row,"TENDER_OFFER");

                attLinkResult.attMap.put("WINNING_BIDS_AMOUNT", typeValueText);
            }
            //进场时间
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DATE;
                typeValueText.value = JdbcMapUtil.getString(row,"END_DATETIME");
                typeValueText.text = JdbcMapUtil.getString(row,"END_DATETIME");

                attLinkResult.attMap.put("IN_DATE", typeValueText);
            }
            //招标单位
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"BID_UNIT");
                typeValueText.text = JdbcMapUtil.getString(row,"BID_UNIT");

                attLinkResult.attMap.put("ENTRUSTING_UNIT", typeValueText);
            }
            //审批状态
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"STATUS");
                typeValueText.text = JdbcMapUtil.getString(row,"STATUS");

                attLinkResult.attMap.put("APPROVAL_STATUS", typeValueText);
            }
            //经办人
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"BID_USER_ID");
                typeValueText.text = JdbcMapUtil.getString(row,"BID_USER_ID");

                attLinkResult.attMap.put("PROCURE_USER_ID", typeValueText);
            }
            //对方联系人
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"CONTACT_NAME_RECORD");
                typeValueText.text = JdbcMapUtil.getString(row,"CONTACT_NAME_RECORD");

                attLinkResult.attMap.put("OTHER_RESPONSOR", typeValueText);
            }
            //对方联系方式
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"CONTACT_MOBILE_WIN");
                typeValueText.text = JdbcMapUtil.getString(row,"CONTACT_MOBILE_WIN");

                attLinkResult.attMap.put("OTHER_CONTACT_PHONE", typeValueText);
            }
            //服务周期
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"SERVICE_DAYS");
                typeValueText.text = JdbcMapUtil.getString(row,"SERVICE_DAYS");

                attLinkResult.attMap.put("SERVICE_DAYS", typeValueText);
            }
            return attLinkResult;
        }else {
            throw new BaseException("属性联动的参数的attCode为" + attCode + "，不支持！");
        }
    }

    //获取资金信息
    private AttLinkResult getResult(Map<String, Object> stringObjectMap) {
        AttLinkResult attLinkResult = new AttLinkResult();
        attLinkResult.attMap = new HashMap<>();
        //总投资
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap,"PRJ_TOTAL_INVEST");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap,"PRJ_TOTAL_INVEST");

            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", typeValueText);
        }
        //工程费用
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap,"PROJECT_AMT");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap,"PROJECT_AMT");

            attLinkResult.attMap.put("PROJECT_AMT", typeValueText);
        }
        //工程建设其他费用
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap,"PROJECT_OTHER_AMT");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap,"PROJECT_OTHER_AMT");

            attLinkResult.attMap.put("PROJECT_OTHER_AMT", typeValueText);
        }
        //预备费
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap,"PREPARE_AMT");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap,"PREPARE_AMT");

            attLinkResult.attMap.put("PREPARE_AMT", typeValueText);
        }
        //利息
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap,"CONSTRUCT_PERIOD_INTEREST");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap,"CONSTRUCT_PERIOD_INTEREST");

            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", typeValueText);
        }
        return attLinkResult;
    }



}
