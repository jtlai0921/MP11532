
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
public class ExecuteSQL
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
	public void executeSql(String sql)throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url
				, user , pass);
			// 使用Connection來建立一個Statement物件
			Statement stmt = conn.createStatement())
		{
			// 執行SQL,返回boolean值表示是否包含ResultSet
			boolean hasResultSet = stmt.execute(sql);
			// 如果執行後有ResultSet結果集
			if (hasResultSet)
			{
				try(
					// 獲取結果集
					ResultSet rs = stmt.getResultSet())
				{
					// ResultSetMetaData是用於分析結果集的元資料介面
					ResultSetMetaData rsmd = rs.getMetaData();
					int columnCount = rsmd.getColumnCount();
					// 迭代輸出ResultSet物件
					while (rs.next())
					{
						// 依次輸出每列的值
						for (int i = 0 ; i < columnCount ; i++ )
						{
							System.out.print(rs.getString(i + 1) + "\t");
						}
						System.out.print("\n");
					}
				}
			}
			else
			{
				System.out.println("該SQL語句影響的記錄有"
					+ stmt.getUpdateCount() + "條");
			}
		}
	}
	public static void main(String[] args) throws Exception
	{
		ExecuteSQL es = new ExecuteSQL();
		es.initParam("mysql.ini");
		System.out.println("------執行刪除表的DDL語句-----");
		es.executeSql("drop table if exists my_test");
		System.out.println("------執行建表的DDL語句-----");
		es.executeSql("create table my_test"
			+ "(test_id int auto_increment primary key, "
			+ "test_name varchar(255))");
		System.out.println("------執行插入資料的DML語句-----");
		es.executeSql("insert into my_test(test_name) "
			+ "select student_name from student_table");
		System.out.println("------執行查詢資料的查詢語句-----");
		es.executeSql("select * from my_test");
	}
}
