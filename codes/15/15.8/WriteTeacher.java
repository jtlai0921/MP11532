
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
public class WriteTeacher
{
	public static void main(String[] args)
	{
		try(
			// 建立一個ObjectOutputStream輸出串流
			ObjectOutputStream oos = new ObjectOutputStream(
				new FileOutputStream("teacher.txt")))
		{
			Person per = new Person("孫悟空", 500);
			Teacher t1 = new Teacher("唐僧" , per);
			Teacher t2 = new Teacher("菩提祖師" , per);
			// 依次將四個物件寫入輸出串流
			oos.writeObject(t1);
			oos.writeObject(t2);
			oos.writeObject(per);
			oos.writeObject(t2);
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}
}

