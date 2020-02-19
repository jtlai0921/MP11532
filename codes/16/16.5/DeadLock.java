

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
class A
{
	public synchronized void foo( B b )
	{
		System.out.println("當前執行緒名: " + Thread.currentThread().getName()
			+ " 進入了A實例的foo()方法" );     // ①
		try
		{
			Thread.sleep(200);
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
		System.out.println("當前執行緒名: " + Thread.currentThread().getName()
			+ " 企圖呼叫B實例的last()方法");    // ③
		b.last();
	}
	public synchronized void last()
	{
		System.out.println("進入了A類別的last()方法內部");
	}
}
class B
{
	public synchronized void bar( A a )
	{
		System.out.println("當前執行緒名: " + Thread.currentThread().getName()
			+ " 進入了B實例的bar()方法" );   // ②
		try
		{
			Thread.sleep(200);
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
		System.out.println("當前執行緒名: " + Thread.currentThread().getName()
			+ " 企圖呼叫A實例的last()方法");  // ④
		a.last();
	}
	public synchronized void last()
	{
		System.out.println("進入了B類別的last()方法內部");
	}
}
public class DeadLock implements Runnable
{
	A a = new A();
	B b = new B();
	public void init()
	{
		Thread.currentThread().setName("主執行緒");
		// 呼叫a物件的foo方法
		a.foo(b);
		System.out.println("進入了主執行緒之後");
	}
	public void run()
	{
		Thread.currentThread().setName("副執行緒");
		// 呼叫b物件的bar方法
		b.bar(a);
		System.out.println("進入了副執行緒之後");
	}
	public static void main(String[] args)
	{
		DeadLock dl = new DeadLock();
		// 以dl為target啟動新執行緒
		new Thread(dl).start();
		// 呼叫init()方法
		dl.init();
	}
}

