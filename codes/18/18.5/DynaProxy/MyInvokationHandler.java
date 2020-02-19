
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
public class MyInvokationHandler implements InvocationHandler
{
	// 需要被代理的物件
	private Object target;
	public void setTarget(Object target)
	{
		this.target = target;
	}
	// 執行動態代理物件的所有方法時，都會被替換成執行如下的invoke方法
	public Object invoke(Object proxy, Method method, Object[] args)
		throws Exception
	{
		DogUtil du = new DogUtil();
		// 執行DogUtil物件中的method1。
		du.method1();
		// 以target作為主呼來執行method方法
		Object result = method.invoke(target , args);
		// 執行DogUtil物件中的method2。
		du.method2();
		return result;
	}
}

