
import java.net.*;
import java.io.*;
import javax.swing.*;
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
public class Client
{
	private static final int SERVER_PORT = 30000;
	private Socket socket;
	private PrintStream ps;
	private BufferedReader brServer;
	private BufferedReader keyIn;
	public void init()
	{
		try
		{
			// 初始化代表鍵盤的輸入串流
			keyIn = new BufferedReader(
				new InputStreamReader(System.in));
			// 連線到伺服器
			socket = new Socket("127.0.0.1", SERVER_PORT);
			// 獲取該Socket對應的輸入串流和輸出串流
			ps = new PrintStream(socket.getOutputStream());
			brServer = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
			String tip = "";
			// 採用迴圈不斷地彈出對話方塊要求輸入使用者名稱
			while(true)
			{
				String userName = JOptionPane.showInputDialog(tip
					+ "輸入使用者名稱");    //①
				// 將使用者輸入的使用者名稱的前後增加協定字串後發送
				ps.println(CrazyitProtocol.USER_ROUND + userName
					+ CrazyitProtocol.USER_ROUND);
				// 讀取伺服器的回應
				String result = brServer.readLine();
				// 如果使用者重複，開始下次迴圈
				if (result.equals(CrazyitProtocol.NAME_REP))
				{
					tip = "使用者名稱重複！請重新";
					continue;
				}
				// 如果伺服器返回登入成功，結束迴圈
				if (result.equals(CrazyitProtocol.LOGIN_SUCCESS))
				{
					break;
				}
			}
		}
		// 捕捉到異常，關閉網路資源，並結束該程式
		catch (UnknownHostException ex)
		{
			System.out.println("找不到遠端伺服器，請確定伺服器已經啟動！");
			closeRs();
			System.exit(1);
		}
		catch (IOException ex)
		{
			System.out.println("網路異常！請重新登入！");
			closeRs();
			System.exit(1);
		}
		// 以該Socket對應的輸入串流啟動ClientThread執行緒
		new ClientThread(brServer).start();
	}
	// 定義一個讀取鍵盤輸出，並向網路發送的方法
	private void readAndSend()
	{
		try
		{
			// 不斷讀取鍵盤輸入
			String line = null;
			while((line = keyIn.readLine()) != null)
			{
				// 如果發送的資訊中有冒號，且以//開頭，則認為想發送私人訊息
				if (line.indexOf(":") > 0 && line.startsWith("//"))
				{
					line = line.substring(2);
					ps.println(CrazyitProtocol.PRIVATE_ROUND +
					line.split(":")[0] + CrazyitProtocol.SPLIT_SIGN
						+ line.split(":")[1] + CrazyitProtocol.PRIVATE_ROUND);
				}
				else
				{
					ps.println(CrazyitProtocol.MSG_ROUND + line
						+ CrazyitProtocol.MSG_ROUND);
				}
			}
		}
		// 捕捉到異常，關閉網路資源，並結束該程式
		catch (IOException ex)
		{
			System.out.println("網路通訊異常！請重新登入！");
			closeRs();
			System.exit(1);
		}
	}
	// 關閉Socket、輸入串流、輸出串流的方法
	private void closeRs()
	{
		try
		{
			if (keyIn != null)
			{
				ps.close();
			}
			if (brServer != null)
			{
				ps.close();
			}
			if (ps != null)
			{
				ps.close();
			}
			if (socket != null)
			{
				keyIn.close();
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
	public static void main(String[] args)
	{
		Client client = new Client();
		client.init();
		client.readAndSend();
	}
}
