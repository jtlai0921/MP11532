
import java.nio.*;
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
public class CharsetTransform
{
	public static void main(String[] args)
		throws Exception
	{
		// 建立簡體中文對應的Charset
		Charset cn = Charset.forName("GBK");
		// 獲取cn物件對應的編碼器和解碼器
		CharsetEncoder cnEncoder = cn.newEncoder();
		CharsetDecoder cnDecoder = cn.newDecoder();
		// 建立一個CharBuffer物件
		CharBuffer cbuff = CharBuffer.allocate(8);
		cbuff.put('孫');
		cbuff.put('悟');
		cbuff.put('空');
		cbuff.flip();
		// 將CharBuffer中的字元序列轉換成位元組序列
		ByteBuffer bbuff = cnEncoder.encode(cbuff);
		// 迴圈存取ByteBuffer中的每個位元組
		for (int i = 0; i < bbuff.capacity() ; i++)
		{
			System.out.print(bbuff.get(i) + " ");
		}
		// 將ByteBuffer的資料解碼成字元序列
		System.out.println("\n" + cnDecoder.decode(bbuff));
	}
}
