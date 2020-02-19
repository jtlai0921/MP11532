

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
class Apple
{
	// 定義info方法已過時
	@Deprecated
	public void info()
	{
		System.out.println("Apple的info方法");
	}
}
public class DeprecatedTest
{
	public static void main(String[] args)
	{
		// 下面使用info方法時將會被編譯器警告
		new Apple().info();
	}
}

