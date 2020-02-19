
import java.io.*;
import java.util.*;
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
public class ReplaceTest
{
	public static void main(String[] args)
	{
		try(
			// 建立一個ObjectOutputStream輸出串流
			ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("replace.txt"));
			// 建立一個ObjectInputStream輸入串流
			ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("replace.txt")))
		{
			Person per = new Person("孫悟空", 500);
			// 系統將per物件轉換位元組序列並輸出
			oos.writeObject(per);
			// 反序列化讀取得到的是ArrayList
			ArrayList list = (ArrayList)ois.readObject();
			System.out.println(list);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}

