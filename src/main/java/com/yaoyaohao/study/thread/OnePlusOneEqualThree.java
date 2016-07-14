package com.yaoyaohao.study.thread;

import java.lang.reflect.Field;

/**
 * 如何实现 1 +　1 = 3
 * 
 * @author liujianzhu
 * @date 2016年7月14日 下午4:33:26
 *
 */
public class OnePlusOneEqualThree {
	
	static {
		try{
			Class<?> intergerCacheClazz = Integer.class.getDeclaredClasses()[0];
			Field cacheField = intergerCacheClazz.getDeclaredField("cache");
			cacheField.setAccessible(true);
			Integer[] caches = (Integer[])cacheField.get(0); 
			caches[130] = caches[131];
		}catch(Exception e){}
	}
	
	public static void main(String[] args){
		System.out.printf("%d", 1 + 1);
	}
}
