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
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.pms.cisdipmswordtopdf.controller.ToPdfController;
import com.pms.cisdipmswordtopdf.model.PoOrderReq;
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

    private static final int wdFormatPDF = 17; // PDF 格式
    private static final int xlTypePDF = 0;  // xls格式s

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
        //windows环境下测试获取文件地址，勿删
//        String filePath = jdbcTemplate.queryForList("select name from test_demo").get(0).get("name").toString();
        String code = jdbcTemplate.queryForList("select remark from base_third_interface where code = 'order_word_to_pdf' and SYS_TRUE = 1 ").get(0).get("remark").toString();
        StringBuilder errorBuilder = new StringBuilder();
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp : list1) {
                String filePath = tmp.get("PHYSICAL_LOCATION").toString();
                String path = filePath.substring(0,filePath.lastIndexOf(code)+1);
                String id = IdUtil.getSnowflakeNextIdStr();
                String copyPath = path+id+"copy.pdf";
                String pdfPath = path+id+".pdf";
                //word转pdf
                String error = newPdf(filePath,copyPath);
//                String error = wordStartToPdf(filePath,copyPath);
                if (error.length() > 0 && error != null && !"".equals(error)){
                    errorBuilder.append(error).append("\n ");
                }
                //pdf加水印
                Boolean res = addWater(companyName,copyPath,pdfPath,errorBuilder);
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
            //更新业务表
            updateOrder(newFileId,poOrderReq);
        }
        if (errorBuilder.length() > 0){
            //将问题信息计入提示信息表便于排查分析
            String id = IdUtil.getSnowflakeNextIdStr();
            String remark = "用户"+poOrderReq.getCreateBy()+"在合同签订上传的合同文本转化为pdf失败";
            String sql = "update AD_REMIND_LOG set AD_ENT_ID='0099799190825103145',ENT_CODE='PO_ORDER_REQ',ENTITY_RECORD_ID=?,REMIND_USER_ID='0099250247095871681'," +
                    "REMIND_METHOD='日志提醒',REMIND_TARGET='admin',REMIND_TIME=?,REMIND_TEXT=? where id = ?";
            jdbcTemplate.update(sql,poOrderReq.getId(),date,remark,id);
        }
    }

    private String newPdf(String sfileName, String toFileName) {
        String error = "";
        long start = System.currentTimeMillis();
        ActiveXComponent app = null;
        Dispatch doc = null;
        boolean result = true;

        try {
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", new Variant(false));
            Dispatch docs = app.getProperty("Documents").toDispatch();
            doc = Dispatch.call(docs, "Open", sfileName).toDispatch();
            System.out.println("打开文档..." + sfileName);
            System.out.println("转换文档到 PDF..." + toFileName);
            File tofile = new File(toFileName);
            if (tofile.exists()) {
                tofile.delete();
            }
            Dispatch.call(doc, "SaveAs", toFileName, wdFormatPDF);
            long end = System.currentTimeMillis();
            System.out.println("转换完成..用时：" + (end - start) + "ms.");

            result = true;
        } catch (Exception e) {
            // TODO: handle exception

            System.out.println("========Error:文档转换失败：" + e.getMessage());
            error = "转换失败";
            result = false;
        } finally {
            Dispatch.call(doc, "Close", false);
            System.out.println("关闭文档");
            if (app != null) {
                app.invoke("Quit", new Variant[] {});
            }
        }

        ComThread.Release();
        return error;
    }

    /**
     * 更新合同表
     * @param newFileId pdf文件id
     * @param poOrderReq 合同信息
     */
    private void updateOrder(String newFileId, PoOrderReq poOrderReq) {
        String tableCode = poOrderReq.getTableCode();
        String attCode = poOrderReq.getColsCode();
        String newFileIds = poOrderReq.getFileId();
        String poOrderId = poOrderReq.getId();
        List<String> orderFileIds = new ArrayList<>(Arrays.asList(newFileIds.split(",")));
        List<String> pdfFileIds = new ArrayList<>();
        //查询pdf文件
        String oldFileId = jdbcTemplate.queryForList("select "+attCode+" from "+tableCode+" where id = ?",poOrderId).get(0).get(attCode).toString();
        oldFileId = StringUtils.replaceCode(oldFileId,",","','");
        String sql1 = "select id as id from fl_file where id in ('"+oldFileId+"') and EXT = 'PDF'";
        List<Map<String,Object>> list1 = jdbcTemplate.queryForList(sql1);
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp : list1) {
                String value = tmp.get("id").toString();
                if (value.length() > 0 && value != null){
                    pdfFileIds.add(value);
                }
            }
            if (!CollectionUtils.isEmpty(pdfFileIds)){
                orderFileIds.removeAll(pdfFileIds);
                newFileIds = String.join(",",orderFileIds);
            } else {
                newFileIds = newFileIds + "," + newFileId;
            }
        }
        newFileIds = newFileIds + "," + newFileId;
        String sql2 = "update "+tableCode+" set "+attCode+" = ? where id = ?";
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
     * @param errorBuilder 错误信息记录
     */
    private Boolean addWater(String name, String copyPath, String pdfPath,StringBuilder errorBuilder) {
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
            errorBuilder.append("添加水印失败\n ");
            log.error("添加水印失败，详情： ",e);
        }
        return res;
    }

    /**
     * word转pdf
     * @param wordPath word文件地址
     * @param pdfPath pdf文件地址
     */
    private String wordStartToPdf(String wordPath, String pdfPath) {
        String error = "";
        try {
            //如果是远程调用，使用0.0.0.0。本机调试使用127.0.0.1
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8200);
//            OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0",8100);
            connection.connect();
            File inputFile = new File(wordPath);
            File outputFile = new File(pdfPath);
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            try{
                converter.convert(inputFile, outputFile);
            } catch (Exception e){
                error = "文件转换失败1，"+e;
                log.error("文件转换失败，详情：",e);
            }

        } catch (ConnectException e) {
            error = "文件转换失败，"+e;
            log.error("文件转换失败，详情：",e);
        }
        return error;
    }
}
