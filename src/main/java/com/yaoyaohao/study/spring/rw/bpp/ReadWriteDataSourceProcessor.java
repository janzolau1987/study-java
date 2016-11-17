package com.yaoyaohao.study.spring.rw.bpp;

import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.TypedStringValue;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.interceptor.RuleBasedTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

/**
 * 此类实现读、写数据库选择处理器
 * 1、首先读取<tx:advice>事务属性配置
 * 
 * 2、对于所有读方法设置 read-only="true" 表示读取操作（以此来判断是选择读还是写库），其他操作都是走写库
 *    如<tx:method name="×××" read-only="true"/>
 *    
 * 3、 forceChoiceReadOnWrite用于确定在如果目前是写（即开启了事务），下一步如果是读，
 *    是直接参与到写库进行读，还是强制从读库读<br/>
 *      forceChoiceReadOnWrite:true 表示目前是写，下一步如果是读，强制参与到写事务（即从写库读）
 *                                  这样可以避免写的时候从读库读不到数据
 *                                  
 *                                  通过设置事务传播行为：SUPPORTS实现
 *                                  
 *      forceChoiceReadOnWrite:false 表示不管当前事务是写/读，都强制从读库获取数据
 *                                  通过设置事务传播行为：NOT_SUPPORTS实现（连接是尽快释放）                
 *                                  『此处借助了 NOT_SUPPORTS会挂起之前的事务进行操作 然后再恢复之前事务完成的』
 * 4、配置方式
 *  <bean id="readWriteDataSourceTransactionProcessor" class="cn.javass.common.datasource.ReadWriteDataSourceProcessor">
 *      <property name="forceChoiceReadWhenWrite" value="false"/>
 *  </bean>
 *
 * 5、目前只适用于<tx:advice>情况
 * 
 * 参照：http://jinnianshilongnian.iteye.com/blog/1720618
 *
 */
@SuppressWarnings("unchecked")
public class ReadWriteDataSourceProcessor implements BeanFactoryPostProcessor {
	private static final Logger log = LoggerFactory.getLogger(ReadWriteDataSourceProcessor.class);
	
	private boolean forceReadWhenWrite = false;
	
	/**
     * 当之前操作是写的时候，是否强制从从库读
     * 默认（false） 当之前操作是写，默认强制从写库读
     * @param forceReadOnWrite
     */
	public void setForceReadWhenWrite(boolean forceReadWhenWrite) {
		this.forceReadWhenWrite = forceReadWhenWrite;
	}

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		try{
			String[] beanNames = beanFactory.getBeanNamesForType(TransactionInterceptor.class);
			for(String beanName : beanNames) {
				BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
				MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
				RootBeanDefinition txAdviceDefinition = (RootBeanDefinition)propertyValues.get("transactionAttributeSource");
				//
				Map<TypedStringValue,TransactionAttribute> nameMaps = (Map<TypedStringValue,TransactionAttribute>) txAdviceDefinition.getPropertyValues().get("nameMap");
				for(Entry<TypedStringValue,TransactionAttribute> entry : nameMaps.entrySet()) {
					RuleBasedTransactionAttribute attr = (RuleBasedTransactionAttribute)entry.getValue();
	                String methodName = entry.getKey().getValue();
	                doReadOnly(methodName, attr);
				}
			}
		}catch(Exception e){
			throw new ReadWriteDataSourceException("process read/write transaction error @ method[postProcessBeanFactory]", e);
		}
	}
	
	private void doReadOnly(String methodName,RuleBasedTransactionAttribute attr) {
		//仅对read-only的处理
		if(!attr.isReadOnly())
			return;
		Boolean isForceRead = Boolean.FALSE;
        if(forceReadWhenWrite) {
            //不管之前操作是写，默认强制从读库读 （设置为NOT_SUPPORTED即可）
            //NOT_SUPPORTED会挂起之前的事务
            attr.setPropagationBehavior(Propagation.NOT_SUPPORTED.value());
            isForceRead = Boolean.TRUE;
        } else {
            //否则 设置为SUPPORTS（这样可以参与到写事务）
            attr.setPropagationBehavior(Propagation.SUPPORTS.value());
        }
        log.debug("read/write transaction process  method:{} force read:{}", methodName, isForceRead);
        ReadWriteDataSourceDecision.markForceRead(methodName, isForceRead);
	}

}
