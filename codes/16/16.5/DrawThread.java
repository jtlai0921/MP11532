

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
}
