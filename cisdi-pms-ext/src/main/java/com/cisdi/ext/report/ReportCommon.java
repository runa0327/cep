package com.cisdi.ext.report;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.AdEnt;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 台账共用接口
 */
public class ReportCommon {

    public static final Map<String,Object> COMMON_MAP = new HashMap<>();
    static {
        COMMON_MAP.put("ID","ID");
        COMMON_MAP.put("TS","TS");
        COMMON_MAP.put("CRT_DT","CRT_DT");
        COMMON_MAP.put("VER","VER");
        COMMON_MAP.put("IS_PRESET","IS_PRESET");
        COMMON_MAP.put("LAST_MODI_DT","LAST_MODI_DT");
        COMMON_MAP.put("LAST_MODI_USER_ID","LAST_MODI_USER_ID");
    }

    /**
     * 台账数据确认
     */
    public void checkProData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String codeId = AdEnt.selectByWhere(new Where().eq(AdEnt.Cols.CODE,entCode)).get(0).getId();
        List<EntityRecord> list = ExtJarHelper.entityRecordList.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String now = DateTimeUtil.dttmToString(new Date());
        String userName = ExtJarHelper.loginInfo.get().userName;
        StringBuilder sb = new StringBuilder("用户 ").append(userName).append(" 在 ").append(now);
        for (EntityRecord tmp : list) {
            Map<String,Object> map = tmp.valueMap;
            String proInstanceId = JdbcMapUtil.getString(map,"LK_WF_INST_ID");
            String isCheck = JdbcMapUtil.getString(map,"IS_CHECK_OVER");
            if (SharedUtil.isEmptyString(isCheck) || !"1".equals(isCheck)){
                String projectId = PmPrjExt.getProjectIdByProcess(map);
                String id = tmp.csCommId;
                String[] prjArr = projectId.split(",");
                for (String prj : prjArr) {
                    sb.append(" 对项目id为：").append(prj).append(" ,流程实例id为：").append(proInstanceId).append(",记录id为：").append(id).append(" 的数据点击了确认按钮！");
                    PmProUpdateLogExt.createData(codeId,id,prj,proInstanceId,userId,now,sb.toString());
                }
                Crud.from(entCode).where().eq("ID",id).update().set("IS_CHECK_OVER",1).exec();
            }
        }
    }
}
