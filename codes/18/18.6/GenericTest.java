
import java.util.*;
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
public class GenericTest
{
	private Map<String , Integer> score;
	public static void main(String[] args)
		throws Exception
	{
		Class<GenericTest> clazz = GenericTest.class;
		Field f = clazz.getDeclaredField("score");
		// 直接使用getType()取出的類型只對普通類型的成員變數有效
		Class<?> a = f.getType();
		// 下面將看到僅輸出java.util.Map
		System.out.println("score的類型是:" + a);
		// 獲得成員變數f的泛型類型
		Type gType = f.getGenericType();
		// 如果gType類型是ParameterizedType物件
		if(gType instanceof ParameterizedType)
		{
			// 強制類型轉換
			ParameterizedType pType = (ParameterizedType)gType;
			// 獲取原始類型
			Type rType = pType.getRawType();
			System.out.println("原始類型是：" + rType);
			// 取得泛型類型的泛型參數
			Type[] tArgs = pType.getActualTypeArguments();
			System.out.println("泛型資訊是:");
			for (int i = 0; i < tArgs.length; i++)
			{
				System.out.println("第" + i + "個泛型類型是：" + tArgs[i]);
			}
		}
		else
		{
			System.out.println("獲取泛型類型出錯！");
		}
	}
}
