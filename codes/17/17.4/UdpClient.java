
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
public class UdpClient
{
	// 定義發送資料包的目的地
	public static final int DEST_PORT = 30000;
	public static final String DEST_IP = "127.0.0.1";
	// 定義每個資料包的最大大小為4K
	private static final int DATA_LEN = 4096;
	// 定義接收網路資料的位元組陣列
	byte[] inBuff = new byte[DATA_LEN];
	// 以指定位元組陣列建立準備接受資料的DatagramPacket物件
	private DatagramPacket inPacket =
		new DatagramPacket(inBuff , inBuff.length);
	// 定義一個用於發送的DatagramPacket物件
	private DatagramPacket outPacket = null;
	public void init()throws IOException
	{
		try(
			// 建立一個客戶端DatagramSocket，使用隨機連接埠
			DatagramSocket socket = new DatagramSocket())
		{
			// 初始化發送用的DatagramSocket，它包含一個長度為0的位元組陣列
			outPacket = new DatagramPacket(new byte[0] , 0
				, InetAddress.getByName(DEST_IP) , DEST_PORT);
			// 建立鍵盤輸入流
			Scanner scan = new Scanner(System.in);
			// 不斷讀取鍵盤輸入
			while(scan.hasNextLine())
			{
				// 將鍵盤輸入的一行字串轉換位元組陣列
				byte[] buff = scan.nextLine().getBytes();
				// 設置發送用的DatagramPacket裡的位元組資料
				outPacket.setData(buff);
				// 發送資料包
				socket.send(outPacket);
				// 讀取Socket中的資料，讀到的資料放在inPacket所封裝的位元組陣列裡。
				socket.receive(inPacket);
				System.out.println(new String(inBuff , 0
					, inPacket.getLength()));
			}
		}
	}
	public static void main(String[] args)
		throws IOException
	{
		new UdpClient().init();
	}
}
