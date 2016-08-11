package com.yaoyaohao.study.spi;

import java.util.ServiceLoader;

/**
 * Java SPI机制
 * 规范：
 * 1、需要一个目录： META-INF/services 放到classpath下面
 * 2、目录下放置一个配置文件:文件名是要扩展的接口全名、文件内部为要实现的接口实现类、文件必须为UTF-8编码
 * 3、使用：ServiceLoader<T> loaders = ServiceLoader.load(T.class);
 * 
 * @author liujianzhu
 * @date 2016年8月11日 下午4:03:08
 */
public class SPIDemo {
	public static void main(String[] args) {
		ServiceLoader<IHello> loaders = ServiceLoader.load(IHello.class);
		//
		for(IHello h : loaders) {
			h.sayHello();
		}
	}
}
