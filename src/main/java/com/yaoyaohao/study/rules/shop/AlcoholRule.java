package com.yaoyaohao.study.rules.shop;

import org.easyrules.core.BasicRule;

public class AlcoholRule extends BasicRule {
	private Person person;

	public AlcoholRule(Person person) {
		super("AlcoholRule", "Children are not allowed to buy alcohol", 2);
		this.person = person;
	}

	@Override
	public boolean evaluate() {
		return !person.isAdult();
	}

	@Override
	public void execute() {
		System.out.printf("Shop: Sorry %s,you are not allowed to buy alcohol", person.getName());
	}
}
