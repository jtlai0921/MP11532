

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
		// 直接呼叫account物件的draw方法來執行提款
		// 同步方法的同步監視器是this，this代表呼叫draw()方法的物件。
		// 也就是說：執行緒進入draw()方法之前，必須先對account物件的加鎖。
		account.draw(drawAmount);
	}
}
