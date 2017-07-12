# 1  Map
### 2.1 HashMap
以Entry[]数组实现的哈希桶数组，用KEY的哈希值取模桶数组的大小可得到数组下标。  
插入元素时，如果两条KEY落在同一个桶（比如哈希值1和17取模16后都属于第一个哈希桶），称之为哈希冲突。  
JDK的做法是链表法，Entry用一个next属性实现多个Entry以单身链表存放。查找哈希值为17的key时，先定位到哈希桶，然后链表遍历桶里所有元素，
逐个比较其Hash值然后key值。  
在JDK8里，新增默认为8的阈值，当一个桶里Entry超过阈值，就不以单身链表而以红黑树来存放以加快Key的查找速度。  
当然最后还是桶里只有一个元素，不用去比较。所以默认当Entry数量达到桶数量75%时哈希冲突已比较严重，就会成倍扩容桶数组，并重新分配所有原来的
Entry。扩容成本不低，所以也最好有个预估值。  
取模用与操作（hash & (arrayLength - 1)）会比较快，所以数组的大小永远是2的N次方，随便给一个初始值比如17会转为32。默认第一次放入元素时的
初始值是1。  
iterator()是顺着哈希桶数组来遍历，是乱序。

### 1.2 LinkedHashMap
扩展HashMap，每个Entry增加双向链表，号称是最占内存的数据结构。  
支持iterator()时按Entry的插入顺序来排序(如果设置accessOrder属性为true，则所有读写访问都排序)。  
插入时，Entry把自己加到Header Entry的前面去。如果所有读写访问都要排序，还要把前后Entry的before/after拼接起来以在链表中删除自己，所以
此时读操作也是线程不安全的。

### 1.3 TreeMap
以红黑树实现，红黑树又叫自平衡二叉树:
>> 对于任一节点而言，其到叶子节点的每一条路径都包含相同数目的黑结点。  

上面的规定使得树的层次不会差的太远，使得所有操作的复杂度不超过O(lgn)，但也使得插入、修改时要复杂的左旋左旋来保持树的平衡。  
支持iterator()时按key值排序，可按实现了Comparable接口的key的升序排序，或由传入的Comparator控制。可想象的，在树上插入/删除元素的代价一定比HashMap的大。  
支持SortedMap接口，如firstKey()，lastKey()取得最大最小的key，或sub(fromKey,toKey)，tailMap(fromKey)剪取Map的某一段。  

### 1.4 EnumMap
EnumMap的原理是，在构造函数里要传入枚举类，那它就构建一个与枚举的所有值等大的数组，按Enum.ordinal()下标来访问数组。性能与内存占用俱佳。  

### 1.5 ConcurrentHashMap
并发优化的HashMap。    
在JDK5里的经典设计默认16把写锁（可以设置更多），有效分散了阻塞的概率。数据结构为Segment[]，每个Segment一把锁。Segment里面才是哈希桶
数组。Key先算出它在哪个Segment里，再去算它在哪个哈希桶里。  
也没有读锁，因为put/remove动作是原子动作（比如put的整个过程是一个对数组元素/Entry指针的赋值操作），读操作不会看到一个更新动作的中间状态。  
但在JDK8里，Segment[]的设计被抛弃，改为精心设计的，只在需要锁的时候加锁。  
支持ConcurrentMap接口，如putIfAbsent(key,value)与相反的replace(key,value)与以及实现CAS的replace(key,oldValue,newValue)。


### 1.6 ConcurrentSkipListMap
JDK6新增的并发优化的SortedMap，以SkipList结构实现。Concurrent包选用它是因为它支持基于CAS的无锁算法，而红黑树则没有好的无锁算法。  
原理上可以想象为多个链表组成的N层楼，其中的元素从稀疏到密集，每个元素有往右与往下的指针。从第一层楼开始遍历，如果右端的值比期望的大，那就往下走一层，继续往
前走。  
典型的空间换时间。每次插入，都要决定在哪几层插入，同时要决定 要不要多盖一层楼。  
它的size()同样不会随便调，会遍历来统计。