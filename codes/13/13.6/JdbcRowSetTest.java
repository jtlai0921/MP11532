
import java.util.*;
import java.io.*;
import java.sql.*;
import javax.sql.rowset.*;
import com.sun.rowset.*;
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
public class JdbcRowSetTest
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

	public void update(String sql)throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url , user , pass);
			// 建立JdbcRowSetImpl物件
			JdbcRowSet jdbcRs = new JdbcRowSetImpl(conn))   // ①
		{
			// 設置SQL查詢語句
			jdbcRs.setCommand(sql);
			// 執行查詢
			jdbcRs.execute();
			jdbcRs.afterLast();
			// 向前捲動結果集
			while (jdbcRs.previous())
			{
				System.out.println(jdbcRs.getString(1)
					+ "\t" + jdbcRs.getString(2)
					+ "\t" + jdbcRs.getString(3));
				if (jdbcRs.getInt("student_id") == 3)
				{
					// 修改指定記錄行
					jdbcRs.updateString("student_name", "孫悟空");
					jdbcRs.updateRow();
				}
			}
		}
	}
	public static void main(String[] args)throws Exception
	{
		JdbcRowSetTest jt = new JdbcRowSetTest();
		jt.initParam("mysql.ini");
		jt.update("select * from student_table");
	}
}
