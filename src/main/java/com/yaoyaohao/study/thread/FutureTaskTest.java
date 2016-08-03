package com.yaoyaohao.study.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

/**
 * FutureTask 示例
 * 	FutureTask除了实现Future接口外，还实现了Runnable接口。因此FutureTask可以交给Executor执行；也可以通过ExecutorService.submit(...)方法
 * 返回一个FutureTask，然后执行FutureTask.get()方法或FutureTask.cancel(...)方法；除此以外，还可以单独使用FutureTask
 * 
 * @author liujianzhu
 * @date 2016年8月3日 下午8:59:31
 */
public class FutureTaskTest {
	private final ConcurrentHashMap<Object, Future<String>> taskCache = new ConcurrentHashMap<>();
	
	public String executeTask(final String taskName) throws InterruptedException, ExecutionException {
		while (true) {
			Future<String> future = taskCache.get(taskName);
			if(future == null) {
				Callable<String> task = new Callable<String>() {
					@Override
					public String call() throws Exception {
						return taskName;
					}
				};
				//创建任务
				FutureTask<String> futureTask = new FutureTask<>(task);
				future = taskCache.putIfAbsent(taskName, futureTask);
				if(future == null) {
					future = futureTask;
					futureTask.run();
				}
			}
			
			try{
				String result = future.get();
				System.out.println(Thread.currentThread().getName() + " executes task :  " + result);
				return result;
			} catch(CancellationException e) {
				taskCache.remove(taskName, future);
			}
		}
	}
	
	static class Runner implements Runnable {
		private FutureTaskTest test;
		private String taskName;
		
		public Runner(FutureTaskTest test,String taskName) {
			this.test = test;
			this.taskName = taskName;
		}
		
		@Override
		public void run() {
			try {
				this.test.executeTask(this.taskName);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		final FutureTaskTest test = new FutureTaskTest();
		//
		for(int i = 0;i < 10;i++) {
			new Thread(new Runner(test, "task"+ i % 2),"Thread-" + i).start();
		}
	}
}
