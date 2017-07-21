package com.yaoyaohao.study.algorithm.sort;

/**
 * 快速排序：
 * 	通过一趟排序将待排记录分隔成独立的两部分，其中一部分记录的关键字均比另一部分的关键字小，则可分别对这两部分记录继续进行排序，
 * 	以达到整个序列有序。
 * 
 * 具体算法描述如下：
 * <1>.从数列中挑出一个元素，称为 “基准”（pivot）；
 * <2>.重新排序数列，所有元素比基准值小的摆放在基准前面，所有元素比基准值大的摆在基准的后面（相同的数可以到任一边）。在这个分区
 * 		退出之后，该基准就处于数列的中间位置。这个称为分区（partition）操作；
 * <3>.递归地（recursive）把小于基准值元素的子数列和大于基准值元素的子数列排序。
 * 
 * @author liujianzhu
 * @date 2017年7月21日 上午10:20:22
 */
public class QuickSort {
	
	//默认算法实现:默认选择序列的最后一个元素为“基准”
	public static <T extends Object & Comparable<T>> void quickSort(T[] arr) {
		long beginTime = System.nanoTime();	
		
		quickSortInternal(arr, 0, arr.length - 1);
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	private static <T extends Object & Comparable<T>> void quickSortInternal(T[] arr, int left, int right) {
		T x = arr[right], temp;
		int i = left - 1;
		for(int j = left; j <= right; j++) {
			if(arr[j].compareTo(x) <= 0) {
				i++;
				temp = arr[i];
				arr[i] = arr[j];
				arr[j] = temp;
			}
		}
		if(left < i - 1)
			quickSortInternal(arr, left, i - 1);
		if(right > i + 1)
			quickSortInternal(arr, i + 1, right);
	}
	
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		QuickSort.quickSort(arr);
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
