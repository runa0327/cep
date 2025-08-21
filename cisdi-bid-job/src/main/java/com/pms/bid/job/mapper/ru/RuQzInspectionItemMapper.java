package com.pms.bid.job.mapper.ru;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionItem;
import com.pms.bid.job.domain.qingZhu.RuQzInspectionResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RuQzInspectionItemMapper extends BaseMapper<RuQzInspectionItem> {

    public List<RuQzInspectionItem> selectListByZh(@Param("zhName")String  zhName);

    public RuQzInspectionItem selectOneByZh(@Param("zhName")String  zhName);

}
