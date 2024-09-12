package com.pms.bid.job.domain.zhanJiang;

import com.pms.bid.job.domain.BaseDomain;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {"EN": "特种设备-起重机械", "ZH_CN": "特种设备-待办任务", "ZH_TW": "特种设备-起重机械"}。
 */
@Data
public class CcSpecialEquipTodo extends BaseDomain {

    private String  id;

    /**
     * {"EN": "特种设备ID", "ZH_CN": "特种设备ID", "ZH_TW": "特种设备ID"}。
     */
    private String ccSpecialEquipId;

    /**
     * {"EN": "特种设备类型", "ZH_CN": "特种设备类型", "ZH_TW": "特种设备类型"}。
     */
    private String ccSpecialEquipCtg;

    /**
     * {"EN": "特种设备待办类型", "ZH_CN": "特种设备待办类型", "ZH_TW": "特种设备待办类型"}。
     */
    private String ccSpecialEquipTodoType;


    /**
     * {"EN": "特种设备待办任务ID", "ZH_CN": "特种设备待办任务ID", "ZH_TW": "特种设备待办任务ID"}。
     */
    private String ccSpecialEquipTodoTaskId;


    /**
     * 关联任务是否关闭
     */
    private Integer taskIsEnd;

}