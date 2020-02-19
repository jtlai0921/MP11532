
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
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
public class LoginFrame
{
	private final String PROP_FILE = "mysql.ini";
	private String driver;
	// url是資料庫的服務位址
	private String url;
	private String user;
	private String pass;
	// 登入介面的GUI元件
	private JFrame jf = new JFrame("登入");
	private JTextField userField = new JTextField(20);
	private JTextField passField = new JTextField(20);
	private JButton loginButton = new JButton("登入");
	public void init()throws Exception
	{
		Properties connProp = new Properties();
		connProp.load(new FileInputStream(PROP_FILE));
		driver = connProp.getProperty("driver");
		url = connProp.getProperty("url");
		user = connProp.getProperty("user");
		pass = connProp.getProperty("pass");
		// 載入驅動
		Class.forName(driver);
		// 為登入按鈕添加事件監聽器
		loginButton.addActionListener(e -> {
			// 登入成功則顯示「登入成功」
			if (validate(userField.getText(), passField.getText()))
			{
				JOptionPane.showMessageDialog(jf, "登入成功");
			}
			// 否則顯示「登入失敗」
			else
			{
				JOptionPane.showMessageDialog(jf, "登入失敗");
			}
		});
		jf.add(userField , BorderLayout.NORTH);
		jf.add(passField);
		jf.add(loginButton , BorderLayout.SOUTH);
		jf.pack();
		jf.setVisible(true);
	}
//	private boolean validate(String userName, String userPass)
//	{
//		// 執行查詢的SQL語句
//		String sql = "select * from jdbc_test "
//			+ "where jdbc_name='" + userName
//			+ "' and jdbc_desc='" + userPass + "'";
//		System.out.println(sql);
//		try(
//			Connection conn = DriverManager.getConnection(url , user ,pass);
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery(sql))
//		{
//			// 如果查詢的ResultSet裡有超過一條的記錄，則登入成功
//			if (rs.next())
//			{
//				return true;
//			}
//		}
//		catch(Exception e)
//		{
//			e.printStackTrace();
//		}
//		return false;
//	}

	private boolean validate(String userName, String userPass)
	{
		try(
			Connection conn = DriverManager.getConnection(url
				, user ,pass);
			PreparedStatement pstmt = conn.prepareStatement(
				"select * from jdbc_test where jdbc_name=? and jdbc_desc=?"))
		{
			pstmt.setString(1, userName);
			pstmt.setString(2, userPass);
			try(
				ResultSet rs = pstmt.executeQuery())
			{
				//如果查詢的ResultSet裡有超過一條的記錄，則登入成功
				if (rs.next())
				{
					return true;
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) throws Exception
	{
		new LoginFrame().init();
	}
}
