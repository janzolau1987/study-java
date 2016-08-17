package com.yaoyaohao.study.nio.reactor.single;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

/**
 * Handler : 负责处理非阻塞的行为，标识系统管理的资源；同时将handler与事件绑定
 * 
 * @author liujianzhu
 * @date 2016年8月17日 下午4:19:00
 */
public final class Handler implements Runnable {
	private static final int MAXIN = 1024;
	private static final int MAXOUT = 1024;
	final SocketChannel socket;
	final SelectionKey sk;
	ByteBuffer input = ByteBuffer.allocate(MAXIN);
	ByteBuffer output = ByteBuffer.allocate(MAXOUT);

	public Handler(Selector sel, SocketChannel c) throws IOException {
		socket = c;
		socket.configureBlocking(false);
		sk = socket.register(sel, 0);
		sk.attach(this);
		sk.interestOps(SelectionKey.OP_READ);
		sel.wakeup();
	}

	@Override
	public void run() {
		try {
			socket.read(input);
			if (inputIsComplete()) {
				process();
				// use of GoF State-Object pattern
				sk.attach(new Sender());
				sk.interestOps(SelectionKey.OP_WRITE);
				sk.selector().wakeup();
			}
		} catch (IOException ex) {
		}
	}

	class Sender implements Runnable {
		@Override
		public void run() {
			try {
				socket.write(output);
				if (outputIsComplete())
					sk.cancel();
			} catch (IOException ex) {
			}
		}
	}

	/**
	 * 判断读取是否结束
	 * 
	 * @return
	 */
	private boolean inputIsComplete() {
		return true;
	}

	/**
	 * 判断发送写入是否结束
	 * 
	 * @return
	 */
	private boolean outputIsComplete() {
		return true;
	}

	/* 业务处理 */
	private void process() {
		//先读
		input.flip();
		if(input.hasRemaining()) {
			String str = new String(input.array());
			System.out.println("INFO >>> 收到信息: " + str);
		}
		input.clear();
		//后写
		output.clear();
		String result = "我收到你的消息了.";
		output.put(result.getBytes());
		output.flip();
	}
}
