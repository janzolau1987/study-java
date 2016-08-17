package com.yaoyaohao.study.nio.reactor.single;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Reactor : 负责响应IO事件，当检测到一个新的事件，将其发送给相应的Handler去处理
 * 
 * @author liujianzhu
 * @date 2016年8月17日 下午3:45:17
 */
public class Reactor implements Runnable {
	final Selector selector;
	final ServerSocketChannel serverSocket;
	
	public Reactor(int port) throws IOException{
		selector = Selector.open();
		serverSocket = ServerSocketChannel.open();
		serverSocket.bind(new InetSocketAddress(port));
		serverSocket.configureBlocking(false);
		SelectionKey sk = serverSocket.register(selector, SelectionKey.OP_ACCEPT);
		sk.attach(new Acceptor());
	}

	@Override
	public void run() {
		try{
			while(!Thread.interrupted()) {
				selector.select();
				Iterator<SelectionKey> it = selector.selectedKeys().iterator();
				while(it.hasNext()) {
					dispatch(it.next());
					it.remove();
				}
			}
		} catch(IOException ex){}
	}
	
	private void dispatch(SelectionKey k) {
		Runnable r = (Runnable)(k.attachment());
		if(r!=null)
			r.run();
	}

	//inner class
	class Acceptor implements Runnable {
		@Override
		public void run() {
			try{
				SocketChannel c = serverSocket.accept();
				if(c != null)
					new Handler(selector, c);
			} catch(IOException ex) {
				/*Ignore*/
			}
		}
	}
}
