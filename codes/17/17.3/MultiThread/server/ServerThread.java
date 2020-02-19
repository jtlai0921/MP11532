
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
// 負責處理每個執行緒通訊的執行緒類別
public class ServerThread implements Runnable
{
	// 定義當前執行緒所處理的Socket
	Socket s = null;
	// 該執行緒所處理的Socket所對應的輸入串流
	BufferedReader br = null;
	public ServerThread(Socket s)
	throws IOException
	{
		this.s = s;
		// 初始化該Socket對應的輸入串流
		br = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	public void run()
	{
		try
		{
			String content = null;
			// 採用迴圈不斷從Socket中讀取客戶端發送過來的資料
			while ((content = readFromClient()) != null)
			{
				// 遍歷socketList中的每個Socket，
				// 將讀到的內容向每個Socket發送一次
				for (Socket s : MyServer.socketList)
				{
					PrintStream ps = new PrintStream(s.getOutputStream());
					ps.println(content);
				}
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	// 定義讀取客戶端資料的方法
	private String readFromClient()
	{
		try
		{
			return br.readLine();
		}
		// 如果捕捉到異常，表明該Socket對應的客戶端已經關閉
		catch (IOException e)
		{
			// 刪除該Socket。
			MyServer.socketList.remove(s);      // ①
		}
		return null;
	}
}
