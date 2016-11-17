package com.yaoyaohao.study.spring.rw.ard;

/**
 * 通过ThreadLocal变量保存当前线程对应数据源是哪种：读或写，默认是写
 * 
 * @author liujianzhu
 * @date 2016年4月28日 下午12:08:08
 *
 */
public class DataSourceTypeHolder {
	private static final ThreadLocal<DataSourceType> holder = new ThreadLocal<DataSourceType>(){
		//默认走写库
		protected DataSourceType initialValue() {
			return DataSourceType.WRITE;
		};
	};
	
	public static DataSourceType get(){
		return holder.get();
	}
	
	public static void set(DataSourceType dataSourceType) {
		holder.set(dataSourceType);
	}
	
	public static void reset(){
		holder.set(DataSourceType.WRITE);
	}
}
