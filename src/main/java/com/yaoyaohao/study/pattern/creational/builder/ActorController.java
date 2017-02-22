package com.yaoyaohao.study.pattern.creational.builder;

//游戏角色创建控制器：指挥者
public class ActorController {
	//逐步构建复杂产品对象
	public Actor construct(ActorBuilder ab) {
		ab.buildCostume();
		ab.buildFace();
		ab.buildHairstyle();
		ab.buildSex();
		ab.buildType();
		return ab.createActor();
	}
}
