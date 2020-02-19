
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
public class RandomFileChannelTest
{
	public static void main(String[] args)
		throws IOException
	{
		File f = new File("a.txt");
		try(
			// 建立一個RandomAccessFile物件
			RandomAccessFile raf = new RandomAccessFile(f, "rw");
			// 獲取RandomAccessFile對應的Channel
			FileChannel randomChannel = raf.getChannel())
		{
			// 將Channel中所有資料對應成ByteBuffer
			ByteBuffer buffer = randomChannel.map(FileChannel
				.MapMode.READ_ONLY, 0 , f.length());
			// 把Channel的記錄指位器移動到最後
			randomChannel.position(f.length());
			// 將buffer中所有資料輸出
			randomChannel.write(buffer);
		}
	}
}
