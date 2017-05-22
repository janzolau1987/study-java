package com.yaoyaohao.study.rules.shop;

import org.easyrules.core.BasicRule;

public class AgeRule extends BasicRule {
	private static final int ADULT_AGE = 18;

	private Person person;

	public AgeRule(Person person) {
		super("AgeRule", "Check if person's age is > 18 and marks the person as audlt", 1);
		this.person = person;
	}
	
	@Override
	public boolean evaluate() {
		return person.getAge() > ADULT_AGE;
	}
	
	@Override
	public void execute() throws Exception {
		person.setAdult(true);
		System.out.printf("Person %s has been marked as audlt", person.getName());
	}
}
