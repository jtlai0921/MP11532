
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.net.InetSocketAddress;
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
// 定義交談的對話方塊
public class ChatFrame extends JDialog
{
	// 聊天訊息區
	JTextArea msgArea = new JTextArea(12 , 45);
	// 聊天輸入區
	JTextField chatField = new JTextField(30);
	// 發送聊天訊息的按鈕
	JButton sendBn = new JButton("發送");
	// 該交談視窗對應的使用者
	UserInfo user;
	// 建構子，用於初始化交談對話方塊的介面
	public ChatFrame(LanTalk parent , final UserInfo user)
	{
		super(parent , "和" + user.getName() + "聊天中" , false);
		this.user = user;
		msgArea.setEditable(false);
		add(new JScrollPane(msgArea));
		JPanel buttom = new JPanel();
		buttom.add(new JLabel("輸入訊息："));
		buttom.add(chatField);
		buttom.add(sendBn);
		add(buttom , BorderLayout.SOUTH);
		// 發送訊息的Action，Action是ActionListener的子介面
		Action sendAction = new AbstractAction()
		{
			@Override
			public void actionPerformed(ActionEvent evt)
			{
				InetSocketAddress dest = (InetSocketAddress)user.getAddress();
				// 在聊友列表中，所有人項目的SocketAddress是null
				// 這表明是向所有人發送訊息
				if (dest == null)
				{
					LoginFrame.comUtil.broadCast(chatField.getText());
					msgArea.setText("您對大家說："
						+ chatField.getText() + "\n" + msgArea.getText());
				}
				// 向私人發送訊息
				else
				{
					// 獲取發送訊息的目的
					dest = new InetSocketAddress(dest.getHostName(),
						dest.getPort() + 1);
					LoginFrame.comUtil.sendSingle(chatField.getText(), dest);
					msgArea.setText("您對" + user.getName() +  "說："
						+ chatField.getText() + "\n" + msgArea.getText());

				}
				chatField.setText("");
			}
		};
		sendBn.addActionListener(sendAction);
		// 將Ctrl+Enter鍵和"send"關聯
		chatField.getInputMap().put(KeyStroke.getKeyStroke('\n'
			, java.awt.event.InputEvent.CTRL_MASK) , "send");
		// 將"send"與sendAction關聯
		chatField.getActionMap().put("send", sendAction);
		pack();
	}
	// 定義向聊天區域添加訊息的方法
	public void addString(String msg)
	{
		msgArea.setText(msg + "\n" + msgArea.getText());
	}
}
