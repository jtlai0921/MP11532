
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
public class ProxySelectorTest
{
	// 下面是代理伺服器的位址和連接埠，
	// 隨便一個代理伺服器的位址和連接埠
	final String PROXY_ADDR = "139.82.12.188";
	final int PROXY_PORT = 3124;
	// 定義需要存取的網站位址
	String urlStr = "http://www.crazyit.org";
	public void init()
		throws IOException , MalformedURLException
	{
		// 註冊預設的代理選擇器
		ProxySelector.setDefault(new ProxySelector()
		{
			@Override
			public void connectFailed(URI uri
				, SocketAddress sa, IOException ioe)
			{
				System.out.println("無法連接到指定代理伺服器！");
			}
			// 根據"業務需要"返回特定的對應的代理伺服器
			@Override
			public List<Proxy> select(URI uri)
			{
				// 本程式總是返回某個固定的代理伺服器。
				List<Proxy> result = new ArrayList<>();
				result.add(new Proxy(Proxy.Type.HTTP
					, new InetSocketAddress(PROXY_ADDR , PROXY_PORT)));
				return result;
			}
		});
		URL url = new URL(urlStr);
		// 沒有指定代理伺服器、直接開啟連線
		URLConnection conn = url.openConnection();   //①
		// 設置逾時時長。
		conn.setConnectTimeout(3000);
		try(
			// 通過代理伺服器讀取資料的Scanner
			Scanner scan = new Scanner(conn.getInputStream());
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
		new ProxySelectorTest().init();
	}
}