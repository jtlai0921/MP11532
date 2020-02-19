

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
class Account
{
	/* 定義一個ThreadLocal類型的變數，該變數將是一個執行緒局部變數
	每個執行緒都會保留該變數的一個副本 */
	private ThreadLocal<String> name = new ThreadLocal<>();
	// 定義一個初始化name成員變數的建構子
	public Account(String str)
	{
		this.name.set(str);
		// 下面程式碼用於存取當前執行緒的name副本的值
		System.out.println("---" + this.name.get());
	}
	// name的setter和getter方法
	public String getName()
	{
		return name.get();
	}
	public void setName(String str)
	{
		this.name.set(str);
	}
}
class MyTest extends Thread
{
	// 定義一個Account類型的成員變數
	private Account account;
	public MyTest(Account account, String name)
	{
		super(name);
		this.account = account;
	}
	public void run()
	{
		// 迴圈10次
		for (int i = 0 ; i < 10 ; i++)
		{
			// 當i == 6時輸出將帳號名替換成當前執行緒名
			if (i == 6)
			{
				account.setName(getName());
			}
			// 輸出同一個帳號的帳號名和迴圈變數
			System.out.println(account.getName()
				+ " 帳號的i值：" + i);
		}
	}
}
public class ThreadLocalTest
{
	public static void main(String[] args)
	{
		// 啟動兩條執行緒，兩條執行緒共享同一個Account
		Account at = new Account("初始名");
		/*
		雖然兩條執行緒共享同一個帳號，即只有一個帳號名
		但由於帳號名是ThreadLocal類型的，所以每條執行緒
		都完全擁有各自的帳號名副本，所以從i == 6之後，將看到兩條
		執行緒存取同一個帳號時看到不同的帳號名。
		*/
		new MyTest(at , "執行緒甲").start();
		new MyTest(at , "執行緒乙").start ();
	}
}
