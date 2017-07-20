package com.yaoyaohao.study.algorithm.sort;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 归并排序：
 * 	和选择排序一样，归并排序的性能不受输入数据的影响，但表现比选择排序好的多，因为始终都是O(n log n）的时间复杂度。
 * 	代价是需要额外的内存空间。
 * 
 * 	归并排序是建立在归并操作上的一种有效的排序算法。该算法是采用分治法(Divide and Conquer)的一个非常典型的应用。归并排序
 *  是一种稳定的排序方法。将已有序的子序列合并，得到完全有序的序列；即先使用每个子序列有序，再使子序列间有序。若将两个有序集合并成一个
 *  有序集，称为2-路归并。
 *  
 *  具体算法描述如下：
 *  <1>.把长度为n的输入序列分成两个长度为n/2的子序列；
 *  <2>.对这两个子序列分别采用归并排序；
 *  <3>.将两个排序好的子序列合并成一个最终的排序序列。
 * 
 * @author liujianzhu
 * @date 2017年7月20日 下午7:54:40
 */
public class MergeSort {
	public static <T extends Object & Comparable<T>> T[] mergeSort(T[] arr) {
		int len = arr.length;
		if(len < 2) 
			return arr;
		int mid = len >> 1;
		T[] left = Arrays.copyOfRange(arr, 0, mid),
			right = Arrays.copyOfRange(arr, mid, len);
		T[] result = merge(mergeSort(left), mergeSort(right));
		return result;
	}
	
	//归并排序核心代码
	@SuppressWarnings("unchecked")
	private static <T extends Object & Comparable<T>> T[] merge(T[] left, T[] right) {
		//根据数组类型及总长度，创建新数组
		Class<?> newType = left.getClass();
		int newLength = left.length + right.length;
		T[] result = ((Object)newType == (Object)Object[].class)? (T[]) new Object[newLength]
	                    : (T[]) Array.newInstance(left.getClass().getComponentType(), newLength);
		
		int i = 0, li = 0, ri = 0;
		while(li < left.length && ri < right.length) {
			if(left[li].compareTo(right[ri]) <= 0) {
				result[i++] = left[li++];
			} else {
				result[i++] = right[ri++];
			}
		}
		
		while(li < left.length) {
			result[i++] = left[li++];
		}
		
		while(ri < right.length) {
			result[i++] = right[ri++];
		}
		
		return result;
	}
	
	//
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		
		long beginTime = System.nanoTime();
		
		Integer[] result = MergeSort.mergeSort(arr);
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
		printArray(result);
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
