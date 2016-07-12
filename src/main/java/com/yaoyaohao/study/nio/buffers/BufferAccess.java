package com.yaoyaohao.study.nio.buffers;

import java.nio.CharBuffer;

/**
 * 存取buffer
 * 
 * @author liujianzhu
 * @date 2016年7月11日 下午5:26:36
 *
 */
public class BufferAccess {
	public static void main(String[] args) {
		String str = "Hello Buffer.";
		
		CharBuffer buffer = CharBuffer.allocate(20);
		//存buffer
		for(int i = 0;i<str.length();i++) {
			buffer.put(str.charAt(i));
		}
		
		//翻转buffer
		buffer.flip();
		
		//取buffer
		while(buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
	}
}
