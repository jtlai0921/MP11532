
import java.lang.reflect.*;
import java.lang.annotation.*;
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
public class CrazyitArray
{
	// 對Array的newInstance方法進行包裝
	@SuppressWarnings("unchecked")
	public static <T> T[] newInstance(Class<T> componentType, int length)
	{
		return (T[])Array.newInstance(componentType , length);  //①
	}
	public static void main(String[] args)
	{
		// 使用CrazyitArray的newInstance()建立一維陣列
		String[] arr = CrazyitArray.newInstance(String.class , 10);
		// 使用CrazyitArray的newInstance()建立二維陣列
		// 在這種情況下，只要設置陣列元素的類型是int[]即可。
		int[][] intArr = CrazyitArray.newInstance(int[].class , 5);
		arr[5] = "瘋狂Java講義";
		// intArr是二維陣列，初始化該陣列的第二個陣列元素
		// 二維陣列的元素必須是一維陣列
		intArr[1] = new int[]{23, 12};
		System.out.println(arr[5]);
		System.out.println(intArr[1][1]);
	}
}
