package com.pms.bid.job.mapper.zhanJiang;

import com.pms.bid.job.domain.zhanJiang.CcHoistingMachinery;
import com.pms.bid.job.domain.zhanJiang.CcSpecialEquipTodo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CcSpecialEquipTodoMapper {

    /**
     * 获取特种设备相关待办任务
     * @return
     */
    List<CcHoistingMachinery> queryList(@Param("equipId")String equipId,@Param("category")String category,@Param("todoType")String todoType);

    /**
     * 获取特种设备相关未完成任务
     * @return
     */
    List<CcHoistingMachinery> queryIncompleteList(@Param("equipId")String equipId,@Param("category")String category,@Param("todoType")String todoType);


    /**
     * 新增特种设备待办任务关联
     * @param equipTodo
     * @return
     */
    int addTodo(@Param("equipTodo") CcSpecialEquipTodo equipTodo);

    String getUserIdByMemberId(@Param("memberId")String  memberId);

}
