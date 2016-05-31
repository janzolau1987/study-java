package com.yaoyaohao.study.nio.channels;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;

/**
 * 通过MappedByteBuffer模拟HTTP server.通过命令行提供文件名，后台模拟web服务提供包括有合适
 * 请求头和文件内容。数据的返回是通过Gatering write实现
 * 
 * @author liujianzhu
 * @date 2016年5月31日 下午7:17:46
 *
 */
public class MappedHttp {
	private static final String OUTPUT_FILE = "MappedHttp.out";
	private static final String LINE_SEP = "\r\n";
	private static final String SERVER_ID = "Server: Ronsoft Dummy Server";
	private static final String HTTP_HDR = "HTTP/1.0 200 OK" + LINE_SEP + SERVER_ID + LINE_SEP;
	private static final String HTTP_404_HDR = "HTTP/1.0 404 Not Found" + LINE_SEP + SERVER_ID + LINE_SEP;
	private static final String MSG_404 = "Could not open file: ";
	
	public static void main(String[] args) throws Exception {
//		if(args.length < 1) {
//			System.err.println("Usage: filename");
//			return;
//		}
		
		//String file = args[0];
		String file = "robot.txt";
		ByteBuffer header = ByteBuffer.wrap(bytes(HTTP_HDR));
		ByteBuffer dynhdrs = ByteBuffer.allocate(128);
		ByteBuffer[] gather = {header,dynhdrs,null};
		String contentType = "unknown/unknown";
		long contentLength = -1;
		
		try{
			FileInputStream fis = new FileInputStream(file);
			FileChannel fc = fis.getChannel();
			MappedByteBuffer filedata = fc.map(MapMode.READ_ONLY, 0, fc.size());
			gather[2] = filedata;
			contentLength = fc.size();
			contentType = URLConnection.guessContentTypeFromName(file);
			//
			fc.close();
			fis.close();
		}catch(IOException e) {
			//如果文件找不到或不能打开，则报告错误
			ByteBuffer buf = ByteBuffer.allocate(128);
			String msg = MSG_404 + e + LINE_SEP;
			
			buf.put(bytes(msg));
			buf.flip();
			
			//
			gather[0] = ByteBuffer.wrap(bytes(HTTP_404_HDR));
			gather[2] = buf;
			
			contentLength = msg.length();
			contentType = "text/plain";
		}
		
		StringBuffer sb = new StringBuffer();
		sb.append("Content-Length : " + contentLength);
		sb.append(LINE_SEP);
		sb.append("Content-Type: ").append(contentType);
		sb.append(LINE_SEP).append(LINE_SEP);
		
		dynhdrs.put(bytes(sb.toString()));
		dynhdrs.flip();
		FileOutputStream fos = new FileOutputStream(OUTPUT_FILE);
		FileChannel out = fos.getChannel();
		
		//
		while(out.write(gather) > 0) {
			//空body
		}
		out.close();
		fos.close();
		System.out.println("output written to " + OUTPUT_FILE);
	}
	
	private static byte[] bytes(String string) throws Exception {
		return string.getBytes("US-ASCII");
	}
}
