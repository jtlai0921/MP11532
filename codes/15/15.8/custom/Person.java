
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

	private void writeObject(java.io.ObjectOutputStream out)
		throws IOException
	{
		// 將name實例變數的值反轉後寫入二進位串流
		out.writeObject(new StringBuffer(name).reverse());
		out.writeInt(age);
	}
	private void readObject(java.io.ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		// 將讀取的字串反轉後賦給name實例變數
		this.name = ((StringBuffer)in.readObject()).reverse()
			.toString();
		this.age = in.readInt();
	}
}