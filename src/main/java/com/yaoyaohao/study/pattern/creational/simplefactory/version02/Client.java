package com.yaoyaohao.study.pattern.creational.simplefactory.version02;

public class Client {
	public static void main(String[] args) {
		Chart chart = ChartFactory.getChart("histogram");
		chart.display();
	}
}
