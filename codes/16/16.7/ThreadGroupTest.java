

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
class MyThread extends Thread
{
	// 提供指定執行緒名的建構子
	public MyThread(String name)
	{
		super(name);
	}
	// 提供指定執行緒名、執行緒群組的建構子
	public MyThread(ThreadGroup group , String name)
	{
		super(group, name);
	}
	public void run()
	{
		for (int i = 0; i < 20 ; i++ )
		{
			System.out.println(getName() + " 執行緒的i變數" + i);
		}
	}
}
public class ThreadGroupTest
{
	public static void main(String[] args)
	{
		// 獲取主執行緒所在的執行緒群組，這是所有執行緒預設的執行緒群組
		ThreadGroup mainGroup = Thread.currentThread().getThreadGroup();
		System.out.println("主執行緒群組的名字："
			+ mainGroup.getName());
		System.out.println("主執行緒群組是否是背景執行緒群組："
			+ mainGroup.isDaemon());
		new MyThread("主執行緒群組的執行緒").start();
		ThreadGroup tg = new ThreadGroup("新執行緒群組");
		tg.setDaemon(true);
		System.out.println("tg執行緒群組是否是背景執行緒群組："
			+ tg.isDaemon());
		MyThread tt = new MyThread(tg , "tg群組的執行緒甲");
		tt.start();
		new MyThread(tg , "tg群組的執行緒乙").start();
	}
}

