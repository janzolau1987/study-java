package com.yaoyaohao.study.spring.rw.bpp;

import org.aspectj.lang.ProceedingJoinPoint;

/**
 * ★★通过AOP切面实现读/写库选择★★
 * 
 * 1、首先将当前方法 与 根据之前【读/写动态数据库选择处理器】  提取的读库方法 进行匹配
 * 
 * 2、如果匹配，说明是读取数据：
 *  2.1、如果forceChoiceReadOnWrite:true，即强制走读库
 *  2.2、如果之前是写操作且forceChoiceReadOnWrite:false，将从写库进行读取
 *  2.3、否则，到读库进行读取数据
 * 
 * 3、如果不匹配，说明默认将使用写库进行操作
 * 
 * 4、配置方式
 *      <aop:aspect order="-2147483648" ref="readWriteDataSourceTransactionProcessor">
 *          <aop:around pointcut-ref="txPointcut" method="determineReadOrWriteDB"/>
 *      </aop:aspect>
 *  4.1、此处order = Integer.MIN_VALUE 即最高的优先级
 *  4.2、切入点：txPointcut 和 实施事务的切入点一样
 *  4.3、determineReadOrWrite方法用于决策是走读/写库的
 *  
 * 	参照：http://jinnianshilongnian.iteye.com/blog/1720618
 */
public class ReadWriteDataSourceAspect {
	
	public Object determineReadOrWrite(ProceedingJoinPoint pjp) throws Throwable {
		boolean isRead = ReadWriteDataSourceDecision.matchMethod(pjp.getSignature().getName());
		if(isRead) {
			ReadWriteDataSourceDecision.markRead();
		}
		else{
			ReadWriteDataSourceDecision.markWrite();
		}
		try{
			return pjp.proceed();
		}finally{
			ReadWriteDataSourceDecision.reset();
		}
	}
}
