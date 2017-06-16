package com.yaoyaohao.study.mybatis;

public class MyBatisTest {
	public static void main(String[] args) {
		StudentService mapper = new StudentService();
		System.out.println(mapper.selectStudentById(8));
	}
}
