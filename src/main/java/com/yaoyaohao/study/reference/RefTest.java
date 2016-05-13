package com.yaoyaohao.study.reference;

import java.util.WeakHashMap;

/**
 * 弱引用相关测试
 * 
 * @author liujianzhu
 * @date 2016年5月13日 下午5:25:47
 *
 */
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
public class RefTest {
    public static void main(String[] args) {
        WeakHashMap<KeyHolder, ValueHolder> weakMap = new WeakHashMap<KeyHolder, ValueHolder>();
        KeyHolder kh = new KeyHolder();    
        ValueHolder vh = new ValueHolder();
        
        weakMap.put(kh, vh);
        while (true) {
            for (KeyHolder key : weakMap.keySet()) {
                System.out.println(key + " : " + weakMap.get(key));
            }
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("here...");
            //这里把kh设为null，这样一来就只有弱引用指向kh指向的对象
            kh = null;
            System.gc();
        }
    }
}
