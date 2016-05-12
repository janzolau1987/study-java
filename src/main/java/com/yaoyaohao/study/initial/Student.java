package com.yaoyaohao.study.initial;
/**
 * 初始化顺序：
 * 1、静态变量/块
 * 2、非静态变更/块 （按各自顺序）
 * 3、构造函数
 * 
 * @author liujianzhu
 * @date 2016年5月12日 上午10:29:59
 *
 */
public class Student {
	int age = defaultAge();
	static int height = defaultHeight();
	
	public Student(){
		System.out.println("Construct " + this.age);
	}
	
	private int defaultAge(){
		System.out.println(age + " defaultAge");
		return 10;
	}
	
	static {
		System.out.println("static block");
	}
	
	{
		System.out.println("non-static block");
	}
	
	static int defaultHeight(){
		System.out.println(height + " defaultHeight");
		return 170;
	}
}
