package com.cisdi.cisdipreview.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class IssueAreaDTO {
    private String version;
    private Map<String, Object> flags;
    private List<IssueBox> shapes;
    private int imageHeight;
    private int imageWidth;
    @JsonProperty("file_id")
    private String fileId;

    @JsonProperty("ai_inspection_req_id")
    private String aiInspectionReqId;

    public String getAiInspectionReqId() {
        return aiInspectionReqId;
    }

    public void setAiInspectionReqId(String aiInspectionReqId) {
        this.aiInspectionReqId = aiInspectionReqId;
    }

    // Getters and Setters
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Shape {
        private String label;
        private List<List<Integer>> points;
        private Integer groupId;
        private String shapeType;
        private Map<String, Object> flags;
        private String inspectionPoint;

        // Getters and Setters
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public Map<String, Object> getFlags() {
        return flags;
    }

    public void setFlags(Map<String, Object> flags) {
        this.flags = flags;
    }

    public List<IssueBox> getShapes() {
        return shapes;
    }

    public void setShapes(List<IssueBox> shapes) {
        this.shapes = shapes;
    }

    public int getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(int imageHeight) {
        this.imageHeight = imageHeight;
    }

    public int getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(int imageWidth) {
        this.imageWidth = imageWidth;
    }


    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class IssueBox {
        private String label;
        private List<List<Integer>> points;
        private Integer groupId;
        private String shapeType;
        private Map<String, Object> flags;
        private String remark;

        @JsonProperty("inspection_point")
        private List<String> inspectionPoint;

        // Getters and Setters

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public List<List<Integer>> getPoints() {
            return points;
        }

        public void setPoints(List<List<Integer>> points) {
            this.points = points;
        }

        public Integer getGroupId() {
            return groupId;
        }

        public void setGroupId(Integer groupId) {
            this.groupId = groupId;
        }

        public String getShapeType() {
            return shapeType;
        }

        public void setShapeType(String shapeType) {
            this.shapeType = shapeType;
        }

        public Map<String, Object> getFlags() {
            return flags;
        }

        public void setFlags(Map<String, Object> flags) {
            this.flags = flags;
        }

        public List<String> getInspectionPoint() {
            return inspectionPoint;
        }

        public void setInspectionPoint(List<String> inspectionPoint) {
            this.inspectionPoint = inspectionPoint;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
