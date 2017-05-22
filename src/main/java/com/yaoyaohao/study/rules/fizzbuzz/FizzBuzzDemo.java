package com.yaoyaohao.study.rules.fizzbuzz;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

public class FizzBuzzDemo {
	public static void main(String[] args) {
		//create rules engine
		RulesEngine fizzBuzzEngine = RulesEngineBuilder.aNewRulesEngine()
					.withSkipOnFirstAppliedRule(true)
					.withSilentMode(true)
					.build();
		//create rules
		FizzRule fizzRule = new FizzRule();
		BuzzRule buzzRule = new BuzzRule();
		FizzBuzzRule fizzBuzzRule = new FizzBuzzRule(fizzRule, buzzRule);
		NonFizzBuzzRule nonFizzBuzzRule = new NonFizzBuzzRule();
		
		//register rules
		fizzBuzzEngine.registerRule(fizzRule);
		fizzBuzzEngine.registerRule(buzzRule);
		fizzBuzzEngine.registerRule(fizzBuzzRule);
		fizzBuzzEngine.registerRule(nonFizzBuzzRule);
		
		//fire rules
		for(int i = 0; i <= 100; i++) {
			fizzRule.setInput(i);
			buzzRule.setInput(i);
			nonFizzBuzzRule.setInput(i);
			
			fizzBuzzEngine.fireRules();
			System.out.println();
		}
	}
}
