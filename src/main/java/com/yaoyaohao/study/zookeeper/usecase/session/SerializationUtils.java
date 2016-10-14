package com.yaoyaohao.study.zookeeper.usecase.session;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.log4j.Logger;

/**
 * 序列化、反序列化工具
 * 
 * @author liujianzhu
 * @date 2016年10月10日 下午3:33:58
 */
public class SerializationUtils {
	private static Logger log = Logger.getLogger(SerializationUtils.class);

	/**
	 * 反序列化操作
	 * 
	 * @param data
	 * @return
	 */
	public static Object deserialize(byte[] bytes) {
		Object result = null;
		if (bytes == null || bytes.length == 0)
			return null;
		try {
			ByteArrayInputStream byteStream = new ByteArrayInputStream(bytes);
			ObjectInputStream objectStream = new ObjectInputStream(byteStream);
			result = objectStream.readObject();
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}

	/**
	 * 序列化
	 * 
	 * @param obj
	 * @return
	 */
	public static byte[] serialize(Object obj) {
		byte[] result = null;
		if (obj == null)
			return new byte[0];
		try {
			if (!(obj instanceof Serializable)) {
				throw new IllegalArgumentException(" requires a Serializable payload ");
			}
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream(128);
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(obj);
			objectStream.flush();
			result = byteStream.toByteArray();
		} catch (Exception e) {
			log.error(e);
		}
		return result;
	}
}
