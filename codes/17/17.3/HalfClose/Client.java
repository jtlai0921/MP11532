
import java.io.*;
import java.net.*;
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
public class Client
{
	public static void main(String[] args)
		throws Exception
	{
		Socket s = new Socket("localhost" , 30000);
		Scanner scan = new Scanner(s.getInputStream());
		while (scan.hasNextLine())
		{
			System.out.println(scan.nextLine());
		}
		PrintStream ps = new PrintStream(s.getOutputStream());
		ps.println("客戶端的第一行資料");
		ps.println("客戶端的第二行資料");
		ps.close();
		scan.close();
		s.close();
	}
}
