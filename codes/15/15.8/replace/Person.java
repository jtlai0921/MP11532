
import java.util.*;
import java.io.*;
/**
 * Description:
 * <br/>網站: <a href="http://www.crazyit.org">瘋狂Java聯盟</a>
 * <br/>Copyright (C), 2001-2016, Yeeku.H.Lee
 * <br/>This program is protected by copyright laws.
 * <br/>Program Name:
 * <br/>Date:
 * @author Yeeku.H.Lee kongyeeku@163.com
 * @version 1.0
 */
public class Person
	implements java.io.Serializable
{
	private String name;
	private int age;
	// 注意此處沒有提供無參數的建構子!
	public Person(String name , int age)
	{
		System.out.println("有參數的建構子");
		this.name = name;
		this.age = age;
	}
	// 省略name與age的setter和getter方法

	// name的setter和getter方法
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}

	// age的setter和getter方法
	public void setAge(int age)
	{
		this.age = age;
	}
	public int getAge()
	{
		return this.age;
	}

	//	覆寫writeReplace方法，程式在序列化該物件之前，先呼叫該方法
	private Object writeReplace()throws ObjectStreamException
	{
		ArrayList<Object> list = new ArrayList<>();
		list.add(name);
		list.add(age);
		return list;
	}
}