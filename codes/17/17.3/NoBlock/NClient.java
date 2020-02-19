
import java.util.*;
import java.net.*;
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
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
public class NClient
{
	// 定義檢測SocketChannel的Selector物件
	private Selector selector = null;
	static final int PORT = 30000;
	// 定義處理編碼和解碼的字元集
	private Charset charset = Charset.forName("UTF-8");
	// 客戶端SocketChannel
	private SocketChannel sc = null;
	public void init()throws IOException
	{
		selector = Selector.open();
		InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);
		// 呼叫open靜態方法建立連線到指定主機的SocketChannel
		sc = SocketChannel.open(isa);
		// 設置該sc以非阻擋方式工作
		sc.configureBlocking(false);
		// 將SocketChannel物件註冊到指定Selector
		sc.register(selector, SelectionKey.OP_READ);
		// 啟動讀取伺服器端資料的執行緒
		new ClientThread().start();
		// 建立鍵盤輸入流
		Scanner scan = new Scanner(System.in);
		while (scan.hasNextLine())
		{
			// 讀取鍵盤輸入
			String line = scan.nextLine();
			// 將鍵盤輸入的內容輸出到SocketChannel中
			sc.write(charset.encode(line));
		}
	}
	// 定義讀取伺服器資料的執行緒
	private class ClientThread extends Thread
	{
		public void run()
		{
			try
			{
				while (selector.select() > 0)    // ①
				{
					// 遍歷每個有可用IO操作Channel對應的SelectionKey
					for (SelectionKey sk : selector.selectedKeys())
					{
						// 刪除正在處理的SelectionKey
						selector.selectedKeys().remove(sk);
						// 如果該SelectionKey對應的Channel中有可讀的資料
						if (sk.isReadable())
						{
							// 使用NIO讀取Channel中的資料
							SocketChannel sc = (SocketChannel)sk.channel();
							ByteBuffer buff = ByteBuffer.allocate(1024);
							String content = "";
							while(sc.read(buff) > 0)
							{
								sc.read(buff);
								buff.flip();
								content += charset.decode(buff);
							}
							// 列印輸出讀取的內容
							System.out.println("聊天訊息：" + content);
							// 為下一次讀取作準備
							sk.interestOps(SelectionKey.OP_READ);
						}
					}
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
	public static void main(String[] args)
		throws IOException
	{
		new NClient().init();
	}
}
