package com.yaoyaohao.study.collection.map;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Hash table based implementation of the <tt>Map</tt> interface, with weak keys.
 * An entry in a WeakHashMap will automatically be removed when its key is no longer 
 * in ordinary use. 
 * 
 * 主要目的是为了优化JVM，使JVM中的垃圾回收器GC更智能的回收“无用”的对象。
 * 与其它Map最主要的区别就在于其KEY是弱引用类型
 * 
 * @author liujianzhu
 * @date 2017年7月7日 下午4:57:45
 */
public class WeakHashMapDemo {
	public static void main(String[] args) {
		Map<KeyHolder, ValueHolder> map = new WeakHashMap<>();
		KeyHolder kh = new KeyHolder();
		ValueHolder vh = new ValueHolder();
		map.put(kh, vh);
		
		while(true) {
			for(KeyHolder key : map.keySet()) {
				System.out.println(key + " : " + map.get(key));
			}
			
			try {
				Thread.sleep(2000);
			} catch(InterruptedException e) {}
			
			System.out.println("here .........");
			
			//设置kh为null，这样就只有弱引用指向kh指向的对象
			kh = null;
			System.gc();
		}
	}
}

class KeyHolder {
	@Override
	protected void finalize() throws Throwable {
		System.out.println("I am over from key");
		super.finalize();
	}
}

class ValueHolder {
	@Override
	protected void finalize() throws Throwable {
		System.out.println("I am over from value");
		super.finalize();
	}
}
