
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
public class ArrayTest2
{
	public static void main(String args[])
	{
		/*
		  建立一個三維陣列。
		  根據前面介紹陣列時講的：三維陣列也是一維陣列，
		  是陣列元素是二維陣列的一維陣列，
		  因此可以認為arr是長度為3的一維陣列
		*/
		Object arr = Array.newInstance(String.class, 3, 4, 10);
		// 獲取arr陣列中index為2的元素，該元素應該是二維陣列
		Object arrObj = Array.get(arr, 2);
		// 使用Array為二維陣列的陣列元素賦值。二維陣列的陣列元素是一維陣列，
		// 所以傳入Array的set()方法的第三個參數是一維陣列。
		Array.set(arrObj , 2 , new String[]
		{
			"瘋狂Java講義",
			"輕量級Java EE企業應用實戰"
		});
		// 獲取arrObj陣列中index為3的元素，該元素應該是一維陣列。
		Object anArr  = Array.get(arrObj, 3);
		Array.set(anArr , 8  , "瘋狂Android講義");
		// 將arr強制類型轉換為三維陣列
		String[][][] cast = (String[][][])arr;
		// 獲取cast三維陣列中指定元素的值
		System.out.println(cast[2][3][8]);
		System.out.println(cast[2][2][0]);
		System.out.println(cast[2][2][1]);
	}
}

