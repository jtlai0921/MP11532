
import java.net.*;
import java.io.*;
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
public class MyServer
{
	// 定義存放所有Socket的ArrayList，並將其包裝為執行緒安全的
	public static List<Socket> socketList
		= Collections.synchronizedList(new ArrayList<>());
	public static void main(String[] args)
		throws IOException
	{
		ServerSocket ss = new ServerSocket(30000);
		while(true)
		{
			// 此行程式碼會阻擋，將一直等待別人的連線
			Socket s = ss.accept();
			socketList.add(s);
			// 每當客戶端連線後啟動一條ServerThread執行緒為該客戶端服務
			new Thread(new ServerThread(s)).start();
		}
	}
}
