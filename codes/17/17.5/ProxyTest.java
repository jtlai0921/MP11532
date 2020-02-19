
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
public class ProxyTest
{
	// 下面是代理伺服器的位址和連接埠，
	// 換成實際有效的代理伺服器的位址和連接埠
	final String PROXY_ADDR = "129.82.12.188";
	final int PROXY_PORT = 3124;
	// 定義需要存取的網站位址
	String urlStr = "http://www.crazyit.org";
	public void init()
		throws IOException , MalformedURLException
	{
		URL url = new URL(urlStr);
		// 建立一個代理伺服器物件
		Proxy proxy = new Proxy(Proxy.Type.HTTP
			, new InetSocketAddress(PROXY_ADDR , PROXY_PORT));
		// 使用指定的代理伺服器開啟連線
		URLConnection conn = url.openConnection(proxy);
		// 設置逾時時長。
		conn.setConnectTimeout(5000);
		try(
			// 通過代理伺服器讀取資料的Scanner
			Scanner scan = new Scanner(conn.getInputStream(), "utf-8");
			PrintStream ps = new PrintStream("index.htm"))
		{
			while (scan.hasNextLine())
			{
				String line = scan.nextLine();
				// 在主控台輸出網頁資源內容
				System.out.println(line);
				// 將網頁資源內容輸出到指定輸出流
				ps.println(line);
			}
		}
	}
	public static void main(String[] args)
		throws IOException , MalformedURLException
	{
		new ProxyTest().init();
	}
}
