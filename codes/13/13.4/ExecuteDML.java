
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
public class ExecuteDML
{
	private String driver;
	private String url;
	private String user;
	private String pass;

	public void initParam(String paramFile)
		throws Exception
	{
		// 使用Properties類別來載入屬性檔
		Properties props = new Properties();
		props.load(new FileInputStream(paramFile));
		driver = props.getProperty("driver");
		url = props.getProperty("url");
		user = props.getProperty("user");
		pass = props.getProperty("pass");
	}
	public int insertData(String sql)throws Exception
	{
		// 載入驅動
		Class.forName(driver);
		try(
			// 獲取資料庫連接
			Connection conn = DriverManager.getConnection(url
				, user , pass);
			// 使用Connection來建立一個Statment物件
			Statement stmt = conn.createStatement())
		{
			// 執行DML,返回受影響的記錄條數
			return stmt.executeUpdate(sql);
		}
	}
	public static void main(String[] args)throws Exception
	{
		ExecuteDML ed = new ExecuteDML();
		ed.initParam("mysql.ini");
		int result = ed.insertData("insert into jdbc_test(jdbc_name,jdbc_desc)"
			+ "select s.student_name , t.teacher_name "
			+ "from student_table s , teacher_table t "
			+ "where s.java_teacher = t.teacher_id;");
		System.out.println("--系統中共有" + result + "條記錄受影響--");
	}
}
