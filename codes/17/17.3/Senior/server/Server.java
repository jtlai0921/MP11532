
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
	private static final int SERVER_PORT = 30000;
	// 使用CrazyitMap物件來存放每個客戶名字和對應輸出串流之間的對應關係。
	public static CrazyitMap<String , PrintStream> clients
		= new CrazyitMap<>();
	public void init()
	{
		try(
			// 建立監聽的ServerSocket
			ServerSocket ss = new ServerSocket(SERVER_PORT))
		{
			// 採用無限迴圈來不斷接受來自客戶端的請求
			while(true)
			{
				Socket socket = ss.accept();
				new ServerThread(socket).start();
			}
		}
		// 如果拋出異常
		catch (IOException ex)
		{
			System.out.println("伺服器啟動失敗，是否連接埠"
				+ SERVER_PORT + "已被佔用？");
		}
	}
	public static void main(String[] args)
	{
		Server server = new Server();
		server.init();
	}
}

