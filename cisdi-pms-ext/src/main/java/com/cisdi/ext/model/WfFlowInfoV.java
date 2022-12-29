package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 流转信息。
 */
public class WfFlowInfoV {

    /**
     * 模型助手。
     */
    private static final ModelHelper<WfFlowInfoV> modelHelper = new ModelHelper<>("WF_FLOW_INFO_V", new WfFlowInfoV());

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

    public static final String ENT_CODE = "WF_FLOW_INFO_V";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.SUB_QUERY;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 流程。
         */
        public static final String WF_PROCESS_ID = "WF_PROCESS_ID";
        /**
         * 流程图标。
         */
        public static final String WF_PROCESS_ICON_FILE_GROUP_ID = "WF_PROCESS_ICON_FILE_GROUP_ID";
        /**
         * 流程实例。
         */
        public static final String WF_PROCESS_INSTANCE_ID = "WF_PROCESS_INSTANCE_ID";
        /**
         * 流程实例开始日期时间。
         */
        public static final String PI_START_DT = "PI_START_DT";
        /**
         * 流程实例结束日期时间。
         */
        public static final String PI_END_DT = "PI_END_DT";
        /**
         * 节点实例名称。
         */
        public static final String NI_NAME = "NI_NAME";
        /**
         * 节点实例序号。
         */
        public static final String NI_SEQ_NO = "NI_SEQ_NO";
        /**
         * 任务类型。
         */
        public static final String WF_TASK_TYPE_ID = "WF_TASK_TYPE_ID";
        /**
         * 节点。
         */
        public static final String WF_NODE_ID = "WF_NODE_ID";
        /**
         * 节点视图ID。
         */
        public static final String WF_NODE_VIEW_ID = "WF_NODE_VIEW_ID";
        /**
         * 任务。
         */
        public static final String WF_TASK_ID = "WF_TASK_ID";
        /**
         * 节点实例生效操作。
         */
        public static final String NI_EFF_ACT_ID = "NI_EFF_ACT_ID";
        /**
         * 任务是否关闭。
         */
        public static final String TASK_IS_CLOSED = "TASK_IS_CLOSED";
        /**
         * 任务序号。
         */
        public static final String TASK_SEQ_NO = "TASK_SEQ_NO";
        /**
         * 任务接收日期时间。
         */
        public static final String TASK_REC_DT = "TASK_REC_DT";
        /**
         * 任务查看日期时间。
         */
        public static final String TASK_VIEW_DT = "TASK_VIEW_DT";
        /**
         * 任务操作日期时间。
         */
        public static final String TASK_ACT_DT = "TASK_ACT_DT";
        /**
         * 任务用户。
         */
        public static final String TASK_USER_ID = "TASK_USER_ID";
        /**
         * 任务操作。
         */
        public static final String TASK_ACT_ID = "TASK_ACT_ID";
        /**
         * 任务用户批注。
         */
        public static final String TASK_USER_COMMENT = "TASK_USER_COMMENT";
        /**
         * 任务用户附件。
         */
        public static final String TASK_USER_ATTACHMENT = "TASK_USER_ATTACHMENT";
        /**
         * 实体。
         */
        public static final String AD_ENT_ID = "AD_ENT_ID";
        /**
         * 实体记录ID。
         */
        public static final String ENTITY_RECORD_ID = "ENTITY_RECORD_ID";
        /**
         * 当前节点实例。
         */
        public static final String CURRENT_WF_NODE_INSTANCE_ID = "CURRENT_WF_NODE_INSTANCE_ID";
        /**
         * 当前节点实例。
         */
        public static final String CURRENT_NI_ID = "CURRENT_NI_ID";
        /**
         * 当前节点开始时间。
         */
        public static final String CURRENT_NI_START_DT = "CURRENT_NI_START_DT";
        /**
         * 当前节点结束时间。
         */
        public static final String CURRENT_NI_END_DT = "CURRENT_NI_END_DT";
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
    public WfFlowInfoV setId(String id) {
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
    public WfFlowInfoV setWfProcessId(String wfProcessId) {
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
     * 流程图标。
     */
    private String wfProcessIconFileGroupId;

    /**
     * 获取：流程图标。
     */
    public String getWfProcessIconFileGroupId() {
        return this.wfProcessIconFileGroupId;
    }

    /**
     * 设置：流程图标。
     */
    public WfFlowInfoV setWfProcessIconFileGroupId(String wfProcessIconFileGroupId) {
        if (this.wfProcessIconFileGroupId == null && wfProcessIconFileGroupId == null) {
            // 均为null，不做处理。
        } else if (this.wfProcessIconFileGroupId != null && wfProcessIconFileGroupId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfProcessIconFileGroupId.compareTo(wfProcessIconFileGroupId) != 0) {
                this.wfProcessIconFileGroupId = wfProcessIconFileGroupId;
                if (!this.toUpdateCols.contains("WF_PROCESS_ICON_FILE_GROUP_ID")) {
                    this.toUpdateCols.add("WF_PROCESS_ICON_FILE_GROUP_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfProcessIconFileGroupId = wfProcessIconFileGroupId;
            if (!this.toUpdateCols.contains("WF_PROCESS_ICON_FILE_GROUP_ID")) {
                this.toUpdateCols.add("WF_PROCESS_ICON_FILE_GROUP_ID");
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
    public WfFlowInfoV setWfProcessInstanceId(String wfProcessInstanceId) {
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
     * 流程实例开始日期时间。
     */
    private LocalDateTime piStartDt;

    /**
     * 获取：流程实例开始日期时间。
     */
    public LocalDateTime getPiStartDt() {
        return this.piStartDt;
    }

    /**
     * 设置：流程实例开始日期时间。
     */
    public WfFlowInfoV setPiStartDt(LocalDateTime piStartDt) {
        if (this.piStartDt == null && piStartDt == null) {
            // 均为null，不做处理。
        } else if (this.piStartDt != null && piStartDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.piStartDt.compareTo(piStartDt) != 0) {
                this.piStartDt = piStartDt;
                if (!this.toUpdateCols.contains("PI_START_DT")) {
                    this.toUpdateCols.add("PI_START_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.piStartDt = piStartDt;
            if (!this.toUpdateCols.contains("PI_START_DT")) {
                this.toUpdateCols.add("PI_START_DT");
            }
        }
        return this;
    }

    /**
     * 流程实例结束日期时间。
     */
    private LocalDateTime piEndDt;

    /**
     * 获取：流程实例结束日期时间。
     */
    public LocalDateTime getPiEndDt() {
        return this.piEndDt;
    }

    /**
     * 设置：流程实例结束日期时间。
     */
    public WfFlowInfoV setPiEndDt(LocalDateTime piEndDt) {
        if (this.piEndDt == null && piEndDt == null) {
            // 均为null，不做处理。
        } else if (this.piEndDt != null && piEndDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.piEndDt.compareTo(piEndDt) != 0) {
                this.piEndDt = piEndDt;
                if (!this.toUpdateCols.contains("PI_END_DT")) {
                    this.toUpdateCols.add("PI_END_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.piEndDt = piEndDt;
            if (!this.toUpdateCols.contains("PI_END_DT")) {
                this.toUpdateCols.add("PI_END_DT");
            }
        }
        return this;
    }

    /**
     * 节点实例名称。
     */
    private String niName;

    /**
     * 获取：节点实例名称。
     */
    public String getNiName() {
        return this.niName;
    }

    /**
     * 设置：节点实例名称。
     */
    public WfFlowInfoV setNiName(String niName) {
        if (this.niName == null && niName == null) {
            // 均为null，不做处理。
        } else if (this.niName != null && niName != null) {
            // 均非null，判定不等，再做处理：
            if (this.niName.compareTo(niName) != 0) {
                this.niName = niName;
                if (!this.toUpdateCols.contains("NI_NAME")) {
                    this.toUpdateCols.add("NI_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.niName = niName;
            if (!this.toUpdateCols.contains("NI_NAME")) {
                this.toUpdateCols.add("NI_NAME");
            }
        }
        return this;
    }

    /**
     * 节点实例序号。
     */
    private BigDecimal niSeqNo;

    /**
     * 获取：节点实例序号。
     */
    public BigDecimal getNiSeqNo() {
        return this.niSeqNo;
    }

    /**
     * 设置：节点实例序号。
     */
    public WfFlowInfoV setNiSeqNo(BigDecimal niSeqNo) {
        if (this.niSeqNo == null && niSeqNo == null) {
            // 均为null，不做处理。
        } else if (this.niSeqNo != null && niSeqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.niSeqNo.compareTo(niSeqNo) != 0) {
                this.niSeqNo = niSeqNo;
                if (!this.toUpdateCols.contains("NI_SEQ_NO")) {
                    this.toUpdateCols.add("NI_SEQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.niSeqNo = niSeqNo;
            if (!this.toUpdateCols.contains("NI_SEQ_NO")) {
                this.toUpdateCols.add("NI_SEQ_NO");
            }
        }
        return this;
    }

    /**
     * 任务类型。
     */
    private String wfTaskTypeId;

    /**
     * 获取：任务类型。
     */
    public String getWfTaskTypeId() {
        return this.wfTaskTypeId;
    }

    /**
     * 设置：任务类型。
     */
    public WfFlowInfoV setWfTaskTypeId(String wfTaskTypeId) {
        if (this.wfTaskTypeId == null && wfTaskTypeId == null) {
            // 均为null，不做处理。
        } else if (this.wfTaskTypeId != null && wfTaskTypeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfTaskTypeId.compareTo(wfTaskTypeId) != 0) {
                this.wfTaskTypeId = wfTaskTypeId;
                if (!this.toUpdateCols.contains("WF_TASK_TYPE_ID")) {
                    this.toUpdateCols.add("WF_TASK_TYPE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfTaskTypeId = wfTaskTypeId;
            if (!this.toUpdateCols.contains("WF_TASK_TYPE_ID")) {
                this.toUpdateCols.add("WF_TASK_TYPE_ID");
            }
        }
        return this;
    }

    /**
     * 节点。
     */
    private String wfNodeId;

    /**
     * 获取：节点。
     */
    public String getWfNodeId() {
        return this.wfNodeId;
    }

    /**
     * 设置：节点。
     */
    public WfFlowInfoV setWfNodeId(String wfNodeId) {
        if (this.wfNodeId == null && wfNodeId == null) {
            // 均为null，不做处理。
        } else if (this.wfNodeId != null && wfNodeId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfNodeId.compareTo(wfNodeId) != 0) {
                this.wfNodeId = wfNodeId;
                if (!this.toUpdateCols.contains("WF_NODE_ID")) {
                    this.toUpdateCols.add("WF_NODE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfNodeId = wfNodeId;
            if (!this.toUpdateCols.contains("WF_NODE_ID")) {
                this.toUpdateCols.add("WF_NODE_ID");
            }
        }
        return this;
    }

    /**
     * 节点视图ID。
     */
    private String wfNodeViewId;

    /**
     * 获取：节点视图ID。
     */
    public String getWfNodeViewId() {
        return this.wfNodeViewId;
    }

    /**
     * 设置：节点视图ID。
     */
    public WfFlowInfoV setWfNodeViewId(String wfNodeViewId) {
        if (this.wfNodeViewId == null && wfNodeViewId == null) {
            // 均为null，不做处理。
        } else if (this.wfNodeViewId != null && wfNodeViewId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfNodeViewId.compareTo(wfNodeViewId) != 0) {
                this.wfNodeViewId = wfNodeViewId;
                if (!this.toUpdateCols.contains("WF_NODE_VIEW_ID")) {
                    this.toUpdateCols.add("WF_NODE_VIEW_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfNodeViewId = wfNodeViewId;
            if (!this.toUpdateCols.contains("WF_NODE_VIEW_ID")) {
                this.toUpdateCols.add("WF_NODE_VIEW_ID");
            }
        }
        return this;
    }

    /**
     * 任务。
     */
    private String wfTaskId;

    /**
     * 获取：任务。
     */
    public String getWfTaskId() {
        return this.wfTaskId;
    }

    /**
     * 设置：任务。
     */
    public WfFlowInfoV setWfTaskId(String wfTaskId) {
        if (this.wfTaskId == null && wfTaskId == null) {
            // 均为null，不做处理。
        } else if (this.wfTaskId != null && wfTaskId != null) {
            // 均非null，判定不等，再做处理：
            if (this.wfTaskId.compareTo(wfTaskId) != 0) {
                this.wfTaskId = wfTaskId;
                if (!this.toUpdateCols.contains("WF_TASK_ID")) {
                    this.toUpdateCols.add("WF_TASK_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.wfTaskId = wfTaskId;
            if (!this.toUpdateCols.contains("WF_TASK_ID")) {
                this.toUpdateCols.add("WF_TASK_ID");
            }
        }
        return this;
    }

    /**
     * 节点实例生效操作。
     */
    private String niEffActId;

    /**
     * 获取：节点实例生效操作。
     */
    public String getNiEffActId() {
        return this.niEffActId;
    }

    /**
     * 设置：节点实例生效操作。
     */
    public WfFlowInfoV setNiEffActId(String niEffActId) {
        if (this.niEffActId == null && niEffActId == null) {
            // 均为null，不做处理。
        } else if (this.niEffActId != null && niEffActId != null) {
            // 均非null，判定不等，再做处理：
            if (this.niEffActId.compareTo(niEffActId) != 0) {
                this.niEffActId = niEffActId;
                if (!this.toUpdateCols.contains("NI_EFF_ACT_ID")) {
                    this.toUpdateCols.add("NI_EFF_ACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.niEffActId = niEffActId;
            if (!this.toUpdateCols.contains("NI_EFF_ACT_ID")) {
                this.toUpdateCols.add("NI_EFF_ACT_ID");
            }
        }
        return this;
    }

    /**
     * 任务是否关闭。
     */
    private Boolean taskIsClosed;

    /**
     * 获取：任务是否关闭。
     */
    public Boolean getTaskIsClosed() {
        return this.taskIsClosed;
    }

    /**
     * 设置：任务是否关闭。
     */
    public WfFlowInfoV setTaskIsClosed(Boolean taskIsClosed) {
        if (this.taskIsClosed == null && taskIsClosed == null) {
            // 均为null，不做处理。
        } else if (this.taskIsClosed != null && taskIsClosed != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskIsClosed.compareTo(taskIsClosed) != 0) {
                this.taskIsClosed = taskIsClosed;
                if (!this.toUpdateCols.contains("TASK_IS_CLOSED")) {
                    this.toUpdateCols.add("TASK_IS_CLOSED");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskIsClosed = taskIsClosed;
            if (!this.toUpdateCols.contains("TASK_IS_CLOSED")) {
                this.toUpdateCols.add("TASK_IS_CLOSED");
            }
        }
        return this;
    }

    /**
     * 任务序号。
     */
    private BigDecimal taskSeqNo;

    /**
     * 获取：任务序号。
     */
    public BigDecimal getTaskSeqNo() {
        return this.taskSeqNo;
    }

    /**
     * 设置：任务序号。
     */
    public WfFlowInfoV setTaskSeqNo(BigDecimal taskSeqNo) {
        if (this.taskSeqNo == null && taskSeqNo == null) {
            // 均为null，不做处理。
        } else if (this.taskSeqNo != null && taskSeqNo != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskSeqNo.compareTo(taskSeqNo) != 0) {
                this.taskSeqNo = taskSeqNo;
                if (!this.toUpdateCols.contains("TASK_SEQ_NO")) {
                    this.toUpdateCols.add("TASK_SEQ_NO");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskSeqNo = taskSeqNo;
            if (!this.toUpdateCols.contains("TASK_SEQ_NO")) {
                this.toUpdateCols.add("TASK_SEQ_NO");
            }
        }
        return this;
    }

    /**
     * 任务接收日期时间。
     */
    private LocalDateTime taskRecDt;

    /**
     * 获取：任务接收日期时间。
     */
    public LocalDateTime getTaskRecDt() {
        return this.taskRecDt;
    }

    /**
     * 设置：任务接收日期时间。
     */
    public WfFlowInfoV setTaskRecDt(LocalDateTime taskRecDt) {
        if (this.taskRecDt == null && taskRecDt == null) {
            // 均为null，不做处理。
        } else if (this.taskRecDt != null && taskRecDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskRecDt.compareTo(taskRecDt) != 0) {
                this.taskRecDt = taskRecDt;
                if (!this.toUpdateCols.contains("TASK_REC_DT")) {
                    this.toUpdateCols.add("TASK_REC_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskRecDt = taskRecDt;
            if (!this.toUpdateCols.contains("TASK_REC_DT")) {
                this.toUpdateCols.add("TASK_REC_DT");
            }
        }
        return this;
    }

    /**
     * 任务查看日期时间。
     */
    private LocalDateTime taskViewDt;

    /**
     * 获取：任务查看日期时间。
     */
    public LocalDateTime getTaskViewDt() {
        return this.taskViewDt;
    }

    /**
     * 设置：任务查看日期时间。
     */
    public WfFlowInfoV setTaskViewDt(LocalDateTime taskViewDt) {
        if (this.taskViewDt == null && taskViewDt == null) {
            // 均为null，不做处理。
        } else if (this.taskViewDt != null && taskViewDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskViewDt.compareTo(taskViewDt) != 0) {
                this.taskViewDt = taskViewDt;
                if (!this.toUpdateCols.contains("TASK_VIEW_DT")) {
                    this.toUpdateCols.add("TASK_VIEW_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskViewDt = taskViewDt;
            if (!this.toUpdateCols.contains("TASK_VIEW_DT")) {
                this.toUpdateCols.add("TASK_VIEW_DT");
            }
        }
        return this;
    }

    /**
     * 任务操作日期时间。
     */
    private LocalDateTime taskActDt;

    /**
     * 获取：任务操作日期时间。
     */
    public LocalDateTime getTaskActDt() {
        return this.taskActDt;
    }

    /**
     * 设置：任务操作日期时间。
     */
    public WfFlowInfoV setTaskActDt(LocalDateTime taskActDt) {
        if (this.taskActDt == null && taskActDt == null) {
            // 均为null，不做处理。
        } else if (this.taskActDt != null && taskActDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskActDt.compareTo(taskActDt) != 0) {
                this.taskActDt = taskActDt;
                if (!this.toUpdateCols.contains("TASK_ACT_DT")) {
                    this.toUpdateCols.add("TASK_ACT_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskActDt = taskActDt;
            if (!this.toUpdateCols.contains("TASK_ACT_DT")) {
                this.toUpdateCols.add("TASK_ACT_DT");
            }
        }
        return this;
    }

    /**
     * 任务用户。
     */
    private String taskUserId;

    /**
     * 获取：任务用户。
     */
    public String getTaskUserId() {
        return this.taskUserId;
    }

    /**
     * 设置：任务用户。
     */
    public WfFlowInfoV setTaskUserId(String taskUserId) {
        if (this.taskUserId == null && taskUserId == null) {
            // 均为null，不做处理。
        } else if (this.taskUserId != null && taskUserId != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskUserId.compareTo(taskUserId) != 0) {
                this.taskUserId = taskUserId;
                if (!this.toUpdateCols.contains("TASK_USER_ID")) {
                    this.toUpdateCols.add("TASK_USER_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskUserId = taskUserId;
            if (!this.toUpdateCols.contains("TASK_USER_ID")) {
                this.toUpdateCols.add("TASK_USER_ID");
            }
        }
        return this;
    }

    /**
     * 任务操作。
     */
    private String taskActId;

    /**
     * 获取：任务操作。
     */
    public String getTaskActId() {
        return this.taskActId;
    }

    /**
     * 设置：任务操作。
     */
    public WfFlowInfoV setTaskActId(String taskActId) {
        if (this.taskActId == null && taskActId == null) {
            // 均为null，不做处理。
        } else if (this.taskActId != null && taskActId != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskActId.compareTo(taskActId) != 0) {
                this.taskActId = taskActId;
                if (!this.toUpdateCols.contains("TASK_ACT_ID")) {
                    this.toUpdateCols.add("TASK_ACT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskActId = taskActId;
            if (!this.toUpdateCols.contains("TASK_ACT_ID")) {
                this.toUpdateCols.add("TASK_ACT_ID");
            }
        }
        return this;
    }

    /**
     * 任务用户批注。
     */
    private String taskUserComment;

    /**
     * 获取：任务用户批注。
     */
    public String getTaskUserComment() {
        return this.taskUserComment;
    }

    /**
     * 设置：任务用户批注。
     */
    public WfFlowInfoV setTaskUserComment(String taskUserComment) {
        if (this.taskUserComment == null && taskUserComment == null) {
            // 均为null，不做处理。
        } else if (this.taskUserComment != null && taskUserComment != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskUserComment.compareTo(taskUserComment) != 0) {
                this.taskUserComment = taskUserComment;
                if (!this.toUpdateCols.contains("TASK_USER_COMMENT")) {
                    this.toUpdateCols.add("TASK_USER_COMMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskUserComment = taskUserComment;
            if (!this.toUpdateCols.contains("TASK_USER_COMMENT")) {
                this.toUpdateCols.add("TASK_USER_COMMENT");
            }
        }
        return this;
    }

    /**
     * 任务用户附件。
     */
    private String taskUserAttachment;

    /**
     * 获取：任务用户附件。
     */
    public String getTaskUserAttachment() {
        return this.taskUserAttachment;
    }

    /**
     * 设置：任务用户附件。
     */
    public WfFlowInfoV setTaskUserAttachment(String taskUserAttachment) {
        if (this.taskUserAttachment == null && taskUserAttachment == null) {
            // 均为null，不做处理。
        } else if (this.taskUserAttachment != null && taskUserAttachment != null) {
            // 均非null，判定不等，再做处理：
            if (this.taskUserAttachment.compareTo(taskUserAttachment) != 0) {
                this.taskUserAttachment = taskUserAttachment;
                if (!this.toUpdateCols.contains("TASK_USER_ATTACHMENT")) {
                    this.toUpdateCols.add("TASK_USER_ATTACHMENT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.taskUserAttachment = taskUserAttachment;
            if (!this.toUpdateCols.contains("TASK_USER_ATTACHMENT")) {
                this.toUpdateCols.add("TASK_USER_ATTACHMENT");
            }
        }
        return this;
    }

    /**
     * 实体。
     */
    private String adEntId;

    /**
     * 获取：实体。
     */
    public String getAdEntId() {
        return this.adEntId;
    }

    /**
     * 设置：实体。
     */
    public WfFlowInfoV setAdEntId(String adEntId) {
        if (this.adEntId == null && adEntId == null) {
            // 均为null，不做处理。
        } else if (this.adEntId != null && adEntId != null) {
            // 均非null，判定不等，再做处理：
            if (this.adEntId.compareTo(adEntId) != 0) {
                this.adEntId = adEntId;
                if (!this.toUpdateCols.contains("AD_ENT_ID")) {
                    this.toUpdateCols.add("AD_ENT_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.adEntId = adEntId;
            if (!this.toUpdateCols.contains("AD_ENT_ID")) {
                this.toUpdateCols.add("AD_ENT_ID");
            }
        }
        return this;
    }

    /**
     * 实体记录ID。
     */
    private String entityRecordId;

    /**
     * 获取：实体记录ID。
     */
    public String getEntityRecordId() {
        return this.entityRecordId;
    }

    /**
     * 设置：实体记录ID。
     */
    public WfFlowInfoV setEntityRecordId(String entityRecordId) {
        if (this.entityRecordId == null && entityRecordId == null) {
            // 均为null，不做处理。
        } else if (this.entityRecordId != null && entityRecordId != null) {
            // 均非null，判定不等，再做处理：
            if (this.entityRecordId.compareTo(entityRecordId) != 0) {
                this.entityRecordId = entityRecordId;
                if (!this.toUpdateCols.contains("ENTITY_RECORD_ID")) {
                    this.toUpdateCols.add("ENTITY_RECORD_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.entityRecordId = entityRecordId;
            if (!this.toUpdateCols.contains("ENTITY_RECORD_ID")) {
                this.toUpdateCols.add("ENTITY_RECORD_ID");
            }
        }
        return this;
    }

    /**
     * 当前节点实例。
     */
    private String currentWfNodeInstanceId;

    /**
     * 获取：当前节点实例。
     */
    public String getCurrentWfNodeInstanceId() {
        return this.currentWfNodeInstanceId;
    }

    /**
     * 设置：当前节点实例。
     */
    public WfFlowInfoV setCurrentWfNodeInstanceId(String currentWfNodeInstanceId) {
        if (this.currentWfNodeInstanceId == null && currentWfNodeInstanceId == null) {
            // 均为null，不做处理。
        } else if (this.currentWfNodeInstanceId != null && currentWfNodeInstanceId != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentWfNodeInstanceId.compareTo(currentWfNodeInstanceId) != 0) {
                this.currentWfNodeInstanceId = currentWfNodeInstanceId;
                if (!this.toUpdateCols.contains("CURRENT_WF_NODE_INSTANCE_ID")) {
                    this.toUpdateCols.add("CURRENT_WF_NODE_INSTANCE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentWfNodeInstanceId = currentWfNodeInstanceId;
            if (!this.toUpdateCols.contains("CURRENT_WF_NODE_INSTANCE_ID")) {
                this.toUpdateCols.add("CURRENT_WF_NODE_INSTANCE_ID");
            }
        }
        return this;
    }

    /**
     * 当前节点实例。
     */
    private String currentNiId;

    /**
     * 获取：当前节点实例。
     */
    public String getCurrentNiId() {
        return this.currentNiId;
    }

    /**
     * 设置：当前节点实例。
     */
    public WfFlowInfoV setCurrentNiId(String currentNiId) {
        if (this.currentNiId == null && currentNiId == null) {
            // 均为null，不做处理。
        } else if (this.currentNiId != null && currentNiId != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentNiId.compareTo(currentNiId) != 0) {
                this.currentNiId = currentNiId;
                if (!this.toUpdateCols.contains("CURRENT_NI_ID")) {
                    this.toUpdateCols.add("CURRENT_NI_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentNiId = currentNiId;
            if (!this.toUpdateCols.contains("CURRENT_NI_ID")) {
                this.toUpdateCols.add("CURRENT_NI_ID");
            }
        }
        return this;
    }

    /**
     * 当前节点开始时间。
     */
    private LocalDateTime currentNiStartDt;

    /**
     * 获取：当前节点开始时间。
     */
    public LocalDateTime getCurrentNiStartDt() {
        return this.currentNiStartDt;
    }

    /**
     * 设置：当前节点开始时间。
     */
    public WfFlowInfoV setCurrentNiStartDt(LocalDateTime currentNiStartDt) {
        if (this.currentNiStartDt == null && currentNiStartDt == null) {
            // 均为null，不做处理。
        } else if (this.currentNiStartDt != null && currentNiStartDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentNiStartDt.compareTo(currentNiStartDt) != 0) {
                this.currentNiStartDt = currentNiStartDt;
                if (!this.toUpdateCols.contains("CURRENT_NI_START_DT")) {
                    this.toUpdateCols.add("CURRENT_NI_START_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentNiStartDt = currentNiStartDt;
            if (!this.toUpdateCols.contains("CURRENT_NI_START_DT")) {
                this.toUpdateCols.add("CURRENT_NI_START_DT");
            }
        }
        return this;
    }

    /**
     * 当前节点结束时间。
     */
    private LocalDateTime currentNiEndDt;

    /**
     * 获取：当前节点结束时间。
     */
    public LocalDateTime getCurrentNiEndDt() {
        return this.currentNiEndDt;
    }

    /**
     * 设置：当前节点结束时间。
     */
    public WfFlowInfoV setCurrentNiEndDt(LocalDateTime currentNiEndDt) {
        if (this.currentNiEndDt == null && currentNiEndDt == null) {
            // 均为null，不做处理。
        } else if (this.currentNiEndDt != null && currentNiEndDt != null) {
            // 均非null，判定不等，再做处理：
            if (this.currentNiEndDt.compareTo(currentNiEndDt) != 0) {
                this.currentNiEndDt = currentNiEndDt;
                if (!this.toUpdateCols.contains("CURRENT_NI_END_DT")) {
                    this.toUpdateCols.add("CURRENT_NI_END_DT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.currentNiEndDt = currentNiEndDt;
            if (!this.toUpdateCols.contains("CURRENT_NI_END_DT")) {
                this.toUpdateCols.add("CURRENT_NI_END_DT");
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
    public static WfFlowInfoV newData() {
        WfFlowInfoV obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static WfFlowInfoV insertData() {
        WfFlowInfoV obj = modelHelper.insertData();
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
    public static WfFlowInfoV selectById(String id, List<String> includeCols, List<String> excludeCols) {
        WfFlowInfoV obj = modelHelper.selectById(id, includeCols, excludeCols);
        return obj;
    }

    /**
     * 根据ID列表获取数据。
     *
     * @param ids         ID列表。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<WfFlowInfoV> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<WfFlowInfoV> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
        return objList;
    }

    /**
     * 根据Where条件获取数据。
     *
     * @param where       Where条件。
     * @param includeCols 获取时包含的列，空为包含所有。
     * @param excludeCols 获取时排除的列，空为不排除。
     * @return 获取到的对象列表，若无则为null。建议使用SharedUtil.isEmptyList(list)方法判断有无。
     */
    public static List<WfFlowInfoV> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<WfFlowInfoV> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
        return objList;
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
    public static void copyCols(WfFlowInfoV fromModel, WfFlowInfoV toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}