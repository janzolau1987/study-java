package com.yaoyaohao.study.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Exchanger示例 
 * 用于进行线程间的数据交换。它提供一个同步点，在这个同步点，两个线程可以交换彼此的数据。
 * 
 * 应用场景： 
 * 》可以用于遗传算法： 
 * 》可以用于校对工作：
 * 
 * @author liujianzhu
 * @date 2016年8月3日 下午3:50:47
 */
public class ExchangerTest {
	private static final Exchanger<String> exchanger = new Exchanger<>();

	private static ExecutorService threadPool = Executors.newFixedThreadPool(2);

	public static void main(String[] args) {
		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				String A = "银行流水A";
				try {
					String x = exchanger.exchange(A);
					System.out.println("A线程得到的交换数据 : " + x);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		threadPool.execute(new Runnable() {

			@Override
			public void run() {
				String B = "银行流水B";
				try {
					String A = exchanger.exchange(B);

					System.out.println("A和B数据是否一致 ： " + A.equals(B) + " , A录入的是 ： " + A + " , B录入的是 : " + B);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});
		
		threadPool.shutdown();
	}
}
