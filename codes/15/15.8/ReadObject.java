
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
public class ReadObject
{
	public static void main(String[] args)
	{
		try(
			// 建立一個ObjectInputStream輸入串流
			ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("object.txt")))
		{
			// 從輸入串流中讀取一個Java物件，並將其強制類型轉換為Person類別
			Person p = (Person)ois.readObject();
			System.out.println("名字為：" + p.getName()
				+ "\n年齡為：" + p.getAge());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}
