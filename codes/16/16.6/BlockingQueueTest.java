
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
public class BlockingQueueTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 定義一個長度為2的阻擋佇列
		BlockingQueue<String> bq = new ArrayBlockingQueue<>(2);
		bq.put("Java"); // 與bq.add("Java"、bq.offer("Java")相同
		bq.put("Java"); // 與bq.add("Java"、bq.offer("Java")相同
		bq.put("Java"); // ① 阻擋執行緒。
	}
}
