package com.bid.ext.entity;

import lombok.Data;

import java.util.List;

@Data
public class QbqBody {
    private String filePhysicalLocation;
    private String fileDspName;
    private String wfProcInstId;
    private String wfProcInstName;
    private List<User> userList;

    @Data
    public static class User {
        private String id;
        private String name;
        private String tel;
        private String idCardNo;
    }
}
