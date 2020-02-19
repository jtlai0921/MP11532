
import java.nio.file.*;
import java.nio.charset.*;
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
public class FilesTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 複製檔案
		Files.copy(Paths.get("FilesTest.java")
			, new FileOutputStream("a.txt"));
		// 判斷FilesTest.java檔案是否為隱藏檔
		System.out.println("FilesTest.java是否為隱藏檔："
			+ Files.isHidden(Paths.get("FilesTest.java")));
		// 一次性讀取FilesTest.java檔案的所有行
		List<String> lines = Files.readAllLines(Paths
			.get("FilesTest.java"), Charset.forName("gbk"));
		System.out.println(lines);
		// 判斷指定檔案的大小
		System.out.println("FilesTest.java的大小為："
			+ Files.size(Paths.get("FilesTest.java")));
		List<String> poem = new ArrayList<>();
		poem.add("水晶潭底銀魚躍");
		poem.add("清徐風中碧竿橫");
		// 直接將多個字串內容寫入指定檔案中
		Files.write(Paths.get("poem.txt") , poem
			, Charset.forName("gbk"));
		// 使用Java 8新增的Stream API列出當前目錄下所有檔案和子目錄
		Files.list(Paths.get(".")).forEach(path -> System.out.println(path));
		// 使用Java 8新增的Stream API讀取檔案內容
		Files.lines(Paths.get("FilesTest.java") , Charset.forName("gbk"))
			.forEach(line -> System.out.println(line));
		FileStore cStore = Files.getFileStore(Paths.get("C:"));
		// 判斷C槽的總空間，可用空間
		System.out.println("C:共有空間：" + cStore.getTotalSpace());
		System.out.println("C:可用空間：" + cStore.getUsableSpace());
	}
}
