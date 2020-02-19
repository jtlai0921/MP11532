
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
public class Server
{
	public static void main(String[] args)
		throws IOException
	{
		// 建立一個ServerSocket，用於監聽客戶端Socket的連線請求
		ServerSocket ss = new ServerSocket(30000);
		// 採用迴圈不斷接受來自客戶端的請求
		while (true)
		{
			// 每當接受到客戶端Socket的請求，伺服器端也對應產生一個Socket
			Socket s = ss.accept();
			// 將Socket對應的輸出串流包裝成PrintStream
			PrintStream ps = new PrintStream(s.getOutputStream());
			// 進行普通IO操作
			ps.println("您好，您收到了伺服器的新年祝福！");
			// 關閉輸出串流，關閉Socket
			ps.close();
			s.close();
		}
	}
}

