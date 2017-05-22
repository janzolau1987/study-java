package com.yaoyaohao.study.rules.hello;

import java.util.Scanner;

import org.easyrules.api.RulesEngine;
import org.easyrules.core.RulesEngineBuilder;

public class Launcher {

	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Are you a friend of duke?[yes/no]");
		String input = scanner.nextLine();
		
		//
		scanner.close();
		
		//Declare the rule
		HelloWorldRule rule = new HelloWorldRule();
		
		//Set business data to operate on
		rule.setInput(input.trim());
		
		//Create a rules engine and register the business rule
		RulesEngine rulesEngine = RulesEngineBuilder.aNewRulesEngine()
				.withSkipOnFirstAppliedRule(true)
				.withSilentMode(true)
				.build();
		
		rulesEngine.registerRule(rule);
		
		//Fire rules
		rulesEngine.fireRules();
	}

}
