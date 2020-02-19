

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
// 定義自己的異常處理器
class MyExHandler implements Thread.UncaughtExceptionHandler
{
	// 實作uncaughtException方法，該方法將處理執行緒的未處理異常
	public void uncaughtException(Thread t, Throwable e)
	{
		System.out.println(t + " 執行緒出現了異常：" + e);
	}
}
public class ExHandler
{
	public static void main(String[] args)
	{
		// 設置主執行緒的異常處理器
		Thread.currentThread().setUncaughtExceptionHandler
			(new MyExHandler());
		int a = 5 / 0;     // ①
		System.out.println("程式正常結束！");
	}
}
