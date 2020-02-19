
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.*;
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
public class DownUtil
{
	// 定義下載資源的路徑
	private String path;
	// 指定所下載的檔案的存放位置
	private String targetFile;
	// 定義需要使用多少執行緒下載資源
	private int threadNum;
	// 定義下載的執行緒物件
	private DownThread[] threads;
	// 定義下載的檔案的總大小
	private int fileSize;

	public DownUtil(String path, String targetFile, int threadNum)
	{
		this.path = path;
		this.threadNum = threadNum;
		// 初始化threads陣列
		threads = new DownThread[threadNum];
		this.targetFile = targetFile;
	}

	public void download() throws Exception
	{
		URL url = new URL(path);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setConnectTimeout(5 * 1000);
		conn.setRequestMethod("GET");
		conn.setRequestProperty(
			"Accept",
			"image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
			+ "application/x-shockwave-flash, application/xaml+xml, "
			+ "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
			+ "application/x-ms-application, application/vnd.ms-excel, "
			+ "application/vnd.ms-powerpoint, application/msword, */*");
		conn.setRequestProperty("Accept-Language", "zh-CN");
		conn.setRequestProperty("Charset", "UTF-8");
		conn.setRequestProperty("Connection", "Keep-Alive");
		// 得到檔案大小
		fileSize = conn.getContentLength();
		conn.disconnect();
		int currentPartSize = fileSize / threadNum + 1;
		RandomAccessFile file = new RandomAccessFile(targetFile, "rw");
		// 設置本地檔案的大小
		file.setLength(fileSize);
		file.close();
		for (int i = 0; i < threadNum; i++)
		{
			// 運算每條執行緒的下載的開始位置
			int startPos = i * currentPartSize;
			// 每個執行緒使用一個RandomAccessFile進行下載
			RandomAccessFile currentPart = new RandomAccessFile(targetFile,
				"rw");
			// 定位該執行緒的下載位置
			currentPart.seek(startPos);
			// 建立下載執行緒
			threads[i] = new DownThread(startPos, currentPartSize,
				currentPart);
			// 啟動下載執行緒
			threads[i].start();
		}
	}

	// 獲取下載的完成百分比
	public double getCompleteRate()
	{
		// 統計多條執行緒已經下載的總大小
		int sumSize = 0;
		for (int i = 0; i < threadNum; i++)
		{
			sumSize += threads[i].length;
		}
		// 返回已經完成的百分比
		return sumSize * 1.0 / fileSize;
	}

	private class DownThread extends Thread
	{
		// 當前執行緒的下載位置
		private int startPos;
		// 定義當前執行緒負責下載的檔案大小
		private int currentPartSize;
		// 當前執行緒需要下載的檔案區塊
		private RandomAccessFile currentPart;
		// 定義已經該執行緒已下載的位元組數
		public int length;

		public DownThread(int startPos, int currentPartSize,
			RandomAccessFile currentPart)
		{
			this.startPos = startPos;
			this.currentPartSize = currentPartSize;
			this.currentPart = currentPart;
		}

		@Override
		public void run()
		{
			try
			{
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection)url
					.openConnection();
				conn.setConnectTimeout(5 * 1000);
				conn.setRequestMethod("GET");
				conn.setRequestProperty(
					"Accept",
					"image/gif, image/jpeg, image/pjpeg, image/pjpeg, "
					+ "application/x-shockwave-flash, application/xaml+xml, "
					+ "application/vnd.ms-xpsdocument, application/x-ms-xbap, "
					+ "application/x-ms-application, application/vnd.ms-excel, "
					+ "application/vnd.ms-powerpoint, application/msword, */*");
				conn.setRequestProperty("Accept-Language", "zh-CN");
				conn.setRequestProperty("Charset", "UTF-8");
				InputStream inStream = conn.getInputStream();
				// 跳過startPos個位元組，表明該執行緒只下載自己負責哪部分檔案。
				inStream.skip(this.startPos);
				byte[] buffer = new byte[1024];
				int hasRead = 0;
				// 讀取網路資料，並寫入本地檔案
				while (length < currentPartSize
					&& (hasRead = inStream.read(buffer)) != -1)
				{
					currentPart.write(buffer, 0, hasRead);
					// 累計該執行緒下載的總大小
					length += hasRead;
				}
				currentPart.close();
				inStream.close();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
}
