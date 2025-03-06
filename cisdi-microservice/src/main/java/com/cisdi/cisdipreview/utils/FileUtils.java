package com.cisdi.cisdipreview.utils;

public class FileUtils {
    public static String formatFileSize(long bytes) {
        // 定义不同单位的转换因子
        final int KB = 1024;
        final int MB = KB * 1024;
        final int GB = MB * 1024;

        // 根据文件大小选择合适的单位
        if (bytes < KB) {
            return bytes + " B";
        } else if (bytes < MB) {
            double kilobytes = (double) bytes / KB;
            return String.format("%.2f KB", kilobytes);
        } else if (bytes < GB) {
            double megabytes = (double) bytes / MB;
            return String.format("%.2f MB", megabytes);
        } else {
            double gigabytes = (double) bytes / GB;
            return String.format("%.2f GB", gigabytes);
        }
    }
}
