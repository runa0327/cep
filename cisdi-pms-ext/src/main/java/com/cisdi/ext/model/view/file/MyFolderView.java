package com.cisdi.ext.model.view.file;

import com.cisdi.ext.pm.MyFolder;

import java.util.List;

public class MyFolderView {

    public String id;
    public String name;
    public String createBy;
    public String createDate;
    public String status;
    public String myFolderId;
    public List<MyFolderView> children;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateDate() {
        return createDate;
    }

    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMyFolderId() {
        return myFolderId;
    }

    public void setMyFolderId(String myFolderId) {
        this.myFolderId = myFolderId;
    }

    public List<MyFolderView> getChildren() {
        return children;
    }

    public void setChildren(List<MyFolderView> children) {
        this.children = children;
    }
}
