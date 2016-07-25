package com.yaoyaohao.study.thread.timeout.demo03;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import com.yaoyaohao.study.thread.timeout.demo02.DefaultThreadPool;
import com.yaoyaohao.study.thread.timeout.demo02.ThreadPool;

/**
 * 基于线程池技术的简单web服务器
 * 
 * @author liujianzhu
 * @date 2016年7月25日 上午10:03:54
 */
public class SimpleHttpServer {
	//处理HttpRequest线程池
	static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<>(10);
	//根路径 
	static String basePath;
	static ServerSocket serverSocket;
	//监听商品
	static int port = 8080;
	
	public static void setPort(int port) {
		if(port > 0) {
			SimpleHttpServer.port = port;
		}
	}
	
	public static void setBasePath(String basePath) {
		if(basePath != null 
				&& new File(basePath).exists()
				&& new File(basePath).isDirectory()) {
			SimpleHttpServer.basePath = basePath;
		}
	}
	
	//启动SimpleHttpServer
	public static void start() throws Exception  {
		serverSocket = new ServerSocket(port);
		Socket socket = null;
		while((socket = serverSocket.accept()) != null) {
			threadPool.execute(new HttpRequestHandler(socket));
		}
		serverSocket.close();
	}

	static class HttpRequestHandler implements Runnable {
		private Socket socket;

		public HttpRequestHandler(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			String line = null;
			BufferedReader reader = null;
			BufferedReader br = null;
			PrintWriter out = null;
			InputStream in = null;
			try {
				reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String header = reader.readLine();
				//由相对路径 计算出绝对路径 
				String filePath = basePath + header.split(" ")[1];
				out = new PrintWriter(socket.getOutputStream());
				//如果请求资源的后缀为Jpg或ico,则读取资源并输出
				if(filePath.endsWith("jpg") || filePath.endsWith("ico")) {
					in = new FileInputStream(filePath);
					ByteArrayOutputStream baos = new ByteArrayOutputStream();
					int i = 0;
					while((i = in.read()) != -1) {
						baos.write(i);
					}
					byte[] array = baos.toByteArray();
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Molly");
					out.println("Content-Type: image/jpeg");
					out.println("Content-Length: " + array.length);
					out.println("");
					socket.getOutputStream().write(array, 0, array.length);
				}
				else{
					br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
					out = new PrintWriter(socket.getOutputStream());
					out.println("HTTP/1.1 200 OK");
					out.println("Server: Molly");
					out.println("Content-Type: text/html;charset=UTF-8");
					out.println();
					while((line = br.readLine()) != null) {
						out.println(line);
					}
				}
				out.flush();
			} catch (Exception ex) {
				out.println("HTTP/1.1 500");
				out.println("");
				out.flush();
			} finally {
				close(br, in, reader, out, socket);
			}
		}
	}

	// 关闭流或socket
	private static void close(Closeable... closeables) {
		if (closeables != null) {
			for (Closeable closeable : closeables) {
				try {
					closeable.close();
				} catch (Exception ex) {
				}
			}
		}
	}
}
