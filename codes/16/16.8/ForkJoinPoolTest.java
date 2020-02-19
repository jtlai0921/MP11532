
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

// 繼承RecursiveAction來實作"可分解"的任務
class PrintTask extends RecursiveAction
{
	// 每個「小任務」只最多只列印50個數
	private static final int THRESHOLD = 50;
	private int start;
	private int end;
	// 列印從start到end的任務
	public PrintTask(int start, int end)
	{
		this.start = start;
		this.end = end;
	}
	@Override
	protected void compute()
	{
		// 當end與start之間的差小於THRESHOLD時，開始列印
		if(end - start < THRESHOLD)
		{
			for (int i = start ; i < end ; i++ )
			{
				System.out.println(Thread.currentThread().getName()
					+ "的i值：" + i);
			}
		}
		else
		{
			// 如果當end與start之間的差大於THRESHOLD時，即要列印的數超過50個
			// 將大任務分解成兩個小任務。
			int middle = (start + end) / 2;
			PrintTask left = new PrintTask(start, middle);
			PrintTask right = new PrintTask(middle, end);
			// 平行執行兩個「小任務」
			left.fork();
			right.fork();
		}
	}
}
public class ForkJoinPoolTest
{
	public static void main(String[] args)
		throws Exception
	{
		ForkJoinPool pool = new ForkJoinPool();
		// 提交可分解的PrintTask任務
		pool.submit(new PrintTask(0 , 300));
		pool.awaitTermination(2, TimeUnit.SECONDS);
		// 關閉執行緒池
		pool.shutdown();
	}
}

