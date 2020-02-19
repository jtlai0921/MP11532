
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
public class InetAddressTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 根據主機名稱來獲取對應的InetAddress實例
		InetAddress ip = InetAddress.getByName("www.crazyit.org");
		// 判斷是否可達
		System.out.println("crazyit是否可達：" + ip.isReachable(2000));
		// 獲取該InetAddress實例的IP字串
		System.out.println(ip.getHostAddress());
		// 根據原始IP位址來獲取對應的InetAddress實例
		InetAddress local = InetAddress.getByAddress(
			new byte[]{127,0,0,1});
		System.out.println("本機是否可達：" + local.isReachable(5000));
		// 獲取該InetAddress實例對應的正規域名
		System.out.println(local.getCanonicalHostName());
	}
}
