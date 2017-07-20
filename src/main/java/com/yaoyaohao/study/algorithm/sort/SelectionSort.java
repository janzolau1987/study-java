package com.yaoyaohao.study.algorithm.sort;

/**
 * 选择排序:
 * 	选择排序(Selection-sort)是一种简单直观的排序算法。它的工作原理：首先在未排序序列中找到最小（大）元素，存放到排序序列的起始位置，然后，
 * 	再从剩余未排序元素中继续寻找最小（大）元素，然后放到已排序序列的末尾。以此类推，直到所有元素均排序完毕。
 * 
 * 具体算法描述如下：
 * <1>.初始状态：无序区为R[1..n]，有序区为空；
 * <2>.第i趟排序(i=1,2,3…n-1)开始时，当前有序区和无序区分别为R[1..i-1]和R(i..n）。该趟排序从当前无序区中-选出关键字最小的记录 R[k]，
 * 		将它与无序区的第1个记录R交换，使R[1..i]和R[i+1..n)分别变为记录个数增加1个的新有序区和记录个数减少1个的新无序区；
 * <3>.n-1趟结束，数组有序化了。
 * 
 * 
 * @author liujianzhu
 * @date 2017年7月20日 下午5:56:06
 */
public class SelectionSort {
	public static <T extends Object & Comparable<T>> void selectionSort(T[] arr) {
		long beginTime = System.nanoTime();
		
		int len = arr.length;
		int minIndex;
		for(int i= 0; i < len - 1; i++) {
			minIndex = i;
			for(int j = i + 1; j < len; j++) {
				if(arr[j].compareTo(arr[minIndex]) < 0) { //寻找最小数
					minIndex = j; //将最小数索引保存
				}
			}
			T temp = arr[i];
			arr[i] = arr[minIndex];
			arr[minIndex] = temp;
		}
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	//
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		SelectionSort.selectionSort(arr);
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
