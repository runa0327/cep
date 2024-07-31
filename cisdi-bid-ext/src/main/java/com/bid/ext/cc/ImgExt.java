package com.bid.ext.cc;

import cn.hutool.core.date.DateUtil;
import com.bid.ext.model.AdAtt;
import com.bid.ext.model.CcDrawingUpload;
import com.bid.ext.model.FlPath;
import com.bid.ext.utils.JsonUtil;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import com.qygly.ext.jar.helper.sql.Crud;
import com.qygly.ext.jar.helper.sql.Where;
import com.qygly.shared.BaseException;
import com.qygly.shared.interaction.EntityRecord;
import com.qygly.shared.util.JdbcMapUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Slf4j
public class ImgExt {
    /**
     * 考勤打卡 base64编码转为图片
     */
    public void base64ToImg(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        String id = JdbcMapUtil.getString(valueMap, "ID");
        String CARD_SWIPE_IMAGE = JdbcMapUtil.getString(valueMap, "CARD_SWIPE_IMAGE");//刷卡近照
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;
        String fileId = Crud.from("fl_file").insertData();
        Map<String, Object> queryForMap = myJdbcTemplate.queryForMap("SELECT `NAME` FROM ad_user WHERE id = ?", userId);
        String name = JdbcMapUtil.getString(queryForMap, "NAME");
        String userName = JsonUtil.getCN(name);
        toImg(fileId,CARD_SWIPE_IMAGE,userId,userName);
        Crud.from("CC_PERSONNEL_ATTENDANCE").where().eq("ID",id).update().set("IMGS",fileId).exec();

    }

    /**
     * 考勤打卡 base64编码转为图片(批量)
     */
    public void batchBase64ToImg(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<Map<String, Object>> mapList = myJdbcTemplate.queryForList("SELECT\n" +
                "\ta.id,\n" +
                "\tb.`NAME` AS userName,\n" +
                "\ta. CARD_SWIPE_IMAGE,\n" +
                "\tb.id AS userId\n" +
                "FROM\n" +
                "\tCC_PERSONNEL_ATTENDANCE AS a \n" +
                "\tLEFT JOIN ad_user AS b ON a.CRT_USER_ID = b.id\n" +
                "WHERE\n" +
                "\ta.CARD_SWIPE_IMAGE IS NOT NULL \n" +
                "\tAND a.IMGS IS NULL");
        if(!CollectionUtils.isEmpty(mapList)){
            for (Map<String, Object> strMap : mapList) {
                String id = JdbcMapUtil.getString(strMap, "ID");
                String name = JdbcMapUtil.getString(strMap, "userName");
                String userName = JsonUtil.getCN(name);
                String userId = JdbcMapUtil.getString(strMap, "userId");
                String CARD_SWIPE_IMAGE = JdbcMapUtil.getString(strMap, "CARD_SWIPE_IMAGE");
                String fileId = Crud.from("fl_file").insertData();
                toImg(fileId,CARD_SWIPE_IMAGE,userId,userName);
                Crud.from("CC_PERSONNEL_ATTENDANCE").where().eq("ID",id).update().set("IMGS",fileId).exec();
            }
        }
    }

    /**
     * 工人信息 base64编码转为图片
     */
    public void userInfoBase64ToImg(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        EntityRecord entityRecord = ExtJarHelper.getEntityRecordList().get(0);
        Map<String, Object> valueMap = entityRecord.valueMap;
        String id = JdbcMapUtil.getString(valueMap, "ID");
        String RECENT_PHOTO = JdbcMapUtil.getString(valueMap, "RECENT_PHOTO");//近照
        String userId = ExtJarHelper.getLoginInfo().userInfo.id;
        String fileId = Crud.from("fl_file").insertData();
        Map<String, Object> queryForMap = myJdbcTemplate.queryForMap("SELECT `NAME` FROM ad_user WHERE id = ?", userId);
        String name = JdbcMapUtil.getString(queryForMap, "NAME");
        String userName = JsonUtil.getCN(name);
        toImg(fileId,RECENT_PHOTO,userId,userName);
        Crud.from("CC_WORKER_INFORMATION").where().eq("ID",id).update().set("IMGS",fileId).exec();

    }

