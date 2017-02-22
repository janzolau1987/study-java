package com.yaoyaohao.study.pattern.creational.prototype;

public class Client {
	public static void main(String[] args) {
		WeekLog logPrevious = new WeekLog();
		logPrevious.setName("张三");
		logPrevious.setDate("第12周");
		logPrevious.setContent("这周工作很忙!");
		
		printLog(logPrevious);
		WeekLog logNew = logPrevious.clone();
		logNew.setDate("第13周");
		printLog(logNew);
		//
		compareLog(logPrevious, logNew);
	}
	
	private static void printLog(WeekLog log) {
		System.out.println("****周报****");
        System.out.println("周次：" +  log.getDate());
        System.out.println("姓名：" +  log.getName());
        System.out.println("内容：" +  log.getContent());
        System.out.println("--------------------------------");
	}
	
	private static void compareLog(WeekLog logPrevious, WeekLog logNew) {
		System.out.println("比较--->");
		System.out.println(logPrevious == logNew);
		System.out.println(logPrevious.getContent() == logNew.getContent());
		System.out.println(logPrevious.getDate() == logNew.getDate());
		System.out.println(logPrevious.getName() == logNew.getName());
	}
}
