package com.cisdi.ext.link;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.interaction.TypeValueText;
import com.qygly.shared.util.BooleanUtil;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

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
        String sevId = param.sevId;

        // 获取实体代码（表名）：
        String entCode = jdbcTemplate.queryForMap("select e.code ent_code from ad_single_ent_view sev join ad_ent e on sev.AD_ENT_ID = e.ID and sev.id=?", sevId).get("ent_code").toString();

        AttLinkResult attLinkResult = new AttLinkResult();
        attLinkResult.attMap = new HashMap<>();
        if ("PROJECT_TYPE_ID".equals(attCode)) {

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

            //项目基础信息
            List<Map<String, Object>> list = jdbcTemplate
                    .queryForList("select t.code prj_code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name," +
                            "l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,st.id st_id,st.name st_name," +
                            "su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION, t.BUILD_YEARS," +
                            "t.PRJ_REPLY_NO, t.PRJ_REPLY_DATE, t.PRJ_REPLY_FILE, t.INVESTMENT_SOURCE_ID,t.BUILDING_AREA, " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST1 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'FS', " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST2 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'PD', " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST3 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'budget' " +
                            "from pm_prj t join PM_PARTY c on t.id=? and t.CUSTOMER_UNIT=c.id " +
                            "join gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                            "join gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                            "join gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                            "join gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                            "join gr_set_value su on t.CON_SCALE_UOM_ID=su.id", attValue);


            if (CollectionUtils.isEmpty(list)) {
                throw new BaseException("项目的相关属性不完整！");
            }

            Map row = list.get(0);

            if ("PO_PUBLIC_BID_REQ".equals(entCode) || "PO_ORDER_REQ".equals(entCode)){
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
                        "ORDER BY b.CRT_DT desc LIMIT 1 ) b ORDER BY id desc", attValue, attValue);

                if (!CollectionUtils.isEmpty(list2)) {
                    String date0 = "";
                    String date1 = "";
                    if (list2.size() == 2) {
                        date0 = list2.get(0).get("END_DATETIME").toString();
                        date1 = list2.get(1).get("END_DATETIME").toString();
                        if ("0".equals(date0) || "0".equals(date1)) {
                            if ("0".equals(date0)) {
                                attLinkResult = getResult(list2.get(1));
                            } else {
                                attLinkResult = getResult(list2.get(0));
                            }
                        }
                    } else {
                        date0 = list2.get(0).get("END_DATETIME").toString();
                        if (!"0".equals(date0)) {
                            attLinkResult = getResult(list2.get(0));
                        }
                    }

                }
            }
            List<String> contractTables = Arrays.asList("PO_ORDER_SUPPLEMENT_REQ", "PO_ORDER_CHANGE_REQ",
                    "PO_ORDER_TERMINATE_REQ");
            if (contractTables.contains(entCode)){
                List<Map<String, Object>> orderLists = jdbcTemplate.queryForList("SELECT * FROM (SELECT " +
                        "ifnull(b.END_DATETIME,0) as END_DATETIME, a.PRJ_TOTAL_INVEST, a.PROJECT_AMT, a.PROJECT_OTHER_AMT, a.PREPARE_AMT, a.CONSTRUCT_PERIOD_INTEREST, '1' as id " +
                        "FROM PM_PRJ_INVEST1 a " +
                        "LEFT JOIN wf_process_instance b on b.id = a.LK_WF_INST_ID " +
                        "WHERE a.PM_PRJ_ID = ? " +
                        "ORDER BY b.CRT_DT desc LIMIT 1) a union all select * FROM( " +
                        "SELECT ifnull(b.END_DATETIME,0) as END_DATETIME, a.PRJ_TOTAL_INVEST, a.PROJECT_AMT, a.PROJECT_OTHER_AMT, a.PREPARE_AMT, a.CONSTRUCT_PERIOD_INTEREST, '2' as id " +
                        "FROM PM_PRJ_INVEST2 a " +
                        "LEFT JOIN wf_process_instance b on b.id = a.LK_WF_INST_ID " +
                        "WHERE a.PM_PRJ_ID = ? " +
                        "ORDER BY b.CRT_DT desc LIMIT 1 ) b UNION ALL SELECT * FROM (SELECT " +
                        "ifnull( b.END_DATETIME, 0 ) AS END_DATETIME,a.PRJ_TOTAL_INVEST,a.PROJECT_AMT,a.PROJECT_OTHER_AMT,a.PREPARE_AMT,a.CONSTRUCT_PERIOD_INTEREST,'3' AS id " +
                        "FROM PM_PRJ_INVEST3 a " +
                        "LEFT JOIN wf_process_instance b ON b.id = a.LK_WF_INST_ID " +
                        "WHERE a.PM_PRJ_ID = ? " +
                        "ORDER BY b.CRT_DT DESC LIMIT 1 ) c " +
                        "ORDER BY id desc", attValue, attValue, attValue);

                if (!CollectionUtils.isEmpty(orderLists)){
                    //取最大的id
                    List<Map<String, Object>> newOrderList = orderLists.stream().sorted(Comparator.comparing(x -> x.get("id").toString())).collect(Collectors.toList());
                    for (int i = newOrderList.size()-1; i >=0; i--) {
                        if (!"0".equals(newOrderList.get(i).get("END_DATETIME"))){
                            attLinkResult = getResult(newOrderList.get(i));
                            break;
                        }
                    }
                }
            }


            if ("PM_PRJ_KICK_OFF_REQ".equals(entCode)){ //工程开工报审
                String sql = "select PRJ_TOTAL_INVEST,PROJECT_AMT from PM_PRJ_INVEST2 where PM_PRJ_ID = ? and STATUS = 'AP' order by CRT_DT desc limit 1";
                List<Map<String,Object>> map = jdbcTemplate.queryForList(sql,attValue);
                if (CollectionUtils.isEmpty(map)){
                    throw new BaseException("该项目的总投资、工程费用信息不完善！");
                }
                Map row2 = map.get(0);
                //总投资
                {
                    TypeValueText typeValueText = new TypeValueText();
                    typeValueText.type = AttDataTypeE.DOUBLE;
                    typeValueText.value = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");
                    typeValueText.text = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");

                    attLinkResult.attMap.put("PRJ_TOTAL_INVEST", typeValueText);
                }
                //工程费用
                {
                    TypeValueText typeValueText = new TypeValueText();
                    typeValueText.type = AttDataTypeE.DOUBLE;
                    typeValueText.value = JdbcMapUtil.getString(row2, "PROJECT_AMT");
                    typeValueText.text = JdbcMapUtil.getString(row2, "PROJECT_AMT");

                    attLinkResult.attMap.put("PROJECT_AMT", typeValueText);
                }

            }

            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_SHORT;
                typeValueText.value = JdbcMapUtil.getString(row, "prj_code");
                typeValueText.text = JdbcMapUtil.getString(row, "prj_code");

                attLinkResult.attMap.put("PRJ_CODE", typeValueText);
            }
            //建筑面积
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row,"BUILDING_AREA");
                typeValueText.text = JdbcMapUtil.getString(row,"BUILDING_AREA");
                attLinkResult.attMap.put("BUILDING_AREA",typeValueText);
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
        } else if ("PMS_RELEASE_WAY_ID".equals(attCode) || "GUARANTEE_LETTER_TYPE_ID".equals(attCode) || "CONTRACT_CATEGORY_ID".equals(attCode) || "PRJ_MANAGE_MODE_ID".equals(attCode)) {
            // 1.PMS_RELEASE_WAY_ID 招标类别下拉框
            // 2.GUARANTEE_LETTER_TYPE_ID 保函类别下拉框
            // 3.PMS_RELEASE_WAY_ID 项目类别下拉框

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
        } else if ("BIDDING_NAME_ID".equals(attCode)) {
            //根据id查询招投标信息
            List<Map<String, Object>> list = jdbcTemplate.queryForList("SELECT a.PMS_RELEASE_WAY_ID,a.BID_CTL_PRICE_LAUNCH,a.APPROVE_PURCHASE_TYPE,a.WIN_BID_UNIT_TXT,a.TENDER_OFFER,a.CONTACT_MOBILE_WIN,a.CONTACT_NAME_RECORD,a.BID_USER_ID,a.STATUS,a.BID_UNIT, ifnull((SELECT END_DATETIME FROM wf_process_instance WHERE id = a.LK_WF_INST_ID ),0) as END_DATETIME, a.SERVICE_DAYS FROM po_public_bid_req a WHERE id = ?", attValue);
            if (CollectionUtils.isEmpty(list)) {
                throw new BaseException("采购流程相关属性不完善！");
            }
            Map row = list.get(0);
            //招采类型
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");
                typeValueText.text = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");

                attLinkResult.attMap.put("PMS_RELEASE_WAY_ID", typeValueText);
            }
            //招标控制价
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
                typeValueText.text = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");

                attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH", typeValueText);
            }
            //采购方式
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "APPROVE_PURCHASE_TYPE");
                typeValueText.text = JdbcMapUtil.getString(row, "APPROVE_PURCHASE_TYPE");

                attLinkResult.attMap.put("PURCHASE_TYPE", typeValueText);
            }
            //中标单位
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
                typeValueText.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");

                attLinkResult.attMap.put("AUTHOR_UNIT", typeValueText);
                attLinkResult.attMap.put("AUTHOR_UNIT_TEXT", typeValueText);
                attLinkResult.attMap.put("WIN_BID_UNIT_TXT", typeValueText);
            }
            //中标单位报价
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "TENDER_OFFER");
                typeValueText.text = JdbcMapUtil.getString(row, "TENDER_OFFER");

                attLinkResult.attMap.put("ENTRUSTING_UNIT", typeValueText);
                attLinkResult.attMap.put("WINNING_BIDS_AMOUNT", typeValueText);
            }
            //进场时间
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DATE;
                typeValueText.value = JdbcMapUtil.getString(row, "END_DATETIME");
                typeValueText.text = JdbcMapUtil.getString(row, "END_DATETIME");

                attLinkResult.attMap.put("IN_DATE", typeValueText);
            }
            //招标单位
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "BID_UNIT");
                typeValueText.text = JdbcMapUtil.getString(row, "BID_UNIT");

                attLinkResult.attMap.put("ENTRUSTING_UNIT", typeValueText);
                attLinkResult.attMap.put("ENTRUSTING_UNIT_TEXT", typeValueText);
            }
            //审批状态
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "STATUS");
                typeValueText.text = JdbcMapUtil.getString(row, "STATUS");

                attLinkResult.attMap.put("APPROVAL_STATUS", typeValueText);
            }
            //经办人
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "BID_USER_ID");
                typeValueText.text = JdbcMapUtil.getString(row, "BID_USER_ID");

                attLinkResult.attMap.put("PROCURE_USER_ID", typeValueText);
            }
            //对方联系人
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "CONTACT_NAME_RECORD");
                typeValueText.text = JdbcMapUtil.getString(row, "CONTACT_NAME_RECORD");

                attLinkResult.attMap.put("OTHER_RESPONSOR", typeValueText);
            }
            //对方联系方式
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "CONTACT_MOBILE_WIN");
                typeValueText.text = JdbcMapUtil.getString(row, "CONTACT_MOBILE_WIN");

                attLinkResult.attMap.put("OTHER_CONTACT_PHONE", typeValueText);
            }
            //服务周期
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "SERVICE_DAYS");
                typeValueText.text = JdbcMapUtil.getString(row, "SERVICE_DAYS");

                attLinkResult.attMap.put("SERVICE_DAYS", typeValueText);
            }
            return attLinkResult;
        }else if ("CONTRACT_ID".equals(attCode)){
            //根据id查询招投标信息
            List<Map<String, Object>> list = jdbcTemplate.queryForList("select CONTRACT_CODE, CONTRACT_CATEGORY_ID, CONTRACT_NAME, CONTRACT_PRICE, " +
                    "BIDDING_NAME_ID,PMS_RELEASE_WAY_ID,BID_CTL_PRICE_LAUNCH,PURCHASE_TYPE,WIN_BID_UNIT_TXT,WINNING_BIDS_AMOUNT,PLAN_TOTAL_DAYS,"+
                    "IS_REFER_GUARANTEE,GUARANTEE_LETTER_TYPE_ID,OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT,IS_TEMPLATE "+
                    "from po_order_req where id = ?", attValue);

            if (CollectionUtils.isEmpty(list)) {
                throw new BaseException("合同签订相关属性不完善！");
            }
            Map row = list.get(0);
            //合同编号
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"CONTRACT_CODE");
                typeValueText.text = JdbcMapUtil.getString(row,"CONTRACT_CODE");
                attLinkResult.attMap.put("CONTRACT_CODE",typeValueText);
            }
            //合同类型
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"CONTRACT_CATEGORY_ID");
                typeValueText.text = JdbcMapUtil.getString(row,"CONTRACT_CATEGORY_ID");
                attLinkResult.attMap.put("CONTRACT_CATEGORY_ID",typeValueText);
            }
            //关联招采
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"BIDDING_NAME_ID");
                typeValueText.text = JdbcMapUtil.getString(row,"BIDDING_NAME_ID");
                attLinkResult.attMap.put("BIDDING_NAME_ID",typeValueText);
            }
            //招标类别
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"PMS_RELEASE_WAY_ID");
                typeValueText.text = JdbcMapUtil.getString(row,"PMS_RELEASE_WAY_ID");
                attLinkResult.attMap.put("PMS_RELEASE_WAY_ID",typeValueText);
            }
            //招标控制价
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"BID_CTL_PRICE_LAUNCH");
                typeValueText.text = JdbcMapUtil.getString(row,"BID_CTL_PRICE_LAUNCH");
                attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH",typeValueText);
            }
            //采购方式
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"PURCHASE_TYPE");
                typeValueText.text = JdbcMapUtil.getString(row,"PURCHASE_TYPE");
                attLinkResult.attMap.put("PURCHASE_TYPE",typeValueText);
            }
            //中标单位
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"WIN_BID_UNIT_TXT");
                typeValueText.text = JdbcMapUtil.getString(row,"WIN_BID_UNIT_TXT");
                attLinkResult.attMap.put("WIN_BID_UNIT_TXT",typeValueText);
            }
            //中标价
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"WINNING_BIDS_AMOUNT");
                typeValueText.text = JdbcMapUtil.getString(row,"WINNING_BIDS_AMOUNT");
                attLinkResult.attMap.put("WINNING_BIDS_AMOUNT",typeValueText);
            }
            //合同工期
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.INTEGER;
                typeValueText.value = JdbcMapUtil.getString(row,"PLAN_TOTAL_DAYS");
                typeValueText.text = JdbcMapUtil.getString(row,"PLAN_TOTAL_DAYS");
                attLinkResult.attMap.put("CONTRACT_DAYS",typeValueText);
            }
            //是否涉及保函
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
//                typeValueText.value = JdbcMapUtil.getBoolean(row,"IS_REFER_GUARANTEE")==null?null:JdbcMapUtil.getBoolean(row,"IS_REFER_GUARANTEE").toString();
                String code = JdbcMapUtil.getString(row, "IS_REFER_GUARANTEE");
                Map<String, Object> idMap = jdbcTemplate.queryForMap("SELECT v.id id FROM gr_set_value v left " +
                                "join gr_set k on v.GR_SET_ID = k.id where k.`CODE` = 'is_refer_guarantee' and v.`CODE` = ?",
                        code);
                typeValueText.value = idMap.get("id").toString();
                typeValueText.text = idMap.get("id").toString();
