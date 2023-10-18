package com.pms.cisdipmswordtopdf.controller;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.*;
import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.pms.cisdipmswordtopdf.util.FileUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping(value = "/trans")
public class ToPdfController {

    private static final int wdFormatPDF = 17; // PDF 格式
    private static final int xlTypePDF = 0;  // xls格式

    @GetMapping(value = "/test")
    public String testHello(){
        return "HELLO WORLD";
    }

    @GetMapping("/start")
    public void startPdf(){
        String old = "C:\\Users\\EDY\\Desktop\\copyFile\\三亚崖州湾科教城安置区（二期）项目门窗、节能及室内环境检测服务合同-终稿.docx";
        String newPdf = "C:\\Users\\EDY\\Desktop\\copyFile\\三亚崖州湾科教城安置区（二期）项目门窗、节能及室内环境检测服务合同-终稿copy.pdf";
        String pdfPath = "C:\\Users\\EDY\\Desktop\\copyFile\\三亚崖州湾科教城安置区（二期）项目门窗、节能及室内环境检测服务合同-终稿.pdf";
        FileUtil.fileExistCheck(old,"C:\\copyFile\\2.doc");
        word2PDF(old,newPdf);
        //pdf加水印
        String companyName = "三亚崖州湾科技城开发建设有限公司";
        StringBuilder errorBuilder = new StringBuilder();
        Boolean res = addWater(companyName,newPdf,pdfPath,errorBuilder);
    }

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
            System.out.println("添加水印失败，详情：");
//            log.error("添加水印失败，详情： ",e);
        }
        return res;
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

    private void trans(String sfileName, String toFileName) {
        System.out.println("------开始转换------");
        String suffix = getFileSufix(sfileName);
        File file = new File(sfileName);
        if (!file.exists()) {
            System.out.println("文件不存在！");
        }
        if (suffix.equals("pdf")) {
            System.out.println("PDF not need to convert!");
        }

        if (suffix.equals("doc") || suffix.equals("docx") || suffix.equals("txt")) {
            word2PDF(sfileName, toFileName);
        } else if (suffix.equals("ppt") || suffix.equals("pptx")) {
            ppt2PDF(sfileName, toFileName);
        } else if (suffix.equals("xls") || suffix.equals("xlsx")) {
            excel2PDF(sfileName, toFileName);
        } else {
            System.out.println("文件格式不支持转换!");
        }
    }

    private void ppt2PDF(String srcFilePath, String pdfFilePath) {
        ActiveXComponent app = null;
        Dispatch ppt = null;
        boolean result = true;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("PowerPoint.Application");
            Dispatch ppts = app.getProperty("Presentations").toDispatch();

            // 因POWER.EXE的发布规则为同步，所以设置为同步发布
            ppt = Dispatch.call(ppts, "Open", srcFilePath, true, // ReadOnly
                    true, // Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();

            Dispatch.call(ppt, "SaveAs", pdfFilePath, 32); // ppSaveAsPDF为特定值32
            System.out.println("转换文档到 PDF..." + pdfFilePath);
            result = true; // set flag true;
        } catch (ComFailException e) {
            result = false;
        } catch (Exception e) {
            result = false;
        } finally {
            if (ppt != null) {
                Dispatch.call(ppt, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
            ComThread.Release();
        }
    }

    private void excel2PDF(String inputFile, String pdfFile) {
        ActiveXComponent app = null;
        Dispatch excel = null;
        boolean result = true;
        try {

            app = new ActiveXComponent("Excel.Application");
            app.setProperty("Visible", false);
            Dispatch excels = app.getProperty("Workbooks").toDispatch();
            excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();
            Dispatch.call(excel, "ExportAsFixedFormat", xlTypePDF, pdfFile);
            System.out.println("打开文档..." + inputFile);
            System.out.println("转换文档到 PDF..." + pdfFile);
            result = true;
        } catch (Exception e) {
            result = false;
        } finally {
            if (excel != null) {
                Dispatch.call(excel, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
        }
    }

    private void word2PDF(String sfileName, String toFileName) {
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
            result = false;
        } finally {
            Dispatch.call(doc, "Close", false);
            System.out.println("关闭文档");
            if (app != null) {
                app.invoke("Quit", new Variant[] {});
            }
        }

        ComThread.Release();
    }

    private String getFileSufix(String fileName) {
        int splitIndex = fileName.lastIndexOf(".");
        return fileName.substring(splitIndex + 1);
    }
}
