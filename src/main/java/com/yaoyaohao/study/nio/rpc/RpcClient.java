package com.yaoyaohao.study.nio.rpc;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * 提供接口方法远程调用rpc服务功能,基于NIO
 * 
 * @author liujianzhu
 * @date 2016年6月21日 下午4:21:52
 *
 */
public class RpcClient {
	
	/**
	  * @Description: 引用服务 ,内部通过动态代理远程调用rpc服务
	  * @param interfaceClass	接口类型
	  * @param host				服务器主机名
	  * @param port				服务器端口
	  * @return	远程服务
	  * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static <T> T refer(final Class<T> interfaceClass,final String host,final int port) throws Exception {
		if(interfaceClass == null)
			throw new IllegalArgumentException("Interface class is null");
		if(!interfaceClass.isInterface()) 
			throw new IllegalArgumentException("The " + interfaceClass.getName() + " must be interface class");
		if(host == null || host.length() == 0)
			throw new IllegalArgumentException("Host is null");
		if(port <= 0 || port > 65535)
			throw new IllegalArgumentException("Invalid port " + port);
		System.out.println("Get remote service " + interfaceClass.getName() + " from server " + host + ":" + port);
		
		return (T)Proxy.newProxyInstance(interfaceClass.getClassLoader(), 
				new Class<?>[]{interfaceClass},
				new InvocationHandler() {
					@Override
					public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
						InetSocketAddress addr = new InetSocketAddress(host, port);
						SocketChannel sc = SocketChannel.open();
						sc.connect(addr);
						try{
							RpcRequest rpcData = new RpcRequest();
							rpcData.setInterfaceName(interfaceClass.getName());
							rpcData.setMethodName(method.getName());
							rpcData.setParameterTypes(method.getParameterTypes());
							rpcData.setParameterValues(args);
							//写数据
							ByteBuffer buffer = ByteBuffer.wrap(SerializableUtil.serialize(rpcData));
							sc.write(buffer);
				            sc.socket().shutdownOutput(); 
				            //取结果
				            Object result = receiveData(sc);
				            return result;
						}catch(IOException e) {
							e.printStackTrace();
						}finally {
							if(sc != null)
								sc.close();
						}
						return null;
					}
				});
	}	
	
	private static Object receiveData(SocketChannel sc) throws Exception {
		Object resultObj = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        try {  
            ByteBuffer buffer = ByteBuffer.allocateDirect(1024);  
            byte[] bytes;  
            int count = 0;  
            while ((count = sc.read(buffer)) >= 0) {  
                buffer.flip();  
                bytes = new byte[count];  
                buffer.get(bytes);  
                baos.write(bytes);  
                buffer.clear();  
            }  
            bytes = baos.toByteArray();  
            resultObj = SerializableUtil.deserialize(bytes);  
            sc.socket().shutdownInput();  
        } finally {  
            try {  
                baos.close();  
            } catch(Exception ex) {}  
        }  
		return resultObj;
	}
}
