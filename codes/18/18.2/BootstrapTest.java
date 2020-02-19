
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
public class BootstrapTest
{
	public static void main(String[] args)
	{
		// 獲取根類別載入器所載入的全部URL陣列
		URL[] urls = sun.misc.Launcher.
		getBootstrapClassPath().getURLs();
		// 遍歷、輸出根類別載入器載入的全部URL
		for (int i = 0; i < urls.length; i++)
		{
			System.out.println(urls[i].toExternalForm());
		}
	}
}

