package com.cisdi.data.domain;

import lombok.Data;

/**
 * 招标台账
 */
@Data
public class PoPublicBid {
    private String NAME;
    private String PM_PRJ_ID;
    private String PRJ_CODE;
    private String PRJ_REPLY_NO;
    private String PRJ_SITUATION;
    private String PMS_RELEASE_WAY_ID;
    private String BID_UNIT;
    private String BID_BASIS;
    private String BID_CTL_PRICE_LAUNCH;
    private String BID_DEMAND_FILE_GROUP_ID;
    private String APPROVE_PMS_RELEASE_WAY_ID;
    private String APPROVE_PURCHASE_TYPE;
    private String APPROVE_BID_CTL_PRICE;
    private String APPROVE_PURCHASE_TYPE_ECHO;
    private String BID_USER_ID;
    private String BID_FILE_GROUP_ID;
    private String BID_OPEN_DATE;
    private String WIN_BID_UNIT_TXT;
    private String TENDER_OFFER;
    private String CONTACT_NAME;
    private String CONTACT_MOBILE_WIN;
    private String CONTACT_IDCARD_WIN;
    private String CPMS_UUID;
    private String CPMS_ID;
    private String BID_AGENCY;

}
