package com.cisdi.ext.link;

import com.cisdi.ext.util.DateTimeUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        String attCode = param.attCode;
        String attValue = param.attValue;
        String sevId = param.sevId;

        // 获取实体代码（表名）：
        String entCode = myJdbcTemplate.queryForMap("select e.code ent_code from ad_single_ent_view sev join ad_ent e on sev.AD_ENT_ID = e.ID and sev.id=?", sevId).get("ent_code").toString();

        if ("PROJECT_TYPE_ID".equals(attCode)) {
            return linkForPROJECT_TYPE_ID(myJdbcTemplate, attValue);
        } else if ("PM_PRJ_ID".equals(attCode)) {
            return linkForPM_PRJ_ID(myJdbcTemplate, attValue, entCode);
        } else if ("PMS_RELEASE_WAY_ID".equals(attCode) || "GUARANTEE_LETTER_TYPE_ID".equals(attCode) || "CONTRACT_CATEGORY_ID".equals(attCode) || "PRJ_MANAGE_MODE_ID".equals(attCode)) {
            return linkForMany(myJdbcTemplate, attCode, attValue);
        } else if ("BIDDING_NAME_ID".equals(attCode)) {
            return linkForBIDDING_NAME_ID(myJdbcTemplate, attValue, sevId);
        } else if ("CONTRACT_ID".equals(attCode)) {
            return linkForCONTRACT_ID(myJdbcTemplate, attValue, sevId);
        } else if (("RELATION_CONTRACT_ID").equals(attCode)) {
            return linkForRELATION_CONTRACT_ID(myJdbcTemplate, attValue,sevId);
        } else if ("GUARANTEE_ID".equals(attCode)) {
            return linkForGUARANTEE_ID(myJdbcTemplate, attValue);
        } else if ("AMOUT_PM_PRJ_ID".equals(attCode)) {
            return linkForAMOUT_PM_PRJ_ID(myJdbcTemplate, attValue, entCode);
        } else if ("STATUS".equals(attCode)) {
            return linkForSTATUS(attValue, sevId);
        } else {
            throw new BaseException("属性联动的参数的attCode为" + attCode + "，不支持！");
        }
    }

    private AttLinkResult linkForMany(MyJdbcTemplate myJdbcTemplate, String attCode, String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 1.PMS_RELEASE_WAY_ID 招标类别下拉框
        // 2.GUARANTEE_LETTER_TYPE_ID 保函类别下拉框
        // 3.PMS_RELEASE_WAY_ID 项目类别下拉框

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT t.id, t.name FROM gr_set_value t WHERE t.id = ?", attValue);

        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("未设置相应项目类型的联动！");
        } else if (list.size() > 1) {
            throw new BaseException("重复设置相应项目类型的联动！");
        }

        Map row = list.get(0);

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "id");
            linkedAtt.text = JdbcMapUtil.getString(row, "name");

            attLinkResult.attMap.put(attCode, linkedAtt);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForAMOUT_PM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 资金需求项目名称(AMOUT_PM_PRJ_ID),引用（单值）
        // 项目基础信息
        List<Map<String, Object>> list = myJdbcTemplate
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
        // 回显项目信息
        // 项目编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "prj_code");
            linkedAtt.text = JdbcMapUtil.getString(row, "prj_code");
            attLinkResult.attMap.put("PRJ_CODE", linkedAtt);
        }
        // 项目批复文号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");
            linkedAtt.text = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");
            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt);
            attLinkResult.attMap.put("REPLY_NO", linkedAtt);
        }
        // 项目介绍
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PRJ_SITUATION");
            linkedAtt.text = JdbcMapUtil.getString(row, "PRJ_SITUATION");
            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt);
        }
        // 资金来源
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
            attLinkResult.attMap.put("PM_FUND_SOURCE_ID", linkedAtt);
            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt);
        }
        // 业主单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "customer_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "customer_name");
            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt);
        }
        // 项目类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "pt_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "pt_name");
            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt);
        }
        if ("PO_ORDER_PAYMENT_REQ".equals(entCode)) { // 采购合同付款申请
            // 查询付款申请历史信息
            String sql = "SELECT COLLECTION_DEPT_TWO,BANK_OF_DEPOSIT,ACCOUNT_NO,RECEIPT,SPECIAL_BANK_OF_DEPOSIT,SPECIAL_ACCOUNT_NO FROM PO_ORDER_PAYMENT_REQ WHERE AMOUT_PM_PRJ_ID = ? AND STATUS = 'AP' ORDER BY CRT_DT DESC limit 1";
            List<Map<String, Object>> map1 = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(map1)) {
                Map row2 = map1.get(0);
                // 收款单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "COLLECTION_DEPT_TWO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "COLLECTION_DEPT_TWO");
                    attLinkResult.attMap.put("COLLECTION_DEPT_TWO", linkedAtt);
                }
                // 开户行
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "BANK_OF_DEPOSIT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "BANK_OF_DEPOSIT");
                    attLinkResult.attMap.put("BANK_OF_DEPOSIT", linkedAtt);
                }
                // 账号
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "ACCOUNT_NO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "ACCOUNT_NO");
                    attLinkResult.attMap.put("ACCOUNT_NO", linkedAtt);
                }
                // 农民工工资专用账号收款单位
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "RECEIPT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "RECEIPT");
                    attLinkResult.attMap.put("RECEIPT", linkedAtt);
                }
                // 专户开户行
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "SPECIAL_BANK_OF_DEPOSIT");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "SPECIAL_BANK_OF_DEPOSIT");
                    attLinkResult.attMap.put("SPECIAL_BANK_OF_DEPOSIT", linkedAtt);
                }
                // 专户账号
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
                    linkedAtt.value = JdbcMapUtil.getString(row2, "SPECIAL_ACCOUNT_NO");
                    linkedAtt.text = JdbcMapUtil.getString(row2, "SPECIAL_ACCOUNT_NO");
                    attLinkResult.attMap.put("SPECIAL_ACCOUNT_NO", linkedAtt);
                }
            }
            return attLinkResult;
        } else if ("PM_FUND_REQUIRE_PLAN_REQ".equals(entCode)) { // 资金需求计划申请
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy");

            // 查询关联合同信息
            List<Map<String, Object>> contractMaps = myJdbcTemplate.queryForList("select o.WIN_BID_UNIT_TXT,o" +
                    ".CONTRACT_PRICE,a.PRJ_TOTAL_INVEST estimate,b.PRJ_TOTAL_INVEST budget from PO_ORDER_REQ " +
                    "o left join PM_PRJ_INVEST2 a on a.PM_PRJ_ID =o.PM_PRJ_ID left join PM_PRJ_INVEST3 b on b" +
                    ".PM_PRJ_ID = o.PM_PRJ_ID where o.PM_PRJ_ID = ? and o.`STATUS` = 'AP' order by o.CRT_DT " +
                    "limit 1", attValue);
            if (!CollectionUtils.isEmpty(contractMaps)) {
                Map<String, Object> contractRow = contractMaps.get(0);
                // 合同已支付金额

                // 支付比例

                // 概算金额
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(contractRow, "estimate");
                    linkedAtt.text = JdbcMapUtil.getString(contractRow, "estimate");
                    attLinkResult.attMap.put("ESTIMATED_AMOUNT", linkedAtt);
                }
                // 预算金额
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(contractRow, "budget");
                    linkedAtt.text = JdbcMapUtil.getString(contractRow, "budget");
                    attLinkResult.attMap.put("FINANCIAL_AMOUNT", linkedAtt);
                }
                // 结算金额
            }

            // 该项目之前有无资金需求计划
            List<Map<String, Object>> reqMaps = myJdbcTemplate.queryForList("select r.YEAR,r.IS_BUDGET_ID,r.PROJECT_NATURE_ID," +
                    "r.BASE_LOCATION_ID,r.AGENT_BUILD_UNIT_RESPONSE,r.AGENT_BUILD_UNIT_RESPONSE_PHONE,r.DEMOLITION_COMPLETED," +
                    "r.PLAN_START_DATE,r.PLAN_COMPL_DATE,r.ACTUAL_START_DATE,r.CREATE_PROJECT_COMPLETED,r.FEASIBILITY_COMPLETED," +
                    "r.SELECT_SITE_COMPLETED,r.USE_LAND_COMPLETED,r.EIA_COMPLETED,r.WOODLAND_WATER_SOIL_COMPLETED,r.ESTIMATE_COMPLETED," +
                    "r.REPLY_FILE,r.BUDGET_REVIEW_COMPLETED,r.CONSERVATION_REPLY_FILE,r.CONSTRUCT_BID_COMPLETED,r.BID_WIN_NOTICE_FILE_GROUP_ID " +
                    " from PM_FUND_REQUIRE_PLAN_REQ r " +
                    "left join pm_prj p on r.AMOUT_PM_PRJ_ID = p.id " +
                    "where p.id = ? and r.`STATUS` = 'AP'", attValue);
            if (!CollectionUtils.isEmpty(reqMaps)) {
                Map<String, Object> reqRow = reqMaps.get(0);
                if (!CollectionUtils.isEmpty(reqMaps)) {// 该项目有过资金需求计划，回显表单数据
                    List<String> keyList = getKeyList();
                    Set<String> keys = reqRow.keySet();
                    for (String key : keys) {
                        String id = JdbcMapUtil.getString(reqRow, key);
                        String name = id;
                        if (keyList.contains(key)) {// 是否财政预算
                            String sqlName = "select name from gr_set_value where id = ?";
                            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(sqlName, id);
                            name = JdbcMapUtil.getString(nameMap.get(0), "name");
                        }
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = name;
                            attLinkResult.attMap.put(key, linkedAtt);
                        }
                    }
                }

                // 可研完成情况
            } else {// 未填写 根据项目回显

                // 立项年度
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DATE;
//                    String year = simpleDateFormat.format(JdbcMapUtil.getDate(row, "PRJ_REPLY_DATE"));
                    String year = JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");
                    linkedAtt.value = year;
                    linkedAtt.text = year;
                    attLinkResult.attMap.put("YEAR", linkedAtt);
                }
                // 建设地点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.REF_SINGLE;
                    linkedAtt.value = JdbcMapUtil.getString(row, "l_id");
                    linkedAtt.text = JdbcMapUtil.getString(row, "l_name");
                    attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt);
                }
                // 查询五方
                List<Map<String, Object>> partyMaps = myJdbcTemplate.queryForList("SELECT r.BUILD_UNIT_RESPONSE,r" +
                        ".AGENT_PHONE FROM PM_PRJ_PARTY_REQ r left join pm_prj p on p.id = r.PM_PRJ_ID where p.id = ?" +
                        " and r.`STATUS` = 'AP' ORDER BY r.CRT_DT desc LIMIT 1", attValue);
                if (!CollectionUtils.isEmpty(partyMaps)) {
                    Map<String, Object> partyRow = partyMaps.get(0);
                    // 项目负责人
                    {
                        LinkedAtt linkedAtt = new LinkedAtt();
                        linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                        linkedAtt.value = JdbcMapUtil.getString(partyRow, "BUILD_UNIT_RESPONSE");
                        linkedAtt.text = JdbcMapUtil.getString(partyRow, "BUILD_UNIT_RESPONSE");
                        attLinkResult.attMap.put("AGENT_BUILD_UNIT_RESPONSE", linkedAtt);
                    }
                    // 负责人联系电话
                    {
                        LinkedAtt linkedAtt = new LinkedAtt();
                        linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                        linkedAtt.value = JdbcMapUtil.getString(partyRow, "AGENT_PHONE");
                        linkedAtt.text = JdbcMapUtil.getString(partyRow, "AGENT_PHONE");
                        attLinkResult.attMap.put("AGENT_BUILD_UNIT_RESPONSE_PHONE", linkedAtt);
                    }
                }
                // 征地拆迁完成情况
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("DEMOLITION_COMPLETED", linkedAtt);
                }
                // 预计开工时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("PLAN_START_DATE", linkedAtt);
                }
                // 预计完工时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("PLAN_COMPL_DATE", linkedAtt);
                }
                // 实际开工时间
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_SHORT;
                    linkedAtt.value = null;
                    linkedAtt.text = null;
                    attLinkResult.attMap.put("ACTUAL_START_DATE", linkedAtt);
                }
                // 查询节点名称
                List<Map<String, Object>> nodeMaps = myJdbcTemplate.queryForList("select n.name,n.PROGRESS_STATUS_ID from PM_PRO_PLAN_NODE n" +
                        " left join PM_PRO_PLAN p on n.PM_PRO_PLAN_ID = p.id where p.PM_PRJ_ID = ? and n.level = '3'", attValue);
                if (CollectionUtils.isEmpty(nodeMaps)) {
                    throw new BaseException("项目没有对应的三级节点！");
                }

                // 默认未涉及
                initNUll(attLinkResult);
                // 匹配字段
                List<String> createMatch = Arrays.asList("立项", "立项批复");
                List<String> feasibility = Arrays.asList("可研", "可研批复");
                List<String> landUsePlan = Arrays.asList("用地规划", "用地规划许可证");
                List<String> eia = Arrays.asList("环评审批完成情况", "环评");
                List<String> advanceExam = Arrays.asList("用地预审");
                List<String> save = Arrays.asList("节能", "水保", "林地使用调整");
                List<String> prj = Arrays.asList("(施工)工程量清单", "工程量清单", "EPC", "施工", "工程量清单");
                for (Map<String, Object> nodeMap : nodeMaps) {
                    String name = nodeMap.get("name").toString();
                    String id = JdbcMapUtil.getString(nodeMap, "PROGRESS_STATUS_ID");
                    String sqlName = "select name from gr_set_value where id = ?";
                    List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(sqlName, id);
                    String valueName = JdbcMapUtil.getString(nameMap.get(0), "name");
                    if (judgeMatch(createMatch, name)) {
//                        if ("立项批复".equals(name)){
                        // 立项完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("CREATE_PROJECT_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(feasibility, name)) {
//                            if ("可研批复".equals(name)){
                        // 可研完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("FEASIBILITY_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(landUsePlan, name)) {
//                            if ("用地规划许可证".equals(name)){
                        // 规划选址完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("SELECT_SITE_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(eia, name)) {
//                        if ("环评".equals(name)){
                        // 环评完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("EIA_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(advanceExam, name)) {
//                        if ("用地预审".equals(name)){
                        // 用地预审
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("USE_LAND_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(save, name)) {
//                        if ("节能+水保+林地使用调整".equals(name)){
                        // 用地预审
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("WOODLAND_WATER_SOIL_COMPLETED", linkedAtt);
                        }
                    } else if ("初步设计概算批复".equals(name)) {
                        // 概算完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("ESTIMATE_COMPLETED", linkedAtt);
                        }
                    } else if ("预算财政评审".equals(name)) {
                        // 预算评审完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("BUDGET_REVIEW_COMPLETED", linkedAtt);
                        }
                    } else if (judgeMatch(prj, name)) {
//                        if ("工程量清单、EPC、施工".equals(name)){
                        // 预算评审完成情况
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = id;
                            linkedAtt.text = valueName;
                            attLinkResult.attMap.put("CONSTRUCT_BID_COMPLETED", linkedAtt);
                        }
                    }

                }
            }

            //文件信息回显
            String sql1 = "select REPLY_FILE from PM_PRJ_INVEST2 where PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
            List<Map<String,Object>> list1 = myJdbcTemplate.queryForList(sql1,attValue);
            if (!CollectionUtils.isEmpty(list1)){
                String fileId = JdbcMapUtil.getString(list1.get(0),"REPLY_FILE");
                String file = StringUtil.codeToSplit(fileId);
                String sql2 = "select GROUP_CONCAT(FILE_INLINE_URL) as file from fl_file where id in ('"+file+"')";
                List<Map<String,Object>> list2 = myJdbcTemplate.queryForList(sql2);
                if (!CollectionUtils.isEmpty(list2)){
                    {
                        LinkedAtt linkedAtt = new LinkedAtt();
                        linkedAtt.type = AttDataTypeE.FILE_GROUP;
                        linkedAtt.value = fileId;
                        linkedAtt.text = JdbcMapUtil.getString(list2.get(0), "file");

                        attLinkResult.attMap.put("REPLY_FILE", linkedAtt);
                    }
                }

            }

            return attLinkResult;

        } else if ("SKILL_DISCLOSURE_PAPER_RECHECK_RECORD".equals(entCode) || "PM_DESIGN_ASSIGNMENT_BOOK".equals(entCode)) {
            // SKILL_DISCLOSURE_PAPER_RECHECK_RECORD 技术交底与图纸会审记录 PM_DESIGN_ASSIGNMENT_BOOK 设计任务书
            Map resultRow = getAmtMap(attValue);
            attLinkResult = getResult(resultRow, attLinkResult);
            return attLinkResult;
        } else {
            return attLinkResult;
        }
    }

    private AttLinkResult linkForGUARANTEE_ID(MyJdbcTemplate myJdbcTemplate, String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 获取保函名称

        // 查询保函相关信息
        String sql = "select NAME,SUPPLIER,BENEFICIARY,GUARANTEE_LETTER_TYPE_ID,GUARANTEE_MECHANISM,GUARANTEE_CODE,GUARANTEE_AMT,GUARANTEE_START_DATE,GUARANTEE_END_DATE,GUARANTEE_FILE from PO_GUARANTEE_LETTER_REQUIRE_REQ where id = ?";
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, attValue);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("保函没有相关信息，请先完善");
        }
        Map row = list.get(0);

        // 保函名称
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "NAME");
            linkedAtt.text = JdbcMapUtil.getString(row, "NAME");
            attLinkResult.attMap.put("GUARANTEE_NAME", linkedAtt);
        }
        // 供应商
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "SUPPLIER");
            linkedAtt.text = JdbcMapUtil.getString(row, "SUPPLIER");

            attLinkResult.attMap.put("SUPPLIER", linkedAtt);
        }
        // 受益人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BENEFICIARY");
            linkedAtt.text = JdbcMapUtil.getString(row, "BENEFICIARY");

            attLinkResult.attMap.put("BENEFICIARY", linkedAtt);
        }
        // 保函类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_ID");

            attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_ID", linkedAtt);
        }
        // 保函开立机构
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_MECHANISM");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_MECHANISM");

            attLinkResult.attMap.put("GUARANTEE_MECHANISM", linkedAtt);
        }
        // 保函编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_CODE");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_CODE");

            attLinkResult.attMap.put("GUARANTEE_CODE", linkedAtt);
        }
        // 担保金额
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_AMT");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_AMT");

            attLinkResult.attMap.put("GUARANTEE_AMT", linkedAtt);
        }
        // 保函开立日期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_START_DATE");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_START_DATE");

            attLinkResult.attMap.put("GUARANTEE_START_DATE", linkedAtt);
        }
        // 保函到期日期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_END_DATE");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_END_DATE");

            attLinkResult.attMap.put("GUARANTEE_END_DATE", linkedAtt);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForRELATION_CONTRACT_ID(MyJdbcTemplate myJdbcTemplate, String attValue,String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();

        //查询明细表信息
        if ("99902212142028526".equals(sevId)){ //资金需求计划申请-发起 实体视图id
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            // 查询明细信息
            String sql = "select COST_TYPE_TREE_ID,FEE_DETAIL,AMT from PM_ORDER_COST_DETAIL where CONTRACT_ID = ? order by id asc";
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> tmp : list) {
                    LinkedRecord linkedRecord = new LinkedRecord();

                    // 费用类型
                    linkedRecord.valueMap.put("COST_TYPE_TREE_ID", tmp.get("COST_TYPE_TREE_ID").toString());
                    linkedRecord.textMap.put("COST_TYPE_TREE_ID", tmp.get("COST_TYPE_TREE_ID").toString());
                    // 费用明细
                    linkedRecord.valueMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL"));
                    linkedRecord.textMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL").toString());
                    // 费用金额
                    linkedRecord.valueMap.put("AMT", tmp.get("AMT"));
                    linkedRecord.textMap.put("AMT", tmp.get("AMT").toString());

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put("99952822476362485", linkedRecordList);
            }
            attLinkResult.childCreatable.put("99952822476362485", false);
            attLinkResult.childClear.put("99952822476362485", true);
        }


        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select CONTRACT_CODE,NAME,WIN_BID_UNIT_TXT,CONTRACT_PRICE from po_order_req where id = ?", attValue);

        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("合同相关属性不完善！");
        }
        Map row = list.get(0);

        // 合同编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            attLinkResult.attMap.put("CONTRACT_CODE", linkedAtt);
        }
        // 合同名称
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "NAME");
            linkedAtt.text = JdbcMapUtil.getString(row, "NAME");
            attLinkResult.attMap.put("CONTRACT_NAME", linkedAtt);
        }
        // 中标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            linkedAtt.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            attLinkResult.attMap.put("WIN_BID_UNIT_TXT", linkedAtt);
        }
        // 合同总金额
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_PRICE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_PRICE");
            attLinkResult.attMap.put("CONTRACT_PRICE", linkedAtt);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForCONTRACT_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select CONTRACT_CODE, CONTRACT_CATEGORY_ID, CONTRACT_NAME, CONTRACT_PRICE, " +
                "BIDDING_NAME_ID,PMS_RELEASE_WAY_ID,BID_CTL_PRICE_LAUNCH,PURCHASE_TYPE,WIN_BID_UNIT_TXT,WINNING_BIDS_AMOUNT,PLAN_TOTAL_DAYS," +
                "IS_REFER_GUARANTEE,GUARANTEE_LETTER_TYPE_ID,OPPO_SITE_LINK_MAN,OPPO_SITE_CONTACT,IS_TEMPLATE " +
                "from po_order_req where id = ?", attValue);

        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("合同签订相关属性不完善！");
        }
        Map row = list.get(0);
        // 合同编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_CODE");
            attLinkResult.attMap.put("CONTRACT_CODE", linkedAtt);
        }
        // 合同类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_CATEGORY_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_CATEGORY_ID");
            attLinkResult.attMap.put("CONTRACT_CATEGORY_ID", linkedAtt);
        }
        // 关联招采
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BIDDING_NAME_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "BIDDING_NAME_ID");
            attLinkResult.attMap.put("BIDDING_NAME_ID", linkedAtt);
        }
        // 招标类别
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");
            attLinkResult.attMap.put("PMS_RELEASE_WAY_ID", linkedAtt);
        }
        // 招标控制价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            linkedAtt.text = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH", linkedAtt);
        }
        // 采购方式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PURCHASE_TYPE");
            linkedAtt.text = JdbcMapUtil.getString(row, "PURCHASE_TYPE");
            attLinkResult.attMap.put("PURCHASE_TYPE", linkedAtt);
        }
        // 中标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            linkedAtt.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            attLinkResult.attMap.put("WIN_BID_UNIT_TXT", linkedAtt);
        }
        // 中标价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WINNING_BIDS_AMOUNT");
            linkedAtt.text = JdbcMapUtil.getString(row, "WINNING_BIDS_AMOUNT");
            attLinkResult.attMap.put("WINNING_BIDS_AMOUNT", linkedAtt);
        }
        // 合同工期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.INTEGER;
            linkedAtt.value = JdbcMapUtil.getString(row, "PLAN_TOTAL_DAYS");
            linkedAtt.text = JdbcMapUtil.getString(row, "PLAN_TOTAL_DAYS");
            attLinkResult.attMap.put("CONTRACT_DAYS", linkedAtt);
        }
        // 是否涉及保函
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
//                linkedAtt.value = JdbcMapUtil.getBoolean(row,"IS_REFER_GUARANTEE")==null?null:JdbcMapUtil.getBoolean(row,"IS_REFER_GUARANTEE").toString();
            String code = JdbcMapUtil.getString(row, "IS_REFER_GUARANTEE");
            Map<String, Object> idMap = myJdbcTemplate.queryForMap("SELECT v.id id FROM gr_set_value v left " +
                            "join gr_set k on v.GR_SET_ID = k.id where k.`CODE` = 'is_refer_guarantee' and v.`CODE` = ?",
                    code);
            linkedAtt.value = idMap.get("id").toString();
            linkedAtt.text = idMap.get("id").toString();
