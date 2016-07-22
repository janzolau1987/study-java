package com.yaoyaohao.study.thread.timeout.demo01;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 测试
 * 
 * @author liujianzhu
 * @date 2016年7月22日 下午4:37:24
 */
public class ConnectionPoolTest {
	static ConnectionPool pool = new ConnectionPool(10);
	//保证所有ConnectionRunner能够同时开始
	static CountDownLatch start = new CountDownLatch(1);
	//main线程将会等待所有ConnectionRunner结束后才能继续执行
	static CountDownLatch end ;
	
	public static void main(String[] args) throws Exception{
		//线程数量，可以修改线程数量进行观察
		int threadCount = 20;
		end = new CountDownLatch(threadCount);
		int count = 20;
		AtomicInteger got = new AtomicInteger();
		AtomicInteger notGot = new AtomicInteger();
		for(int i = 0; i < threadCount; i++) {
			Thread thread = new Thread(new ConnectionRunner(count,got,notGot),"ConnectionRunnerThread" + i);
			thread.start();
		}
		start.countDown();
		end.await();
		//
		System.out.println("Total invoke : " + (threadCount * count));
		System.out.println("Got connection : " + got);
		System.out.println("Not got connection : " + notGot);
	}
	
	static class ConnectionRunner implements Runnable {
		int count;
		AtomicInteger got;
		AtomicInteger notGot;
		
		public ConnectionRunner(int count, AtomicInteger got, AtomicInteger notGot) {
			super();
			this.count = count;
			this.got = got;
			this.notGot = notGot;
		}

		@Override
		public void run() {
			try{
				start.await();
			}catch(Exception e){}
			while(count > 0) {
				try{
					//从线程池中获取连接，如果1000ms内无法获取到，将会返回null
					//分别统计连接获取数量got和未获取到的数据notGot
					Connection connection = pool.fetchConnection(1000);
					if(connection != null) {
						try{
							connection.createStatement();
							connection.commit();
						}finally {
							pool.releaseConnection(connection);
							got.incrementAndGet();
						}
					} else{
						notGot.incrementAndGet();
					}
				}catch(Exception ex){}
				finally{
					count --;
				}
			}
			end.countDown();
		}
	}
}
