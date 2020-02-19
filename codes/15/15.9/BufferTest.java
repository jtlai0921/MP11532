
import java.nio.*;
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
public class BufferTest
{
	public static void main(String[] args)
	{
		// 建立Buffer
		CharBuffer buff = CharBuffer.allocate(8);    // ①
		System.out.println("capacity: "	+ buff.capacity());
		System.out.println("limit: " + buff.limit());
		System.out.println("position: " + buff.position());
		// 放入元素
		buff.put('a');
		buff.put('b');
		buff.put('c');      // ②
		System.out.println("加入三個元素後，position = "
			+ buff.position());
		// 呼叫flip()方法
		buff.flip();	  // ③
		System.out.println("執行flip()後，limit = " + buff.limit());
		System.out.println("position = " + buff.position());
		// 取出第一個元素
		System.out.println("第一個元素(position=0)：" + buff.get());  // ④
		System.out.println("取出一個元素後，position = "
			+ buff.position());
		// 呼叫clear方法
		buff.clear();     // ⑤
		System.out.println("執行clear()後，limit = " + buff.limit());
		System.out.println("執行clear()後，position = "
			+ buff.position());
		System.out.println("執行clear()後，緩衝區內容並沒有被清除："
			+ "第三個元素為：" +  buff.get(2));    // ⑥
		System.out.println("執行絕對讀取後，position = "
			+ buff.position());
	}
}