//                linkedAtt.text = BooleanUtil.toText(JdbcMapUtil.getBoolean(row,"IS_REFER_GUARANTEE"));
            attLinkResult.attMap.put("IS_REFER_GUARANTEE_ID", linkedAtt);
        }
        // 保函类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "GUARANTEE_LETTER_TYPE_ID");
            attLinkResult.attMap.put("GUARANTEE_LETTER_TYPE_ID", linkedAtt);
        }
        // 相对方联系人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "OPPO_SITE_LINK_MAN");
            linkedAtt.text = JdbcMapUtil.getString(row, "OPPO_SITE_LINK_MAN");
            attLinkResult.attMap.put("OPPO_SITE_LINK_MAN", linkedAtt);
        }
        // 相对电话
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "OPPO_SITE_CONTACT");
            linkedAtt.text = JdbcMapUtil.getString(row, "OPPO_SITE_CONTACT");
            attLinkResult.attMap.put("OPPO_SITE_CONTACT", linkedAtt);
        }
        // 是否标准模板
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
//                linkedAtt.value = JdbcMapUtil.getBoolean(row,"IS_TEMPLATE")==null?null:JdbcMapUtil.getBoolean(row,"IS_TEMPLATE").toString();
            String code = JdbcMapUtil.getString(row, "IS_TEMPLATE");
            Map<String, Object> idMap = myJdbcTemplate.queryForMap("SELECT v.id id FROM gr_set_value v left " +
                            "join gr_set k on v.GR_SET_ID = k.id where k.`CODE` = 'is_standard_contract_template' and v.`CODE` = ?",
                    code);
            linkedAtt.value = idMap.get("id").toString();
            linkedAtt.text = idMap.get("id").toString();
            attLinkResult.attMap.put("IS_STANDARD_CONTRACT_TEMPLATE_ID", linkedAtt);
        }

        // 合同名称带序号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_NAME");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_NAME");
            attLinkResult.attMap.put("CONTRACT_NAME", linkedAtt);
        }

        // 合同总金额
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTRACT_PRICE");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTRACT_PRICE");

            attLinkResult.attMap.put("CONTRACT_PRICE", linkedAtt);
            attLinkResult.attMap.put("CONTRACT_AMOUNT", linkedAtt);
        }

        // 明细信息
        if ("99902212142033284".equals(sevId)) {
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            // 查询明细信息
            String sql = "select COST_TYPE_TREE_ID,FEE_DETAIL,TOTAL_AMT,AMT from pm_order_cost_detail where CONTRACT_ID = ? order by id asc";
            List<Map<String, Object>> list1 = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(list1)) {
                for (Map<String, Object> tmp : list1) {
                    LinkedRecord linkedRecord = new LinkedRecord();

                    // 费用类型
                    String payType = tmp.get("COST_TYPE_TREE_ID").toString();
                    linkedRecord.valueMap.put("COST_TYPE_TREE_ID", payType);
                    linkedRecord.textMap.put("COST_TYPE_TREE_ID", payType);
                    // 费用明细
                    linkedRecord.valueMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL").toString());
                    linkedRecord.textMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL").toString());
                    // 费用金额
                    BigDecimal TOTAL_AMT = new BigDecimal(tmp.get("TOTAL_AMT").toString());
                    linkedRecord.valueMap.put("TOTAL_AMT", String.valueOf(TOTAL_AMT));
                    linkedRecord.textMap.put("TOTAL_AMT", String.valueOf(TOTAL_AMT));
                    // 合同金额
                    linkedRecord.valueMap.put("AMT", tmp.get("AMT").toString());
                    linkedRecord.textMap.put("AMT", tmp.get("AMT").toString());

                    // 查询明细付款信息
                    String sql2 = "SELECT a.PAY_AMT_TWO,a.PAY_AMT_ONE FROM PM_PAY_COST_DETAIL a left join PO_ORDER_PAYMENT_REQ b on a.PARENT_ID = b.id WHERE a.CONTRACT_ID = ? AND a.COST_TYPE_TREE_ID = ? AND b.STATUS = 'AP' order by a.CRT_DT asc";
                    List<Map<String, Object>> list2 = myJdbcTemplate.queryForList(sql2, attValue, payType);
                    if (CollectionUtils.isEmpty(list2)) {
                        // 已付金额
                        linkedRecord.valueMap.put("PAY_AMT_TWO", "0");
                        linkedRecord.textMap.put("PAY_AMT_TWO", "0");
                        // 可付金额
                        linkedRecord.valueMap.put("REQ_AMT", tmp.get("AMT").toString());
                        linkedRecord.textMap.put("REQ_AMT", tmp.get("AMT").toString());
                    } else {
                        // 已付金额
                        BigDecimal priceDetail = bigDecimalSum(list2);
                        linkedRecord.valueMap.put("PAY_AMT_TWO", String.valueOf(priceDetail));
                        linkedRecord.textMap.put("PAY_AMT_TWO", String.valueOf(priceDetail));
                        // 可付金额
                        BigDecimal REQ_AMT = new BigDecimal(tmp.get("AMT").toString());
                        BigDecimal cha = REQ_AMT.subtract(priceDetail);
                        linkedRecord.valueMap.put("REQ_AMT", String.valueOf(cha));
                        linkedRecord.textMap.put("REQ_AMT", String.valueOf(cha));
                    }
                    // 本期支付
                    linkedRecord.valueMap.put("PAY_AMT_ONE", "0");
                    linkedRecord.textMap.put("PAY_AMT_ONE", "0");

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put("99902212142514118", linkedRecordList);
            }
            attLinkResult.childCreatable.put("99902212142514118", false);
            attLinkResult.childClear.put("99902212142514118", true);
        }
        return attLinkResult;
    }

    // list内求和
    private BigDecimal bigDecimalSum(List<Map<String, Object>> list) {
        BigDecimal sum = new BigDecimal(0);
        for (int i = 0; i < list.size(); i++) {
            if (i == 0) {
                String date = list.get(0).get("PAY_AMT_ONE").toString();
                sum = sum.add(new BigDecimal(date));
            } else {
                String date = list.get(i).get("PAY_AMT_TWO").toString();
                sum = sum.add(new BigDecimal(date));
            }
        }
        return sum;
    }

    private AttLinkResult linkForBIDDING_NAME_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String sevId) {
        AttLinkResult attLinkResult = new AttLinkResult();
        // 合同签订的费用明细实体视图
        if ("99799190825103187".equals(sevId)) {
            List<LinkedRecord> linkedRecordList = new ArrayList<>();
            // 查询明细信息
            String sql = "select COST_TYPE_TREE_ID,FEE_DETAIL,TOTAL_AMT from pm_cost_detail where BIDDING_NAME_ID = ? order by id asc";
            List<Map<String, Object>> list = myJdbcTemplate.queryForList(sql, attValue);
            if (!CollectionUtils.isEmpty(list)) {
                for (Map<String, Object> tmp : list) {
                    LinkedRecord linkedRecord = new LinkedRecord();

                    // 费用类型
                    linkedRecord.valueMap.put("COST_TYPE_TREE_ID", tmp.get("COST_TYPE_TREE_ID").toString());
                    linkedRecord.textMap.put("COST_TYPE_TREE_ID", tmp.get("COST_TYPE_TREE_ID").toString());
                    // 费用明细
                    linkedRecord.valueMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL"));
                    linkedRecord.textMap.put("FEE_DETAIL", tmp.get("FEE_DETAIL").toString());
                    // 费用金额
                    linkedRecord.valueMap.put("TOTAL_AMT", tmp.get("TOTAL_AMT"));
                    linkedRecord.textMap.put("TOTAL_AMT", tmp.get("TOTAL_AMT").toString());

                    linkedRecordList.add(linkedRecord);
                }
                attLinkResult.childData.put("99902212142513357", linkedRecordList);
            }
            attLinkResult.childCreatable.put("99902212142513357", false);
            attLinkResult.childClear.put("99902212142513357", true);

        }

        // 根据id查询招投标信息
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("SELECT a.PMS_RELEASE_WAY_ID,a.BID_CTL_PRICE_LAUNCH,a.APPROVE_PURCHASE_TYPE,a.WIN_BID_UNIT_TXT,a.TENDER_OFFER,a.CONTACT_MOBILE_WIN,a.CONTACT_NAME_RECORD,a.BID_USER_ID,a.STATUS,a.BID_UNIT, ifnull((SELECT END_DATETIME FROM wf_process_instance WHERE id = a.LK_WF_INST_ID ),0) as END_DATETIME, a.SERVICE_DAYS FROM po_public_bid_req a WHERE id = ?", attValue);
        if (CollectionUtils.isEmpty(list)) {
            throw new BaseException("采购流程相关属性不完善！");
        }
        Map row = list.get(0);
        // 招采类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "PMS_RELEASE_WAY_ID");

            attLinkResult.attMap.put("PMS_RELEASE_WAY_ID", linkedAtt);
        }
        // 招标控制价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");
            linkedAtt.text = JdbcMapUtil.getString(row, "BID_CTL_PRICE_LAUNCH");

            attLinkResult.attMap.put("BID_CTL_PRICE_LAUNCH", linkedAtt);
        }
        // 采购方式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "APPROVE_PURCHASE_TYPE");
            linkedAtt.text = JdbcMapUtil.getString(row, "APPROVE_PURCHASE_TYPE");

            attLinkResult.attMap.put("PURCHASE_TYPE", linkedAtt);
        }
        // 中标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");
            linkedAtt.text = JdbcMapUtil.getString(row, "WIN_BID_UNIT_TXT");

            attLinkResult.attMap.put("AUTHOR_UNIT", linkedAtt);
            attLinkResult.attMap.put("AUTHOR_UNIT_TEXT", linkedAtt);
            attLinkResult.attMap.put("WIN_BID_UNIT_TXT", linkedAtt);
        }
        // 中标单位报价
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "TENDER_OFFER");
            linkedAtt.text = JdbcMapUtil.getString(row, "TENDER_OFFER");

            attLinkResult.attMap.put("ENTRUSTING_UNIT", linkedAtt);
            attLinkResult.attMap.put("WINNING_BIDS_AMOUNT", linkedAtt);
        }
        // 进场时间
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = JdbcMapUtil.getString(row, "END_DATETIME");
            linkedAtt.text = JdbcMapUtil.getString(row, "END_DATETIME");

            attLinkResult.attMap.put("IN_DATE", linkedAtt);
        }
        // 招标单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BID_UNIT");
            linkedAtt.text = JdbcMapUtil.getString(row, "BID_UNIT");

            attLinkResult.attMap.put("ENTRUSTING_UNIT", linkedAtt);
            attLinkResult.attMap.put("ENTRUSTING_UNIT_TEXT", linkedAtt);
        }
        // 审批状态
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "STATUS");
            linkedAtt.text = JdbcMapUtil.getString(row, "STATUS");

            attLinkResult.attMap.put("APPROVAL_STATUS", linkedAtt);
        }
        // 经办人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            String id = JdbcMapUtil.getString(row, "BID_USER_ID");
            // 根据id查询人员名称
            String name = myJdbcTemplate.queryForList("select name from ad_user where id = ?", id).get(0).get("name").toString();
            linkedAtt.text = name;
            linkedAtt.value = id;

            attLinkResult.attMap.put("PROCURE_USER_ID", linkedAtt);
        }
        // 对方联系人
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTACT_NAME_RECORD");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTACT_NAME_RECORD");

            attLinkResult.attMap.put("OTHER_RESPONSOR", linkedAtt);
        }
        // 对方联系方式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "CONTACT_MOBILE_WIN");
            linkedAtt.text = JdbcMapUtil.getString(row, "CONTACT_MOBILE_WIN");

            attLinkResult.attMap.put("OTHER_CONTACT_PHONE", linkedAtt);
        }
        // 服务周期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "SERVICE_DAYS");
            linkedAtt.text = JdbcMapUtil.getString(row, "SERVICE_DAYS");

            attLinkResult.attMap.put("SERVICE_DAYS", linkedAtt);
        }
        return attLinkResult;
    }

    private AttLinkResult linkForPROJECT_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select v0.id project_type_id,v0.name project_type_name,v1.id CON_SCALE_TYPE_ID,v1.name CON_SCALE_TYPE_NAME,v2.id CON_SCALE_UOM_ID,v2.name CON_SCALE_UOM_NAME from pm_prj_type_link t join gr_set_value v0 on t.PROJECT_TYPE_ID=v0.id join gr_set_value v1 on t.CON_SCALE_TYPE_ID=v1.id join gr_set_value v2 on t.CON_SCALE_UOM_ID=v2.id where t.PROJECT_TYPE_ID=?", attValue);

        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("未设置相应项目类型的联动！");
        } else if (list.size() > 1) {
            throw new BaseException("重复设置相应项目类型的联动！");
        }

        Map row = list.get(0);

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_NAME");

            attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt);
        }

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "CON_SCALE_UOM_ID");
            linkedAtt.text = JdbcMapUtil.getString(row, "CON_SCALE_UOM_NAME");

            attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForPM_PRJ_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode) {
        AttLinkResult attLinkResult = new AttLinkResult();

        // 项目基础信息
        List<Map<String, Object>> list = myJdbcTemplate
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

        if ("PM_PRJ_KICK_OFF_REQ".equals(entCode)) { // 工程开工报审
            String sql = "select PRJ_TOTAL_INVEST,PROJECT_AMT from PM_PRJ_INVEST2 where PM_PRJ_ID = ? and STATUS = 'AP' order by CRT_DT desc limit 1";
            List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql, attValue);
            if (CollectionUtils.isEmpty(map)) {
                throw new BaseException("该项目的总投资、工程费用信息不完善！");
            }
            Map row2 = map.get(0);
            // 总投资
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");
                linkedAtt.text = JdbcMapUtil.getString(row2, "PRJ_TOTAL_INVEST");

                attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
            }
            // 工程费用
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.DOUBLE;
                linkedAtt.value = JdbcMapUtil.getString(row2, "PROJECT_AMT");
                linkedAtt.text = JdbcMapUtil.getString(row2, "PROJECT_AMT");

                attLinkResult.attMap.put("PROJECT_AMT", linkedAtt);
            }
        }

        // 项目编号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_SHORT;
            linkedAtt.value = JdbcMapUtil.getString(row, "prj_code");
            linkedAtt.text = JdbcMapUtil.getString(row, "prj_code");

            attLinkResult.attMap.put("PRJ_CODE", linkedAtt);
        }
        // 建筑面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "BUILDING_AREA");
            linkedAtt.text = JdbcMapUtil.getString(row, "BUILDING_AREA");
            attLinkResult.attMap.put("BUILDING_AREA", linkedAtt);
        }
        // 业主单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "customer_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "customer_name");

            attLinkResult.attMap.put("CUSTOMER_UNIT", linkedAtt);
        }
        // 项目管理模式
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "m_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "m_name");

            attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID", linkedAtt);
        }
        // 建设地点
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "l_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "l_name");

            attLinkResult.attMap.put("BASE_LOCATION_ID", linkedAtt);
        }
        // 占地面积
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "FLOOR_AREA");
            linkedAtt.text = JdbcMapUtil.getString(row, "FLOOR_AREA");

            attLinkResult.attMap.put("FLOOR_AREA", linkedAtt);
        }
        // 项目类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "pt_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "pt_name");

            attLinkResult.attMap.put("PROJECT_TYPE_ID", linkedAtt);
        }
        // 建设规模类型
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "st_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "st_name");

            attLinkResult.attMap.put("CON_SCALE_TYPE_ID", linkedAtt);
        }
        // 建设规模单位
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.REF_SINGLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "su_id");
            linkedAtt.text = JdbcMapUtil.getString(row, "su_name");

            attLinkResult.attMap.put("CON_SCALE_UOM_ID", linkedAtt);
        }
        // 建设规模数量
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "CON_SCALE_QTY");
            linkedAtt.text = JdbcMapUtil.getString(row, "CON_SCALE_QTY");

            attLinkResult.attMap.put("CON_SCALE_QTY", linkedAtt);
        }
        // 建设规模数量2
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "CON_SCALE_QTY2");
            linkedAtt.text = JdbcMapUtil.getString(row, "CON_SCALE_QTY2");

            attLinkResult.attMap.put("CON_SCALE_QTY2", linkedAtt);
        }
        // 建设年限
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "BUILD_YEARS");
            linkedAtt.text = JdbcMapUtil.getString(row, "BUILD_YEARS");
            attLinkResult.attMap.put("BUILD_YEARS", linkedAtt);
        }
        // 项目概况
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PRJ_SITUATION");
            linkedAtt.text = JdbcMapUtil.getString(row, "PRJ_SITUATION");

            attLinkResult.attMap.put("PRJ_SITUATION", linkedAtt);
        }
        // 批复文号
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");
            linkedAtt.text = JdbcMapUtil.getString(row, "PRJ_REPLY_NO");

            attLinkResult.attMap.put("PRJ_REPLY_NO", linkedAtt);
        }
        // 批复日期
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            linkedAtt.value = JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");
            linkedAtt.text = JdbcMapUtil.getString(row, "PRJ_REPLY_DATE");

            attLinkResult.attMap.put("PRJ_REPLY_DATE", linkedAtt);
        }
        // 批复材料
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");
            linkedAtt.text = JdbcMapUtil.getString(row, "PRJ_REPLY_FILE");

            attLinkResult.attMap.put("PRJ_REPLY_FILE", linkedAtt);
        }
        // 资金来源
        {
            String id = JdbcMapUtil.getString(row, "INVESTMENT_SOURCE_ID");
            String sqlName = "select name from gr_set_value where id = ?";
            List<Map<String, Object>> nameMap = myJdbcTemplate.queryForList(sqlName, id);
            String name = JdbcMapUtil.getString(nameMap.get(0), "name");
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = id;
            linkedAtt.text = name;

            attLinkResult.attMap.put("INVESTMENT_SOURCE_ID", linkedAtt);
            attLinkResult.attMap.put("PM_FUND_SOURCE_ID", linkedAtt);
        }
        // 可研批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "FS");
            linkedAtt.text = JdbcMapUtil.getString(row, "FS");

            attLinkResult.attMap.put("FEASIBILITY_APPROVE_FUND", linkedAtt);
        }
        // 初概批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "PD");
            linkedAtt.text = JdbcMapUtil.getString(row, "PD");

            attLinkResult.attMap.put("ESTIMATE_APPROVE_FUND", linkedAtt);
        }
        // 财评批复资金
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(row, "budget");
            linkedAtt.text = JdbcMapUtil.getString(row, "budget");

            attLinkResult.attMap.put("EVALUATION_APPROVE_FUND", linkedAtt);
        }

        // 资金信息回显。优先级 可研估算<初设概算<预算财评
        List<String> amtList = getAmtList();
        if (amtList.contains(entCode)) {
            // 查询预算财评信息
            Map resultRow = getAmtMap(attValue);
            attLinkResult = getResult(resultRow, attLinkResult);
        }

        return attLinkResult;
    }

    private AttLinkResult linkForSTATUS(String attValue, String sevId) {
        AttLinkResult attLinkResult = null;
        if ("99626673179203336".equals(sevId)) {
            // 实体视图ID=99626673179203336,当前触发联动的实体视图是”测试学生“：

            attLinkResult = linkForSTATUSofTestStu(attValue);
        } else if ("99626673179203381".equals(sevId)) {
            // 实体视图ID=99626673179203381,当前触发联动的实体视图是”测试老师“：

            attLinkResult = linkForSTATUSofTestClass(attValue);
        }
        return attLinkResult;
    }

    private AttLinkResult linkForSTATUSofTestStu(String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "已批准";
                linkedAtt.text = "已批准";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "审批中";
                linkedAtt.text = "审批中";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "已拒绝";
                linkedAtt.text = "已拒绝";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "草稿";
                linkedAtt.text = "草稿";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "已作废";
                linkedAtt.text = "已作废";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "作废中";
                linkedAtt.text = "作废中";
            }
            attLinkResult.attMap.put("NAME", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "已批准备注";
                linkedAtt.text = "已批准备注";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "审批中备注";
                linkedAtt.text = "审批中备注";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "已拒绝备注";
                linkedAtt.text = "已拒绝备注";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "草稿备注";
                linkedAtt.text = "草稿备注";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "已作废备注";
                linkedAtt.text = "已作废备注";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "作废中备注";
                linkedAtt.text = "作废中备注";
            }
            attLinkResult.attMap.put("REMARK", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.INTEGER;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "1";
                linkedAtt.text = "1";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2";
                linkedAtt.text = "2";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "3";
                linkedAtt.text = "3";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "4";
                linkedAtt.text = "4";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "5";
                linkedAtt.text = "5";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "6";
                linkedAtt.text = "6";
            }
            attLinkResult.attMap.put("CALC_TIMES", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "1.10";
                linkedAtt.text = "1.10";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2.10";
                linkedAtt.text = "2.10";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "3.10";
                linkedAtt.text = "3.10";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "4.10";
                linkedAtt.text = "4.10";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "5.10";
                linkedAtt.text = "5.10";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "6.10";
                linkedAtt.text = "6.10";
            }
            attLinkResult.attMap.put("SIGN_AMT", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATE;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "2022-01-01";
                linkedAtt.text = "2022-01-01";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2022-01-02";
                linkedAtt.text = "2022-01-02";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "2022-01-03";
                linkedAtt.text = "2022-01-03";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "2022-01-04";
                linkedAtt.text = "2022-01-04";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "2022-01-05";
                linkedAtt.text = "2022-01-05";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "2022-01-06";
                linkedAtt.text = "2022-01-06";
            }
            attLinkResult.attMap.put("SIGN_DATE", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TIME;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "10:10:10";
                linkedAtt.text = "10:10:10";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "11:11:11";
                linkedAtt.text = "11:11:11";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "12:12:12";
                linkedAtt.text = "12:12:12";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "13:13:13";
                linkedAtt.text = "13:13:13";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "14:14:14";
                linkedAtt.text = "14:14:14";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "15:15:15";
                linkedAtt.text = "15:15:15";
            }
            attLinkResult.attMap.put("TEST_TIME", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DATETIME;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "2022-01-01 10:10:10";
                linkedAtt.text = "2022-01-0110:10:10";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "2022-01-02 11:11:11";
                linkedAtt.text = "2022-01-02 11:11:11";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "2022-01-03 12:12:12";
                linkedAtt.text = "2022-01-03 12:12:12";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "2022-01-04 13:13:13";
                linkedAtt.text = "2022-01-04 13:13:13";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "2022-01-05 14:14:14";
                linkedAtt.text = "2022-01-05 14:14:14";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "2022-01-06 15:15:15";
                linkedAtt.text = "2022-01-06 15:15:15";
            }
            attLinkResult.attMap.put("CALC_DTTM", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.BOOLEAN;
            if ("AP".equals(attValue)) {
                linkedAtt.value = "true";
                linkedAtt.text = "true";
            } else if ("APING".equals(attValue)) {
                linkedAtt.value = "false";
                linkedAtt.text = "false";
            } else if ("DN".equals(attValue)) {
                linkedAtt.value = "true";
                linkedAtt.text = "true";
            } else if ("DR".equals(attValue)) {
                linkedAtt.value = "false";
                linkedAtt.text = "false";
            } else if ("VD".equals(attValue)) {
                linkedAtt.value = "true";
                linkedAtt.text = "true";
            } else if ("VDING".equals(attValue)) {
                linkedAtt.value = "false";
                linkedAtt.text = "false";
            }
            attLinkResult.attMap.put("CALC_SUCC", linkedAtt);
        }
        return attLinkResult;
    }

    private AttLinkResult linkForSTATUSofTestClass(String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        boolean ap = "AP".equals(attValue);

        // 附件：
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = "99952822476358787,99952822476358788";
            linkedAtt.text = "/qygly-gateway/qygly-file/viewImage?fileId=99952822476358787,/qygly-gateway/qygly-file/viewImage?fileId=99952822476358788";

            getFileInfoList(linkedAtt);

            attLinkResult.attMap.put("ATT_FILE_GROUP_ID", linkedAtt);
        }

        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_LONG;
            linkedAtt.value = "状态" + (ap ? "“已批准”" : "”非已批准“") + "联动后的备注";
            linkedAtt.text = "状态" + (ap ? "“已批准”" : "”非已批准“") + "联动后的备注";

            linkedAtt.changeToName = "状态" + (ap ? "“已批准”" : "”非已批准“") + "联动后的备注";
            linkedAtt.changeToShown = ap;
            linkedAtt.changeToEditable = ap;
            linkedAtt.changeToMandatory = ap;

            attLinkResult.attMap.put("REMARK", linkedAtt);
        }
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.TEXT_SHORT;
            long l = System.currentTimeMillis();
            linkedAtt.value = "名称" + l;
            linkedAtt.text = "名称" + l;

            attLinkResult.attMap.put("NAME", linkedAtt);
        }
        attLinkResult.childClear.put("99626673179203460", ap);// 测试学生的视图部分ID，不是实体视图ID
        attLinkResult.childClear.put("99902212142028120", !ap);// 测试老师的视图部分ID，不是实体视图ID

        attLinkResult.childCreatable.put("99626673179203460", ap);// 测试学生的视图部分ID，不是实体视图ID
        attLinkResult.childCreatable.put("99902212142028120", !ap);// 测试老师的视图部分ID，不是实体视图ID

        attLinkResult.childData.put("99626673179203460", null);// 测试学生的视图部分ID，不是实体视图ID

        List<LinkedRecord> linkedRecordList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            LinkedRecord linkedRecord = new LinkedRecord();
            linkedRecord.valueMap.put("NAME", "老师" + (i + 1));
            linkedRecord.textMap.put("NAME", "老师" + (i + 1));

            linkedRecordList.add(linkedRecord);
        }
        attLinkResult.childData.put("99902212142028120", linkedRecordList);// 测试老师的视图部分ID，不是实体视图ID

        return attLinkResult;
    }

    /**
     * 为联动的属性获取文件信息列表。
     *
     * @param linkedAtt
     */
    private void getFileInfoList(LinkedAtt linkedAtt) {
        if (SharedUtil.isEmptyObject(linkedAtt.value)) {
            return;
        }

        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String[] idArr = linkedAtt.value.toString().split(",");
        List<String> idList = Arrays.asList(idArr);
        Map<String, Object> map = new HashMap<>();
        map.put("ids", idList);
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select t.*, crt_user.code crt_user_code, crt_user.name crt_user_name from fl_file t left join ad_user crt_user on t.crt_user_id = crt_user.id where t.id in (:ids)", map);
        if (list.size() > 0) {
            linkedAtt.fileInfoList = new ArrayList<>(list.size());

            for (Map<String, Object> row : list) {
                LinkedAttFileInfo fileInfo = new LinkedAttFileInfo();
                linkedAtt.fileInfoList.add(fileInfo);

                fileInfo.attachmentUrl = JdbcMapUtil.getString(row, "FILE_ATTACHMENT_URL");
                fileInfo.code = JdbcMapUtil.getString(row, "CODE");
                fileInfo.crtUserCode = JdbcMapUtil.getString(row, "CRT_USER_CODE");
                fileInfo.crtUserName = JdbcMapUtil.getString(row, "CRT_USER_NAME");
                fileInfo.dspName = JdbcMapUtil.getString(row, "DSP_NAME");
                fileInfo.dspSize = JdbcMapUtil.getString(row, "DSP_SIZE");
                fileInfo.ext = JdbcMapUtil.getString(row, "EXT");
                fileInfo.id = JdbcMapUtil.getString(row, "ID");
                fileInfo.inlineUrl = JdbcMapUtil.getString(row, "FILE_INLINE_URL");
                fileInfo.name = JdbcMapUtil.getString(row, "NAME");
                fileInfo.sizeKiloByte = JdbcMapUtil.getDouble(row, "SIZE_KB");
                fileInfo.uploadDttm = DateTimeUtil.dttmToString(JdbcMapUtil.getObject(row, "UPLOAD_DTTM"));
            }
        }
    }

    // 默认未涉及
    private void initNUll(AttLinkResult attLinkResult) {
        List<String> fields = Arrays.asList("CREATE_PROJECT_COMPLETED", "FEASIBILITY_COMPLETED",
                "SELECT_SITE_COMPLETED", "EIA_COMPLETED", "USE_LAND_COMPLETED", "WOODLAND_WATER_SOIL_COMPLETED",
                "ESTIMATE_COMPLETED", "BUDGET_REVIEW_COMPLETED", "CONSTRUCT_BID_COMPLETED");
        for (String field : fields) {
            {
                LinkedAtt linkedAtt = new LinkedAtt();
                linkedAtt.type = AttDataTypeE.TEXT_LONG;
                linkedAtt.value = "99902212142036278";
                linkedAtt.text = "未涉及";
//                linkedAtt.text = "99902212142036278";
                attLinkResult.attMap.put(field, linkedAtt);
            }
        }
    }

    // 获取资金信息
    private AttLinkResult getResult(Map<String, Object> stringObjectMap, AttLinkResult attLinkResult) {
//        AttLinkResult attLinkResult = new AttLinkResult();
//        attLinkResult.attMap = new HashMap<>();
        // 总投资
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST");
            linkedAtt.text = JdbcMapUtil.getString(stringObjectMap, "PRJ_TOTAL_INVEST");

            attLinkResult.attMap.put("PRJ_TOTAL_INVEST", linkedAtt);
        }
        // 工程费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT");
            linkedAtt.text = JdbcMapUtil.getString(stringObjectMap, "PROJECT_AMT");

            attLinkResult.attMap.put("PROJECT_AMT", linkedAtt);
        }
        // 工程建设其他费用
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT");
            linkedAtt.text = JdbcMapUtil.getString(stringObjectMap, "PROJECT_OTHER_AMT");

            attLinkResult.attMap.put("PROJECT_OTHER_AMT", linkedAtt);
        }
        // 预备费
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT");
            linkedAtt.text = JdbcMapUtil.getString(stringObjectMap, "PREPARE_AMT");

            attLinkResult.attMap.put("PREPARE_AMT", linkedAtt);
        }
        // 利息
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.DOUBLE;
            linkedAtt.value = JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST");
            linkedAtt.text = JdbcMapUtil.getString(stringObjectMap, "CONSTRUCT_PERIOD_INTEREST");

            attLinkResult.attMap.put("CONSTRUCT_PERIOD_INTEREST", linkedAtt);
        }
        return attLinkResult;
    }

    public boolean judgeMatch(List<String> words, String name) {
        for (String word : words) {
            if (name.contains(word)) {
                return true;
            }
        }
        return false;
    }

    // 资金信息回显，按照优先级 可研估算<初设概算<预算财评 的流程
    public List<String> getAmtList() {
        List<String> list = new ArrayList<>();
        list.add("PM_SUPERVISE_PLAN_REQ"); // 监理规划及细则申请
        list.add("PM_PRJ_PARTY_REQ"); // 五方责任主体维护申请
        list.add("PO_ORDER_TERMINATE_REQ"); // 采购合同终止申请
        list.add("PO_ORDER_CHANGE_REQ"); // 采购合同变更申请
        list.add("PO_ORDER_SUPPLEMENT_REQ"); // 采购合同补充协议申请
        list.add("PO_ORDER_REQ"); // 采购合同签订申请
        list.add("PO_PUBLIC_BID_REQ"); // 采购公开招标申请
        list.add("PM_BUILD_ORGAN_PLAN_REQ"); // 施工组织设计及施工方案
        list.add("PM_WORK_LIST_REQ"); // 工作联系单
        return list;
    }

    // 查询可研估算<初设概算<预算财评优先级最高的数据
    private Map getAmtMap(String attValue) {
        Map resultRow = new HashMap();
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String sql1 = "SELECT PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST3 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
        List<Map<String, Object>> map = myJdbcTemplate.queryForList(sql1, attValue);
        List<Map<String, Object>> map1 = new ArrayList<>();
        List<Map<String, Object>> map2 = new ArrayList<>();
        if (CollectionUtils.isEmpty(map)) {
            // 初设概算信息
            String sql2 = "SELECT PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST2 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
            map1 = myJdbcTemplate.queryForList(sql2, attValue);
            if (CollectionUtils.isEmpty(map1)) {
                String sql3 = "SELECT PRJ_TOTAL_INVEST,PROJECT_AMT,CONSTRUCT_AMT,PROJECT_OTHER_AMT,PREPARE_AMT,CONSTRUCT_PERIOD_INTEREST FROM PM_PRJ_INVEST1 WHERE PM_PRJ_ID = ? and status = 'AP' order by CRT_DT desc limit 1";
                map2 = myJdbcTemplate.queryForList(sql3, attValue);
                if (!CollectionUtils.isEmpty(map2)) {
                    resultRow = map2.get(0);
                }
            } else {
                resultRow = map1.get(0);
            }
        } else {
            resultRow = map.get(0);
        }
        return resultRow;
    }

    // 属性联动中需要单独查询额外字典名称的key
    public List<String> getKeyList() {
        List<String> keyList = new ArrayList<>();
        keyList.add("IS_BUDGET_ID");  // 是否市财政预算政府投资计划项目
        keyList.add("FEASIBILITY_COMPLETED");  // 可研完成情况
        keyList.add("CREATE_PROJECT_COMPLETED"); // 立项完成情况
        keyList.add("SELECT_SITE_COMPLETED"); // 规划选址完成情况
        keyList.add("USE_LAND_COMPLETED"); // 用地预审完成情况
        keyList.add("EIA_COMPLETED"); // 环评审批完成情况
        keyList.add("WOODLAND_WATER_SOIL_COMPLETED"); // 林地、水土保持、节能完成情况
        keyList.add("ESTIMATE_COMPLETED"); // 概算完成情况
        keyList.add("BUDGET_REVIEW_COMPLETED"); // 预算评审完成情况
        keyList.add("CONSTRUCT_BID_COMPLETED"); // 施工招标备案完成情况
        return keyList;
    }
}
