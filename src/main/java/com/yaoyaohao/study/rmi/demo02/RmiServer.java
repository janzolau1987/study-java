package com.yaoyaohao.study.rmi.demo02;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/**
 * 创建RMI注册表，启动RMI服务，并将远程对象注册到RMI注册表中
 * 
 * @author liujianzhu
 * @date 2016年8月15日 下午4:57:28
 */
public class RmiServer {
	public static void main(String[] args) {
		try{
			//1.实例对象
			PersonService personService = new PersonServiceImpl();
			//2.注册
			LocateRegistry.createRegistry(6600);
			Naming.bind("rmi://127.0.0.1:6600/PersonService", personService);
			//
			System.out.println(">>>> Service start !!!");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
