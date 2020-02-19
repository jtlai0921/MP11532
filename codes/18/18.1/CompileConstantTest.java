

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
class MyTest
{
	static
	{
		System.out.println("靜態初始化區塊...");
	}
	// 使用一個字串字面常數為static final的類別變數賦值
	static final String compileConstant = "瘋狂Java講義";
}
public class CompileConstantTest
{
	public static void main(String[] args)
	{
		// 存取、輸出MyTest中的compileConstant類別變數
		System.out.println(MyTest.compileConstant);   // ①
	}
}
