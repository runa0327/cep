package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcAssemblingPressureVessels;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CcAssemblingPressureVesselsMapper {


    int updateWfInstIdById(@Param("id")String id,@Param("lkWfInstId")String lkWfInstId);

    List<CcAssemblingPressureVessels> queryList();


}
