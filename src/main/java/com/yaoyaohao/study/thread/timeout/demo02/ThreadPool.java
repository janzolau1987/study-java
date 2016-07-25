package com.yaoyaohao.study.thread.timeout.demo02;

/**
 * 线程池接口
 * @param <Job>
 * @author liujianzhu
 * @date 2016年7月25日 上午9:14:22
 */
public interface ThreadPool<Job extends Runnable> {
	//执行一个job，此job需要实现Runnable
	void execute(Job job);
	//关闭线程池
	void shutdown();
	//增加工作线程
	void addWorkers(int num);
	//减少工作线程
	void removeWorkers(int num);
	//得到正在等待执行的任务数量
	int getJobSize();
}
