
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
public class FileInputStreamTest
{
	public static void main(String[] args) throws IOException
	{
		// 建立位元組輸入串流
		FileInputStream fis = new FileInputStream(
			"FileInputStreamTest.java");
		// 建立一個長度為1024的「竹筒」
		byte[] bbuf = new byte[1024];
		// 用於存放實際讀取的位元組數
		int hasRead = 0;
		// 使用迴圈來重複「取水」過程
		while ((hasRead = fis.read(bbuf)) > 0 )
		{
			// 取出「竹筒」中水滴（位元組），將位元組陣列轉換成字串輸入！
			System.out.print(new String(bbuf , 0 , hasRead ));
		}
		// 關閉檔案輸入串流，放在finally區塊裡更安全
		fis.close();
	}
}
