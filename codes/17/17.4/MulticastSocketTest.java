
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
// 讓該類別實作Runnable介面，該類別的實例可作為執行緒的target
public class MulticastSocketTest implements Runnable
{
	// 使用常數作為本程式的多點廣播IP位址
	private static final String BROADCAST_IP
		= "230.0.0.1";
	// 使用常數作為本程式的多點廣播目的的連接埠
	public static final int BROADCAST_PORT = 30000;
	// 定義每個資料包的最大大小為4K
	private static final int DATA_LEN = 4096;
	//定義本程式的MulticastSocket實例
	private MulticastSocket socket = null;
	private InetAddress broadcastAddress = null;
	private Scanner scan = null;
	// 定義接收網路資料的位元組陣列
	byte[] inBuff = new byte[DATA_LEN];
	// 以指定位元組陣列建立準備接受資料的DatagramPacket物件
	private DatagramPacket inPacket
		= new DatagramPacket(inBuff , inBuff.length);
	// 定義一個用於發送的DatagramPacket物件
	private DatagramPacket outPacket = null;
	public void init()throws IOException
	{
		try(
			// 建立鍵盤輸入串流
			Scanner scan = new Scanner(System.in))
		{
			// 建立用於發送、接收資料的MulticastSocket物件
			// 由於該MulticastSocket物件需要接收資料，所以有指定連接埠
			socket = new MulticastSocket(BROADCAST_PORT);
			broadcastAddress = InetAddress.getByName(BROADCAST_IP);
			// 將該socket加入指定的多點廣播位址
			socket.joinGroup(broadcastAddress);
			// 設置本MulticastSocket發送的資料包會被回送到自身
			socket.setLoopbackMode(false);
			// 初始化發送用的DatagramSocket，它包含一個長度為0的位元組陣列
			outPacket = new DatagramPacket(new byte[0]
				, 0 , broadcastAddress , BROADCAST_PORT);
			// 啟動以本實例的run()方法作為執行緒體的執行緒
			new Thread(this).start();
			// 不斷讀取鍵盤輸入
			while(scan.hasNextLine())
			{
				// 將鍵盤輸入的一行字串轉換位元組陣列
				byte[] buff = scan.nextLine().getBytes();
				// 設置發送用的DatagramPacket裡的位元組資料
				outPacket.setData(buff);
				// 發送資料包
				socket.send(outPacket);
			}
		}
		finally
		{
			socket.close();
		}
	}
	public void run()
	{
		try
		{
			while(true)
			{
				// 讀取Socket中的資料，讀到的資料放在inPacket所封裝的位元組陣列裡。
				socket.receive(inPacket);
				// 列印輸出從socket中讀取的內容
				System.out.println("聊天訊息：" + new String(inBuff
					, 0 , inPacket.getLength()));
			}
		}
		// 捕捉異常
		catch (IOException ex)
		{
			ex.printStackTrace();
			try
			{
				if (socket != null)
				{
					// 讓該Socket離開該多點IP廣播位址
					socket.leaveGroup(broadcastAddress);
					// 關閉該Socket物件
					socket.close();
				}
				System.exit(1);
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args)
		throws IOException
	{
		new MulticastSocketTest().init();
	}
}

