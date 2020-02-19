
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
public class ServerThread extends Thread
{
	private Socket socket;
	BufferedReader br = null;
	PrintStream ps = null;
	// 定義一個建構子，用於接收一個Socket來建立ServerThread執行緒
	public ServerThread(Socket socket)
	{
		this.socket = socket;
	}
	public void run()
	{
		try
		{
			// 獲取該Socket對應的輸入串流
			br = new BufferedReader(new InputStreamReader(socket
				.getInputStream()));
			// 獲取該Socket對應的輸出串流
			ps = new PrintStream(socket.getOutputStream());
			String line = null;
			while((line = br.readLine())!= null)
			{
				// 如果讀到的行以CrazyitProtocol.USER_ROUND開始，並以其結束，
				// 可以確定讀到的是使用者登入的使用者名稱
				if (line.startsWith(CrazyitProtocol.USER_ROUND)
					&& line.endsWith(CrazyitProtocol.USER_ROUND))
				{
					// 得到真實訊息
					String userName = getRealMsg(line);
					// 如果使用者名稱重複
					if (Server.clients.map.containsKey(userName))
					{
						System.out.println("重複");
						ps.println(CrazyitProtocol.NAME_REP);
					}
					else
					{
						System.out.println("成功");
						ps.println(CrazyitProtocol.LOGIN_SUCCESS);
						Server.clients.put(userName , ps);
					}
				}
				// 如果讀到的行以CrazyitProtocol.PRIVATE_ROUND開始，並以其結束，
				// 可以確定是私人訊息，私人訊息只向特定的輸出串流發送
				else if (line.startsWith(CrazyitProtocol.PRIVATE_ROUND)
					&& line.endsWith(CrazyitProtocol.PRIVATE_ROUND))
				{
					// 得到真實訊息
					String userAndMsg = getRealMsg(line);
					// 以SPLIT_SIGN分割字串，前半是私訊使用者，後半是聊天訊息
					String user = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[0];
					String msg = userAndMsg.split(CrazyitProtocol.SPLIT_SIGN)[1];
					// 獲取私訊使用者對應的輸出串流，並發送私人訊息
					Server.clients.map.get(user).println(Server.clients
						.getKeyByValue(ps) + "悄悄地對你說：" + msg);
				}
				// 公開要向每個Socket發送
				else
				{
					// 得到真實訊息
					String msg = getRealMsg(line);
					// 遍歷clients中的每個輸出串流
					for (PrintStream clientPs : Server.clients.valueSet())
					{
						clientPs.println(Server.clients.getKeyByValue(ps)
							+ "說：" + msg);
					}
				}
			}
		}
		// 捕捉到異常後，表明該Socket對應的客戶端已經出現了問題
		// 所以程式將其對應的輸出流從Map中刪除
		catch (IOException e)
		{
			Server.clients.removeByValue(ps);
			System.out.println(Server.clients.map.size());
			// 關閉網路、IO資源
			try
			{
				if (br != null)
				{
					br.close();
				}
				if (ps != null)
				{
					ps.close();
				}
				if (socket != null)
				{
					socket.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	// 將讀到的內容去掉前後的協定字元，恢覆成真實資料
	private String getRealMsg(String line)
	{
		return line.substring(CrazyitProtocol.PROTOCOL_LEN
			, line.length() - CrazyitProtocol.PROTOCOL_LEN);
	}
}
