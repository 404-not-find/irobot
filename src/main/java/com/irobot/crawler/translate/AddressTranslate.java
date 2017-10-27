package com.irobot.crawler.translate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.irobot.crawler.StaticPageCrawler;

import us.codecraft.xsoup.Xsoup;

public class AddressTranslate {

	private static final Logger LOG = LoggerFactory.getLogger(AddressTranslate.class);
	private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.124 Safari/537.36";
	private static final int TIMEOUT = 10 * 1000; // 10 second
	private static final String ACCEPT = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8";
	private static final String ACCEPT_LANGUAGE = "zh-CN,zh;q=0.8";

	private static final String prefix = "http://e.9181.cn/addt.asp?what=";
	private static final String suffix = "&Submit.x=56&Submit.y=19&Submit=Search&which=prod";
	
	/**
	 * @param url
	 * @return return null if get 404 from target or occur ioexcetpion(be
	 *         logged)
	 * @throws IOException
	 */
	public Document getDoc(String url) {
		if (!url.contains("://")) {
			url = "http://" + url;
		}
		// LOG.info("UUUUUU: " + url);
		try {
			Connection conn = Jsoup.connect(url).userAgent(USER_AGENT).header("Accept", ACCEPT)
					.header("Accept-Language", ACCEPT_LANGUAGE).ignoreContentType(true).timeout(TIMEOUT)
					.ignoreHttpErrors(true);
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

	public String englishAddress(String url, String xpathStr) {
		String address = "";
		Document document = getDoc(url);

		address = Xsoup.compile(xpathStr).evaluate(document).get();

		return address;

	}

	public String buildUrl(String prefix, String chineseAddress, String suffix) {
		StringBuilder sb = new StringBuilder(prefix);
		try {
			if (StringUtils.isEmpty(chineseAddress)) {
				sb.append("").append(suffix);
			} else {
				chineseAddress = URLEncoder.encode(chineseAddress, "UTF-8");
				sb.append(chineseAddress).append(suffix);
			}
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			sb.append("").append(suffix);
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static void main(String[] args) {

		AddressTranslate addressTranslate = new AddressTranslate();
		String address = "北京市海淀区秋露园10号楼3单元301";
		
		String url = addressTranslate.buildUrl(prefix, address, suffix);
		String xpathStr = "//*[@id=\"list_article\"]/tbody/tr[1]/td/font";
		String result = addressTranslate.englishAddress(url, xpathStr);
		
		System.out.println("result: "+ result);

	}

}
