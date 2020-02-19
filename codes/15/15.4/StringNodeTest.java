
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
public class StringNodeTest
{
	public static void main(String[] args)
	{
		String src = "從明天起，做一個幸福的人\n"
			+ "餵馬，劈柴，周遊世界\n"
			+ "從明天起，關心糧食和蔬菜\n"
			+ "我有一所房子，面朝大海，春暖花開\n"
			+ "從明天起，和每一個親人通訊\n"
			+ "告訴他們我的幸福\n";
		char[] buffer = new char[32];
		int hasRead = 0;
		try(
			StringReader sr = new StringReader(src))
		{
			// 採用迴圈讀取的存取讀取字串
			while((hasRead = sr.read(buffer)) > 0)
			{
				System.out.print(new String(buffer ,0 , hasRead));
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
		try(
			// 建立StringWriter時，實際上以一個StringBuffer作為輸出節點
			// 下面指定的20就是StringBuffer的初始長度
			StringWriter sw = new StringWriter())
		{
			// 呼叫StringWriter的方法執行輸出
			sw.write("有一個美麗的新世界，\n");
			sw.write("她在遠方等我,\n");
			sw.write("哪裡有天真的孩子，\n");
			sw.write("還有姑娘的酒窩\n");
			System.out.println("----下面是sw的字串節點裡的內容----");
			// 使用toString()方法返回StringWriter的字串節點的內容
			System.out.println(sw.toString());
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}
