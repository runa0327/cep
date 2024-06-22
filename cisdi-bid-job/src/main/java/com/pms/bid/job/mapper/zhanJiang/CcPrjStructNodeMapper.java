package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcPrjStructNode;

import java.util.List;

public interface CcPrjStructNodeMapper {

    /**
     * 获取单元工程名称编码信息（目前只查询项目id为1790672761571196928的项目的数据）
     * @return 查询结果
     */
    List<CcPrjStructNode> queryUnitProjectList();
}
