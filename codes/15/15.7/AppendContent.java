
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
public class AppendContent
{
	public static void main(String[] args)
	{
		try(
			//以讀、寫方式開啟一個RandomAccessFile物件
			RandomAccessFile raf = new RandomAccessFile("out.txt" , "rw"))
		{
			//將記錄指位器移動到out.txt檔案的最後
			raf.seek(raf.length());
			raf.write("附加的內容！\r\n".getBytes());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
