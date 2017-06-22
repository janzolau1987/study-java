package com.yaoyaohao.study.xml.dom;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * dom4j解析xml样例
 * 
 * @author liujianzhu
 * @date 2017年6月22日 下午1:28:32
 */
public class DOM4JParser {
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		SAXReader reader = new SAXReader();
		try {
			String filepath = DOM4JParser.class.getResource("/books.xml").getPath();
			Document document = reader.read(new File(filepath));
			Element rootElement = document.getRootElement();
			Iterator<Element> iter = rootElement.elementIterator();
			int bookIndex = 1;
			while(iter.hasNext()) {
				Element bookElement = iter.next();
				System.out.println("------> " + bookElement.getName() + ":" + (bookIndex++));
				//获取book属性
				List<Attribute> attrs = bookElement.attributes();
				for(Attribute attr : attrs) {
					System.out.println("属性名:" + attr.getName() + ",属性值:" + attr.getValue());
				}
				//
				Iterator<Element> childIter = bookElement.elementIterator();
				while(childIter.hasNext()) {
					Element childElement = childIter.next();
					System.out.println("节点名:" + childElement.getName() + ",节点值:" + childElement.getStringValue());
				}
			}
		} catch(DocumentException e) {
			e.printStackTrace();
		}
	}
}
