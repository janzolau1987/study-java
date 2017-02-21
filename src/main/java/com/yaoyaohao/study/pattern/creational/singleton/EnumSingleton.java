package com.yaoyaohao.study.pattern.creational.singleton;

//说明：
//1.枚举写法简单
//2.枚举自己处理序列化
// 传统单例存在的另外一个问题是一旦你实现了序列化接口，那么它们不再保持单例了，因为readObject()方法一直返回一个新的对象就像java的构造方法一样，
// 你可以通过使用readResolve()方法来避免此事发生
// private Object readResolve(){
//     return INSTANCE;
// }
//3.枚举实例创建是thread-safe
public enum EnumSingleton {
	INSTANCE;
	
	private EnumSingleton() {
		//init method
	}
	
	//其它方法
}
