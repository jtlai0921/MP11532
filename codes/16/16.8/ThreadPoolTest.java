
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
public class ThreadPoolTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 建立一個具有固定執行緒數（6）的執行緒池
		ExecutorService pool = Executors.newFixedThreadPool(6);
		// 使用Lambda運算式建立Runnable物件
		Runnable target = () -> {
			for (int i = 0; i < 100 ; i++ )
			{
				System.out.println(Thread.currentThread().getName()
					+ "的i值為:" + i);
			}
		};
		// 向執行緒池中提交兩個執行緒
		pool.submit(target);
		pool.submit(target);
		// 關閉執行緒池
		pool.shutdown();
	}
}

