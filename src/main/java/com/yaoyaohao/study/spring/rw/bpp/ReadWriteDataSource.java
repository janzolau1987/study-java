package com.yaoyaohao.study.spring.rw.bpp;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.jdbc.datasource.AbstractDataSource;
import org.springframework.util.CollectionUtils;

/**
 * 读写分离动态选择数据源实现
 * 暂实现：一写多读
 * 	当写时默认读操作到写库
 * 	当写时强制读操作到读库
 * 
 * 参照：http://jinnianshilongnian.iteye.com/blog/1720618
 * 
 */
public class ReadWriteDataSource  extends AbstractDataSource implements InitializingBean {
	private static final Logger log = LoggerFactory.getLogger(ReadWriteDataSource.class);
	
	/**
	 * 写库数据源
	 */
	private DataSource writeDataSource;
	
	/**
	 * 多读库数据源
	 */
	private String[] readDataSourceNames;
    private DataSource[] readDataSources;
    private int readDataSourceCount;

    private AtomicInteger counter = new AtomicInteger(1);
	
	public void setWriteDataSource(DataSource writeDataSource) {
		if(writeDataSource == null) {
            throw new IllegalArgumentException("property 'writeDataSource' is required");
        }
		this.writeDataSource = writeDataSource;
	}

	public void setReadDataSourceMap(Map<String, DataSource> map) {
		if(CollectionUtils.isEmpty(map)) {
            throw new IllegalArgumentException("property 'readDataSourceMap' is required");
        }
		this.readDataSourceCount = map.size();
        this.readDataSources = new DataSource[readDataSourceCount];
        this.readDataSourceNames = new String[readDataSourceCount];
        //
        int i = 0;
        for(Entry<String, DataSource> e : map.entrySet()) {
            readDataSources[i] = e.getValue();
            readDataSourceNames[i] = e.getKey();
            i++;
        }
	}
	
	private DataSource determineDataSource(){
		if(ReadWriteDataSourceDecision.isWrite()) {
			log.debug("determine current dataSource to write dataSource , 【选择写库】 ");
			return this.writeDataSource;
		}
		if(ReadWriteDataSourceDecision.isNone()) {
			log.debug("Did not read/write dataSource decision,determine to write by default. 【默认写库】");
			return this.writeDataSource;
		}
		return determineReadDataSource(); 
	}
	
	private DataSource determineReadDataSource(){
		int index = counter.incrementAndGet() % readDataSourceCount;
        if(index < 0) {
            index = - index;
        }
        String dataSourceName = readDataSourceNames[index];
        
		log.debug("determine current dataSource to read dataSource : 【选择读库】  {}",dataSourceName);
		return readDataSources[index];
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		//
	}

	@Override
	public Connection getConnection() throws SQLException {
		return determineDataSource().getConnection();
	}

	@Override
	public Connection getConnection(String username, String password) throws SQLException {
		return determineDataSource().getConnection(username, password);
	}
}