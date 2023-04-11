package com.cisdi.ext.file;

import com.cisdi.ext.model.MyFolderFile;
import com.cisdi.ext.model.view.file.MyFolderFileView;
import com.cisdi.ext.model.view.file.MyFolderView;
import com.cisdi.ext.pm.MyFolder;
import com.cisdi.ext.pm.PmRosterExt;
import com.cisdi.ext.util.JsonUtil;
import com.cisdi.ext.util.StringUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 个人文件夹-扩展
 */
public class MyFolderExt {

    /**
     * 新增文件夹
     */
    public void createFolder(){
        // 获取输入
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        MyFolderView myFolder = JsonUtil.fromJson(json, MyFolderView.class);
        String myFolderId = myFolder.getId();
        String id = MyFolder.insertData().getId();
        myFolder.setMyFolderId(id);
        Crud.from("MY_FOLDER").where().eq("ID",id).update()
                .set("NAME",myFolder.getName()).set("STATUS","AP")
                .set("MY_FOLDER_ID",myFolderId)
                .exec();
    }

    /**
     * 修改文件夹
     */
    public void updateFolder(){
        // 获取输入
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        MyFolderView myFolder = JsonUtil.fromJson(json, MyFolderView.class);
        String id = myFolder.getId();
        myFolder.setMyFolderId(id);
        Crud.from("MY_FOLDER").where().eq("ID",id).update()
                .set("NAME",myFolder.getName()).set("STATUS","AP")
                .exec();
    }

