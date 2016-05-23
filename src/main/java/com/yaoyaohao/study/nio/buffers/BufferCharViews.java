package com.yaoyaohao.study.nio.buffers;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;

/**
 * byteBuffer.position(2);注释后结果：
[limit=7, position = 0, capacity = 7, array = java.nio.HeapByteBuffer[pos=0 lim=7 cap=7]]
[limit=3, position = 0, capacity = 3, array = Hi!]

	未注释后结果：
[limit=7, position = 2, capacity = 7, array = java.nio.HeapByteBuffer[pos=2 lim=7 cap=7]]
[limit=2, position = 0, capacity = 2, array = i!]

 * 
 * @author liujianzhu
 * @date 2016年5月23日 下午8:47:41
 *
 */
public class BufferCharViews {

	public static void main(String[] args) {
		ByteBuffer byteBuffer = ByteBuffer.allocate(7).order(ByteOrder.BIG_ENDIAN);
		//Load the ByteBuffer with some bytes
		byteBuffer.put(0,(byte)0);
		byteBuffer.put(1, (byte)'H');
		byteBuffer.put(2,(byte)0);
		byteBuffer.put(3, (byte)'i');
		byteBuffer.put(4,(byte)0);
		byteBuffer.put(5, (byte)'!');
		byteBuffer.put(6,(byte)0);
		
		//
		byteBuffer.position(2);
		CharBuffer charBuffer = byteBuffer.asCharBuffer();
		charBuffer.put(0, 'A');
		//
		printBuffer(byteBuffer);
		printBuffer(charBuffer);
		System.out.println((char)byteBuffer.get(3));
	}

	private static void printBuffer(Buffer buffer) {
		System.out.println("[limit=" + buffer.limit() 
				+", position = " + buffer.position()
				+", capacity = " + buffer.capacity()
				+", array = " + buffer.toString()+"]");
	}
}
