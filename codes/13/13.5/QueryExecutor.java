
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;
import java.util.*;
import java.io.*;
import java.sql.*;
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
public class QueryExecutor
{
	JFrame jf = new JFrame("查詢執行器");
	private JScrollPane scrollPane;
	private JButton execBn = new JButton("查詢");
	// 用於輸入查詢語句的文字方塊
	private JTextField sqlField = new JTextField(45);
	private static Connection conn;
	private static Statement stmt;
	// 採用靜態初始化區塊來初始化Connection、Statement物件
	static
	{
		try
		{
			Properties props = new Properties();
			props.load(new FileInputStream("mysql.ini"));
			String drivers = props.getProperty("driver");
			String url = props.getProperty("url");
			String username = props.getProperty("user");
			String password = props.getProperty("pass");
			// 載入資料庫驅動
			Class.forName(drivers);
			// 取得資料庫連接
			conn = DriverManager.getConnection(url, username, password);
			stmt = conn.createStatement();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	// --------初始化介面的方法---------
	public void init()
	{
		JPanel top = new JPanel();
		top.add(new JLabel("輸入查詢語句："));
		top.add(sqlField);
		top.add(execBn);
		// 為執行按鈕、單行文字方塊添加事件監聽器
		execBn.addActionListener(new ExceListener());
		sqlField.addActionListener(new ExceListener());
		jf.add(top , BorderLayout.NORTH);
		jf.setSize(680, 480);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	}
	// 定義監聽器
	class ExceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent evt)
		{
			// 刪除原來的JTable(JTable使用scrollPane來包裝)
			if (scrollPane != null)
			{
				jf.remove(scrollPane);
			}
			try(
				// 根據使用者輸入的SQL執行查詢
				ResultSet rs = stmt.executeQuery(sqlField.getText()))
			{
				// 取出ResultSet的MetaData
				ResultSetMetaData rsmd = rs.getMetaData();
				Vector<String> columnNames =  new Vector<>();
				Vector<Vector<String>> data = new Vector<>();
				// 把ResultSet的所有列名添加到Vector裡
				for (int i = 0 ; i < rsmd.getColumnCount(); i++ )
				{
					columnNames.add(rsmd.getColumnName(i + 1));
				}
				// 把ResultSet的所有記錄添加到Vector裡
				while (rs.next())
				{
					Vector<String> v = new Vector<>();
					for (int i = 0 ; i < rsmd.getColumnCount(); i++ )
					{
						v.add(rs.getString(i + 1));
					}
					data.add(v);
				}
				// 建立新的JTable
				JTable table = new JTable(data , columnNames);
				scrollPane = new JScrollPane(table);
				// 添加新的Table
				jf.add(scrollPane);
				// 更新主視窗
				jf.validate();
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args)
	{
		new QueryExecutor().init();
	}
}
