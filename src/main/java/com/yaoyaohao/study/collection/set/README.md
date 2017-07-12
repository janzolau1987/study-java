# Set
所有Set几乎都是内部用一个Map来实现，因为Map里的keySet就是一个Set，而value是假值，全部使用同一个Object即可。  
Set的特征也继承了那些内部Map实现的特征：
* HashSet : 内部是HashMap
* LinkedHashSet : 内部是LinkedHashMap
* TreeSet : 内部是TreeMap的SortedSet
* ConcurrentSkipListSet : 内部是ConcurrentSkipListMap的并发优化的SortedSet
* CopyOnWriteArraySet : 内部是CopyOnWriteArrayList的并发优化的Set，利用其addIfAbsent()方法实现元素去重，该方法性能一般  

好像少了个ConcurrentHashSet，本来也该有一个内部用的ConcurrentHashMap的简单实现，但JDK偏偏没提供。Jetty自己简单封装了一个，Guava则直接用
java.util.Collections.newSetFromMap(new ConcurrentHashMap())实现。