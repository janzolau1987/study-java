package com.yaoyaohao.study.rules.fizzbuzz;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "buzz rule", description = "print \"buzz\" if the number is multiple of 7")
public class BuzzRule {
	private int input;
	
	@Condition
	public boolean isBuzz() {
		return input % 7 == 0;
	}
	
	@Action
	public void printBuzz() {
		System.out.print("buzz");
	}
	
	public void setInput(int input) {
		this.input = input;
	}
	
	@Priority
	public int getPriority() {
		return 2;
	}
}
