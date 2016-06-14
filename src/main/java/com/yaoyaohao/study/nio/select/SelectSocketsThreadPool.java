package com.yaoyaohao.study.nio.select;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.LinkedList;
import java.util.List;

/**
 * 增加的线程池支持的SelectSockets类特殊版本
 * 
 * @author liujianzhu
 * @date 2016年6月14日 下午3:18:58
 *
 */
public class SelectSocketsThreadPool extends SelectSockets {
	private static final int MAX_THREADS = 5;
	private ThreadPool pool = new ThreadPool(MAX_THREADS);
	
	public static void main(String[] args) throws Exception{
		new SelectSocketsThreadPool().go(args);
	}
	
	/**
	 * 此方法处理通道完成数据准备后的读取操作
	 * 此方法的调用是在父类的go方法实现中
	 * 此方法将数据处理任务交给了pool线程池中线程后直接返回
	 */
	@Override
	protected void readDataFromSocket(SelectionKey key) throws Exception {
		WorkerThread worker = pool.getWorker();
		if(worker == null) {
			//当线程池中无线程可用时直接返回。此处可修改为循环请求直到可用
			return;
		}
		
		worker.serviceChannel(key);
	}
	
	/**
	 * 简单版线程池类（进出策略是FIFO）
	 * 
	 */
	private class ThreadPool {
		List<WorkerThread> idle = new LinkedList<WorkerThread>();
		
		ThreadPool(int poolSize) {
			for(int i = 0;i<poolSize;i++) {
				WorkerThread thread = new WorkerThread(this);
				thread.setName("Worker" + (i + 1));
				thread.start();
				
				//
				idle.add(thread);
			}
		}
		
		/**
		 * 获取可用线程
		  * 
		  * @return
		 */
		WorkerThread getWorker() {
			WorkerThread worker = null;
			synchronized (idle) {
				if(idle.size() > 0)
					worker = idle.remove(0);
			}
			return worker;
		}
		
		/**
		 * 使用完的线程可重新加到线程池中
		  * 
		  * @param worker
		 */
		void returnWorker(WorkerThread worker) {
			synchronized (idle) {
				idle.add(worker);
			}
		}
	}
	
	private class WorkerThread extends Thread {
		private ByteBuffer buffer = ByteBuffer.allocate(1024);
		private ThreadPool pool;
		private SelectionKey key;
		
		WorkerThread(ThreadPool pool) {
			this.pool = pool;
		}
		
		@Override
		public synchronized void run() {
			System.out.println(this.getName() + " is ready");
			while(true) {
				try{
					//Sleep and release object lock
					this.wait();
				} catch(InterruptedException e) {
					e.printStackTrace();
					//清除中断状态
					Thread.interrupted();
				}
				if(key == null) 
					continue; // just in case
				System.out.println(this.getName() + " has been awakened");
				
				try{
					drainChannel(key);
				}catch(Exception e) {
					System.out.println("Caught '" + e + "' closing channel");
					try{
						key.channel().close();
					}catch(IOException ex) {
						ex.printStackTrace();
					}
					key.selector().wakeup();
				}
				key = null;
				this.pool.returnWorker(this);
			}
		}
		
		/**
		  * This method is synchronized, as is the run() method, so only one key can be serviced at a given time.
		  * 
		  * @param key
		 */
		synchronized void serviceChannel(SelectionKey key) {
			this.key = key;
			//在启动worker线程前将key的interest set删除OP_READ
			//当线程处理channel时会忽略读预备
			//大体思路应该是在操作当前读预备时，让Selector不需要再监听channel的读预备事件，当本次读事件操作后再加回对读预备的监听
			key.interestOps(key.interestOps() & (~SelectionKey.OP_READ));
			this.notify();
		}
		
		void drainChannel(SelectionKey key) throws Exception{
			SocketChannel channel = (SocketChannel) key.channel();
			int count;
			buffer.clear();
			
			while((count = channel.read(buffer)) > 0) {
				buffer.flip();
				while(buffer.hasRemaining()) {
					channel.write(buffer);
				}
				buffer.clear();
			}
			if(count < 0) {
				channel.close();
				return;
			}
			//Resume interest in OP_READ
			key.interestOps(key.interestOps() | SelectionKey.OP_READ);
			
			//Cycle the selector so this key is active again
			key.selector().wakeup();
		}
	}
}
