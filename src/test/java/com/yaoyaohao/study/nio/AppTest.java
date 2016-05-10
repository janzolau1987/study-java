package com.yaoyaohao.study.nio;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

import org.junit.Test;

import junit.framework.TestCase;

public class AppTest extends TestCase {
	
	@Test
	public void testApp() throws IOException {
		ByteBuffer buffer = ByteBuffer.allocate(10);
		
		//
		//byte[] array = new byte[10];
		//ByteBuffer buffer = ByteBuffer.wrap(array); 
		
		//给缓冲区充数据0-9
		for (int i = 0; i < buffer.capacity(); i++) {
			buffer.put((byte)i);
		}
		
		//创建子缓冲区
		buffer.position(3);
		buffer.limit(7);
		ByteBuffer sliceBuffer = buffer.slice();
		
		//改变子缓冲区数据
		for(int i=0;i<sliceBuffer.capacity();i++) {
			byte b = sliceBuffer.get(i);
			b *= 10;
			sliceBuffer.put(i, b);
		}
		
		buffer.position(0);
		buffer.limit(buffer.capacity());
		while(buffer.remaining() > 0) {
			System.out.println(buffer.get());
		}
		
		
		//直接缓冲区，使用allocateDirect
		//直接缓冲区是为加快I/O速度，使用一种特殊方式为其分配内存的缓冲区，JDK文档中的描述为：
		//给定一个直接字节缓冲区，Java虚拟机将尽最大努 力直接对它执行本机I/O操作。也就是说，它会
		//在每一次调用底层操作系统的本机I/O操作之前(或之后)，尝试避免将缓冲区的内容拷贝到一个中间
		//缓冲区中 或者从一个中间缓冲区中拷贝数据。
		ByteBuffer directBuffer = ByteBuffer.allocateDirect(10);
		
		//内存映射缓冲区,MappedByteBuffer
		//内存映射文件I/O是一种读和写文件数据的方法，它可以比常规的基于流或者基于通道的I/O快的多。
		//内存映射文件I/O是通过使文件中的数据出现为 内存数组的内容来完成的，这其初听起来似乎不过就是
		//将整个文件读到内存中，但是事实上并不是这样。一般来说，只有文件中实际读取或者写入的部分才会映射到内存中。
		RandomAccessFile raf = new RandomAccessFile("filePath", "rw");
		FileChannel fc = raf.getChannel();
		
		MappedByteBuffer mbb = fc.map(MapMode.READ_WRITE, 0, 1024);
		mbb.put(0,(byte) 97);
		mbb.put(1023, (byte) 122);
		raf.close();
	}
}
