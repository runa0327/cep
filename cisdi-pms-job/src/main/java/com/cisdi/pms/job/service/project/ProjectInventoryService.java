package com.cisdi.pms.job.service.project;

import com.cisdi.pms.job.domain.project.ProjectInventory;

import java.util.List;
import java.util.Map;

public interface ProjectInventoryService {

    /**
     * 根据流程信息查询该流程对应的资料清单类型
     * @param processId 流程id
     * @return 流程资料清单
     */
    List<ProjectInventory> queryProInventory(String processId);

    /**
     * 根据项目id找到项目资料清单
     * @param prjId 项目id
     * @return 项目资料清单
     */
    List<ProjectInventory> queryProjectInventory(String prjId);


    /**
     * 创建或更新项目清单及清单文件明细
     * @param processInstanceId 流程实例id
     * @param processId 流程id
     * @param map 对应流程详情
     * @param procInventoryList 流程资料清单
     * @param projectInventoryList 项目清单
     */
    String createOrUpdateInventory(String processInstanceId, String processId, Map<String, Object> map, List<ProjectInventory> procInventoryList, List<ProjectInventory> projectInventoryList);
}
