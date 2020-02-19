
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
public class FilenameFilterTest
{
	public static void main(String[] args)
	{
		File file = new File(".");
		// 使用Lambda運算式（目標類型為FilenameFilter）實作檔案過濾器。
		// 如果檔名以.java結尾，或者檔案對應一個路徑，返回true
		String[] nameList = file.list((dir, name) -> name.endsWith(".java")
			|| new File(name).isDirectory());
		for(String name : nameList)
		{
			System.out.println(name);
		}
	}
}
