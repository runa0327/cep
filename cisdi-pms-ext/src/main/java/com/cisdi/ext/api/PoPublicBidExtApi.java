package com.cisdi.ext.api;

import com.cisdi.ext.fundManage.FileCommon;
import com.cisdi.ext.model.BasePageEntity;
import com.cisdi.ext.model.view.CommonDrop;
import com.cisdi.ext.model.view.File;
import com.cisdi.ext.util.JsonUtil;
import com.google.common.base.Strings;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
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
        Input input = JsonUtil.fromJson(json, Input.class);
        String projectId = input.projectId;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String baseSql = "select ID,NAME,PM_PRJ_ID,PRJ_CODE,PRJ_REPLY_NO,PRJ_SITUATION,INVESTMENT_SOURCE_ID,CUSTOMER_UNIT,BUY_TYPE_ID," +
                "FEASIBILITY_APPROVE_FUND,ESTIMATE_APPROVE_FUND,EVALUATION_APPROVE_FUND,BID_UNIT,BID_BASIS,BID_CTL_PRICE_LAUNCH,SERVICE_DAYS," +
                "BID_DEMAND_FILE_GROUP_ID,REMARK,APPROVE_PMS_RELEASE_WAY_ID,APPROVE_PURCHASE_TYPE,APPROVE_BID_CTL_PRICE,APPROVE_PURCHASE_TYPE_ECHO," +
                "LEADER_APPROVE_COMMENT,LEADER_APPROVE_FILE_GROUP_ID,BID_CTL_PRICE_LAUNCH_ECHO,BID_USER_ID,BID_AGENCY,DEMAND_PROMOTER," +
                "BID_FILE_GROUP_ID,REGISTRATION_DATE,BID_OPEN_DATE,WIN_BID_UNIT_TXT,TENDER_OFFER from po_public_bid where PM_PRJ_ID = ? ";
        // 筛选条件
        if (!Strings.isNullOrEmpty(input.tenderName)) {// 招标名称模糊查询
            baseSql += "and NAME like '%" + input.tenderName + "%' ";
        }
        if (!Strings.isNullOrEmpty(input.startDate) && !Strings.isNullOrEmpty(input.endDate)) {// 开标日期
            baseSql += "and BID_OPEN_DATE BETWEEN '" + input.startDate + "' and '" + input.endDate + "' ";
        }
        if (!Strings.isNullOrEmpty(input.tenderWayId)) {// 招标方式
            baseSql += "and APPROVE_PURCHASE_TYPE = '" + input.tenderWayId + "' ";
        }
        if (!Strings.isNullOrEmpty(input.tenderReleaseWayId)) {// 招标类型
            baseSql += "and APPROVE_PMS_RELEASE_WAY_ID = '" + input.tenderReleaseWayId + "' ";
        }
        String totalSql = baseSql;
        // 分页
        Integer start = input.pageSize * (input.pageIndex - 1);
        baseSql += "limit " + start + "," + input.pageSize;

        List<Map<String, Object>> list = myJdbcTemplate.queryForList(baseSql, projectId);
        List<Map<String, Object>> totalList = myJdbcTemplate.queryForList(totalSql, projectId);
        List<ProjectTender> resList = list.stream().map(this::convertProjectTender).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(resList)) {
            ExtJarHelper.returnValue.set(null);
        } else {
            for (ProjectTender projectTender : resList) {
                // 招采类型
                projectTender.pmsReleaseWay = findInDict("pms_tender_type", projectTender.pmsReleaseWayId, myJdbcTemplate);
                // 招标类型
                projectTender.approveReleaseWay = findInDict("pms_release_way", projectTender.approvePmsReleaseWayId, myJdbcTemplate);
                // 招标方式
                projectTender.approvePurchaseTypeName = findInDict("buy_type", projectTender.approvePurchaseType, myJdbcTemplate);
                // 经办部门
                if (!Strings.isNullOrEmpty(projectTender.bidUserId)) {
                    Map<String, Object> deptUserMap = myJdbcTemplate.queryForMap("select u.name handleUserName,d.name handleDeptName from ad_user u " +
                            "left join hr_dept_user t on t.ad_user_id = u.id " +
                            "left join hr_dept d on d.id = t.hr_dept_id " +
                            "where u.id = ?", projectTender.bidUserId);
                    projectTender.handleDeptName = JdbcMapUtil.getString(deptUserMap, "handleDeptName");
                    projectTender.handleUserName = JdbcMapUtil.getString(deptUserMap, "handleUserName");
                }
                // 项目
                if (!Strings.isNullOrEmpty(projectTender.projectId)) {
                    Map<String, Object> projectMap = myJdbcTemplate.queryForMap("select name from pm_prj where id = ?",
                            projectTender.projectId);
                    projectTender.projectName = JdbcMapUtil.getString(projectMap, "name");
                }

            }
            ProjectObj obj = new ProjectObj();
            obj.list = resList;
            obj.total = totalList.size();
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(obj), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        }

    }

    /**
     * 招标类型下拉
     */
    public void getReleaseWayDrop() {
        Map<String, Object> inputMap = ExtJarHelper.extApiParamMap.get();
        String wayName = JdbcMapUtil.getString(inputMap, "wayName");
        String code = JdbcMapUtil.getString(inputMap, "code");

        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        List<CommonDrop> commonDrops = getCommonDropList(wayName, myJdbcTemplate, code);

        WayDrops drops = new WayDrops();
        drops.commonDropList = commonDrops;
        Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(drops), Map.class);
        ExtJarHelper.returnValue.set(outputMap);
    }

    private List<CommonDrop> getCommonDropList(String searchName, MyJdbcTemplate myJdbcTemplate, String code) {
        StringBuffer baseSql = new StringBuffer();
        baseSql.append("select v.name,v.id,v.code from gr_set_value v left join gr_set s on s.id = v.gr_set_id where s.code = '").append(code).append("' ");
        if (!Strings.isNullOrEmpty(searchName)) {
            baseSql.append("and v.name like '%").append(searchName).append("%' ");
        }

        List<Map<String, Object>> ways = myJdbcTemplate.queryForList(baseSql.toString());
        List<CommonDrop> commonDrops = new ArrayList<>();
        for (Map<String, Object> way : ways) {
            CommonDrop wayDrop = new CommonDrop();
            wayDrop.id = JdbcMapUtil.getString(way, "id");
            wayDrop.name = JdbcMapUtil.getString(way, "name");
            wayDrop.code = JdbcMapUtil.getString(way, "code");
            commonDrops.add(wayDrop);
        }
        return commonDrops;
    }

    /**
     *
     */

    /**
     * 招标详情接口
     */
    public void getPublicBidView() {
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        RequestParam param = JsonUtil.fromJson(json, RequestParam.class);
        String id = param.id;
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        try {
            String baseSql = "select ID,NAME,PM_PRJ_ID,PRJ_CODE,PRJ_REPLY_NO,PRJ_SITUATION,INVESTMENT_SOURCE_ID,CUSTOMER_UNIT,BUY_TYPE_ID," +
                    "FEASIBILITY_APPROVE_FUND,ESTIMATE_APPROVE_FUND,EVALUATION_APPROVE_FUND,BID_UNIT,BID_BASIS,BID_CTL_PRICE_LAUNCH,SERVICE_DAYS," +
                    "BID_DEMAND_FILE_GROUP_ID,REMARK,APPROVE_PMS_RELEASE_WAY_ID,APPROVE_PURCHASE_TYPE,APPROVE_BID_CTL_PRICE," +
                    "APPROVE_PURCHASE_TYPE_ECHO," +
                    "LEADER_APPROVE_COMMENT,LEADER_APPROVE_FILE_GROUP_ID,BID_CTL_PRICE_LAUNCH_ECHO,BID_USER_ID,BID_AGENCY,DEMAND_PROMOTER," +
                    "BID_FILE_GROUP_ID,REGISTRATION_DATE,BID_OPEN_DATE,WIN_BID_UNIT_TXT,TENDER_OFFER from po_public_bid where ID = ? ";
            Map<String, Object> stringObjectMap = myJdbcTemplate.queryForMap(baseSql, id);
            ProjectTender projectTender = convertProjectTender(stringObjectMap);
            // 招标需求附件
            projectTender.bidDemandFileGroup = FileCommon.getFileResp(projectTender.bidDemandFileGroupId, myJdbcTemplate);
            // 招标文件
            projectTender.bidFileGroup = FileCommon.getFileResp(projectTender.bidFileGroupId, myJdbcTemplate);
            // 领导审批附件
            projectTender.leaderApproveFileGroup = FileCommon.getFileResp(projectTender.leaderApproveFileGroupId, myJdbcTemplate);
            // 费用明细
            String costSql = "select c.id,v.NAME costTypeName,c.FEE_DETAIL feeDetail,c.TOTAL_AMT totalAmt from pm_cost_detail_data c left join " +
                    "gr_set_value v on v.id = c.COST_TYPE_TREE_ID left join gr_set s on s.id = v.GR_SET_ID and s.code = 'cost_type_tree' where c" +
                    ".PO_PUBLIC_BID_ID = ? ";
            List<Map<String, Object>> costList = myJdbcTemplate.queryForList(costSql, projectTender.id);
            List<Cost> costs = costList.stream().map(costMap -> convertCost(costMap)).collect(Collectors.toList());
            projectTender.costDetails = costs;
            // 投标明细
            String bidSql = "select id,BID_UNIT,TENDER_OFFER,CONTACT_MOBILE,CONTACT_NAME,CONTACT_IDCARD_RECORD from pm_bid_detail_data where " +
                    "PO_PUBLIC_BID_ID = ?";
            List<Map<String, Object>> bidList = myJdbcTemplate.queryForList(bidSql, projectTender.id);
            List<BidDetail> bidDetails = bidList.stream().map(bidMap -> convertBidDetail(bidMap)).collect(Collectors.toList());
            projectTender.bidDetails = bidDetails;
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(projectTender), Map.class);
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
        tender.approvePurchaseType = JdbcMapUtil.getString(data, "BUY_TYPE_ID");
        tender.approveBidCtlPrice = JdbcMapUtil.getString(data, "APPROVE_BID_CTL_PRICE");
        tender.approvePurchaseTypeEcho = JdbcMapUtil.getString(data, "APPROVE_PURCHASE_TYPE_ECHO");
        tender.leaderApproveComment = JdbcMapUtil.getString(data, "LEADER_APPROVE_COMMENT");
        tender.leaderApproveFileGroupId = JdbcMapUtil.getString(data, "LEADER_APPROVE_FILE_GROUP_ID");

        tender.bidCtlPriceLaunchEcho = JdbcMapUtil.getString(data, "BID_CTL_PRICE_LAUNCH_ECHO");
        tender.bidUserId = JdbcMapUtil.getString(data, "BID_USER_ID");
        tender.bidAgency = JdbcMapUtil.getString(data, "BID_AGENCY");
        tender.winBidUnitTxt = JdbcMapUtil.getString(data, "WIN_BID_UNIT_TXT");
        tender.demandPromoter = JdbcMapUtil.getString(data, "DEMAND_PROMOTER");

        tender.bidFileGroupId = JdbcMapUtil.getString(data, "BID_FILE_GROUP_ID");

        tender.registrationDate = JdbcMapUtil.getString(data, "REGISTRATION_DATE");
        tender.bidOpenDate = JdbcMapUtil.getString(data, "BID_OPEN_DATE");
        tender.tenderOffer = JdbcMapUtil.getString(data, "TENDER_OFFER");
        return tender;
    }

    public Cost convertCost(Map<String, Object> costMap) {
        Cost cost = new Cost();
        cost.id = JdbcMapUtil.getString(costMap, "id");
        cost.costTypeName = JdbcMapUtil.getString(costMap, "costTypeName");
        cost.feeDetail = JdbcMapUtil.getString(costMap, "feeDetail");
        cost.totalAmt = JdbcMapUtil.getString(costMap, "totalAmt");
        return cost;
    }

    public BidDetail convertBidDetail(Map<String, Object> bidMap) {
        BidDetail bidDetail = new BidDetail();
        bidDetail.id = JdbcMapUtil.getString(bidMap, "id");
        bidDetail.bidUnit = JdbcMapUtil.getString(bidMap, "BID_UNIT");
        bidDetail.tenderOffer = JdbcMapUtil.getString(bidMap, "TENDER_OFFER");
        bidDetail.contactMobile = JdbcMapUtil.getString(bidMap, "CONTACT_MOBILE");
        bidDetail.contactName = JdbcMapUtil.getString(bidMap, "CONTACT_NAME");
        bidDetail.contactIdCard = JdbcMapUtil.getString(bidMap, "CONTACT_IDCARD_RECORD");
        return bidDetail;
    }

    public String findInDict(String code, String id, MyJdbcTemplate myJdbcTemplate) {
        if (Strings.isNullOrEmpty(id)) {
            return null;
        }
        String name = "";
        List<Map<String,Object>> list = myJdbcTemplate.queryForList("select v.name from gr_set_value v left join gr_set s on s.id = v.gr_set_id where s.code = ? and v.id = ?", code, id);
        if (!CollectionUtils.isEmpty(list)){
            name = JdbcMapUtil.getString(list.get(0),"name");
        }
        return name;
    }

    public static class RequestParam {
        public String pmPrjId;
        public String id;
    }

    public static class ProjectTender {
        // 招标id
        public String id;
        // 招标名称
        public String name;
        public String projectId;
        // 项目名
        public String projectName;
        public String projectCode;
        public String replyNo;
        public String prjSituation;
        public String investSourceId;
        public String customerUnit;
        public String pmsReleaseWayId;
        // 招采类型
        public String pmsReleaseWay;

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
        public List<File> bidDemandFileGroup;

        public String remark;

        public String approvePmsReleaseWayId;

        /**
         * 招标方式
         */
        public String approvePurchaseType;
        // 招标方式名称
        public String approvePurchaseTypeName;
        // 核定招标控制价（限价）
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
        public List<File> leaderApproveFileGroup;

        public String bidCtlPriceLaunchEcho;

        public String bidUserId;

        // 招标代理单位
        public String bidAgency;
        // 中标单位
        public String winBidUnitTxt;
        public String demandPromoter;

        public String bidFileGroupId;
        // 招标文件
        public List<File> bidFileGroup;

        // 报名时间
        public String registrationDate;

        // 开标日期
        public String bidOpenDate;

        // 中标价
        public String tenderOffer;

        // 经办部门
        public String handleDeptName;

        // 经办人
        public String handleUserName;

        // 招标类型
        public String approveReleaseWay;

        // 费用明细
        public List<Cost> costDetails;
        // 投标明细
        public List<BidDetail> bidDetails;

    }

    public static class Cost {
        public String id;
        // 费用类型
        public String costTypeName;
        // 费用明细
        public String feeDetail;
        // 总金额
        public String totalAmt;
    }

    public static class BidDetail {
        public String id;
        // 投标单位
        public String bidUnit;
        // 投标报价
        public String tenderOffer;
        // 联系人电话
        public String contactMobile;
        // 联系人姓名
        public String contactName;
        // 联系人身份证
        public String contactIdCard;
    }

    public static class ProjectObj {
        public List<ProjectTender> list;
        public Integer total;
    }

    public static class WayDrops {
        public List<CommonDrop> commonDropList;
    }

    // 入参
    public static class Input extends BasePageEntity {
        // 项目id
        public String projectId;
        // 招标名称
        public String tenderName;
        // 开标开始日期
        public String startDate;
        // 开标结束日期
        public String endDate;
        // 招标方式id
        public String tenderWayId;
        // 招标类别id
        public String tenderReleaseWayId;
    }

}
