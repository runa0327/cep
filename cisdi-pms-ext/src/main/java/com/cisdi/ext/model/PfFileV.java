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
 * 项目文件(视图)。
 */
public class PfFileV {

    /**
     * 模型助手。
     */
    private static final ModelHelper<PfFileV> modelHelper = new ModelHelper<>("PF_FILE_V", new PfFileV());

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

    public static final String ENT_CODE = "PF_FILE_V";
    public static final EntityTypeE ENTITY_TYPE = EntityTypeE.SUB_QUERY;

    // </editor-fold>

    // 属性常量相关：
    // <editor-fold>

    public static class Cols {
        /**
         * 扩展名。
         */
        public static final String EXT = "EXT";
        /**
         * 资料管理文件。
         */
        public static final String PF_FILE_ID = "PF_FILE_ID";
        /**
         * 文件。
         */
        public static final String FL_FILE_ID = "FL_FILE_ID";
        /**
         * 名称。
         */
        public static final String NAME = "NAME";
        /**
         * ID。
         */
        public static final String ID = "ID";
        /**
         * 显示名称。
         */
        public static final String DSP_NAME = "DSP_NAME";
        /**
         * 大小KB。
         */
        public static final String SIZE_KB = "SIZE_KB";
        /**
         * 显示大小。
         */
        public static final String DSP_SIZE = "DSP_SIZE";
        /**
         * 文件内联URL。
         */
        public static final String FILE_INLINE_URL = "FILE_INLINE_URL";
        /**
         * 文件附件URL。
         */
        public static final String FILE_ATTACHMENT_URL = "FILE_ATTACHMENT_URL";
        /**
         * 上传时间。
         */
        public static final String UPLOAD_DTTM = "UPLOAD_DTTM";
        /**
         * 物理位置。
         */
        public static final String PHYSICAL_LOCATION = "PHYSICAL_LOCATION";
        /**
         * 路径。
         */
        public static final String FL_PATH_ID = "FL_PATH_ID";
    }

    // </editor-fold>

    // 各个属性及setter、getter：
    // <editor-fold>

    /**
     * 扩展名。
     */
    private String ext;

    /**
     * 获取：扩展名。
     */
    public String getExt() {
        return this.ext;
    }

