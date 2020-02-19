
import java.util.concurrent.*;
import java.util.*;
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

// 繼承RecursiveTask來實作"可分解"的任務
class CalTask extends RecursiveTask<Integer>
{
	// 每個「小任務」只最多隻累加20個數
	private static final int THRESHOLD = 20;
	private int arr[];
	private int start;
	private int end;
	// 累加從start到end的陣列元素
	public CalTask(int[] arr , int start, int end)
	{
		this.arr = arr;
		this.start = start;
		this.end = end;
	}
	@Override
	protected Integer compute()
	{
		int sum = 0;
		// 當end與start之間的差小於THRESHOLD時，開始進行實際累加
		if(end - start < THRESHOLD)
		{
			for (int i = start ; i < end ; i++ )
			{
				sum += arr[i];
			}
			return sum;
		}
		else
		{
			// 如果當end與start之間的差大於THRESHOLD時，即要累加的數超過20個時
			// 將大任務分解成兩個小任務。
			int middle = (start + end) / 2;
			CalTask left = new CalTask(arr , start, middle);
			CalTask right = new CalTask(arr , middle, end);
			// 平行執行兩個「小任務」
			left.fork();
			right.fork();
			// 把兩個「小任務」累加的結果合併起來
			return left.join() + right.join();    // ①
		}
	}
}
public class Sum
{
	public static void main(String[] args)
		throws Exception
	{
		int[] arr = new int[100];
		Random rand = new Random();
		int total = 0;
		// 初始化100個數字元素
		for (int i = 0 , len = arr.length; i < len ; i++ )
		{
			int tmp = rand.nextInt(20);
			// 對陣列元素賦值，並將陣列元素的值添加到sum總和中。
			total += (arr[i] = tmp);
		}
		System.out.println(total);
		// 建立一個通用池
		ForkJoinPool pool = ForkJoinPool.commonPool();
		// 提交可分解的CalTask任務
		Future<Integer> future = pool.submit(new CalTask(arr , 0 , arr.length));
		System.out.println(future.get());
		// 關閉執行緒池
		pool.shutdown();
	}
}

