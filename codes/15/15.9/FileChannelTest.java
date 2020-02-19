
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
public class FileChannelTest
{
	public static void main(String[] args)
	{
		File f = new File("FileChannelTest.java");
		try(
			// 建立FileInputStream，以該檔案輸入串流建立FileChannel
			FileChannel inChannel = new FileInputStream(f).getChannel();
			// 以檔案輸出串流建立FileBuffer，用以控制輸出
			FileChannel outChannel = new FileOutputStream("a.txt")
				.getChannel())
		{
			// 將FileChannel裡的全部資料對應成ByteBuffer
			MappedByteBuffer buffer = inChannel.map(FileChannel
				.MapMode.READ_ONLY , 0 , f.length());   // ①
			// 使用GBK的字元集來建立解碼器
			Charset charset = Charset.forName("GBK");
			// 直接將buffer裡的資料全部輸出
			outChannel.write(buffer);     // ②
			// 再次呼叫buffer的clear()方法，復原limit、position的位置
			buffer.clear();
			// 建立解碼器(CharsetDecoder)物件
			CharsetDecoder decoder = charset.newDecoder();
			// 使用解碼器將ByteBuffer轉換成CharBuffer
			CharBuffer charBuffer =  decoder.decode(buffer);
			// CharBuffer的toString方法可以獲取對應的字串
			System.out.println(charBuffer);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
