
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
public class ReadFile
{
	public static void main(String[] args)
		throws IOException
	{
		try(
			// 建立檔案輸入串流
			FileInputStream fis = new FileInputStream("ReadFile.java");
			// 建立一個FileChannel
			FileChannel fcin = fis.getChannel())
		{
			// 定義一個ByteBuffer物件，用於重複取水
			ByteBuffer bbuff = ByteBuffer.allocate(256);
			// 將FileChannel中資料放入ByteBuffer中
			while( fcin.read(bbuff) != -1 )
			{
				// 鎖定Buffer的空白區
				bbuff.flip();
				// 建立Charset物件
				Charset charset = Charset.forName("GBK");
				// 建立解碼器(CharsetDecoder)物件
				CharsetDecoder decoder = charset.newDecoder();
				// 將ByteBuffer的內容轉碼
				CharBuffer cbuff = decoder.decode(bbuff);
				System.out.print(cbuff);
				// 將Buffer初始化，為下一次讀取資料做準備
				bbuff.clear();
			}
		}
	}
}
