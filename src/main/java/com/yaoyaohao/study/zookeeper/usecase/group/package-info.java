package com.yaoyaohao.study.zookeeper.usecase.group;

/*
 * 基于ZooKeeper提供分布式组服务-Group MemberShip
 * 
The pseudocode for the algorithm to implement this group membership protocol is
shown here.
Let a persistent znode, /membership, represent the root of the group in the
ZooKeeper tree. A group membership protocol can then be implemented as follows:
1. Clients joining the group create ephemeral nodes under the group root to
indicate membership.
2. All the members of the group will register for watch events on /membership,
thereby being aware of other members in the group. This is done as shown in
the following code:
L = getChildren("/membership", true)
3. When a new client arrives and joins the group, all other members
are notified.
4. Similarly, when a client leaves due to failure or otherwise, ZooKeeper
automatically deletes the ephemeral znodes created in step 2. This triggers
an event, and other group members get notified.
5. Live members know which node joined or left by looking at the list
of children L.
The preceding algorithm suffers from the herd effect, as events of
NodeChildrenChanged emitted due to the joining or leaving of members will cause
all other members to wake up and find the current membership of the system.
 */