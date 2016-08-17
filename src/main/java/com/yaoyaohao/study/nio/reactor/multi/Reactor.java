package com.yaoyaohao.study.nio.reactor.multi;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;

/**
 * Reactor定义类：抽象定义了Reactor的核心组成部分
 * 
 * @author liujianzhu
 * @date 2016年8月17日 下午6:43:22
 */
public class Reactor implements Runnable{
	protected final static int NCPU = Runtime.getRuntime().availableProcessors();
	
	final Selector selector;
	final String name;
	
	public Reactor(String name,Selector selector) {
		this.name = name;
		this.selector = selector;
	}
	
	@Override
	public void run() {
		try {
			while (!Thread.interrupted()) {
				System.out.println("INFO >>> "+this.name + " 正监听  " + selector);
				selector.select();
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while (it.hasNext()) {
					dispatch(it.next());
					it.remove();
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	private void dispatch(SelectionKey k) {
		Runnable r = (Runnable) (k.attachment());
		if (r != null)
			r.run();
	}
}
