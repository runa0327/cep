package com.pms.bid.job.mapper.ru;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionDangerLevel;
import org.apache.ibatis.annotations.Param;

public interface RuQzInspectionDangerLevelMapper extends BaseMapper<RuQzInspectionDangerLevel> {

    public  RuQzInspectionDangerLevel  selectOneByZh(@Param("zhName")String  zhName);

}
