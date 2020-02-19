
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
public class DefaultProxySelectorTest
{
	// 定義需要存取的網站位址
	static String urlStr = "http://www.crazyit.org";
	public static void main(String[] args) throws Exception
	{
		// 獲取系統的預設屬性
		Properties props = System.getProperties();
		// 通過系統屬性設置HTTP存取所用的代理伺服器的主機位址、連接埠
		props.setProperty("http.proxyHost", "192.168.10.96");
		props.setProperty("http.proxyPort", "8080");
		// 通過系統屬性設置HTTP存取無需使用代理伺服器的主機
		// 可以使用*萬用字元，多個位址用|分隔
		props.setProperty("http.nonProxyHosts", "localhost|192.168.10.*");
		// 通過系統屬性設置HTTPS存取所用的代理伺服器的主機位址、連接埠
		props.setProperty("https.proxyHost", "192.168.10.96");
		props.setProperty("https.proxyPort", "443");
		/* DefaultProxySelector不支援https.nonProxyHosts屬性，
		 DefaultProxySelector直接按http.nonProxyHosts的設置規則處理 */
		// 通過系統屬性設置FTP存取所用的代理伺服器的主機位址、連接埠
		props.setProperty("ftp.proxyHost", "192.168.10.96");
		props.setProperty("ftp.proxyPort", "2121");
		// 通過系統屬性設置FTP存取無需使用代理伺服器的主機
		props.setProperty("ftp.nonProxyHosts", "localhost|192.168.10.*");
		// 通過系統屬性設置設置SOCKS代理伺服器的主機位址、連接埠
		props.setProperty("socks.ProxyHost", "192.168.10.96");
		props.setProperty("socks.ProxyPort", "1080");
		// 獲取系統預設的代理選擇器
		ProxySelector selector = ProxySelector.getDefault();   // ①
		System.out.println("系統預設的代理選擇器：" + selector);
		// 根據URI動態決定所使用的代理伺服器
		System.out.println("系統為ftp://www.crazyit.org選擇的代理伺服器為："
			+ ProxySelector.getDefault().select(new URI("ftp://www.crazyit.org"))); // ②
		URL url = new URL(urlStr);
		// 直接開啟連接，預設的代理選擇器會使用http.proxyHost、
		// http.proxyPort系統屬性設置的代理伺服器，
		// 如果無法連接代理伺服器，預設的代理選擇器會嘗試直接連接
		URLConnection conn = url.openConnection();   // ③
		// 設置逾時時長。
		conn.setConnectTimeout(3000);
		try(
			Scanner scan = new Scanner(conn.getInputStream() , "utf-8"))
		{
			// 讀取遠端主機的內容
			while(scan.hasNextLine())
			{
				System.out.println(scan.nextLine());
			}
		}
	}
}
