
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
public class UserInfo
{
	// 該使用者的圖示
	private String icon;
	// 該使用者的名字
	private String name;
	// 該使用者的MulitcastSocket所在的IP和連接埠
	private SocketAddress address;
	// 該使用者失去聯繫的次數
	private int lost;
	// 該使用者對應的交談視窗
	private ChatFrame chatFrame;
	public UserInfo(){}
	// 有參數的建構子
	public UserInfo(String icon , String name
		, SocketAddress address , int lost)
	{
		this.icon = icon;
		this.name = name;
		this.address = address;
		this.lost = lost;
	}

	// 省略所有成員變數的setter和getter方法

	// icon的setter和getter方法
	public void setIcon(String icon)
	{
		this.icon = icon;
	}
	public String getIcon()
	{
		return this.icon;
	}

	// name的setter和getter方法
	public void setName(String name)
	{
		this.name = name;
	}
	public String getName()
	{
		return this.name;
	}

	// address的setter和getter方法
	public void setAddress(SocketAddress address)
	{
		this.address = address;
	}
	public SocketAddress getAddress()
	{
		return this.address;
	}

	// lost的setter和getter方法
	public void setLost(int lost)
	{
		this.lost = lost;
	}
	public int getLost()
	{
		return this.lost;
	}

	// chatFrame的setter和getter方法
	public void setChatFrame(ChatFrame chatFrame)
	{
		this.chatFrame = chatFrame;
	}
	public ChatFrame getChatFrame()
	{
		return this.chatFrame;
	}

	// 使用address作為該使用者的標識，所以根據address作為
	// 覆寫hashCode()和equals方法的標準
	public int hashCode()
	{
		return address.hashCode();
	}
	public boolean equals(Object obj)
	{
		if (obj != null && obj.getClass() == UserInfo.class)
		{
			UserInfo target = (UserInfo)obj;
			if (address != null)
			{
				return address.equals(target.getAddress());
			}
		}
		return false;
	}
}