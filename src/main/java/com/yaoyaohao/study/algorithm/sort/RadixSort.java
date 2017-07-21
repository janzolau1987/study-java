package com.yaoyaohao.study.algorithm.sort;

import java.util.ArrayDeque;

/**
 * 基数排序:
 * 	基数排序是非比较的排序算法，对每一位进行排序，从最低位开始排序，复杂度为O(kn)，n为数组长度，k为数组中的数的最大位数。
 *  基数排序是按照低位先排序，然后收集；再按照高位排序，然后再收集；依次类推，直到最高位。有时候有些属性是有优先级顺序的，先按低优先级排序，
 *  再按高优先级排序。最后的次序就是高优先级高的在前，高优先级相同的低优先级高的在前。基数排序基于分别排序，分别收集，所以是稳定的。
 * 
 * 具体算法描述如下：
 * <1>.取得数组中的最大数，并取得位数；
 * <2>.arr为原始数组，从最低位开始取每个位组成radix数组；
 * <3>.对radix进行计数排序（利用计数排序适用于小范围数的特点）；
 * 
 * @author liujianzhu
 * @date 2017年7月21日 下午3:56:13
 */
public class RadixSort {
	public static void radixSort(Integer[] arr) {
		long beginTime = System.nanoTime();
		
		//1.计算出最大位数
		int maxDigit = 1;
		for(int i : arr) {
			int len = String.valueOf(i).length();
			maxDigit = maxDigit > len? maxDigit : len;
		}
		
		//2.
		int mod = 10, dev = 1;
		@SuppressWarnings("unchecked")
		ArrayDeque<Integer>[] counter = new ArrayDeque[arr.length];
		for(int i = 0; i < maxDigit; i++, dev *= 10, mod *= 10) {
			for(int j = 0; j < arr.length; j++) {
				int bucket = Double.valueOf((arr[j] % mod) / dev).intValue();
				if(counter[bucket] == null) {
					counter[bucket] = new ArrayDeque<>();
				}
				counter[bucket].push(arr[j]);
			}
			int pos = 0;
			for(int j = 0; j < counter.length; j++) {
				Integer value = null;
				if(counter[j] != null) {
					while((value = counter[j].pollLast()) != null) {
						arr[pos ++] = value;
					}
				}
			}
		}
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	//
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		RadixSort.radixSort(arr);
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
