

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
public class InvokeRun extends Thread
{
	private int i ;
	// 覆寫run方法，run方法的方法體就是執行緒體
	public void run()
	{
		for ( ; i < 100 ; i++ )
		{
			// 直接呼叫run方法時，Thread的this.getName返回的是該物件名字，
			// 而不是當前執行緒的名字。
			// 使用Thread.currentThread().getName()總是獲取當前執行緒名字
			System.out.println(Thread.currentThread().getName()
				+  " " + i);   // ①
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
				// 直接呼叫執行緒物件的run方法，
				// 系統會把執行緒物件當成普通物件，run方法當成普通方法，
				// 所以下面兩行程式碼並不會啟動兩條執行緒，而是依次執行兩個run方法
				new InvokeRun().run();
				new InvokeRun().run();
			}
		}
	}
}
