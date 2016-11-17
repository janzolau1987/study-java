package com.yaoyaohao.study.spring.rw.ard;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 通过继承Spring类AbstractRoutingDataSource实现方法determineCurrentLookupKey来确定走的是读库或写库
 * 
 * @author liujianzhu
 * @date 2016年4月28日 下午1:56:14
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DataSourceTypeHolder.get();
	}

}
