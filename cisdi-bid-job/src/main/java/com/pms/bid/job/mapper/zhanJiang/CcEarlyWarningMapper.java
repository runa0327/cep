package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcEarlyWarning;
import com.pms.bid.job.domain.zhanJiang.PressurePipeline;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface CcEarlyWarningMapper {


    /**
     * 新增警告
     * @param ccEarlyWarning
     */
    int insert(CcEarlyWarning ccEarlyWarning);

}
