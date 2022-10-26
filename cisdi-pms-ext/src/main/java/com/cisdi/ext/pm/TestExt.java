package com.cisdi.ext.pm;

//import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TestExt {

    public static String input = "C:\\Users\\Administrator\\Desktop\\Demo\\demo.doc";
    public static String inputx = "C:\\Users\\Administrator\\Desktop\\Demo\\demo.docx";
    public static String output = "C:\\Users\\Administrator\\Desktop\\Demo\\demo.pdf";
    public static String outputWaterMark = "C:\\Users\\Administrator\\Desktop\\Demo\\demoWater.pdf";

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

    }

    private void addFooterAndWater(String str, String output, String outputWaterMark) {
        try {
            PdfReader reader = new PdfReader(output);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(
                    outputWaterMark));
            //这里的字体设置比较关键，这个设置是支持中文的写法
            BaseFont base = BaseFont.createFont("STSong-Light",
                    "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);// 使用系统字体
            int total = reader.getNumberOfPages() + 1;
            PdfContentByte under;
            Rectangle pageRect = null;
            for (int i = 1; i < total; i++) {
                for (int i1 = 1; i1 <= 3; i1++) {
                    pageRect = stamper.getReader().
                            getPageSizeWithRotation(i);
                    // 计算水印X,Y坐标
                    float x = (pageRect.getWidth()/3)*i1;
                    float y = (pageRect.getHeight()/3)*i1;
                    // 获得PDF最顶层
                    under = stamper.getOverContent(i);
                    under.saveState();
                    // set Transparency
                    PdfGState gs = new PdfGState();
                    // 设置透明度为0.2
                    gs.setFillOpacity(1.f);
                    under.setGState(gs);
                    under.restoreState();
                    under.beginText();
                    under.setFontAndSize(base, 60);
                    under.setColorFill(BaseColor.ORANGE);
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
