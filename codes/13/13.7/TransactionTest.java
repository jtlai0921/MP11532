
import java.sql.*;
import java.io.*;
import java.util.*;
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
public class TransactionTest
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
	public void insertInTransaction(String[] sqls) throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			Connection conn = DriverManager.getConnection(url , user , pass))
		{
			// 關閉自動提交，開啟交易
			conn.setAutoCommit(false);
			try(
				// 使用Connection來建立一個Statment物件
				Statement stmt = conn.createStatement())
			{
				// 迴圈多次執行SQL語句
				for (String sql : sqls)
				{
					stmt.executeUpdate(sql);
				}
			}
			// 提交交易
			conn.commit();
		}
	}
	public static void main(String[] args) throws Exception
	{
		TransactionTest tt = new TransactionTest();
		tt.initParam("mysql.ini");
		String[] sqls = new String[]{
			"insert into student_table values(null , 'aaa' ,1)",
			"insert into student_table values(null , 'bbb' ,1)",
			"insert into student_table values(null , 'ccc' ,1)",
			// 下面這條SQL語句將會違反外鍵約束，
			// 因為teacher_table中沒有ID為5的記錄。
			"insert into student_table values(null , 'ccc' ,5)" //①
		};
		tt.insertInTransaction(sqls);
	}
}
