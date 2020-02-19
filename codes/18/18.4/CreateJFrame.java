
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
public class CreateJFrame
{
	public static void main(String[] args)
		throws Exception
	{
		// 獲取JFrame對應的Class物件
		Class<?> jframeClazz = Class.forName("javax.swing.JFrame");
		// 獲取JFrame中帶一個字串參數的建構子
		Constructor ctor = jframeClazz
			.getConstructor(String.class);
		// 呼叫Constructor的newInstance方法建立物件
		Object obj = ctor.newInstance("測試視窗");
		// 輸出JFrame物件
		System.out.println(obj);
	}
}
