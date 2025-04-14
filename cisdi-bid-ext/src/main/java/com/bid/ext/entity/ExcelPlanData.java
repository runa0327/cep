package com.bid.ext.entity;

import lombok.Data;

@Data
public class ExcelPlanData {
    private String seq;       // 可能为空
    private String name;
    private String milestone; // Excel中是字符串（"是"/"否"）
    private String remark;
}