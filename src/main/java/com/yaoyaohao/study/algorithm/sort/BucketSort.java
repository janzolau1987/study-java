package com.yaoyaohao.study.algorithm.sort;

/**
 * 桶排序:
 * 	桶排序是计数排序的升级版。
 *  工作原理：假设输入数据服从均匀分布，将数据分到有限数量的桶里，每个桶再分别排序。
 *  
 *  具体算法描述如下：
 *  <1>.设置一个定量的数组当作空桶；
 *  <2>.遍历输入数据，并且把数据一个一个放到对应的桶里去；
 *  <3>.对每个不是空的桶进行排序；
 *  <4>.从不是空的桶里把排好序的数据拼接起来。
 *  
 *  类似HashMap的实现
 * 
 * @author liujianzhu
 * @date 2017年7月21日 下午2:37:46
 */
public class BucketSort {
	public static void bucketSort(Integer[] arr) {
		long beginTime = System.nanoTime();
		
		int len = arr.length, 
				min = arr[0],
				max = min,
				space,
				BIT = 3,
				NUM = 1 << BIT,
				n = NUM - 1;
		Integer[][] buckets = new Integer[NUM][];
		
		for(int i = 1; i < len; i++) {
			min = min <= arr[i]? min : arr[i];
			max = max >= arr[i]? max : arr[i];
		}
		space = (max - min + 1) >> BIT;
		for(int j = 0; j < len; j++) {
			int index = Double.valueOf(Math.floor((arr[j] - min) / space)).intValue();
			if(index >= NUM) //如果出现超阈值则取半
				index = NUM - 1;
			if(buckets[index] != null) { //非空桶，比较插入
				int k = buckets[index].length - 1;
				while(k >= 0) {
					if(buckets[index][k] != null && buckets[index][k] > arr[j]) {
						buckets[index][k + 1] = buckets[index][k];
					}
					else if(buckets[index][k] != null && buckets[index][k] < arr[j]) {
						break;
					}
					k--;
				}
				buckets[index][k + 1] = arr[j];
			} 
			else { //空桶，初始化
				buckets[index] = new Integer[len];
				buckets[index][0] = arr[j];
			}
		}
		
		while(n >= 0) {
			if(buckets[n] != null) {
				for(int i = buckets[n].length - 1; i >= 0; i--) {
					if(buckets[n][i] != null) 
						arr[--len] = buckets[n][i];}
			}
			n --;
		}
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	//
	public static void main(String[] args) {
		Integer[] arr = new Integer[]{3,44,38,5,47,15,36,26,27,2,46,4,19,50,48};
		BucketSort.bucketSort(arr);
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
