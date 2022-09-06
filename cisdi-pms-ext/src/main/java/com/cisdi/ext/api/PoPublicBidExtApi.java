package com.cisdi.ext.api;

import com.cisdi.ext.proImg.ProImgExt;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 尹涛 * @version V1.0.0
 * @projectName cisdi-pms-service
 * @title PoPublicBidExtApi
 * @package com.cisdi.ext.api
 * @description 招标信息
 * @date 2022/9/5
 */
public class PoPublicBidExtApi {

    /**
     * 招标列表接口
     */
    public void getPublicBidList() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String pmPrjId = param.pmPrjId;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        List<Map<String, Object>> list = jdbcTemplate.queryForList("", pmPrjId);
        List<ProjectTender> resList = list.stream().map(this::convertProjectTender).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resList)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            ProjectObj obj = new ProjectObj();
            obj.list = resList;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(obj), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }

    }


    /**
     * 招标详情接口
     */
    public void getPublicBidView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String id = param.id;
        JdbcTemplate jdbcTemplate = ExtJarHelper.jdbcTemplate.get();
        try {
            Map<String, Object> stringObjectMap = jdbcTemplate.queryForMap("", id);
            ProjectTender tender = this.convertProjectTender(stringObjectMap);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(tender), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } catch (Exception e) {
            ExtJarHelper.returnValue.set(null);
        }
    }


    public ProjectTender convertProjectTender(Map<String, Object> data) {
        ProjectTender tender = new ProjectTender();
        tender.id = JdbcMapUtil.getString(data, "ID");
        tender.name = JdbcMapUtil.getString(data, "NAME");
        tender.projectId = JdbcMapUtil.getString(data, "PM_PRJ_ID");

        tender.projectName = JdbcMapUtil.getString(data, "PROJECT_NAME");

        tender.projectCode = JdbcMapUtil.getString(data, "PRJ_CODE");
        tender.replyNo = JdbcMapUtil.getString(data, "PRJ_REPLY_NO");
        tender.prjSituation = JdbcMapUtil.getString(data, "PRJ_SITUATION");
        tender.investSourceId = JdbcMapUtil.getString(data, "INVESTMENT_SOURCE_ID");
        tender.customerUnit = JdbcMapUtil.getString(data, "CUSTOMER_UNIT");
        tender.pmsReleaseWayId = JdbcMapUtil.getString(data, "PMS_RELEASE_WAY_ID");
        tender.feasibilityApproveFund = JdbcMapUtil.getString(data, "FEASIBILITY_APPROVE_FUND");
        tender.estimateApproveFund = JdbcMapUtil.getString(data, "ESTIMATE_APPROVE_FUND");
        tender.evaluationApproveFund = JdbcMapUtil.getString(data, "EVALUATION_APPROVE_FUND");
        tender.bidUnit = JdbcMapUtil.getString(data, "BID_UNIT");
        tender.bidBasis = JdbcMapUtil.getString(data, "BID_BASIS");
        tender.bidCtlPriceLaunch = JdbcMapUtil.getString(data, "BID_CTL_PRICE_LAUNCH");
        tender.serviceDays = JdbcMapUtil.getString(data, "SERVICE_DAYS");
        tender.bidDemandFileGroupId = JdbcMapUtil.getString(data, "BID_DEMAND_FILE_GROUP_ID");
        tender.remark = JdbcMapUtil.getString(data, "REMARK");
        tender.approvePmsReleaseWayId = JdbcMapUtil.getString(data, "APPROVE_PMS_RELEASE_WAY_ID");
        tender.approvePurchaseType = JdbcMapUtil.getString(data, "APPROVE_PURCHASE_TYPE");
        tender.approveBidCtlPrice = JdbcMapUtil.getString(data, "APPROVE_BID_CTL_PRICE");
        tender.approvePurchaseTypeEcho = JdbcMapUtil.getString(data, "APPROVE_PURCHASE_TYPE_ECHO");
        tender.leaderApproveComment = JdbcMapUtil.getString(data, "LEADER_APPROVE_COMMENT");

        tender.leaderApproveFileGroupId = JdbcMapUtil.getString(data, "LEADER_APPROVE_FILE_GROUP_ID");

        tender.bidCtlPriceLaunchEcho = JdbcMapUtil.getString(data, "BID_CTL_PRICE_LAUNCH_ECHO");
        tender.bidUserId = JdbcMapUtil.getString(data, "BID_USER_ID");
        tender.bidAgency = JdbcMapUtil.getString(data, "BID_AGENCY");
        tender.demandPromoter = JdbcMapUtil.getString(data, "DEMAND_PROMOTER");

        tender.bidFileGroupId = JdbcMapUtil.getString(data, "BID_FILE_GROUP_ID");

        tender.registrationDate = JdbcMapUtil.getString(data, "REGISTRATION_DATE");
        tender.bidOpenDate = JdbcMapUtil.getString(data, "BID_OPEN_DATE");
        return tender;
    }

    public static class RequestParam {
        public String pmPrjId;
        public String id;
    }

    public static class ProjectTender {
        public String id;
        public String name;
        public String projectId;
        public String projectName;
        public String projectCode;
        public String replyNo;
        public String prjSituation;
        public String investSourceId;
        public String customerUnit;
        public String pmsReleaseWayId;

        public String feasibilityApproveFund;
        public String estimateApproveFund;
        public String evaluationApproveFund;
        public String bidUnit;
        public String bidBasis;
        public String bidCtlPriceLaunch;
        public String serviceDays;
        /**
         * 招标需求附件
         */
        public String bidDemandFileGroupId;

        public String remark;

        public String approvePmsReleaseWayId;

        /**
         * 招标方式
         */
        public String approvePurchaseType;

        public String approveBidCtlPrice;

        public String approvePurchaseTypeEcho;

        /**
         * 领导审批意见
         */
        public String leaderApproveComment;

        /**
         * 领导审批附件
         */
        public String leaderApproveFileGroupId;

        public String bidCtlPriceLaunchEcho;

        public String bidUserId;

        public String bidAgency;

        public String demandPromoter;

        public String bidFileGroupId;

        public String registrationDate;

        public String bidOpenDate;
    }

    public static class ProjectObj {
        public List<ProjectTender> list;
    }
}
