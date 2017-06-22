package com.yaoyaohao.study.xml.sax;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class BookHandler extends DefaultHandler {
	private String value = null;
	private Book book = null;
	int bookIndex = 0;

	private List<Book> bookList = new ArrayList<>();

	public List<Book> getBookList() {
		return this.bookList;
	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		super.characters(ch, start, length);
		value = new String(ch, start, length);
		if(!"".equals(value.trim())) {
			System.out.println("节点值: " + value);
		}
	}

	@Override
	public void endDocument() throws SAXException {
		super.endDocument();
		System.out.println("SAX解析结束");
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		super.endElement(uri, localName, qName);
		if("book".equals(qName)) {
			bookList.add(book);
			book = null;
		}
		else if("title".equals(qName)) {
			book.setTitle(value);
		}
		else if("author".equals(qName)) {
			book.setAuthor(value);
		}
		else if("year".equals(qName)) {
			book.setYear(Integer.parseInt(value));
		}
		else if("price".equals(qName)) {
			book.setPrice(Float.parseFloat(value));
		}
	}

	@Override
	public void startDocument() throws SAXException {
		super.startDocument();
		System.out.println("SAX解析开始");
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		super.startElement(uri, localName, qName, attributes);
		if ("book".equals(qName)) {
			bookIndex ++;
			book = new Book();
			for(int i = 0; i < attributes.getLength(); i++) {
				String attrQName = attributes.getQName(i);
				if("id".equals(attrQName)) {
					book.setId(attributes.getValue(i));
				}
			}
		} else {
			System.out.println("节点: uri=" + uri + ",localName=" + localName + ",qName=" + qName);
		}
	}
}
