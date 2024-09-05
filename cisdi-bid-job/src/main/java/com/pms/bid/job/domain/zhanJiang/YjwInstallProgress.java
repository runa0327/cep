package com.pms.bid.job.domain.zhanJiang;

import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * {"EN": "设备安装进展", "ZH_CN": "设备安装进展", "ZH_TW": "设备安装进展"}。
 */
@Data
public class YjwInstallProgress {

    /**
     * {"EN": "ID", "ZH_CN": "ID", "ZH_TW": "繁：ID"}。
     */
    private String id;

    /**
     * {"EN": "VER", "ZH_CN": "版本", "ZH_TW": "繁：版本"}。
     */
    private Integer ver;

    /**
     * {"EN": "TS", "ZH_CN": "时间戳", "ZH_TW": "繁：时间戳"}。
     */
    private LocalDateTime ts;

    /**
     * {"EN": "IS_PRESET", "ZH_CN": "是否预设", "ZH_TW": "繁：是否预设"}。
     */
    private Integer isPreset;

    /**
     * {"EN": "CRT_DT", "ZH_CN": "创建日期时间", "ZH_TW": "繁：创建日期时间"}。
     */
    private Date crtDt;


    /**
     * {"EN": "CRT_USER_ID", "ZH_CN": "创建用户", "ZH_TW": "繁：创建用户"}。
     */
    private String crtUserId;



    /**
     * {"EN": "LAST_MODI_DT", "ZH_CN": "最后修改日期时间", "ZH_TW": "繁：最后修改日期时间"}。
     */
    private Date lastModiDt;

    /**
     * {"EN": "LAST_MODI_USER_ID", "ZH_CN": "最后修改用户", "ZH_TW": "繁：最后修改用户"}。
     */
    private String lastModiUserId;

    /**
     * {"EN": "STATUS", "ZH_CN": "记录状态", "ZH_TW": "繁：记录状态"}。
     */
    private String status;

    /**
     * {"EN": "LK_WF_INST_ID", "ZH_CN": "锁定流程实例", "ZH_TW": "繁：锁定流程实例"}。
     */
    private String lkWfInstId;

    /**
     * {"EN": "CODE", "ZH_CN": "代码", "ZH_TW": "繁：代码"}。
     */
    private String code;

    /**
     * {"EN": "NAME", "ZH_CN": "名称", "ZH_TW": "繁：名称"}。
     */
    private String name;

    /**
     * {"EN": "REMARK", "ZH_CN": "备注", "ZH_TW": "繁：备注"}。
     */
    private String remark;

    /**
     * {"EN": "FAST_CODE", "ZH_CN": "快捷码", "ZH_TW": "繁：快捷码"}。
     */
    private String fastCode;

    /**
     * {"EN": "ICON_FILE_GROUP_ID", "ZH_CN": "图标", "ZH_TW": "繁：图标"}。
     */
    private String iconFileGroupId;

    /**
     * {"EN": "附件", "ZH_CN": "附件", "ZH_TW": "附件"}。
     */
    private String ccAttachments;

    /**
     * {"EN": "压力管道", "ZH_CN": "压力管道", "ZH_TW": "压力管道"}。
     */
    private String yjwPressurePipelineId;

    /**
     * {"EN": "资料填报状态", "ZH_CN": "资料填报状态", "ZH_TW": "资料填报状态"}。
     */
    private Integer reviewIsFilled;

    /**
     * {"EN": "设备安装进度", "ZH_CN": "设备安装进度(%)", "ZH_TW": "设备安装进度"}。
     */
    private Integer ccSpecialEquipInstallProgress;

    /**
     * {"EN": "安装进度填报日期从", "ZH_CN": "安装进度填报日期从", "ZH_TW": "安装进度填报日期从"}。
     */
    private Date installFillDateFr;

    /**
     * {"EN": "安装进度填报日期到", "ZH_CN": "安装进度填报日期到", "ZH_TW": "安装进度填报日期到"}。
     */
    private Date installFillDateTo;

}