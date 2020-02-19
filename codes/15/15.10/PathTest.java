
import java.io.*;
import java.net.*;
import java.nio.file.*;
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
public class PathTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 以當前路徑來建立Path物件
		Path path = Paths.get(".");
		System.out.println("path裡包含的路徑數量："
			+ path.getNameCount());
		System.out.println("path的根路徑：" + path.getRoot());
		// 獲取path對應的絕對路徑。
		Path absolutePath = path.toAbsolutePath();
		System.out.println(absolutePath);
		// 獲取絕對路徑的根路徑
		System.out.println("absolutePath的根路徑："
			+ absolutePath.getRoot());
		// 獲取絕對路徑所包含的路徑數量
		System.out.println("absolutePath裡包含的路徑數量："
			+ absolutePath.getNameCount());
		System.out.println(absolutePath.getName(3));
		// 以多個String來構建Path物件
		Path path2 = Paths.get("g:" , "publish" , "codes");
		System.out.println(path2);
	}
}
