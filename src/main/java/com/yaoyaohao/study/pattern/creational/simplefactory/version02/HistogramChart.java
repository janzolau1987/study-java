package com.yaoyaohao.study.pattern.creational.simplefactory.version02;

/**
 * 柱状图类:具体产品类
 * 
 * @author liujianzhu
 * @date 2017年2月20日 下午5:43:04
 */
public class HistogramChart implements Chart {
	public HistogramChart() {
		System.out.println("创建柱状图!");
	}
	
	@Override
	public void display() {
		System.out.println("显示柱状图!");
	}
}
