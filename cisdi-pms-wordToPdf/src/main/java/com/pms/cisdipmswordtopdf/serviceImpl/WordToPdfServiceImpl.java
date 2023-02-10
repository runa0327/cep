package com.pms.cisdipmswordtopdf.serviceImpl;

import cn.hutool.core.util.IdUtil;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.pms.cisdipmswordtopdf.api.PoOrderReq;
import com.pms.cisdipmswordtopdf.service.WordToPdfService;
import com.pms.cisdipmswordtopdf.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
@Service
public class WordToPdfServiceImpl implements WordToPdfService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void wordToPdf(PoOrderReq poOrderReq) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(new Date());
        String companyName = poOrderReq.getCompanyName();
        String fileId = StringUtils.replaceCode(poOrderReq.getFileId(),",","','");
        //获取文件地址
        String sql1 = "select * from fl_file where id in ('"+fileId+"') and EXT in ('doc','docx')";
        List<Map<String,Object>> list1 = jdbcTemplate.queryForList(sql1);
        StringBuilder sb = new StringBuilder();
        int i = 1;
//        String filePath = jdbcTemplate.queryForList("select name from test_demo").get(0).get("name").toString();
        String code = jdbcTemplate.queryForList("select remark from base_third_interface where code = 'order_word_to_pdf' ").get(0).get("remark").toString();
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp : list1) {
                String filePath = tmp.get("PHYSICAL_LOCATION").toString();
                String path = filePath.substring(0,filePath.lastIndexOf(code)+1);
                String id = IdUtil.getSnowflakeNextIdStr();
                String copyPath = path+id+"copy.pdf";
                String pdfPath = path+id+".pdf";
                //word转pdf
                wordStartToPdf(filePath,copyPath);
                //pdf加水印
                Boolean res = addWater(companyName,copyPath,pdfPath);
                if (res){
                    sb.append(id).append(",");
                    //删除中间文件
                    deleteFile(copyPath);
                    //获取文件相关信息
                    Map<String,Object> map = getFileMess(pdfPath,id);
                    //写入文件表
                    updateData(map,poOrderReq,tmp,date);
                }
                i++;
            }
        }
        if (sb.length() > 0){
            String newFileId = sb.deleteCharAt(sb.length()-1).toString();
            //更新合同表
            updateOrder(newFileId,poOrderReq);
        }
    }

    /**
     * 更新合同表
     * @param newFileId pdf文件id
     * @param poOrderReq 合同信息
     */
    private void updateOrder(String newFileId, PoOrderReq poOrderReq) {
        String isModel = poOrderReq.getIsModel();
        String attCode = "",newFileIds = poOrderReq.getFileId(), poOrderId = poOrderReq.getId();
        if ("1".equals(isModel)){ //是模板
            attCode = "ATT_FILE_GROUP_ID"; //合同文本
        } else {
            attCode = "FILE_ID_ONE";  //合同修订稿
        }
        //查询pdf文件
        String oldFileId = jdbcTemplate.queryForList("seleCT ATT_FILE_GROUP_ID from po_order_req where id = ?",poOrderId).get(0).get("ATT_FILE_GROUP_ID").toString();
        oldFileId = StringUtils.replaceCode(oldFileId,",","','");
        String sql1 = "select group_concat(id) as id from fl_file where id in ('"+oldFileId+"') and EXT = 'PDF'";
        List<Map<String,Object>> list1 = jdbcTemplate.queryForList(sql1);
        if (!CollectionUtils.isEmpty(list1)){
            String fileId = list1.get(0).get("id").toString();
            List<String> orderFileIds = new ArrayList<>(Arrays.asList(newFileIds.split(",")));
            List<String> pdfFileIds = new ArrayList<>(Arrays.asList(fileId.split(",")));
            orderFileIds.removeAll(pdfFileIds);
            newFileIds = String.join(",",orderFileIds);
        }
        newFileIds = newFileIds + "," + newFileId;
        String sql2 = "update po_order_req set "+attCode+" = ? where id = ?";
        Integer exec = jdbcTemplate.update(sql2,newFileIds,poOrderId);
        log.info("执行成功，共{}条",exec);
    }

    /**
     *
     * @param map 新文件信息
     * @param poOrderReq 合同信息
     * @param oldFileTmp 原始文件信息
     * @param date 时间
     * @return
     */
    private void updateData(Map<String, Object> map, PoOrderReq poOrderReq,Map<String, Object> oldFileTmp,String date) {
        String fileId = map.get("fileId").toString();
        String name = oldFileTmp.get("Name").toString();
        String flPathId = oldFileTmp.get("FL_PATH_ID").toString();
        String createBy = poOrderReq.getCreateBy();
        String size = map.get("size").toString();
        String fileSize = map.get("fileSize").toString();
        String fileIdPath = map.get("fileIdPath").toString();
        String fileName = name+".pdf";
        String insertSql = "insert into fl_file (ID,CODE,NAME,VER,FL_PATH_ID,EXT,STATUS,CRT_DT,CRT_USER_ID,LAST_MODI_DT,LAST_MODI_USER_ID," +
                "SIZE_KB,TS,UPLOAD_DTTM,PHYSICAL_LOCATION,DSP_NAME,DSP_SIZE,IS_PUBLIC_READ) values (" +
                "?,?,?,'1',?,'pdf','AP',?,?,?,?,?,?,?,?,?,?,0)";
        Integer exec = jdbcTemplate.update(insertSql,fileId,fileId,name,flPathId,date,createBy,date,createBy,size,date,date,fileIdPath,fileName,fileSize);
        log.info("执行成功，共{}条",exec);
    }

    /**
     * 文件信息
     * @param filePath 文件地址
     * @param fileId 文件id
     * @return
     */
    private Map<String, Object> getFileMess(String filePath, String fileId) {
        Map<String,Object> map = new HashMap<>();
        File file = new File(filePath);
        long size = file.length()/1024;
        String fileSize = getSize(size);
        String fileName = file.getName();
        map.put("fileSize",fileSize);
        map.put("size",size);
        map.put("fileName",fileName);
        map.put("fileId",fileId);
        map.put("fileIdPath",filePath);
        return map;
    }

    /**
     * 文件大小转换
     * @param size 文件大小(KB)
     * @return
     */
    private String getSize(long size) {
        String fileSize = "";
        if (size >= 1024){
            size = size/1024;
            fileSize = size + "M";
        } else {
            fileSize = size + "K";
        }
        return fileSize;
    }

    /**
     * 文件删除
     * @param filePath 文件地址
     */
    private void deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }
    }

    /**
     * pdf文件加水印
     * @param name 水印名称
     * @param copyPath pdf文件
     * @param pdfPath 加水印后pdf
     */
    private Boolean addWater(String name, String copyPath, String pdfPath) {
        boolean res = true;
        try {
            PdfReader reader = new PdfReader(copyPath);
            int n = reader.getNumberOfPages();
            PdfStamper stamper = null;
            stamper = new PdfStamper(reader, new FileOutputStream(pdfPath));
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.2f);
            PdfContentByte over;
            Rectangle pagesize;
            float x, y;
            for (int i = 1; i <= n; i++) {
                pagesize = reader.getPageSizeWithRotation(i);
                x = (pagesize.getWidth()/2);
                y = (pagesize.getHeight()/3);
                over = stamper.getOverContent(i);
                over.saveState();
                over.setGState(gs1);
                over.setFontAndSize(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),30);
                over.showTextAligned(Element.ALIGN_CENTER , name, x, y*0.6f, 20);
                over.showTextAligned(Element.ALIGN_CENTER , name, x, y*1.5f, 20);
                over.showTextAligned(Element.ALIGN_CENTER , name, x, y*2.5f, 20);
                over.restoreState();
            }
            stamper.close();
            reader.close();
        } catch (DocumentException | IOException e) {
            res = false;
        }
        return res;
    }

    /**
     * word转pdf
     * @param wordPath word文件地址
     * @param pdfPath pdf文件地址
     */
    private void wordStartToPdf(String wordPath, String pdfPath) {
        try {
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
            connection.connect();
            File inputFile = new File(wordPath);
            File outputFile = new File(pdfPath);
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(inputFile, outputFile);
        } catch (ConnectException e) {
            e.printStackTrace();
        }
    }
}
