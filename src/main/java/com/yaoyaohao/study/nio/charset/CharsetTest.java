package com.yaoyaohao.study.nio.charset;

import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.SortedMap;

public class CharsetTest {

	public static void main(String[] args) {
		//遍历出所有可用charset
		SortedMap<String, Charset> availableCharsets = Charset.availableCharsets();
		Iterator<String> iter = availableCharsets.keySet().iterator();
		while(iter.hasNext()) {
			String key = iter.next();
			Charset c = availableCharsets.get(key);
			System.out.println( key + " : " + c.name());
		}
	}

}
