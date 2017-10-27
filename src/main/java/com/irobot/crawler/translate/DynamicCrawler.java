package com.irobot.crawler.translate;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlImageInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import us.codecraft.xsoup.Xsoup;

public class DynamicCrawler {

	private final static String url = "http://e.9181.cn/addt.asp";
	
	
	public String getText(String chineseAddress, String xpathStr) throws FailingHttpStatusCodeException, MalformedURLException, IOException{
		 WebClient webClient = new WebClient();
		 webClient.getOptions().setCssEnabled(false);
		 webClient.getOptions().setJavaScriptEnabled(false);
		 
		 
		 HtmlPage page = webClient.getPage(url);
		 
		 HtmlForm htmlForm = page.getFormByName("searchform");
		 HtmlImageInput button = htmlForm.getInputByValue("Search");
		 HtmlTextInput  textField = htmlForm.getInputByName("what");
		 textField.setValueAttribute(chineseAddress);
		 HtmlPage nextPage = (HtmlPage)button.click();
		 
		 String result = nextPage.asXml();
		 
		 result = englishAddress(result,xpathStr);
		 
		 System.out.println("text: " + result);
//		 System.out.println("xml: " + page.asXml());
		 
		 
		 
		 webClient.close();
		 return result;
		 
	}
	
	public String englishAddress(String html, String xpathStr) {
		String address = "";
		Document document = Jsoup.parse(html);

		address = Xsoup.compile(xpathStr).evaluate(document).getElements().text();

		return address;

	}
	
	
	
	public static void main(String[] args) {
		
		DynamicCrawler dynamicCrawler = new DynamicCrawler();
		
		String address = "北京市东城区和平里东街12号3宿舍1门412号";
		String xpathStr = "//*[@id=\"list_article\"]/tbody/tr[2]/td/div/font[2]";
		try {
			String result = dynamicCrawler.getText(address,xpathStr);
			
			System.out.println("英文result: " + result);
		} catch (FailingHttpStatusCodeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