//                typeValueText.text = BooleanUtil.toText(JdbcMapUtil.getBoolean(row,"IS_REFER_GUARANTEE"));
                attLinkResult.attMap.put("IS_REFER_GUARANTEE_ID",typeValueText);
            }
            //保函类型
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"GUARANTEE_LETTER_TYPE_ID");
                typeValueText.text = JdbcMapUtil.getString(row,"GUARANTEE_LETTER_TYPE_ID");
                attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_ID",typeValueText);
            }
            //相对方联系人
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"OPPO_SITE_LINK_MAN");
                typeValueText.text = JdbcMapUtil.getString(row,"OPPO_SITE_LINK_MAN");
                attLinkResult.attMap.put("OPPO_SITE_LINK_MAN",typeValueText);
            }
            //相对电话
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"OPPO_SITE_CONTACT");
                typeValueText.text = JdbcMapUtil.getString(row,"OPPO_SITE_CONTACT");
                attLinkResult.attMap.put("OPPO_SITE_CONTACT",typeValueText);
            }
            //是否标准模板
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
//                typeValueText.value = JdbcMapUtil.getBoolean(row,"IS_TEMPLATE")==null?null:JdbcMapUtil.getBoolean(row,"IS_TEMPLATE").toString();
                String code = JdbcMapUtil.getString(row, "IS_TEMPLATE");
                Map<String, Object> idMap = jdbcTemplate.queryForMap("SELECT v.id id FROM gr_set_value v left " +
                        "join gr_set k on v.GR_SET_ID = k.id where k.`CODE` = 'is_standard_contract_template' and v.`CODE` = ?",
                        code);
                typeValueText.value = idMap.get("id").toString();
                typeValueText.text = idMap.get("id").toString();
                attLinkResult.attMap.put("IS_STANDARD_CONTRACT_TEMPLATE_ID",typeValueText);
            }

            //合同名称带序号
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"CONTRACT_NAME");
                typeValueText.text = JdbcMapUtil.getString(row,"CONTRACT_NAME");
                attLinkResult.attMap.put("CONTRACT_NAME",typeValueText);
            }

            //合同总金额
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row,"CONTRACT_PRICE");
                typeValueText.text = JdbcMapUtil.getString(row,"CONTRACT_PRICE");

                attLinkResult.attMap.put("CONTRACT_PRICE",typeValueText);
                attLinkResult.attMap.put("CONTRACT_AMOUNT",typeValueText);
            }

            return attLinkResult;
        }else if (("RELATION_CONTRACT_ID").equals(attCode)){
            //根据id查询招投标信息
            List<Map<String, Object>> list = jdbcTemplate.queryForList("select CONTRACT_CODE, NAME from po_order_req where id = ?", attValue);

            if (CollectionUtils.isEmpty(list)) {
                throw new BaseException("合同相关属性不完善！");
            }
            Map row = list.get(0);

            //合同编号
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"CONTRACT_CODE");
                typeValueText.text = JdbcMapUtil.getString(row,"CONTRACT_CODE");
                attLinkResult.attMap.put("CONTRACT_CODE",typeValueText);
            }
            //合同名称
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"NAME");
                typeValueText.text = JdbcMapUtil.getString(row,"NAME");
                attLinkResult.attMap.put("CONTRACT_NAME",typeValueText);
            }

            return attLinkResult;
        } else if ("GUARANTEE_ID".equals(attCode)){ //获取保函名称

            //查询保函相关信息
            String sql = "select NAME,SUPPLIER,BENEFICIARY,GUARANTEE_LETTER_TYPE_ID,GUARANTEE_MECHANISM,GUARANTEE_CODE,GUARANTEE_AMT,GUARANTEE_START_DATE,GUARANTEE_END_DATE,GUARANTEE_FILE from PO_GUARANTEE_LETTER_REQUIRE_REQ where id = ?";
            List<Map<String,Object>> list = jdbcTemplate.queryForList(sql,attValue);
            if (CollectionUtils.isEmpty(list)){
                throw new BaseException("保函没有相关信息，请先完善");
            }
            Map row = list.get(0);

            //保函名称
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"NAME");
                typeValueText.text = JdbcMapUtil.getString(row,"NAME");
                attLinkResult.attMap.put("GUARANTEE_NAME",typeValueText);
            }
            //供应商
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"SUPPLIER");
                typeValueText.text = JdbcMapUtil.getString(row,"SUPPLIER");

                attLinkResult.attMap.put("SUPPLIER",typeValueText);
            }
            //受益人
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"BENEFICIARY");
                typeValueText.text = JdbcMapUtil.getString(row,"BENEFICIARY");

                attLinkResult.attMap.put("BENEFICIARY",typeValueText);
            }
            //保函类型
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"GUARANTEE_LETTER_TYPE_ID");
                typeValueText.text = JdbcMapUtil.getString(row,"GUARANTEE_LETTER_TYPE_ID");

                attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_ID",typeValueText);
            }
            //保函开立机构
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"GUARANTEE_MECHANISM");
                typeValueText.text = JdbcMapUtil.getString(row,"GUARANTEE_MECHANISM");

                attLinkResult.attMap.put("GUARANTEE_MECHANISM",typeValueText);
            }
            //保函编号
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row,"GUARANTEE_CODE");
                typeValueText.text = JdbcMapUtil.getString(row,"GUARANTEE_CODE");

                attLinkResult.attMap.put("GUARANTEE_CODE",typeValueText);
            }
            //担保金额
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DOUBLE;
                typeValueText.value = JdbcMapUtil.getString(row,"GUARANTEE_AMT");
                typeValueText.text = JdbcMapUtil.getString(row,"GUARANTEE_AMT");

                attLinkResult.attMap.put("GUARANTEE_AMT",typeValueText);
            }
            //保函开立日期
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DATE;
                typeValueText.value = JdbcMapUtil.getString(row,"GUARANTEE_START_DATE");
                typeValueText.text = JdbcMapUtil.getString(row,"GUARANTEE_START_DATE");

                attLinkResult.attMap.put("GUARANTEE_START_DATE",typeValueText);
            }
            //保函到期日期
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.DATE;
                typeValueText.value = JdbcMapUtil.getString(row,"GUARANTEE_END_DATE");
                typeValueText.text = JdbcMapUtil.getString(row,"GUARANTEE_END_DATE");

                attLinkResult.attMap.put("GUARANTEE_END_DATE",typeValueText);
            }

            return attLinkResult;

        } else if ("AMOUT_PM_PRJ_ID".equals(attCode)){ //资金需求项目名称(AMOUT_PM_PRJ_ID),引用（单值）
            //项目基础信息
            List<Map<String, Object>> list = jdbcTemplate
                    .queryForList("select t.code prj_code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name," +
                            "l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,st.id st_id,st.name st_name," +
                            "su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION, t.BUILD_YEARS," +
                            "t.PRJ_REPLY_NO, t.PRJ_REPLY_DATE, t.PRJ_REPLY_FILE, t.INVESTMENT_SOURCE_ID,t.PRJ_CODE, " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST1 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'FS', " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST2 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'PD', " +
                            "(SELECT PRJ_TOTAL_INVEST from PM_PRJ_INVEST3 WHERE PM_PRJ_ID = t.id order by CRT_DT desc limit 1) as 'budget' " +
                            "from pm_prj t join PM_PARTY c on t.id=? and t.CUSTOMER_UNIT=c.id " +
                            "join gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID " +
                            "join gr_set_value l on t.BASE_LOCATION_ID=l.id " +
                            "join gr_set_value pt on t.PROJECT_TYPE_ID=pt.id " +
                            "join gr_set_value st on t.CON_SCALE_TYPE_ID=st.id " +
                            "join gr_set_value su on t.CON_SCALE_UOM_ID=su.id", attValue);
            if (CollectionUtils.isEmpty(list)) {
                throw new BaseException("项目的相关属性不完整！");
            }
            Map row = list.get(0);
            //回显项目信息
            //项目编号
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type =AttDataTypeE.TEXT_LONG;
                typeValueText.value =JdbcMapUtil.getString(row,"prj_code");
                typeValueText.text =JdbcMapUtil.getString(row,"prj_code");
                attLinkResult.attMap.put("PRJ_CODE",typeValueText);
            }
            //项目批复文号
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");
                typeValueText.text = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");
                attLinkResult.attMap.put("PRJ_REPLY_NO", typeValueText);
                attLinkResult.attMap.put("REPLY_NO", typeValueText);
            }
            //项目介绍
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "PRJ_SITUATION");
                typeValueText.text = JdbcMapUtil.getString(row, "PRJ_SITUATION");
                attLinkResult.attMap.put("PRJ_SITUATION", typeValueText);
            }
            //资金来源
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
                typeValueText.text = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
                attLinkResult.attMap.put("PM_FUND_SOURCE_ID", typeValueText);
                attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", typeValueText);
            }
            //业主单位
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "customer_id");
                typeValueText.text = JdbcMapUtil.getString(row, "customer_name");
                attLinkResult.attMap.put("CUSTOMER_UNIT", typeValueText);
            }
            //项目类型
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.REF_SINGLE;
                typeValueText.value = JdbcMapUtil.getString(row, "pt_id");
                typeValueText.text = JdbcMapUtil.getString(row, "pt_name");
                attLinkResult.attMap.put("PROJECT_TYPE_ID", typeValueText);
            }


            if ("PO_ORDER_PAYMENT_REQ".equals(entCode)){ //采购合同付款申请
                //查询付款申请历史信息
                String sql = "SELECT COLLECTION_DEPT_TWO,BANK_OF_DEPOSIT,ACCOUNT_NO,RECEIPT,SPECIAL_BANK_OF_DEPOSIT,SPECIAL_ACCOUNT_NO FROM PO_ORDER_PAYMENT_REQ WHERE AMOUT_PM_PRJ_ID = ? AND STATUS = 'AP' ORDER BY CRT_DT DESC limit 1";
                List<Map<String,Object>> map1 = jdbcTemplate.queryForList(sql,attValue);
                if (!CollectionUtils.isEmpty(map1)){
                    Map row2 = map1.get(0);
                    //收款单位
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.DATE;
                        typeValueText.value = JdbcMapUtil.getString(row2,"COLLECTION_DEPT_TWO");
                        typeValueText.text = JdbcMapUtil.getString(row2,"COLLECTION_DEPT_TWO");
                        attLinkResult.attMap.put("COLLECTION_DEPT_TWO",typeValueText);
                    }
                    //开户行
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.DATE;
                        typeValueText.value = JdbcMapUtil.getString(row2,"BANK_OF_DEPOSIT");
                        typeValueText.text = JdbcMapUtil.getString(row2,"BANK_OF_DEPOSIT");
                        attLinkResult.attMap.put("BANK_OF_DEPOSIT",typeValueText);
                    }
                    //账号
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.DATE;
                        typeValueText.value = JdbcMapUtil.getString(row2,"ACCOUNT_NO");
                        typeValueText.text = JdbcMapUtil.getString(row2,"ACCOUNT_NO");
                        attLinkResult.attMap.put("ACCOUNT_NO",typeValueText);
                    }
                    //农民工工资专用账号收款单位
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.DATE;
                        typeValueText.value = JdbcMapUtil.getString(row2,"RECEIPT");
                        typeValueText.text = JdbcMapUtil.getString(row2,"RECEIPT");
                        attLinkResult.attMap.put("RECEIPT",typeValueText);
                    }
                    //专户开户行
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.DATE;
                        typeValueText.value = JdbcMapUtil.getString(row2,"SPECIAL_BANK_OF_DEPOSIT");
                        typeValueText.text = JdbcMapUtil.getString(row2,"SPECIAL_BANK_OF_DEPOSIT");
                        attLinkResult.attMap.put("SPECIAL_BANK_OF_DEPOSIT",typeValueText);
                    }
                    //专户账号
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.DATE;
                        typeValueText.value = JdbcMapUtil.getString(row2,"SPECIAL_ACCOUNT_NO");
                        typeValueText.text = JdbcMapUtil.getString(row2,"SPECIAL_ACCOUNT_NO");
                        attLinkResult.attMap.put("SPECIAL_ACCOUNT_NO",typeValueText);
                    }
                }
                return attLinkResult;
            } else if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entCode)){ //资金需求计划申请
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

                //查询关联合同信息
                List<Map<String, Object>> contractMaps = jdbcTemplate.queryForList("select o.WIN_BID_UNIT_TXT,o" +
                        ".CONTRACT_PRICE,a.PRJ_TOTAL_INVEST estimate,b.PRJ_TOTAL_INVEST budget from PO_ORDER_REQ " +
                        "o left join PM_PRJ_INVEST2 a on a.PM_PRJ_ID =o.PM_PRJ_ID left join PM_PRJ_INVEST3 b on b" +
                        ".PM_PRJ_ID = o.PM_PRJ_ID where o.PM_PRJ_ID = ? and o.`STATUS` = 'AP' order by o.CRT_DT " +
                        "limit 1", attValue);
                if (!CollectionUtils.isEmpty(contractMaps)){
                    Map<String, Object> contractRow = contractMaps.get(0);
                    //签订单位
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_LONG;
                        typeValueText.value = JdbcMapUtil.getString(contractRow, "WIN_BID_UNIT_TXT");
                        typeValueText.text = JdbcMapUtil.getString(contractRow, "WIN_BID_UNIT_TXT");
                        attLinkResult.attMap.put("ENTRUSTING_UNIT", typeValueText);
                        attLinkResult.attMap.put("WIN_BID_UNIT_TXT", typeValueText);
                    }
                    //合同金额
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_LONG;
                        typeValueText.value = JdbcMapUtil.getString(contractRow, "CONTRACT_PRICE");
                        typeValueText.text = JdbcMapUtil.getString(contractRow, "CONTRACT_PRICE");
                        attLinkResult.attMap.put("CONTRACT_PRICE", typeValueText);
                    }
                    //合同已支付金额

                    //支付比例

                    //概算金额
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_LONG;
                        typeValueText.value = JdbcMapUtil.getString(contractRow, "estimate");
                        typeValueText.text = JdbcMapUtil.getString(contractRow, "estimate");
                        attLinkResult.attMap.put("ESTIMATED_AMOUNT", typeValueText);
                    }
                    //预算金额
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_LONG;
                        typeValueText.value = JdbcMapUtil.getString(contractRow, "budget");
                        typeValueText.text = JdbcMapUtil.getString(contractRow, "budget");
                        attLinkResult.attMap.put("FINANCIAL_AMOUNT", typeValueText);
                    }
                    //结算金额
                }

                //该项目之前有无资金需求计划
                List<Map<String, Object>> reqMaps = jdbcTemplate.queryForList("select r.YEAR,r.IS_BUDGET_ID,r.PROJECT_NATURE_ID," +
                        "r.BASE_LOCATION_ID,r.AGENT_BUILD_UNIT_RESPONSE,r.AGENT_BUILD_UNIT_RESPONSE_PHONE,r.DEMOLITION_COMPLETED," +
                        "r.PLAN_START_DATE,r.PLAN_COMPL_DATE,r.ACTUAL_START_DATE,r.CREATE_PROJECT_COMPLETED,r.FEASIBILITY_COMPLETED," +
                        "r.SELECT_SITE_COMPLETED,r.USE_LAND_COMPLETED,r.EIA_COMPLETED,r.WOODLAND_WATER_SOIL_COMPLETED,r.ESTIMATE_COMPLETED," +
                        "r.REPLY_FILE,r.BUDGET_REVIEW_COMPLETED,r.CONSERVATION_REPLY_FILE,r.CONSTRUCT_BID_COMPLETED,r.BID_WIN_NOTICE_FILE_GROUP_ID" +
                        " from PM_FUND_REQUIRE_PLAN_REQ " +
                        "r left join pm_prj p on r.AMOUT_PM_PRJ_ID = p.id where p.id = ? and r" +
                        ".`STATUS` = 'AP'", attValue);
                if (!CollectionUtils.isEmpty(reqMaps)){
                    Map<String, Object> reqRow = reqMaps.get(0);
                    if (!CollectionUtils.isEmpty(reqMaps)){//该项目有过资金需求计划，回显表单数据
                        Set<String> keys = reqRow.keySet();
                        for (String key : keys) {
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(reqRow, key);
                                typeValueText.text = JdbcMapUtil.getString(reqRow, key);
                                attLinkResult.attMap.put(key, typeValueText);
                            }
                        }
                    }
                } else {//未填写 根据项目回显

                    //立项年度
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.DATE;
                        String year = simpleDateFormat.format(JdbcMapUtil.getDate(row, "PRJ_REPLY_DATE"));
                        typeValueText.value = year;
                        typeValueText.text = year;
                        attLinkResult.attMap.put("YEAR", typeValueText);
                    }
                    //建设地点
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.REF_SINGLE;
                        typeValueText.value = JdbcMapUtil.getString(row, "l_id");
                        typeValueText.text = JdbcMapUtil.getString(row, "l_name");
                        attLinkResult.attMap.put("BASE_LOCATION_ID", typeValueText);
                    }
                    //查询五方
                    List<Map<String, Object>> partyMaps = jdbcTemplate.queryForList("SELECT r.BUILD_UNIT_RESPONSE,r" +
                            ".AGENT_PHONE FROM PM_PRJ_PARTY_REQ r left join pm_prj p on p.id = r.PM_PRJ_ID where p.id = ?" +
                            " and r.`STATUS` = 'AP' ORDER BY r.CRT_DT desc LIMIT 1", attValue);
                    if (!CollectionUtils.isEmpty(partyMaps)){
                        Map<String, Object> partyRow = partyMaps.get(0);
                        //项目负责人
                        {
                            TypeValueText typeValueText = new TypeValueText();
                            typeValueText.type = AttDataTypeE.TEXT_SHORT;
                            typeValueText.value = JdbcMapUtil.getString(partyRow, "BUILD_UNIT_RESPONSE");
                            typeValueText.text = JdbcMapUtil.getString(partyRow, "BUILD_UNIT_RESPONSE");
                            attLinkResult.attMap.put("AGENT_BUILD_UNIT_RESPONSE", typeValueText);
                        }
                        //负责人联系电话
                        {
                            TypeValueText typeValueText = new TypeValueText();
                            typeValueText.type = AttDataTypeE.TEXT_SHORT;
                            typeValueText.value = JdbcMapUtil.getString(partyRow, "AGENT_PHONE");
                            typeValueText.text = JdbcMapUtil.getString(partyRow, "AGENT_PHONE");
                            attLinkResult.attMap.put("AGENT_BUILD_UNIT_RESPONSE_PHONE", typeValueText);
                        }
                    }
                    //征地拆迁完成情况
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_SHORT;
                        typeValueText.value = null;
                        typeValueText.text = null;
                        attLinkResult.attMap.put("DEMOLITION_COMPLETED", typeValueText);
                    }
                    //预计开工时间
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_SHORT;
                        typeValueText.value = null;
                        typeValueText.text = null;
                        attLinkResult.attMap.put("PLAN_START_DATE", typeValueText);
                    }
                    //预计完工时间
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_SHORT;
                        typeValueText.value = null;
                        typeValueText.text = null;
                        attLinkResult.attMap.put("PLAN_COMPL_DATE", typeValueText);
                    }
                    //实际开工时间
                    {
                        TypeValueText typeValueText = new TypeValueText();
                        typeValueText.type = AttDataTypeE.TEXT_SHORT;
                        typeValueText.value = null;
                        typeValueText.text = null;
                        attLinkResult.attMap.put("ACTUAL_START_DATE", typeValueText);
                    }
                    //查询节点名称
                    List<Map<String, Object>> nodeMaps = jdbcTemplate.queryForList("select n.name,n.PROGRESS_STATUS_ID from PM_PRO_PLAN_NODE n" +
                            " left join PM_PRO_PLAN p on n.PM_PRO_PLAN_ID = p.id where p.PM_PRJ_ID = ? and n.level = '3'", attValue);
                    if (CollectionUtils.isEmpty(nodeMaps)){
                        throw new BaseException("项目没有对应的三级节点！");
                    }

                    //默认未涉及
                    initNUll(attLinkResult);
                    //匹配字段
                    List<String> createMatch = Arrays.asList("立项", "立项批复");
                    List<String> feasibility = Arrays.asList("可研", "可研批复");
                    List<String> landUsePlan = Arrays.asList("用地规划", "用地规划许可证");
                    List<String> eia = Arrays.asList("环评审批完成情况", "环评");
                    List<String> advanceExam = Arrays.asList("用地预审");
                    List<String> save = Arrays.asList("节能", "水保", "林地使用调整");
                    List<String> prj = Arrays.asList("(施工)工程量清单", "工程量清单", "EPC", "施工", "工程量清单");
                    for (Map<String, Object> nodeMap : nodeMaps) {
                        String name = nodeMap.get("name").toString();
                        if(judgeMatch(createMatch,name)){
//                        if ("立项批复".equals(name)){
                            //立项完成情况
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("CREATE_PROJECT_COMPLETED", typeValueText);
                            }
                        }else
                        if(judgeMatch(feasibility,name)){
//                            if ("可研批复".equals(name)){
                            //可研完成情况
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("FEASIBILITY_COMPLETED", typeValueText);
                            }
                        }else
                        if(judgeMatch(landUsePlan,name)){
//                            if ("用地规划许可证".equals(name)){
                            //规划选址完成情况
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("SELECT_SITE_COMPLETED", typeValueText);
                            }
                        }else
                        if(judgeMatch(eia,name)){
//                        if ("环评".equals(name)){
                            //环评完成情况
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("EIA_COMPLETED", typeValueText);
                            }
                        }else
                        if(judgeMatch(advanceExam,name)){
//                        if ("用地预审".equals(name)){
                            //用地预审
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("USE_LAND_COMPLETED", typeValueText);
                            }
                        }else
                        if(judgeMatch(save,name)){
//                        if ("节能+水保+林地使用调整".equals(name)){
                            //用地预审
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("WOODLAND_WATER_SOIL_COMPLETED", typeValueText);
                            }
                        }else
                        if ("初步设计概算批复".equals(name)){
                            //概算完成情况
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("ESTIMATE_COMPLETED", typeValueText);
                            }
                        }else
                        if ("预算财政评审".equals(name)){
                            //预算评审完成情况
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("BUDGET_REVIEW_COMPLETED", typeValueText);
                            }
                        }else
                        if (judgeMatch(prj,name)){
//                        if ("工程量清单、EPC、施工".equals(name)){
                            //预算评审完成情况
                            {
                                TypeValueText typeValueText = new TypeValueText();
                                typeValueText.type = AttDataTypeE.TEXT_LONG;
                                typeValueText.value = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                typeValueText.text = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                                attLinkResult.attMap.put("CONSTRUCT_BID_COMPLETED", typeValueText);
                            }
                        }

                    }
                }
                //dlttodo
                return attLinkResult;

            } else if ("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD".equals(entCode)) { //技术交底与图纸会审记录
                //查询预算财评信息
                String sql1 = "SELECT PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST3 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
                List<Map<String,Object>> map = jdbcTemplate.queryForList(sql1,attValue);
                List<Map<String,Object>> map1 = new ArrayList<>();
                List<Map<String,Object>> map2 = new ArrayList<>();
                Map resultRow = new HashMap();
                if (CollectionUtils.isEmpty(map)){
                    //初设概算信息
                    String sql2 = "SELECT PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST2 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
                    map1 = jdbcTemplate.queryForList(sql2,attValue);
                    if (CollectionUtils.isEmpty(map1)){
                        String sql3 = "SELECT PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST1 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
                        map2 = jdbcTemplate.queryForList(sql3,attValue);
                        if (!CollectionUtils.isEmpty(map2)){
                            resultRow = map2.get(0);
                        }
                    } else {
                        resultRow = map1.get(0);
                    }
                } else {
                    resultRow = map.get(0);
                }
                //总投资
                {
                    TypeValueText typeValueText = new TypeValueText();
                    typeValueText.type = AttDataTypeE.DOUBLE;
                    typeValueText.value = JdbcMapUtil.getString(resultRow, "PRJ_TOTAL_INVEST");
                    typeValueText.text = JdbcMapUtil.getString(resultRow, "PRJ_TOTAL_INVEST");
                    attLinkResult.attMap.put("PRJ_TOTAL_INVEST", typeValueText);
                }
                //工程费
                {
                    TypeValueText typeValueText = new TypeValueText();
                    typeValueText.type = AttDataTypeE.DOUBLE;
                    typeValueText.value = JdbcMapUtil.getString(resultRow, "PROJECT_AMT");
                    typeValueText.text = JdbcMapUtil.getString(resultRow, "PROJECT_AMT");
                    attLinkResult.attMap.put("PROJECT_AMT", typeValueText);
                }
                //工程建设其他费
                {
                    TypeValueText typeValueText = new TypeValueText();
                    typeValueText.type = AttDataTypeE.DOUBLE;
                    typeValueText.value = JdbcMapUtil.getString(resultRow, "PROJECT_OTHER_AMT");
                    typeValueText.text = JdbcMapUtil.getString(resultRow, "PROJECT_OTHER_AMT");
                    attLinkResult.attMap.put("PROJECT_OTHER_AMT", typeValueText);
                }
                //预备费
                {
                    TypeValueText typeValueText = new TypeValueText();
                    typeValueText.type = AttDataTypeE.DOUBLE;
                    typeValueText.value = JdbcMapUtil.getString(resultRow, "PREPARE_AMT");
                    typeValueText.text = JdbcMapUtil.getString(resultRow, "PREPARE_AMT");
                    attLinkResult.attMap.put("PREPARE_AMT", typeValueText);
                }
                //建设期利息
                {
                    TypeValueText typeValueText = new TypeValueText();
                    typeValueText.type = AttDataTypeE.DOUBLE;
                    typeValueText.value = JdbcMapUtil.getString(resultRow, "CONSTRUCT_PERIOD_INTEREST");
                    typeValueText.text = JdbcMapUtil.getString(resultRow, "CONSTRUCT_PERIOD_INTEREST");
                    attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", typeValueText);
                }
                return attLinkResult;
            } else {
                return null;
            }

        } else if ("STATUS".equals(attCode)){
            return null;
        }else {
            throw new BaseException("属性联动的参数的attCode为" + attCode + "，不支持！");
        }
    }

    //默认未涉及
    private void initNUll(AttLinkResult attLinkResult) {
        List<String> fields = Arrays.asList("CREATE_PROJECT_COMPLETED", "FEASIBILITY_COMPLETED",
                "SELECT_SITE_COMPLETED", "EIA_COMPLETED", "USE_LAND_COMPLETED", "WOODLAND_WATER_SOIL_COMPLETED",
                "ESTIMATE_COMPLETED", "BUDGET_REVIEW_COMPLETED", "CONSTRUCT_BID_COMPLETED");
        for (String field : fields) {
            {
                TypeValueText typeValueText = new TypeValueText();
                typeValueText.type = AttDataTypeE.TEXT_LONG;
                typeValueText.value = "99902212142036278";
                typeValueText.text = "99902212142036278";
                attLinkResult.attMap.put(field, typeValueText);
            }
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
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST");

            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", typeValueText);
        }
        //工程费用
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT");

            attLinkResult.attMap.put("PROJECT_AMT", typeValueText);
        }
        //工程建设其他费用
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT");

            attLinkResult.attMap.put("PROJECT_OTHER_AMT", typeValueText);
        }
        //预备费
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT");

            attLinkResult.attMap.put("PREPARE_AMT", typeValueText);
        }
        //利息
        {
            TypeValueText typeValueText = new TypeValueText();
            typeValueText.type = AttDataTypeE.DOUBLE;
            typeValueText.value = JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST");
            typeValueText.text = JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST");

            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", typeValueText);
        }
        return attLinkResult;
    }

    public boolean judgeMatch(List<String> words,String name){
        for (String word : words) {
            if (name.contains(word)){
                return true;
            }
        }
        return false;
    }

}
