

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
public class MultiThreadDown
{
	public static void main(String[] args) throws Exception
	{
		// 初始化DownUtil物件
		final DownUtil downUtil = new DownUtil("http://www.crazyit.org/"
			+ "attachments/month_1403/1403202355ff6cc9a4fbf6f14a.png"
			, "ios.png", 4);
		// 開始下載
		downUtil.download();
		new Thread(() -> {
				while(downUtil.getCompleteRate() < 1)
				{
					// 每隔0.1秒查詢一次任務的完成進度，
					// GUI程式中可根據該進度來繪製進度條
					System.out.println("已完成："
						+ downUtil.getCompleteRate());
					try
					{
						Thread.sleep(1000);
					}
					catch (Exception ex){}
				}
		}).start();
	}
}
