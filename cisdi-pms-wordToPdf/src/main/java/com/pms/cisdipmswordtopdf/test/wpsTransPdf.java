package com.pms.cisdipmswordtopdf.test;

public class wpsTransPdf {
    public static void main(String[] args) {
        String oldPath = "C:\\Users\\EDY\\Desktop\\kkfileview\\崖州湾科技城种业专业研发外包服务公共平台增采科研配套设备采购合同-2023.6.1（最终修订版）.doc";
        String pdfPath = "C:\\Users\\EDY\\Desktop\\kkfileview\\崖州湾科技城种业专业研发外包服务公共平台增采科研配套设备采购合同-2023.6.1（最终修订版）-wps.pdf";


        System.out.println(oldPath.substring(oldPath.lastIndexOf(".")));


        String type = System.getProperty("os.name");
        System.out.println(type);
    }
}
