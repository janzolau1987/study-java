package com.yaoyaohao.study.nio.reactor.multi;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

import com.yaoyaohao.study.nio.reactor.single.Handler;

/**
 * 主Reactor处理逻辑
 * 
 * @author liujianzhu
 * @date 2016年8月17日 下午6:38:58
 */
public class MainReactor extends Reactor {
	final ServerSocketChannel serverSocket;

	/** 从Reactor数组 */
	final Selector[] selectors = new Selector[NCPU];
	int next = 0;

	public MainReactor(int port) throws IOException {
		super("主Reactor", Selector.open());
		for (int i = 0; i < NCPU; i++) {
			selectors[i] = Selector.open();
		}
		serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress(port));
		serverSocket.configureBlocking(false);
		SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT); // 主selector负责accept
		sk.attach(new Acceptor());
	}

	// inner class
	class Acceptor implements Runnable {
		@Override
		public synchronized void run() {
			try {
				SocketChannel c = serverSocket.accept();
				if (c != null) {
					System.out.println("INFO >>> 绑定Handler到 " + selectors[next]);
					new Handler(selectors[next], c);
					// 针对每个Reactor都另起线程监听select就绪事件，避免多个Reactor互相影响
					new Thread(new Reactor("从Reactor" + next, selectors[next])).start();
				}
				if (++next == selectors.length)
					next = 0;
			} catch (IOException ex) {
				ex.printStackTrace();
				//
				if(serverSocket != null)
					try {
						serverSocket.close();
					} catch (IOException e) {/**IGNORE*/}
			}
		}
	}
}
