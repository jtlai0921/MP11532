
import java.net.*;
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
public class SimpleAIOClient
{
	static final int PORT = 30000;
	public static void main(String[] args)
		throws Exception
	{
		// 用於讀取資料的ByteBuffer。
		ByteBuffer buff = ByteBuffer.allocate(1024);
		Charset utf = Charset.forName("utf-8");
		try(
			// ①建立AsynchronousSocketChannel物件
			AsynchronousSocketChannel clientChannel
				= AsynchronousSocketChannel.open())
		{
			// ②連線遠端伺服器
			clientChannel.connect(new InetSocketAddress("127.0.0.1"
				, PORT)).get();     // ④
			buff.clear();
			// ③從clientChannel中讀取資料
			clientChannel.read(buff).get();     // ⑤
			buff.flip();
			// 將buff中內容轉換為字串
			String content = utf.decode(buff).toString();
			System.out.println("伺服器資訊：" + content);
		}
	}
}
