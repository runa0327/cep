package com.cisdi.ext.pm;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentFormat;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.qygly.ext.jar.helper.ExtJarHelper;
import com.qygly.ext.jar.helper.MyJdbcTemplate;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class TestExt {

    public static String input = "C:\\demo\\demo.doc";
    public static String input1 = "C:\\demo\\demo1.doc";
    public static String inputx = "C:\\demo\\demo.docx";
    public static String output = "C:\\demo\\demo.pdf";
    public static String outputWaterMark = "C:\\demo\\demoWater.pdf";
    public static String outputWaterMark1 = "C:\\demo\\demoWater1.pdf";

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
        MyJdbcTemplate myJdbcTemplate = ExtJarHelper.myJdbcTemplate.get();
//        String path = myJdbcTemplate.queryForList("select name from test_demo").get(0).get("name").toString();

        OpenOfficeConnection connection = new SocketOpenOfficeConnection("127.0.0.1",8100);
//        String command = "C:\\Program Files (x86)\\OpenOffice 4\\program\\soffice.exe -headless -accept=\"socket,host=81.70.1.71,port=8100;urp;";
//        Process pro = Runtime.getRuntime().exec(command);
        connection.connect();


        File inputFile = new File(input);
        File outputFile = new File(output);

        DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
        converter.convert(inputFile, outputFile);

        //添加水印
//        addFooterAndWater("海南城发建设工程有限公司",output,outputWaterMark1);
        addFooterAndWaterNew1("海南城发建设工程有限公司",input,input1);

    }

    /**
     *
     * @param watermark 水印内容
     * @param src 原始文件
     * @param dest 加水印后输出文件
     */
    private void addFooterAndWaterNew1(String watermark, String src, String dest) {
        try {
            log.info("成功调用插入水印方法");
            PdfReader reader = new PdfReader(src);
            log.info("准备插入水印前的文件路径为："+src);
            int n = reader.getNumberOfPages();
            PdfStamper stamper = null;
            stamper = new PdfStamper(reader, new FileOutputStream(dest));
            log.info("插入水印后的文件路径为："+dest);
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
                over.showTextAligned(Element.ALIGN_CENTER , watermark, x, y*0.6f, 20);
                over.showTextAligned(Element.ALIGN_CENTER , watermark, x, y*1.5f, 20);
                over.showTextAligned(Element.ALIGN_CENTER , watermark, x, y*2.5f, 20);
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

    private void addFooterAndWater(String str, String output, String outputWaterMark) {
        try {
            PdfReader reader = new PdfReader(output);
            PdfReader.unethicalreading = true;
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputWaterMark));
            //这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte under;
            PdfGState gs = new PdfGState();
            // 设置透明度为0.2
            gs.setFillOpacity(0.02f);
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

                    under.setGState(gs);
                    under.restoreState();
                    under.beginText();
                    under.setFontAndSize(BaseFont.createFont("STSong-Light","UniGB-UCS2-H", BaseFont.NOT_EMBEDDED),30);
                    under.setColorFill(BaseColor.LIGHT_GRAY);
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
