
import java.io.*;
import java.nio.*;
import java.nio.channels.*;
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
public class FileLockTest
{
	public static void main(String[] args)
		throws Exception
	{

		try(
			// 使用FileOutputStream獲取FileChannel
			FileChannel channel = new FileOutputStream("a.txt")
				.getChannel())
		{
			// 使用非阻擋式方式對指定檔案加鎖
			FileLock lock = channel.tryLock();
			// 程式暫停10s
			Thread.sleep(10000);
			// 釋放鎖
			lock.release();
		}
	}
}
