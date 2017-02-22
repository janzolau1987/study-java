package com.yaoyaohao.study.pattern.creational.builder;

public class Client {
	public static void main(String[] args) {
		ActorBuilder ab = new AngelBuilder(); //建造者
		Actor actor = new ActorController().construct(ab);
		//
		String  type = actor.getType();
        System.out.println(type  + "的外观：");
        System.out.println("性别：" + actor.getSex());
        System.out.println("面容：" + actor.getFace());
        System.out.println("服装：" + actor.getCostume());
        System.out.println("发型：" + actor.getHairstyle());
	}
}
