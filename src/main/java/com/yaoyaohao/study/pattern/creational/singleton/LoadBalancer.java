package com.yaoyaohao.study.pattern.creational.singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 单例模式的一般使用
 * 
 * 				注意：存在并发问题
 * 
 * @author liujianzhu
 * @date 2017年2月21日 下午5:49:54
 */
public class LoadBalancer {
	private static LoadBalancer instance = null;
	
	//服务器集合
	private List<String> serverList = null;
	
	//私有构造函数
	private LoadBalancer() {
		serverList = new ArrayList<>();
	}
	
	//公有静态成员方法，返回唯一实例
	public static LoadBalancer getLoadBalancer() {
		if(instance == null) {
			instance = new LoadBalancer();
		}
		return instance;
	}
	
	//增加服务器
	public void addServer(String server) {
		serverList.add(server);
	}
	
	//删除服务器
	public void removeServer(String server) {
		serverList.remove(server);
	}
	
	//使用Random类随机获取服务器
	public String getServer() {
		Random random = new Random();
		int i = random.nextInt(serverList.size());
		//
		return serverList.get(i);
	}
}
