package com.yaoyaohao.study.xml.dom;

import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * 基于xpath解析xml
 * 
 * @author liujianzhu
 * @date 2017年6月22日 下午2:10:37
 */
public class XPathExample {
	public static void main(String[] args) throws Exception {
		String filepath = XPathExample.class.getResource("/books.xml").getPath();
		
		DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
		domFactory.setNamespaceAware(false);
		domFactory.setValidating(false);
		
		DocumentBuilder builder = domFactory.newDocumentBuilder();
		builder.setEntityResolver(new EntityResolver() {
			@Override
			public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
				//TODO 自定义实现对xml实体验证
				return null;
			}
		});
		builder.setErrorHandler(new ErrorHandler() {
			@Override
			public void warning(SAXParseException exception) throws SAXException {
				//警告不处理
			}
			
			@Override
			public void fatalError(SAXParseException exception) throws SAXException {
				//直接抛异常
				throw exception;
			}
			
			@Override
			public void error(SAXParseException exception) throws SAXException {
				throw exception;
			}
		});
		Document doc = builder.parse(filepath);
		
		XPathFactory factory = XPathFactory.newInstance();
		XPath xpath = factory.newXPath();
		
		XPathExpression expr = xpath.compile("//book[author='J. Doung']/title/text()");
		Object result = expr.evaluate(doc, XPathConstants.NODESET);
		NodeList nodes = (NodeList) result;
		for(int i = 0; i < nodes.getLength(); i++) {
			System.out.println(nodes.item(i).getNodeValue());
		}
	}
}
