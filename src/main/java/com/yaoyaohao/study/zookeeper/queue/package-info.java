package com.yaoyaohao.study.zookeeper.queue;

/*
	A distributed queue is a very common data structure used in distributed systems.
A special implementation of a queue, called a producer-consumer queue, is where a
collection of processes called producers generate or create new items and put them in
the queue, while consumer processes remove the items from the queue and process
them. The addition and removal of items in the queue follow a strict ordering of FIFO.
	A producer-consumer queue can be implemented using ZooKeeper. A znode will be
designated to hold a queue instance, say queue-znode. All queue items are stored as
znodes under this znode. Producers add an item to the queue by creating a znode
under the queue-znode, and consumers retrieve the items by getting and then
deleting a child from the queue-znode.
	The FIFO order of the items is maintained using sequential property of znode
provided by ZooKeeper. When a producer process creates a znode for a queue
item, it sets the sequential flag. This lets ZooKeeper append the znode name with
a monotonically increasing sequence number as the suffix. ZooKeeper guarantees
that the sequence numbers are applied in order and are not reused. The consumer
process processes the items in the correct order by looking at the sequence number
of the znode.
	The pseudocode for the algorithm to implement a producer-consumer queue using
ZooKeeper is shown here:
1. Let /_QUEUE_ represent the top-level znode for our queue implementation,which is also 
	called the queue-node.
2. Clients acting as producer processes put something into the queue by calling the 
	create() method with the znode name as "queue-" and set the sequence and ephemeral 
	flags if the create() method call is set true:
		create( "queue-", SEQUENCE_EPHEMERAL)
	The sequence flag lets the new znode get a name like queue-N, where N is a monotonically 
	increasing number.
3. Clients acting as consumer processes process a getChildren() method call
	on the queue-node with a watch event set to true:
	M = getChildren(/_QUEUE_, true)
	It sorts the children list M, takes out the lowest numbered child znode from
	the list, starts processing on it by taking out the data from the znode, and
	then deletes it.
4. The client picks up items from the list and continues processing on them.
	On reaching the end of the list, the client should check again whether any
	new items are added to the queue by issuing another get_children()
	method call.
5. The algorithm continues when get_children() returns an empty list;
	this means that no more znodes or items are left under /_QUEUE_.
*/