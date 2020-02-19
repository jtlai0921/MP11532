
import java.text.*;
import java.util.Date;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
public class LanTalk extends JFrame
{
	private DefaultListModel<UserInfo> listModel
		= new DefaultListModel<>();
	// 定義一個JList物件
	private JList<UserInfo> friendsList = new JList<>(listModel);
	// 定義一個用於格式化日期的格式器
	private DateFormat formatter = DateFormat.getDateTimeInstance();
	public LanTalk()
	{
		super("區域網路聊天");
		// 設置該JList使用ImageCellRenderer作為儲存格繪製器
		friendsList.setCellRenderer(new ImageCellRenderer());
		listModel.addElement(new UserInfo("all" , "所有人"
			, null , -2000));
		friendsList.addMouseListener(new ChangeMusicListener());
		add(new JScrollPane(friendsList));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(2, 2, 160 , 600);
	}
	// 根據位址來查詢使用者
	public UserInfo getUserBySocketAddress(SocketAddress address)
	{
		for (int i = 1 ; i < getUserNum() ; i++)
		{
			UserInfo user = getUser(i);
			if (user.getAddress() != null
				&& user.getAddress().equals(address))
			{
				return user;
			}
		}
		return null;
	}
	// ------下面四個方法是對ListModel的包裝------
	// 向使用者列表中添加使用者
	public void addUser(UserInfo user)
	{
		listModel.addElement(user);
	}
	// 從使用者列表中刪除使用者
	public void removeUser(int pos)
	{
		listModel.removeElementAt(pos);
	}
	// 獲取該聊天視窗的使用者數量
	public int getUserNum()
	{
		return listModel.size();
	}
	// 獲取指定位置的使用者
	public UserInfo getUser(int pos)
	{
		return listModel.elementAt(pos);
	}
	// 實作JList上的滑鼠雙擊事件的監聽器
	class ChangeMusicListener extends MouseAdapter
	{
		public void mouseClicked(MouseEvent e)
		{
			// 如果滑鼠的擊鍵次數大於2
			if (e.getClickCount() >= 2)
			{
				// 取出滑鼠雙擊時選取的列表項
				UserInfo user = (UserInfo)friendsList.getSelectedValue();
				// 如果該列表項對應使用者的交談視窗為null
				if (user.getChatFrame() == null)
				{
					// 為該使用者建立一個交談視窗，並讓該使用者參照該視窗
					user.setChatFrame(new ChatFrame(null , user));
				}
				// 如果該使用者的視窗沒有顯示，則讓該使用者的視窗顯示出來
				if (!user.getChatFrame().isShowing())
				{
					user.getChatFrame().setVisible(true);
				}
			}
		}
	}
	/**
	 * 處理網路資料包，該方法將根據聊天訊息得到聊天者，
	 * 並將資訊顯示在聊天對話方塊中。
	 * @param packet 需要處理的資料包
	 * @param single 該資訊是否為私人訊息
	 */
	public void processMsg(DatagramPacket packet , boolean single)
	{
		// 獲取該發送該資料包的SocketAddress
		InetSocketAddress srcAddress = (InetSocketAddress)
			packet.getSocketAddress();
		// 如果是私人訊息，則該Packet獲取的是DatagramSocket的位址，
		// 將連接埠減1才是對應的MulticastSocket的位址
		if (single)
		{
			srcAddress = new InetSocketAddress(srcAddress.getHostName()
				, srcAddress.getPort() - 1);
		}
		UserInfo srcUser = getUserBySocketAddress(srcAddress);
		if (srcUser != null)
		{
			// 確定訊息將要顯示到哪個使用者對應視窗上。
			UserInfo alertUser = single ? srcUser : getUser(0);
			// 如果該使用者對應的視窗為空，顯示該視窗
			if (alertUser.getChatFrame() == null)
			{
				alertUser.setChatFrame(new ChatFrame(null , alertUser));
			}
			// 定義添加的提示資訊
			String tipMsg = single ? "對您說：" : "對大家說：";
			try{
				// 顯示提示資訊
				alertUser.getChatFrame().addString(srcUser.getName()
					+ tipMsg + "......................("
					+ formatter.format(new Date()) + ")\n"
					+ new String(packet.getData() , 0 , packet.getLength()
					, ComUtil.CHARSET) + "\n");
			} catch (Exception ex) { ex.printStackTrace(); }
			if (!alertUser.getChatFrame().isShowing())
			{
				alertUser.getChatFrame().setVisible(true);
			}
		}
	}
	// 主方法，程式的入口
	public static void main(String[] args)
	{
		LanTalk lanTalk = new LanTalk();
		new LoginFrame(lanTalk , "請輸入使用者名稱、頭像後登入");
	}
}
// 定義用於改變JList列表項外觀的類別
class ImageCellRenderer extends JPanel
	implements ListCellRenderer<UserInfo>
{
	private ImageIcon icon;
	private String name;
	// 定義繪製儲存格時的背景色
	private Color background;
	// 定義繪製儲存格時的前景色
	private Color foreground;
	@Override
	public Component getListCellRendererComponent(JList list
		, UserInfo userInfo , int index
		, boolean isSelected , boolean cellHasFocus)
	{
		// 設置圖示
		icon = new ImageIcon("ico/" + userInfo.getIcon() + ".gif");
		name = userInfo.getName();
		// 設置背景色、前景色
		background = isSelected ? list.getSelectionBackground()
			: list.getBackground();
		foreground = isSelected ? list.getSelectionForeground()
			: list.getForeground();
		// 返回該JPanel物件作為儲存格繪製器
		return this;
	}
	// 覆寫paintComponent方法，改變JPanel的外觀
	public void paintComponent(Graphics g)
	{
		int imageWidth = icon.getImage().getWidth(null);
		int imageHeight = icon.getImage().getHeight(null);
		g.setColor(background);
		g.fillRect(0, 0, getWidth(), getHeight());
		g.setColor(foreground);
		// 繪製好友圖示
		g.drawImage(icon.getImage() , getWidth() / 2 - imageWidth / 2
			, 10 , null);
		g.setFont(new Font("SansSerif" , Font.BOLD , 18));
		// 繪製好友使用者名稱
		g.drawString(name, getWidth() / 2 - name.length() * 10
			, imageHeight + 30 );
	}
	// 通過該方法來設置該ImageCellRenderer的最佳大小
	public Dimension getPreferredSize()
	{
		return new Dimension(60, 80);
	}
}