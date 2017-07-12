# Queue
## 1 普通队列
### 1.1 LinkedList
是的，以双向链表实现的LinkedList既是List,也是Queue。

### 1.2 ArrayDeque
以循环数组实现的双向Queue。大小是2的倍数，默认是16。  
为了支持FIFO，即从数组尾压入元素（快），从数组头取出元素（超慢），就不能再使用普通ArrayList实现了，改为使用循环数组。  
有队头队尾两个下标：弹出元素时，队头下标递增；加入元素时，队尾下标递增。如果加入元素时已到数组空间的末尾，则将元素赋值到数组[0]，同时
队尾下标指向0，再插入一个元素则赋值到数组[1]，队尾下标指向1。如果队尾下标追上队头，说明数组所有空间已用完，进行双倍的数组扩容。

### 1.3 PriorityQueue
用平衡二叉最小堆实现的优先级队列，不再是FIFO，而是按元素实现的Comparable接口或传入的Comparator的比较结果来出队，数值越小，优先级越高，
越先出队。但是注意其iterator()的返回不会排序。  
平衡最小堆，用一个简单的数组即可表达，可以快速寻址，没有指针什么的。最小的在queue[0]，比如queue[4]的两个孩子，会在queue[2*4+1]和
queue[2*(4+1)]，即queue[9]和queue[10]。  
入队时，插入queue[size]，然后二叉堆往上浮比较调整堆。  
出队时，弹出queue[0]，然后把queue[size]拿出来往下沉比较调整堆。  
初始大小为11，空间不够时自动50%扩容。

## 2 线程安全队列
### 2.1 ConcurrentLinkedQueue/Deque
无界的并发优化的Queue，基于链表，实现了依赖于CAS的无锁算法。  
ConcurrentLinkedQueue的结构是单向链表和head/tail指针，因为入队时需要修改队尾元素的next指针，以及修改tail指向新入队的元素两个CAS操作无法原子，所以需要特殊的算法

## 3 线程安全阻塞队列
BlockingQueue，一来如果队列 已空不用重复的查看 是否有新数据而会阻塞在那里，二来队列的长度受限，用以保证生产者与消费者的速度不会相关太远。当入队时队列 已满，或出队时队列已空，不同函数的效果见下表：  
|　操作　|　立刻报异常　|　立即返回布尔　|　阻塞等待　|　可设定等待时间　|  
|　入队　|　add(e)　　　|　offer(e)　　　　|　put(e)　　|　offer(e,timeout,unit)　|  
|　出队　|　remove(e)　|　poll(e)　　　　|　take()　　|　poll(timeout,unit)　|  
|　查看　|　element(e)　　　|　peek(e)　　　　|　无　　|　无　|   

### 3.1 ArrayBlockingQueue
定长的并发的BlockingQueue，也是基于循环数组实现。有一把公共的锁与notFull、noteEmpty两个Condition管理队列满或空时的阻塞状态。  

### 3.2 LinkedBlockingQueue/Deque
可选定长的并发优化的BlockingQueue，基于链表实现，所以可将长度设为Integer.MAX_VALUE成为无界无等待的。  
利用链表的特征，分离了takeLock与putLock两把锁，继续用notEmpty、notFull管理队列满或空时阻塞状态。  

### 3.3 PriorityBlockingQueue
无界的PriorityQueue，也是基于数组存储的堆。一把公共的锁实现线程安全。因为无界，空间不够时会自动扩容，所以入列时不会锁，出列为空时才会锁。  

### 3.4 DelayQueue
内部包含一个PriorityQueue，同样是无界，同样出列时才会锁。一把公共的锁实现线程安全。元素需实现Delayed接口，每次调用时需返回当前离触发时间还有多久，小于0表示该触发了。   
pull()时会用peek()查看队头的元素，检查是否到达触发时间。  
ScheduledThreadPoolExecutor用了类似结构。  

## 4 同步队列
SynchronousQueue同步队列本身无容量，放入元素时，比如等待元素被另一条线程的消费者取走再返回。JDK线程池里用到。  
JDK7还有个LinkedTransferQueue，在普通线程安全的BlockingQueue基本上，增加一个transfer(e)函数，效果与SynchronousQueue一样。

