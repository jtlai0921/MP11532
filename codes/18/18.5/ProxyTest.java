
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
interface Person
{
	void walk();
	void sayHello(String name);
}
class MyInvokationHandler implements InvocationHandler
{
	/*
	執行動態代理物件的所有方法時，都會被替換成執行如下的invoke方法
	其中：
	proxy：代表動態代理物件
	method：代表正在執行的方法
	args：代表呼叫目標方法時傳入的實際參數。
	*/
	public Object invoke(Object proxy, Method method, Object[] args)
	{
		System.out.println("----正在執行的方法:" + method);
		if (args != null)
		{
			System.out.println("下面是執行該方法時傳入的實際參數為：");
			for (Object val : args)
			{
				System.out.println(val);
			}
		}
		else
		{
			System.out.println("呼叫該方法沒有實際參數！");
		}
		return null;
	}
}
public class ProxyTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 建立一個InvocationHandler物件
		InvocationHandler handler = new MyInvokationHandler();
		// 使用指定的InvocationHandler來產生一個動態代理物件
		Person p = (Person)Proxy.newProxyInstance(Person.class.getClassLoader()
			, new Class[]{Person.class}, handler);
		// 呼叫動態代理物件的walk()和sayHello()方法
		p.walk();
		p.sayHello("孫悟空");
	}
}
