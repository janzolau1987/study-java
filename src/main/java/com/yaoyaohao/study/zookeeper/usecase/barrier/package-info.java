package com.yaoyaohao.study.zookeeper.usecase.barrier;

/*
 Barrier is a type of synchronization method used in distributed systems to block the
 processing of a set of nodes until a condition is satisfied. It defines a point where
 all nodes must stop their processing and cannot proceed until all the other nodes reach
 this barrier.
 
 The algorithm to implement a barrier using ZooKeeper is as follows:
 1. To start with,a znode is designated to be a barrier znode,say /zk_barrier
 2. The barrier is said to be active in the system if this barrier znode exists.
 3. Each client calls the ZooKeeper API's exists() funtion on /zk_barrier by registering
 	watch events on the barrier znode (the watch event is set to true).
 4. If the exists() method returns false, the barrier no longer exists, and the client
 	proceeds with its computation.
 5. Else, if the exists() method returns true, the clients just waits for watch events.
 6. Whenever the barrier exit condition is met, the client in charge of the barrier
 	will delete /zk_barrier
 7. The deletion triggers a wathc event, and on getting this notification, the client calls
 	the exists() funtion on /zk_barrier again.
 8. Step 7 returns true, and the clients can proceed further.
 */