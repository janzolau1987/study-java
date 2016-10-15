package com.yaoyaohao.study.zookeeper.usecase.twopc;

/*
 * 基于ZooKeeper提供2PC服务 - Two-Phase Commit
 * 
	The two-phase commit (2PC) protocol is a distributed algorithm that coordinates
all the processes that participate in a distributed atomic transaction on whether to
commit or abort (roll back) the transaction. 2PC is a specialized type of consensus
protocol and is widely used in transaction processing systems. It ensures an
atomic behavior that guarantees that either all the transactions or none of them are
completed so that the resources under transactional control remain synchronized.
	The 2PC protocol consists of two phases, which are as follows:
		• In the first phase, the coordinator node asks all the transaction's participating
		  processes to prepare and vote to either commit or abort the transaction.
		• In the second phase, the coordinator decides whether to commit or abort
		  the transaction, depending on the result of the voting in the first phase. If all
		  participants voted for commit, it commits the transaction; otherwise, it aborts
		  it. It finally notifies the result to all the participants.
	Let /2PC_Transactions represent the root node to run the 2PC algorithm in
ZooKeeper. The algorithm to do so is as follows.
	1. A coordinator node creates a transaction znode, say /2PC_Transactions/
		TX. We can use the leader election algorithm to elect the coordinator using
		ZooKeeper. The coordinator node sets a watch on the transaction node.
	2. Another persistent znode, tx_result, is created under /2PC_Transactions/
		TX by the coordinator to post the result of the protocol, commit, or abort, and
		any additional outcomes of the transactions.
	3. Each participating client node sets a watch on the /2PC_Transactions as
		well as /2PC_Transactions/TX/tx_result znode paths.
	4. When the coordinator node creates the transaction znode, it notifies the
		participating client nodes that the coordinator node is requesting for voting
		on the transaction.
	5. The participants then create an ephemeral child znode in the /2PC_
		Transactions/TX path, with their own identifier (say hostnames) and vote
		for commit or abort by writing to the data field of their specific znodes.
	6. The coordinator is notified of the creation of all the child znodes, and when
		the number of child znodes in /2PC_Transactions/TX equals the number
		of participants, it checks the votes of all the participants by reading the
		participants' znodes.
	7. If all the participants voted for commit, the coordinator commits the
		transaction; otherwise, it aborts it. Subsequently, it posts the result of the
		transaction by writing to the /2PC_Transactions/TX/tx_result znode.
	8. The participant znodes get to know the outcome of the transaction when it
		gets a notification of NodeDataChanged for /2PC_Transactions/TX/tx_
		result.
	The preceding algorithm might be a little slow, as all messaging happens through
the coordinator node, but it takes care of the participant nodes' failure during the
execution of the protocol, using ephemeral znodes.
 */