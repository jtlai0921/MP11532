
import java.lang.reflect.*;
import java.awt.event.*;
import javax.swing.*;
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
public class ActionListenerInstaller
{
	// 處理Annotation的方法，其中obj是包含Annotation的物件
	public static void processAnnotations(Object obj)
	{
		try
		{
			// 獲取obj物件的類別
			Class cl = obj.getClass();
			// 獲取指定obj物件的所有成員變數，並遍歷每個成員變數
			for (Field f : cl.getDeclaredFields())
			{
				// 將該成員變數設置成可自由存取。
				f.setAccessible(true);
				// 獲取該成員變數上ActionListenerFor類型的Annotation
				ActionListenerFor a = f.getAnnotation(ActionListenerFor.class);
				// 獲取成員變數f的值
				Object fObj  = f.get(obj);
				// 如果f是AbstractButton的實例，且a不為null
				if (a != null && fObj != null
					&& fObj instanceof AbstractButton)
				{
					// 獲取a註文裡的listner元資料（它是一個監聽器類別）
					Class<? extends ActionListener> listenerClazz = a.listener();
					// 使用反射來建立listner類別的物件
					ActionListener al = listenerClazz.newInstance();
					AbstractButton ab = (AbstractButton)fObj;
					// 為ab按鈕添加事件監聽器
					ab.addActionListener(al);
				}
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
