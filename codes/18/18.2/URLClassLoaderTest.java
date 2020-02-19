
import java.sql.*;
import java.util.*;
import java.net.*;
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
public class URLClassLoaderTest
{
	private static Connection conn;
	// 定義一個獲取資料庫連接方法
	public static Connection getConn(String url ,
		String user , String pass) throws Exception
	{
		if (conn == null)
		{
			// 建立一個URL陣列
			URL[] urls = {new URL(
				"file:mysql-connector-java-5.1.30-bin.jar")};
			// 以預設的ClassLoader作為父ClassLoader，建立URLClassLoader
			URLClassLoader myClassLoader = new URLClassLoader(urls);
			// 載入MySQL的JDBC驅動，並建立預設實例
			Driver driver = (Driver)myClassLoader.
				loadClass("com.mysql.jdbc.Driver").newInstance();
			// 建立一個設置JDBC連接屬性的Properties物件
			Properties props = new Properties();
			// 至少需要為該物件傳入user和password兩個屬性
			props.setProperty("user" , user);
			props.setProperty("password" , pass);
			// 呼叫Driver物件的connect方法來取得資料庫連接
			conn = driver.connect(url , props);
		}
		return conn;
	}
	public static void main(String[] args)throws Exception
	{
		System.out.println(getConn("jdbc:mysql://localhost:3306/mysql"
			, "root" , "32147"));
	}
}

