package com.yaoyaohao.study.pattern.creational.simplefactory.version02;

/**
 * 饼状图类:具体产品类
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:44:37
 */
public class PieChart implements Chart {
	public PieChart() {
		System.out.println("创建饼状图!");
	}

	@Override
	public void display() {
		System.out.println("显示饼状图!");
	}

}
