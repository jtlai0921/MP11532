
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.concurrent.*;
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
public class AIOServer
{
	static final int PORT = 30000;
	final static String UTF_8 = "utf-8";
	static List<AsynchronousSocketChannel> channelList
		= new ArrayList<>();
	public void startListen() throws InterruptedException,
		Exception
	{
		// 建立一個執行緒池
		ExecutorService executor = Executors.newFixedThreadPool(20);
		// 以指定執行緒池來建立一個AsynchronousChannelGroup
		AsynchronousChannelGroup channelGroup = AsynchronousChannelGroup
			.withThreadPool(executor);
		// 以指定執行緒池來建立一個AsynchronousServerSocketChannel
		AsynchronousServerSocketChannel serverChannel
			= AsynchronousServerSocketChannel.open(channelGroup)
			// 指定監聽本機的PORT連接埠
			.bind(new InetSocketAddress(PORT));
		// 使用CompletionHandler接受來自客戶端的連線請求
		serverChannel.accept(null, new AcceptHandler(serverChannel));  // ①
		Thread.sleep(5000);
	}
	public static void main(String[] args)
		throws Exception
	{
		AIOServer server = new AIOServer();
		server.startListen();
	}
}
// 實作自己的CompletionHandler類別
class AcceptHandler implements
	CompletionHandler<AsynchronousSocketChannel, Object>
{
	private AsynchronousServerSocketChannel serverChannel;
	public AcceptHandler(AsynchronousServerSocketChannel sc)
	{
		this.serverChannel = sc;
	}
	// 定義一個ByteBuffer準備讀取資料
	ByteBuffer buff = ByteBuffer.allocate(1024);
	// 當實際IO操作完成時候觸發該方法
	@Override
	public void completed(final AsynchronousSocketChannel sc
		, Object attachment)
	{
		// 記錄新連線進來的Channel
		AIOServer.channelList.add(sc);
		// 準備接受客戶端的下一次連線
		serverChannel.accept(null , this);
		sc.read(buff , null
			, new CompletionHandler<Integer,Object>()  // ②
		{
			@Override
			public void completed(Integer result
				, Object attachment)
			{
				buff.flip();
				// 將buff中內容轉換為字串
				String content = StandardCharsets.UTF_8
					.decode(buff).toString();
				// 遍歷每個Channel，將收到的資訊寫入各Channel中
				for(AsynchronousSocketChannel c : AIOServer.channelList)
				{
					try
					{
						c.write(ByteBuffer.wrap(content.getBytes(
							AIOServer.UTF_8))).get();
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				buff.clear();
				// 讀取下一次資料
				sc.read(buff , null , this);
			}
			@Override
			public void failed(Throwable ex, Object attachment)
			{
				System.out.println("讀取資料失敗: " + ex);
				// 從該Channel讀取資料失敗，就將該Channel刪除
				AIOServer.channelList.remove(sc);
			}
		});
	}
	@Override
	public void failed(Throwable ex, Object attachment)
	{
		System.out.println("連線失敗: " + ex);
	}
}