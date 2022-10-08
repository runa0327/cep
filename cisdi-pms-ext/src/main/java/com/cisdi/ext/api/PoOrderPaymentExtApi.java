package com.cisdi.ext.api;

import com.cisdi.ext.model.view.order.PoOrderPaymentView;
import com.cisdi.ext.util.EntityUtil;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目库-工程付款相关接口
 */
public class PoOrderPaymentExtApi {

    //工程付款列表
    public void getPayment(){
        // 获取输入：
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        PoOrderPaymentView param = JsonUtil.fromJson(json, PoOrderPaymentView.class);
        if (SharedUtil.isEmptyString(param.projectId)) {
            throw new BaseException("接口调用失败，项目id不能为空");
        }
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }

        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        StringBuilder baseSql = new StringBuilder();
        baseSql.append("SELECT a.ID,a.CONTRACT_ID,b.NAME contract_name,a.PM_PRJ_ID project_id,a.STAGE_PAY_AMT_TWO,a.AMT,a.PAY_DATE,a.PAY_AMT,a" +
                ".PO_ORDER_DTL_PRO_ID,a.PO_ORDER_DTL_ID ,p.name project_name " +
                "FROM po_order_payment a left join po_order b on a.CONTRACT_ID = b.CONTRACT_APP_ID  " +
                "left join pm_prj p on p.id = a.PM_PRJ_ID " +
                "WHERE a.PM_PRJ_ID = '" + param.projectId + "' ");
        //其他筛选条件
        if (!SharedUtil.isEmptyString(param.contractName)){
            baseSql.append("and b.NAME like '%" + param.contractName + "%' ");
        }
        if (!SharedUtil.isEmptyString(param.startDate) && !SharedUtil.isEmptyString(param.endDate)){
            baseSql.append("and a.pay_date BETWEEN '" + param.startDate + "' and '" + param.endDate + "' ");
        }
        if (!SharedUtil.isEmptyString(param.minPayAmt)){
            baseSql.append("and a.PAY_AMT >= " + param.minPayAmt + " ");
        }
        if (!SharedUtil.isEmptyString(param.maxPayAmt)){
            baseSql.append("and a.PAY_AMT <= " + param.maxPayAmt + " ");
        }

        String totalSql = baseSql.toString();
        //排序分页
        baseSql.append("order by a.PAY_DATE desc ");
        baseSql.append(limit);

        List<Map<String, Object>> payMaps = myJdbcTemplate.queryForList(baseSql.toString());
        List<Map<String, Object>> totalMaps = myJdbcTemplate.queryForList(totalSql);
        ArrayList<PoOrderPaymentView> paymentViews = new ArrayList<>();
        for (Map<String, Object> payMap : payMaps) {
            PoOrderPaymentView paymentView = EntityUtil.mapToEntity(PoOrderPaymentView.class,payMap);
            paymentViews.add(paymentView);
        }
        HashMap<String, Object> result = new HashMap<>();
        result.put("paymentViews",paymentViews);
        result.put("total",totalMaps.size());

        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

}
