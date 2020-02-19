
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
public class UdpServer
{
	public static final int PORT = 30000;
	// 定義每個資料包的最大大小為4K
	private static final int DATA_LEN = 4096;
	// 定義接收網路資料的位元組陣列
	byte[] inBuff = new byte[DATA_LEN];
	// 以指定位元組陣列建立準備接受資料的DatagramPacket物件
	private DatagramPacket inPacket =
		new DatagramPacket(inBuff , inBuff.length);
	// 定義一個用於發送的DatagramPacket物件
	private DatagramPacket outPacket;
	// 定義一個字串陣列，伺服器發送該陣列的的元素
	String[] books = new String[]
	{
		"瘋狂Java講義",
		"輕量級Java EE企業應用實戰",
		"瘋狂Android講義",
		"瘋狂Ajax講義"
	};
	public void init()throws IOException
	{
		try(
			// 建立DatagramSocket物件
			DatagramSocket socket = new DatagramSocket(PORT))
		{
			// 採用迴圈接受資料
			for (int i = 0; i < 1000 ; i++ )
			{
				// 讀取Socket中的資料，讀到的資料放入inPacket封裝的陣列裡。
				socket.receive(inPacket);
				// 判斷inPacket.getData()和inBuff是否是同一個陣列
				System.out.println(inBuff == inPacket.getData());
				// 將接收到的內容轉成字串後輸出
				System.out.println(new String(inBuff
					, 0 , inPacket.getLength()));
				// 從字串陣列中取出一個元素作為發送的資料
				byte[] sendData = books[i % 4].getBytes();
				// 以指定位元組陣列作為發送資料、以剛接受到的DatagramPacket的
				// 源SocketAddress作為目標SocketAddress建立DatagramPacket。
				outPacket = new DatagramPacket(sendData
					, sendData.length , inPacket.getSocketAddress());
				// 發送資料
				socket.send(outPacket);
			}
		}
	}
	public static void main(String[] args)
		throws IOException
	{
		new UdpServer().init();
	}
}