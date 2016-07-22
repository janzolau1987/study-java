package com.yaoyaohao.study.thread.timeout.demo01;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

/**
 * 模拟数据库驱动，生成数据库连接
 * 
 * @author liujianzhu
 * @date 2016年7月22日 下午4:10:52
 */
public class ConnectionDriver {
	static class ConnectionHandler implements InvocationHandler {
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			if (method.getName().equals("commit")) {
				TimeUnit.SECONDS.sleep(1);
			}
			return null;
		}
	}

	public static final Connection createConnection() {
		return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
				new Class<?>[] { Connection.class }, new ConnectionHandler());
	}
}
