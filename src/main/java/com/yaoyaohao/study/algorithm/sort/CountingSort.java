package com.yaoyaohao.study.algorithm.sort;

/**
 * 计数排序： 计数排序的核心在于将输入的数据转化为键存储在额外开辟的数组空间中。（要求输入的数据必须是有确定范围的整数）
 * 计数排序是一种稳定的排序算法。计数排序使用一个额外的数组C，其中第i个元素是待排序数组A中值等于i元素的个数。然后根据
 * 数组C来将A中的元素排到正确的位置。它只能对整数进行排序。
 * 
 * 具体算法描述如下： <1>. 找出待排序的数组中最大和最小的元素； <2>. 统计数组中每个值为i的元素出现的次数，存入数组C的第i项； <3>.
 * 对所有的计数累加（从C中的第一个元素开始，每一项和前一项相加）； <4>.
 * 反向填充目标数组：将每个元素i放在新数组的第C(i)项，每放一个元素就将C(i)减去1。
 * 
 * @author liujianzhu
 * @date 2017年7月21日 下午1:39:24
 */
public class CountingSort {

	public static void countingSort(Integer[] arr) {
		long beginTime = System.nanoTime();
		
		int len = arr.length, min = arr[0], max = min;
		// 1.找出最大/最小元素
		for (int i = 0; i < len; i++) {
			min = (min <= arr[i] ? min : arr[i]);
			max = (max >= arr[i] ? max : arr[i]);
		}
		// 2.构建新数组，统计数组中每个值为i的元素出现的次数，存入数组C的第i项
		Integer[] C = new Integer[max - min + 1];
		for (int i = 0; i < len; i++) {
			int index = arr[i] - min;
			C[index] = C[index] != null ? C[index] + 1 : 1;
		}
		// 3.反向填充
		for (int k = (max - min); k >= 0; k--) {
			while (C[k] != null && C[k] != 0) {
				arr[--len] = k + min;
				C[k]--;
			}
		}
		
		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}

	public static void main(String[] args) {
		Integer[] arr = new Integer[] { 2, 2, 3, 8, 7, 1, 2, 2, 2, 7, 3, 9, 8, 2, 1, 4, 2, 4, 6, 9, 2 };
		CountingSort.countingSort(arr);
		printArray(arr);
	}

	private static <T> void printArray(T[] t) {
		StringBuilder sb = new StringBuilder("[");
		for (int i = 0, len = t.length; i < len; i++) {
			if (i == 0)
				sb.append(t[i]);
			else
				sb.append("," + t[i]);
		}
		sb.append("]");
		System.out.println(sb.toString());
	}
}
