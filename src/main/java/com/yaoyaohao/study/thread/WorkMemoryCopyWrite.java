package com.yaoyaohao.study.thread;

/**
 * 共有变量的并发问题演示
 * @author Administrator
 *
 */
public class WorkMemoryCopyWrite {
	public static void main(String[] args) {
		final int[] values = {0};
		Thread thread1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int j = 0;j < 10000;j ++) {
					values[0] = 10;
					if(values[0] != 10)
						System.out.println("|第"+j+"次 : " + values[0]);
					values[0] = values[0] + 1;
					if(values[0] != 11)
						System.out.println("||第"+j+"次 : " + values[0]);
					values[0] = values[0] * 10;
					if(values[0] != 110)
						System.out.println("|||第"+j+"次 : " + values[0]);
				}
			}
		},"Thread-1");
		Thread thread2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				for(int j = 0;j < 10000;j ++) {
					values[0] = 1;
					if(values[0] != 1)
						System.out.println("--第"+j+"次 : " + values[0]);
				}
			}
		},"Thread-2");
		
		//
		thread1.start();
		thread2.start();
	}
}
