
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
public class ErrorUtils
{
	@SafeVarargs
	public static void faultyMethod(List<String>... listStrArray)
	{
		// Java語言不允許建立泛型陣列，因此listArray只能被當成List[]處理
		// 此時相當於把List<String>賦給了List，已經發生了「抹除」
		List[] listArray = listStrArray;
		List<Integer> myList = new ArrayList<Integer>();
		myList.add(new Random().nextInt(100));
		// 把listArray的第一個元素賦為myList
		listArray[0] = myList;
		String s = listStrArray[0].get(0);
	}
}