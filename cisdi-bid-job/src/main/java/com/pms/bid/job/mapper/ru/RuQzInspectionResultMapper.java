package com.pms.bid.job.mapper.ru;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionDangerLevel;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionResult;
import org.apache.ibatis.annotations.Param;

public interface RuQzInspectionResultMapper extends BaseMapper<RuQzInspectionResult> {

    public RuQzInspectionResult selectOneByZh(@Param("zhName")String  zhName);

}
