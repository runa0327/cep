package com.cisdi.ext.report;

import com.cisdi.ext.base.PmPrjExt;
import com.cisdi.ext.model.AdEnt;
import com.cisdi.ext.pm.processCommon.ProcessCommon;
import com.cisdi.ext.util.DateTimeUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.StringUtils;

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
        COMMON_MAP.put("IS_CHECK_OVER","IS_CHECK_OVER");
        COMMON_MAP.put("IS_HISTORY_DATA","IS_HISTORY_DATA");
        COMMON_MAP.put("IS_OMPORT","IS_OMPORT");
        COMMON_MAP.put("CODE","CODE");
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
        StringBuilder sb2 = new StringBuilder("用户:").append(userName).append(" 在 ").append(now);
        for (EntityRecord tmp : list) {
            StringBuilder sb = new StringBuilder(sb2);
            Map<String,Object> map = tmp.valueMap;
            String proInstanceId = JdbcMapUtil.getString(map,"LK_WF_INST_ID");
            String isCheck = JdbcMapUtil.getString(map,"IS_CHECK_OVER");
            if (SharedUtil.isEmptyString(isCheck) || !"1".equals(isCheck)){
                String projectId = PmPrjExt.getProjectIdByProcess(map);
                String id = tmp.csCommId;
                String[] prjArr = projectId.split(",");
                for (String prj : prjArr) {
                    sb.append(" 对项目id为：").append(prj).append(" ,流程实例id为：").append(proInstanceId).append(",记录id为：").append(id).append(" 的数据点击了确认按钮！");
                    PmProUpdateLogExt.createData(codeId,id,prj,proInstanceId,userId,now,sb.toString(),"1688359200281030656");
                }
                Crud.from(entCode).where().eq("ID",id).update().set("IS_CHECK_OVER",1).exec();
            }
        }
    }

    /**
     * 台账数据修改
     */
    public void updateProData(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        String entCode = ExtJarHelper.sevInfo.get().entityInfo.code;
        String codeId = AdEnt.selectByWhere(new Where().eq(AdEnt.Cols.CODE,entCode)).get(0).getId();
        List<EntityRecord> list = ExtJarHelper.entityRecordList.get();
        String userId = ExtJarHelper.loginInfo.get().userId;
        String now = DateTimeUtil.dttmToString(new Date());
        String userName = ExtJarHelper.loginInfo.get().userName;
        StringBuilder sb = new StringBuilder("用户:").append(userName).append(" 在 ").append(now);
        for (EntityRecord tmp : list) {
            Map<String,Object> map = tmp.valueMap;
            String isCheck = JdbcMapUtil.getString(map,"IS_CHECK_OVER");
            if ("1".equals(isCheck)){
                throw new BaseException("抱歉，该条数据已核对完毕，不允许被修改！");
            } else {
                String prj = PmPrjExt.getProjectIdByProcess(map);
                String proInstanceId = JdbcMapUtil.getString(map,"LK_WF_INST_ID");
                String id = JdbcMapUtil.getString(map,"ID");
                map.entrySet().removeIf(p->COMMON_MAP.containsKey(p.getKey()));
                Map<String,Object> oldMap = myJdbcTemplate.queryForMap("select * from " + entCode + " where id = ?",id);
                StringBuilder nsb = getUpdateChangeMsg(map,oldMap);
                if (StringUtils.hasText(nsb)){
                    sb.append(" 时间修改了数据，修改内容有：").append(nsb);
                } else {
                    sb.append(" 时间点击了修改按钮，但是没有修改数据");
                }
                PmProUpdateLogExt.createData(codeId,id,prj,proInstanceId,userId,now,sb.toString(),"1688359123244249088");
            }
        }
    }

    /**
     * 台账数据修改，比较判断新老数据变化的地址
     * @param newMap 修改后map
     * @param oldMap 修改前map
     * @return 变化记录
     */
    private StringBuilder getUpdateChangeMsg(Map<String, Object> newMap, Map<String, Object> oldMap) {
        StringBuilder sb = new StringBuilder();
        for (String key : newMap.keySet()){
            if (oldMap.containsKey(key)){
                String newValue = JdbcMapUtil.getString(newMap,key);
                String oldValue = JdbcMapUtil.getString(oldMap,key);
                if (!StringUtils.hasText(newValue) && !StringUtils.hasText(oldValue)){
                    continue;
                } else if (StringUtils.hasText(newValue) && StringUtils.hasText(oldValue)){
                    if (!newValue.equals(oldValue)){
                        sb.append("字段：").append(key).append(",修改前值为：").append(oldValue).append(";修改后值为：").append(newValue).append(";");
                    }
                } else {
                    sb.append("字段：").append(key).append(",修改前值为：").append(oldValue).append(";修改后值为：").append(newValue).append(";");
                }

            }
        }
        return sb;
    }
}
