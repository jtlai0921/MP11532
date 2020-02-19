
import java.util.concurrent.*;
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
	// 顯式定義Lock物件
	private final Lock lock = new ReentrantLock();
	// 獲得指定Lock物件對應的Condition
	private final Condition cond  = lock.newCondition();
	// 封裝帳號編號、帳號餘額的兩個成員變數
	private String accountNo;
	private double balance;
	// 標識帳號中是否已有存款的旗標
	private boolean flag = false;

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

	public void draw(double drawAmount)
	{
		// 加鎖
		lock.lock();
		try
		{
			// 如果flag為假，表明帳號中還沒有人存錢進去，提款方法阻擋
			if (!flag)
			{
				cond.await();
			}
			else
			{
				// 執行提款
				System.out.println(Thread.currentThread().getName()
					+ " 提款:" +  drawAmount);
				balance -= drawAmount;
				System.out.println("帳號餘額為：" + balance);
				// 將標識帳號是否已有存款的旗標設為false。
				flag = false;
				// 喚醒其他執行緒
				cond.signalAll();
			}
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
		// 使用finally區塊來釋放鎖
		finally
		{
			lock.unlock();
		}
	}
	public void deposit(double depositAmount)
	{
		lock.lock();
		try
		{
			// 如果flag為真，表明帳號中已有人存錢進去，則存錢方法阻擋
			if (flag)             // ①
			{
				cond.await();
			}
			else
			{
				// 執行存款
				System.out.println(Thread.currentThread().getName()
					+ " 存款:" +  depositAmount);
				balance += depositAmount;
				System.out.println("帳號餘額為：" + balance);
				// 將表示帳號是否已有存款的旗標設為true
				flag = true;
				// 喚醒其他執行緒
				cond.signalAll();
			}
		}
		catch (InterruptedException ex)
		{
			ex.printStackTrace();
		}
		// 使用finally區塊來釋放鎖
		finally
		{
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