package com.cisdi.ext.pm;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class TestExt {

    public static String input = "C:\\Users\\EDY\\Desktop\\demo\\demo.doc";
    public static String inputx = "C:\\Users\\EDY\\Desktop\\demo\\demo.docx";
    public static String output = "C:\\Users\\EDY\\Desktop\\demo\\demo.pdf";
    public static String outputWaterMark = "C:\\Users\\EDY\\Desktop\\demo\\demoWater.pdf";

//    @Resource
//    private DocumentConverter converter;


//    public void testExt() throws IOException {
//        File inputFile = new File(inputx);
//        File outputFile = new File(output);
//        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
//        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
//        converter.convert(inputFile,outputFile);
//
//    }

    public void testExt() throws IOException {

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
        connection.connect();

        File inputFile = new File(input);
        File outputFile = new File(output);

        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        //添加水印
        addFooterAndWater("无痕水印",output,outputWaterMark);
//        addFooterAndWaterNew1("无痕水印",output,outputWaterMark);

    }

    private void addFooterAndWaterNew1(String watermark, String src, String dest) {
        Map<String, String> map = new HashMap();
        try {
            log.info("成功调用插入水印方法");
            PdfReader reader = new PdfReader(src);
            map.put("primary",src);
            log.info("准备插入水印前的文件路径为："+src);
            int n = reader.getNumberOfPages();
            PdfStamper stamper = null;
            try {
                stamper = new PdfStamper(reader, new FileOutputStream(dest));
            } catch (DocumentException e) {
                e.printStackTrace();
            }
            log.info("插入水印后的文件路径为："+dest);
            map.put("show",dest);
            // transparency
            PdfGState gs1 = new PdfGState();
            gs1.setFillOpacity(0.4f);
            // properties
            PdfContentByte over;
            Rectangle pagesize;
            float x, y;
            // loop over every page
            for (int i = 1; i <= n; i++) {
                pagesize = reader.getPageSizeWithRotation(i);
//                x = (pagesize.getLeft() + pagesize.getRight()) / 2;
//                y = (pagesize.getTop() + pagesize.getBottom()) / 2;
                x = (pagesize.getWidth()/3)*2;
                y = (pagesize.getHeight()/2);
                over = stamper.getOverContent(i);
                over.saveState();
                over.setGState(gs1);

//                over.addImage(img, w, 0, 0, h, x - (w / 2), y - (h / 2));
                over.setFontAndSize(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),30);
                over.showTextAligned(Element.ALIGN_CENTER , watermark, x, y, 55);
                over.restoreState();

            }
            log.info("插入水印成功文件页数为"+n);
            stamper.close();
            reader.close();
        } catch (IOException ioexception) {
            log.error("文件异常" + ioexception);
        } catch (DocumentException documentexception) {
            log.error("文件操作异常" + documentexception);

        }
    }

    private void addFooterAndWater(String str, String output, String outputWaterMark) {
        try {
            PdfReader reader = new PdfReader(output);
            PdfReader.unethicalreading = true;
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputWaterMark));
            //这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte under;
            Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {
                for (int i1 = 1; i1 <= 3; i1++) {
                    pageRect = stamper.getReader().getPageSizeWithRotation(i);
                    // 计算水印X,Y坐标
                    float x = (pageRect.getWidth()/3)*i1;
                    float y = (pageRect.getHeight()/3)*i1;
                    // 获得PDF最顶层
                    under = stamper.getOverContent(i);
                    under.saveState();
                    // set Transparency
                    PdfGState gs = new PdfGState();
                    // 设置透明度为0.2
                    gs.setFillOpacity(0.02f);
                    under.setGState(gs);
                    under.restoreState();
                    under.beginText();
                    under.setFontAndSize(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),30);
//                    under.setColorFill(BaseColor.ORANGE);
                    // 水印文字成45度角倾斜
                    under.showTextAligned(Element.ALIGN_CENTER , str, x, y, 55);
                    // 添加水印文字
                    under.endText();
                    under.setLineWidth(1f);
                    under.stroke();
                }

            }
            stamper.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
