
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
public class SerializeMutable
{
	public static void main(String[] args)
	{

		try(
			// 建立一個ObjectOutputStream輸入串流
			ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("mutable.txt"));
			// 建立一個ObjectInputStream輸入串流
			ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("mutable.txt")))
		{
			Person per = new Person("孫悟空", 500);
			// 系統會per物件轉換位元組序列並輸出
			oos.writeObject(per);
			// 改變per物件的name實例變數
			per.setName("豬八戒");
			// 系統只是輸出序列化編號，所以改變後的name不會被序列化
			oos.writeObject(per);
			Person p1 = (Person)ois.readObject();    //①
			Person p2 = (Person)ois.readObject();    //②
			// 下面輸出true，即反序列化後p1等於p2
			System.out.println(p1 == p2);
			// 下面依然看到輸出"孫悟空"，即改變後的實例變數沒有被序列化
			System.out.println(p2.getName());
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}