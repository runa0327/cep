package com.pms.bid.job.mapper.zhanJiang;



import com.pms.bid.job.domain.process.SpecialEquipPreVe;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CcSpecialEquipPreVeMapper {


    List<SpecialEquipPreVe> queryBaseFillList();

    List<SpecialEquipPreVe> queryConFillList();

    List<SpecialEquipPreVe> queryPlanUseRegFillList();

    List<SpecialEquipPreVe> queryComUseRegFillList();

    List<SpecialEquipPreVe> queryBeOverdueList1();

    List<SpecialEquipPreVe> queryBeOverdueList2();

    void updateInstId1ById(@Param("id") String id, @Param("lkWfInstId1") String lkWfInstId1);
    void updateInstId2ById(@Param("id") String id, @Param("lkWfInstId2") String lkWfInstId2);
    void updateInstId3ById(@Param("id") String id, @Param("lkWfInstId3") String lkWfInstId3);
    void updateInstId4ById(@Param("id") String id, @Param("lkWfInstId4") String lkWfInstId4);

    int updateInstIdById(@Param("id") String id, @Param("lkWfInstId") String lkWfInstId);


}
