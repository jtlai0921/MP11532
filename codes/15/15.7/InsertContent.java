
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
public class InsertContent
{
	public static void insert(String fileName , long pos
		, String insertContent) throws IOException
	{
		File tmp = File.createTempFile("tmp" , null);
		tmp.deleteOnExit();
		try(
			RandomAccessFile raf = new RandomAccessFile(fileName , "rw");
			// 使用暫存檔來存放插入點後的資料
			FileOutputStream tmpOut = new FileOutputStream(tmp);
			FileInputStream tmpIn = new FileInputStream(tmp))
		{
			raf.seek(pos);
			// ------下面程式碼將插入點後的內容讀入暫存檔中存放------
			byte[] bbuf = new byte[64];
			// 用於存放實際讀取的位元組數
			int hasRead = 0;
			// 使用迴圈方式讀取插入點後的資料
			while ((hasRead = raf.read(bbuf)) > 0 )
			{
				// 將讀取的資料寫入暫存檔
				tmpOut.write(bbuf , 0 , hasRead);
			}
			// ----------下面程式碼插入內容----------
			// 把檔案記錄指位器重新定位到pos位置
			raf.seek(pos);
			// 附加需要插入的內容
			raf.write(insertContent.getBytes());
			// 附加暫存檔中的內容
			while ((hasRead = tmpIn.read(bbuf)) > 0 )
			{
				raf.write(bbuf , 0 , hasRead);
			}
		}
	}
	public static void main(String[] args)
		throws IOException
	{
		insert("InsertContent.java" , 45 , "插入的內容\r\n");
	}
}

