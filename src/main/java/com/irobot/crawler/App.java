package com.irobot.crawler;

import java.util.Map;
import java.util.Set;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        try {
            StaticPageCrawler staticPageCrawler = new StaticPageCrawler();

            Thread crawl = new Thread(staticPageCrawler);
            crawl.start();
            crawl.join();
            Set<String> urlSet = staticPageCrawler.getUrlSet();

            for (String url : urlSet) {
                System.out.println(url);
            }
            Map<String, String> map = staticPageCrawler.getUrlMap();
            for (Map.Entry<String, String> entry : map.entrySet()) {
                System.out.println("url: " + entry.getKey() + "网页: " + entry.getValue());
            }

            System.out.println("URL 数量:" + urlSet.size());
            System.out.println("URL Html 数量:" + map.size());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
