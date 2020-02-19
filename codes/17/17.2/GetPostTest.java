
import java.io.*;
import java.net.*;
import java.util.*;
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
public class GetPostTest
{
	/**
	 * 向指定URL發送GET方法的請求
	 * @param url 發送請求的URL
	 * @param param 請求參數，格式滿足name1=value1&name2=value2的形式。
	 * @return URL所代表遠端資源的回應
	 */
	public static String sendGet(String url , String param)
	{
		String result = "";
		String urlName = url + "?" + param;
		try
		{
			URL realUrl = new URL(urlName);
			// 開啟和URL之間的連接
			URLConnection conn = realUrl.openConnection();
			// 設置通用的請求屬性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent"
				, "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立實際的連接
			conn.connect();
			// 獲取所有回應標頭欄位
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍歷所有的回應標頭欄位
			for (String key : map.keySet())
			{
				System.out.println(key + "--->" + map.get(key));
			}
			try(
				// 定義BufferedReader輸入串流來讀取URL的回應
				BufferedReader in = new BufferedReader(
					new InputStreamReader(conn.getInputStream() , "utf-8")))
			{
				String line;
				while ((line = in.readLine())!= null)
				{
					result += "\n" + line;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("發送GET請求出現異常！" + e);
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 向指定URL發送POST方法的請求
	 * @param url 發送請求的URL
	 * @param param 請求參數，格式應該滿足name1=value1&name2=value2的形式。
	 * @return URL所代表遠端資源的回應
	 */
	public static String sendPost(String url , String param)
	{
		String result = "";
		try
		{
			URL realUrl = new URL(url);
			// 開啟和URL之間的連線
			URLConnection conn = realUrl.openConnection();
			// 設置通用的請求屬性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
			"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 發送POST請求必須設置如下兩行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			try(
				// 獲取URLConnection物件對應的輸出串流
				PrintWriter out = new PrintWriter(conn.getOutputStream()))
			{
				// 發送請求參數
				out.print(param);
				// flush輸出串流的緩衝
				out.flush();
			}
			try(
				// 定義BufferedReader輸入串流來讀取URL的回應
				BufferedReader in = new BufferedReader(new InputStreamReader
					(conn.getInputStream() , "utf-8")))
			{
				String line;
				while ((line = in.readLine())!= null)
				{
					result += "\n" + line;
				}
			}
		}
		catch(Exception e)
		{
			System.out.println("發送POST請求出現異常！" + e);
			e.printStackTrace();
		}
		return result;
	}
	// 提供主方法，測試發送GET請求和POST請求
	public static void main(String args[])
	{
		// 發送GET請求
		String s = GetPostTest.sendGet("http://localhost:8888/abc/a.jsp"
			, null);
		System.out.println(s);
		// 發送POST請求
		String s1 = GetPostTest.sendPost("http://localhost:8888/abc/login.jsp"
			, "name=crazyit.org&pass=leegang");
		System.out.println(s1);
	}
}