    /**
     * 人工信息 base64编码转为图片批量
     */
    public void batchUserInfoBase64ToImg(){
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.getMyJdbcTemplate();
        List<Map<String, Object>> mapList = myJdbcTemplate.queryForList("SELECT\n" +
                "\ta.id,\n" +
                "\tb.`NAME` AS userName,\n" +
                "\ta. RECENT_PHOTO,\n" +
                "\tb.id AS userId\n" +
                "FROM\n" +
                "\tCC_WORKER_INFORMATION AS a \n" +
                "\tLEFT JOIN ad_user AS b ON a.CRT_USER_ID = b.id\n" +
                "WHERE\n" +
                "\ta.RECENT_PHOTO IS NOT NULL \n" +
                "\tAND a.IMGS IS NULL");
        if(!CollectionUtils.isEmpty(mapList)){
            for (Map<String, Object> strMap : mapList) {
                String id = JdbcMapUtil.getString(strMap, "ID");
                String name = JdbcMapUtil.getString(strMap, "userName");
                String userName = JsonUtil.getCN(name);
                String userId = JdbcMapUtil.getString(strMap, "userId");
                String RECENT_PHOTO = JdbcMapUtil.getString(strMap, "RECENT_PHOTO");
                String fileId = Crud.from("fl_file").insertData();
                toImg(fileId,RECENT_PHOTO,userId,userName);
                Crud.from("CC_WORKER_INFORMATION").where().eq("ID",id).update().set("IMGS",fileId).exec();
            }
        }
    }



    public void toImg(String id,String base64Str,String userId,String userName){
//        SimpleDateFormat month = new SimpleDateFormat("MM");
//        SimpleDateFormat day = new SimpleDateFormat("dd");
        // 获取属性：
        Where attWhere = new Where();
        attWhere.eq(AdAtt.Cols.CODE, CcDrawingUpload.Cols.CC_ATTACHMENT);
        AdAtt adAtt = AdAtt.selectOneByWhere(attWhere);
        // 获取路径：
        Where pathWhere = new Where();
        pathWhere.eq(FlPath.Cols.ID, adAtt.getFilePathId());
        FlPath flPath = FlPath.selectOneByWhere(pathWhere);

        int year = DateUtil.year(new Date());
        LocalDate localDate = LocalDate.now();
        String month = String.format("%02d", localDate.getMonthValue());
        String day = String.format("%02d", localDate.getDayOfMonth());
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMDDHHSSmm");
        String now = format.format(new Date());
        // 解压输出路径
        String outputDir = flPath.getDir() + year + "/" + month + "/" + day + "/" + id+".png";
        // 将Base64字符串解码为字节数组
        byte[] imageBytes = Base64.getDecoder().decode(base64Str);
        // 图片文件保存路径
        String imagePath = outputDir;
        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
            fos.write(imageBytes);
            Path path = Paths.get(imagePath);
            long fileSize = Files.size(path);//字节
            double size = Math.round(fileSize / 1024.0);//保留两位小鼠
            String nowDate = DateUtil.now();
            Crud.from("fl_file").where().eq("ID",id).update()
                    .set("CODE",id)
                    .set("NAME",now+userName)
                    .set("VER",1)
                    .set("FL_PATH_ID","0099250247095872688")
                    .set("EXT","png")
                    .set("STATUS","AP")
                    .set("CRT_DT",nowDate)
                    .set("CRT_USER_ID",userId)
                    .set("LAST_MODI_DT",nowDate)
                    .set("LAST_MODI_USER_ID",userId)
                    .set("FILE_INLINE_URL","/qygly-gateway/qygly-file/viewImage?fileId="+id+"")
                    .set("FILE_ATTACHMENT_URL","/qygly-gateway/qygly-file/downloadCommonFile?fileId="+id+"")
                    .set("TS",nowDate)
                    .set("UPLOAD_DTTM",nowDate)
                    .set("IS_PUBLIC_READ",1 )
                    .set("ORIGIN_FILE_PHYSICAL_LOCATION","/data/qygly/file3/prod/FileStore/"+DateUtil.year(new Date())+"/"+month+"/"+day+"/"+id+".png" )
                    .set("PHYSICAL_LOCATION","/data/qygly/file3/prod/FileStore/"+DateUtil.year(new Date())+"/"+month+"/"+day+"/"+id+".png" )
                    .set("REMARK","1" )
                    .set("SIZE_KB",size)
                    .set("DSP_NAME",now+userName+".png" )
                    .set("DSP_SIZE",size+"KB")
                    .exec();
        } catch (IOException e) {
            e.printStackTrace();
            throw  new BaseException("无法写入文件到路径：" + imagePath, e);
        }
    }
}
