package com.cisdi.ext.api;

import com.cisdi.ext.model.view.PoOrderPaymentView;
import com.cisdi.ext.model.view.PoOrderView;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.SharedUtil;

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
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();

        String sql1 = "SELECT a.ID,a.CONTRACT_ID,b.NAME as contractName,a.PM_PRJ_ID,a.STAGE_PAY_AMT_TWO,a.AMT,a.PAY_DATE,a.PAY_AMT,a.PO_ORDER_DTL_PRO_ID,a.PO_ORDER_DTL_ID,a.NAME,a.CODE,a.STATUS " +
                "FROM po_order_payment a left join po_order b on a.CONTRACT_ID = b.id WHERE a.PM_PRJ_ID = ? ";
        String sql2 = "select count(*) from FROM po_order_payment WHERE PM_PRJ_ID = ? ";

        if (!SharedUtil.isEmptyString(param.contractName)){

        }

        sb.append(sql1);
        sb2.append(sql2);
    }

}
