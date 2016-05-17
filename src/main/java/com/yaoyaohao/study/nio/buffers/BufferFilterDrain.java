package com.yaoyaohao.study.nio.buffers;

import java.nio.CharBuffer;

/**
 * 缓冲区的填充/释放用例
 * 
 * @author liujianzhu
 * @date 2016年5月17日 下午5:05:36
 *
 */
public class BufferFilterDrain {
	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(100);
		while(fillBuffer(buffer)) {
			buffer.flip();	//翻转
			drainBuffer(buffer);
			buffer.clear(); //清空
		}
	}
	
	/**
	  * 释放缓冲区
	  * 
	  * @param buffer
	 */
	private static void drainBuffer(CharBuffer buffer) {
		while(buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println("");
	}
	
	/**
	  * 填充缓冲区
	  * 
	  * @param buffer
	  * @return
	 */
	private static boolean fillBuffer(CharBuffer buffer) {
		if(index >= strings.length)
			return false;
		String string = strings[index++];
		for(int i = 0;i<string.length();i++) {
			buffer.put(string.charAt(i));
		}
		return true;
	}
	
	private static int index = 0;
	private static String[] strings = {
			"A random string value",
			"The product of an infinite number of monkeys",
			"Hey hey we're the Monkees",
			"Opening act for the Monkees: Jimi Hendrix",
			"'Scuse me while I kiss this fly", // Sorry Jimi ;-)
			"Help Me! Help Me!"
	};
}
