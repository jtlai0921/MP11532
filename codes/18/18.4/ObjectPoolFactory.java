
import java.util.*;
import java.io.*;
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
public class ObjectPoolFactory
{
	// 定義一個物件池,前面是物件名，後面是實際物件
	private Map<String ,Object> objectPool = new HashMap<>();
	// 定義一個建立物件的方法，
	// 該方法只要傳入一個字串類別名稱，程式可以根據該類別名稱產生Java物件
	private Object createObject(String clazzName)
		throws InstantiationException
		, IllegalAccessException , ClassNotFoundException
	{
		// 根據字串來獲取對應的Class物件
		Class<?> clazz = Class.forName(clazzName);
		// 使用clazz對應類別的預設建構子建立實例
		return clazz.newInstance();
	}
	// 該方法根據指定檔案來初始化物件池，
	// 它會根據設定檔案來建立物件
	public void initPool(String fileName)
		throws InstantiationException
		, IllegalAccessException ,ClassNotFoundException
	{
		try(
			FileInputStream fis = new FileInputStream(fileName))
		{
			Properties props = new Properties();
			props.load(fis);
			for (String name : props.stringPropertyNames())
			{
				// 每取出一對key-value對，就根據value建立一個物件
				// 呼叫createObject()建立物件，並將物件添加到物件池中
				objectPool.put(name ,
					createObject(props.getProperty(name)));
			}
		}
		catch (IOException ex)
		{
			System.out.println("讀取" + fileName + "異常");
		}

	}
	public Object getObject(String name)
	{
		// 從objectPool中取出指定name對應的物件。
		return objectPool.get(name);
	}

	public static void main(String[] args)
		throws Exception
	{
		ObjectPoolFactory pf = new ObjectPoolFactory();
		pf.initPool("obj.txt");
		System.out.println(pf.getObject("a"));      // ①
		System.out.println(pf.getObject("b"));      // ②
	}
}
