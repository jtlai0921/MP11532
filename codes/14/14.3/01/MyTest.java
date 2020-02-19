

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
public class MyTest
{
	// 使用@Testable註文指定該方法是可測試的
	@Testable
	public static void m1()
	{
	}
	public static void m2()
	{
	}
	// 使用@Testable註文指定該方法是可測試的
	@Testable
	public static void m3()
	{
		throw new IllegalArgumentException("參數出錯了！");
	}
	public static void m4()
	{
	}
	// 使用@Testable註文指定該方法是可測試的
	@Testable
	public static void m5()
	{
	}
	public static void m6()
	{
	}
	// 使用@Testable註文指定該方法是可測試的
	@Testable
	public static void m7()
	{
		throw new RuntimeException("程式業務出現異常！");
	}
	public static void m8()
	{
	}
}
