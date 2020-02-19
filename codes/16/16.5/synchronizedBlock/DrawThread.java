

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
public class DrawThread extends Thread
{
	// 模擬使用者帳號
	private Account account;
	// 當前提款執行緒所希望取的額度
	private double drawAmount;
	public DrawThread(String name , Account account
		, double drawAmount)
	{
		super(name);
		this.account = account;
		this.drawAmount = drawAmount;
	}
	// 當多條執行緒修改同一個共享資料時，將涉及資料安全問題。
	public void run()
	{
		// 使用account作為同步監視器，任何執行緒進入下面同步程式碼區塊之前，
		// 必須先獲得對account帳號的鎖定――其他執行緒無法獲得鎖，也就無法修改它
		// 這種做法符合：「加鎖 → 修改 → 釋放鎖」的邏輯
		synchronized (account)
		{
			// 帳號餘額大於提款數目
			if (account.getBalance() >= drawAmount)
			{
				// 吐出鈔票
				System.out.println(getName()
					+ "提款成功！吐出鈔票:" + drawAmount);
				try
				{
					Thread.sleep(1);
				}
				catch (InterruptedException ex)
				{
					ex.printStackTrace();
				}
				// 修改餘額
				account.setBalance(account.getBalance() - drawAmount);
				System.out.println("\t餘額為: " + account.getBalance());
			}
			else
			{
				System.out.println(getName() + "提款失敗！餘額不足！");
			}
		}
		// 同步程式碼區塊結束，該執行緒釋放同步鎖
	}
}
