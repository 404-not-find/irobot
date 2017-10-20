package com.irobot.crawler;

public abstract class PageCrawler implements Runnable{

	private Crawler crawler;

	
	public PageCrawler() {
	}

	public PageCrawler(Crawler crawler) {
		this.crawler = crawler;
	}
	
	public abstract void run();
	
}
