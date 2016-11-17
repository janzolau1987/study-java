package com.yaoyaohao.study.spring.rw.ard;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 增加切面实现动态切数据源
 * NOTE: @Order(0) 此注解必须有，从而保证数据源切换在事务之前执行
 * 
 * @author liujianzhu
 * @date 2016年4月28日 下午2:26:29
 *
 */
@Aspect
@Component
@Order(-1)
public class DataSourceAspect {
	@Pointcut("(execution(* com.yaoyaohao.study.rw.service.*.select*(..))) "
			+ "|| (execution(* com.yaoyaohao.study.rw.service.*.find*(..)))"
			+ "|| (execution(* com.yaoyaohao.study.rw.service.*.get*(..)))")
	public void pointcut(){}
	
	@Around("pointcut()")
	public Object around(ProceedingJoinPoint pjp) throws Throwable{
			DataSourceTypeHolder.set(DataSourceType.READ);
			System.out.println("数据源切换到读库");
			//
			Object retVal = pjp.proceed();
			//
			DataSourceTypeHolder.reset();
			System.out.println("数据源切换重置");
			return retVal;
	}
}
