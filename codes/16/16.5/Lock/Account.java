
import java.util.concurrent.locks.*;
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
public class Account
{
	// 定義鎖物件
	private final ReentrantLock lock = new ReentrantLock();
	// 封裝帳號編號、帳號餘額的兩個成員變數
	private String accountNo;
	private double balance;
	public Account(){}
	// 建構子
	public Account(String accountNo , double balance)
	{
		this.accountNo = accountNo;
		this.balance = balance;
	}

	// accountNo的setter和getter方法
	public void setAccountNo(String accountNo)
	{
		this.accountNo = accountNo;
	}
	public String getAccountNo()
	{
		return this.accountNo;
	}
	// 因此帳號餘額不允許隨便修改，所以只為balance提供getter方法，
	public double getBalance()
	{
		return this.balance;
	}

	// 提供一個執行緒安全draw()方法來完成提款操作
	public void draw(double drawAmount)
	{
		// 加鎖
		lock.lock();
		try
		{
			// 帳號餘額大於提款數目
			if (balance >= drawAmount)
			{
				// 吐出鈔票
				System.out.println(Thread.currentThread().getName()
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
				balance -= drawAmount;
				System.out.println("\t餘額為: " + balance);
			}
			else
			{
				System.out.println(Thread.currentThread().getName()
					+ "提款失敗！餘額不足！");
			}
		}
		finally
		{
			// 修改完成，釋放鎖
			lock.unlock();
		}
	}

	// 下面兩個方法根據accountNo來覆寫hashCode()和equals()方法
	public int hashCode()
	{
		return accountNo.hashCode();
	}
	public boolean equals(Object obj)
	{
		if(this == obj)
			return true;
		if (obj !=null
			&& obj.getClass() == Account.class)
		{
			Account target = (Account)obj;
			return target.getAccountNo().equals(accountNo);
		}
		return false;
	}
}