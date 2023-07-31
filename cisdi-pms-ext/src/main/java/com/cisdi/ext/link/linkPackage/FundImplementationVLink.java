package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkParam;
import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkedAtt;
import com.cisdi.ext.util.StringUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资金来源属性联动
 */
public class FundImplementationVLink {

    /**
     * 资金来源-属性联动-入口
     * @param myJdbcTemplate 数据源
     * @param attValue 属性联动值
     * @param entCode 业务表名
     * @param sevId 视图id
     * @param param 参数
     * @return 属性联动结果
     */
    public static AttLinkResult linkFUND_IMPLEMENTATION_V_ID(MyJdbcTemplate myJdbcTemplate, String attValue, String entCode, String sevId, AttLinkParam param) {
        AttLinkResult attLinkResult = new AttLinkResult();
        //专项资金支付选择资金来源后联动
        if ("FUND_SPECIAL".equals(entCode) || "FUND_NEWLY_INCREASED_DETAIL".equals(entCode)){
            String fundSource = attValue;//资金来源
            Map<String, Object> fundSourceMap = myJdbcTemplate.queryForMap("select fi.FUND_SOURCE_TEXT name from fund_implementation fi left join" +
                    " fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id where fid.id = ?", fundSource);
            String fundSourceName = JdbcMapUtil.getString(fundSourceMap, "name");
            String prjId = JdbcMapUtil.getString(param.valueMap, "PM_PRJ_ID");//项目id
            if (Strings.isNullOrEmpty(prjId)){
                throw new BaseException("请先选择项目");
            }
            //数据联动主页面sql
            String baseSql = "select fi.id fundImpId,fi.FUND_SOURCE_TEXT,fid.PM_PRJ_ID,IFNULL(temp1.appAmt,0) appAmt,IFNULL(temp2.cumReachAmt,0) " +
                    "cumReachAmt,IFNULL(temp3.cumPayAmt,0) cumPayAmt,IFNULL(temp1.appAmt,0)-IFNULL(temp2.cumReachAmt,0) notReachAmt,pa.name " +
                    "customerUnitName,pa.id customerUnitId,sv.name manageModeName,sv.id manageModeId,IFNULL(temp4.cumBuildReachAmt,0) cumBuildReachAmt,IFNULL(temp5.cumAcqReachAmt,0) cumAcqReachAmt\n" +
                    "from fund_implementation fi left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                    "left join (select sum(IFNULL(fid.APPROVED_AMOUNT,0)) appAmt,fid.PM_PRJ_ID,fi.FUND_SOURCE_TEXT from fund_implementation_detail" +
                    " fid left join fund_implementation fi on fi.id = fid.FUND_IMPLEMENTATION_ID group by fid.PM_PRJ_ID,fi.FUND_SOURCE_TEXT) temp1" +
                    " on temp1.PM_PRJ_ID = fid.PM_PRJ_ID and temp1.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                    "left join (select sum(IFNULL(fr.REACH_AMOUNT,0)) cumReachAmt,fr.FUND_SOURCE_TEXT from fund_reach fr group by fr" +
                    ".FUND_SOURCE_TEXT,fr.pm_prj_id\n" +
                    ") temp2 on temp2.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                    "left join (select sum(IFNULL(fs.PAID_AMT,0)) cumPayAmt,fs.FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID from fund_special fs group by " +
                    "fs.FUND_IMPLEMENTATION_V_ID,fs.PM_PRJ_ID) temp3 on temp3.FUND_IMPLEMENTATION_V_ID = fid.id and temp3.PM_PRJ_ID =" +
                    " fid.PM_PRJ_ID\n" +
                    "left join pm_prj pr on pr.id = fid.PM_PRJ_ID\n" +
                    "left join PM_PARTY pa on pa.id = pr.CUSTOMER_UNIT\n" +
                    "left join gr_set_value sv on sv.id = pr.PRJ_MANAGE_MODE_ID\n" +
                    "left join gr_set se on se.id = sv.GR_SET_ID and se.code = 'management_unit'\n" +
                    "left join (select sum(IFNULL(fr.REACH_AMOUNT,0)) cumBuildReachAmt,fr.FUND_SOURCE_TEXT from fund_reach fr where fr" +
                    ".FUND_REACH_CATEGORY = '0099952822476371282' group by fr.FUND_SOURCE_TEXT,fr.pm_prj_id\n" +
                    ") temp4 on temp4.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
                    "left join (select sum(IFNULL(fr.REACH_AMOUNT,0)) cumAcqReachAmt,fr.FUND_SOURCE_TEXT from fund_reach fr where fr" +
                    ".FUND_REACH_CATEGORY = '0099952822476371281' group by fr.FUND_SOURCE_TEXT,fr.pm_prj_id\n" +
                    ") temp5 on temp5.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT where fi.FUND_SOURCE_TEXT = ? and fid.PM_PRJ_ID = ? ";
            //基础统计回显
            List<Map<String, Object>> priStatistic = myJdbcTemplate.queryForList(baseSql, fundSourceName, prjId);

            //查询银行到位余额
//            String bankReachSql = "select reachTemp.bankName,reachTemp.FUND_SOURCE_TEXT soureName,appTemp.appAmt - reachTemp.reachAmt reachBalance " +
//                    "from (select rb.name bankName,re.FUND_SOURCE_TEXT,sum(re.REACH_AMOUNT) reachAmt,re.PM_PRJ_ID\n" +
//                    "from fund_reach re\n" +
//                    "left join fund_implementation fi on re.FUND_SOURCE_TEXT = fi.FUND_SOURCE_TEXT\n" +
//                    "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id and fid.PM_PRJ_ID = re.PM_PRJ_ID\n" +
//                    "left join receiving_bank rb on rb.id = re.RECEIVING_BANK_ID\n" +
//                    "group by re.RECEIVING_BANK_ID,re.FUND_SOURCE_TEXT,re.PM_PRJ_ID) reachTemp\n" +
//                    "left join (select fi.FUND_SOURCE_TEXT,fid.PM_PRJ_ID,sum(fid.APPROVED_AMOUNT) appAmt\n" +
//                    "\tfrom fund_implementation fi\n" +
//                    "\tleft join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
//                    "\tgroup by fi.FUND_SOURCE_TEXT,fid.PM_PRJ_ID\n" +
//                    ") appTemp on appTemp.FUND_SOURCE_TEXT = reachTemp.FUND_SOURCE_TEXT and appTemp.PM_PRJ_ID = reachTemp.PM_PRJ_ID\n" +
//                    "where reachTemp.FUND_SOURCE_TEXT = ? and reachTemp.PM_PRJ_ID = ?";
//            myJdbcTemplate.queryForList(bankReachSql,fundSourceName,prjId);

            if (!CollectionUtils.isEmpty(priStatistic)){
                Map<String, Object> priStatisticMap = priStatistic.get(0);
                //批复金额(APPROVED_AMOUNT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"appAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"appAmt");
                    attLinkResult.attMap.put("APPROVED_AMOUNT",linkedAtt);
                }

                //累计到位金额(CUM_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumReachAmt");
                    attLinkResult.attMap.put("CUM_REACH_AMT",linkedAtt);
                }

                //累计支付金额(CUM_PAY_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumPayAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumPayAmt");
                    attLinkResult.attMap.put("CUM_PAY_AMT",linkedAtt);
                }

                //未到位资金(NOT_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    attLinkResult.attMap.put("NOT_REACH_AMT",linkedAtt);
                }

                //业主单位(CUSTOMER_UNIT),引用（单值）
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"customerUnitId");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"customerUnitName");
                    attLinkResult.attMap.put("CUSTOMER_UNIT",linkedAtt);
                }

                //项目管理模式(PRJ_MANAGE_MODE_ID),引用（单值）(代管单位)
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"manageModeId");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"manageModeName");
                    attLinkResult.attMap.put("PRJ_MANAGE_MODE_ID",linkedAtt);
                }

                //累计到位建设资金(CUM_BUILD_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumBuildReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumBuildReachAmt");
                    attLinkResult.attMap.put("CUM_BUILD_REACH_AMT",linkedAtt);
                }

                //累计到位征拆资金(CUM_ACQ_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"cumAcqReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"cumAcqReachAmt");
                    attLinkResult.attMap.put("CUM_ACQ_REACH_AMT",linkedAtt);
                }

                //剩余到位资金(NOT_REACH_AMT),双精度浮点
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.DOUBLE;
                    linkedAtt.value = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    linkedAtt.text = JdbcMapUtil.getString(priStatisticMap,"notReachAmt");
                    attLinkResult.attMap.put("SUR_REACH_AMT",linkedAtt);
                }

                //支付明细码(FUND_PAY_CODE),文本（短）
                //查询相同日期专项资金支付条数
                Map<String, Object> countToday = myJdbcTemplate.queryForMap("select count(*) countToday from fund_special where CRT_DT > DATE(NOW())");
                DecimalFormat df = new DecimalFormat("0000");
                String suffixNum = df.format(JdbcMapUtil.getInt(countToday, "countToday") + 1);
                SimpleDateFormat sdf = new SimpleDateFormat("YYYYMMdd");
                String fundPayCode = "zf" + sdf.format(new Date()) + suffixNum;
                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.TEXT_LONG;
                    linkedAtt.value = fundPayCode;
                    linkedAtt.text = fundPayCode;
                    attLinkResult.attMap.put("FUND_PAY_CODE",linkedAtt);
                }

                //期数
                //查询相同项目、资金来源专项资金支付条数
                int count = 0;
                Map<String, Object> specialCountMap = myJdbcTemplate.queryForMap("select ifnull(max(NPER),0) count from fund_special where PM_PRJ_ID = ? and FUND_IMPLEMENTATION_V_ID = ?", prjId, fundSource);
                Map<String, Object> payCountMap = myJdbcTemplate.queryForMap("select ifnull(max(NPER),0) count from fund_newly_increased_detail where PM_PRJ_ID = ? and FUND_IMPLEMENTATION_V_ID = ?",prjId,fundSource);
                int specialCount = StringUtil.getNum(JdbcMapUtil.getString(specialCountMap,"count"));
                int payCount = StringUtil.getNum(JdbcMapUtil.getString(payCountMap,"count"));
                count = specialCount > payCount ? specialCount : payCount;

                {
                    LinkedAtt linkedAtt = new LinkedAtt();
                    linkedAtt.type = AttDataTypeE.INTEGER;
                    linkedAtt.value = count + 1;
                    linkedAtt.text = String.valueOf(count + 1);
                    attLinkResult.attMap.put("NPER",linkedAtt);
                }

                if ("FUND_NEWLY_INCREASED_DETAIL".equals(entCode)){//支付资金信息表
                    String fundCategorySql = "select fi.FUND_CATEGORY_FIRST categoryFirstId,ft1.name categoryFirstName,fi.FUND_CATEGORY_SECOND " +
                            "categorySecondId,ft2.name categorySecondName \n" +
                            "from fund_implementation fi \n" +
                            "left join fund_implementation_detail fid on fid.FUND_IMPLEMENTATION_ID = fi.id\n" +
                            "left join fund_type ft1 on ft1.id = fi.FUND_CATEGORY_FIRST\n" +
                            "left join fund_type ft2 on ft2.id = fi.FUND_CATEGORY_SECOND\n" +
                            "where fi.FUND_SOURCE_TEXT = ? and fid.PM_PRJ_ID = ?";
                    List<Map<String, Object>> categoryList = myJdbcTemplate.queryForList(fundCategorySql, fundSourceName, prjId);
                    if (!CollectionUtils.isEmpty(categoryList)){
                        //资金大类一级
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = JdbcMapUtil.getString(categoryList.get(0),"categoryFirstId");
                            linkedAtt.text = JdbcMapUtil.getString(categoryList.get(0),"categoryFirstName");
                            attLinkResult.attMap.put("FUND_CATEGORY_FIRST",linkedAtt);
                        }

                        //资金大类二级
                        {
                            LinkedAtt linkedAtt = new LinkedAtt();
                            linkedAtt.type = AttDataTypeE.TEXT_LONG;
                            linkedAtt.value = JdbcMapUtil.getString(categoryList.get(0),"categorySecondId");
                            linkedAtt.text = JdbcMapUtil.getString(categoryList.get(0),"categorySecondName");
                            attLinkResult.attMap.put("FUND_CATEGORY_SECOND",linkedAtt);
                        }

                    }
                    //支付信息（子表）
//                    String viewId = "0099952822476415265";
//                    ArrayList<LinkedRecord> linkedRecordList = new ArrayList<>();
//                    LinkedRecord linkedRecord = new LinkedRecord();
//                    //资金类别
//                    linkedRecord.valueMap.put("NPER",count + 1);
//                    linkedRecord.textMap.put("NPER",String.valueOf(count + 1));
//                    linkedRecordList.add(linkedRecord);
//                    attLinkResult.childData.put(viewId,linkedRecordList);
//                    attLinkResult.childCreatable.put(viewId, true);
//                    attLinkResult.childClear.put(viewId, true);
                }

            }
        }
        return attLinkResult;
    }
}
