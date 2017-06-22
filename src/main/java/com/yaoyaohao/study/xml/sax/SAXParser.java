package com.yaoyaohao.study.xml.sax;

import java.io.IOException;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

/**
 * 与DOM建立树形结构的方式不同，SAX采用事件模型来解析XML文档，是解析XML文档的一种更快速、更轻量的方法。采用SAX可以
 * 对XML文档进行有选择的解析和访问，而不必像DOM那样加载整个文档，因为它对内存要求较低。但SAX对XML文档解析为一次性读取，
 * 不创建任何文档对象，很难同时访问文档中的多处数据。
 * 
 * SAX解析器接口和事件处理器接口定义在org.xml.sax包中。主要的接口包括ContentHandler、DTDHandler、EntityResolver及ErrorHandler。
 * 其中ContentHandler是主要的处理器接口，用于处理基本的文档解析事件；DTDHandler和EntityResolver接口用于处理DTD验证和实体解析相关的事件；
 * ErrorHandler是基本的错误处理接口。DefaultHandler类实现了上述四个事件处理接口。
 * 
 * 
 * SAX解析XML样例
 * 
 * @author liujianzhu
 * @date 2017年6月22日 上午11:35:04
 */
public class SAXParser {
	public static void main(String[] args) throws SAXException, IOException{
		XMLReader parser = XMLReaderFactory.createXMLReader();
		BookHandler bookHandler = new BookHandler();
		parser.setContentHandler(bookHandler);
		parser.parse(SAXParser.class.getResource("/books.xml").getPath());
		//
		System.out.println("共有" + bookHandler.getBookList().size() + "本书");
		for(Book book : bookHandler.getBookList()) {
			System.out.println(book);
		}
	}
}
