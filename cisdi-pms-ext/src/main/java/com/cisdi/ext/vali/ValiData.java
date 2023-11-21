package com.cisdi.ext.vali;

import com.cisdi.ext.model.WfProcessInstance;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.util.JdbcMapUtil;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * vali环境数据刷新成固定模式
 */
public class ValiData {

    /**
     * 刷新流程实例名称
     */
    public void updateName(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();

        //刷项目名称
        List<Map<String,Object>> list1 = myJdbcTemplate.queryForList("select a.id,a.name,b.name as className from pm_prj a left join b on a.PROJECT_CLASSIFICATION_ID = b.id order by id asc");
        if (!CollectionUtils.isEmpty(list1)){
            AtomicInteger atomicInteger = new AtomicInteger(1);
            for (int i = 0; i < list1.size(); i++) {
                String name = JdbcMapUtil.getString(list1.get(i),"name");
                if ("猪种质资源保存与应用创新平台项目".equals(name)){
                    name = "项目1房建工程";
                } else if ("三亚崖州湾科技城生物谷外围道路二期".equals(name)){
                    name = "项目2市政道路";
                } else if ("深海科技城街道生态修复工程".equals(name)){
                    name = "项目3景观工程";
                } else if ("G98环岛高速公路三亚崖州湾科技城段改建工程".equals(name)){
                    name = "项目4公路工程";
                } else if ("三亚崖州湾科技城生物谷马鹿溪治理工程（一期）".equals(name)){
                    name = "项目5水利工程";
                } else if ("三亚崖州湾科技城深海科技城防洪排涝整治项目".equals(name)){
                    name = "项目6市政管道";
                } else if ("三亚崖州湾科技城南繁基础功能建设项目".equals(name)){
                    name = "项目7装修工程";
                } else if ("三亚崖州湾科技城综合服务中心地块电力增容工程".equals(name)){
                    name = "项目8专项工程";
                } else if ("中国科学院深海科学与工程研究所深远海新型多功能科考船项目".equals(name)){
                    name = "项目9船舶工程";
                } else if ("三亚南山港公共科考码头工程项目".equals(name)){
                    name = "项目10水运工程";
                } else if ("三亚水产苗种南繁生态产业园项目".equals(name)){
                    name = "项目11房建工程";
                } else if ("三亚崖州中心渔港水产品交易中心改造工程项目（二期）".equals(name)){
                    name = "项目12装修工程";
                } else if ("三亚崖州湾科技城植物基因芯片检测鉴定平台项目仪器设备购置".equals(name)){
                    name = "项目13设备采购";
                } else if ("全球动植物种质资源鉴定评价及确权交换中心项目设备采购".equals(name)){
                    name = "项目14设备采购";
                } else if ("三亚崖州湾科技城南繁综合服务中心项目等5宗地".equals(name)){
                    name = "项目15零星项目";
                } else if ("三亚市遥感信息产业园布水路建设项目等5宗地农转用测绘服务".equals(name)){
                    name = "项目16零星项目";
                } else {
                    name = "项目"+atomicInteger.incrementAndGet()+JdbcMapUtil.getString(list1.get(i),"className");
                }
                Crud.from("PM_PRJ").where().eq("ID", JdbcMapUtil.getString(list1.get(i),"id")).update()
                        .set("name",name).exec();
            }
        }

        // 刷新员工名称
        List<Map<String,Object>> list2 = myJdbcTemplate.queryForList("select id from ad_user order by id asc");
        if (!CollectionUtils.isEmpty(list2)){
            for (int i = 0; i < list2.size(); i++) {
                Crud.from("AD_USER").where().eq("ID",JdbcMapUtil.getString(list2.get(i),"id")).update()
                        .set("name","员工-"+(i+1)).exec();
            }
        }

        // 刷新流程实例
        List<WfProcessInstance> list3 = WfProcessInstance.selectByWhere(new Where().eq(WfProcessInstance.Cols.STATUS,"AP"));
        if (!CollectionUtils.isEmpty(list3)){
            String nowDate = "",name = "";
            for (WfProcessInstance tmp : list3) {
                String id = tmp.getId();
                name = tmp.getName();
                if (!name.contains("历史数据导入")){
                    nowDate = name.substring(name.length()-20);
                }
                name = "流程实例" + nowDate;
                Crud.from(WfProcessInstance.ENT_CODE).where().eq("ID",id).update()
                        .set("NAME",name).exec();
            }
        }

        // 刷新业主单位
        List<Map<String,Object>> list4 = myJdbcTemplate.queryForList("select id from PM_PARTY order by id asc");
        if (!CollectionUtils.isEmpty(list4)){
            for (int i = 0; i < list4.size(); i++) {
                Crud.from("PM_PARTY").where().eq("ID",JdbcMapUtil.getString(list4.get(i),"id")).update()
                        .set("name","合作方-"+(i+1)).exec();
            }
        }
    }
}
