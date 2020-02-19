

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
// 使用@Inheritable修飾的Base類別
@Inheritable
class Base
{
}
// TestInheritable類別只是繼承了Base類別，
// 並未直接使用@Inheritable Annotiation修飾
public class InheritableTest extends Base
{
	public static void main(String[] args)
	{
		// 列印TestInheritable類別是否具有@Inheritable修飾
		System.out.println(InheritableTest.class
			.isAnnotationPresent(Inheritable.class));
	}
}

