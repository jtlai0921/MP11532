
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
public class ArrayTest1
{
	public static void main(String args[])
	{
		try
		{
			// 建立一個元素類型為String ，長度為10的陣列
			Object arr = Array.newInstance(String.class, 10);
			// 依次為arr陣列中index為5、6的元素賦值
			Array.set(arr, 5, "瘋狂Java講義");
			Array.set(arr, 6, "輕量級Java EE企業應用實戰");
			// 依次取出arr陣列中index為5、6的元素的值
			Object book1 = Array.get(arr , 5);
			Object book2 = Array.get(arr , 6);
			// 輸出arr陣列中index為5、6的元素
			System.out.println(book1);
			System.out.println(book2);
		}
		catch (Throwable e)
		{
			System.err.println(e);
		}
	}
}
