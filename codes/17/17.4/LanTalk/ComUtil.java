
import java.util.*;
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
// 聊天交換資訊的工具類別
public class ComUtil
{
	// 定義本程式通訊所使用的字元集
	public static final String CHARSET = "utf-8";
	// 使用常數作為本程式的多點廣播IP位址
	private static final String BROADCAST_IP
		= "230.0.0.1";
	// 使用常數作為本程式的多點廣播目的的連接埠
	// DatagramSocket所用的的連接埠為該連接埠+1。
	public static final int BROADCAST_PORT = 30000;
	// 定義每個資料包的最大大小為4K
	private static final int DATA_LEN = 4096;
	// 定義本程式的MulticastSocket實例
	private MulticastSocket socket = null;
	// 定義本程式私訊的Socket實例
	private DatagramSocket singleSocket = null;
	// 定義廣播的IP位址
	private InetAddress broadcastAddress = null;
	// 定義接收網路資料的位元組陣列
	byte[] inBuff = new byte[DATA_LEN];
	// 以指定位元組陣列建立準備接受資料的DatagramPacket物件
	private DatagramPacket inPacket =
		new DatagramPacket(inBuff , inBuff.length);
	// 定義一個用於發送的DatagramPacket物件
	private DatagramPacket outPacket = null;
	// 聊天的主介面程式
	private LanTalk lanTalk;
	// 建構子，初始化資源
	public ComUtil(LanTalk lanTalk) throws Exception
	{
		this.lanTalk = lanTalk;
		// 建立用於發送、接收資料的MulticastSocket物件
		// 因為該MulticastSocket物件需要接收，所以有指定連接埠
		socket = new MulticastSocket(BROADCAST_PORT);
		// 建立私訊用的DatagramSocket物件
		singleSocket = new DatagramSocket(BROADCAST_PORT + 1);
		broadcastAddress = InetAddress.getByName(BROADCAST_IP);
		// 將該socket加入指定的多點廣播位址
		socket.joinGroup(broadcastAddress);
		// 設置本MulticastSocket發送的資料包被回送到自身
		socket.setLoopbackMode(false);
		// 初始化發送用的DatagramSocket，它包含一個長度為0的位元組陣列
		outPacket = new DatagramPacket(new byte[0]
			, 0 , broadcastAddress , BROADCAST_PORT);
		// 啟動兩個讀取網路資料的執行緒
		new ReadBroad().start();
		Thread.sleep(1);
		new ReadSingle().start();
	}
	// 廣播訊息的工具方法
	public void broadCast(String msg)
	{
		try
		{
			// 將msg字串轉換位元組陣列
			byte[] buff = msg.getBytes(CHARSET);
			// 設置發送用的DatagramPacket裡的位元組資料
			outPacket.setData(buff);
			// 發送資料包
			socket.send(outPacket);
		}
		// 捕捉異常
		catch (IOException ex)
		{
			ex.printStackTrace();
			if (socket != null)
			{
				// 關閉該Socket物件
				socket.close();
			}
			JOptionPane.showMessageDialog(null
				, "發送資訊異常，請確認30000連接埠空閒，且網路連線正常！"
				, "網路異常", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	// 定義向單獨使用者發送訊息的方法
	public void sendSingle(String msg , SocketAddress dest)
	{
		try
		{
			// 將msg字串轉換位元組陣列
			byte[] buff = msg.getBytes(CHARSET);
			DatagramPacket packet = new DatagramPacket(buff
				, buff.length , dest);
			singleSocket.send(packet);
		}
		// 捕捉異常
		catch (IOException ex)
		{
			ex.printStackTrace();
			if (singleSocket != null)
			{
				// 關閉該Socket物件
				singleSocket.close();
			}
			JOptionPane.showMessageDialog(null
				, "發送資訊異常，請確認30001連接埠空閒，且網路連線正常！"
				, "網路異常", JOptionPane.ERROR_MESSAGE);
			System.exit(1);
		}
	}
	// 不斷從DatagramSocket中讀取資料的執行緒
	class ReadSingle extends Thread
	{
		// 定義接收網路資料的位元組陣列
		byte[] singleBuff = new byte[DATA_LEN];
		private DatagramPacket singlePacket =
			new DatagramPacket(singleBuff , singleBuff.length);
		public void run()
		{
			while (true)
			{
				try
				{
					// 讀取Socket中的資料。
					singleSocket.receive(singlePacket);
					// 處理讀到的資訊
					lanTalk.processMsg(singlePacket , true);
				}
				// 捕捉異常
				catch (IOException ex)
				{
					ex.printStackTrace();
					if (singleSocket != null)
					{
						// 關閉該Socket物件
						singleSocket.close();
					}
					JOptionPane.showMessageDialog(null
						, "接收資訊異常，請確認30001連接埠空閒，且網路連線正常！"
						, "網路異常", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
		}
	}
	// 持續讀取MulticastSocket的執行緒
	class ReadBroad extends Thread
	{
		public void run()
		{
			while (true)
			{
				try
				{
					// 讀取Socket中的資料。
					socket.receive(inPacket);
					// 列印輸出從socket中讀取的內容
					String msg = new String(inBuff , 0
						, inPacket.getLength() , CHARSET);
					// 讀到的內容是私人訊息
					if (msg.startsWith(YeekuProtocol.PRESENCE)
						&& msg.endsWith(YeekuProtocol.PRESENCE))
					{
						String userMsg = msg.substring(2
							, msg.length() - 2);
						String[] userInfo = userMsg.split(YeekuProtocol
							.SPLITTER);
						UserInfo user = new UserInfo(userInfo[1]
							, userInfo[0] , inPacket.getSocketAddress(), 0);
						// 控制是否需要添加該使用者的旗標
						boolean addFlag = true;
						ArrayList<Integer> delList = new ArrayList<>();
						// 遍歷系統中已有的所有使用者,該迴圈必須循環完成
						for (int i = 1 ; i < lanTalk.getUserNum() ; i++ )
						{
							UserInfo current = lanTalk.getUser(i);
							// 將所有使用者失去聯繫的次數加1
							current.setLost(current.getLost() + 1);
							// 如果該資訊由指定使用者發送過來
							if (current.equals(user))
							{
								current.setLost(0);
								// 設置該使用者無須添加
								addFlag = false;
							}
							if (current.getLost() > 2)
							{
								delList.add(i);
							}
						}
						// 刪除delList中的所有索引對應的使用者
						for (int i = 0; i < delList.size() ; i++)
						{
							lanTalk.removeUser(delList.get(i));
						}
						if (addFlag)
						{
							// 添加新使用者
							lanTalk.addUser(user);
						}
					}
					// 讀到的內容是公開訊息
					else
					{
						// 處理讀到的訊息
						lanTalk.processMsg(inPacket , false);
					}
				}
				// 捕捉異常
				catch (IOException ex)
				{
					ex.printStackTrace();
					if (socket != null)
					{
						// 關閉該Socket物件
						socket.close();
					}
					JOptionPane.showMessageDialog(null
						, "接收資訊異常，請確認30000連接埠空閒，且網路連線正常！"
						, "網路異常", JOptionPane.ERROR_MESSAGE);
					System.exit(1);
				}
			}
		}
	}
}
