package com.yaoyaohao.study.thread;

/**
 * 通过wait()/notify()配合实现：
 * 三个线程，线程一输出10次A，线程二输出10次B，线程三输出10次C，要求三个线程按ABC的顺序输出
 * 
 * @author liujianzhu
 * @date 2016年7月6日 下午8:04:04
 *
 */
public class AbcTask implements Runnable {
	private String name;
	private Object pre;
	private Object cur;

	public AbcTask(String name, Object pre, Object cur) {
		this.name = name;
		this.pre = pre;
		this.cur = cur;
	}

	@Override
	public void run() {
		for (int i = 0; i < 10; i++) {
			synchronized (pre) {
				synchronized (cur) {
					if("C".equals(this.name))
						System.out.println(this.name);
					else
						System.out.print(this.name);
					cur.notify();
				}
				//增加判断，避免10次ABC执行完后，主流程还是不能线束
				if(i != 9) {
					try {
						pre.wait();
					} catch (Exception e) {/* ignore */}
				}
			}
		}
	}

	public static void main(String[] args) throws Exception {
		Object conditionA = new Object();
		Object conditionB = new Object();
		Object conditionC = new Object();
		//
		new Thread(new AbcTask("A", conditionC, conditionA)).start();
		Thread.sleep(10);
		new Thread(new AbcTask("B", conditionA, conditionB)).start();
		Thread.sleep(10);
		new Thread(new AbcTask("C", conditionB, conditionC)).start();
	}

}
