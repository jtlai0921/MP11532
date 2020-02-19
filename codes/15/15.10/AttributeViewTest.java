
import java.io.*;
import java.util.*;
import java.nio.file.*;
import java.nio.*;
import java.nio.charset.*;
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
public class AttributeViewTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 獲取將要操作的檔案
		Path testPath = Paths.get("AttributeViewTest.java");
		// 獲取存取基本屬性的BasicFileAttributeView
		BasicFileAttributeView basicView = Files.getFileAttributeView(
			testPath , BasicFileAttributeView.class);
		// 獲取存取基本屬性的BasicFileAttributes
		BasicFileAttributes basicAttribs = basicView.readAttributes();
		// 存取檔案的基本屬性
		System.out.println("建立時間：" + new Date(basicAttribs
			.creationTime().toMillis()));
		System.out.println("最後存取時間：" + new Date(basicAttribs
			.lastAccessTime().toMillis()));
		System.out.println("最後修改時間：" + new Date(basicAttribs
			.lastModifiedTime().toMillis()));
		System.out.println("檔案大小：" + basicAttribs.size());
		// 獲取存取檔案屬性主資訊的FileOwnerAttributeView
		FileOwnerAttributeView ownerView = Files.getFileAttributeView(
			testPath, FileOwnerAttributeView.class);
		// 獲取該檔案所屬的使用者
		System.out.println(ownerView.getOwner());
		// 獲取系統中guest對應的使用者
		UserPrincipal user = FileSystems.getDefault()
			.getUserPrincipalLookupService()
			.lookupPrincipalByName("guest");
		// 修改使用者
		ownerView.setOwner(user);
		// 獲取存取自訂屬性的FileOwnerAttributeView
		UserDefinedFileAttributeView userView = Files.getFileAttributeView(
			testPath, UserDefinedFileAttributeView.class);
		List<String> attrNames = userView.list();
		// 遍歷所有的自訂屬性
		for (String name : attrNames)
		{
			ByteBuffer buf = ByteBuffer.allocate(userView.size(name));
			userView.read(name, buf);
			buf.flip();
			String value = Charset.defaultCharset().decode(buf).toString();
			System.out.println(name + "--->" + value) ;
		}
		// 添加一個自訂屬性
		userView.write("發行者", Charset.defaultCharset()
			.encode("瘋狂Java聯盟"));
		// 獲取存取DOS屬性的DosFileAttributeView
		DosFileAttributeView dosView = Files.getFileAttributeView(testPath
			, DosFileAttributeView.class);
		// 將檔案設置隱藏、唯讀
		dosView.setHidden(true);
		dosView.setReadOnly(true);
	}
}