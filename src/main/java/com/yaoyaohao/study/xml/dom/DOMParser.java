package com.yaoyaohao.study.xml.dom;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Java中DOM接口简介： 
 * JDK中的DOM API遵循W3C DOM规范，其中org.w3c.dom包提供了Document、DocumentType、Node、NodeList、Element等接口，
 * 这些接口均是访问DOM文档所必须的。可以利用这些接口创建、遍历、修改DOM文档。
 * javax.xml.parsers包中的DocumentBuilder和DocumentBuilderFactory用于解析XML文档生成对应的 DOM Document对象。
 * javax.xml.transform.dom和javax.xml.transform.stream包中的DOMSource类和StreamSource类，用于将更新后的DOM文档写入XML文件。
 * 
 * 运用DOM解析XML样例
 * 
 * @author liujianzhu
 * @date 2017年6月22日 上午10:28:22
 */
public class DOMParser {
	DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();

	// Load and parse XML file into DOM
	public Document parse(String filePath) {
		Document document = null;
		try {
			// DOM parser instance
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			// parse an xml file into a DOM tree
			document = builder.parse(new File(filePath));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return document;
	}

	public static void main(String[] args) {
		DOMParser parser = new DOMParser();
		Document document = parser.parse(DOMParser.class.getResource("/books.xml").getPath());
		// get root element
		Element rootElement = document.getDocumentElement();
		// traverse child elements
		NodeList nodeList = rootElement.getElementsByTagName("book");
		if (nodeList != null) {
			System.out.println("一共有 " + nodeList.getLength() + " 本书");
			for (int i = 0; i < nodeList.getLength(); i++) {
				System.out.println("开始遍历第" + (i + 1) + "本书");
				Node bookNode = nodeList.item(i);
				// 获取book节点的所有属性集合
				NamedNodeMap attrs = bookNode.getAttributes();
				System.out.println("第 " + (i + 1) + "本书共有" + attrs.getLength() + "个属性");
				for (int j = 0; j < attrs.getLength(); j++) {
					Node attr = attrs.item(j);
					System.out.println("属性名:" + attr.getNodeName() + ", 属性值:" + attr.getNodeValue());
				}
				// 遍历book节点的子节点
				NodeList childNodes = bookNode.getChildNodes();
				System.out.println("第" + (i + 1) + "本书共有" + childNodes.getLength() + "个子节点");
				//
				for (int k = 0; k < childNodes.getLength(); k++) {
					Node childNode = childNodes.item(k);
					if (childNode.getNodeType() == Node.ELEMENT_NODE) {
						// 获取了element类型节点的节点名
						System.out.print("第" + (k + 1) + "个节点的节点名：" + childNode.getNodeName());
						// 获取了element类型节点的节点值
						System.out.println("--节点值是：" + childNode.getFirstChild().getNodeValue());
					}
				}
				System.out.println("======================结束遍历第" + (i + 1) + "本书的内容=========");
			}
		}
	}
}
