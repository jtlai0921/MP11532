
import java.sql.*;
import java.util.*;
import java.io.*;
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
public class DatabaseMetaDataTest
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
	public void info() throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url
				, user , pass))
		{
			// 獲取的DatabaseMetaData物件
			DatabaseMetaData dbmd = conn.getMetaData();
			// 獲取MySQL支援的所有表類型
			ResultSet rs = dbmd.getTableTypes();
			System.out.println("--MySQL支援的表類型資訊--");
			printResultSet(rs);
			// 獲取當前資料庫的全部資料表
			rs = dbmd.getTables(null,null, "%" , new String[]{"TABLE"});
			System.out.println("--當前資料庫裡的資料表資訊--");
			printResultSet(rs);
			// 獲取student_table表的主鍵
			rs = dbmd.getPrimaryKeys(null , null, "student_table");
			System.out.println("--student_table表的主鍵資訊--");
			printResultSet(rs);
			// 獲取當前資料庫的全部儲存過程
			rs = dbmd.getProcedures(null , null, "%");
			System.out.println("--當前資料庫裡的儲存過程資訊--");
			printResultSet(rs);
			// 獲取teacher_table表和student_table之間的外鍵約束
			rs = dbmd.getCrossReference(null,null, "teacher_table"
				, null, null, "student_table");
			System.out.println("--teacher_table表和student_table之間"
				+ "的外鍵約束--");
			printResultSet(rs);
			// 獲取student_table表的全部資料列
			rs = dbmd.getColumns(null, null, "student_table", "%");
			System.out.println("--student_table表的全部資料列--");
			printResultSet(rs);
		}
	}
	public void printResultSet(ResultSet rs)throws SQLException
	{
		ResultSetMetaData rsmd = rs.getMetaData();
		// 列印ResultSet的所有欄標題
		for (int i = 0 ; i < rsmd.getColumnCount() ; i++ )
		{
			System.out.print(rsmd.getColumnName(i + 1) + "\t");
		}
		System.out.print("\n");
		// 列印ResultSet裡的全部資料
		while (rs.next())
		{
			for (int i = 0; i < rsmd.getColumnCount() ; i++ )
			{
				System.out.print(rs.getString(i + 1) + "\t");
			}
			System.out.print("\n");
		}
		rs.close();
	}
	public static void main(String[] args)
		throws Exception
	{
		DatabaseMetaDataTest dt = new DatabaseMetaDataTest();
		dt.initParam("mysql.ini");
		dt.info();
	}
}
