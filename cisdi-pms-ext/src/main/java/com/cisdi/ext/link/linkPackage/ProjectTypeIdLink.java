package com.cisdi.ext.link.linkPackage;

import com.cisdi.ext.link.AttLinkResult;
import com.cisdi.ext.link.LinkSql;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.att.AttDataTypeE;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;

import java.util.List;
import java.util.Map;

/**
 * 项目类型-属性联动
 */
public class ProjectTypeIdLink {

    /**
     * 项目类型属性联动入口
     * @param myJdbcTemplate 数据源
     * @param attValue 项目类型值
     * @return 联动结果
     */
    public static AttLinkResult linkForPROJECT_TYPE_ID(MyJdbcTemplate myJdbcTemplate, String attValue) {

        AttLinkResult attLinkResult = new AttLinkResult();
        List<Map<String, Object>> list = LinkSql.selectProjectType(myJdbcTemplate,attValue);

        if (SharedUtil.isEmptyList(list)) {
            throw new BaseException("未设置相应项目类型的联动！");
        } else if (list.size() > 1) {
            throw new BaseException("重复设置相应项目类型的联动！");
        }

        Map<String, Object> row = list.get(0);
        AttLinkExtDetail.clearProjectTypeData(attLinkResult);
        AttLinkExtDetail.prjTypeLinkNew(row,attLinkResult);

        //建设规模类型
        String scaleTypeId = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_ID");
        String scaleTypeName = JdbcMapUtil.getString(row, "CON_SCALE_TYPE_NAME");
        LinkUtils.mapAddAllValue("CON_SCALE_TYPE_ID",AttDataTypeE.REF_SINGLE,scaleTypeId,scaleTypeName,true,true,false,attLinkResult);

        //建设规模单位
        String id = JdbcMapUtil.getString(row, "CON_SCALE_UOM_ID");
        String name = JdbcMapUtil.getString(row, "CON_SCALE_UOM_NAME");
        LinkUtils.mapAddAllValue("CON_SCALE_UOM_ID",AttDataTypeE.REF_SINGLE,id,name,true,true,false,attLinkResult);

        return attLinkResult;
    }
}
