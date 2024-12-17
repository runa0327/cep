package com.cisdi.cisdipreview.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class PanoData {

    @JsonProperty("data")
    private List<PanoMonth> data;

    public List<PanoMonth> getData() {
        return data;
    }

    public void setData(List<PanoMonth> data) {
        this.data = data;
    }

    public static class PanoMonth {

        @JsonProperty("pano-month")
        private String panoMonth;

        @JsonProperty("pano-list")
        private List<PanoListItem> panoList;

        public String getPanoMonth() {
            return panoMonth;
        }

        public void setPanoMonth(String panoMonth) {
            this.panoMonth = panoMonth;
        }

        public List<PanoListItem> getPanoList() {
            return panoList;
        }

        public void setPanoList(List<PanoListItem> panoList) {
            this.panoList = panoList;
        }
    }

    public static class PanoListItem {

        @JsonProperty("CC_VR_ATTACHMENT_ID")
        private String ccVrAttachmentId;

        @JsonProperty("CC_VR_ATTACHMENT_PREVIEW")
        private String ccVrAttachmentPreview;

        @JsonProperty("CC_DOC_FILE_ID")
        private String ccDocFileId;

        @JsonProperty("CC_VR_ATTACHMENT")
        private String ccVrAttachment;

        @JsonProperty("CC_YEAR_MONTH")
        private String ccYearMonth;

        @JsonProperty("CC_VR_ATTACHMENT_PREVIEW_ID")
        private String ccVrAttachmentPreviewId;

        @JsonProperty("VR_DATE")
        private String vrDate;

        @JsonProperty("NAME")
        private String name;

        public String getCcVrAttachmentId() {
            return ccVrAttachmentId;
        }

        public void setCcVrAttachmentId(String ccVrAttachmentId) {
            this.ccVrAttachmentId = ccVrAttachmentId;
        }

        public String getCcVrAttachmentPreview() {
            return ccVrAttachmentPreview;
        }

        public void setCcVrAttachmentPreview(String ccVrAttachmentPreview) {
            this.ccVrAttachmentPreview = ccVrAttachmentPreview;
        }

        public String getCcDocFileId() {
            return ccDocFileId;
        }

        public void setCcDocFileId(String ccDocFileId) {
            this.ccDocFileId = ccDocFileId;
        }

        public String getCcVrAttachment() {
            return ccVrAttachment;
        }

        public void setCcVrAttachment(String ccVrAttachment) {
            this.ccVrAttachment = ccVrAttachment;
        }

        public String getCcYearMonth() {
            return ccYearMonth;
        }

        public void setCcYearMonth(String ccYearMonth) {
            this.ccYearMonth = ccYearMonth;
        }

        public String getCcVrAttachmentPreviewId() {
            return ccVrAttachmentPreviewId;
        }

        public void setCcVrAttachmentPreviewId(String ccVrAttachmentPreviewId) {
            this.ccVrAttachmentPreviewId = ccVrAttachmentPreviewId;
        }

        public String getVrDate() {
            return vrDate;
        }

        public void setVrDate(String vrDate) {
            this.vrDate = vrDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
