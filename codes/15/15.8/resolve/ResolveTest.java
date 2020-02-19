
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
public class ResolveTest
{
	public static void main(String[] args)
	{
		try(
			// 建立一個ObjectOutputStream輸入串流
			ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("transient.txt"));
			// 建立一個ObjectInputStream輸入串流
			ObjectInputStream ois = new ObjectInputStream(
				new FileInputStream("transient.txt")))
		{
			oos.writeObject(Orientation.HORIZONTAL);
			Orientation ori = (Orientation)ois.readObject();
			System.out.println(ori == Orientation.HORIZONTAL);
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
	}
}