
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
public class CachedRowSetPage
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

	public CachedRowSet query(String sql , int pageSize
		, int page)throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url , user , pass);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(sql))
		{
			// 使用RowSetProvider建立RowSetFactory
			RowSetFactory factory = RowSetProvider.newFactory();
			// 建立預設的CachedRowSet實例
			CachedRowSet cachedRs = factory.createCachedRowSet();
			// 設置每頁顯示pageSize條記錄
			cachedRs.setPageSize(pageSize);
			// 使用ResultSet裝填RowSet，設置從第幾條記錄開始
			cachedRs.populate(rs , (page - 1) * pageSize + 1);
			return cachedRs;
		}
	}
	public static void main(String[] args)throws Exception
	{
		CachedRowSetPage cp = new CachedRowSetPage();
		cp.initParam("mysql.ini");
		CachedRowSet rs = cp.query("select * from student_table" , 3 , 2);   // ①
		// 向後捲動結果集
		while (rs.next())
		{
			System.out.println(rs.getString(1)
				+ "\t" + rs.getString(2)
				+ "\t" + rs.getString(3));
		}
	}
}