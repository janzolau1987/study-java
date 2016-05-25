package com.yaoyaohao.study.nio.channels;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * 通道之道的copy操作
 * 
 * @author liujianzhu
 * @date 2016年5月25日 上午11:50:06
 *
 */
public class ChannelCopy {

	public static void main(String[] args) throws IOException {
		ReadableByteChannel source = Channels.newChannel(System.in);
		WritableByteChannel dest = Channels.newChannel(System.out);
		
		channelCopy1(source, dest);
		//channelCopy2(source, dest);
		
		source.close();
		dest.close();
	}
	
	private static void channelCopy1(ReadableByteChannel src,WritableByteChannel dest)
		throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		while(src.read(buffer) != -1) {
			//Prepare the buffer to be drained
			buffer.flip();
			//Write to the channel;may block
			dest.write(buffer);
			//If partial transfer,shift remainder down
			buffer.compact();
		}
		//EOF will leave buffer in fill state
		buffer.flip();
		//
		while(buffer.hasRemaining()) {
			dest.write(buffer);
		}
	}
	
	private static void channelCopy2(ReadableByteChannel src,WritableByteChannel dest)
		throws IOException {
		ByteBuffer buffer = ByteBuffer.allocateDirect(16 * 1024);
		while(src.read(buffer) != -1) {
			buffer.flip();
			//
			while(buffer.hasRemaining()) {
				dest.write(buffer);
			}
			buffer.clear();
		}
	}

}
