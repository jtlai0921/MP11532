
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
public class FileWriterTest
{
	public static void main(String[] args)
	{
		try(
			FileWriter fw = new FileWriter("poem.txt"))
		{
			fw.write("錦瑟 - 李商隱\r\n");
			fw.write("錦瑟無端五十弦，一弦一柱思華年。\r\n");
			fw.write("莊生曉夢迷蝴蝶，望帝春心託杜鵑。\r\n");
			fw.write("滄海月明珠有淚，藍田日暖玉生煙。\r\n");
			fw.write("此情可待成追憶，只是當時已惘然。\r\n");
		}
		catch (IOException ioe)
		{
			ioe.printStackTrace();
		}
	}
}
