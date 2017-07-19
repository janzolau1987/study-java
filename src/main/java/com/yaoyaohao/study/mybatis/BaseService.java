package com.yaoyaohao.study.mybatis;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

public class BaseService {
	protected static SqlSessionFactory sqlSessionFactory;
	private final static String resourcePath = "mybatis/mybatis-config.xml";
	
	static {
		//基于XML格式配置
		//initSqlSessionFactoryFromXML();
		//java代码方式创建
		initSqlSessionFactoryFromCode();
	}
	
	private static void initSqlSessionFactoryFromXML() {
		try{
			InputStream inputStream = Resources.getResourceAsStream(resourcePath);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private static void initSqlSessionFactoryFromCode() {
		//构建数据库连接池
		PooledDataSource dataSource = new PooledDataSource();
		dataSource.setDriver("com.mysql.jdbc.Driver");
		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8");
		dataSource.setUsername("root");
		dataSource.setPassword("lc123456");
		//构建数据库事务方式
		TransactionFactory transactionFactory = new JdbcTransactionFactory();
		//创建数据库运行环境
		Environment environment = new Environment("development", transactionFactory, dataSource);
		//构建Configuration对象
		Configuration configuration = new Configuration(environment);
		//注册别名
		configuration.getTypeAliasRegistry().registerAlias("Student", Student.class);
		//加入映射器
		configuration.addMapper(StudentMapper.class);
		//加入自定义插件
		configuration.addInterceptor(new MyPlugin());
		
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
	}
	
	protected static SqlSession openSession() {
		return sqlSessionFactory.openSession();
	}
}
