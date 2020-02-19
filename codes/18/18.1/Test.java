

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
public class Test
{
	static
	{
		// 使用靜態初始化區塊為變數b指定出初始值
		b = 6;
		System.out.println("----------");
	}
	// 宣告變數a時指定初始值
	static int a = 5;
	static int b = 9;         // ①
	static int c;
	public static void main(String[] args)
	{
		System.out.println(Test.b);
	}
}
