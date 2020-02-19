
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
public class CallableStatementTest
{
	private String driver;
	private String url;
	private String user;
	private String pass;
	public void initParam(String paramFile)throws Exception
	{
		// 使用Properties類別來載入屬性檔
		Properties props = new Properties();
		props.load(new FileInputStream(paramFile));
		driver = props.getProperty("driver");
		url = props.getProperty("url");
		user = props.getProperty("user");
		pass = props.getProperty("pass");
	}
	public void callProcedure()throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url
				, user , pass);
			// 使用Connection來建立一個CallableStatment物件
			CallableStatement cstmt = conn.prepareCall(
				"{call add_pro(?,?,?)}"))
		{
			cstmt.setInt(1, 4);
			cstmt.setInt(2, 5);
			// 註冊CallableStatement的第三個參數是int類型
			cstmt.registerOutParameter(3, Types.INTEGER);
			// 執行儲存過程
			cstmt.execute();
			// 獲取，並輸出儲存過程傳出參數的值。
			System.out.println("執行結果是: " + cstmt.getInt(3));
		}
	}
	public static void main(String[] args) throws Exception
	{
		CallableStatementTest ct = new CallableStatementTest();
		ct.initParam("mysql.ini");
		ct.callProcedure();
	}
}

