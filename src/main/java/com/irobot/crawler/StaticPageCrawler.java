package com.irobot.crawler;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.irobot.crawler.utils.RegExpUtils;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StaticPageCrawler implements Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(StaticPageCrawler.class);

    private Set<String> urlSet = new HashSet<String>();
    private Map<String, String> urlMap = new HashMap<String, String>();

    private final static String homePage = "http://www.wdzj.com";
    private String site = "http://www.wdzj.com";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36";
    private static final int TIMEOUT = 10 * 1000; // 10 second
    private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
    private static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8";

    private static final String regExp = "/^.+wdzj.+html/";

    public StaticPageCrawler() {
    }

    /**
     * @param url
     * @return return null if get 404 from target or occur ioexcetpion(be
     * logged)
     * @throws IOException
     */
    public Document getDoc(String url) {
        if (!url.contains("://")) {
            url = "http://" + url;
        }
//        LOG.info("UUUUUU: " + url);
        try {
            Connection conn = Jsoup.connect(url).userAgent(USER_AGENT).header("Accept", ACCEPT)
                    .header("Accept-Language", ACCEPT_LANGUAGE).ignoreContentType(true).timeout(TIMEOUT).ignoreHttpErrors(true);
            Connection.Response resp = conn.execute();
            if (404 == resp.statusCode()) {
                LOG.warn("GOT 404 from {}", url);
                return null;
            }
            return resp.parse();
        } catch (IOException e) {
            LOG.error("fail to get document object from {}", url, e);
        }
        return null;
    }


    public void crawl(String url) {
        Document doc = getDoc(url);
        if (doc == null)
            return;
        urlSet.add(url);
//        urlMap.put(url, doc.toString());
        Elements links = doc.select("a");

        for (Element link : links) {
            String href = link.attr("abs:href").toLowerCase();
//            LOG.info("href: " + href);

            if (isMatch(href) && !urlSet.contains(href)) {
                urlSet.add(href);
                crawl(href);

            }

        }


    }

    private boolean isMatch(String url) {
        // TODO Auto-generated method stub
        boolean match = false;

        if (url.contains("wdzj") && !url.endsWith(".apk")) {
            match = true;
        }

        return match;
    }


    public Set<String> getUrlSet() {
        return urlSet;
    }


    public Map<String, String> getUrlMap() {
        return urlMap;
    }


    public static String getHomePage() {
        return homePage;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

        crawl(site);


    }


}
