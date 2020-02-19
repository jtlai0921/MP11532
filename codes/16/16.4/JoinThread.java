

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
public class JoinThread extends Thread
{
	// 提供一個有參數的建構子，用於設置該執行緒的名字
	public JoinThread(String name)
	{
		super(name);
	}
	// 覆寫run()方法，定義執行緒體
	public void run()
	{
		for (int i = 0; i < 100 ; i++ )
		{
			System.out.println(getName() + "  " + i);
		}
	}
	public static void main(String[] args)throws Exception
	{
		// 啟動子執行緒
		new JoinThread("新執行緒").start();
		for (int i = 0; i < 100 ; i++ )
		{
			if (i == 20)
			{
				JoinThread jt = new JoinThread("被Join的執行緒");
				jt.start();
				// main執行緒呼叫了jt執行緒的join()方法，main執行緒
				// 必須等jt執行結束才會向下執行
				jt.join();
			}
			System.out.println(Thread.currentThread().getName()
				+ "  " + i);
		}
	}
}
