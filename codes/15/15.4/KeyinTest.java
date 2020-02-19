
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
public class KeyinTest
{
	public static void main(String[] args)
	{
		try(
			// 將Sytem.in物件轉換成Reader物件
			InputStreamReader reader = new InputStreamReader(System.in);
			// 將普通Reader包裝成BufferedReader
			BufferedReader br = new BufferedReader(reader))
		{
			String line = null;
			// 採用迴圈方式來一行一行的讀取
			while ((line = br.readLine()) != null)
			{
				// 如果讀取的字串為"exit"，程式結束
				if (line.equals("exit"))
				{
					System.exit(1);
				}
				// 列印讀取的內容
				System.out.println("輸入內容為:" + line);
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
