package com.yaoyaohao.study.nio.rpc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.yaoyaohao.study.nio.rpc.test.HelloService;
import com.yaoyaohao.study.nio.rpc.test.HelloServiceImpl;

/**
 * 基于NIO实现简单rpc服务
 * 
 * @author liujianzhu
 * @date 2016年6月21日 下午3:45:19
 *
 */
public class RpcServer {
	// 此参数以后可做成可配置
	private static int DEAULF_PORT = 10000;

	private int port = DEAULF_PORT;
	private Selector selector;
	private ServerSocketChannel serverSocketChannel;
	
	//模拟bean工厂，这部分可以通过结合spring实现，也可以自定义bean初始化工厂
	private static Map<String, Object> beanCache = new HashMap<String, Object>();
	static {
		HelloService helloService = new HelloServiceImpl();
		beanCache.put(helloService.getClass().getInterfaces()[0].getName(), helloService);
	}

	public RpcServer() {
	}

	public RpcServer(int port) {
		this.port = port;
	}

	/**
	 * 主体运行逻辑
	  * 
	  * @throws Exception
	 */
	public void run() throws Exception {
		selector = Selector.open();
		serverSocketChannel = ServerSocketChannel.open();
		serverSocketChannel.configureBlocking(false);
		serverSocketChannel.socket().setReuseAddress(true);
		serverSocketChannel.socket().bind(new InetSocketAddress(port));
		// 注册监听事件
		serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
		System.out.println("---->> 监听服务启动,端口号: "+port+".");
		while (true) {
			int n = selector.select();
			if (n == 0)
				continue;
			Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
			while (iter.hasNext()) {
				SelectionKey key = iter.next();
				if (key.isAcceptable()) {
					ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
					execute(ssc);
				}
				iter.remove();
			}
		}
	}

	public void close() throws IOException {
		if (selector != null)
			selector.close();
		if (serverSocketChannel != null)
			serverSocketChannel.close();
	}

	/**
	 * 当ServerSocket接收到ACCEPT ready事件后，获取请求数据，调用相关服务接口并通过socket发送返回结果
	  * 
	  * @param serverSocketChannel
	  * @throws Exception
	 */
	private void execute(ServerSocketChannel serverSocketChannel) throws Exception {
		SocketChannel socketChannel = null;
		try {
			socketChannel = serverSocketChannel.accept();
			RpcRequest rpcData = receiveData(socketChannel);
			System.out.println("---->>接收到请求 : " + rpcData);
			//
			Object target = beanCache.get(rpcData.getInterfaceName());
			if(target != null) {
				Class<?> clazz = Class.forName(rpcData.getInterfaceName());
				Method method = clazz.getMethod(rpcData.getMethodName(), rpcData.getParameterTypes());
				Object result = method.invoke(target, rpcData.getParameterValues());
				//
				sendData(socketChannel, result);
			}
		} finally {
			try {
				if (socketChannel != null)
					socketChannel.close();
			} catch (Exception e) {
			}
		}
	}

	/**
	 * 处理接收请求
	  * 
	  * @param socketChannel
	  * @return
	  * @throws IOException
	 */
	private static RpcRequest receiveData(SocketChannel socketChannel) throws IOException {
		RpcRequest reqObj = null;
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		try {
			byte[] bytes;
			int size = 0;
			while ((size = socketChannel.read(buffer)) >= 0) {
				buffer.flip();
				bytes = new byte[size];
				buffer.get(bytes);
				byteStream.write(bytes);
				buffer.clear();
			}
			bytes = byteStream.toByteArray();
			Object obj = SerializableUtil.deserialize(bytes);
			reqObj = (RpcRequest) obj;
		} finally {
			try {
				byteStream.close();
			} catch (Exception e) {
			}
		}
		return reqObj;
	}

	/**
	 * 返回处理结果
	  * 
	  * @param socketChannel
	  * @param respObj
	  * @throws IOException
	 */
	private static void sendData(SocketChannel socketChannel, Object respObj) throws IOException {
		byte[] bytes = SerializableUtil.serialize(respObj);
		ByteBuffer buffer = ByteBuffer.wrap(bytes);
		socketChannel.write(buffer);
	}
}
