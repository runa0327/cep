package com.pms.bid.job.domain.qbq;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.pms.bid.job.domain.BaseDomain;
import lombok.Data;
import org.springframework.data.relational.core.sql.In;

import java.util.List;

/**
 * 亲笔签回调请求
 */
@Data
public class QbqCallbackRequest {

    private String  requestId;
    private String  taskCode;
    private String  publishAppCode;
    private Integer  operateType;
    private String[]  acceptAppCodeList;

    private OperateUserInfo operateUserInfo;
    @Data
    public static class OperateUserInfo{
        private String signerName;
        private String signerId;
    }

    private AddTaskInfo addTaskInfo;
    @Data
    public static class AddTaskInfo{
        private String taskName;
        private String taskCode;
        private String taskBid;
        private String taskFlowId;
        private Integer taskType;
        private Integer taskCategory;
        private String taskVersion;
        private Integer signType;
        private String publisherId;
        private String publisherName;
        private String publisherTel;
        private String publisherCardType;
        private String publisherCardNo;
        private String publisherEnterpriseCode;
        private String publisherEnterpriseName;
        private String publishTime;
        private String deadline;
        private String createTime;
        private String changeCount;
        private List<Signatory> signatoryList;

        @Data
        public static class Signatory{
            private String signatoryId;
            private String signatoryName;
            private String signatoryEnterpriseName;
            private String signatoryEnterpriseCode;
            private List<SignerInfo> signerInfoList;
            @Data
            public static class SignerInfo{
                private String signerId;
                private String signerPaasId;
                private String signerTel;
                private String signerName;
                private String signerCardType;
                private String signerCardNo;
            }
        }

        private List<SignFile> signFileList;
        @Data
        public static class SignFile{
            private String id;
            private String fileId;
            private String fileName;
            private Integer fileSize;
            private String signJson;
            private String fileExt;
            private List<Signatory> signatoryList;
            @Data
            public static class Signatory{
                private String signatoryId;
            }
        }

        private List<SignAnnexFile> signAnnexFileList;
        @Data
        public static class SignAnnexFile{
            private String id;
            private String fileId;
            private String fileName;
            private Integer fileSize;
            private String fileExt;
        }
    }

    private ExpiredInfo ExpiredInfo;
    @Data
    public static class ExpiredInfo{
        private String taskCode;
        private Integer changeCount;
    }

    private ChangeTaskInfo changeTaskInfo;
    @Data
    public static  class ChangeTaskInfo{
        private String taskCode;
        private Integer taskStatus;
        private String signerId;
        private Integer signerStatus;
        private String signatoryId;
        private Integer signatoryStatus;
        private String reason;
        private Integer changeCount;
        private String operateTime;
        private List<SignFile> signFileList;

        @Data
        public static class SignFile{
            private String id;
            private String fileId;
            private String signedFileId;
            private Integer fileStatus;
            private Integer signedFileSize;
        }

        private String finishTime;

        private List<SignatorySigner> signatorySignerList;
        @Data
        public static class SignatorySigner{
            private String signerId;
            private Integer signerStatus;
            private String signatoryId;
            private Integer signatoryStatus;
        }
    }

    private AddSignerInfo addSignerInfo;
    @Data
    public static class AddSignerInfo{
        private String taskCode;
        private Integer changeCount;
        private String signatoryId;
        private SignerInfo signerInfo;
        @Data
        public static class SignerInfo{
            private String signerId;
            private String signerPaasId;
            private String signerTel;
            private String signerName;
            private String signerCardType;
            private String signerCardNo;
        }
    }

    private FinalizeTaskInfo finalizeTaskInfo;
    @Data
    public static class FinalizeTaskInfo{
        private Integer taskStatus;
        private Integer changeCount;
        private String taskCode;
        private String operateTime;
        private String finishTime;
        private List<SignFile> signFileList;
        @Data
        public static  class SignFile{
            private String fileId;
            private String signedFileId;
            private Integer fileStatus;
            private Integer signedFileSize;
        }

        private  List<SignatoryFinalize> signatoryFinalize;
        @Data
        public static class SignatoryFinalize{
            private String signatoryId;
            private Integer signatoryStatus;
            private List<SignerInfo> signerInfoList;
            @Data
            public static class SignerInfo{
                private String signerId;
                private Integer signerStatus;
            }
        }

    }

}
