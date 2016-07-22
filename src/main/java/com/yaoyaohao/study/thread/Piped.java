package com.yaoyaohao.study.thread;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * 管理输入/输出流
 * 注：
 * 管道输入/输出流和普通的文件输入/输出流或者网络输入/输出流不同之处在于，它主要用于线程之前的数据传输，而传输的媒介为内存
 * 
 * @author liujianzhu
 * @date 2016年7月22日 下午2:23:16
 */
public class Piped {
	public static void main(String[] args) throws Exception {
		PipedWriter out = new PipedWriter();
		PipedReader in = new PipedReader();
		//将输入/输出流连接
		in.connect(out);
		
		Thread printThread = new Thread(new Print(in),"PrintThread");
		printThread.start();
		int receive = 0;
		try{
			while((receive = System.in.read()) != -1) {
				out.write(receive);
			}
		}finally {
			out.close();
		}
	}
	
	static class Print implements Runnable {
		private PipedReader in;
		public Print(PipedReader in) {
			this.in = in;
		}
		
		@Override
		public void run() {
			int receive = 0;
			try{
				while((receive = in.read()) != -1) {
					System.out.print((char)receive);
				}
			}catch(IOException e){}
		}
	}
}
