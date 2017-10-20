package com.irobot.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.irobot.crawler.utils.UrlUtils;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by sunway on 17/10/18.
 */
public class Crawler {
	private static final Logger LOGGER = LoggerFactory.getLogger(Crawler.class);

	private int threadCount = 1;// 线程数
	// 未访问过的url
	private volatile LinkedBlockingQueue<String> unVisitedUrlQueue = new LinkedBlockingQueue<String>();

	// 访问过的url set
	private volatile Set<String> visitedUrlSet = Collections.synchronizedSet(new HashSet<String>());

	private ExecutorService executorService = Executors.newCachedThreadPool();

	/**
	 * add url to queue
	 * @param link
	 * @return
	 */
	public boolean addUrl(String link) {
		// check url format
		if (!UrlUtils.isUrl(link)) {
			return false;
		}
		// check 访问过
		if (visitedUrlSet.contains(link)) {
			return false;
		}
		//check 未访问过
		if (unVisitedUrlQueue.contains(link)) {

			return false;
		}
		
		unVisitedUrlQueue.add(link);
		LOGGER.info(">>>>>>>>>>>>>>> Crawler addUrl: {} ",link);
		return true;
	}

	/**
	 * get and remove a link from queue
	 * @return
	 * @throws InterruptedException
	 */
	public String takeUrl() throws InterruptedException{
		String url = "";
		if(!unVisitedUrlQueue.isEmpty()){
			url = unVisitedUrlQueue.take();
			visitedUrlSet.add(url);
		}
		
		return url;
	}
	
	
}
