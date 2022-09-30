package com.cisdi.ext.model.view.order;

import com.cisdi.ext.model.BasePageEntity;

/**
 * 采购合同相对方联系人明细
 */
public class PoOrderContactsView extends BasePageEntity {

    //id
    public String id;
    //父级id
    public String parentId;
    //相对方联系人
    public String oppoSiteLinkMan;
    //相对方联系方式
    public String oppoSiteContact;
}
