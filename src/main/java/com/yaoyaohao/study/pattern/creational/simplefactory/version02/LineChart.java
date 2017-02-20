package com.yaoyaohao.study.pattern.creational.simplefactory.version02;

/**
 * 折线图类: 具体产品类
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:47:12
 */
public class LineChart implements Chart {
	public LineChart() {
		System.out.println("创建折线图!");
	}

	@Override
	public void display() {
		System.out.println("显示折线图!");
	}

}
