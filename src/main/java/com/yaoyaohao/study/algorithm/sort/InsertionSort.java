package com.yaoyaohao.study.algorithm.sort;

/**
 * 插入排序:
 * 	通过构建有序序列，对于未排序数据，在已排序序列中从后向前扫描，找到相应位置并插入。插入排序在实现上，通常采用
 * 	in-place排序（即只需乃至O(1)的额外空间的排序），因而在从后向前扫描过程中，需要反复把已排序元素逐步往后
 *  挪位，为最新元素提供插入空间。
 *  
 *  具体算法描述如下：
 *  <1>.从第一个元素开始，该元素可以认为已经被排序；
 *  <2>.取出下一个元素，在已经排序的元素序列中从后向前扫描；
 *  <3>.如果该元素（已排序）大于新元素，将该元素移到下一位置；
 *  <4>.重复步骤3，直到找到已排序的元素小于或者等于新元素的位置；
 *  <5>.将新元素插入到该位置后；
 *  <6>.重复步骤2~5。
 * 
 * @author liujianzhu
 * @date 2017年7月20日 下午7:13:03
 */
public class InsertionSort {
	
	public static <T extends Object & Comparable<T>> void insertionSort(T[] arr) {
		long beginTime = System.nanoTime();
		
		for(int i = 1, len = arr.length; i < len; i++) {
			T key = arr[i];
			int j = i - 1;
			while(j >= 0 && arr[j].compareTo(key) > 0) {
				arr[j + 1] = arr[j];
				j --;
			}
			arr[j + 1] = key;
		}
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	/**
	 * 改进：查找插入位置时使用二分查找方式
	 * @param arr
	 */
	public static <T extends Object & Comparable<T>> void insertionSort2(T[] arr) {
		long beginTime = System.nanoTime();
		
		for(int i = 1, len = arr.length; i < len; i++) {
			T key = arr[i];
			int left = 0, right = i - 1;
			while(left <= right) {
				int mid = (left + right) >> 1; //中间点
				if(key.compareTo(arr[mid]) < 0) {
					right = mid - 1;
				} else {
					left = mid + 1;
				}
			}
			
			for(int j = i - 1; j >= left; j--) {
				arr[j + 1] = arr[j];
			}
			arr[left] = key;
		}
		
		System.out.println("改进后排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	//
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		InsertionSort.insertionSort(arr);
		printArray(arr);
		
		//改进后算法
		arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		InsertionSort.insertionSort2(arr);
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
