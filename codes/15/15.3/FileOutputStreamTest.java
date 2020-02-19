
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
public class FileOutputStreamTest
{
	public static void main(String[] args)
	{
		try(
			// 建立位元組輸入串流
			FileInputStream fis = new FileInputStream(
				"FileOutputStreamTest.java");
			// 建立位元組輸出串流
			FileOutputStream fos = new FileOutputStream("newFile.txt"))
		{
			byte[] bbuf = new byte[32];
			int hasRead = 0;
			// 迴圈從輸入串流中取出資料
			while ((hasRead = fis.read(bbuf)) > 0 )
			{
				// 每讀取一次，即寫入檔案輸出串流，讀了多少，就寫多少。
				fos.write(bbuf , 0 , hasRead);
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
