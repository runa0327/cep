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
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class WordToPdfServiceImpl implements WordToPdfService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void wordToPdf(PoOrderReq poOrderReq) {
        String companyName = poOrderReq.getCompanyName();
        String fileId = StringUtils.replaceCode(poOrderReq.getFileId(),",","','");
        //获取文件地址
        String sql1 = "select NAME,PHYSICAL_LOCATION from fl_file where id in ('"+fileId+"')";
        List<Map<String,Object>> list1 = jdbcTemplate.queryForList(sql1);
        if (!CollectionUtils.isEmpty(list1)){
            for (Map<String, Object> tmp : list1) {
                String fileName = tmp.get("NAME").toString();
                String filePath = tmp.get("PHYSICAL_LOCATION").toString();
                String path = filePath.substring(0,filePath.lastIndexOf("/")+1);
                String id = IdUtil.getSnowflakeNextIdStr();
                String copyPath = path+id+"copy.pdf";
                String pdfPath = path+id+".pdf";

                //word转pdf
                wordStartToPdf(fileName,copyPath);
                //pdf加水印
                addWater(companyName,copyPath,pdfPath);
                //删除中间文件
            }
        }
    }

    /**
     * pdf文件加水印
     * @param name 水印名称
     * @param copyPath pdf文件
     * @param pdfPath 加水印后pdf
     */
    private void addWater(String name, String copyPath, String pdfPath) {
        try {
            log.info("成功调用插入水印方法");
            PdfReader reader = new PdfReader(copyPath);
            log.info("准备插入水印前的文件路径为："+copyPath);
            int n = reader.getNumberOfPages();
            PdfStamper stamper = null;
            stamper = new PdfStamper(reader, new FileOutputStream(pdfPath));
            log.info("插入水印后的文件路径为："+pdfPath);
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
            log.info("插入水印成功文件页数为"+n);
            stamper.close();
            reader.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException ioexception) {
            log.error("文件异常" + ioexception);
        }
    }

    /**
     * word转pdf
     * @param wordPath word文件地址
     * @param pdfPath pdf文件地址
     */
    private void wordStartToPdf(String wordPath, String pdfPath) {
        try {
            OpenOfficeConnection connection = new SocketOpenOfficeConnection("0.0.0.0",8100);
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
