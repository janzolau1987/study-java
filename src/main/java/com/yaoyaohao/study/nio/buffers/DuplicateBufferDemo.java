package com.yaoyaohao.study.nio.buffers;

import java.nio.Buffer;
import java.nio.CharBuffer;

/**
 * 测试缓冲区复制功能
 * 
 * 
 * @author liujianzhu
 * @date 2016年5月17日 下午7:57:52
 *
 */
public class DuplicateBufferDemo {

	public static void main(String[] args) {
		CharBuffer buffer = CharBuffer.allocate(8);
		for(int i= 0 ; i < buffer.capacity() ; i++) {
			buffer.put(String.valueOf(i).charAt(0));
		}
		buffer.flip();
		printBuffer(buffer);
		//
		buffer.position(3).limit(6).mark().position(5);
		CharBuffer dupeBuffer = buffer.duplicate();
		buffer.clear();
		//
		printBuffer(buffer);
		printBuffer(dupeBuffer);
		//
		dupeBuffer.clear();
		printBuffer(dupeBuffer);
		
/**
[limit=8, position = 0, capacity = 8, array = 01234567]
[limit=8, position = 0, capacity = 8, array = 01234567]
[limit=6, position = 5, capacity = 8, array = 5]
[limit=8, position = 0, capacity = 8, array = 01234567]
*/
		
	}
	
	private static void printBuffer(Buffer buffer) {
		System.out.println("[limit=" + buffer.limit() 
				+", position = " + buffer.position()
				+", capacity = " + buffer.capacity()
				+", array = " + buffer.toString()+"]");
	}

}
