
import java.lang.reflect.*;
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
class Person
{
	private String name;
	private int age;
	public String toString()
	{
		return "Person[name:" + name +
		" , age:" + age + " ]";
	}
}
public class FieldTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 建立一個Person物件
		Person p = new Person();
		// 獲取Person類別對應的Class物件
		Class<Person> personClazz = Person.class;
		// 獲取Person的名為name的成員變數
		// 使用getDeclaredField()方法表明可獲取各種存取控制詞的成員變數
		Field nameField = personClazz.getDeclaredField("name");
		// 設置通過反射存取該成員變數時取消存取權限檢查
		nameField.setAccessible(true);
		// 呼叫set()方法為p物件的name成員變數設置值
		nameField.set(p , "Yeeku.H.Lee");
		// 獲取Person類別名稱為age的成員變數
		Field ageField = personClazz.getDeclaredField("age");
		// 設置通過反射存取該成員變數時取消存取權限檢查
		ageField.setAccessible(true);
		// 呼叫setInt()方法為p物件的age成員變數設置值
		ageField.setInt(p , 30);
		System.out.println(p);
	}
}
