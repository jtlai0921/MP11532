
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
public class PushbackTest
{
	public static void main(String[] args)
	{
		try(
			// 建立一個PushbackReader物件，指定推回緩衝區的長度為64
			PushbackReader pr = new PushbackReader(new FileReader(
				"PushbackTest.java") , 64))
		{
			char[] buf = new char[32];
			// 用以存放上次讀取的字串內容
			String lastContent = "";
			int hasRead = 0;
			// 迴圈讀取檔案內容
			while ((hasRead = pr.read(buf)) > 0)
			{
				// 將讀取的內容轉換成字串
				String content = new String(buf , 0 , hasRead);
				int targetIndex = 0;
				// 將上次讀取的字串和本次讀取的字串拼起來，
				// 查看是否包含目標字串, 如果包含目標字串
				if ((targetIndex = (lastContent + content)
					.indexOf("new PushbackReader")) > 0)
				{
					// 將本次內容和上次內容一起推回緩衝區
					pr.unread((lastContent + content).toCharArray());
					// 重新定義一個長度為targetIndex的char陣列
					if(targetIndex > 32)
					{
						buf = new char[targetIndex];
					}
					// 再次讀取指定長度的內容（就是目標字串之前的內容）
					pr.read(buf , 0 , targetIndex);
					// 列印讀取的內容
					System.out.print(new String(buf , 0 ,targetIndex));
					System.exit(0);
				}
				else
				{
					// 列印上次讀取的內容
					System.out.print(lastContent);
					// 將本次內容設為上次讀取的內容
					lastContent = content;
				}
			}
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}

