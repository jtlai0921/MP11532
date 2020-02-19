
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
public class ReadTeacher
{
	public static void main(String[] args)
	{
		try(
			// 建立一個ObjectInputStream輸出串流
			ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("teacher.txt")))
		{
			// 依次讀取ObjectInputStream輸入串流中的四個物件
			Teacher t1 = (Teacher)ois.readObject();
			Teacher t2 = (Teacher)ois.readObject();
			Person p = (Person)ois.readObject();
			Teacher t3 = (Teacher)ois.readObject();
			// 輸出true
			System.out.println("t1的student參照和p是否相同："
				+ (t1.getStudent() == p));
			// 輸出true
			System.out.println("t2的student參照和p是否相同："
				+ (t2.getStudent() == p));
			// 輸出true
			System.out.println("t2和t3是否是同一個物件："
				+ (t2 == t3));
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
