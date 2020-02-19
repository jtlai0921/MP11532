
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
public class URLDecoderTest
{
	public static void main(String[] args)
		throws Exception
	{
		// 將application/x-www-form-urlencoded字串
		// 轉換成普通字串
		// 其中的字串直接從圖17.3所示視窗複製過來
		String keyWord = URLDecoder.decode(
			"%E7%98%8B%E7%8B%82java", "utf-8");
		System.out.println(keyWord);
		// 將普通字串轉換成
		// application/x-www-form-urlencoded字串
		String urlStr = URLEncoder.encode(
			"瘋狂Android講義" , "GBK");
		System.out.println(urlStr);
	}
}
