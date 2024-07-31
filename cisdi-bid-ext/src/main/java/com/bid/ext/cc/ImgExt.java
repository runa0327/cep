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


    public static void main(String[] args) {
//        String str = "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";
//        //转为img
//        // 将Base64字符串解码为字节数组
//        byte[] imageBytes = Base64.getDecoder().decode(str);
//        // 图片文件保存路径
//        String imagePath = "D:/img/123.png";
//        try (FileOutputStream fos = new FileOutputStream(imagePath)) {
//            fos.write(imageBytes);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//                            set("ORIGIN_FILE_PHYSICAL_LOCATION","/data/qygly/file3/prod/FileStore/"+DateUtil.year(new Date())+"/"+month.format(new Date())+"/"+day.format(new Date())+"/"+id+".png" )
//        System.out.println(DateUtil.year(new Date()));
//        SimpleDateFormat month = new SimpleDateFormat("MM");
//        SimpleDateFormat day = new SimpleDateFormat("dd");
//        System.out.println(month.format(new Date()));
//        System.out.println(day.format(new Date()));
        SimpleDateFormat format = new SimpleDateFormat("YYYYMMDDHHSSmm");
        System.out.println(format.format(new Date()));
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
        }
    }
}
