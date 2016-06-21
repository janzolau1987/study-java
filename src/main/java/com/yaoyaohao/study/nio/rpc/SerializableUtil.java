package com.yaoyaohao.study.nio.rpc;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * 序列化与反序列化工具
 * 
 * @author liujianzhu
 * @date 2016年6月21日 上午10:07:57
 *
 */
public class SerializableUtil {
	
	/**
	 * 序列化操作
	  * 
	  * @param object
	  * @return
	 */
	public static byte[] serialize(Object object) {
		byte[] result = null;
		if(object == null)
			return new byte[0];
		ByteArrayOutputStream byteStream = null;
		ObjectOutputStream objectStream = null;
		try{
			if(!(object instanceof Serializable)) {
				throw new IllegalArgumentException("SerializeUtil.serialize(Object) requires a Serializable payload " +
						"but received an object of type [" + object.getClass().getName() + "]");
			}
			byteStream = new ByteArrayOutputStream(128);
			objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(object);
			objectStream.flush();
			result = byteStream.toByteArray();
		}catch(Exception e) {
			e.printStackTrace();
		}finally{
			try {
				objectStream.close();
				objectStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try{
				byteStream.close();
				byteStream = null;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	 * 反序列操作
	  * 
	  * @param bytes
	  * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object result = null;
		if(bytes == null || bytes.length == 0)
			return null;
		ByteArrayInputStream byteStream = null;
		ObjectInputStream objectStream = null;
		try{
			byteStream = new ByteArrayInputStream(bytes);
			objectStream = new ObjectInputStream(byteStream);
			result = objectStream.readObject();
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				objectStream.close();
				objectStream = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
			try{
				byteStream.close();
				byteStream = null;
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return result;
	}
}
