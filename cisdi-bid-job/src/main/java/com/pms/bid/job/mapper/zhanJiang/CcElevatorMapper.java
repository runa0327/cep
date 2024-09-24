package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcElevator;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CcElevatorMapper {


    int updateWfInstIdById(@Param("id")String id,@Param("lkWfInstId")String lkWfInstId);

    List<CcElevator> queryList();


}
