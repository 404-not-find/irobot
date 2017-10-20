package com.irobot.crawler;

public class ThreadTest implements Runnable{

	private boolean test;
	@Override
	public void run() {
		// TODO Auto-generated method stub
		if(test){
			System.out.println("true: " + test);
		}else{
			System.out.println("false: " + test);
			
		}
	}

	public static void main(String[] args) {
		ThreadTest threadTest = new ThreadTest();
		Thread test = new Thread(threadTest);
		test.start();
		
	}
}
