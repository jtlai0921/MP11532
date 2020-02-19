
import java.lang.reflect.*;
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
class Test
{
	public void replace(String str, List<String> list){}
}
public class MethodParameterTest
{
	public static void main(String[] args)throws Exception
	{
		// 獲取String的類別
		Class<Test> clazz = Test.class;
		// 獲取String類別的帶兩個參數的replace()方法
		Method replace = clazz.getMethod("replace"
			, String.class, List.class);
		// 獲取指定方法的參數個數
		System.out.println("replace方法參數個數：" + replace.getParameterCount());
		// 獲取replace的所有參數資訊
		Parameter[] parameters = replace.getParameters();
		int index = 1;
		// 遍歷所有參數
		for (Parameter p : parameters)
		{
			if (p.isNamePresent())
			{
				System.out.println("---第" + index++ + "個參數資訊---");
				System.out.println("參數名：" + p.getName());
				System.out.println("形式參數類型：" + p.getType());
				System.out.println("泛型類型：" + p.getParameterizedType());
			}
		}
	}
}
