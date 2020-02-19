
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.nio.*;
import java.nio.channels.*;
import java.nio.charset.*;
import java.util.concurrent.*;
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
public class AIOClient
{
	final static String UTF_8 = "utf-8";
	final static int PORT = 30000;
	// 與伺服器端通訊的非同步Channel
	AsynchronousSocketChannel clientChannel;
	JFrame mainWin = new JFrame("多人聊天");
	JTextArea jta = new JTextArea(16 , 48);
	JTextField jtf = new JTextField(40);
	JButton sendBn = new JButton("發送");
	public void init()
	{
		mainWin.setLayout(new BorderLayout());
		jta.setEditable(false);
		mainWin.add(new JScrollPane(jta), BorderLayout.CENTER);
		JPanel jp = new JPanel();
		jp.add(jtf);
		jp.add(sendBn);
		// 發送訊息的Action,Action是ActionListener的子介面
		Action sendAction = new AbstractAction()
		{
			public void actionPerformed(ActionEvent e)
			{
				String content = jtf.getText();
				if (content.trim().length() > 0)
				{
					try
					{
						// 將content內容寫入Channel中
						clientChannel.write(ByteBuffer.wrap(content
							.trim().getBytes(UTF_8))).get();    //①
					}
					catch (Exception ex)
					{
						ex.printStackTrace();
					}
				}
				// 清空輸入框
				jtf.setText("");
			}
		};
		sendBn.addActionListener(sendAction);
		// 將Ctrl+Enter鍵和"send"關聯
		jtf.getInputMap().put(KeyStroke.getKeyStroke('\n'
			, java.awt.event.InputEvent.CTRL_MASK) , "send");
		// 將"send"和sendAction關聯
		jtf.getActionMap().put("send", sendAction);
		mainWin.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainWin.add(jp , BorderLayout.SOUTH);
		mainWin.pack();
		mainWin.setVisible(true);
	}
	public void connect()
		throws Exception
	{
		// 定義一個ByteBuffer準備讀取資料
		final ByteBuffer buff = ByteBuffer.allocate(1024);
		// 建立一個執行緒池
		ExecutorService executor = Executors.newFixedThreadPool(80);
		// 以指定執行緒池來建立一個AsynchronousChannelGroup
		AsynchronousChannelGroup channelGroup =
			AsynchronousChannelGroup.withThreadPool(executor);
		// 以channelGroup作為組管理器來建立AsynchronousSocketChannel
		clientChannel = AsynchronousSocketChannel.open(channelGroup);
		// 讓AsynchronousSocketChannel連線到指定IP、指定連接埠
		clientChannel.connect(new InetSocketAddress("127.0.0.1"
			, PORT)).get();
		jta.append("---與伺服器連線成功---\n");
		buff.clear();
		clientChannel.read(buff, null
			, new CompletionHandler<Integer,Object>()   //②
		{
			@Override
			public void completed(Integer result, Object attachment)
			{
				buff.flip();
				// 將buff中內容轉換為字串
				String content = StandardCharsets.UTF_8
					.decode(buff).toString();
				// 顯示從伺服器端讀取的資料
				jta.append("某人說：" + content + "\n");
				buff.clear();
				clientChannel.read(buff , null , this);
			}
			@Override
			public void failed(Throwable ex, Object attachment)
			{
				System.out.println("讀取資料失敗: " + ex);
			}
		});
	}
	public static void main(String[] args)
		throws Exception
	{
		AIOClient client = new AIOClient();
		client.init();
		client.connect();
	}
}