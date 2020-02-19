
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.sql.*;
import javax.sql.rowset.*;
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
public class CachedRowSetTest
{
	private static String driver;
	private static String url;
	private static String user;
	private static String pass;
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

	public CachedRowSet query(String sql)throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		// 獲取資料庫連接
		Connection conn = DriverManager.getConnection(url , user , pass);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		// 使用RowSetProvider建立RowSetFactory
		RowSetFactory factory = RowSetProvider.newFactory();
		// 建立預設的CachedRowSet實例
		CachedRowSet cachedRs = factory.createCachedRowSet();
		// 使用ResultSet裝填RowSet
		cachedRs.populate(rs);    // ①
		// 關閉資源
		rs.close();
		stmt.close();
		conn.close();
		return cachedRs;
	}
	public static void main(String[] args)throws Exception
	{
		CachedRowSetTest ct = new CachedRowSetTest();
		ct.initParam("mysql.ini");
		CachedRowSet rs = ct.query("select * from student_table");
		rs.afterLast();
		// 向前捲動結果集
		while (rs.previous())
		{
			System.out.println(rs.getString(1)
				+ "\t" + rs.getString(2)
				+ "\t" + rs.getString(3));
			if (rs.getInt("student_id") == 3)
			{
				// 修改指定記錄行
				rs.updateString("student_name", "孫悟空");
				rs.updateRow();
			}
		}
		// 重新獲取資料庫連接
		Connection conn = DriverManager.getConnection(url
			, user , pass);
		conn.setAutoCommit(false);
		// 把對RowSet所做的修改同步到底層資料庫
		rs.acceptChanges(conn);
	}
}