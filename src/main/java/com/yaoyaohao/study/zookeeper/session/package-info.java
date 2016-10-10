package com.yaoyaohao.study.zookeeper.session;

/**
参考地址:http://blog.csdn.net/jacktan/article/details/6112806
实现分布式Session所面临的挑战:
	1）  Session ID的共享
	2）  Session中数据的复制
	3）  Session的失效
	4）  类装载问题

思路：
Web容器中的Session容器不再对用户的Session进行本地管理，而是委托给ZooKeeper和我们自己实现的Session管理器。也就是说，
ZooKeeper负责Session数据的存储，而我们自己实现的Session管理器将负责Session生命周期的管理。最后是关于在分布式环境下共
享Session ID的策略,还是通过客户端的Cookie来实现，我们会自定义一个Cookie，并通过一定的算法在多个子系统之间进行共享。

基于ZooKeeper实现分布式Session的数据模型
 		/
		|
		|__ /SESSIONS
				|
				|_ /1gyh0za3qmld7 <---- SessionMetaData
						|
						|__ /key  <---- value

其中,"/SESSIONS"是一个组节点，用来zookeeper上划分不同功能组的定义。在此节点下可以存放0或N个子节点。
把一个session的实例作为一个节点，节点的名称就是SessionID。在Zookeeper中，每个节点本身也可以存放一个字节数组。
 */