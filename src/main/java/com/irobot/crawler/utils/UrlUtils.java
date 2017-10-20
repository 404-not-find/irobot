package com.irobot.crawler.utils;

/**
 * url tool
 *
 * @author xuxueli 2017-10-10 14:57:05
 */
public class UrlUtils {

    /**
     * url格式校验
     */
    public static boolean isUrl(String url) {
        if (url!=null && url.trim().length()>0 && (url.startsWith("http") || url.startsWith("https"))) {
            return true;
        }
        return false;
    }

}
