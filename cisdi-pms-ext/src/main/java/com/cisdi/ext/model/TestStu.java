package com.cisdi.ext.model;

import com.qygly.ext.jar.helper.orm.ModelHelper;
import com.qygly.ext.jar.helper.orm.OrmHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.ad.entity.EntityTypeE;
import com.qygly.shared.util.SharedUtil;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 测试学生。
 */
public class TestStu {

    /**
     * 模型助手。
     */
    private static final ModelHelper<TestStu> modelHelper = new ModelHelper<>("TEST_STU", new TestStu());

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

    public static final String ENT_CODE = "TEST_STU";
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
         * 测试班级列表。
         */
        public static final String TEST_CLASS_IDS = "TEST_CLASS_IDS";
        /**
         * 测试班级。
         */
        public static final String TEST_CLASS_ID = "TEST_CLASS_ID";
        /**
         * 数据库验证密码。
         */
        public static final String PASSWORD = "PASSWORD";
        /**
         * 计算次数。
         */
        public static final String CALC_TIMES = "CALC_TIMES";
        /**
         * 签订金额。
         */
        public static final String SIGN_AMT = "SIGN_AMT";
        /**
         * 签订日期。
         */
        public static final String SIGN_DATE = "SIGN_DATE";
        /**
         * 测试时间。
         */
        public static final String TEST_TIME = "TEST_TIME";
        /**
         * 计算日期时间。
         */
        public static final String CALC_DTTM = "CALC_DTTM";
        /**
         * 计算成功。
         */
        public static final String CALC_SUCC = "CALC_SUCC";
        /**
         * 任务用户附件。
         */
        public static final String TASK_USER_ATTACHMENT = "TASK_USER_ATTACHMENT";
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
    public TestStu setId(String id) {
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
    public TestStu setVer(Integer ver) {
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
    public TestStu setTs(LocalDateTime ts) {
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
    public TestStu setIsPreset(Boolean isPreset) {
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
    public TestStu setCrtDt(LocalDateTime crtDt) {
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
    public TestStu setCrtUserId(String crtUserId) {
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
    public TestStu setLastModiDt(LocalDateTime lastModiDt) {
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
    public TestStu setLastModiUserId(String lastModiUserId) {
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
    public TestStu setStatus(String status) {
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
    public TestStu setLkWfInstId(String lkWfInstId) {
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
    public TestStu setCode(String code) {
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
    public TestStu setName(String name) {
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
    public TestStu setRemark(String remark) {
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
     * 测试班级列表。
     */
    private String testClassIds;

    /**
     * 获取：测试班级列表。
     */
    public String getTestClassIds() {
        return this.testClassIds;
    }

    /**
     * 设置：测试班级列表。
     */
    public TestStu setTestClassIds(String testClassIds) {
        if (this.testClassIds == null && testClassIds == null) {
            // 均为null，不做处理。
        } else if (this.testClassIds != null && testClassIds != null) {
            // 均非null，判定不等，再做处理：
            if (this.testClassIds.compareTo(testClassIds) != 0) {
                this.testClassIds = testClassIds;
                if (!this.toUpdateCols.contains("TEST_CLASS_IDS")) {
                    this.toUpdateCols.add("TEST_CLASS_IDS");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.testClassIds = testClassIds;
            if (!this.toUpdateCols.contains("TEST_CLASS_IDS")) {
                this.toUpdateCols.add("TEST_CLASS_IDS");
            }
        }
        return this;
    }

    /**
     * 测试班级。
     */
    private String testClassId;

    /**
     * 获取：测试班级。
     */
    public String getTestClassId() {
        return this.testClassId;
    }

    /**
     * 设置：测试班级。
     */
    public TestStu setTestClassId(String testClassId) {
        if (this.testClassId == null && testClassId == null) {
            // 均为null，不做处理。
        } else if (this.testClassId != null && testClassId != null) {
            // 均非null，判定不等，再做处理：
            if (this.testClassId.compareTo(testClassId) != 0) {
                this.testClassId = testClassId;
                if (!this.toUpdateCols.contains("TEST_CLASS_ID")) {
                    this.toUpdateCols.add("TEST_CLASS_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.testClassId = testClassId;
            if (!this.toUpdateCols.contains("TEST_CLASS_ID")) {
                this.toUpdateCols.add("TEST_CLASS_ID");
            }
        }
        return this;
    }

    /**
     * 数据库验证密码。
     */
    private String password;

    /**
     * 获取：数据库验证密码。
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 设置：数据库验证密码。
     */
    public TestStu setPassword(String password) {
        if (this.password == null && password == null) {
            // 均为null，不做处理。
        } else if (this.password != null && password != null) {
            // 均非null，判定不等，再做处理：
            if (this.password.compareTo(password) != 0) {
                this.password = password;
                if (!this.toUpdateCols.contains("PASSWORD")) {
                    this.toUpdateCols.add("PASSWORD");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.password = password;
            if (!this.toUpdateCols.contains("PASSWORD")) {
                this.toUpdateCols.add("PASSWORD");
            }
        }
        return this;
    }

    /**
     * 计算次数。
     */
    private Integer calcTimes;

    /**
     * 获取：计算次数。
     */
    public Integer getCalcTimes() {
        return this.calcTimes;
    }

    /**
     * 设置：计算次数。
     */
    public TestStu setCalcTimes(Integer calcTimes) {
        if (this.calcTimes == null && calcTimes == null) {
            // 均为null，不做处理。
        } else if (this.calcTimes != null && calcTimes != null) {
            // 均非null，判定不等，再做处理：
            if (this.calcTimes.compareTo(calcTimes) != 0) {
                this.calcTimes = calcTimes;
                if (!this.toUpdateCols.contains("CALC_TIMES")) {
                    this.toUpdateCols.add("CALC_TIMES");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.calcTimes = calcTimes;
            if (!this.toUpdateCols.contains("CALC_TIMES")) {
                this.toUpdateCols.add("CALC_TIMES");
            }
        }
        return this;
    }

    /**
     * 签订金额。
     */
    private BigDecimal signAmt;

    /**
     * 获取：签订金额。
     */
    public BigDecimal getSignAmt() {
        return this.signAmt;
    }

    /**
     * 设置：签订金额。
     */
    public TestStu setSignAmt(BigDecimal signAmt) {
        if (this.signAmt == null && signAmt == null) {
            // 均为null，不做处理。
        } else if (this.signAmt != null && signAmt != null) {
            // 均非null，判定不等，再做处理：
            if (this.signAmt.compareTo(signAmt) != 0) {
                this.signAmt = signAmt;
                if (!this.toUpdateCols.contains("SIGN_AMT")) {
                    this.toUpdateCols.add("SIGN_AMT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.signAmt = signAmt;
            if (!this.toUpdateCols.contains("SIGN_AMT")) {
                this.toUpdateCols.add("SIGN_AMT");
            }
        }
        return this;
    }

    /**
     * 签订日期。
     */
    private LocalDate signDate;

    /**
     * 获取：签订日期。
     */
    public LocalDate getSignDate() {
        return this.signDate;
    }

    /**
     * 设置：签订日期。
     */
    public TestStu setSignDate(LocalDate signDate) {
        if (this.signDate == null && signDate == null) {
            // 均为null，不做处理。
        } else if (this.signDate != null && signDate != null) {
            // 均非null，判定不等，再做处理：
            if (this.signDate.compareTo(signDate) != 0) {
                this.signDate = signDate;
                if (!this.toUpdateCols.contains("SIGN_DATE")) {
                    this.toUpdateCols.add("SIGN_DATE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.signDate = signDate;
            if (!this.toUpdateCols.contains("SIGN_DATE")) {
                this.toUpdateCols.add("SIGN_DATE");
            }
        }
        return this;
    }

    /**
     * 测试时间。
     */
    private LocalTime testTime;

    /**
     * 获取：测试时间。
     */
    public LocalTime getTestTime() {
        return this.testTime;
    }

    /**
     * 设置：测试时间。
     */
    public TestStu setTestTime(LocalTime testTime) {
        if (this.testTime == null && testTime == null) {
            // 均为null，不做处理。
        } else if (this.testTime != null && testTime != null) {
            // 均非null，判定不等，再做处理：
            if (this.testTime.compareTo(testTime) != 0) {
                this.testTime = testTime;
                if (!this.toUpdateCols.contains("TEST_TIME")) {
                    this.toUpdateCols.add("TEST_TIME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.testTime = testTime;
            if (!this.toUpdateCols.contains("TEST_TIME")) {
                this.toUpdateCols.add("TEST_TIME");
            }
        }
        return this;
    }

    /**
     * 计算日期时间。
     */
    private LocalDateTime calcDttm;

    /**
     * 获取：计算日期时间。
     */
    public LocalDateTime getCalcDttm() {
        return this.calcDttm;
    }

    /**
     * 设置：计算日期时间。
     */
    public TestStu setCalcDttm(LocalDateTime calcDttm) {
        if (this.calcDttm == null && calcDttm == null) {
            // 均为null，不做处理。
        } else if (this.calcDttm != null && calcDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.calcDttm.compareTo(calcDttm) != 0) {
                this.calcDttm = calcDttm;
                if (!this.toUpdateCols.contains("CALC_DTTM")) {
                    this.toUpdateCols.add("CALC_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.calcDttm = calcDttm;
            if (!this.toUpdateCols.contains("CALC_DTTM")) {
                this.toUpdateCols.add("CALC_DTTM");
            }
        }
        return this;
    }

    /**
     * 计算成功。
     */
    private Boolean calcSucc;

    /**
     * 获取：计算成功。
     */
    public Boolean getCalcSucc() {
        return this.calcSucc;
    }

    /**
     * 设置：计算成功。
     */
    public TestStu setCalcSucc(Boolean calcSucc) {
        if (this.calcSucc == null && calcSucc == null) {
            // 均为null，不做处理。
        } else if (this.calcSucc != null && calcSucc != null) {
            // 均非null，判定不等，再做处理：
            if (this.calcSucc.compareTo(calcSucc) != 0) {
                this.calcSucc = calcSucc;
                if (!this.toUpdateCols.contains("CALC_SUCC")) {
                    this.toUpdateCols.add("CALC_SUCC");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.calcSucc = calcSucc;
            if (!this.toUpdateCols.contains("CALC_SUCC")) {
                this.toUpdateCols.add("CALC_SUCC");
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
    public TestStu setTaskUserAttachment(String taskUserAttachment) {
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
    public static TestStu newData() {
        TestStu obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static TestStu insertData() {
        TestStu obj = modelHelper.insertData();
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
    public static TestStu selectById(String id, List<String> includeCols, List<String> excludeCols) {
        TestStu obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<TestStu> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<TestStu> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<TestStu> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<TestStu> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(TestStu fromModel, TestStu toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}