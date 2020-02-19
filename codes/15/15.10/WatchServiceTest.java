
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
public class WatchServiceTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 獲取檔案系統的WatchService物件
		WatchService watchService = FileSystems.getDefault()
			.newWatchService();
		// 為C:槽根路徑註冊監聽
		Paths.get("C:/").register(watchService
			, StandardWatchEventKinds.ENTRY_CREATE
			, StandardWatchEventKinds.ENTRY_MODIFY
			, StandardWatchEventKinds.ENTRY_DELETE);
		while(true)
		{
			// 獲取下一個檔案改動事件
			WatchKey key = watchService.take();    //①
			for (WatchEvent<?> event : key.pollEvents())
			{
				System.out.println(event.context() +" 檔案發生了 "
					+ event.kind()+ "事件！");
			}
			// 重設WatchKey
			boolean valid = key.reset();
			// 如果重設失敗，結束監聽
			if (!valid)
			{
				break;
			}
		}
	}
}
