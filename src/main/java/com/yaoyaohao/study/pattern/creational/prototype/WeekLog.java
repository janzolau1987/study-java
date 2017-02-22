package com.yaoyaohao.study.pattern.creational.prototype;

/**
 * 工作周报WeeklyLog：具体原型类
 * 
 * @author liujianzhu
 * @date 2017年2月21日 下午7:36:11
 */
public class WeekLog implements Cloneable {
	private String name;
	private String date;
	private String content;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public WeekLog clone() {
		Object obj = null;
		try{
			obj = super.clone();
			return (WeekLog) obj;
		} catch(CloneNotSupportedException e) {
			System.out.println("不支持复制, 原因：" + e.getMessage());
			return null;
		}
	}
}
