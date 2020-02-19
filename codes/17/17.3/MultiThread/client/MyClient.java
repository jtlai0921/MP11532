
import java.io.*;
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
public class MyClient
{
	public static void main(String[] args)throws Exception
	{
		Socket s = new Socket("127.0.0.1" , 30000);
		// 客戶端啟動ClientThread執行緒不斷讀取來自伺服器的資料
		new Thread(new ClientThread(s)).start();   // ①
		// 獲取該Socket對應的輸出串流
		PrintStream ps = new PrintStream(s.getOutputStream());
		String line = null;
		// 不斷讀取鍵盤輸入
		BufferedReader br = new BufferedReader(
			new InputStreamReader(System.in));
		while ((line = br.readLine()) != null)
		{
			// 將使用者的鍵盤輸入內容寫入Socket對應的輸出串流
			ps.println(line);
		}
	}
}
