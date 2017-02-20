package com.yaoyaohao.study.pattern.creational.simplefactory.version03;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.*;
import org.w3c.dom.*;

import com.yaoyaohao.study.pattern.creational.simplefactory.version02.Chart;

public class NewChartFactory {
	private static final Map<String,String> chartMap = new HashMap<>();
	
	static {
		try{
			final String configPath = NewChartFactory.class.getResource("config.xml").getPath();
			//创建文档对象  
            DocumentBuilderFactory dFactory = DocumentBuilderFactory.newInstance();  
            DocumentBuilder builder = dFactory.newDocumentBuilder();  
            Document doc = builder.parse(new File(configPath));
            //获取包含图表类型的文本节点  
            NodeList nl = doc.getElementsByTagName("item");
            for (int i = 0; i < nl.getLength(); i++) {
            	Element element = (Element) nl.item(i);
            	Node typeNode = element.getElementsByTagName("type").item(0).getFirstChild();
            	Node classNode = element.getElementsByTagName("className").item(0).getFirstChild();
            	String chartType = typeNode.getNodeValue().trim();
            	String className = classNode.getNodeValue().trim();
            	//
            	chartMap.put(chartType, className);
            }
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Chart getChart(String type) {
		Chart chart = null;
		if(chartMap.containsKey(type)) {
			String className = chartMap.get(type);
			try {
				Class<?> clazz = Class.forName(className);
				chart = (Chart) clazz.newInstance();
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
		return chart;
	}
}
