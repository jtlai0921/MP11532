

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
// 通過繼承Thread類別來建立執行緒類別
public class FirstThread extends Thread
{
	private int i ;
	// 覆寫run方法，run方法的方法體就是執行緒體
	public void run()
	{
		for ( ; i < 100 ; i++ )
		{
			// 當執行緒類別繼承Thread類別時，直接使用this即可獲取當前執行緒
			// Thread物件的getName()返回當前該執行緒的名字
			// 因此可以直接呼叫getName()方法返回當前執行緒的名字
			System.out.println(getName() +  " " + i);
		}
	}
	public static void main(String[] args)
	{
		for (int i = 0; i < 100;  i++)
		{
			// 呼叫Thread的currentThread方法獲取當前執行緒
			System.out.println(Thread.currentThread().getName()
				+  " " + i);
			if (i == 20)
			{
				// 建立、並啟動第一條執行緒
				new FirstThread().start();
				// 建立、並啟動第二條執行緒
				new FirstThread().start();
			}
		}
	}
}

