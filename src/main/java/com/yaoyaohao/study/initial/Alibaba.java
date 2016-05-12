package com.yaoyaohao.study.initial;
/**
 * 结果：
		1:j   i=0    n=0
		2:构造块   i=1    n=1
		3:t1   i=2    n=2
		4:j   i=3    n=3
		5:构造块   i=4    n=4
		6:t2   i=5    n=5
		7:i   i=6    n=6
		8:静态块   i=7    n=99
		9:j   i=8    n=100
		10:构造块   i=9    n=101
		11:init   i=10    n=102

1、调用Alibaba类的static方法main，所以JVM会在classpath中加载Alibaba.class，然后对所有static的成员变量进行默认初始化。
	这时k、i、n被赋值为0，t1、t2被赋值为null。
2、又因为static变量被显式赋值了，所以进行显式初始化，当对t1进行显式赋值时，用new的方法调用了Alibaba的构造函数，所以这次需要对t1对象进行初始化。
	因为Alibaba所有static部分已经在第一步中初始化过了（虽然第一步中还没有执行static块，但在初始化t1时也不会去执行static块，因为JVM认为这
	次是第二次加载Alibaba这个类了，所有的static部分都会被忽略掉），所以这次直接初始化非static部分。于是有了
	1:j   i=0    n=0
	2:构造块   i=1    n=1
	3:t1   i=2    n=2
	的输出。
3、接着对t2进行赋值，过程与t1相同
	4:j   i=3    n=3
	5:构造块   i=4    n=4
	6:t2   i=5    n=5
4、t1、t2这两个static成员变量赋值完后到了static的i与n，于是有了下面的输出：
	7:i   i=6    n=6
5、到现在为止，所有的static的成员变量已经被第二次赋值过了，下面到static块了
	8:静态块   i=7    n=99
6、至此，所有的static部分赋值完毕了，下面开始执行main方法里面的内容，因为main方法的第一行就又用new的方式调用了Alibaba的构造函数，所以其过程与t1、t2类似
	9:j   i=8    n=100
	10:构造块   i=9    n=101
	11:init   i=10    n=102

 * 
 * @author liujianzhu
 * @date 2016年5月12日 上午11:32:14
 *
 */
public class Alibaba {
	public static int k = 0;
    public static Alibaba t1 = new Alibaba("t1");
    public static Alibaba t2 = new Alibaba("t2");
    public static int i = print("i");
    public static int n = 99;
    private int a = 0;
    public int j = print("j");
    {
        print("构造块");
    }
    static {
        print("静态块");
    }
    public Alibaba(String str) {
        System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
        ++i;
        ++n;
    }
    public static int print(String str) {
        System.out.println((++k) + ":" + str + "   i=" + i + "    n=" + n);
        ++n;
        return ++i;
    }
    public static void main(String args[]) {
        Alibaba t = new Alibaba("init");
    }
}
