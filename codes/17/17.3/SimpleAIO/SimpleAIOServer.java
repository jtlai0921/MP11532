
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
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
public class SimpleAIOServer
{
	static final int PORT = 30000;
	public static void main(String[] args)
		throws Exception
	{
		try(
			// ①建立AsynchronousServerSocketChannel物件。
			AsynchronousServerSocketChannel serverChannel =
				AsynchronousServerSocketChannel.open())
		{
			// ②指定在指定位址、連接埠監聽。
			serverChannel.bind(new InetSocketAddress(PORT));
			while (true)
			{
				// ③採用迴圈接受來自客戶端的連接
				Future<AsynchronousSocketChannel> future
					= serverChannel.accept();
				// 獲取連接完成後返回的AsynchronousSocketChannel
				AsynchronousSocketChannel socketChannel = future.get();
				// 執行輸出。
				socketChannel.write(ByteBuffer.wrap("歡迎你來自AIO的世界！"
					.getBytes("UTF-8"))).get();
			}
		}
	}
}
