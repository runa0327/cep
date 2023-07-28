package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.link.LinkedAttFileInfo;
import com.cisdi.ext.link.LinkedRecord;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 状态属性联动
 */
public class StatusLink {

    /**
     * 状态属性联动-入口
     * @param attValue 属性联动值
     * @param sevId 视图id
     * @return 属性联动结果
     */
    public static AttLinkResult linkForSTATUS(String attValue, String sevId) {
        AttLinkResult attLinkResult = null;
        if ("0099626673179203336".equals(sevId)) {
            // 实体视图ID=0099626673179203336,当前触发联动的实体视图是”测试学生“：

            attLinkResult = linkForSTATUSofTestStu(attValue);
        } else if ("0099626673179203381".equals(sevId)) {
            // 实体视图ID=0099626673179203381,当前触发联动的实体视图是”测试老师“：

            attLinkResult = linkForSTATUSofTestClass(attValue);
        }
        return attLinkResult;
    }

    private static AttLinkResult linkForSTATUSofTestClass(String attValue) {
        AttLinkResult attLinkResult = new AttLinkResult();

        boolean ap = "AP".equals(attValue);

        // 附件：
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = "0099952822476358787,0099952822476358788";
//            linkedAtt.text = "/qygly-gateway/qygly-file/viewImage?fileId=0099952822476358787,/qygly-gateway/qygly-file/viewImage?fileId=0099952822476358788";

            getFileInfoList(linkedAtt);

            attLinkResult.attMap.put("ATT_FILE_GROUP_ID", linkedAtt);
        }
        // 附件：
        {
            LinkedAtt linkedAtt = new LinkedAtt();
            linkedAtt.type = AttDataTypeE.FILE_GROUP;
            linkedAtt.value = "0099952822476358787,0099952822476358788";
//            linkedAtt.text = "/qygly-gateway/qygly-file/viewImage?fileId=0099952822476358787,/qygly-gateway/qygly-file/viewImage?fileId=0099952822476358788";

            getFileInfoList(linkedAtt);

            attLinkResult.attMap.put("APPROVE_FILE_ID_TWO", linkedAtt);
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
        attLinkResult.childClear.put("0099626673179203460", ap);// 测试学生的视图部分ID，不是实体视图ID
        attLinkResult.childClear.put("0099902212142028120", !ap);// 测试老师的视图部分ID，不是实体视图ID

        attLinkResult.childCreatable.put("0099626673179203460", ap);// 测试学生的视图部分ID，不是实体视图ID
        attLinkResult.childCreatable.put("0099902212142028120", !ap);// 测试老师的视图部分ID，不是实体视图ID

        attLinkResult.childData.put("0099626673179203460", null);// 测试学生的视图部分ID，不是实体视图ID

        List<LinkedRecord> linkedRecordList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            LinkedRecord linkedRecord = new LinkedRecord();
            linkedRecord.valueMap.put("NAME", "老师" + (i + 1));
            linkedRecord.textMap.put("NAME", "老师" + (i + 1));

            linkedRecordList.add(linkedRecord);
        }
        attLinkResult.childData.put("0099902212142028120", linkedRecordList);// 测试老师的视图部分ID，不是实体视图ID

        return attLinkResult;
    }

    private static void getFileInfoList(LinkedAtt linkedAtt) {
        if (SharedUtil.isEmptyObject(linkedAtt.value)) {
            return;
        }

        MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
        String[] idArr = linkedAtt.value.toString().split(",");
        List<String> idList = Arrays.asList(idArr);
        Map<String, Object> map = new HashMap<>();
        map.put("ids", idList);
        List<Map<String, Object>> list = myNamedParameterJdbcTemplate.queryForList("select t.*, crt_user.code crt_user_code, crt_user.name crt_user_name from fl_file t left join ad_user crt_user on t.crt_user_id = crt_user.id where t.id in (:ids)", map);
        if (!CollectionUtils.isEmpty(list)) {
            linkedAtt.fileInfoList = new ArrayList<>(list.size());
            StringBuilder sb = new StringBuilder();
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
                String url = JdbcMapUtil.getString(row, "FILE_INLINE_URL");
                fileInfo.inlineUrl = url;
                fileInfo.name = JdbcMapUtil.getString(row, "NAME");
                fileInfo.sizeKiloByte = Double.parseDouble(JdbcMapUtil.getString(row, "SIZE_KB"));
                fileInfo.uploadDttm = DateTimeUtil.dttmToString(JdbcMapUtil.getObject(row, "UPLOAD_DTTM"));
                sb.append(url).append(",");
            }
            linkedAtt.text = sb.substring(0,sb.length()-1);
        }
    }

    private static AttLinkResult linkForSTATUSofTestStu(String attValue) {
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
}
