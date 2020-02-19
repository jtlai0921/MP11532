
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
public class ClientThread extends Thread
{
	// 該客戶端執行緒負責處理的輸入串流
	BufferedReader br = null;
	// 使用一個網路輸入串流來建立客戶端執行緒
	public ClientThread(BufferedReader br)
	{
		this.br = br;
	}
	public void run()
	{
		try
		{
			String line = null;
			// 不斷從輸入串流中讀取資料，並將這些資料列印輸出
			while((line = br.readLine())!= null)
			{
				System.out.println(line);
				/*
				本例僅列印了從伺服器端讀到的內容。實際上，此處的情況可以更複雜：
				如果希望客戶端能看到聊天室的使用者列表，則可以讓伺服器在
				每次有使用者登入、使用者結束時，將所有使用者列表資訊都向客戶端發送一遍。
				為了區分伺服器發送的是聊天訊息，還是使用者列表，伺服器也應該
				在要發送的資訊前、後都添加一定的協定字串，客戶端此處則根據協定
				字串的不同而進行不同的處理！
				更複雜的情況：
				如果兩端進行遊戲，則還有可能發送遊戲資訊，例如兩端進行五子棋遊戲，
				則還需要發送下棋座標資訊等，伺服器同樣在這些下棋座標資訊前、後
				添加協定字串後再發送，客戶端就可以根據該資訊知道對手的下棋座標。
				*/
			}
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
		// 使用finally區塊來關閉該執行緒對應的輸入串流
		finally
		{
			try
			{
				if (br != null)
				{
					br.close();
				}
			}
			catch (IOException ex)
			{
				ex.printStackTrace();
			}
		}
	}
}
