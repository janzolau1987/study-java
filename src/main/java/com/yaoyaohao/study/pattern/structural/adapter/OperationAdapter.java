package com.yaoyaohao.study.pattern.structural.adapter;

//操作适配器：适配器
public class OperationAdapter implements ScoreOperation {
	private QuickSort sortObj;//定义适配者QuickSort对象
	private BinarySearch searchObj;//定义适配者BinarySearch对象
	
	public OperationAdapter() {
		sortObj = new QuickSort();
		searchObj = new BinarySearch();
	}

	@Override
	public int[] sort(int[] array) {
		//调用适配者类QuickSort的排序方法
		return sortObj.quickSort(array);
	}

	@Override
	public int search(int[] array, int key) {
		//调用适配者类BinarySearch的排序方法
		return searchObj.binarySearch(array, key);
	}

}
