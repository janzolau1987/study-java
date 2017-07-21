package com.yaoyaohao.study.algorithm.sort;

/**
 * 堆排序: 利用堆这种数据结构所设计的一种排序算法。堆积是一个近似完全二叉树的结构，并同时满足堆积的性质：即子结点的键值或
 * 索引总是小于（或者）大于它的父节点。
 * 
 * 具体算法描述如下： <1>.将初始待排序关键字序列(R1,R2….Rn)构建成大顶堆，此堆为初始的无序区；
 * <2>.将堆顶元素R[1]与最后一个元素R[n]交换，此时得到新的无序区(R1,R2,……Rn-1)和新的有序区(Rn),且满
 * 足R[1,2…n-1]<=R[n]；
 * <3>.由于交换后新的堆顶R[1]可能违反堆的性质，因此需要对当前无序区(R1,R2,……Rn-1)调整为新堆，然后再次将
 * R[1]与无序区最后一个元素交换，得到新的无序区(R1,R2….Rn-2)和新的有序区(Rn-1,Rn)。不断重复此过程直到
 * 有序区的元素个数为n-1，则整个排序过程完成。
 * 
 * @author liujianzhu
 * @date 2017年7月21日 上午11:41:56
 */
public class HeapSort {
	
	public static <T extends Object & Comparable<T>> void heapSort(T[] arr) {
		long beginTime = System.nanoTime();
		
		//建堆
		int heapSize = arr.length;
		T temp;
		for(int i = heapSize >> 1; i >= 0; i--) {
			heapify(arr, i, heapSize);
		}
		
		//堆排序
		for(int j = heapSize - 1; j >= 1; j--) {
			temp = arr[0];
			arr[0] = arr[j];
			arr[j] = temp;
			heapify(arr, 0, --heapSize);
		}

		System.out.println("排序算法耗时: " + (System.nanoTime() - beginTime) + " ns");
	}
	
	/**
	 * 维护堆的性质
	 * @param arr	数组
	 * @param x		数组下标
	 * @param len	堆大小
	 */
	private static <T extends Object & Comparable<T>> void heapify(T[] arr, int x, int len) {
		int l = (x << 1) + 1,	//左子节点下标 = x * 2 + 1
			r = (x << 1) + 2,	//右子节点下标 = x * 2 + 2
			largest = x;
		T temp;
		if(l < len && arr[l].compareTo(arr[largest]) > 0) {
			largest = l;
		}
		if(r < len && arr[r].compareTo(arr[largest]) > 0) {
			largest = r;
		}
		if(largest != x) {
			temp = arr[x];
			arr[x] = arr[largest];
			arr[largest] = temp;
			//重构建堆
			heapify(arr, largest, len);
		}
	}

	public static void main(String[] args) {
		Integer[] arr = new Integer[] { 3, 44, 38, 5, 47, 15, 36, 26, 27, 2, 46, 4, 19, 50, 48 };
		HeapSort.heapSort(arr);
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
