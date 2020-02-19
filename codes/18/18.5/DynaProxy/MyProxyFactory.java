
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
public class MyProxyFactory
{
	// 為指定target產生動態代理物件
	public static Object getProxy(Object target)
		throws Exception
		{
		// 建立一個MyInvokationHandler物件
		MyInvokationHandler handler =
		new MyInvokationHandler();
		// 為MyInvokationHandler設置target物件
		handler.setTarget(target);
		// 建立、並返回一個動態代理
		return Proxy.newProxyInstance(target.getClass().getClassLoader()
			, target.getClass().getInterfaces() , handler);
	}
}

