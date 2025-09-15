package demo.ext.test;

import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.shared.ad.ViewShowMode;
import com.qygly.shared.ad.ext.UrlToOpen;
import com.qygly.shared.ad.ext.ViewToOpen;
import com.qygly.shared.interaction.InvokeActResult;
import com.qygly.shared.sql.SqlWithParamValueList;
import com.qygly.shared.util.SharedUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TestExt {
    public void changeVar() {
    }

    public void save() {
    }

    public void f1() {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.urlToOpenList = new ArrayList<>();

        UrlToOpen urlToOpen = new UrlToOpen();
        urlToOpen.url = "https://www.baidu.com";
        urlToOpen.viewShowMode = ViewShowMode.DIALOG;
        urlToOpen.popupWidth = "500";
        urlToOpen.popupHeight = "80%";

        invokeActResult.urlToOpenList.add(urlToOpen);
    }

    public void f2() {
        InvokeActResult invokeActResult = new InvokeActResult();
        invokeActResult.viewToOpenList = new ArrayList<>();

        ViewToOpen viewToOpen = new ViewToOpen();
        viewToOpen.viewId = "视图ID";
        viewToOpen.viewShowMode = ViewShowMode.DRAWER;
        viewToOpen.drawerWidth = "30%";

        invokeActResult.viewToOpenList.add(viewToOpen);
    }

    public void f3() {
        Map<String, Object> varMap = ExtJarHelper.getVarMap();

        SqlWithParamValueList sqlWithParamValueList = new SqlWithParamValueList();
        sqlWithParamValueList.sql.append("select p.*\n" +
                "from km_prj_lib p\n" +
                "where 1 = 1\n" +
                "  and p.name like concat('%', ?, '%')\n" +
                "  #and p.remark like concat('%', ?, '%')\n" +
                "  and exists(select 1\n" +
                "             from km_prj_att_value v,\n" +
                "                  km_att ka\n" +
                "             where p.id = v.KM_PRJ_LIB_ID\n" +
                "               #and v.KM_UNIT_ATT ->> '$.attValue' >= ?\n" +
                "               and v.KM_UNIT_ATT ->> '$.attValue' <= ?\n" +
                "               and v.KM_ATT_ID = ka.ID\n" +
                "               and ka.code = 'CONSTRUCT_FEE')\n" +
                "  and exists(select 1\n" +
                "             from km_prj_att_value v,\n" +
                "                  km_att ka\n" +
                "             where p.id = v.KM_PRJ_LIB_ID\n" +
                "               and (find_in_set(?, v.KM_UNIT_ATT ->> '$.attValue')\n" +
                "                 or\n" +
                "                    find_in_set(?, v.KM_UNIT_ATT ->> '$.attValue')\n" +
                "                 or\n" +
                "                    find_in_set(?, v.KM_UNIT_ATT ->> '$.attValue')\n" +
                "                 )\n" +
                "               and v.KM_ATT_ID = ka.ID\n" +
                "               and ka.CODE = 'MAIN_STRUCT_FORM')\n" +
                ";");
        sqlWithParamValueList.paramValueList.add("项目");
        sqlWithParamValueList.paramValueList.add(200);


        Object pXxx = varMap.get("P_XXX");
        List<String> pXxxList = SharedUtil.splittedStrToStrList(pXxx.toString());
        // for (int i = 0; i < pXxxList.size(); i++) {
        //     sqlWithParamValueList.paramValueList.add(pXxxList.get(i));
        // }
        sqlWithParamValueList.paramValueList.addAll(pXxxList);


        List<Map<String, Object>> mapList = ExtJarHelper.getMyJdbcTemplate().queryForList(sqlWithParamValueList.sql.toString(), sqlWithParamValueList.paramValueList.toArray());
        ExtJarHelper.setReturnValue(mapList);
    }
}
