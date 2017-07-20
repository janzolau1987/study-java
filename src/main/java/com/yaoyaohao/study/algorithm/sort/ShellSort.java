package com.yaoyaohao.study.algorithm.sort;

/**
 * 希尔排序:
 * 	是简单插入排序的改进版；它与插入排序的不同之处在于，它会优先比较距离较远的元素。希尔排序又叫缩小增量排序
 * 	希尔排序的核心在于间隔序列的设定。既可以提前设定好间隔序列，也可以动态的定义间隔序列。
 * 
 * 具体算法描述：
 * <1>. 选择一个增量序列t1，t2，…，tk，其中ti>tj，tk=1；
 * <2>.按增量序列个数k，对序列进行k 趟排序；
 * <3>.每趟排序，根据对应的增量ti，将待排序列分割成若干长度为m 的子序列，分别对各子表进行直接插入排序。仅增量因子
 * 		为1 时，整个序列作为一个表来处理，表长度即为整个序列的长度。
 * 
 * @author liujianzhu
 * @date 2017年7月20日 下午7:37:48
 */
public class ShellSort {
	public static <T extends Object & Comparable<T>> void shellSort(T[] arr) {
		long beginTime = System.nanoTime();
		
		int len = arr.length, 
			gap = 1;
		T temp;
		while(gap < len / 5) { //动态定义间隔序列
			gap = gap * 5 + 1;
		}
		
		for(; gap > 0; gap = Double.valueOf(Math.floor(gap / 5)).intValue()) {
			for(int i = gap; i < len; i++) {
				temp = arr[i];
				int j = i - gap;
				for(; j >= 0 && arr[j].compareTo(temp) > 0; j -= gap) {
					arr[j + gap] = arr[j];
				}
				arr[j + gap] = temp;
			}
		}
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	//
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		ShellSort.shellSort(arr);
		printArray(arr);
	}
	
	private static <T> void printArray(T[] t) {
		StringBuilder sb = new StringBuilder("[");
		for(int i = 0, len = t.length; i < len; i++) {
			if(i == 0)
				sb.append(t[i]);
			else
				sb.append("," + t[i]);
		}
		sb.append("]");
		System.out.println(sb.toString());
	}
}
