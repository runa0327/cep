package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 周报明细。
 */
public class HrWeeklyReportDtl {

    /**
     * 模型助手。
     */
    private static final ModelHelper<HrWeeklyReportDtl> modelHelper = new ModelHelper<>("HR_WEEKLY_REPORT_DTL", new HrWeeklyReportDtl());

    /**
     * 待更新的列。
     */
    private List<String> toUpdateCols = new ArrayList<>();

    /**
     * 清除待更新的列。
     */
    public void clearToUpdateCols() {
        this.toUpdateCols.clear();
    }

    // 实体常量：
    // <editor-fold>

    public static final String ENT_CODE = "HR_WEEKLY_REPORT_DTL";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.TABLE;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 版本。
         */
        public static final String VER = "VER";
        /**
         * 时间戳。
         */
        public static final String TS = "TS";
        /**
         * 是否预设。
         */
        public static final String IS_PRESET = "IS_PRESET";
        /**
         * 创建日期时间。
         */
        public static final String CRT_DT = "CRT_DT";
        /**
         * 创建用户。
         */
        public static final String CRT_USER_ID = "CRT_USER_ID";
        /**
         * 最后修改日期时间。
         */
        public static final String LAST_MODI_DT = "LAST_MODI_DT";
        /**
         * 最后修改用户。
         */
        public static final String LAST_MODI_USER_ID = "LAST_MODI_USER_ID";
        /**
         * 记录状态。
         */
        public static final String STATUS = "STATUS";
        /**
         * 锁定流程实例。
         */
        public static final String LK_WF_INST_ID = "LK_WF_INST_ID";
        /**
         * 代码。
         */
        public static final String CODE = "CODE";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * 备注。
         */
        public static final String REMARK = "REMARK";
        /**
         * 流程。
         */
        public static final String WF_PROCESS_ID = "WF_PROCESS_ID";
        /**
         * 流程实例。
         */
        public static final String WF_PROCESS_INSTANCE_ID = "WF_PROCESS_INSTANCE_ID";
        /**
         * 是否发起。
         */
        public static final String IS_START = "IS_START";
        /**
         * 是否审核。
         */
        public static final String IS_APPROVE = "IS_APPROVE";
        /**
         * 是否办结。
         */
        public static final String IS_END = "IS_END";
        /**
         * 是否办结时通知部门。
         */
        public static final String IS_NOTI_DEPT_ON_END = "IS_NOTI_DEPT_ON_END";
        /**
         * 是否办结时通知分管领导。
         */
        public static final String IS_NOTI_LEADER_ON_END = "IS_NOTI_LEADER_ON_END";
        /**
         * 是否未结。
         */
        public static final String IS_UNEND = "IS_UNEND";
        /**
         * 项目。
         */
        public static final String PM_PRJ_ID = "PM_PRJ_ID";
        /**
         * 周报。
         */
        public static final String HR_WEEKLY_REPORT_ID = "HR_WEEKLY_REPORT_ID";
        /**
         * 所属的部门周报。
         */
        public static final String HR_WEEKLY_REPORT_ID_DEPT = "HR_WEEKLY_REPORT_ID_DEPT";
        /**
         * 所属的分管领导周报。
         */
        public static final String HR_WEEKLY_REPORT_ID_LEADER = "HR_WEEKLY_REPORT_ID_LEADER";
        /**
         * 所属的总经理周报。
         */
        public static final String HR_WEEKLY_REPORT_ID_GM = "HR_WEEKLY_REPORT_ID_GM";
        /**
         * 批号。
         */
        public static final String BATCH_ID = "BATCH_ID";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * ID。
     */
    private String id;

