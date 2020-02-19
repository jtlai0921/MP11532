
import java.util.*;
import java.net.*;
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
public class ClassLoaderPropTest
{
	public static void main(String[] args)
		throws IOException
	{
		// 獲取系統類別載入器
		ClassLoader systemLoader = ClassLoader.getSystemClassLoader();
		System.out.println("系統類別載入器：" + systemLoader);
		/*
		獲取系統類別載入器的載入路徑――通常由CLASSPATH環境變數指定
		如果作業系統沒有指定CLASSPATH環境變數，預設以當前路徑作為
		系統類別載入器的載入路徑
		*/
		Enumeration<URL> em1 = systemLoader.getResources("");
		while(em1.hasMoreElements())
		{
			System.out.println(em1.nextElement());
		}
		// 獲取系統類別載入器的父類別載入器：得到擴展類別載入器
		ClassLoader extensionLader = systemLoader.getParent();
		System.out.println("擴展類別載入器：" + extensionLader);
		System.out.println("擴展類別載入器的載入路徑："
			+ System.getProperty("java.ext.dirs"));
		System.out.println("擴展類別載入器的parent: "
			+ extensionLader.getParent());
	}
}

