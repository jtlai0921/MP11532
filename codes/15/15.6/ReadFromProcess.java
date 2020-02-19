
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
public class ReadFromProcess
{
	public static void main(String[] args)
		throws IOException
	{
		// 運行javac命令，返回運行該命令的子處理序
		Process p = Runtime.getRuntime().exec("javac");
		try(
			// 以p處理序的錯誤串流建立BufferedReader物件
			// 這個錯誤串流對本程式是輸入串流，對p處理序則是輸出串流
			BufferedReader br = new BufferedReader(new
				InputStreamReader(p.getErrorStream())))
		{
			String buff = null;
			// 採取迴圈方式來讀取p處理序的錯誤輸出
			while((buff = br.readLine()) != null)
			{
				System.out.println(buff);
			}
		}
	}
}

