
import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.*;
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
public class FileVisitorTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 遍歷g:\publish\codes\15目錄下的所有檔案和子目錄
		Files.walkFileTree(Paths.get("g:", "publish" , "codes" , "15")
			, new SimpleFileVisitor<Path>()
		{
			// 存取檔案時候觸發該方法
			@Override
			public FileVisitResult visitFile(Path file
				, BasicFileAttributes attrs) throws IOException
			{
				System.out.println("正在存取" + file + "檔案");
				// 找到了FileInputStreamTest.java檔案
				if (file.endsWith("FileInputStreamTest.java"))
				{
					System.out.println("--已經找到目標檔案--");
					return FileVisitResult.TERMINATE;
				}
				return FileVisitResult.CONTINUE;
			}
			// 開始存取目錄時觸發該方法
			@Override
			public FileVisitResult preVisitDirectory(Path dir
				, BasicFileAttributes attrs) throws IOException
			{
				System.out.println("正在存取：" + dir + " 路徑");
				return FileVisitResult.CONTINUE;
			}
		});
	}
}
