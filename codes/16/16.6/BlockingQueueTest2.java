
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
class Producer extends Thread
{
	private BlockingQueue<String> bq;
	public Producer(BlockingQueue<String> bq)
	{
		this.bq = bq;
	}
	public void run()
	{
		String[] strArr = new String[]
		{
			"Java",
			"Struts",
			"Spring"
		};
		for (int i = 0 ; i < 999999999 ; i++ )
		{
			System.out.println(getName() + "生產者準備生產集合元素！");
			try
			{
				Thread.sleep(200);
				// 嘗試放入元素，如果佇列已滿，執行緒被阻擋
				bq.put(strArr[i % 3]);
			}
			catch (Exception ex){ex.printStackTrace();}
			System.out.println(getName() + "生產完成：" + bq);
		}
	}
}
class Consumer extends Thread
{
	private BlockingQueue<String> bq;
	public Consumer(BlockingQueue<String> bq)
	{
		this.bq = bq;
	}
	public void run()
	{
		while(true)
		{
			System.out.println(getName() + "消費者準備消費集合元素！");
			try
			{
				Thread.sleep(200);
				// 嘗試取出元素，如果佇列已空，執行緒被阻擋
				bq.take();
			}
			catch (Exception ex){ex.printStackTrace();}
			System.out.println(getName() + "消費完成：" + bq);
		}
	}
}
public class BlockingQueueTest2
{
	public static void main(String[] args)
	{
		// 建立一個容量為1的BlockingQueue
		BlockingQueue<String> bq = new ArrayBlockingQueue<>(1);
		// 啟動3條生產者執行緒
		new Producer(bq).start();
		new Producer(bq).start();
		new Producer(bq).start();
		// 啟動一條消費者執行緒
		new Consumer(bq).start();
	}
}