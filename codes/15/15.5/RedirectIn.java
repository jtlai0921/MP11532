
import java.util.*;
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
public class RedirectIn
{
	public static void main(String[] args)
	{
		try(
			FileInputStream fis = new FileInputStream("RedirectIn.java"))
		{
			// 將標準輸入重定向到fis輸入串流
			System.setIn(fis);
			// 使用System.in建立Scanner物件，用於獲取標準輸入
			Scanner sc = new Scanner(System.in);
			// 增加下面一行將只把Enter作為分隔符
			sc.useDelimiter("\n");
			// 判斷是否還有下一個輸入項
			while(sc.hasNext())
			{
				// 輸出輸入項
				System.out.println("鍵盤輸入的內容是：" + sc.next());
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}

