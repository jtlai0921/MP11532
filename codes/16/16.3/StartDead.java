

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
public class StartDead extends Thread
{
	private int i ;
	// 覆寫run方法，run方法的方法體就是執行緒體
	public void run()
	{
		for ( ; i < 100 ; i++ )
		{
			System.out.println(getName() +  " " + i);
		}
	}
	public static void main(String[] args)
	{
		// 建立執行緒物件
		StartDead sd = new StartDead();
		for (int i = 0; i < 300;  i++)
		{
			// 呼叫Thread的currentThread方法獲取當前執行緒
			System.out.println(Thread.currentThread().getName()
				+  " " + i);
			if (i == 20)
			{
				// 啟動執行緒
				sd.start();
				// 判斷啟動後執行緒的isAlive()值，輸出true
				System.out.println(sd.isAlive());
			}
			// 只有當執行緒處於新增、死亡兩種狀態時isAlive()方法返回false。
			// 當i > 20，則該執行緒肯定已經啟動過了，如果sd.isAlive()為假時，
			// 那只能是死亡狀態了。
			if (i > 20 && !sd.isAlive())

			{
				// 試圖再次啟動該執行緒
				sd.start();
			}
		}
	}
}

