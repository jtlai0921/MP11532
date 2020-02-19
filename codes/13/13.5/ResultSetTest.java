
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
public class ResultSetTest
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
	public void query(String sql)throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url , user , pass);
			// 使用Connection來建立一個PreparedStatement物件
			// 傳入控制結果集可捲動，可更新的參數。
			PreparedStatement pstmt = conn.prepareStatement(sql
				, ResultSet.TYPE_SCROLL_INSENSITIVE
				, ResultSet.CONCUR_UPDATABLE);
			ResultSet rs = pstmt.executeQuery())
		{
			rs.last();
			int rowCount = rs.getRow();
			for (int i = rowCount; i > 0 ; i-- )
			{
				rs.absolute(i);
				System.out.println(rs.getString(1) + "\t"
					+ rs.getString(2) + "\t" + rs.getString(3));
				// 修改記錄指位器所有記錄、第2欄的值
				rs.updateString(2 , "學生名" + i);
				// 提交修改
				rs.updateRow();
			}
		}
	}
	public static void main(String[] args) throws Exception
	{
		ResultSetTest rt = new ResultSetTest();
		rt.initParam("mysql.ini");
		rt.query("select * from student_table");
	}
}
