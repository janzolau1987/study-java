package com.yaoyaohao.study.pattern.creational.simplefactory.version03;

import com.yaoyaohao.study.pattern.creational.simplefactory.version02.Chart;

public class Client {
	public static void main(String[] args) {
		String type = "pie";
		//
		Chart chart = NewChartFactory.getChart(type);
		chart.display();
	}
}
