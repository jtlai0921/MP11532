

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
// 通過實作Runnable介面來建立執行緒類別
public class SecondThread implements Runnable 
{
	private int i ;
	// run方法同樣是執行緒體
	public void run()
	{
		for ( ; i < 100 ; i++ )
		{
			// 當執行緒類別實作Runnable介面時，
			// 如果想獲取當前執行緒，只能用Thread.currentThread()方法。
			System.out.println(Thread.currentThread().getName()
				+ "  " + i);
		}
	}

	public static void main(String[] args)
	{
		for (int i = 0; i < 100;  i++)
		{
			System.out.println(Thread.currentThread().getName()
				+ "  " + i);
			if (i == 20)
			{
				SecondThread st = new SecondThread();     // ①
				// 通過new Thread(target , name)方法建立新執行緒
				new Thread(st , "新執行緒1").start();
				new Thread(st , "新執行緒2").start();
			}
		}
	}
}

