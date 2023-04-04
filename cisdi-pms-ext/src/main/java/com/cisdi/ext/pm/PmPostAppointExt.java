package com.cisdi.ext.pm;

import com.cisdi.ext.model.PmPostAppoint;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 岗位指派流程-扩展
 */
public class PmPostAppointExt {

    public static final Map<String,String> postCodeMap = new HashMap<>();
    static {
        postCodeMap.put("AD_USER_TWELVE_ID","前期报建岗");
        postCodeMap.put("AD_USER_THIRTEEN_ID","土地管理岗");
        postCodeMap.put("AD_USER_FOURTEEN_ID","管线迁改岗");
        postCodeMap.put("AD_USER_FIFTEEN_ID","计划运营岗");
        postCodeMap.put("AD_USER_SIXTEEN_ID","前期设备岗");
        postCodeMap.put("AD_USER_EIGHTEEN_ID","成本管理岗");
        postCodeMap.put("AD_USER_NINETEEN_ID","合约管理岗");
        postCodeMap.put("AD_USER_TWENTY_ID","设备成本岗");
        postCodeMap.put("AD_USER_TWENTY_ONE_ID","采购管理岗");
        postCodeMap.put("AD_USER_TWENTY_TWO_ID","设计管理岗");
        postCodeMap.put("AD_USER_TWENTY_THREE_ID","工程管理岗");
        postCodeMap.put("AD_USER_TWENTY_FOUR_ID","征拆对接岗");
        postCodeMap.put("AD_USER_TWENTY_FIVE_ID","财务管理岗");
    }

    /**
     * 岗位指派-部门负责人审批-扩展
     */
    public void chargeLeaderCheck(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        //项目岗位信息写入项目花名册
        PmRosterExt.createDataByProcess(entityRecord,projectId, postCodeMap);
    }

    /**
     * 岗位指派-发起时校验
     */
    public void postAppointStartCheck(){
        EntityRecord entityRecord = ExtJarHelper.entityRecordList.get().get(0);
        String projectId = JdbcMapUtil.getString(entityRecord.valueMap,"PM_PRJ_ID");
        String id = entityRecord.csCommId;
        List<PmPostAppoint> list = PmPostAppoint.selectByWhere(new Where().eq(PmPostAppoint.Cols.PM_PRJ_ID,projectId)
                .nin(PmPostAppoint.Cols.STATUS,"VD,VDING").neq(PmPostAppoint.Cols.ID,id));
        if (!CollectionUtils.isEmpty(list)){
            throw new BaseException("该项目已有流程正在启动指派中，请勿重复发起指派");
        }
    }

    /**
     * 岗位流程-自动发起流程
     */
    public static void autoCreateProcess(String projectId,String userId){
        Map<String,Object> map = new HashMap<>();
        //组装
    }
}
