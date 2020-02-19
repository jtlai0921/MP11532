
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
public class PreparedStatementTest
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
		// 載入驅動
		Class.forName(driver);
	}
	public void insertUseStatement()throws Exception
	{
		long start = System.currentTimeMillis();
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url
				, user , pass);
			// 使用Connection來建立一個Statment物件
			Statement stmt = conn.createStatement())
		{
			// 需要使用100條SQL語句來插入100條記錄
			for (int i = 0; i < 100 ; i++ )
			{
				stmt.executeUpdate("insert into student_table values("
					+ " null ,'姓名" + i + "' , 1)");
			}
			System.out.println("使用Statement費時:"
				+ (System.currentTimeMillis() - start));
		}
	}
	public void insertUsePrepare()throws Exception
	{
		long start = System.currentTimeMillis();
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url
				, user , pass);
			// 使用Connection來建立一個PreparedStatement物件
			PreparedStatement pstmt = conn.prepareStatement(
				"insert into student_table values(null,?,1)"))

		{
			// 100次為PreparedStatement的參數設值，就可以插入100條記錄
			for (int i = 0; i < 100 ; i++ )
			{
				pstmt.setString(1 , "姓名" + i);
				pstmt.executeUpdate();
			}
			System.out.println("使用PreparedStatement費時:"
				+ (System.currentTimeMillis() - start));
		}
	}
	public static void main(String[] args) throws Exception
	{
		PreparedStatementTest pt = new PreparedStatementTest();
		pt.initParam("mysql.ini");
		pt.insertUseStatement();
		pt.insertUsePrepare();
	}
}
