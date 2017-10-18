package com.irobot.crawler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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


    private int threadCount = 1;//线程数
    //未访问过的url
    private volatile LinkedBlockingQueue<String> unVisitedUrlQueue = new LinkedBlockingQueue<String>();

    //访问过的url set
    private volatile Set<String> visitedUrlSet = Collections.synchronizedSet(new HashSet<String>());

    private ExecutorService executorService = Executors.newCachedThreadPool();



}
