package com.yaoyaohao.study.collection.map;

import java.util.EnumMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * A specialized Map implementation for use with enum type keys. All of the keys
 * in an enum map must come from a single enum type that is specified, explicitly or
 * implicitly, when the map is created. Enum maps are represented internally as arrays.
 * This representation is extremely compact and efficient.
 * 
 * @author liujianzhu
 * @date 2017年7月7日 下午4:13:05
 */
public class EnumMapDemo {
	public static void main(String[] args) {
		Map<Course, String> map = new EnumMap<>(Course.class);
		map.put(Course.ONE, "语文");
		map.put(Course.ONE, "政治");
		map.put(Course.TWO, "数学");
		map.put(Course.THREE, "英语");
		
		for(Entry<Course, String> entry : map.entrySet()) {
			System.out.println(entry.getKey() + ":" + entry.getValue());
		}
	}
}

enum Course {
	ONE,
	TWO,
	THREE,
	FOUR,
	FIVE;
}