    /**
     * 获取：ID。
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置：ID。
     */
    public HrWeeklyReportDtl setId(String id) {
        if (this.id == null && id == null) {
            // 均为null，不做处理。
        } else if (this.id != null && id != null) {
            // 均非null，判定不等，再做处理：
            if (this.id.compareTo(id) != 0) {
                this.id = id;
                if (!this.toUpdateCols.contains("ID")) {
                    this.toUpdateCols.add("ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.id = id;
            if (!this.toUpdateCols.contains("ID")) {
                this.toUpdateCols.add("ID");
            }
        }
        return this;
    }

    /**
     * 版本。
     */
    private Integer ver;

    /**
     * 获取：版本。
     */
    public Integer getVer() {
        return this.ver;
    }

    /**
     * 设置：版本。
     */
    public HrWeeklyReportDtl setVer(Integer ver) {
        if (this.ver == null && ver == null) {
            // 均为null，不做处理。
        } else if (this.ver != null && ver != null) {
            // 均非null，判定不等，再做处理：
            if (this.ver.compareTo(ver) != 0) {
                this.ver = ver;
                if (!this.toUpdateCols.contains("VER")) {
                    this.toUpdateCols.add("VER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ver = ver;
            if (!this.toUpdateCols.contains("VER")) {
                this.toUpdateCols.add("VER");
            }
        }
        return this;
    }

    /**
     * 时间戳。
     */
    private LocalDateTime ts;

    /**
     * 获取：时间戳。
     */
    public LocalDateTime getTs() {
        return this.ts;
    }

    /**
     * 设置：时间戳。
     */
    public HrWeeklyReportDtl setTs(LocalDateTime ts) {
        if (this.ts == null && ts == null) {
            // 均为null，不做处理。
        } else if (this.ts != null && ts != null) {
            // 均非null，判定不等，再做处理：
            if (this.ts.compareTo(ts) != 0) {
                this.ts = ts;
                if (!this.toUpdateCols.contains("TS")) {
                    this.toUpdateCols.add("TS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ts = ts;
            if (!this.toUpdateCols.contains("TS")) {
                this.toUpdateCols.add("TS");
            }
        }
        return this;
    }

    /**
     * 是否预设。
     */
    private Boolean isPreset;

    /**
     * 获取：是否预设。
     */
    public Boolean getIsPreset() {
        return this.isPreset;
    }

    /**
     * 设置：是否预设。
     */
    public HrWeeklyReportDtl setIsPreset(Boolean isPreset) {
        if (this.isPreset == null && isPreset == null) {
            // 均为null，不做处理。
        } else if (this.isPreset != null && isPreset != null) {
            // 均非null，判定不等，再做处理：
            if (this.isPreset.compareTo(isPreset) != 0) {
                this.isPreset = isPreset;
                if (!this.toUpdateCols.contains("IS_PRESET")) {
                    this.toUpdateCols.add("IS_PRESET");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isPreset = isPreset;
            if (!this.toUpdateCols.contains("IS_PRESET")) {
                this.toUpdateCols.add("IS_PRESET");
            }
        }
        return this;
    }

    /**
     * 创建日期时间。
     */
    private LocalDateTime crtDt;

    /**
     * 获取：创建日期时间。
     */
    public LocalDateTime getCrtDt() {
        return this.crtDt;
    }

    /**
     * 设置：创建日期时间。
     */
    public HrWeeklyReportDtl setCrtDt(LocalDateTime crtDt) {
        if (this.crtDt == null && crtDt == null) {
            // 均为null，不做处理。
        } else if (this.crtDt != null && crtDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtDt.compareTo(crtDt) != 0) {
                this.crtDt = crtDt;
                if (!this.toUpdateCols.contains("CRT_DT")) {
                    this.toUpdateCols.add("CRT_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtDt = crtDt;
            if (!this.toUpdateCols.contains("CRT_DT")) {
                this.toUpdateCols.add("CRT_DT");
            }
        }
        return this;
    }

    /**
     * 创建用户。
     */
    private String crtUserId;

    /**
     * 获取：创建用户。
     */
    public String getCrtUserId() {
        return this.crtUserId;
    }

    /**
     * 设置：创建用户。
     */
    public HrWeeklyReportDtl setCrtUserId(String crtUserId) {
        if (this.crtUserId == null && crtUserId == null) {
            // 均为null，不做处理。
        } else if (this.crtUserId != null && crtUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.crtUserId.compareTo(crtUserId) != 0) {
                this.crtUserId = crtUserId;
                if (!this.toUpdateCols.contains("CRT_USER_ID")) {
                    this.toUpdateCols.add("CRT_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.crtUserId = crtUserId;
            if (!this.toUpdateCols.contains("CRT_USER_ID")) {
                this.toUpdateCols.add("CRT_USER_ID");
            }
        }
        return this;
    }

    /**
     * 最后修改日期时间。
     */
    private LocalDateTime lastModiDt;

    /**
     * 获取：最后修改日期时间。
     */
    public LocalDateTime getLastModiDt() {
        return this.lastModiDt;
    }

    /**
     * 设置：最后修改日期时间。
     */
    public HrWeeklyReportDtl setLastModiDt(LocalDateTime lastModiDt) {
        if (this.lastModiDt == null && lastModiDt == null) {
            // 均为null，不做处理。
        } else if (this.lastModiDt != null && lastModiDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.lastModiDt.compareTo(lastModiDt) != 0) {
                this.lastModiDt = lastModiDt;
                if (!this.toUpdateCols.contains("LAST_MODI_DT")) {
                    this.toUpdateCols.add("LAST_MODI_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lastModiDt = lastModiDt;
            if (!this.toUpdateCols.contains("LAST_MODI_DT")) {
                this.toUpdateCols.add("LAST_MODI_DT");
            }
        }
        return this;
    }

    /**
     * 最后修改用户。
     */
    private String lastModiUserId;

    /**
     * 获取：最后修改用户。
     */
    public String getLastModiUserId() {
        return this.lastModiUserId;
    }

    /**
     * 设置：最后修改用户。
     */
    public HrWeeklyReportDtl setLastModiUserId(String lastModiUserId) {
        if (this.lastModiUserId == null && lastModiUserId == null) {
            // 均为null，不做处理。
        } else if (this.lastModiUserId != null && lastModiUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.lastModiUserId.compareTo(lastModiUserId) != 0) {
                this.lastModiUserId = lastModiUserId;
                if (!this.toUpdateCols.contains("LAST_MODI_USER_ID")) {
                    this.toUpdateCols.add("LAST_MODI_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lastModiUserId = lastModiUserId;
            if (!this.toUpdateCols.contains("LAST_MODI_USER_ID")) {
                this.toUpdateCols.add("LAST_MODI_USER_ID");
            }
        }
        return this;
    }

    /**
     * 记录状态。
     */
    private String status;

    /**
     * 获取：记录状态。
     */
    public String getStatus() {
        return this.status;
    }

    /**
     * 设置：记录状态。
     */
    public HrWeeklyReportDtl setStatus(String status) {
        if (this.status == null && status == null) {
            // 均为null，不做处理。
        } else if (this.status != null && status != null) {
            // 均非null，判定不等，再做处理：
            if (this.status.compareTo(status) != 0) {
                this.status = status;
                if (!this.toUpdateCols.contains("STATUS")) {
                    this.toUpdateCols.add("STATUS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.status = status;
            if (!this.toUpdateCols.contains("STATUS")) {
                this.toUpdateCols.add("STATUS");
            }
        }
        return this;
    }

    /**
     * 锁定流程实例。
     */
    private String lkWfInstId;

    /**
     * 获取：锁定流程实例。
     */
    public String getLkWfInstId() {
        return this.lkWfInstId;
    }

    /**
     * 设置：锁定流程实例。
     */
    public HrWeeklyReportDtl setLkWfInstId(String lkWfInstId) {
        if (this.lkWfInstId == null && lkWfInstId == null) {
            // 均为null，不做处理。
        } else if (this.lkWfInstId != null && lkWfInstId != null) {
            // 均非null，判定不等，再做处理：
            if (this.lkWfInstId.compareTo(lkWfInstId) != 0) {
                this.lkWfInstId = lkWfInstId;
                if (!this.toUpdateCols.contains("LK_WF_INST_ID")) {
                    this.toUpdateCols.add("LK_WF_INST_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.lkWfInstId = lkWfInstId;
            if (!this.toUpdateCols.contains("LK_WF_INST_ID")) {
                this.toUpdateCols.add("LK_WF_INST_ID");
            }
        }
        return this;
    }

    /**
     * 代码。
     */
    private String code;

    /**
     * 获取：代码。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置：代码。
     */
    public HrWeeklyReportDtl setCode(String code) {
        if (this.code == null && code == null) {
            // 均为null，不做处理。
        } else if (this.code != null && code != null) {
            // 均非null，判定不等，再做处理：
            if (this.code.compareTo(code) != 0) {
                this.code = code;
                if (!this.toUpdateCols.contains("CODE")) {
                    this.toUpdateCols.add("CODE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.code = code;
            if (!this.toUpdateCols.contains("CODE")) {
                this.toUpdateCols.add("CODE");
            }
        }
        return this;
    }

    /**
     * 名称。
     */
    private String name;

    /**
     * 获取：名称。
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置：名称。
     */
    public HrWeeklyReportDtl setName(String name) {
        if (this.name == null && name == null) {
            // 均为null，不做处理。
        } else if (this.name != null && name != null) {
            // 均非null，判定不等，再做处理：
            if (this.name.compareTo(name) != 0) {
                this.name = name;
                if (!this.toUpdateCols.contains("NAME")) {
                    this.toUpdateCols.add("NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.name = name;
            if (!this.toUpdateCols.contains("NAME")) {
                this.toUpdateCols.add("NAME");
            }
        }
        return this;
    }

    /**
     * 备注。
     */
    private String remark;

    /**
     * 获取：备注。
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置：备注。
     */
    public HrWeeklyReportDtl setRemark(String remark) {
        if (this.remark == null && remark == null) {
            // 均为null，不做处理。
        } else if (this.remark != null && remark != null) {
            // 均非null，判定不等，再做处理：
            if (this.remark.compareTo(remark) != 0) {
                this.remark = remark;
                if (!this.toUpdateCols.contains("REMARK")) {
                    this.toUpdateCols.add("REMARK");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.remark = remark;
            if (!this.toUpdateCols.contains("REMARK")) {
                this.toUpdateCols.add("REMARK");
            }
        }
        return this;
    }

    /**
     * 流程。
     */
    private String wfProcessId;

    /**
     * 获取：流程。
     */
    public String getWfProcessId() {
        return this.wfProcessId;
    }

    /**
     * 设置：流程。
     */
    public HrWeeklyReportDtl setWfProcessId(String wfProcessId) {
        if (this.wfProcessId == null && wfProcessId == null) {
            // 均为null，不做处理。
        } else if (this.wfProcessId != null && wfProcessId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfProcessId.compareTo(wfProcessId) != 0) {
                this.wfProcessId = wfProcessId;
                if (!this.toUpdateCols.contains("WF_PROCESS_ID")) {
                    this.toUpdateCols.add("WF_PROCESS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfProcessId = wfProcessId;
            if (!this.toUpdateCols.contains("WF_PROCESS_ID")) {
                this.toUpdateCols.add("WF_PROCESS_ID");
            }
        }
        return this;
    }

    /**
     * 流程实例。
     */
    private String wfProcessInstanceId;

    /**
     * 获取：流程实例。
     */
    public String getWfProcessInstanceId() {
        return this.wfProcessInstanceId;
    }

    /**
     * 设置：流程实例。
     */
    public HrWeeklyReportDtl setWfProcessInstanceId(String wfProcessInstanceId) {
        if (this.wfProcessInstanceId == null && wfProcessInstanceId == null) {
            // 均为null，不做处理。
        } else if (this.wfProcessInstanceId != null && wfProcessInstanceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfProcessInstanceId.compareTo(wfProcessInstanceId) != 0) {
                this.wfProcessInstanceId = wfProcessInstanceId;
                if (!this.toUpdateCols.contains("WF_PROCESS_INSTANCE_ID")) {
                    this.toUpdateCols.add("WF_PROCESS_INSTANCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfProcessInstanceId = wfProcessInstanceId;
            if (!this.toUpdateCols.contains("WF_PROCESS_INSTANCE_ID")) {
                this.toUpdateCols.add("WF_PROCESS_INSTANCE_ID");
            }
        }
        return this;
    }

    /**
     * 是否发起。
     */
    private Boolean isStart;

    /**
     * 获取：是否发起。
     */
    public Boolean getIsStart() {
        return this.isStart;
    }

    /**
     * 设置：是否发起。
     */
    public HrWeeklyReportDtl setIsStart(Boolean isStart) {
        if (this.isStart == null && isStart == null) {
            // 均为null，不做处理。
        } else if (this.isStart != null && isStart != null) {
            // 均非null，判定不等，再做处理：
            if (this.isStart.compareTo(isStart) != 0) {
                this.isStart = isStart;
                if (!this.toUpdateCols.contains("IS_START")) {
                    this.toUpdateCols.add("IS_START");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isStart = isStart;
            if (!this.toUpdateCols.contains("IS_START")) {
                this.toUpdateCols.add("IS_START");
            }
        }
        return this;
    }

    /**
     * 是否审核。
     */
    private Boolean isApprove;

    /**
     * 获取：是否审核。
     */
    public Boolean getIsApprove() {
        return this.isApprove;
    }

    /**
     * 设置：是否审核。
     */
    public HrWeeklyReportDtl setIsApprove(Boolean isApprove) {
        if (this.isApprove == null && isApprove == null) {
            // 均为null，不做处理。
        } else if (this.isApprove != null && isApprove != null) {
            // 均非null，判定不等，再做处理：
            if (this.isApprove.compareTo(isApprove) != 0) {
                this.isApprove = isApprove;
                if (!this.toUpdateCols.contains("IS_APPROVE")) {
                    this.toUpdateCols.add("IS_APPROVE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isApprove = isApprove;
            if (!this.toUpdateCols.contains("IS_APPROVE")) {
                this.toUpdateCols.add("IS_APPROVE");
            }
        }
        return this;
    }

    /**
     * 是否办结。
     */
    private Boolean isEnd;

    /**
     * 获取：是否办结。
     */
    public Boolean getIsEnd() {
        return this.isEnd;
    }

    /**
     * 设置：是否办结。
     */
    public HrWeeklyReportDtl setIsEnd(Boolean isEnd) {
        if (this.isEnd == null && isEnd == null) {
            // 均为null，不做处理。
        } else if (this.isEnd != null && isEnd != null) {
            // 均非null，判定不等，再做处理：
            if (this.isEnd.compareTo(isEnd) != 0) {
                this.isEnd = isEnd;
                if (!this.toUpdateCols.contains("IS_END")) {
                    this.toUpdateCols.add("IS_END");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isEnd = isEnd;
            if (!this.toUpdateCols.contains("IS_END")) {
                this.toUpdateCols.add("IS_END");
            }
        }
        return this;
    }

    /**
     * 是否办结时通知部门。
     */
    private Boolean isNotiDeptOnEnd;

    /**
     * 获取：是否办结时通知部门。
     */
    public Boolean getIsNotiDeptOnEnd() {
        return this.isNotiDeptOnEnd;
    }

    /**
     * 设置：是否办结时通知部门。
     */
    public HrWeeklyReportDtl setIsNotiDeptOnEnd(Boolean isNotiDeptOnEnd) {
        if (this.isNotiDeptOnEnd == null && isNotiDeptOnEnd == null) {
            // 均为null，不做处理。
        } else if (this.isNotiDeptOnEnd != null && isNotiDeptOnEnd != null) {
            // 均非null，判定不等，再做处理：
            if (this.isNotiDeptOnEnd.compareTo(isNotiDeptOnEnd) != 0) {
                this.isNotiDeptOnEnd = isNotiDeptOnEnd;
                if (!this.toUpdateCols.contains("IS_NOTI_DEPT_ON_END")) {
                    this.toUpdateCols.add("IS_NOTI_DEPT_ON_END");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isNotiDeptOnEnd = isNotiDeptOnEnd;
            if (!this.toUpdateCols.contains("IS_NOTI_DEPT_ON_END")) {
                this.toUpdateCols.add("IS_NOTI_DEPT_ON_END");
            }
        }
        return this;
    }

    /**
     * 是否办结时通知分管领导。
     */
    private Boolean isNotiLeaderOnEnd;

    /**
     * 获取：是否办结时通知分管领导。
     */
    public Boolean getIsNotiLeaderOnEnd() {
        return this.isNotiLeaderOnEnd;
    }

    /**
     * 设置：是否办结时通知分管领导。
     */
    public HrWeeklyReportDtl setIsNotiLeaderOnEnd(Boolean isNotiLeaderOnEnd) {
        if (this.isNotiLeaderOnEnd == null && isNotiLeaderOnEnd == null) {
            // 均为null，不做处理。
        } else if (this.isNotiLeaderOnEnd != null && isNotiLeaderOnEnd != null) {
            // 均非null，判定不等，再做处理：
            if (this.isNotiLeaderOnEnd.compareTo(isNotiLeaderOnEnd) != 0) {
                this.isNotiLeaderOnEnd = isNotiLeaderOnEnd;
                if (!this.toUpdateCols.contains("IS_NOTI_LEADER_ON_END")) {
                    this.toUpdateCols.add("IS_NOTI_LEADER_ON_END");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isNotiLeaderOnEnd = isNotiLeaderOnEnd;
            if (!this.toUpdateCols.contains("IS_NOTI_LEADER_ON_END")) {
                this.toUpdateCols.add("IS_NOTI_LEADER_ON_END");
            }
        }
        return this;
    }

    /**
     * 是否未结。
     */
    private Boolean isUnend;

    /**
     * 获取：是否未结。
     */
    public Boolean getIsUnend() {
        return this.isUnend;
    }

    /**
     * 设置：是否未结。
     */
    public HrWeeklyReportDtl setIsUnend(Boolean isUnend) {
        if (this.isUnend == null && isUnend == null) {
            // 均为null，不做处理。
        } else if (this.isUnend != null && isUnend != null) {
            // 均非null，判定不等，再做处理：
            if (this.isUnend.compareTo(isUnend) != 0) {
                this.isUnend = isUnend;
                if (!this.toUpdateCols.contains("IS_UNEND")) {
                    this.toUpdateCols.add("IS_UNEND");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.isUnend = isUnend;
            if (!this.toUpdateCols.contains("IS_UNEND")) {
                this.toUpdateCols.add("IS_UNEND");
            }
        }
        return this;
    }

    /**
     * 项目。
     */
    private String pmPrjId;

    /**
     * 获取：项目。
     */
    public String getPmPrjId() {
        return this.pmPrjId;
    }

    /**
     * 设置：项目。
     */
    public HrWeeklyReportDtl setPmPrjId(String pmPrjId) {
        if (this.pmPrjId == null && pmPrjId == null) {
            // 均为null，不做处理。
        } else if (this.pmPrjId != null && pmPrjId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pmPrjId.compareTo(pmPrjId) != 0) {
                this.pmPrjId = pmPrjId;
                if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                    this.toUpdateCols.add("PM_PRJ_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pmPrjId = pmPrjId;
            if (!this.toUpdateCols.contains("PM_PRJ_ID")) {
                this.toUpdateCols.add("PM_PRJ_ID");
            }
        }
        return this;
    }

    /**
     * 周报。
     */
    private String hrWeeklyReportId;

    /**
     * 获取：周报。
     */
    public String getHrWeeklyReportId() {
        return this.hrWeeklyReportId;
    }

    /**
     * 设置：周报。
     */
    public HrWeeklyReportDtl setHrWeeklyReportId(String hrWeeklyReportId) {
        if (this.hrWeeklyReportId == null && hrWeeklyReportId == null) {
            // 均为null，不做处理。
        } else if (this.hrWeeklyReportId != null && hrWeeklyReportId != null) {
            // 均非null，判定不等，再做处理：
            if (this.hrWeeklyReportId.compareTo(hrWeeklyReportId) != 0) {
                this.hrWeeklyReportId = hrWeeklyReportId;
                if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID")) {
                    this.toUpdateCols.add("HR_WEEKLY_REPORT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hrWeeklyReportId = hrWeeklyReportId;
            if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID")) {
                this.toUpdateCols.add("HR_WEEKLY_REPORT_ID");
            }
        }
        return this;
    }

    /**
     * 所属的部门周报。
     */
    private String hrWeeklyReportIdDept;

    /**
     * 获取：所属的部门周报。
     */
    public String getHrWeeklyReportIdDept() {
        return this.hrWeeklyReportIdDept;
    }

    /**
     * 设置：所属的部门周报。
     */
    public HrWeeklyReportDtl setHrWeeklyReportIdDept(String hrWeeklyReportIdDept) {
        if (this.hrWeeklyReportIdDept == null && hrWeeklyReportIdDept == null) {
            // 均为null，不做处理。
        } else if (this.hrWeeklyReportIdDept != null && hrWeeklyReportIdDept != null) {
            // 均非null，判定不等，再做处理：
            if (this.hrWeeklyReportIdDept.compareTo(hrWeeklyReportIdDept) != 0) {
                this.hrWeeklyReportIdDept = hrWeeklyReportIdDept;
                if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID_DEPT")) {
                    this.toUpdateCols.add("HR_WEEKLY_REPORT_ID_DEPT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hrWeeklyReportIdDept = hrWeeklyReportIdDept;
            if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID_DEPT")) {
                this.toUpdateCols.add("HR_WEEKLY_REPORT_ID_DEPT");
            }
        }
        return this;
    }

    /**
     * 所属的分管领导周报。
     */
    private String hrWeeklyReportIdLeader;

    /**
     * 获取：所属的分管领导周报。
     */
    public String getHrWeeklyReportIdLeader() {
        return this.hrWeeklyReportIdLeader;
    }

    /**
     * 设置：所属的分管领导周报。
     */
    public HrWeeklyReportDtl setHrWeeklyReportIdLeader(String hrWeeklyReportIdLeader) {
        if (this.hrWeeklyReportIdLeader == null && hrWeeklyReportIdLeader == null) {
            // 均为null，不做处理。
        } else if (this.hrWeeklyReportIdLeader != null && hrWeeklyReportIdLeader != null) {
            // 均非null，判定不等，再做处理：
            if (this.hrWeeklyReportIdLeader.compareTo(hrWeeklyReportIdLeader) != 0) {
                this.hrWeeklyReportIdLeader = hrWeeklyReportIdLeader;
                if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID_LEADER")) {
                    this.toUpdateCols.add("HR_WEEKLY_REPORT_ID_LEADER");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hrWeeklyReportIdLeader = hrWeeklyReportIdLeader;
            if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID_LEADER")) {
                this.toUpdateCols.add("HR_WEEKLY_REPORT_ID_LEADER");
            }
        }
        return this;
    }

    /**
     * 所属的总经理周报。
     */
    private String hrWeeklyReportIdGm;

    /**
     * 获取：所属的总经理周报。
     */
    public String getHrWeeklyReportIdGm() {
        return this.hrWeeklyReportIdGm;
    }

    /**
     * 设置：所属的总经理周报。
     */
    public HrWeeklyReportDtl setHrWeeklyReportIdGm(String hrWeeklyReportIdGm) {
        if (this.hrWeeklyReportIdGm == null && hrWeeklyReportIdGm == null) {
            // 均为null，不做处理。
        } else if (this.hrWeeklyReportIdGm != null && hrWeeklyReportIdGm != null) {
            // 均非null，判定不等，再做处理：
            if (this.hrWeeklyReportIdGm.compareTo(hrWeeklyReportIdGm) != 0) {
                this.hrWeeklyReportIdGm = hrWeeklyReportIdGm;
                if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID_GM")) {
                    this.toUpdateCols.add("HR_WEEKLY_REPORT_ID_GM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.hrWeeklyReportIdGm = hrWeeklyReportIdGm;
            if (!this.toUpdateCols.contains("HR_WEEKLY_REPORT_ID_GM")) {
                this.toUpdateCols.add("HR_WEEKLY_REPORT_ID_GM");
            }
        }
        return this;
    }

    /**
     * 批号。
     */
    private String batchId;

    /**
     * 获取：批号。
     */
    public String getBatchId() {
        return this.batchId;
    }

    /**
     * 设置：批号。
     */
    public HrWeeklyReportDtl setBatchId(String batchId) {
        if (this.batchId == null && batchId == null) {
            // 均为null，不做处理。
        } else if (this.batchId != null && batchId != null) {
            // 均非null，判定不等，再做处理：
            if (this.batchId.compareTo(batchId) != 0) {
                this.batchId = batchId;
                if (!this.toUpdateCols.contains("BATCH_ID")) {
                    this.toUpdateCols.add("BATCH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.batchId = batchId;
            if (!this.toUpdateCols.contains("BATCH_ID")) {
                this.toUpdateCols.add("BATCH_ID");
            }
        }
        return this;
    }

    // </editor-fold>

    // 实例方法：
    // <editor-fold>

    /**
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void insertById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        modelHelper.insertById(includeCols, excludeCols, refreshThis, this.id, this);
        this.clearToUpdateCols();
    }

    /**
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void insertById(boolean refreshThis) {
        insertById(null, null, refreshThis);
    }

    /**
     * 根据ID插入数据。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     */
    public void insertById() {
        insertById(null, null, false);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param includeCols 更新时包含的列，空为包含所有。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(List<String> includeCols, List<String> excludeCols, boolean refreshThis) {
        if (SharedUtil.isEmptyList(includeCols) && SharedUtil.isEmptyList(toUpdateCols)) {
            // 既未指明includeCols，也无toUpdateCols，则不更新。

            if (refreshThis) {
                modelHelper.refreshThis(this.id, this, "无需更新，直接刷新");
            }
        } else {
            // 若已指明includeCols，或有toUpdateCols；则先以includeCols为准，再以toUpdateCols为准：
            modelHelper.updateById(SharedUtil.isEmptyList(includeCols) ? toUpdateCols : includeCols, excludeCols, refreshThis, this.id, this);
            this.clearToUpdateCols();
        }
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param refreshThis 更新后，是否刷新当前对象。刷新时将刷新所有列。
     */
    public void updateById(boolean refreshThis) {
        updateById(null, null, refreshThis);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     */
    public void updateById() {
        updateById(null, null, false);
    }

    /**
     * 根据ID删除数据。
     */
    public void deleteById() {
        modelHelper.deleteById(this.id);
    }

    // </editor-fold>

    // 静态方法：
    // <editor-fold>

    /**
     * 获取新的数据（未插入）。
     *
     * @return
     */
    public static HrWeeklyReportDtl newData() {
        HrWeeklyReportDtl obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static HrWeeklyReportDtl insertData() {
        HrWeeklyReportDtl obj = modelHelper.insertData();
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id          ID。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象，若无则为null。
     */
    public static HrWeeklyReportDtl selectById(String id, List<String> includeCols, List<String> excludeCols) {
        HrWeeklyReportDtl obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID获取数据。
     *
     * @param id ID。
     * @return 获取到的对象，若无则为null。
     */
    public static HrWeeklyReportDtl selectById(String id) {
        return selectById(id, null, null);
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<HrWeeklyReportDtl> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<HrWeeklyReportDtl> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids ID列表。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<HrWeeklyReportDtl> selectByIds(List<String> ids) {
        return selectByIds(ids, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<HrWeeklyReportDtl> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<HrWeeklyReportDtl> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<HrWeeklyReportDtl> selectByWhere(Where where) {
        return selectByWhere(where, null, null);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象。
     */
    public static HrWeeklyReportDtl selectOneByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<HrWeeklyReportDtl> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        if (objList != null && objList.size() > 1) {
            throw new BaseException("调用HrWeeklyReportDtl.selectOneByWhere方法不能返回" + objList.size() + "条记录（只能返回0条或1条）！");
        }

        return SharedUtil.isEmptyList(objList) ? null : objList.get(0);
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where Where条件。
     * @return 获取到的对象。
     */
    public static HrWeeklyReportDtl selectOneByWhere(Where where) {
        return selectOneByWhere(where, null, null);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param id          ID。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateById(String id, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateById(id, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据ID更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param id          ID。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateById(String id, Map<String, Object> keyValueMap) {
        return updateById(id, keyValueMap, null, null);
    }

    /**
     * 根据ID列表更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param ids         ID列表。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateByIds(List<String> ids, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateByIds(ids, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据ID列表更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param ids         ID列表。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateByIds(List<String> ids, Map<String, Object> keyValueMap) {
        return updateByIds(ids, keyValueMap, null, null);
    }

    /**
     * 根据Where条件更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param where       Where条件。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @param includeCols 更新时包含的列，空为包含所有keyValueMap里的列。
     * @param excludeCols 更新时排除的列，空为不排除。
     * @return 影响的行数。
     */
    public static int updateByWhere(Where where, Map<String, Object> keyValueMap, List<String> includeCols, List<String> excludeCols) {
        return modelHelper.updateByWhere(where, keyValueMap, includeCols, excludeCols);
    }

    /**
     * 根据Where条件更新数据。ID自身不会更新。将忽略用户设置、并自动设置VER、TS、LAST_MODI_DT、LAST_MODI_USER_ID（若有）。
     *
     * @param where       Where条件。
     * @param keyValueMap 要更新的列和新值。其中，key为列名、value为新值（可为null）。
     * @return 影响的行数。
     */
    public static int updateByWhere(Where where, Map<String, Object> keyValueMap) {
        return updateByWhere(where, keyValueMap, null, null);
    }

    /**
     * 根据ID删除数据。
     *
     * @param id ID。
     * @return 影响的行数。
     */
    public static int deleteById(String id) {
        return modelHelper.deleteById(id);
    }

    /**
     * 根据ID列表删除数据。
     *
     * @param ids ID列表。
     * @return 影响的行数。
     */
    public static int deleteByIds(List<String> ids) {
        return modelHelper.deleteByIds(ids);
    }

    /**
     * 根据Where条件删除数据。
     *
     * @param where Where条件。
     * @return 影响的行数。
     */
    public static int deleteByWhere(Where where) {
        return modelHelper.deleteByWhere(where);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel   从模型。
     * @param toModel     到模型。
     * @param includeCols 拷贝时包含的列，空为包含所有。
     * @param excludeCols 拷贝时排除的列，空为不排除。
     */
    public static void copyCols(HrWeeklyReportDtl fromModel, HrWeeklyReportDtl toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    /**
     * 拷贝列值。
     *
     * @param fromModel 从模型。
     * @param toModel   到模型。
     */
    public static void copyCols(HrWeeklyReportDtl fromModel, HrWeeklyReportDtl toModel) {
        copyCols(fromModel, toModel, null, null);
    }

    // </editor-fold>
}