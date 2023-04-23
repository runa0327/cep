package com.cisdi.ext.base;

import cn.hutool.core.util.IdUtil;
import com.cisdi.ext.model.BasePostUser;
import com.cisdi.ext.model.PmPostProprj;
import com.cisdi.ext.model.PmProcessPostCon;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 流程岗位相关扩展
 */
public class BaseProcessPostExt {

    /**
     * 流程岗位节点配置岗位和业主单位信息
     * @param id 业主单位id
     */
    public static void cloneProcessPostCompany(String id) {
        //删除该业主单位下挂所有关联岗位流程节点信息
        Crud.from("PM_PROCESS_POST_CON").where().eq("CUSTOMER_UNIT",id).delete().exec();
        //查询开发公司所有下挂的信息
        List<PmProcessPostCon> list = PmProcessPostCon.selectByWhere(new Where().eq(PmProcessPostCon.Cols.STATUS,"AP").eq(PmProcessPostCon.Cols.CUSTOMER_UNIT,"0099902212142008831"));
        if (CollectionUtils.isEmpty(list)){
            list = PmProcessPostCon.selectByWhere(new Where().eq(PmProcessPostCon.Cols.STATUS,"AP").eq(PmProcessPostCon.Cols.CUSTOMER_UNIT,"0099902212142008832"));
        }
        if (!CollectionUtils.isEmpty(list)){
            list = list.stream().peek(p->{
              p.setId(Crud.from("PM_PROCESS_POST_CON").insertData());
              p.setCustomerUnit(id);
            }).collect(Collectors.toList());
        }
        if (!CollectionUtils.isEmpty(list)){
            list.forEach(p-> Crud.from("PM_PROCESS_POST_CON").where().eq("ID",p.getId()).update()
                    .set("CODE",p.getCode()).set("NAME",p.getName())
                    .set("STATUS",p.getStatus()).set("WF_NODE_ID",p.getWfNodeId())
                    .set("CUSTOMER_UNIT",p.getCustomerUnit()).set("BASE_PROCESS_POST_ID",p.getBaseProcessPostId())
                    .exec());
        }

    }

    /**
     * 流程岗位默认人员配置
     * @param id 业主单位id
     */
    public static void cloneProcessPostNormalUser(String id) {
        //查询该业主单位下是否有流程岗位默认信息配置，若有，则不进行新增
        List<BasePostUser> list = BasePostUser.selectByWhere(new Where().eq(BasePostUser.Cols.CUSTOMER_UNIT,id)
                .eq(BasePostUser.Cols.STATUS,"AP"));
        if (CollectionUtils.isEmpty(list)){
            list = BasePostUser.selectByWhere(new Where().eq(BasePostUser.Cols.CUSTOMER_UNIT,"0099902212142008831").eq(BasePostUser.Cols.STATUS,"AP"));
            if (!CollectionUtils.isEmpty(list)){
                list = list.stream().peek(p->{
                    p.setId(Crud.from("BASE_POST_USER").insertData());
                    p.setCustomerUnit(id);
                }).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(list)){
                list.forEach(p->Crud.from("BASE_POST_USER").where().eq("ID",p.getId()).update()
                        .set("STATUS",p.getStatus()).set("CUSTOMER_UNIT",p.getCustomerUnit()).set("CODE",p.getCode())
                        .set("NAME",p.getName()).set("AD_USER_ID",p.getAdUserId())
                        .exec());
            }
        }

    }

    /**
     * 根据业主单位克隆 流程岗位项目岗位关联关系
     * @param id 业主单位id
     */
    public static void clonePostDept(String id) {
        //查询该业主单位下是否有流程岗位默认信息配置，若有，则不进行新增
        List<PmPostProprj> list = PmPostProprj.selectByWhere(new Where().eq(PmPostProprj.Cols.CUSTOMER_UNIT,id)
                .eq(PmPostProprj.Cols.STATUS,"AP"));
        if (CollectionUtils.isEmpty(list)){
            list = PmPostProprj.selectByWhere(new Where().eq(PmPostProprj.Cols.CUSTOMER_UNIT,"0099902212142008831").eq(PmPostProprj.Cols.STATUS,"AP"));
            if (!CollectionUtils.isEmpty(list)){
                list = list.stream().peek(p->{
                    p.setId(Crud.from("PM_POST_PROPRJ").insertData());
                    p.setCustomerUnit(id);
                }).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(list)){
                list.forEach(p->Crud.from("PM_POST_PROPRJ").where().eq("ID",p.getId()).update()
                        .set("STATUS",p.getStatus()).set("CUSTOMER_UNIT",p.getCustomerUnit())
                        .set("POST_INFO_ID",p.getPostInfoId()).set("BASE_PROCESS_POST_ID",p.getBaseProcessPostId())
                        .exec());
            }
        }

    }
}
