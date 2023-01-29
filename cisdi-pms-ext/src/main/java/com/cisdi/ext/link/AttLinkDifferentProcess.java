package com.cisdi.ext.link;

import java.util.ArrayList;
import java.util.List;

/**
 * 属性联动分支判断各类流程
 */
public class AttLinkDifferentProcess {

    // 默认自动带出成本岗的流程
    public static List<String> getAutoCostUser() {
        List<String> list = new ArrayList<>();
        list.add("PO_ORDER_CHANGE_REQ"); //合同需求审批
        return list;
    }
}
