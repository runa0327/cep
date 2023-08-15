package com.cisdi.ext.invest;

import com.cisdi.ext.util.DoubleUtil;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.MyNamedParameterJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * 投资测算与实际的对比。
 */
public class InvestEstActCompareExt {
    public void getPrjInvestEstActCompare() {
        // 解析参数，得到InvestEstActCompareParam对象：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        InvestEstActCompareParam param = JsonUtil.fromJson(json, InvestEstActCompareParam.class);
        String pmPrjId = param.pmPrjId;
        // 查询费用类型
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList("select * from PM_EXP_TYPE");
        List<InvestEstActCompareRow> investEstActCompareRowList = list.stream().map(p -> {
            InvestEstActCompareRow row = new InvestEstActCompareRow();
            row.expTypeId = String.valueOf(p.get("ID"));
            row.pid = String.valueOf(p.get("PM_EXP_TYPE_PID") == null ? "0" : p.get("PM_EXP_TYPE_PID"));
            row.expTypeCode = String.valueOf(p.get("CODE"));
            row.expTypeName = String.valueOf(p.get("NAME"));
            row.invest0Amt = 0d;
            row.invest1Amt = 0d;
            row.invest2Amt = 0d;
            row.invest3Amt = 0d;
            row.invest0AmtSum = 0d;
            row.invest1AmtSum = 0d;
            row.invest2AmtSum = 0d;
            row.invest3AmtSum = 0d;
            row.complAmt = 0d;
            row.complAmtSum = 0d;
            row.payAmt = 0d;
            row.payAmtSum = 0d;
            return row;
        }).collect(Collectors.toList());

        // 获取可研估算、初设概算、预算财评：
        List<Map<String, Object>> investEstList = myJdbcTemplate.queryForList("select t.ID, t.VER, t.TS, t.IS_PRESET, t.CRT_DT, t.CRT_USER_ID, t.LAST_MODI_DT, t.LAST_MODI_USER_ID, t.STATUS, t.LK_WF_INST_ID, t.CODE, t.NAME, t.REMARK, ifnull(t.AMT,0) as AMT, t.PM_INVEST_EST_ID, t.PM_EXP_TYPE_ID, t.CPMS_UUID, t.CPMS_ID, t.PM_INVEST_EST_DTL_PID, t.SEQ_NO,a.INVEST_EST_TYPE_ID as INVEST_EST_TYPE_ID,gsv.`code`  as INVEST_EST_TYPE\n" +
                "from PM_INVEST_EST_DTL t \n" +
                "left join PM_INVEST_EST a on t.PM_INVEST_EST_ID = a.id \n" +
                "left join gr_set_value gsv on gsv.id = a.INVEST_EST_TYPE_ID\n" +
                "left join gr_set gr on gr.id = gsv.GR_SET_ID and gr.`CODE`='invest_est_type'\n" +
                "where a.PM_PRJ_ID=? and gsv.`code` <>'invest4' ", pmPrjId);


        // 按投资测算类型分组
        Map<String, List<Map<String, Object>>> typeMap = investEstList.stream().collect(Collectors.groupingBy(x -> x.get("INVEST_EST_TYPE").toString()));
        for (InvestEstActCompareRow investEstActCompareRow : investEstActCompareRowList) {
            for (String key : typeMap.keySet()) {
                List<Map<String, Object>> mapData = typeMap.get(key);
                switch (key) {
                    case "invest0":
                        Optional<Map<String, Object>> mapOptional = mapData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_ID")), String.valueOf(investEstActCompareRow.expTypeId))).findAny();
                        mapOptional.ifPresent(stringObjectMap -> investEstActCompareRow.invest0Amt = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        mapOptional.ifPresent(stringObjectMap -> investEstActCompareRow.invest0AmtSum = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        break;
                    case "invest1":
                        Optional<Map<String, Object>> optionalInvestEstActCompareRow = mapData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_ID")), String.valueOf(investEstActCompareRow.expTypeId))).findAny();
                        optionalInvestEstActCompareRow.ifPresent(stringObjectMap -> investEstActCompareRow.invest1Amt = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        optionalInvestEstActCompareRow.ifPresent(stringObjectMap -> investEstActCompareRow.invest1AmtSum = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        break;
                    case "invest2":
                        Optional<Map<String, Object>> optionalMap = mapData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_ID")), String.valueOf(investEstActCompareRow.expTypeId))).findAny();
                        optionalMap.ifPresent(stringObjectMap -> investEstActCompareRow.invest2Amt = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        optionalMap.ifPresent(stringObjectMap -> investEstActCompareRow.invest2AmtSum = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        break;
                    case "invest3":
                        Optional<Map<String, Object>> optional = mapData.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_ID")), String.valueOf(investEstActCompareRow.expTypeId))).findAny();
                        optional.ifPresent(stringObjectMap -> investEstActCompareRow.invest3Amt = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        optional.ifPresent(stringObjectMap -> investEstActCompareRow.invest3AmtSum = Double.parseDouble(JdbcMapUtil.getString(stringObjectMap, "AMT")));
                        break;
                    default:
                        throw new BaseException("数据错误！");
                }
            }
        }

        // 获取合同明细进度
        List<Map<String, Object>> orderProList = myJdbcTemplate.queryForList("select pod.*,ifnull(sum(podp.COMPL_TOTAL_AMT),0) as COMPL_TOTAL_AMT from PO_ORDER_DTL pod \n" +
                "left join po_order po on po.id = pod.PO_ORDER_ID\n" +
                "left join PO_ORDER_DTL_PRO podp on  podp.PO_ORDER_DTL_ID = pod.id\n" +
                "where po.PM_PRJ_ID=? group by pod.id\n", pmPrjId);

        // 获取合同明细付款情况
        List<Map<String, Object>> orderPayList = myJdbcTemplate.queryForList("select pod.*,ifnull(sum(pop.PAY_AMT),0) as PAY_AMT from PO_ORDER_DTL pod \n" +
                "left join po_order po on po.id = pod.PO_ORDER_ID\n" +
                "left join PO_ORDER_PAYMENT pop on  pop.PO_ORDER_DTL_ID = pod.id\n" +
                "where po.PM_PRJ_ID=? group by pod.id", pmPrjId);

        investEstActCompareRowList.forEach(item -> {
            Optional<Map<String, Object>> optional = orderProList.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_ID")), String.valueOf(item.expTypeId))).findAny();
            optional.ifPresent(stringObjectMap -> item.complAmt = Double.parseDouble(String.valueOf(stringObjectMap.get("COMPL_TOTAL_AMT"))));

            Optional<Map<String, Object>> optionalMap = orderPayList.stream().filter(p -> Objects.equals(String.valueOf(p.get("PM_EXP_TYPE_ID")), String.valueOf(item.expTypeId))).findAny();
            optionalMap.ifPresent(stringObjectMap -> item.payAmt = Double.parseDouble(String.valueOf(stringObjectMap.get("PAY_AMT"))));
        });

        // 构建树结构
        List<InvestEstActCompareRow> rowTree = investEstActCompareRowList.stream().filter(p -> "0".equals(p.pid)).peek(m -> {
            List<InvestEstActCompareRow> child = getChildNode(m, investEstActCompareRowList);
            m.children = child;
            if (CollectionUtils.isEmpty(child)){//没有子节点了，不计算
                return;
            }
            m.invest0AmtSum = sumAmt(child, "invest0Amt",m.invest0AmtSum);
            m.invest1AmtSum = sumAmt(child, "invest1Amt",m.invest1AmtSum);
            m.invest2AmtSum = sumAmt(child, "invest2Amt",m.invest2AmtSum);
            m.invest3AmtSum = sumAmt(child, "invest3Amt",m.invest3AmtSum);
            m.complAmtSum = sumAmt(child, "complAmt",m.complAmtSum);
            m.payAmtSum = sumAmt(child, "payAmt",m.payAmtSum);
        }).collect(Collectors.toList());

        // 返回结果，如：
        InvestEstActCompareRow row = rowTree.get(0);
        // row.children添加子行：
        // 最终，返回：
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(row), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private double sumAmt(List<InvestEstActCompareRow> data, String column, double oldSum) {
        double res = 0d;
        if (data != null && data.size() > 0) {
            for (InvestEstActCompareRow datum : data) {
                double var = 0d;
                switch (column) {
                    case "invest0Amt":
                        var = datum.invest0AmtSum;
                        break;
                    case "invest1Amt":
                        var = datum.invest1AmtSum;
                        break;
                    case "invest2Amt":
                        var = datum.invest2AmtSum;
                        break;
                    case "invest3Amt":
                        var = datum.invest3AmtSum;
                        break;
                    case "complAmt":
                        var = datum.complAmtSum;
                        break;
                    case "payAmt":
                        var = datum.payAmtSum;
                        break;
                    default:
                        throw new BaseException("数据错误！");
                }
                res = DoubleUtil.add(res, var);
            }
        }
        if (res == 0){//子节点加起来为0，取原值
            return oldSum;
        }
        return res;
    }

    /**
     * 递归子节点
     *
     * @param root        当前单个实体
     * @param allListTree 表中的所有实体集合
     * @return
     */
    private List<InvestEstActCompareRow> getChildNode(InvestEstActCompareRow root, List<InvestEstActCompareRow> allListTree) {
        List<InvestEstActCompareRow> children = allListTree.stream().filter((treeEntity) -> treeEntity.pid.equals(root.expTypeId)).peek((treeEntity) -> {
            List<InvestEstActCompareRow> child = getChildNode(treeEntity, allListTree);
            treeEntity.children = child;
            if (CollectionUtils.isEmpty(child)){//没有子节点了，不计算
                return;
            }
            treeEntity.invest0AmtSum = sumAmt(child, "invest0Amt",treeEntity.invest0AmtSum);
            treeEntity.invest1AmtSum = sumAmt(child, "invest1Amt",treeEntity.invest1AmtSum);
            treeEntity.invest2AmtSum = sumAmt(child, "invest2Amt",treeEntity.invest2AmtSum);
            treeEntity.invest3AmtSum = sumAmt(child, "invest3Amt",treeEntity.invest3AmtSum);
            treeEntity.complAmtSum = sumAmt(child, "complAmt",treeEntity.complAmtSum);
            treeEntity.payAmtSum = sumAmt(child, "payAmt",treeEntity.payAmtSum);
        }).collect(Collectors.toList());
        return children;

    }

    public static class InvestEstActCompareParam {
        /**
         * 项目id。
         */
        public String pmPrjId;
    }

    public static class InvestEstActCompareRow {
        /**
         * 费用类型id。
         */
        public String expTypeId;
        /**
         * 父ID
         */
        public String pid;
        /**
         * 费用类型代码。
         */
        public String expTypeCode;
        /**
         * 费用类型名称。
         */
        public String expTypeName;

        /**
         * 立项匡算金额。
         */
        public Double invest0Amt;
        /**
         * 立项匡算金额。
         */
        public Double invest0AmtSum;

        /**
         * 可研估算金额。
         */
        public Double invest1Amt;
        /**
         * 可研估算金额。
         */
        public Double invest1AmtSum;

        /**
         * 初设概算金额。
         */
        public Double invest2Amt;
        /**
         * 初设概算金额。
         */
        public Double invest2AmtSum;

        /**
         * 预算财评金额。
         */
        public Double invest3Amt;
        /**
         * 预算财评金额。
         */
        public Double invest3AmtSum;

        /**
         * 完成金额。
         */
        public Double complAmt;
        /**
         * 支付金额。
         */
        public Double payAmt;
        /**
         * 完成金额（累计）。
         */
        public Double complAmtSum;
        /**
         * 支付金额（累计）。
         */
        public Double payAmtSum;
        /**
         * 子行。递归。
         */
        public List<InvestEstActCompareRow> children;
    }


    /**
     * 项目预算获取批复文件
     */
    public void budgetFile() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String type = String.valueOf(map.get("type"));
        String projectId = String.valueOf(map.get("projectId"));
        String tableName = "";
        String filed = "";
        switch (type) {
            case "1":
                //立项匡算
                tableName = "PM_PRJ_REQ";
                filed = "REPLY_FILE";
                break;
            case "2":
                //可研估算
                tableName = "PM_PRJ_INVEST1";
                filed = "REPLY_FILE";
                break;
            case "3":
                tableName = "PM_PRJ_INVEST2";
                filed = "REPLY_FILE";
                break;
            case "4":
                tableName = "PM_PRJ_INVEST3";
                filed = "FINANCIAL_REVIEW_FILE";
                break;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("select ").append(filed).append(" as fileIds from ").append(tableName).append(" where PM_PRJ_ID= ").append("'").append(projectId).append("'");
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<Map<String, Object>> list = myJdbcTemplate.queryForList(sb.toString());
        List<FileObj> fileObjList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            String fileIds = String.valueOf(list.get(0).get("fileIds"));
            List<String> ids = Arrays.asList(fileIds.split(","));
            MyNamedParameterJdbcTemplate myNamedParameterJdbcTemplate = ExtJarHelper.myNamedParameterJdbcTemplate.get();
            Map<String, Object> queryParams = new HashMap<>();// 创建入参map
            queryParams.put("ids", ids);
            List<Map<String, Object>> fileList = myNamedParameterJdbcTemplate.queryForList("select ff.ID as ID, DSP_NAME,SIZE_KB,UPLOAD_DTTM,au.`NAME` as USER_NAME,FILE_INLINE_URL,FILE_ATTACHMENT_URL from fl_file ff left join ad_user au on ff.CRT_USER_ID = au.id  where ff.id in (:ids)", queryParams);
            AtomicInteger index = new AtomicInteger(0);
            fileObjList = fileList.stream().map(p -> {
                FileObj obj = new FileObj();
                obj.num = index.getAndIncrement() + 1;
                obj.fileName = JdbcMapUtil.getString(p, "DSP_NAME");
                obj.fileSize = JdbcMapUtil.getString(p, "SIZE_KB");
                obj.uploadUser = JdbcMapUtil.getString(p, "USER_NAME");
                obj.uploadDate = StringUtil.withOutT(JdbcMapUtil.getString(p, "UPLOAD_DTTM"));
                obj.id = JdbcMapUtil.getString(p, "ID");
                obj.viewUrl = JdbcMapUtil.getString(p, "FILE_INLINE_URL");
                obj.downloadUrl = JdbcMapUtil.getString(p, "FILE_ATTACHMENT_URL");
                return obj;
            }).collect(Collectors.toList());
        }
        if (CollectionUtils.isEmpty(fileObjList)) {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        } else {
            OutSide resData = new OutSide();
            resData.fileObjList = fileObjList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(resData), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }
    }

    public static class FileObj {
        //序号
        public Integer num;
        //文件名称
        public String fileName;
        //文件大小
        public String fileSize;
        //上传人
        public String uploadUser;
        //上传时间
        public String uploadDate;

        public String id;

        public String viewUrl;

        public String downloadUrl;

    }

    public static class OutSide {
        public List<FileObj> fileObjList;
    }

}
