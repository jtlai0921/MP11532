
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
public class FileTest
{
	public static void main(String[] args)
		throws IOException
	{
		// 以當前路徑來建立一個File物件
		File file = new File(".");
		// 直接獲取檔名，輸出一點
		System.out.println(file.getName());
		// 獲取相對路徑的父路徑可能出錯，下面程式碼輸出null
		System.out.println(file.getParent());
		// 獲取絕對路徑
		System.out.println(file.getAbsoluteFile());
		// 獲取上一級路徑
		System.out.println(file.getAbsoluteFile().getParent());
		// 在當前路徑下建立一個暫存檔
		File tmpFile = File.createTempFile("aaa", ".txt", file);
		// 指定當JVM結束時刪除該檔案
		tmpFile.deleteOnExit();
		// 以系統當前時間作為新檔名來建立新檔案
		File newFile = new File(System.currentTimeMillis() + "");
		System.out.println("newFile物件是否存在：" + newFile.exists());
		// 以指定newFile物件來建立一個檔案
		newFile.createNewFile();
		// 以newFile物件來建立一個目錄，因為newFile已經存在，
		// 所以下面方法返回false，即無法建立該目錄
		newFile.mkdir();
		// 使用list()方法來列出當前路徑下的所有檔案和路徑
		String[] fileList = file.list();
		System.out.println("====當前路徑下所有檔案和路徑如下====");
		for (String fileName : fileList)
		{
			System.out.println(fileName);
		}
		// listRoots()靜態方法列出所有的磁碟根路徑。
		File[] roots = File.listRoots();
		System.out.println("====系統所有根路徑如下====");
		for (File root : roots)
		{
			System.out.println(root);
		}
	}
}

