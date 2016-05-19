package com.yaoyaohao.study.spring;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * spring bean工厂实现
 * 
 * @author liujianzhu
 * @date 2016年5月19日 下午4:51:32
 *
 */
public class BeanFactory {
	private Map<String, Object> beanMap = new HashMap<String, Object>();
	
	private String configPath;
	
	public BeanFactory(String configPath){
		this.configPath = configPath;
		try {
			init();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private void init() throws Exception{
		SAXReader reader = new SAXReader();
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.configPath);
		//
		Document document = reader.read(inputStream);
		//获取根节点
		Element root = document.getRootElement();
		//遍历bean节点
		Element ele;
		Iterator<Element> iter = root.elementIterator("bean");
		while(iter.hasNext()) {
			ele = iter.next();
			//获取属性结点
			Attribute id = ele.attribute("id");
			Attribute cls = ele.attribute("class");
			Class<?> bean = Class.forName(cls.getText());
			Object obj = bean.newInstance();
			//设置对应属性
			BeanInfo info = Introspector.getBeanInfo(bean);
			PropertyDescriptor[] properties = info.getPropertyDescriptors();
			Method setMethod = null;
			
			Iterator<Element> propIter = ele.elementIterator("property");
			while(propIter.hasNext()) {
				Element propEle = propIter.next();
				String name = propEle.attributeValue("name");
				String value = propEle.attributeValue("value");
				for(PropertyDescriptor pd : properties) {
					if(pd.getName().equalsIgnoreCase(name)) {
						setMethod = pd.getWriteMethod();
						setMethod.invoke(obj, value);
					}
				}
			}
			//
			beanMap.put(id.getText(), obj);
		}
	}
	
	public Object getBean(String beanName) {
		Object obj = beanMap.get(beanName);
		return obj;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T getBean(String beanName , Class<T> cls) {
		return (T) beanMap.get(beanName);
	}
}
