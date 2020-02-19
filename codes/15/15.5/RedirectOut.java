
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
public class RedirectOut
{
	public static void main(String[] args)
	{
		try(
			// 一次性建立PrintStream輸出串流
			PrintStream ps = new PrintStream(new FileOutputStream("out.txt")))
		{
			// 將標準輸出重定向到ps輸出串流
			System.setOut(ps);
			// 向標準輸出輸出一個字串
			System.out.println("普通字串");
			// 向標準輸出輸出一個物件
			System.out.println(new RedirectOut());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}

