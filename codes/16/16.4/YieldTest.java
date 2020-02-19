

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
public class YieldTest extends Thread
{
	public YieldTest(String name)
	{
		super(name);
	}
	// 定義run方法作為執行緒體
	public void run()
	{
		for (int i = 0; i < 50 ; i++ )
		{
			System.out.println(getName() + "  " + i);
			// 當i等於20時，使用yield方法讓當前執行緒讓步
			if (i == 20)
			{
				Thread.yield();
			}
		}
	}
	public static void main(String[] args)throws Exception
	{
		// 啟動兩條並行執行緒
		YieldTest yt1 = new YieldTest("高級");
		// 將ty1執行緒設置成最高優先級
		yt1.setPriority(Thread.MAX_PRIORITY);
		yt1.start();
		YieldTest yt2 = new YieldTest("低級");
		// 將yt2執行緒設置成最低優先級
		yt2.setPriority(Thread.MIN_PRIORITY);
		yt2.start();
	}
}
