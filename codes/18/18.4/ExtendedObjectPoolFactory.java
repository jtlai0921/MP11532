
import java.util.*;
import java.io.*;
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
public class ExtendedObjectPoolFactory
{
	// 定義一個物件池,前面是物件名，後面是實際物件
	private Map<String ,Object> objectPool = new HashMap<>();
	private Properties config = new Properties();
	// 從指定屬性檔案中初始化Properties物件
	public void init(String fileName)
	{
		try(
			FileInputStream fis = new FileInputStream(fileName))
		{
			config.load(fis);
		}
		catch (IOException ex)
		{
			System.out.println("讀取" + fileName + "異常");
		}
	}
	// 定義一個建立物件的方法，
	// 該方法只要傳入一個字串類別名稱，程式可以根據該類別名稱產生Java物件
	private Object createObject(String clazzName)
		throws InstantiationException
		, IllegalAccessException , ClassNotFoundException
	{
		// 根據字串來獲取對應的Class物件
		Class<?> clazz =Class.forName(clazzName);
		// 使用clazz對應類別的預設建構子建立實例
		return clazz.newInstance();
	}
	// 該方法根據指定檔案來初始化物件池，
	// 它會根據設定檔案來建立物件
	public void initPool()throws InstantiationException
		,IllegalAccessException , ClassNotFoundException
	{
		for (String name : config.stringPropertyNames())
		{
			// 每取出一對key-value對，如果key中不包含百分比符號（%）
			// 這就標明是根據value來建立一個物件
			// 呼叫createObject建立物件，並將物件添加到物件池中
			if (!name.contains("%"))
			{
				objectPool.put(name ,
					createObject(config.getProperty(name)));
			}
		}
	}
	// 該方法將會根據屬性檔來呼叫指定物件的setter方法
	public void initProperty()throws InvocationTargetException
		,IllegalAccessException,NoSuchMethodException
	{
		for (String name : config.stringPropertyNames())
		{
			// 每取出一對key-value對，如果key中包含百分比符號（%）
			// 即可認為該key用於控制呼叫物件的setter方法設置值，
			// %前半為物件名字，後半控制setter方法名
			if (name.contains("%"))
			{
				// 將設定檔案中key按%分割
				String[] objAndProp = name.split("%");
				// 取出呼叫setter方法的參數值
				Object target = getObject(objAndProp[0]);
				// 獲取setter方法名:set + "首字母大寫" + 剩下部分
				String mtdName = "set" +
				objAndProp[1].substring(0 , 1).toUpperCase()
					+ objAndProp[1].substring(1);
				// 通過target的getClass()獲取它實作類別所對應的Class物件
				Class<?> targetClass = target.getClass();
				// 獲取希望呼叫的setter方法
				Method mtd = targetClass.getMethod(mtdName , String.class);
				// 通過Method的invoke方法執行setter方法，
				// 將config.getProperty(name)的值作為呼叫setter的方法的參數
				mtd.invoke(target , config.getProperty(name));
			}
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
		ExtendedObjectPoolFactory epf = new ExtendedObjectPoolFactory();
		epf.init("extObj.txt");
		epf.initPool();
		epf.initProperty();
		System.out.println(epf.getObject("a"));
	}
}

