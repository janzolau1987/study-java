package com.yaoyaohao.study.rules.fizzbuzz;

import org.easyrules.core.CompositeRule;

public class FizzBuzzRule extends CompositeRule {
	public FizzBuzzRule(Object... rules) {
		for (Object rule : rules) {
			addRule(rule);
		}
	}

	@Override
	public int getPriority() {
		return 0;
	}
}