    /**
     * 删除文件夹
     */
    public void deleteFolder(){
        // 获取输入
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        MyFolderView myFolder = JsonUtil.fromJson(json, MyFolderView.class);
        String id = myFolder.getId();
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("id不能为空！");
        }
        Crud.from("MY_FOLDER").where().eq("ID",id).update().set("STATUS","VD").exec();
    }

    /**
     * 查询文件夹树形列表
     */
    public void selectFolder(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        MyFolderView myFolder = JsonUtil.fromJson(json, MyFolderView.class);
        String userId = ExtJarHelper.loginInfo.get().userId;
        List<MyFolderView> resData;
        StringBuilder sb = new StringBuilder( "select * from my_folder where crt_user_id = ? and status = 'AP' ");
        if (!SharedUtil.isEmptyString(myFolder.getName())){
            sb.append("and name like '%").append(myFolder.getName()).append("%'");
            sb.append(" order by id asc");
            resData = getResData(sb.toString(),userId,myJdbcTemplate);
        } else {
            sb.append(" order by id asc");
            List<MyFolderView> fileViewList = getResData(sb.toString(),userId,myJdbcTemplate);
            resData = fileViewList.stream().filter(p->"0".equals(p.getMyFolderId())).peek(m->{
                List<MyFolderView> children = this.getChilden(m,fileViewList);
                m.setChildrenList(children);
            }).collect(Collectors.toList());

        }
        if (!CollectionUtils.isEmpty(resData)){
            HashMap<String, Object> result = new HashMap<>();
            result.put("result",resData);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }

    }

    /**
     * 查询树
     * @param sql sql语句
     * @param userId 当前登录人id
     * @param myJdbcTemplate 数据源
     * @return 文件信息
     */
    public List<MyFolderView> getResData(String sql, String userId, MyJdbcTemplate myJdbcTemplate) {
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sql,userId);
        List<MyFolderView> fileViewList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)){
            fileViewList = list.stream().map(p->{
                MyFolderView myFolderView = new MyFolderView();
                myFolderView.setStatus(JdbcMapUtil.getString(p,"STATUS"));
                myFolderView.setId(JdbcMapUtil.getString(p,"ID"));
                myFolderView.setCreateBy(JdbcMapUtil.getString(p,"CRT_USER_ID"));
                myFolderView.setName(JdbcMapUtil.getString(p,"NAME"));
                String myFolderId = JdbcMapUtil.getString(p,"MY_FOLDER_ID");
                if (SharedUtil.isEmptyString(myFolderId)){
                    myFolderId = "0";
                }
                myFolderView.setMyFolderId(myFolderId);
                return myFolderView;
            }).collect(Collectors.toList());
        }
        return fileViewList;
    }

    public List<MyFolderView> getChilden(MyFolderView myFolderView, List<MyFolderView> list) {
        return list.stream().filter(p->myFolderView.id.equals(p.getMyFolderId())).peek(m->{
            List<MyFolderView> children = this.getChilden(m,list);
            m.setChildrenList(children);
        }).collect(Collectors.toList());
    }

    /**
     * 新增文件
     */
    public void createFolderFile(){
        // 获取输入
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        MyFolderFileView myFolderFile = JsonUtil.fromJson(json, MyFolderFileView.class);
        String fileId = myFolderFile.getFileId();
        List<String> fileArr = StringUtil.getStrToList(fileId,",");
        for (String tmp : fileArr) {
            String id = MyFolderFile.insertData().getId();
            Crud.from("MY_FOLDER_FILE").where().eq("ID",id).update()
                    .set("FL_FILE_ID",tmp)
                    .set("STATUS","AP").set("MY_FOLDER_ID",myFolderFile.getFolderId())
                    .exec();
        }
    }

    /**
     * 删除文件
     */
    public void deleteFolderFile(){
        // 获取输入
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        MyFolderFileView myFolder = JsonUtil.fromJson(json, MyFolderFileView.class);
        String id = myFolder.getId();
        if (SharedUtil.isEmptyString(id)){
            throw new BaseException("id不能为空！");
        }
        Crud.from("MY_FOLDER_FILE").where().eq("ID",id).update().set("STATUS","VD").exec();
    }

    /**
     * 查询文件列表
     */
    public void selectFolderFileList(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
        // 获取输入
        Map<String, Object> map = ExtJarHelper.extApiParamMap.get();// 输入参数的map。
        String json = JsonUtil.toJson(map);
        MyFolderFileView param = JsonUtil.fromJson(json, MyFolderFileView.class);
        String folderId = param.getFolderId();
        if (SharedUtil.isEmptyString(folderId)){
            throw new BaseException("文件夹id不能为空！");
        }
        if (param.pageIndex == 0 || param.pageSize == 0) {
            throw new BaseException("分页参数不能必须大于0");
        }

        // 起始条数
        int start = (param.pageIndex - 1) * param.pageSize;
        String limit = "limit " + start + "," + param.pageSize;

        String sql = "select A.id,A.FL_FILE_ID AS fileId,A.MY_FOLDER_ID as folderId,B.DSP_NAME as fileName,B.DSP_SIZE as fileSize,B.UPLOAD_DTTM as uploadTime,B.PHYSICAL_LOCATION as address,B.FILE_ATTACHMENT_URL as downloadAddress,B.FILE_INLINE_URL as previewAddress from MY_FOLDER_FILE a left join fl_file B ON A.FL_FILE_ID = B.ID WHERE A.MY_FOLDER_ID = ? AND A.STATUS = 'AP' AND B.STATUS = 'AP'";
        StringBuilder sb = new StringBuilder(sql);
        if (!SharedUtil.isEmptyString(param.getFileName())){
            sb.append(" and b.name like ('%").append(param.getFileName()).append("%') ");
        }
        sb.append(" ORDER BY A.CRT_DT DESC ").append(limit);
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sb.toString(),folderId);
        if (!CollectionUtils.isEmpty(list)){
            List<MyFolderFileView> resData = list.stream().map(p->{
                MyFolderFileView myFolderFileView = new MyFolderFileView();
                myFolderFileView.setId(JdbcMapUtil.getString(p,"id"));
                myFolderFileView.setFileId(JdbcMapUtil.getString(p,"fileId"));
                myFolderFileView.setFolderId(JdbcMapUtil.getString(p,"folderId"));
                myFolderFileView.setFileName(JdbcMapUtil.getString(p,"fileName"));
                myFolderFileView.setFileSize(JdbcMapUtil.getString(p,"fileSize"));
                myFolderFileView.setUploadTime(JdbcMapUtil.getString(p,"uploadTime"));
                myFolderFileView.setAddress(JdbcMapUtil.getString(p,"address"));
                myFolderFileView.setDownloadAddress(JdbcMapUtil.getString(p,"downloadAddress"));
                myFolderFileView.setPreviewAddress(JdbcMapUtil.getString(p,"previewAddress"));
                return myFolderFileView;
            }).collect(Collectors.toList());
            HashMap<String, Object> result = new HashMap<>();
            result.put("result",resData);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }

    }
}
