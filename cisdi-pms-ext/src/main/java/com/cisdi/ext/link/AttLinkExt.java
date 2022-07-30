package com.cisdi.ext.link;

import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.interaction.TypeValueText;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AttLinkExt {

    private AttLinkResult projectLink(String PM_PRJ_REQ_ID) {
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();

        List<Map<String, Object>> list = jdbcTemplate.queryForList("select * from pm_prj_req t where t.id=? and t.status='AP' LIMIT 1", PM_PRJ_REQ_ID);

        List<Map<String, Object>> list1 = jdbcTemplate.queryForList("select * from pm_prj_invest1 t where t.PM_PRJ_REQ_ID=? and t.status='AP' LIMIT 1", PM_PRJ_REQ_ID);

        List<Map<String, Object>> list2 = jdbcTemplate.queryForList("select * from pm_prj_invest2 t where t.PM_PRJ_REQ_ID=? and t.status='AP' LIMIT 1", PM_PRJ_REQ_ID);

        List<Map<String, Object>> list3 = jdbcTemplate.queryForList("select * from pm_prj_invest3 t where t.PM_PRJ_REQ_ID=? and t.status='AP' LIMIT 1", PM_PRJ_REQ_ID);

        AttLinkResult projectLinkResult = new AttLinkResult();
        projectLinkResult.attMap = new HashMap<>();

        // 遍历4组数据，相同的属性以后面的为准：
        extracted(jdbcTemplate, list, projectLinkResult);
        extracted(jdbcTemplate, list1, projectLinkResult);
        extracted(jdbcTemplate, list2, projectLinkResult);
        extracted(jdbcTemplate, list3, projectLinkResult);

        projectLinkResult.attMap.remove("LK_WF_INST_ID");
        projectLinkResult.attMap.remove("VER");
        projectLinkResult.attMap.remove("IS_PRESET");
        projectLinkResult.attMap.remove("STATUS");
        projectLinkResult.attMap.remove("ID");
        projectLinkResult.attMap.remove("REMARK");
        projectLinkResult.attMap.remove("LAST_MODI_USER_ID");
        projectLinkResult.attMap.remove("LAST_MODI_DT");
        projectLinkResult.attMap.remove("NAME");
        projectLinkResult.attMap.remove("CODE");
        projectLinkResult.attMap.remove("CRT_DT");
        projectLinkResult.attMap.remove("CRT_USER_ID");
        projectLinkResult.attMap.remove("TS");

        projectLinkResult.attMap.remove("CRT_DEPT_ID");

        return projectLinkResult;
    }

    private void extracted(JdbcTemplate jdbcTemplate, List<Map<String, Object>> list, AttLinkResult projectLinkResult) {
        if (!SharedUtil.isEmptyList(list)) {
            Map row = list.get(0);
            for (Object keyObject : row.keySet()) {
                String key = keyObject.toString();
                String value = JdbcMapUtil.getString(row, key);

                TypeValueText typeValueText = new TypeValueText();
                typeValueText.value = value;
                // 将text默认和id相同：
                typeValueText.text = value;

                // 若为引用，且值非空，再将text改为被引用的记录的name：
                List<Map<String, Object>> ll = jdbcTemplate.queryForList("select e.code refed_table_name,st.AD_ATT_TYPE_ID from ad_att a,AD_ATT_SUB_TYPE st,ad_single_ent_view sev,ad_ent e where a.code=? and a.REFED_SEV_ID=sev.id and sev.AD_ENT_ID=e.id and a.AD_ATT_SUB_TYPE_ID=st.id", key);
                if (!SharedUtil.isEmptyList(ll)) {
                    Map<String, Object> rr = ll.get(0);
                    String refed_table_name = JdbcMapUtil.getString(rr, "refed_table_name");
                    String AD_ATT_TYPE_ID = JdbcMapUtil.getString(rr, "AD_ATT_TYPE_ID");

                    typeValueText.type = AttDataTypeE.valueOf(AD_ATT_TYPE_ID);

                    if (!SharedUtil.isEmptyObject(value)) {
                        List<Map<String, Object>> refedList = jdbcTemplate.queryForList("select * from " + refed_table_name + " t where t.id=?", value);
                        if (!SharedUtil.isEmptyList(refedList)) {
                            Map<String, Object> refedRow = refedList.get(0);
                            typeValueText.text = JdbcMapUtil.getString(refedRow, "name");
                        }
                    }
                } else {
                    String AD_ATT_TYPE_ID = jdbcTemplate.queryForMap("select st.AD_ATT_TYPE_ID from ad_att a,AD_ATT_SUB_TYPE st where a.code=? and a.AD_ATT_SUB_TYPE_ID=st.id", key).get("AD_ATT_TYPE_ID").toString();

                    typeValueText.type = AttDataTypeE.valueOf(AD_ATT_TYPE_ID);

                    if ("BOOLEAN".equals(AD_ATT_TYPE_ID)) {
                        typeValueText.text = (SharedUtil.isEmptyString(typeValueText.text) ? null : ("1".equals(typeValueText.text) ? "是" : "否"));
                    }
                }

                if (typeValueText.type == AttDataTypeE.DATETIME) {
                    if (!SharedUtil.isEmptyString(typeValueText.value)) {
                        typeValueText.value = typeValueText.value.replaceAll("T", " ");
                        typeValueText.text = typeValueText.text.replaceAll("T", " ");
                    }
                }

                if (typeValueText.type != AttDataTypeE.FILE_GROUP) {
                    projectLinkResult.attMap.put(key, typeValueText);
                }
            }
        }
    }

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
        if ("PM_PRJ_REQ_ID".equals(attCode)) {
            return projectLink(attValue);
        } else if ("PROJECT_TYPE_ID".equals(attCode)) {
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

            List<Map<String, Object>> list = jdbcTemplate.queryForList("select t.code prj_code,c.id customer_id,c.name customer_name,m.id m_id,m.name m_name,l.id l_id,l.name l_name,t.FLOOR_AREA,pt.id pt_id,pt.name pt_name,st.id st_id,st.name st_name,su.id su_id,su.name su_name,t.CON_SCALE_QTY,t.CON_SCALE_QTY2,t.PRJ_SITUATION from pm_prj t join PM_PARTY c on t.id=? and t.CUSTOMER_UNIT=c.id join gr_set_value m on t.PRJ_MANAGE_MODE_ID = m.ID join gr_set_value l on t.BASE_LOCATION_ID=l.id join gr_set_value pt on t.PROJECT_TYPE_ID=pt.id join gr_set_value st on t.CON_SCALE_TYPE_ID=st.id join gr_set_value su on t.CON_SCALE_UOM_ID=su.id", attValue);

            if (list.size() == 0) {
                throw new BaseException("项目的相关属性不完整！");
            }

            Map row = list.get(0);

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
                typeValueText.value = JdbcMapUtil.getString(row, "PRJ_SITUATION");
                typeValueText.text = JdbcMapUtil.getString(row, "PRJ_SITUATION");

                attLinkResult.attMap.put("PRJ_SITUATION", typeValueText);
            }

            return attLinkResult;
        } else {
            throw new BaseException("属性联动的参数的attCode不是PM_PRJ_REQ_ID或PROJECT_TYPE_ID！");
        }
    }
}
