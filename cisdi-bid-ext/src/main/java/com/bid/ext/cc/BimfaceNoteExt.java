package com.bid.ext.cc;

import com.bid.ext.model.CcBimfaceNote;
import com.bid.ext.model.CcDocFile;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BimfaceNoteExt {


    public  void addNote(){
        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        String content = (String) extApiParamMap.get("content");
        String docId = (String) extApiParamMap.get("docId");

        if(!StringUtils.hasLength(docId)){
            throw new BaseException("批注文档ID不能为空");
        }

        if(!StringUtils.hasLength(content)){
            throw new BaseException("批注内容不能为空");
        }

        CcDocFile ccDocFile = CcDocFile.selectById(docId);
        if (ccDocFile==null){
            throw new BaseException("批注文档ID错误");
        }

        CcBimfaceNote ccBimfaceNote = CcBimfaceNote.newData();
        ccBimfaceNote.setContent(content);
        ccBimfaceNote.setCcDocId(docId);
        ccBimfaceNote.insertById();
    }

    public void getNoteList(){

        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        String docId = (String) extApiParamMap.get("docId");

        Where queryNote = new Where();
        queryNote.sql("T.CC_DOC_ID="+docId);
        List<CcBimfaceNote> ccBimfaceNotes = CcBimfaceNote.selectByWhere(queryNote);

        String sql = "SELECT JSON_EXTRACT(U.`NAME`, '$.ZH_CN') USERNAME,N.CONTENT, N.CRT_DT CREATE_TIME " +
                "FROM  cc_bimface_note N LEFT JOIN  ad_user U ON  N.CRT_USER_ID=U.ID  " +
                "WHERE N.CC_DOC_ID = '"+docId+"'";


        List<Map<String, Object>> maps = ExtJarHelper.getMyJdbcTemplate().queryForList(sql);

        Map<String,Object> result = new HashMap<>();
        result.put("data",maps);
        ExtJarHelper.setReturnValue(result);
    }

    public void deleteNote(){
        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        String noteId = (String) extApiParamMap.get("noteId");
        CcBimfaceNote ccBimfaceNote = null;
        try {
             ccBimfaceNote = CcBimfaceNote.selectById(noteId);
        }catch (EmptyResultDataAccessException e){
            throw  new BaseException("数据不存在！");
        }

        if (ccBimfaceNote!=null){

            ccBimfaceNote.deleteById();

        }else{
            throw new BaseException("数据不存在！");
        }
    }

    public void updateNote(){
        Map<String, Object> extApiParamMap = ExtJarHelper.getExtApiParamMap();
        String content = (String) extApiParamMap.get("content");
        String noteId = (String) extApiParamMap.get("noteId");

        if (!StringUtils.hasLength(noteId)){
            throw  new BaseException("ID不能为空！");
        }
        if (!StringUtils.hasLength(content)){
            throw  new BaseException("内容不能为空！");
        }
        CcBimfaceNote ccBimfaceNote=null;
        try{
             ccBimfaceNote = CcBimfaceNote.selectById(noteId);
        }catch (EmptyResultDataAccessException  e){
            throw new BaseException("批注记录不存在！");
        }
        if (ccBimfaceNote==null){
            throw new BaseException("批注记录不存在！");
        }

        ccBimfaceNote.setContent(content);
        ccBimfaceNote.updateById();

    }

}
