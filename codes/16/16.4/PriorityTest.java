

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
public class PriorityTest extends Thread
{
	// 定義一個有參數的建構子，用於建立執行緒時指定name
	public PriorityTest(String name)
	{
		super(name);
	}
	public void run()
	{
		for (int i = 0 ; i < 50 ; i++ )
		{
			System.out.println(getName() +  ",其優先級是："
				+ getPriority() + ",迴圈變數的值為:" + i);
		}
	}
	public static void main(String[] args)
	{
		// 改變主執行緒的優先級
		Thread.currentThread().setPriority(6);
		for (int i = 0 ; i < 30 ; i++ )
		{
			if (i == 10)
			{
				PriorityTest low  = new PriorityTest("低級");
				low.start();
				System.out.println("建立之初的優先級:"
					+ low.getPriority());
				// 設置該執行緒為最低優先級
				low.setPriority(Thread.MIN_PRIORITY);
			}
			if (i == 20)
			{
				PriorityTest high = new PriorityTest("高級");
				high.start();
				System.out.println("建立之初的優先級:"
					+ high.getPriority());
				// 設置該執行緒為最高優先級
				high.setPriority(Thread.MAX_PRIORITY);
			}
		}
	}
}
