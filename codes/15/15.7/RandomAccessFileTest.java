
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
public class RandomAccessFileTest
{
	public static void main(String[] args)
	{
		try(
			RandomAccessFile raf =  new RandomAccessFile(
				"RandomAccessFileTest.java" , "r"))
		{
			// 獲取RandomAccessFile物件檔案指位器的位置，初始位置是0
			System.out.println("RandomAccessFile的檔案指位器的初始位置："
				+ raf.getFilePointer());
			// 移動raf的檔案記錄指位器的位置
			raf.seek(300);
			byte[] bbuf = new byte[1024];
			// 用於存放實際讀取的位元組數
			int hasRead = 0;
			// 使用迴圈來重複「取水」過程
			while ((hasRead = raf.read(bbuf)) > 0 )
			{
				// 取出「竹筒」中水滴（位元組），將位元組陣列轉換成字串輸入！
				System.out.print(new String(bbuf , 0 , hasRead ));
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
