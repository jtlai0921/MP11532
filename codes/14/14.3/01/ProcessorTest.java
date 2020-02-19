
import java.lang.reflect.*;
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
public class ProcessorTest
{
	public static void process(String clazz)
		throws ClassNotFoundException
	{
		int passed = 0;
		int failed = 0;
		// 遍歷clazz對應的類別裡的所有方法
		for (Method m : Class.forName(clazz).getMethods())
		{
			// 如果該方法使用了@Testable修飾
			if (m.isAnnotationPresent(Testable.class))
			{
				try
				{
					// 呼叫m方法
					m.invoke(null);
					// 測試成功，passed計數器加1
					passed++;
				}
				catch (Exception ex)
				{
					System.out.println("方法" + m + "運行失敗，異常："
						+ ex.getCause());
					// 測試出現異常，failed計數器加1
					failed++;
				}
			}
		}
		// 統計測試結果
		System.out.println("共運行了:" + (passed + failed)
			+ "個方法，其中：\n" + "失敗了:" + failed + "個，\n"
			+ "成功了:" + passed + "個！");
	}
}