    /**
     * 设置：扩展名。
     */
    public PfFileV setExt(String ext) {
        if (this.ext == null && ext == null) {
            // 均为null，不做处理。
        } else if (this.ext != null && ext != null) {
            // 均非null，判定不等，再做处理：
            if (this.ext.compareTo(ext) != 0) {
                this.ext = ext;
                if (!this.toUpdateCols.contains("EXT")) {
                    this.toUpdateCols.add("EXT");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.ext = ext;
            if (!this.toUpdateCols.contains("EXT")) {
                this.toUpdateCols.add("EXT");
            }
        }
        return this;
    }

    /**
     * 资料管理文件。
     */
    private String pfFileId;

    /**
     * 获取：资料管理文件。
     */
    public String getPfFileId() {
        return this.pfFileId;
    }

    /**
     * 设置：资料管理文件。
     */
    public PfFileV setPfFileId(String pfFileId) {
        if (this.pfFileId == null && pfFileId == null) {
            // 均为null，不做处理。
        } else if (this.pfFileId != null && pfFileId != null) {
            // 均非null，判定不等，再做处理：
            if (this.pfFileId.compareTo(pfFileId) != 0) {
                this.pfFileId = pfFileId;
                if (!this.toUpdateCols.contains("PF_FILE_ID")) {
                    this.toUpdateCols.add("PF_FILE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.pfFileId = pfFileId;
            if (!this.toUpdateCols.contains("PF_FILE_ID")) {
                this.toUpdateCols.add("PF_FILE_ID");
            }
        }
        return this;
    }

    /**
     * 文件。
     */
    private String flFileId;

    /**
     * 获取：文件。
     */
    public String getFlFileId() {
        return this.flFileId;
    }

    /**
     * 设置：文件。
     */
    public PfFileV setFlFileId(String flFileId) {
        if (this.flFileId == null && flFileId == null) {
            // 均为null，不做处理。
        } else if (this.flFileId != null && flFileId != null) {
            // 均非null，判定不等，再做处理：
            if (this.flFileId.compareTo(flFileId) != 0) {
                this.flFileId = flFileId;
                if (!this.toUpdateCols.contains("FL_FILE_ID")) {
                    this.toUpdateCols.add("FL_FILE_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.flFileId = flFileId;
            if (!this.toUpdateCols.contains("FL_FILE_ID")) {
                this.toUpdateCols.add("FL_FILE_ID");
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
    public PfFileV setName(String name) {
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
    public PfFileV setId(String id) {
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
     * 显示名称。
     */
    private String dspName;

    /**
     * 获取：显示名称。
     */
    public String getDspName() {
        return this.dspName;
    }

    /**
     * 设置：显示名称。
     */
    public PfFileV setDspName(String dspName) {
        if (this.dspName == null && dspName == null) {
            // 均为null，不做处理。
        } else if (this.dspName != null && dspName != null) {
            // 均非null，判定不等，再做处理：
            if (this.dspName.compareTo(dspName) != 0) {
                this.dspName = dspName;
                if (!this.toUpdateCols.contains("DSP_NAME")) {
                    this.toUpdateCols.add("DSP_NAME");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dspName = dspName;
            if (!this.toUpdateCols.contains("DSP_NAME")) {
                this.toUpdateCols.add("DSP_NAME");
            }
        }
        return this;
    }

    /**
     * 大小KB。
     */
    private BigDecimal sizeKb;

    /**
     * 获取：大小KB。
     */
    public BigDecimal getSizeKb() {
        return this.sizeKb;
    }

    /**
     * 设置：大小KB。
     */
    public PfFileV setSizeKb(BigDecimal sizeKb) {
        if (this.sizeKb == null && sizeKb == null) {
            // 均为null，不做处理。
        } else if (this.sizeKb != null && sizeKb != null) {
            // 均非null，判定不等，再做处理：
            if (this.sizeKb.compareTo(sizeKb) != 0) {
                this.sizeKb = sizeKb;
                if (!this.toUpdateCols.contains("SIZE_KB")) {
                    this.toUpdateCols.add("SIZE_KB");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.sizeKb = sizeKb;
            if (!this.toUpdateCols.contains("SIZE_KB")) {
                this.toUpdateCols.add("SIZE_KB");
            }
        }
        return this;
    }

    /**
     * 显示大小。
     */
    private String dspSize;

    /**
     * 获取：显示大小。
     */
    public String getDspSize() {
        return this.dspSize;
    }

    /**
     * 设置：显示大小。
     */
    public PfFileV setDspSize(String dspSize) {
        if (this.dspSize == null && dspSize == null) {
            // 均为null，不做处理。
        } else if (this.dspSize != null && dspSize != null) {
            // 均非null，判定不等，再做处理：
            if (this.dspSize.compareTo(dspSize) != 0) {
                this.dspSize = dspSize;
                if (!this.toUpdateCols.contains("DSP_SIZE")) {
                    this.toUpdateCols.add("DSP_SIZE");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.dspSize = dspSize;
            if (!this.toUpdateCols.contains("DSP_SIZE")) {
                this.toUpdateCols.add("DSP_SIZE");
            }
        }
        return this;
    }

    /**
     * 文件内联URL。
     */
    private String fileInlineUrl;

    /**
     * 获取：文件内联URL。
     */
    public String getFileInlineUrl() {
        return this.fileInlineUrl;
    }

    /**
     * 设置：文件内联URL。
     */
    public PfFileV setFileInlineUrl(String fileInlineUrl) {
        if (this.fileInlineUrl == null && fileInlineUrl == null) {
            // 均为null，不做处理。
        } else if (this.fileInlineUrl != null && fileInlineUrl != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileInlineUrl.compareTo(fileInlineUrl) != 0) {
                this.fileInlineUrl = fileInlineUrl;
                if (!this.toUpdateCols.contains("FILE_INLINE_URL")) {
                    this.toUpdateCols.add("FILE_INLINE_URL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileInlineUrl = fileInlineUrl;
            if (!this.toUpdateCols.contains("FILE_INLINE_URL")) {
                this.toUpdateCols.add("FILE_INLINE_URL");
            }
        }
        return this;
    }

    /**
     * 文件附件URL。
     */
    private String fileAttachmentUrl;

    /**
     * 获取：文件附件URL。
     */
    public String getFileAttachmentUrl() {
        return this.fileAttachmentUrl;
    }

    /**
     * 设置：文件附件URL。
     */
    public PfFileV setFileAttachmentUrl(String fileAttachmentUrl) {
        if (this.fileAttachmentUrl == null && fileAttachmentUrl == null) {
            // 均为null，不做处理。
        } else if (this.fileAttachmentUrl != null && fileAttachmentUrl != null) {
            // 均非null，判定不等，再做处理：
            if (this.fileAttachmentUrl.compareTo(fileAttachmentUrl) != 0) {
                this.fileAttachmentUrl = fileAttachmentUrl;
                if (!this.toUpdateCols.contains("FILE_ATTACHMENT_URL")) {
                    this.toUpdateCols.add("FILE_ATTACHMENT_URL");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.fileAttachmentUrl = fileAttachmentUrl;
            if (!this.toUpdateCols.contains("FILE_ATTACHMENT_URL")) {
                this.toUpdateCols.add("FILE_ATTACHMENT_URL");
            }
        }
        return this;
    }

    /**
     * 上传时间。
     */
    private LocalDateTime uploadDttm;

    /**
     * 获取：上传时间。
     */
    public LocalDateTime getUploadDttm() {
        return this.uploadDttm;
    }

    /**
     * 设置：上传时间。
     */
    public PfFileV setUploadDttm(LocalDateTime uploadDttm) {
        if (this.uploadDttm == null && uploadDttm == null) {
            // 均为null，不做处理。
        } else if (this.uploadDttm != null && uploadDttm != null) {
            // 均非null，判定不等，再做处理：
            if (this.uploadDttm.compareTo(uploadDttm) != 0) {
                this.uploadDttm = uploadDttm;
                if (!this.toUpdateCols.contains("UPLOAD_DTTM")) {
                    this.toUpdateCols.add("UPLOAD_DTTM");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.uploadDttm = uploadDttm;
            if (!this.toUpdateCols.contains("UPLOAD_DTTM")) {
                this.toUpdateCols.add("UPLOAD_DTTM");
            }
        }
        return this;
    }

    /**
     * 物理位置。
     */
    private String physicalLocation;

    /**
     * 获取：物理位置。
     */
    public String getPhysicalLocation() {
        return this.physicalLocation;
    }

    /**
     * 设置：物理位置。
     */
    public PfFileV setPhysicalLocation(String physicalLocation) {
        if (this.physicalLocation == null && physicalLocation == null) {
            // 均为null，不做处理。
        } else if (this.physicalLocation != null && physicalLocation != null) {
            // 均非null，判定不等，再做处理：
            if (this.physicalLocation.compareTo(physicalLocation) != 0) {
                this.physicalLocation = physicalLocation;
                if (!this.toUpdateCols.contains("PHYSICAL_LOCATION")) {
                    this.toUpdateCols.add("PHYSICAL_LOCATION");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.physicalLocation = physicalLocation;
            if (!this.toUpdateCols.contains("PHYSICAL_LOCATION")) {
                this.toUpdateCols.add("PHYSICAL_LOCATION");
            }
        }
        return this;
    }

    /**
     * 路径。
     */
    private String flPathId;

    /**
     * 获取：路径。
     */
    public String getFlPathId() {
        return this.flPathId;
    }

    /**
     * 设置：路径。
     */
    public PfFileV setFlPathId(String flPathId) {
        if (this.flPathId == null && flPathId == null) {
            // 均为null，不做处理。
        } else if (this.flPathId != null && flPathId != null) {
            // 均非null，判定不等，再做处理：
            if (this.flPathId.compareTo(flPathId) != 0) {
                this.flPathId = flPathId;
                if (!this.toUpdateCols.contains("FL_PATH_ID")) {
                    this.toUpdateCols.add("FL_PATH_ID");
                }
            }
        } else {
            // 一者为null、一者非null，直接处理：
            this.flPathId = flPathId;
            if (!this.toUpdateCols.contains("FL_PATH_ID")) {
                this.toUpdateCols.add("FL_PATH_ID");
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
    public static PfFileV newData() {
        PfFileV obj = modelHelper.newData();
        return obj;
    }

    /**
     * 插入数据。
     *
     * @return
     */
    public static PfFileV insertData() {
        PfFileV obj = modelHelper.insertData();
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
    public static PfFileV selectById(String id, List<String> includeCols, List<String> excludeCols) {
        PfFileV obj = modelHelper.selectById(id, includeCols, excludeCols);
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
    public static List<PfFileV> selectByIds(List<String> ids, List<String> includeCols, List<String> excludeCols) {
        List<PfFileV> objList = modelHelper.selectByIds(ids, includeCols, excludeCols);
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
    public static List<PfFileV> selectByWhere(Where where, List<String> includeCols, List<String> excludeCols) {
        List<PfFileV> objList = modelHelper.selectByWhere(where, includeCols, excludeCols);
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
    public static void copyCols(PfFileV fromModel, PfFileV toModel, List<String> includeCols, List<String> excludeCols) {
        OrmHelper.copyCols(fromModel, toModel, includeCols, excludeCols);
    }

    // </editor-fold>
}