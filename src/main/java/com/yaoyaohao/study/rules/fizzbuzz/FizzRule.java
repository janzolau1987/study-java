package com.yaoyaohao.study.rules.fizzbuzz;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Priority;
import org.easyrules.annotation.Rule;

@Rule(name = "fizz rule", description = "print \"fizz\" if the number is multiple of 5")
public class FizzRule {
	private int input;
	
	@Condition
	public boolean isFizz() {
		return input % 5 == 0;
	}
	
	@Action
	public void printFizz() {
		System.out.print("fizz");
	}
	
	public void setInput(int input) {
		this.input = input;
	}
	
	@Priority
	public int getPriority() {
		return 1;
	}
}
