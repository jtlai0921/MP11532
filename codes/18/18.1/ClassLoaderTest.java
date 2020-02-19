

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
class Tester
{
	static
	{
		System.out.println("Tester類別的靜態初始化區塊...");
	}
}
public class ClassLoaderTest
{
	public static void main(String[] args)
		throws ClassNotFoundException
	{
		ClassLoader cl = ClassLoader.getSystemClassLoader();
		// 下面語句僅僅是載入Tester類別
		cl.loadClass("Tester");
		System.out.println("系統載入Tester類別");
		// 下面語句才會初始化Tester類別
		Class.forName("Tester");
	}
}

