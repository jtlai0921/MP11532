
import java.util.concurrent.*;
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

public class ThirdThread
{
	public static void main(String[] args)
	{
		// 建立Callable物件
		ThirdThread rt = new ThirdThread();
		// 先使用Lambda運算式建立Callable<Integer>物件
		// 使用FutureTask來包裝Callable物件
		FutureTask<Integer> task = new FutureTask<Integer>((Callable<Integer>)() -> {
			int i = 0;
			for ( ; i < 100 ; i++ )
			{
				System.out.println(Thread.currentThread().getName()
					+ " 的迴圈變數i的值：" + i);
			}
			// call()方法可以有返回值
			return i;
		});
		for (int i = 0 ; i < 100 ; i++)
		{
			System.out.println(Thread.currentThread().getName()
				+ " 的迴圈變數i的值：" + i);
			if (i == 20)
			{
				// 實質還是以Callable物件來建立、並啟動執行緒
				new Thread(task , "有返回值的執行緒").start();
			}
		}
		try
		{
			// 獲取執行緒返回值
			System.out.println("子執行緒的返回值：" + task.get());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}

