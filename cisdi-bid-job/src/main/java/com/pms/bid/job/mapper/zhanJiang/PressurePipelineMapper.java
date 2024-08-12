package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.PressurePipeline;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface PressurePipelineMapper {

    /**
     * 查询实际投用时间为空的数据
     * @return
     */
    List<PressurePipeline> selectList();

    int updateInstIdById(@Param("id") String id,@Param("wfProcessInstanceId") String wfProcessInstanceId);

    /**
     * 修改时间节点
     * @param id
     * @param taskId
     * @param number
     */
    int updateTaskById(@Param("id")String id, @Param("taskId")String taskId, @Param("number")String number);

    /**
     * 根据项目id查询用户id
     * @param yjwAcceptanceManager
     * @return
     */
    String selectUserByPrjId(String yjwAcceptanceManager);

    String selectWfId(String id);

    Date selectDateByPipingId(String id);

    /**
     * 查询时间段内内容
     * @param start
     * @param end
     * @return
     */
    List<String> selectContentByTime(@Param("start") String start, @Param("end") String end,@Param("id") String id);

    int updateFilling(@Param("id") String id,@Param("filling") String filling);
}
