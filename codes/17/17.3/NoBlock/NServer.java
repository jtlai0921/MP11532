
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
public class NServer
{
	// 用於檢測所有Channel狀態的Selector
	private Selector selector = null;
	static final int PORT = 30000;
	// 定義實作編碼、解碼的字元集物件
	private Charset charset = Charset.forName("UTF-8");
	public void init()throws IOException
	{
		selector = Selector.open();
		// 通過open方法來開啟一個未綁定的ServerSocketChannel實例
		ServerSocketChannel server = ServerSocketChannel.open();
		InetSocketAddress isa = new InetSocketAddress("127.0.0.1", PORT);
		// 將該ServerSocketChannel綁定到指定IP位址
		server.bind(isa);
		// 設置ServerSocket以非阻擋方式工作
		server.configureBlocking(false);
		// 將server註冊到指定Selector物件
		server.register(selector, SelectionKey.OP_ACCEPT);
		while (selector.select() > 0)
		{
			// 依次處理selector上的每個已選擇的SelectionKey
			for (SelectionKey sk : selector.selectedKeys())
			{
				// 從selector上的已選擇Key集中刪除正在處理的SelectionKey
				selector.selectedKeys().remove(sk);      // ①
				// 如果sk對應的Channel包含客戶端的連接請求
				if (sk.isAcceptable())        // ②
				{
					// 呼叫accept方法接受連線，產生伺服器端的SocketChannel
					SocketChannel sc = server.accept();
					// 設置採用非阻擋模式
					sc.configureBlocking(false);
					// 將該SocketChannel也註冊到selector
					sc.register(selector, SelectionKey.OP_READ);
					// 將sk對應的Channel設置成準備接受其他請求
					sk.interestOps(SelectionKey.OP_ACCEPT);
				}
				// 如果sk對應的Channel有資料需要讀取
				if (sk.isReadable())     // ③
				{
					// 獲取該SelectionKey對應的Channel，該Channel中有可讀的資料
					SocketChannel sc = (SocketChannel)sk.channel();
					// 定義準備執行讀取資料的ByteBuffer
					ByteBuffer buff = ByteBuffer.allocate(1024);
					String content = "";
					// 開始讀取資料
					try
					{
						while(sc.read(buff) > 0)
						{
							buff.flip();
							content += charset.decode(buff);
						}
						// 列印從該sk對應的Channel裡讀取到的資料
						System.out.println("讀取的資料：" + content);
						// 將sk對應的Channel設置成準備下一次讀取
						sk.interestOps(SelectionKey.OP_READ);
					}
					// 如果捕捉到該sk對應的Channel出現了異常，即表明該Channel
					// 對應的Client出現了問題，所以從Selector中取消sk的註冊
					catch (IOException ex)
					{
						// 從Selector中刪除指定的SelectionKey
						sk.cancel();
						if (sk.channel() != null)
						{
							sk.channel().close();
						}
					}
					// 如果content的長度大於0，即聊天訊息不為空
					if (content.length() > 0)
					{
						// 遍歷該selector裡註冊的所有SelectionKey
						for (SelectionKey key : selector.keys())
						{
							// 獲取該key對應的Channel
							Channel targetChannel = key.channel();
							// 如果該channel是SocketChannel物件
							if (targetChannel instanceof SocketChannel)
							{
								// 將讀到的內容寫入該Channel中
								SocketChannel dest = (SocketChannel)targetChannel;
								dest.write(charset.encode(content));
							}
						}
					}
				}
			}
		}
	}
	public static void main(String[] args)
		throws IOException
	{
		new NServer().init();
	}
}
