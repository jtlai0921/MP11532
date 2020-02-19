
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
public class FileReaderTest
{
	public static void main(String[] args)
	{
		try(
			// 建立字元輸入串流
			FileReader fr = new FileReader("FileReaderTest.java"))
		{
			// 建立一個長度為32的「竹筒」
			char[] cbuf = new char[32];
			// 用於存放實際讀取的字元數
			int hasRead = 0;
			// 使用迴圈來重複「取水」過程
			while ((hasRead = fr.read(cbuf)) > 0 )
			{
				// 取出「竹筒」中水滴（字元），將字元陣列轉換成字串輸入！
				System.out.print(new String(cbuf , 0 , hasRead));
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
