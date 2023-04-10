package com.cisdi.ext.file;

import com.cisdi.ext.model.view.file.MyFolderView;
import com.cisdi.ext.pm.MyFolder;
import com.cisdi.ext.pm.PmRosterExt;
import com.cisdi.ext.util.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.util.JdbcMapUtil;
import com.qygly.shared.util.SharedUtil;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        StringBuilder sb = new StringBuilder( "select * from my_folder where crt_user_id = ? and status = 'AP' ");
        if (!SharedUtil.isEmptyString(myFolder.getName())){
            sb.append("and name like '%").append(myFolder.getName()).append("%'");
        }
        sb.append(" order by id asc");
        List<Map<String,Object>> list = myJdbcTemplate.queryForList(sb.toString(),userId);
        if (!CollectionUtils.isEmpty(list)){
            List<MyFolderView> fileViewList = list.stream().map(p->{
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
            List<MyFolderView> resData = fileViewList.stream().filter(p->"0".equals(p.getMyFolderId())).peek(m->{
                List<MyFolderView> children = this.getChilden(m,fileViewList);
                m.setChildrenList(children);
            }).collect(Collectors.toList());
            HashMap<String, Object> result = new HashMap<>();
            result.put("result",resData);
            Map outputMap = JsonUtil.fromJson(JsonUtil.toJson(result), Map.class);
            ExtJarHelper.returnValue.set(outputMap);
        } else {
            ExtJarHelper.returnValue.set(Collections.emptyMap());
        }

    }

    public List<MyFolderView> getChilden(MyFolderView myFolderView, List<MyFolderView> list) {
        return list.stream().filter(p->myFolderView.id.equals(p.getMyFolderId())).peek(m->{
            List<MyFolderView> children = this.getChilden(m,list);
            m.setChildrenList(children);
        }).collect(Collectors.toList());
    }
}
