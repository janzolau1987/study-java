package com.yaoyaohao.study.rules.hello;

import org.easyrules.annotation.Action;
import org.easyrules.annotation.Condition;
import org.easyrules.annotation.Rule;

@Rule(name = "Hello World rule", description = "Say Hello World规则")
public class HelloWorldRule {
	/**
	 * the data that the rule will operate on.
	 */
	private String input;

	@Condition
	public boolean checkInput() {
		// The rule should be applied only if the user's response is yes
		return input.equalsIgnoreCase("yes");
	}

	@Action
	public void sayHelloToDukeFriend() throws Exception {
		// When rule conditions are satisfied, prints 'Hello duke's friend!' to
		// the console
		System.out.println("Hello dule's friend!");
	}

	public void setInput(String input) {
		this.input = input;
	}
}
