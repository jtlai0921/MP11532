
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
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
// 登入用的對話方塊
public class LoginFrame extends JDialog
{
	public JLabel tip;
	public JTextField userField = new JTextField("李剛" , 20);
	public JComboBox<Integer> iconList = new JComboBox<>(
		new Integer[]{1, 2, 3, 4, 5 , 6, 7, 8 ,9 ,10});
	private JButton loginBn = new JButton("登入");
	// 聊天的主介面
	private LanTalk chatFrame;
	// 聊天通訊的工具實例
	public static ComUtil comUtil;
	// 建構子，用於初始化的登入對話方塊
	public LoginFrame(LanTalk parent , String msg)
	{
		super(parent , "輸入名字後登入" , true);
		this.chatFrame = parent;
		setLayout(new GridLayout(5, 1));
		JPanel jp = new JPanel();
		tip = new JLabel(msg);
		tip.setFont(new Font("Serif" , Font.BOLD , 16));
		jp.add(tip);
		add(jp);
		add(getPanel("使用者名稱" , userField));
		iconList.setPreferredSize(new Dimension(224, 20));
		add(getPanel("圖    示" , iconList));
		JPanel bp = new JPanel();
		loginBn.addActionListener(new MyActionListener(this));
		bp.add(loginBn);
		add(bp);
		pack();
		setVisible(true);
	}
	// 工具方法，該方法將一個字串和元件組合成JPanel物件
	private JPanel getPanel(String name , JComponent jf)
	{
		JPanel jp = new JPanel();
		jp.add(new JLabel(name + "："));
		jp.add(jf);
		return jp;
	}
	// 該方法用於改變登入視窗最上面的提示資訊
	public void setTipMsg(String tip)
	{
		this.tip.setText(tip);
	}
	// 定義一個事件監聽器
	class MyActionListener implements ActionListener
	{
		private LoginFrame loginFrame;
		public MyActionListener(LoginFrame loginFrame)
		{
			this.loginFrame = loginFrame;
		}
		// 當滑鼠單擊事件發生時
		public void actionPerformed(ActionEvent evt)
		{
			try
			{
				// 初始化聊天通訊類別
				comUtil = new ComUtil(chatFrame);
				final String loginMsg = YeekuProtocol.PRESENCE + userField.getText()
					+ YeekuProtocol.SPLITTER + iconList.getSelectedObjects()[0]
					+ YeekuProtocol.PRESENCE;
				comUtil.broadCast(loginMsg);
				// 啟動定時器每20秒廣播一次線上資訊
				javax.swing.Timer timer = new javax.swing.Timer(1000 * 10
					, event-> comUtil.broadCast(loginMsg));
				timer.start();
				loginFrame.setVisible(false);
				chatFrame.setVisible(true);
			}
			catch (Exception ex)
			{
				loginFrame.setTipMsg("確認30001連接埠空閒，且網路正常！");
			}
		}
	}
}