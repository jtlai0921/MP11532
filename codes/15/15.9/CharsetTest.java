
import java.nio.charset.*;
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
public class CharsetTest
{
	public static void main(String[] args)
	{
		// 獲取Java支援的全部字元集
		SortedMap<String,Charset>  map = Charset.availableCharsets();
		for (String alias : map.keySet())
		{
			// 輸出字元集的別名和對應的Charset物件
			System.out.println(alias + "----->"
				+ map.get(alias));
		}
	}
}
