
import java.io.*;
import java.util.*;
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
public class WriteToProcess
{
	public static void main(String[] args)
		throws IOException
	{
		// 運行java ReadStandard命令，返回運行該命令的子處理序
		Process p = Runtime.getRuntime().exec("java ReadStandard");
		try(
			// 以p處理序的輸出流建立PrintStream物件
			// 這個輸出串流對本程式是輸出串流，對p處理序則是輸入串流
			PrintStream ps = new PrintStream(p.getOutputStream()))
		{
			// 向ReadStandard程式寫入內容，這些內容將被ReadStandard讀取
			ps.println("普通字串");
			ps.println(new WriteToProcess());
		}
	}
}
// 定義一個ReadStandard類別，該類別可以接受標準輸入，
// 並將標準輸入寫入out.txt檔案。
class ReadStandard
{
	public static void main(String[] args)
	{
		try(
			// 使用System.in建立Scanner物件，用於獲取標準輸入
			Scanner sc = new Scanner(System.in);
			PrintStream ps = new PrintStream(
			new FileOutputStream("out.txt")))
		{
			// 增加下面一行將只把Enter作為分隔符
			sc.useDelimiter("\n");
			// 判斷是否還有下一個輸入項
			while(sc.hasNext())
			{
				// 輸出輸入項
				ps.println("鍵盤輸入的內容是：" + sc.next());
			}
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
