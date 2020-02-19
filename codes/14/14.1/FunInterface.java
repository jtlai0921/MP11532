

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
@FunctionalInterface
public interface FunInterface
{
	static void foo()
	{
		System.out.println("foo類別方法");
	}
	default void bar()
	{
		System.out.println("bar預設方法");
	}
	void test(); // 只定義一個抽象方法

	void abc();
}

